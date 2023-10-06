package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.Utils.ADFUtils;

import custom.lease.custom.view.beans.Utils.JSFUtils;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;


public class AddEditARSetup {

    public AddEditARSetup() {
    }

    ViewObject arSetVo = ADFUtils.findIterator("XxplARSetupVOIterator1").getViewObject();

    public String onClickSave() {
        String pageNavi=null;
        String navi=ADFContext.getCurrent().getPageFlowScope().get("mode").toString()==null?"abc":ADFContext.getCurrent().getPageFlowScope().get("mode").toString();
        String mode="Z";
        if(navi.equalsIgnoreCase("addSetup")||navi.equalsIgnoreCase("editSetup")){
            mode="S";
                if(isvalidARSetup(mode)){
                    JSFUtils.addFacesErrorMessage("Record already exists");
                    pageNavi=null;
                }else{
                    JSFUtils.addFacesInformationMessage("Information saved Successfully");
                    ADFUtils.findOperation("Commit").execute();
                    pageNavi="save"; 
                }
        }else{
            mode="T";
            if(isvalidTaxSetup(mode)){
                JSFUtils.addFacesErrorMessage("Record already exists");
                pageNavi=null;
            }else{
                JSFUtils.addFacesInformationMessage("Information saved Successfully");
                ADFUtils.findOperation("Commit").execute();
                pageNavi="save"; 
            }
        }
        
        return pageNavi;
    }
    
    
    
    public boolean isvalidARSetup(String mode){
        boolean flag=false;
        String orgId=arSetVo.getCurrentRow().getAttribute("BusinessUnitId")==null?"0":arSetVo.getCurrentRow().getAttribute("BusinessUnitId").toString();
        String chargeType=arSetVo.getCurrentRow().getAttribute("ChargeType")==null?"0":arSetVo.getCurrentRow().getAttribute("ChargeType").toString();
        String unitMethod=arSetVo.getCurrentRow().getAttribute("UnitMethod")==null?"0":arSetVo.getCurrentRow().getAttribute("UnitMethod").toString();
        String txSources=arSetVo.getCurrentRow().getAttribute("TxnSource")==null?"0":arSetVo.getCurrentRow().getAttribute("TxnSource").toString();
        String txType=arSetVo.getCurrentRow().getAttribute("TxnType")==null?"0":arSetVo.getCurrentRow().getAttribute("TxnType").toString();
//      String usage=arSetVo.getCurrentRow().getAttribute("Usage")==null?"0":arSetVo.getCurrentRow().getAttribute("Usage").toString();
        
        
        
//        System.out.println("orgId==>"+orgId);
//        System.out.println("chargeType==>"+chargeType);
//        System.out.println("unitMethod==>"+unitMethod);
//        System.out.println("txType==>"+txType);
//        System.out.println("txSources==>"+txSources);
        
        ViewCriteria vc=arSetVo.createViewCriteria();
        ViewCriteriaRow vcr=vc.createViewCriteriaRow();
        vcr.setAttribute("BusinessUnitId", orgId);
//        vc.addRow(vcr);
        vcr.setAttribute("ChargeType", chargeType);
//        vc.addRow(vcr);
        vcr.setAttribute("UnitMethod", unitMethod);
//        vc.addRow(vcr);
        vcr.setAttribute("TxnSource", txSources);
//        vc.addRow(vcr);
        vcr.setAttribute("TxnType", txType);
//        vc.addRow(vcr);
        vcr.setAttribute("Usage", mode);
        vc.addRow(vcr);
        arSetVo.applyViewCriteria(vc);
        arSetVo.executeQuery();
        System.out.println("==>"+arSetVo.getEstimatedRowCount());
        if(arSetVo.getEstimatedRowCount()>=1){
            flag=true;
        }else{
            flag=false;
        }
         
                    
        return flag;
    }
    


    public boolean isvalidTaxSetup(String mode){
        boolean flag=false;
        String orgId=arSetVo.getCurrentRow().getAttribute("BusinessUnitId")==null?"0":arSetVo.getCurrentRow().getAttribute("BusinessUnitId").toString();
        String unitCategory=arSetVo.getCurrentRow().getAttribute("UnitCategory")==null?"0":arSetVo.getCurrentRow().getAttribute("UnitCategory").toString();
        String taxCode=arSetVo.getCurrentRow().getAttribute("TaxCode")==null?"0":arSetVo.getCurrentRow().getAttribute("TaxCode").toString();
        
        
        System.out.println("orgId==>"+orgId);
        System.out.println("chargeType==>"+unitCategory);
        System.out.println("unitMethod==>"+taxCode);
        System.out.println("mode==>"+mode);
        
        ViewCriteria vc=arSetVo.createViewCriteria();
        ViewCriteriaRow vcr=vc.createViewCriteriaRow();
        vcr.setAttribute("BusinessUnitId", orgId);
        vcr.setAttribute("UnitCategory", unitCategory);
        vcr.setAttribute("TaxCode", taxCode);
        vcr.setAttribute("Usage", mode);
        vc.addRow(vcr);
        arSetVo.applyViewCriteria(vc);
        arSetVo.executeQuery();
        System.out.println("==>"+arSetVo.getEstimatedRowCount());
        if(arSetVo.getEstimatedRowCount()>=1){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }






    
}
