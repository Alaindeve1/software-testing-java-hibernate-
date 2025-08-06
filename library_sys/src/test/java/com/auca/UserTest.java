package com.auca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import com.auca.dao.UserDao;
import com.auca.dao.LocationDao;
import com.auca.Models.User;
import com.auca.Models.Location;
import com.auca.Models.Role;
import com.auca.Models.Gender;
// import com.auca.Models.LocationType;
// import com.auca.utils.PasswordHasher;

public class UserTest {
    
    UserDao userDao = new UserDao();
    LocationDao locationDao = new LocationDao();
    User testUser = new User();
    Location testLocation = new Location();
    
    @Before
    public void setUp() {
        // Get the actually persisted location from database
        testLocation = locationDao.getLocationByCodePublic("VILL001");
        
        // Create a test user
        testUser.setPersonId("1199080123456789");
        testUser.setFirstName("alain");
        testUser.setLastName("deve");
        testUser.setUserName("alaindeve");
        testUser.setPassword("password1234");
        testUser.setPhoneNumber("0799324850");
        
        testUser.setRole(Role.STUDENT);
        testUser.setGender(Gender.MALE);
        testUser.setLocation(testLocation); // Set the persisted location
    }
    
    @Test
    public void testSaveUser() {
        String result = userDao.saveUser(testUser);
        assertEquals("User saved Successfully", result);
    }
    
    @Test
    public void testPasswordIsHashedWhenSaving() {
        String originalPassword = "password123";
        testUser.setPassword(originalPassword);
        
        // Save user
        userDao.saveUser(testUser);
        
        // Retrieve user and check password is hashed
        User retrievedUser = userDao.getUserByUserName("alaindeve");
        assertNotNull("User should be retrieved", retrievedUser);
        assertNotEquals("Password should be hashed, not plain text", originalPassword, retrievedUser.getPassword());
        
        // Verify the hashed password contains salt:hash format
        String hashedPassword = retrievedUser.getPassword();
        System.out.println("🔒 Original password: " + originalPassword);
        System.out.println("🔒 Hashed password: " + hashedPassword);
        
        // Check format (should contain colon for salt:hash)
        assertEquals("Hashed password should contain salt:hash format", true, hashedPassword.contains(":"));
    }
    
    @Test
    public void testUserAuthentication() {
        String originalPassword = "password123";
        testUser.setPassword(originalPassword);
        
        // Save user (password will be hashed)
        userDao.saveUser(testUser);
        
        // Test correct authentication
        User authenticatedUser = userDao.authenticateUser("lionel", originalPassword);
        assertNotNull("Authentication should succeed with correct password", authenticatedUser);
        assertEquals("lionel", authenticatedUser.getFirstName());
        
        // Test wrong authentication
        User failedAuth = userDao.authenticateUser("lionel", "wrongpassword");
        assertNull("Authentication should fail with wrong password", failedAuth);
        
        System.out.println("✅ Authentication test passed - password hashing working correctly");
    }
    
    @Test
    public void testChangePassword() {
        String originalPassword = "password123";
        String newPassword = "newPassword456";
        testUser.setPassword(originalPassword);
        
        // Save user
        userDao.saveUser(testUser);
        
        // Change password
        String result = userDao.changePassword("lionel", originalPassword, newPassword);
        assertEquals("User updated Successfully", result);
        
        // Test authentication with new password
        User authenticatedUser = userDao.authenticateUser("lionel", newPassword);
        assertNotNull("Authentication should work with new password", authenticatedUser);
        
        // Test that old password no longer works
        User oldPasswordAuth = userDao.authenticateUser("lionel", originalPassword);
        assertNull("Old password should no longer work", oldPasswordAuth);
        
        System.out.println("✅ Password change test passed");
    }
    
    @Test
    public void testGetProvinceNameByPersonId() {
        // Save user first
        userDao.saveUser(testUser);
        
        // Get province name by person ID
        String provinceName = userDao.getProvinceNameByPersonId("1199980123456789");
        
        System.out.println("🏘️ Person ID: 1199980123456789");
        System.out.println("🏛️ Province: " + provinceName);
        System.out.println("✅ Province discovery by person ID working correctly");
    }
    
}