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
import org.springframework.web.bind.annotation.RestController;

import tqsgroup.chuchu.admin.dao.ConnectionRequest;
import tqsgroup.chuchu.data.entity.*;
import tqsgroup.chuchu.data.service.*;

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
                schema = @Schema(implementation = Station.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create station",
            content = @Content) })
    @PostMapping("/stations")
    public ResponseEntity<Object> createStation(@RequestBody Station request) {
        try {
            logger.info("Creating Station with name: {}", request.getName());
            Station station = stationService.saveStation(new Station(request.getName(), request.getNumberOfLines()));
            logger.info("Station with name {} created successfully", request.getName());
            Station response = station;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Station: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /* End of Station endpoints */

    /* Train endpoints */
    @Operation(summary = "Create a new Train")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Train was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Train.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Train",
            content = @Content) })
    @PostMapping("/trains")
    public ResponseEntity<Object> createTrain(@RequestBody Train request) {
        try {
            logger.info("Creating Train with class: {} and number: {}",request.getType(), request.getNumber());
            Train train = trainService.saveTrain(new Train(request.getType(), request.getNumber()));
            logger.info("Train with number {} created successfully", request.getNumber());
            Train response = train;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Train: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Train: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /* End of Train endpoints */


    /* Connection endpoints */
    @Operation(summary = "Create a new Connection")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Connection was created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Connection.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to create Connection",
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Origin or Destination Station not found",
            content = @Content)})
    @PostMapping("/connections")
    public ResponseEntity<Object> createConnection(@RequestBody Connection request) {
        try {
            Station from = stationService.getStationByName(request.getOrigin().getName());
            Station to = stationService.getStationByName(request.getDestination().getName());
            Train train = trainService.findTrainByNumber(request.getTrain());
            logger.info("Creating Connection from {} to {} with Train number {} and class {}", from.getName(), to.getName(), train.getNumber(), train.getType());
            Connection connection = connectionService.saveConnection(new Connection(from, to, train, request.getDepartureTime(), request.getArrivalTime(), request.getLineNumber(), request.getPrice()));
            logger.info("Connection from {} to {} with Train number {} and class {} created successfully", connection.getOrigin().getName(), connection.getDestination().getName(), trainService.findTrainByNumber(connection.getTrain()).getNumber(), train.getType());
            Connection response = connection;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating Connection: {}", e.getMessage());
            return new ResponseEntity<>("Error while creating Connection: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /* End of Connection endpoints */
}
