package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReportDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;

public class ReportMapper {


    public static ReportDTO mapToReportDTO(Report report){
        return new ReportDTO(
                report.getId(),
                report.getSender().getEmail(),
                report.getReceiver().getEmail(),
                report.getContent(),
                report.getStatus()
        );

    }
    public static Collection<ReportDTO> mapToReportsDTO(Collection<Report> reports){
        Collection<ReportDTO> reportDTOs = new ArrayList<>();
        for (Report report: reports){
            reportDTOs.add(mapToReportDTO(report));
        }
        return  reportDTOs;
    }

    public static Report mapDtoToReport(ReportDTO reportDTO, IUserService userService){

        return new Report(
                reportDTO.getId(),
                userService.findById(reportDTO.getSenderEmail()).get(),
                userService.findById(reportDTO.getReceiverEmail()).get(),
                reportDTO.getContent(),
                reportDTO.getStatus()
        );
    }
}
