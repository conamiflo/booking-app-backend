package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository allUsers;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> ret = allUsers.findById(username);
        if (ret.isEmpty() ) {
            throw new UsernameNotFoundException("User not found with this username: " + username);

        }
        if (ret.get() instanceof Guest){
            return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles("GUEST").build();
        }else if(ret.get() instanceof Owner){
            return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles("OWNER").build();
        }
        return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles("ADMIN").build();


    }
}
