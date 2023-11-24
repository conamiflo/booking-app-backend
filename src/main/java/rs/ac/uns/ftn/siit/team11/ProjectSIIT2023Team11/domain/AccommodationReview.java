package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;

public class AccommodationReview extends Review{

    private Accommodation accommondation;

    public AccommodationReview(Long id, User guest, String description, int rating, LocalDate date, boolean reported, Accommodation accommondation) {
        super(id, guest, description, rating, date, reported);
        this.accommondation = accommondation;
    }

    public AccommodationReview(Accommodation accommondation) {
        this.accommondation = accommondation;
    }

    public Accommodation getAccommondation() {
        return accommondation;
    }

    public void setAccommondation(Accommodation accommondation) {
        this.accommondation = accommondation;
    }
}
