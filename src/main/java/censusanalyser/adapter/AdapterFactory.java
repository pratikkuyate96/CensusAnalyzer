package censusanalyser.adapter;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.service.CensusAnalyser;
import java.util.Comparator;
import java.util.Map;

public class AdapterFactory {
    public static Map<String, IndiaCensusDAO> getCensusData(CensusAnalyser.Country country, String... csvFilePath) {
       if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    public Comparator<IndiaCensusDAO> getCurrentSort(String field) {

        Comparator<IndiaCensusDAO> comparator = null;
        switch (field) {
            case "population":
                comparator = Comparator.comparing( census -> census.population );
                break;
            case "density":
                comparator = Comparator.comparing( census -> census.populationDensity );
                break;
            case "area":
                comparator = Comparator.comparing( census -> census.totalArea );
                break;
            case "state":
                comparator = Comparator.comparing( census -> census.state );
                break;
            case "statecode":
                comparator = Comparator.comparing( census -> census.stateCode );
                break;
        }
        return comparator;
    }

}