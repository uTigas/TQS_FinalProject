package tqsgroup.chuchu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.repository.CarriageRepository;
import tqsgroup.chuchu.data.service.CarriageService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class CarriageServiceTest {

    @Mock(lenient = true)
    private CarriageRepository carriageRepository;

    @InjectMocks
    private CarriageService carriageService;

    @BeforeEach
    void setUp() {
        Train t1 = new Train(TrainType.ALPHA, 1);
        Carriage c1 = new Carriage(CarriageType.FIRST_CLASS, 100, t1);
        Carriage c2 = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));

        List<Carriage> carriages = Arrays.asList(c1,c2);
    
        when(carriageRepository.save(c1)).thenReturn(c1);
        when(carriageRepository.findByType(c1.getType())).thenReturn(c1);
        when(carriageRepository.findAll()).thenReturn(carriages);
    }

    @Test
    void whenSaveValidCarriage_thenCarriageShouldBeSaved() {
        Carriage c3 = new Carriage(CarriageType.FIRST_CLASS, 150, new Train(TrainType.ALPHA, 3));
        carriageService.saveCarriage(c3);
        verify(carriageRepository, times(1)).save(c3);
    }

    @Test
    void whenSaveCarriageWithInvalidCapacity_thenCarriageShouldNotBeSaved() {
        Carriage c = new Carriage(CarriageType.FIRST_CLASS, 5, new Train(TrainType.ALPHA, 4));
        try {
            carriageService.saveCarriage(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Number of seats must be between 10 and 200");
        }
        verify(carriageRepository, never()).save(c);
    }

    @Test
    void whenSaveCarriageWithInvalidType_thenCarriageShouldNotBeSaved() {
        Carriage c = new Carriage(CarriageType.FIRST_CLASS, 100, new Train(TrainType.URBAN, 5));
        try {
            carriageService.saveCarriage(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("FIRST_CLASS carriage is not allowed for URBAN train");
        }
        c = new Carriage(CarriageType.NORMAL, 100, new Train(TrainType.ALPHA, 6));
        try {
            carriageService.saveCarriage(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("NORMAL carriage is not allowed for ALPHA train");
        }
        verify(carriageRepository, never()).save(c);
    }

    @Test
    void whenSearchValidType_thenCarriageShouldBeFound() {
        CarriageType type = CarriageType.FIRST_CLASS;
        Carriage found = carriageService.findByCarriageType(type);
        assertThat(found.getType()).isEqualTo(type);
        verify(carriageRepository, times(1)).findByType(type);
    }

    @Test
    void whenSearchValidTrain_thenCarriageShouldBeFound() {
        Train train = new Train(TrainType.ALPHA, 1);
        Carriage c1 = new Carriage(CarriageType.FIRST_CLASS, 100, train);
        Carriage c2 = new Carriage(CarriageType.FIRST_CLASS, 150, train);

        when(carriageRepository.findAllByTrain(train)).thenReturn(Arrays.asList(c1, c2));

        List<Carriage> found = carriageService.findAllByTrain(train);
        assertThat(found).hasSize(2).containsExactlyInAnyOrder(c1, c2);
        verify(carriageRepository, times(1)).findAllByTrain(train);
    }

    @Test
    void whenSearchNonExistingType_thenCarriageShouldNotBeFound() {
        CarriageType type = CarriageType.SECOND_CLASS;
        Carriage found = carriageService.findByCarriageType(type);
        assertThat(found).isNull();
        verify(carriageRepository, times(1)).findByType(type);
    }

    @Test
    void whenGetAllCarriages_thenAllCarriagesShouldBeReturned() {
        Carriage c1 = new Carriage(CarriageType.FIRST_CLASS, 100, new Train(TrainType.ALPHA, 1));
        Carriage c2 = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
        
        List<Carriage> allCarriages = carriageService.getAllCarriages();
        assertThat(allCarriages).hasSize(2).extracting(Carriage::getType).contains(c1.getType(), c2.getType());
        verify(carriageRepository, times(1)).findAll();
    }
}
