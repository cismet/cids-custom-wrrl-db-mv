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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Informationen zur Adresse.
 *
 * <p>Java class for tgAddress complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgAddress">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="Name1" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40"/>
           &lt;element name="Name2" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="Name3" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="Name4" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="Street" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="PCode" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString20"/>
           &lt;element name="City" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="Country" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;choice minOccurs="0">
             &lt;element name="ILN" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString20"/>
             &lt;element name="CtlgAssign" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgCtlgAssign" maxOccurs="unbounded"/>
           &lt;/choice>
           &lt;element name="Contact" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString40" minOccurs="0"/>
           &lt;element name="Phone" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString20" minOccurs="0"/>
           &lt;element name="Fax" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString20" minOccurs="0"/>
           &lt;element name="Email" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString256" minOccurs="0"/>
           &lt;element name="VATID" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString80" minOccurs="0"/>
           &lt;element name="Bank" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgBank" maxOccurs="unbounded" minOccurs="0"/>
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
    name = "tgAddress",
    propOrder = {
            "name1",
            "name2",
            "name3",
            "name4",
            "street",
            "pCode",
            "city",
            "country",
            "iln",
            "ctlgAssign",
            "contact",
            "phone",
            "fax",
            "email",
            "vatid",
            "bank"
        }
)
public class TgAddress {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(
        name = "Name1",
        required = true
    )
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String name1;
    @XmlElement(name = "Name2")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String name2;
    @XmlElement(name = "Name3")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String name3;
    @XmlElement(name = "Name4")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String name4;
    @XmlElement(name = "Street")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String street;
    @XmlElement(
        name = "PCode",
        required = true
    )
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pCode;
    @XmlElement(name = "City")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String city;
    @XmlElement(name = "Country")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String country;
    @XmlElement(name = "ILN")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String iln;
    @XmlElement(name = "CtlgAssign")
    protected List<TgCtlgAssign> ctlgAssign;
    @XmlElement(name = "Contact")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String contact;
    @XmlElement(name = "Phone")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String phone;
    @XmlElement(name = "Fax")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String fax;
    @XmlElement(name = "Email")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String email;
    @XmlElement(name = "VATID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String vatid;
    @XmlElement(name = "Bank")
    protected List<TgBank> bank;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the name1 property.
     *
     * @return  possible object is {@link String }
     */
    public String getName1() {
        return name1;
    }

    /**
     * Sets the value of the name1 property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setName1(final String value) {
        this.name1 = value;
    }

    /**
     * Gets the value of the name2 property.
     *
     * @return  possible object is {@link String }
     */
    public String getName2() {
        return name2;
    }

    /**
     * Sets the value of the name2 property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setName2(final String value) {
        this.name2 = value;
    }

    /**
     * Gets the value of the name3 property.
     *
     * @return  possible object is {@link String }
     */
    public String getName3() {
        return name3;
    }

    /**
     * Sets the value of the name3 property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setName3(final String value) {
        this.name3 = value;
    }

    /**
     * Gets the value of the name4 property.
     *
     * @return  possible object is {@link String }
     */
    public String getName4() {
        return name4;
    }

    /**
     * Sets the value of the name4 property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setName4(final String value) {
        this.name4 = value;
    }

    /**
     * Gets the value of the street property.
     *
     * @return  possible object is {@link String }
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setStreet(final String value) {
        this.street = value;
    }

    /**
     * Gets the value of the pCode property.
     *
     * @return  possible object is {@link String }
     */
    public String getPCode() {
        return pCode;
    }

    /**
     * Sets the value of the pCode property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setPCode(final String value) {
        this.pCode = value;
    }

    /**
     * Gets the value of the city property.
     *
     * @return  possible object is {@link String }
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setCity(final String value) {
        this.city = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return  possible object is {@link String }
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setCountry(final String value) {
        this.country = value;
    }

    /**
     * Gets the value of the iln property.
     *
     * @return  possible object is {@link String }
     */
    public String getILN() {
        return iln;
    }

    /**
     * Sets the value of the iln property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setILN(final String value) {
        this.iln = value;
    }

    /**
     * Gets the value of the ctlgAssign property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the ctlgAssign property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getCtlgAssign().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link TgCtlgAssign }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<TgCtlgAssign> getCtlgAssign() {
        if (ctlgAssign == null) {
            ctlgAssign = new ArrayList<TgCtlgAssign>();
        }
        return this.ctlgAssign;
    }

    /**
     * Gets the value of the contact property.
     *
     * @return  possible object is {@link String }
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setContact(final String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the phone property.
     *
     * @return  possible object is {@link String }
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setPhone(final String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the fax property.
     *
     * @return  possible object is {@link String }
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setFax(final String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return  possible object is {@link String }
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setEmail(final String value) {
        this.email = value;
    }

    /**
     * Gets the value of the vatid property.
     *
     * @return  possible object is {@link String }
     */
    public String getVATID() {
        return vatid;
    }

    /**
     * Sets the value of the vatid property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setVATID(final String value) {
        this.vatid = value;
    }

    /**
     * Gets the value of the bank property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the bank property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getBank().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link TgBank }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<TgBank> getBank() {
        if (bank == null) {
            bank = new ArrayList<TgBank>();
        }
        return this.bank;
    }
}