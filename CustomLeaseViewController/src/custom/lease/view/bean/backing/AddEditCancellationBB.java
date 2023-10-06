package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import custom.lease.custom.view.beans.Utils.MailServices;
import custom.lease.custom.view.beans.Utils.MailTemplate;

import java.sql.Date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Iterator;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class AddEditCancellationBB {
    private RichInputDate actualNocDate;
    private RichPopup linePop;
    java.util.Set lineSet = new java.util.HashSet();
    private RichPopup p6;
    private RichTable t10;
    private RichCommandMenuItem okbutton;
    private RichImage okFlag;
    private RichImage cancelFlag;
    private RichTable receiptDtlTable;
    private RichTable receiptHdrTable;
    private RichPanelTabbed pt2;
    private RichPanelBox pb5;
    private RichInputText onApproveComment;
    private RichInputText onRejectComment;
    private RichPopup popApp;
    private RichPopup popRej;
    private RichPopup p9;

    public AddEditCancellationBB() {
        super();
    }

    ViewObject openInvVO=ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator").getViewObject();
    ViewObject cancelVO = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
    ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
    ViewObject DueAmtVo = ADFUtils.findIterator("XxplCancellationCharge_VO1Iterator").getViewObject();

    ViewObject ApprovalTypeVO=ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();
    ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
    
    public void onClickSave(ActionEvent actionEvent) {
        saveData();        
        JSFUtils.addFacesInformationMessage("Cancellation details Saved Successfully !");
    }

    private void saveData() {
        ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
        Row row = hdrVo.getCurrentRow();
        row.setAttribute("NocDateActual", row.getAttribute("NocDateActual"));
        
        ViewObject cVo = ADFUtils.findIterator("XxplCancellationCharge_VOIterator").getViewObject();
        RowSetIterator cItr = cVo.createRowSetIterator(null);
        while (cItr.hasNext()) {
            Row r = cItr.next(); 
            r.setAttribute("Amount", r.getAttribute("Amount"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("VatAmount", r.getAttribute("VatAmount"));
            r.setAttribute("TotalAmount", r.getAttribute("TotalAmount")); 
                
        }
        
        ViewObject dueVo = ADFUtils.findIterator("XxplCancellationCharge_VO1Iterator").getViewObject();
        RowSetIterator dueItr = dueVo.createRowSetIterator(null);
        while (dueItr.hasNext()) {
            Row r1 = dueItr.next(); 
            r1.setAttribute("Amount", r1.getAttribute("Amount"));
            r1.setAttribute("TaxRate", r1.getAttribute("TaxRate"));
            r1.setAttribute("VatAmount", r1.getAttribute("VatAmount"));
            r1.setAttribute("TotalAmount", r1.getAttribute("TotalAmount")); 
        }
        
        ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_VOIterator").getViewObject();
        RowSetIterator chargeItr = chargeVo.createRowSetIterator(null);
        while (chargeItr.hasNext()) {
            Row r = chargeItr.next(); 
            r.setAttribute("TaxCode", r.getAttribute("TaxCode"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
            r.setAttribute("InstallmentAmount", r.getAttribute("InstallmentAmount")); 
                
        }
        ADFUtils.findOperation("Commit").execute(); 
//        executeHeader();
    }

    public void onClickAttachment(ActionEvent actionEvent) {
        // Add event code here...
        ADFContext.getCurrent().getPageFlowScope().put("FuncId",
                                                               custom.lease.custom.view.beans.Utils.ADFUtils.getBoundAttributeValue("FuncId"));
                ADFContext.getCurrent().getPageFlowScope().put("FuncRefId",
                                                               custom.lease.custom.view.beans.Utils.ADFUtils.getBoundAttributeValue("CancelId"));

    }

    public void onChangeNOC(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(actualNocDate);
    }
    public java.util.Date getLeaseStartDate(){
        ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
        Row r = hdrVo.getCurrentRow();
        String eDate = r.getAttribute("LeaseStartDate_Trans") != null ? r.getAttribute("LeaseStartDate_Trans").toString() :"0";
         try {
                if(!"0".equals(eDate)){
                  java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(eDate);
                  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                  String currentDate = formatter.format(date1);
                  return formatter.parse(currentDate);
                }
              } catch (Exception e) {
                  e.printStackTrace();
              }
              return null;
    }
    
    public java.util.Date getLeaseEndDate(){
        ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
        Row r = hdrVo.getCurrentRow();
        String eDate = r.getAttribute("LeaseEndDate_Trans") != null ? r.getAttribute("LeaseEndDate_Trans").toString() :"0";
         try {
             if(!"0".equals(eDate)){
                 java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(eDate);
                 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                 String currentDate = formatter.format(date1);
                 return formatter.parse(currentDate);
             } 
              } catch (Exception e) {
                  e.printStackTrace();
              }
              return null; 
    }

    public void setActualNocDate(RichInputDate actualNocDate) {
        this.actualNocDate = actualNocDate;
    }

    public RichInputDate getActualNocDate() {
        return actualNocDate;
    }
    
    private void takeAllLineIntoSet() {
        ViewObject lineVo = ADFUtils.findIterator("XxplCancellationDtl_VOIterator").getViewObject();
        RowSetIterator DtlRs = lineVo.createRowSetIterator(null);
        while (DtlRs.hasNext()) {
            Row DtlRow = DtlRs.next();
            if (DtlRow.getAttribute("UnitId") != null) {
                    lineSet.add(DtlRow.getAttribute("UnitId"));
                }
        }

    }

    public void onAddLinePop(ActionEvent actionEvent) {
        takeAllLineIntoSet();
        ViewObject rovo = ADFUtils.findIterator("BOOKING_DTL_ROVOIterator").getViewObject();
        ViewObject lineVo = ADFUtils.findIterator("XxplCancellationDtl_VOIterator").getViewObject();
        RowSetIterator itr=rovo.createRowSetIterator(null);
        while(itr.hasNext())
        {
        Row row=itr.next();
        String select=row.getAttribute("Select_Trans")==null?"N":row.getAttribute("Select_Trans").toString();
        if(select.equalsIgnoreCase("true")){
            if(lineSet.add(row.getAttribute("UnitId"))){
                Row r=lineVo.createRow();
                r.setAttribute("Building_Trans",row.getAttribute("BuildName"));
                r.setAttribute("UnitNo_Trans",row.getAttribute("UnitNumber"));
                r.setAttribute("UnitId",row.getAttribute("UnitId"));
                r.setAttribute("PropertyId",row.getAttribute("PropertyId"));
                r.setAttribute("BuildingId",row.getAttribute("BuildingId"));
                r.setAttribute("AreaInSqft",row.getAttribute("AreaInSqft"));
                r.setAttribute("BasePrice",row.getAttribute("BasePrice"));
                r.setAttribute("BookingAmount",row.getAttribute("BookingAmount"));
                r.setAttribute("BookingDtlId",row.getAttribute("BookingDtlId"));
                r.setAttribute("BookingHdrId",row.getAttribute("BookingHdrId"));
                r.setAttribute("DiscountType",row.getAttribute("DiscountType"));
                r.setAttribute("DiscPct",row.getAttribute("DiscPct"));
                r.setAttribute("DiscAmount",row.getAttribute("DiscAmount"));            
                r.setAttribute("TaxCode",row.getAttribute("TaxCode"));   
                r.setAttribute("TaxRate",row.getAttribute("TaxRate"));   
                r.setAttribute("TaxAmount",row.getAttribute("TaxAmount"));   
                r.setAttribute("TotalAmount",row.getAttribute("TotalAmount"));   
                lineVo.insertRow(r);
            }
          }
        }
        itr.closeRowSetIterator(); 
        lineVo.executeQuery();
    }

    public void onCancelLinePop(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setLinePop(RichPopup linePop) {
        this.linePop = linePop;
    }

    public RichPopup getLinePop() {
        return linePop;
    }

    public void onClickProposed(ActionEvent actionEvent) {
        saveData();
        ViewObject chargeVo = ADFUtils.findIterator("XxplCancellationCharge_VOIterator").getViewObject();
        ArrayList <String> returnList = new ArrayList<String>();
        String cancelId = ADFUtils.getBoundAttributeValue("CancelId").toString();
        returnList = PackageCalling.cancellationProceed(cancelId);
        if(returnList.get(0)!=null && "S".equals(returnList.get(0))){
            JSFUtils.addFacesInformationMessage(returnList.get(1));   
        }else{
            JSFUtils.addFacesErrorMessage(returnList.get(1));
        }
        this.executeHeader();
        chargeVo.executeQuery();
    }

    private void executeHeader() {
        String cancelId = ADFUtils.getBoundAttributeValue("CancelId").toString();
        ViewObject vo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        vcr.setAttribute("CancelId", cancelId);
        vc.addRow(vcr);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
    }
    public void autoSubmitProcess(String funcId, String bookingId, String tableName) { 
        ArrayList <String> returnList = new ArrayList<String>();
        returnList = PackageCalling.approvalUpdateResponce(funcId, bookingId, tableName);
        // System.err.println(returnList);
        if("0".equals(returnList.get(0))){
            JSFUtils.addFacesInformationMessage("Booking approved successfully !");   
        }else{
            JSFUtils.addFacesErrorMessage("Error : "+returnList.get(1));
        }
    }
  



    
    public ArrayList isInvoiceOpen(){
        ArrayList <String> al = new ArrayList<String>();
        String bookingId=cancelVO.getCurrentRow().getAttribute("BookingId")==null?"0":cancelVO.getCurrentRow().getAttribute("BookingId").toString();
//        System.out.println("bookingId"+bookingId);
        ViewObject tenantROVO = ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator1").getViewObject();
        ViewCriteria vc=tenantROVO.createViewCriteria();
        ViewCriteriaRow vcr=vc.createViewCriteriaRow();
        vcr.setAttribute("BookingId", bookingId);
        vc.addRow(vcr);
        tenantROVO.applyViewCriteria(vc);
        tenantROVO.executeQuery();
//        System.err.println("COUNT==>"+tenantROVO.getEstimatedRowCount());
        int i=0;
        while(tenantROVO.hasNext()){
            Row r=tenantROVO.next();
            String invNum=r.getAttribute("InvoiceNumber")==null?"ABC":r.getAttribute("InvoiceNumber").toString();
            String invAmt=r.getAttribute("InvoiceAmount")==null?"0":r.getAttribute("InvoiceAmount").toString();

//            System.out.println("invNum==>"+invNum);
            if(invNum.equalsIgnoreCase("ABC")){
                if(invAmt.equalsIgnoreCase("0")){
                    // If Invoice Amt 0-don't calculate
                }else{
                    i=i+1;      
                }
            }
        }
//        System.err.println("i Open Inv Count==>"+i);
        if(i==0){
            al.add("FALSE");
            al.add("Success");
        }else{
            al.add("TRUE");
            al.add(i+" Open Invoice In Draft Stage");
        }
        return al;
    }



    public void doPkgCallForErrorRepush(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
        Row currRow = hdrVo.getCurrentRow();
        String cancelId = currRow.getAttribute("CancelId")==null?"0":currRow.getAttribute("CancelId").toString();
        System.err.println("cancelId"+cancelId);
        String code = PackageCalling.createMilestoneBooking_CN(cancelId);
        if("Y".equals(code)){
            JSFUtils.addFacesInformationMessage("Integration Initiated Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Please check, Error !!");
        }
        
    }

    public void setP6(RichPopup p6) {
        this.p6 = p6;
    }

    public RichPopup getP6() {
        return p6;
    }

    public void onClickOpenInvoice(ActionEvent actionEvent) {
        
//        String bookingid=cancelVO.getCurrentRow().getAttribute("BookingId")==null?"0":cancelVO.getCurrentRow().getAttribute("BookingId").toString();        
//        openInvVO.setNamedWhereClauseParam("BV_BOOKING_ID", bookingid);
//        openInvVO.executeQuery();
//        RichPopup.PopupHints hints = new RichPopup.PopupHints();
//        this.getP6().show(hints);
        
    }

    public void onClickSecurityOk(ActionEvent actionEvent) {
//        Row currentRow = cancelVO.getCurrentRow();
//        Double securityDepositAmt = currentRow.getAttribute("SecurityDepositAmt") == null ? 0.0 : Double.parseDouble(currentRow.getAttribute("SecurityDepositAmt").toString());
//        
//        Double sumAmountApplied = 0.0;
//        RowSetIterator itr = openInvVO.createRowSetIterator(null);
//        while(itr.hasNext())
//        {
//            Row r = itr.next();
//            sumAmountApplied +=
//                r.getAttribute("AmountApplied") == null ? 0.0 :
//                Double.parseDouble(r.getAttribute("AmountApplied").toString());
//        }
//        if(sumAmountApplied > securityDepositAmt){
//            JSFUtils.addFacesErrorMessage("Please check, Entered amount is more than security Deposit Amount !!");
//        }else{
//            
//            RowSetIterator itr1 = openInvVO.createRowSetIterator(null);
//            while(itr1.hasNext())
//            {
//                Row r = itr1.next();
//                String amtApp = r.getAttribute("AmountApplied") == null ? "0" : r.getAttribute("AmountApplied").toString();
//                String type = r.getAttribute("Type") == null ? "0" : r.getAttribute("Type").toString();
//                String dtlId = r.getAttribute("DtlId") == null ? "0" : r.getAttribute("DtlId").toString();
//                
//                System.err.println("type-->"+type);
//                System.err.println("amtApp-->"+amtApp);
//                System.err.println("dtlId-->"+dtlId);
//                
//                if("0".equals(amtApp) || "0.0".equals(amtApp) || "0.00".equals(amtApp)){
//                    updateChargeTable(type, dtlId, amtApp, "N");
//                }else{
//                    updateChargeTable(type, dtlId, amtApp, "Y");
//                }
//            } 
//            p6.hide();
//        } 
        ViewObject receiptDtlVO=ADFUtils.findIterator("XxplReceiptLineView1Iterator").getViewObject();
        String receiptID=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("receiptId")==null?"0":
                          AdfFacesContext.getCurrentInstance().getPageFlowScope().get("receiptId").toString();
        double setAmt=0.0;
         ViewObject openInvVO=ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator").getViewObject();
         RowSetIterator rs=openInvVO.createRowSetIterator(null);
         while(rs.hasNext()){
             Row r=rs.next();
             setAmt=r.getAttribute("AmountApplied")==null?0:Double.parseDouble(r.getAttribute("AmountApplied").toString());
             if(setAmt!=0||setAmt!=0.0 && r.getAttribute("GlDate")!=null){
//                 System.err.println("receiptID==>"+receiptID);
//                 System.err.println("Amt==>"+r.getAttribute("AmountApplied"));
//                 System.err.println("Hdr==>"+r.getAttribute("HdrId"));
//                 System.err.println("Fun==>"+r.getAttribute("FuncId"));
//                 System.err.println("Dtl==>"+r.getAttribute("DtlId"));
//                 System.err.println("Date==>"+r.getAttribute("GlDate"));
//                 System.err.println("Inv Amt==>"+r.getAttribute("InvoiceAmount"));
//                 System.err.println("Date==>"+r.getAttribute("GlDate"));
//                 System.err.println("************");
                 String srcFncId=r.getAttribute("FuncId")==null?"0":r.getAttribute("FuncId").toString();
                 String srcRefId=r.getAttribute("HdrId")==null?"0":r.getAttribute("HdrId").toString();
                 String srcRefDtlId=r.getAttribute("DtlId")==null?"0":r.getAttribute("DtlId").toString();
                 String l_amountApplied=r.getAttribute("AmountApplied")==null?"0":r.getAttribute("AmountApplied").toString();
                 Object glDate=r.getAttribute("GlDate");
                 String preAmt=r.getAttribute("PreviousAmountApplied")==null?"0":r.getAttribute("PreviousAmountApplied").toString();
                 String balncAmt=r.getAttribute("BalanceAmt")==null?"0":r.getAttribute("BalanceAmt").toString();
                // Receipt Detail Insert
                 createReceipt(receiptID, srcFncId, srcRefId, srcRefDtlId, l_amountApplied, preAmt, balncAmt, glDate);
             }
         }
        rs.closeRowSetIterator();
        receiptDtlVO.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptDtlTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pt2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pb5);
        p6.hide();        
    }
    
    
    public void createReceipt(String rcptId,String srcFncId, String srcRefId, String srcRefDtlId, String setAmt, String apldAmt, String blncAmt, Object glDate){
        ViewObject receiptDtlVO=ADFUtils.findIterator("XxplReceiptLineView2Iterator").getViewObject();
        Row dtlR=receiptDtlVO.createRow();
        dtlR.setAttribute("ReceiptId", rcptId);
        dtlR.setAttribute("SourcesFunId", srcFncId);
        dtlR.setAttribute("SourcesRefId", srcRefId);
        dtlR.setAttribute("SourcesRefDtlId", srcRefDtlId);
        dtlR.setAttribute("AmountApplied", setAmt); 
        dtlR.setAttribute("GlDate", glDate);
        dtlR.setAttribute("AppliedDate", glDate);
        dtlR.setAttribute("TransactionApplied", apldAmt);
        dtlR.setAttribute("TransactionBalance", blncAmt);
        dtlR.setAttribute("InterfaceStatus", "Draft");
        receiptDtlVO.insertRow(dtlR);
        ADFUtils.findOperation("Commit").execute(); 
        receiptDtlVO.executeQuery();
    }
    
    
    private void updateChargeTable(String type, String dtlId, String amount, String flag) {
        if(type!=null && "TENANT_LEASE".equals(type)){
            
            ViewObject vo = ADFUtils.findIterator("BookingMilestonesIteratorUPDATE").getViewObject();
            ViewCriteria vc = vo.createViewCriteria();
            ViewCriteriaRow vcr = vc.createViewCriteriaRow();
            vcr.setAttribute("BookingMsDtlId", dtlId);
            vc.addRow(vcr);
            vo.applyViewCriteria(vc);
            vo.executeQuery();
            if(vo.getEstimatedRowCount()>0){
                Row firstRow = vo.first();    
                firstRow.setAttribute("CanAmtApply", amount);
                firstRow.setAttribute("CanFlag", flag);
                System.err.println("Updated TENANT_LEASE");
            } 
        }
        
        if(type!=null && "TENANT_LEASE_CANCEL".equals(type)){
            
            ViewObject vo = ADFUtils.findIterator("CancellationChargeIteratorUPDATE").getViewObject();
            ViewCriteria vc = vo.createViewCriteria();
            ViewCriteriaRow vcr = vc.createViewCriteriaRow();
            vcr.setAttribute("CancelChargeId", dtlId);
            vc.addRow(vcr);
            vo.applyViewCriteria(vc);
            vo.executeQuery();
            if(vo.getEstimatedRowCount()>0){
                Row firstRow = vo.first();    
                firstRow.setAttribute("CanAmtApply", amount);
                firstRow.setAttribute("CanFlag", flag);
                System.err.println("Updated CANCEL_CHARGE");
            } 
        } 
    }

    public void onClickCancel(ActionEvent actionEvent) {
        this.getP6().cancel();
    }

    public void setT10(RichTable t10) {
        this.t10 = t10;
    }

    public RichTable getT10() {
        return t10;
    }
    

    public void onClickProcessDueAmount(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("Commit").execute(); 
        Row currRow = cancelVO.getCurrentRow();
        String cancelId = currRow.getAttribute("CancelId")==null?"0":currRow.getAttribute("CancelId").toString();
        System.err.println("cancelId"+cancelId);
        String code = PackageCalling.createMilestoneBooking_CN(cancelId);
        if("Success".equals(code)){
            JSFUtils.addFacesInformationMessage("Process Completed Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Please check, Error !!");
        }
        this.executeHeader();
        DueAmtVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(t10);
    }


    public void onClickInvoiceForOC(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject cVo = ADFUtils.findIterator("XxplCancellationCharge_VOIterator").getViewObject();
        Row r = cVo.getCurrentRow();
        r.setAttribute("InterfaceStatus", "PAY_CANCEL_FEES");
    }

    public void onClickApplySecuDepo(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject receiptHdrVO=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
        // getting
        String l_receiptId=receiptHdrVO.getCurrentRow().getAttribute("ReceiptId")==null?"0":receiptHdrVO.getCurrentRow().getAttribute("ReceiptId").toString();
        String l_receiptNum=receiptHdrVO.getCurrentRow().getAttribute("ReceiptNumber")==null?"0":receiptHdrVO.getCurrentRow().getAttribute("ReceiptNumber").toString();
        double l_receivedAmount=receiptHdrVO.getCurrentRow().getAttribute("ReceivedAmount")==null?0.0:Double.parseDouble(receiptHdrVO.getCurrentRow().getAttribute("ReceivedAmount").toString());
        double l_preReceiptAmt=getReceiptDetailAmt(l_receiptId);
        double l_recebalaAmt=l_receivedAmount-l_preReceiptAmt;
        /// setting
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("receiptId", l_receiptId);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("receivedAmount", l_receivedAmount);            
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("receiptNumber", l_receiptNum);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("receiptDetailAmt", l_preReceiptAmt);            
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("receiptDtlBalAmt", l_recebalaAmt);            
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("totAmt", 0);
        
        //open popup
        String bookingid=cancelVO.getCurrentRow().getAttribute("BookingId")==null?"0":cancelVO.getCurrentRow().getAttribute("BookingId").toString();
        openInvVO.setNamedWhereClauseParam("BV_BOOKING_ID", bookingid);
        openInvVO.executeQuery();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getP6().show(hints);
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptDtlTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptHdrTable);    
        AdfFacesContext.getCurrentInstance().addPartialTarget(pb5);
    }
    
    
    public Double getReceiptDetailAmt(String receiptId){
        double amt=0.0;
        double receiptAmt=0.0;
        ViewObject receiptDtlVO=ADFUtils.findIterator("XxplReceiptLineView2Iterator").getViewObject();
        ViewCriteria vc=receiptDtlVO.createViewCriteria();
        ViewCriteriaRow vcr=vc.createViewCriteriaRow();
        vcr.setAttribute("ReceiptId", receiptId);
        vc.addRow(vcr);
        receiptDtlVO.applyViewCriteria(vc);
        receiptDtlVO.executeQuery();
        if(receiptDtlVO.getEstimatedRowCount()>=1){
            RowSetIterator rs=receiptDtlVO.createRowSetIterator(null);    
            while(rs.hasNext()){
                Row r=rs.next();
                receiptAmt=r.getAttribute("AmountApplied")==null?0.0:Double.parseDouble(r.getAttribute("AmountApplied").toString());
                amt=amt+receiptAmt;    
            }
            rs.closeRowSetIterator();
            return amt;
        }else{
               return amt; 
        }
    }


    public void onClickSDAmtCal(ActionEvent actionEvent) {
        double totAmt=0.0;
        double setAmt=0.0;
        double calAmt=0.0;
        
        double balAmt=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("receiptDtlBalAmt")==null?0.0:
                      Double.parseDouble(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("receiptDtlBalAmt").toString());  
        ViewObject vo=ADFUtils.findIterator("XXPL_TENANT_OPEN_INV_VROVO1Iterator").getViewObject();
        RowSetIterator Rs=vo.createRowSetIterator(null);
        while(Rs.hasNext()){
            Row r=Rs.next();
            setAmt=r.getAttribute("AmountApplied")==null?0.0:Double.parseDouble(r.getAttribute("AmountApplied").toString());
            calAmt=calAmt+setAmt;
        }
        Rs.closeRowSetIterator();
        totAmt=balAmt-calAmt;
//        System.out.println("calAmt==>"+calAmt);
//        System.out.println("balAmt==>"+balAmt);
//        System.out.println("totAmt==>"+totAmt);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("totAmt", calAmt);
        if(totAmt<0||totAmt==balAmt){
            System.err.println("====");
            okFlag.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(okFlag);
            cancelFlag.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelFlag);
            okbutton.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(okbutton);
        }else{
            System.err.println("====ELS");
            okbutton.setVisible(true);
            okFlag.setVisible(true);
            cancelFlag.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelFlag);
            AdfFacesContext.getCurrentInstance().addPartialTarget(okbutton);
            AdfFacesContext.getCurrentInstance().addPartialTarget(okFlag);
        }
    }

    public void setOkbutton(RichCommandMenuItem okbutton) {
        this.okbutton = okbutton;
    }

    public RichCommandMenuItem getOkbutton() {
        return okbutton;
    }

    public void setOkFlag(RichImage okFlag) {
        this.okFlag = okFlag;
    }

    public RichImage getOkFlag() {
        return okFlag;
    }

    public void setCancelFlag(RichImage cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public RichImage getCancelFlag() {
        return cancelFlag;
    }

    public void setReceiptDtlTable(RichTable receiptDtlTable) {
        this.receiptDtlTable = receiptDtlTable;
    }

    public RichTable getReceiptDtlTable() {
        return receiptDtlTable;
    }

    public void onClickReceiptDtlDelete(ActionEvent actionEvent) {
        ViewObject dtlVo=ADFUtils.findIterator("XxplReceiptLineView1Iterator").getViewObject();
        String integrationStatus=dtlVo.getCurrentRow().getAttribute("InterfaceStatus")==null?"":dtlVo.getCurrentRow().getAttribute("InterfaceStatus").toString();
        if(integrationStatus.equalsIgnoreCase("Draft")){
            dtlVo.getCurrentRow().remove();
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Deleted Successfully");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptDtlTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptHdrTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pt2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pb5);
    }

    public void setReceiptHdrTable(RichTable receiptHdrTable) {
        this.receiptHdrTable = receiptHdrTable;
    }

    public RichTable getReceiptHdrTable() {
        return receiptHdrTable;
    }

    public void rowDisclosureListener(RowDisclosureEvent rowDisclosureEvent) {
        RichTable table = (RichTable) rowDisclosureEvent.getSource();
                   RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
                   RowKeySet lastAddedRowKeySet = rowDisclosureEvent.getAddedSet();
                   Iterator lastAddedRowKeySetIter = lastAddedRowKeySet.iterator();
                   if (lastAddedRowKeySetIter.hasNext())
                   {
                     discloseRowKeySet.clear();
                     Object lastRowKey = lastAddedRowKeySetIter.next();
                     discloseRowKeySet.add(lastRowKey);
                     makeDisclosedRowCurrent(table, lastAddedRowKeySet);
                     AdfFacesContext adfFacesContext = null;
                     adfFacesContext = AdfFacesContext.getCurrentInstance();
                     adfFacesContext.addPartialTarget(table.getParent());
                   }
    }
    
    private void makeDisclosedRowCurrent(RichTable table, RowKeySet keySet)
          {
            table.setSelectedRowKeys(keySet);
            CollectionModel tableModel = (CollectionModel) table.getValue();
            JUCtrlHierBinding tableHierBinding = null;
            tableHierBinding = (JUCtrlHierBinding) (tableModel).getWrappedData();
            DCIteratorBinding dCIteratorBindin = null;
            dCIteratorBindin = tableHierBinding.getDCIteratorBinding();
            Iterator keySetIter = keySet.iterator();
            List firstKey = (List) keySetIter.next();
            oracle.jbo.Key key = (oracle.jbo.Key) firstKey.get(0);
            dCIteratorBindin.setCurrentRowWithKey(key.toStringFormat(true));
          }

    public void setPt2(RichPanelTabbed pt2) {
        this.pt2 = pt2;
    }

    public RichPanelTabbed getPt2() {
        return pt2;
    }

    public void setPb5(RichPanelBox pb5) {
        this.pb5 = pb5;
    }

    public RichPanelBox getPb5() {
        return pb5;
    }

    public void onClickApprove(ActionEvent actionEvent) {
        String Comments=onApproveComment.getValue()==null?"Approved":onApproveComment.getValue().toString();
        String P_FUNC_ID=cancelVO.getCurrentRow().getAttribute("FuncId")==null?"12":cancelVO.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=cancelVO.getCurrentRow().getAttribute("CancelId")==null?"0":cancelVO.getCurrentRow().getAttribute("CancelId").toString();  
        String ccAddress=cancelVO.getCurrentRow().getAttribute("CreatedBy")==null?"dineshkumar.p@4iapps.com":cancelVO.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
            ccAddress="prism@omniyat.com";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
//      String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String cancelNumber=cancelVO.getCurrentRow().getAttribute("CancelNumber")==null?"CANCEL-000":cancelVO.getCurrentRow().getAttribute("CancelNumber").toString();
        String userGroupID=cancelVO.getCurrentRow().getAttribute("UserGrpId")==null?"0":cancelVO.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=cancelVO.getCurrentRow().getAttribute("FlowLevel")==null?"0":cancelVO.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=cancelVO.getCurrentRow().getAttribute("FlowWith")==null?"0":cancelVO.getCurrentRow().getAttribute("FlowWith").toString();
        String Attribute1_Org=cancelVO.getCurrentRow().getAttribute("OrgId")==null?"0":cancelVO.getCurrentRow().getAttribute("OrgId").toString();
        String BUName=null;
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+Attribute1_Org;
        }
        String ArStatus="APPROVE";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
                JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, 
                                                     P_REF_ID, 
                                                     userGroupID, 
                                                     levelNo, 
                                                     approveId, 
                                                     Comments, ArStatus, P_FWD_TO, 
                                                     BaseDetail.CANCELLATION_TABLE, 
                                                     BaseDetail.CANCELLATION_STATUS_COLUMN, 
                                                     BaseDetail.CANCELLATION_ID_COLUMN, 
                                                     BaseDetail.initialAmount);
            if(list.get(0)!=null){
                //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Approved")) {
                        JSFUtils.addFacesInformationMessage("Cancellation Approved Successfully");
                }else{
                        JSFUtils.addFacesInformationMessage("Cancellation Approved Successfully and resubmitted to next level user");
                        String toAddress=list.get(2)==null?"dineshkumar.p@4iapps.com":list.get(2).toString();
                        String subject=BaseDetail.CancellationSubjects+" Submitted for Approval. "+"Cancellation Number:"+cancelNumber;
                        ccAddress=ccAddress+","+userlogin;
                        String mailContent=MailTemplate.priceListMailContent("Cancellation", cancelNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
                        String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                        JSFUtils.addFacesErrorMessage(mailFlag);
                }
            }else{
                JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));                
            }
        }
    }

    public void onClickReject(ActionEvent actionEvent) {
        String Comments=onApproveComment.getValue()==null?"Approved":onApproveComment.getValue().toString();
        String P_FUNC_ID=cancelVO.getCurrentRow().getAttribute("FuncId")==null?"12":cancelVO.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=cancelVO.getCurrentRow().getAttribute("CancelId")==null?"0":cancelVO.getCurrentRow().getAttribute("CancelId").toString();  
        String ccAddress=cancelVO.getCurrentRow().getAttribute("CreatedBy")==null?"dineshkumar.p@4iapps.com":cancelVO.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
            ccAddress="prism@omniyat.com";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        //      String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String cancelNumber=cancelVO.getCurrentRow().getAttribute("CancelNumber")==null?"CANCEL-000":cancelVO.getCurrentRow().getAttribute("CancelNumber").toString();
        String userGroupID=cancelVO.getCurrentRow().getAttribute("UserGrpId")==null?"0":cancelVO.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=cancelVO.getCurrentRow().getAttribute("FlowLevel")==null?"0":cancelVO.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=cancelVO.getCurrentRow().getAttribute("FlowWith")==null?"0":cancelVO.getCurrentRow().getAttribute("FlowWith").toString();
        String Attribute1_Org=cancelVO.getCurrentRow().getAttribute("OrgId")==null?"0":cancelVO.getCurrentRow().getAttribute("OrgId").toString();
        String BUName=null;
        ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+Attribute1_Org;
        }
        String ArStatus="REJECT";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
                JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, 
                                                     P_REF_ID, 
                                                     userGroupID, 
                                                     levelNo, 
                                                     approveId, 
                                                     Comments, ArStatus, P_FWD_TO, 
                                                     BaseDetail.CANCELLATION_TABLE, 
                                                     BaseDetail.CANCELLATION_STATUS_COLUMN, 
                                                     BaseDetail.CANCELLATION_ID_COLUMN, 
                                                     BaseDetail.initialAmount);
            if(list.get(0)!=null){
                //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Approved")) {
                        JSFUtils.addFacesInformationMessage("Cancellation Approved Successfully");
                }else{
                        
                        String toAddress=list.get(2)==null?"dineshkumar.p@4iapps.com":list.get(2).toString();
                        String subject=BaseDetail.CancellationSubjects+" Rejected. "+"Cancellation Number:"+cancelNumber;
                        ccAddress=ccAddress+","+userlogin;
                        String mailContent=MailTemplate.cancellationMailContent("Cancellation", cancelNumber, BUName, BaseDetail.SysDate(), ccAddress, "Rejected");
                        String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                        JSFUtils.addFacesErrorMessage(mailFlag);
                }
            }else{
                JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));                
            }
        }
    }

    public void setOnApproveComment(RichInputText onApproveComment) {
        this.onApproveComment = onApproveComment;
    }

    public RichInputText getOnApproveComment() {
        return onApproveComment;
    }

    public void setOnRejectComment(RichInputText onRejectComment) {
        this.onRejectComment = onRejectComment;
    }

    public RichInputText getOnRejectComment() {
        return onRejectComment;
    }

    public String onClickCancelSubmit() {
        ArrayList <String> ls = new ArrayList<String>();
        String navi=null;
        ls=isInvoiceOpen();
        if(ls.get(0).toString().equalsIgnoreCase("FALSE")){
            ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "CANCELLATION_APR_TYPE");
            ApprovalTypeVO.executeQuery();
            String approveType=ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
            String P_FUNC_ID=cancelVO.getCurrentRow().getAttribute("FuncId")==null?"12":cancelVO.getCurrentRow().getAttribute("FuncId").toString();
            String P_REF_ID=cancelVO.getCurrentRow().getAttribute("CancelId")==null?"0":cancelVO.getCurrentRow().getAttribute("CancelId").toString();
            String Attribute1_Org=cancelVO.getCurrentRow().getAttribute("OrgId")==null?"0":cancelVO.getCurrentRow().getAttribute("OrgId").toString();
            String P_ATTRIBUTE2=null;
            String P_ATTRIBUTE3=null;
            String P_ATTRIBUTE4=null;
            String P_ATTRIBUTE5=null;
            String BUName=null;
            String ccAddress=cancelVO.getCurrentRow().getAttribute("CreatedBy")==null?"prism@omniyat.com":cancelVO.getCurrentRow().getAttribute("CreatedBy").toString();
            if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
                ccAddress="prism@omniyat.com";
            }
