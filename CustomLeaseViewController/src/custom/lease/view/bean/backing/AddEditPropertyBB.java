package custom.lease.view.bean.backing;


import custom.lease.custom.view.beans.Utils.ADFUtils;
import custom.lease.custom.view.beans.Utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;



import oracle.binding.OperationBinding;

public class AddEditPropertyBB {
    public AddEditPropertyBB() {
        super();
    }

    public void onClickSave(ActionEvent actionEvent) {
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Property Information Saved Successfully !");
    }

    public void onClickAttachment(ActionEvent actionEvent) {
        //
        ADFContext.getCurrent().getPageFlowScope().put("FuncId",
                                                               ADFUtils.getBoundAttributeValue("FuncId"));
                ADFContext.getCurrent().getPageFlowScope().put("FuncRefId",
                                                               ADFUtils.getBoundAttributeValue("PropertyId"));

    }
     
}
