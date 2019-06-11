package com.app.Listeners;

import com.app.model.Patient;

import java.util.EventListener;

public interface PatientTableSelectionListener extends EventListener {
    public void formListSelectionOccurred(Patient p);
}
