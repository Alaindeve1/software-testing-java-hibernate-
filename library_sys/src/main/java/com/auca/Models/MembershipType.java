package com.auca.Models;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "membership_type")
public class MembershipType {
    
    @Id
    @Column(name = "membership_type_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID membershipTypeId;
    
    @Column(name = "membership_name", nullable = false, unique = true)
    private String membershipName;
    
    @Column(name = "max_books", nullable = false)
    private int maxBooks;
    
    @Column(name = "price", nullable = false)
    private int price; // Price per day in Rwf
    
    @OneToMany(mappedBy = "membershipType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Membership> memberships;
    
    // Constructors
    public MembershipType() {}
    
    public MembershipType(String membershipName, int maxBooks, int price) {
        this.membershipName = membershipName;
        this.maxBooks = maxBooks;
        this.price = price;
    }
    
    // Getters and Setters
    public UUID getMembershipTypeId() {
        return membershipTypeId;
    }
    
    public void setMembershipTypeId(UUID membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }
    
    public String getMembershipName() {
        return membershipName;
    }
    
    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }
    
    public int getMaxBooks() {
        return maxBooks;
    }
    
    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public List<Membership> getMemberships() {
        return memberships;
    }
    
    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
    
    // Additional methods for backward compatibility
    public void setName(String name) {
        this.membershipName = name;
    }
    
    public String getName() {
        return this.membershipName;
    }
    
    // Overloaded setPrice method to handle double values
    public void setPrice(double price) {
        this.price = (int) price;
    }
}
