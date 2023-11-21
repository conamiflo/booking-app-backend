public interface AccommodationRepository {
    Collection<Accommodation> findAll();
    Accommodation findById(Long id);
    Accommodation save(Accommodation accommodation);
    Accommodation update(Accommodation accommodation);
    void delete(Long id);
    // Dodatna metoda za pretragu smeštaja
    Collection<Accommodation> search(String location, int guests, String startDate, String endDate);
}