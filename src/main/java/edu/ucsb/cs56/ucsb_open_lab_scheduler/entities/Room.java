package edu.ucsb.cs56.ucsb_open_lab_scheduler.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Room{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Room name is required")
    private String name;

    public Room(){}

    public Room(String name){
        this.name = name;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }

}
