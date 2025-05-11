package com.example.S2_H1.repository;

import com.example.S2_H1.entity.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxRecord, Long> {}