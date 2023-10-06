package custom.lease.view.bean.backing;


import custom.lease.custom.view.beans.Utils.ADFUtils;
import custom.lease.custom.view.beans.Utils.JSFUtils;

import custom.lease.model.View.XxplInvoiceLineVORowImpl;

import java.math.BigDecimal;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class AddEditInvoice {

    private RichTable t3;
    private RichSelectBooleanCheckbox checkbox;
    private RichTable t2;
    private RichOutputText total;
    private RichInputText linehrd;

    public void onClickSave(ActionEvent actionEvent) {

        setAction();
    }

    public void setAction() {
        if (ADFUtils.getBoundAttributeValue("InvoiceNumber") == null) {
            ViewObjectImpl vo =
                (ViewObjectImpl)ADFUtils.findIterator("InvoiceNumberROVO1Iterator").getViewObject();
            // System.err.println("="+ADFUtils.getBoundAttributeValue("ProjectIdTrans"));
            ViewCriteria voVc = vo.getViewCriteria("FindByProjectId");
            vo.setNamedWhereClauseParam("p_project_id", ADFUtils.getBoundAttributeValue("ProjectIdTrans"));
            vo.applyViewCriteria(voVc);
            vo.executeQuery();
            if (vo.getEstimatedRowCount() > 0) {
                String prefix = (String)vo.first().getAttribute("InvPrefix");
                BigDecimal sqno = (BigDecimal)vo.first().getAttribute("SeqNo");
                if (prefix != null && sqno != null) {
                    ADFUtils.setBoundAttributeValue("InvoiceNumber", prefix + "-" +sqno.toString());
                    OperationBinding op =
                        ADFUtils.findOperation("updateTranscation");
                    op.getParamsMap().put("project_id", ADFUtils.getBoundAttributeValue("ProjectIdTrans"));
                    op.execute();
                    JSFUtils.addFacesInformationMessage("Saved Sucessfully");
                }
            }
        } else {
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Saved Sucessfully");
        }
    }

    public void onClickSaveandClose(ActionEvent actionEvent) {
        setAction();
    }

    public void onChangeCheckBox(ValueChangeEvent valueChangeEvent) {
        ViewObject popupLines =
            ADFUtils.findIterator("InvoiceLinesROVOIterator").getViewObject();
        RowSetIterator dtlsRS = popupLines.createRowSetIterator(null);
        int currIndex = popupLines.getCurrentRowIndex();
        // System.err.println("currIndex" + currIndex);
        int i = 0;
        while (dtlsRS.hasNext()) {
            Row r = dtlsRS.next();
            if (i == currIndex) {
                i++;
                continue;
            }
            r.setAttribute("CheckBoxTrans", Boolean.FALSE);
            i++;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(t2);
        //       }
    }

    public String checkDuplicate(Boolean Type) {
        String dup = "N";
        try {

            ViewObject vo =
                ADFUtils.findIterator("InvoiceLinesROVOIterator").getViewObject();
            Row[] rows = vo.getFilteredRows("ChargeTypeTrans", Type);
            if (rows.length > 1)
                return dup = "Y";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dup;
    }

    public void popupFetch(PopupFetchEvent popupFetchEvent) {
        if (popupFetchEvent.getLaunchSourceClientId().contains("create")) {
            ViewObject vo =
                ADFUtils.findIterator("InvoiceLinesROVOIterator").getViewObject();
            ViewObject headervo =
                ADFUtils.findIterator("XxplInvoiceHeader1Iterator").getViewObject();
            if (popupFetchEvent.getLaunchSourceClientId().contains("create")) {
                ViewCriteria vc = vo.createViewCriteria();
                ViewCriteriaRow unitVOR = vc.createViewCriteriaRow();
                unitVOR.setAttribute("BookingHdrId",
                                     headervo.getCurrentRow().getAttribute("BookingId"));
                vc.addRow(unitVOR);
                vo.applyViewCriteria(vc);
                vo.executeQuery();
            } else {

            }


        }

    }

    public void onClickOk(DialogEvent dialogEvent) {
        ViewObjectImpl vo =
            (ViewObjectImpl)ADFUtils.findIterator("InvoiceLinesROVOIterator").getViewObject();
        RowSetIterator row = vo.createRowSetIterator(null);
        ViewObject linevo =
            ADFUtils.findIterator("XxplInvoiceLineVO1Iterator").getViewObject();


        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            while (row.hasNext()) {
                Row r = row.next();
               
                if (r.getAttribute("CheckBoxTrans") != null) {
                    boolean value = (Boolean)r.getAttribute("CheckBoxTrans");
                    // System.err.println("checkbox=====>" + value);
                    if (value) {
                       
                       
                        // System.err.println("inside");
//                        // System.err.println("=="+r.getAttribute("ChargeType"));
//                        // System.err.println("=ChargeTypeDisp="+r.getAttribute("ChargeTypeDisp"));
                        Row rline = linevo.createRow();
                        rline.setAttribute("LineNumber", 1);
                        rline.setAttribute("Description", "PRISM Lease");
                        rline.setAttribute("Amount", r.getAttribute("Baseamount"));
                        rline.setAttribute("LineAmount", r.getAttribute("Baseamount"));
                        rline.setAttribute("ChargeType",r.getAttribute("ChargeType"));
                        rline.setAttribute("PropertyId", r.getAttribute("PropertyId"));
                        rline.setAttribute("PropertyNumber", r.getAttribute("PropertyNumber"));
                        rline.setAttribute("BuildId", r.getAttribute("BuildingId"));
                        rline.setAttribute("BuildNumber", r.getAttribute("BuildName"));
                        rline.setAttribute("UnitId", r.getAttribute("UnitId"));
                        rline.setAttribute("UnitNumber", r.getAttribute("UnitNumber"));
                        rline.setAttribute("InstallmentType",r.getAttribute("InstallmentName"));
                        rline.setAttribute("LineType","LINE");
                        linevo.insertRow(rline);
                        
                        
                        Row rline1 = linevo.createRow();
                        rline1.setAttribute("LineNumber", 2);
                        rline1.setAttribute("Description", "PRISM Lease");
                        rline1.setAttribute("TaxAmount", r.getAttribute("TaxAmount"));
                        rline1.setAttribute("LineAmount", r.getAttribute("TaxAmount"));
                        rline1.setAttribute("TaxPerc", r.getAttribute("TaxRate"));
                        rline1.setAttribute("TaxCode",r.getAttribute("TaxCode"));
                        rline1.setAttribute("ChargeType",r.getAttribute("ChargeType"));
                        rline1.setAttribute("PropertyId", r.getAttribute("PropertyId"));
                        rline1.setAttribute("PropertyNumber", r.getAttribute("PropertyNumber"));
                        rline1.setAttribute("BuildId", r.getAttribute("BuildingId"));
                        rline1.setAttribute("BuildNumber", r.getAttribute("BuildName"));
                        rline1.setAttribute("UnitId", r.getAttribute("UnitId"));
                        rline1.setAttribute("UnitNumber", r.getAttribute("UnitNumber"));
                        rline1.setAttribute("InstallmentType",r.getAttribute("InstallmentName"));
                        rline1.setAttribute("LineType","TAX");
                        linevo.insertRow(rline1);                        
                        
                        
                        
                    }
                }
            }
            linevo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(t3);

        }
    }

    public void onClickOkprocesss(ActionEvent actionEvent) {
        ViewObject popupLines =
            ADFUtils.findIterator("InvoiceLinesROVOIterator").getViewObject();
        ViewObject invLine =
            ADFUtils.findIterator("XxplInvoiceLineVO1Iterator").getViewObject();

        RowSetIterator rs = popupLines.createRowSetIterator(null);
        
               
    
        while (rs.hasNext()) {
            Row r = rs.next();
            // System.err.println("==>" + r.getAttribute("CheckBoxTrans"));
            String aaba =
                r.getAttribute("CheckBoxTrans") == null ? "0" : r.getAttribute("CheckBoxTrans").toString();
            if (aaba.equalsIgnoreCase("true")) {
                // System.err.println("==" + "True");
                String a =
                    r.getAttribute("InstallmentName") == null ? "0" : r.getAttribute("InstallmentName").toString();
                // System.err.println("a==" + a);
                Row inRow = invLine.createRow();
                inRow.setAttribute("Description", a);
                invLine.insertRow(inRow);
            }
        }
        
        invLine.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(t3);

    }

    public void setT3(RichTable t3) {
        this.t3 = t3;
    }

    public RichTable getT3() {
        return t3;
    }

    public void setCheckbox(RichSelectBooleanCheckbox checkbox) {
        this.checkbox = checkbox;
    }

    public RichSelectBooleanCheckbox getCheckbox() {
        return checkbox;
    }

    public void setT2(RichTable t2) {
        this.t2 = t2;
    }

    public RichTable getT2() {
        return t2;
    }

    public void setTotal(RichOutputText total) {
        this.total = total;
    }

    public RichOutputText getTotal() {
        return total;
    }

    public void onClickDelete(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject invLine =
            ADFUtils.findIterator("XxplInvoiceLineVO1Iterator").getViewObject();
//        if (invLine.getCurrentRow().getAttribute("Amount") != null) {
//            BigDecimal amout =
//                (BigDecimal)invLine.getCurrentRow().getAttribute("Amount");
//            if (ADFUtils.getBoundAttributeValue("TotalLineAmount") != null) {
//                BigDecimal hrdAmount =
//                    (BigDecimal)ADFUtils.getBoundAttributeValue("TotalLineAmount");
//                ADFUtils.setBoundAttributeValue("TotalLineAmount",
//                                                amout.subtract(hrdAmount));
//                AdfFacesContext.getCurrentInstance().addPartialTarget(linehrd);
//                invLine.removeCurrentRow();
//                ;
//            }
//
//        }
        invLine.executeQuery();
        RowSetIterator rs=invLine.createRowSetIterator(null);
        while(rs.hasNext()){
            Row r=rs.next();
            r.remove();
        }
        invLine.executeQuery();

    }

    public void setLinehrd(RichInputText linehrd) {
        this.linehrd = linehrd;
    }

    public RichInputText getLinehrd() {
        return linehrd;
    }

    public void onClickInvoice(ActionEvent actionEvent) {
        // Add event code here...
//        ADFUtils.setBoundAttributeValue("InterfaceStatus", "READY TO INVOICE");
        ADFUtils.setBoundAttributeValue("InterfaceStatus", "Invoiced");
        ADFUtils.findOperation("Commit").execute();
    }
}

