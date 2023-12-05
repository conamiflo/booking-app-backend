package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Analytic;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AnalyticDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAnalyticService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;

public class AnalyticMapper {
    public static AnalyticDTO mapToAnalyticDTO(Analytic analytic){
        return new AnalyticDTO(
                analytic.getId(),
                analytic.getAccommodation().getId(),
                analytic.getMonth(),
                analytic.getYear(),
                analytic.getNumberOfReservations(),
                analytic.getProfit()
        );

    }
    public static Collection<AnalyticDTO> mapToAnalyticsDTO(Collection<Analytic> analytics){
        Collection<AnalyticDTO> analyticDTOs = new ArrayList<>();
        for (Analytic analytic: analytics){
            analyticDTOs.add(mapToAnalyticDTO(analytic));
        }
        return  analyticDTOs;
    }

    public static Analytic mapDtoToAnalytic(AnalyticDTO analyticDTO, IAccommodationService accommodationService){

        return new Analytic(
                analyticDTO.getId(),
                accommodationService.findById(analyticDTO.getAccommodationId()).get(),
                analyticDTO.getMonth(),
                analyticDTO.getYear(),
                analyticDTO.getNumberOfReservations(),
                analyticDTO.getProfit()
        );
    }
}
