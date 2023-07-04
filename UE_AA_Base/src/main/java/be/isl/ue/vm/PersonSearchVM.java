package be.isl.ue.vm;

public class PersonSearchVM {

    private String firstName;
    private String lastName;
    private String city;
    private String dateOfBirth;
    private String email;

    public PersonSearchVM() {
    }

    public PersonSearchVM(String firstName, String lastName, String city, String dateOfBirth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

