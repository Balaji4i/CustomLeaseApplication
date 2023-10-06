package custom.lease.custom.view.beans.PackageUtils;


import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.Utils.DBUtils;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;

import oracle.adf.share.ADFContext;

import oracle.jbo.domain.Number;
import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jdbc.OracleTypes;

public class PackageCalling {
    public PackageCalling() {
        super();
    }
    private static Object[][] dobProcArgs = null;


    public static String createMilestoneBooking(String pBookingId) { 
        String p_err_msg = null; 
        String p_err_code = null; 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        oracle.jbo.domain.Number bookingId = new oracle.jbo.domain.Number();
        try {
            bookingId = new oracle.jbo.domain.Number(pBookingId);
        } catch (SQLException e) {

        }
        oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
//        String qry = "call XXPL_CREATE_BOOKING(?,?,?,?)";
        String qry = "call XXPL_CREATE_NEW_BOOKING(?,?,?,?)";
        dobProcArgs =
                new Object[][] { { "IN", bookingId, Types.NUMERIC }, 
                                 { "IN", userName, Types.VARCHAR },
                                 { "OUT", p_err_msg, Types.VARCHAR },
                                 { "OUT", p_err_code, Types.VARCHAR } };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            p_err_code = (String) dobProcArgs[3][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }
        return p_err_code;
    }
    
