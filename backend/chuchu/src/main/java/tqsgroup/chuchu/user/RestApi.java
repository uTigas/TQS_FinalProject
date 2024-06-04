package tqsgroup.chuchu.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tqsgroup.chuchu.data.dao.SearchRouteDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;
import tqsgroup.chuchu.data.service.TicketService;


@RestController
@RequestMapping("/private/api/v1")
@PreAuthorize("@securityService.hasRole('USER')")
@Tag(name = "User API", description = "Endpoints for user-related operations")
public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Hello Endpoint", description = "Returns a greeting message")
    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }

    @Operation(summary = "Fetch User Details", description = "Retrieves details of the authenticated user")
    @GetMapping("/user")
    public ResponseEntity<User> fetchUser(){
        logger.info("Fetching user Details");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

     @Operation(summary = "Buy Ticket", description = "Allows the user to buy a ticket for a specific route")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket purchased successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = SearchRouteDAO.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input while attempting to buy a ticket",
            content = @Content)})
    @PostMapping("/tickets")
    public ResponseEntity<Ticket> buyTicket(@RequestBody SearchRouteDAO request){
        logger.info("Buying ticket for {}€", request.getPrice());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        Ticket newTicket = new Ticket();
        List<UUID> route = new ArrayList<>();
        List<Connection> connections = new ArrayList<>();
        for (Connection c : request.getConnections()) {
            Connection con = connectionRepository.findById(c.getId()).get();
            route.add(con.getId());
            connections.add(con);
        }
        newTicket.setRoute(route);
        newTicket.setTotalPrice(request.getPrice());
        newTicket.setUser(user);
        newTicket.setTo(connections.get(connections.size()-1).getDestination().getName());
        newTicket.setorigin(connections.get(0).getOrigin().getName());
        newTicket.setDeparture(connections.get(0).getDepartureTime());
        newTicket.setArrival(connections.get(connections.size()-1).getArrivalTime());
        ticketService.saveTicket(newTicket);
        
        return new ResponseEntity<>(newTicket, HttpStatus.OK);

    }

    @Operation(summary = "Get User Tickets", description = "Retrieves all tickets bought by the authenticated user")
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getTickets(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        List<Ticket> tickets = ticketService.findAllByUserName(user.getUsername());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

}
