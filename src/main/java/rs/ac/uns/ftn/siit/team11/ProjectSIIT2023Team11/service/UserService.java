package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public Optional<User> findById(String s) {
        return userRepository.findById(s);
    }

    public void deleteById(String s) {
        userRepository.deleteById(s);
    }

    public boolean isLoginValid(String email, String password) {
        Optional<User> user = userRepository.findById(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    @Override
    public User register(User userRequest) {

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return this.userRepository.save(userRequest);
    }
}