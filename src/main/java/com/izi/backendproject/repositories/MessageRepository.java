package com.izi.backendproject.repositories;

import com.izi.backendproject.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
