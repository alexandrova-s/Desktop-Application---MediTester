package com.app.guiModels;

import com.app.model.Doctor;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

public class DoctorTableModel extends AbstractTableModel {

    private List<Doctor> db;

    private String[] colNames = {"Imię", "Nazwisko", "PWZ", "Numer telefonu"};

    public DoctorTableModel() { }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Doctor> db) {
        this.db = db;
    }

    @Override
    public int getRowCount(){
        int result = 0;
        synchronized(LOCK) {
            if(db != null) {
                result = db.size();
            }
        }
        return result;
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public Doctor getDoctorByRow(int row) {
        if(row == -1) {
            return new Doctor();
        }
        return db.get(row);
    }

    public Object getValueAt(int row, int col) {
        Doctor doctor = db.get(row);
        switch(col) {
            case 0:
                return doctor.getName();
            case 1:
                return doctor.getSurname();
            case 2:
                return doctor.getMedicalLicence();
            case 3:
                return doctor.getPhoneNumber();
        }
        return null;
    }

}


