package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Admin;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.GuestForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserRegistrationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserMapper {
    public static UserForShowDTO mapToUserDto(User user) {
        return new UserForShowDTO(
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.isNotifications(),
                user.getPhoto()
        );
    }


    public static User mapDtoToUser(UserForShowDTO dto, IUserService userService){
        User user = userService.findById(dto.getEmail()).get();
        user.setPassword(dto.getPassword());
        user.setName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setNotifications(dto.isNotifications());
        user.setPhoto(dto.getPhoto());

        return user;
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

    public static GuestForShowDTO mapGuestToGuestForShowDTO(Guest guest){
        return new GuestForShowDTO(
                guest.getEmail(),
                guest.getName(),
                guest.getLastName(),
                guest.getAddress(),
                guest.getFavoriteAccommodations()
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
