package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IStatService {
	
	List<Pair<String, Integer>> GetCountryPopulations();

}
