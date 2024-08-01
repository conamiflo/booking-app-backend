package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAmenityRepository;

import java.util.List;
import java.util.Optional;
@Service
public class AmenityService implements IAmenityService{

    @Autowired
    private IAmenityRepository amenityRepository;
    @Override
    public <S extends Amenity> S save(S entity) {
        return amenityRepository.save(entity);
    }

    @Override
    public List<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    @Override
    public Optional<Amenity> findById(Long aLong) {
        return amenityRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        amenityRepository.deleteById(aLong);
    }

    @Override
    public void deleteAccommodationFromAmenities(Accommodation accommodation) {
        for(Amenity amenity : findAll()){
            while (amenity.getAccommodations().contains(accommodation)){
                amenity.getAccommodations().remove(accommodation);
            }
        }
    }

    public void deleteAmenities( List<Amenity> amenities, Accommodation accommodation){
        deleteAccommodationFromAmenities(accommodation);
        amenities.clear();
    }

}
