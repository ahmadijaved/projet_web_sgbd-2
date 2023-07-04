
package be.isl.ue.entity;


import java.util.ArrayList;
import java.util.Objects;

public class Ue implements Entity, Comparable<Ue>{
    private Integer ueId;
    private String name;
    private String description;
    private Integer numOfPeriods;
    private Boolean isDecisive;
    private Section section;
    private String code;
    private ArrayList<Capacity> capacities;
    
    public Ue() {
    }
    
    public Ue(Integer ueId, String name, String description, Integer numOfPeriods, Boolean isDecisive, Section section, String code) {
        this.ueId = ueId;
        this.name = name;
        this.description = description;
        this.numOfPeriods = numOfPeriods;
        this.isDecisive = isDecisive;
        this.section = section;
        this.code = code;
    }
    
    @Override
    public Integer getId(){
        return ueId;
    }
    
    @Override
    public void setId(Integer id){
        this.ueId = id;
    }

    public Integer getUeId() {
        return ueId;
    }

    public void setUeId(Integer ueId) {
        this.ueId = ueId;
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

    public Integer getNumOfPeriods() {
        return numOfPeriods;
    }

    public void setNumOfPeriods(Integer numOfPeriods) {
        this.numOfPeriods = numOfPeriods;
    }

    public Boolean getIsDecisive() {
        return isDecisive;
    }

    public void setIsDecisive(Boolean isDecisive) {
        this.isDecisive = isDecisive;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Capacity> getCapacities() {
        return capacities;
    }

    public void setCapacities(ArrayList<Capacity> capacities) {
        this.capacities = capacities;
    }

    public Ue clone(){
        Ue clone = new Ue();
        
        clone.ueId = this.ueId;
        clone.name = this.name;
        clone.description = this.description;
        clone.numOfPeriods = this.numOfPeriods;
        clone.isDecisive = this.isDecisive;
        clone.section = this.section;
        clone.code = this.code;
        
        return clone;
    }
    
    public Ue deepClone(){
        Ue clone = new Ue();
        
        clone.ueId = this.ueId;
        clone.name = this.name;
        clone.description = this.description;
        clone.numOfPeriods = this.numOfPeriods;
        clone.isDecisive = this.isDecisive;
        clone.section = this.section.deepClone();
        clone.code = this.code;
        
        return clone;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
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
        final Ue other = (Ue) obj;
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
        return name + " " + section.toString();
    }
 
    @Override
    public int compareTo(Ue o){
        return this.name.compareTo(o.name);
    }    
}
