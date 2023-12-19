package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccountRequestService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccountRequest;

import java.util.Collection;
import java.util.Optional;

//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/accRequests")
public class AccountRequestController {

    @Autowired
    private IAccountRequestService accountRequestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountRequest>> getAccountRequests() {
        Collection<AccountRequest> accountRequests = accountRequestService.findAll();
        return new ResponseEntity<>(accountRequests, HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRequest> createAccountRequest(AccountRequest accountRequest){
        Optional<AccountRequest> newRequest = Optional.ofNullable(accountRequestService.save(accountRequest));
        return new ResponseEntity<>(newRequest.get(), HttpStatus.CREATED);
    }


}
