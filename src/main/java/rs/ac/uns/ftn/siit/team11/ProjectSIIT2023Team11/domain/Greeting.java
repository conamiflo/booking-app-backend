package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;


public class Greeting {

    private Long id;
    private String text;

    public Greeting() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void copyValues(Greeting greeting) {
        this.text = greeting.getText();
    }

}

