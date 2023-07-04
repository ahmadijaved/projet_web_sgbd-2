
package be.isl.ue.ui;

import be.isl.ue.dao.PersonDAO;
import be.isl.ue.entity.Person;
import be.isl.ue.vm.PersonSearchVM;
import java.util.ArrayList;

public class MainPerson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        personTest();
    }

    public static void personTest() {

        PersonDAO pDAO = new PersonDAO();
        ArrayList<Person> persons;

        System.out.println("Search person");
        System.out.println("-------------");

        PersonSearchVM s = new PersonSearchVM();
        s.setEmail("Pierre");

        persons = pDAO.load(s);
        display(persons);

        System.out.println("Get all persons");
        System.out.println("---------------");
        // load
        persons = pDAO.load();
        // display
        display(persons);

        // Add a person
        // ------------
        System.out.println("Add a person");
        System.out.println("------------");
        String dateOfBirth = "1974-08-10";
        Person p = new Person(null, "Paul", "Durant", "0000000", "paul.durant@mail.com", "Rue Saint Laurent 33", "4000", "Liège", "Belgique", false, dateOfBirth, false);
        // modify date of birth
        dateOfBirth = "1974-08-11";
        p.setDateOfBirth(dateOfBirth);
        pDAO.save(p);
        // display
        persons = pDAO.load(s);
        display(persons);

        // Update a person
        // ---------------
        System.out.println("Update a person");
        System.out.println("---------------");
        s.setLastName("Durant");
        s.setFirstName("Paul");
        persons = pDAO.load(s);
        Person uP;
        uP = persons.get(0);
        int idPerson = uP.getId();
        uP.setFirstName("Pierre");
        // save
        pDAO.save(uP);

        // Delete a person
        System.out.println("Delete a person");
        System.out.println("---------------");
        Person p1;
        pDAO.load();
        p1 = pDAO.getById(idPerson);
        System.out.println("Ddelete : " + p1.toString());
        pDAO.delete(idPerson);
        // display
        persons = pDAO.load(s);
        display(persons);

    }

    public static void display(ArrayList<Person> personList) {
        System.out.println("***************");
        System.out.println("Person List:");
        System.out.println("***************");
        personList.forEach(p -> {
            System.out.println(p.toString() + " " + p.getPersonId());
        });
        System.out.println("");
    }
}
