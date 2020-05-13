package censusanalyser.adapter;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.csvFiles.IndiaCensusCSV;
import censusanalyser.csvFiles.IndianStatesCode;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.model.CSVBuilderFactory;
import censusanalyser.model.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends Adapter {

    Map<String, IndiaCensusDAO> censusDAOMap=null;
    List<IndiaCensusDAO> list=null;

    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) {
        censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadStateCodeData(csvFilePath[1]);
        return censusDAOMap;
    }

    public int loadStateCodeData(String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStatesCode> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStatesCode.class);
            Iterable<IndianStatesCode> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusDAOMap.get(csvState.state) != null)
                    .forEach(csvState -> censusDAOMap.get(csvState.state).stateCode = csvState.stateCode);
            list = censusDAOMap.values().stream().collect(Collectors.toList());
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM);
        }
    }
}
