package com.lukasjusten.tto.backend.aggregation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrafficDetailsCronJob {

    private final Logger log = LoggerFactory.getLogger(TrafficDetailsCronJob.class);

    private final TrafficDetailsService trafficDetailsService;

    @Autowired
    public TrafficDetailsCronJob(TrafficDetailsService trafficDetailsService) {
        this.trafficDetailsService = trafficDetailsService;
    }

    @Scheduled(cron = "${tto.scheduling}")
    public void requestTrafficDetails() {
        log.info("Triggered Request Traffic Details CronJob");
        List<TrafficDetails> trafficDetailsList = trafficDetailsService.requestLatestTrafficDetails();
        trafficDetailsService.insertTrafficDetails(trafficDetailsList);
    }

}