package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;
import custom.lease.custom.view.beans.Utils.ADFUtils;

import custom.lease.custom.view.beans.Utils.DBUtils;
import custom.lease.custom.view.beans.Utils.JSFUtils;

import custom.lease.custom.view.beans.Utils.MailServices;

import custom.lease.custom.view.beans.Utils.MailTemplate;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.text.DecimalFormat;

import java.util.ArrayList;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class AddEditPriceList {

    private RichPopup p1;
    private RichTable table;
    private RichDialog dialog;
    private RichInputText amount;
    private RichOutputText othercharges;
    public RichCommandButton helper;
    private RichCommandButton heping;
    private RichSelectOneChoice type;
    private RichPopup approvePop;
    private RichInputText onClickApprove;
    private RichInputText approveComments;
    private RichPopup rejectPopup;
    private RichInputText rejectComments;
    private RichPanelGroupLayout ppg1;
    private RichPopup p4;
    private RichSelectOneChoice chargeMethod;
    private RichPopup p5;


    public void onChangeDialog(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);

        }
    }
    
    ViewObject priccelistVO=ADFUtils.findIterator("XxplHeader1Iterator").getViewObject();
    
    ViewObject ApprovalTypeVO=com.view.uiutils.ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();
    
    ViewObject GetDataVO=com.view.uiutils.ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
    

//    public String onClickSaveAndClose() {
//        String pricelist = "PL-";
//        Object pricenum = ADFUtils.getBoundAttributeValue("PlId");
//        Object pricenumber = pricelist + pricenum;
//        if (ADFUtils.getBoundAttributeValue("PlNumber") != null) {
//            ADFUtils.setBoundAttributeValue("Status", "Available");
//            JSFUtils.addFacesInformationMessage("Saved Successfully");
//            return "save";
//        } else {
//            ADFUtils.setBoundAttributeValue("PlNumber", pricenumber);
//            ADFUtils.setBoundAttributeValue("Status", "Available");
//            JSFUtils.addFacesInformationMessage("Saved Successfully");
//            return "save";
//        }
//
//    }


    public String onClickClose() {
        return "cancel";
    }


    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }


    public void onClickPopUp(PopupFetchEvent popupFetchEvent) {
        if (popupFetchEvent.getLaunchSourceClientId().contains("create")) {
            ADFUtils.findOperation("CreateInsert").execute();
        }

    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public void onChangePriceListAmount(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processDecodes(FacesContext.getCurrentInstance());
        BigDecimal AreaSqt =
            (BigDecimal)ADFUtils.getBoundAttributeValue("AreaSqFtTrans");
        BigDecimal totalAmount = (BigDecimal)valueChangeEvent.getNewValue();
        if (totalAmount != null && AreaSqt != null &&
            AreaSqt.compareTo(BigDecimal.ZERO) > 0) {
            ADFUtils.setBoundAttributeValue("BasePrice",totalAmount.divide(AreaSqt, 2,RoundingMode.HALF_UP));
            ADFUtils.setBoundAttributeValue("MinPrice",totalAmount.divide(AreaSqt, 2,RoundingMode.HALF_UP));
        } else {
            ADFUtils.setBoundAttributeValue("BasePrice", new BigDecimal(0));
        }
    }

    public String sumbitAction() {
        
        ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "PRICE_LIST_APR_TYPE");
        ApprovalTypeVO.executeQuery();
        String approveType=ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
        ActionEvent a = null;
        onClickSave(a);
        String P_FUNC_ID=priccelistVO.getCurrentRow().getAttribute("FuncId")==null?"5":priccelistVO.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=priccelistVO.getCurrentRow().getAttribute("PlId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PlId").toString();
        String Attribute1_Org=priccelistVO.getCurrentRow().getAttribute("OrgId")==null?"0":priccelistVO.getCurrentRow().getAttribute("OrgId").toString();
        String P_ATTRIBUTE2=null;
        String P_ATTRIBUTE3=null;
        String P_ATTRIBUTE4=null;
        String P_ATTRIBUTE5=null;
        String ccAddress=priccelistVO.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":priccelistVO.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api") || ccAddress.equalsIgnoreCase("PRISM")){
            ccAddress="prasenjit.d@4iapps.com";
        }
