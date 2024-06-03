package tqsgroup.chuchu.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqsgroup.chuchu.data.dao.*;
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
@PreAuthorize("@securityService.hasRole('ADMIN')")
public class AdminRestApi {
    private static final Logger logger = LoggerFactory.getLogger(AdminRestApi.class);

    private final StationService stationService;
    private final TrainService trainService;
    private final ConnectionService connectionService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from SpringBoot Admin Rest API Controller";
    }

    @GetMapping("/admin")
    public ResponseEntity<User> fetchAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /* Station endpoints */
    @Operation(summary = "Create a new Station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Station was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create station",
            content = @Content) })
    @PostMapping("/stations")
    public ResponseEntity<Object> createStation(@RequestBody StationDAO request) {
        try {
            logger.info("Creating Station with name: {}", request.getName());
            Station station = stationService.saveStation(new Station(request.getName(), request.getNumberOfLines()));
            logger.info("Station with name {} created successfully", request.getName());
            StationDAO response = mapToStationResponse(station);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Station: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /* End of Station endpoints */

    private StationDAO mapToStationResponse(Station station) {
        return StationDAO.builder()
                .name(station.getName())
                .numberOfLines(station.getNumberOfLines())
                .build();
    }
    /* Train endpoints */
    @Operation(summary = "Create a new Train")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Train was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrainDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Train",
            content = @Content) })
    @PostMapping("/trains")
    public ResponseEntity<Object> createTrain(@RequestBody TrainDAO request) {
        try {
            logger.info("Creating Train with class: {} and number: {}",request.getType(), request.getNumber());
            Train train = trainService.saveTrain(new Train(request.getType(), request.getNumber()));
            logger.info("Train with number {} created successfully", request.getNumber());
            TrainDAO response = mapToTrainResponse(train);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Train: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Train: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private TrainDAO mapToTrainResponse(Train train) {
        return TrainDAO.builder()
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
                schema = @Schema(implementation = ConnectionDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Connection",
            content = @Content) })
    @PostMapping("/connections")
    public ResponseEntity<Object> createConnection(@RequestBody ConnectionDAO request) {
        try {
            Station from = stationService.getStationByName(request.getFrom().getName());
            Station to = stationService.getStationByName(request.getTo().getName());
            Train train = trainService.findTrainByNumber(request.getTrain().getNumber());
            logger.info("Creating Connection from {} to {} with Train number {} and class {}", from.getName(), to.getName(), train.getNumber(), train.getType());
            Connection connection = connectionService.saveConnection(new Connection(from, to, train, request.getDepartureTime(), request.getArrivalTime(), request.getLineNumber(), request.getPrice()));
            logger.info("Connection from {} to {} with Train number {} and class {} created successfully", connection.getOrigin().getName(), connection.getDestination().getName(), trainService.findTrainByNumber(connection.getTrain()).getNumber(), train.getType());
            ConnectionDAO response = mapToConnectionResponse(connection);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Connection: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Connection: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all connections or get a connection by origin and destination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all Connections or a list of Connections based in origin and destination (if provided)",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch a Connection by origin and destination",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "No Connections were found",
            content = @Content)})
    @GetMapping("/connections")
    public ResponseEntity<Object> listConnections(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        if (origin == null || origin.isEmpty() || destination == null || destination.isEmpty()) {
            logger.info("No parameters given, fetching all connections");
            List<Connection> connections = connectionService.getAllConnections();
            if (connections.isEmpty()) {
                logger.info("No Connections found");
                return new ResponseEntity<>("No connections found", HttpStatus.NOT_FOUND);
            }
            List<ConnectionDAO> response = new ArrayList<>();
            for (Connection connection : connections) {
                response.add(mapToConnectionResponse(connection));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            //Fetch all connection having by origin and destination
            logger.info("Fetching Connection from {} to {}", origin, destination);
            try {
                Station originStation = stationService.getStationByName(origin);
                Station destinationStation = stationService.getStationByName(destination);
                if (originStation == null || destinationStation == null) {
                    logger.info("Origin or Destination Station not found");
                    return new ResponseEntity<>("Origin or Destination Station not found", HttpStatus.NOT_FOUND);
                }
                List<Connection> connections = connectionService.findAllByOriginAndDestination(originStation, destinationStation);
                if (connections.isEmpty()) {
                    logger.info("No Connections found from {} to {}", origin, destination);
                    return new ResponseEntity<>("No connections found from " + origin + " to " + destination, HttpStatus.NOT_FOUND);
                }
                List<ConnectionDAO> response = new ArrayList<>();
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

    private ConnectionDAO mapToConnectionResponse(Connection connection) {
        return ConnectionDAO.builder()
                .from(connection.getOrigin())
                .to(connection.getDestination())
                .train(trainService.findTrainByNumber(connection.getTrain()))
                .departureTime(connection.getDepartureTime())
                .arrivalTime(connection.getArrivalTime())
                .lineNumber(connection.getLineNumber())
                .price(connection.getPrice())
                .build();
    }
    /* End of Connection endpoints */
}
