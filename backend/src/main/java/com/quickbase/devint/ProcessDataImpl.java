package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instance which implements the ProcessData interface.
 * Retrieves the records, merges the records into set and
 * returns the same to the User utilizing the instance.
 */
public class ProcessDataImpl implements ProcessData{

  /**
   * Using the query as input, and the db specified in the input, performs the query execution and
   * retrieves the records.
   *
   * @param query      Query represented in the String format. Needs to be a qualified string which
   *                   can be executed using JDBC statement.
   * @param dbInstance Represents the connection to the DB.
   */
  @Override
  public Map<String, Integer> retrieveRecords(final String query, Connection dbInstance) {
    //Variable to retrieve records.
    Statement statement = null;
    ResultSet resultSet = null;

    //Variables to store the records retrieved.
    Map<String, Integer> countryPopulation = new HashMap<>();

    //Variables to store key value pairs.
    String name;
    Integer population;

    try {
      //create an instance of Statement.
      statement = dbInstance.createStatement();
      resultSet = statement.executeQuery(query);

      //retrieve the records one by one.
      while(resultSet.next()) {
        name = resultSet.getString(1);
        double d = Double.parseDouble(resultSet.getString(2));
        population = (int)d;

        //validate the population value.
        if (population < 0) {
          throw new NumberFormatException("Invalid population data. Database data needs"
                  + " to be validated.");
        }

        countryPopulation.put(name, population);
      }

      //close the connection.
      dbInstance.close();

    } catch (NumberFormatException exception) {
      System.out.println(exception.getMessage());
      exception.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return countryPopulation;
  }

  /**
   * Given two data sources, the method merges the two data sources. In case of duplicates, the
   * values from data source specified in the first argument are taken into consideration.
   *
   * @param source1 Represents data in the form of Key, value.
   * @param source2 Represents data in the form of Key, value.
   * @return Data, which now represents the values from both sources in the form of Key, value.
   */
  @Override
  public HashMap<String, Integer> mergeDataSources(HashMap<String, Integer> source1,
                                                   List<Pair<String, Integer>> source2) {
    //Iterate through the country values retrieved from the API.
    for(Pair<String, Integer> pair: source2) {
      //check if the key of the pair is available
      //in the hashmap.
      //If it does not exists, add it to the hashmap.
      //Else proceed to the next record.
      String countryName = pair.getKey();
      int population = pair.getValue();
      if(!source1.containsKey(countryName)) {
        source1.put(countryName, population);
      }
    }

    return source1;
  }
}
