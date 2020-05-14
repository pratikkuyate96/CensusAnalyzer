package censusanalyser.DAOFile;

import censusanalyser.csvFiles.IndiaCensusCSV;
import censusanalyser.csvFiles.USCensusCSV;
import censusanalyser.service.CensusAnalyser;

public class  IndiaCensusDAO {
    public double totalArea;
    public String state;
    public int areaInSqKm;
    public double densityPerSqKm;
    public double population;
    public String stateCode;
    public double populationDensity;

    public IndiaCensusDAO(IndiaCensusCSV next) {
        state=next.state;
        areaInSqKm=next.areaInSqKm;
        densityPerSqKm=next.densityPerSqKm;
        population=next.population;
    }

    public IndiaCensusDAO(USCensusCSV next) {
        state = next.state;
        stateCode = next.stateId;
        population = next.population;
        totalArea = next.totalArea;
        densityPerSqKm = next.totalArea;
        populationDensity = next.populationDensity;
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if(country.equals( CensusAnalyser.Country.INDIA ))
            return new IndiaCensusCSV(state,(int)population,(int)populationDensity,(int)totalArea);
        return new USCensusCSV(state,stateCode,(int)population,populationDensity,totalArea);
    }
}