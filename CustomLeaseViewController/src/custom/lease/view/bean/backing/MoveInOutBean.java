package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import custom.lease.custom.view.beans.Utils.MailServices;
import custom.lease.custom.view.beans.Utils.MailTemplate;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class MoveInOutBean {
    private RichInputText approveComments;
    private RichDialog approvebind;
    private RichPopup p1;
    private RichPopup p2;
    private RichPopup p3;

    public MoveInOutBean() {
    }
    ViewObject headervo=ADFUtils.findIterator("xxplMoveInOutView1Iterator").getViewObject();
    
    ViewObject ApprovalTypeVO=ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();
    ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
    
    public void onClickSave(ActionEvent actionEvent) {
        Row r=headervo.getCurrentRow();
             String  MoveInOutNo="";
             String  primarykey=r.getAttribute("MoveIoId")==null?"":r.getAttribute("MoveIoId").toString();
                 String  IoFlag=r.getAttribute("IoFlag")==null?"":r.getAttribute("IoFlag").toString();
             String  addeditscope=ADFContext.getCurrent().getPageFlowScope().get("navigation").toString();
              if(addeditscope.equalsIgnoreCase("add"))
              {
                      
                   if(IoFlag.equalsIgnoreCase("I")){
                MoveInOutNo="MI-".concat(primarykey);
                r.setAttribute("MoveIoNumber",MoveInOutNo);
                            r.setAttribute("FuncId","13");
                        }
                        else
                        {
                         MoveInOutNo="MO-".concat(primarykey);
                r.setAttribute("MoveIoNumber",MoveInOutNo);
                            r.setAttribute("FuncId","14");
                        }
                
                        
              }
                  
                  String buttonType = actionEvent.getComponent().getAttributes().get("buttonType")!=null ? actionEvent.getComponent().getAttributes().get("buttonType").toString() : null;
             if(buttonType.equals("save")){
              ADFUtils.findOperation("Commit").execute();
              JSFUtils.addFacesInformationMessage("Information saved successfully!!!");
                        }
    }

    public void onClickAttachment(ActionEvent actionEvent) {
        String movein =headervo.getCurrentRow().getAttribute("MoveIoId")==null?"0":headervo.getCurrentRow().getAttribute("MoveIoId").toString();
        String fun_id =headervo.getCurrentRow().getAttribute("FuncId")==null?"0":headervo.getCurrentRow().getAttribute("FuncId").toString();
        ADFContext.getCurrent().getPageFlowScope().put("FuncId",fun_id);
        ADFContext.getCurrent().getPageFlowScope().put("FuncRefId",movein);
    }
    
//    public String onClcikApprove() {
//      
//    }
    public void save(){ 
        Row r=headervo.getCurrentRow();
             String  MoveInOutNo="";
             String  primarykey=r.getAttribute("MoveIoId")==null?"":r.getAttribute("MoveIoId").toString();
                 String  IoFlag=r.getAttribute("IoFlag")==null?"":r.getAttribute("IoFlag").toString();
             String  addeditscope=ADFContext.getCurrent().getPageFlowScope().get("navigation").toString();
              if(addeditscope.equalsIgnoreCase("add"))
              {
                      
                   if(IoFlag.equalsIgnoreCase("I")){
                MoveInOutNo="MI-".concat(primarykey);
                r.setAttribute("MoveIoNumber",MoveInOutNo);
                            r.setAttribute("FuncId","13");
                        }
                        else
                        {
                         MoveInOutNo="MO-".concat(primarykey);
                r.setAttribute("MoveIoNumber",MoveInOutNo);
                            r.setAttribute("FuncId","14");
                        }
                
                        
              }
                  
//              String buttonType = r.getAttribute("buttonType")!=null ?"": r.getAttribute("buttonType").toString();
//             if(buttonType.equals("save")){
              ADFUtils.findOperation("Commit").execute();
//              JSFUtils.addFacesInformationMessage("Information saved successfully!!!");
//              }
    }
    
    public String onClickSubmit(ActionEvent actionEvent) {
        ArrayList <String> ls = new ArrayList<String>();
        String navi=null;
        ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "MOVE_OUT_APR_TYPE");
        ApprovalTypeVO.executeQuery();
        String approveType=ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
        String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"14":headervo.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=headervo.getCurrentRow().getAttribute("MoveIoId")==null?"0":headervo.getCurrentRow().getAttribute("MoveIoId").toString();
        String Attribute1_Org=headervo.getCurrentRow().getAttribute("OrgId")==null?"0":headervo.getCurrentRow().getAttribute("OrgId").toString();
        String P_ATTRIBUTE2=null;
        String P_ATTRIBUTE3=null;
        String P_ATTRIBUTE4=null;
        String P_ATTRIBUTE5=null;
        String BUName=null;
        String type=
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString()==null?"O":
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString();
        System.err.println("typeeeee=="+type);
        if(type.equalsIgnoreCase("I")){
            P_FUNC_ID="13";    
        }else{
            P_FUNC_ID="14";
        }
        String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prism@omniyat.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
            ccAddress="prism@omniyat.com";
        }
