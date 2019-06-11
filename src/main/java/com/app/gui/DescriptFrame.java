package com.app.gui;

import com.app.model.Effect;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.*;
import java.awt.*;

public class DescriptFrame extends JFrame {

    private JTextArea descriptionArea;
    private JScrollPane descriptionScroll;

    public DescriptFrame(Integer effId) {
        super("Opis Obserwacji pacjenta");

        MyService myService = new MyServiceImpl();
        Effect e = myService.findByIdEffect(effId);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setText(e.getDescription());
        descriptionScroll = new JScrollPane(descriptionArea);
        descriptionArea.setText(e.getDescription());
        descriptionArea.setEditable(false);
        setLayout(new BorderLayout());
        add(descriptionScroll, BorderLayout.CENTER);

        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
