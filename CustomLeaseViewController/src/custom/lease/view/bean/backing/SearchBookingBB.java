package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import custom.lease.custom.view.beans.Utils.InvokeWebServices;
import custom.lease.custom.view.beans.filmStrip.FilmStripBean;

import java.io.IOException;

import java.io.OutputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import org.json.JSONException;

public class SearchBookingBB {
    private RichPopup renewPop;
    private RichInputDate invCreateDate;

    public SearchBookingBB() {
        super();
    }

    ViewObject bookingSearchVO = ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();

    public void onClickStatusCount(ActionEvent actionEvent) { 
        String statusType = ADFContext.getCurrent()
                             .getPageFlowScope()
                             .get("statusType") == null ? "0" : ADFContext.getCurrent()
                                                                            .getPageFlowScope()
                                                                            .get("statusType")
                                                                            .toString();
        //System.err.println(statusType);
        
        ViewObject dashVo = ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();
        dashVo.applyViewCriteria(null);
        dashVo.executeQuery();
        if(!"ALL".equals(statusType)){
            ViewCriteria vc = dashVo.createViewCriteria();
            ViewCriteriaRow vcRow = vc.createViewCriteriaRow(); 
            vcRow.setAttribute("Status", statusType);
            vc.addRow(vcRow);
            dashVo.applyViewCriteria(vc);
            dashVo.executeQuery(); 
        }    
    }

    public String onClickRenewal() throws ParseException {
        String msg = "0";
        ArrayList <String> returnList = new ArrayList<String>();
        ViewObject rovo = ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();
        Row currRow = rovo.getCurrentRow();
        String bookingId = currRow.getAttribute("BookingHdrId")!=null?currRow.getAttribute("BookingHdrId").toString():"0";
        String reqStartDate = currRow.getAttribute("RenewalFromDate")!=null?currRow.getAttribute("RenewalFromDate").toString():"0";
        String reqEndDate = currRow.getAttribute("DummyDate2")!=null?currRow.getAttribute("DummyDate2").toString():"0";
        String paymentType = currRow.getAttribute("PaymentType")!=null?currRow.getAttribute("PaymentType").toString():"0"; 
        if (!reqEndDate.equals("0")){
//      =====check date and payment term
//        msg=isValidPeriod(reqStartDate, reqEndDate, paymentType);
        msg="Y";
        if(msg.equalsIgnoreCase("Y")){
//          =========================================
//        JSFUtils.addFacesInformationMessage("ID::"+bookingId+"-sDate::"+getFormattedDate(reqStartDate)+"-eDate::"+getFormattedDate(reqEndDate)+"Type::"+paymentType);
            System.err.println("ID::"+bookingId+"-sDate::"+getFormattedDate(reqStartDate)+"-eDate::"+getFormattedDate(reqEndDate)+"Type::"+paymentType);
            returnList = PackageCalling.bookingRenewalProcess(bookingId, getFormattedDate(reqStartDate), getFormattedDate(reqEndDate),paymentType); 
            //System.err.println(returnList);
            JSFUtils.hidePopup(renewPop);
//          =========================================            
            if("S".equals(returnList.get(0))){         
                JSFUtils.addFacesInformationMessage(returnList.get(1));
                
//                ADFContext.getCurrent().getPageFlowScope().put("addEditMode", "Edit");
//                ADFContext.getCurrent().getPageFlowScope().put("editBookingId", returnList.get(2));
//                ADFContext.getCurrent().getPageFlowScope().put("hdrSaved", "true");
//                ADFContext.getCurrent().getPageFlowScope().put("EditMode", "EditMode");
//                ADFContext.getCurrent().getPageFlowScope().put("showMSOC", "false");
//                return "toAddEdit";
                return null;
            }else{
                JSFUtils.addFacesErrorMessage(returnList.get(1)); 
                return null;
            }
//          =========================================
        }else{
            return null;
        }
        }else{
            JSFUtils.addFacesErrorMessage("Kindly select Renewal End Date !!!");
            return null;
        }
    }



    public String isValidPeriod(String reqStartDate, String  reqEndDate,String paymentType ) throws ParseException {
        String returnMsg = "N";
        if("0".equals(paymentType)){
            JSFUtils.addFacesErrorMessage("Please select payment plan ! ");
            returnMsg="N";
        }else{
            if(!"0".equals(reqStartDate) && !"0".equals(reqEndDate)){
                returnMsg=PackageCalling.checkValidDates(getDateFormat(reqStartDate), getDateFormat(reqEndDate), paymentType); 
                System.err.println("Return value--->>"+returnMsg);
                if(!"SUCCESS".equals(returnMsg)){
                    JSFUtils.addFacesErrorMessage("Please check, Selected payment plan is not applicable  for selected date !");     
                    returnMsg="N";
                }else{
                    returnMsg="Y";
                }
            }else{
                returnMsg="N";
            }
        } 
        return returnMsg;
    }


