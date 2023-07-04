
package be.isl.ue.dao;


import be.isl.ue.entity.Entity;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmadi
 */

public abstract class DAO<T extends Entity> {

    private ArrayList<T> entityList = new ArrayList();
    private ConnectDB cDB;
    private String deleteCommande;

    public DAO() {
        cDB = new ConnectDB();
    }

    public ArrayList<T> getList() {
        return entityList;
    }

    public ConnectDB getCDB() {
        return cDB;
    }

    public T getById(Integer id) {
        for (T entity : entityList) {
            if (Objects.equals(entity.getId(), id)) {
                return entity;
            }
        }
        return null;
    }

    public void setDeleteCommande(String deleteCommande) {
        this.deleteCommande = deleteCommande;
    }

    public void delete(T entity) {
        if (entity != null && entity.getId() != null) {
            try {
                PreparedStatement pStmt = getCDB().getConn().prepareStatement(deleteCommande);
                pStmt.setInt(1, entity.getId());
                pStmt.executeUpdate();
                pStmt.close();
                entityList.remove(entity);
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(Integer id) {
        if (id != null) {
            try {
                PreparedStatement pStmt = getCDB().getConn().prepareStatement(deleteCommande);
                pStmt.setInt(1, id);
                pStmt.executeUpdate();
                pStmt.close();
                for (T entity : entityList) {
                    if (Objects.equals(entity.getId(), id)) {
                        entityList.remove(entity);
                        break;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public abstract ArrayList<T> load();

    public abstract void save(T e);
}
