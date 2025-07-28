package com.auca.Models;

//import com.auca.Models.Status;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "membership")
public class Membership {
    
    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID membershipId;
    
    @Column(name = "membership_code", nullable = false, unique = true)
    private String membershipCode;
    
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;
    
    @Column(name = "expiring_time", nullable = false)
    private LocalDate expiringTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    private Status membershipStatus;
    
    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;
    
    @ManyToOne
    @JoinColumn(name = "membership_type_id", nullable = false)
    private MembershipType membershipType;
    
    // Constructors
    public Membership() {}
    
    public Membership(String membershipCode, LocalDate registrationDate, LocalDate expiringTime, 
                     Status membershipStatus, User reader, MembershipType membershipType) {
        this.membershipCode = membershipCode;
        this.registrationDate = registrationDate;
        this.expiringTime = expiringTime;
        this.membershipStatus = membershipStatus;
        this.reader = reader;
        this.membershipType = membershipType;
    }
    
    // Getters and Setters
    public UUID getMembershipId() {
        return membershipId;
    }
    
    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }
    
    public String getMembershipCode() {
        return membershipCode;
    }
    
    public void setMembershipCode(String membershipCode) {
        this.membershipCode = membershipCode;
    }
    
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public LocalDate getExpiringTime() {
        return expiringTime;
    }
    
    public void setExpiringTime(LocalDate expiringTime) {
        this.expiringTime = expiringTime;
    }
    
    public Status getMembershipStatus() {
        return membershipStatus;
    }
    
    public void setMembershipStatus(Status membershipStatus) {
        this.membershipStatus = membershipStatus;
    }
    
    public User getReader() {
        return reader;
    }
    
    public void setReader(User reader) {
        this.reader = reader;
    }
    
    public MembershipType getMembershipType() {
        return membershipType;
    }
    
    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
    
    // Additional methods for backward compatibility
    public void setCode(String code) {
        this.membershipCode = code;
    }
    
    public String getCode() {
        return this.membershipCode;
    }
    
    public void setStatus(Status status) {
        this.membershipStatus = status;
    }
    
    public Status getStatus() {
        return this.membershipStatus;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiringTime = expiryDate;
    }
    
    public LocalDate getExpiryDate() {
        return this.expiringTime;
    }
}
