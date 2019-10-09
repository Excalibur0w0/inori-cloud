package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDao extends JpaRepository<DemoEntity, Integer> {
}
