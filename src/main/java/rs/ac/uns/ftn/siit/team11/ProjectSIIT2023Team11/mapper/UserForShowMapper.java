package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Admin;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

public class UserForShowMapper {
    // Convert User JPA Entity into UserDto
    public static UserForShowDTO mapToUserDto(User user){
        UserForShowDTO userForShow = new UserForShowDTO();

        return new UserForShowDTO(
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                getRole(user)
                );
    }

    public static String getRole(User user){
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
        for (User user: users){
            usersForShow.add(mapToUserDto(user));
        }
        return  usersForShow;
    }
}