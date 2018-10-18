package com.quickbase;

import com.quickbase.devint.ConcreteStatService;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;
import com.quickbase.devint.ProcessData;
import com.quickbase.devint.ProcessDataImpl;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main(String args[]) {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();

        //If the connection fails, exit the program.
        if (null == c) {
            System.out.println("failed.");
            System.exit(1);
        } else {
            //If the connection succeeds, using the connection to get records
            //from the database.
            ProcessData processData = new ProcessDataImpl();

            //Query to execute.
            final String QUERY = "SELECT Country.CountryName, SUM(City.Population) " +
                    "FROM Country, City, State " +
                    "WHERE Country.CountryId = State.CountryId AND " +
                    "State.StateId = City.StateId " +
                    "GROUP BY Country.CountryName";

            HashMap<String, Integer> sourceDB
                    = (HashMap<String, Integer>)processData.retrieveRecords(QUERY, c);

            //get the records from API as well.
            List<Pair<String, Integer>> sourceAPI
                    = new ConcreteStatService().GetCountryPopulations();

            //return a unique list of country - population
            //based on both the sources.
            HashMap<String, Integer> finalValues
                    = processData.mergeDataSources(sourceDB, sourceAPI);

            //print the records to a file or STDOUT.
            for(String key: finalValues.keySet()) {
                System.out.println(key + ", " + finalValues.get(key));
            }
        }

    }
}