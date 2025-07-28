package com.auca.dao;

import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.MembershipType;

public class MembershipTypeDao {
    
    Connection connection = new Connection();
    
    public String saveMembershipType(MembershipType membershipType) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(membershipType);
            transaction.commit();
            session.close();
            return "MembershipType saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving membershipType: " + e.getMessage();
        }
    }
    
    public MembershipType getMembershipTypeById(UUID id) {
        try {
            Session session = connection.getSession();
            MembershipType membershipType = session.get(MembershipType.class, id);
            session.close();
            return membershipType;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public MembershipType getMembershipTypeByName(String name) {
        try {
            Session session = connection.getSession();
            Query<MembershipType> query = session.createQuery("FROM MembershipType WHERE membershipName = :name", MembershipType.class);
            query.setParameter("name", name);
            MembershipType membershipType = query.uniqueResult();
            session.close();
            return membershipType;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<MembershipType> getAllMembershipTypes() {
        try {
            Session session = connection.getSession();
            Query<MembershipType> query = session.createQuery("FROM MembershipType", MembershipType.class);
            List<MembershipType> membershipTypes = query.getResultList();
            session.close();
            return membershipTypes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateMembershipType(MembershipType membershipType) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(membershipType);
            transaction.commit();
            session.close();
            return "MembershipType updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating membershipType: " + e.getMessage();
        }
    }
    
    public String deleteMembershipType(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            MembershipType membershipType = session.get(MembershipType.class, id);
            if (membershipType != null) {
                session.remove(membershipType);
                transaction.commit();
                session.close();
                return "MembershipType deleted Successfully";
            } else {
                session.close();
                return "MembershipType not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting membershipType: " + e.getMessage();
        }
    }
}