//       String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String moveOutNumber=headervo.getCurrentRow().getAttribute("MoveIoNumber")==null?"MOV-000":headervo.getCurrentRow().getAttribute("MoveIoNumber").toString();
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+Attribute1_Org;
        }
        ArrayList <String> list = new ArrayList<String>();
        // Submit
//        if(type.equalsIgnoreCase("I")){
//            navi=onClickAutoApproval();
//            if(navi.equalsIgnoreCase("SAVE")){
//                return "toSave";
//            }else{
//                return null;
//            }
//        }else{
            if(approveType.equalsIgnoreCase("AUTO")){
                navi=onClickAutoApproval();
                if(navi.equalsIgnoreCase("SAVE")){
                    return "toSave";
                }else{
                    return null;
                }
            }else if(approveType.equalsIgnoreCase("ADF")){
                list=PackageCalling.invokeSubmit(
                                    P_FUNC_ID, 
                                    P_REF_ID, 
                                    BaseDetail.initialLevel, 
                                    BaseDetail.MOVEINOUT_TABLE, 
                                    BaseDetail.MOVEINOUT_STATUS_COLUMN, 
                                    BaseDetail.MOVEINOUT_ID_COLUMN, 
                                    BaseDetail.initialAmount, 
                                    Attribute1_Org, 
                                    P_ATTRIBUTE2, 
                                    P_ATTRIBUTE3, 
                                    P_ATTRIBUTE4, 
                                    P_ATTRIBUTE5);
                if(list.get(3)!=null){
//                    String toAddress=list.get(3)==null?"dineshkumar.p@4iapps.com":list.get(3).toString();
//                    String subject=BaseDetail.MoveIOSubjects+" Submitted for Approval "+"Move Out Number:"+moveOutNumber;
//                    String mailContent=MailTemplate.cancellationMailContent("Move Out", moveOutNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//                    String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                            if(type.equalsIgnoreCase("I")){
                    JSFUtils.addFacesInformationMessage("Move In Submitted for Approval !!!");            
                            }else{
                    JSFUtils.addFacesInformationMessage("Move Out Submitted for Approval !!!");
                            }
//                    JSFUtils.addFacesErrorMessage(mailFlag);
                    return "toSave";
                }else{
//                    JSFUtils.addFacesErrorMessage("Error: Mail Notification Missing");
                    JSFUtils.addFacesErrorMessage("Mail ID          :" +list.get(3));
                    JSFUtils.addFacesErrorMessage("Error Code       :" +list.get(5));
                    JSFUtils.addFacesErrorMessage("Error Message    :" +list.get(6));
                    JSFUtils.addFacesErrorMessage("Status           :" +list.get(8));
                    return null;
                }
            }else if(approveType.equalsIgnoreCase("PCS")){
                return null;
            }else{
                return null;
            }
