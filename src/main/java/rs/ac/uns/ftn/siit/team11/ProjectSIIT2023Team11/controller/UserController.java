package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.UserMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

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
        if (userService.findById(registeredUser.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (registeredUser.getRole().equalsIgnoreCase("Guest")) {
                userService.save(UserMapper.mapToGuest(registeredUser));
            } else if (registeredUser.getRole().equalsIgnoreCase("Owner")) {
                userService.save(UserMapper.mapToOwner(registeredUser));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
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
    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        userService.deleteById(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}