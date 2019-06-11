package com.app.gui;


import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.app.Listeners.ButtonListener;
import com.app.model.Doctor;
import com.app.model.Patient;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class FormPanel extends JPanel {

    // dane pacjenta
    private JLabel patientNameLabel;
    private JLabel patientSurnameLabel;
    private JLabel patientPeselLabel;
    private JLabel genderLabel;
    private JTextField patientNameField;
    private JTextField patientSurnameField;
    private JTextField patientPeselField;
    private JDatePickerImpl birthdayPicker;

    private JRadioButton femaleRadio;
    private JRadioButton maleRadio;
    private ButtonGroup genderGroup;

    // dane lekarza
    private JLabel doctorNameLabel;
    private JComboBox doctorBox;

    // przyciski
    private JButton saveBtn;
    private JButton delBtn;

    private ButtonListener formSaveBtnListener;
    private ButtonListener formDelBtnListener;

    private JLabel dateLabel;
    private boolean is_placebo;

    public boolean isIs_placebo() {
        return is_placebo;
    }

    public void setIs_placebo(boolean is_placebo) {
        this.is_placebo = is_placebo;
    }

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatDateTime = today.format(formatter);


    public FormPanel() {

        // dane pacjenta
        patientNameLabel = new JLabel("Imię pacjenta: ");
        patientNameLabel.setFont(new Font("Verdana", 1, 15));

        patientSurnameLabel = new JLabel("Nazwisko pacjenta: ");
        patientSurnameLabel.setFont(new Font("Verdana", 1, 15));

        patientPeselLabel = new JLabel("PESEL: ");
        patientPeselLabel.setFont(new Font("Verdana", 1, 15));

        patientNameField = new JTextField(15);
        patientSurnameField = new JTextField(15);
        patientPeselField = new JTextField(15);
        genderLabel = new JLabel("Płeć: ");
        genderLabel.setFont(new Font("Verdana", 1, 15));

        femaleRadio = new JRadioButton("Kobieta");
        maleRadio = new JRadioButton("Mężczyzna");
        genderGroup = new ButtonGroup();
        femaleRadio.setBackground(new Color(156, 223, 232));
        maleRadio.setBackground(new Color(156, 223, 232));

        dateLabel = new JLabel("Data urodzenia: ");
        dateLabel.setFont(new Font("Verdana", 1, 15));

        UtilDateModel model  = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        birthdayPicker = new JDatePickerImpl(datePanel, new FormPanel.DateLabelFormatter());


        // dane doktora
        doctorNameLabel = new JLabel("Lekarz prowadzący: ");
        doctorNameLabel.setFont(new Font("Verdana", 1, 15));

        doctorBox = new JComboBox();

        genderGroup.add(femaleRadio);
        genderGroup.add(maleRadio);

        patientNameLabel.setLabelFor(patientNameField);

        saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(e -> {
            formSaveBtnListener.buttonEventOccurred();
        });
        delBtn = new JButton("Anuluj");
        delBtn.addActionListener(e -> {
            formDelBtnListener.buttonEventOccurred();
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
        add(patientNameLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(patientNameField, gc);

        /////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(patientSurnameLabel, gc);
////
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(patientSurnameField, gc);
////
////		/////////////////////// NEXT ROW ///////
////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(patientPeselLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(patientPeselField, gc);
////
////		/////////////////////// NEXT ROW ///////
////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.2;
////
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(genderLabel, gc);
////
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(femaleRadio, gc);
////
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(maleRadio, gc);
        /////////////////////// NEXT ROW ///////
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(dateLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(birthdayPicker, gc);
             /////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(doctorNameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doctorBox, gc);
////
/////////////////////////// NEXT ROW ///////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(saveBtn, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(delBtn, gc);

    }

    public boolean checkPatientData(){
        String gender;
        if (femaleRadio.isSelected()) {
            gender = "kobieta";
        } else if (maleRadio.isSelected()) {
            gender = "mężczyzna";
        } else {
            gender = null;
        }
           if((Doctor)doctorBox.getSelectedItem() != null && gender!=null && patientNameField.getText() != null && patientPeselField.getText() != null && patientSurnameField.getText() != null && birthdayPicker.getJFormattedTextField().getText() != null){
               return true;
           } else {
               return false;
           }
    }

    public String getPeselFromForm(){
        return patientPeselField.getText();
    }
    public void setDoctorsBox(List<Doctor> doctors){
        DefaultComboBoxModel phyModel = new DefaultComboBoxModel();
        phyModel.addAll(doctors);
        doctorBox.setModel(phyModel);
        doctorBox.setRenderer( new DoctorRenderer() );
        doctorBox.setEditable(true);
    }

    public Patient getPatienFromForm() {
        String gender;
        if (femaleRadio.isSelected()) {
            gender = "kobieta";
        } else if (maleRadio.isSelected()) {
            gender = "mężczyzna";
        } else {
            gender = null;
        }
        Patient patient = new Patient();
        patient.setName(patientNameField.getText());
        patient.setSurname(patientSurnameField.getText());
        patient.setGender(gender);
        patient.setPlacebo(is_placebo);
        patient.setPesel(patientPeselField.getText());
        patient.setBirthday(birthdayPicker.getJFormattedTextField().getText());
        Doctor d = (Doctor)doctorBox.getSelectedItem();
        patient.setDoctorId(d.getId());
        return patient;
    }

    public void clearFormPanel(){
        patientNameField.setText("");
        patientSurnameField.setText("");
        genderGroup.clearSelection();
        patientPeselField.setText("");
        birthdayPicker.getJFormattedTextField().setText(formatDateTime);
        doctorBox.setSelectedIndex(-1);
    }

    class DoctorRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                Doctor d = (Doctor) value;
                setText(d.getName() + " " + d.getSurname());
            }
            if (index == -1) {
                Doctor d = (Doctor) value;
                setText("" + d.getId());
            }
            return this;
        }
    }

    public void setFormSaveBtnListener(ButtonListener listener) {
        this.formSaveBtnListener = listener;
    }

    public void setFormDelBtnListener(ButtonListener listener) {
        this.formDelBtnListener = listener;
    }

    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }

}
