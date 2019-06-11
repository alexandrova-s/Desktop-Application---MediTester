package com.app.gui;

import com.app.Listeners.ButtonListener;
import com.app.model.Doctor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormPanelD extends JPanel {

    private JLabel doctorNameLabel;
    private JLabel doctorSurnameLabel;
    private JLabel medicalLicenceLabel;
    private JLabel numberLabel;
    private JTextField doctorNameField;
    private JTextField doctorSurnameField;
    private JTextField medicalLicenceField;
    private JTextField numberField;

    // przyciski
    private JButton saveBtn;
    private JButton closeBtn;

    private ButtonListener formSaveBtnListener;
    private ButtonListener formCloseBtnListener;


    public FormPanelD() {

        // dane pacjeta
        doctorNameLabel = new JLabel("Imię lekarza: ");
        doctorNameLabel.setFont(new Font("Verdana", 1, 15));

        doctorSurnameLabel = new JLabel("Nazwisko lekarza: ");
        doctorSurnameLabel.setFont(new Font("Verdana", 1, 15));

        medicalLicenceLabel = new JLabel("PWZ: ");
        medicalLicenceLabel.setFont(new Font("Verdana", 1, 15));

        numberLabel = new JLabel("Numer telefonu: ");
        numberLabel.setFont(new Font("Verdana", 1, 15));

        doctorNameField = new JTextField(15);
        doctorSurnameField = new JTextField(15);
        medicalLicenceField = new JTextField(15);
        numberField = new JTextField(15);

        saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(e -> {
            formSaveBtnListener.buttonEventOccurred();
        });

        closeBtn = new JButton("Anuluj");
        closeBtn.addActionListener(e -> {
            formCloseBtnListener.buttonEventOccurred();
        });

        Border insideBorder = BorderFactory.createTitledBorder("Formularz pacjenta");
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

        setBackground(new Color(156, 223, 232));

        layoutComponents();

    }

    public void layoutComponents() {

//
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        /////////////////////// FIRST ROW ///////

        gc.gridy = 0;

        gc.weightx = 2;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 10, 10, 10);
        add(doctorNameLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doctorNameField, gc);

        /////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(doctorSurnameLabel, gc);
////
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doctorSurnameField, gc);
////
////		/////////////////////// NEXT ROW ///////
////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(medicalLicenceLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(medicalLicenceField, gc);
////
////		/////////////////////// NEXT ROW ///////
////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.2;
////
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(numberLabel, gc);
////
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(numberField, gc);
////
        //////////////////////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(saveBtn, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(closeBtn, gc);

    }
    public boolean checkDoctorData(){
        if( doctorNameField.getText() != null && doctorSurnameField.getText()!= null && medicalLicenceField.getText()!= null&& numberField.getText()!= null){
            return true;
        } else{
            return false;
        }
    }

    public void clearFormPanelD(){
        doctorNameField.setText("");
        doctorSurnameField.setText("");
        medicalLicenceField.setText("");
        numberField.setText("");
    }

    public Doctor getDoctorFromForm(){
        Doctor doctor = new Doctor();
        doctor.setName(doctorNameField.getText());
        doctor.setSurname(doctorSurnameField.getText());
        doctor.setMedicalLicence(medicalLicenceField.getText());
        doctor.setPhoneNumber(numberField.getText());
        return doctor;
    }

    public void setFormSaveBtnListener(ButtonListener listener) {
        this.formSaveBtnListener = listener;
    }

    public void setFormCloseBtnListener(ButtonListener listener) {
        this.formCloseBtnListener = listener;
    }

    public boolean checkPWZ(){
        String pwz = medicalLicenceField.getText();
        if (pwz.matches("[0-9]+") && pwz.length() == 7) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPhoneNr(){
        String phoneNumber = numberField.getText();
        if (phoneNumber.matches("[0-9]+") && phoneNumber.length() == 9) {
            return true;
        } else {
            return false;
        }
    }

}