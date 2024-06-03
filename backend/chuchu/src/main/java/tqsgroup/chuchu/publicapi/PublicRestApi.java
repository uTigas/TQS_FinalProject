package tqsgroup.chuchu.publicapi;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tqsgroup.chuchu.data.dao.ConnectionDAO;
import tqsgroup.chuchu.data.dao.SearchRouteDAO;
import tqsgroup.chuchu.data.dao.StationDAO;
import tqsgroup.chuchu.data.dao.TrainDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.service.ConnectionService;
import tqsgroup.chuchu.data.service.RouteService;
import tqsgroup.chuchu.data.service.StationService;
import tqsgroup.chuchu.data.service.TrainService;

@RestController
@RequestMapping("/public/api/v1")
public class PublicRestApi {
    private static final Logger logger = LoggerFactory.getLogger(PublicRestApi.class);

    @Autowired
    private StationService stationService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private RouteService routeService;
    
    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }

    @Operation(summary ="List new arrival to a station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all new arrivals to a station",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch new arrivals to a station",
            content = @Content)})
    @GetMapping("/arrivals")
    public ResponseEntity<List<ConnectionDAO>> fetchArrivals(@RequestParam String stationName, @RequestParam int limit, @RequestParam String time){
        Station station = stationService.getStationByName(stationName);
        if (station == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Connection> arrivals = connectionService.getArrivals(station,10, LocalTime.parse(time));
        return new ResponseEntity<>(arrivals.stream().map(
            connection -> mapToConnectionResponse(connection)).toList()
         , HttpStatus.OK);
    }

    @Operation(summary = "List new arrivals from a track of a station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all new arrivals from a track of a station",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConnectionDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to fetch new arrivals from a track of a station",
            content = @Content)})
    @GetMapping("/arrivals/line")
    public ResponseEntity<List<ConnectionDAO>> fetchArrivalsFromLind(@RequestParam String stationName, @RequestParam int lineNumber, @RequestParam int limit, @RequestParam String time){
        Station station = stationService.getStationByName(stationName);
        if (station == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Connection> arrivals = connectionService.getArrivalsFromTrack(station, limit, lineNumber, LocalTime.parse(time));
        return new ResponseEntity<>( arrivals.stream().map(
            connection -> mapToConnectionResponse(connection)).toList(), 
        HttpStatus.OK);
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

        @Operation(summary = "List all stations or get a station by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Stations or a specific Station if name is provided",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationDAO.class)) }),
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
            List<StationDAO> response = new ArrayList<>();
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
                StationDAO response = mapToStationResponse(station);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Station: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Station: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private StationDAO mapToStationResponse(Station station) {
        return StationDAO.builder()
                .name(station.getName())
                .numberOfLines(station.getNumberOfLines())
                .build();
    }
    /* End of Station endpoints */


        @Operation(summary = "List all trains or get a train by number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Trains or a specific Train if number is provided",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrainDAO.class)) }),
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
            List<TrainDAO> response = new ArrayList<>();
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
                TrainDAO response = mapToTrainResponse(train);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                logger.error("Error while fetching Train: {}", e.getMessage());
                return new ResponseEntity<>("Error while fetching Train: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private TrainDAO mapToTrainResponse(Train train) {
        return TrainDAO.builder()
                .type(train.getType())
                .number(train.getNumber())
                .build();
    }

    // End of Train Endpoints

    //Start of Routes Endpoints

    @GetMapping("/routes")
    public ResponseEntity<List<SearchRouteDAO>> searchRoutes(@RequestBody String from, String to){
        Station origin = stationService.getStationByName(from);
        Station destination = stationService.getStationByName(to);

        logger.info("Searching Routes from {} to {}", origin.getName(), destination.getName());
        return new ResponseEntity<>(routeService.searchRoutes(origin, destination), HttpStatus.OK);
    }
}
