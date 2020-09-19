package com.lukasjusten.tto.backend.aggregation;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrafficDetailService {

    private final Logger log = LoggerFactory.getLogger(TrafficDetailService.class);

    private final TrafficDetailRepository trafficDetailRepository;
    private final TrafficDetailConfiguration trafficDetailConfiguration;
    private final RestTemplate restTemplate;

    @Autowired
    public TrafficDetailService(TrafficDetailRepository trafficDetailRepository,
                                TrafficDetailConfiguration trafficDetailConfiguration,
                                RestTemplate restTemplate) {
        this.trafficDetailRepository = trafficDetailRepository;
        this.trafficDetailConfiguration = trafficDetailConfiguration;
        this.restTemplate = restTemplate;
    }

    public void insertLatestTrafficDetails() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(trafficDetailConfiguration.getUrl())
                .queryParam("origins", trafficDetailConfiguration.getOrigin())
                .queryParam("destinations", trafficDetailConfiguration.getDestination())
                .queryParam("key", trafficDetailConfiguration.getKey())
                .queryParam("mode", "driving")
                .queryParam("departure_time", "now");

        JsonNode response = restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        TrafficDetailResponseParser trafficDetailParser = new TrafficDetailResponseParser();

        LocalDateTime timestamp = LocalDateTime.now();
        List<TrafficDetail> trafficDetailList = trafficDetailParser.parse(response, timestamp);
        log.info("Received {} new traffic details for timestamp {}", trafficDetailList.size(), timestamp);

        insertTrafficDetailList(trafficDetailList);
    }

    public void insertTrafficDetailList(List<TrafficDetail> trafficDetailList) {
        trafficDetailRepository.saveAll(trafficDetailList);
    }

    public TrafficDetailSummary getTrafficDetailSummary(String origin, String destination) {
        List<TrafficDetail> trafficDetailList = trafficDetailRepository
                .findAllByOriginEqualsAndDestinationEquals(origin, destination);
        return TrafficDetailSummary.createSummary(trafficDetailList);
    }

}
