package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserEditAdminDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserSelfEditDTO;

import java.util.ArrayList;
import java.util.Collection;

public class UserSelfEditMapper {
    public static UserSelfEditDTO mapToUserEditAdminDto(User user){
        return new UserSelfEditDTO(
                user.getPassword(),
                user.getName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

    public static Collection<UserSelfEditDTO> mapToUserEditAdminDto(Collection<User> users) {
        Collection<UserSelfEditDTO> usersForShow = new ArrayList<>();
        for (User user: users){
            usersForShow.add(mapToUserEditAdminDto(user));
        }
        return  usersForShow;

    }
}
