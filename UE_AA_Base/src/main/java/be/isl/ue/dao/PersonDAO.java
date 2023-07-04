/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.isl.ue.dao;

import be.isl.ue.entity.Person;
import be.isl.ue.vm.PersonSearchVM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAO extends DAO<Person> {

    public PersonDAO() {
        super();
        super.setDeleteCommande("DELETE FROM person WHERE person_id = ?");
    }

    @Override
    public ArrayList<Person> load() {
        return this.load(null);
    }

    public ArrayList<Person> load(PersonSearchVM s) {
        try {
            String sql;

            sql = """
                    SELECT 
                        p.person_id, 
                        p.last_name, 
                        p.first_name, 
                        p.date_of_birth, 
                        p.address, 
                        p.postal_code, 
                        p.city, 
                        p.country, 
                        p.mobile, 
                        p.email, 
                        p.is_teacher, 
                        p.is_jury_member 
                    FROM person AS p
                    """;
            System.out.println("SQL - " + sql);
            if (s != null) {
                String where;
                where = "WHERE 1=1 ";
                if (s.getLastName() != null && s.getLastName().length() > 0) {
                    where += "AND last_name LIKE ? ";
                }
                if (s.getFirstName() != null && s.getFirstName().length() > 0) {
                    where += "AND first_name LIKE ? ";
                }
                if (s.getCity() != null && s.getCity().length() > 0) {
                    where += "AND city LIKE ? ";
                }
                if (s.getDateOfBirth() != null && s.getDateOfBirth().length() > 0) {
                    where += "AND date_of_birth = ? ";
                }
                if (s.getEmail() != null && s.getEmail().length() > 0) {
                    where += "AND email LIKE ? ";
                }
                sql += where + "ORDER BY last_name;";
            }

            PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);

            if (s != null) {
                int paramNumber = 0;
                if (s.getLastName() != null && s.getLastName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getLastName() + "%");
                }
                if (s.getFirstName() != null && s.getFirstName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getFirstName() + "%");
                }
                if (s.getCity() != null && s.getCity().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getCity() + "%");
                }
                if (s.getDateOfBirth() != null && s.getDateOfBirth().length() > 0) {
                    paramNumber++;
                    pStmt.setDate(paramNumber, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(s.getDateOfBirth()).getTime()));
                }
                if (s.getEmail() != null && s.getEmail().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getEmail() + "%");
                }
            }

            ResultSet rs = pStmt.executeQuery();

            super.getList().clear();
            while (rs.next()) {
                Person p = new Person(
                        rs.getInt("person_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("mobile"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("postal_code"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getBoolean("is_teacher"),
                        rs.getDate("date_of_birth"),
                        rs.getBoolean("is_jury_member"));

                super.getList().add(p);
            }
            pStmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return super.getList();
        }

    }

    @Override
    public void save(Person p) {
        try {
            String sql;

            if (p != null) {
                if (p.getId() != null) {
                    sql = "UPDATE person SET "
                            + "first_name = ?, "
                            + "last_name = ?, "
                            + "mobile = ?, "
                            + "email = ?, "
                            + "address = ?, "
                            + "postal_code = ?, "
                            + "city = ?, "
                            + "country = ?, "
                            + "is_teacher = ?, "
                            + "date_of_birth = ?, "
                            + "is_jury_member = ? "
                            + "WHERE person_id = ?";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, p.getFirstName());
                    pStmt.setString(2, p.getLastName());
                    pStmt.setString(3, p.getMobile());
                    pStmt.setString(4, p.getEmail());
                    pStmt.setString(5, p.getAddress());
                    pStmt.setString(6, p.getPostalCode());
                    pStmt.setString(7, p.getCity());
                    pStmt.setString(8, p.getCountry());
                    pStmt.setBoolean(9, p.getIsTeacher());
                    pStmt.setDate(10, new java.sql.Date(p.getDateOfBirth().getTime()));
                    pStmt.setBoolean(11, p.getIsJuryMember());
                    pStmt.setInt(12, p.getPersonId());
                    pStmt.executeUpdate();

                    pStmt.close();
                } else {
                    sql = "INSERT INTO person ("
                            + "first_name, "
                            + "last_name, "
                            + "mobile, "
                            + "email, "
                            + "address, "
                            + "postal_code, "
                            + "city, "
                            + "country, "
                            + "is_teacher, "
                            + "date_of_birth, "
                            + "is_jury_member "
                            + ") VALUES ("
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "? "
                            + ")";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, p.getFirstName());
                    pStmt.setString(2, p.getLastName());
                    pStmt.setString(3, p.getMobile());
                    pStmt.setString(4, p.getEmail());
                    pStmt.setString(5, p.getAddress());
                    pStmt.setString(6, p.getPostalCode());
                    pStmt.setString(7, p.getCity());
                    pStmt.setString(8, p.getCountry());
                    pStmt.setBoolean(9, p.getIsTeacher());
                    pStmt.setDate(10, new java.sql.Date(p.getDateOfBirth().getTime()));
                    pStmt.setBoolean(11, p.getIsJuryMember());

                    pStmt.executeUpdate();
                    pStmt.close();

                    sql = "SELECT currval('person_person_id_seq') AS id";
                    Statement stmt = super.getCDB().getConn().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        p.setId(rs.getInt("id"));
                    }
                    super.getList().add(p);
                    rs.close();
                    stmt.close();
                }

                Collections.sort(super.getList());
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
