package custom.lease.view.bean.backing;

import custom.lease.custom.view.beans.Utils.ADFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichDocument;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class Attachment {
    private RichDocument d1;

    public Attachment() {
    }
    private BlobDomain createBlobDomain(UploadedFile file) {
        InputStream in = null;
//        BlobDomain blobDomain = null;
//        OutputStream out = null;
//        try {
//            in = file.getInputStream();
//            blobDomain = new BlobDomain();
//            out = blobDomain.getBinaryOutputStream();
//            IOUtils.copy(in, out);
//                   in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.fillInStackTrace();
//        }
//        
//        return blobDomain;
        BlobDomain blobDomain = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            blobDomain = new BlobDomain();
            out = blobDomain.getBinaryOutputStream();
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return blobDomain;
    }

    private DCBindingContainer getBindingsCont() {
        return (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    }

   public String commitDate() {
       ADFUtils.findOperation("commitAttachment").execute();
       return "retunBack";
   }

    public String UploadFileActionToDB(UploadedFile file) {
        UploadedFile myfile = file;
        if (myfile != null) {
            DCIteratorBinding imageIter =
                (DCIteratorBinding)getBindingsCont().get("XxfndFuncAttachVOIterator");
            ViewObject vo = imageIter.getViewObject();
            Row curRow = vo.getCurrentRow();
            String filename = myfile.getFilename();
            String ContentType = myfile.getContentType();
            try {
                curRow.setAttribute("FileName", filename);
                curRow.setAttribute("FileType", ContentType);
                curRow.setAttribute("Attachment", createBlobDomain(myfile));
               
            } catch (Exception ex) {
//                System.out.println("Exception-" + ex);
            }
        }
        return null;
    }
    public void onClickAdd(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateInsert").execute();
    }

    public void onFileDownload(FacesContext facesContext,
                               OutputStream outputStream) {
        ViewObject vc =
            ADFUtils.findIterator("XxfndFuncAttachVOIterator").getViewObject();
     
        BlobDomain blob =
            (BlobDomain)vc.getCurrentRow().getAttribute("Attachment");
        try {
            IOUtils.copy(blob.getInputStream(), outputStream);
            blob.closeInputStream();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    public void OnFileUpload(ValueChangeEvent valueChangeEvent) {
//        System.err.println("------------inside the vce-----------");
        if (valueChangeEvent.getNewValue() != null) {
          
            UploadFileActionToDB((UploadedFile)valueChangeEvent.getNewValue());
//                   ADFUtils.invokeEL("#{bindings.Commit.execute}");
        }
    }

    public void setD1(RichDocument d1) {
        this.d1 = d1;
    }

    public RichDocument getD1() {
        return d1;
    }

    public void onClickSave(ActionEvent actionEvent) {
//        ViewObject vo =
//            ADFUtils.findIterator("XxfndFuncAttachVOIterator").getViewObject();
//        String func = vo.getCurrentRow().getAttribute("FuncId") == null ? "0" :vo.getCurrentRow().getAttribute("FuncId").toString();
//        vo.getCurrentRow().setAttribute("FuncId",ADFContext.getCurrent().getPageFlowScope().get("funcId"));
//        String funcRef = vo.getCurrentRow().getAttribute("FuncRefId") == null ? "0" :vo.getCurrentRow().getAttribute("FuncRefId").toString();
//        vo.getCurrentRow().setAttribute("FuncRefId",ADFContext.getCurrent().getPageFlowScope().get("funcRefId"));

    }
}
