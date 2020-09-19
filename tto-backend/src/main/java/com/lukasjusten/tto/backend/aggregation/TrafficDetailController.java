package com.lukasjusten.tto.backend.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TrafficDetailController {

    private final TrafficDetailService trafficDetailService;

    @Autowired
    public TrafficDetailController(TrafficDetailService trafficDetailService) {
        this.trafficDetailService = Objects.requireNonNull(trafficDetailService);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/traffic", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TrafficDetailSummary> getTrafficDetails(
            @RequestParam(name = "origin") String origin,
            @RequestParam(name = "destination") String destination
    ) {
        TrafficDetailSummary summary = trafficDetailService.getTrafficDetailSummary(origin, destination);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

}
