package com.app.Listeners;

import com.app.model.Effect;

import java.util.EventListener;

public interface EffectTableSelectionListener extends EventListener {
    public void formListSelectionOccurred(Effect e);
}
