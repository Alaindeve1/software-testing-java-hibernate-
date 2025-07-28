package com.auca.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.User;
import com.auca.Models.Role;
import com.auca.Models.Location;
import com.auca.utils.PasswordHasher;
//import com.auca.dao.LocationDao;

public class UserDao {
    
    Connection connection = new Connection();
    LocationDao locationDao = new LocationDao();
    
    public String saveUser(User user) {
        try {
            // Hash the password before saving
            String plainPassword = user.getPassword();
            String hashedPassword = PasswordHasher.hashPassword(plainPassword);
            user.setPassword(hashedPassword);
            
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            session.close();
            return "User saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving user: " + e.getMessage();
        }
    }
    
    public User getUserByUserName(String userName) {
        try {
            Session session = connection.getSession();
            Query<User> query = session.createQuery("FROM User WHERE userName = :userName", User.class);
            query.setParameter("userName", userName);
            User user = query.uniqueResult();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User getUserByPersonId(String personId) {
        try {
            Session session = connection.getSession();
            Query<User> query = session.createQuery("FROM User WHERE personId = :personId", User.class);
            query.setParameter("personId", personId);
            User user = query.uniqueResult();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<User> getAllUsers() {
        try {
            Session session = connection.getSession();
            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> users = query.getResultList();
            session.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<User> getUsersByRole(Role role) {
        try {
            Session session = connection.getSession();
            Query<User> query = session.createQuery("FROM User WHERE role = :role", User.class);
            query.setParameter("role", role);
            List<User> users = query.getResultList();
            session.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateUser(User user) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            session.close();
            return "User updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating user: " + e.getMessage();
        }
    }
    
    public String deleteUser(String personId) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, personId);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                session.close();
                return "User deleted Successfully";
            } else {
                session.close();
                return "User not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting user: " + e.getMessage();
        }
    }
    
    /**
     * Authenticate user with username and password
     * @param userName The username
     * @param password The plain text password
     * @return The User object if authentication successful, null otherwise
     */
    public User authenticateUser(String userName, String password) {
        try {
            User user = getUserByUserName(userName);
            if (user != null && PasswordHasher.verifyPassword(password, user.getPassword())) {
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Change user password
     * @param userName The username
     * @param oldPassword The current password
     * @param newPassword The new password
     * @return Success message or error message
     */
    public String changePassword(String userName, String oldPassword, String newPassword) {
        try {
            User user = authenticateUser(userName, oldPassword);
            if (user == null) {
                return "Invalid current password";
            }
            
            // Hash the new password
            String hashedNewPassword = PasswordHasher.hashPassword(newPassword);
            user.setPassword(hashedNewPassword);
            
            // Update user
            return updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error changing password: " + e.getMessage();
        }
    }
    
    /**
     * Get the province name where a person lives by their person ID
     * @param personId The person's ID
     * @return The province name where the person lives, or null if not found
     */
    public String getProvinceNameByPersonId(String personId) {
        try {
            // Get the user by person ID
            User user = getUserByPersonId(personId);
            if (user == null) {
                return null;
            }
            
            // Get the user's village/location
            Location userVillage = user.getVillage();
            if (userVillage == null) {
                return null;
            }
            
            // Use LocationDao to get the province name from village ID
            return locationDao.getProvinceNameByVillageId(userVillage.getLocationId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}