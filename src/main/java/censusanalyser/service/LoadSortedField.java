package censusanalyser.service;

import censusanalyser.DAOFile.IndiaCensusDAO;
import java.util.*;

public class LoadSortedField {

        Map<SortedField, Comparator<IndiaCensusDAO>> sortedMap;

        public LoadSortedField() {
            this.sortedMap = new HashMap<>();
            this.sortedMap.put(SortedField.STATE, Comparator.comparing(census -> census.state));
            this.sortedMap.put(SortedField.POPULATION, Comparator.comparing(census -> census.population));
            this.sortedMap.put(SortedField.POPULATIONSDENSITY, Comparator.comparing(census -> census.densityPerSqKm));
            this.sortedMap.put(SortedField.TOTALAREA, Comparator.comparing(census -> census.areaInSqKm));
        }
}

