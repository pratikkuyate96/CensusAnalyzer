package censusanalyser;

import com.opencsv.bean.CsvBindByName;

   public class IndianStatesCode {

       @CsvBindByName(column = "Serial Number", required = true)
       public int srNo;

       @CsvBindByName(column = "State Name", required = true)
       public String state;

       @CsvBindByName(column = "TIN", required = true)
       public int tin;

       @CsvBindByName(column = "State Code")
       public String stateCode;

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
