package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.AuthResponseDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.exceptions.BadRequestException;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.security.JwtTokenUtil;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.UserService;

import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();


    @PostMapping("/logIn")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO loginDTO) {
        Optional<User> user = userService.findById(loginDTO.getEmail());
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                loginDTO.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        securityContext.setAuthentication(auth);

        String token = jwtTokenUtil.generateToken(loginDTO.getEmail(), user.get().getRole());

        user.get().setJwt(token);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    @PostMapping("/logOut")
    public ResponseEntity<String> logout() {
        Authentication auth = securityContext.getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You unsuccessfully logged out!", HttpStatus.BAD_REQUEST);
        } }
}
