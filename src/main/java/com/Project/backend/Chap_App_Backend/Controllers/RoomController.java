package com.Project.backend.Chap_App_Backend.Controllers;

import com.Project.backend.Chap_App_Backend.Entity.Message;
import com.Project.backend.Chap_App_Backend.Entity.Room;
import com.Project.backend.Chap_App_Backend.Repository.MessageRepository;
import com.Project.backend.Chap_App_Backend.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("https://chat-app-frontend-eta-one.vercel.app")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Create a Room

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody Room room){
        Room room1 =roomRepository.findByRoomId(room.getRoomId());

        if(room1 !=null){
            return ResponseEntity.badRequest().body("Room Already Exists");
        }

        Room newroom=new Room();

        newroom.setRoomId(room.getRoomId());
        newroom.setUserName(room.getUserName());
        Room savedRoom=roomRepository.save(newroom);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // Get Room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId){
            Room room=roomRepository.findByRoomId(roomId);

            if(room==null){
                return ResponseEntity.badRequest().body("Room not found");
            }

            return ResponseEntity.ok(room);
    }

    // Get messages
    @GetMapping("/messages/{roomId}")
        public ResponseEntity<?> getMessages(@PathVariable String roomId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size
                                         ){
        Room room=roomRepository.findByRoomId(roomId);

        if(room==null){
            return ResponseEntity.badRequest().body("Room not found");
        }
        Pageable pageable= PageRequest.of(page,size, Sort.by("sendAt").descending());

        Page<Message> messagePage=messageRepository.findByRoomId(roomId,pageable);

        return ResponseEntity.ok(messagePage);
    }
}
