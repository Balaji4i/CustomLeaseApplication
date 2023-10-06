package custom.lease.model.AM;


import custom.lease.model.AM.common.Invoice_AM;
import custom.lease.model.View.XxplCancellationCharge_VOImpl;
import custom.lease.model.View.XxplCancellation_VOImpl;

import java.sql.CallableStatement;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 19 19:45:54 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class Invoice_AMImpl extends ApplicationModuleImpl implements Invoice_AM {
    /**
     * This is the default constructor (do not remove).
     */
    public Invoice_AMImpl() {
    }

    /**
     * Container's getter for XxplInvoiceHeader1.
     * @return XxplInvoiceHeader1
     */
    public ViewObjectImpl getXxplInvoiceHeader1() {
        return (ViewObjectImpl)findViewObject("XxplInvoiceHeader1");
    }

    /**
     * Container's getter for XxplInvoiceLineVO1.
     * @return XxplInvoiceLineVO1
     */
    public ViewObjectImpl getXxplInvoiceLineVO1() {
        return (ViewObjectImpl)findViewObject("XxplInvoiceLineVO1");
    }

    /**
     * Container's getter for InvoiceNumberROVO1.
     * @return InvoiceNumberROVO1
     */
    public ViewObjectImpl getInvoiceNumberROVO1() {
        return (ViewObjectImpl)findViewObject("InvoiceNumberROVO1");
    }

    /**
     * Container's getter for SerachInvoiceROVO.
     * @return SerachInvoiceROVO
     */
    public ViewObjectImpl getSerachInvoiceROVO() {
        return (ViewObjectImpl)findViewObject("SerachInvoiceROVO");
    }

    /**
     * Container's getter for XxplInvoiceVL1.
     * @return XxplInvoiceVL1
     */
    public ViewLinkImpl getXxplInvoiceVL1() {
        return (ViewLinkImpl)findViewLink("XxplInvoiceVL1");
    }
    
    public void updateTranscation(Long project_id) {
          
           try {
               // Calling Package
             CallableStatement cs =this.getDBTransaction().createCallableStatement("{call UPDATE_TRANSCATION_NUMBER(?)}",1);
              
//             System.err.println("project_id"+project_id);               
              
              cs.setLong(1, project_id);
              cs.execute();
              
           } catch (Exception e) {
//               System.out.println(e.getMessage());
           }
         
       }


    /**
     * Container's getter for InvoiceLinesROVO1.
     * @return InvoiceLinesROVO1
     */
    public ViewObjectImpl getInvoiceLinesROVO() {
        return (ViewObjectImpl)findViewObject("InvoiceLinesROVO");
    }

    /**
     * Container's getter for XxplCancellation_VO1.
     * @return XxplCancellation_VO1
     */
    public ViewObjectImpl getXxplCancellation_VO() {
        return (ViewObjectImpl)findViewObject("XxplCancellation_VO");
    }

    /**
     * Container's getter for XxplBookingMilestones_VO1.
     * @return XxplBookingMilestones_VO1
     */
    public ViewObjectImpl getXxplBookingMilestones_VO() {
        return (ViewObjectImpl)findViewObject("XxplBookingMilestones_VO");
    }

    /**
     * Container's getter for Cancellation_Milestone_VL1.
     * @return Cancellation_Milestone_VL1
     */
    public ViewLinkImpl getCancellation_Milestone_VL1() {
        return (ViewLinkImpl)findViewLink("Cancellation_Milestone_VL1");
    }

    /**
     * Container's getter for SEARCH_CANCELLATION_ROVO1.
     * @return SEARCH_CANCELLATION_ROVO1
     */
    public ViewObjectImpl getSEARCH_CANCELLATION_ROVO() {
        return (ViewObjectImpl)findViewObject("SEARCH_CANCELLATION_ROVO");
    }

    /**
     * Container's getter for CANCELLATION_DTL_ROVO1.
     * @return CANCELLATION_DTL_ROVO1
     */
    public ViewObjectImpl getBOOKING_DTL_ROVO() {
        return (ViewObjectImpl)findViewObject("BOOKING_DTL_ROVO");
    }

    /**
     * Container's getter for Cancellation_BookingDtl_VL1.
     * @return Cancellation_BookingDtl_VL1
     */
    public ViewLinkImpl getCancellation_BookingDtl_VL1() {
        return (ViewLinkImpl)findViewLink("Cancellation_BookingDtl_VL1");
    }


    /**
     * Container's getter for XxplCancellationDtl_VO2.
     * @return XxplCancellationDtl_VO2
     */
    public ViewObjectImpl getXxplCancellationDtl_VO() {
        return (ViewObjectImpl)findViewObject("XxplCancellationDtl_VO");
    }

    /**
     * Container's getter for Cancellation_Hdr_Dtl_VL2.
     * @return Cancellation_Hdr_Dtl_VL2
     */
    public ViewLinkImpl getCancellation_Hdr_Dtl_VL2() {
        return (ViewLinkImpl)findViewLink("Cancellation_Hdr_Dtl_VL2");
    }

    /**
     * Container's getter for XxplCancellationCharge_VO1.
     * @return XxplCancellationCharge_VO1
     */
    public ViewObjectImpl getXxplCancellationCharge_VO() {
        return (ViewObjectImpl)findViewObject("XxplCancellationCharge_VO");
    }

    /**
     * Container's getter for Cancellation_Hdr_Charge_VL1.
     * @return Cancellation_Hdr_Charge_VL1
     */
    public ViewLinkImpl getCancellation_Hdr_Charge_VL1() {
        return (ViewLinkImpl)findViewLink("Cancellation_Hdr_Charge_VL1");
    }

    /**
     * Container's getter for XxplBookingMilestones_VO1.
     * @return XxplBookingMilestones_VO1
     */
    public ViewObjectImpl getXxplBookingMilestones_VO1() {
        return (ViewObjectImpl)findViewObject("XxplBookingMilestones_VO1");
    }

    /**
     * Container's getter for CancellationVo_BookingMs_VL1.
     * @return CancellationVo_BookingMs_VL1
     */
    public ViewLinkImpl getCancellationVo_BookingMs_VL1() {
        return (ViewLinkImpl)findViewLink("CancellationVo_BookingMs_VL1");
    }

    /**
     * Container's getter for XxplCancellationCharge_VO1.
     * @return XxplCancellationCharge_VO1
     */
    public XxplCancellationCharge_VOImpl getXxplCancellationCharge_VO1() {
        return (XxplCancellationCharge_VOImpl)findViewObject("XxplCancellationCharge_VO1");
    }

    /**
     * Container's getter for Cancellation_Hdr_Charge_VL2.
     * @return Cancellation_Hdr_Charge_VL2
     */
    public ViewLinkImpl getCancellation_Hdr_Charge_VL2() {
        return (ViewLinkImpl)findViewLink("Cancellation_Hdr_Charge_VL2");
    }

    /**
     * Container's getter for XXPL_TENANT_OPEN_INV_VROVO1.
     * @return XXPL_TENANT_OPEN_INV_VROVO1
     */
    public ViewObjectImpl getXXPL_TENANT_OPEN_INV_VROVO1() {
        return (ViewObjectImpl)findViewObject("XXPL_TENANT_OPEN_INV_VROVO1");
    }

    /**
     * Container's getter for XxplCancellationCharge_VO2.
     * @return XxplCancellationCharge_VO2
     */
    public XxplCancellationCharge_VOImpl getCancellationCharge_VO() {
        return (XxplCancellationCharge_VOImpl)findViewObject("CancellationCharge_VO");
    }

    /**
     * Container's getter for XxplBookingMilestones_VO2.
     * @return XxplBookingMilestones_VO2
     */
    public ViewObjectImpl getBookingMilestones_VO() {
        return (ViewObjectImpl)findViewObject("BookingMilestones_VO");
    }

    /**
     * Container's getter for XxplReceiptHeaderView1.
     * @return XxplReceiptHeaderView1
     */
    public ViewObjectImpl getXxplReceiptHeaderView1() {
        return (ViewObjectImpl)findViewObject("XxplReceiptHeaderView1");
    }

    /**
     * Container's getter for Cancell_VL_ReceiptHdr1.
     * @return Cancell_VL_ReceiptHdr1
     */
    public ViewLinkImpl getCancell_VL_ReceiptHdr1() {
        return (ViewLinkImpl)findViewLink("Cancell_VL_ReceiptHdr1");
    }

    /**
     * Container's getter for XxplReceiptLineView1.
     * @return XxplReceiptLineView1
     */
    public ViewObjectImpl getXxplReceiptLineView1() {
        return (ViewObjectImpl)findViewObject("XxplReceiptLineView1");
    }

    /**
     * Container's getter for ReceiptHdrLine_VL1.
     * @return ReceiptHdrLine_VL1
     */
    public ViewLinkImpl getReceiptHdrLine_VL1() {
        return (ViewLinkImpl)findViewLink("ReceiptHdrLine_VL1");
    }

    /**
     * Container's getter for XxplReceiptLineView2.
     * @return XxplReceiptLineView2
     */
    public ViewObjectImpl getXxplReceiptLineView2() {
        return (ViewObjectImpl)findViewObject("XxplReceiptLineView2");
    }

    /**
     * Container's getter for TenantOpenInvROVO1.
     * @return TenantOpenInvROVO1
     */
    public ViewObjectImpl getTenantOpenInvROVO1() {
        return (ViewObjectImpl)findViewObject("TenantOpenInvROVO1");
    }

    /**
     * Container's getter for CancellHdrVLTenantOpenInv1.
     * @return CancellHdrVLTenantOpenInv1
     */
    public ViewLinkImpl getCancellHdrVLTenantOpenInv1() {
        return (ViewLinkImpl)findViewLink("CancellHdrVLTenantOpenInv1");
    }

    /**
     * Container's getter for TenantOpenInvROVO2.
     * @return TenantOpenInvROVO2
     */
    public ViewObjectImpl getTenantOpenInvROVO2() {
        return (ViewObjectImpl)findViewObject("TenantOpenInvROVO2");
    }
}
