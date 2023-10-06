package custom.lease.view.bean.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


public class BaseDetail {
    public BaseDetail() {
        super();
    }

    //Mail Services
    public static final String PriceListSubjects = "Reg: Price list";
    public static final String BOOKING_SUBJECT = "Reg: Booking";   
    public static final String PaymentPlanSubjects = "Reg: Payment Plan";    
    public static final String CancellationSubjects = "Reg: Cancellation";    
    public static final String MoveIOSubjects = "Reg: Move Out";    
    // Generic Fields
    public static final String initialLevel = "0";
    public static final String initialAmount = "0";

    //Price List
    public static final String PriceListHdrTableName = "xxpl_pl_header";
    public static final String PriceListStatusColumnName = "STATUS";
    public static final String PriceListPkColumnName = "PL_ID";

    //Booking
    public static final String BOOKING_HDR_TABLE = "XXPL_BOOKING_HEADER";
    public static final String BOOKING_STATUS_COLUMN = "STATUS";
    public static final String BOOKING_ID_COLUMN = "BOOKING_HDR_ID";
    
    //Milestone List 
    public static final String MilestoneHdrTableName = "XXPL_MILESTONES_HDR";
    public static final String MilestoneStatusColumnName = "STATUS";
    public static final String MilestonePkColumnName = "MS_HDR_ID";
    
    //Cancellation
    public static final String CANCELLATION_TABLE = "XXPL_CANCELLATION";
    public static final String CANCELLATION_STATUS_COLUMN = "STATUS";
    public static final String CANCELLATION_ID_COLUMN ="CANCEL_ID";
    
    //MoveInOut
    public static final String MOVEINOUT_TABLE = "XXPL_MOVE_IN_OUT";
    public static final String MOVEINOUT_STATUS_COLUMN = "STATUS";
    public static final String MOVEINOUT_ID_COLUMN ="MOVE_IO_ID";


    /**
     * Method to get sysdate
     * @return
     */
    public static String SysDate() {
        String pattern = "dd-MMM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        // Get the today date using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String todayAsString = df.format(today);
        // Print the result!
//        System.out.println("Today is: " + todayAsString);
        return todayAsString;
    }


    



    
    
    
}
