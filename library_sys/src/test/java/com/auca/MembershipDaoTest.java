package com.auca;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.auca.dao.*;
import com.auca.Models.*;
import java.time.LocalDate;
import java.util.List;

public class MembershipDaoTest {
    
    private MembershipDao membershipDao;
    private MembershipTypeDao membershipTypeDao;
    private UserDao userDao;
    
    
    private User testUser;
    private MembershipType goldMembershipType;
    private MembershipType silverMembershipType;
    private MembershipType striverMembershipType;
    
    @Before
    public void setUp() {
        membershipDao = new MembershipDao();
        membershipTypeDao = new MembershipTypeDao();
        userDao = new UserDao();
        
        
       
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
    
    private MembershipType getStriverMembershipType() {
        if (striverMembershipType == null) {
            striverMembershipType = new MembershipType();
            striverMembershipType.setMembershipName("SILVER");
            striverMembershipType.setMaxBooks(2);
            striverMembershipType.setPrice(10);
            membershipTypeDao.saveMembershipType(striverMembershipType);
            System.out.println("📋 Created SILVER membership type (can be shared by many members)");
        }
        return striverMembershipType;
    }
    @Test
    public void testSaveMembershipWithPersonId() {
        // Use GOLD membership type (shared across many members)
        String result = membershipDao.saveMembershipWithPersonId(
            "MEM002", 
            "1199980123456789", 
            getStriverMembershipType(),  // This membership type can be used by many people
            LocalDate.now(), 
            LocalDate.now().plusYears(1), 
            Status.APPROVED
        );
        assertEquals("Membership saved Successfully", result);
        
        System.out.println("membership created for person ID: 1199080123456789");
    }
    
    @Test
    public void testSaveMembershipWithNonExistentPersonId() {
        // Test with non-existent person ID
        String result = membershipDao.saveMembershipWithPersonId(
            "MEM002", 
            "123456789", // Non-existent person ID
            getGoldMembershipType(), 
            LocalDate.now(), 
            LocalDate.now().plusYears(1), 
            Status.APPROVED
        );
        assertEquals("Error: User with person ID 123456789 not found", result);
        
        System.out.println("✅ Non-existent person ID test passed");
    }
    
    @Test
    public void testGetMembershipByCode() {
        // Create membership using person ID
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        Membership retrievedMembership = membershipDao.getMembershipByCode("MEM001");
        assertNotNull("Membership should be retrieved", retrievedMembership);
        assertEquals("MEM001", retrievedMembership.getMembershipCode());
        
        System.out.println("✅ Retrieved membership for person ID: " + retrievedMembership.getReader().getPersonId());
    }
    
    @Test
    public void testGetMembershipsByPersonId() {
        // Create membership using person ID
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        // Get memberships by person ID
        List<Membership> memberships = membershipDao.getMembershipsByPersonId("1199080123456789");
        assertNotNull("Memberships should be found", memberships);
        assertTrue("Should have at least one membership", memberships.size() > 0);
        assertEquals("MEM001", memberships.get(0).getMembershipCode());
        
        System.out.println("🎯 Found " + memberships.size() + " membership(s) for person ID: 1199080123456789");
    }
    
    @Test
    public void testGetAllMemberships() {
        // Create membership using person ID
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        List<Membership> allMemberships = membershipDao.getAllMemberships();
        assertNotNull("Memberships list should not be null", allMemberships);
        assertTrue("Should have at least one membership", allMemberships.size() > 0);
    }
    
    @Test
    public void testGetMembershipsByStatus() {
         //Create membership using person ID
        membershipDao.saveMembershipWithPersonId("MEM001", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED);
        
        List<Membership> approvedMemberships = membershipDao.getMembershipsByStatus(Status.APPROVED);
        assertNotNull("Approved memberships should not be null", approvedMemberships);
        assertTrue("Should have at least one approved membership", approvedMemberships.size() > 0);
    }
    
    @Test
    public void testGetPendingMemberships() {
        // Create a pending membership using person ID
        membershipDao.saveMembershipWithPersonId("MEM002", "1199080123456789", getGoldMembershipType(), 
                                                LocalDate.now(), LocalDate.now().plusYears(1), Status.PENDING);
        
        List<Membership> pendingMemberships = membershipDao.getMembershipsByStatus(Status.PENDING);
        assertNotNull("Pending memberships should not be null", pendingMemberships);
        assertTrue("Should have at least one pending membership", pendingMemberships.size() > 0);
        
        System.out.println("🎯 Found " + pendingMemberships.size() + " pending membership(s)");
    }
    
    @Test
    public void testMultipleMembershipsUsingSameMembershipType() {
        // Create second user
        User secondUser = new User();
        secondUser.setPersonId("2200091234567890");
        secondUser.setFirstName("Jane");
        secondUser.setLastName("Smith");
        secondUser.setUserName("janesmith");
        secondUser.setPassword("password456");
        secondUser.setPhoneNumber("0787654321");
        secondUser.setRole(Role.STUDENT);
        secondUser.setGender(Gender.FEMALE);
        secondUser.setLocation(testUser.getLocation());
        userDao.saveUser(secondUser);
        
        // Both users get GOLD membership (same membership type used by multiple people)
        String result1 = membershipDao.saveMembershipWithPersonId(
            "MEM001", "1199080123456789", getGoldMembershipType(), 
            LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED
        );
        
        String result2 = membershipDao.saveMembershipWithPersonId(
            "MEM002", "2200091234567890", getGoldMembershipType(),  // Same membership type
            LocalDate.now(), LocalDate.now().plusYears(1), Status.APPROVED
        );
        
        assertEquals("Membership saved Successfully", result1);
        assertEquals("Membership saved Successfully", result2);
        
        System.out.println("✅ Two people now share the same GOLD membership type");
        System.out.println("📋 This demonstrates: one membership type → many memberships");
    }
    
    @Test
    public void testDifferentMembershipTypes() {
        // Test that we can create different membership types
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
        
        System.out.println("✅ Created GOLD and SILVER memberships");
        System.out.println("📋 Both membership types can serve multiple members");
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
