package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccommodationRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Test
    public void shouldSaveAccommodation() {

        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        accommodation.setDescription("Test Description");
        accommodation.setId(10L);

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        assertThat(savedAccommodation).isNotNull();
        assertThat(savedAccommodation.getId()).isNotNull();

        assertThat(savedAccommodation).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodation);
    }

}
