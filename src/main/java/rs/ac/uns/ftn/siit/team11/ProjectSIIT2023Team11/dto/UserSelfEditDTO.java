package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

public class UserSelfEditDTO {
    private String password;

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;

    public UserSelfEditDTO(String password, String name, String lastName, String address, String phoneNumber) {
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
