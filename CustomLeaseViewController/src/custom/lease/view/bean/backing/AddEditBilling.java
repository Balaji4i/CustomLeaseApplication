package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import custom.lease.custom.view.beans.Utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class AddEditBilling {
    private RichPopup p1;
    private RichTable t2;

    public AddEditBilling() {
    }

    ViewObject bilingHdrVO=ADFUtils.findIterator("XxplBilling_View1Iterator").getViewObject();

    ViewObject bookingMsROVO_VO=ADFUtils.findIterator("BillingMilestoneROVO1Iterator").getViewObject();
    
    ViewObject bookingBillingMsVO=ADFUtils.findIterator("XxplBookingMilestones_VO1Iterator").getViewObject();

    ViewObject ApprovalTypeVO=ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();

    ViewObject bookingMsVO=ADFUtils.findIterator("XxplBookingMilestones_VO2Iterator").getViewObject();

    public void onClickMilestone(ActionEvent actionEvent) {
        String orgId=bilingHdrVO.getCurrentRow().getAttribute("OrgId")==null?"0":bilingHdrVO.getCurrentRow().getAttribute("OrgId").toString();
        // System.err.println("orgId-->"+orgId);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        getP1().show(hints);
        bookingMsROVO_VO.setNamedWhereClauseParam("BVBILLORGID", orgId);
        bookingMsROVO_VO.executeQuery();
        
    }

    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }

    public void onClickBookingMsOk(ActionEvent actionEvent) {
       String billingId=bilingHdrVO.getCurrentRow().getAttribute("BillingId")==null?"0":bilingHdrVO.getCurrentRow().getAttribute("BillingId").toString();
       // System.err.println("billingId==>"+billingId);
       if(billingId.equalsIgnoreCase("0")){
           
       }else{
           RowSetIterator Rs=bookingMsROVO_VO.createRowSetIterator(null);
           while(Rs.hasNext()){
             Row r=Rs.next();
             String checkflag=r.getAttribute("TransientCheckBox")==null?"0":r.getAttribute("TransientCheckBox").toString();
             // System.err.println("checkflag==>"+checkflag);  
               if(checkflag.equalsIgnoreCase("true")){
                   String _BookingHdrId=r.getAttribute("BookingHdrId")==null?"0":r.getAttribute("BookingHdrId").toString();
                   String _ChargeType=r.getAttribute("ChargeType")==null?"0":r.getAttribute("ChargeType").toString();
                   String _BookingMsDtlId=r.getAttribute("BookingMsDtlId")==null?"0":r.getAttribute("BookingMsDtlId").toString();
                   // System.err.println("_BookingHdrId"+_BookingHdrId);
                   // System.err.println("_ChargeType"+_ChargeType);
                   // System.err.println("_BookingMsDtlId"+_BookingMsDtlId);
                   // filter booking MS
                   ViewCriteria vc=bookingMsVO.createViewCriteria();
                   ViewCriteriaRow vcRow=vc.createViewCriteriaRow();
                   vcRow.setAttribute("ChargeType", _ChargeType);
                   vcRow.setAttribute("BookingHdrId", _BookingHdrId);
                   vcRow.setAttribute("BookingMsDtlId", _BookingMsDtlId);
                   vc.addRow(vcRow);
                   bookingMsVO.applyViewCriteria(vc);
                   bookingMsVO.executeQuery();
                   // System.err.println("count=="+bookingMsVO.getEstimatedRowCount());
                   if(bookingMsVO.getEstimatedRowCount()>0){
                       RowSetIterator msRS=bookingMsVO.createRowSetIterator(null);
                       while(msRS.hasNext()){
                           Row msRow=msRS.next();
                           msRow.setAttribute("BillingId", billingId);
                           // System.err.println("==SETTT==");
                       }
                   }
               }
           }  
           AdfFacesContext.getCurrentInstance().addPartialTarget(t2); 
           bookingBillingMsVO.executeQuery();
       }
    }

    public void setT2(RichTable t2) {
        this.t2 = t2;
    }

    public RichTable getT2() {
        return t2;
    }

    public void onClickDelete(ActionEvent actionEvent) {
        bookingBillingMsVO.getCurrentRow().setAttribute("BillingId", null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(t2); 
        bookingBillingMsVO.executeQuery();
    }

    public void onClickSave(ActionEvent actionEvent) {
        String page=ADFContext.getCurrent().getPageFlowScope().get("billNaviResult")==null?"ABC":ADFContext.getCurrent().getPageFlowScope().get("billNaviResult").toString();
        if(page.equalsIgnoreCase("EDIT")){
            custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
        }else if(page.equalsIgnoreCase("ADD")){
            String billingId=bilingHdrVO.getCurrentRow().getAttribute("BillingId")==null?"0":bilingHdrVO.getCurrentRow().getAttribute("BillingId").toString();
            bilingHdrVO.getCurrentRow().setAttribute("BillingNumber", "BILL-"+billingId);
            custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Error in Navigation");
        }
    }


    public void onClickSubmit(ActionEvent actionEvent) {
        ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "BILLING_APR_TYPE");
        ApprovalTypeVO.executeQuery();
        String approveType=ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
        onClickSave(actionEvent);
        if(approveType.equalsIgnoreCase("AUTO")){
            bilingHdrVO.getCurrentRow().setAttribute("Status", "Approved");
//            bilingHdrVO.getCurrentRow().setAttribute("Status", "Pending");
            custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully and Approved");
        }else if (approveType.equalsIgnoreCase("ADF")){
            
        }else if(approveType.equalsIgnoreCase("PCS")){
            
        }else{
            JSFUtils.addFacesErrorMessage("Apprival Type is missing."); 
        }
    }
}
