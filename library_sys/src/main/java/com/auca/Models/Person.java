package com.auca.Models;

//import com.auca.Models.Gender;
import jakarta.persistence.*;

@MappedSuperclass  
public abstract class Person {
    @Id
    private String personId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    
    public Person() {}
    
    public Person(String personId, String firstName, String lastName, Gender gender, String phoneNumber) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
    
    
    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}