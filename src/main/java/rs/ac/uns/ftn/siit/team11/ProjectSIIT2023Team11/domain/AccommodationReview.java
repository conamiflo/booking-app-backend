package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;

public class AccommodationReview extends Review{

    private String accommondation;

    public AccommodationReview(int id, String guest, String description, int rating, LocalDate date, boolean reported, String accommondation) {
        super(id, guest, description, rating, date, reported);
        this.accommondation = accommondation;
    }

    public AccommodationReview(String accommondation) {
        this.accommondation = accommondation;
    }

    public String getAccommondation() {
        return accommondation;
    }

    public void setAccommondation(String accommondation) {
        this.accommondation = accommondation;
    }
}
