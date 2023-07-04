
package be.isl.ue.dao;

import be.isl.ue.entity.Capacity;
import be.isl.ue.entity.Person;
import be.isl.ue.entity.Section;
import be.isl.ue.entity.Ue;
import be.isl.ue.vm.UeSearchVM;
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
 * @author ahmad
 */


public class UeDAO extends DAO<Ue> {

    public UeDAO() {
        super();
        super.setDeleteCommande("DELETE FROM ue WHERE ue_id = ? ");
    }

    @Override
    public ArrayList<Ue> load() {
        return this.load(null);
    }

    public ArrayList<Ue> load(UeSearchVM s) {
        try {
            String sql;

            sql = """                  
                    SELECT 
                        u.ue_id, 
                        u.name AS uname, 
                        u.description AS udescription, 
                        u.num_of_periods, 
                        u.is_decisive, 
                        u.section_id, 
                        u.code, 
                        s.name AS sname, 
                        s.description AS sdescription, 
                        s.person_id, 
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
                        p.is_jury_member, 
                        c.capacity_id, 
                        c.name AS cname, 
                        c.description AS cdescription, 
                        c.is_threshold_of_success, 
                        c.ue_id 
                    FROM ue AS u 
                    LEFT JOIN section AS s ON 
                        u.section_id = s.section_id 
                    LEFT JOIN person AS p ON 
                        s.person_id = p.person_id 
                    LEFT JOIN capacity AS c ON 
                        u.ue_id = c.ue_id ;
                    """;

            if (s != null) {
                String where;
                where = "WHERE 1=1 ";
                if (s.getName() != null && s.getName().length() > 0) {
                    where += "AND u.name LIKE ? ";
                }
                if (s.getDescription() != null && s.getDescription().length() > 0) {
                    where += "AND u.description LIKE ? ";
                }
                if (s.getSectionName() != null && s.getSectionName().length() > 0) {
                    where += "AND s.name LIKE ? ";
                }
                if (s.getCapacityname() != null && s.getCapacityname().length() > 0) {
                    where += "AND c.name LIKE ? ";
                }
                sql += where + "ORDER BY u.ue_id;";
            }

            PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);

            if (s != null) {
                int paramNumber = 0;
                if (s.getName() != null && s.getName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getName() + "%");
                }
                if (s.getDescription() != null && s.getDescription().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getDescription() + "%");
                }
                if (s.getSectionName() != null && s.getSectionName().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getSectionName() + "%");
                }
                if (s.getCapacityname() != null && s.getCapacityname().length() > 0) {
                    paramNumber++;
                    pStmt.setString(paramNumber, "%" + s.getCapacityname() + "%");
                }
            }

            ResultSet rs = pStmt.executeQuery();

            super.getList().clear();
            Boolean eof = !rs.next();
            while (!eof) {
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

                ArrayList<Capacity> capacities = new ArrayList();
                int oldUeId = rs.getInt("ue_id");
                while (!eof && rs.getInt("ue_id") == oldUeId) {
                    if (rs.getInt("capacity_id") != 0) {
                        Capacity capacity = new Capacity(rs.getInt("capacity_id"),
                                rs.getString("cname"),
                                rs.getString("cdescription"),
                                rs.getBoolean("is_threshold_of_success"),
                                ue);
                        capacities.add(capacity);
                    }
                    eof = !rs.next();
                }
                ue.setCapacities(capacities);
                super.getList().add(ue);
            }
            pStmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return super.getList();
        }
    }

    @Override
    public void save(Ue u) {
        try {
            String sql;

            if (u != null) {
                if (u.getId() != null) {
                    sql = "UPDATE ue SET "
                            + "name = ?, "
                            + "description = ?, "
                            + "num_of_periods = ?, "
                            + "is_decisive = ?, "
                            + "section_id = ?, "
                            + "code = ? "
                            + "WHERE ue_id = ?";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, u.getName());
                    pStmt.setString(2, u.getDescription());
                    pStmt.setInt(3, u.getNumOfPeriods());
                    pStmt.setBoolean(4, u.getIsDecisive());
                    pStmt.setInt(5, u.getSection().getSectionId());
                    pStmt.setString(6, u.getCode());
                    pStmt.setInt(7, u.getUeId());
                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    sql = "INSERT INTO ue ("
                            + "name, "
                            + "description, "
                            + "num_of_periods, "
                            + "is_decisive, "
                            + "section_id, "
                            + "code "
                            + ") VALUES ("
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "?, "
                            + "? "
                            + ")";

                    PreparedStatement pStmt = super.getCDB().getConn().prepareStatement(sql);
                    pStmt.setString(1, u.getName());
                    pStmt.setString(2, u.getDescription());
                    pStmt.setInt(3, u.getNumOfPeriods());
                    pStmt.setBoolean(4, u.getIsDecisive());
                    pStmt.setInt(5, u.getSection().getSectionId());
                    pStmt.setString(6, u.getCode());
                    pStmt.executeUpdate();
                    pStmt.close();

                    sql = "SELECT currval('ue_ue_id_seq') AS id";

                    Statement stmt = super.getCDB().getConn().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        u.setId(rs.getInt("id"));
                    }
                    super.getList().add(u);
                    rs.close();
                    stmt.close();
                }

                CapacityDAO capacityDAO = new CapacityDAO();
                if (u.getCapacities() != null) {
                    String listOfCapacities = "";
                    for (Capacity c : u.getCapacities()) {
                        capacityDAO.save(c);
                        listOfCapacities = listOfCapacities + c.getId() + ", ";
                    }
                    sql = "DELETE FROM capacity WHERE ue_id = " + u.getId();
                    if (listOfCapacities.length() > 0) {
                        listOfCapacities = " AND capacity_id NOT IN ("
                                + listOfCapacities.substring(0, listOfCapacities.length() - 2) + ")";
                    }
                    sql = sql + listOfCapacities;

                    Statement stmt = super.getCDB().getConn().createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                }
                Collections.sort(super.getList());
            }
        } catch (SQLException ex) {
            Logger.getLogger(UeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Ue ue) {
        CapacityDAO capacityDAO = new CapacityDAO();
        if (ue.getCapacities() != null) {
            for (Capacity c : ue.getCapacities()) {
                capacityDAO.delete(c.getCapacityId());
            }
        }

        this.delete(ue.getUeId());
    }
}
