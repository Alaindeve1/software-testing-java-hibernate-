package com.auca.Models;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID locationId;
    
    @Column(name = "location_code", nullable = false, unique = true)
    private String locationCode;
    
    @Column(name = "location_name", nullable = false)
    private String locationName;
    
    @Column(name = "location_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Location parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> children;
    
    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    
    // Constructors
    public Location() {}
    
    public Location(String locationCode, String locationName, LocationType locationType, Location parent) {
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.locationType = locationType;
        this.parent = parent;
    }
    
    // Getters and Setters
    public UUID getLocationId() {
        return locationId;
    }
    
    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }
    
    public String getLocationCode() {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    
    // Overloaded method for int parameter
    public void setLocationCode(int locationCode) {
        this.locationCode = String.valueOf(locationCode);
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public LocationType getLocationType() {
        return locationType;
    }
    
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
    
    public Location getParent() {
        return parent;
    }
    
    public void setParent(Location parent) {
        this.parent = parent;
    }
    
    public List<Location> getChildren() {
        return children;
    }
    
    public void setChildren(List<Location> children) {
        this.children = children;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    // Additional methods for backward compatibility with test files
    public void setCode(int code) {
        this.locationCode = String.valueOf(code);
    }
    
    public void setCode(String code) {
        this.locationCode = code;
    }
    
    public void setName(String name) {
        this.locationName = name;
    }
    
    public String getName() {
        return this.locationName;
    }
    
    public LocationType getType() {
        return this.locationType;
    }
    
    public void setType(LocationType type) {
        this.locationType = type;
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationCode='" + locationCode + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationType=" + locationType +
                '}';
    }
}
