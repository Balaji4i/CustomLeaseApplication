package custom.lease.custom.view.beans.filmStrip;


import com.sun.org.apache.xml.internal.security.utils.Base64;


import com.view.beans.filmStrip.SessionState;
import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.event.PhaseId;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
//import oracle.adf.model.BindingContext;
//import oracle.adf.model.OperationBinding;
//import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.json.JSONObject;


public class FilmStripPhaseListener implements PagePhaseListener {

//    private static final String DATE_FORMAT = "MM/dd/yyyy";
//    private static final String CURRENCY_CODE = "AED";
//    private static final String TIME_FORMAT = "HH24:MT";
//    private static final String TIME_ZONE_FORMAT = "GT";
    private static final String NUMBER_FORMAT = "###,###,###,###"; 
    private static final Number MIN_FRACTION_DIGITS=2;
    private static final Number MAX_FRACTION_DIGITS=2;
    private static final Number MIN_INTEGER_DIGITS=1;
    private String jwt;
    java.util.Map sessionMap = ADFContext.getCurrent().getSessionScope();

    public FilmStripPhaseListener() {
        super();
    }


    public void afterPhase(PagePhaseEvent phaseEvent) {
    }


    public void beforePhase(PagePhaseEvent phaseEvent) {
        //        System.err.println("phaseEvent.getPhaseId()-->"+phaseEvent.getPhaseId());
        //        System.err.println("phaseEvent.getLifecycleContext()-->"+phaseEvent.getLifecycleContext());
        //        System.err.println("Lifecycle.PREPARE_RENDER_ID-->"+Lifecycle.PREPARE_RENDER_ID);

        if (phaseEvent.getPhaseId() != Lifecycle.PREPARE_RENDER_ID) {
            onPageLoad();
            //            ADFContext.getCurrent().getPageFlowScope().put("redirect","Dashboard");
        }
    }

    public PhaseId getPhaseId() {
        return null;
    }

//    private String pagePoit="Error";
//    ADFContext.getCurrent().getPageFlowScope().put("pagePoit","Error");

    public void onPageLoad() {
        
        Map<Object, Object> sessionMap = ADFContext.getCurrent().getSessionScope(); 
        sessionMap.put("NumberFormat", NUMBER_FORMAT);
        sessionMap.put("MinFractionDigits", MIN_FRACTION_DIGITS);
        sessionMap.put("MaxFractionDigits", MAX_FRACTION_DIGITS);
        sessionMap.put("MinIntegerDigits", MIN_INTEGER_DIGITS);
        
        //        System.err.println("isPostback-->"+AdfFacesContext.getCurrentInstance().isPostback());
        if (!AdfFacesContext.getCurrentInstance().isPostback()) {
            String pageRedirect = null;
//             System.err.println("==http Path==>"+ADFContext.getCurrent().getSessionScope().get("httpPath"));
//            System.err.println("==api==>"+ADFContext.getCurrent().getSessionScope().get("userName"));
//            System.err.println("==accessMap==>"+ADFContext.getCurrent().getSessionScope().get("grantMap"));
            if (ADFContext.getCurrent().getSessionScope().get("userName") !=null) {
//                System.err.println("oo--->"+ADFContext.getCurrent().getSessionScope().get("userName"));
//                ADFContext.getCurrent().getSessionScope().get("loginuser");
//                System.err.println("loginEmployeeId-->" +
//                System.err.println("--0-0-0-==>"+ADFContext.getCurrent().getSessionScope().get("loginEmployeeId"));
//                    System.out.println("userName==>"+ADFContext.getCurrent().getSessionScope().get("userName"));
                
//                        DCBindingContainer bindings =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
//                        OperationBinding oper = (OperationBinding)bindings.getControlBinding("CheckUserNaname");
//                        System.err.println("oper=="+oper);
//                        if(oper!=null){
//                            oper.getParamsMap().put("userName", ADFContext.getCurrent().getSessionScope().get("userName"));
//                            String opj = (String)oper.execute();
//                            if (opj != null) {
//                            //                          String a = opj.getBytes().toString();
//                        //                            System.out.println("a===>"+opj);
//                                if(opj.equalsIgnoreCase("validUser")){
//                                    ADFContext.getCurrent().getPageFlowScope().put("redirect","Dashboard");
//                                    
//                                }else{
//                                    ADFContext.getCurrent().getPageFlowScope().put("redirect","Error");
//                                }
//                            }else{
//                                ADFContext.getCurrent().getPageFlowScope().put("c","Error");
//                            }
//                        }
                       ADFContext.getCurrent().getPageFlowScope().put("redirect","Dashboard");

            } else {
                ADFContext.getCurrent().getPageFlowScope().put("redirect","Error");
                //System.err.println("No Token");
                pageRedirect = "invalidUser";
                ADFContext.getCurrent().getPageFlowScope().put("val","invalidUser");
            }

            SessionState sessionState =
                (SessionState)JSFUtils.getManagedBeanValue("sessionScope.SessionState");
            if (sessionState == null) {
                sessionState = new SessionState();
            }
            sessionState.parseRootMenu();
            JSFUtils.setManagedBeanValue("sessionScope.SessionState",
                                         sessionState);
            String groupNodeId = "LeaseModule";
            String itemNodeId = sessionState.getHomePage();
            if (ADFContext.getCurrent().getSessionScope().get("selectedItemId") ==
                null) {
                ADFContext.getCurrent().getSessionScope().put("selectedGroupId", groupNodeId);
                ADFContext.getCurrent().getSessionScope().put("selectedItemId",itemNodeId);
                ADFContext.getCurrent().getSessionScope().put("disableGoHome","N");
                if (groupNodeId.equalsIgnoreCase(itemNodeId)) {
                    ADFContext.getCurrent().getSessionScope().put("hideStrip",true);
                    ADFContext.getCurrent().getSessionScope().put("hideStripToggle",true);
                } else {
                    ADFContext.getCurrent().getSessionScope().put("hideStrip",
                                                                  false);
                    ADFContext.getCurrent().getSessionScope().put("hideStripToggle",
                                                                  false);
                }
            }


        }
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}