//      String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String PriceNumber=priccelistVO.getCurrentRow().getAttribute("PlNumber")==null?"Error-000":priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
        String orgid=priccelistVO.getCurrentRow().getAttribute("OrgId")==null?"Error BU":priccelistVO.getCurrentRow().getAttribute("OrgId").toString();
        String BUName="ABC";
        
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", orgid);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+orgid;
        }
//            JSFUtils.resolveExpression("#{bindings.OrgId.attributeValue}").toString();
        ArrayList <String> list = new ArrayList<String>();
        // System.err.println("approveType=="+approveType);
        if(approveType.equalsIgnoreCase("AUTO")){
            ADFUtils.setBoundAttributeValue("Status", "Approved");
            custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully and Approved");
        // auto if end 
        }else if (approveType.equalsIgnoreCase("ADF")){
            list=PackageCalling.invokeSubmit(
                            P_FUNC_ID, 
                            P_REF_ID, 
                            BaseDetail.initialLevel, 
                            BaseDetail.PriceListHdrTableName, 
                            BaseDetail.PriceListStatusColumnName, 
                            BaseDetail.PriceListPkColumnName, 
                            BaseDetail.initialAmount, 
                            Attribute1_Org, 
                            P_ATTRIBUTE2, 
                            P_ATTRIBUTE3, 
                            P_ATTRIBUTE4, 
                            P_ATTRIBUTE5
                            );
            if(list.get(3)!=null){
//            String toAddress=list.get(3)==null?"prasenjit.d@4iapps.com":list.get(3).toString();
//            String subject=BaseDetail.PriceListSubjects+" Submitted for Approval "+"Price List Number"+priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
//            String mailContent=MailTemplate.priceListMailContent("Price List", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//            String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
            JSFUtils.addFacesInformationMessage("Price List Submitted for Approval");
//            JSFUtils.addFacesInformationMessage("To Address"+toAddress);
//            JSFUtils.addFacesInformationMessage("Cc Address"+ccAddress);
//            JSFUtils.addFacesInformationMessage(mailFlag);
                
            }else{
                JSFUtils.addFacesErrorMessage("Error: Mail Notification Missing");
                JSFUtils.addFacesErrorMessage("Mail ID          :" +list.get(3));
                JSFUtils.addFacesErrorMessage("Error Code       :" +list.get(5));
                JSFUtils.addFacesErrorMessage("Error Message    :" +list.get(6));
                JSFUtils.addFacesErrorMessage("Status           :" +list.get(8));
            }
        // adf if end 
        }else if(approveType.equalsIgnoreCase("PCS")){
            
        }else{
            JSFUtils.addFacesErrorMessage("Approved Type is missing."); 
        }
        
        return "save";
    }

