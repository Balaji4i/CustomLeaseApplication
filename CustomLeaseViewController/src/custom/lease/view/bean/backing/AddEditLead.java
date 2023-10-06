package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.Utils.ADFUtils;

import custom.lease.custom.view.beans.Utils.JSFUtils;

import custom.lease.model.View.XxplLeadVORowImpl;

import java.math.BigDecimal;

import java.math.RoundingMode;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import weblogic.jms.common.HdrMessageImpl;

public class AddEditLead {
    public AddEditLead() {
    }


    ViewObject vo = ADFUtils.findIterator("XxplLeadVOIterator").getViewObject();

    ViewObject leadNoteVO = ADFUtils.findIterator("XxplLeadNoteView1Iterator").getViewObject();

    public void onClickSave(ActionEvent actionEvent) {
        vo.getCurrentRow().setAttribute("FuncId", "6");
        vo.getCurrentRow().setAttribute("Status", "Active");
        String leadName = vo.getCurrentRow().getAttribute("LeadName") == null ? "0" :vo.getCurrentRow().getAttribute("LeadName").toString();
        vo.getCurrentRow().setAttribute("LeadName",leadName);
        String id=vo.getCurrentRow().getAttribute("LeadId")==null?"0":vo.getCurrentRow().getAttribute("LeadId").toString();
        vo.getCurrentRow().setAttribute("LeadNumber","LD-"+id);
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Saved Successfully");

    }

    public void onClickSaveAndClose(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("XxplLeadVOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        ADFUtils.findOperation("Rollback").execute();
    }

    public void onClickActiveRPVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
              
        ViewObject vo = ADFUtils.findIterator("XxplLeadVOIterator").getViewObject();
        Row hdrRow = vo.getCurrentRow();
        RowSetIterator uopLine = vo.createRowSetIterator(null);
//      String  a = hdrRow.getAttribute("PerCountry").toString();
    // System.err.println("ActiveRp"+hdrRow.getAttribute("ActiveRp") );

        String perCou = (hdrRow.getAttribute("Trans_PerCountry") == null ? " " :hdrRow.getAttribute("Trans_PerCountry").toString());
        if(hdrRow.getAttribute("ActiveRp")!= null && hdrRow.getAttribute("ActiveRp") == "Y"){
//          Trans_CurCountry
        hdrRow.setAttribute("CurCountry",hdrRow.getAttribute("PerCountry"));
         hdrRow.setAttribute("Trans_CurCountry",perCou);
                    hdrRow.setAttribute("CurAddress1",hdrRow.getAttribute("PerAddress1"));
                    hdrRow.setAttribute("CurAddress2",hdrRow.getAttribute("PerAddress2"));
                    hdrRow.setAttribute("CurAddress3",hdrRow.getAttribute("PerAddress3"));
                    hdrRow.setAttribute("CurAddress4",hdrRow.getAttribute("PerAddress4"));
                    hdrRow.setAttribute("CurCity",hdrRow.getAttribute("PerCity"));
    }  else{
                hdrRow.setAttribute("CurCountry",null);
                hdrRow.setAttribute("Trans_CurCountry",null);
                hdrRow.setAttribute("CurAddress1",null);
                hdrRow.setAttribute("CurAddress2",null);
                hdrRow.setAttribute("CurAddress3",null);
                hdrRow.setAttribute("CurAddress4",null);
                hdrRow.setAttribute("CurCity",null);
            
            }
                }


    public void onClickAttachment(ActionEvent actionEvent) {
        
        String funId= leadNoteVO.getCurrentRow().getAttribute("FuncId")==null?"41":leadNoteVO.getCurrentRow().getAttribute("FuncId").toString();
        String leadNoteId= leadNoteVO.getCurrentRow().getAttribute("LeadNoteId")==null?"0":leadNoteVO.getCurrentRow().getAttribute("LeadNoteId").toString();
        // System.err.println("funId=>"+funId);
        // System.out.println("leadNoteId=>"+leadNoteId);
        ADFContext.getCurrent().getPageFlowScope().put("FuncId",funId);
        ADFContext.getCurrent().getPageFlowScope().put("FuncRefId",leadNoteId);
    }
}

