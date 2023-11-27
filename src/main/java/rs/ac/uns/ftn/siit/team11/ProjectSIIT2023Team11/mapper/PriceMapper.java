package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceForShowDTO;

public class PriceMapper {
    public static PriceForShowDTO mapToPriceForShowDto(Price price) {
        return new PriceForShowDTO(
                price.getId(),
                price.getTimeSlot(),
                price.getPrice()
        );
    }
    public static Price mapInputPriceToPrice(InputPriceDTO priceInput) {
        Price price = new Price();
        price.setPrice(priceInput.getPrice());
        price.setTimeSlot(priceInput.getTimeSlot());
        return price;
    }
    public static void mapPriceForShowToPrice(InputPriceDTO priceInput, Price price) {
        price.setPrice(priceInput.getPrice());
        price.setTimeSlot(priceInput.getTimeSlot());
    }
}
