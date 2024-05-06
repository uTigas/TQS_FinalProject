package tqsgroup.chuchu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.repository.SeatRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void whenSaveValidSeat_thenSeatIsSaved() {
        Carriage c = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
        Seat s = new Seat(1, c);
        seatService.saveSeat(s);
        verify(seatRepository, times(1)).save(s);
    }

    @Test
    void whenSaveSeatWithInvalidNumber_thenSeatIsNotSaved() {
        int capacity = 50;
        Carriage c = new Carriage(CarriageType.NORMAL, capacity, new Train(TrainType.URBAN, 2));
        Seat s = new Seat(capacity + 1, c);
        try {
            seatService.saveSeat(s);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Seat number must be within carriage capacity");
        }
    }

    @Test
    void whenSaveSeatWithoutCarriage_thenSeatIsNotSaved() {
        Seat s = new Seat(1, null);
        try {
            seatService.saveSeat(s);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Seat must be assigned to a carriage");
        }
    }

    @Test
    void whenFindAllByCarriage_thenSeatsAreReturned() {
        Carriage c = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
        Seat s1 = new Seat(1, c);
        Seat s2 = new Seat(2, c);
        List<Seat> seats = Arrays.asList(s1, s2);
        when(seatRepository.findAllByCarriage(c)).thenReturn(seats);
        assertThat(seatService.findAllByCarriage(c)).isEqualTo(seats);
    }

    @Test
    void whenFindByNumberAndCarriage_thenSeatIsReturned() {
        Carriage c = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
        Seat s = new Seat(1, c);
        when(seatRepository.findByNumberAndCarriage(1, c)).thenReturn(s);
        assertThat(seatService.findByNumberAndCarriage(1, c)).isEqualTo(s);
    }
}
