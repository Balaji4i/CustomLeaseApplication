package custom.lease.model.AM.client;

import custom.lease.model.AM.common.PaymentPlanAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 05 18:33:23 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PaymentPlanAMClient extends ApplicationModuleImpl implements PaymentPlanAM {
    /**
     * This is the default constructor (do not remove).
     */
    public PaymentPlanAMClient() {
    }

    public String callrevision(Number id, String revname) {
        Object _ret =
            this.riInvokeExportedMethod(this,"callrevision",new String [] {"java.lang.Number","java.lang.String"},new Object[] {id, revname});
        return (String)_ret;
    }

    public String callrevision(String id, String revname) {
        Object _ret =
            this.riInvokeExportedMethod(this,"callrevision",new String [] {"java.lang.String","java.lang.String"},new Object[] {id, revname});
        return (String)_ret;
    }
}
