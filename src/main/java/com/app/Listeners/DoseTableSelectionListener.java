package com.app.Listeners;

import com.app.model.Dose;

import java.util.EventListener;

public interface DoseTableSelectionListener extends EventListener {
    public void formListSelectionOccurred(Dose d);
}
