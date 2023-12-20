package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationRequest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationRequestsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccommodationRequestService  implements  IAccommodationRequestService{

    @Autowired
    private IAccommodationRequestRepository accommodationRequestRepository;

    public List<AccommodationRequest> findAll() {
        return accommodationRequestRepository.findAll();
    }

    public <S extends AccommodationRequest> S save(S entity) {
        return accommodationRequestRepository.save(entity);
    }

    public AccommodationRequest create(AccommodationRequestsDTO accommodationRequestDTO){
        
        AccommodationRequest accRequest = AccommodationMapper.mapRequestDtoToNewRequest(accommodationRequestDTO);
        return save(accRequest);
    }

    public Optional<AccommodationRequest> findById(Long aLong) {
        return accommodationRequestRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        accommodationRequestRepository.deleteById(aLong);
    }
}
