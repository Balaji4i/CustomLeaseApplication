package custom.lease.custom.view.beans.Utils;

public class MailTemplate {
    public MailTemplate() {
        super();
    }
    
//https://html5-editor.net/
/*****************************************/
//<p>Hi,</p>
//<p>Good Day..!</p>
//<p>Price List has been submitted for the Approval. Kindly Review and&nbsp;do the necessary action.</p>
//<p>&nbsp;</p>
//<p><strong>Price List number (CN-1141).</strong></p>
//<table style="height: 84px; width: 777.91px; border-color: blue; float: left;" border="1">
//<tbody>
//<tr style="height: 25px;">
//<td style="width: 162px; text-align: center; height: 25px;"><span style="color: #0000ff;"><strong>Business Unit</strong></span></td>
//<td style="width: 95px; text-align: center; height: 25px;"><span style="color: #0000ff;"><strong>Date </strong></span></td>
//<td style="width: 209px; text-align: center; height: 25px;"><span style="color: #0000ff;"><strong>Submitted By</strong></span></td>
//<td style="width: 192.91px; text-align: center; height: 25px;"><span style="color: #0000ff;"><strong>Status</strong></span></td>
//</tr>
//<tr style="height: 34px;">
//<td style="width: 162px; height: 34px;"><strong>Opus</strong></td>
//<td style="width: 95px; height: 34px;"><strong>23-OCT-19</strong></td>
//<td style="width: 209px; height: 34px;"><strong>dilini.fernando@omniyat.com</strong></td>
//<td style="width: 192.91px; height: 34px;"><strong>Submitted for Approval</strong></td>
//</tr>
//</tbody>
//</table>
//<p>&nbsp;</p>
//<p>&nbsp;</p>
//<p>&nbsp;</p>
//<p>&nbsp;</p>
//<p>Regards</p>
//<p>Lease Alert Message</p>
//<p><span style="color: #ff0000;">Note: This is a system generated mail so please do not reply to this mail</span></p>





/*****************************************/
/******************************
 * 
 * Price List
 * 
 * ****************************/

public static String priceListMailContent(String ScreenName, String ScreenNumber, String BUName, String Sysdate, String CreatedBy, String Status){
        String html=
            "<p>Hi,</p>\n" + 
            "<p>Good Day..!</p>\n" + 
            "<p>"+ScreenName+" has been submitted for the Approval. Kindly Review and&nbsp;do the necessary action.</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p><strong>"+ScreenName+"+  Number : "+ScreenNumber+".</strong></p>\n" + 
            "<table style=\"height: 84px; width: 777.91px; border-color: blue; float: left;\" border=\"1\">\n" + 
            "<tbody>\n" + 
            "<tr style=\"height: 25px;\">\n" + 
            "<td style=\"width: 162px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Business Unit</strong></span></td>\n" + 
            "<td style=\"width: 95px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Date </strong></span></td>\n" + 
            "<td style=\"width: 209px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Submitted By</strong></span></td>\n" + 
            "<td style=\"width: 192.91px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Status</strong></span></td>\n" + 
            "</tr>\n" + 
            "<tr style=\"height: 34px;\">\n" + 
            "<td style=\"width: 162px; height: 34px;\"><strong>"+BUName+"</strong></td>\n" + 
            "<td style=\"width: 95px; height: 34px;\"><strong>"+Sysdate+"</strong></td>\n" + 
            "<td style=\"width: 209px; height: 34px;\"><strong>"+CreatedBy+"</strong></td>\n" + 
            "<td style=\"width: 192.91px; height: 34px;\"><strong>"+Status+"</strong></td>\n" + 
            "</tr>\n" + 
            "</tbody>\n" + 
            "</table>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>Regards</p>\n" + 
            "<p>Lease Alert Message</p>\n" + 
            "<p><span style=\"color: #ff0000;\">Note: This is a system generated mail so please do not reply to this mail</span></p>\n";
                
return html; 
}

/******************************
 * 
 * Mile Stone
 * 
 * ****************************/

public static String mileStonrMailContent(String ScreenName, String ScreenNumber, String BUName, String Sysdate, String CreatedBy, String Status){
        String html=
            "<p>Hi,</p>\n" + 
            "<p>Good Day..!</p>\n" + 
            "<p>"+ScreenName+" has been submitted for the Approval. Kindly Review and&nbsp;do the necessary action.</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p><strong>"+ScreenName+"+  Number : "+ScreenNumber+".</strong></p>\n" + 
            "<table style=\"height: 84px; width: 777.91px; border-color: blue; float: left;\" border=\"1\">\n" + 
            "<tbody>\n" + 
            "<tr style=\"height: 25px;\">\n" + 
            "<td style=\"width: 162px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Business Unit</strong></span></td>\n" + 
            "<td style=\"width: 95px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Date </strong></span></td>\n" + 
            "<td style=\"width: 209px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Submitted By</strong></span></td>\n" + 
            "<td style=\"width: 192.91px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Status</strong></span></td>\n" + 
            "</tr>\n" + 
            "<tr style=\"height: 34px;\">\n" + 
            "<td style=\"width: 162px; height: 34px;\"><strong>"+BUName+"</strong></td>\n" + 
            "<td style=\"width: 95px; height: 34px;\"><strong>"+Sysdate+"</strong></td>\n" + 
            "<td style=\"width: 209px; height: 34px;\"><strong>"+CreatedBy+"</strong></td>\n" + 
            "<td style=\"width: 192.91px; height: 34px;\"><strong>"+Status+"</strong></td>\n" + 
            "</tr>\n" + 
            "</tbody>\n" + 
            "</table>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>&nbsp;</p>\n" + 
            "<p>Regards</p>\n" + 
            "<p>Lease Alert Message</p>\n" + 
            "<p><span style=\"color: #ff0000;\">Note: This is a system generated mail so please do not reply to this mail</span></p>\n";
                
return html; 
}

