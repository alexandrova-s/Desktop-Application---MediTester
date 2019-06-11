
package com.app.Listeners;
import com.app.model.Doctor;
import java.util.EventListener;

public interface DoctorTableSelectionListener extends EventListener {
    public void formListSelectionOccurred(Doctor d);
}
