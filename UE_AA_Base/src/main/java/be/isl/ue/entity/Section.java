
package be.isl.ue.entity;


import java.util.Objects;

public class Section implements Entity, Comparable<Section>{
    private Integer sectionId;
    private String name;
    private String description;
    private Person person;
    
    public Section() {
    }

    public Section(Integer sectionId, String name, String description, Person person) {
        this.sectionId = sectionId;
        this.name = name;
        this.description = description;
        this.person = person;
    }
    
    @Override
    public Integer getId(){
        return sectionId;
    }
    
    @Override
    public void setId(Integer id){
        this.sectionId = id;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    
    
    @Override
    public Section clone(){
        Section clone = new Section();
        
        clone.sectionId = this.sectionId;
        clone.name = this.name;
        clone.description = this.description;
        clone.person = this.person;
        
        return clone;        
    }
    
    public Section deepClone(){
        Section clone = new Section();
        
        clone.sectionId = this.sectionId;
        clone.name = this.name;
        clone.description = this.description;
        clone.person = this.person.deepClone();
        
        return clone;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
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
        final Section other = (Section) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int compareTo(Section s){
        return this.name.compareTo(s.name) + this.description.compareTo(s.description);
    }
    
}

