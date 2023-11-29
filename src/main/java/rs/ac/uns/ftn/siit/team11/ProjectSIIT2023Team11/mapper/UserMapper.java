package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Admin;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserMapper {
    public static UserForShowDTO mapToUserDto(User user) {
        return new UserForShowDTO(
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                getRole(user)
        );
    }

    public static Guest mapToGuest(UserRegistrationDTO registeredUser) {
        return new Guest(
                registeredUser.getEmail(),
                registeredUser.getPassword(),
                registeredUser.isActive(),
                registeredUser.getName(),
                registeredUser.getLastName(),
                registeredUser.getAddress(),
                registeredUser.getPhoneNumber()
        );
    }

    public static Owner mapToOwner(UserRegistrationDTO registeredUser) {
        return new Owner(
                registeredUser.getEmail(),
                registeredUser.getPassword(),
                registeredUser.isActive(),
                registeredUser.getName(),
                registeredUser.getLastName(),
                registeredUser.getAddress(),
                registeredUser.getPhoneNumber()
        );
    }

    public static String getRole(User user) {
        if (user instanceof Owner) {
            return "Owner";
        } else if (user instanceof Admin) {
            return "Admin";
        } else {
            return "Guest";
        }
    }

    public static Collection<UserForShowDTO> mapToUsersDto(Collection<User> users) {
        Collection<UserForShowDTO> usersForShow = new ArrayList<>();
        for (User user : users) {
            usersForShow.add(mapToUserDto(user));
        }
        return usersForShow;
    }

}
