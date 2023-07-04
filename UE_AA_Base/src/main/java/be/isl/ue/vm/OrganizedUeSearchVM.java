
package be.isl.ue.vm;


public class OrganizedUeSearchVM {

    private String name;
    private String startDateBegin;
    private String startDateEnd;

    public OrganizedUeSearchVM() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDateBegin() {
        return startDateBegin;
    }

    public void setStartDateBegin(String startDateBegin) {
        this.startDateBegin = startDateBegin;
    }

    public String getStartDateEnd() {
        return startDateEnd;
    }

    public void setStartDateEnd(String startDateEnd) {
        this.startDateEnd = startDateEnd;
    }

    public OrganizedUeSearchVM(String name, String startDateBegin, String startDateEnd) {
        this.name = name;
        this.startDateBegin = startDateBegin;
        this.startDateEnd = startDateEnd;
    }

}
