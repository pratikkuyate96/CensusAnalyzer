package censusanalyser;

public class CSVBuilderException extends RuntimeException {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,CSV_WRONG_FILE,UNABLE_TO_PARSE
    }

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
