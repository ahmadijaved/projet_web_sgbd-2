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

public class OrganizedUe implements Entity, Comparable<OrganizedUe> {

    private Integer organizedUeId;
    private Date startDate;
    private Date endDate;
    private Integer levelId;
    private Ue ue;
    private String name;

    public OrganizedUe() {
    }

    public OrganizedUe(Integer organizedUeId, Date startDate, Date endDate, Integer levelId, Ue ue, String name) {
        this.organizedUeId = organizedUeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.levelId = levelId;
        this.ue = ue;
        this.name = name;
    }

    public OrganizedUe(Integer organizedUeId, String startDate, String endDate, Integer levelId, Ue ue, String name) {
        this.organizedUeId = organizedUeId;
        this.startDate = null;
        this.endDate = null;
        this.levelId = levelId;
        this.ue = ue;
        this.name = name;

        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    @Override
    public Integer getId() {
        return organizedUeId;
    }

    @Override
    public void setId(Integer id) {
        this.organizedUeId = id;
    }

    public Integer getOrganizedUeId() {
        return organizedUeId;
    }

    public void setOrganizedUeId(Integer organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = dateFormat.parse(date);
            this.startDate = convertedDate;
        } catch (ParseException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            this.startDate = null;
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = dateFormat.parse(date);
            this.endDate = convertedDate;
        } catch (ParseException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            this.endDate = null;
        }
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizedUe clone() {
        OrganizedUe clone = new OrganizedUe();

        clone.organizedUeId = this.organizedUeId;
        clone.name = this.name;
        clone.levelId = this.levelId;
        clone.ue = this.ue;
        clone.startDate = this.startDate;
        clone.endDate = this.endDate;

        return clone;
    }

    public OrganizedUe deepClone() {
        OrganizedUe clone = new OrganizedUe();

        clone.organizedUeId = this.organizedUeId;
        clone.name = this.name;
        clone.levelId = this.levelId;
        clone.ue = this.ue.deepClone();
        clone.startDate = this.startDate;
        clone.endDate = this.endDate;

        return clone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.organizedUeId);
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final OrganizedUe other = (OrganizedUe) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.organizedUeId, other.organizedUeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return organizedUeId + " " + name;
    }

    @Override
    public int compareTo(OrganizedUe o) {
        return this.name.compareTo(o.name) + this.organizedUeId.compareTo(o.organizedUeId);
    }

}
