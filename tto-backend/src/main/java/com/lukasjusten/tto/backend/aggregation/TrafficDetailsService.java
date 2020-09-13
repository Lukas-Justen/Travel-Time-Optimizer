package com.lukasjusten.tto.backend.aggregation;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrafficDetailsService {

    private final Logger log = LoggerFactory.getLogger(TrafficDetailsService.class);

    private final TrafficDetailsRepository trafficDetailsRepository;
    private final TrafficDetailsConfiguration trafficDetailsConfiguration;
    private final RestTemplate restTemplate;

    @Autowired
    public TrafficDetailsService(TrafficDetailsRepository trafficDetailsRepository,
                                 TrafficDetailsConfiguration trafficDetailsConfiguration,
                                 RestTemplate restTemplate) {
        this.trafficDetailsRepository = trafficDetailsRepository;
        this.trafficDetailsConfiguration = trafficDetailsConfiguration;
        this.restTemplate = restTemplate;
    }

    public List<TrafficDetails> requestLatestTrafficDetails() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(trafficDetailsConfiguration.getUrl())
                .queryParam("origins", trafficDetailsConfiguration.getOrigin())
                .queryParam("destinations", trafficDetailsConfiguration.getDestination())
                .queryParam("key", trafficDetailsConfiguration.getKey())
                .queryParam("mode", "driving")
                .queryParam("departure_time", "now");

        JsonNode response = restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        TrafficDetailsResponseParser trafficDetailsParser = new TrafficDetailsResponseParser();

        LocalDateTime timestamp = LocalDateTime.now();
        List<TrafficDetails> trafficDetailsList = trafficDetailsParser.parse(response, timestamp);
        log.info("Received {} new traffic details for timestamp {}", trafficDetailsList.size(), timestamp);

        return trafficDetailsList;
    }

    public void insertTrafficDetails(List<TrafficDetails> trafficDetailsList) {
        trafficDetailsRepository.saveAll(trafficDetailsList);
    }

}
