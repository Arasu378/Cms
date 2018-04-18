package com.cms.arasu.model;

import com.cms.arasu.dao.StaffDao;
import com.cms.arasu.entity.Staff;
import com.cms.arasu.entity.StaffPosition;
import com.cms.arasu.util.Utils;
import com.google.gson.Gson;
import hibernate.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class StaffModel implements StaffDao {
    private static EntityManager entityManager;
    private Logger Log = Logger.getLogger(StaffModel.class.getSimpleName());
    @Override
    public ObservableList<Staff> getStaffs() {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<Staff> staffs = entityManager.createQuery("from Staff ", Staff.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        staffs.stream().forEach(staffList:: add);
        return staffList;
    }

    @Override
    public Staff getStaff(Long id) {
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT * FROM staff WHERE id = :id");
        query.setParameter("id", id);
        Staff staff =  (Staff) query.getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return staff;
    }

    @Override
    public void saveStaff(Staff staff) {
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(staff);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateStaff(Staff staff) {
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Staff staff1 = entityManager.find(Staff.class, staff.getId());
        staff1.setFirstName(staff.getFirstName());
        staff1.setLastName(staff.getLastName());
        staff1.setEmail(staff.getEmail());
        staff1.setPassword(staff.getPassword());
        staff1.setVerified(staff.getVerified());
        staff1.setModifiedOn(Utils.getCurrentDate());
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void deleteStaff(Staff staff) {
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Staff staff1 = entityManager.find(Staff.class, staff.getId());
        entityManager.remove(staff1);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public boolean checkPassword(String email, String password) {
        entityManager = JPAUtil.getFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT * FROM staff WHERE email = :Email AND password = :Password");
        query.setParameter("Email", email);
        query.setParameter("Password", password);
        Object staff = query.getSingleResult();
        System.out.println("Password Check: "+ new Gson().toJson(staff));
        entityManager.getTransaction().commit();
        entityManager.close();
        if (staff != null) {
            return true;
        } else {
            System.out.println("Entity manager is null!");
        }

        return false;
    }

    @Override
    public Staff checkStaff(String email, String password) {

        entityManager = JPAUtil.getFactory().createEntityManager();
            Staff object = null;
            try {
                entityManager.getTransaction().begin();
                Query query = entityManager.createQuery("FROM Staff WHERE email = :EmailId AND password = :Password", Staff.class);
                query.setParameter("EmailId", email);
                query.setParameter("Password", password);
                object = (Staff)query.getSingleResult();
                entityManager.getTransaction().commit();
                entityManager.close();

            } catch (NoResultException e) {
                e.getCause();
            }
            return object;

    }

    @Override
    public Long insertStaff(Staff staff) {
        Long id = insertData(staff);
        return id;
    }

    @Override
    public Long insertStaffPosition(StaffPosition staffPosition) {
       Long id = insertData(staffPosition);
       if (entityManager != null){
           entityManager.close();
       }
        return id;
    }

    @Override
    public Staff changePassword(String email, String password) {
        Staff staff = null;
        try {
            entityManager = JPAUtil.getFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM Staff WHERE email = :EmailId", Staff.class);
            query.setParameter("EmailId", email);
            staff = (Staff)query.getSingleResult();
            entityManager.getTransaction().commit();
            if (staff != null) {
                staff.setPassword(password);
                entityManager.getTransaction().begin();
                entityManager.persist(staff);
                entityManager.getTransaction().commit();
                entityManager.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    private Long insertData(Object object) {
        Long id = 0L;
        try {
            entityManager = JPAUtil.getFactory().createEntityManager();
            entityManager.getTransaction().begin();
            id = (Long) entityManager.unwrap(Session.class).save(object);
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            e.getCause();
            Log.info("Data Constraint Exception! :: "+ e.getMessage());
        }


        return id;
    }
}
