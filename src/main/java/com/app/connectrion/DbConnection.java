package com.app.connectrion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {return ourInstance;}
    private Logger logger = Logger.getLogger(getClass().getName());

    private final String DRIVER = "org.sqlite.JDBC";
    private final String DB = "jdbc:sqlite:test.db";
    private Connection connection;

    private DbConnection()
    {
        try {
            Class.forName(DRIVER); //zaladowanie sterownika
            connection = DriverManager.getConnection(DB);
            createTables();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
            System.out.println(e.toString());
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    private void createTables()
    {
        try {
            final String sqlDoctor = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name VARCHAR(50) NOT NULL," +
                            "surname VARCHAR(50) NOT NULL," +
                            "medicalLicence VARCHAR(50) NOT NULL," +
                            "phoneNumber VARCHAR(9) NOT NULL)",
                    DbTables.Doctor.toString()
            );
            final String sqlPatient = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name VARCHAR(50) NOT NULL," +
                            "surname VARCHAR(50) NOT NULL," +
                            "pesel VARCHAR(11) NOT NULL," +
                            "placebo BOOLEAN NOT NULL," +
                            "birthday VARCHAR(50) NOT NULL," +
                            "gender VARCHAR(20) NOT NULL," +
                            "doctorId INTEGER NOT NULL," +
                            "FOREIGN KEY (doctorId) REFERENCES Doctor(id) " +
                            "ON DELETE CASCADE ON UPDATE CASCADE )",
                    DbTables.Patient.toString()
            );
            final String sqlDose = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "doseValue DOUBLE NOT NULL," +
                            "dateDose VARCHAR(50) NOT NULL," +
                            "patientId INTEGER NOT NULL," +
                            "doctorId INTEGER NOT NULL," +
                            "FOREIGN KEY (patientId) REFERENCES Patient(id) " +
                            "FOREIGN KEY (doctorId) REFERENCES Doctor(id) " +
                            "ON DELETE CASCADE ON UPDATE CASCADE )",
                    DbTables.Dose.toString()
            );
            final String sqlEffect = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "description VARCHAR(500) NOT NULL," +
                            "dateEffect VARCHAR(50) NOT NULL," +
                            "patientId INTEGER NOT NULL," +
                            "doctorId INTEGER NOT NULL," +
                            "FOREIGN KEY (patientId) REFERENCES Patient(id) " +
                            "FOREIGN KEY (doctorId) REFERENCES Doctor(id) " +
                            "ON DELETE CASCADE ON UPDATE CASCADE )",
                    DbTables.Effect.toString()
            );
            Statement statement = connection.createStatement();
            statement.execute(sqlDoctor);
            statement.execute(sqlPatient);
            statement.execute(sqlDose);
            statement.execute(sqlEffect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
