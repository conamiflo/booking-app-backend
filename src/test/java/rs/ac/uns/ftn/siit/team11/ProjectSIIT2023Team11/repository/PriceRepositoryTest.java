package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PriceRepositoryTest {
    @Autowired
    private IPriceRepository priceRepository;

    @Test
    public void shouldSavePrice() {
        Long startDate = LocalDateTime.of(2024, 2, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Price price = new Price(76L,new TimeSlot(startDate,endDate),50.0);

        Price savedPrice = priceRepository.save(price);
        assertThat(savedPrice).isNotNull();
        assertThat(savedPrice.getId()).isNotNull();

        assertThat(savedPrice).usingRecursiveComparison().ignoringFields("id").isEqualTo(price);
    }

}
