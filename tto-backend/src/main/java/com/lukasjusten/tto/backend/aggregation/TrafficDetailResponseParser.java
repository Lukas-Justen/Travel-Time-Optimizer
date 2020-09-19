package com.lukasjusten.tto.backend.aggregation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TrafficDetailResponseParser {

    public static List<String> getStringListFromIterator(Iterator<JsonNode> iterator) {
        List<String> list = new ArrayList<>();

        iterator.forEachRemaining(t -> list.add(t.asText()));

        return list;
    }

    public List<TrafficDetail> parse(JsonNode response, LocalDateTime timestamp) {
        Objects.requireNonNull(response);

        List<TrafficDetail> result = new ArrayList<>();

        ArrayNode destinationsNode = (ArrayNode) response.get("destination_addresses");
        ArrayNode originsNode = (ArrayNode) response.get("origin_addresses");

        List<String> destinations = getStringListFromIterator(destinationsNode.elements());
        List<String> origins = getStringListFromIterator(originsNode.elements());

        ArrayNode rows = (ArrayNode) response.get("rows");

        for (int o = 0; o < origins.size(); o++) {
            ArrayNode elements = (ArrayNode) rows.get(o).get("elements");
            for (int d = 0; d < destinations.size(); d++) {
                result.add(new TrafficDetail(origins.get(o), destinations.get(d), timestamp, elements.get(d)));
            }
        }

        return result;
    }

}