    public static ArrayList createReceipt(String pBookingId, String pBookMsId, String pInstallType, String pReceiptType) { 
        // System.err.println("From receipt creation pkg :: "+pBookingId +"---"+pBookMsId + "---"+pInstallType + "---"+pReceiptType);
        ArrayList <String> returnList = new ArrayList<String>();
        String p_err_msg = null; 
        String p_err_code = null; 
        String receId = "0";
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        oracle.jbo.domain.Number bookingId = new oracle.jbo.domain.Number();
        oracle.jbo.domain.Number bookingMsId = new oracle.jbo.domain.Number();
        try {
            bookingId = new oracle.jbo.domain.Number(pBookingId);
            if(pBookMsId.equalsIgnoreCase("0")){
                bookingMsId = new oracle.jbo.domain.Number(0);     
            }else{
                bookingMsId = new oracle.jbo.domain.Number(pBookMsId);    
            }
            // System.err.println("==0"+bookingMsId);
        } catch (SQLException e) {

        } 
        BigDecimal receiptId = new BigDecimal(0);
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_CREATE_RECEIPT(?,?,?,?,?,?,?,?)";
        dobProcArgs =
                new Object[][] {    { "IN", bookingId, Types.NUMERIC }, 
                                    { "IN", bookingMsId, Types.NUMERIC },
                                    { "IN", pInstallType, Types.VARCHAR },
                                    { "IN", pReceiptType, Types.VARCHAR },
                                    { "IN", userName, Types.VARCHAR },
                                    { "OUT", receiptId, Types.NUMERIC },
                                    { "OUT", p_err_msg, Types.VARCHAR },
                                    { "OUT", p_err_code, Types.VARCHAR } };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            receiptId = (BigDecimal)dobProcArgs[5][1]; 
            p_err_msg = (String) dobProcArgs[6][1]; 
            p_err_code = (String) dobProcArgs[7][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }
        receId = receiptId.toString();
        returnList.add(receId);  // get(2)
         
        return returnList;
    }
    
    
    public static ArrayList invokeSubmit(String P_FUNC_ID, String P_REF_ID, 
                                         String P_LEVEL_NO, 
                                         String P_TABLE_NAME, String P_STATUS_COLUMN, 
                                         String P_PK_COLUMN,
                                         String P_AMOUNT, 
                                         String P_ATTRIBUTE1, String P_ATTRIBUTE2, 
                                         String P_ATTRIBUTE3, String P_ATTRIBUTE4, 
                                         String P_ATTRIBUTE5) { 

//        // System.err.println("Submit Process :: P_TABLE_NAME"+P_TABLE_NAME);
//        // System.err.println("Submit Process :: P_FUNC_ID"+P_FUNC_ID);
//        // System.err.println("Submit Process :: P_REF_ID"+P_REF_ID);
//        // System.err.println("Submit Process :: P_LEVEL_NO"+P_LEVEL_NO);
//        // System.err.println("Submit Process :: P_STATUS_COLUMN"+P_STATUS_COLUMN);
//        // System.err.println("Submit Process :: P_PK_COLUMN"+P_PK_COLUMN);
//        // System.err.println("Submit Process :: P_AMOUNT"+P_AMOUNT);
//        // System.err.println("Submit Process :: P_ATTRIBUTE1"+P_ATTRIBUTE1); 
//        // System.err.println("Submit Process :: P_ATTRIBUTE2"+P_ATTRIBUTE2); 
        
        ArrayList <String> returnList = new ArrayList<String>();
        
        String P_FLOW_STATUS= null; 
        String P_FLOW_WITH= null; 
        String P_APPROVER_NAME= null; 
        String P_APPROVER_EMAIL= null; 
        String P_USER_GRP_ID= null; 
        String P_ERR_CODE= null; 
        String P_ERR_MSG= null; 
        String P1_LEVEL= null; 
        
//        oracle.jbo.domain.Number P1_REF_ID = new oracle.jbo.domain.Number();
//        oracle.jbo.domain.Number P1_FUNC_ID = new oracle.jbo.domain.Number();
//        try {
//        //--------------------Primary ID
//            P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);
//            if(P_REF_ID.equalsIgnoreCase("0")){
//                P1_REF_ID = new oracle.jbo.domain.Number(0);     
//            }else{
//                P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);    
//            }
//        //--------------------Function
//            P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);
//            if(P_FUNC_ID.equalsIgnoreCase("0")){
//                P1_FUNC_ID = new oracle.jbo.domain.Number(0);     
//            }else{
//                P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);    
//            }
//        } catch (SQLException e) {
//
//        } 
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXFND_UTIL_PKG.SUBMIT_FOR_APPROVAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dobProcArgs =
                new Object[][] {    
                                    { "IN", P_FUNC_ID           , Types.VARCHAR },      //0
                                    { "IN", P_REF_ID            , Types.VARCHAR },      //1  
                                    { "IN", P_LEVEL_NO          , Types.VARCHAR },      //2        
                                    { "IN", P_TABLE_NAME        , Types.VARCHAR },      //3
                                    { "IN", P_STATUS_COLUMN     , Types.VARCHAR },      //4
                                    { "IN", P_PK_COLUMN         , Types.VARCHAR },      //5
                                    { "IN", P_AMOUNT            , Types.VARCHAR },      //6
                                    { "IN", P_ATTRIBUTE1        , Types.VARCHAR },      //7
                                    { "IN", P_ATTRIBUTE2        , Types.VARCHAR },      //8
                                    { "IN", P_ATTRIBUTE3        , Types.VARCHAR },      //9
                                    { "IN", P_ATTRIBUTE4        , Types.VARCHAR },      //10
                                    { "IN", P_ATTRIBUTE5        , Types.VARCHAR },      //11
                                    { "OUT", P_FLOW_STATUS      , Types.VARCHAR },      //12
                                    { "OUT", P_FLOW_WITH        , Types.VARCHAR },      //13
                                    { "OUT", P_APPROVER_NAME    , Types.VARCHAR },      //14
                                    { "OUT", P_APPROVER_EMAIL   , Types.VARCHAR },      //15
                                    { "OUT", P_USER_GRP_ID      , Types.VARCHAR },      //16
                                    { "OUT", P_ERR_CODE         , Types.VARCHAR },      //17
                                    { "OUT", P_ERR_MSG          , Types.VARCHAR }       //18
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            
            P_FLOW_STATUS = (String)dobProcArgs[12][1]; 
            P_FLOW_WITH = (String)dobProcArgs[13][1];  
            P_APPROVER_NAME = (String)dobProcArgs[14][1]; 
            P_APPROVER_EMAIL = (String)dobProcArgs[15][1]; 
            P_USER_GRP_ID = (String)dobProcArgs[16][1]; 
            P_ERR_CODE = (String)dobProcArgs[17][1]; 
            P_ERR_MSG = (String)dobProcArgs[18][1]; 
            P1_LEVEL = (String)dobProcArgs[2][1]; 
            
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Error: Please check Submit Package!");
        }
        
        returnList.add(P_FLOW_STATUS);          // get(0)
        if(P_FLOW_WITH!=null){
        returnList.add(P_FLOW_WITH.toString()); // get(1)    
        }else{
        returnList.add("0");                    // get(1)    
        }
        returnList.add(P_APPROVER_NAME);    // get(2)
        returnList.add(P_APPROVER_EMAIL);   // get(3)
        returnList.add(P_USER_GRP_ID);      // get(4)
        returnList.add(P_ERR_CODE);         // get(5)
        returnList.add(P_ERR_MSG);          // get(6)
        returnList.add(P1_LEVEL);           // get(7)
        returnList.add(P_FLOW_STATUS);      // get(8)
         
        return returnList;
    }

