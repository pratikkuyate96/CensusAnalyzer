package censusanalyser.adapter;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.service.CensusAnalyser;

import java.util.Map;

public class AdapterFactory {
    public static Map<String, IndiaCensusDAO> getCensusData(CensusAnalyser.Country country, String... csvFilePath) {
       if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
