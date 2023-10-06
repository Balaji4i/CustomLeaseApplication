package custom.lease.model.AM.client;

import custom.lease.model.AM.common.Documents_AM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 16 14:13:35 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class Documents_AMClient extends ApplicationModuleImpl implements Documents_AM {
    /**
     * This is the default constructor (do not remove).
     */
    public Documents_AMClient() {
    }


    public void intAttachments(String funcid, String funcrefid) {
        Object _ret =
            this.riInvokeExportedMethod(this,"intAttachments",new String [] {"java.lang.String","java.lang.String"},new Object[] {funcid, funcrefid});
        return;
    }

    public String CheckUserNaname(String userName) {
        Object _ret =
            this.riInvokeExportedMethod(this,"CheckUserNaname",new String [] {"java.lang.String"},new Object[] {userName});
        return (String)_ret;
    }

    public void commitAttachment() {
        Object _ret =
            this.riInvokeExportedMethod(this,"commitAttachment",null,null);
        return;
    }

    public void filterCheckList(String funcId, String funcRefId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterCheckList",new String [] {"java.lang.String","java.lang.String"},new Object[] {funcId, funcRefId});
        return;
    }
}
