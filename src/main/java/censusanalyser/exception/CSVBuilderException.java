package censusanalyser.exception;

public class CSVBuilderException extends RuntimeException {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,CSV_WRONG_FILE,UNABLE_TO_PARSE,INVALID_COUNTRY,CSV_TEMPLATE_PROBLEM,INDIA_STATE_CODE
    }

    public ExceptionType type;
    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
