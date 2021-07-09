package model.table;

import java.util.Comparator;

public class FormatableInteger implements Comparator<FormatableInteger> {
    private int size;

    public FormatableInteger(int size) {
        this.size = size;
    }

    @Override
    public String toString() {

            String returnvalue;
            if(size<=0){
                returnvalue = "0";

            }
            else if(size<1024){
                returnvalue = size + " B";
            }
            else if(size<1048576){
                returnvalue = size/1024 + " kB";
            }
            else{
                returnvalue = size/1048576 + " MB";
            }
            //formattedvalues.put(returnvalue,size);
            return returnvalue;
        }


    @Override
    public int compare(FormatableInteger o1, FormatableInteger o2) {
        Integer int1 = o1.size;
        Integer int2 = o2.size;
        return int1.compareTo(int2);


    }
}