//        }
    }
    
    
    
    public String onClickAutoApproval(){
        this.save();
        ViewObject hdrVo = ADFUtils.findIterator("xxplMoveInOutView1Iterator").getViewObject();
        Row currRow = hdrVo.getCurrentRow();
        String funcId = currRow.getAttribute("FuncId")==null?"0":currRow.getAttribute("FuncId").toString();
        System.err.println(funcId);
        String moveId = currRow.getAttribute("MoveIoId")==null?"0":currRow.getAttribute("MoveIoId").toString();
        System.err.println(moveId);
        ArrayList <String> returnList = new ArrayList<String>();
        returnList = PackageCalling.approvalUpdateResponce(funcId, moveId, BaseDetail.MOVEINOUT_TABLE); 
        System.err.println(returnList);
            if("0".equals(returnList.get(0))){
                JSFUtils.addFacesInformationMessage("Approved successfully !"); 
                return "SAVE";
            }else{
                JSFUtils.addFacesErrorMessage("Error : "+returnList.get(1));
                return "ERROR";
            }
    }
    

    public void setApproveComments(RichInputText approveComments) {
        this.approveComments = approveComments;
    }

    public RichInputText getApproveComments() {
        return approveComments;
    }

    public void onClickApprove(ActionEvent actionEvent) {
        String Comments=approveComments.getValue()==null?"Approved":approveComments.getValue().toString();
        String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"14":headervo.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=headervo.getCurrentRow().getAttribute("MoveIoId")==null?"0":headervo.getCurrentRow().getAttribute("MoveIoId").toString();
        String BUName=null;
        String type=
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString()==null?"O":
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString();
        if(type.equalsIgnoreCase("I")){
            P_FUNC_ID="13";    
        }else{
            P_FUNC_ID="14";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prism@omniyat.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
            ccAddress="prism@omniyat.com";
        }
        //String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String moveOutNumber=headervo.getCurrentRow().getAttribute("MoveIoNumber")==null?"MOV-000":headervo.getCurrentRow().getAttribute("MoveIoNumber").toString();
        String userGroupID=headervo.getCurrentRow().getAttribute("UserGrpId")==null?"0":headervo.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=headervo.getCurrentRow().getAttribute("FlowLevel")==null?"0":headervo.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=headervo.getCurrentRow().getAttribute("FlowWith")==null?"0":headervo.getCurrentRow().getAttribute("FlowWith").toString();
        String Attribute1_Org=headervo.getCurrentRow().getAttribute("OrgId")==null?"0":headervo.getCurrentRow().getAttribute("OrgId").toString();
        ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+Attribute1_Org;
        }
        String ArStatus="APPROVE";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
            JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, 
                                                             P_REF_ID, 
                                                             userGroupID, 
                                                             levelNo, 
                                                             approveId, 
                                                             Comments, ArStatus, P_FWD_TO, 
                                                             BaseDetail.MOVEINOUT_TABLE, 
                                                             BaseDetail.MOVEINOUT_STATUS_COLUMN, 
                                                             BaseDetail.MOVEINOUT_ID_COLUMN, 
                                                             BaseDetail.initialAmount);
            if(list.get(0)!=null){
                        //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Approved")) {
                     JSFUtils.addFacesInformationMessage("Move Out Approved Successfully");
                }else{
                     JSFUtils.addFacesInformationMessage("Move Out Approved Successfully and resubmitted to next level user");
                     String toAddress=list.get(2)==null?"dineshkumar.p@4iapps.com":list.get(2).toString();
                     String subject=BaseDetail.MoveIOSubjects+" Submitted for Approval. "+"Move Out Number:"+moveOutNumber;
                     ccAddress=ccAddress+","+userlogin;
                     String mailContent=MailTemplate.priceListMailContent("Move Out", moveOutNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
                     String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                     JSFUtils.addFacesErrorMessage(mailFlag);
                }
            }else{
                     JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                     JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                     JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));                
            }
        }
    }

    public void onClickRejected(ActionEvent actionEvent) {
        String Comments=approveComments.getValue()==null?"Approved":approveComments.getValue().toString();
        String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"14":headervo.getCurrentRow().getAttribute("FuncId").toString();
        String P_REF_ID=headervo.getCurrentRow().getAttribute("MoveIoId")==null?"0":headervo.getCurrentRow().getAttribute("MoveIoId").toString();
        String BUName=null;
        String type=
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString()==null?"O":
            ADFContext.getCurrent().getSessionScope().get("MoveInOut").toString();
        if(type.equalsIgnoreCase("I")){
            P_FUNC_ID="13";    
        }else{
            P_FUNC_ID="14";
        }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prism@omniyat.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")||ccAddress.equalsIgnoreCase("Projects")|ccAddress.equalsIgnoreCase("finance")){
            ccAddress="prism@omniyat.com";
        }
        //String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
        String moveOutNumber=headervo.getCurrentRow().getAttribute("MoveIoNumber")==null?"MOV-000":headervo.getCurrentRow().getAttribute("MoveIoNumber").toString();
        String userGroupID=headervo.getCurrentRow().getAttribute("UserGrpId")==null?"0":headervo.getCurrentRow().getAttribute("UserGrpId").toString();
        String levelNo=headervo.getCurrentRow().getAttribute("FlowLevel")==null?"0":headervo.getCurrentRow().getAttribute("FlowLevel").toString();
        String approveId=headervo.getCurrentRow().getAttribute("FlowWith")==null?"0":headervo.getCurrentRow().getAttribute("FlowWith").toString();
        String Attribute1_Org=headervo.getCurrentRow().getAttribute("OrgId")==null?"0":headervo.getCurrentRow().getAttribute("OrgId").toString();
        ViewObject GetDataVO=ADFUtils.findIterator("GetID_Rovo1Iterator").getViewObject();
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "ORG_NAME");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "xxstg_organizations");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "ORG_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", Attribute1_Org);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            BUName=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();    
        }else{
            BUName="Error in BU and BU ID is "+Attribute1_Org;
        }
        String ArStatus="REJECT";
        String P_FWD_TO=null;
        ArrayList <String> list = new ArrayList<String>();
        if(approveId.equalsIgnoreCase("0")){
            JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
        }else{
            list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, 
                                                             P_REF_ID, 
                                                             userGroupID, 
                                                             levelNo, 
                                                             approveId, 
                                                             Comments, ArStatus, P_FWD_TO, 
                                                             BaseDetail.MOVEINOUT_TABLE, 
                                                             BaseDetail.MOVEINOUT_STATUS_COLUMN, 
                                                             BaseDetail.MOVEINOUT_ID_COLUMN, 
                                                             BaseDetail.initialAmount);
            if(list.get(0)!=null){
                        //- checking approve or pending
                if(list.get(0).equalsIgnoreCase("Approved")) {
                     JSFUtils.addFacesInformationMessage("Move Out Approved Successfully");
                }else{
                     String toAddress=list.get(2)==null?"dineshkumar.p@4iapps.com":list.get(2).toString();
                     String subject=BaseDetail.MoveIOSubjects+" Rejected. "+"Move Out Number:"+moveOutNumber;
                     ccAddress=ccAddress+","+userlogin;
                     String mailContent=MailTemplate.priceListMailContent("Move Out", moveOutNumber, BUName, BaseDetail.SysDate(), ccAddress, "Rejected");
                     String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                     JSFUtils.addFacesErrorMessage(mailFlag);
                }
            }else{
                JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));                
            }
        }
    }

    public void setApprovebind(RichDialog approvebind) {
        this.approvebind = approvebind;
    }

    public RichDialog getApprovebind() {
        return approvebind;
    }

    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }

    public void setP2(RichPopup p2) {
        this.p2 = p2;
    }

    public RichPopup getP2() {
        return p2;
    }
    

    public void onClickp1Cancel(ActionEvent actionEvent) {
        this.getP1().cancel();
    }

    public void onClickp2Cancel(ActionEvent actionEvent) {
        this.getP2().cancel();
    }


    public void onClickAprovalHis(ActionEvent actionEvent) {

        String fuctionid=headervo.getCurrentRow().getAttribute("FuncId")==null?"14":headervo.getCurrentRow().getAttribute("FuncId").toString();
        String plid=headervo.getCurrentRow().getAttribute("MoveIoId")==null?"0":headervo.getCurrentRow().getAttribute("MoveIoId").toString();
        ADFContext.getCurrent().getSessionScope().put("plid",plid);
        ADFContext.getCurrent().getSessionScope().put("fuctionid",fuctionid);
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        this.getP3().show(hints);
    }

    public void setP3(RichPopup p3) {
        this.p3 = p3;
    }

    public RichPopup getP3() {
        return p3;
    }
    
    
    public boolean getApprovalUser() {
        boolean flag = false;
        String loginId=null;
        String flowWith ="0";                        
        String userLogin ="-99";
        System.err.println(headervo.getEstimatedRowCount() +"--Row count");
//        if(headervo.getEstimatedRowCount() > 0){
//            if(headervo.getCurrentRow().getAttribute("FlowWith")!=null){
//                flowWith =headervo.getCurrentRow().getAttribute("FlowWith").toString();                         
//            }  
//        }
             
        flowWith=JSFUtils.resolveExpression("#{bindings.FlowWith.inputValue}")==null?"0":JSFUtils.resolveExpression("#{bindings.FlowWith.inputValue}").toString();
        
        
        if(ADFContext.getCurrent().getSessionScope().get("userName")!=null){
            userLogin =ADFContext.getCurrent().getSessionScope().get("userName").toString();
        }
        
        System.err.println("Tx flowWith ==>"+flowWith );
        System.err.println("Saas userLogin==>"+userLogin);
        GetDataVO.setNamedWhereClauseParam("BV_SELECT_COLUMN", "USER_ID");
        GetDataVO.setNamedWhereClauseParam("BV_TABLENAME", "XXFND_USER");
        GetDataVO.setNamedWhereClauseParam("BV_WHERE_COLUMN", "USER_EMAIL_ID");
        GetDataVO.setNamedWhereClauseParam("BV_VALUE", userLogin);
        GetDataVO.executeQuery();
        if(GetDataVO.getEstimatedRowCount()==1){
            loginId=GetDataVO.first().getAttribute("Id")==null?"0":GetDataVO.first().getAttribute("Id").toString();
            System.err.println("db loginId==>"+loginId);
        }else{
            loginId="0";
        }
        if (flowWith != null && loginId != null) {
            if (flowWith.equalsIgnoreCase(loginId)) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }
}
