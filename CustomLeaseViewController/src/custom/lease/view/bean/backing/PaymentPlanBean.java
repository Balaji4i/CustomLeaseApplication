package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import custom.lease.custom.view.beans.PackageUtils.PackageCalling;

import custom.lease.custom.view.beans.Utils.MailServices;
import custom.lease.custom.view.beans.Utils.MailTemplate;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

public class PaymentPlanBean {
    private RichInputText approveComments;
    private RichPopup p8;

    public PaymentPlanBean() {
    }
   ViewObject headervo=ADFUtils.findIterator("XxplPaymentPlanHeaderView1Iterator").getViewObject();
    ViewObject linevo=ADFUtils.findIterator("XxplPaymentPlanLineView1Iterator").getViewObject();
    ViewObject ApprovalTypeVO=ADFUtils.findIterator("AppovalTypeROVO1Iterator").getViewObject();
    public void onClickSave(ActionEvent actionEvent) {
     Row r=headervo.getCurrentRow();
     String  PaymentPlanNo="";
     String  primarykey=r.getAttribute("MsHdrId")==null?"":r.getAttribute("MsHdrId").toString();
     String  addeditscope=ADFContext.getCurrent().getPageFlowScope().get("navigation").toString();
      if(addeditscope.equalsIgnoreCase("add"))
      {
        PaymentPlanNo="MS-".concat(primarykey);
        r.setAttribute("MilestoneNumber",PaymentPlanNo);
        r.setAttribute("FuncId",4);
        r.setAttribute("Status","Draft");
      }
      ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Information saved successfully!!!");
    
    }

