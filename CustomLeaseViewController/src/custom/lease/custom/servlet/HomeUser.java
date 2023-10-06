package custom.lease.custom.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.octetstring.vde.util.Base64;

import com.view.uiutils.ADFUtils;

import custom.lease.custom.view.beans.Utils.JSFUtils;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Map;

import javax.naming.NamingException;

//import javax.servlet.*;
//import javax.servlet.http.*;

import oracle.adf.share.ADFContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import org.json.JSONObject;

public class HomeUser {
    public HomeUser() {
        super();
    }

    private static final String dataSource = "PRISM_PL";
    private static final String NumberFormat = "###,###,###,###";
    private static final String DateFormat = "dd-MMM-yyyy";
    private static final String currencyFormat = "AED";
    private static final Number MinFractionDigits=2;
    private static final Number MaxFractionDigits=2;
    private static final Number MinIntegerDigits=1;
    //    private static final String codeFirst = currencyFormat.substring(0, currencyFormat.indexOf("_"));
    //    private static final String codeLast = currencyFormat.substring(currencyFormat.indexOf("_") + 1, currencyFormat.length());
    //  #{sessionScope.dateFormat}

    private static String _grouId=null;
    private static String _userRole="NO_ROLE";
    private static String _userRoleID=null;
    private static String _userID=null;

//***************************************
    //******// MASTER
    private static final String LEASE_PROPERTY="WEB-INF/TaskFlow/SearchProperty_BTF.xml#SearchProperty_BTF";
    private static final String LEASE_BUILDING="WEB-INF/TaskFlow/SearchBuilding_BTF.xml#SearchBuilding_BTF";
    private static final String LEASE_UNIT="WEB-INF/TaskFlow/SearchUnit_BTF.xml#SearchUnit_BTF";
    private static final String LEASE_MILESTONE="WEB-INF/TaskFlow/SearchPaymentPlan_BTF.xml#SearchPaymentPlan_BTF";
    private static final String LEASE_PRICELIST="WEB-INF/TaskFlow/SearchPriceList_BTF.xml#SearchPriceList_BTF";
    //******// TRANSACTION
    private static final String LEASE_LEAD="WEB-INF/TaskFlow/SearchLead_BTF.xml#SearchLead_BTF";
    private static final String LEASE_BOOKING="WEB-INF/TaskFlow/SearchBooking_BTF.xml#SearchBooking_BTF";
    private static final String LEASE_RECEIPT="WEB-INF/TaskFlow/SearchReceipt_BTF.xml#SearchReceipt_BTF";
    //******// TRANSACTION 2
    private static final String LEASE_MOVE_IN="WEB-INF/TaskFlow/SearchMoveInOut_BTF.xml#SearchMoveInOut_BTF";
    private static final String LEASE_CANCEL="WEB-INF/TaskFlow/SearchCancellation_BTF.xml#SearchCancellation_BTF";
    private static final String LEASE_MOVE_OUT="WEB-INF/TaskFlow/SearchMoveInOut_BTF.xml#SearchMoveInOut_BTF";
    private static final String LEASE_AR_SETUP="WEB-INF/TaskFlow/ARSetup_BTF.xml#ARSetup_BTF";

//***************************************
    Map<Object, Object> sessionMap = ADFContext.getCurrent().getSessionScope();
    

