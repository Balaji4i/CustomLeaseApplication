package custom.lease.model.AM;

import custom.lease.model.AM.common.Documents_AM;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;

//import com.model.util.CommonJBOException;

import com.octetstring.vde.util.Base64;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;

//import org.json.JSONException;
//import org.json.JSONObject;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 16 13:58:57 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class Documents_AMImpl extends ApplicationModuleImpl implements Documents_AM {
    /**
     * This is the default constructor (do not remove).
     */
    public Documents_AMImpl() {
    }

    /**
     * Container's getter for XxfndFuncAttachVO.
     * @return XxfndFuncAttachVO
     */
    public ViewObjectImpl getXxfndFuncAttachVO() {
        return (ViewObjectImpl)findViewObject("XxfndFuncAttachVO");
    }
    public void intAttachments(String funcid, String funcrefid) {
//        // System.err.println("Func Id==>" + funcid + "Func Ref Id==>" +funcrefid);
        ViewObject vo = this.getXxfndFuncAttachVO().getViewObject();
        ViewObjectImpl voImpl = (ViewObjectImpl)vo.getViewObject();
        ViewCriteria voVc = voImpl.getViewCriteria("findByFuncId");
        voImpl.setNamedWhereClauseParam("BV_FuncId", funcid);
        voImpl.setNamedWhereClauseParam("BV_FuncRefId", funcrefid);
        vo.applyViewCriteria(voVc);
        vo.executeQuery();
    }
    
    public void filterCheckList(String funcId, String funcRefId) { 
        ViewObject clVo = this.getXxplChecklist_VO().getViewObject();
        ViewCriteria vc = clVo.createViewCriteria(); 
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncId", funcId);
        vc.addRow(vcRow);
        vcRow.setAttribute("FuncRefId", funcRefId);
        vc.addRow(vcRow);
        clVo.applyViewCriteria(vc);
        clVo.executeQuery();
    }

    /**
     * Container's getter for getUserDetailsROVO1.
     * @return getUserDetailsROVO1
     */
    public ViewObjectImpl getgetUserDetailsROVO1() {
        return (ViewObjectImpl)findViewObject("getUserDetailsROVO1");
    }

    /**
     * Container's getter for getMenuAccessforMenuROVO1.
     * @return getMenuAccessforMenuROVO1
     */
    public ViewObjectImpl getgetMenuAccessforMenuROVO1() {
        return (ViewObjectImpl)findViewObject("getMenuAccessforMenuROVO1");
    }
    
    
    public String CheckUserNaname(String userName) {
        String retValu = "invalid";
        Map<Object, Object> sessionMap = ADFContext.getCurrent().getSessionScope();
        sessionMap.put("userName", "dineshkumar.p@4iapps.com");
        String userRole = null;

        if (userName != null) {
    //            String inputEncodedText = jwt;
                //ADFContext.getCurrent().getPageFlowScope().get("tokens").toString();
            try {
    //                String[] xIn = inputEncodedText.split("\\.");
    //                byte b[] = Base64.decode(xIn[1]);
    //                String tempPass = new String(b);
    //                int chkOccurance = tempPass.lastIndexOf("}");
    //                if (chkOccurance < 1) {
    //                    tempPass += "}";
    //                }
    //                JSONObject jo;
    //
    //                jo = new JSONObject(tempPass);
                //// System.err.println("userName==>"+userName);
                sessionMap.put("userName", userName);
                String NumberFormat = "###,###,###,###";
                String DateFormat = "dd-MMM-yyyy";
                String currencyFormat = "ar_AE";
                Number MinFractionDigits=2;
                Number MaxFractionDigits=2;
                Number MinIntegerDigits=1;
                String codeFirst = currencyFormat.substring(0, currencyFormat.indexOf("_"));
                String codeLast = currencyFormat.substring(currencyFormat.indexOf("_") + 1, currencyFormat.length());
                //TODO commenting hard coded values since PROFILE_VALUED coming from lookup data
                // sessionMap.put("NumberFormat", NumberFormat);
                // sessionMap.put("Currency", new Locale(codeFirst, codeLast));
                // sessionMap.put("DateFormat", DateFormat);

                ViewObject getUserDetaiulsVO = this.getgetUserDetailsROVO1().getViewObject();
                getUserDetaiulsVO.setNamedWhereClauseParam("USER_NAME",
                                                           ADFContext.getCurrent().getSessionScope().get("userName"));
                getUserDetaiulsVO.executeQuery();
               
                if (getUserDetaiulsVO.getEstimatedRowCount() > 0) {
                    Row re = getUserDetaiulsVO.first();
                    if (re.getAttribute("LookupValueName") != null) {
                        userRole = re.getAttribute("LookupValueName").toString();
                      
                    }
                    String grouId=re.getAttribute("DataGroupId")==null?"0":re.getAttribute("DataGroupId").toString();
                  //  ADFContext.getCurrent().getSessionScope().put("MinFractionDigits", MinFractionDigits);
                 //   ADFContext.getCurrent().getSessionScope().put("MaxFractionDigits", MaxFractionDigits);
                    sessionMap.put("userRole", userRole);
                    sessionMap.put("UR", userRole);
                    sessionMap.put("userRoleId", re.getAttribute("UserRoleId").toString());
                    sessionMap.put("UserId", re.getAttribute("UserId").toString());
                    prepareMenu(re.getAttribute("UserRoleId") == null ? "" : re.getAttribute("UserRoleId").toString());
                    sessionMap.put("UserGrpId", grouId);
                    sessionMap.put("navigation", "Dashboard");
                    sessionMap.put("NumberFormat", NumberFormat);
                    sessionMap.put("MinFractionDigits", MinFractionDigits);
                    sessionMap.put("MaxFractionDigits", MaxFractionDigits);
                    sessionMap.put("MinIntegerDigits", MinIntegerDigits);
                    
                    
                    //TODO for testing can remove later
                    //// System.err.println("User Role==" + sessionMap.get("UR"));
//                    // System.err.println("User Role Id==" + sessionMap.get("userRoleId"));
//                    // System.err.println("UserGrpId==" + sessionMap.get("UserGrpId"));
//                    // System.err.println("User Name==" + sessionMap.get("userName"));
//                    // System.err.println("User Id==" + sessionMap.get("UserId"));

                    retValu = "validUser";
                } else {
                    sessionMap.put("navigation", "InvalidUser");
                    retValu = "invalid";
                }
            }  catch (Exception e) {
                //System.out.println("caught by common exception");
            } 
        } else {
            
        }
        return "validUser";
    }



    public void prepareMenu(String userRole) {
        
        Map grantMap = new HashMap();

        try {
            if (userRole != null) {
//                // System.err.println("userRole==>"+userRole);
                ViewObject getMenuAccessVo = this.getgetMenuAccessforMenuROVO1().getViewObject();
                getMenuAccessVo.setNamedWhereClauseParam("USER_ROLE", userRole);
                getMenuAccessVo.executeQuery();

                if (getMenuAccessVo.getEstimatedRowCount() > 0) {
                    RowSetIterator rs = getMenuAccessVo.createRowSetIterator("");
                    try {
                        while (rs.hasNext()) {
                            Row re = rs.next();
                            //Row re=rs.first();
                            grantMap.put(re.getAttribute("SubMenuName"), re.getAttribute("MenuControl"));
//                            // System.err.println(""+re.getAttribute("SubMenuName")+"=="+re.getAttribute("MenuControl"));
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
//                    // System.err.println("==grantMap==");

//                    #{sessionScope.grantMap.Property =='F'}
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
                } else {
                    // JSFUtils.addFacesInformationMessage("This User has not having a any Access..!");
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void commitAttachment() {
        this.getDBTransaction().commit();
        
    }

    /**
     * Container's getter for AppovalTypeROVO1.
     * @return AppovalTypeROVO1
     */
    public ViewObjectImpl getAppovalTypeROVO1() {
        return (ViewObjectImpl)findViewObject("AppovalTypeROVO1");
    }

    /**
     * Container's getter for XxplChecklist_VO1.
     * @return XxplChecklist_VO1
     */
    public ViewObjectImpl getXxplChecklist_VO() {
        return (ViewObjectImpl)findViewObject("XxplChecklist_VO");
    }

    /**
     * Container's getter for GetID_Rovo1.
     * @return GetID_Rovo1
     */
    public ViewObjectImpl getGetID_Rovo1() {
        return (ViewObjectImpl)findViewObject("GetID_Rovo1");
    }
}
