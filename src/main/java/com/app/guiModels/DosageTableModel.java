package com.app.guiModels;

import com.app.model.Doctor;
import com.app.model.Dose;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLOutput;
import java.util.List;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

public class DosageTableModel  extends AbstractTableModel {

    private List<Dose> db;

    private String[] colNames = {"Lekarz zalecający", "Dawka", "Data podania", "Pacjent"};

    public DosageTableModel() { }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Dose> db) {
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

    public Dose getDoseByRow(int row) {
        if(row == -1) {
            return new Dose();
        }
        return db.get(row);
    }

    public Object getValueAt(int row, int col) {
        Dose dose = db.get(row);
        MyService myService = new MyServiceImpl();
        switch(col) {
            case 0:
                Doctor d = myService.findByIdDoctor(dose.getPatientId());
                if (d == null){
                    return "-- Lekarza usunięto --";
                } else {
                    String ns0 = d.getName() + " " + d.getSurname();
                    return ns0;
                }
            case 1:
                return dose.getDoseValue();
            case 2:
                return dose.getDateDose();
            case 3:
                Patient p = myService.findByIdPatient(dose.getDoctorId());
                if (p == null) {
                    return "-- Pacjenta usunięto --";
                } else {
                    String ns3 = p.getName()+" "+ p.getSurname();
                    return ns3;
                }
        }
        return null;
    }

}