//          String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
            String cancelNumber=cancelVO.getCurrentRow().getAttribute("CancelNumber")==null?"CANCEL-000":cancelVO.getCurrentRow().getAttribute("CancelNumber").toString();
            
            ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
            GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
            GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
            GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
            GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
            GetDataVO.executeQuery();
            if(GetDataVO.getEstimatedRowCount()==1){
                BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
            }else{
                BUName="Error in BU and BU ID is "+Attribute1_Org;
            }
            ArrayList <String> list = new ArrayList<String>();
            // condition
            if(approveType.equalsIgnoreCase("AUTO")){
                navi=toAutoSave();
                if(navi.equalsIgnoreCase("SAVE")){
                    return "toSave";
                }else{
                    return null;
                }
            }else if(approveType.equalsIgnoreCase("ADF")){
                list=PackageCalling.invokeSubmit(
                                                P_FUNC_ID, P_REF_ID, 
                                                BaseDetail.initialLevel, 
                                                BaseDetail.CANCELLATION_TABLE, 
                                                BaseDetail.BOOKING_STATUS_COLUMN, 
                                                BaseDetail.CANCELLATION_ID_COLUMN, 
                                                BaseDetail.initialAmount, 
                                                Attribute1_Org, 
                                                P_ATTRIBUTE2, P_ATTRIBUTE3, 
                                                P_ATTRIBUTE4, P_ATTRIBUTE5);
                if(list.get(3)!=null){
//                    String toAddress=list.get(3)==null?"prasenjit.d@4iapps.com":list.get(3).toString();
//                    String subject=BaseDetail.CancellationSubjects+" Submitted for Approval "+"Cancellation Number:"+cancelNumber;
//                    String mailContent=MailTemplate.cancellationMailContent("Cancellation", cancelNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//                    String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                    JSFUtils.addFacesInformationMessage("Cancellation Submitted for Approval !!!");
//                    JSFUtils.addFacesErrorMessage(mailFlag);
                    return "toSave";
                }else{
//                    JSFUtils.addFacesErrorMessage("Error: Mail Notification Missing");
                    JSFUtils.addFacesErrorMessage("Mail ID          :" +list.get(3));
                    JSFUtils.addFacesErrorMessage("Error Code       :" +list.get(5));
                    JSFUtils.addFacesErrorMessage("Error Message    :" +list.get(6));
                    JSFUtils.addFacesErrorMessage("Status           :" +list.get(8));
                    return null;
                }
            }else if(approveType.equalsIgnoreCase("PCS")){
                return null;  
            }else{
                return null;  
            }
        }else{
            JSFUtils.addFacesErrorMessage(ls.get(1).toString());
            return null;  
        }
    }
    
    public String toAutoSave() {
            this.saveData();
            ViewObject hdrVo = ADFUtils.findIterator("XxplCancellation_VOIterator").getViewObject();
            Row currRow = hdrVo.getCurrentRow();
            String funcId = currRow.getAttribute("FuncId")==null?"12":currRow.getAttribute("FuncId").toString();
            String cancelId = currRow.getAttribute("CancelId")==null?"0":currRow.getAttribute("CancelId").toString();
            ArrayList <String> returnList = new ArrayList<String>();
            returnList = PackageCalling.approvalUpdateResponce(funcId, cancelId, BaseDetail.CANCELLATION_TABLE); 
            if("0".equals(returnList.get(0))){
                JSFUtils.addFacesInformationMessage("Cancellation approved successfully !"); 
                return "SAVE";
            }else{
                JSFUtils.addFacesErrorMessage("Error : "+returnList.get(1));
                return "ERROR";
            }                                           
    }

    public void onClickCancelApp(ActionEvent actionEvent) {
        popRej.cancel();
    }

    public void onClickCancelRej(ActionEvent actionEvent) {
        popApp.cancel();
    }

    public void setPopApp(RichPopup popApp) {
        this.popApp = popApp;
    }

    public RichPopup getPopApp() {
        return popApp;
    }

    public void setPopRej(RichPopup popRej) {
        this.popRej = popRej;
    }

    public RichPopup getPopRej() {
        return popRej;
    }

    public void onClickApprovalHistory(ActionEvent actionEvent) {
        String plid=cancelVO.getCurrentRow().getAttribute("CancelId")==null?"0":cancelVO.getCurrentRow().getAttribute("CancelId").toString();
        String fuctionid=cancelVO.getCurrentRow().getAttribute("FuncId")==null?"0":cancelVO.getCurrentRow().getAttribute("FuncId").toString();
        ADFContext.getCurrent().getSessionScope().put("plid",plid);
        ADFContext.getCurrent().getSessionScope().put("fuctionid",fuctionid);
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        p9.show(hints);
    }

    public void setP9(RichPopup p9) {
        this.p9 = p9;
    }

    public RichPopup getP9() {
        return p9;
    }
    
    public boolean getApprovalUser() {
        boolean flag = false;
        String loginId=null;
        String flowWith ="0";                        
        String userLogin ="-99";
    //        if(headervo.getEstimatedRowCount() > 0){
    //            if(headervo.getCurrentRow().getAttribute("FlowWith")!=null){
    //                flowWith =headervo.getCurrentRow().getAttribute("FlowWith").toString();
    //            }
    //        }
        flowWith=JSFUtils.resolveExpression("#{bindings.FlowWith.inputValue}")==null?"0":JSFUtils.resolveExpression("#{bindings.FlowWith.inputValue}").toString();
        if(ADFContext.getCurrent().getSessionScope().get("userName")!=null){
            userLogin =ADFContext.getCurrent().getSessionScope().get("userName").toString();
        }
        System.err.println("Tx flowWith ==>"+flowWith );
        System.err.println("Saas userLogin==>"+userLogin);
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "USER_ID");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "XXFND_USER");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "USER_EMAIL_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", userLogin);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            loginId=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();
            System.err.println("db loginId==>"+loginId);
        }else{
            loginId="0";
        }
        if (flowWith != null && loginId != null) {
            if (flowWith.equalsIgnoreCase(loginId)) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }
    
}
