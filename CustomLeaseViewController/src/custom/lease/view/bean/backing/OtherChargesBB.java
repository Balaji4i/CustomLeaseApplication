package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class OtherChargesBB {
    private RichPopup addEditPopup;
    private RichPopup receiptConformPop;

    public OtherChargesBB() {
        super();
    }
    
    public void onClickSaveAL(ActionEvent actionEvent) {
        saveData();
        ADFUtils.findOperation("Commit").execute(); 
        JSFUtils.addFacesInformationMessage("Saved Successfully !!");
    }
    
    private void saveData() { 
        ViewObject chargeVo = ADFUtils.findIterator("XxplBookingOtherChargesIterator").getViewObject();
        RowSetIterator chargeItr = chargeVo.createRowSetIterator(null);
        while (chargeItr.hasNext()) {
            Row r = chargeItr.next();
            r.setAttribute("Charges", r.getAttribute("Charges"));
            r.setAttribute("Baseamount", r.getAttribute("Baseamount"));
            r.setAttribute("TaxCode", r.getAttribute("TaxCode"));
            r.setAttribute("TaxRate", r.getAttribute("TaxRate"));
            r.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
            r.setAttribute("InstallmentAmount", r.getAttribute("InstallmentAmount"));      
        }
        chargeItr.closeRowSetIterator(); 
        ViewObject amtVo = ADFUtils.findIterator("MilestoneReceiptAmount_ROVOIterator").getViewObject();
        amtVo.executeQuery();
    }
    
    public void onChangeChargeType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject chargeVo = ADFUtils.findIterator("XxplBookingOtherChargesIterator").getViewObject();
        Row currRow = chargeVo.getCurrentRow();
        currRow.setAttribute("Charges", new BigDecimal(0));
        currRow.setAttribute("Baseamount", new BigDecimal(0));
    }
    
    public void onChangeChargeAmt(ValueChangeEvent valueChangeEvent) {
          valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //  To get booking line amount 
            Double bookLineAmt = 0.0;
            Double chargeVal = 0.0;
            double chargeAmt = 0.0;
            ViewObject bookdtlvo = ADFUtils.findIterator("BookingLineForChargeROVOIterator").getViewObject();
            Row r = bookdtlvo.getCurrentRow();
            bookLineAmt = r.getAttribute("TotalAmount") == null ? 0.0 :
                    Double.parseDouble(r.getAttribute("TotalAmount").toString()); 
            
        //  To get charge detils 
            ViewObject chargeVo = ADFUtils.findIterator("XxplBookingOtherChargesIterator").getViewObject();
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

    public void setAddEditPopup(RichPopup addEditPopup) {
        this.addEditPopup = addEditPopup;
    }

    public RichPopup getAddEditPopup() {
        return addEditPopup;
    }

    public void deleteChargeAL(ActionEvent actionEvent) { 
        ADFUtils.findOperation("DeleteOC").execute(); 
        JSFUtils.addFacesInformationMessage("Deleted !!");
    }
    public void editChargeAL(ActionEvent actionEvent) {
        ViewObject bookdtlvo = ADFUtils.findIterator("XxplBookingOtherChargesIterator").getViewObject(); 
        if(bookdtlvo.getEstimatedRowCount()==0){
            ADFUtils.findOperation("CreateOtherCharge").execute(); 
        }
        JSFUtils.showPopup(addEditPopup);
    }

    public void onClickOkPopup(ActionEvent actionEvent) {
        JSFUtils.hidePopup(addEditPopup);
    } 
    public void onClickAddReceipt(ActionEvent actionEvent) {
        JSFUtils.showPopup(receiptConformPop);
    }

    public void setReceiptConformPop(RichPopup receiptConformPop) {
        this.receiptConformPop = receiptConformPop;
    }

    public RichPopup getReceiptConformPop() {
        return receiptConformPop;
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
        ViewObject bookHdrvo = ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();
        Row hdrRow = bookHdrvo.getCurrentRow();
        String bookId = hdrRow.getAttribute("BookingHdrId")!=null?hdrRow.getAttribute("BookingHdrId").toString():"0";
        String msDtlId = "0"; 
        
        //to get BookingMsid 
//        if ("MS".equals(mileType)) {
//            ViewObject vo = ADFUtils.findIterator("XxplBookingMilestones_MSIterator").getViewObject();
//            Row r = vo.getCurrentRow();
//            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
//            
//        } else 
        if ("OC".equals(mileType)) {
            ViewObject vo = ADFUtils.findIterator("XxplBookingOtherChargesIterator").getViewObject();
            Row r = vo.getCurrentRow();
            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
        } 
// else if ("CP".equals(mileType)) {
//            ViewObject vo = ADFUtils.findIterator("XxplBookingCarParkingPAIDIterator").getViewObject();
//            Row r = vo.getCurrentRow();
//            msDtlId = r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
//        } else {
//
//        }
        if("G".equals(receiptType)){
            msDtlId= "0";
        }
        list = PackageCalling.createReceipt(bookId, msDtlId, mileType, receiptType);
//        System.err.println(list);
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
    
    public void onCancelReceiptCreate(ActionEvent actionEvent){ 
        JSFUtils.hidePopup(receiptConformPop);
    }
}
