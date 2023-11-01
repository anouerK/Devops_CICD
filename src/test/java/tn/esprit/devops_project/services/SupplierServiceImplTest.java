package tn.esprit.devops_project.services;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.*;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {
    @Mock
    private SupplierRepository supplierRepository;
    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    public void itShouldAddSupplier() {
        // Arrange
        Supplier supplier = Supplier.builder()
                .idSupplier(1L)
                .supplierCategory(SupplierCategory.CONVENTIONNE)
                .label("Sample Supplier")
                .code("S123")
                .build();
        when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(supplier);
        //Act
        Supplier supplier2 = supplierService.addSupplier(supplier);
        //Assert
        Assertions.assertThat(supplier2).isEqualTo(supplier);
    }
    @Test
    public void itShouldUpdateSupplier() {
        // Arrange
        Supplier supplier = Supplier.builder()
                .idSupplier(1L)
                .supplierCategory(SupplierCategory.CONVENTIONNE)
                .label("Sample Supplier")
                .code("S123")
                .build();
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        // Act
        Supplier updatedSupplier = supplierService.updateSupplier(supplier);

        // Assert
        Assertions.assertThat(updatedSupplier).isEqualTo(supplier);
    }
    @Test
    public void itShouldRetrieveAllSuppliers() {
        // Arrange
        List<Supplier> expectedSuppliers = Arrays.asList(
                new Supplier()
        );
        when(supplierRepository.findAll()).thenReturn(expectedSuppliers);

        // Act
        List<Supplier> actualSuppliers = supplierService.retrieveAllSuppliers();

        // Assert
        Assertions.assertThat(actualSuppliers).isEqualTo(expectedSuppliers);
    }
    @Test
    public void itShouldDeleteSupplier() {
        // Arrange
        Long idToDelete = 1L;

        // Act
        supplierService.deleteSupplier(idToDelete);

        // Assert
        verify(supplierRepository, times(1)).deleteById(idToDelete);
    }

}
