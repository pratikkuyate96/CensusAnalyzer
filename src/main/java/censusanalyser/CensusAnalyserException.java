package censusanalyser;

public class CensusAnalyserException extends Exception {
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, String type) {
        super(message);
        this.type = ExceptionType.valueOf(type);
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,CSV_WRONG_FILE,UNABLE_TO_PARSE
    }

    ExceptionType type;
}
