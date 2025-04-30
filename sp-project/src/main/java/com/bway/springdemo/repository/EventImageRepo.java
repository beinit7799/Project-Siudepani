package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bway.springdemo.model.EventImage;

@Repository
public interface EventImageRepo extends JpaRepository<EventImage,Long>{

}
