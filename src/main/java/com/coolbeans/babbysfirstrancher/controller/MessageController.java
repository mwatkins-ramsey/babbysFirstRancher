package com.coolbeans.babbysfirstrancher.controller;

import com.coolbeans.babbysfirstrancher.model.Message;
import com.coolbeans.babbysfirstrancher.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private MessageService messageService;

    @Autowired
    public  MessageController(MessageService messageService) {
        System.out.println("Autowiring of setMyConfig ran!");
        this.messageService = messageService;
    }

    @GetMapping("/hello")
    public Message helloWorld() {
        return new Message(0, "Hello World");
    }

    @GetMapping("/msg")
    public List<Message> getMessages(){
        return messageService.findAll();
    }

    @GetMapping("/msg/{id}")
    public Message getMessage(@PathVariable("id") Integer id){
        return messageService.getMessageById(id);
    }

    @PostMapping("/msg")
    public Message postMessage(@RequestBody Message msg){
        return messageService.saveMessage(msg);
    }

    @PutMapping("/msg/{id}")
    public Message putMessage(@PathVariable("id") Integer id, @RequestBody Message msg){
        return messageService.updateMessage(msg, id);
    }

    @DeleteMapping("/msg/{id}")
    public void deleteMessage(@PathVariable("id") Integer id){
        messageService.deleteMessageById(id);
    }

}
