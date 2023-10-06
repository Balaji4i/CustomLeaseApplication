package custom.lease.custom.errorhandler;


import java.io.Serializable;

import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;


public class ErrorHandler implements Serializable{
    private static ADFLogger _logger = ADFLogger.createADFLogger(ErrorHandler.class);

    public ErrorHandler() {
    }

    /*Method to handle exceptions - called in adfc-config.xml*/
    public void handleError() {
        // Add event code here...
        String errorMsg = null;
        ControllerContext context = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort =context.getCurrentRootViewPort();       
        if(currentRootViewPort.isExceptionPresent()) {
            Exception e = currentRootViewPort.getExceptionData();            
            if(e!=null && e instanceof JboException) {
                JboException je = (JboException)e;
                je.printStackTrace();
                errorMsg = je.getMessage();
                    }
            if(e!=null){
                e.printStackTrace();
                errorMsg = e.getMessage();
                _logger.exiting("ErrorHandler", "exceptioHandeler");
            }
           
        }
        String expMsg =
            "<html><body>" + "Some thing went wrong!! Please contact the administrator and report the below error!!" +
            "<br/><br/>" + errorMsg +
            "</body></html>";
        FacesMessage message = new FacesMessage(expMsg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ectx.getSession(false);
        _logger.severe("Error Message in ErrorHandler for User :" +
                       ((String) session.getAttribute("userName")).toUpperCase() + ":" + errorMsg);
        _logger.exiting("ErrorHandler", "exceptioHandeler");

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
}
