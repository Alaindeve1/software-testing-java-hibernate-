package com.auca.dao;

import com.auca.Models.Membership;
import com.auca.Models.MembershipType;
import com.auca.Models.User;
import com.auca.Models.Status;
//import com.auca.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MembershipDao {
    
    Connection connection = new Connection();
    UserDao userDao = new UserDao();
    
    public String saveMembership(Membership membership) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(membership);
            transaction.commit();
            session.close();
            return "Membership saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving membership: " + e.getMessage();
        }
    }
    
    public Membership getMembershipById(UUID id) {
        try {
            Session session = connection.getSession();
            Membership membership = session.get(Membership.class, id);
            session.close();
            return membership;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Membership getMembershipByCode(String membershipCode) {
        try {
            Session session = connection.getSession();
            Query<Membership> query = session.createQuery("FROM Membership WHERE membershipCode = :code", Membership.class);
            query.setParameter("code", membershipCode);
            Membership membership = query.uniqueResult();
            session.close();
            return membership;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Membership> getAllMemberships() {
        try {
            Session session = connection.getSession();
            Query<Membership> query = session.createQuery("FROM Membership", Membership.class);
            List<Membership> memberships = query.getResultList();
            session.close();
            return memberships;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Membership> getMembershipsByReader(User reader) {
        try {
            Session session = connection.getSession();
            Query<Membership> query = session.createQuery(
                "FROM Membership m WHERE m.reader = :reader", Membership.class);
            query.setParameter("reader", reader);
            List<Membership> memberships = query.getResultList();
            session.close();
            return memberships;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Membership> getMembershipsByStatus(Status status) {
        try {
            Session session = connection.getSession();
            Query<Membership> query = session.createQuery("FROM Membership WHERE membershipStatus = :status", Membership.class);
            query.setParameter("status", status);
            List<Membership> memberships = query.getResultList();
            session.close();
            return memberships;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Membership> getExpiredMemberships() {
        try {
            Session session = connection.getSession();
            Query<Membership> query = session.createQuery("FROM Membership WHERE expiringTime < CURRENT_DATE", Membership.class);
            List<Membership> memberships = query.getResultList();
            session.close();
            return memberships;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateMembership(Membership membership) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(membership);
            transaction.commit();
            session.close();
            return "Membership updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating membership: " + e.getMessage();
        }
    }
    
    public String deleteMembership(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            Membership membership = session.get(Membership.class, id);
            if (membership != null) {
                session.remove(membership);
                transaction.commit();
                session.close();
                return "Membership deleted Successfully";
            } else {
                session.close();
                return "Membership not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting membership: " + e.getMessage();
        }
    }
    
    /**
     * Save membership using user's person ID instead of User object
     * @param membershipCode The membership code
     * @param personId The person ID of the user
     * @param membershipType The membership type
     * @param registrationDate Registration date
     * @param expiringTime Expiring date
     * @param status Membership status
     * @return Success or error message
     */
    public String saveMembershipWithPersonId(String membershipCode, String personId, 
                                           MembershipType membershipType, LocalDate registrationDate, 
                                           LocalDate expiringTime, Status status) {
        try {
            // Look up the user by person ID
            User user = userDao.getUserByPersonId(personId);
            if (user == null) {
                return "Error: User with person ID " + personId + " not found";
            }
            
            // Create membership with the found user
            Membership membership = new Membership();
            membership.setMembershipCode(membershipCode);
            membership.setReader(user);
            membership.setMembershipType(membershipType);
            membership.setRegistrationDate(registrationDate);
            membership.setExpiringTime(expiringTime);
            membership.setStatus(status);
            
            // Save the membership
            return saveMembership(membership);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating membership: " + e.getMessage();
        }
    }
    
    /**
     * Get memberships by person ID
     * @param personId The person ID
     * @return List of memberships for the person
     */
    public List<Membership> getMembershipsByPersonId(String personId) {
        try {
            User user = userDao.getUserByPersonId(personId);
            if (user == null) {
                return null;
            }
            return getMembershipsByReader(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}