package com.lukasjusten.tto.backend.aggregation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    private List<SummaryDay> days;

    private TrafficDetailSummary(String origin,
                                 String destination,
                                 List<SummaryDay> days) {
        this.origin = origin;
        this.destination = destination;
        this.days = days;
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

        Map<Integer, List<TrafficDetail>> groupedDetails = trafficDetailList.stream()
                .collect(Collectors.groupingBy(trafficDetail -> trafficDetail.getTimestamp().getDayOfMonth()));

        List<SummaryDay> summariesForDays = new LinkedList<>();

        for (Integer day : groupedDetails.keySet()) {
            List<TrafficDetail> detailsForDay = groupedDetails.get(day);
            List<SummaryItem> summaryItemsForDay = detailsForDay.stream()
                    .map(trafficDetail -> new SummaryItem(trafficDetail.getTimestamp().toLocalTime(), trafficDetail.getDuration()))
                    .collect(Collectors.toList());
            summariesForDays.add(new SummaryDay(day.toString(), summaryItemsForDay));
        }

        return new TrafficDetailSummary(origins.get(0), destinations.get(0), summariesForDays);
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

    public List<SummaryDay> getDays() {
        return days;
    }

    public void setDays(List<SummaryDay> days) {
        this.days = days;
    }
}

class SummaryDay {

    private String day;
    private List<SummaryItem> trafficTimeSeries;

    public SummaryDay() {
    }

    public SummaryDay(String day, List<SummaryItem> trafficTimeSeries) {
        this.day = day;
        this.trafficTimeSeries = trafficTimeSeries;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<SummaryItem> getTrafficTimeSeries() {
        return trafficTimeSeries;
    }

    public void setTrafficTimeSeries(List<SummaryItem> trafficTimeSeries) {
        this.trafficTimeSeries = trafficTimeSeries;
    }

}

class SummaryItem {

    private LocalTime time;
    private int duration;

    public SummaryItem() {
    }

    public SummaryItem(LocalTime time, int duration) {
        this.time = time;
        this.duration = duration;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
