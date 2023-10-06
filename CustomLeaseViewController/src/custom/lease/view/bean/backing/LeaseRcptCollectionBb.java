package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class LeaseRcptCollectionBb {
    private RichInputText it1;

    public LeaseRcptCollectionBb() {
    }

    public void onEntrSetAmtTrans(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplReceiptTransaction_V_ROVO1Iterator").getViewObject();
        String setAmt = valueChangeEvent.getNewValue()==null ? "0" : valueChangeEvent.getNewValue().toString();
        String blncAmt = rcptTrnstnRoVo.getCurrentRow().getAttribute("BalanceAmt")==null ? "0" : rcptTrnstnRoVo.getCurrentRow().getAttribute("BalanceAmt").toString();
        BigDecimal setAmount = new BigDecimal(setAmt);
        BigDecimal blncAmount = new BigDecimal(blncAmt);
        if(setAmount.compareTo(blncAmount) > 0){
        //            rcptTrnstnRoVo.getCurrentRow().setAttribute("Set_Amount_Trans", null);
            it1.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.it1);
            JSFUtils.addFacesErrorMessage("Set Amount should be less than or equal to Balance Amount "+blncAmount);
        }
        if(setAmt.equals("0")){
            System.out.println("Inside zero");
            it1.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.it1);
            JSFUtils.addFacesErrorMessage("Set Amount can't be zero !!!");
        }
    }

    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void onClickReceiptSelectAll(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplReceiptTransaction_V_ROVO1Iterator").getViewObject();
        RowSetIterator tenantRS=rcptTrnstnRoVo.createRowSetIterator(null);
        while(tenantRS.hasNext()){
            Row r=tenantRS.next();
            r.setAttribute("Receipt_Flag_Trans", Boolean.TRUE);
        }
        tenantRS.closeRowSetIterator();
    }

    public void onClickReceiptUnSelectAll(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplReceiptTransaction_V_ROVO1Iterator").getViewObject();
        RowSetIterator tenantRS=rcptTrnstnRoVo.createRowSetIterator(null);
        while(tenantRS.hasNext()){
            Row r=tenantRS.next();
            r.setAttribute("Receipt_Flag_Trans", Boolean.FALSE);
        }
        tenantRS.closeRowSetIterator();
    }
    
    ViewObject bookHdrvo=ADFUtils.findIterator("XxplBookingHeader_VOIterator").getViewObject();
    
    public void onClickCreateRcpt(ActionEvent actionEvent) {
        // Add event code here...        
        ViewObject rcptTrnstnRoVo=ADFUtils.findIterator("XxplReceiptTransaction_V_ROVO1Iterator").getViewObject(); 
        ViewObject customerVo = ADFUtils.findIterator("XxplBookingCustomer_VOIterator").getViewObject();
        RowSetIterator dtlsRS = rcptTrnstnRoVo.createRowSetIterator(null);
        String bkHdrId = bookHdrvo.getCurrentRow().getAttribute("BookingHdrId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("BookingHdrId").toString();
        String funcId = bookHdrvo.getCurrentRow().getAttribute("FuncId")==null?"0":bookHdrvo.getCurrentRow().getAttribute("FuncId").toString();
        String ownerName = customerVo.getCurrentRow().getAttribute("Customer_Trans")==null?"0":customerVo.getCurrentRow().getAttribute("Customer_Trans").toString();
        String custId = customerVo.getCurrentRow().getAttribute("CustId")==null?"0":customerVo.getCurrentRow().getAttribute("CustId").toString();
        String custSiteId = customerVo.getCurrentRow().getAttribute("BillToSiteId")==null?"0":customerVo.getCurrentRow().getAttribute("BillToSiteId").toString();
        String flag=null;
        String setAmt= null;
        String srcFnDtlId= null;
        String rcptCtgry= null;
        String orgId= null;
        String propertyId= null;
        String buildId= null;
        String unitId= null;
        String apldAmt= null;
        String blncAmt= null;
        String receiptType=null;
        Object curDate=null;
        String attribute2 = null;
        String attribute3 = null;
        String ownerPayment = null;
        String interfaceStatus = "Draft";
        int i =1;
        System.out.println("No of rows :"+rcptTrnstnRoVo.getEstimatedRowCount());
        while(dtlsRS.hasNext()){
            Row r = dtlsRS.next();
            flag = r.getAttribute("Receipt_Flag_Trans")==null ? "0" : r.getAttribute("Receipt_Flag_Trans").toString();
            setAmt = r.getAttribute("Set_Amount_Trans")==null ? "0" : r.getAttribute("Set_Amount_Trans").toString();
            srcFnDtlId = r.getAttribute("SourcesRefDtlId")==null ? "0" : r.getAttribute("SourcesRefDtlId").toString();
            rcptCtgry = r.getAttribute("ReceiptCategory")==null ? "0" : r.getAttribute("ReceiptCategory").toString();
            orgId = r.getAttribute("OrgId")==null ? "0" : r.getAttribute("OrgId").toString();
            propertyId = r.getAttribute("PropertyId")==null ? "0" : r.getAttribute("PropertyId").toString();
            buildId = r.getAttribute("BuildId")==null ? "0" : r.getAttribute("BuildId").toString();
            unitId = r.getAttribute("UnitId")==null ? "0" : r.getAttribute("UnitId").toString();
            apldAmt = r.getAttribute("ReceiptAppliedAmt")==null ? "0" : r.getAttribute("ReceiptAppliedAmt").toString();
            blncAmt = r.getAttribute("BalanceAmt")==null ? "0" : r.getAttribute("BalanceAmt").toString();
            receiptType=r.getAttribute("Attribute3")==null ? "0" : r.getAttribute("Attribute3").toString();
            attribute2=r.getAttribute("Attribute2")==null ? "0" : r.getAttribute("Attribute2").toString();
            attribute3=r.getAttribute("Attribute3")==null ? "0" : r.getAttribute("Attribute3").toString();
            ownerPayment=r.getAttribute("OwnerPayment")==null ? "0" : r.getAttribute("OwnerPayment").toString();
//            if(ownerPayment.equalsIgnoreCase("Owner")){
//                interfaceStatus="Receipted";//if rcpt created against ms which is direct to owner payment attribute4 than restrict interface
//            }
            curDate = getCurrentDate();
            System.out.println("Flag :::"+flag);
            System.out.println("Set amt :::"+setAmt);
            if(flag.equalsIgnoreCase("True") && !setAmt.equals("0")){
                System.out.println("Start------------"+i);                
                System.out.println("SourceFuncId"+ funcId);
                System.out.println("SourceFuncRefId"+ bkHdrId);
                System.out.println("SourcesRefDtlId"+ srcFnDtlId);
                System.out.println("ReceivedAmount"+ setAmt);
                System.out.println("ReceiptAmount"+ setAmt); //ReceiptAmount
                System.out.println("ReceiptNumber"+ "");
                System.out.println("ReceiptDate"+ curDate);
                System.out.println("OrgId"+ orgId);
                System.out.println("ReceiptType "+ attribute3);// lou removed and kept in variable
                System.out.println("PropertyId"+ propertyId);
                System.out.println("BuildId"+ buildId);
                System.out.println("UnitId"+ unitId);
                System.out.println("CustId"+ custId);
                System.out.println("CustomerName"+ ownerName);
                System.out.println("CustSiteId"+ custSiteId);
                System.out.println("CurrencyCode"+ "AED"); //confirm
                System.out.println("InterfaceStatus"+ "Draft"); //confirm
                System.out.println("ReceiptCategory"+ rcptCtgry);
                System.out.println("Applied Amt"+ apldAmt);
                System.out.println("Balance Amt"+ blncAmt);
                System.out.println("Owner Payment"+ ownerPayment);
                System.out.println("End------------");               
                //calling receipt insert
                    insertRcpt(bkHdrId,funcId,srcFnDtlId,setAmt,rcptCtgry,
                               orgId,propertyId,buildId,unitId,
                               ownerName,custId,custSiteId,curDate,apldAmt,blncAmt,receiptType, attribute2,ownerPayment,interfaceStatus); 
                i++;
            }
        }
        dtlsRS.closeRowSetIterator();
        System.out.println("i after ++ to handle commit ::"+i);
        if (i!=1){
                ADFUtils.findOperation("Commit").execute();
                JSFUtils.addFacesInformationMessage("Receipts Saved Successfully !!");
        }else{
            JSFUtils.addFacesInformationMessage("Either flag or set amount is not entered !!!");  
        }
    }
    //insert receipt method
    public void insertRcpt(String srcRefId,String srcFncId,String srcRefDtlId,String setAmt,String rcptCtgry,
                          String orgId,String propertyId,String buildId,String unitId,
                          String ownerName,String custId,String custSiteId,Object curDate,
                          String apldAmt,String blncAmt,String receiptType, String attribute2,String ownerPayment,
                          String interfaceStatus) {
        ViewObject rcptDtlVO=ADFUtils.findIterator("XxplReceiptLineView2Iterator").getViewObject();
        ViewObject rcptVO=ADFUtils.findIterator("XxplReceiptHeaderView1Iterator").getViewObject();
        Row r=rcptVO.createRow();
        System.out.println("Get current receipt Id after create row :"+r.getAttribute("ReceiptId"));
        String rcptId = r.getAttribute("ReceiptId")==null ? "" : r.getAttribute("ReceiptId").toString();
        r.setAttribute("SourceFuncId", srcFncId);
        r.setAttribute("SourceFuncRefId", srcRefId);
        r.setAttribute("SourcesRefDtlId", srcRefDtlId);
        r.setAttribute("ReceivedAmount", setAmt);
        r.setAttribute("ReceiptAmount", setAmt); //ReceiptAmount
        r.setAttribute("ReceiptNumber", "RT-"+rcptId);
        r.setAttribute("ReceiptDate", r.getAttribute("GlDate")==null ? null :r.getAttribute("GlDate"));
        r.setAttribute("OrgId", orgId);
        r.setAttribute("ReceiptType", receiptType);//    confirm
        r.setAttribute("PropertyId", propertyId);
        r.setAttribute("Attribute1", attribute2);
        r.setAttribute("BuildId", buildId);
        r.setAttribute("UnitId", unitId);
        r.setAttribute("CustId", custId);
        r.setAttribute("CustomerName", ownerName);
        r.setAttribute("CustSiteId", custSiteId);
        r.setAttribute("CurrencyCode", "AED"); //confirm
        r.setAttribute("InterfaceStatus", interfaceStatus); //confirm
        r.setAttribute("ReceiptCategory", rcptCtgry);
        r.setAttribute("Status", "Draft");
        r.setAttribute("Attribute4", ownerPayment);
        rcptVO.insertRow(r);
        rcptVO.executeQuery();
        
        if(attribute2.equalsIgnoreCase("Security Deposit")){
            // no security deposite and no apply recipt
        }else{
            //Receipt dtl ReceiptDtlId
                Row dtlR=rcptDtlVO.createRow();
                System.out.println("Get current receipt dtl Id after create row :"+dtlR.getAttribute("ReceiptDtlId"));
                dtlR.setAttribute("ReceiptId", rcptId);
                dtlR.setAttribute("SourcesFunId", srcFncId);
                dtlR.setAttribute("SourcesRefId", srcRefId);
                dtlR.setAttribute("SourcesRefDtlId", srcRefDtlId);
                dtlR.setAttribute("AmountApplied", setAmt); //AmountApplied
                dtlR.setAttribute("TransactionApplied", apldAmt);
                dtlR.setAttribute("TransactionBalance", blncAmt);
                rcptDtlVO.insertRow(dtlR);
                rcptDtlVO.executeQuery();
        }
       
    }
    //get current date
    public Object getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        String dateFormat = "yyyy-MM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Object sysDate = sdf.format(cal.getTime());
        return sysDate;
    }

    public void onClickCancel(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Current Date ::"+getCurrentDate());
    }
}
