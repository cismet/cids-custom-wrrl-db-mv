/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.11.23 at 11:39:14 AM CET
//
package de.cismet.cids.gaeb.xsd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Nur bei Instandhaltung: Informationen zur Instandhaltung
 *
 * <p>Java class for tgMaintInfo complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgMaintInfo">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="MaintType">
             &lt;simpleType>
               &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
                 &lt;enumeration value="Maint"/>
                 &lt;enumeration value="Repair"/>
                 &lt;enumeration value="Modern"/>
                 &lt;enumeration value="Warr"/>
                 &lt;enumeration value="Cancelorder"/>
               &lt;/restriction>
             &lt;/simpleType>
           &lt;/element>
           &lt;element name="ProcessType">
             &lt;simpleType>
               &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
                 &lt;enumeration value="StandCon"/>
                 &lt;enumeration value="InsurClaim"/>
                 &lt;enumeration value="InvoicToTen"/>
                 &lt;enumeration value="FinApartInsp"/>
                 &lt;enumeration value="ThirdPartyBill"/>
               &lt;/restriction>
             &lt;/simpleType>
           &lt;/element>
           &lt;sequence minOccurs="0">
             &lt;element name="ContrTransDate" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgDate"/>
             &lt;element name="ContrTransTime" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgTime" minOccurs="0"/>
           &lt;/sequence>
           &lt;element name="ContrLaw" minOccurs="0">
             &lt;simpleType>
               &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
                 &lt;enumeration value="OwnForOwn"/>
                 &lt;enumeration value="OwnForThi"/>
                 &lt;enumeration value="ThiForThi"/>
               &lt;/restriction>
             &lt;/simpleType>
           &lt;/element>
           &lt;element name="Deadline" minOccurs="0">
             &lt;simpleType>
               &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
                 &lt;enumeration value="Imm"/>
                 &lt;enumeration value="Rush"/>
                 &lt;enumeration value="AsArr"/>
               &lt;/restriction>
             &lt;/simpleType>
           &lt;/element>
         &lt;/sequence>
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tgMaintInfo",
    propOrder = {
            "maintType",
            "processType",
            "contrTransDate",
            "contrTransTime",
            "contrLaw",
            "deadline"
        }
)
public class TgMaintInfo {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(
        name = "MaintType",
        required = true
    )
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String maintType;
    @XmlElement(
        name = "ProcessType",
        required = true
    )
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String processType;
    @XmlElement(name = "ContrTransDate")
    protected XMLGregorianCalendar contrTransDate;
    @XmlElement(name = "ContrTransTime")
    protected XMLGregorianCalendar contrTransTime;
    @XmlElement(name = "ContrLaw")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String contrLaw;
    @XmlElement(name = "Deadline")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String deadline;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the maintType property.
     *
     * @return  possible object is {@link String }
     */
    public String getMaintType() {
        return maintType;
    }

    /**
     * Sets the value of the maintType property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setMaintType(final String value) {
        this.maintType = value;
    }

    /**
     * Gets the value of the processType property.
     *
     * @return  possible object is {@link String }
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * Sets the value of the processType property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setProcessType(final String value) {
        this.processType = value;
    }

    /**
     * Gets the value of the contrTransDate property.
     *
     * @return  possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getContrTransDate() {
        return contrTransDate;
    }

    /**
     * Sets the value of the contrTransDate property.
     *
     * @param  value  allowed object is {@link XMLGregorianCalendar }
     */
    public void setContrTransDate(final XMLGregorianCalendar value) {
        this.contrTransDate = value;
    }

    /**
     * Gets the value of the contrTransTime property.
     *
     * @return  possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getContrTransTime() {
        return contrTransTime;
    }

    /**
     * Sets the value of the contrTransTime property.
     *
     * @param  value  allowed object is {@link XMLGregorianCalendar }
     */
    public void setContrTransTime(final XMLGregorianCalendar value) {
        this.contrTransTime = value;
    }

    /**
     * Gets the value of the contrLaw property.
     *
     * @return  possible object is {@link String }
     */
    public String getContrLaw() {
        return contrLaw;
    }

    /**
     * Sets the value of the contrLaw property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setContrLaw(final String value) {
        this.contrLaw = value;
    }

    /**
     * Gets the value of the deadline property.
     *
     * @return  possible object is {@link String }
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the value of the deadline property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setDeadline(final String value) {
        this.deadline = value;
    }
}