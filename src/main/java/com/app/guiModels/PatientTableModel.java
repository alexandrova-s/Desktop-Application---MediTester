package com.app.guiModels;

import com.app.model.Doctor;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

public class PatientTableModel extends AbstractTableModel {

    private List<Patient> db;

    private String[] colNames = {"Imię", "Nazwisko", "PESEL", "Placebo", "Data urodzenia", "Płeć", "Lekarz prowadzący"};

    public PatientTableModel() { }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Patient> db) {
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

    public Patient getPatientByRow(int row) {
        if(row == -1) {
            return new Patient();
        }
        return db.get(row);
    }

    public Object getValueAt(int row, int col) {
        Patient patient = db.get(row);
        switch(col) {
            case 0:
                return patient.getName();
            case 1:
                return patient.getSurname();
            case 2:
                return patient.getPesel();
            case 3:
                return patient.getPlacebo();
            case 4:
                return patient.getBirthday();
            case 5:
                return patient.getGender();
            case 6:
                MyService myService = new MyServiceImpl();
                Doctor d = myService.findByIdDoctor(patient.getDoctorId());
                if (d == null) {
                    return "-- Lekarza usunięto --";
                } else {
                    String ns = d.getName()+" "+ d.getSurname();
                    return ns;
                }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        switch(col) {
            case 3:
                return true;
            default:
                return false;
        }
    }

    public Class getColumnClass(int col) {
        if (col == 3)
            return Boolean.class;
        return String.class;
    }
}

