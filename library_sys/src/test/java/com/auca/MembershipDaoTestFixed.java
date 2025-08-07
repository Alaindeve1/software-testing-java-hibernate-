package com.auca;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.auca.dao.*;
import com.auca.Models.*;
import java.time.LocalDate;
import java.util.List;

public class MembershipDaoTestFixed {
    
    private MembershipDao membershipDao;
    private MembershipTypeDao membershipTypeDao;
    private UserDao userDao;
    
    
    private User testUser;
    private MembershipType goldMembershipType;
    private MembershipType silverMembershipType;
    
    @Before
    public void setUp() {
        membershipDao = new MembershipDao();
        membershipTypeDao = new MembershipTypeDao();
        userDao = new UserDao();
        //locationDao = new LocationDao();
        
        // // Create complete hierarchy for user location
        // locationDao.saveLocationWithParentCode("PROV001", "Southern Province", LocationType.PROVINCE, null);
        // locationDao.saveLocationWithParentCode("DIST001", "Muhanga District", LocationType.DISTRICT, "PROV001");
        // locationDao.saveLocationWithParentCode("SECT001", "Muhanga Sector", LocationType.SECTOR, "DIST001");
        // locationDao.saveLocationWithParentCode("CELL001", "Muhanga Cell", LocationType.CELL, "SECT001");
        // locationDao.saveLocationWithParentCode("VILL001", "Test Village", LocationType.VILLAGE, "CELL001");
        
        // Get the persisted location
        // Location testLocation = locationDao.getLocationByCodePublic("VILL001");
        
        // // Create and save multiple test users
        // testUser = new User();
        // testUser.setPersonId("1199080123456789");
        // testUser.setFirstName("Test");
        // testUser.setLastName("Member");
        // testUser.setUserName("testmember");
        // testUser.setPassword("password123");
        // testUser.setPhoneNumber("0781234567");
        // testUser.setRole(Role.STUDENT);
        // testUser.setGender(Gender.MALE);
        // testUser.setLocation(testLocation);
        // userDao.saveUser(testUser);
        

    }
    
    // Helper method to get or create GOLD membership type (shared across tests)
    private MembershipType getGoldMembershipType() {
        if (goldMembershipType == null) {
            goldMembershipType = new MembershipType();
            goldMembershipType.setMembershipName("GOLD");
            goldMembershipType.setMaxBooks(5);
            goldMembershipType.setPrice(50);
            membershipTypeDao.saveMembershipType(goldMembershipType);
            System.out.println("📋 Created GOLD membership type (can be shared by many members)");
        }
        return goldMembershipType;
    }
    
    // Helper method to get or create SILVER membership type (shared across tests)  
    private MembershipType getSilverMembershipType() {
        if (silverMembershipType == null) {
            silverMembershipType = new MembershipType();
            silverMembershipType.setMembershipName("SILVER");
            silverMembershipType.setMaxBooks(3);
            silverMembershipType.setPrice(30);
            membershipTypeDao.saveMembershipType(silverMembershipType);
            System.out.println("📋 Created SILVER membership type (can be shared by many members)");
        }
        return silverMembershipType;
    }
    
    @Test
    public void testSaveMembershipWithPersonId() {
        // Use GOLD membership type (shared across many members)
        String result = membershipDao.saveMembershipWithPersonId(
            "MEM001", 
            "1199080123456789", 
            getGoldMembershipType(),  
            LocalDate.now(), 
            LocalDate.now().plusYears(1), 
            Status.APPROVED
        );
        assertEquals("Membership saved Successfully", result);
        
        System.out.println("✅ GOLD membership created for person ID: 1199080123456789");
    }
    
    
    @Test
    public void testSaveMembershipWithNonExistentPersonId() {
        String result = membershipDao.saveMembershipWithPersonId(
            "MEM999", 
            "9999999999999999", 
            getGoldMembershipType(), 
            LocalDate.now(), 
            LocalDate.now().plusYears(1), 
            Status.APPROVED
        );
        assertEquals("Error: User with person ID 9999999999999999 not found", result);
        
        System.out.println("✅ Non-existent person ID test passed");
    }
    
    @Test
    public void testGetMembershipByCode() {
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        Membership retrievedMembership = membershipDao.getMembershipByCode("MEM001");
        assertNotNull("Membership should be retrieved", retrievedMembership);
        assertEquals("MEM001", retrievedMembership.getMembershipCode());
        assertEquals("GOLD", retrievedMembership.getMembershipType().getMembershipName());
        
        System.out.println("✅ Retrieved GOLD membership for person ID: " + retrievedMembership.getReader().getPersonId());
    }
    
    @Test
    public void testGetMembershipsByPersonId() {
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        List<Membership> memberships = membershipDao.getMembershipsByPersonId("1199080123456789");
        assertNotNull("Memberships should be found", memberships);
        assertTrue("Should have at least one membership", memberships.size() > 0);
        assertEquals("MEM001", memberships.get(0).getMembershipCode());
        
        System.out.println("🎯 Found " + memberships.size() + " membership(s) for person ID: 1199080123456789");
    }
    
    @Test
    public void testDifferentMembershipTypes() {
        // Test that we can create different membership types and assign them to different people
        String goldResult = membershipDao.saveMembershipWithPersonId(
            "MEM001", "1199080123456789", getGoldMembershipType(), 
            LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED
        );
        
        // Create another user for silver membership
        User silverUser = new User();
        silverUser.setPersonId("3300092345678901");
        silverUser.setFirstName("Bob");
        silverUser.setLastName("Johnson");
        silverUser.setUserName("bobjohnson");
        silverUser.setPassword("password789");
        silverUser.setPhoneNumber("0788888888");
        silverUser.setRole(Role.STUDENT);
        silverUser.setGender(Gender.MALE);
        silverUser.setLocation(testUser.getLocation());
        userDao.saveUser(silverUser);
        
        String silverResult = membershipDao.saveMembershipWithPersonId(
            "MEM003", "3300092345678901", getSilverMembershipType(),  // Different membership type
            LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED
        );
        
        assertEquals("Membership saved Successfully", goldResult);
        assertEquals("Membership saved Successfully", silverResult);
        
        System.out.println("✅ Created GOLD and SILVER memberships - both membership types can serve multiple members");
    }
    
    @Test
    public void testSaveMembershipType() {
        MembershipType premiumType = new MembershipType();
        premiumType.setMembershipName("PREMIUM");
        premiumType.setMaxBooks(10);
        premiumType.setPrice(100);
        
        String result = membershipTypeDao.saveMembershipType(premiumType);
        assertEquals("MembershipType saved Successfully", result);
        
        System.out.println("✅ Created new PREMIUM membership type - ready to be used by many members");
    }
}
