package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.Utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchARSetup {
    public SearchARSetup() {
    }

    public void onClickEdit(ActionEvent actionEvent) {
        Object obj =  ADFContext.getCurrent().getPageFlowScope().get("headerId");
          // System.err.println("Object Name"+obj);
                 ViewObject HdrVO = ADFUtils.findIterator("XxplARSetupVOIterator").getViewObject();
                 ViewCriteria hdrVC = HdrVO.createViewCriteria();
                 ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
                 hdrVcr.setAttribute("ArSetupId", obj);
                 hdrVC.addRow(hdrVcr);
                 HdrVO.applyViewCriteria(hdrVC);
                 HdrVO.executeQuery();
    }

//    ViewObject ArHdrVO = ADFUtils.findIterator("XxplARSetupVOIterator1").getViewObject();
//    
//    public void onClickDelete(ActionEvent actionEvent) {
//        ViewObject SearchVO = ADFUtils.findIterator("SearchARSetupROVOIterator").getViewObject();
//        String id = SearchVO.getCurrentRow().getAttribute("ArSetupId")==null?"0":SearchVO.getCurrentRow().getAttribute("ArSetupId").toString();
//        System.out.println("ID==>"+SearchVO.getCurrentRow().getAttribute("ArSetupId"));
//        ViewCriteria VC=ArHdrVO.createViewCriteria();
//        ViewCriteriaRow VCR=VC.createViewCriteriaRow();
//        VCR.setAttribute("ArSetupId", id);
//        VC.add(VCR);
//        ArHdrVO.applyViewCriteria(VC);
//        ArHdrVO.executeQuery();
//        if(ArHdrVO.getEstimatedRowCount()==1){
//            ADFUtils.findOperation("Commit").execute();
//        }
//        
//    }
}
