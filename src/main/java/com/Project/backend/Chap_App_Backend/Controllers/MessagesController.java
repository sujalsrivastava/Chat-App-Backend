package com.Project.backend.Chap_App_Backend.Controllers;
import com.Project.backend.Chap_App_Backend.Entity.Message;
import com.Project.backend.Chap_App_Backend.Entity.Room;
import com.Project.backend.Chap_App_Backend.Payload.MessageRequest;
import com.Project.backend.Chap_App_Backend.Repository.MessageRepository;
import com.Project.backend.Chap_App_Backend.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("https://chat-app-frontend-eta-one.vercel.app")
public class MessagesController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public Message broadcastMsg(
            @DestinationVariable String roomId,
            MessageRequest messageRequest
    ){

        Room room=roomRepository.findByRoomId(roomId);

        if(room==null){
            throw new RuntimeException("Room not found");
        }

        Message message=new Message();
        message.setRoomId(roomId);
        message.setContent(messageRequest.getContent());
        message.setSender(messageRequest.getSender());
        message.setSendAt(LocalDateTime.now());

        messageRepository.save(message);

        return message;
    }
}
