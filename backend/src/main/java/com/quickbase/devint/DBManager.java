package com.quickbase.devint;

import java.sql.Connection;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {

    /**
     * Establishes the connection with the database and returns
     * the connection object to the user.
     *
     * @return  An instance of connection object.
     */
    public Connection getConnection();

    /**
     * Allows the user to close connection. The user is
     * responsible to close the connection.
     */
    public void closeConnection();

    /**
     * Based on the query the user develops, the method
     * executes the query and
     */

}
