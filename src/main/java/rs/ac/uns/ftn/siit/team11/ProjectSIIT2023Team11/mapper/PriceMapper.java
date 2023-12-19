package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.PriceForEditDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.PriceForShowDTO;

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

    public static PriceForEditDTO mapToPriceDto(Price price) {
        PriceForEditDTO priceDto = new PriceForEditDTO();
        priceDto.setFrom(price.getTimeSlot().getStartDate().toString());
        priceDto.setTo(price.getTimeSlot().getEndDate().toString());
        priceDto.setPrice(price.getPrice());
        return priceDto;
    }


}
