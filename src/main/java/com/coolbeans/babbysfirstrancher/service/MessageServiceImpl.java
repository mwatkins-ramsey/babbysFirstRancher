package com.coolbeans.babbysfirstrancher.service;

import com.coolbeans.babbysfirstrancher.controller.exceptions.DisinformationException;
import com.coolbeans.babbysfirstrancher.model.Message;
import com.coolbeans.babbysfirstrancher.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public Message updateMessage(Message message, Integer messageId) {
        Message foundMsg = messageRepository.findById(messageId).get();
        if (Objects.nonNull(message.getMessage()) && !"".equals(message.getMessage())) {
            foundMsg.setMessage(message.getMessage());
        }
        return messageRepository.save(foundMsg);
    }

    @Override
    public Message getMessageById(Integer id) {
        Optional<Message> msg = messageRepository.findById(id);
        if (msg.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (0==msg.get().getMessage().compareToIgnoreCase("debt is good")) {
            throw new DisinformationException();
        }
        return msg.get();
    }

    @Override
    public void deleteMessageById(Integer messageId) {
        Optional<Message> msg = messageRepository.findById(messageId);
        if (msg.isEmpty()) {
            throw new EntityNotFoundException();
        }
        messageRepository.deleteById(messageId);
    }
}
