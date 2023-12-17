package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService implements IAccommodationService{

    @Autowired
    private IAccommodationRepository accommodationRepository;
    public <S extends Accommodation> S save(S entity) {
        return accommodationRepository.save(entity);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Optional<Accommodation> findById(Long aLong) {
        return accommodationRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        accommodationRepository.deleteById(aLong);
    }
    public Optional<AccommodationDetailsDTO> create(AccommodationDetailsDTO accommodationDetailsDTO, IUserService userService){
        Optional<User> user =  userService.findById(accommodationDetailsDTO.ownerEmail());
        if(user.isEmpty()){
            return Optional.empty();
        }
        Owner owner = (Owner) user.get();
        Accommodation accommodation = AccommodationMapper.mapDetailsDtoToNewAccommodation(accommodationDetailsDTO,owner);
        return Optional.of(AccommodationMapper.mapToAccommodationDetailsDto(save(accommodation)));
    }

    @Override
    public void deletePriceFromAllAccommodations(Price price) {
        List<Accommodation> accommodations = findAll();
        for(Accommodation accommodation : accommodations){
            if(accommodation.containsPrice(price)){
                accommodation.getPriceList().remove(price);
                save(accommodation);
            }
        }
    }

    @Override
    public List<Accommodation> findByOwnersId(String email) {
        List<Accommodation> accommodations = new ArrayList<>();
        for (Accommodation accommodation: accommodationRepository.findAll()) {
            if (accommodation.getOwner().getEmail().equals(email)){
                accommodations.add(accommodation);
            }
        }
        return accommodations;
    }

    @Override
    public void deleteAccommodations(List<Accommodation> accommodations) {
        for(Accommodation accommodation : accommodations){
            deleteById(accommodation.getId());
        }
    }

    @Override
    public void deleteAmenityFromAccommodations(Amenity amenity) {
        for (Accommodation accommodation: accommodationRepository.findAll()) {
            while (accommodation.getAmenities().contains(amenity)){
                accommodation.getAmenities().remove(amenity);
            }
        }
    }

}
