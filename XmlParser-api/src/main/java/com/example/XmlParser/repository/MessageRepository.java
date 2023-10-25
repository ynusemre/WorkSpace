package com.example.XmlParser.repository;

import com.example.XmlParser.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