    public void onClickApprove(ActionEvent actionEvent) {
//        Row r=headervo.getCurrentRow();
//        r.setAttribute("Status","Approved");
//        String Comments="TEST";
         String Comments=approveComments.getValue()==null?"Approved":approveComments.getValue().toString();
         String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"5":headervo.getCurrentRow().getAttribute("FuncId").toString();
         String P_REF_ID=headervo.getCurrentRow().getAttribute("MsHdrId")==null?"0":headervo.getCurrentRow().getAttribute("MsHdrId").toString();  
        String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")){
                    ccAddress="prasenjit.d@4iapps.com";
                }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
                if(userlogin.equalsIgnoreCase("api")){
                    userlogin="prasenjit.d@4iapps.com";
                }
        String PriceNumber=headervo.getCurrentRow().getAttribute("MilestoneNumber")==null?"Error-000":headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
                String userGroupID=headervo.getCurrentRow().getAttribute("UserGrpId")==null?"0":headervo.getCurrentRow().getAttribute("UserGrpId").toString();
                String levelNo=headervo.getCurrentRow().getAttribute("FlowLevel")==null?"0":headervo.getCurrentRow().getAttribute("FlowLevel").toString();
                String approveId=headervo.getCurrentRow().getAttribute("FlowWith")==null?"0":headervo.getCurrentRow().getAttribute("FlowWith").toString();
                String BUName=headervo.getCurrentRow().getAttribute("BuNameTrans")==null?"Error BU":headervo.getCurrentRow().getAttribute("BuNameTrans").toString();
//                String BUName="ABCD";
        String ArStatus="APPROVE";
                String P_FWD_TO=null;
                ArrayList <String> list = new ArrayList<String>();
                if(approveId.equalsIgnoreCase("0")){
                    JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
                }
        else{
                    list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, P_REF_ID, userGroupID, levelNo, approveId, 
                                                             Comments, ArStatus, P_FWD_TO, 
                                                             BaseDetail.MilestoneHdrTableName, 
                                                             BaseDetail.MilestoneStatusColumnName, 
                                                             BaseDetail.MilestonePkColumnName, 
                                                             BaseDetail.initialAmount);
                    if(list.get(0)!=null){
                        //- checking approve or pending
                        if(list.get(0).equalsIgnoreCase("Approved")) {
                            JSFUtils.addFacesInformationMessage("Payment Plan Approved Successfully");
                        }else{
                            JSFUtils.addFacesInformationMessage("Payment Plan Approved Successfully and resubmitted to next level user");
//                            String toAddress=list.get(2)==null?"prasenjit.d@4iapps.com":list.get(2).toString();
//                            String subject=BaseDetail.PaymentPlanSubjects+" Submitted for Approval "+"Payment Plan Number"+headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
//                            ccAddress=ccAddress+","+userlogin;
//                            String mailContent=MailTemplate.priceListMailContent("Payment Plan", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//                            String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
//                            JSFUtils.addFacesErrorMessage(mailFlag);
                        }
                    }else{
                        JSFUtils.addFacesErrorMessage("Error in Approval Status. Please Contact Administrator");
                        JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                        JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));
                    }
        }
    }
    public void onClickRejected(ActionEvent actionEvent) {
//        Row r=headervo.getCurrentRow();
//        r.setAttribute("Status","Rejected");
               String Comments=approveComments.getValue()==null?"Rejected":approveComments.getValue().toString();
         String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"5":headervo.getCurrentRow().getAttribute("FuncId").toString();
         String P_REF_ID=headervo.getCurrentRow().getAttribute("MsHdrId")==null?"0":headervo.getCurrentRow().getAttribute("MsHdrId").toString();  
        String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
        if(ccAddress.equalsIgnoreCase("api")){
                    ccAddress="prasenjit.d@4iapps.com";
                }
        String userlogin = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
                if(userlogin.equalsIgnoreCase("api")){
                    userlogin="prasenjit.d@4iapps.com";
                }
        String PriceNumber=headervo.getCurrentRow().getAttribute("MilestoneNumber")==null?"Error-000":headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
                String userGroupID=headervo.getCurrentRow().getAttribute("UserGrpId")==null?"0":headervo.getCurrentRow().getAttribute("UserGrpId").toString();
                String levelNo=headervo.getCurrentRow().getAttribute("FlowLevel")==null?"0":headervo.getCurrentRow().getAttribute("FlowLevel").toString();
                String approveId=headervo.getCurrentRow().getAttribute("FlowWith")==null?"0":headervo.getCurrentRow().getAttribute("FlowWith").toString();
              String BUName=headervo.getCurrentRow().getAttribute("BuNameTrans")==null?"Error BU":headervo.getCurrentRow().getAttribute("BuNameTrans").toString();
//                String BUName="ABCD";
        String ArStatus="REJECT";
                String P_FWD_TO=null;
                ArrayList <String> list = new ArrayList<String>();
                if(approveId.equalsIgnoreCase("0")){
                    JSFUtils.addFacesErrorMessage("Error in Aproval configuration. Please Contact Administrator");
                }
        else{
                    list=PackageCalling.invokeUpdateResponse(P_FUNC_ID, P_REF_ID, userGroupID, levelNo, approveId, 
                                                             Comments, ArStatus, P_FWD_TO, 
                                                             BaseDetail.MilestoneHdrTableName, 
                                                             BaseDetail.MilestoneStatusColumnName, 
                                                             BaseDetail.MilestonePkColumnName, 
                                                             BaseDetail.initialAmount);
                    if(list.get(0)!=null){
                        //- checking approve or pending
                        if(list.get(0).equalsIgnoreCase("Rejected")) {
                            JSFUtils.addFacesInformationMessage("Payment Plan Rejected Successfully");
                        }else{
                            JSFUtils.addFacesInformationMessage("Payment Plan Rejected Successfully !!!");
//                            String toAddress=list.get(2)==null?"prasenjit.d@4iapps.com":list.get(2).toString();
//                            String subject=BaseDetail.PaymentPlanSubjects+" Submitted for Approval "+"Payment Plan Number"+headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
//                            ccAddress=ccAddress+","+userlogin;
//                            String mailContent=MailTemplate.priceListMailContent("Payment Plan", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Reject");
//                            String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
//                            JSFUtils.addFacesErrorMessage(mailFlag);
                        }
                    }else{
                        JSFUtils.addFacesErrorMessage("Error in Reject Status. Please Contact Administrator");
                        JSFUtils.addFacesErrorMessage("Error Code: "+list.get(3));
                        JSFUtils.addFacesErrorMessage("Error Message: "+list.get(4));
                    }
        }
    }

    public void onClickRevised(ActionEvent actionEvent) {
        
        
        Row r=headervo.getCurrentRow();
        // System.err.println("msid"+r.getAttribute("MsHdrId"));
        String num = r.getAttribute("MsHdrId")==null?"":r.getAttribute("MsHdrId").toString();
//       
//        OperationBinding op=ADFUtils.findOperation("callrevision");
//        op.getParamsMap().put("id",num);
//        op.getParamsMap().put("revname",revname);
//        String result=op.execute().toString();
//        // System.err.println("result-->"+result);
        
        String result=PackageCalling.createRevisionMilestone(num);
        // System.err.println("RESULT-->"+result);
        if(result.equalsIgnoreCase("Success")){
        
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Revisied sucessfully!!!");
        }
       else
        {
            JSFUtils.addFacesInformationMessage("Failed !!!");
        }
    }

    public void onClickSubmit(ActionEvent actionEvent) {
//    float percentagecount=0;
//    Long rowcount=linevo.getEstimatedRowCount();
//    if(rowcount>0){
//    RowSetIterator itr=linevo.createRowSetIterator(null);
//    while(itr.hasNext())
//    {
//        Row r=itr.next();
//        percentagecount=percentagecount+Float.parseFloat(r.getAttribute("InstallmentPct").toString());
//    }
//    itr.closeRowSetIterator();
//    // System.err.println("Print-->"+percentagecount);
//    if(percentagecount<100)
//    {
//        JSFUtils.addFacesErrorMessage("Installment percentage should be 100.Sum of percentage is "+percentagecount+"");
//    }else if(percentagecount>100)
//    {
//        JSFUtils.addFacesErrorMessage("Installment percentage should not exceed 100.Sum of percentage is "+percentagecount+"");
//    }else if(percentagecount==100)
//    {
    //start
    Row r=headervo.getCurrentRow();
    //        r.setAttribute("Status","Approved");
    //        ADFUtils.findOperation("Commit").execute();
    ApprovalTypeVO.setNamedWhereClauseParam("BV_APPR_LOOKUP_NAME", "PAYMENT_PLAN_APR_TYPE");
    ApprovalTypeVO.executeQuery();
    String approveType=ApprovalTypeVO.first().getAttribute("LookupAddlValue")==null?"AUTO":ApprovalTypeVO.first().getAttribute("LookupAddlValue").toString();
    String P_FUNC_ID=headervo.getCurrentRow().getAttribute("FuncId")==null?"4":headervo.getCurrentRow().getAttribute("FuncId").toString();
           String P_REF_ID=headervo.getCurrentRow().getAttribute("MsHdrId")==null?"0":headervo.getCurrentRow().getAttribute("MsHdrId").toString();
           String Attribute1_Org=headervo.getCurrentRow().getAttribute("OrgId")==null?"0":headervo.getCurrentRow().getAttribute("OrgId").toString();
           String P_ATTRIBUTE2=null;
           String P_ATTRIBUTE3=null;
           String P_ATTRIBUTE4=null;
           String P_ATTRIBUTE5=null;
           String ccAddress=headervo.getCurrentRow().getAttribute("CreatedBy")==null?"prasenjit.d@4iapps.com":headervo.getCurrentRow().getAttribute("CreatedBy").toString();
           // System.err.println(P_FUNC_ID+"---"+P_REF_ID+"--"+Attribute1_Org+"--"+ccAddress);
    if(ccAddress.equalsIgnoreCase("api")){
                ccAddress="prasenjit.d@4iapps.com";
            }
    // String ccAddress = ADFContext.getCurrent().getSessionScope().get("userName")==null?"API":ADFContext.getCurrent().getSessionScope().get("userName").toString();
    String PriceNumber=headervo.getCurrentRow().getAttribute("MilestoneNumber")==null?"Error-000":headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
    String BUName=headervo.getCurrentRow().getAttribute("BuNameTrans")==null?"Error BU":headervo.getCurrentRow().getAttribute("BuNameTrans").toString();
    //        String BUName="ABC";
    ArrayList <String> list = new ArrayList<String>();
    // System.err.println("approveType=="+approveType);
//        approveType="ADF";
    if(approveType.equalsIgnoreCase("AUTO")){
                ADFUtils.setBoundAttributeValue("Status", "Approved");
                ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Information Saved Successfully and Approved");
    // auto if end 
    }
    else if (approveType.equalsIgnoreCase("ADF")){
                list=PackageCalling.invokeSubmit(
                                P_FUNC_ID, 
                                P_REF_ID, 
                                BaseDetail.initialLevel, 
                                BaseDetail.MilestoneHdrTableName, 
                                BaseDetail.MilestoneStatusColumnName, 
                                BaseDetail.MilestonePkColumnName, 
                                BaseDetail.initialAmount, 
                                Attribute1_Org, 
                                P_ATTRIBUTE2, 
                                P_ATTRIBUTE3, 
                                P_ATTRIBUTE4, 
                                P_ATTRIBUTE5
                                );
                if(list.get(3)!=null){
//                String toAddress=list.get(3)==null?"prasenjit.d@4iapps.com":list.get(3).toString();
//                String subject=BaseDetail.PaymentPlanSubjects+" Submitted for Approval "+"Payment Plan Number"+headervo.getCurrentRow().getAttribute("MilestoneNumber").toString();
//                String mailContent=MailTemplate.priceListMailContent("Payment Plan", PriceNumber, BUName, BaseDetail.SysDate(), ccAddress, "Submitted for Approval");
//                String  mailFlag=MailServices.sendMailWithoutAttachment(toAddress, ccAddress, "NULL", subject, mailContent);
                JSFUtils.addFacesInformationMessage("Payment Plan Submitted for Approval");
//                JSFUtils.addFacesErrorMessage(mailFlag);
                    
                }else{
                    JSFUtils.addFacesErrorMessage("Error: Mail Notification Missing");
                    JSFUtils.addFacesErrorMessage("Mail ID          :" +list.get(3));
                    JSFUtils.addFacesErrorMessage("Error Code       :" +list.get(5));
                    JSFUtils.addFacesErrorMessage("Error Message    :" +list.get(6));
                    JSFUtils.addFacesErrorMessage("Status           :" +list.get(8));
                }
                
    }else if(approveType.equalsIgnoreCase("PCS")){
            
        }else{
            JSFUtils.addFacesErrorMessage("Approved Type is missing."); 
        }

    
    //end   
//    }
//    else
//    {
//    JSFUtils.addFacesErrorMessage("Please create payment plan");
//    }
        
   
//    }
    }

    public void setApproveComments(RichInputText approveComments) {
        this.approveComments = approveComments;
    }

    public RichInputText getApproveComments() {
        return approveComments;
    }

    public void setP8(RichPopup p8) {
        this.p8 = p8;
    }

    public RichPopup getP8() {
        return p8;
    }

    public void onClickApprovalHistory(ActionEvent actionEvent) {
        String msHdrid=JSFUtils.resolveExpression("#{bindings.MsHdrId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.MsHdrId.inputValue}").toString();
        String fuctionid=JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}")==null?"":JSFUtils.resolveExpression("#{bindings.FuncId.inputValue}").toString();
        ADFContext.getCurrent().getSessionScope().put("msHdrid",msHdrid);
        ADFContext.getCurrent().getSessionScope().put("fuctionid",fuctionid);
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        this.getP8().show(hints);
    }
}
