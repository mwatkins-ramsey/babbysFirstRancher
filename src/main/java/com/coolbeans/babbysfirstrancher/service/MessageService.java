package com.coolbeans.babbysfirstrancher.service;

import com.coolbeans.babbysfirstrancher.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    Message saveMessage(Message message);

    List<Message> findAll();

    Message updateMessage(Message message, Integer messageId);

    Message getMessageById(Integer id);

    void deleteMessageById(Integer messageId);
}
