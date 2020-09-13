package com.lukasjusten.tto.backend.aggregation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficDetailsRepository extends JpaRepository<TrafficDetails, Long> {

    @Override
    List<TrafficDetails> findAll();

}
