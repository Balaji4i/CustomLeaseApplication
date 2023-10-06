package custom.lease.model.Entity;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 23 11:50:39 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplMoveIoCustVisit_EOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        VisitId {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getVisitId();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setVisitId((Number)value);
            }
        }
        ,
        LeaseAgreementId {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getLeaseAgreementId();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setLeaseAgreementId((BigDecimal)value);
            }
        }
        ,
        BookingId {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getBookingId();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setBookingId((BigDecimal)value);
            }
        }
        ,
        MoveIoId {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getMoveIoId();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setMoveIoId((BigDecimal)value);
            }
        }
        ,
        AcceptFlag {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAcceptFlag();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAcceptFlag((String)value);
            }
        }
        ,
        VisitDate {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getVisitDate();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setVisitDate((Date)value);
            }
        }
        ,
        AssistedBy {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAssistedBy();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAssistedBy((BigDecimal)value);
            }
        }
        ,
        Description {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getDescription();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setDescription((String)value);
            }
        }
        ,
        AttributeCategory {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttributeCategory();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttributeCategory((String)value);
            }
        }
        ,
        Attribute1 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute1();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute1((String)value);
            }
        }
        ,
        Attribute2 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute2();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute2((String)value);
            }
        }
        ,
        Attribute3 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute3();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute3((String)value);
            }
        }
        ,
        Attribute4 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute4();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute4((String)value);
            }
        }
        ,
        Attribute5 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute5();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute5((String)value);
            }
        }
        ,
        Attribute6 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute6();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute6((String)value);
            }
        }
        ,
        Attribute7 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute7();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute7((String)value);
            }
        }
        ,
        Attribute8 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute8();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute8((String)value);
            }
        }
        ,
        Attribute9 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute9();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute9((String)value);
            }
        }
        ,
        Attribute10 {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getAttribute10();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setAttribute10((String)value);
            }
        }
        ,
        ObjectVersion {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getObjectVersion();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setObjectVersion((BigDecimal)value);
            }
        }
        ,
        CreatedBy {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationDate {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getCreationDate();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setCreationDate((Timestamp)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setLastUpdatedBy((String)value);
            }
        }
        ,
        LastUpdateDate {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getLastUpdateDate();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setLastUpdateDate((Timestamp)value);
            }
        }
        ,
        LastUpdateLogin {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getLastUpdateLogin();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setLastUpdateLogin((String)value);
            }
        }
        ,
        XxplMoveInOut_Eo {
            public Object get(XxplMoveIoCustVisit_EOImpl obj) {
                return obj.getXxplMoveInOut_Eo();
            }

            public void put(XxplMoveIoCustVisit_EOImpl obj, Object value) {
                obj.setXxplMoveInOut_Eo((XxplMoveInOut_EoImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(XxplMoveIoCustVisit_EOImpl object);

        public abstract void put(XxplMoveIoCustVisit_EOImpl object,
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


    public static final int VISITID = AttributesEnum.VisitId.index();
    public static final int LEASEAGREEMENTID = AttributesEnum.LeaseAgreementId.index();
    public static final int BOOKINGID = AttributesEnum.BookingId.index();
    public static final int MOVEIOID = AttributesEnum.MoveIoId.index();
    public static final int ACCEPTFLAG = AttributesEnum.AcceptFlag.index();
    public static final int VISITDATE = AttributesEnum.VisitDate.index();
    public static final int ASSISTEDBY = AttributesEnum.AssistedBy.index();
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
    public static final int OBJECTVERSION = AttributesEnum.ObjectVersion.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATELOGIN = AttributesEnum.LastUpdateLogin.index();
    public static final int XXPLMOVEINOUT_EO = AttributesEnum.XxplMoveInOut_Eo.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XxplMoveIoCustVisit_EOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("custom.lease.model.Entity.XxplMoveIoCustVisit_EO");
    }

    /**
     * Gets the attribute value for VisitId, using the alias name VisitId.
     * @return the VisitId
     */
    public Number getVisitId() {
        return (Number)getAttributeInternal(VISITID);
    }

    /**
     * Sets <code>value</code> as the attribute value for VisitId.
     * @param value value to set the VisitId
     */
    public void setVisitId(Number value) {
        setAttributeInternal(VISITID, value);
    }

    /**
     * Gets the attribute value for LeaseAgreementId, using the alias name LeaseAgreementId.
     * @return the LeaseAgreementId
     */
    public BigDecimal getLeaseAgreementId() {
        return (BigDecimal)getAttributeInternal(LEASEAGREEMENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for LeaseAgreementId.
     * @param value value to set the LeaseAgreementId
     */
    public void setLeaseAgreementId(BigDecimal value) {
        setAttributeInternal(LEASEAGREEMENTID, value);
    }

    /**
     * Gets the attribute value for BookingId, using the alias name BookingId.
     * @return the BookingId
     */
    public BigDecimal getBookingId() {
        return (BigDecimal)getAttributeInternal(BOOKINGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BookingId.
     * @param value value to set the BookingId
     */
    public void setBookingId(BigDecimal value) {
        setAttributeInternal(BOOKINGID, value);
    }

    /**
     * Gets the attribute value for MoveIoId, using the alias name MoveIoId.
     * @return the MoveIoId
     */
    public BigDecimal getMoveIoId() {
        return (BigDecimal)getAttributeInternal(MOVEIOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for MoveIoId.
     * @param value value to set the MoveIoId
     */
    public void setMoveIoId(BigDecimal value) {
        setAttributeInternal(MOVEIOID, value);
    }

    /**
     * Gets the attribute value for AcceptFlag, using the alias name AcceptFlag.
     * @return the AcceptFlag
     */
    public String getAcceptFlag() {
        return (String)getAttributeInternal(ACCEPTFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for AcceptFlag.
     * @param value value to set the AcceptFlag
     */
    public void setAcceptFlag(String value) {
        setAttributeInternal(ACCEPTFLAG, value);
    }

    /**
     * Gets the attribute value for VisitDate, using the alias name VisitDate.
     * @return the VisitDate
     */
    public Date getVisitDate() {
        return (Date)getAttributeInternal(VISITDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for VisitDate.
     * @param value value to set the VisitDate
     */
    public void setVisitDate(Date value) {
        setAttributeInternal(VISITDATE, value);
    }

    /**
     * Gets the attribute value for AssistedBy, using the alias name AssistedBy.
     * @return the AssistedBy
     */
    public BigDecimal getAssistedBy() {
        return (BigDecimal)getAttributeInternal(ASSISTEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for AssistedBy.
     * @param value value to set the AssistedBy
     */
    public void setAssistedBy(BigDecimal value) {
        setAttributeInternal(ASSISTEDBY, value);
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
     * Gets the attribute value for ObjectVersion, using the alias name ObjectVersion.
     * @return the ObjectVersion
     */
    public BigDecimal getObjectVersion() {
        return (BigDecimal)getAttributeInternal(OBJECTVERSION);
    }

    /**
     * Sets <code>value</code> as the attribute value for ObjectVersion.
     * @param value value to set the ObjectVersion
     */
    public void setObjectVersion(BigDecimal value) {
        setAttributeInternal(OBJECTVERSION, value);
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
    public void setCreationDate(Timestamp value) {
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
    public void setLastUpdateDate(Timestamp value) {
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
     * @return the associated entity XxplMoveInOut_EoImpl.
     */
    public XxplMoveInOut_EoImpl getXxplMoveInOut_Eo() {
        return (XxplMoveInOut_EoImpl)getAttributeInternal(XXPLMOVEINOUT_EO);
    }

    /**
     * Sets <code>value</code> as the associated entity XxplMoveInOut_EoImpl.
     */
    public void setXxplMoveInOut_Eo(XxplMoveInOut_EoImpl value) {
        setAttributeInternal(XXPLMOVEINOUT_EO, value);
    }


    /**
     * @param visitId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number visitId) {
        return new Key(new Object[]{visitId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        SequenceImpl seq = new SequenceImpl("VISIT_ID_S", this.getDBTransaction());
                     this.setVisitId(seq.getSequenceNumber());
                     this.setCreatedBy(ADFContext.getCurrent().getSessionScope().get("userName").toString());
                     this.setLastUpdatedBy(ADFContext.getCurrent().getSessionScope().get("userName").toString());
                     this.setLastUpdateLogin(ADFContext.getCurrent().getSessionScope().get("userName").toString());
                     this.setCreationDate(new Timestamp(System.currentTimeMillis())); 
                     this.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
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
                     this.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
                  }
        super.doDML(operation, e);
    }
}
