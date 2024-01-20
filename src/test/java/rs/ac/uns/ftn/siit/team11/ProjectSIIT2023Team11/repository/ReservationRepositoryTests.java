package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.ProjectSiit2023Team11Application;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class ReservationRepositoryTests {
    @Autowired
    private IReservationRepository reservationRepository;

//    @Autowired
//    TestEntityManager entityManager;

    @Test
    public void ReservationRepository_Save_ReturnsSavedReservation(){
        //Arrange
        Reservation reservation = Reservation.builder().numberOfGuests(3).build();

        //Act
        Reservation savedReservation = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findAll();

        //Assert
        assertEquals(1L, savedReservation.getId());
    }
}
