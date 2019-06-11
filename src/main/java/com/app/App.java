package com.app;

import com.app.connectrion.DbConnection;
import com.app.gui.LoginFrame;
import com.app.gui.MainFrame;
import com.app.model.*;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import javax.swing.*;
import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();

            }
        });
    }
}