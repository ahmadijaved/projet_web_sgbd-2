/**
 *
 * @author ahmadi
 */

package be.isl.ue.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Person implements Entity, Comparable<Person>{
    private Integer personId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private Boolean isTeacher;
    private Date dateOfBirth;
    private Boolean isJuryMember;

    public Person() {
    }
    
    public Person(Integer personId, String firstName, String lastName, String mobile, String email, String address, String postalCode, String city, String country, Boolean isTeacher, Date dateOfBirth, Boolean isJuryMember) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.isTeacher = isTeacher;
        this.dateOfBirth = dateOfBirth;
        this.isJuryMember = isJuryMember;
    }

    public Person(Integer personId, String firstName, String lastName, String mobile, String email, String address, String postalCode, String city, String country, Boolean isTeacher, String dateOfBirth, Boolean isJuryMember) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.isTeacher = isTeacher;
        this.dateOfBirth = null;
        this.isJuryMember = isJuryMember;
        
        this.setDateOfBirth(dateOfBirth);
    }
    
    @Override
    public Integer getId(){
        return personId;
    }
    
    @Override
    public void setId(Integer id){
        this.personId = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsJuryMember() {
        return isJuryMember;
    }

    public void setIsJuryMember(Boolean isJuryMember) {
        this.isJuryMember = isJuryMember;
    }  
    
    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
    public void setDateOfBirth(String date){
         try { 
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = dateFormat.parse(date);
            this.dateOfBirth = convertedDate;
        } catch (ParseException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
             this.dateOfBirth = null;
        }    
    }
    
   
    @Override
    public Person clone(){
        Person clone = new Person();
        
        clone.personId = this.personId;
        clone.firstName = this.firstName;
        clone.lastName = this.lastName;
        clone.mobile = this.mobile;
        clone.email = this.email;
        clone.address = this.address;
        clone.postalCode = this.postalCode;
        clone.city = this.city;
        clone.country = this.country;
        clone.isTeacher = this.isTeacher;
        clone.dateOfBirth = this.dateOfBirth;
        clone.isJuryMember = this.isJuryMember;
        
        return clone;        
    }
    
       public Person deepClone(){
        Person clone = new Person();
        
        clone.personId = this.personId;
        clone.firstName = this.firstName;
        clone.lastName = this.lastName;
        clone.mobile = this.mobile;
        clone.email = this.email;
        clone.address = this.address;
        clone.postalCode = this.postalCode;
        clone.city = this.city;
        clone.country = this.country;
        clone.isTeacher = this.isTeacher;
        clone.dateOfBirth = this.dateOfBirth;
        clone.isJuryMember = this.isJuryMember;
        
        return clone;        
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.firstName);
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.dateToString());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if(this.dateOfBirth != null && other.dateOfBirth != null){
            if (!Objects.equals(this.toString(), other.toString())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    
    @Override
    public int compareTo(Person p){
        if(this.dateOfBirth != null && p.dateOfBirth != null){
        return this.firstName.compareTo(p.firstName)+this.lastName.compareTo(p.lastName)+this.dateOfBirth.toString().compareTo(p.dateOfBirth.toString());
        }
        else{
            return this.firstName.compareTo(p.firstName)+this.lastName.compareTo(p.lastName);
        }
    }
    
    public String dateToString(){
        String date;
        if(this.getDateOfBirth() == null){
           date = "null";
       }
       else{
            date = this.getDateOfBirth().toString();
       }  
        return date;
    }
      
}
