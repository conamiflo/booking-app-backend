package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserEditAdminDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

public class UserEditAdminMapper {

    public static UserEditAdminDTO mapToUserEditAdminDto(User user){
        return new UserEditAdminDTO(
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.getName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

    public static Collection<UserEditAdminDTO> mapToUserEditAdminDto(Collection<User> users) {
        Collection<UserEditAdminDTO> usersForShow = new ArrayList<>();
        for (User user: users){
            usersForShow.add(mapToUserEditAdminDto(user));
        }
        return  usersForShow;

    }
}
