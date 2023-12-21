package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

public record AmenityOutputDTO(Long id, String name) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
