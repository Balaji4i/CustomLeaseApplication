package custom.lease.custom.view.beans.filmStrip;


import com.view.beans.filmStrip.SessionState;
import com.view.beans.filmStrip.UtilsBean;
import com.view.data.ItemNode;

import com.view.uiutils.ADFUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class FilmStripBean {
    private UtilsBean _utils = new UtilsBean();
    private RichRegion dynamicRegionBinding;


    public void handleFilmStripCardClick(ClientEvent clientEvent) {
        _utils.setEL("#{sessionScope.selectedItemId}", clientEvent.getParameters().get("itemNodeId"));
        String ItemNodeId =clientEvent.getParameters().get("itemNodeId").toString();

//        if(ItemNodeId.equals("PropertyMaster")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("PropertyBuilding")){
//            _utils.refreshView();
//        }else 
//          if(ItemNodeId.equals("PaymentPlan")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("PriceList")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("VisitorLog")){
//            _utils.refreshView();
//        }else 
          if(ItemNodeId.equals("Booking"))
          {
            ADFContext.getCurrent().getSessionScope().put("screenName", "Booking");
              _hideFilmStrip();
            }else if(ItemNodeId.equals("Unit"))
          {
              _hideFilmStrip();
            }else if(ItemNodeId.equals("PriceList"))
          {
            ADFContext.getCurrent().getSessionScope().put("screenName", "PriceList");
              _hideFilmStrip();
            }else
// if(ItemNodeId.equals("OtherCharge")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("Billing")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("Transcation")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("Receipt")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("Cancellation")){
//            _utils.refreshView();
//        }else if(ItemNodeId.equals("ARSetup")){
//            _utils.refreshView();
//        }else 
        if (ItemNodeId.equals("MoveIn")) {
           
            ViewObject vo = ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl")
                               .findApplicationModule("MoveInOut_AM1").findViewObject("SearchMoveInOut_ROVO1");
                             vo.setNamedWhereClauseParam("BV_IoFlag","I");
                              vo.executeQuery();
            ADFContext.getCurrent().getSessionScope().put("ScreenName", "Move In");
            ADFContext.getCurrent().getSessionScope().put("MoveInOut", "I");
           
            _utils.refreshView();
        } else if (ItemNodeId.equals("MoveOut")) {
            
            ViewObject vo = ADFUtils.getApplicationModuleForDataControl("customRootAppModuleDataControl")
                              .findApplicationModule("MoveInOut_AM1").findViewObject("SearchMoveInOut_ROVO1");
            vo.setNamedWhereClauseParam("BV_IoFlag","O");
             vo.executeQuery();
            ADFContext.getCurrent().getSessionScope().put("ScreenName","Move Out");
            ADFContext.getCurrent().getSessionScope().put("MoveInOut", "O");
           
            _utils.refreshView();
        }else {
            _utils.refreshView();
        }
        
    
        


        

        //        if(ItemNodeId.equalsIgnoreCase("Cancellation")){
        //         ViewObject vo = ADFUtils.getApplicationModuleForDataControl("NewMoveAppModuleDataControl")
        //            .findViewObject("cancellation_ROVO1");
        //            vo.applyViewCriteria(null);
        //            vo.executeQuery();
        //            _utils.setEL("#{sessionScope.Flag}", "CAN");
        //            _utils.setEL("#{sessionScope.transno}", null);
        //            ADFContext.getCurrent().getSessionScope().put("ScreenName", "Cancellation");
        //        }

        //            String validuser=ADFContext.getCurrent().getSessionScope().get("loginUser")==null?"":ADFContext.getCurrent().getSessionScope().get("loginUser").toString();
        //            String loginEmployeeId=ADFContext.getCurrent().getSessionScope().get("loginEmployeeId")==null?"":ADFContext.getCurrent().getSessionScope().get("loginEmployeeId").toString();
        //            System.err.println("validuser==>"+validuser);
        //            System.err.println("loginEmployeeId==>"+loginEmployeeId);
        //            if(validuser.equalsIgnoreCase("")){
        //
        //            }else{
        //                if(ItemNodeId.equalsIgnoreCase("EmployeeAttendance")){
        //                    ViewObject vo = ADFUtils.getApplicationModuleForDataControl("EmployeeAtendanceAppModule")
        //                    .findViewObject("EmployeeDetailsROVO1");
        //                    ViewCriteria vc=vo.createViewCriteria();
        //                    ViewCriteriaRow vcr=vc.createViewCriteriaRow();
        //                    vcr.setAttribute("EmpId", loginEmployeeId);
        //                    vc.addRow(vcr);
        //                    vcr.setAttribute("EmpNumber", validuser);
        //                    vc.addRow(vcr);
        //                    vo.applyViewCriteria(vc);
        //                    vo.executeQuery();
        //                }
        //            }
        
//        dynamicRegionBinding.refresh(FacesContext.getCurrentInstance());
    }

    public TaskFlowId getDynamicTaskFlowId() {
        TaskFlowId taskFlowId =
            new TaskFlowId("/WEB-INF/4i/apps/uikit/flow/NotImplementedFlow.xml",
                           "NotImplementedFlow");
        String itemId = null;
        String groupId = null;
        if (ADFContext.getCurrent().getSessionScope().get("selectedGroupId") !=
            null)
            groupId =
                    (String)ADFContext.getCurrent().getSessionScope().get("selectedGroupId");
        if (ADFContext.getCurrent().getSessionScope().get("selectedItemId") !=
            null)
            itemId =
                    (String)ADFContext.getCurrent().getSessionScope().get("selectedItemId");
        //
        SessionState sessionState =
            (SessionState)_utils.getSessionScope().get("SessionState");

        ItemNode node = sessionState.getClusterMap().get(itemId);
        //            System.err.println("node"+node);
        if (node != null) {
            String destUrl = node.getDestinationUrl();
            String result = destUrl.substring(0, destUrl.lastIndexOf("."));
            String localTFId = result.substring(destUrl.lastIndexOf("/") + 1);
            taskFlowId = new TaskFlowId(destUrl, localTFId);
        }
        //        System.err.println("taskFlowId -->"+taskFlowId );


        return taskFlowId;
    } //getDynamicTaskFlowId

    public Map getFilmStripLayout() {
        return new HashMap<String, String>() {
            public String get(Object key) {
                try {
                    String groupId = null;
                    String itemId = "";
                    if (ADFContext.getCurrent().getSessionScope().get("selectedGroupId") !=
                        null)
                        groupId =
                                (String)ADFContext.getCurrent().getSessionScope().get("selectedGroupId");
                    if (ADFContext.getCurrent().getSessionScope().get("selectedItemId") !=
                        null)
                        itemId =
                                (String)ADFContext.getCurrent().getSessionScope().get("selectedItemId");
                    //
                    SessionState sessionState =
                        (SessionState)_utils.getSessionScope().get("SessionState");
                    String rootMenuData = sessionState.fetchGridNodes(groupId);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    ExtendedRenderKitService erks =
                        Service.getRenderKitService(fctx,
                                                    ExtendedRenderKitService.class);
                    String js =
                        "filmStripLayoutManager.handleFilmDocumentLoad('" +
                        rootMenuData + "','" + itemId + "');";
                    erks.addScript(fctx, js);
                } catch (Exception e) {
                    e.printStackTrace();
                } //try-catch
                return null;
            } //get
        };
    } //getFilmStripLayout

    public void toggleStripRender(ActionEvent actionEvent) {
        boolean hideStrip =
            (Boolean)_utils.evaluateEL("#{sessionScope.hideStrip}");
        if (hideStrip)
            _utils.setEL("#{sessionScope.hideStrip}", false);
        else
            _utils.setEL("#{sessionScope.hideStrip}", true);
        _utils.refreshView();
    } //toggleStripRender

    public void setDynamicRegionBinding(RichRegion dynamicRegionBinding) {
        this.dynamicRegionBinding = dynamicRegionBinding;
    }

    public RichRegion getDynamicRegionBinding() {
        return dynamicRegionBinding;
    }

    public void _hideFilmStrip() {
        ADFUtils.setEL("#{sessionScope.hideStrip}", (Object)true);
        ADFUtils.setEL("#{sessionScope.hideStripToggle}", (Object)true);
        this.refreshView();
    }


    public void refreshView() {
        final String viewId =
            FacesContext.getCurrentInstance().getViewRoot().getViewId();
        final ViewHandler vh =
            FacesContext.getCurrentInstance().getApplication().getViewHandler();
        final UIViewRoot uivr =
            vh.createView(FacesContext.getCurrentInstance(), viewId);
        FacesContext.getCurrentInstance().setViewRoot(uivr);
    }

    public void _showFilmStrip() {
        ADFUtils.setEL("#{sessionScope.hideStrip}", (Object)false);
        ADFUtils.setEL("#{sessionScope.hideStripToggle}", (Object)false);
        final FacesContext fctx = FacesContext.getCurrentInstance();
        final UIViewRoot vr = fctx.getViewRoot();
        final RichPanelGroupLayout pg =
            (RichPanelGroupLayout)vr.findComponent("pt1:filmStripPG");
        if (pg != null) {
            final AdfFacesContext adfFacesContext =
                AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget((UIComponent)pg);
        }
    }
    
    public void hideFSDropDown(ActionEvent actionEvent) {
            ADFUtils.setEL("#{sessionScope.hideStrip}", true);
            ADFUtils.setEL("#{sessionScope.hideStripToggle}", true);
            FacesContext fctx = FacesContext.getCurrentInstance();
            UIViewRoot vr = fctx.getViewRoot();
            RichPanelGroupLayout pg = (RichPanelGroupLayout)vr.findComponent("pt1:filmStripPG");
            if(pg!=null){
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(pg);
            }
        }
        public void showFSDropDown(ActionEvent actionEvent) {
            ADFUtils.setEL("#{sessionScope.hideStrip}", false);
            ADFUtils.setEL("#{sessionScope.hideStripToggle}", false);
            FacesContext fctx = FacesContext.getCurrentInstance();
            UIViewRoot vr = fctx.getViewRoot();
            RichPanelGroupLayout pg = (RichPanelGroupLayout)vr.findComponent("pt1:filmStripPG");
            if(pg!=null){
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(pg);
            }
        }
    public String getIpAddress() { 
        Context ctx;
        String ipAddress ="";
        Connection con = null;
        try{
        ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("PRISM_PL");
            con = ds.getConnection();
            
            String selectSQL = "SELECT GET_IP_ADDRESS() IP_ADDRESS FROM DUAL"; 
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL); 
            ResultSet rs1 = preparedStatement.executeQuery(selectSQL); 
            while (rs1.next()) {
                ipAddress = rs1.getString("IP_ADDRESS");
            }   
        }
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
        return ipAddress;
    }
    
}
