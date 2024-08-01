package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.PriceForEditDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.PriceForShowDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        priceDto.setFrom(Instant.ofEpochSecond(price.getTimeSlot().getStartEpochTime()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        priceDto.setTo(Instant.ofEpochSecond(price.getTimeSlot().getEndEpochTime()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        priceDto.setPrice(price.getPrice());
        return priceDto;
    }


}
