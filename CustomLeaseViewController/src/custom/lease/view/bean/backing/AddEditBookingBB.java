package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import custom.lease.custom.view.beans.Utils.InvokeWebServices;
import custom.lease.custom.view.beans.Utils.MailServices;
import custom.lease.custom.view.beans.Utils.MailTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;



import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.json.JSONException;

public class AddEditBookingBB {
    private RichPopup bookContinuePopup;
    private RichPopup submitPopup;
    private RichTable customerTable;
    private RichPopup receiptConfirmation;
    private RichPopup otherChargePop;
    ViewObject ApprovalTypeVO=ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();
    ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();

    ViewObject dateValidateVO=ADFUtils.findIterator("MilestoneValidateROVO1Iterator").getViewObject();

    ViewObject bookingMSVO=ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
    
    
    
    private RichInputText approvalCmts;
    private RichPopup p8;
    private RichInputDate moveInDate;
    private RichInputDate moveOutDate;
    private RichPopup addCarPop;
    private RichPopup p6;
    private RichInputDate msStartDate;
    private RichInputDate msEndDate;
    private RichTable mst1;
    private RichInputText it1;
    private RichPopup p17;
    private RichTable t22;
    private RichInputText toAddress;
    private RichPopup p15;
    private RichInputText bookingAmt;
    private RichInputDate id5;
    private RichInputDate id1;

    public AddEditBookingBB() {
        super();
    } 
    ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();

    public void onClickSave(ActionEvent actionEvent) { 
        calculateAmount();   
        if(isInstallmentPerValid()){
            ADFUtils.findOperation("Commit").execute();  
            JSFUtils.addFacesInformationMessage("Booking Information Saved Successfully !"); 
        }
        ViewObject amtVo = ADFUtils.findIterator("BookingAmount_ROVOIterator").getViewObject();
        amtVo.executeQuery();
    }

    private void calculateAmount() {
        
        //  To get booking line amount 
            Double bookLineAmt = 0.0;
            ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
            RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
            while (itr.hasNext()) {
                Row r = itr.next();
                bookLineAmt +=
                    r.getAttribute("TotalAmount") == null ? 0.0 :
                    Double.parseDouble(r.getAttribute("TotalAmount").toString());
            }
            itr.closeRowSetIterator(); 
            
         // MS Calculation           
            ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
            RowSetIterator msRS = msVo.createRowSetIterator(null); 
            while (msRS.hasNext()) {
                Row currMS = msRS.next();
                msVo.setCurrentRow(currMS);
                //Installment amount revision
                double pct =
                    currMS.getAttribute("InstallmentPct") == null ? 0 :
                    Double.parseDouble(currMS.getAttribute("InstallmentPct").toString());
                double amt = (pct * bookLineAmt.doubleValue()) / 100;
                currMS.setAttribute("InstallmentAmount", new BigDecimal(amt));
                
                //Base price revision 
                double taxRate =
                               currMS.getAttribute("TaxRate") == null ? 0 :
                               Double.parseDouble(currMS.getAttribute("TaxRate").toString());
                double msBaseAmt =amt/(100 + taxRate)*100;
                currMS.setAttribute("Baseamount", new BigDecimal(msBaseAmt));
                 
            }
            msRS.closeRowSetIterator(); 
            
            //Other charge calculation 
            Double chargeVal = 0.0;
            double chargeAmt = 0.0;
            ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
            RowSetIterator ocItr = chargeVo.createRowSetIterator(null); 
            while (ocItr.hasNext()) {
                Row currRow = ocItr.next();
                chargeVo.setCurrentRow(currRow); 
                String type = currRow.getAttribute("ChargeMethod")==null?"":currRow.getAttribute("ChargeMethod").toString();
                chargeVal = currRow.getAttribute("Charges") == null ? 0.0 :
                            Double.parseDouble(currRow.getAttribute("Charges").toString());
            if("%".equals(type)){
                chargeAmt = chargeVal.doubleValue() * bookLineAmt.doubleValue() / 100;
            }else{
                chargeAmt = chargeVal;
            }
            currRow.setAttribute("Baseamount", new BigDecimal(chargeAmt));
    }
        saveData(); 
    }
    private void saveData() {
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        currRow.setAttribute("NoOfDays", currRow.getAttribute("NoOfDays")); 
        
        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            r.setAttribute("BasePrice", r.getAttribute("BasePrice"));
            r.setAttribute("DiscPct", r.getAttribute("DiscPct"));
            r.setAttribute("DiscAmount", r.getAttribute("DiscAmount"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
            r.setAttribute("TotalAmount", r.getAttribute("TotalAmount"));
                
        }
        itr.closeRowSetIterator();
        
        ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
        RowSetIterator chargeItr = chargeVo.createRowSetIterator(null);
        while (chargeItr.hasNext()) {
            Row r = chargeItr.next();
            r.setAttribute("Charges", r.getAttribute("Charges"));
            r.setAttribute("Baseamount", r.getAttribute("Baseamount"));
            r.setAttribute("TaxCode", r.getAttribute("TaxCode"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
            r.setAttribute("InstallmentAmount", r.getAttribute("InstallmentAmount")); 
            r.setAttribute("DueDate", r.getAttribute("StartDate"));     
        }
        chargeItr.closeRowSetIterator();
        ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
        RowSetIterator msItr = msVo.createRowSetIterator(null);
        while (msItr.hasNext()) {
            Row r = msItr.next();
            r.setAttribute("TaxCode", r.getAttribute("TaxCode"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));  
                
        }
        msItr.closeRowSetIterator();
        ViewObject paidCpVo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
        RowSetIterator paidCpItr = paidCpVo.createRowSetIterator(null);
        while (paidCpItr.hasNext()) {
            Row r = paidCpItr.next();
            r.setAttribute("TaxCode", r.getAttribute("TaxCode"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
            r.setAttribute("InstallmentAmount", r.getAttribute("InstallmentAmount")); 
                
        }
        paidCpItr.closeRowSetIterator(); 
    }

    public void onChangeChargeAmt(ValueChangeEvent valueChangeEvent) {
          valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //  To get booking line amount 
            Double bookLineAmt = 0.0;
            Double chargeVal = 0.0;
            double chargeAmt = 0.0;
            ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
            Row r = bookdtlvo.getCurrentRow();
            bookLineAmt = r.getAttribute("TotalAmount") == null ? 0.0 :
                    Double.parseDouble(r.getAttribute("TotalAmount").toString()); 
            
        //  To get charge detils 
            ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
            Row currRow = chargeVo.getCurrentRow();
            String type = currRow.getAttribute("ChargeMethod")==null?"":currRow.getAttribute("ChargeMethod").toString();
            chargeVal = currRow.getAttribute("Charges") == null ? 0.0 :
                Double.parseDouble(currRow.getAttribute("Charges").toString());
            if("%".equals(type)){
                chargeAmt = chargeVal.doubleValue() * bookLineAmt.doubleValue() / 100;
            }else{
                chargeAmt = chargeVal;
            }
            currRow.setAttribute("Baseamount", new BigDecimal(chargeAmt));
    }

    public void onChangeChargeType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
        Row currRow = chargeVo.getCurrentRow();
        currRow.setAttribute("Charges", new BigDecimal(0));
        currRow.setAttribute("Baseamount", new BigDecimal(0));
    }
    
    public Double getTotalInsPerc(){ 
        Double totalInsPerc = 0.0;
        ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
        RowSetIterator itr = msVo.createRowSetIterator(null);  
            while (itr.hasNext()) {
                Row r = itr.next();
                totalInsPerc +=
                    r.getAttribute("InstallmentPct") == null ? 0.0 :
                    Double.parseDouble(r.getAttribute("InstallmentPct").toString());
            }
        itr.closeRowSetIterator();
//        return Math.round(totalInsPerc * 100.00) / 100.00;      
        return totalInsPerc;      
    }
    
    public Double getTotalInsAmt(){ 
        Double totalInsAmt = 0.0;
        ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
        RowSetIterator itr = msVo.createRowSetIterator(null);  
            while (itr.hasNext()) {
                Row r = itr.next();
                totalInsAmt +=r.getAttribute("InstallmentAmount") == null ? 0.0 :Double.parseDouble(r.getAttribute("InstallmentAmount").toString());
            }
        itr.closeRowSetIterator();
        return Math.round(totalInsAmt* 100.00) / 100.00;      
//        return totalInsAmt;      
    }
    
    
    
    public Integer getTotalNoOfDays(){ 
        Integer totalInsAmt = 0;
        ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
        RowSetIterator itr = msVo.createRowSetIterator(null);  
            while (itr.hasNext()) {
                Row r = itr.next();
                totalInsAmt +=
                    r.getAttribute("NoOfDays") == null ? 0 :
                    Integer.parseInt(r.getAttribute("NoOfDays").toString());
            }
        itr.closeRowSetIterator();
        return totalInsAmt;      
    }

    public void onChangeInstalAmount(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //  To get booking line amount
            Double bookLineAmt = 0.0;
            Double msBaseAmt = 0.0;
            ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
            RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
            while (itr.hasNext()) {
                Row r = itr.next();
                bookLineAmt +=
                    r.getAttribute("TotalAmount") == null ? 0.0 :
                    Double.parseDouble(r.getAttribute("TotalAmount").toString());
            }
            itr.closeRowSetIterator();
            // System.err.println("bookLineAmt::"+bookLineAmt);
            
         // Installment perc calculation 
            ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
            Row currMS = msVo.getCurrentRow();
            double amt =
                    currMS.getAttribute("InstallmentAmount") == null ? 0 :
                    Double.parseDouble(currMS.getAttribute("InstallmentAmount").toString());
//        double amt =
//                valueChangeEvent.getNewValue() == null ? 0 :
//                Double.parseDouble(valueChangeEvent.getNewValue().toString());
            
            double pct = (amt / bookLineAmt.doubleValue()) * 100;
            currMS.setAttribute("InstallmentPct", new BigDecimal(pct));
            
        // Base amount calculation
            double taxRate =
                currMS.getAttribute("TaxRate") == null ? 0 :
                Double.parseDouble(currMS.getAttribute("TaxRate").toString());
            
            msBaseAmt =amt/(100 + taxRate)*100;
            currMS.setAttribute("Baseamount", new BigDecimal(msBaseAmt));  
    }

    public void onContinueBooking(ActionEvent actionEvent) {
        saveData(); 
        if(isValidToContinue()){
            ADFUtils.findOperation("Commit").execute();
            ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
            Row currRow = bookHdrvo.getCurrentRow();
            String bookingId = currRow.getAttribute("BookingHdrId")!=null?currRow.getAttribute("BookingHdrId").toString():"0";
            String code = PackageCalling.createMilestoneBooking(bookingId); 
            
            ViewObject chargeVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
            chargeVo.executeQuery();
            ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
            msVo.executeQuery();
            ViewObject paidCpVo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
            paidCpVo.executeQuery();
            
            if("S".equals(code)){
                ADFContext.getCurrent().getPageFlowScope().put("hdrSaved", "true"); 
                JSFUtils.addFacesInformationMessage("Booking created successfully !!");
                JSFUtils.addFacesInformationMessage("Installments and Other charges generated !!");
                ViewObject amtVo = ADFUtils.findIterator("BookingAmount_ROVOIterator").getViewObject();
                amtVo.executeQuery();
            }else{
                JSFUtils.addFacesErrorMessage("Please check, Error !!");
            } 
        }else{
            JSFUtils.addFacesErrorMessage("Please check, Details missing to ceate lease !!");
        }
        this.executeBooking();
        JSFUtils.hidePopup(bookContinuePopup);
    }
    public void onCancelBookPopup(ActionEvent actionEvent){
        JSFUtils.hidePopup(bookContinuePopup);
        
    }

    public void setBookContinuePopup(RichPopup bookContinuePopup) {
        this.bookContinuePopup = bookContinuePopup;
    }

    public RichPopup getBookContinuePopup() {
        return bookContinuePopup;
    }
    public void onCancelSubmitPopup(ActionEvent actionEvent){
        JSFUtils.hidePopup(submitPopup);
        
    }

    public void setSubmitPopup(RichPopup submitPopup) {
        this.submitPopup = submitPopup;
    }

    public RichPopup getSubmitPopup() {
        return submitPopup;
    }
    
    private boolean isCustomerValid() {
        ViewObject customerVo = ADFUtils.findIterator("XxplBookingCustomer_VOIterator").getViewObject();
        if(customerVo.getEstimatedRowCount()==0){
            JSFUtils.addFacesErrorMessage("Please add customers details !");   
            return false;
        }
        RowSetIterator dtlsRS = customerVo.createRowSetIterator(null);
        boolean flag = false;
        while (dtlsRS.hasNext()) { 
            Row r = dtlsRS.next();
            String primary = r.getAttribute("PrimaryFlag")!=null?r.getAttribute("PrimaryFlag").toString():"false";
            if("true".equals(primary)){
                flag = true;
            }
        }
        if(!flag){ 
            JSFUtils.addFacesErrorMessage("Please check, One customer should be primary !");   
        } 
        return flag;
    }

    public String onSubmitAction() {
        calculateAmount();
        if (isInstallmentPerValid()) {
            if (isUnitValidForBookingSubmit()) {
                ADFUtils.findOperation("Commit").execute(); 
                if (isCustomerValid()) {
                    if ("Y".equals(this.submitProcess())) {
                        return "toSave"; 
             }}
        }}
        return null;
    }
    
    public boolean isUnitValidForBookingSubmit(){
        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            String unitId = r.getAttribute("UnitId")!=null?r.getAttribute("UnitId").toString():"0";
            boolean flag = false;
            flag = isUnitAvailable(unitId);
            String bookStatus=bookHdrvo.getCurrentRow().getAttribute("Status")==null?"abc":bookHdrvo.getCurrentRow().getAttribute("Status").toString();
            String renwlStatus=bookHdrvo.getCurrentRow().getAttribute("Attribute3")==null?"abc":bookHdrvo.getCurrentRow().getAttribute("Attribute3").toString();
            System.err.println("FLAG+"+flag);
            if(!flag){
                
                if(bookStatus.equalsIgnoreCase("Revision")){
                    JSFUtils.addFacesInformationMessage("Revised Leasing Submitted for Approval");
                    return true;    
                }
                else if (renwlStatus.equalsIgnoreCase("Renewal")){
                    JSFUtils.addFacesInformationMessage("Renewed Leasing Submitted for Approval");
                    return true; 
            }
                else{
                    JSFUtils.addFacesErrorMessage("Please check, Unit is not available !");
                    return false;    
                }
            }
        }
        return true;
    }
    
    
    public String getPrimaryUnitMethod(){
        String unitId="0";
        String unitMethod=null;
        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
        while(itr.hasNext()){
            Row r=itr.next();
            unitId=r.getAttribute("UnitId")!=null?r.getAttribute("UnitId").toString():"0";
        }
        
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "UNIT_METHOD");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "XXPL_PROPERTY_UNITS");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "UNIT_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", unitId);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            unitMethod=GetDataVO.first().getAttribute("Id")==null?"OWN_UNIT":GetDataVO.first().getAttribute("Id").toString();
        }else{
            unitMethod="OWN_UNIT";
        }
        
