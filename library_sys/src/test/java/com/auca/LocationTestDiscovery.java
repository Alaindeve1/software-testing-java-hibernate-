package com.auca;

import com.auca.Models.*;
import com.auca.dao.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class LocationTestDiscovery {
    
    private LocationDao locationDao;
    
    @Before
    public void setUp() {
        locationDao = new LocationDao();
    }
    
    @Test 
    public void testRandomVillageProvinceDiscovery() {
        System.out.println("\n=== RANDOM VILLAGE DISCOVERY TEST ===");
        
        // Test with any village ID and discover its province
        String[] testVillages = {"VILL101",};
        
        for (String villageCode : testVillages) {
            Location village = locationDao.getLocationByCodePublic(villageCode);
            String discoveredProvince = locationDao.getProvinceNameByVillageId(village.getLocationId());
            
            System.out.println("🏘️  Village '" + village.getLocationName() + "' automatically discovered to belong to: '" + discoveredProvince + "'");
            
            // Assert that discovery works (not checking for specific province)
            assertNotNull("Should discover a province for any valid village", discoveredProvince);
        }
        
        System.out.println("=== END RANDOM DISCOVERY TEST ===\n");
    }
    
}
