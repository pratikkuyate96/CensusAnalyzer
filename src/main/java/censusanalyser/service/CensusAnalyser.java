package censusanalyser.service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.adapter.AdapterFactory;
import censusanalyser.csvFiles.IndiaCensusCSV;
import censusanalyser.csvFiles.IndianStatesCode;
import censusanalyser.csvFiles.USCensusCSV;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.model.CSVBuilderFactory;
import censusanalyser.model.ICSVBuilder;
import com.google.gson.Gson;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
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
//
//    public int loadUSCensusData(String csvFilePath) {
//        return this.loadCensusData(csvFilePath, USCensusCSV.class);
//    }
//
//    public int loadUSCensusData(String csvFilePath) {
//        return this.loadCensusData(csvFilePath, USCensusCSV.class);
//    }


    public String getUSSortedCensusData(SortedField sortField) throws CensusAnalyserException {
        if(censusCSVMap.size()==0 || censusCSVMap==null) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        list = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(new LoadSortedField().sortedMap.get(sortField).reversed());
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    public String getSortedCensusData(SortedField sortedField) {
        if (list == null || list.size() == 0) {
            throw new CensusAnalyserException("No Census data available", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        list = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(new LoadSortedField().sortedMap.get(sortedField).reversed());

        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                IndiaCensusDAO census1 = list.get(j);
                IndiaCensusDAO census2 = list.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    list.set(j, census2);
                    list.set(j + 1, census1);
                }
            }
        }
    }

}