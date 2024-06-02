package tqsgroup.chuchu.publicapi;

import java.time.LocalTime;
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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tqsgroup.chuchu.admin.dao.ConnectionDAO;
import tqsgroup.chuchu.admin.dao.StationDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.service.ConnectionService;
import tqsgroup.chuchu.data.service.StationService;

@RestController
@RequestMapping("/public/api/v1")
public class PublicRestApi {
    private static final Logger logger = LoggerFactory.getLogger(PublicRestApi.class);

    @Autowired
    private StationService stationService;

    @Autowired
    private ConnectionService connectionService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }

    @Operation(summary = "List all stations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all Stations",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = StationDAO.class)) })})
    @GetMapping("/stations")
    public ResponseEntity<List<StationDAO>> fetchStations(){
        logger.info("Fetching all stations");
        List<Station> stations = stationService.getAllStations();
        return new ResponseEntity<>(stations.stream().map(
            station -> mapToStationResponse(station)).toList()
            , HttpStatus.OK);
    }

    private StationDAO mapToStationResponse(Station station) {
        return StationDAO.builder()
                .name(station.getName())
                .numberOfLines(station.getNumberOfLines())
                .build();
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
                .train(connection.getTrain())
                .departureTime(connection.getDepartureTime())
                .arrivalTime(connection.getArrivalTime())
                .lineNumber(connection.getLineNumber())
                .price(connection.getPrice())
                .build();
    }
}
