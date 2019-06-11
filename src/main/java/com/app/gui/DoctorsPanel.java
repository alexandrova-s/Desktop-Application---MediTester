package com.app.gui;


import com.app.Listeners.ButtonListener;
import com.app.Listeners.DoctorTableSelectionListener;
import com.app.guiModels.DoctorTableModel;
import com.app.model.Doctor;
import com.app.model.Patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class DoctorsPanel extends JPanel {

    private JButton closeBtn;
    private JButton delBtn;
    private JButton showBtn;
    private ButtonListener panelCloseBthListener;
    private ButtonListener panelDelBthListener;
    private ButtonListener panelShowBthListener;
    private DoctorTableModel doctorTable;
    private JTable table;
    private JPanel btnpanel;
    private DoctorTableSelectionListener doctorTableSelectionListener;

    private boolean userMode;

    public DoctorsPanel(boolean userMode) {

        closeBtn = new JButton("Zamknij");
        closeBtn.addActionListener(e -> {
            panelCloseBthListener.buttonEventOccurred();
        });

        showBtn = new JButton("Pokaż pacjentów danego pacjenta");
        showBtn.addActionListener(e -> {
            panelShowBthListener.buttonEventOccurred();
        });


        doctorTable = new DoctorTableModel();
        table = new JTable(doctorTable);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
            doctorTableSelectionListener.formListSelectionOccurred(
                    doctorTable.getDoctorByRow(dlsm.getMinSelectionIndex())
            );
        });

        setLayout(new BorderLayout());

        btnpanel = new JPanel();
        btnpanel.setBackground(new Color(156, 223, 232));
        btnpanel.add(showBtn);

        if(userMode == false){
            delBtn = new JButton("Usuń");

            delBtn.addActionListener(e -> {
                panelDelBthListener.buttonEventOccurred();
            });
            btnpanel.add(delBtn);
        }
        btnpanel.add(closeBtn);
        add(new JScrollPane(table),BorderLayout.CENTER);
        add(btnpanel, BorderLayout.SOUTH);

        Border insideBorder = BorderFactory.createTitledBorder("TABELA LEKARZY");
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setBackground(new Color(156, 223, 232));
    }
    public void setData(List<Doctor> db) {
        doctorTable.setData(db);
    }

    public void setCloseBtnListener(ButtonListener panelButtonListener) {
        this.panelCloseBthListener = panelButtonListener;
    }

    public void setDelBtnListener(ButtonListener panelButtonListener) {
        this.panelDelBthListener = panelButtonListener;
    }

    public void setShowBtnListener(ButtonListener panelButtonListener) {
        this.panelShowBthListener = panelButtonListener;
    }

    public void setDoctorTableSelectionListener(DoctorTableSelectionListener tableSelectionListener) {
        this.doctorTableSelectionListener = tableSelectionListener;
    }
}