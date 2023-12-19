package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name="account_requests")
public class AccountRequest {

//    @Id
    private Long id;
    private String user;
    private Date creationDate;
    private AccountStatus status;

    public AccountRequest(String user, Date creationDate) {
        this.user = user;
        this.creationDate = creationDate;
        this.status = AccountStatus.WAITING;
    }

    public boolean isExpired() {return (System.currentTimeMillis() - creationDate.getTime()) >= (24*3600*1000);}

}
