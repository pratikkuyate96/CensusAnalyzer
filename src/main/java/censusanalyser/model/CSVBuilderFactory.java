package censusanalyser.model;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder()
    {
        return new OpenCSVBuilder();
    }
}