    public String checkLoginUserInfor(){
        String reDirect = "";
        String path= "toRouter";
        
        String jwt =ADFContext.getCurrent().getPageFlowScope().get("jwtToken")!=""?
                    ADFContext.getCurrent().getPageFlowScope().get("jwtToken").toString():null;
        System.err.println("JWT tokens:=====>"+ADFContext.getCurrent().getPageFlowScope().get("jwtToken").toString());
        String applicationType =ADFContext.getCurrent().getPageFlowScope().get("appType")!=""?
                                ADFContext.getCurrent().getPageFlowScope().get("appType").toString():"L";
        System.err.println("applicationType=====>"+applicationType);        
        String screenName =
            ADFContext.getCurrent().getPageFlowScope().get("SCREEN_NAME")!=""?
            ADFContext.getCurrent().getPageFlowScope().get("SCREEN_NAME").toString():"LEASE_PROPERTY";
        System.err.println("screenName=====>"+screenName);
        
        if(jwt!=null){
            try{
                reDirect=checkLoginUserRole(jwt);
                if(reDirect.equalsIgnoreCase("invalidUser")){
                    ADFContext.getCurrent().getPageFlowScope().put("val","invalidUser"); 
                }else{
                    ADFContext.getCurrent().getPageFlowScope().put("val","validUser"); 
                    ADFContext.getCurrent().getPageFlowScope().put("taskId", getTaskflowpath(screenName));
                    System.err.println("task flow id=>"+getTaskflowpath(screenName));
                    System.err.println("task flow id 2=>"+ADFContext.getCurrent().getPageFlowScope().get("taskId"));
                }
            }catch(Exception e){
                ADFContext.getCurrent().getPageFlowScope().put("val","invalidUser"); 
            }
        }else{
            try{
                reDirect=checkLoginUserRole(getUserToken());
                System.out.println("reDirect==>"+reDirect);
                if(reDirect.equalsIgnoreCase("invalidUser")){
                    System.out.println("==Else No Token==>");
                    ADFContext.getCurrent().getPageFlowScope().put("val","invalidUser"); 
                }else{
                    System.out.println("==Token==>");
                    ADFContext.getCurrent().getPageFlowScope().put("val","validUser"); 
                    ADFContext.getCurrent().getPageFlowScope().put("taskId", getTaskflowpath(screenName));
                    System.err.println("task flow id"+getTaskflowpath(screenName));
                }
            }catch(Exception e){
                ADFContext.getCurrent().getPageFlowScope().put("val","invalidUser"); 
            }
        }
        getApplicationType(applicationType);
        sessionMap.put("MinFractionDigits", MinFractionDigits);
        sessionMap.put("MaxFractionDigits", MaxFractionDigits);
        sessionMap.put("MinIntegerDigits", MinIntegerDigits);
        sessionMap.put("dateFormat", DateFormat);

        return path;
    }


    public void getApplicationType(String appType){
        if(appType.equalsIgnoreCase("P")){
            sessionMap.put("httpPath", "https://omnijcsprod01.omniyat.com");            
        }else{
            sessionMap.put("httpPath", "https://jcs.omniyat.com");
        }
        sessionMap.put("dateFormat", DateFormat);
    }


    public String checkLoginUserRole(String jwt) throws SQLException, JSONException, NamingException {
            

            String pageRedirect = "invalid";
            String userRole = null;
            if (jwt != null) {
    //                    // System.err.println("==>in"+jwt);
                    String inputEncodedText = jwt;
                //                    ADFContext.getCurrent().getPageFlowScope().get("tokens").toString();
    //              String[] xIn = inputEncodedText.split("\\.");
    //              String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
                        String[] xIn = inputEncodedText.split("\\.");
                        byte b[] = Base64.decode(xIn[1]);
                        String tempPass = new String(b);
                        int chkOccurance = tempPass.lastIndexOf("}");
                        if (chkOccurance < 1) {
                            tempPass += "}";
                        }
                        JSONObject jo;

                        jo = new JSONObject(tempPass); 
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        Timestamp expFromToken = new Timestamp(jo.getLong("exp"));
                                        
                        //comment the below if statement for local
                        //                if(timestamp.compareTo(expFromToken)>0){
                        //                    return "tokenExpired";
                        //                }
                                        
                                        String userName = jo.getString("prn");
                                        sessionMap.put("userName", userName);
                                        
//                                        if(userName.equalsIgnoreCase("finance")){
//                                            sessionMap.put("userName", "suresh.madaparthi@4iapps.com");
//                                        }else if(userName.equalsIgnoreCase("PRISM@OMNIYAT.COM")){
//                                            sessionMap.put("userName", "dineshkumar.p@4iapps.com");
//                                        }
                    //                    session.setAttribute("userName", "api");

                    //                    sessionMap.put(userName, userName);
                    //                    // System.err.println("===Map=="+sessionMap.get("userName"));
    //                                        // System.err.println("PRN==>"+jo.getString("prn"));
                    //                    // System.err.println("HTTPuserName==>"+session.getAttribute("userName"));
                                       //--Check user role 
                                        userRole=getDBConnection(userName);
                                        if(userRole.equalsIgnoreCase("NO_ROLE")){
                                            JSFUtils.addFacesErrorMessage("userRole==>"+userRole);
                                            pageRedirect = "invalidUser";
                                        }else{
                                            pageRedirect = "validUser";
    //                                        // System.err.println("==>pageRedirect==>"+pageRedirect);

                                        }
    //                }catch(Exception e){
    //                    // System.err.println("==>eee==>");
    //                }
            } else {
                JSFUtils.addFacesErrorMessage("Token null==>");
                pageRedirect = "invalidUser";  
            }
            return pageRedirect;    
        }


