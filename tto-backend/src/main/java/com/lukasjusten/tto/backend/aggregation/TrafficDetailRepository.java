package com.lukasjusten.tto.backend.aggregation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficDetailRepository extends JpaRepository<TrafficDetail, Long> {

    @Override
    List<TrafficDetail> findAll();

    List<TrafficDetail> findAllByOriginEqualsAndDestinationEquals(String origin, String destination);

}
