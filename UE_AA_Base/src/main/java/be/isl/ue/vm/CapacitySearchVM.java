
package be.isl.ue.vm;


public class CapacitySearchVM {

    private String name;
    private String ueName;

    public CapacitySearchVM() {
    }

    public CapacitySearchVM(String name, String sectionName) {
        this.name = name;
        this.ueName = sectionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUeName() {
        return ueName;
    }

    public void setUeName(String ueName) {
        this.ueName = ueName;
    }

}
