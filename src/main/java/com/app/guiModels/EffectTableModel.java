package com.app.guiModels;

import com.app.model.Doctor;
import com.app.model.Effect;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

public class EffectTableModel extends AbstractTableModel {

    private List<Effect> db;

    private String[] colNames = {"Lekarz", "Opis obserwacji", "Data wpisu", "Pacjent"};

    public EffectTableModel() { }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Effect> db) {
        this.db = db;
    }

    @Override
    public int getRowCount(){
        int result = 0;
        synchronized(LOCK) {
            if (db != null) {
                result = db.size();
            }
        }
        return result;
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public Effect getEffectByRow(int row) {
        if(row == -1) {
            return new Effect();
        }
        return db.get(row);
    }

    public Object getValueAt(int row, int col) {
        Effect effect = db.get(row);
        MyService myService = new MyServiceImpl();
        switch(col) {
            case 0:
                Doctor d = myService.findByIdDoctor(effect.getPatientId());
                if (d == null){
                    return "-- Lekarza usunięto --";
                } else {
                    String ns0 = d.getName() + " " + d.getSurname();
                    return ns0;
                }
            case 1:
                return effect.getDescription();
            case 2:
                return effect.getDateEffect();
            case 3:
                Patient p = myService.findByIdPatient(effect.getDoctorId());
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



