
package be.isl.ue.dao;

import be.isl.ue.entity.Person;
import be.isl.ue.entity.Section;
import be.isl.ue.vm.SectionSearchVM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ahmadi
 */

public class SectionDAO extends DAO<Section> {

    public SectionDAO() {
        super();
        super.setDeleteCommande("DELETE FROM section WHERE section_id = ?");
    }

    @Override
    public ArrayList<Section> load() {
        return this.load(null);
    }

    public ArrayList<Section> load(SectionSearchVM s) {
        try {
            String sql;

            sql = "SELECT "
                    + "s.section_id, "
                    + "s.name, "
                    + "s.description, "
                    + "s.person_id, "
                    + "p.person_id, "
                    + "p.last_name, "
                    + "p.first_name, "
                    + "p.date_of_birth, "
                    + "p.address, "
                    + "p.postal_code, "
                    + "p.city, "
                    + "p.country, "
                    + "p.mobile, "
                    + "p.email, "
                    + "p.is_teacher, "
                    + "p.is_jury_member "
                    + "FROM section AS s "
                    + "INNER JOIN person AS p ON "
                    + "s.person_id = p.person_id ";

            if (s != null) {
                String where;
                where = "WHERE 1=1 ";
                if (s.getName() != null && s.getName().length() > 0) {
                    where += "AND s.name LIKE ? ";
                }
                if (s.getPersonLastName() != null && s.getPersonLastName().length() > 0) {
                    where += " AND p.last_name LIKE ? ";
                }
                sql += where + "ORDER BY s.name;";
            }

            PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
            if (s != null) {
                int paramNumber = 0;
                if (s.getName() != null && s.getName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getName() + "%");
                }
                if (s.getPersonLastName() != null && s.getPersonLastName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getPersonLastName() + "%");
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

                super.getList().add(new Section(
                        rs.getInt("section_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        p));
            }

            pStmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return super.getList();
        }
    }

    @Override
    public void save(Section s) {
        try {
            String sql;

            if (s != null) {
                if (s.getId() != null) {
                    sql = "UPDATE section SET "
                            + "name = ?, "
                            + "description = ?, "
                            + "person_id = ? "
                            + "WHERE section_id = ?";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, s.getName());
                    pStmt.setString(2, s.getDescription());
                    pStmt.setInt(3, s.getPerson().getId());
                    pStmt.setInt(4, s.getId());
                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    sql = "INSERT INTO section ("
                            + "name, "
                            + "description, "
                            + "person_id "
                            + ") VALUES ("
                            + " ?, "
                            + " ?, "
                            + " ? "
                            + ")";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, s.getName());
                    pStmt.setString(2, s.getDescription());
                    pStmt.setInt(3, s.getPerson().getId());
                    pStmt.executeUpdate();
                    pStmt.close();

                    sql = "SELECT currval('section_section_id_seq') AS id";

                    Statement stmt = super.getCDB().getConn().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        s.setId(rs.getInt("id"));
                    }
                    super.getList().add(s);
                    rs.close();
                    stmt.close();
                }
                Collections.sort(super.getList());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
