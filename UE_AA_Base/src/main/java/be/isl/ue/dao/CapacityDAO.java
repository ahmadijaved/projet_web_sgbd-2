
package be.isl.ue.dao;

import be.isl.ue.entity.Capacity;
import be.isl.ue.entity.Person;
import be.isl.ue.entity.Section;
import be.isl.ue.entity.Ue;
import be.isl.ue.vm.CapacitySearchVM;
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

public class CapacityDAO extends DAO<Capacity> {

    public CapacityDAO() {
        super();
        super.setDeleteCommande("DELETE FROM capacity WHERE capacity_id = ?");
    }

    @Override
    public ArrayList<Capacity> load() {
        return this.load(null);
    }

    public ArrayList<Capacity> load(CapacitySearchVM s) {
        try {
            String sql;

            sql = "SELECT "
                    + "c.capacity_id, "
                    + "c.name AS cname, "
                    + "c.description AS cdescription, "
                    + "c.is_threshold_of_success, "
                    + "c.ue_id, "
                    + "u.ue_id, "
                    + "u.name AS uname, "
                    + "u.description AS udescription, "
                    + "u.num_of_periods, "
                    + "u.is_decisive, "
                    + "u.code, "
                    + "u.section_id, "
                    + "s.section_id, "
                    + "s.name AS sname, "
                    + "s.description AS sdescription, "
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
                    + "FROM capacity AS c "
                    + "INNER JOIN ue AS u ON "
                    + "c.ue_id = u.ue_id "
                    + "LEFT JOIN section AS s ON "
                    + "u.section_id = s.section_id "
                    + "LEFT JOIN person AS p ON "
                    + "s.person_id = p.person_id ";

            if (s != null) {
                String where = "WHERE 1=1 ";
                if (s.getName() != null && s.getName().length() > 0) {
                    where += "AND c.name LIKE ? ";
                }
                if (s.getUeName() != null && s.getUeName().length() > 0) {
                    where += "AND u.name LIKE ? ";
                }
                sql += where + "ORDER BY c.name;";
            }

            PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);

            if (s != null) {
                int paramNumber = 0;
                if (s.getName() != null && s.getName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getName() + "%");
                }
                if (s.getUeName() != null && s.getUeName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getUeName() + "%");
                }
            }

            ResultSet rs = pStmt.executeQuery();

            super.getList().clear();
            while (rs.next()) {
                Person pers = new Person(
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

                Section sec = new Section(
                        rs.getInt("section_id"),
                        rs.getString("sname"),
                        rs.getString("sdescription"),
                        pers);

                Ue ue = new Ue(
                        rs.getInt("ue_id"),
                        rs.getString("uname"),
                        rs.getString("udescription"),
                        rs.getInt("num_of_periods"),
                        rs.getBoolean("is_decisive"),
                        sec,
                        rs.getString("code"));

                super.getList().add(new Capacity(
                        rs.getInt("capacity_id"),
                        rs.getString("cname"),
                        rs.getString("cdescription"),
                        rs.getBoolean("is_threshold_of_success"),
                        ue));

                pStmt.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return super.getList();
        }
    }

    @Override
    public void save(Capacity c) {
        try {
            String sql;

            if (c != null) {
                if (c.getId() != null) {
                    sql = "UPDATE capacity SET "
                            + "name = ?, "
                            + "description = ?, "
                            + "is_threshold_of_success = ?, "
                            + "ue_id = ? "
                            + "WHERE capacity_id = ?";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, c.getName());
                    pStmt.setString(2, c.getDescription());
                    pStmt.setBoolean(3, c.getIsThresholdOfSuccess());
                    pStmt.setInt(4, c.getUe().getUeId());
                    pStmt.setInt(5, c.getCapacityId());
                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    sql = "INSERT INTO capacity ("
                            + "name, "
                            + "description, "
                            + "is_threshold_of_success, "
                            + "ue_id "
                            + ") VALUES ("
                            + "?, "
                            + "?, "
                            + "?, "
                            + "? "
                            + ")";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, c.getName());
                    pStmt.setString(2, c.getDescription());
                    pStmt.setBoolean(3, c.getIsThresholdOfSuccess());
                    pStmt.setInt(4, c.getUe().getUeId());
                    pStmt.executeUpdate();
                    pStmt.close();

                    sql = "SELECT currval('capacity_capacity_id_seq') AS id";

                    Statement stmt = super.getCDB().getConn().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        c.setId(rs.getInt("id"));
                    }
                    super.getList().add(c);
                    rs.close();
                    stmt.close();
                }
                Collections.sort(super.getList());
            }

        } catch (SQLException ex) {
            Logger.getLogger(UeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

