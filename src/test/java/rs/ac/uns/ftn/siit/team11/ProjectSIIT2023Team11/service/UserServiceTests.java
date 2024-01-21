package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;
    private final static String VALID_ID = "ognjen@gmail.com";

    private final static String NON_EXISTING = "invalid@gmail.com";
    Optional<User> user = Optional.of(User.builder().build());
    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);

        when(userRepository.findById(VALID_ID)).thenReturn(user);
        when(userRepository.findById(NON_EXISTING)).thenReturn(Optional.empty());
    }

    @Test
    public void AccommodationService_FindById_ValidId(){
        Optional<User> optionalUser = userService.findById(VALID_ID);
        assertEquals(user, optionalUser);
    }

    @Test
    public void AccommodationService_FindById_InvalidId(){
        Optional<User> optionalUser = userService.findById(NON_EXISTING);
        assertEquals(Optional.empty(), optionalUser);
    }

}
