package cachemoney420.financeproject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by mspear on 4/5/16.
 */
public class Compare {


    public static void main(String[] args) {
        ArrayList<String> tickerArray = new ArrayList<String>();
        tickerArray.add("PFE");
        tickerArray.add("BAC");
        tickerArray.add("VRX");

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        from.add(Calendar.YEAR, -1);
    }

}
