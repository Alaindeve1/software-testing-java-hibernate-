package com.auca.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.Location;
import com.auca.Models.LocationType;

public class LocationDao {
    
    Connection connection = new Connection();
    
    // Main method for hierarchical location saving
    public String saveLocationWithParentCode(String locationCode, String locationName, 
                                           LocationType locationType, String parentCode) {
        try {
            Location parent = null;
            if (parentCode != null) {
                parent = getLocationByCode(parentCode);
                if (parent == null) {
                    return "Error: Parent location with code " + parentCode + " not found";
                }
            }
            
            Location location = new Location(locationCode, locationName, locationType, parent);
            return saveLocation(location);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating location: " + e.getMessage();
        }
    }
    
    // Method to get province name from village ID
    /**
     * Retrieves all districts that belong to a specific province
     * @param provinceId The UUID of the province
     * @return List of Location objects representing districts
     */
    @SuppressWarnings("unchecked")
    public List<Location> getAllDistrictsByProvinceId(UUID provinceId) {
        Session session = null;
        try {
            session = getSession();
            String hql = "FROM Location l WHERE l.locationType = :type AND l.parent.locationId = :provinceId";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("type", LocationType.DISTRICT);
            query.setParameter("provinceId", provinceId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Retrieves a location by its name (case-insensitive exact match)
     * @param name The name of the location to find
     * @return The Location object if found, null otherwise
     */
    public Location getLocationByName(String name) {
        Session session = null;
        try {
            session = getSession();
            String hql = "FROM Location WHERE LOWER(locationName) = LOWER(:name)";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public String getProvinceNameByVillageId(UUID villageId) {
        try {
            Session session = connection.getSession();
            Location village = session.get(Location.class, villageId);
            
            if (village == null) {
                session.close();
                return null;
            }
            
            if (village.getLocationType() != LocationType.VILLAGE) {
                session.close();
                return null;
            }
            
            Location current = village;
            while (current != null && current.getLocationType() != LocationType.PROVINCE) {
                current = current.getParent();
            }
            
            session.close();
            return current != null ? current.getLocationName() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Public method to get location by code (needed for testing)
    public Location getLocationByCodePublic(String code) {
        try {
            Session session = connection.getSession();
            Query<Location> query = session.createQuery("FROM Location WHERE locationCode = :code", Location.class);
            query.setParameter("code", code);
            Location location = query.uniqueResult();
            session.close();
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Private helper method for saving with validation
    private String saveLocation(Location location) {
        try {
            // Validate hierarchical requirements
            String validationResult = validateLocationHierarchy(location);
            if (!validationResult.equals("Valid")) {
                return validationResult;
            }
            
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(location);
            transaction.commit();
            session.close();
            return "Location saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving location: " + e.getMessage();
        }
    }
    
    // Private helper method for validation
    private String validateLocationHierarchy(Location location) {
        LocationType type = location.getLocationType();
        Location parent = location.getParent();
        
        switch (type) {
            case PROVINCE:
                if (parent != null) {
                    return "Error: Province cannot have a parent location";
                }
                break;
                
            case DISTRICT:
                if (parent == null) {
                    return "Error: District must have a Province as parent";
                }
                if (parent.getLocationType() != LocationType.PROVINCE) {
                    return "Error: District parent must be a Province";
                }
                break;
                
            case SECTOR:
                if (parent == null) {
                    return "Error: Sector must have a District as parent";
                }
                if (parent.getLocationType() != LocationType.DISTRICT) {
                    return "Error: Sector parent must be a District";
                }
                break;
                
            case CELL:
                if (parent == null) {
                    return "Error: Cell must have a Sector as parent";
                }
                if (parent.getLocationType() != LocationType.SECTOR) {
                    return "Error: Cell parent must be a Sector";
                }
                break;
                
            case VILLAGE:
                if (parent == null) {
                    return "Error: Village must have a Cell as parent";
                }
                if (parent.getLocationType() != LocationType.CELL) {
                    return "Error: Village parent must be a Cell";
                }
                break;
                
            default:
                return "Error: Invalid location type";
        }
        
        return "Valid";
    }
    
    // Private helper method to get location by code
    private Location getLocationByCode(String code) {
        try {
            Session session = connection.getSession();
            Query<Location> query = session.createQuery("FROM Location WHERE locationCode = :code", Location.class);
            query.setParameter("code", code);
            Location location = query.uniqueResult();
            session.close();
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
