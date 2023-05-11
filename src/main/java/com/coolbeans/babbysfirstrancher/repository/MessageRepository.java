package com.coolbeans.babbysfirstrancher.repository;

import com.coolbeans.babbysfirstrancher.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