    /******************************
     * 
     * Leasing
     * 
     * ****************************/

public static String LeaseMailContent(String message, String bookingNumber, String BUName, String Sysdate, String CreatedBy, String Status){
            String html=
                "<p>Hi,</p>\n" + 
                "<p>Good Day..!</p>\n" + 
                "<p>"+message+"</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p><strong>Lease Booking No: "+bookingNumber+".</strong></p>\n" + 
                "<table style=\"height: 84px; width: 777.91px; border-color: blue; float: left;\" border=\"1\">\n" + 
                "<tbody>\n" + 
                "<tr style=\"height: 25px;\">\n" + 
                "<td style=\"width: 162px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Business Unit</strong></span></td>\n" + 
                "<td style=\"width: 95px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Date </strong></span></td>\n" + 
                "<td style=\"width: 209px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Submitted By</strong></span></td>\n" + 
                "<td style=\"width: 192.91px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Status</strong></span></td>\n" + 
                "</tr>\n" + 
                "<tr style=\"height: 34px;\">\n" + 
                "<td style=\"width: 162px; height: 34px;\"><strong>"+BUName+"</strong></td>\n" + 
                "<td style=\"width: 95px; height: 34px;\"><strong>"+Sysdate+"</strong></td>\n" + 
                "<td style=\"width: 209px; height: 34px;\"><strong>"+CreatedBy+"</strong></td>\n" + 
                "<td style=\"width: 192.91px; height: 34px;\"><strong>"+Status+"</strong></td>\n" + 
                "</tr>\n" + 
                "</tbody>\n" + 
                "</table>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>Regards</p>\n" + 
                "<p>Lease Alert Message</p>\n" + 
                "<p><span style=\"color: #ff0000;\">Note: This is a system generated mail so please do not reply to this mail</span></p>\n";
                    
    return html; 
    }


    /******************************
     * 
     * Cancellation
     * 
     * ****************************/

    public static String cancellationMailContent(String ScreenName, String ScreenNumber, String BUName, String Sysdate, String CreatedBy, String Status){
            String html=
                "<p>Hi,</p>\n" + 
                "<p>Good Day..!</p>\n" + 
                "<p>"+ScreenName+" has been submitted for the Approval. Kindly Review and&nbsp;do the necessary action.</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p><strong>"+ScreenName+"+  Number : "+ScreenNumber+".</strong></p>\n" + 
                "<table style=\"height: 84px; width: 777.91px; border-color: blue; float: left;\" border=\"1\">\n" + 
                "<tbody>\n" + 
                "<tr style=\"height: 25px;\">\n" + 
                "<td style=\"width: 162px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Business Unit</strong></span></td>\n" + 
                "<td style=\"width: 95px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Date </strong></span></td>\n" + 
                "<td style=\"width: 209px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Submitted By</strong></span></td>\n" + 
                "<td style=\"width: 192.91px; text-align: center; height: 25px;\"><span style=\"color: #0000ff;\"><strong>Status</strong></span></td>\n" + 
                "</tr>\n" + 
                "<tr style=\"height: 34px;\">\n" + 
                "<td style=\"width: 162px; height: 34px;\"><strong>"+BUName+"</strong></td>\n" + 
                "<td style=\"width: 95px; height: 34px;\"><strong>"+Sysdate+"</strong></td>\n" + 
                "<td style=\"width: 209px; height: 34px;\"><strong>"+CreatedBy+"</strong></td>\n" + 
                "<td style=\"width: 192.91px; height: 34px;\"><strong>"+Status+"</strong></td>\n" + 
                "</tr>\n" + 
                "</tbody>\n" + 
                "</table>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>&nbsp;</p>\n" + 
                "<p>Regards</p>\n" + 
                "<p>Lease Alert Message</p>\n" + 
                "<p><span style=\"color: #ff0000;\">Note: This is a system generated mail so please do not reply to this mail</span></p>\n";
                    
    return html; 
    }

    //******************************    
    
    
}
