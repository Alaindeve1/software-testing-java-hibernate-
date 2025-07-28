package com.auca.Models;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "room")
public class Room {
    
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomId;
    
    @Column(name = "room_code", nullable = false, unique = true)
    private String roomCode;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shelf> shelves;
    
    // Constructors
    public Room() {}
    
    public Room(String roomCode) {
        this.roomCode = roomCode;
    }
    
    // Getters and Setters
    public UUID getRoomId() {
        return roomId;
    }
    
    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomCode() {
        return roomCode;
    }
    
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
    
    public List<Shelf> getShelves() {
        return shelves;
    }
    
    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }
    
    // Additional methods for backward compatibility
    public void setCode(String code) {
        this.roomCode = code;
    }
    
    public String getCode() {
        return this.roomCode;
    }
}
