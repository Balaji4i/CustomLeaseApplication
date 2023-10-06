package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;


import custom.lease.custom.view.beans.PackageUtils.PackageCalling;
import custom.lease.custom.view.beans.Utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchPriceList {


    private RichPopup popup;

    public void onChangeRecord(PopupFetchEvent popupFetchEvent) {
    
//     OperationBinding op= ADFUtils.findOperation("ExecuteWithParams");
//     
//     ViewObject vo =ADFUtils.findIterator("SearchPriceListROVOIterator").getViewObject();
//    
//     //System.err.println(""+ vo.getCurrentRow().getAttribute("PlNumber"));
//        op.getParamsMap().put("p_property_number", vo.getCurrentRow().getAttribute("PlNumber"));
//        op.execute();
    }

    ViewObject priceListVO =ADFUtils.findIterator("SearchPriceListROVOIterator").getViewObject();

    public void onClickAbout(ActionEvent actionEvent) {
        OperationBinding op= ADFUtils.findOperation("ExecuteWithParams");
        
        ViewObject vo =ADFUtils.findIterator("SearchPriceListROVOIterator").getViewObject();
        
        //System.err.println(""+ vo.getCurrentRow().getAttribute("PlNumber"));
           op.getParamsMap().put("p_property_number", vo.getCurrentRow().getAttribute("PlNumber"));
           op.execute();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getPopup().show(hints);
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public String onClickRevise() {
        String pricenum =priceListVO.getCurrentRow().getAttribute("PlId")==null?"0":priceListVO.getCurrentRow().getAttribute("PlId").toString();
        //System.err.println("pricenum==>"+pricenum);
        String a=PackageCalling.createRevisionPriceList(pricenum);
        //System.err.println("PRINT-->"+a);
        ADFContext.getCurrent().getPageFlowScope().put("Priceid", pricenum);
//        custom.lease.custom.view.beans.Utils.ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Revisied  Successfully");
        return "toPrice";
    }
}
