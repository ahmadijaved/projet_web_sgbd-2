
package be.isl.ue.dao;

import be.isl.ue.entity.OrganizedUe;
import be.isl.ue.entity.Person;
import be.isl.ue.entity.Section;
import be.isl.ue.entity.Ue;
import be.isl.ue.vm.OrganizedUeSearchVM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmadi
 */

public class OrganizedUeDAO extends DAO<OrganizedUe> {

    public OrganizedUeDAO() {
        super();
        super.setDeleteCommande("DELETE FROM organized_ue WHERE organized_ue_id = ? ");
    }

    @Override
    public ArrayList<OrganizedUe> load() {
        return this.load(null);
    }

    public ArrayList<OrganizedUe> load(OrganizedUeSearchVM s) {
        try {
            String sql;

            sql = "SELECT "
                    + "o.organized_ue_id, "
                    + "o.start_date, "
                    + "o.end_date, "
                    + "o.name AS oname, "
                    + "o.level_id, "
                    + "o.ue_id, "
                    + "u.ue_id, "
                    + "u.name AS uname, "
                    + "u.description AS udescription, "
                    + "u.num_of_periods, "
                    + "u.is_decisive, "
                    + "u.code, "
                    + "u.section_id, "
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
                    + "FROM organized_ue AS o "
                    + "INNER JOIN ue AS u ON "
                    + " o.ue_id = u.ue_id "
                    + "LEFT JOIN section AS s ON "
                    + "u.section_id = s.section_id "
                    + "LEFT JOIN person AS p ON "
                    + "s.person_id = p.person_id ";

            if (s != null) {
                String where = "WHERE 1=1 ";
                if (s.getName() != null && s.getName().length() > 0) {
                    where += "AND o.name LIKE ? ";
                }
                if (s.getStartDateBegin() != null && s.getStartDateBegin().length() > 0 && s.getStartDateEnd() != null && s.getStartDateEnd().length() > 0) {
                    where += "AND o.start_date BETWEEN ? AND ? ";
                }
                sql += where + "ORDER BY o.name;";
            }

            PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);

            if (s != null) {
                int paramNumber = 0;
                if (s.getName() != null && s.getName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getName() + "%");
                }
                if (s.getStartDateBegin() != null && s.getStartDateBegin().length() > 0 && s.getStartDateEnd() != null && s.getStartDateEnd().length() > 0) {
                    paramNumber++;
                    pStmt.setDate(paramNumber, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(s.getStartDateBegin()).getTime()));
                    paramNumber++;
                    pStmt.setDate(paramNumber, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(s.getStartDateEnd()).getTime()));
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

                super.getList().add(new OrganizedUe(
                        rs.getInt("organized_ue_id"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("level_id"),
                        ue,
                        rs.getString("oname")));

                pStmt.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrganizedUeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return super.getList();
        }
    }

    @Override
    public void save(OrganizedUe o) {
        try {
            String sql;

            if (o != null) {
                if (o.getId() != null) {
                    sql = "UPDATE organized_ue SET "
                            + "start_date = ?, "
                            + "end_date = ?, "
                            + "level_id = ?, "
                            + "ue_id = ? ,"
                            + "name = ? "
                            + "WHERE organized_ue_id = ?";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setDate(1, new java.sql.Date(o.getStartDate().getTime()));
                    pStmt.setDate(2, new java.sql.Date(o.getEndDate().getTime()));
                    pStmt.setInt(3, o.getLevelId());
                    pStmt.setInt(4, o.getUe().getId());
                    pStmt.setString(5, o.getName());
                    pStmt.setInt(6, o.getId());
                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    sql = "INSERT INTO organized_ue ("
                            + "start_date, "
                            + "end_date, "
                            + "level_id, "
                            + "ue_id, "
                            + "name "
                            + ") VALUES ("
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "? "
                            + ")";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setDate(1, new java.sql.Date(o.getStartDate().getTime()));
                    pStmt.setDate(2, new java.sql.Date(o.getEndDate().getTime()));
                    pStmt.setInt(3, o.getLevelId());
                    pStmt.setInt(4, o.getUe().getId());
                    pStmt.setString(5, o.getName());
                    pStmt.executeUpdate();
                    pStmt.close();

                    sql = "SELECT currval('organized_ue_organized_ue_id_seq') AS id";
                    Statement stmt = super.getCDB().getConn().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        o.setId(rs.getInt("id"));
                    }
                    super.getList().add(o);
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
