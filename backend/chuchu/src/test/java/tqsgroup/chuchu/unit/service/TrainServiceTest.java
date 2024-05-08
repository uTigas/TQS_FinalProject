package tqsgroup.chuchu.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.repository.TrainRepository;
import tqsgroup.chuchu.data.service.TrainService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class TrainServiceTest {

    @Mock(lenient = true)
    private TrainRepository trainRepository;

    @InjectMocks
    private TrainService trainService;

    @BeforeEach
    void setUp() {
        Train t1 = new Train(TrainType.ALPHA, 1);
        Train t2 = new Train(TrainType.URBAN, 2);

        List<Train> trains = Arrays.asList(t1,t2);
    
        when(trainRepository.save(t1)).thenReturn(t1);
        when(trainRepository.findByNumber(t1.getNumber())).thenReturn(t1);
        when(trainRepository.findByNumber(-10)).thenReturn(null);
        when(trainRepository.findAll()).thenReturn(trains);
    }

    @Test
    void whenSaveTrainWithValidNumber_thenTrainShouldBeSaved() {
        Train t3 = new Train(TrainType.ALPHA, 3);
        trainService.saveTrain(t3);
        verify(trainRepository, times(1)).save(t3);
    }

    @Test
    void whenSaveTrainWithInvalidNumber_thenTrainShouldNotBeSaved() {
        Train t = new Train(TrainType.ALPHA, -1);
        try {
            trainService.saveTrain(t);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Train number must be between 1 and 9999 inclusive");
        }
        verify(trainRepository, never()).save(t);
    }

    @Test
    void whenSaveTrainWithNonUniqueNumber_thenTrainShouldNotBeSaved() {
        Train t = new Train(TrainType.ALPHA, 1);
        try {
            trainService.saveTrain(t);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Train number must be unique");
        }
        verify(trainRepository, never()).save(t);
    }

    @Test
    void whenSearchValidNumber_thenTrainShouldBeFound() {
        int number = 1;
        Train found = trainService.findTrainByNumber(number);
        assertThat(found.getNumber()).isEqualTo(number);
        verify(trainRepository, times(1)).findByNumber(number);
    }

    @Test
    void whenSearchNonExistingNumber_thenTrainShouldNotBeFound() {
        int number = 10;
        Train found = trainService.findTrainByNumber(number);
        assertThat(found).isNull();
        verify(trainRepository, times(1)).findByNumber(number);
    }

    @Test 
    void whenSearchInvalidNumber_thenTrainShouldNotBeFound() {
        int number = -10;
        try {
            trainService.findTrainByNumber(number);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Train number must be between 1 and 9999 inclusive");
        }
        verify(trainRepository, never()).findByNumber(number);
    }

    @Test
    void whenGetAllTrains_thenAllTrainsShouldBeReturned() {
        Train t1 = new Train(TrainType.ALPHA, 1);
        Train t2 = new Train(TrainType.URBAN, 2);

        List<Train> allTrains = trainService.getAllTrains();
        assertThat(allTrains).hasSize(2).extracting(Train::getNumber).contains(t1.getNumber(), t2.getNumber());
        verify(trainRepository, times(1)).findAll();
    }
}
