package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationRequest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationRequestsDTO;

import java.util.List;
import java.util.Optional;

public interface IAccommodationRequestService {
    List<AccommodationRequest> findAll();
    <S extends AccommodationRequest> S save(S entity);

    AccommodationRequest create(AccommodationRequestsDTO accommodationrequestDTO);
    Optional<AccommodationRequest> findById(Long aLong);
    void deleteById(Long aLong);
}