    public String getDBConnection(String USER_NAME) {
        String retStr = "NO_ROLE";
        Context ctx;
        Connection con = null;
        try{
        ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(dataSource);
            con = ds.getConnection();
            
            String selectSQL =
                    "SELECT USER_ID,(UPPER(US.USER_NAME)) AS USER_NAME,  US.USER_ROLE_ID USER_ROLE_ID,US.DATA_GROUP_ID,\n" + 
                    "                                  (select XXFND_LOOKUP_V.LOOKUP_VALUE_NAME \n" + 
                    "                                  from XXFND_LOOKUP_V XXFND_LOOKUP_V\n" + 
                    "                                 where XXFND_LOOKUP_V.LINE_ACTIVE='Y'\n" + 
                    "                             AND   XXFND_LOOKUP_V.LOOKUP_VALUE_ID = US.USER_ROLE_ID) LOOKUP_VALUE_NAME\n" + 
                    "                             FROM XXFND_USER US\n" + 
                    "                                  where upper(USER_NAME) = upper('"+USER_NAME+"')";
            
    //            // System.err.println("selectSQL==>"+selectSQL);
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
    //        // System.err.println("=====preparedStatement==" + preparedStatement);
            ResultSet rs1 = preparedStatement.executeQuery(selectSQL);
    //        // System.err.println("rs1==>"+rs1);
            while (rs1.next()) {
                retStr = rs1.getString("LOOKUP_VALUE_NAME");
                 _grouId=rs1.getString("DATA_GROUP_ID");
                _userRole=rs1.getString("LOOKUP_VALUE_NAME");
                _userRoleID=rs1.getString("USER_ROLE_ID");
                _userID=rs1.getString("USER_ID");
            }
    //            // System.err.println("retStr==>"+retStr);
    //            // System.err.println("_grouId==>"+_grouId);
    //            // System.err.println("_userRole==>"+_userRole);
    //            // System.err.println("_userRoleID==>"+_userRoleID);
    //            // System.err.println("_userID==>"+_userID);
            
            String grouId=_grouId;
            //  ADFContext.getCurrent().getSessionScope().put("MinFractionDigits", MinFractionDigits);
            //   ADFContext.getCurrent().getSessionScope().put("MaxFractionDigits", MaxFractionDigits);
            sessionMap.put("userRole", _userRole);
            sessionMap.put("UR", _userRole);
            sessionMap.put("userRoleId", _userRoleID);
            sessionMap.put("UserId", _userID);
            prepareMenu(_userRoleID);
            sessionMap.put("UserGrpId", grouId);
            sessionMap.put("navigation", "Dashboard");
    //            sessionMap.put("NumberFormat", NumberFormat);
    //            sessionMap.put("MinFractionDigits", MinFractionDigits);
    //            sessionMap.put("MaxFractionDigits", MaxFractionDigits);
    //            sessionMap.put("MinIntegerDigits", MinIntegerDigits);
            
            //TODO for testing can remove later
    //            // System.err.println("User Role==" + sessionMap.get("UR"));
    //            // System.err.println("User Role Id==" + sessionMap.get("userRoleId"));
    //            // System.err.println("UserGrpId==" + sessionMap.get("UserGrpId"));
    //            // System.err.println("User Name==" + sessionMap.get("userName"));
    //            // System.err.println("User Id==" + sessionMap.get("UserId"));
        }
        catch(Exception e){
            e.printStackTrace();
    //            // System.err.println("Exception:"+e.getMessage());
        }
        finally{
            if(con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
    //                    // System.err.println("Exception while trying to close the connection:"+e.getMessage());
                }
            }
        }
    //        // System.err.println("=====retStr==" + retStr);
        return retStr;              
    }
    
    
    
    public void prepareMenu(String userRole) {
            
            Map grantMap = new HashMap();
            Context ctx;
            Connection con = null;
            try {
                if (userRole != null) {
    //                // System.err.println("userRole==>"+userRole);

                        try{
                        ctx = new InitialContext();
                            DataSource ds = (DataSource)ctx.lookup(dataSource);
                            con = ds.getConnection();
                            
                            String selectSQL =                
                                    "select\n" + 
                                    "    XXFND_MENU_ACCESS_T.MENU_ACCESS_ID   MENU_ACCESS_ID,\n" + 
                                    "    XXFND_MENU_ACCESS_T.USER_ROLE_ID     USER_ROLE_ID,\n" + 
                                    "    XXFND_MENU_ACCESS_T.SUB_MENU_ID      SUB_MENU_ID,\n" + 
                                    "    XXFND_SUB_MENU_T.SUB_MENU_NAME       SUB_MENU_NAME,\n" + 
                                    "    XXFND_MENU_ACCESS_T.MENU_DISPLAY     MENU_DISPLAY,\n" + 
                                    "    XXFND_MENU_ACCESS_T.MENU_CONTROL     MENU_CONTROL\n" + 
                                    "from\n" + 
                                    "    XXFND_MENU_ACCESS   XXFND_MENU_ACCESS_T,\n" + 
                                    "    XXFND_SUB_MENU      XXFND_SUB_MENU_T\n" + 
                                    "where\n" + 
                                    "    XXFND_MENU_ACCESS_T.SUB_MENU_ID = XXFND_SUB_MENU_T.SUB_MENU_ID\n" + 
                                    "    and XXFND_MENU_ACCESS_T.USER_ROLE_ID ='"+userRole+"'";                
                                
    //                        // System.err.println("userRole==>"+userRole);
    //                        // System.err.println("selectSQL==>"+selectSQL);
                            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
                            ResultSet rs1 = preparedStatement.executeQuery(selectSQL);
                            while (rs1.next()) {
                                grantMap.put(rs1.getString("SUB_MENU_NAME"), rs1.getString("MENU_CONTROL"));
                            }
    //                        ADFContext.getCurrent().getSessionScope().put("grantMap", grantMap);
                            sessionMap.put("grantMap", grantMap);
    //                      System.out.println("grantMap==>"+ADFContext.getCurrent().getSessionScope().get("grantMap").toString());
                            
                        }
                    
                    //                    #{sessionScope.accessMap.Property =='F'}
                    //                    #{sessionScope.grantMap.Property =='F'}
                    //                    #{sessionScope.grantMap.Building =='F'}
                    //                    #{sessionScope.grantMap.Unit =='F'}
                    //                    #{sessionScope.grantMap.PaymentPlan =='F'}
                    //                    #{sessionScope.grantMap.PriceList =='F'}
                    //                    #{sessionScope.grantMap.Lead =='F'}
                    //                    #{sessionScope.grantMap.Leasing =='F'}
                    //                    #{sessionScope.grantMap.Invoice =='F'}
                    //                    #{sessionScope.grantMap.Receipt =='F'}
                    //                    #{sessionScope.grantMap.OtherCharge =='F'}
                    //                    #{sessionScope.grantMap.Billing =='F'}
                    //                    #{sessionScope.grantMap.Cancellation =='F'}
                    //                    #{sessionScope.grantMap.MoveIn =='F'}
                    //                    #{sessionScope.grantMap.MoveOut =='F'}
        
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        finally{
                            if(con!=null){
                                try {
                                    con.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                }

            } catch (Exception e) {
                //e.printStackTrace();
            }
            
        }
    
    public String getTaskflowpath(String screenName){
        String l_taskflow=null;
        
        if(screenName.equals("LEASE_PROPERTY")){
            l_taskflow=LEASE_PROPERTY;
        }else if(screenName.equals("LEASE_BUILDING")){
            l_taskflow=LEASE_BUILDING;
        }else if(screenName.equals("LEASE_UNIT")){
            l_taskflow=LEASE_UNIT;
        }else if(screenName.equals("LEASE_MILESTONE")){
            l_taskflow=LEASE_MILESTONE;
        }else if(screenName.equals("LEASE_PRICELIST")){
            // PRICE LIST
            ADFContext.getCurrent().getSessionScope().put("screenName", "PriceList");
            l_taskflow=LEASE_PRICELIST;
        }else if(screenName.equals("LEASE_LEAD")){
            l_taskflow=LEASE_LEAD;
        }else if(screenName.equals("LEASE_BOOKING")){
            //BOOKING
            ADFContext.getCurrent().getSessionScope().put("screenName", "Booking");
            l_taskflow=LEASE_BOOKING;
        }else if(screenName.equals("LEASE_RECEIPT")){
            l_taskflow=LEASE_RECEIPT;
        }else if(screenName.equals("LEASE_MOVE_IN")){
            // MOVE IN 
//            ViewObject vo = ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl")
//                               .findApplicationModule("MoveInOut_AM1").findViewObject("SearchMoveInOut_ROVO1");
//                             vo.setNamedWhereClauseParam("BV_IoFlag","I");
//                              vo.executeQuery();
            ADFContext.getCurrent().getSessionScope().put("ScreenName", "Move In");
            ADFContext.getCurrent().getSessionScope().put("MoveInOut", "I");
            l_taskflow=LEASE_MOVE_IN;
        }else if(screenName.equals("LEASE_CANCEL")){
            l_taskflow=LEASE_CANCEL;
        }else if(screenName.equals("LEASE_MOVE_OUT")){
//            ViewObjectImpl  vo = (ViewObjectImpl )ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl")
//                              .findApplicationModule("MoveInOut_AM1").findViewObject("SearchMoveInOut_ROVO1");
////            vo.setNamedWhereClauseParam("BV_IoFlag","O");
////             vo.executeQuery();
//            vo.setApplyViewCriteriaName("ScreenFilter");
//            vo.setNamedWhereClauseParam("BV_IoFlag", "O");
//            vo.executeQuery();
            ADFContext.getCurrent().getSessionScope().put("ScreenName","Move Out");
            ADFContext.getCurrent().getSessionScope().put("MoveInOut", "O");
            l_taskflow=LEASE_MOVE_OUT;
        }else if(screenName.equals("LEASE_AR_SETUP")){
            l_taskflow=LEASE_AR_SETUP;
        }
        return l_taskflow;
    }
    
    
    
    
    
    public String getUserToken(){
       String userloginToken=null;
       // Ken
//      userloginToekn="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IkI2MXZGVHlWa1ZzZ2JJNUFxM0pnaVV1Rmt6VSIsImtpZCI6InRydXN0c2VydmljZSJ9.eyJleHAiOjE1NjU4MDUzMjEsInN1YiI6Imtlbm5ldGguZGNvc3RhQG9tbml5YXQuY29tIiwiaXNzIjoid3d3Lm9yYWNsZS5jb20iLCJwcm4iOiJrZW5uZXRoLmRjb3N0YUBvbW5peWF0LmNvbSIsImlhdCI6MTU2NTc5MDkyMX0.SAZQR0xUTJWD2TRf6tTYAU249WUbMJdSW80YALciOobhy9JuaqnPXfKmwOIFNdAXy3DUqc1KiBbpxiUq7Tp2QAQDdcN3Agkgs2t68A-pbxJjUf4pw0tdWFtKVjoMaVSUQxPOya1p1_Um_L2dP1C8KRHKbTHGos4Dm0WU3dUQizMOGtJFbD35wBWhJ1LZ3gBkmAiDVhAMegVFe-h7lKJiQFrYBmo6_yCbgLRvzllCjjYel0QiJafkKppYqOzrwR4L4MDTggtuXm9y5dl0_O68os_T4CkKz9kwVg69H752GGkpWCPntVali1kzQQFvq2I3E2wUVFpQgKXg3pMFIMUcLA";
       // prismuser
       userloginToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IkI2MXZGVHlWa1ZzZ2JJNUFxM0pnaVV1Rmt6VSIsImtpZCI6InRydXN0c2VydmljZSJ9.eyJleHAiOjE1NjU4MDUzMjEsInN1YiI6ImFwaSIsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiYXBpIiwiaWF0IjoxNTY1NzkwOTIxfQ.BbiQgftPRmNLAUPmPt-6dpcJktU4ZfWNf5hxatQlFfU";
       //api
//       userloginToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IlRBc0pMVXY0MFVuaWRJclVrRGFwRzhFXzlLOCJ9.eyJleHAiOjE0ODM1MzA3NzEsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiYXBpIiwiaWF0IjoxNDgzNTE2MzcxfQ.ALvDilyGj-VQUmRQnUc7L1tz895bxjiSfPetczwqbUhMTmBIIoyJd9tKFQTnYPg8esUiiym8RnXRisFXcWmdmcoYAg3bbhqQ877KBDdXg6_CAk5h4OSHG8jgXhWFSJsE-";
       return userloginToken; 
    }


}
