package dev.teamso.flightbooking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.teamso.flightbooking.model.dto.FlightCreateRequest;
import dev.teamso.flightbooking.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);
    private final ObjectMapper objectMapper;

    @Autowired
    private FlightService flightService;

    @Autowired
    private JpaFlightRepository flightRepository;

    public JsonDataLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (flightRepository.count() == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/flights.json")) {
                List<FlightCreateRequest> flights = objectMapper.readValue(inputStream, new TypeReference<List<FlightCreateRequest>>() {});
                log.info("Reading {} flights from JSON data and saving to the database.", flights.size());

                for (FlightCreateRequest flight : flights) {
                    flightService.createFlight(flight);
                    log.info("Saved flight: {}", flight.getName()); // Assuming each flight has a 'name' property
                }
            } catch (IOException e) {
                log.error("Failed to read JSON data", e);
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading flights from JSON data because the repository already contains data.");
        }
    }
}

