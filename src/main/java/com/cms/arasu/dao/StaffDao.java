package com.cms.arasu.dao;

import com.cms.arasu.entity.Staff;
import com.cms.arasu.entity.StaffPosition;
import javafx.collections.ObservableList;

public interface StaffDao {
    ObservableList<Staff> getStaffs();
    Staff getStaff(Long Id);
    void saveStaff(Staff staff);
    void updateStaff(Staff staff);
    void deleteStaff(Staff staff);
    boolean checkPassword(String email, String password);
    Staff checkStaff(String email, String password);
    Long insertStaff(Staff staff);
    Long insertStaffPosition(StaffPosition staffPosition);
    Staff changePassword(String email, String password);


}
