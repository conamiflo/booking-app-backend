package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;

@Repository
public interface IReportRepository extends JpaRepository<Report,Long> {

    boolean existsBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);

}
