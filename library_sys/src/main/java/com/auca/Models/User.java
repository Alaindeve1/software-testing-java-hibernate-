package com.auca.Models;

//import com.auca.Models.Gender;
//import com.auca.Models.Role;
import jakarta.persistence.*;
import java.util.List;
//import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends Person {
    
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Location village;
    
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Membership> memberships;
    
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Borrower> borrowers;
    
    // Constructors
    public User() {}
    
    public User(String personId, String firstName, String lastName, Gender gender, 
                String phoneNumber, String userName, String password, Role role, Location village) {
        super(personId, firstName, lastName, gender, phoneNumber);
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.village = village;
    }
    
    // Getters and Setters
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Location getVillage() {
        return village;
    }
    
    public void setVillage(Location village) {
        this.village = village;
    }
    
    public List<Membership> getMemberships() {
        return memberships;
    }
    
    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
    
    public List<Borrower> getBorrowers() {
        return borrowers;
    }
    
    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }
    
    // Additional methods for backward compatibility
    public void setUsername(String username) {
        this.userName = username;
    }
    
    public String getUsername() {
        return this.userName;
    }
    
    public void setLocation(Location location) {
        this.village = location;
    }
    
    public Location getLocation() {
        return this.village;
    }
}
