package custom.lease.model.Entity;

import java.math.BigDecimal;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.TooManyObjectsException;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 20 11:02:00 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplARSetupEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ArSetupId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getArSetupId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setArSetupId((Number)value);
            }
        }
        ,
        BusinessUnitId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getBusinessUnitId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setBusinessUnitId((Number)value);
            }
        }
        ,
        LegalEntityId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getLegalEntityId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setLegalEntityId((BigDecimal)value);
            }
        }
        ,
        ChargeType {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getChargeType();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setChargeType((String)value);
            }
        }
        ,
        TxnType {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTxnType();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTxnType((String)value);
            }
        }
        ,
        TxnSource {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTxnSource();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTxnSource((String)value);
            }
        }
        ,
        CmTxnType {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getCmTxnType();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setCmTxnType((String)value);
            }
        }
        ,
        CmTxnSource {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getCmTxnSource();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setCmTxnSource((String)value);
            }
        }
        ,
        TypeId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTypeId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTypeId((String)value);
            }
        }
        ,
        TxnTypeId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTxnTypeId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTxnTypeId((BigDecimal)value);
            }
        }
        ,
        TxnSourceId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTxnSourceId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTxnSourceId((BigDecimal)value);
            }
        }
        ,
        CmTxnTypeId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getCmTxnTypeId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setCmTxnTypeId((BigDecimal)value);
            }
        }
        ,
        CmTxnSourceId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getCmTxnSourceId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setCmTxnSourceId((BigDecimal)value);
            }
        }
        ,
        RevAccountId {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getRevAccountId();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setRevAccountId((BigDecimal)value);
            }
        }
        ,
        ConcatenatedSegment {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getConcatenatedSegment();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setConcatenatedSegment((String)value);
            }
        }
        ,
        AccountingRuleName {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getAccountingRuleName();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setAccountingRuleName((String)value);
            }
        }
        ,
        InvoicingRule {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getInvoicingRule();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setInvoicingRule((String)value);
            }
        }
        ,
        IntercompanyGlCode {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getIntercompanyGlCode();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setIntercompanyGlCode((String)value);
            }
        }
        ,
        CostCenterGlCode {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getCostCenterGlCode();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setCostCenterGlCode((String)value);
            }
        }
        ,
        Usage {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getUsage();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setUsage((String)value);
            }
        }
        ,
        UnitCategory {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getUnitCategory();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setUnitCategory((String)value);
            }
        }
        ,
        TaxCode {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getTaxCode();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setTaxCode((String)value);
            }
        }
        ,
        LedgerName {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getLedgerName();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setLedgerName((String)value);
            }
        }
        ,
        Attribute1 {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getAttribute1();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setAttribute1((String)value);
            }
        }
        ,
        UnitMethod {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getUnitMethod();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setUnitMethod((String)value);
            }
        }
        ,
        Attribute2 {
            public Object get(XxplARSetupEOImpl obj) {
                return obj.getAttribute2();
            }

            public void put(XxplARSetupEOImpl obj, Object value) {
                obj.setAttribute2((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(XxplARSetupEOImpl object);

        public abstract void put(XxplARSetupEOImpl object, Object value);

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


    public static final int ARSETUPID = AttributesEnum.ArSetupId.index();
    public static final int BUSINESSUNITID = AttributesEnum.BusinessUnitId.index();
    public static final int LEGALENTITYID = AttributesEnum.LegalEntityId.index();
    public static final int CHARGETYPE = AttributesEnum.ChargeType.index();
    public static final int TXNTYPE = AttributesEnum.TxnType.index();
    public static final int TXNSOURCE = AttributesEnum.TxnSource.index();
    public static final int CMTXNTYPE = AttributesEnum.CmTxnType.index();
    public static final int CMTXNSOURCE = AttributesEnum.CmTxnSource.index();
    public static final int TYPEID = AttributesEnum.TypeId.index();
    public static final int TXNTYPEID = AttributesEnum.TxnTypeId.index();
    public static final int TXNSOURCEID = AttributesEnum.TxnSourceId.index();
    public static final int CMTXNTYPEID = AttributesEnum.CmTxnTypeId.index();
    public static final int CMTXNSOURCEID = AttributesEnum.CmTxnSourceId.index();
    public static final int REVACCOUNTID = AttributesEnum.RevAccountId.index();
    public static final int CONCATENATEDSEGMENT = AttributesEnum.ConcatenatedSegment.index();
    public static final int ACCOUNTINGRULENAME = AttributesEnum.AccountingRuleName.index();
    public static final int INVOICINGRULE = AttributesEnum.InvoicingRule.index();
    public static final int INTERCOMPANYGLCODE = AttributesEnum.IntercompanyGlCode.index();
    public static final int COSTCENTERGLCODE = AttributesEnum.CostCenterGlCode.index();
    public static final int USAGE = AttributesEnum.Usage.index();
    public static final int UNITCATEGORY = AttributesEnum.UnitCategory.index();
    public static final int TAXCODE = AttributesEnum.TaxCode.index();
    public static final int LEDGERNAME = AttributesEnum.LedgerName.index();
    public static final int ATTRIBUTE1 = AttributesEnum.Attribute1.index();
    public static final int UNITMETHOD = AttributesEnum.UnitMethod.index();
    public static final int ATTRIBUTE2 = AttributesEnum.Attribute2.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XxplARSetupEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("custom.lease.model.Entity.XxplARSetupEO");
    }

    /**
     * Gets the attribute value for ArSetupId, using the alias name ArSetupId.
     * @return the ArSetupId
     */
    public Number getArSetupId() {
        return (Number)getAttributeInternal(ARSETUPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ArSetupId.
     * @param value value to set the ArSetupId
     */
    public void setArSetupId(Number value) {
        try {
            setAttributeInternal(ARSETUPID, value);
            
        } catch ( TooManyObjectsException e) {
            // TODO: Add catch code
            e.printStackTrace();
            throw new JboException("Duplicate id");
            
        }
       
    }

    /**
     * Gets the attribute value for BusinessUnitId, using the alias name BusinessUnitId.
     * @return the BusinessUnitId
     */
    public Number getBusinessUnitId() {
        return (Number)getAttributeInternal(BUSINESSUNITID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BusinessUnitId.
     * @param value value to set the BusinessUnitId
     */
    public void setBusinessUnitId(Number value) {
        setAttributeInternal(BUSINESSUNITID, value);
    }

    /**
     * Gets the attribute value for LegalEntityId, using the alias name LegalEntityId.
     * @return the LegalEntityId
     */
    public BigDecimal getLegalEntityId() {
        return (BigDecimal)getAttributeInternal(LEGALENTITYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for LegalEntityId.
     * @param value value to set the LegalEntityId
     */
    public void setLegalEntityId(BigDecimal value) {
        setAttributeInternal(LEGALENTITYID, value);
    }

    /**
     * Gets the attribute value for ChargeType, using the alias name ChargeType.
     * @return the ChargeType
     */
    public String getChargeType() {
        return (String)getAttributeInternal(CHARGETYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ChargeType.
     * @param value value to set the ChargeType
     */
    public void setChargeType(String value) {
        setAttributeInternal(CHARGETYPE, value);
    }

    /**
     * Gets the attribute value for TxnType, using the alias name TxnType.
     * @return the TxnType
     */
    public String getTxnType() {
        return (String)getAttributeInternal(TXNTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TxnType.
     * @param value value to set the TxnType
     */
    public void setTxnType(String value) {
        setAttributeInternal(TXNTYPE, value);
    }

    /**
     * Gets the attribute value for TxnSource, using the alias name TxnSource.
     * @return the TxnSource
     */
    public String getTxnSource() {
        return (String)getAttributeInternal(TXNSOURCE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TxnSource.
     * @param value value to set the TxnSource
     */
    public void setTxnSource(String value) {
        setAttributeInternal(TXNSOURCE, value);
    }

    /**
     * Gets the attribute value for CmTxnType, using the alias name CmTxnType.
     * @return the CmTxnType
     */
    public String getCmTxnType() {
        return (String)getAttributeInternal(CMTXNTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CmTxnType.
     * @param value value to set the CmTxnType
     */
    public void setCmTxnType(String value) {
        setAttributeInternal(CMTXNTYPE, value);
    }

    /**
     * Gets the attribute value for CmTxnSource, using the alias name CmTxnSource.
     * @return the CmTxnSource
     */
    public String getCmTxnSource() {
        return (String)getAttributeInternal(CMTXNSOURCE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CmTxnSource.
     * @param value value to set the CmTxnSource
     */
    public void setCmTxnSource(String value) {
        setAttributeInternal(CMTXNSOURCE, value);
    }

    /**
     * Gets the attribute value for TypeId, using the alias name TypeId.
     * @return the TypeId
     */
    public String getTypeId() {
        return (String)getAttributeInternal(TYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TypeId.
     * @param value value to set the TypeId
     */
    public void setTypeId(String value) {
        setAttributeInternal(TYPEID, value);
    }

    /**
     * Gets the attribute value for TxnTypeId, using the alias name TxnTypeId.
     * @return the TxnTypeId
     */
    public BigDecimal getTxnTypeId() {
        return (BigDecimal)getAttributeInternal(TXNTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TxnTypeId.
     * @param value value to set the TxnTypeId
     */
    public void setTxnTypeId(BigDecimal value) {
        setAttributeInternal(TXNTYPEID, value);
    }

    /**
     * Gets the attribute value for TxnSourceId, using the alias name TxnSourceId.
     * @return the TxnSourceId
     */
    public BigDecimal getTxnSourceId() {
        return (BigDecimal)getAttributeInternal(TXNSOURCEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TxnSourceId.
     * @param value value to set the TxnSourceId
     */
    public void setTxnSourceId(BigDecimal value) {
        setAttributeInternal(TXNSOURCEID, value);
    }

    /**
     * Gets the attribute value for CmTxnTypeId, using the alias name CmTxnTypeId.
     * @return the CmTxnTypeId
     */
    public BigDecimal getCmTxnTypeId() {
        return (BigDecimal)getAttributeInternal(CMTXNTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CmTxnTypeId.
     * @param value value to set the CmTxnTypeId
     */
    public void setCmTxnTypeId(BigDecimal value) {
        setAttributeInternal(CMTXNTYPEID, value);
    }

    /**
     * Gets the attribute value for CmTxnSourceId, using the alias name CmTxnSourceId.
     * @return the CmTxnSourceId
     */
    public BigDecimal getCmTxnSourceId() {
        return (BigDecimal)getAttributeInternal(CMTXNSOURCEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CmTxnSourceId.
     * @param value value to set the CmTxnSourceId
     */
    public void setCmTxnSourceId(BigDecimal value) {
        setAttributeInternal(CMTXNSOURCEID, value);
    }

    /**
     * Gets the attribute value for RevAccountId, using the alias name RevAccountId.
     * @return the RevAccountId
     */
    public BigDecimal getRevAccountId() {
        return (BigDecimal)getAttributeInternal(REVACCOUNTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for RevAccountId.
     * @param value value to set the RevAccountId
     */
    public void setRevAccountId(BigDecimal value) {
        setAttributeInternal(REVACCOUNTID, value);
    }

    /**
     * Gets the attribute value for ConcatenatedSegment, using the alias name ConcatenatedSegment.
     * @return the ConcatenatedSegment
     */
    public String getConcatenatedSegment() {
        return (String)getAttributeInternal(CONCATENATEDSEGMENT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ConcatenatedSegment.
     * @param value value to set the ConcatenatedSegment
     */
    public void setConcatenatedSegment(String value) {
        setAttributeInternal(CONCATENATEDSEGMENT, value);
    }

    /**
     * Gets the attribute value for AccountingRuleName, using the alias name AccountingRuleName.
     * @return the AccountingRuleName
     */
    public String getAccountingRuleName() {
        return (String)getAttributeInternal(ACCOUNTINGRULENAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for AccountingRuleName.
     * @param value value to set the AccountingRuleName
     */
    public void setAccountingRuleName(String value) {
        setAttributeInternal(ACCOUNTINGRULENAME, value);
    }

    /**
     * Gets the attribute value for InvoicingRule, using the alias name InvoicingRule.
     * @return the InvoicingRule
     */
    public String getInvoicingRule() {
        return (String)getAttributeInternal(INVOICINGRULE);
    }

    /**
     * Sets <code>value</code> as the attribute value for InvoicingRule.
     * @param value value to set the InvoicingRule
     */
    public void setInvoicingRule(String value) {
        setAttributeInternal(INVOICINGRULE, value);
    }

    /**
     * Gets the attribute value for IntercompanyGlCode, using the alias name IntercompanyGlCode.
     * @return the IntercompanyGlCode
     */
    public String getIntercompanyGlCode() {
        return (String)getAttributeInternal(INTERCOMPANYGLCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for IntercompanyGlCode.
     * @param value value to set the IntercompanyGlCode
     */
    public void setIntercompanyGlCode(String value) {
        setAttributeInternal(INTERCOMPANYGLCODE, value);
    }

    /**
     * Gets the attribute value for CostCenterGlCode, using the alias name CostCenterGlCode.
     * @return the CostCenterGlCode
     */
    public String getCostCenterGlCode() {
        return (String)getAttributeInternal(COSTCENTERGLCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CostCenterGlCode.
     * @param value value to set the CostCenterGlCode
     */
    public void setCostCenterGlCode(String value) {
        setAttributeInternal(COSTCENTERGLCODE, value);
    }

    /**
     * Gets the attribute value for Usage, using the alias name Usage.
     * @return the Usage
     */
    public String getUsage() {
        return (String)getAttributeInternal(USAGE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Usage.
     * @param value value to set the Usage
     */
    public void setUsage(String value) {
        setAttributeInternal(USAGE, value);
    }

    /**
     * Gets the attribute value for UnitCategory, using the alias name UnitCategory.
     * @return the UnitCategory
     */
    public String getUnitCategory() {
        return (String)getAttributeInternal(UNITCATEGORY);
    }

    /**
     * Sets <code>value</code> as the attribute value for UnitCategory.
     * @param value value to set the UnitCategory
     */
    public void setUnitCategory(String value) {
        setAttributeInternal(UNITCATEGORY, value);
    }

    /**
     * Gets the attribute value for TaxCode, using the alias name TaxCode.
     * @return the TaxCode
     */
    public String getTaxCode() {
        return (String)getAttributeInternal(TAXCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxCode.
     * @param value value to set the TaxCode
     */
    public void setTaxCode(String value) {
        setAttributeInternal(TAXCODE, value);
    }

    /**
     * Gets the attribute value for LedgerName, using the alias name LedgerName.
     * @return the LedgerName
     */
    public String getLedgerName() {
        return (String)getAttributeInternal(LEDGERNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for LedgerName.
     * @param value value to set the LedgerName
     */
    public void setLedgerName(String value) {
        setAttributeInternal(LEDGERNAME, value);
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
     * Gets the attribute value for UnitMethod, using the alias name UnitMethod.
     * @return the UnitMethod
     */
    public String getUnitMethod() {
        return (String)getAttributeInternal(UNITMETHOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UnitMethod.
     * @param value value to set the UnitMethod
     */
    public void setUnitMethod(String value) {
        setAttributeInternal(UNITMETHOD, value);
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
     * @param arSetupId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number arSetupId) {
        return new Key(new Object[]{arSetupId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        
//        SequenceImpl seq = new SequenceImpl("AR_SETUP_ID_S", this.getDBTransaction());
//        this.setArSetupId(seq.getSequenceNumber());
//       
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        try {
                    super.lock();
                } catch (RowInconsistentException e) {
                    refresh(REFRESH_WITH_DB_ONLY_IF_UNCHANGED | REFRESH_CONTAINEES);
                    super.lock();
                }
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if(operation==DML_INSERT)
        this.setArSetupId((new SequenceImpl("AR_SETUP_ID_S",this.getDBTransaction()).getSequenceNumber()));
        super.doDML(operation, e);
    }
}
