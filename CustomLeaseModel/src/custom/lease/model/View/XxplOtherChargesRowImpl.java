package custom.lease.model.View;

import custom.lease.model.Entity.XxplPlLinesOtherChargeImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 21 17:24:45 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxplOtherChargesRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Amount {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAmount();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAmount((BigDecimal)value);
            }
        }
        ,
        Attribute1 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute1();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute1((String)value);
            }
        }
        ,
        Attribute10 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute10();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute10((String)value);
            }
        }
        ,
        Attribute2 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute2();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute2((String)value);
            }
        }
        ,
        Attribute3 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute3();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute3((String)value);
            }
        }
        ,
        Attribute4 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute4();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute4((String)value);
            }
        }
        ,
        Attribute5 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute5();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute5((String)value);
            }
        }
        ,
        Attribute6 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute6();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute6((String)value);
            }
        }
        ,
        Attribute7 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute7();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute7((String)value);
            }
        }
        ,
        Attribute8 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute8();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute8((String)value);
            }
        }
        ,
        Attribute9 {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttribute9();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttribute9((String)value);
            }
        }
        ,
        AttributeCategory {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getAttributeCategory();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttributeCategory((String)value);
            }
        }
        ,
        BuildId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getBuildId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setBuildId((BigDecimal)value);
            }
        }
        ,
        ChargeMethod {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getChargeMethod();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setChargeMethod((String)value);
            }
        }
        ,
        ChargeType {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getChargeType();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setChargeType((String)value);
            }
        }
        ,
        Charges {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getCharges();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setCharges((BigDecimal)value);
            }
        }
        ,
        CreatedBy {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationDate {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getCreationDate();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setCreationDate((Timestamp)value);
            }
        }
        ,
        LastUpdateDate {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getLastUpdateDate();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setLastUpdateDate((Timestamp)value);
            }
        }
        ,
        LastUpdateLogin {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getLastUpdateLogin();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setLastUpdateLogin((String)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setLastUpdatedBy((String)value);
            }
        }
        ,
        ObjectVersion {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getObjectVersion();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setObjectVersion((BigDecimal)value);
            }
        }
        ,
        PlId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getPlId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setPlId((BigDecimal)value);
            }
        }
        ,
        PllId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getPllId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setPllId((BigDecimal)value);
            }
        }
        ,
        PllothId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getPllothId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setPllothId((Number)value);
            }
        }
        ,
        PropertyId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getPropertyId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setPropertyId((BigDecimal)value);
            }
        }
        ,
        UnitId {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getUnitId();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setUnitId((BigDecimal)value);
            }
        }
        ,
        ChargeTypeTrans {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getChargeTypeTrans();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setChargeTypeTrans((String)value);
            }
        }
        ,
        XXFND_LOOKUP_V_ROVO {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getXXFND_LOOKUP_V_ROVO();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        XXFND_LOOKUP_V_ROVO1_DIS {
            public Object get(XxplOtherChargesRowImpl obj) {
                return obj.getXXFND_LOOKUP_V_ROVO1_DIS();
            }

            public void put(XxplOtherChargesRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(XxplOtherChargesRowImpl object);

        public abstract void put(XxplOtherChargesRowImpl object, Object value);

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

    public static final int AMOUNT = AttributesEnum.Amount.index();
    public static final int ATTRIBUTE1 = AttributesEnum.Attribute1.index();
    public static final int ATTRIBUTE10 = AttributesEnum.Attribute10.index();
    public static final int ATTRIBUTE2 = AttributesEnum.Attribute2.index();
    public static final int ATTRIBUTE3 = AttributesEnum.Attribute3.index();
    public static final int ATTRIBUTE4 = AttributesEnum.Attribute4.index();
    public static final int ATTRIBUTE5 = AttributesEnum.Attribute5.index();
    public static final int ATTRIBUTE6 = AttributesEnum.Attribute6.index();
    public static final int ATTRIBUTE7 = AttributesEnum.Attribute7.index();
    public static final int ATTRIBUTE8 = AttributesEnum.Attribute8.index();
    public static final int ATTRIBUTE9 = AttributesEnum.Attribute9.index();
    public static final int ATTRIBUTECATEGORY = AttributesEnum.AttributeCategory.index();
    public static final int BUILDID = AttributesEnum.BuildId.index();
    public static final int CHARGEMETHOD = AttributesEnum.ChargeMethod.index();
    public static final int CHARGETYPE = AttributesEnum.ChargeType.index();
    public static final int CHARGES = AttributesEnum.Charges.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATELOGIN = AttributesEnum.LastUpdateLogin.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int OBJECTVERSION = AttributesEnum.ObjectVersion.index();
    public static final int PLID = AttributesEnum.PlId.index();
    public static final int PLLID = AttributesEnum.PllId.index();
    public static final int PLLOTHID = AttributesEnum.PllothId.index();
    public static final int PROPERTYID = AttributesEnum.PropertyId.index();
    public static final int UNITID = AttributesEnum.UnitId.index();
    public static final int CHARGETYPETRANS = AttributesEnum.ChargeTypeTrans.index();
    public static final int XXFND_LOOKUP_V_ROVO = AttributesEnum.XXFND_LOOKUP_V_ROVO.index();
    public static final int XXFND_LOOKUP_V_ROVO1_DIS = AttributesEnum.XXFND_LOOKUP_V_ROVO1_DIS.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XxplOtherChargesRowImpl() {
    }

    /**
     * Gets XxplPlLinesOtherCharge entity object.
     * @return the XxplPlLinesOtherCharge
     */
    public XxplPlLinesOtherChargeImpl getXxplPlLinesOtherCharge() {
        return (XxplPlLinesOtherChargeImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for AMOUNT using the alias name Amount.
     * @return the AMOUNT
     */
    public BigDecimal getAmount() {
        return (BigDecimal) getAttributeInternal(AMOUNT);
    }

    /**
     * Sets <code>value</code> as attribute value for AMOUNT using the alias name Amount.
     * @param value value to set the AMOUNT
     */
    public void setAmount(BigDecimal value) {
        setAttributeInternal(AMOUNT, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE1 using the alias name Attribute1.
     * @return the ATTRIBUTE1
     */
    public String getAttribute1() {
        return (String) getAttributeInternal(ATTRIBUTE1);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE1 using the alias name Attribute1.
     * @param value value to set the ATTRIBUTE1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE10 using the alias name Attribute10.
     * @return the ATTRIBUTE10
     */
    public String getAttribute10() {
        return (String) getAttributeInternal(ATTRIBUTE10);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE10 using the alias name Attribute10.
     * @param value value to set the ATTRIBUTE10
     */
    public void setAttribute10(String value) {
        setAttributeInternal(ATTRIBUTE10, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE2 using the alias name Attribute2.
     * @return the ATTRIBUTE2
     */
    public String getAttribute2() {
        return (String) getAttributeInternal(ATTRIBUTE2);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE2 using the alias name Attribute2.
     * @param value value to set the ATTRIBUTE2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE3 using the alias name Attribute3.
     * @return the ATTRIBUTE3
     */
    public String getAttribute3() {
        return (String) getAttributeInternal(ATTRIBUTE3);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE3 using the alias name Attribute3.
     * @param value value to set the ATTRIBUTE3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE4 using the alias name Attribute4.
     * @return the ATTRIBUTE4
     */
    public String getAttribute4() {
        return (String) getAttributeInternal(ATTRIBUTE4);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE4 using the alias name Attribute4.
     * @param value value to set the ATTRIBUTE4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE5 using the alias name Attribute5.
     * @return the ATTRIBUTE5
     */
    public String getAttribute5() {
        return (String) getAttributeInternal(ATTRIBUTE5);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE5 using the alias name Attribute5.
     * @param value value to set the ATTRIBUTE5
     */
    public void setAttribute5(String value) {
        setAttributeInternal(ATTRIBUTE5, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE6 using the alias name Attribute6.
     * @return the ATTRIBUTE6
     */
    public String getAttribute6() {
        return (String) getAttributeInternal(ATTRIBUTE6);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE6 using the alias name Attribute6.
     * @param value value to set the ATTRIBUTE6
     */
    public void setAttribute6(String value) {
        setAttributeInternal(ATTRIBUTE6, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE7 using the alias name Attribute7.
     * @return the ATTRIBUTE7
     */
    public String getAttribute7() {
        return (String) getAttributeInternal(ATTRIBUTE7);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE7 using the alias name Attribute7.
     * @param value value to set the ATTRIBUTE7
     */
    public void setAttribute7(String value) {
        setAttributeInternal(ATTRIBUTE7, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE8 using the alias name Attribute8.
     * @return the ATTRIBUTE8
     */
    public String getAttribute8() {
        return (String) getAttributeInternal(ATTRIBUTE8);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE8 using the alias name Attribute8.
     * @param value value to set the ATTRIBUTE8
     */
    public void setAttribute8(String value) {
        setAttributeInternal(ATTRIBUTE8, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE9 using the alias name Attribute9.
     * @return the ATTRIBUTE9
     */
    public String getAttribute9() {
        return (String) getAttributeInternal(ATTRIBUTE9);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE9 using the alias name Attribute9.
     * @param value value to set the ATTRIBUTE9
     */
    public void setAttribute9(String value) {
        setAttributeInternal(ATTRIBUTE9, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory.
     * @return the ATTRIBUTE_CATEGORY
     */
    public String getAttributeCategory() {
        return (String) getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory.
     * @param value value to set the ATTRIBUTE_CATEGORY
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**
     * Gets the attribute value for BUILD_ID using the alias name BuildId.
     * @return the BUILD_ID
     */
    public BigDecimal getBuildId() {
        return (BigDecimal) getAttributeInternal(BUILDID);
    }

    /**
     * Sets <code>value</code> as attribute value for BUILD_ID using the alias name BuildId.
     * @param value value to set the BUILD_ID
     */
    public void setBuildId(BigDecimal value) {
        setAttributeInternal(BUILDID, value);
    }

    /**
     * Gets the attribute value for CHARGE_METHOD using the alias name ChargeMethod.
     * @return the CHARGE_METHOD
     */
    public String getChargeMethod() {
        return (String) getAttributeInternal(CHARGEMETHOD);
    }

    /**
     * Sets <code>value</code> as attribute value for CHARGE_METHOD using the alias name ChargeMethod.
     * @param value value to set the CHARGE_METHOD
     */
    public void setChargeMethod(String value) {
        setAttributeInternal(CHARGEMETHOD, value);
    }

    /**
     * Gets the attribute value for CHARGE_TYPE using the alias name ChargeType.
     * @return the CHARGE_TYPE
     */
    public String getChargeType() {
        return (String) getAttributeInternal(CHARGETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CHARGE_TYPE using the alias name ChargeType.
     * @param value value to set the CHARGE_TYPE
     */
    public void setChargeType(String value) {
        setAttributeInternal(CHARGETYPE, value);
    }

    /**
     * Gets the attribute value for CHARGES using the alias name Charges.
     * @return the CHARGES
     */
    public BigDecimal getCharges() {
        return (BigDecimal) getAttributeInternal(CHARGES);
    }

    /**
     * Sets <code>value</code> as attribute value for CHARGES using the alias name Charges.
     * @param value value to set the CHARGES
     */
    public void setCharges(BigDecimal value) {
        setAttributeInternal(CHARGES, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Timestamp getCreationDate() {
        return (Timestamp) getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Timestamp value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @return the LAST_UPDATE_DATE
     */
    public Timestamp getLastUpdateDate() {
        return (Timestamp) getAttributeInternal(LASTUPDATEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @param value value to set the LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Timestamp value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin.
     * @return the LAST_UPDATE_LOGIN
     */
    public String getLastUpdateLogin() {
        return (String) getAttributeInternal(LASTUPDATELOGIN);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin.
     * @param value value to set the LAST_UPDATE_LOGIN
     */
    public void setLastUpdateLogin(String value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @return the LAST_UPDATED_BY
     */
    public String getLastUpdatedBy() {
        return (String) getAttributeInternal(LASTUPDATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @param value value to set the LAST_UPDATED_BY
     */
    public void setLastUpdatedBy(String value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for OBJECT_VERSION using the alias name ObjectVersion.
     * @return the OBJECT_VERSION
     */
    public BigDecimal getObjectVersion() {
        return (BigDecimal) getAttributeInternal(OBJECTVERSION);
    }

    /**
     * Sets <code>value</code> as attribute value for OBJECT_VERSION using the alias name ObjectVersion.
     * @param value value to set the OBJECT_VERSION
     */
    public void setObjectVersion(BigDecimal value) {
        setAttributeInternal(OBJECTVERSION, value);
    }

    /**
     * Gets the attribute value for PL_ID using the alias name PlId.
     * @return the PL_ID
     */
    public BigDecimal getPlId() {
        return (BigDecimal) getAttributeInternal(PLID);
    }

    /**
     * Sets <code>value</code> as attribute value for PL_ID using the alias name PlId.
     * @param value value to set the PL_ID
     */
    public void setPlId(BigDecimal value) {
        setAttributeInternal(PLID, value);
    }

    /**
     * Gets the attribute value for PLL_ID using the alias name PllId.
     * @return the PLL_ID
     */
    public BigDecimal getPllId() {
        return (BigDecimal) getAttributeInternal(PLLID);
    }

    /**
     * Sets <code>value</code> as attribute value for PLL_ID using the alias name PllId.
     * @param value value to set the PLL_ID
     */
    public void setPllId(BigDecimal value) {
        setAttributeInternal(PLLID, value);
    }

    /**
     * Gets the attribute value for PLLOTH_ID using the alias name PllothId.
     * @return the PLLOTH_ID
     */
    public Number getPllothId() {
        return (Number)getAttributeInternal(PLLOTHID);
    }

    /**
     * Sets <code>value</code> as attribute value for PLLOTH_ID using the alias name PllothId.
     * @param value value to set the PLLOTH_ID
     */
    public void setPllothId(Number value) {
        setAttributeInternal(PLLOTHID, value);
    }

    /**
     * Gets the attribute value for PROPERTY_ID using the alias name PropertyId.
     * @return the PROPERTY_ID
     */
    public BigDecimal getPropertyId() {
        return (BigDecimal) getAttributeInternal(PROPERTYID);
    }

    /**
     * Sets <code>value</code> as attribute value for PROPERTY_ID using the alias name PropertyId.
     * @param value value to set the PROPERTY_ID
     */
    public void setPropertyId(BigDecimal value) {
        setAttributeInternal(PROPERTYID, value);
    }

    /**
     * Gets the attribute value for UNIT_ID using the alias name UnitId.
     * @return the UNIT_ID
     */
    public BigDecimal getUnitId() {
        return (BigDecimal) getAttributeInternal(UNITID);
    }

    /**
     * Sets <code>value</code> as attribute value for UNIT_ID using the alias name UnitId.
     * @param value value to set the UNIT_ID
     */
    public void setUnitId(BigDecimal value) {
        setAttributeInternal(UNITID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ChargeTypeTrans.
     * @return the ChargeTypeTrans
     */
    public String getChargeTypeTrans() {
        String buId = null;
        String departmentName = null;
        if (getChargeType() != null) {
            buId = getChargeType();
            Row[] deptRows;
            deptRows =
                    getXXFND_LOOKUP_V_ROVO().getFilteredRows("LookupValueName",
                                                             buId);
            if (deptRows.length > 0) {
                departmentName =
                        deptRows[0].getAttribute("LookupValueNameDisp").toString();
            }
            return departmentName;
        }
        else {
            return (String) getAttributeInternal(CHARGETYPETRANS);
        }
       
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ChargeTypeTrans.
     * @param value value to set the  ChargeTypeTrans
     */
    public void setChargeTypeTrans(String value) {
        setAttributeInternal(CHARGETYPETRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> XXFND_LOOKUP_V_ROVO.
     */
    public RowSet getXXFND_LOOKUP_V_ROVO() {
        return (RowSet)getAttributeInternal(XXFND_LOOKUP_V_ROVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> XXFND_LOOKUP_V_ROVO1_DIS.
     */
    public RowSet getXXFND_LOOKUP_V_ROVO1_DIS() {
        return (RowSet)getAttributeInternal(XXFND_LOOKUP_V_ROVO1_DIS);
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
}