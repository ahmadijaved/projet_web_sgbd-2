package be.isl.ue.vm;

public class SectionSearchVM {

    private String name;
    private String personLastName;
    private String personFirstName;

    public SectionSearchVM() {
    }

    public SectionSearchVM(String name, String personLastName, String personFirstName) {
        this.name = name;
        this.personLastName = personLastName;
        this.personFirstName = personFirstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

}
