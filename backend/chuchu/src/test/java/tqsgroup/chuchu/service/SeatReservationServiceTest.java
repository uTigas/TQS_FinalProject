package tqsgroup.chuchu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.neo4j.repository.ConnectionRepository;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.repository.SeatRepository;
import tqsgroup.chuchu.data.repository.SeatReservationRepository;
import tqsgroup.chuchu.data.service.SeatReservationService;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SeatReservationServiceTest {
    
    @Mock(lenient = true)
    private SeatReservationRepository seatReservationRepository;

    @Mock(lenient = true)
    private SeatRepository seatRepository;

    @Mock(lenient = true)
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private SeatReservationService seatReservationService;

    Carriage carriage = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
    Seat seat = new Seat(1, carriage);
    Connection connection = new Connection(new Station("D", 4), new Station("E", 5), new Train(TrainType.ALPHA, 3), LocalTime.of(14, 0), LocalTime.of(15, 0), 3, 1L);
    SeatReservation seatReservation = new SeatReservation(seat, connection.getId());


    @Test
    void whenSaveValidSeatReservation_thenSeatReservationShouldBeSaved() {
        when(connectionRepository.findById(seatReservation.getConnection())).thenReturn(Optional.of(connection));
        when(seatReservationRepository.save(seatReservation)).thenReturn(seatReservation);
        seatReservationService.saveSeatReservation(seatReservation);
        verify(seatReservationRepository, times(1)).save(seatReservation);
    }

    @Test
    void whenSaveSeatReservationWithInvalidSeat_thenSeatReservationShouldNotBeSaved() {
        Seat nullSeat = null;
        SeatReservation badSeatReservation = new SeatReservation(nullSeat, connection.getId());
        when(connectionRepository.findById(connection.getId())).thenReturn(Optional.of(connection));
        when(seatReservationRepository.save(badSeatReservation)).thenReturn(null);
        try {
            seatReservationService.saveSeatReservation(badSeatReservation);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Seat and connection are mandatory for calculating seat reservation price");
        }
        verify(seatReservationRepository, never()).save(badSeatReservation);
    }

    @Test
    void whenSaveSeatReservationWithInvalidConnection_thenSeatReservationShouldNotBeSaved() {
        SeatReservation badConnectionReservation = new SeatReservation(seat, null);
        when(connectionRepository.findById(badConnectionReservation.getConnection())).thenReturn(Optional.empty());
        when(seatReservationRepository.save(badConnectionReservation)).thenReturn(null);
        try {
            seatReservationService.saveSeatReservation(badConnectionReservation);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Seat and connection are mandatory for calculating seat reservation price");
        }
        verify(seatReservationRepository, never()).save(badConnectionReservation);
    }

    @Test
    void whenReserveFreeSeat_thenSeatShouldBeReserved() {
        when(seatReservationRepository.findBySeat(seat)).thenReturn(null);
        when(seatRepository.save(seat)).thenReturn(seat);
        when(seatReservationRepository.save(seatReservation)).thenReturn(seatReservation);

        assertThat(seat.isReserved()).isFalse();
        assertThat(seatReservationService.reserveSeat(seat, connection)).isTrue();
        assertThat(seat.isReserved()).isTrue();
        
        verify(seatRepository, times(1)).save(seat);
        verify(seatReservationRepository, times(1)).save(any(SeatReservation.class));
    }

    @Test
    void whenReserveOccupiedSeat_thenSeatShouldNotBeReserved() {
        Seat reservedSeat = new Seat(2, carriage);
        reservedSeat.setReserved(true);
        SeatReservation reservedSeatReservation = new SeatReservation(reservedSeat, connection.getId());
        when(seatReservationRepository.findBySeat(reservedSeat)).thenReturn(reservedSeatReservation);

        assertThat(reservedSeat.isReserved()).isTrue();
        assertThat(seatReservationService.reserveSeat(reservedSeat, connection)).isFalse();
        assertThat(reservedSeat.isReserved()).isTrue();

        verify(seatRepository, never()).save(reservedSeat);
        verify(seatReservationRepository, never()).save(any(SeatReservation.class));
    }

    @Test
    void whenReleaseReservedSeat_thenSeatShouldBeReleased() {
        Seat reservedSeat = new Seat(2, carriage);
        reservedSeat.setReserved(true);
        SeatReservation reservedSeatReservation = new SeatReservation(reservedSeat, connection.getId());
        when(seatReservationRepository.findBySeat(reservedSeat)).thenReturn(reservedSeatReservation);
        when(seatRepository.save(reservedSeat)).thenReturn(reservedSeat);

        assertThat(reservedSeat.isReserved()).isTrue();
        assertThat(seatReservationService.releaseSeat(reservedSeat)).isTrue();
        assertThat(reservedSeat.isReserved()).isFalse();

        verify(seatRepository, times(1)).save(reservedSeat);
        verify(seatReservationRepository, times(1)).delete(reservedSeatReservation);
    }

    @Test
    void whenReleaseFreeSeat_thenSeatShouldNotBeReleased() {
        when(seatReservationRepository.findBySeat(seat)).thenReturn(null);

        assertThat(seat.isReserved()).isFalse();
        assertThat(seatReservationService.releaseSeat(seat)).isFalse();
        assertThat(seat.isReserved()).isFalse();

        verify(seatRepository, never()).save(seat);
        verify(seatReservationRepository, never()).delete(any(SeatReservation.class));
    }

    @Test
    void whenFindSeatReservationBySeat_thenSeatReservationShouldBeFound() {
        when(seatReservationRepository.findBySeat(seat)).thenReturn(seatReservation);
        assertThat(seatReservationService.findSeatReservationBySeat(seat)).isEqualTo(seatReservation);
        verify(seatReservationRepository, times(1)).findBySeat(seat);
    }

    @Test
    void whenFindSeatReservationByInvalidSeat_thenSeatReservationShouldNotBeFound() {
        when(seatReservationRepository.findBySeat(seat)).thenReturn(null);
        assertThat(seatReservationService.findSeatReservationBySeat(seat)).isNull();
        verify(seatReservationRepository, times(1)).findBySeat(seat);
    }

    @Test
    void whenFindAllSeatReservationsByConnection_thenSeatReservationsShouldBeFound() {
        when(seatReservationRepository.findAllByConnection(connection)).thenReturn(Arrays.asList(seatReservation));
        assertThat(seatReservationService.findAllSeatReservationsByConnection(connection)).isEqualTo(Arrays.asList(seatReservation));
        verify(seatReservationRepository, times(1)).findAllByConnection(connection);
    }

    @Test
    void whenFindAllSeatReservationsByInvalidConnection_thenSeatReservationsShouldNotBeFound() {
        when(seatReservationRepository.findAllByConnection(connection)).thenReturn(null);
        assertThat(seatReservationService.findAllSeatReservationsByConnection(connection)).isNull();
        verify(seatReservationRepository, times(1)).findAllByConnection(connection);
    }
}
