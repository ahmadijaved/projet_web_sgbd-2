/**
 *
 * @author ahmadi
 */

package be.isl.ue.entity;

import java.util.Objects;

public class Capacity implements Entity, Comparable<Capacity>{

    private Integer capacityId;
    private String name;
    private String description;
    private Boolean isThresholdOfSuccess;
    private Ue ue;
    
    public Capacity() {
    }
    
    public Capacity(Integer capacityId, String name, String description, Boolean isThresholdOfSuccess, Ue ue) {
        this.capacityId = capacityId;
        this.name = name;
        this.description = description;
        this.isThresholdOfSuccess = isThresholdOfSuccess;
        this.ue = ue;
    }

    @Override
    public Integer getId(){
        return capacityId;
    }
    
    @Override
    public void setId(Integer id) {
        this.capacityId = id;
    }
    
    public Integer getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Integer capacityId) {
        this.capacityId = capacityId;
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

    public Boolean getIsThresholdOfSuccess() {
        return isThresholdOfSuccess;
    }

    public void setIsThresholdOfSuccess(Boolean isThresholdOfSuccess) {
        this.isThresholdOfSuccess = isThresholdOfSuccess;
    }

    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }

    public Capacity clone(){
        Capacity clone = new Capacity();
        
        clone.capacityId = this.capacityId;
        clone.name = this.name;
        clone.description = this.description;
        clone.isThresholdOfSuccess = this.isThresholdOfSuccess;
        clone.ue = this.ue;
        
        return clone;
    }
    
    public Capacity deepClone(){
        Capacity clone = new Capacity();
        
        clone.capacityId = this.capacityId;
        clone.name = this.name;
        clone.description = this.description;
        clone.isThresholdOfSuccess = this.isThresholdOfSuccess;
        clone.ue = this.ue.deepClone();
        
        return clone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.ue.getName());
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
        final Capacity other = (Capacity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ue.getName(), other.ue.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " " + ue.getName() + " " + description;
    }

    @Override
    public int compareTo(Capacity o){
        return this.name.compareTo(o.name) + this.ue.getName().compareTo(o.ue.getName());
    }
   
}
