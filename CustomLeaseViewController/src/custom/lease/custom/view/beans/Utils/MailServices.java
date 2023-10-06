package custom.lease.custom.view.beans.Utils;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import javax.mail.internet.InternetAddress;

/**********2nd Approach***************/
 import java.util.ArrayList;
 import java.util.Properties;
 import javax.activation.DataHandler;
 import javax.activation.DataSource;
 import javax.activation.FileDataSource;
 import javax.mail.BodyPart;
 import javax.mail.Message;
 import javax.mail.MessagingException;
 import javax.mail.Multipart;
 import javax.mail.NoSuchProviderException;
 import javax.mail.PasswordAuthentication;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.AddressException;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeBodyPart;
 import javax.mail.internet.MimeMessage;
 import javax.mail.internet.MimeMultipart;

public class MailServices {
    public MailServices() {
        super();
    }
    
    /**********************Calling EL Expression*****************************************/

        public static Object evaluateEL(String el) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ELContext elContext = facesContext.getELContext();
                    ExpressionFactory expressionFactory =
                    facesContext.getApplication().getExpressionFactory();
                    ValueExpression exp =
                    expressionFactory.createValueExpression(elContext, el,
                    Object.class);

                    return exp.getValue(elContext);
                    }

    /**********************double quotes convert into single quotes*****************************************/

        public static String quotesReplace(String htmldata){
                String out=htmldata.replace( "\"",  "'");
                //StringBuilder builder = new StringBuilder("<html> <body>");
                //builder.append(out);
                //builder.append("</body> </html>");
                //System.err.println("----HTML BODY value---"+builder.toString());    
                //return builder.toString();
                // System.out.println("---Quotes Replace Output-----: "+out);
                return out;
                }

        /*******************Calling REST ful Services********************************************/
        
