package censusanalyser.service;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.csvFiles.IndiaCensusCSV;
import censusanalyser.csvFiles.IndianStatesCode;
import censusanalyser.csvFiles.USCensusCSV;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.model.CSVBuilderFactory;
import censusanalyser.model.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusLoader {

    public Map<String, IndiaCensusDAO> censusCSVMap;

    public CensusLoader() {
        this.censusCSVMap = new HashMap<>();
    }

    public <E> Map<String, IndiaCensusDAO> loadCensusData(CensusAnalyser.Country country, String[] csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return this.loadCensusData(USCensusCSV.class, csvFilePath);
        throw new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    private <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if (censusCSVClass.getName().equals("censusanalyzer.csvFiles.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyzer.csvFiles.UsCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }
            if (csvFilePath.length == 1)
                return censusCSVMap;
            this.loadStateCodeData(censusCSVMap, csvFilePath[1]);
            return censusCSVMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_WRONG_FILE);
        }
    }

    private int loadStateCodeData(Map<String, IndiaCensusDAO> censusCSVMap, String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStatesCode> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStatesCode.class);
            Iterable<IndianStatesCode> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> this.censusCSVMap.get(csvState.state) != null)
                    .forEach(csvState -> this.censusCSVMap.get(csvState.state));
            return this.censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_WRONG_FILE);
        }
    }
}
