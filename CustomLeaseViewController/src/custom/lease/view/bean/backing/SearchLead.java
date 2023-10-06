package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.Utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchLead {
    public SearchLead() {
    }

    public void onClickEdit(ActionEvent actionEvent) {
        Object obj =  ADFContext.getCurrent().getPageFlowScope().get("headerId");
          //System.err.println("Object Name"+obj);
                 ViewObject HdrVO = ADFUtils.findIterator("XxplLeadVOIterator").getViewObject();
                 ViewCriteria hdrVC = HdrVO.createViewCriteria();
                 ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
                 hdrVcr.setAttribute("LeadId", obj);
                 hdrVC.addRow(hdrVcr);
                 HdrVO.applyViewCriteria(hdrVC);
                 HdrVO.executeQuery();
    }
}
