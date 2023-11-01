package tn.esprit.devops_project.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivitySectorImplTest {

    @Mock
    private ActivitySectorRepository activitySectorRepository;

    @InjectMocks
    private ActivitySectorImpl activitySector;

    @Test
    public void itShouldAddActivity() {
        //Arrange
        ActivitySector activity = ActivitySector.builder()
                .idSecteurActivite(1L)
                .codeSecteurActivite("code")
                .suppliers(null)
                .libelleSecteurActivite("libelle")
                .build();
        when(activitySectorRepository.save(Mockito.any(ActivitySector.class))).thenReturn(activity);
        //Act
        ActivitySector activitySector1 = activitySector.addActivitySector(activity);
        //Assert
        Assertions.assertThat(activitySector1).isEqualTo(activity);
    }


    @Test
    public void itShouldRetrieveAllActivitySectors() {
        // Arrange
        List<ActivitySector> expectedActivitySectors = Arrays.asList(
                new ActivitySector()
        );
        when(activitySectorRepository.findAll()).thenReturn(expectedActivitySectors);

        // Act
        List<ActivitySector> actualActivitySectors = activitySector.retrieveAllActivitySectors();

        // Assert
        Assertions.assertThat(actualActivitySectors).isEqualTo(expectedActivitySectors);
    }

    @Test
    public void itShouldDeleteActivitySector() {
        // Arrange
        Long idToDelete = 1L;

        // Act
        activitySector.deleteActivitySector(idToDelete);

        // Assert
        verify(activitySectorRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    public void itShouldUpdateActivitySector() {
        // Arrange
        ActivitySector activity = ActivitySector.builder()
                .idSecteurActivite(1L)
                .codeSecteurActivite("code")
                .suppliers(null)
                .libelleSecteurActivite("libelle")
                .build();
        when(activitySectorRepository.save(activity)).thenReturn(activity);

        // Act
        ActivitySector updatedActivity = activitySector.updateActivitySector(activity);

        // Assert
        Assertions.assertThat(updatedActivity).isEqualTo(activity);
    }

    @Test
    public void itShouldRetrieveActivitySector() {
        // Arrange
        Long idToRetrieve = 1L;
        ActivitySector expectedActivity = ActivitySector.builder()
                .idSecteurActivite(idToRetrieve)
                .codeSecteurActivite("code")
                .suppliers(null)
                .libelleSecteurActivite("libelle")
                .build();
        when(activitySectorRepository.findById(idToRetrieve)).thenReturn(Optional.of(expectedActivity));

        // Act
        ActivitySector actualActivity = activitySector.retrieveActivitySector(idToRetrieve);

        // Assert
        Assertions.assertThat(actualActivity).isEqualTo(expectedActivity);
    }

}