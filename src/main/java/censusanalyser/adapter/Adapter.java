package censusanalyser.adapter;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.csvFiles.IndiaCensusCSV;
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
import java.util.stream.StreamSupport;

public abstract class Adapter {

    public abstract Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) ;

    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusCsvClass, String csvFilePath) {
        Map<String, IndiaCensusDAO> censusMap = new HashMap<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCsvIterator = icsvBuilder.getCSVFileIterator(reader, censusCsvClass);
            Iterable<E> csvIterable = () -> censusCsvIterator;

            if (censusCsvClass.getName().equals("IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new IndiaCensusDAO(censusCsv)));
            } else if (censusCsvClass.getName().equals("UsCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new IndiaCensusDAO(censusCsv)));
            }
            
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM);
        }
    }

}