    private String getDateFormat(String repDate) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(repDate);
            SimpleDateFormat ft = new SimpleDateFormat("dd-MMM-yyyy");
            return ft.format(date).toUpperCase();
     }



    public void setRenewPop(RichPopup renewPop) {
        this.renewPop = renewPop;
    }

    public RichPopup getRenewPop() {
        return renewPop;
    }
    
    private String getFormattedDate(String repDate) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(repDate);
            SimpleDateFormat ft = new SimpleDateFormat("dd-MMM-yyyy");
            return ft.format(date).toUpperCase();
     }
    private String getDateByFormat(String repDate, String format) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            java.util.Date date = formatter.parse(repDate);
            SimpleDateFormat ft = new SimpleDateFormat("dd-MMM-yyyy");
            return ft.format(date).toUpperCase();
     }

    public void onCancelRenewal(ActionEvent actionEvent) {
        JSFUtils.hidePopup(renewPop);
    }

    public void onSendInterfaceError(ActionEvent actionEvent) throws IOException,
                                                                     JSONException {
        FilmStripBean film = new FilmStripBean();
        String ipAdd = film.getIpAddress();
        String url = ipAdd + "/LeaseIntegrationServices/webresources/autointegration/LeaseError?P_TABLE=XXPL_BOOKING_MILESTONES";
        //System.err.println("URL-->"+url);
        String result = InvokeWebServices.invokeRestServicesNoParam(url);
        //System.err.println("RESULT-->"+result);
        JSFUtils.addFacesInformationMessage(result);
    }
    public void onCreateInvoiceMail(ActionEvent actionEvent) throws ParseException,
                                                                    IOException,
                                                                    JSONException {
        FilmStripBean film = new FilmStripBean();
        String ipAdd = film.getIpAddress();
        String p_date = invCreateDate.getValue().toString();
        String strDate = this.getDateByFormat(p_date, "E MMM dd HH:mm:ss Z yyyy");
        String url = ipAdd + "/LeaseIntegrationServices/webresources/autointegration/LeaseInvoice?P_TDATE=" + strDate;
        //System.err.println("URL-->"+url);
        String result = InvokeWebServices.invokeRestServicesNoParam(url);
        //System.err.println("RESULT-->"+result);
        JSFUtils.addFacesInformationMessage(result);

    }

    public void setInvCreateDate(RichInputDate invCreateDate) {
        this.invCreateDate = invCreateDate;
    }

    public RichInputDate getInvCreateDate() {
        return invCreateDate;
    }

    public void onDeleteBooking(ActionEvent actionEvent) { 
        ArrayList <String> returnList = new ArrayList<String>();
        ViewObject rovo = ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();
        ViewObject countVo = ADFUtils.findIterator("BookingCount_ROVOIterator").getViewObject();
        Row currRow = rovo.getCurrentRow();
        String bookingId = currRow.getAttribute("BookingHdrId")!=null?currRow.getAttribute("BookingHdrId").toString():"0";  
        returnList = PackageCalling.bookingDeleteProcess(bookingId); 
        //System.err.println(returnList);
        if("S".equals(returnList.get(1))){         
            JSFUtils.addFacesInformationMessage(returnList.get(0)); 
        }else{
            JSFUtils.addFacesErrorMessage(returnList.get(0));  
        } 
        rovo.executeQuery();
        countVo.executeQuery();
    }

    public String onClickLeasePaymentPlanChange() {
        ArrayList <String> returnList = new ArrayList<String>();
        
        String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.bookingRevisionProcess(bookingId);
        
//        System.err.println("00==>"+returnList.get(0));
//        System.err.println("01==>"+returnList.get(1));
        
        if(returnList.get(0).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
            JSFUtils.addFacesInformationMessage(returnList.get(1));
            ADFContext.getCurrent().getPageFlowScope().put("addEditMode", "Edit");
            ADFContext.getCurrent().getPageFlowScope().put("editBookingId", bookingId);
            ADFContext.getCurrent().getPageFlowScope().put("hdrSaved", "true");
            ADFContext.getCurrent().getPageFlowScope().put("EditMode", "EditMode");
            ADFContext.getCurrent().getPageFlowScope().put("showMSOC", "false");
            return "toAddEdit";
        }else{
            JSFUtils.addFacesErrorMessage(returnList.get(1)); 
            return null;
        }
    }

    public String leaseToCancel() {
        ArrayList <String> returnList = new ArrayList<String>();
        String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.cancellation(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
            JSFUtils.addFacesInformationMessage(returnList.get(1));
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
        String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.moveIn(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
            JSFUtils.addFacesInformationMessage(returnList.get(1));
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
        String bookingId=bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId")!=null?bookingSearchVO.getCurrentRow().getAttribute("BookingHdrId").toString():"0";
        returnList=PackageCalling.moveOut(bookingId);
                System.err.println("00==>"+returnList.get(0));
                System.err.println("01==>"+returnList.get(1));
                System.err.println("02==>"+returnList.get(2));
        if(returnList.get(1).toString().equalsIgnoreCase("S")){
        System.out.println("bookingId==>"+bookingId);
            JSFUtils.addFacesInformationMessage(returnList.get(1));
            ADFContext.getCurrent().getPageFlowScope().put("navigation", "edit");
            ADFContext.getCurrent().getPageFlowScope().put("MoveInOutId", returnList.get(0));
            return "toMoveInOutScreen";
        }else{
            System.err.println("in else");
            JSFUtils.addFacesErrorMessage(returnList.get(1)); 
            return null;
        }

    }
    //excel download
    public void exportToExcel(javax.faces.context.FacesContext facesContext,
                                      OutputStream outputStream) {
                // Add event code here...
                try {
             
                            HSSFWorkbook workbook = new HSSFWorkbook();
                            HSSFSheet sheet =
                                workbook.createSheet("Lease Booking");
                            HSSFRow rowhead = sheet.createRow((short)0);
                            rowhead.createCell(0).setCellValue("Booking No");
                            sheet.setColumnWidth(0, 4500);
                            rowhead.createCell(1).setCellValue("Booking Date");
                            sheet.setColumnWidth(1, 4500);
                            rowhead.createCell(2).setCellValue("Lease Start Date");
                            sheet.setColumnWidth(2, 4500);
                            rowhead.createCell(3).setCellValue("Lease End Date");
                            sheet.setColumnWidth(3, 4500);
                            rowhead.createCell(4).setCellValue("Contract Days");
                            sheet.setColumnWidth(4, 4500);
                            rowhead.createCell(5).setCellValue("Business Unit");
                            sheet.setColumnWidth(5, 6000);
                            rowhead.createCell(6).setCellValue("Property Name");
                            sheet.setColumnWidth(6, 6000);
                            rowhead.createCell(7).setCellValue("Build Name");
                            sheet.setColumnWidth(7, 6000);
                            rowhead.createCell(8).setCellValue("Unit Name");
                            sheet.setColumnWidth(8, 6000);
                            rowhead.createCell(9).setCellValue("Unit Number");
                            sheet.setColumnWidth(9, 6000);
                            rowhead.createCell(10).setCellValue("Customer Name");
                            sheet.setColumnWidth(10, 6000);
                            rowhead.createCell(11).setCellValue("Payment Plan Name");
                            sheet.setColumnWidth(11, 6000);
                            rowhead.createCell(12).setCellValue("Milestone Type");
                            sheet.setColumnWidth(12, 3500);
                            rowhead.createCell(13).setCellValue("Price List Name");
                            sheet.setColumnWidth(13, 5000);
                            rowhead.createCell(14).setCellValue("Booking Status");
                            sheet.setColumnWidth(14, 4000);
                            rowhead.createCell(15).setCellValue("Booking Sub Status");
                            sheet.setColumnWidth(15, 5000);
                            rowhead.createCell(16).setCellValue("Area SqFt");
                            sheet.setColumnWidth(16, 3500);
                            rowhead.createCell(17).setCellValue("Booking Amount");
                            sheet.setColumnWidth(17, 3500);
                            rowhead.createCell(18).setCellValue("Discount Type");
                            sheet.setColumnWidth(18, 3500);
                            rowhead.createCell(19).setCellValue("Discount Percentage");
                            sheet.setColumnWidth(19, 5000);
                            rowhead.createCell(20).setCellValue("Discount Amount");
                            sheet.setColumnWidth(20, 4000);
                            rowhead.createCell(21).setCellValue("Tax Code");
                            sheet.setColumnWidth(21, 4000);
                            rowhead.createCell(22).setCellValue("Tax Rate");
                            sheet.setColumnWidth(22, 4000);
                            rowhead.createCell(23).setCellValue("Tax Amount");
                            sheet.setColumnWidth(23, 4000);
                            rowhead.createCell(24).setCellValue("Total Amount");
                            sheet.setColumnWidth(24, 4000);
                            rowhead.createCell(25).setCellValue("Currency Code");
                            sheet.setColumnWidth(25, 4000);
                            rowhead.createCell(26).setCellValue("Seq No");
                            sheet.setColumnWidth(26, 4000);
                            rowhead.createCell(27).setCellValue("Charge Type");
                            sheet.setColumnWidth(27, 4000);
                            rowhead.createCell(28).setCellValue("Charge Name");
                            sheet.setColumnWidth(28, 5000);
                            rowhead.createCell(29).setCellValue("Installment Type");
                            sheet.setColumnWidth(29, 5000);
                            rowhead.createCell(30).setCellValue("Installment Pct");
                            sheet.setColumnWidth(30, 5000);
                            rowhead.createCell(31).setCellValue("Installment Base Amount");
                            sheet.setColumnWidth(31, 4000);
                            rowhead.createCell(32).setCellValue("Installment Tax Code");
                            sheet.setColumnWidth(32, 5000);
                            rowhead.createCell(33).setCellValue("Installment Tax Rate");
                            sheet.setColumnWidth(33, 5000);
                            rowhead.createCell(34).setCellValue("Installment Tax Amount");
                            sheet.setColumnWidth(34, 5000);
                            rowhead.createCell(35).setCellValue("Installment Amount");
                            sheet.setColumnWidth(35, 5000);
                            rowhead.createCell(36).setCellValue("Installment Start Date");
                            sheet.setColumnWidth(36, 5000);
                            rowhead.createCell(37).setCellValue("Installment Due Date");
                            sheet.setColumnWidth(37, 5000);
                            rowhead.createCell(38).setCellValue("Remarks");
                            sheet.setColumnWidth(38, 4000);
                            rowhead.createCell(39).setCellValue("Renewed From");
                            sheet.setColumnWidth(39, 4000);
                            
                            ViewObject actVO =
                                ADFUtils.findIterator("BookingVmsDtls_RoVo1Iterator").getViewObject();
                            ViewObject bkvMsDtlRoVo =
                                ADFUtils.findIterator("SEARCH_BOOKING_ROVOIterator").getViewObject();
                            actVO.executeQuery();
                    System.err.println("count++++"+actVO.getEstimatedRowCount());
                            bkvMsDtlRoVo.executeQuery();
                            if (actVO.getEstimatedRowCount() > 0) {
                                RowSetIterator rs = actVO.createRowSetIterator(null);
                                int excelRowData = 1;
                                while (rs.hasNext()) {
                                    Row actsRow = rs.next();
//                                    String bkHdrId =
//                                        actRow.getAttribute("BookingHdrId") != null ?
//                                        actRow.getAttribute("BookingHdrId").toString() : 
//                                         "";
//                                RowSetIterator rss = bkvMsDtlRoVo.createRowSetIterator(null);
//                                int excelRowDatas = 1;
//                                while (rss.hasNext()) {
//                                    Row actsRow = rss.next();
//                                    String bkId =
//                                        actsRow.getAttribute("BookingHdrId") != null ?
//                                        actsRow.getAttribute("BookingHdrId").toString() : 
//                                        "";  
//                                    if(bkHdrId.equals(bkId)){
                                    String bkNo =
                                        actsRow.getAttribute("BookingNumber") != null ?
                                        actsRow.getAttribute("BookingNumber").toString() :
                                        "";
                                    String bkDate =
                                        actsRow.getAttribute("BookingDate") != null ?
                                        actsRow.getAttribute("BookingDate").toString() :
                                        "";
                                    String bkStDate =
                                        actsRow.getAttribute("BookingLeaseStartDate") != null ?
                                        actsRow.getAttribute("BookingLeaseStartDate").toString() :
                                        "";
                                    String bkEndDate =
                                        actsRow.getAttribute("BookingLeaseEndDate") != null ?
                                        actsRow.getAttribute("BookingLeaseEndDate").toString() : "";
                                    String contractDays =
                                        actsRow.getAttribute("ContractDays") != null ? 
                                        actsRow.getAttribute("ContractDays").toString() :
                                        "";
                                    String orgName =
                                        actsRow.getAttribute("OrgName") != null ?
                                        actsRow.getAttribute("OrgName").toString() :
                                        "";
                                    String propName =
                                        actsRow.getAttribute("PropertyName") != null ?
                                        actsRow.getAttribute("PropertyName").toString() :
                                        "";          
                                    String buildName =
                                        actsRow.getAttribute("BuildName") != null ?
                                        actsRow.getAttribute("BuildName").toString() :
                                        "";
                                    String unitName =
                                        actsRow.getAttribute("UnitName") != null ?
                                        actsRow.getAttribute("UnitName").toString() :
                                        "";
                                    String unitNo =
                                        actsRow.getAttribute("UnitNumber") != null ?
                                        actsRow.getAttribute("UnitNumber").toString() :
                                        "";
                                    String custName =
                                        actsRow.getAttribute("CustomerName") != null ?
                                        actsRow.getAttribute("CustomerName").toString() :
                                        "";
                                    String ppName =
                                        actsRow.getAttribute("MilestoneName") != null ?
                                        actsRow.getAttribute("MilestoneName").toString() :
                                        "";
                                    String msType =
                                        actsRow.getAttribute("Attribute1") != null ?
                                        actsRow.getAttribute("Attribute1").toString() :
                                        "";
                                    String plName =
                                        actsRow.getAttribute("PlName") != null ?
                                        actsRow.getAttribute("PlName").toString() :
                                        "";
                                    String bkStatus =
                                        actsRow.getAttribute("Status") != null ?
                                        actsRow.getAttribute("Status").toString() :
                                        "";
                                    String bkSubStatus =
                                        actsRow.getAttribute("Attribute2") != null ?
                                        actsRow.getAttribute("Attribute2").toString() :
                                        "";                     
                                    String areaSqFt =
                                        actsRow.getAttribute("AreaInSqft") != null ?
                                        actsRow.getAttribute("AreaInSqft").toString() :
                                        "";
                                    String bkAmt =
                                        actsRow.getAttribute("BookingAmount") != null ?
                                        actsRow.getAttribute("BookingAmount").toString() :
                                        "";
                                                                String disTyp =
                                                                    actsRow.getAttribute("DiscountType") != null ?
                                                                    actsRow.getAttribute("DiscountType").toString() :
                                                                    "";
                                                                String disPct =
                                                                    actsRow.getAttribute("DiscPct") != null ?
                                                                    actsRow.getAttribute("DiscPct").toString() :
                                                                    "";
                                                                String disAmt =
                                                                    actsRow.getAttribute("DiscAmount") != null ?
                                                                    actsRow.getAttribute("DiscAmount").toString() :
                                                                    "";
                                                                String taxCode =
                                                                    actsRow.getAttribute("TaxCode") != null ?
                                                                    actsRow.getAttribute("TaxCode").toString() :
                                                                    "";
                                                                String taxRate =
                                                                    actsRow.getAttribute("TaxRate") != null ?
                                                                    actsRow.getAttribute("TaxRate").toString() :
                                                                    "";
                                                                String taxAmt =
                                                                    actsRow.getAttribute("TaxAmount") != null ?
                                                                    actsRow.getAttribute("TaxAmount").toString() :
                                                                    "";
                                                                String totAmt =
                                                                    actsRow.getAttribute("TotalAmount") != null ?
                                                                    actsRow.getAttribute("TotalAmount").toString() :
                                                                    "";
                                                                String curCode =
                                                                    actsRow.getAttribute("CurrencyCode") != null ?
                                                                    actsRow.getAttribute("CurrencyCode").toString() :
                                                                    "";
                                                                String seqNo =
                                                                    actsRow.getAttribute("SeqNumber") != null ?
                                                                    actsRow.getAttribute("SeqNumber").toString() :
                                                                    "";
                                                                String crgTyp =
                                                                    actsRow.getAttribute("ChargeType") != null ?
                                                                    actsRow.getAttribute("ChargeType").toString() :
                                                                    "";
                                                                String crgName =
                                                                    actsRow.getAttribute("ChargeName") != null ?
                                                                    actsRow.getAttribute("ChargeName").toString() :
                                                                    "";
                                                                String instlType =
                                                                    actsRow.getAttribute("InstallmentType") != null ?
                                                                    actsRow.getAttribute("InstallmentType").toString() :
                                                                    "";
                                                                String instlPct =
                                                                    actsRow.getAttribute("InstallmentPct") != null ?
                                                                    actsRow.getAttribute("InstallmentPct").toString() :
                                                                    "";
                                                                String msBaseAmt =
                                                                    actsRow.getAttribute("MsBaseamount") != null ?
                                                                    actsRow.getAttribute("MsBaseamount").toString() :
                                                                    "";
                                                                String msTaxCode =
                                                                    actsRow.getAttribute("MsTaxCode") != null ?
                                                                    actsRow.getAttribute("MsTaxCode").toString() :
                                                                    "";
                                                                String msTaxRate =
                                                                    actsRow.getAttribute("MsTaxRate") != null ?
                                                                    actsRow.getAttribute("MsTaxRate").toString() :
                                                                    "";
                                                                String msTaxAmt =
                                                                    actsRow.getAttribute("MsTaxAmount") != null ?
                                                                    actsRow.getAttribute("MsTaxAmount").toString() :
                                                                    "";
                                                                String instlAmt =
                                                                    actsRow.getAttribute("InstallmentAmount") != null ?
                                                                    actsRow.getAttribute("InstallmentAmount").toString() :
                                                                    "";
                                                                String msStDate =
                                                                    actsRow.getAttribute("StartDate") != null ?
                                                                    actsRow.getAttribute("StartDate").toString() :
                                                                    "";
                                                                String msDueDate =
                                                                    actsRow.getAttribute("DueDate") != null ?
                                                                    actsRow.getAttribute("DueDate").toString() :
                                                                    "";
                                                                String remarks =
                                                                    actsRow.getAttribute("Remarks") != null ?
                                                                    actsRow.getAttribute("Remarks").toString() :
                                                                    "";
                                    String renewedFrom =
                                        actsRow.getAttribute("Attribute4") != null ?
                                        actsRow.getAttribute("Attribute4").toString() :
                                        "";
                                    
                                    HSSFRow row = sheet.createRow((short)excelRowData);
                                    row.createCell(0).setCellValue(bkNo);
                                    row.createCell(1).setCellValue(bkDate);
                                    row.createCell(2).setCellValue(bkStDate);
                                    row.createCell(3).setCellValue(bkEndDate);
                                    row.createCell(4).setCellValue(contractDays);
                                    row.createCell(5).setCellValue(orgName);
                                    row.createCell(6).setCellValue(propName);
                                    row.createCell(7).setCellValue(buildName);
                                    row.createCell(8).setCellValue(unitName);
                                    row.createCell(9).setCellValue(unitNo);
                                    row.createCell(10).setCellValue(custName);
                                    row.createCell(11).setCellValue(ppName);
                                    row.createCell(12).setCellValue(msType);
                                    row.createCell(13).setCellValue(plName);
                                    row.createCell(14).setCellValue(bkStatus);
                                    row.createCell(15).setCellValue(bkSubStatus);
                                    row.createCell(16).setCellValue(areaSqFt);
                                    row.createCell(17).setCellValue(bkAmt);
                                    row.createCell(18).setCellValue(disTyp);
                                    row.createCell(19).setCellValue(disPct);
                                    row.createCell(20).setCellValue(disAmt);
                                    row.createCell(21).setCellValue(taxCode);
                                    row.createCell(22).setCellValue(taxRate);
                                    row.createCell(23).setCellValue(taxAmt);
                                    row.createCell(24).setCellValue(totAmt);
                                    row.createCell(25).setCellValue(curCode);
                                    row.createCell(26).setCellValue(seqNo);
                                    row.createCell(27).setCellValue(crgTyp);
                                    row.createCell(28).setCellValue(crgName);
                                    row.createCell(29).setCellValue(instlType);
                                    row.createCell(30).setCellValue(instlPct);
                                    row.createCell(31).setCellValue(msBaseAmt);
                                    row.createCell(32).setCellValue(msTaxCode);
                                    row.createCell(33).setCellValue(msTaxRate);
                                    row.createCell(34).setCellValue(msTaxAmt);
                                    row.createCell(35).setCellValue(instlAmt);
                                    row.createCell(36).setCellValue(msStDate);
                                    row.createCell(37).setCellValue(msDueDate);
                                    row.createCell(38).setCellValue(remarks);
                                    row.createCell(39).setCellValue(renewedFrom);
//                                }
//                                    excelRowDatas++;
//                                       }
                                    excelRowData++;
                                }
                            }
                            workbook.write(outputStream);
                            outputStream.flush();
                        } catch (Exception e) {
                            System.err.println("BDS" + e.getMessage());
                        }
                   }
}