    public static ArrayList invokeUpdateResponse(
                                            String P_FUNC_ID, 
                                            String P_REF_ID,
                                            String P_USER_GROUP_ID, 
                                            String P_LEVEL_NO, 
                                            String P_APPR_ID,
                                            String P_COMMENTS,
                                            String P_ARStatus,
                                            String P_FWD_TO,
                                            String P_TABLE_NAME, 
                                            String P_STATUS_COLUMN, 
                                            String P_PK_COLUMN,
                                            String P_AMOUNT) { 

//        // System.err.println("Submit Process :: P_TABLE_NAME"+P_TABLE_NAME);
//        // System.err.println("Submit Process :: P_FUNC_ID"+P_FUNC_ID);
//        // System.err.println("Submit Process :: P_REF_ID"+P_REF_ID);
//        // System.err.println("Submit Process :: P_LEVEL_NO"+P_LEVEL_NO);
//        // System.err.println("Submit Process :: P_STATUS_COLUMN"+P_STATUS_COLUMN);
//        // System.err.println("Submit Process :: P_PK_COLUMN"+P_PK_COLUMN);
//        // System.err.println("Submit Process :: P_AMOUNT"+P_AMOUNT);
//        // System.err.println("Submit Process :: P_APPR_ID"+P_APPR_ID);
//        // System.err.println("Submit Process :: P_COMMENTS"+P_COMMENTS);
//        // System.err.println("Submit Process :: P_ARStatus"+P_ARStatus);
//        // System.err.println("Submit Process :: P_USER_GROUP_ID"+P_USER_GROUP_ID);
//        // System.err.println("Submit Process :: P_FWD_TO"+P_FWD_TO);
        
        ArrayList <String> returnList = new ArrayList<String>();
        
        String P_FLOW_STATUS= null; 
        String P_APPROVER_NAME= null; 
        String P_APPROVER_EMAIL= null; 
        String P_ERR_CODE= null; 
        String P_ERR_MSG= null; 
        
//        oracle.jbo.domain.Number P1_REF_ID = new oracle.jbo.domain.Number();
//        oracle.jbo.domain.Number P1_FUNC_ID = new oracle.jbo.domain.Number();
//        oracle.jbo.domain.Number P1_USER_GRP_ID = new oracle.jbo.domain.Number();
//        oracle.jbo.domain.Number P1_LEVEL_NO = new oracle.jbo.domain.Number();
//        oracle.jbo.domain.Number P1_APPR_ID = new oracle.jbo.domain.Number();
        
//        try {
//        //--------------------Primary ID
//            P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);
//            if(P_REF_ID.equalsIgnoreCase("0")){
//                P1_REF_ID = new oracle.jbo.domain.Number(0);     
//            }else{
//                P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);    
//            }
//        //--------------------Function
//            P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);
//            if(P_FUNC_ID.equalsIgnoreCase("0")){
//                P1_FUNC_ID = new oracle.jbo.domain.Number(0);     
//            }else{
//                P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);    
//            }
//        //--------------------User Group id
//                P1_USER_GRP_ID = new oracle.jbo.domain.Number(P_USER_GROUP_ID);
//                if(P_USER_GROUP_ID.equalsIgnoreCase("0")){
//                    P1_USER_GRP_ID = new oracle.jbo.domain.Number(0);     
//                }else{
//                    P1_USER_GRP_ID = new oracle.jbo.domain.Number(P_USER_GROUP_ID);    
//                }
//        //--------------------Level
//                P1_LEVEL_NO = new oracle.jbo.domain.Number(P_LEVEL_NO);
//                if(P_LEVEL_NO.equalsIgnoreCase("0")){
//                    P1_LEVEL_NO = new oracle.jbo.domain.Number(0);     
//                }else{
//                    P1_LEVEL_NO = new oracle.jbo.domain.Number(P_LEVEL_NO);    
//                }    
//        //--------------------Approver id
//                    P1_APPR_ID = new oracle.jbo.domain.Number(P_APPR_ID);
//                    if(P_APPR_ID.equalsIgnoreCase("0")){
//                        P1_APPR_ID = new oracle.jbo.domain.Number(0);     
//                    }else{
//                        P1_APPR_ID = new oracle.jbo.domain.Number(P_APPR_ID);    
//                    }
//            
//        } catch (SQLException e) {
//
//        } 
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXFND_UTIL_PKG.UPDATE_RESPONSE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dobProcArgs =
                new Object[][] {    
                                    { "IN", P_FUNC_ID          , Types.VARCHAR },     //0
                                    { "IN", P_REF_ID           , Types.VARCHAR },     //1  
                                    { "IN", P_USER_GROUP_ID      , Types.VARCHAR },      //2  
                                    { "IN", P_LEVEL_NO         , Types.VARCHAR },      //3  
                                    { "IN", P_APPR_ID          , Types.VARCHAR },      //4  
                                    { "IN", P_COMMENTS          , Types.VARCHAR },      //5
                                    { "IN", P_ARStatus          , Types.VARCHAR },      //6
                                    { "IN", P_FWD_TO            , Types.VARCHAR },      //7
                                    { "IN", P_TABLE_NAME        , Types.VARCHAR },      //8
                                    { "IN", P_STATUS_COLUMN     , Types.VARCHAR },      //9
                                    { "IN", P_PK_COLUMN         , Types.VARCHAR },      //10
                                    { "IN", P_AMOUNT            , Types.VARCHAR },      //11
                                    { "OUT", P_FLOW_STATUS      , Types.VARCHAR },      //12
                                    { "OUT", P_APPROVER_NAME    , Types.VARCHAR },      //13
                                    { "OUT", P_APPROVER_EMAIL   , Types.VARCHAR },      //14
                                    { "OUT", P_ERR_CODE         , Types.VARCHAR },      //15
                                    { "OUT", P_ERR_MSG          , Types.VARCHAR }       //16
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            
            P_FLOW_STATUS = (String)dobProcArgs[12][1]; 
            P_APPROVER_NAME = (String)dobProcArgs[13][1]; 
            P_APPROVER_EMAIL = (String)dobProcArgs[14][1]; 
            P_ERR_CODE = (String)dobProcArgs[15][1]; 
            P_ERR_MSG = (String)dobProcArgs[16][1]; 
            P_LEVEL_NO = (String)dobProcArgs[3][1]; 
            
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Error: Please check Submit Package!");
        }
        
        if(P_FLOW_STATUS!=null){
            returnList.add(P_FLOW_STATUS.toString());       // get(0)
        }else{
            returnList.add("Error");                        // get(0)
        }
        returnList.add(P_APPROVER_NAME);                    // get(1)
        returnList.add(P_APPROVER_EMAIL);                   // get(2)
        returnList.add(P_ERR_CODE);                         // get(3)
        returnList.add(P_ERR_MSG);                          // get(4)
        return returnList;
    }


