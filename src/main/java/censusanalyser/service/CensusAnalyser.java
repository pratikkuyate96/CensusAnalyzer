package censusanalyser.service;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.adapter.AdapterFactory;
import censusanalyser.exception.CensusAnalyserException;
import com.google.gson.Gson;
import java.util.stream.Collectors;
import java.util.*;

public class CensusAnalyser {

     Country country;

    public enum Country {INDIA, US}

    List<IndiaCensusDAO> list;
    Map<String, IndiaCensusDAO> censusCSVMap;

    public CensusAnalyser()
    {
        this.list = new ArrayList<>();
        this.censusCSVMap = new HashMap<>();
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = AdapterFactory.getCensusData(country, csvFilePath);
        return censusCSVMap.size();    }


    public String getSortedCensusData(String sortedField) {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census data available", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaCensusDAO> censusComparator = new AdapterFactory().getCurrentSort( sortedField );
        List censusDTO=censusCSVMap.values().stream()
                .sorted(censusComparator.reversed())
                .map( IndiacensusDAO -> IndiacensusDAO.getCensusDTO(country))
                .collect( Collectors.toList());
        String sortedStateCensusJson = new Gson().toJson( censusDTO );
        return sortedStateCensusJson;
    }

}