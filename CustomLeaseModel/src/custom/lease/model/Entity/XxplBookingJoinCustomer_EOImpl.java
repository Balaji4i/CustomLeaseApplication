package custom.lease.model.Entity;

import custom.lease.model.View.XxplBookingCustomer_VORowImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 16 11:31:31 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplBookingJoinCustomer_EOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        BookingCustDtlId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getBookingCustDtlId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setBookingCustDtlId((Number)value);
            }
        }
        ,
        BookingCustId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getBookingCustId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setBookingCustId((Number)value);
            }
        }
        ,
        BookingHdrId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getBookingHdrId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setBookingHdrId((Number)value);
            }
        }
        ,
        CustId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getCustId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setCustId((BigDecimal)value);
            }
        }
        ,
        ShipToSiteId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getShipToSiteId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setShipToSiteId((BigDecimal)value);
            }
        }
        ,
        BillToSiteId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getBillToSiteId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setBillToSiteId((BigDecimal)value);
            }
        }
        ,
        CustContactId {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getCustContactId();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setCustContactId((BigDecimal)value);
            }
        }
        ,
        Description {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getDescription();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setDescription((String)value);
            }
        }
        ,
        AttributeCategory {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttributeCategory();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttributeCategory((String)value);
            }
        }
        ,
        Attribute1 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute1();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute1((String)value);
            }
        }
        ,
        Attribute2 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute2();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute2((String)value);
            }
        }
        ,
        Attribute3 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute3();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute3((String)value);
            }
        }
        ,
        Attribute4 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute4();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute4((String)value);
            }
        }
        ,
        Attribute5 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute5();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute5((String)value);
            }
        }
        ,
        Attribute6 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute6();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute6((String)value);
            }
        }
        ,
        Attribute7 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute7();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute7((String)value);
            }
        }
        ,
        Attribute8 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute8();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute8((String)value);
            }
        }
        ,
        Attribute9 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute9();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute9((String)value);
            }
        }
        ,
        Attribute10 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute10();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute10((String)value);
            }
        }
        ,
        Attribute11 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute11();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute11((String)value);
            }
        }
        ,
        Attribute12 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute12();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute12((String)value);
            }
        }
        ,
        Attribute13 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute13();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute13((String)value);
            }
        }
        ,
        Attribute14 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute14();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute14((String)value);
            }
        }
        ,
        Attribute15 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute15();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute15((String)value);
            }
        }
        ,
        Attribute16 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute16();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute16((String)value);
            }
        }
        ,
        Attribute17 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute17();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute17((String)value);
            }
        }
        ,
        Attribute18 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute18();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute18((String)value);
            }
        }
        ,
        Attribute19 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute19();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute19((String)value);
            }
        }
        ,
        Attribute20 {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getAttribute20();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttribute20((String)value);
            }
        }
        ,
        CreatedBy {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationDate {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getCreationDate();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setCreationDate((oracle.jbo.domain.Timestamp)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setLastUpdatedBy((String)value);
            }
        }
        ,
        LastUpdateDate {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getLastUpdateDate();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setLastUpdateDate((oracle.jbo.domain.Timestamp)value);
            }
        }
        ,
        LastUpdateLogin {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getLastUpdateLogin();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setLastUpdateLogin((String)value);
            }
        }
        ,
        RowID {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getRowID();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        XxplBookingCustomer_EO {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getXxplBookingCustomer_EO();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setXxplBookingCustomer_EO((XxplBookingCustomer_EOImpl)value);
            }
        }
        ,
        XxplBookingCustomer_VO {
            public Object get(XxplBookingJoinCustomer_EOImpl obj) {
                return obj.getXxplBookingCustomer_VO();
            }

            public void put(XxplBookingJoinCustomer_EOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(XxplBookingJoinCustomer_EOImpl object);

        public abstract void put(XxplBookingJoinCustomer_EOImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int BOOKINGCUSTDTLID = AttributesEnum.BookingCustDtlId.index();
    public static final int BOOKINGCUSTID = AttributesEnum.BookingCustId.index();
    public static final int BOOKINGHDRID = AttributesEnum.BookingHdrId.index();
    public static final int CUSTID = AttributesEnum.CustId.index();
    public static final int SHIPTOSITEID = AttributesEnum.ShipToSiteId.index();
    public static final int BILLTOSITEID = AttributesEnum.BillToSiteId.index();
    public static final int CUSTCONTACTID = AttributesEnum.CustContactId.index();
    public static final int DESCRIPTION = AttributesEnum.Description.index();
    public static final int ATTRIBUTECATEGORY = AttributesEnum.AttributeCategory.index();
    public static final int ATTRIBUTE1 = AttributesEnum.Attribute1.index();
    public static final int ATTRIBUTE2 = AttributesEnum.Attribute2.index();
    public static final int ATTRIBUTE3 = AttributesEnum.Attribute3.index();
    public static final int ATTRIBUTE4 = AttributesEnum.Attribute4.index();
    public static final int ATTRIBUTE5 = AttributesEnum.Attribute5.index();
    public static final int ATTRIBUTE6 = AttributesEnum.Attribute6.index();
    public static final int ATTRIBUTE7 = AttributesEnum.Attribute7.index();
    public static final int ATTRIBUTE8 = AttributesEnum.Attribute8.index();
    public static final int ATTRIBUTE9 = AttributesEnum.Attribute9.index();
    public static final int ATTRIBUTE10 = AttributesEnum.Attribute10.index();
    public static final int ATTRIBUTE11 = AttributesEnum.Attribute11.index();
    public static final int ATTRIBUTE12 = AttributesEnum.Attribute12.index();
    public static final int ATTRIBUTE13 = AttributesEnum.Attribute13.index();
    public static final int ATTRIBUTE14 = AttributesEnum.Attribute14.index();
    public static final int ATTRIBUTE15 = AttributesEnum.Attribute15.index();
    public static final int ATTRIBUTE16 = AttributesEnum.Attribute16.index();
    public static final int ATTRIBUTE17 = AttributesEnum.Attribute17.index();
    public static final int ATTRIBUTE18 = AttributesEnum.Attribute18.index();
    public static final int ATTRIBUTE19 = AttributesEnum.Attribute19.index();
    public static final int ATTRIBUTE20 = AttributesEnum.Attribute20.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATELOGIN = AttributesEnum.LastUpdateLogin.index();
    public static final int ROWID = AttributesEnum.RowID.index();
    public static final int XXPLBOOKINGCUSTOMER_EO = AttributesEnum.XxplBookingCustomer_EO.index();
    public static final int XXPLBOOKINGCUSTOMER_VO = AttributesEnum.XxplBookingCustomer_VO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XxplBookingJoinCustomer_EOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("custom.lease.model.Entity.XxplBookingJoinCustomer_EO");
    }

    /**
     * Gets the attribute value for BookingCustDtlId, using the alias name BookingCustDtlId.
     * @return the BookingCustDtlId
     */
    public Number getBookingCustDtlId() {
        return (Number)getAttributeInternal(BOOKINGCUSTDTLID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BookingCustDtlId.
     * @param value value to set the BookingCustDtlId
     */
    public void setBookingCustDtlId(Number value) {
        setAttributeInternal(BOOKINGCUSTDTLID, value);
    }

    /**
     * Gets the attribute value for BookingCustId, using the alias name BookingCustId.
     * @return the BookingCustId
     */
    public Number getBookingCustId() {
        return (Number)getAttributeInternal(BOOKINGCUSTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BookingCustId.
     * @param value value to set the BookingCustId
     */
    public void setBookingCustId(Number value) {
        setAttributeInternal(BOOKINGCUSTID, value);
    }

    /**
     * Gets the attribute value for BookingHdrId, using the alias name BookingHdrId.
     * @return the BookingHdrId
     */
    public Number getBookingHdrId() {
        return (Number)getAttributeInternal(BOOKINGHDRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BookingHdrId.
     * @param value value to set the BookingHdrId
     */
    public void setBookingHdrId(Number value) {
        setAttributeInternal(BOOKINGHDRID, value);
    }

    /**
     * Gets the attribute value for CustId, using the alias name CustId.
     * @return the CustId
     */
    public BigDecimal getCustId() {
        return (BigDecimal)getAttributeInternal(CUSTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CustId.
     * @param value value to set the CustId
     */
    public void setCustId(BigDecimal value) {
        setAttributeInternal(CUSTID, value);
    }

    /**
     * Gets the attribute value for ShipToSiteId, using the alias name ShipToSiteId.
     * @return the ShipToSiteId
     */
    public BigDecimal getShipToSiteId() {
        return (BigDecimal)getAttributeInternal(SHIPTOSITEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShipToSiteId.
     * @param value value to set the ShipToSiteId
     */
    public void setShipToSiteId(BigDecimal value) {
        setAttributeInternal(SHIPTOSITEID, value);
    }

    /**
     * Gets the attribute value for BillToSiteId, using the alias name BillToSiteId.
     * @return the BillToSiteId
     */
    public BigDecimal getBillToSiteId() {
        return (BigDecimal)getAttributeInternal(BILLTOSITEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BillToSiteId.
     * @param value value to set the BillToSiteId
     */
    public void setBillToSiteId(BigDecimal value) {
        setAttributeInternal(BILLTOSITEID, value);
    }

    /**
     * Gets the attribute value for CustContactId, using the alias name CustContactId.
     * @return the CustContactId
     */
    public BigDecimal getCustContactId() {
        return (BigDecimal)getAttributeInternal(CUSTCONTACTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CustContactId.
     * @param value value to set the CustContactId
     */
    public void setCustContactId(BigDecimal value) {
        setAttributeInternal(CUSTCONTACTID, value);
    }

    /**
     * Gets the attribute value for Description, using the alias name Description.
     * @return the Description
     */
    public String getDescription() {
        return (String)getAttributeInternal(DESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for Description.
     * @param value value to set the Description
     */
    public void setDescription(String value) {
        setAttributeInternal(DESCRIPTION, value);
    }

    /**
     * Gets the attribute value for AttributeCategory, using the alias name AttributeCategory.
     * @return the AttributeCategory
     */
    public String getAttributeCategory() {
        return (String)getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**
     * Sets <code>value</code> as the attribute value for AttributeCategory.
     * @param value value to set the AttributeCategory
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**
     * Gets the attribute value for Attribute1, using the alias name Attribute1.
     * @return the Attribute1
     */
    public String getAttribute1() {
        return (String)getAttributeInternal(ATTRIBUTE1);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute1.
     * @param value value to set the Attribute1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**
     * Gets the attribute value for Attribute2, using the alias name Attribute2.
     * @return the Attribute2
     */
    public String getAttribute2() {
        return (String)getAttributeInternal(ATTRIBUTE2);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute2.
     * @param value value to set the Attribute2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**
     * Gets the attribute value for Attribute3, using the alias name Attribute3.
     * @return the Attribute3
     */
    public String getAttribute3() {
        return (String)getAttributeInternal(ATTRIBUTE3);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute3.
     * @param value value to set the Attribute3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**
     * Gets the attribute value for Attribute4, using the alias name Attribute4.
     * @return the Attribute4
     */
    public String getAttribute4() {
        return (String)getAttributeInternal(ATTRIBUTE4);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute4.
     * @param value value to set the Attribute4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**
     * Gets the attribute value for Attribute5, using the alias name Attribute5.
     * @return the Attribute5
     */
    public String getAttribute5() {
        return (String)getAttributeInternal(ATTRIBUTE5);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute5.
     * @param value value to set the Attribute5
     */
    public void setAttribute5(String value) {
        setAttributeInternal(ATTRIBUTE5, value);
    }

    /**
     * Gets the attribute value for Attribute6, using the alias name Attribute6.
     * @return the Attribute6
     */
    public String getAttribute6() {
        return (String)getAttributeInternal(ATTRIBUTE6);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute6.
     * @param value value to set the Attribute6
     */
    public void setAttribute6(String value) {
        setAttributeInternal(ATTRIBUTE6, value);
    }

    /**
     * Gets the attribute value for Attribute7, using the alias name Attribute7.
     * @return the Attribute7
     */
    public String getAttribute7() {
        return (String)getAttributeInternal(ATTRIBUTE7);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute7.
     * @param value value to set the Attribute7
     */
    public void setAttribute7(String value) {
        setAttributeInternal(ATTRIBUTE7, value);
    }

    /**
     * Gets the attribute value for Attribute8, using the alias name Attribute8.
     * @return the Attribute8
     */
    public String getAttribute8() {
        return (String)getAttributeInternal(ATTRIBUTE8);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute8.
     * @param value value to set the Attribute8
     */
    public void setAttribute8(String value) {
        setAttributeInternal(ATTRIBUTE8, value);
    }

    /**
     * Gets the attribute value for Attribute9, using the alias name Attribute9.
     * @return the Attribute9
     */
    public String getAttribute9() {
        return (String)getAttributeInternal(ATTRIBUTE9);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute9.
     * @param value value to set the Attribute9
     */
    public void setAttribute9(String value) {
        setAttributeInternal(ATTRIBUTE9, value);
    }

    /**
     * Gets the attribute value for Attribute10, using the alias name Attribute10.
     * @return the Attribute10
     */
    public String getAttribute10() {
        return (String)getAttributeInternal(ATTRIBUTE10);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute10.
     * @param value value to set the Attribute10
     */
    public void setAttribute10(String value) {
        setAttributeInternal(ATTRIBUTE10, value);
    }

    /**
     * Gets the attribute value for Attribute11, using the alias name Attribute11.
     * @return the Attribute11
     */
    public String getAttribute11() {
        return (String)getAttributeInternal(ATTRIBUTE11);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute11.
     * @param value value to set the Attribute11
     */
    public void setAttribute11(String value) {
        setAttributeInternal(ATTRIBUTE11, value);
    }

    /**
     * Gets the attribute value for Attribute12, using the alias name Attribute12.
     * @return the Attribute12
     */
    public String getAttribute12() {
        return (String)getAttributeInternal(ATTRIBUTE12);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute12.
     * @param value value to set the Attribute12
     */
    public void setAttribute12(String value) {
        setAttributeInternal(ATTRIBUTE12, value);
    }

    /**
     * Gets the attribute value for Attribute13, using the alias name Attribute13.
     * @return the Attribute13
     */
    public String getAttribute13() {
        return (String)getAttributeInternal(ATTRIBUTE13);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute13.
     * @param value value to set the Attribute13
     */
    public void setAttribute13(String value) {
        setAttributeInternal(ATTRIBUTE13, value);
    }

    /**
     * Gets the attribute value for Attribute14, using the alias name Attribute14.
     * @return the Attribute14
     */
    public String getAttribute14() {
        return (String)getAttributeInternal(ATTRIBUTE14);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute14.
     * @param value value to set the Attribute14
     */
    public void setAttribute14(String value) {
        setAttributeInternal(ATTRIBUTE14, value);
    }

    /**
     * Gets the attribute value for Attribute15, using the alias name Attribute15.
     * @return the Attribute15
     */
    public String getAttribute15() {
        return (String)getAttributeInternal(ATTRIBUTE15);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute15.
     * @param value value to set the Attribute15
     */
    public void setAttribute15(String value) {
        setAttributeInternal(ATTRIBUTE15, value);
    }

    /**
     * Gets the attribute value for Attribute16, using the alias name Attribute16.
     * @return the Attribute16
     */
    public String getAttribute16() {
        return (String)getAttributeInternal(ATTRIBUTE16);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute16.
     * @param value value to set the Attribute16
     */
    public void setAttribute16(String value) {
        setAttributeInternal(ATTRIBUTE16, value);
    }

    /**
     * Gets the attribute value for Attribute17, using the alias name Attribute17.
     * @return the Attribute17
     */
    public String getAttribute17() {
        return (String)getAttributeInternal(ATTRIBUTE17);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute17.
     * @param value value to set the Attribute17
     */
    public void setAttribute17(String value) {
        setAttributeInternal(ATTRIBUTE17, value);
    }

    /**
     * Gets the attribute value for Attribute18, using the alias name Attribute18.
     * @return the Attribute18
     */
    public String getAttribute18() {
        return (String)getAttributeInternal(ATTRIBUTE18);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute18.
     * @param value value to set the Attribute18
     */
    public void setAttribute18(String value) {
        setAttributeInternal(ATTRIBUTE18, value);
    }

    /**
     * Gets the attribute value for Attribute19, using the alias name Attribute19.
     * @return the Attribute19
     */
    public String getAttribute19() {
        return (String)getAttributeInternal(ATTRIBUTE19);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute19.
     * @param value value to set the Attribute19
     */
    public void setAttribute19(String value) {
        setAttributeInternal(ATTRIBUTE19, value);
    }

    /**
     * Gets the attribute value for Attribute20, using the alias name Attribute20.
     * @return the Attribute20
     */
    public String getAttribute20() {
        return (String)getAttributeInternal(ATTRIBUTE20);
    }

    /**
     * Sets <code>value</code> as the attribute value for Attribute20.
     * @param value value to set the Attribute20
     */
    public void setAttribute20(String value) {
        setAttributeInternal(ATTRIBUTE20, value);
    }

    /**
     * Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     * @return the CreatedBy
     */
    public String getCreatedBy() {
        return (String)getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedBy.
     * @param value value to set the CreatedBy
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CreationDate, using the alias name CreationDate.
     * @return the CreationDate
     */
    public Timestamp getCreationDate() {
        return (Timestamp)getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreationDate.
     * @param value value to set the CreationDate
     */
    public void setCreationDate(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for LastUpdatedBy, using the alias name LastUpdatedBy.
     * @return the LastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return (String)getAttributeInternal(LASTUPDATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdatedBy.
     * @param value value to set the LastUpdatedBy
     */
    public void setLastUpdatedBy(String value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for LastUpdateDate, using the alias name LastUpdateDate.
     * @return the LastUpdateDate
     */
    public Timestamp getLastUpdateDate() {
        return (Timestamp)getAttributeInternal(LASTUPDATEDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdateDate.
     * @param value value to set the LastUpdateDate
     */
    public void setLastUpdateDate(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**
     * Gets the attribute value for LastUpdateLogin, using the alias name LastUpdateLogin.
     * @return the LastUpdateLogin
     */
    public String getLastUpdateLogin() {
        return (String)getAttributeInternal(LASTUPDATELOGIN);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdateLogin.
     * @param value value to set the LastUpdateLogin
     */
    public void setLastUpdateLogin(String value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**
     * Gets the attribute value for RowID, using the alias name RowID.
     * @return the RowID
     */
    public String getRowID() {
        return (String)getAttributeInternal(ROWID);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }


    /**
     * @return the associated entity XxplBookingCustomer_EOImpl.
     */
    public XxplBookingCustomer_EOImpl getXxplBookingCustomer_EO() {
        return (XxplBookingCustomer_EOImpl)getAttributeInternal(XXPLBOOKINGCUSTOMER_EO);
    }

    /**
     * Sets <code>value</code> as the associated entity XxplBookingCustomer_EOImpl.
     */
    public void setXxplBookingCustomer_EO(XxplBookingCustomer_EOImpl value) {
        setAttributeInternal(XXPLBOOKINGCUSTOMER_EO, value);
    }

    /**
     * Uses the link Booking_Cust_JoinCust_VL to return rows of XxplBookingJoinCustomer_VO
     */
    public XxplBookingCustomer_VORowImpl getXxplBookingCustomer_VO() {
        return (XxplBookingCustomer_VORowImpl)getAttributeInternal(XXPLBOOKINGCUSTOMER_VO);
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        SequenceImpl seq = new SequenceImpl("BOOKING_CUST_DTL_ID_S", this.getDBTransaction());
        this.setBookingCustDtlId(seq.getSequenceNumber());
        this.setCreatedBy(ADFContext.getCurrent().getSessionScope().get("userName").toString());
        this.setLastUpdatedBy(ADFContext.getCurrent().getSessionScope().get("userName").toString());
        this.setLastUpdateLogin(ADFContext.getCurrent().getSessionScope().get("userName").toString());
        this.setLastUpdateDate(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        this.setCreationDate(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));   
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if(operation==EntityImpl.DML_UPDATE){
            this.setLastUpdatedBy(ADFContext.getCurrent().getSessionScope().get("userName").toString());
            this.setLastUpdateLogin(ADFContext.getCurrent().getSessionScope().get("userName").toString());
            this.setLastUpdateDate(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        }
        super.doDML(operation, e);
    }
}