package com.auca;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.auca.Models.Location;
import com.auca.Models.LocationType;
import com.auca.dao.LocationDao;

import java.util.List;

public class LocationTest {
    
    private LocationDao locationDao;
    
    @Before
    public void setUp() {
        locationDao = new LocationDao();
        // Set up test data
        locationDao.saveLocationWithParentCode("PROV001", "Southern Province", LocationType.PROVINCE, null);
        locationDao.saveLocationWithParentCode("DIST001", "Kayonza District", LocationType.DISTRICT, "PROV001");
        locationDao.saveLocationWithParentCode("DIST002", "Muhanga District", LocationType.DISTRICT, "PROV001");
    }
    
    @After
    public void tearDown() {
        // Clean up test data if needed
        locationDao = null;
    }
    
    
    //@Test
    public void testSaveProvinceSuccessfully() {
        String result = locationDao.saveLocationWithParentCode("PROV001", "SOUTHERN Province", LocationType.PROVINCE, null);
        assertEquals("Location saved Successfully", result);
    }
    
    @Test
    public void testSaveDistrictInDifferentProvinces() {      
        // Create Kayonza district in Eastern Province
        String result1 = locationDao.saveLocationWithParentCode("DIST002", "rustiro District", LocationType.DISTRICT, "PROV002");
        assertEquals("Location saved Successfully", result1);
        
        // Create Muhanga district in Southern Province
        String result2 = locationDao.saveLocationWithParentCode("DIST002", "Muhanga District", LocationType.DISTRICT, "PROV001");
        assertEquals("Location saved Successfully", result2);
    }
    
    @Test
    public void testSaveSectorWithDistrictParentSuccessfully() {
        // Save sector with district parent code
        String sectorResult = locationDao.saveLocationWithParentCode(
            "SECT001", "SHYOGWE Sector", LocationType.SECTOR, "DIST002");
        assertEquals("Location saved Successfully", sectorResult);
    }
                                
    @Test
    public void testSaveCellWithSectorParentSuccessfully() {
        // Save sector first
        locationDao.saveLocationWithParentCode("SECT001", "Test Sector", LocationType.SECTOR, "DIST001");
        
        // Save cell with sector parent code
        String cellResult = locationDao.saveLocationWithParentCode(
            "CELL001", "RULI Cell", LocationType.CELL, "SECT001");
        assertEquals("Location saved Successfully", cellResult);
    }
    
    @Test
    public void testGetAllDistrictsByProvinceId() {
        // Get the province ID
        Location province = locationDao.getLocationByCodePublic("PROV001");
        assertNotNull("Province should exist", province);
        
        // Get districts for the province
        List<Location> districts = locationDao.getAllDistrictsByProvinceId(province.getLocationId());
        
        // Verify we got the expected number of districts
        assertEquals("Should find 2 districts", 2, districts.size());
        
        // Verify each district has the correct parent
        for (Location district : districts) {
            assertEquals("District should have correct type", LocationType.DISTRICT, district.getLocationType());
            assertNotNull("District should have a parent", district.getParent());
            assertEquals("District should belong to the test province", 
                        province.getLocationId(), 
                        district.getParent().getLocationId());
        }
    }
    
    @Test
    public void testGetLocationByName() {
        // Test exact match
        Location kayonza = locationDao.getLocationByName("Kayonza District");
        assertNotNull("Should find Kayonza District", kayonza);
        assertEquals("Should have correct code", "DIST001", kayonza.getLocationCode());
        
        // Test case-insensitive match
        Location muhanga = locationDao.getLocationByName("muhanga district");
        assertNotNull("Should find Muhanga District (case-insensitive)", muhanga);
        assertEquals("Should have correct code", "DIST002", muhanga.getLocationCode());
        
        // Test non-existent location
        Location notFound = locationDao.getLocationByName("Nonexistent Location");
        assertNull("Should return null for non-existent location", notFound);
    }
    
    @Test
    public void testSaveVillageWithCellParentSuccessfully() {
        // Save village with cell parent code
        String villageResult = locationDao.saveLocationWithParentCode(
            "VILL001", "MURAMBI Village", LocationType.VILLAGE, "CELL001");
        assertEquals("Location saved Successfully", villageResult);
    }
}
