package custom.lease.custom.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.octetstring.vde.util.Base64;

import com.view.uiutils.ADFUtils;

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


public class DashBoardServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String TASK_FLOW_ID ="faces/FilmStrip";
    private static final String InValidTASK_FLOW_ID ="faces/Invalid";
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


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    Map<Object, Object> sessionMap = ADFContext.getCurrent().getSessionScope();
    
    /**Process the HTTP doGet request.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = null;
        String reDirect="Error";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>DashBoard Servlet</title></head>");
        out.println("<body>");
        //---
        String url = request.getRequestURL().toString();
        String subUrl =url.substring(0, url.lastIndexOf("/"));
        String jwtToken = request.getParameter("jwt");
        String appType = request.getParameter("appType")!=null?request.getParameter("appType"):"L";
        HttpSession session = request.getSession();
        System.err.println("reDirect -->1");
        if(jwtToken!=null){
            try{
                reDirect = checkLoginUserRole(jwtToken,session);
                 System.err.println("reDirect -->"+reDirect);
                if(reDirect.equalsIgnoreCase("invalidUser")){
                    path = subUrl + "/" + InValidTASK_FLOW_ID +"?errorType="+reDirect;
                }else{
                    path = subUrl + "/" + TASK_FLOW_ID;
                }
                System.err.println("path"+path);
            }catch(Exception e){
              
              
            }
        }else{
            try {
             // Ken
//            reDirect = checkLoginUserRole("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IkI2MXZGVHlWa1ZzZ2JJNUFxM0pnaVV1Rmt6VSIsImtpZCI6InRydXN0c2VydmljZSJ9.eyJleHAiOjE1NjU4MDUzMjEsInN1YiI6Imtlbm5ldGguZGNvc3RhQG9tbml5YXQuY29tIiwiaXNzIjoid3d3Lm9yYWNsZS5jb20iLCJwcm4iOiJrZW5uZXRoLmRjb3N0YUBvbW5peWF0LmNvbSIsImlhdCI6MTU2NTc5MDkyMX0.SAZQR0xUTJWD2TRf6tTYAU249WUbMJdSW80YALciOobhy9JuaqnPXfKmwOIFNdAXy3DUqc1KiBbpxiUq7Tp2QAQDdcN3Agkgs2t68A-pbxJjUf4pw0tdWFtKVjoMaVSUQxPOya1p1_Um_L2dP1C8KRHKbTHGos4Dm0WU3dUQizMOGtJFbD35wBWhJ1LZ3gBkmAiDVhAMegVFe-h7lKJiQFrYBmo6_yCbgLRvzllCjjYel0QiJafkKppYqOzrwR4L4MDTggtuXm9y5dl0_O68os_T4CkKz9kwVg69H752GGkpWCPntVali1kzQQFvq2I3E2wUVFpQgKXg3pMFIMUcLA", session);   
         System.err.println("reDirect -->2");
             reDirect = 
                 checkLoginUserRole("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IkI2MXZGVHlWa1ZzZ2JJNUFxM0pnaVV1Rmt6VSIsImtpZCI6InRydXN0c2VydmljZSJ9.eyJleHAiOjE1NjU4MDUzMjEsInN1YiI6ImFwaSIsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiYXBpIiwiaWF0IjoxNTY1NzkwOTIxfQ.BbiQgftPRmNLAUPmPt-6dpcJktU4ZfWNf5hxatQlFfU", session);   
         
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if(reDirect.equalsIgnoreCase("invalidUser")){
                path = subUrl + "/" + InValidTASK_FLOW_ID +"?errorType="+reDirect;
            }else{
                path = subUrl + "/" + TASK_FLOW_ID;    
            }
            System.err.println("path -->"+path);
        }
        //---   
        applicationType(appType, session);
        response.sendRedirect(path);
        out.println("<p>Redirecting to Dashboard taskflow</p>");
        out.println("</body></html>");
        out.close();
    }
    
    
    public void applicationType(String appType, HttpSession session){
        if(appType.equalsIgnoreCase("P")){
            session.setAttribute("httpPath", "https://omnijcsprod01.omniyat.com");            
        }else{
            session.setAttribute("httpPath", "https://jcs.omniyat.com");
        }
        session.setAttribute("dateFormat", DateFormat);
        System.err.println("httpPath path for appType -->"+session.getAttribute("httpPath"));
    }
    
    
    public String checkLoginUserRole(String jwt,HttpSession session) throws SQLException, JSONException, NamingException {
            

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
                                        session.setAttribute("userName", userName);
                                        System.out.println("userName ##"+session.getAttribute("userName"));
//                                        session.setAttribute("userName", "2dineshkmrp0@gmail.com");
//                                          session.setAttribute("userName", "dineshkumar.p@4iapps.com");
                    //                    sessionMap.put(userName, userName);
                    //                    // System.err.println("===Map=="+sessionMap.get("userName"));
//                                        // System.err.println("PRN==>"+jo.getString("prn"));
                    //                    // System.err.println("HTTPuserName==>"+session.getAttribute("userName"));
                                       //--Check user role 
                                        userRole=getDBConnection(userName,session);
                                        if(userRole.equalsIgnoreCase("NO_ROLE")){
                                            pageRedirect = "invalidUser";
                                        }else{
                                            pageRedirect = "validUser";
//                                        // System.err.println("==>pageRedirect==>"+pageRedirect);

                                        }
//                }catch(Exception e){
//                    // System.err.println("==>eee==>");
//                }
            } else {
                pageRedirect = "invalidUser";  
            }
            return pageRedirect;    
        }
    

    public String getDBConnection(String USER_NAME, HttpSession session) {
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
            prepareMenu(_userRoleID, session);
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
    


public void prepareMenu(String userRole, HttpSession session) {
        
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
                        session.setAttribute("grantMap", grantMap);
                        
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
    
}

