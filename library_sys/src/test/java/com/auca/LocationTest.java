package com.auca;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.auca.Models.LocationType;
import com.auca.dao.LocationDao;

public class LocationTest {
    
    private LocationDao locationDao;
    
    @Before
    public void setUp() {
        locationDao = new LocationDao();
    }
    
    
    //@Test
    public void testSaveProvinceSuccessfully() {
        String result = locationDao.saveLocationWithParentCode("PROV001", "SOUTHERN Province", LocationType.PROVINCE, null);
        assertEquals("Location saved Successfully", result);
    }
    
    @Test
    public void testSaveDistrictInDifferentProvinces() {      
        // Create Kayonza district in Eastern Province
        String result1 = locationDao.saveLocationWithParentCode("DIST001", "Kayonza District", LocationType.DISTRICT, "PROV002");
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
        // Save cell with sector parent code
        String cellResult = locationDao.saveLocationWithParentCode(
            "CELL001", "RULI Cell", LocationType.CELL, "SECT001");
        assertEquals("Location saved Successfully", cellResult);
    }
    
    @Test
    public void testSaveVillageWithCellParentSuccessfully() {
        // Save village with cell parent code
        String villageResult = locationDao.saveLocationWithParentCode(
            "VILL001", "MURAMBI Village", LocationType.VILLAGE, "CELL001");
        assertEquals("Location saved Successfully", villageResult);
    }
}