//        public static String sendNotification(String to, String from, String textBody, String htmlBody, String subject)
//                                                        throws MalformedURLException,IOException {
//                        
//                        String output = null;
//                        try {
//                            // Sobha Full PaaS-Not Working
//    //                      URL url = new URL("https://141.144.50.75/ords/pdb1/sbcmtest/mail/send");
//                            // EMS Test
//    //                      URL url = new URL("https://apex-a418962.db.em2.oraclecloudapps.com/apex/test/sam/");
//                            // EMS Prod
//                            URL url = new URL("https://apex-a418962.db.em2.oraclecloudapps.com/apex/bfm/bfmmail/");
//                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                            conn.setRequestMethod("POST");
//                            conn.setRequestProperty("Accept", "application/json");            
//                            conn.setRequestProperty( "User-Agent", "Mozilla/5.0" );
//                            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");            
//                            conn.setDoOutput(true);
//                            conn.setDoInput(true);
//                            
//                            String payload =   "{\"l_to\":\""+to+"\"," +    "\"l_from\":\""+from+"\"," +    "\"l_body\":\""+textBody+"\", " +
//                                                "\"l_body_html\":\""+htmlBody+"\", " +     "\"l_subject\":\""+subject+"\"}";
//                            conn.setRequestProperty("Content-Length", String.valueOf(payload.getBytes().length));
//                            
//                            OutputStream writer = conn.getOutputStream();
//                            writer.write(payload.getBytes());
//                            writer.flush();
//                            writer .close();
//                            // System.out.println(payload);
//                            if (conn.getResponseCode() != 200) {
//                                throw new RuntimeException("Failed : HTTP error code : " +
//                                                           conn.getResponseCode());
//                            }
//
//                            BufferedReader br =  new BufferedReader(new InputStreamReader((conn.getInputStream())));
//
//
//                            // System.out.println("Output from Server .... \n");
//                            while ((output = br.readLine()) != null) {
//                                // System.out.println("=======OUTPUT======="+output);
//                            }           
//                            conn.disconnect();
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        return output;
//                    }



    //    public void sendMessage(ActionEvent actionEvent) {
    //            String to = (String)evaluateEL("#{pageFlowScope.pMessageTo}");
    //                    System.err.println("=====TO====="+to);
    //                    String from = (String)evaluateEL("#{pageFlowScope.pMessageFrom}");
    //                    System.err.println("=====From====="+from);
    //                    String subject = (String)evaluateEL("#{pageFlowScope.pMessageSubject}");
    //                    System.err.println("=====Subject====="+subject);
    //                    String body = (String)evaluateEL("#{pageFlowScope.pMessageBody}");
    //                    System.err.println("====body======"+body);
    //                    String  convertHtmlBody= (String)evaluateEL("#{pageFlowScope.pMessageBodyhtml}");
    //                    String  htmlbody = quotesReplace(convertHtmlBody);
    //                    System.err.println("====htmlbody======"+htmlbody);
    //                    
    //                    try {
    //                        sendNotification(to, from, null, htmlbody, subject);
    //                    } catch (MalformedURLException e) {
    //                    } catch (IOException e) {
    //                    }
    //        }

     /**********2nd Approach***************/

             public static String sendMail(String msg, String subject, String FromUser, ArrayList<String> ToUser, String pwd,
                                    String hostName, String isAnyAtchmnt, ArrayList<String> fileNameNPath) {
                 // Setting Properties
                 Properties emailProperties = new Properties();
                 emailProperties.put("mail.smtp.host", hostName);
                 emailProperties.put("mail.smtp.auth", "true");
                 emailProperties.put("mail.smtp.starttls.enable", "true");
                 //Login Credentials
                 final String user = FromUser;  //change accordingly
                 final String password = pwd;  //change accordingly
                 //Authenticating...
                 Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
                     public PasswordAuthentication getPasswordAuthentication() {
                         return new PasswordAuthentication(user, password);
                     }
                 });
                 //1) create MimeBodyPart object and set your message content
                 MimeMessage message = new MimeMessage(session);
                 try {
                     message.setFrom(new InternetAddress(user));
                     for (String email : ToUser) {
                         // System.out.println("Mail Id is-" + email);
                         message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                     }
                     message.setSubject(subject);
                     BodyPart messageBody = new MimeBodyPart();
                     messageBody.setContent(msg, "text/html");
                     // If there is any attachment to send
                     //5) create Multipart object and add MimeBodyPart objects to this object
                     Multipart multipart = new MimeMultipart();
                     multipart.addBodyPart(messageBody);
                     if ("Y".equalsIgnoreCase(isAnyAtchmnt)) {
                         //2) create new MimeBodyPart object and set DataHandler object to this object
                         for (String path : fileNameNPath) {
                             MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                             // System.out.println("Exact path--->" + path);
                             DataSource source = new FileDataSource(path);
                             messageBodyPart2.setDataHandler(new DataHandler(source));
                             // // System.out.println("FileName is-"+path.substring(path.lastIndexOf("//")+1, path.length()));
                             messageBodyPart2.setFileName(path.substring(path.lastIndexOf("//") + 2, path.length()));
                             multipart.addBodyPart(messageBodyPart2);
                         }
                         //6) set the multiplart object to the message object
                         message.setContent(multipart);
                         message.saveChanges();
                     }
                     //If there is plain eMail- No Attachment
                     else {
                         message.setContent(msg, "text/html"); //for a html email
                     }
                 } catch (MessagingException e) {
                 }
                 Transport transport = null;
                 try {
                     transport = session.getTransport("smtp");
                 } catch (NoSuchProviderException e) {
                     // System.out.println("No such Provider Exception");
                 }
                 try {
                     transport.connect(hostName, FromUser, pwd);
                     transport.sendMessage(message, message.getAllRecipients());
                     transport.close();
                     // System.out.println("Email sent successfully.");
                     return "Y";
                 } catch (MessagingException e) {
                     // System.out.println("Messaging Exception" + e);
                     return "N";
                 }
             }    


    public static String sendMailWithoutAttachment(String toAddress, 
                                                String ccAddress, 
                                                String bccAddress,  
                                                String subject, 
                                                Object mailContent) {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        final String username = "prismalerts@omniyat.com";
        final String password = "Or@cl3alert";
        


        Session session = Session.getInstance(props,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
    
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            
            
            if(ccAddress!="NULL"){
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));                
            }
            if(bccAddress!="NULL"){
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccAddress));    
            }
//            System.err.println("to=="+Message.RecipientType.TO);
//            System.err.println("cc=="+Message.RecipientType.CC);
//            System.err.println("bcc=="+Message.RecipientType.BCC);
            message.setSubject(subject);
            
    //            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
    //            MimeBodyPart pdfBodyPart = new MimeBodyPart();
    //            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
    //            pdfBodyPart.setFileName(fileName);
    //            mimeMultipart.addBodyPart(pdfBodyPart);
            
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mailContent, "text/html");
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            message.setContent(mimeMultipart);

            Transport.send(message);
            return "Mail Send Successfully";
        
        } catch (AddressException ex) {
            return "Mail Notification (AddressException) Error : " + ex.toString();
        } catch (MessagingException ex) {
            return "Mail Notification (MessagingException) Error : " + ex.toString();
        } catch (Exception e) {
            return "Mail Notification (Exception) Error : " + e.toString();
        }
    } 


    
}
