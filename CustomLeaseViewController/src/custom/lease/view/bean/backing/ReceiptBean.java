package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import java.math.BigDecimal;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class ReceiptBean {
    private RichTable t2;
    private RichSelectOneChoice soc5;
    private RichInputText it13;
    private RichInputText it10;
    private RichInputText it19;
    private RichInputText it23;
    private RichInputComboboxListOfValues invoicenumberTransId;
    private RichInputText it26;
    private RichInputText receivedamt;
    private RichInputText receiptamount;
    private RichPopup p3;
    private RichSelectBooleanCheckbox select;
    private RichTable t4;
    private RichPopup reversePop;
    private RichPopup canInvPopup;
    private RichOutputFormatted newInterfaceStatus;
    private RichPanelLabelAndMessage plam1;


    public ReceiptBean() {
    }
   ViewObject headervo=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
    ViewObject linevo=ADFUtils.findIterator("XxplReceiptLineView1Iterator").getViewObject();
    ViewObject msvo=ADFUtils.findIterator("ReceiptLineMs_ROVO1Iterator").getViewObject();
   
    public void onClickSave(ActionEvent actionEvent) {
        
        String  ReceiptNo="";
        String  addeditscope=ADFContext.getCurrent().getPageFlowScope().get("navigation").toString();
         if(addeditscope.equalsIgnoreCase("add"))
         {   Row r=headervo.getCurrentRow();
             String  primarykey=r.getAttribute("ReceiptId")==null?"":r.getAttribute("ReceiptId").toString();
           ReceiptNo="RC-".concat(primarykey);
           r.setAttribute("ReceiptNumber",ReceiptNo);
           
         }
            String buttonType = actionEvent.getComponent().getAttributes().get("buttonType")!=null ? actionEvent.getComponent().getAttributes().get("buttonType").toString() : null;
                   if(buttonType.equals("save")){
         ADFUtils.findOperation("Commit").execute();
                
           JSFUtils.addFacesInformationMessage("Information saved successfully!!!");
    }
        
        }

    public void OnClickInvoice(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Row currentrow=linevo.getCurrentRow();
        
        String invoiceid=currentrow.getAttribute("InvoiceId")==null?"":currentrow.getAttribute("InvoiceId").toString();
        RowSetIterator itr=linevo.createRowSetIterator(null);
        int count=0;
        while(itr.hasNext())
        {
            Row r=itr.next();
            String tempinvoiceid=r.getAttribute("InvoiceId")==null?"":r.getAttribute("InvoiceId").toString();
            if(invoiceid.equalsIgnoreCase(tempinvoiceid))
            {
                count++;
            }
        }
        itr.closeRowSetIterator();
        if(count>1)
        {   
//           
            currentrow.setAttribute("invoicenumberTrans","");
            currentrow.setAttribute("InvoiceId","");
            currentrow.setAttribute("TransactionApplied","");
            currentrow.setAttribute("TransactionBalance","");
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoicenumberTransId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(it26);
//            AdfFacesContext.getCurrentInstance().addPartialTarget(it10);
            AdfFacesContext.getCurrentInstance().addPartialTarget(it19);
            AdfFacesContext.getCurrentInstance().addPartialTarget(it23);
//            AdfFacesContext.getCurrentInstance().addPartialTarget(t2);
            JSFUtils.addFacesErrorMessage("Invoice is already selected");
        }
    }

    public void setT2(RichTable t2) {
        this.t2 = t2;
    }

    public RichTable getT2() {
        return t2;
    }



    public void setIt13(RichInputText it13) {
        this.it13 = it13;
    }

    public RichInputText getIt13() {
        return it13;
    }

    public void setIt10(RichInputText it10) {
        this.it10 = it10;
    }

    public RichInputText getIt10() {
        return it10;
    }

    public void setIt19(RichInputText it19) {
        this.it19 = it19;
    }

    public RichInputText getIt19() {
        return it19;
    }

    public void setIt23(RichInputText it23) {
        this.it23 = it23;
    }

    public RichInputText getIt23() {
        return it23;
    }

    public void setInvoicenumberTransId(RichInputComboboxListOfValues invoicenumberTransId) {
        this.invoicenumberTransId = invoicenumberTransId;
    }

    public RichInputComboboxListOfValues getInvoicenumberTransId() {
        return invoicenumberTransId;
    }

    public void setIt26(RichInputText it26) {
        this.it26 = it26;
    }

    public RichInputText getIt26() {
        return it26;
    }

    public void onClickAR(ActionEvent actionEvent) {
       Row r=headervo.getCurrentRow();
       String ownerPayment = headervo.getCurrentRow().getAttribute("Attribute4")==null?"":headervo.getCurrentRow().getAttribute("Attribute4").toString();
//       System.out.println("ownerPayment :"+ownerPayment);
       if(isValidaForTransfer()){
           if (!ownerPayment.equalsIgnoreCase("Owner")){
//               System.out.println("ownerPayment 1 :"+ownerPayment);
           r.setAttribute("InterfaceStatus","AR_TRANSFER");
           ADFUtils.findOperation("Commit").execute();
           JSFUtils.addFacesInformationMessage("Applied to transfer AR !!");
           }else{
               JSFUtils.addFacesInformationMessage("Can't transfer to AR as it is a direct owner payment!!");
           }
       } 
    }

    public void onClickAppliedAmount(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
       float sum=0;
       String amt="";
       RowSetIterator itr=linevo.createRowSetIterator(null);
       while(itr.hasNext())
       {
        Row r=itr.next();
         amt=r.getAttribute("AmountApplied")==null?"0":r.getAttribute("AmountApplied").toString();
           sum=sum+Float.parseFloat(amt);
//           // System.err.println("test->"+r.getAttribute("AmountApplied").toString());
       }
       itr.closeRowSetIterator();
       
       
       JSFUtils.setExpressionValue("#{bindings.ReceivedAmount.inputValue}",new BigDecimal(sum));
        JSFUtils.setExpressionValue("#{bindings.ReceiptAmount.inputValue}",new BigDecimal(sum));
       AdfFacesContext.getCurrentInstance().addPartialTarget(receivedamt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptamount);
    }

    public void setReceivedamt(RichInputText receivedamt) {
        this.receivedamt = receivedamt;
    }

    public RichInputText getReceivedamt() {
        return receivedamt;
    }

    public void setReceiptamount(RichInputText receiptamount) {
        this.receiptamount = receiptamount;
    }

    public RichInputText getReceiptamount() {
        return receiptamount;
    }

    public void onClickMS(ActionEvent actionEvent) {
        String bu=JSFUtils.resolveExpression("#{bindings.OrgId.inputValue}").toString();
        String bookingid=JSFUtils.resolveExpression("#{bindings.SourceFuncRefId.inputValue}").toString();
        String buttonType = actionEvent.getComponent().getAttributes().get("buttonType")!=null ? actionEvent.getComponent().getAttributes().get("buttonType").toString() : null;
                if(buttonType.equals("MS"))
                {
                    msvo.setNamedWhereClauseParam("BV_orgid",bu);
                    msvo.setNamedWhereClauseParam("BV_bookingid",bookingid);
                    msvo.setNamedWhereClauseParam("BV_MStype","MS"); 
                  ADFContext.getCurrent().getPageFlowScope().put("button","Milestone Information");
                }
                else if(buttonType.equals("CP"))
                {
                    msvo.setNamedWhereClauseParam("BV_orgid",bu);
                    msvo.setNamedWhereClauseParam("BV_bookingid",bookingid);
                    msvo.setNamedWhereClauseParam("BV_MStype","CP");
                    ADFContext.getCurrent().getPageFlowScope().put("button","Car Parking Information");
                }
                else if(buttonType.equals("OC"))
                {
                    msvo.setNamedWhereClauseParam("BV_orgid",bu);
                    msvo.setNamedWhereClauseParam("BV_bookingid",bookingid);
                    msvo.setNamedWhereClauseParam("BV_MStype","OC"); 
                    ADFContext.getCurrent().getPageFlowScope().put("button","OtherCharges Information");
                }
        

        msvo.executeQuery();
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        this.getP3().show(hints);
    }

    public void setP3(RichPopup p3) {
        this.p3 = p3;
    }

    public RichPopup getP3() {
        return p3;
    }


    public void onClickOk(ActionEvent actionEvent) {

        RowSetIterator itr=msvo.createRowSetIterator(null);
        while(itr.hasNext())
        {
        Row row=itr.next();
            String select=row.getAttribute("Select_trans")==null?"N":row.getAttribute("Select_trans").toString();
        // System.err.println("select"+row.getAttribute("Select_trans"));
            if(select.equalsIgnoreCase("Y")){
            Row r=linevo.createRow();
                           r.setAttribute("BookingMsDtlId",row.getAttribute("BookingMsDtlId"));
                           r.setAttribute("ChargeType_trans",row.getAttribute("ChargeTypeDisp"));
                           r.setAttribute("MocName_Trans",row.getAttribute("MocName"));
                           r.setAttribute("TransactionAmountTrans",row.getAttribute("InstallmentAmount"));
                           r.setAttribute("TransactionApplied",row.getAttribute("Amtapplied"));
                           r.setAttribute("TransactionBalance",row.getAttribute("Balanceamt"));
                            r.setAttribute("SourcesFunId",row.getAttribute("SourcesFunId"));
                            r.setAttribute("SourcesRefDtlId",row.getAttribute("SourcesRefDtlId"));
                            r.setAttribute("SourcesRefId",row.getAttribute("SourcesRefId"));
                           linevo.insertRow(r);
            }
        }
        itr.closeRowSetIterator();
        //       
        linevo.executeQuery();
    }

    public void onClickSelect(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
       
                if(ADFContext.getCurrent().getPageFlowScope().get("button").equals("Milestone Information"))
                {
        Row r=msvo.getCurrentRow();
        int count=1;
        String id=r.getAttribute("BookingMsDtlId")==null?"":r.getAttribute("BookingMsDtlId").toString();
        // System.err.println("Print->"+id);
        // System.err.println("Count-->"+msvo.getEstimatedRowCount());
        RowSetIterator itr=msvo.createRowSetIterator(null);
        while(itr.hasNext())
        {
            Row rr=itr.next();
           if(id.equalsIgnoreCase(rr.getAttribute("BookingMsDtlId").toString()))
           {
               rr.setAttribute("Select_trans","Y");
               // System.err.println("Count-->"+count++);
           }
            else
           {
                rr.setAttribute("Select_trans","N");  
                
            }
            
        }
        itr.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(t4);
                }
  
    }

    public void setSelect(RichSelectBooleanCheckbox select) {
        this.select = select;
    }

    public RichSelectBooleanCheckbox getSelect() {
        return select;
    }

    public void setT4(RichTable t4) {
        this.t4 = t4;
    }

    public RichTable getT4() {
        return t4;
    }


    public void onClickRevision(ActionEvent actionEvent) {
        String receiptid=ADFUtils.getBoundAttributeValue("ReceiptId")==null?"":ADFUtils.getBoundAttributeValue("ReceiptId").toString();
        PackageCalling.createRevisionReceipt(receiptid);
//        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Revised Successfully!!!");
    }

    private boolean isValidaForTransfer() { 
        Row r=headervo.getCurrentRow(); 
        String receiptId = r.getAttribute("ReceiptId")!=null?r.getAttribute("ReceiptId").toString():"0";
        String receiptNumber = r.getAttribute("ReceiptNumber")!=null?r.getAttribute("ReceiptNumber").toString():"0";
        String receiptDate = r.getAttribute("ReceiptDate")!=null?r.getAttribute("ReceiptDate").toString():"0";
        String matDate = r.getAttribute("MaturityDate")!=null?r.getAttribute("MaturityDate").toString():"0";
        String glDate = r.getAttribute("GlDate")!=null?r.getAttribute("GlDate").toString():"0";
        String orgId = r.getAttribute("OrgId")!=null?r.getAttribute("OrgId").toString():"0";
        String custId = r.getAttribute("CustId")!=null?r.getAttribute("CustId").toString():"0";
        String siteId = r.getAttribute("CustSiteId")!=null?r.getAttribute("CustSiteId").toString():"0";
        String currency = r.getAttribute("CurrencyCode")!=null?r.getAttribute("CurrencyCode").toString():"0";
        String receMethId = r.getAttribute("ReceiptMethodId")!=null?r.getAttribute("ReceiptMethodId").toString():"0";
        String remitId = r.getAttribute("RemitBankAcctUseId")!=null?r.getAttribute("RemitBankAcctUseId").toString():"0";
        String amt = r.getAttribute("ReceiptAmount")!=null?r.getAttribute("ReceiptAmount").toString():"0";
        String prop = r.getAttribute("PropertyId")!=null?r.getAttribute("PropertyId").toString():"0";
        String build = r.getAttribute("BuildId")!=null?r.getAttribute("BuildId").toString():"0";
        String unit = r.getAttribute("UnitId")!=null?r.getAttribute("UnitId").toString():"0";
        String srcFunc = r.getAttribute("SourceFuncId")!=null?r.getAttribute("SourceFuncId").toString():"0";
        String srcFuctRef = r.getAttribute("SourceFuncRefId")!=null?r.getAttribute("SourceFuncRefId").toString():"0";
        if("0".equals(receiptId)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt id missing !!");
            return false;
        }
        if("0".equals(receiptNumber)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt No missing !!");
            return false;
        }
        if("0".equals(matDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Maturity Date missing !!");
            return false;
        }
        if("0".equals(glDate)){
            JSFUtils.addFacesErrorMessage("Please Check, GL Date missing !!");
            return false;
        }
        if("0".equals(receiptDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt Date missing !!");
            return false;
        }
        if("0".equals(orgId)){
            JSFUtils.addFacesErrorMessage("Please Check, Business unit missing !!");
            return false;
        }
        if("0".equals(custId)){
            JSFUtils.addFacesErrorMessage("Please Check, Customer information missing !!");
            return false;
        }
        if("0".equals(siteId)){
            JSFUtils.addFacesErrorMessage("Please Check,Customer site missing !!");
            return false;
        }
        if("0".equals(currency)){
            JSFUtils.addFacesErrorMessage("Please Check, Currency missing !!");
            return false;
        }
        if("0".equals(receMethId)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt Method missing !!");
            return false;
        }
        if("0".equals(remitId)){
            JSFUtils.addFacesErrorMessage("Please Check, Remit Bank Account missing !!");
            return false;
        }
        if("0".equals(amt)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt amount missing !!");
            return false;
        }
        if("0".equals(prop)){
            JSFUtils.addFacesErrorMessage("Please Check, Property missing !!");
            return false;
        }
        if("0".equals(build)){
            JSFUtils.addFacesErrorMessage("Please Check, Building missing !!");
            return false;
        }
        if("0".equals(unit)){
            JSFUtils.addFacesErrorMessage("Please Check, Unit missing !!");
            return false;
        }
        if("0".equals(srcFunc)){
            JSFUtils.addFacesErrorMessage("Please Check, Source function missing !!");
            return false;
        }
        if("0".equals(srcFuctRef)){
            JSFUtils.addFacesErrorMessage("Please Check, Source function reference missing !!");
            return false;
        }
    return true; 
    }

    public void onClickApplyReceipt(ActionEvent actionEvent) {
        Row curLRow = linevo.getCurrentRow(); 
        if(isValidaForApply()){
            curLRow.setAttribute("InterfaceStatus","READY_TO_APPLY");
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Receipt Applied To AR !!");
        } 
    }
    public void onClickUnApplyReceipt(ActionEvent actionEvent) {
        Row curLRow = linevo.getCurrentRow(); 
        if(isValidaForUnApply()){
            curLRow.setAttribute("UnApplyInterfaceStatus","READY_TO_UN_APPLY");
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Receipt Unpplied !!");
        } 
    }

    private boolean isValidaForApply() {
        Row curLRow = linevo.getCurrentRow(); 
        Row r=headervo.getCurrentRow(); 
        String receivedAmt = r.getAttribute("ReceivedAmount")!=null?r.getAttribute("ReceivedAmount").toString():"0";
        String receiptDate = r.getAttribute("ReceiptDate")!=null?r.getAttribute("ReceiptDate").toString():"0";    
        
        String recLineId = curLRow.getAttribute("ReceiptDtlId")!=null?curLRow.getAttribute("ReceiptDtlId").toString():"0";
        String amtApplied = curLRow.getAttribute("AmountApplied")!=null?curLRow.getAttribute("AmountApplied").toString():"0";
        String applyDate = curLRow.getAttribute("AppliedDate")!=null?curLRow.getAttribute("AppliedDate").toString():"0";
        String accDate = curLRow.getAttribute("GlDate")!=null?curLRow.getAttribute("GlDate").toString():"0";
        String bookMsId = curLRow.getAttribute("SourcesRefDtlId")!=null?curLRow.getAttribute("SourcesRefDtlId").toString():"0";
        if("0".equals(receivedAmt)){
            JSFUtils.addFacesErrorMessage("Please Check, Received Amount missing !!");
            return false;
        }
        if("0".equals(receiptDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt Date missing !!");
            return false;
        }
        
        if("0".equals(recLineId)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt id missing !!");
            return false;
        }
        if("0".equals(amtApplied)){
            JSFUtils.addFacesErrorMessage("Please Check, Applied Amount missing !!");
            return false;
        }
        if("0".equals(applyDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Apply Date missing !!");
            return false;
        }
        if("0".equals(accDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Accounting Date missing !!");
            return false;
        }
        if("0".equals(bookMsId)){
            JSFUtils.addFacesErrorMessage("Please Check, Milestone id missing !!");
            return false;
        }
        
        return true;
    }
    private boolean isValidaForUnApply() {
        Row curLRow = linevo.getCurrentRow(); 
        Row r=headervo.getCurrentRow(); 
        String orgId = r.getAttribute("OrgId")!=null?r.getAttribute("OrgId").toString():"0";
        String recNo = r.getAttribute("ReceiptNumber")!=null?r.getAttribute("ReceiptNumber").toString():"0";    
        
        String recLineId = curLRow.getAttribute("ReceiptDtlId")!=null?curLRow.getAttribute("ReceiptDtlId").toString():"0";
        String recId = curLRow.getAttribute("ReceiptId")!=null?curLRow.getAttribute("ReceiptId").toString():"0";
        String amtApplied = curLRow.getAttribute("AmountApplied")!=null?curLRow.getAttribute("AmountApplied").toString():"0";
        String revGlDate = curLRow.getAttribute("ReversalGlDate")!=null?curLRow.getAttribute("ReversalGlDate").toString():"0";
        String applyDate = curLRow.getAttribute("AppliedDate")!=null?curLRow.getAttribute("AppliedDate").toString():"0";
        String unapplyDate = curLRow.getAttribute("UnappliedDate")!=null?curLRow.getAttribute("UnappliedDate").toString():"0";
        if("0".equals(orgId)){
            JSFUtils.addFacesErrorMessage("Please Check, Business Unit missing !!");
            return false;
        }
        if("0".equals(recNo)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt Number missing !!");
            return false;
        }
        
        if("0".equals(recLineId)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt id missing !!");
            return false;
        }
        if("0".equals(recId)){
            JSFUtils.addFacesErrorMessage("Please Check, Receipt id missing !!");
            return false;
        }
        if("0".equals(amtApplied)){
            JSFUtils.addFacesErrorMessage("Please Check, Applied Amount missing !!");
            return false;
        }
        if("0".equals(applyDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Apply Date missing !!");
            return false;
        }
        if("0".equals(unapplyDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Unapply Date missing !!");
            return false;
        }
        if("0".equals(revGlDate)){
            JSFUtils.addFacesErrorMessage("Please Check, Reversal GL Date missing !!");
            return false;
        }
        
        return true;
    }

    public void onClickReverse(ActionEvent actionEvent) {
        Row r = headervo.getCurrentRow();
        r.setAttribute("RevIntStatus", "READY_TO_REVERSAL");
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.hidePopup(reversePop);
        JSFUtils.addFacesInformationMessage("Applied to transfer AR !!"); 
    }
    public void onCancelReverse(ActionEvent actionEvent) {
        JSFUtils.hidePopup(reversePop);
    }

    public void setReversePop(RichPopup reversePop) {
        this.reversePop = reversePop;
    }

    public RichPopup getReversePop() {
        return reversePop;
    }

    public void onClickReceiptDate(ValueChangeEvent valueChangeEvent) {
     Row r=headervo.getCurrentRow();
//     r.setAttribute("GlDate",valueChangeEvent.getNewValue());
//     System.err.println("-->"+valueChangeEvent.getNewValue());
     JSFUtils.setExpressionValue("#{bindings.GlDate.inputValue}",valueChangeEvent.getNewValue());
     
    }

    public void onClickCancelPopup(ActionEvent actionEvent) {
        ViewObject cancelVo=ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator").getViewObject();
        String bookingHdr=headervo.getCurrentRow().getAttribute("SourceFuncRefId")==null?"0":
                          headervo.getCurrentRow().getAttribute("SourceFuncRefId").toString();
        System.out.println("bookingHdr==>"+bookingHdr);
        cancelVo.setNamedWhereClauseParam("BV_BOOKING_ID", bookingHdr);
        cancelVo.executeQuery();
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        this.getCanInvPopup().show(hints);
    }


    public void setCanInvPopup(RichPopup canInvPopup) {
        this.canInvPopup = canInvPopup;
    }

    public RichPopup getCanInvPopup() {
        return canInvPopup;
    }

    public void onClickCancelInvOk(ActionEvent actionEvent) {
        String receiptID=headervo.getCurrentRow().getAttribute("ReceiptId")==null?"0":
                          headervo.getCurrentRow().getAttribute("ReceiptId").toString();
        ViewObject cancelVo=ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator").getViewObject();
        RowSetIterator cancelRs=cancelVo.createRowSetIterator(null);
        while(cancelRs.hasNext()){
            Row r=cancelRs.next();
            String amt=r.getAttribute("AmountApplied")==null?"0":r.getAttribute("AmountApplied").toString();
            if(amt.equals("0")){
                
            }else{
                Row lineRow=linevo.createRow();
                lineRow.setAttribute("ReceiptId",receiptID);
                lineRow.setAttribute("BookingMsDtlId",r.getAttribute("DtlId"));
                lineRow.setAttribute("TransactionApplied",r.getAttribute("PreviousAmountApplied"));
                lineRow.setAttribute("TransactionBalance",r.getAttribute("BalanceAmt"));
                lineRow.setAttribute("AmountApplied",r.getAttribute("AmountApplied"));
                lineRow.setAttribute("SourcesFunId",r.getAttribute("FuncId"));
                lineRow.setAttribute("SourcesRefDtlId",r.getAttribute("DtlId"));
                lineRow.setAttribute("SourcesRefId",r.getAttribute("HdrId"));
                linevo.insertRow(lineRow);
                ADFUtils.findOperation("Commit").execute();
            }
        }
        cancelRs.closeRowSetIterator();
        linevo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(t4);
    }

    public void onClickCanInvClose(ActionEvent actionEvent) {
        this.getCanInvPopup().cancel();
    }

    public void onClickReceiptDtlRemove(ActionEvent actionEvent) {
        String status=linevo.getCurrentRow().getAttribute("InterfaceStatus")==null?"Draft":linevo.getCurrentRow().getAttribute("InterfaceStatus").toString();
        System.out.println("status"+status);
        if(status.equalsIgnoreCase("Draft")||status.equalsIgnoreCase("Error")||status.equalsIgnoreCase("ERROR")){
            linevo.getCurrentRow().remove();
            ADFUtils.findOperation("Commit").execute();
            linevo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(t4);
            JSFUtils.addFacesInformationMessage("Information Deleted Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Selected Receipt Detail is not possible. Integration Status:"+status);
        }
    }

    public void setNewInterfaceStatus(RichOutputFormatted newInterfaceStatus) {
        this.newInterfaceStatus = newInterfaceStatus;
    }

    public RichOutputFormatted getNewInterfaceStatus() {
        return newInterfaceStatus;
    }

    public void setPlam1(RichPanelLabelAndMessage plam1) {
        this.plam1 = plam1;
    }

    public RichPanelLabelAndMessage getPlam1() {
        return plam1;
    }
}
