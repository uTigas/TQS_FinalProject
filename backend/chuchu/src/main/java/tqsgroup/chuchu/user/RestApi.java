package tqsgroup.chuchu.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tqsgroup.chuchu.admin.dao.ConnectionDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.service.ConnectionService;
import tqsgroup.chuchu.data.service.StationService;


@RestController
@RequestMapping("/private/api/v1")
public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private StationService stationService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }

    @GetMapping("/user")
    public ResponseEntity<User> fetchUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "List all connections or get a connection by origin and destination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all Connections or a list of Connections based in origin and destination (if provided)",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch a Connection by origin and destination",
            content = @Content)})

    @GetMapping("/connections")
    public ResponseEntity<List<Connection>> listConnections(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        if (origin == null || origin.isEmpty() || destination == null || destination.isEmpty()) {
            logger.info("No parameters given, fetching all connections");
            List<Connection> connections = connectionService.getAllConnections();
            
            return new ResponseEntity<>(connections, HttpStatus.OK);
        } else {
            //Fetch all connection having by origin and destination
            logger.info("Fetching Connection from {} to {}", origin, destination);
            try {
                Station originStation = stationService.getStationByName(origin);
                Station destinationStation = stationService.getStationByName(destination);
                if (originStation == null || destinationStation == null) {
                    logger.info("Origin or Destination Station not found");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                List<Connection> connections = connectionService.findAllByOriginAndDestination(originStation, destinationStation);
                
                return new ResponseEntity<>(connections, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Connection: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
