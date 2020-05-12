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

    public int loadIndiaCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
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
    
    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if (censusCSVClass.getName().equals("censusanalyser.csvfiles.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.csvFiles.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }
            list = censusCSVMap.values().stream().collect(Collectors.toList());
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_WRONG_FILE);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStatesCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStatesCode.class);
            Iterable<IndianStatesCode> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusCSVMap.get(csvState.state) != null)
                    .forEach(csvState -> censusCSVMap.get(csvState.state));
            return censusCSVMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_WRONG_FILE);
        }
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