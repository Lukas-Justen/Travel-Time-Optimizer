package com.lukasjusten.tto.backend.aggregation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "tto.scheduling", name = "enabled")
public class TrafficDetailCronJob {

    private final Logger log = LoggerFactory.getLogger(TrafficDetailCronJob.class);

    private final TrafficDetailService trafficDetailService;

    @Autowired
    public TrafficDetailCronJob(TrafficDetailService trafficDetailService) {
        this.trafficDetailService = trafficDetailService;
    }

    @Scheduled(cron = "${tto.scheduling.interval}")
    public void requestTrafficDetailsFromApi() {
        log.info("Triggered Cronjob to Insert Latest Traffic Details");
        trafficDetailService.insertLatestTrafficDetails();
    }

}