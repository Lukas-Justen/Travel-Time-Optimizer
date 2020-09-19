package com.lukasjusten.tto.backend.aggregation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A TrafficDetailSummary represents traffic information between a single origin location and destination location. The
 * traffic information is being represented as a list of travel durations through traffic paired with a list of
 * corresponding timestamps. In more detail, the duration at index 0 belongs to the timestamp at index 0 and so on.
 */
public class TrafficDetailSummary {

    private String origin;
    private String destination;

    private List<LocalDateTime> timestamps;
    private List<Integer> durations;

    private TrafficDetailSummary(String origin,
                                 String destination,
                                 List<LocalDateTime> timestamps,
                                 List<Integer> durations) {
        this.origin = origin;
        this.destination = destination;
        this.timestamps = timestamps;
        this.durations = durations;
    }

    /**
     * If you want to create a TrafficDetailSummary you should use this factoryMethod to create a summary. This method
     * takes a list of {@link TrafficDetail} and removes the redundant origin/destination information and condenses the
     * durations and timestamps in separate lists.
     * <p>
     * NOTE: If the list of {@link TrafficDetail} contains more than one distinct origins or destinations this method
     * will throw a RuntimeException as the list of details cannot be reduced into a summary for one start and end
     * location.
     *
     * @param trafficDetailList A list of {@link TrafficDetail} which will be condensed into a condensed summary.
     * @return Returns a {@link TrafficDetailSummary} for an origin and destination location.
     */
    public static TrafficDetailSummary createSummary(List<TrafficDetail> trafficDetailList) {
        final List<String> origins = extractToDistinctList(trafficDetailList, TrafficDetail::getOrigin);
        final List<String> destinations = extractToDistinctList(trafficDetailList, TrafficDetail::getDestination);

        if (origins.size() != 1 || destinations.size() != 1) {
            throw new RuntimeException("Cannot create traffic details summary of from a list of multiple routes!");
        }

        final List<LocalDateTime> timestamps = extractToList(trafficDetailList, TrafficDetail::getTimestamp);
        final List<Integer> durations = extractToList(trafficDetailList, TrafficDetail::getDurationInTraffic);

        return new TrafficDetailSummary(origins.get(0), destinations.get(0), timestamps, durations);
    }

    /**
     * Helper function which takes a list of {@link TrafficDetail} and reduces the information gathered by the accessor
     * to a distinct list of values. This method is used to gather information about the distinct origins and
     * destinations that are part of a list of {@link TrafficDetail}.
     *
     * @param details  A list of {@link TrafficDetail} which will be reduced to a SET of values.
     * @param accessor The accessor for the {@link TrafficDetail} object to extract the value.
     * @param <T>      The Type of the result of the method.
     * @return A list which contains distinct values of type {@link T}.
     */
    private static <T> List<T> extractToDistinctList(List<TrafficDetail> details,
                                                     Function<? super TrafficDetail, ? extends T> accessor) {
        return extractToList(details, accessor).stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Helper function which takes a list of {@link TrafficDetail} and reduces the information gathered by the accessor
     * to a distinct list of values. This method is used to gather information about the distinct origins and
     * destinations that are part of a list of {@link TrafficDetail}.
     *
     * @param details  A list of {@link TrafficDetail} which will be reduced to a LIST of values.
     * @param accessor The accessor for the {@link TrafficDetail} object to extract the value.
     * @param <T>      The Type of the result of the method.
     * @return A list which contains distinct values of type {@link T}.
     */
    private static <T> List<T> extractToList(List<TrafficDetail> details,
                                             Function<? super TrafficDetail, ? extends T> accessor) {
        return details.stream()
                .map(accessor)
                .collect(Collectors.toList());
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

    public List<LocalDateTime> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<LocalDateTime> timestamps) {
        this.timestamps = timestamps;
    }

    public List<Integer> getDurations() {
        return durations;
    }

    public void setDurations(List<Integer> durations) {
        this.durations = durations;
    }

}
