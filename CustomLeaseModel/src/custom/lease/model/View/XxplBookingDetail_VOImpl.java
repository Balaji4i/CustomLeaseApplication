package custom.lease.model.View;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 19 13:46:37 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplBookingDetail_VOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public XxplBookingDetail_VOImpl() {
    }

    /**
     * Returns the variable value for BV_BOOK_ID.
     * @return variable value for BV_BOOK_ID
     */
    public String getBV_BOOK_ID() {
        return (String)ensureVariableManager().getVariableValue("BV_BOOK_ID");
    }

    /**
     * Sets <code>value</code> for variable BV_BOOK_ID.
     * @param value value to bind as BV_BOOK_ID
     */
    public void setBV_BOOK_ID(String value) {
        ensureVariableManager().setVariableValue("BV_BOOK_ID", value);
    }
}
