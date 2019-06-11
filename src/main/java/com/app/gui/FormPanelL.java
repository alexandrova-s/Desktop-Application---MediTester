package com.app.gui;

import com.app.Listeners.ButtonListener;
import com.app.model.Doctor;
import com.app.model.Dose;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


public class FormPanelL extends JPanel {

    private JLabel titleLabel;

    private JLabel dateLabel;
    private JLabel doseLabel;
    private JTextField doseField;

    private JButton addDoseB;
    private JButton closeB;
    private ButtonListener formSaveBtnListener;
    private ButtonListener formDelBtnListener;

    // dane lekarza
    private JLabel doctorNameLabel;
    private JComboBox doctorBox;

    private JDatePickerImpl doseDatePicker;

    private Integer patIdL;

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatDateTime = today.format(formatter);

    public FormPanelL() {

        titleLabel = new JLabel("Dodaj dawkę");
        titleLabel.setFont(new Font("Verdana", 1, 20));

        dateLabel = new JLabel("Data: ");
        dateLabel.setFont(new Font("Verdana", 1, 15));

        UtilDateModel model  = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        doseDatePicker = new JDatePickerImpl(datePanel, new FormPanelL.DateLabelFormatter());

        doseLabel = new JLabel("Dawka: ");
        doseLabel.setFont(new Font("Verdana", 1, 15));
        doseField = new JTextField(15);

        addDoseB = new JButton("Dodaj dawkę");
        addDoseB.addActionListener(e -> {
            formSaveBtnListener.buttonEventOccurred();
        });

        closeB = new JButton("Anuluj");
        closeB.addActionListener(e -> {
            formDelBtnListener.buttonEventOccurred();
        });

        // dane doktora
        doctorNameLabel = new JLabel("Lekarz zlecający: ");
        doctorNameLabel.setFont(new Font("Verdana", 1, 15));
        doctorBox = new JComboBox();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        add(addDoseB);
        add(closeB);
        setBackground(new Color(156, 223, 232));
        setMinimumSize(new Dimension(500, 300));
        setMinimumSize(new Dimension(500, 300));
        setSize(500, 300);

        setVisible(true);

        layoutComponents();
    }

    public void setDoctorsBox(List<Doctor> doctors){
        DefaultComboBoxModel docModel = new DefaultComboBoxModel();
        docModel.addAll(doctors);
        doctorBox.setModel(docModel);
        doctorBox.setRenderer( new DoctorRenderer() );
        doctorBox.setEditable(true);
    }

    public void clearFormPanelL(){
        doseField.setText("");
        doseDatePicker.getJFormattedTextField().setText(formatDateTime);
        doctorBox.setSelectedIndex(-1);
    }

    public void setFormSaveBtnListener(ButtonListener listener) {
        this.formSaveBtnListener = listener;
    }

    public void setFormDelBtnListener(ButtonListener listener) {
        this.formDelBtnListener = listener;
    }

    public void layoutComponents() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

/////////////////////// FIRST ROW ///////

        gc.gridy = 0;

        gc.weightx = 2;
        gc.weighty = 0D;

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 10, 10, 10);
        add(titleLabel, gc);
        ////////////////////////////////////
        gc.gridy++;
        gc.gridy++;


        gc.weightx = 2;
        gc.weighty = 0D;

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        add(dateLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doseDatePicker, gc);
/////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(doseLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doseField, gc);
        /////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(doctorNameLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doctorBox, gc);

/////////////////////// NEXT ROW ///////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(addDoseB, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(closeB, gc);

    }

    public Dose getDoseFromForm() {
        if (doseDatePicker.getJFormattedTextField().getText() == null) {
            return null;
        } else {
            MyService myService = new MyServiceImpl();
            Dose dose = new Dose();
            dose.setDoseValue(Double.parseDouble(doseField.getText()));
            dose.setDateDose(doseDatePicker.getJFormattedTextField().getText());
            Doctor d = (Doctor) doctorBox.getSelectedItem();
            dose.setDoctorId(d.getId());
            Patient p = myService.findByIdPatient(patIdL);
            dose.setPatientId(p.getId());
            return dose;
        }
    }
    public boolean checkDoseData(){
        if( doseField.getText() != null && doseDatePicker.getJFormattedTextField().getText()!= null && doctorBox.getSelectedItem() != null){
            return true;
        } else{
            return false;
        }
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

    public void setPatIdL(Integer patIdL) {
        this.patIdL = patIdL;
    }

    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);        }

        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    public boolean checkDoseFormat(){
        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        return Pattern.matches(decimalPattern, this.doseField.getText());
    }
}
