package censusanalyser.adapter;

import censusanalyser.DAOFile.IndiaCensusDAO;
import censusanalyser.csvFiles.USCensusCSV;

import java.util.Map;

public class USCensusAdapter extends Adapter {

    public Map<String, IndiaCensusDAO> censusMap;

    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) {
        censusMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusMap;
    }
}