//    public String revisonAction() {
//        String pricenum =ADFUtils.getBoundAttributeValue("PlId")==null?"":ADFUtils.getBoundAttributeValue("PlId").toString();
//         String a=PackageCalling.createRevisionPriceList(pricenum);
//        // System.err.println("PRINT-->"+a);
////        ADFUtils.setBoundAttributeValue("Status", "Revision");
//        ADFUtils.findOperation("Commit").execute();
//        priccelistVO.clearCache();
//        JSFUtils.addFacesInformationMessage("Revisied  Successfully");
//        return null;
//    }

    public void onClickAdd(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateInert").execute();
    }

    public void cancelListerner(PopupCanceledEvent popupCanceledEvent) {
        if (dialog.getType().equals("Cancel")) {
            ViewObject vo =
                ADFUtils.findIterator("XxplLineVO1Iterator").getViewObject();

            vo.removeCurrentRow();
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);

        }

    }

    public void setDialog(RichDialog dialog) {
        this.dialog = dialog;
    }

    public RichDialog getDialog() {
        return dialog;
    }
         
    public void onChangeChargeType(ValueChangeEvent valueChangeEvent) {
        ViewObject vo =
            ADFUtils.findIterator("XxplOtherChargesIterator").getViewObject();
        vo.getCurrentRow().setAttribute("Charges",BigDecimal.ZERO) ;
        vo.getCurrentRow().setAttribute("Amount",BigDecimal.ZERO) ;
        otherCharges();
    }

    public void onChangeAmount(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        // System.err.println("cslled");
        ViewObject vo =ADFUtils.findIterator("XxplOtherChargesIterator").getViewObject();
        // System.err.println("ChargeMethod=="+vo.getCurrentRow().getAttribute("ChargeMethod"));
        if (valueChangeEvent.getNewValue() != null &&
            vo.getCurrentRow().getAttribute("ChargeMethod") != null) {
            String type =
                (String)vo.getCurrentRow().getAttribute("ChargeMethod");
            BigDecimal charge = (BigDecimal)valueChangeEvent.getNewValue();
            // System.err.println("ase" +ADFUtils.getBoundAttributeValue("BasePrice"));
            if (ADFUtils.getBoundAttributeValue("BasePrice") != null) {

                // System.err.println("type====" + type);
                if (type.equalsIgnoreCase("%")) {
                    BigDecimal baseamount =
                        (BigDecimal)ADFUtils.getBoundAttributeValue("BasePrice");

                    if (baseamount.compareTo(BigDecimal.ZERO) > 0) {
                        vo.getCurrentRow().setAttribute("Amount",
                                                        charge.multiply(baseamount).divide(new BigDecimal(100)));
                        
                        AdfFacesContext.getCurrentInstance().addPartialTarget(amount);
                    } else {
                        JSFUtils.addFacesWarningMessage("Base Price Is Zero");
                    }

                } else {
                    vo.getCurrentRow().setAttribute("Amount", charge);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amount);
                }

            }
            otherCharges();
        }
        else {
            JSFUtils.addFacesErrorMessage(" Please the Enter Amount.. ");
        }
        

    }

    public void otherCharges() {
        // Add event code here...
            ViewObject vo =
            ADFUtils.findIterator("XxplOtherChargesIterator").getViewObject();
        RowSetIterator row = vo.createRowSetIterator(null);
        BigDecimal sum = new BigDecimal(0);

        while (row.hasNext()) {
            Row r = row.next();
            BigDecimal value = (BigDecimal)r.getAttribute("Amount");
            if(value!=null) {
                sum = sum.add(value);
            }
            else {
                
            }
           
        }
        ADFUtils.setBoundAttributeValue("OtherCharge", sum);
       AdfFacesContext.getCurrentInstance().addPartialTarget(table);
//        refreshPage();
    }

    public void setAmount(RichInputText amount) {
        this.amount = amount;
    }

    public RichInputText getAmount() {
        return amount;
    }

    public void setOthercharges(RichOutputText othercharges) {
        this.othercharges = othercharges;
    }

    public RichOutputText getOthercharges() {
        return othercharges;
    }

    public void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void setHeping(RichCommandButton heping) {
        this.heping = heping;
    }

    public RichCommandButton getHeping() {
        
        return heping;
    }

    public void onChangeType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance()); 
        String returnvalue=checkDuplicate(JSFUtils.resolveExpression("#{row.bindings.ChargeTypeTrans.attributeValue}").toString());
       if(returnvalue.equals("Y")) {
        JSFUtils.addFacesInformationMessage("Selected Charge type is already exist");
        type.setValue("");
         AdfFacesContext.getCurrentInstance().addPartialTarget(type);
    }
    }
    public String checkDuplicate(String Type) {
           String dup = "N";
           try {

               ViewObject vo =
                   ADFUtils.findIterator("XxplOtherChargesIterator").getViewObject();
               Row[] rows =
                   vo.getFilteredRows("ChargeTypeTrans", Type);
               if (rows.length > 1)
                   return dup = "Y";

           } catch (Exception e) {
               e.printStackTrace();
           }
           return dup;
       }

    public void onClickDelete(ActionEvent actionEvent) {
        
        ViewObject vo =
            ADFUtils.findIterator("XxplOtherChargesIterator").getViewObject();
        
       BigDecimal amount = (BigDecimal)vo.getCurrentRow().getAttribute("Amount");
       if(amount!=null  && ADFUtils.getBoundAttributeValue("OtherCharge")!=null)
       {
        BigDecimal lineamount = (BigDecimal)ADFUtils.getBoundAttributeValue("OtherCharge");
        ADFUtils.setBoundAttributeValue("OtherCharge", amount.subtract(lineamount));
        ADFUtils.findOperation("DeleteLine").execute();
           AdfFacesContext.getCurrentInstance().addPartialTarget(table);
       }
    }


    public void setType(RichSelectOneChoice type) {
        this.type = type;
    }

    public RichSelectOneChoice getType() {
        return type;
    }

    public void onClickSave(ActionEvent actionEvent) {
        String page=ADFContext.getCurrent().getPageFlowScope().get("action")==null?"ABC":ADFContext.getCurrent().getPageFlowScope().get("action").toString();
        if(page.equalsIgnoreCase("Add")){
            String pricelist = "PL-";
            Object pricenum = ADFUtils.getBoundAttributeValue("PlId");
            Object pricenumber = pricelist + pricenum;
            ADFUtils.setBoundAttributeValue("PlNumber", pricenumber);
//            ADFUtils.setBoundAttributeValue("Status", "Available");
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
            ADFUtils.findOperation("Commit").execute();
//            priccelistVO.clearCache();
            
        }else{
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
            ADFUtils.findOperation("Commit").execute();
//            priccelistVO.clearCache();
            
        }
    }

    public void onClickApprove(ActionEvent actionEvent) {
        String Comments=approveComments.getValue()==null?"Approved":approveComments.getValue().toString();
        String P_FUNC_ID=priccelistVO.getCurrentRow().getAttribute("FuncId")==null?"5":priccelistVO.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=priccelistVO.getCurrentRow().getAttribute("PlId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PlId").toString();
        String ccAddress=priccelistVO.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":priccelistVO.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")){
            ccAddress="prasenjit.d@4iapps.com";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        if(userlogin.equalsIgnoreCase("api")){
            userlogin="prasenjit.d@4iapps.com";
        }
        String PriceNumber=priccelistVO.getCurrentRow().getAttribute("PlNumber")==null?"Error-000":priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
        String userGroupID=priccelistVO.getCurrentRow().getAttribute("UserGrpId")==null?"0":priccelistVO.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=priccelistVO.getCurrentRow().getAttribute("FlowLevel")==null?"0":priccelistVO.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=priccelistVO.getCurrentRow().getAttribute("FlowWith")==null?"0":priccelistVO.getCurrentRow().getAttribute("FlowWith").toString();
//        String BUName=priccelistVO.getCurrentRow().getAttribute("orgTransIdId")==null?"Error BU":priccelistVO.getCurrentRow().getAttribute("orgTransIdId").toString();
        String BUName="ABCD";
//            JSFUtils.resolveExpression("#{row.bindings.OrgId.attributeValue}").toString();
        String ArStatus="APPROVE";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
            JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, P_REF_ID, userGroupID, levelNo, approveId, 
                                                     Comments, ArStatus, P_FWD_TO, 
                                                     BaseDetail.PriceListHdrTableName, 
                                                     BaseDetail.PriceListStatusColumnName, 
                                                     BaseDetail.PriceListPkColumnName, 
                                                     BaseDetail.initialAmount);
            if(list.get(0)!=null){
                //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Approved")) {
                    JSFUtils.addFacesInformationMessage("Price List Approved Successfully");
                }else{
                    JSFUtils.addFacesInformationMessage("Price List Approved Successfully and resubmitted to next level user");
//                    String toAddress=list.get(2)==null?"prasenjit.d@4iapps.com":list.get(2).toString();
//                    String subject=BaseDetail.PriceListSubjects+" Submitted for Approval "+"Price List Number"+priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
//                    ccAddress=ccAddress+","+userlogin;
//                    String mailContent=MailTemplate.priceListMailContent("Price List", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//                    String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
//                    JSFUtils.addFacesInformationMessage(mailFlag);
//                    JSFUtils.addFacesInformationMessage("To Address"+toAddress);
//                    JSFUtils.addFacesInformationMessage("Cc Address"+ccAddress);
                }
            }else{
                JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));
            }
        }
    }

    public void setApprovePop(RichPopup approvePop) {
        this.approvePop = approvePop;
    }

    public RichPopup getApprovePop() {
        return approvePop;
    }

    public void setOnClickApprove(RichInputText onClickApprove) {
        this.onClickApprove = onClickApprove;
    }

    public RichInputText getOnClickApprove() {
        return onClickApprove;
    }

    public void setApproveComments(RichInputText approveComments) {
        this.approveComments = approveComments;
    }

    public RichInputText getApproveComments() {
        return approveComments;
    }


    public void onClickApprovePopCancel(ActionEvent actionEvent) {
        approveComments.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(approveComments);
        approvePop.cancel();
    }

    public void setRejectPopup(RichPopup rejectPopup) {
        this.rejectPopup = rejectPopup;
    }

    public RichPopup getRejectPopup() {
        return rejectPopup;
    }

    public void setRejectComments(RichInputText rejectComments) {
        this.rejectComments = rejectComments;
    }

    public RichInputText getRejectComments() {
        return rejectComments;
    }

    public void onClickReject(ActionEvent actionEvent) {
        String Comments=approveComments.getValue()==null?"Rejected":approveComments.getValue().toString();
        String P_FUNC_ID=priccelistVO.getCurrentRow().getAttribute("FuncId")==null?"5":priccelistVO.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=priccelistVO.getCurrentRow().getAttribute("PlId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PlId").toString();
        String ccAddress=priccelistVO.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":priccelistVO.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")){
            ccAddress="prasenjit.d@4iapps.com";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        if(userlogin.equalsIgnoreCase("api")){
            userlogin="prasenjit.d@4iapps.com";
        }
        String PriceNumber=priccelistVO.getCurrentRow().getAttribute("PlNumber")==null?"Error-000":priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
        String userGroupID=priccelistVO.getCurrentRow().getAttribute("UserGrpId")==null?"0":priccelistVO.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=priccelistVO.getCurrentRow().getAttribute("FlowLevel")==null?"0":priccelistVO.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=priccelistVO.getCurrentRow().getAttribute("FlowWith")==null?"0":priccelistVO.getCurrentRow().getAttribute("FlowWith").toString();
//        String BUName=priccelistVO.getCurrentRow().getAttribute("orgTransIdId")==null?"Error BU":priccelistVO.getCurrentRow().getAttribute("orgTransIdId").toString();
        String BUName="ABCDE";
//            JSFUtils.resolveExpression("#{row.bindings.OrgId.attributeValue}").toString();
        String ArStatus="REJECT";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
            JSFUtils.addFacesErrorMessage("Error in Rejected Configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, P_REF_ID, userGroupID, levelNo, approveId, 
                                                     Comments, ArStatus, P_FWD_TO, 
                                                     BaseDetail.PriceListHdrTableName, 
                                                     BaseDetail.PriceListStatusColumnName, 
                                                     BaseDetail.PriceListPkColumnName, 
                                                     BaseDetail.initialAmount);
            if(list.get(0)!=null){
                //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Rejected")) {
                    JSFUtils.addFacesInformationMessage("Price List Rejected Successfully");
//                    String to_Address=ccAddress;
//                    String cc_Address=ccAddress+","+userlogin;
//                    String subject=BaseDetail.PriceListSubjects+" Rejected "+"Price List Number"+priccelistVO.getCurrentRow().getAttribute("PlNumber").toString();
//                    String mailContent=MailTemplate.priceListMailContent("Price List", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Rejected");
//                    String  mailFlag=MailServices.sendMailWithoutAttachment(to_Address, cc_Address, "NULL", subject, mailContent);
//                    JSFUtils.addFacesInformationMessage(mailFlag);
//                    JSFUtils.addFacesInformationMessage("To Address"+to_Address);
//                    JSFUtils.addFacesInformationMessage("Cc Address"+cc_Address);
                }else{
                    
                }
            }else{
                JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));
            }
        }
    }

    public void onClickRejectPopCancel(ActionEvent actionEvent) {
        rejectComments.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rejectComments);
        rejectPopup.cancel();
    }

    public void onClickRevision(ActionEvent actionEvent) {
        String pricenum =ADFUtils.getBoundAttributeValue("PlId")==null?"0":ADFUtils.getBoundAttributeValue("PlId").toString();
         String a=PackageCalling.createRevisionPriceList(pricenum);
        // System.err.println("PRINT-->"+a);
        //        ADFUtils.setBoundAttributeValue("Status", "Revision");
        ADFUtils.findOperation("Commit").execute();
//        priccelistVO.executeQuery();
//        AdfFacesContext.getCurrentInstance().addPartialTarget(ppg1);
        ViewCriteria vc=priccelistVO.createViewCriteria();
         ViewCriteriaRow vcr=vc.createViewCriteriaRow();
         vcr.setAttribute("PlId", pricenum);
         vc.add(vcr);
         priccelistVO.applyViewCriteria(vc);
         priccelistVO.executeQuery();
         JSFUtils.addFacesInformationMessage("Revisied  Successfully");
     }

    public void setPpg1(RichPanelGroupLayout ppg1) {
        this.ppg1 = ppg1;
    }

    public RichPanelGroupLayout getPpg1() {
        return ppg1;
    }

    public void onClickApprovalHistory(ActionEvent actionEvent) {
        String plid=JSFUtils.resolveExpression("#{bindings.PlId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.PlId.inputValue}").toString();
        String fuctionid=JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}").toString();
        ADFContext.getCurrent().getSessionScope().put("plid",plid);
        ADFContext.getCurrent().getSessionScope().put("fuctionid",fuctionid);
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
            this.getP4().show(hints);
           
        

    }

    public void setP4(RichPopup p4) {
        this.p4 = p4;
    }

    public RichPopup getP4() {
        return p4;
    }

    public void setChargeMethod(RichSelectOneChoice chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    public RichSelectOneChoice getChargeMethod() {
        return chargeMethod;
    }

    public void setP5(RichPopup p5) {
        this.p5 = p5;
    }

    public RichPopup getP5() {
        return p5;
    }

    public void onClickCreateUnit(ActionEvent actionEvent) {
        
        String property_id=priccelistVO.getCurrentRow().getAttribute("PropertyId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PropertyId").toString();
        String building_id=priccelistVO.getCurrentRow().getAttribute("BuildId")==null?"0":priccelistVO.getCurrentRow().getAttribute("BuildId").toString();
        
        ViewObjectImpl vo=(ViewObjectImpl)ADFUtils.findIterator("SearchUnitFilterROVO1Iterator").getViewObject();
        //View Criteria with bind variable 'Bind_deptId'
        vo.setApplyViewCriteriaName("FindByPropertyAndBuilding");
        vo.setNamedWhereClauseParam("p_property_id", property_id);
        vo.setNamedWhereClauseParam("p_build_id", building_id);
        vo.executeQuery();
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        this.getP5().show(hints);
    }

ViewObjectImpl searchUnitVO=(ViewObjectImpl)ADFUtils.findIterator("SearchUnitFilterROVO1Iterator").getViewObject();
ViewObject priccelistDtlVO=ADFUtils.findIterator("XxplLineVO1Iterator").getViewObject();

    public void onClickPLUnitAdd(ActionEvent actionEvent) {
        // Add event code here...
        String p_PlId=priccelistVO.getCurrentRow().getAttribute("PlId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PlId").toString();
        String property_id=priccelistVO.getCurrentRow().getAttribute("PropertyId")==null?"0":priccelistVO.getCurrentRow().getAttribute("PropertyId").toString();
        String building_id=priccelistVO.getCurrentRow().getAttribute("BuildId")==null?"0":priccelistVO.getCurrentRow().getAttribute("BuildId").toString();
        Object startDate=priccelistVO.getCurrentRow().getAttribute("StartDate");
        Object endDate=priccelistVO.getCurrentRow().getAttribute("EndDate");
        RowSetIterator plRs=searchUnitVO.createRowSetIterator(null);
            while(plRs.hasNext()){
            Row r=plRs.next();
                String flag=r.getAttribute("CheckFlag")==null?"ABC":r.getAttribute("CheckFlag").toString();
                int amt=r.getAttribute("SellingAmount")==null?0:Integer.parseInt(r.getAttribute("SellingAmount").toString());
                //
                BigDecimal selling_amt=new BigDecimal(amt);
                if(flag.equalsIgnoreCase("true") && amt>0){
//                    System.out.println("flag==>"+flag+"==>amt==>"+amt);
                    String unit_id=r.getAttribute("UnitId")==null?"0":r.getAttribute("UnitId").toString();
                    String allotType=r.getAttribute("AllotType")==null?"0":r.getAttribute("AllotType").toString();
                    String uom=r.getAttribute("Attribute1")==null?"SQFT":r.getAttribute("Attribute1").toString();
                    String unitNumTrans=r.getAttribute("UnitNumber")==null?"001":r.getAttribute("UnitNumber").toString();
                    String areaVal=r.getAttribute("FuncId")==null?"0":r.getAttribute("FuncId").toString();
                    BigDecimal area_in_sf=new BigDecimal(areaVal);
                    Row pldtlRow=priccelistDtlVO.createRow();
                        pldtlRow.setAttribute("PlId", p_PlId);
                        pldtlRow.setAttribute("PropertyId", property_id);
                        pldtlRow.setAttribute("BuildId", building_id);
                        pldtlRow.setAttribute("UnitId", unit_id);
                        pldtlRow.setAttribute("Uom", "SQFT");
                        if(allotType.equalsIgnoreCase("PAID") || area_in_sf.compareTo(BigDecimal.ZERO) == 0){
                            pldtlRow.setAttribute("BasePrice", 0);
                            pldtlRow.setAttribute("MinPrice", 0);
//                            pldtlRow.setAttribute("Attribute2", 0);
                        }else{
                            pldtlRow.setAttribute("BasePrice", selling_amt.divide(area_in_sf, 2, RoundingMode.HALF_UP));
                            pldtlRow.setAttribute("MinPrice", selling_amt.divide(area_in_sf, 2, RoundingMode.HALF_UP));
//                            pldtlRow.setAttribute("Attribute2", 0);
                        }
                        pldtlRow.setAttribute("PlAmount", selling_amt);
                        pldtlRow.setAttribute("StartDate", startDate);
                        pldtlRow.setAttribute("EndDate", endDate);
                        pldtlRow.setAttribute("ActiveYn", "Y");
                        pldtlRow.setAttribute("UnitNameTrans", unitNumTrans);
                        pldtlRow.setAttribute("AreaSqFtTrans", areaVal);
                    priccelistDtlVO.insertRow(pldtlRow);
//                   custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
                }
        }
        this.getP5().cancel(); 
        priccelistDtlVO.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void onClickPLCancel(ActionEvent actionEvent) {

        this.getP5().cancel();
    }
}
