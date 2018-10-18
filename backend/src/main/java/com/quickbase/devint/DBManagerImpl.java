package com.quickbase.devint;

import java.sql.*;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {

    //Represents the connection to the database.
    Connection connection = null;

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException cnf) {
            System.out.println("could not load driver");
        } catch (SQLException sqlexception) {
            System.out.println("sql exception:" + sqlexception.getStackTrace());
        }
        return connection;
    }

    /**
     * Allows the user to close connection. The user is responsible to close the connection.
     */
    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
