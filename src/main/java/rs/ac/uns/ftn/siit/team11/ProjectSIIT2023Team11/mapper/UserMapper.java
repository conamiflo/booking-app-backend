package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

public class UserMapper {
    // Convert User JPA Entity into UserDto
    public static UserForShowDTO mapToUserDto(User user){
        return new UserForShowDTO(
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getUserRole()
        );
    }

    public static Collection<UserForShowDTO> mapToUsersDto(Collection<User> users) {
        Collection<UserForShowDTO> usersForShow = new ArrayList<>();
        for (User user: users){
            usersForShow.add(mapToUserDto(user));
        }
        return  usersForShow;

    }
}
