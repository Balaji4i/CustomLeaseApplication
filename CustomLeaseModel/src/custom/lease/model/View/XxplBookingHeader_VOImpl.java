package custom.lease.model.View;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 16 15:38:57 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplBookingHeader_VOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public XxplBookingHeader_VOImpl() {
    }

    /**
     * Returns the variable value for BV_BOOKING_ID.
     * @return variable value for BV_BOOKING_ID
     */
    public String getBV_BOOKING_ID() {
        return (String)ensureVariableManager().getVariableValue("BV_BOOKING_ID");
    }

    /**
     * Sets <code>value</code> for variable BV_BOOKING_ID.
     * @param value value to bind as BV_BOOKING_ID
     */
    public void setBV_BOOKING_ID(String value) {
        ensureVariableManager().setVariableValue("BV_BOOKING_ID", value);
    }
}
