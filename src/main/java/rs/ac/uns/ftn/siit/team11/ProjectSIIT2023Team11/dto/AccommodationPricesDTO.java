package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;

import java.util.List;

public class AccommodationPricesDTO {
    private Long id;
    private List<Price> priceList;

    public AccommodationPricesDTO(Long id, List<Price> priceList) {
        this.id = id;
        this.priceList = priceList;
    }

    public AccommodationPricesDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
}
