package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.GuestForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserRegistrationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.UserMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.EmailSender;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAccommodationService accommodationService;
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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            emailSender.sendActivationEmail(registeredUser.getEmail(),"activationlink");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO loginDTO) {
        if (userService.isLoginValid(loginDTO.getEmail(), loginDTO.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
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

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        userService.deleteById(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}