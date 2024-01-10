package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.FavoriteAccommodationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.GuestForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserRegistrationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.UserMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.EmailSender;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IReservationService reservationService;



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserForShowDTO>> getUsers(){
        Collection<User> users = userService.findAll();
        return new ResponseEntity<Collection<UserForShowDTO>>(UserMapper.mapToUsersDto(users), HttpStatus.OK);
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserForShowDTO> getUserByEmail(@PathVariable("email") String email) {
        Optional<User> user = userService.findById(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserMapper.mapToUserDto(user.get()), HttpStatus.OK);
    }


    @GetMapping(value = "/activation/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateUser(@PathVariable("email") String email) {
        Optional<User> user = userService.findById(email);
        if (user.isEmpty() || user.get().isActive()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            user.get().setActive(true);
            userService.save(user.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/{email}/favorite_accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteAccommodationDTO> getIsFavoriteAccommodation(@PathVariable("email") String email, @PathVariable("accommodationId") Long accommodationId) {
        Optional<User> user = userService.findById(email);
        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        if (user.isEmpty() || accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(user.get().getFavoriteAccommodations().contains(accommodation.get()))
        {
            return new ResponseEntity<>(new FavoriteAccommodationDTO(accommodationId,true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new FavoriteAccommodationDTO(accommodationId, false),HttpStatus.OK);
    }

    @Operation(summary = "Update Users Favorite Accommodation.")
    @PutMapping("/{email}/favorite_accommodation")
    public ResponseEntity<FavoriteAccommodationDTO> setFavoriteAccommodation(@PathVariable("email") String userId, @RequestBody FavoriteAccommodationDTO favoriteAccommodationDTO){
        Optional<User> user = userService.findById(userId);
        Optional<Accommodation> accommodation = accommodationService.findById(favoriteAccommodationDTO.getAccommodationId());
        if(user.isEmpty() || accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(favoriteAccommodationDTO.isFavorite())
            user.get().getFavoriteAccommodations().remove(accommodation.get());
        else{
            user.get().getFavoriteAccommodations().add(accommodation.get());
        }
        userService.save(user.get());
        return new ResponseEntity<>(favoriteAccommodationDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegistrationDTO> registerUserWithRole(@RequestBody UserRegistrationDTO registeredUser) throws Exception {
        EmailSender emailSender = new EmailSender();
        if (userService.findById(registeredUser.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (registeredUser.getRole().equalsIgnoreCase("Guest")) {
                userService.register(UserMapper.mapToGuest(registeredUser));
            } else if (registeredUser.getRole().equalsIgnoreCase("Owner")) {
                userService.register(UserMapper.mapToOwner(registeredUser));
            } else {
                return new ResponseEntity<UserRegistrationDTO>(registeredUser, HttpStatus.BAD_REQUEST);
            }
            emailSender.sendActivationEmail(registeredUser.getEmail(),"localhost:4200/activation/" + registeredUser.getEmail());

            return new ResponseEntity<UserRegistrationDTO>(registeredUser, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<UserRegistrationDTO>(registeredUser, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Owner','ROLE_Admin')")
    @Operation(summary = "Login", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO loginDTO) {
        if (userService.isLoginValid(loginDTO.getEmail(), loginDTO.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Owner','ROLE_Admin')")
    @Operation(summary = "Block profile", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{email}/block")
    public ResponseEntity<UserForShowDTO> blockUser(@PathVariable("email") String userId){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.get().setActive(false);
        userService.save(user.get());
        return new ResponseEntity<UserForShowDTO>(UserMapper.mapToUserDto(user.get()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Owner','ROLE_Admin')")
    @Operation(summary = "Update profile", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/{email}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserForShowDTO> updateProfile(@RequestBody UserForShowDTO user, @PathVariable String email) throws Exception {
        System.out.println(user);
        Optional<User> existingUser = userService.findById(email);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(Objects.equals(user.getPassword(), "")){
            user.setPassword(existingUser.get().getPassword());
            User updatedUser = userService.save(UserMapper.mapDtoToUser(user, userService));
        }else{
            User updatedUser = userService.register(UserMapper.mapDtoToUser(user, userService));
        }


        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest')")
    @Operation(summary = "Add favorite accommodations", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{email}/favorite_accommodation/{accommodationId}")
    public ResponseEntity<GuestForShowDTO> addFavoriteAccommodation(@PathVariable("email") String email, @PathVariable("accommodationId") Long accommodationId){
        Optional<User> user = userService.findById(email);
        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        if(user.isEmpty() || accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!(user.get() instanceof Guest guest)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        guest.getFavoriteAccommodations().add(accommodation.get());
        userService.save(guest);
        return new ResponseEntity<>(UserMapper.mapGuestToGuestForShowDTO(guest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest')")
    @Operation(summary = "Get favorite accommodations", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{email}/favorite_accommodation")
    public ResponseEntity<GuestForShowDTO> getGuestsFavoriteAccommodations(@PathVariable("email") String email){
        Optional<User> user = userService.findById(email);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!(user.get() instanceof Guest guest)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserMapper.mapGuestToGuestForShowDTO(guest), HttpStatus.OK);
    }

    @DeleteMapping("/{email}/favorite_accommodation/{accommodationId}")
    public ResponseEntity<GuestForShowDTO> deleteGuestsFavoriteAccommodation(@PathVariable("email") String email, @PathVariable("accommodationId") Long accommodationId){
        Optional<User> user = userService.findById(email);
        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        if(user.isEmpty() || accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!(user.get() instanceof Guest guest)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!guest.getFavoriteAccommodations().contains(accommodation.get())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        guest.getFavoriteAccommodations().remove(accommodation.get());
        userService.save(guest);
        return new ResponseEntity<>(UserMapper.mapGuestToGuestForShowDTO(guest), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_Guest', 'ROLE_Owner')")
    @Operation(summary = "Delete user", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        Optional<User> user = userService.findById(email);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(user.get() instanceof Owner){
            List<Accommodation> ownersAccommodations = accommodationService.findByOwnersId(email);
            for (Accommodation accommodation : ownersAccommodations) {
                if(reservationService.anyReservationInFuture(accommodation)){
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            accommodationService.deleteAccommodations(ownersAccommodations);
        } else if (user.get() instanceof Guest) {
            if(reservationService.guestHasActiveReservations(email)){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        userService.deleteById(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}