    public static ArrayList invokeReject(String P_FUNC_ID, String P_REF_ID, 
                                         oracle.jbo.domain.Number P_LEVEL_NO, 
                                         String P_TABLE_NAME, String P_STATUS_COLUMN, 
                                         String P_PK_COLUMN,
                                         oracle.jbo.domain.Number P_AMOUNT, 
                                         String P_ATTRIBUTE1, String P_ATTRIBUTE2, 
                                         String P_ATTRIBUTE3, String P_ATTRIBUTE4, 
                                         String P_ATTRIBUTE5) { 

//        // System.err.println("Submit Process :: P_TABLE_NAME"+P_TABLE_NAME);
//        // System.err.println("Submit Process :: P_FUNC_ID"+P_FUNC_ID);
//        // System.err.println("Submit Process :: P_REF_ID"+P_REF_ID);
//        // System.err.println("Submit Process :: P_LEVEL_NO"+P_LEVEL_NO);
//        // System.err.println("Submit Process :: P_STATUS_COLUMN"+P_STATUS_COLUMN);
//        // System.err.println("Submit Process :: P_PK_COLUMN"+P_PK_COLUMN);
//        // System.err.println("Submit Process :: P_AMOUNT"+P_AMOUNT);
//        // System.err.println("Submit Process :: P_ATTRIBUTE1"+P_ATTRIBUTE1);
//        // System.err.println("Submit Process :: P_ATTRIBUTE2"+P_ATTRIBUTE2);
        
        ArrayList <String> returnList = new ArrayList<String>();
        
        String P_FLOW_STATUS= null; 
        BigDecimal P_FLOW_WITH= null; 
        String P_APPROVER_NAME= null; 
        String P_APPROVER_EMAIL= null; 
        String P_USER_GRP_ID= null; 
        String P_ERR_CODE= null; 
        String P_ERR_MSG= null; 
        String P1_LEVEL= null; 
        
        oracle.jbo.domain.Number P1_REF_ID = new oracle.jbo.domain.Number();
        oracle.jbo.domain.Number P1_FUNC_ID = new oracle.jbo.domain.Number();
        try {
        //--------------------Primary ID
            P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);
            if(P_REF_ID.equalsIgnoreCase("0")){
                P1_REF_ID = new oracle.jbo.domain.Number(0);     
            }else{
                P1_REF_ID = new oracle.jbo.domain.Number(P_REF_ID);    
            }
        //--------------------Function
            P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);
            if(P_FUNC_ID.equalsIgnoreCase("0")){
                P1_FUNC_ID = new oracle.jbo.domain.Number(0);     
            }else{
                P1_FUNC_ID = new oracle.jbo.domain.Number(P_FUNC_ID);    
            }
        } catch (SQLException e) {

        } 
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXFND_UTIL_PKG.SUBMIT_FOR_APPROVAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dobProcArgs =
                new Object[][] {    
                                    { "IN", P_FUNC_ID           , Types.NUMERIC },      //0
                                    { "IN", P_REF_ID            , Types.NUMERIC },      //1  
                                    { "IN", P_LEVEL_NO          , Types.NUMERIC },      //2        
                                    { "IN", P_TABLE_NAME        , Types.VARCHAR },      //3
                                    { "IN", P_STATUS_COLUMN     , Types.VARCHAR },      //4
                                    { "IN", P_PK_COLUMN         , Types.VARCHAR },      //5
                                    { "IN", P_AMOUNT            , Types.NUMERIC },      //6
                                    { "IN", P_ATTRIBUTE1        , Types.VARCHAR },      //7
                                    { "IN", P_ATTRIBUTE2        , Types.VARCHAR },      //8
                                    { "IN", P_ATTRIBUTE3        , Types.VARCHAR },      //9
                                    { "IN", P_ATTRIBUTE4        , Types.VARCHAR },      //10
                                    { "IN", P_ATTRIBUTE5        , Types.VARCHAR },      //11
                                    { "OUT", P_FLOW_STATUS      , Types.VARCHAR },      //12
                                    { "OUT", P_FLOW_WITH        , Types.NUMERIC },      //13
                                    { "OUT", P_APPROVER_NAME    , Types.VARCHAR },      //14
                                    { "OUT", P_APPROVER_EMAIL   , Types.VARCHAR },      //15
                                    { "OUT", P_USER_GRP_ID      , Types.VARCHAR },      //16
                                    { "OUT", P_ERR_CODE         , Types.VARCHAR },      //17
                                    { "OUT", P_ERR_MSG          , Types.VARCHAR }       //18
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            
            P_FLOW_STATUS = (String)dobProcArgs[12][1]; 
            P_FLOW_WITH = (BigDecimal)dobProcArgs[13][1];  
            P_APPROVER_NAME = (String)dobProcArgs[14][1]; 
            P_APPROVER_EMAIL = (String)dobProcArgs[15][1]; 
            P_USER_GRP_ID = (String)dobProcArgs[16][1]; 
            P_ERR_CODE = (String)dobProcArgs[17][1]; 
            P_ERR_MSG = (String)dobProcArgs[18][1]; 
            P1_LEVEL = (String)dobProcArgs[2][1]; 
            
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Error: Please check Submit Package!");
        }
        
        returnList.add(P_FLOW_STATUS);      // get(0)
        if(P_FLOW_WITH!=null){
        returnList.add(P_FLOW_WITH.toString());        // get(1)    
        }
        returnList.add(P_APPROVER_NAME);    // get(2)
        returnList.add(P_APPROVER_EMAIL);   // get(3)
        returnList.add(P_USER_GRP_ID);      // get(4)
        returnList.add(P_ERR_CODE);         // get(5)
        returnList.add(P_ERR_MSG);          // get(6)
        returnList.add(P1_LEVEL);           // get(7)
        returnList.add(P_FLOW_STATUS);      // get(8)
         
        return returnList;
    }
    
    public static String createRevisionMilestone(String pMsId) { 
         String p_err_msg = null; 
         String p_err_code = null; 
         oracle.jbo.domain.Number msid=null;
         String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
         try {
                    msid = new oracle.jbo.domain.Number(pMsId);
                } catch (SQLException e) {

                }
         
//         ArrayList list = new ArrayList();
         ApplicationModuleImpl am =
             (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
         String qry = "call xxpl_lease_milestone.revise_milestone(?,?,?,?)";
         dobProcArgs =
                 new Object[][] { { "IN", msid, Types.NUMERIC }, 
                                  { "IN", userName, Types.VARCHAR },
                                  { "OUT", p_err_msg, Types.VARCHAR },
                                  { "OUT", p_err_code, Types.VARCHAR } };

         try {
             DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
             p_err_msg = (String) dobProcArgs[3][1]; 
         } catch (SQLException e) {
             JSFUtils.addFacesInformationMessage("Please check, Error !");
         }
         return p_err_msg;
     }

    public static String createRevisionPriceList(String pMsId) { 
         String p_err_msg = null; 
         String p_err_code = null; 
         oracle.jbo.domain.Number msid=null;
         String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
         try {
                    msid = new oracle.jbo.domain.Number(pMsId);
                } catch (SQLException e) {

                }
         
    //         ArrayList list = new ArrayList();
         ApplicationModuleImpl am =
             (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
         String qry = "call xxpl_lease_pricelist.revision_pricelist(?,?,?,?)";
         
         dobProcArgs =
                 new Object[][] { { "IN", msid, Types.NUMERIC }, 
                                  { "IN", userName, Types.VARCHAR },
                                  { "OUT", p_err_msg, Types.VARCHAR },
                                  { "OUT", p_err_code, Types.VARCHAR } };

         try {
             DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
             p_err_msg = (String) dobProcArgs[3][1]; 
         } catch (SQLException e) {
             JSFUtils.addFacesInformationMessage("Please check, Error !");
         }
         return p_err_msg;
     }

    public static String createRevisionReceipt(String pMsId) { 
         String p_err_msg = null; 
         String p_err_code = null; 
         oracle.jbo.domain.Number msid=null;
         String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
         try {
                    msid = new oracle.jbo.domain.Number(pMsId);
                } catch (SQLException e) {

                }
         
    //         ArrayList list = new ArrayList();
         ApplicationModuleImpl am =
             (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
         String qry = "call xxpl_lease_receipt.revision_receipt(?,?,?,?)";
         dobProcArgs =
                 new Object[][] { { "IN", msid, Types.NUMERIC }, 
                                  { "IN", userName, Types.VARCHAR },
                                  { "OUT", p_err_msg, Types.VARCHAR },
                                  { "OUT", p_err_code, Types.VARCHAR } };

         try {
             DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
             p_err_msg = (String) dobProcArgs[3][1]; 
         } catch (SQLException e) {
             JSFUtils.addFacesInformationMessage("Please check, Error !");
         }
         return p_err_msg;
     }
    
    public static ArrayList approvalUpdateResponce(String pFuncId, String pfunRefId, String pTableName) {  
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number funcId = new oracle.jbo.domain.Number();
        oracle.jbo.domain.Number funcRefId = new oracle.jbo.domain.Number();
        try {
            funcId = new oracle.jbo.domain.Number(pFuncId); 
            funcRefId = new oracle.jbo.domain.Number(pfunRefId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXFND_APPROVAL.XXPM_APPROVAL_UPD_RESPONSE(?,?,?,?,?)";
        dobProcArgs =
                new Object[][] {    { "IN", funcId, Types.NUMERIC }, 
                                    { "IN", funcRefId, Types.NUMERIC },
                                    { "IN", pTableName, Types.VARCHAR },
                                    { "OUT", p_err_code, Types.VARCHAR },
                                    { "OUT", p_err_msg, Types.VARCHAR } };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_err_code = (String) dobProcArgs[3][1]; 
            p_err_msg = (String) dobProcArgs[4][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }else{
            returnList.add("-");
        }
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        } 
        return returnList;
    }
    
    public static ArrayList bookingRenewalProcess(String pBookId, String sDate, String eDate, String paymentType) { 
//        // System.err.println("ID::"+pBookId+"-sDate::"+sDate+"-eDate::"+eDate+"Type::"+paymentType);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        BigDecimal newBookId = new BigDecimal(0); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_RENEWAL_BOOKING(?,?,?,?,?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    { "IN", bookId, Types.NUMERIC }, 
                                    { "IN", sDate, Types.VARCHAR },
                                    { "IN", eDate, Types.VARCHAR },
                                    { "IN", userName, Types.VARCHAR },
                                    { "IN", paymentType, Types.VARCHAR },
                                    { "OUT", p_err_code, Types.VARCHAR },
                                    { "OUT", p_err_msg, Types.VARCHAR },
                                    { "OUT", newBookId, Types.NUMERIC }};

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_err_code = (String) dobProcArgs[5][1]; 
            p_err_msg = (String) dobProcArgs[6][1]; 
            newBookId = (BigDecimal)dobProcArgs[7][1];
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }else{
            returnList.add("-");
        }
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  
        returnList.add(newBookId.toString());  // get(2)

        return returnList;
    }
    
    public static ArrayList bookingDeleteProcess(String pBookId) { 
        // System.err.println("ID::"+pBookId);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_DELETE_LEASE_PROCESS.XXPL_DELETE_LEASE_BOOKING(?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    { "IN", bookId, Types.NUMERIC }, 
                                    { "IN", userName, Types.VARCHAR },
                                    { "OUT", p_err_code, Types.VARCHAR },
                                    { "OUT", p_err_msg, Types.VARCHAR }};

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_err_code = (String) dobProcArgs[2][1]; 
            p_err_msg = (String) dobProcArgs[3][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }else{
            returnList.add("-");
        }
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  
        return returnList;
    }
    
    public static ArrayList cancellationProceed(String pCancelId) { 
        // System.err.println("ID::"+pBookId);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number cancelId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            cancelId = new oracle.jbo.domain.Number(pCancelId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_CANCELLATION_CAL(?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    { "IN", cancelId, Types.NUMERIC }, 
                                    { "IN", userName, Types.VARCHAR },
                                    { "OUT", p_err_msg, Types.VARCHAR },
                                    { "OUT", p_err_code, Types.VARCHAR }};

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_err_msg = (String) dobProcArgs[2][1]; 
            p_err_code = (String) dobProcArgs[3][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }else{
            returnList.add("-");
        }
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  
        return returnList;
    }
    
    public static String checkValidDates(String sDate, String eDate, String msType) {  
        System.err.println("PARAM:::"+sDate +"--"+eDate+"--"+msType);
        String returnList = "0";
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String sqlQry = "{? = call GET_INVOICE_VALIDATION('"+sDate+"','"+eDate+"','"+msType+"')}";
        CallableStatement proc = null;
        try {
            proc = am.getDBTransaction().createCallableStatement(sqlQry, 0);
            proc.registerOutParameter(1, OracleTypes.VARCHAR);
            proc.executeQuery();
            returnList = proc.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return returnList;
    }
    //
    public static String createMilestoneBooking_CN(String pCancelId) { 
        String p_err_msg = null; 
        String p_err_code = null; 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        oracle.jbo.domain.Number cancelId = new oracle.jbo.domain.Number();
        try {
            cancelId = new oracle.jbo.domain.Number(pCancelId);
        } catch (SQLException e) {

        }
        oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call xxpl_process_due_amount(?,?,?,?)";
        dobProcArgs =
                new Object[][] { { "IN", cancelId, Types.NUMERIC }, 
                                 { "IN", userName, Types.VARCHAR },
                                 { "OUT", p_err_msg, Types.VARCHAR },
                                 { "OUT", p_err_code, Types.VARCHAR } };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);
            p_err_code = (String) dobProcArgs[3][1]; 
            System.err.println("+"+p_err_code);
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !"+e.toString());
        }
        return p_err_code;
    }
    
    
    
    
    public static ArrayList bookingRevisionProcess(String pBookId) { 

        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call REVISE_BOOKING(?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    
                                    { "IN", bookId, Types.NUMERIC },            //0
                                    { "IN", userName, Types.VARCHAR },          //1    
                                    { "OUT", p_err_code, Types.VARCHAR },       //2
                                    { "OUT", p_err_msg, Types.VARCHAR }         //3
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_err_code = (String) dobProcArgs[2][1]; 
            p_err_msg = (String) dobProcArgs[3][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        
//        System.out.println("Error"+p_err_code);
//        System.out.println("p_err_msg"+p_err_msg);
        
        if(p_err_code!=null){
           returnList.add(p_err_code);  // get(0)
        }else{
            returnList.add("-");
        }
        
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  

        return returnList;
    }
    
    public static ArrayList invokeRevertLease(String P_REF_ID, String P_USER_NAME) { 

        ArrayList <String> returnList = new ArrayList<String>();        
        String p_response= null;        
        ArrayList list = new ArrayList();
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call Revert_Lease_pkg.Revert_Lease(?,?,?)";
        dobProcArgs =
                new Object[][] {    
                                    { "IN", P_REF_ID           , Types.VARCHAR },      //0
                                    { "IN", P_USER_NAME        , Types.VARCHAR },      //1  
                                    { "OUT", p_response         , Types.VARCHAR },      //2                                            
                                };
        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs);          
            p_response = (String)dobProcArgs[2][1];          
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Error: Please check Revert Lease Package!");
        }        
        returnList.add(p_response);          // get(0)        
        return returnList;
    }
    
    public static ArrayList moveIn(String pBookId) { 
        BigDecimal p_OUT_REF_ID =  new BigDecimal(0);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_LEASE_TO_MOVE_IN(?,?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    
                                    { "IN", bookId, Types.NUMERIC },            //0
                                    { "IN", userName, Types.VARCHAR },          //1
                                    { "OUT", p_OUT_REF_ID, Types.NUMERIC },       //2   
                                    { "OUT", p_err_msg, Types.VARCHAR },       //3
                                    { "OUT", p_err_code, Types.VARCHAR }         //4
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_OUT_REF_ID =(BigDecimal) dobProcArgs[2][1]; 
            p_err_code = (String) dobProcArgs[3][1]; 
            p_err_msg = (String) dobProcArgs[4][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        
           System.out.println("Error"+p_err_code);
                System.out.println("p_err_msg"+p_err_msg);
        if (p_OUT_REF_ID != new BigDecimal(0)) {
            returnList.add(p_OUT_REF_ID.toString()); // get(0)
        } else {
            returnList.add("0");
        }
        
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  

        if (p_err_code != null) {
            returnList.add(p_err_code);  // get(2)
        }else{
            returnList.add("-");
        }
         

        return returnList;
    }
    
    
    public static ArrayList moveOut(String pBookId) { 
        BigDecimal p_OUT_REF_ID =  new BigDecimal(0);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_LEASE_TO_MOVE_OUT(?,?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    
                                    { "IN", bookId, Types.NUMERIC },            //0
                                    { "IN", userName, Types.VARCHAR },          //1
                                    { "OUT", p_OUT_REF_ID, Types.NUMERIC },       //2   
                                    { "OUT", p_err_msg, Types.VARCHAR },       //3
                                    { "OUT", p_err_code, Types.VARCHAR }         //4
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_OUT_REF_ID =(BigDecimal) dobProcArgs[2][1]; 
            p_err_code = (String) dobProcArgs[3][1]; 
            p_err_msg = (String) dobProcArgs[4][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        
    //        System.out.println("Error"+p_err_code);
        //        System.out.println("p_err_msg"+p_err_msg);
        if (p_OUT_REF_ID != new BigDecimal(0)) {
            returnList.add(p_OUT_REF_ID.toString()); // get(0)
        } else {
            returnList.add("0");
        }
        
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  

        if (p_err_code != null) {
            returnList.add(p_err_code);  // get(2)
        }else{
            returnList.add("-");
        }
         

        return returnList;
    }
    
    public static ArrayList cancellation(String pBookId) { 
        BigDecimal p_OUT_REF_ID =  new BigDecimal(0);
        String p_err_msg = null; 
        String p_err_code = null;  
        ArrayList <String> returnList = new ArrayList<String>();
        oracle.jbo.domain.Number bookId = new oracle.jbo.domain.Number(); 
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        try {
            bookId = new oracle.jbo.domain.Number(pBookId);  
            
        } catch (SQLException e) {

        }    
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String qry = "call XXPL_LEASE_TO_CANCELLATION(?,?,?,?,?)"; 
        dobProcArgs =
                new Object[][] {    
                                    { "IN", bookId, Types.NUMERIC },            //0
                                    { "IN", userName, Types.VARCHAR },          //1
                                    { "OUT", p_OUT_REF_ID, Types.NUMERIC },       //2   
                                    { "OUT", p_err_msg, Types.VARCHAR },       //3
                                    { "OUT", p_err_code, Types.VARCHAR }         //4
                                };

        try {
            DBUtils.callDBStoredProcedure(am.getDBTransaction(), qry, dobProcArgs); 
            p_OUT_REF_ID =(BigDecimal) dobProcArgs[2][1]; 
            p_err_code = (String) dobProcArgs[3][1]; 
            p_err_msg = (String) dobProcArgs[4][1]; 
        } catch (SQLException e) {
            JSFUtils.addFacesInformationMessage("Please check, Error !");
        }  
        
    //        System.out.println("Error"+p_err_code);
        //        System.out.println("p_err_msg"+p_err_msg);
        if (p_OUT_REF_ID != new BigDecimal(0)) {
            returnList.add(p_OUT_REF_ID.toString()); // get(0)
        } else {
            returnList.add("0");
        }
        
        if(p_err_msg!=null){
           returnList.add(p_err_msg);  // get(1)
        }else{
            returnList.add("-");
        }  

        if (p_err_code != null) {
            returnList.add(p_err_code);  // get(2)
        }else{
            returnList.add("-");
        }
         

        return returnList;
    }
    //fitout period
    public static String onfitOutDate(String bkId) {  
        System.err.println("bkId:::"+bkId);
        String returnList = "0";
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl");
        String sqlQry = "{? = call ON_FITOUT_DATE('"+bkId+"')}";
        CallableStatement proc = null;
        try {
            proc = am.getDBTransaction().createCallableStatement(sqlQry, 0);
            proc.registerOutParameter(1, OracleTypes.VARCHAR);
            proc.executeQuery();
            returnList = proc.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return returnList;
    }
    
}
