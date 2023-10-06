package AddEditBuildingBB;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import javax.faces.event.ActionEvent;

public class AddEditBuildingBB {
    public AddEditBuildingBB() {
    }

    public void onClickSave(ActionEvent actionEvent) {
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Building Information Saved Successfully !!!");

    }
}
