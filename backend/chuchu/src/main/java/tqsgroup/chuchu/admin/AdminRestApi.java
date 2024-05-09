package tqsgroup.chuchu.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqsgroup.chuchu.admin.dao.*; //Get all admin request and response templates
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.service.StationService;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/api/v1")
public class AdminRestApi {
    private static final Logger logger = LoggerFactory.getLogger(AdminRestApi.class);

    private final StationService stationService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from SpringBoot Admin Rest API Controller";
    }

    /* Station endpoints */
    @Operation(summary = "Create a new station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Station was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create station",
            content = @Content) })
    @PostMapping("/stations")
    public ResponseEntity<Object> createStation(CreateStationRequest request) {
        try {
            logger.info("Creating Station with name: {}", request.getName());
            Station station = stationService.saveStation(new Station(request.getName(), request.getNumberOfLines()));
            logger.info("Station with name {} created successfully", request.getName());
            StationResponse response = mapToResponse(station);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Station: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all stations or get a station by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Stations or a specific Station if name is provided",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch a Station by name",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "No Stations were found",
            content = @Content)})
    @GetMapping("/stations")
    public ResponseEntity<Object> listStations(
            @RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            logger.info("No Station name given, fetching all stations");
            List<Station> stations = stationService.getAllStations();
            if (stations.isEmpty()) {
                logger.info("No Stations found");
                return new ResponseEntity<>("No stations found", HttpStatus.NOT_FOUND);
            }
            List<StationResponse> response = new ArrayList<>();
            for (Station station : stations) {
                response.add(mapToResponse(station));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            try {
                Station station = stationService.getStationByName(name);
                logger.info("Fetching Station with name: {}", name);
                if (station == null) {
                    logger.info("Station with name {} not found", name);
                    return new ResponseEntity<>("Station not found", HttpStatus.NOT_FOUND);
                }
                logger.info("Returning Station with name: {}", name);
                StationResponse response = mapToResponse(station);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Station: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private StationResponse mapToResponse(Station station) {
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .numberOfLines(station.getNumberOfLines())
                .build();
    }
    /* End of Station endpoints */
}
