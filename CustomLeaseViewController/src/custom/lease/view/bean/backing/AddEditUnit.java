package custom.lease.view.bean.backing;



import custom.lease.custom.view.beans.Utils.ADFUtils;

import custom.lease.custom.view.beans.Utils.JSFUtils;

import java.util.Iterator;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class AddEditUnit {
    private RichTable t1;
    private RichPopup p1;
    private RichTable t11;

    public AddEditUnit() {
    }

    public void setT1(RichTable t1) {
        this.t1 = t1;
    }

    public RichTable getT1() {
        return t1;
    }

ViewObject UnitMasterVO=ADFUtils.findIterator("XxplPropertyUnitsView1Iterator").getViewObject();
    
ViewObject CarParkingMasterVO=ADFUtils.findIterator("XxplPropertyCarparksView1Iterator").getViewObject();

ViewObject AreaVO=ADFUtils.findIterator("XxplPropertyAreaView1Iterator").getViewObject();

ViewObject CarParkingVO=ADFUtils.findIterator("CarParkingROVO1Iterator1").getViewObject();


ViewObject CarParkingupdateVO=ADFUtils.findIterator("XxplPropertyUnitsView2Iterator").getViewObject();
    

ViewObject UnitOwnerVO=ADFUtils.findIterator("XxplOwnerDtleView1Iterator").getViewObject();    

    


    public void onClickCreateArea(ActionEvent actionEvent) {
        Row row =AreaVO.createRow();
        row.setAttribute("Area", "CHARGE");
        row.setAttribute("Uom", "SQFT");
        AreaVO.insertRow(row);
        AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
    }


    public void onClickSave(ActionEvent actionEvent) {
        String page=ADFContext.getCurrent().getPageFlowScope().get("naviResult")==null?"ABC":ADFContext.getCurrent().getPageFlowScope().get("naviResult").toString();
        if(page.equalsIgnoreCase("EDIT")){
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
        }else if(page.equalsIgnoreCase("ADD")){
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");
        }else{
            JSFUtils.addFacesErrorMessage("Error in Navigation");
        }
       
    }
    
    
    private boolean isCustomerValid() {
        ViewObject customerVo = ADFUtils.findIterator("XxplOwnerDtleView1Iterator").getViewObject();
        if(customerVo.getEstimatedRowCount()==0){
            JSFUtils.addFacesErrorMessage("Please add owner details !");   
            return false;
        }
        RowSetIterator dtlsRS = customerVo.createRowSetIterator(null);
        boolean flag = false;
        while (dtlsRS.hasNext()) { 
            Row r = dtlsRS.next();
            String primary = r.getAttribute("TransientCheckBox")!=null?r.getAttribute("TransientCheckBox").toString():"false";
            if("true".equals(primary)){
                flag = true;
            }
        }
        if(!flag){ 
            JSFUtils.addFacesErrorMessage("Please check, One Owner should be primary !");   
        } 
        return flag;
    }

    

    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }


    
    public void onClickAddFreeCarParking(ActionEvent actionEvent) {
        String propertyId=UnitMasterVO.getCurrentRow().getAttribute("PropertyId")==null?"0":UnitMasterVO.getCurrentRow().getAttribute("PropertyId").toString();
        // System.err.println("propertyId==>"+propertyId);
        CarParkingVO.setNamedWhereClauseParam("BVS_PROPERTY_ID", propertyId); 
        CarParkingVO.setNamedWhereClauseParam("BVS_UNIT_TYPE", "FREE");
        CarParkingVO.executeQuery();
        RichPopup.PopupHints hint=new RichPopup.PopupHints();
        p1.show(hint);
    }

    public void onClickAddPaidCarParking(ActionEvent actionEvent) {
        String propertyId=UnitMasterVO.getCurrentRow().getAttribute("PropertyId")==null?"0":UnitMasterVO.getCurrentRow().getAttribute("PropertyId").toString();
        // System.err.println("propertyId==>"+propertyId);
        CarParkingVO.setNamedWhereClauseParam("BVS_PROPERTY_ID", propertyId); 
        CarParkingVO.setNamedWhereClauseParam("BVS_UNIT_TYPE", "PAID");
        CarParkingVO.executeQuery();
        RichPopup.PopupHints hint=new RichPopup.PopupHints();
        p1.show(hint);
    }



    public void onClickFreeOk(ActionEvent actionEvent) {
      RowSetIterator rs=CarParkingVO.createRowSetIterator(null);
      while(rs.hasNext()){
          Row r=rs.next();
//          // System.err.println("=check box="+r.getAttribute("SelectAll_Trans"));
//          // System.err.println("=UnitId="+r.getAttribute("UnitId"));
//          // System.err.println("=UnitType="+r.getAttribute("UnitType"));
//          // System.err.println("=FuncId="+r.getAttribute("FuncId"));
          if(r.getAttribute("SelectAll_Trans")!=null && r.getAttribute("SelectAll_Trans").equals(true)){
              // Inserting Row
              Row unitRow=CarParkingMasterVO.createRow();
              unitRow.setAttribute("CpUnitId", r.getAttribute("UnitId"));
              unitRow.setAttribute("AllotType", r.getAttribute("AllotType"));
              if(r.getAttribute("AllotType").equals("FREE")){
                  unitRow.setAttribute("Value", 0);    
              }else{
                  unitRow.setAttribute("Value", r.getAttribute("FuncId"));
              }
              CarParkingMasterVO.insertRow(unitRow);
          }
          // filter and update status 
          ViewCriteria  vc = CarParkingupdateVO.createViewCriteria();
          ViewCriteriaRow unitVOR=vc.createViewCriteriaRow();
          unitVOR.setAttribute("UnitId", r.getAttribute("UnitId"));
          vc.addRow(unitVOR);
          CarParkingupdateVO.applyViewCriteria(vc);
          CarParkingupdateVO.executeQuery();
          if(CarParkingupdateVO.getEstimatedRowCount()==1){
              CarParkingupdateVO.first().setAttribute("Status", "Allotted"); 
          }
      }
        CarParkingMasterVO.executeQuery();
    }

    public void onClickDelete(ActionEvent actionEvent) {
        
        String carParkingUnitId=CarParkingMasterVO.getCurrentRow().getAttribute("CpUnitId").toString()==null?"0":CarParkingMasterVO.getCurrentRow().getAttribute("CpUnitId").toString();
        // System.err.println("carParkingUnitId==>"+carParkingUnitId);
        ViewCriteria  vc = CarParkingupdateVO.createViewCriteria();
        ViewCriteriaRow unitVOR=vc.createViewCriteriaRow();
        unitVOR.setAttribute("UnitId", carParkingUnitId);
        vc.addRow(unitVOR);
        CarParkingupdateVO.applyViewCriteria(vc);
        CarParkingupdateVO.executeQuery();
        if(CarParkingupdateVO.getEstimatedRowCount()==1){
            CarParkingupdateVO.first().setAttribute("Status", "Available"); 
        } 
        CarParkingMasterVO.getCurrentRow().remove(); 
    }

    public void onChangeUnitType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if(valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().toString().equalsIgnoreCase("Car Parking")){
            //Refresh
            UnitMasterVO.executeQuery();
            // System.err.println("<===>");
        }else{
            UnitMasterVO.getCurrentRow().setAttribute("AllotType", null);
            // System.err.println("<OLD===>");
        }
    }


    
    public void onChangePrimaryOwner(ValueChangeEvent valueChangeEvent) {
       valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
//       if(valueChangeEvent.getNewValue()!=null){
           RowSetIterator dtlsRS = UnitOwnerVO.createRowSetIterator(null);
            int currIndex = UnitOwnerVO.getCurrentRowIndex();
            // System.err.println("currIndex"+currIndex);
            int i = 0;
            while (dtlsRS.hasNext()) {
              Row r = dtlsRS.next();
                if (i == currIndex) {
                     i++;
                 continue;
              }
              r.setAttribute("TransientCheckBox", Boolean.FALSE);
                  i++;
            }
           AdfFacesContext.getCurrentInstance().addPartialTarget(getT11());    
//       }
    }

    public void setT11(RichTable t11) {
        this.t11 = t11;
    }

    public RichTable getT11() {
        return t11;
    }

    public void rowDisclosureListener(RowDisclosureEvent rowDisclosureEvent) {
        RichTable table = (RichTable) rowDisclosureEvent.getSource();
                   RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
                   RowKeySet lastAddedRowKeySet = rowDisclosureEvent.getAddedSet();
                   Iterator lastAddedRowKeySetIter = lastAddedRowKeySet.iterator();
                   if (lastAddedRowKeySetIter.hasNext())
                   {
                     discloseRowKeySet.clear();
                     Object lastRowKey = lastAddedRowKeySetIter.next();
                     discloseRowKeySet.add(lastRowKey);
                     makeDisclosedRowCurrent(table, lastAddedRowKeySet);
                     AdfFacesContext adfFacesContext = null;
                     adfFacesContext = AdfFacesContext.getCurrentInstance();
                     adfFacesContext.addPartialTarget(table.getParent());
                   }
    }
    
    
    private void makeDisclosedRowCurrent(RichTable table, RowKeySet keySet)
          {
            table.setSelectedRowKeys(keySet);
            CollectionModel tableModel = (CollectionModel) table.getValue();
            JUCtrlHierBinding tableHierBinding = null;
            tableHierBinding = (JUCtrlHierBinding) (tableModel).getWrappedData();
            DCIteratorBinding dCIteratorBindin = null;
            dCIteratorBindin = tableHierBinding.getDCIteratorBinding();
            Iterator keySetIter = keySet.iterator();
            List firstKey = (List) keySetIter.next();
            oracle.jbo.Key key = (oracle.jbo.Key) firstKey.get(0);
            dCIteratorBindin.setCurrentRowWithKey(key.toStringFormat(true));
          }
}


