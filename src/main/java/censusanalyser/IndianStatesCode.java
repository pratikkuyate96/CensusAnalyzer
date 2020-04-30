package censusanalyser;

import com.opencsv.bean.CsvBindByName;

   public class IndianStatesCode {

       @CsvBindByName(column = "Serial Number", required = true)
       private int srNo;

       @CsvBindByName(column = "State Name", required = true)
       private String state;

       @CsvBindByName(column = "TIN", required = true)
       private int tin;

       @CsvBindByName(column = "State Code")
       private String stateCode;

       @Override
       public String toString() {
           return "IndiaStateCode{" +
                   "srNo=" + srNo +
                   ", state='" + state + '\'' +
                   ", tin=" + tin +
                   ", stateCode='" + stateCode + '\'' +
                   '}';
       }
}
