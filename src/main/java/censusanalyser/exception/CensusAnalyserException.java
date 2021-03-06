package censusanalyser.exception;

public class CensusAnalyserException extends RuntimeException {

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_CENSUS_DATA,INVALID_COUNTRY,CSV_TEMPLATE_PROBLEM;
    }

}