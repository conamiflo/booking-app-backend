package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProjectSiit2023Team11Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSiit2023Team11Application.class, args);
	}

}
