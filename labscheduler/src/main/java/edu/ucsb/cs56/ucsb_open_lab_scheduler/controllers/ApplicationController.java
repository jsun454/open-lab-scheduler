package edu.ucsb.cs56.ucsb_open_lab_scheduler.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.entities.Room;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.repositories.RoomRepository;

@Controller
public class ApplicationController{
    private final RoomRepository roomRepository;

    @Autowired
    public ApplicationController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("roomModel", roomRepository.findAll());
        return "roomsTable";
    }
}

