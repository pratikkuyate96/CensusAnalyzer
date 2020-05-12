package censusanalyser;

import censusanalyser.csvFiles.IndiaCensusCSV;
import censusanalyser.csvFiles.USCensusCSV;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.service.CensusAnalyser;
import censusanalyser.service.SortedField;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.google.gson.Gson;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.java";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String WRONG_HEADER_FILE = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_FILE = "./src/test/resources/USCensusData.csv";

    //Tc 1.1
    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    //Tc 1.2
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //Tc 1.3
    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    //Tc 1.4
    @Test
    public void givenIndiaCensusCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
      }

      //Tc 1.5
    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    //Tc 2.1
    @Test
    public void givenIndianStoreCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    //Tc 2.2
    @Test
    public void givenIndiaStoreData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //Tc 2.3
    @Test
    public void givenStateStoreCsvFile_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    //Tc 2.4
    @Test
    public void givenIndiaStateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    //Tc 2.5
    @Test
    public void givenStateStoreCsvFile_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_HEADER_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortedField.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortedField.POPULATION);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnlDensity_ShouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortedField.POPULATIONSDENSITY);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedByArea_ShouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortedField.TOTALAREA);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Goa", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numberOfRecord = censusAnalyser.loadCensusData(US_CENSUS_FILE);
            Assert.assertEquals(51, numberOfRecord);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenSorted_ShouldReturnCorrectResult() {
        try {
            CensusAnalyser censusAnalyzer = new CensusAnalyser();
            censusAnalyzer.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_FILE);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(SortedField.POPULATION);
            USCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}