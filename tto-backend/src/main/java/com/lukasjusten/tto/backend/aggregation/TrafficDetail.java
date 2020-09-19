package com.lukasjusten.tto.backend.aggregation;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "traffic_details")
public class TrafficDetail {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;

    private String origin;
    private String destination;

    private LocalDateTime timestamp;

    private int distance;
    private int duration;
    private int durationInTraffic;

    public TrafficDetail() {
    }

    public TrafficDetail(String origin, String destination, LocalDateTime timestamp, JsonNode trafficDetails) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(timestamp);

        this.origin = origin;
        this.destination = destination;
        this.timestamp = timestamp;

        this.distance = trafficDetails.get("distance").get("value").asInt();
        this.duration = trafficDetails.get("duration").get("value").asInt();
        this.durationInTraffic = trafficDetails.get("duration_in_traffic").get("value").asInt();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDurationInTraffic() {
        return durationInTraffic;
    }

    public void setDurationInTraffic(int durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    @Override
    public String toString() {
        return "TrafficDetails{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                ", durationInTraffic=" + durationInTraffic +
                '}';
    }

}
