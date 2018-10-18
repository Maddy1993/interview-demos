package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instance implementing this interface will have the ability to
 * retrieve the records from the sources and perform any further
 * operations the retrieved records.
 */
public interface ProcessData {

  /**
   * Using the query as input, and the db specified in
   * the input, performs the query execution and retrieves
   * the records.
   *
   * @param query Query represented in the String format. Needs to be
   *              a qualified string which can be executed using JDBC
   *              statement.
   * @param dbInstance  Represents the connection to the DB.
   * @return Data in the form the Key, value pairs.
   */
  public Map<String,Integer> retrieveRecords(String query, Connection dbInstance);

  /**
   * Given two data sources, the method merges the two data sources.
   * In case of duplicates, the values from data source specified in
   * the first argument are taken into consideration.
   *
   * @param source1 Represents data in the form of Key, value.
   * @param source2 Represents data in the form of Key, value.
   * @return  Data, which now represents the values from both sources
   *          in the form of Key, value.
   */
  public HashMap<String, Integer> mergeDataSources(HashMap<String, Integer> source1,
                                                   List<Pair<String, Integer>> source2);
}
