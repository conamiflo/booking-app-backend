package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AvailabilityRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IAvailabilityRepository availabilityRepository;

    @Test
    public void shouldSaveAvailability() {

        Long startDate = LocalDateTime.of(2024, 2, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Availability availability = new Availability(55L,new TimeSlot(startDate,endDate));

        Availability savedAvailability = availabilityRepository.save(availability);

        assertThat(savedAvailability).isNotNull();
        assertThat(savedAvailability.getId()).isNotNull();

        Availability loadedAvailability = testEntityManager.find(Availability.class, savedAvailability.getId());

        assertThat(loadedAvailability).isNotNull();

        assertThat(loadedAvailability).usingRecursiveComparison().ignoringFields("id").isEqualTo(savedAvailability);
    }

}
