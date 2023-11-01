package tn.esprit.devops_project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {
    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private Operator operator1;
    private Operator operator2;
    private Operator updatedOperator;

    @BeforeEach
    void setUp() {
        operator1 = new Operator(1L, "John", "Doe", "1234", null);
        operator2 = new Operator(2L, "Jane", "Doe", "5678", null);
        updatedOperator = new Operator(1L, "Updated", "Name", "5678", null);
        // Clear the stub behavior before each test
        Mockito.reset(operatorRepository);
    }

    @Test
    void testRetrieveAllOperators() {
        // Setup mock
        List<Operator> operators = Arrays.asList(operator1, operator2);
        when(operatorRepository.findAll()).thenReturn(operators);

        // Execute call
        List<Operator> result = operatorService.retrieveAllOperators();

        // Verify result
        assertThat(result, containsInAnyOrder(operator1, operator2));
    }

    @Test
    void testRetrieveAllOperatorsNoData() {
        when(operatorRepository.findAll()).thenReturn(Collections.emptyList());

        List<Operator> operators = operatorService.retrieveAllOperators();

        assertThat(operators, empty());
    }

    @Test
    void testAddOperator() {
        // Scenario 1: Operator doesn't exist in the repository
        when(operatorRepository.save(operator1)).thenReturn(operator1);

        // Execute call for the first scenario
        Operator result1 = operatorService.addOperator(operator1);

        assertThat(result1, is(operator1));

        // Scenario 2: Operator with the same ID already exists in the repository
        when(operatorRepository.save(operator1)).thenThrow(DataIntegrityViolationException.class);

        // Execute call for the second scenario
        assertThrows(DataIntegrityViolationException.class, () -> {
            operatorService.addOperator(operator1);
        });
    }

    @Test
    void testDeleteOperatorExists() {
        // Scenario: Operator with ID 1 exists in the repository
        when(operatorRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> {
            operatorService.deleteOperator(1L);
        });

        // Verify that the delete method was called with the correct ID
        verify(operatorRepository).deleteById(1L);
    }

    @Test
    void testDeleteOperatorNotExists() {
        // Scenario: Operator with ID 3 does not exist in the repository
        when(operatorRepository.existsById(3L)).thenReturn(false);

        // Execute call for the scenario where the operator doesn't exist
        assertThrows(EmptyResultDataAccessException.class, () -> {
            operatorService.deleteOperator(3L);
        });
    }

    @Test
    void testUpdateOperatorSuccess() {
        // Scenario: Operator to update exists in the repository
        when(operatorRepository.save(updatedOperator)).thenReturn(updatedOperator);

        Operator result = operatorService.updateOperator(updatedOperator);

        assertEquals(updatedOperator, result);
    }

    @Test
    void testUpdateOperatorNotFound() {
        // Scenario: Operator to update does not exist in the repository
        when(operatorRepository.save(updatedOperator)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
            operatorService.updateOperator(updatedOperator);
        });
    }

    @Test
    void testRetrieveOperatorSuccess() {
        // Scenario: Operator with the given ID is found in the repository
        when(operatorRepository.findById(1L)).thenReturn(Optional.of(operator1));

        Operator result = operatorService.retrieveOperator(1L);

        assertEquals(operator1, result);
    }

    @Test
    void testRetrieveOperatorNotFound() {
        // Scenario: Operator with the given ID is not found in the repository
        when(operatorRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> {
            operatorService.retrieveOperator(3L);
        });
    }

    @Test
    void testRetrieveOperatorNullObject() {
        // Scenario: Operator with the given ID is found, but the operator object is null
        when(operatorRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        assertThrows(NullPointerException.class, () -> {
            operatorService.retrieveOperator(1L);
        });
    }
}
