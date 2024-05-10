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
import tqsgroup.chuchu.data.entity.*;
import tqsgroup.chuchu.data.service.*;

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
    private final TrainService trainService;
    private final ConnectionService connectionService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from SpringBoot Admin Rest API Controller";
    }

    /* Station endpoints */
    @Operation(summary = "Create a new Station")
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
            StationResponse response = mapToStationResponse(station);
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
                response.add(mapToStationResponse(station));
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
                StationResponse response = mapToStationResponse(station);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Station: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private StationResponse mapToStationResponse(Station station) {
        return StationResponse.builder()
                .name(station.getName())
                .numberOfLines(station.getNumberOfLines())
                .build();
    }
    /* End of Station endpoints */


    /* Train endpoints */
    @Operation(summary = "Create a new Train")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Train was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Train",
            content = @Content) })
    @PostMapping("/trains")
    public ResponseEntity<Object> createTrain(CreateTrainRequest request) {
        try {
            logger.info("Creating Train with number: {}", request.getNumber());
            Train train = trainService.saveTrain(new Train(request.getType(), request.getNumber()));
            logger.info("Train with number {} created successfully", request.getNumber());
            TrainResponse response = mapToTrainResponse(train);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Train: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Train: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all trains or get a train by number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Trains or a specific Train if number is provided",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrainResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch a Train by number",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "No Trains were found",
            content = @Content)})
    @GetMapping("/trains")
    public ResponseEntity<Object> listTrains(
            @RequestParam(required = false) Integer number) {
        if (number == null) {
            logger.info("No Train number given, fetching all trains");
            List<Train> trains = trainService.getAllTrains();
            if (trains.isEmpty()) {
                logger.info("No Trains found");
                return new ResponseEntity<>("No trains found", HttpStatus.NOT_FOUND);
            }
            List<TrainResponse> response = new ArrayList<>();
            for (Train train : trains) {
                response.add(mapToTrainResponse(train));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            try {
                Train train = trainService.findTrainByNumber(number);
                logger.info("Fetching Train with number: {}", number);
                if (train == null) {
                    logger.info("Train with number {} not found", number);
                    return new ResponseEntity<>("Train not found", HttpStatus.NOT_FOUND);
                }
                logger.info("Returning Train with number: {}", number);
                TrainResponse response = mapToTrainResponse(train);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Train: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Train: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private TrainResponse mapToTrainResponse(Train train) {
        return TrainResponse.builder()
                .type(train.getType())
                .number(train.getNumber())
                .build();
    }
    /* End of Train endpoints */


    /* Connection endpoints */
    @Operation(summary = "Create a new Connection")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Connection was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Connection",
            content = @Content) })
    @PostMapping("/connections")
    public ResponseEntity<Object> createConnection(CreateConnectionRequest request) {
        try {
            logger.info("Creating Connection from {} to {} with Train number: {}", request.getFrom().getName(), request.getTo().getName(), request.getTrain().getNumber());
            Connection connection = connectionService.saveConnection(new Connection(request.getFrom(), request.getTo(), request.getTrain(), request.getDepartureTime(), request.getArrivalTime(), request.getLineNumber(), request.getPrice()));
            logger.info("Connection from {} to {} with Train number {} created successfully", request.getFrom().getName(), request.getTo().getName(), request.getTrain().getNumber());
            ConnectionResponse response = mapToConnectionResponse(connection);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Connection: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Connection: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all connections or get a connection by origin and destination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Connections or a specific Connection if origin and destination are provided",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch a Connection by origin and destination",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "No Connections were found",
            content = @Content)})
    @GetMapping("/connections")
    public ResponseEntity<Object> listConnections(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        if (origin == null || origin.isEmpty() || destination == null || destination.isEmpty()) {
            logger.info("No origin and destination given, fetching all connections");
            List<Connection> connections = connectionService.getAllConnections();
            if (connections.isEmpty()) {
                logger.info("No Connections found");
                return new ResponseEntity<>("No connections found", HttpStatus.NOT_FOUND);
            }
            List<ConnectionResponse> response = new ArrayList<>();
            for (Connection connection : connections) {
                response.add(mapToConnectionResponse(connection));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            try {
                Station from = stationService.getStationByName(origin);
                Station to = stationService.getStationByName(destination);
                logger.info("Fetching Connection from {} to {}", origin, destination);
                List<Connection> connections = connectionService.findAllByOriginAndDestination(from, to);
                if (connections.isEmpty()) {
                    logger.info("Connection from {} to {} not found", origin, destination);
                    return new ResponseEntity<>("Connection not found", HttpStatus.NOT_FOUND);
                }
                List<ConnectionResponse> response = new ArrayList<>();
                for (Connection connection : connections) {
                    response.add(mapToConnectionResponse(connection));
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Connection: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Connection: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private ConnectionResponse mapToConnectionResponse(Connection connection) {
        return ConnectionResponse.builder()
                .from(connection.getOrigin())
                .to(connection.getDestination())
                .train(connection.getTrain())
                .departureTime(connection.getDepartureTime())
                .arrivalTime(connection.getArrivalTime())
                .lineNumber(connection.getLineNumber())
                .price(connection.getPrice())
                .build();
    }
}