        return unitMethod;
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

    public String onApproveBooking() { 
        String comments = approvalCmts.getValue()==null?"Approved":approvalCmts.getValue().toString(); 
        
        if("Y".equals(this.approveOrReject(comments, "APPROVE"))){ 
            JSFUtils.hidePopup(submitPopup);
            return "toSave";
        }else{
            JSFUtils.hidePopup(submitPopup);
            return null;
        } 
    }
    public String onRejectBooking() {
        String comments = approvalCmts.getValue()==null?"Rejected":approvalCmts.getValue().toString();
        
        if("Y".equals(this.approveOrReject(comments, "REJECT"))){ 
            JSFUtils.hidePopup(submitPopup);
            return "toSave";
        }else{
            JSFUtils.hidePopup(submitPopup);
            return null;
        }
    }

    public void ApprovalActions(ActionEvent actionEvent) {
        JSFUtils.showPopup(submitPopup);
    }

    public void onchangeCustPrimary(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject customerVo = ADFUtils.findIterator("XxplBookingCustomer_VOIterator").getViewObject();
        RowSetIterator dtlsRS = customerVo.createRowSetIterator(null);
        int currIndex = customerVo.getCurrentRowIndex();
        int i = 0;
        while (dtlsRS.hasNext()) { 
            Row r = dtlsRS.next();
            // System.err.println("PrimaryFlag--"+r.getAttribute("PrimaryFlag"));
            if (i == currIndex) {
                // System.err.println("Current row");
                i++;
                continue; 
                }
                // System.err.println("set false");
            r.setAttribute("PrimaryFlag", Boolean.FALSE);
            i++;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(customerTable);
        }

    public void setCustomerTable(RichTable customerTable) {
        this.customerTable = customerTable;
    }

    public RichTable getCustomerTable() {
        return customerTable;
    }
    public void onCancelReceiptCreate(ActionEvent actionEvent){ 
        JSFUtils.hidePopup(receiptConfirmation);
    }
    public String onCreateReceipt(){   
        ArrayList <String> list = new ArrayList<String>();
        //to get ms type and receipt type 
        String mileType  =
            ADFContext.getCurrent().getPageFlowScope().get("milestoneType")!= null ?
            ADFContext.getCurrent().getPageFlowScope().get("milestoneType").toString() : "";
        String receiptType  =
            ADFContext.getCurrent().getPageFlowScope().get("receiptType")!= null ?
            ADFContext.getCurrent().getPageFlowScope().get("receiptType").toString() : "";
        
        //to get booking hdr id 
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row hdrRow = bookHdrvo.getCurrentRow();
        String bookId = hdrRow.getAttribute("BookingHdrId")!=null?hdrRow.getAttribute("BookingHdrId").toString():"0";
        String msDtlId = "0"; 
        
        //to get BookingMsid 
        if ("MS".equals(mileType)) {
            ViewObject vo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
            Row r = vo.getCurrentRow();
            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
            
        } else if ("OC".equals(mileType)) {
            ViewObject vo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
            Row r = vo.getCurrentRow();
            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
            
        } else if ("CP".equals(mileType)) {
            ViewObject vo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
            Row r = vo.getCurrentRow();
            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
        } else {

        }
        if("G".equals(receiptType)){
            msDtlId= "0";
        }
        list = PackageCalling.createReceipt(bookId, msDtlId, mileType, receiptType);
        // System.err.println(list);
        if(list.get(0)!=null && "S".equals(list.get(0)) && !"0".equals(list.get(2))){        
            ADFContext.getCurrent().getPageFlowScope().put("receiptId", null);
            ADFContext.getCurrent().getPageFlowScope().put("receiptId", list.get(2));
            JSFUtils.addFacesInformationMessage(list.get(1));
            return "toReceipt";         
        }else{
            JSFUtils.addFacesErrorMessage(list.get(1));
            return null;
        } 
    }

    public void setReceiptConfirmation(RichPopup receiptConfirmation) {
        this.receiptConfirmation = receiptConfirmation;
    }

    public RichPopup getReceiptConfirmation() {
        return receiptConfirmation;
    }

    public void onClickAddReceipt(ActionEvent actionEvent) {
        JSFUtils.showPopup(receiptConfirmation);
    }

    public void onClickOkOCPopup(ActionEvent actionEvent) {
        JSFUtils.hidePopup(otherChargePop);
    }

    public void onCancelOCPopup(ActionEvent actionEvent) {
        JSFUtils.hidePopup(otherChargePop);
    }

    public void setOtherChargePop(RichPopup otherChargePop) {
        this.otherChargePop = otherChargePop;
    }

    public RichPopup getOtherChargePop() {
        return otherChargePop;
    }

    private void executeBooking() {
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Object id = bookHdrvo.getCurrentRow().getAttribute("BookingHdrId");
        ViewCriteria vc1 = bookHdrvo.createViewCriteria();
        ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
        vcr.setAttribute("BookingHdrId", id);
        vc1.addRow(vcr);
        bookHdrvo.applyViewCriteria(vc1);
        bookHdrvo.executeQuery();
    }

    public void onClickAttachment(ActionEvent actionEvent) {
        // Add event code here...
        ADFContext.getCurrent().getPageFlowScope().put("FuncId",
                                                               custom.lease.custom.view.beans.Utils.ADFUtils.getBoundAttributeValue("FuncId"));
                ADFContext.getCurrent().getPageFlowScope().put("FuncRefId",
                                                               custom.lease.custom.view.beans.Utils.ADFUtils.getBoundAttributeValue("BookingHdrId"));

    }
    
    public String onClickCheckList() {  
        ADFContext.getCurrent().getPageFlowScope().put("funcId", ADFUtils.getBoundAttributeValue("FuncId"));
        ADFContext.getCurrent().getPageFlowScope().put("funcRefId", ADFUtils.getBoundAttributeValue("BookingHdrId"));
        return "toCheckList";

    }
    
    public String submitProcess() { 
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "LEASE_BOOKING_APR_TYPE");
        ApprovalTypeVO.executeQuery();
        String approveType = ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
        String P_FUNC_ID = currRow.getAttribute("FuncId")==null?"7":currRow.getAttribute("FuncId").toString();
        String P_REF_ID = currRow.getAttribute("BookingHdrId")==null?"0":currRow.getAttribute("BookingHdrId").toString();
        String P_ATTRIBUTE1_ORG = currRow.getAttribute("OrgId")==null?"0":currRow.getAttribute("OrgId").toString();
//        this.isLeaseAmtLessThanPLAmt(); 
        String P_ATTRIBUTE2=currRow.getAttribute("ContractSignBy")==null?"0":currRow.getAttribute("ContractSignBy").toString(); 
        String Status=currRow.getAttribute("Status")==null?"Draft":currRow.getAttribute("Status").toString(); 
        String unitMethod=getPrimaryUnitMethod();
        String P_ATTRIBUTE5=null;
        String P_ATTRIBUTE3=null;
        String P_ATTRIBUTE4=null;
//        if(Status.equalsIgnoreCase("Revision")){
//            P_ATTRIBUTE3=Status;
//            P_ATTRIBUTE4=unitMethod;
//        }else{
//            P_ATTRIBUTE3=Status;
//            P_ATTRIBUTE4="NORMAL";
//        }
        String ccAddress=currRow.getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":currRow.getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api") || ccAddress.equalsIgnoreCase("PRISM")){
            ccAddress="prasenjit.d@4iapps.com";
        }
        
        String booking_number =currRow.getAttribute("BookingNumber")==null?"Error-000":currRow.getAttribute("BookingNumber").toString();
        String orgid =currRow.getAttribute("OrgId")==null?"0":currRow.getAttribute("OrgId").toString();
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
        ArrayList <String> list = new ArrayList<String>();
        // System.err.println("approveType=="+approveType);
        
        //for AUTO approval
        if(approveType.equalsIgnoreCase("AUTO")){
            this.autoSubmitProcess(P_FUNC_ID, P_REF_ID, BaseDetail.BOOKING_HDR_TABLE); 
            return "Y";
        }
        //for AUTO approval
        else if (approveType.equalsIgnoreCase("ADF")){
            
//            System.out.println("P_FUNC_ID==>"+P_FUNC_ID);
//            System.out.println("P_REF_ID===>"+P_REF_ID);
//            System.out.println("int value===>"+BaseDetail.initialLevel);
//            System.out.println("Table NAme===>"+BaseDetail.BOOKING_HDR_TABLE);
//            System.out.println("table status===>"+BaseDetail.BOOKING_STATUS_COLUMN);
//            System.out.println("table id===>"+BaseDetail.BOOKING_ID_COLUMN);
//            System.out.println("amt===>"+BaseDetail.initialAmount);
//            System.out.println("P_ATTRIBUTE1_ORG==>"+P_ATTRIBUTE1_ORG);
//            System.out.println("P_ATTRIBUTE2==>"+P_ATTRIBUTE2);
//            System.out.println("P_ATTRIBUTE3==>"+P_ATTRIBUTE3);
//            System.out.println("P_ATTRIBUTE4==>"+P_ATTRIBUTE4);
//            System.out.println("P_ATTRIBUTE5==>"+P_ATTRIBUTE5);
//              JSFUtils.addFacesInformationMessage("P_FUNC_ID==>"+P_FUNC_ID+" P_REF_ID===>"+P_REF_ID+" int value===>"+BaseDetail.initialLevel+
//             " Table NAme===>"+BaseDetail.BOOKING_HDR_TABLE+" table status===>"+BaseDetail.BOOKING_STATUS_COLUMN+" table id===>"+BaseDetail.BOOKING_ID_COLUMN+
//             " amt===>"+BaseDetail.initialAmount+" P_ATTRIBUTE1_ORG==>"+P_ATTRIBUTE1_ORG+" P_ATTRIBUTE2==>"+P_ATTRIBUTE2+" P_ATTRIBUTE3==>"+P_ATTRIBUTE3+
//             " P_ATTRIBUTE4==>"+P_ATTRIBUTE4+" P_ATTRIBUTE5==>"+P_ATTRIBUTE5);
            list=PackageCalling.invokeSubmit(
                            P_FUNC_ID, 
                            P_REF_ID, 
                            BaseDetail.initialLevel, 
                            BaseDetail.BOOKING_HDR_TABLE, 
                            BaseDetail.BOOKING_STATUS_COLUMN, 
                            BaseDetail.BOOKING_ID_COLUMN, 
                            BaseDetail.initialAmount, 
                            P_ATTRIBUTE1_ORG, 
                            P_ATTRIBUTE2, 
                            P_ATTRIBUTE3, 
                            P_ATTRIBUTE4, 
                            P_ATTRIBUTE5
                            );
            if(list.get(3)!=null){
//            String toAddress=list.get(3)==null?"prasenjit.d@4iapps.com":list.get(3).toString(); 
//            toAddress = "ibrahim.hb@4iapps.com";
//            String subject=BaseDetail.BOOKING_SUBJECT+" - Submitted for Approval "+"Lease No: "+booking_number;
//            String mailContent=MailTemplate.LeaseMailContent("Lease booking has been submitted for the Approval. Kindly Review and do the necessary action.", booking_number, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//            String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
            JSFUtils.addFacesInformationMessage("Booking Submitted for Approval");
//                JSFUtils.addFacesInformationMessage("To Address-"+toAddress);
//                JSFUtils.addFacesInformationMessage("Cc Address-"+ccAddress);
//                JSFUtils.addFacesErrorMessage(mailFlag);
                
            }else{
//                JSFUtils.addFacesErrorMessage("Error: Mail Notification Missing");
                JSFUtils.addFacesErrorMessage("Mail ID          :" +list.get(3));
                JSFUtils.addFacesErrorMessage("Error Code       :" +list.get(5));
                JSFUtils.addFacesErrorMessage("Error Message    :" +list.get(6));
                JSFUtils.addFacesErrorMessage("Status           :" +list.get(8));
            }
            return "Y"; 
        }else if(approveType.equalsIgnoreCase("PCS")){ 
            JSFUtils.addFacesErrorMessage("PCS not configured !"); 
            return "N";
        }else{
            JSFUtils.addFacesErrorMessage("Please check, Approved type not configured !"); 
            return "N";
        }
    }

    public void setApprovalCmts(RichInputText approvalCmts) {
        this.approvalCmts = approvalCmts;
    }

    public RichInputText getApprovalCmts() {
        return approvalCmts;
    }
    
    public String approveOrReject(String Comments, String ArStatus) {
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        String P_FUNC_ID=currRow.getAttribute("FuncId")==null?"7":currRow.getAttribute("FuncId").toString();
        String P_REF_ID=currRow.getAttribute("BookingHdrId")==null?"0":currRow.getAttribute("BookingHdrId").toString();
        String ccAddress=currRow.getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":currRow.getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api") || ccAddress.equalsIgnoreCase("PRISM")){
            ccAddress="prasenjit.d@4iapps.com";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        if(userlogin.equalsIgnoreCase("api") || ccAddress.equalsIgnoreCase("PRISM")){
            userlogin="prasenjit.d@4iapps.com";
        }
//        ccAddress="ibrahim.hb@4iapps.com";
//        userlogin="ibrahim.hb@4iapps.com";
        String booking_no=currRow.getAttribute("BookingNumber")==null?"Error-000":currRow.getAttribute("BookingNumber").toString();
        String userGroupID=currRow.getAttribute("UserGrpId")==null?"0":currRow.getAttribute("UserGrpId").toString();
        String levelNo=currRow.getAttribute("FlowLevel")==null?"0":currRow.getAttribute("FlowLevel").toString();
        String approveId=currRow.getAttribute("FlowWith")==null?"0":currRow.getAttribute("FlowWith").toString(); 
        String BUName="ABCD";  
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
            custom.lease.custom.view.beans.Utils.JSFUtils.addFacesErrorMessage("Error in configuration. Please Contact Administrator");
            return "N";
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, P_REF_ID, userGroupID, levelNo,
                                    approveId, Comments, ArStatus, P_FWD_TO,
                                    BaseDetail.BOOKING_HDR_TABLE,
                                    BaseDetail.BOOKING_STATUS_COLUMN,
                                    BaseDetail.BOOKING_ID_COLUMN,
                                    BaseDetail.initialAmount); 
            if(list.get(0)!=null){
            
                if("APPROVE".equals(ArStatus)){
                    
                    //- checking approve or pending
                    if(list.get(0).equalsIgnoreCase("Approved")) {
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Booking Approved Successfully"); 
                        String to_Address=ccAddress;
                        String cc_Address=ccAddress+","+userlogin;
                        String subject=BaseDetail.BOOKING_SUBJECT+" Approved "+"Booking Number "+booking_no; 
                        String mailContent=MailTemplate.LeaseMailContent("Lease booking has been approved. Kindly review and do the necessary action.", booking_no, BUName, BaseDetail.SysDate(), ccAddress, "Approved");
                        String  mailFlag=MailServices.sendMailWithoutAttachment(to_Address, cc_Address, "NULL", subject, mailContent);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage(mailFlag);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("To Address"+to_Address);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Cc Address"+cc_Address);
                    }else{
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Booking Approved Successfully and resubmitted to next level user");
                        String toAddress=list.get(2)==null?"prasenjit.d@4iapps.com":list.get(2).toString();
                        //String toAddress = "ibrahim.hb@gmail.com";
                        String subject=BaseDetail.BOOKING_SUBJECT+" Submitted for Approval "+"Booking Number "+booking_no; 
                        ccAddress=ccAddress+","+userlogin;
                        String mailContent=MailTemplate.LeaseMailContent("Lease booking has been submitted for the Approval. Kindly Review and do the necessary action.", booking_no, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
                        String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage(mailFlag);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("To Address"+toAddress);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Cc Address"+ccAddress);
                    }
                    
                }else if("REJECT".equals(ArStatus)){
                    if(list.get(0).equalsIgnoreCase("Rejected")) {
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Booking Rejected Successfully");
                        String to_Address=ccAddress;
                        String cc_Address=ccAddress+","+userlogin;
                        String subject=BaseDetail.BOOKING_SUBJECT+" Rejected "+"Booking Number "+booking_no; 
                        String mailContent=MailTemplate.LeaseMailContent("Lease booking has been rejected. Kindly review and do the necessary action.", booking_no, BUName, BaseDetail.SysDate(), ccAddress, "Rejected");
                        String  mailFlag=MailServices.sendMailWithoutAttachment(to_Address, cc_Address, "NULL", subject, mailContent);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage(mailFlag);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("To Address"+to_Address);
                        custom.lease.custom.view.beans.Utils.JSFUtils.addFacesInformationMessage("Cc Address"+cc_Address);
                    }else{
                        
                    }
                } 
                return "Y";
            }else{
                custom.lease.custom.view.beans.Utils.JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                custom.lease.custom.view.beans.Utils.JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                custom.lease.custom.view.beans.Utils.JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));
                return "N";
            }
        }
    }

    private String isLeaseAmtLessThanPLAmt() {
        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        Row r = bookdtlvo.first();
        Double bookLineAmt = 0.0, plAmount = 0.0, discAmount = 0.0 ,leaseAmount = 0.0;
        bookLineAmt = r.getAttribute("BookingAmount") == null ? 0.0 : Double.parseDouble(r.getAttribute("BookingAmount").toString());
        discAmount = r.getAttribute("DiscAmount") == null ? 0.0 : Double.parseDouble(r.getAttribute("DiscAmount").toString());
        plAmount = r.getAttribute("Attribute1") == null ? 0.0 : Double.parseDouble(r.getAttribute("Attribute1").toString());
        leaseAmount = bookLineAmt - discAmount; 
        
        if(leaseAmount >= plAmount){
            return "No";
        }else{
            return "Yes";
        }
    }

    public void setP8(RichPopup p8) {
        this.p8 = p8;
    }

    public RichPopup getP8() {
        return p8;
    }

    public void onClickApprovalHistory(ActionEvent actionEvent) {
        String plid=JSFUtils.resolveExpression("#{bindings.BookingHdrId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.BookingHdrId.inputValue}").toString();
             String fuctionid=JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}").toString();
             ADFContext.getCurrent().getSessionScope().put("plid",plid);
             ADFContext.getCurrent().getSessionScope().put("fuctionid",fuctionid);
             RichPopup.PopupHints hints=new RichPopup.PopupHints();
                 this.getP8().show(hints);
    }
    
    private boolean isInstallmentPerValid() {
        DecimalFormat df = new DecimalFormat("####0.00");
//        double insPerc = Double.valueOf(df.format(getTotalInsPerc()));  
//        if(insPerc!=100){
//            JSFUtils.addFacesErrorMessage("Please Check, Installment % should be 100.");
//            return false;
//        }else{
//            return true;
//        }
        double insPerc = Double.valueOf(df.format(getTotalInsAmt()));  
        Double bookLineAmt = 0.0;
        Integer msNoofDays=Integer.valueOf(getTotalNoOfDays());
        Integer bookingNoofDays=0;
        ViewObject bookdtlvo =
            ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            bookLineAmt +=r.getAttribute("TotalAmount") == null ? 0.0 : Double.parseDouble(r.getAttribute("TotalAmount").toString());
        }
        
        itr.closeRowSetIterator();
        double bookingLineAmt = Double.valueOf(df.format(bookLineAmt)); 
        
        bookingNoofDays=bookHdrvo.getCurrentRow().getAttribute("NoOfDays")==null?0:
                        Integer.parseInt(bookHdrvo.getCurrentRow().getAttribute("NoOfDays").toString());
            
        System.err.println("insPerc--"+insPerc);
        System.err.println("bookLineAmt--"+bookingLineAmt);
        if (insPerc != bookingLineAmt) {
            JSFUtils.addFacesErrorMessage("Please Check, Installment amount not matching with total line amount");
            return false;
        } else {
            if(bookingNoofDays.equals(msNoofDays)){
                return true;    
            }else{
                JSFUtils.addFacesErrorMessage("Please Check, Lease No of Days and Payment Plan No of Days are mismatch");                
                JSFUtils.addFacesErrorMessage("Lease Contract No of Days: "+bookingNoofDays);
                JSFUtils.addFacesErrorMessage("Payment Plan No of Days: "+msNoofDays);
                return false; 
            }
            
        }

    }

    public String onSaveCloseAction() {
        calculateAmount();  
        if(isInstallmentPerValid()){
            ADFUtils.findOperation("Commit").execute();  
            JSFUtils.addFacesInformationMessage("Booking Information Saved Successfully !"); 
            return "toSave";
        }else{
            return null;
        }
    }

    public void onCilckCalculate(ActionEvent actionEvent) {
        calculateAmount();  
    }

    public void onChangeLeaseStartDate(ValueChangeEvent valueChangeEvent) throws ParseException {  
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
//        isValidPeriod();
    }
    public void onChangeLeaseEndDate(ValueChangeEvent valueChangeEvent) throws ParseException {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
//        isValidPeriod();
    }
    
    public void isValidPeriod() throws ParseException {
        String msg = "0";
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        String sDate = currRow.getAttribute("BookingLeaseStartDate")!=null?currRow.getAttribute("BookingLeaseStartDate").toString():"0";
        String eDate = currRow.getAttribute("BookingLeaseEndDate")!=null?currRow.getAttribute("BookingLeaseEndDate").toString():"0";
        String msType = currRow.getAttribute("Attribute1")!=null?currRow.getAttribute("Attribute1").toString():"0";
        if("0".equals(msType)){
            JSFUtils.addFacesErrorMessage("Please select payment plan ! ");
        }else{
            if(!"0".equals(sDate) && !"0".equals(eDate)){
                msg = PackageCalling.checkValidDates(getDateByFormat(sDate), getDateByFormat(eDate), msType); 
                System.err.println("Return value--->>"+msg);
                
                if(!"SUCCESS".equals(msg)){
                    currRow.setAttribute("MsHdrId", null);
                    currRow.setAttribute("PlId", null);
                    currRow.setAttribute("MsPlMap_Trans", null);
                    JSFUtils.addFacesErrorMessage("Please check, Selected payment plan is not applicable  for selected date !");     
                }
            }  
        } 
    }

    public void setMoveInDate(RichInputDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public RichInputDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveOutDate(RichInputDate moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public RichInputDate getMoveOutDate() {
        return moveOutDate;
    }

    public void onCreateInvoiceMS(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
        readyToInvoice(vo);
    }
    public void onCreateInvoiceOC(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
        readyToInvoice(vo);
    }
    public void onCreateInvoiceCP(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
        readyToInvoice(vo);
    }
    public void readyToInvoice(ViewObject vo){
        Row currentRow = vo.getCurrentRow();
        currentRow.setAttribute("IntegrationFlag", "Y");
        ADFUtils.findOperation("Commit").execute();
        vo.executeQuery();
        JSFUtils.addFacesInformationMessage("Applied for Invoice Creation !"); 
    }

    private boolean isValidToContinue() {
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        String propertyId = currRow.getAttribute("PropertyId")!=null?currRow.getAttribute("PropertyId").toString():"0";
        String building = currRow.getAttribute("Building")!=null?currRow.getAttribute("Building").toString():"0";
        String orgId = currRow.getAttribute("OrgId")!=null?currRow.getAttribute("OrgId").toString():"0";
        String plId = currRow.getAttribute("PlId")!=null?currRow.getAttribute("PlId").toString():"0";
        String msId = currRow.getAttribute("MsHdrId")!=null?currRow.getAttribute("MsHdrId").toString():"0";
        String sDate = currRow.getAttribute("BookingLeaseEndDate")!=null?currRow.getAttribute("BookingLeaseEndDate").toString():"0";
        String eDate = currRow.getAttribute("BookingLeaseStartDate")!=null?currRow.getAttribute("BookingLeaseStartDate").toString():"0";
        if("0".equals(propertyId)){
            JSFUtils.addFacesErrorMessage("Please check, Property Missing !!");
            return false;
        }
        if("0".equals(building)){
            JSFUtils.addFacesErrorMessage("Please check, Building Missing !!");
            return false;
        }
        if("0".equals(orgId)){
            JSFUtils.addFacesErrorMessage("Please check, Business Unit Missing !!");
            return false;
        }
        if("0".equals(plId)){
            JSFUtils.addFacesErrorMessage("Please check, Price List Missing !!");
            return false;
        }
        if("0".equals(msId)){
            JSFUtils.addFacesErrorMessage("Please check, Payment Plan Missing !!");
            return false;
        }
        if("0".equals(sDate)){
            JSFUtils.addFacesErrorMessage("Please check, Lease Start Date Missing !!");
            return false;
        }
        if("0".equals(eDate)){
            JSFUtils.addFacesErrorMessage("Please check, Lease End Date Missing !!");
            return false;
        }
        
        return true;
    }

    private boolean isUnitAvailable(String unitId) {
        ViewObject unitVo = ADFUtils.findIterator("XxplPropertyUnitsView1Iterator").getViewObject();
        ViewCriteria vc1 = unitVo.createViewCriteria();
        ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
        vcr.setAttribute("UnitId", unitId);
        vc1.addRow(vcr);
        vcr.setAttribute("Status", "Available");
        vc1.addRow(vcr);
        unitVo.applyViewCriteria(vc1);
        unitVo.executeQuery();
        System.err.println("COUNT="+unitVo.getEstimatedRowCount());
        if(unitVo.getEstimatedRowCount()>0){
            return true;
        }
        unitVo.applyViewCriteria(null);
        unitVo.executeQuery();
        return false;
    }
    
    private String getDateByFormat(String repDate) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(repDate);
            SimpleDateFormat ft = new SimpleDateFormat("dd-MMM-yyyy");
            return ft.format(date).toUpperCase();
     }

    public void onClickAddCP(ActionEvent actionEvent) {
        Object propertyId = ADFUtils.getBoundAttributeValue("PropertyId");
        ViewObject cpVO = ADFUtils.findIterator("CarParkingROVOIterator").getViewObject();
        cpVO.setNamedWhereClauseParam("BVS_PROPERTY_ID", propertyId); 
        cpVO.setNamedWhereClauseParam("BVS_UNIT_TYPE", "PAID");
        cpVO.executeQuery();
        JSFUtils.showPopup(addCarPop);
    }

    public void setAddCarPop(RichPopup addCarPop) {
        this.addCarPop = addCarPop;
    }

    public RichPopup getAddCarPop() {
        return addCarPop;
    }

    public void onClickAddCPInPop(ActionEvent actionEvent) {
        String bookNo = ADFUtils.getBoundAttributeValue("BookingNumber").toString();
        String carParkingLeaseNumber ="CP_" + bookNo + "_";
        ViewObject msPaidVo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
        ViewObject cpVO = ADFUtils.findIterator("CarParkingROVOIterator").getViewObject();
        RowSetIterator rs=cpVO.createRowSetIterator(null);
        while(rs.hasNext()){
            Row r=rs.next();
            if(r.getAttribute("SelectAll_Trans")!=null && r.getAttribute("SelectAll_Trans").equals(true)){
                    // Inserting Row
                    Row msRow = msPaidVo.createRow();
                    msRow.setAttribute("CarparkingId", r.getAttribute("UnitId"));
                    msRow.setAttribute("ChargeMethod", r.getAttribute("AllotType"));
                    msRow.setAttribute("Baseamount", r.getAttribute("FuncId"));
                    msRow.setAttribute("MilestoneType", "CP"); 
                    msRow.setAttribute("ChargeType", "CP"); 
                    String msDtlId = msRow.getAttribute("BookingMsDtlId").toString();
                    msRow.setAttribute("CarParkingLeaseNumber", carParkingLeaseNumber + msDtlId);  
                    msPaidVo.insertRow(msRow);
                    updateUnitStatus(r.getAttribute("UnitId"));
                }
        }
        ADFUtils.findOperation("Commit").execute();  
        addCarPop.hide();
        JSFUtils.addFacesInformationMessage("Paid Carparking added ! ");
}

    private void updateUnitStatus(Object unitId) {
        ViewObject unitVo = ADFUtils.findIterator("XxplPropertyUnitsView1Iterator").getViewObject();
        ViewCriteria vc1 = unitVo.createViewCriteria();
        ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
        vcr.setAttribute("UnitId", unitId);
        vc1.addRow(vcr);
        unitVo.applyViewCriteria(vc1);
        unitVo.executeQuery();
        if(unitVo.getEstimatedRowCount()>0){
            Row row = unitVo.first();
            row.setAttribute("Status", "Allotted");
        }
        unitVo.applyViewCriteria(null);
        unitVo.executeQuery();
        
    }

    public void setP6(RichPopup p6) {
        this.p6 = p6;
    }

    public RichPopup getP6() {
        return p6;
    }

    ViewObject msVO=ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
    
    public void onChangeMSStartDate(ValueChangeEvent valueChangeEvent) throws ParseException {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("OLD==>"+valueChangeEvent.getOldValue());
        System.err.println("NEW==>"+valueChangeEvent.getNewValue());
           if(valueChangeEvent.getNewValue()!=null){
            String bookingHDR=bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();   
            String fromDate=valueChangeEvent.getNewValue()!=null?valueChangeEvent.getNewValue().toString():"0";    
//            System.err.println("==>From"+getDateByFormat(fromDate));
            String toDate=null;
            dateValidateVO.setNamedWhereClauseParam("P_BOOKING_HDR_ID", bookingHDR);
            dateValidateVO.setNamedWhereClauseParam("P_FROM_DATE", getDateByFormat(fromDate));
            dateValidateVO.setNamedWhereClauseParam("P_TO_DATE", toDate);
            dateValidateVO.setNamedWhereClauseParam("P_TYPE", "START");
            dateValidateVO.setNamedWhereClauseParam("P_TYPES", "ABC");
            dateValidateVO.executeQuery();
            String l_value=dateValidateVO.first().getAttribute("DataValue")==null?"-1":dateValidateVO.first().getAttribute("DataValue").toString();
            System.out.println("l_value==>"+l_value);
            if(l_value.equalsIgnoreCase("0")){
                msVO.getCurrentRow().setAttribute("DueDate", null);
                msVO.getCurrentRow().setAttribute("InstallmentAmount", 0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(msEndDate);              
            }else{
                JSFUtils.addFacesErrorMessage("Selected date range exit booking lease limit or already invoiced. Kindly choose another date");
                msStartDate.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(msStartDate);
            }
        }
    }
            

    public void onChangeMSEndDate(ValueChangeEvent valueChangeEvent) throws ParseException {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
//        System.err.println("OLD==>"+valueChangeEvent.getOldValue());
//        System.err.println("NEW==>"+valueChangeEvent.getNewValue());
//            String fromDate=msStartDate.getValue()==null?"0":msStartDate.getValue().toString();
           if(valueChangeEvent.getNewValue()!=null){
            System.out.println("Start Date==>"+msStartDate.getValue());
            String bookingHDR=bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();   
            String fromDate=msStartDate.getValue()==null?"0":msStartDate.getValue().toString();
            String toDate=valueChangeEvent.getNewValue()!=null?valueChangeEvent.getNewValue().toString():"0";    
            
            if(fromDate.equalsIgnoreCase("0")){
                JSFUtils.addFacesErrorMessage("Kindly choose the start date");
                msEndDate.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(msEndDate);
                
            }else{
                System.out.println("From Date==>"+getDateByFormat(fromDate));
                System.out.println("To Date==>"+getDateByFormat(toDate));
                
                dateValidateVO.setNamedWhereClauseParam("P_BOOKING_HDR_ID", bookingHDR);
                dateValidateVO.setNamedWhereClauseParam("P_FROM_DATE", getDateByFormat(fromDate));
                dateValidateVO.setNamedWhereClauseParam("P_TO_DATE", getDateByFormat(toDate));
                dateValidateVO.setNamedWhereClauseParam("P_TYPE", "END");
                dateValidateVO.setNamedWhereClauseParam("P_TYPES", "NO_OF_DAYS");
                dateValidateVO.executeQuery();
                String l_value=dateValidateVO.first().getAttribute("DataValue")==null?"-1":dateValidateVO.first().getAttribute("DataValue").toString();
                String l_no_of_days=dateValidateVO.first().getAttribute("NoOfDays")==null?"0":dateValidateVO.first().getAttribute("NoOfDays").toString();
                int tot_no_of_days=Integer.parseInt(l_no_of_days);
                System.out.println("l_value==>"+tot_no_of_days);
                if(l_value.equalsIgnoreCase("0")){
                     if(tot_no_of_days>=0){
                         System.err.println("tot_no_of_days==>"+tot_no_of_days);
                         msVO.getCurrentRow().setAttribute("NoOfDays", tot_no_of_days);
                     }else{
                         JSFUtils.addFacesErrorMessage("Selected date range exit booking lease date limit or already invoiced. Kindly choose another date");
                         msEndDate.setValue(null);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(msEndDate);
                     }
                     
                }else{
                    JSFUtils.addFacesErrorMessage("Selected date range exit booking lease limit or already invoiced. Kindly choose another date");
                    msEndDate.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(msEndDate);
                }
            }
            
        }
    }

    public void setMsStartDate(RichInputDate msStartDate) {
        this.msStartDate = msStartDate;
    }

    public RichInputDate getMsStartDate() {
        return msStartDate;
    }

    public void setMsEndDate(RichInputDate msEndDate) {
        this.msEndDate = msEndDate;
    }

    public RichInputDate getMsEndDate() {
        return msEndDate;
    }

    public void onClickCreatePaymentPlan(ActionEvent actionEvent) {
        System.out.println("=Booking MS="+bookingMSVO.getEstimatedRowCount());
        long num = bookingMSVO.getEstimatedRowCount()+1;
        Row msRow=bookingMSVO.createRow();
        msRow.setAttribute("MilestoneType", "MS");
        msRow.setAttribute("SeqNumber", bookingMSVO.getEstimatedRowCount()+1);
        msRow.setAttribute("InstallmentType", "CHEQUE-"+num);
        msRow.setAttribute("ChargeType", "LOU");
        msRow.setAttribute("PaymentTerm", "5");
        msRow.setAttribute("MilestonesStatus", "A");
        msRow.setAttribute("Attribute6", "NEW");
        msRow.setAttribute("IntegrationFlag", "N");//NO_OF_DAYS
        msRow.setAttribute("NoOfDays", "0");
        
        bookingMSVO.insertRow(msRow);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mst1);
        
    }

    public void setMst1(RichTable mst1) {
        this.mst1 = mst1;
    }

    public RichTable getMst1() {
        return mst1;
    }

    public void onClickDeletePaymentPlan(ActionEvent actionEvent) {
        
        String invoiceNum=bookingMSVO.getCurrentRow().getAttribute("InvoiceNumber")==null?"ABC":bookingMSVO.getCurrentRow().getAttribute("InvoiceNumber").toString();
        
        if(invoiceNum.equalsIgnoreCase("ABC")){
            bookingMSVO.getCurrentRow().remove();
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Record removed Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Selectec Payment Plan already Invoiced, Invoice Number"+invoiceNum);
        }
        
        

    }

    public void onClickDeleteRcpt(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject rcptVO=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
        String rcptId = rcptVO.getCurrentRow().getAttribute("ReceiptId")==null ? " " : rcptVO.getCurrentRow().getAttribute("ReceiptId").toString();
        String receiptStatus = rcptVO.getCurrentRow().getAttribute("InterfaceStatus")==null ? "Draft" : rcptVO.getCurrentRow().getAttribute("InterfaceStatus").toString();
        System.out.println("Current Receipt id ::"+rcptVO.getCurrentRow().getAttribute("ReceiptId"));
        System.out.println("rcptId==>"+rcptId);
        System.out.println("receiptStatus==>"+receiptStatus);
        ViewObject rcptDtlVO=ADFUtils.findIterator("XxplReceiptLineView2Iterator").getViewObject();
//        rcptDtlVO.executeQuery();
        System.out.println("Estmated rows ::"+rcptDtlVO.getEstimatedRowCount());
        if(receiptStatus.equalsIgnoreCase("Draft") 
            || receiptStatus.equalsIgnoreCase("Error") 
            || receiptStatus.equalsIgnoreCase("ERROR")){
            ViewCriteria vc=rcptDtlVO.createViewCriteria();
            ViewCriteriaRow vcRow=vc.createViewCriteriaRow();
            vcRow.setAttribute("ReceiptId", rcptId);
            vc.addRow(vcRow);
            rcptDtlVO.applyViewCriteria(vc);
            rcptDtlVO.executeQuery();
            if (rcptDtlVO.getEstimatedRowCount()>=0){
            String rcptDtlId=rcptDtlVO.first().getAttribute("ReceiptDtlId")==null?"0":rcptDtlVO.first().getAttribute("ReceiptDtlId").toString();
            System.out.println("Current Receipt dtl id ::"+rcptDtlId);
            //remove recept hdr line
            rcptVO.getCurrentRow().remove();
            //remove recpt dtl line
            rcptDtlVO.first().remove();
                ADFUtils.findOperation("Commit").execute();
                JSFUtils.addFacesInformationMessage("Receipts Deleted Successfully !!");
            }
        }else{
            JSFUtils.addFacesInformationMessage("Delete is not possible for selected receipt,"+receiptStatus);            
        }
    }

    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void onClickAddRcpt(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("BOOKING ID ::"+bookHdrvo.getCurrentRow().getAttribute("BookingHdrId"));
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplReceiptTransaction_V_ROVO1Iterator").getViewObject();
        rcptTrnstnRoVo.executeQuery();
    }

    public void onClickTrnsfrToAr(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject rcptVO=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
        System.out.println("Estmated rows ::"+rcptVO.getEstimatedRowCount());
        System.out.println("Current Receipt id ::"+rcptVO.getCurrentRow().getAttribute("ReceiptId"));
        String rcptId = rcptVO.getCurrentRow().getAttribute("ReceiptId")==null ? "0" : rcptVO.getCurrentRow().getAttribute("ReceiptId").toString();
        ViewCriteria vc=rcptVO.createViewCriteria();
        ViewCriteriaRow vcRow=vc.createViewCriteriaRow();
        vcRow.setAttribute("ReceiptCategory", "LEASE");
        vcRow.setAttribute("ReceiptId", rcptId);
        vc.addRow(vcRow);
        rcptVO.applyViewCriteria(vc);
        rcptVO.executeQuery();
        System.out.println("Estmated rows after view criteria ::"+rcptVO.getEstimatedRowCount());
        if (rcptVO.getEstimatedRowCount()>0){
            RowSetIterator rsi = rcptVO.createRowSetIterator(null);
            while(rsi.hasNext()){
                Row r = rsi.next();
                String interfaceStatus = r.getAttribute("InterfaceStatus")==null ? "Draft" : r.getAttribute("InterfaceStatus").toString();
               System.out.println("InterfaceStatus :"+interfaceStatus);
                if(interfaceStatus.equalsIgnoreCase("Draft") || interfaceStatus.equalsIgnoreCase("Error")){
                    r.setAttribute("InterfaceStatus", "AR_TRANSFER");
                }
            }
            rsi.closeRowSetIterator();
            ADFUtils.findOperation("Commit").execute();
        }else{            
            JSFUtils.addFacesInformationMessage("No Receipts to integrate !!");
        }
        rcptVO.applyViewCriteria(null);
        rcptVO.executeQuery();
        
    }

    public void onClickReceiptDelete(ActionEvent actionEvent) {
        ViewObject rcptVO=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
        ViewObject rcptDtlVO=ADFUtils.findIterator("XxplReceiptLineView2Iterator").getViewObject();
        String rcptId = rcptVO.getCurrentRow().getAttribute("ReceiptId")==null ? " " : rcptVO.getCurrentRow().getAttribute("ReceiptId").toString();
        String receiptStatus = rcptVO.getCurrentRow().getAttribute("InterfaceStatus")==null ? "Draft" : rcptVO.getCurrentRow().getAttribute("InterfaceStatus").toString();
        System.out.println("Current Receipt id ::"+rcptVO.getCurrentRow().getAttribute("ReceiptId"));
        System.out.println("rcptId==>"+rcptId);
        System.out.println("receiptStatus==>"+receiptStatus);
        if(receiptStatus.equalsIgnoreCase("Draft")||
           receiptStatus.equalsIgnoreCase("Error")||
           receiptStatus.equalsIgnoreCase("ERROR")){
            //-------Detail
            ViewCriteria vc=rcptDtlVO.createViewCriteria();
            ViewCriteriaRow vcRow=vc.createViewCriteriaRow();
            vcRow.setAttribute("ReceiptId", rcptId);
            vc.addRow(vcRow);
            rcptDtlVO.applyViewCriteria(vc);
            rcptDtlVO.executeQuery();
            if(rcptDtlVO.getEstimatedRowCount()>=0){
                RowSetIterator dtlRS=rcptDtlVO.createRowSetIterator(null);
                while(dtlRS.hasNext()){
                    Row r=dtlRS.next();
//                    String rcptDtlId=rcptDtlVO.first().getAttribute("ReceiptDtlId")==null?"0":rcptDtlVO.first().getAttribute("ReceiptDtlId").toString();
//                    System.out.println("Current Receipt dtl id ::"+rcptDtlId);
                    r.remove();
                }
                dtlRS.closeRowSetIterator();
                rcptVO.getCurrentRow().remove();
                ADFUtils.findOperation("Commit").execute();
                JSFUtils.addFacesInformationMessage("Receipts Deleted Successfully !!");
            }
        }else{
            JSFUtils.addFacesInformationMessage("Delete is not possible for selected receipt,"+receiptStatus);            
        }
        
                
    }
    public String leaseToCancel() {
        ArrayList <String> returnList = new ArrayList<String>();
        String bookingId=bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();   
//        String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.cancellation(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
    //        JSFUtils.addFacesInformationMessage(returnList.get(1));
           // ADFContext.getCurrent().getPageFlowScope().put("addEditMode", "ADD");
            ADFContext.getCurrent().getPageFlowScope().put("editCancelId", returnList.get(0));
            return "toCancelScreen";
        }else{
            System.err.println("in else");
            JSFUtils.addFacesErrorMessage(returnList.get(1)); 
            return null;
        }
    }

    public String leaseToMoveIn() {
        // Add event code here...
        ArrayList <String> returnList = new ArrayList<String>();
        String bookingId=bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();
       // String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.moveIn(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
           // JSFUtils.addFacesInformationMessage(returnList.get(1));
            ADFContext.getCurrent().getPageFlowScope().put("navigation", "edit");
            ADFContext.getCurrent().getPageFlowScope().put("MoveInOutId", returnList.get(0));
            return "toMoveInOutScreen"; 
        }else{
            System.err.println("in else");
            JSFUtils.addFacesErrorMessage(returnList.get(1)); 
            return null;
        }
       
    }

    public String leaseToMoveOut() {
        ArrayList <String> returnList = new ArrayList<String>();
        String bookingId=bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();
      //  String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.moveOut(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
          //  JSFUtils.addFacesInformationMessage(returnList.get(1));
            ADFContext.getCurrent().getPageFlowScope().put("navigation", "edit");
            ADFContext.getCurrent().getPageFlowScope().put("MoveInOutId", returnList.get(0));
            return "toMoveInOutScreen";
        }else{
            System.err.println("in else");
            JSFUtils.addFacesErrorMessage(returnList.get(1)); 
            return null;
        }

    }

    public String onClickCancelLease() {
        // Add event code here...
        String back="";
        ViewObject bookHdrvo = ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
        Row currRow = bookHdrvo.getCurrentRow();
        String P_REF_ID = currRow.getAttribute("BookingHdrId")==null?"0":currRow.getAttribute("BookingHdrId").toString();
        String userName = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
               
        ArrayList <String> list = new ArrayList<String>();               
            list=PackageCalling.invokeRevertLease(P_REF_ID,userName);   
        if(list.get(0)!=null){
        String response=list.get(0)==null?"":list.get(0).toString();
        if(response.equalsIgnoreCase("SUCCESS")){
            System.out.println("response -"+response);
            JSFUtils.addFacesInformationMessage("Lease cancelled successfully !!!");
            back="toSave";
        }else{
            JSFUtils.addFacesErrorMessage(response); 
         }        
        }else{
        JSFUtils.addFacesErrorMessage("Error: Please check !!!");
        }
        return back;
    }

    public void doSendReceiptMailPop(ActionEvent actionEvent) {
        // Add event code here...
        unSelectAllReceipts();
        ViewObject sendRcptMail=ADFUtils.findIterator("LeaseSendMailDetails_V_ROVO1Iterator").getViewObject();
        ViewCriteria vc = sendRcptMail.createViewCriteria();
                ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
                vcRow.setAttribute("PType","RECEIPT");
                vc.addRow(vcRow);
                sendRcptMail.applyViewCriteria(vc);
                sendRcptMail.executeQuery();
                System.out.println("Estimated Receipts row counts :"+sendRcptMail.getEstimatedRowCount());
        toAddress.setValue("prasenjit.d@4iapps.com");
        String path ="ReceiptMail";
        ADFContext.getCurrent().getPageFlowScope().put("mailPath", path);
        String mailPath = ADFContext.getCurrent().getPageFlowScope().get("mailPath")==null?"":ADFContext.getCurrent().getPageFlowScope().get("mailPath").toString();
        System.out.println("Receipt mailPath ::"+mailPath);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.toAddress);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.t22);
        JSFUtils.showPopup(p17);
    }

    public void doSendInvoiceMail(ActionEvent actionEvent) {
        // Add event code here...
        unSelectAllReceipts();
        ViewObject sendInvMail=ADFUtils.findIterator("LeaseSendMailDetails_V_ROVO1Iterator").getViewObject();
        ViewCriteria vc = sendInvMail.createViewCriteria();
                ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
                vcRow.setAttribute("PType","INVOICE");
                vc.addRow(vcRow);
                sendInvMail.applyViewCriteria(vc);
                sendInvMail.executeQuery();
                System.out.println("Estimated Invoice row counts :"+sendInvMail.getEstimatedRowCount());
        toAddress.setValue("prasenjit.d@4iapps.com");
        String path ="InvoiceMail";
        ADFContext.getCurrent().getPageFlowScope().put("mailPath", path);
        String mailPath = ADFContext.getCurrent().getPageFlowScope().get("mailPath")==null?"":ADFContext.getCurrent().getPageFlowScope().get("mailPath").toString();
        System.out.println("Invoice mailPath ::"+mailPath);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.toAddress);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.t22);
        JSFUtils.showPopup(p17);
    }

    public void setP17(RichPopup p17) {
        this.p17 = p17;
    }

    public RichPopup getP17() {
        return p17;
    }

    public void doSendRcptMail(ActionEvent actionEvent) throws FileNotFoundException,
                                                         SQLException,
                                                         InterruptedException {
     ViewObject vo =
         ADFUtils.findIterator("LeaseSendMailDetails_V_ROVO1Iterator").getViewObject();
     RowSetIterator rsIter = vo.createRowSetIterator(null); 
     String strMail;
     Set<String> mailList = new HashSet<String>();
     ArrayList<String> receiptList = new ArrayList<String>(); 
     while (rsIter.hasNext()) {
         Row r = rsIter.next();
         String selectVal =
             r.getAttribute("SelectTrans") != null ? r.getAttribute("SelectTrans").toString() :
             "false";
         System.out.println("SelectTrans :"+selectVal);
         if ("true".equals(selectVal)) {
             String receiptId =
                 r.getAttribute("PkId") != null ? r.getAttribute("PkId").toString() : "0";
             System.out.println("receiptId :"+receiptId);
             receiptList.add(receiptId);
             String mail = toAddress.getValue()==null ? "0" : toAddress.getValue().toString();
    //                        r.getAttribute("MailId") != null ? r.getAttribute("MailId").toString() :
    //                        "prasenjit.d@4iapps.com";
             System.out.println("mail ::"+mail);
             if (!"0".equals(mail)) {
                 mailList.add(mail);
    //                    mailList.add("ibrahim.hb@4iapps.com");
             }
         } else {

         }
     } 
     
     if(mailList.size()!=0){
         strMail = mailList.toString().replaceAll("\\[|\\]|\\s", "");
         System.out.println("strMail before from loop ::::"+strMail);
         strMail = toAddress.getValue()==null ? "0" : toAddress.getValue().toString();
         System.out.println("strMail after without loop ::::"+strMail);
         Row rowOne = vo.first();
         rowOne.setAttribute("ReceiptList", receiptList.toString().replaceAll("\\[|\\]|\\s", ""));
         String receiptL=rowOne.getAttribute("ReceiptList").toString();
         System.out.println("Receipts List ::"+rowOne.getAttribute("ReceiptList"));
         rowOne.setAttribute("ToAddress", strMail);
         //                JSFUtils.showPopup(mailPop);
         //                secondSendMethod(actionEvent);
         //
         //
            try {
                sendReceiptInMail(receiptL,strMail);
            } catch (IOException e) {
            } catch (JSONException e) {
            }
            System.err.println("receiptList -->"+receiptList);
    //                JSFUtils.addFacesInformationMessage("1st END");
         
     }else{
         JSFUtils.addFacesErrorMessage("Please add mail address to send receipts !!");
     }
     
    }
    
    public void sendReceiptInMail(String receiptList,String toAddrs) throws SQLException, FileNotFoundException, //XDOException,
                                             IOException, JSONException {
        Object URLPort= "https://jcs.omniyat.com";
        String mailPath = ADFContext.getCurrent().getPageFlowScope().get("mailPath")==null?"":ADFContext.getCurrent().getPageFlowScope().get("mailPath").toString();
        System.out.println("Invoice mailPath ::"+mailPath);
        String path = "";
        if (mailPath.equalsIgnoreCase("ReceiptMail")){
            path = "/MailService/webresources/generic/review?p="+receiptList+"&to="+toAddrs+"&P_File_Type=pdf";
        }else if (mailPath.equalsIgnoreCase("InvoiceMail")){
            path = "/MailService/webresources/generic/MS?MS_id="+receiptList+"&to="+toAddrs+"&P_File_Type=pdf";
        }else {
            path = "/MailService/webresources/generic/review?p="+receiptList+"&to="+toAddrs+"&P_File_Type=pdf"; 
        }
        String url = ""; 
        if(URLPort.toString().equals("URL not found")){
            URLPort="https://jcs.omniyat.com";
        } 
        url = URLPort + path;
        String result = "Not Sent";
        System.err.println("url--"+url);
        result = InvokeWebServices.invokeRestServicesNoParam(url);
        
        System.err.println("result--"+result);
        p17.hide();
        JSFUtils.addFacesInformationMessage(result);
    //        return result;
    }
    
    private void unSelectAllReceipts() {
        ViewObject vo = ADFUtils.findIterator("LeaseSendMailDetails_V_ROVO1Iterator").getViewObject();
        RowSetIterator rsIter = vo.createRowSetIterator(null);  
        while (rsIter.hasNext()) {
            Row r = rsIter.next();
            r.setAttribute("SelectTrans", "false");
            r.setAttribute("ToAddress", "");
       } 
    }

    public void setT22(RichTable t22) {
        this.t22 = t22;
    }

    public RichTable getT22() {
        return t22;
    }

    public void setToAddress(RichInputText toAddress) {
        this.toAddress = toAddress;
    }

    public RichInputText getToAddress() {
        return toAddress;
    }

    public void onClickAddNormalUnits(ActionEvent actionEvent) {
        // Add event code here...
        String propId=bookHdrvo.getCurrentRow().getAttribute("PropertyId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("PropertyId").toString(); 
        String buildId=bookHdrvo.getCurrentRow().getAttribute("Building")==null?"0":bookHdrvo.getCurrentRow().getAttribute("Building").toString();
        String unitType="not like 'CAR_PARKING'";
        filterMultiUnit(unitType,propId,buildId);      
        JSFUtils.showPopup(p15);
    }

    public void onClickAddCarParkingUnits(ActionEvent actionEvent) {
        // Add event code here...
        String propId=bookHdrvo.getCurrentRow().getAttribute("PropertyId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("PropertyId").toString(); 
        String buildId=bookHdrvo.getCurrentRow().getAttribute("Building")==null?"0":bookHdrvo.getCurrentRow().getAttribute("Building").toString();
        String unitType="CAR_PARKING";
        filterMultiUnit(unitType,propId,buildId);      
        JSFUtils.showPopup(p15);
    }

    public void setP15(RichPopup p15) {
        this.p15 = p15;
    }

    public RichPopup getP15() {
        return p15;
    }

    public void doAddMultiUnits(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplMultiUnitBooking_V_ROVO2Iterator").getViewObject(); 
        RowSetIterator dtlsRS = rcptTrnstnRoVo.createRowSetIterator(null);
        String bkHdrId = bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();
        String propId = bookHdrvo.getCurrentRow().getAttribute("PropertyId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("PropertyId").toString();
        String building = bookHdrvo.getCurrentRow().getAttribute("Building")==null?"0":bookHdrvo.getCurrentRow().getAttribute("Building").toString();
        String flag=null;
        String areaValue= null;
        String unitType= null;
        String uom= null;
        String propertyId= null;
        String buildId= null;
        String unitId= null;
        String baserice= null; //not available in view
        String bookingAmt= null; //not available in view
        Object curDate=null;
        int i =1;
        System.out.println("No of rows :"+rcptTrnstnRoVo.getEstimatedRowCount());
        while(dtlsRS.hasNext()){
            Row r = dtlsRS.next();
            flag = r.getAttribute("SelectTrans")==null ? "0" : r.getAttribute("SelectTrans").toString();
            propertyId = r.getAttribute("PropertyId")==null ? "0" : r.getAttribute("PropertyId").toString();
            buildId = r.getAttribute("BuildId")==null ? "0" : r.getAttribute("BuildId").toString();
            unitId = r.getAttribute("UnitId")==null ? "0" : r.getAttribute("UnitId").toString();
            areaValue = r.getAttribute("AreaVales")==null ? "0" : r.getAttribute("AreaVales").toString();
            unitType = r.getAttribute("UnitType")==null ? "0" : r.getAttribute("UnitType").toString();
            uom=r.getAttribute("Uom")==null ? "0" : r.getAttribute("Uom").toString();
//            curDate = getCurrentDate();
            if(flag.equalsIgnoreCase("true")){
                System.out.println("Start------------"+i);                
                System.out.println("flag "+ flag);
                System.out.println("Bk Id "+ bkHdrId);
                System.out.println("propertyId "+ propertyId);
                System.out.println("buildId "+ buildId);
                System.out.println("unitId "+ unitId); //ReceiptAmount
                System.out.println("areaValue "+ areaValue);
                System.out.println("unitType "+ unitType);
                System.out.println("Uom  "+ uom);
                System.out.println("End------------"); 
                //validation of unit selected with the already created unit to avoid duplicate
                ViewObject multiUnitVo=ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
                ViewCriteria vc = multiUnitVo.createViewCriteria();
                        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
                        vcRow.setAttribute("UnitId",unitId);
                        vc.addRow(vcRow);
                        multiUnitVo.applyViewCriteria(vc);
                        multiUnitVo.executeQuery();
                        System.out.println("Estimated multiUnit Vo count :"+multiUnitVo.getEstimatedRowCount());
                        if(multiUnitVo.getEstimatedRowCount()==0){
                //calling receipt insert
                    insertMultiUnits(bkHdrId,propertyId,buildId,unitId,areaValue,unitType,uom); 
                i++;
                        }
            }
        }
        dtlsRS.closeRowSetIterator();
        ViewObject multiUtVo=ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
        ViewCriteria vwc = multiUtVo.createViewCriteria();
                ViewCriteriaRow vwcRow = vwc.createViewCriteriaRow();
                vwcRow.setAttribute("BookingHdrId",bkHdrId);
                vwc.addRow(vwcRow);
                multiUtVo.applyViewCriteria(vwc);
                multiUtVo.executeQuery();
        System.out.println("C-1 row ::"+multiUtVo.getEstimatedRowCount());
        System.out.println("i after ++ to handle commit ::"+i);
        if (i!=1){
//                ADFUtils.findOperation("Commit").execute();
                JSFUtils.addFacesInformationMessage("Units Inserted Successfully !!");
        }else{
            JSFUtils.addFacesInformationMessage("Check box not selected or the unit already exist !!!");  
        }
        p15.hide();
    }
    
    public void insertMultiUnits(String bkHdrId,String propertyId,String buildId,String unitId,String areaValue,
                          String unitType,String uom) {
        ViewObject multiUnitVO=ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
        Row r=multiUnitVO.createRow();
        System.out.println("Get current BOOKING_MUL_UNIT_ID Id after create row :"+r.getAttribute("BookingMulUnitId"));
        String multiUnitd = r.getAttribute("BookingMulUnitId")==null ? "" : r.getAttribute("BookingMulUnitId").toString();
        r.setAttribute("BookingHdrId", bkHdrId);
        r.setAttribute("PropertyId", propertyId);
        r.setAttribute("BuildingId", buildId);
        r.setAttribute("UnitId", unitId);
        r.setAttribute("AreaInSqft", areaValue); 
        r.setAttribute("UnitType", unitType);
        r.setAttribute("Uom", uom);
        r.setAttribute("BasePrice", "");//    pending
        r.setAttribute("BookingAmount", ""); //pending
//        r.setAttribute("Attribute1", "");
        multiUnitVO.insertRow(r);
        multiUnitVO.executeQuery();
        ADFUtils.findOperation("Commit").execute();
        }
    
    public void filterMultiUnit(String  unitType,String propId,String buildId) {
        // Add event code here...
        ViewObject vo = ADFUtils.findIterator("XxplMultiUnitBooking_V_ROVO2Iterator").getViewObject();
        ViewCriteria vc=vo.createViewCriteria();
        ViewCriteriaRow vcRow=vc.createViewCriteriaRow();
        vcRow.setAttribute("PropertyId", propId);
        vcRow.setAttribute("BuildId", buildId);
        vcRow.setAttribute("UnitType", unitType);
        vcRow.setAttribute("Status", "Available");
        vc.addRow(vcRow);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
        System.out.println("Estimated Unit rows :"+vo.getEstimatedRowCount());
    }

    public void removeMultiUnits(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject multiUnitVo=ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
        String bookingMulUnitId = multiUnitVo.getCurrentRow().getAttribute("BookingMulUnitId")==null ? " " : multiUnitVo.getCurrentRow().getAttribute("BookingMulUnitId").toString();
        System.out.println("bookingMulUnitId==>"+bookingMulUnitId);
        multiUnitVo.getCurrentRow().remove();
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Unit Removed Successfully !!");        
    }

    public void onCancelMultiUnitPop(ActionEvent actionEvent) {
        // Add event code here...
        p15.hide();
    }
    public Double getTotalAreaSqFt(){ 
            Double totalAreaSqFt = 0.0;
            ViewObject msVo = ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
            RowSetIterator itr = msVo.createRowSetIterator(null);  
                while (itr.hasNext()) {
                    Row r = itr.next();
                    totalAreaSqFt +=
                        r.getAttribute("AreaInSqft") == null ? 0.0 :
                        Double.parseDouble(r.getAttribute("AreaInSqft").toString());
                }
            itr.closeRowSetIterator();
            return totalAreaSqFt;      
        }
    
    public Double getTotalAreaSqFtDetail(){ 
            Double totalAreaSqFt = 0.0;
            Double totalAreaSqFtDtl = 0.0;
            ViewObject msVo = ADFUtils.findIterator("XxplBookingMultiUnit_VO1Iterator").getViewObject();
            ViewObject bkDtlVo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
            RowSetIterator itr = msVo.createRowSetIterator(null);  
                while (itr.hasNext()) {
                    Row r = itr.next();
                    totalAreaSqFt +=
                        r.getAttribute("AreaInSqft") == null ? 0.0 :
                        Double.parseDouble(r.getAttribute("AreaInSqft").toString());
                }
            itr.closeRowSetIterator();
            RowSetIterator itrDtl = bkDtlVo.createRowSetIterator(null);  
                while (itrDtl.hasNext()) {
                    Row ro = itrDtl.next();
                    totalAreaSqFtDtl +=
                        ro.getAttribute("AreaInSqft") == null ? 0.0 :
                        Double.parseDouble(ro.getAttribute("AreaInSqft").toString());
                }
            itrDtl.closeRowSetIterator();
           totalAreaSqFt = totalAreaSqFtDtl+totalAreaSqFt;
            return totalAreaSqFt;      
        }
    
    
    public boolean getApprovalUser() {
        boolean flag = false;
        String loginId=null;
        String flowWith ="0";                        
        String userLogin ="-99";
//        System.err.println(bookHdrvo.getEstimatedRowCount() +"--Row count");
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


        public void onChangeBookingAmount(ValueChangeEvent valueChangeEvent) {
        double sumB = 0, sumU = 0;
        double newVal = 0;
        
        //System.err.println("areaAdd---" + areaAdd);

ViewObject arVo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
        Object bookingDtlId = arVo.getCurrentRow().getAttribute("BookingDtlId");
        System.err.println("BookingDtlId=====" + bookingDtlId);
            ViewObject bookingDetailVo = ADFUtils.getApplicationModuleForDataControl("Booking_AMDataControl")
                              .findApplicationModule("Booking_AM").findViewObject("XxplBookingDetail_VO");
                    ViewCriteria viewCriteria = bookingDetailVo.createViewCriteria();
                    ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
                    viewCriteriaRow.setAttribute("BookingDtlId", bookingDtlId);
                    viewCriteria.addRow(viewCriteriaRow);
                    bookingDetailVo.applyViewCriteria(viewCriteria);
                    bookingDetailVo.executeQuery();

                    RowSetIterator rs = bookingDetailVo.createRowSet("");
                    while (rs.hasNext()) {
                        Row row = rs.next();
                        if (row.getAttribute("UnitId") != null) {
                            Object val = row.getAttribute("BookingAmount");
                            if (val != null) {
                                double i = Double.parseDouble(val.toString());
                                sumU = sumU + i;
                            }
                            System.err.println("BookingAmount----" + val);
                        }
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            Object newAreaValue = valueChangeEvent.getNewValue();
            if (newAreaValue != null) {
                newVal = Double.parseDouble(newAreaValue.toString());
                System.out.println("newval----" + newVal);
            }
           // Object buildId = JSFUtils.resolveExpression("#{bindings.BuildId.inputValue}");
            
             
            }
                    if (newVal != 0) {
                        if (newVal<sumU) {
                            FacesMessage message = new FacesMessage("Unable to decrease the Amount..!");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                            bookingAmt.setValue(null);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(bookingAmt);
                        }
                    }
                }
        }
    public void setBookingAmt(RichInputText bookingAmt) {
        this.bookingAmt = bookingAmt;
    }

    public RichInputText getBookingAmt() {
        return bookingAmt;
    }

//    public void validatorBookingAmount(FacesContext facesContext,
//                                       UIComponent uIComponent,
//                                       Object object) {
//        Double bookLineAmt = 0.0;
//        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
//        RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
//        while (itr.hasNext()) {
//            Row r = itr.next();
//            bookLineAmt +=
//                r.getAttribute("BookingAmount") == null ? 0.0 :
//                Double.parseDouble(r.getAttribute("BookingAmount").toString());
//        }
//        itr.closeRowSetIterator();
//         System.err.println("bookLineAmt::"+bookLineAmt);
//        Double newVal = 0.0;
//        if(object!=null?object.equals(bookLineAmt):bookLineAmt==0) {
//         System.err.println("no changes");
//        }
//        else
//        {
//        Object newBookingAmt=bookingAmt.getValue();
//      // Object oldBookingAmt=valueChangeEvent.getOldValue();
//  
//        if (newBookingAmt != null) {
//            newVal = Double.parseDouble(newBookingAmt.toString());
//            System.err.println("newamt--------->"+newVal);
//        }
//    
//        if(newVal<bookLineAmt)
//        {
//           FacesMessage message = new FacesMessage("Unable to decrease the Amount..!");
//           message.setSeverity(FacesMessage.SEVERITY_WARN);
//           FacesContext fc = FacesContext.getCurrentInstance();
//           fc.addMessage(null, message);
//           bookingAmt.setValue(null);
//           AdfFacesContext.getCurrentInstance().addPartialTarget(bookingAmt);
//        }
//    }}

    public void doCalcInstPctg(ActionEvent actionEvent) {
        //  To get booking line amount
                    Double bookLineAmt = 0.0;
                    Double msBaseAmt = 0.0;
                    ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
                    RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
                    while (itr.hasNext()) {
                        Row r = itr.next();
                        bookLineAmt +=
                            r.getAttribute("TotalAmount") == null ? 0.0 :
                            Double.parseDouble(r.getAttribute("TotalAmount").toString());
                    }
                    itr.closeRowSetIterator();
                    // System.err.println("bookLineAmt::"+bookLineAmt);
                    
                 // Installment perc calculation 
                    ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
                    Row currMS = msVo.getCurrentRow();
                    double amt =
                            currMS.getAttribute("InstallmentAmount") == null ? 0 :
                            Double.parseDouble(currMS.getAttribute("InstallmentAmount").toString());
                    
                    double pct = (amt / bookLineAmt.doubleValue()) * 100;
                    currMS.setAttribute("InstallmentPct", new BigDecimal(pct));
                    
                // Base amount calculation
                    double taxRate =
                        currMS.getAttribute("TaxRate") == null ? 0 :
                        Double.parseDouble(currMS.getAttribute("TaxRate").toString());
                    
                    msBaseAmt =amt/(100 + taxRate)*100;
                    currMS.setAttribute("Baseamount", new BigDecimal(msBaseAmt));
    }

    public void onSlctFitOutDate(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
        String msg = "0";
        String bkId = bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")!=null?bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        String stDte = bookHdrvo.getCurrentRow().getAttribute("BookingLeaseStartDate")!=null?bookHdrvo.getCurrentRow().getAttribute("BookingLeaseStartDate").toString():"0";
        String endSte = bookHdrvo.getCurrentRow().getAttribute("BookingLeaseEndDate")!=null?bookHdrvo.getCurrentRow().getAttribute("BookingLeaseEndDate").toString():"0";
        String fitInDte = bookHdrvo.getCurrentRow().getAttribute("CusReqMoveInDate")!=null?bookHdrvo.getCurrentRow().getAttribute("CusReqMoveInDate").toString():"0";
        String fitOutDte = valueChangeEvent.getNewValue()!=null?valueChangeEvent.getNewValue().toString():"0";
//        oracle.jbo.domain.Date fitst = (oracle.jbo.domain.Date)moveInDate.getValue();
//        oracle.jbo.domain.Date fiten = (oracle.jbo.domain.Date)moveOutDate.getValue();
        java.sql.Date fitst=(java.sql.Date)moveInDate.getValue();
          java.sql.Date fiten=(java.sql.Date)moveOutDate.getValue();
        long fndays = getDifferenceDaysBetweenTwoDates(fitst, fiten);
        int nodaysF = (int)fndays;
        Date date1 = new Date();
          try {
              date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fitOutDte);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          Calendar d = Calendar.getInstance();
          d.setTime(date1);
          d.add(Calendar.DATE, 1);
          DateFormat dFmt = new SimpleDateFormat("yyyy-MM-dd");
          String frmtd = dFmt.format(d.getTime());

          Date date2 = new Date();
          try {
              date2 = new SimpleDateFormat("yyyy-MM-dd").parse(frmtd);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          System.err.println("date2---------->"+date2.getTime());
          java.sql.Date sqlDateY = new java.sql.Date(date2.getTime());
          oracle.jbo.domain.Date domadateY = new oracle.jbo.domain.Date(sqlDateY);
          bookHdrvo.getCurrentRow().setAttribute("BookingLeaseStartDate", domadateY);
          //
          Date date12 = new Date();
            try {
                date12 = new SimpleDateFormat("yyyy-MM-dd").parse(endSte);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar d2 = Calendar.getInstance();
            d2.setTime(date12);
            d2.add(Calendar.DATE, nodaysF);
            DateFormat dFmt2 = new SimpleDateFormat("yyyy-MM-dd");
            String frmtd2 = dFmt2.format(d2.getTime());

            Date date22 = new Date();
            try {
                date22 = new SimpleDateFormat("yyyy-MM-dd").parse(frmtd2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.sql.Date sqlDateY2 = new java.sql.Date(date22.getTime());
            oracle.jbo.domain.Date domadateY2 = new oracle.jbo.domain.Date(sqlDateY2);
            bookHdrvo.getCurrentRow().setAttribute("BookingLeaseEndDate", domadateY2);
          bookHdrvo.getCurrentRow().setAttribute("CusReqMoveInDate",fitInDte);
          bookHdrvo.getCurrentRow().setAttribute("CusReqMoveOutDate", fitOutDte);
          AdfFacesContext.getCurrentInstance().addPartialTarget(id1);
          AdfFacesContext.getCurrentInstance().addPartialTarget(id5);
          AdfFacesContext.getCurrentInstance().addPartialTarget(moveInDate);
          AdfFacesContext.getCurrentInstance().addPartialTarget(moveOutDate);
          
      }
    }
    
    public long getDifferenceDaysBetweenTwoDates(java.sql.Date st,java.sql.Date en) {
        
                if (st != null && en != null) {
                 oracle.jbo.domain.Date newSt=new oracle.jbo.domain.Date(st);
                 oracle.jbo.domain.Date newEn=new oracle.jbo.domain.Date(en);
                 System.err.println("newST------->"+newSt);
                 System.err.println("newEn------->"+newEn);
                    long diffms = newEn.getValue().getTime() - newSt.getValue().getTime();
                    System.out.println(diffms);
                    long msPerDay = 1000 * 60 * 60 * 24;
                    long duration = diffms / msPerDay;
                    System.out.println("duration " + duration);
                    return duration +1;
             }
               return 0;
             } 
    public void calDate(String dt,String a) {
        
    }

    public void setId5(RichInputDate id5) {
        this.id5 = id5;
    }

    public RichInputDate getId5() {
        return id5;
    }

    public void setId1(RichInputDate id1) {
        this.id1 = id1;
    }

    public RichInputDate getId1() {
        return id1;
    }

    public void onSlctFitInDate(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                String unitCatgy ="";
                if (valueChangeEvent.getNewValue() != null) {
                    ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingDetail_VOIterator").getViewObject();
                    System.err.println("bookingdetailcount--->"+bookdtlvo.getEstimatedRowCount());
                        if(bookdtlvo.getEstimatedRowCount()>0){
                    RowSetIterator itr = bookdtlvo.createRowSetIterator(null);
                    while (itr.hasNext()) {
                        Row r = itr.next();
                        unitCatgy = r.getAttribute("Attribute2") == null ? "" : r.getAttribute("Attribute2").toString();
                        if (!unitCatgy.contains("Retail")){
                            bookHdrvo.getCurrentRow().setAttribute("CusReqMoveInDate", null);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(moveInDate);
                            JSFUtils.addFacesErrorMessage("Fit in out period not applicable for Retail unit !!!");
                        }
                    }
                    itr.closeRowSetIterator();
                }
                    else{
                        bookHdrvo.getCurrentRow().setAttribute("CusReqMoveInDate", null);   
                        AdfFacesContext.getCurrentInstance().addPartialTarget(moveInDate);    
                        JSFUtils.addFacesErrorMessage("Create Unit Line!!!");
                            
                    }
                }
    }

    public void onCheckOwnerPaymtAttr4(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
//            ViewObject msVo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
//            msVo.getCurrentRow().setAttribute("Attribute4", "Owner");
        }
    }

    public void onChkOcAttrb4Ownr(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
        //            ViewObject ocVo = ADFUtils.findIterator("XxplBookingMilestones_OCIterator").getViewObject();
        //            ocVo.getCurrentRow().setAttribute("Attribute4", "Owner");
        }
    }
}
