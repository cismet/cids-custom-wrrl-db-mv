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
 * Informationen zum Ausfuehrungsort.
 *
 * <p>Java class for tgCnstSite complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgCnstSite">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="Address" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAddress"/>
           &lt;element name="CnstSiteIDNo" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString20" minOccurs="0"/>
           &lt;element name="CnstSiteName" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString60" minOccurs="0"/>
           &lt;element name="AddText" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAddText" maxOccurs="unbounded" minOccurs="0"/>
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
    name = "tgCnstSite",
    propOrder = {
            "address",
            "cnstSiteIDNo",
            "cnstSiteName",
            "addText"
        }
)
public class TgCnstSite {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(
        name = "Address",
        required = true
    )
    protected TgAddress address;
    @XmlElement(name = "CnstSiteIDNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String cnstSiteIDNo;
    @XmlElement(name = "CnstSiteName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String cnstSiteName;
    @XmlElement(name = "AddText")
    protected List<TgAddText> addText;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the address property.
     *
     * @return  possible object is {@link TgAddress }
     */
    public TgAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param  value  allowed object is {@link TgAddress }
     */
    public void setAddress(final TgAddress value) {
        this.address = value;
    }

    /**
     * Gets the value of the cnstSiteIDNo property.
     *
     * @return  possible object is {@link String }
     */
    public String getCnstSiteIDNo() {
        return cnstSiteIDNo;
    }

    /**
     * Sets the value of the cnstSiteIDNo property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setCnstSiteIDNo(final String value) {
        this.cnstSiteIDNo = value;
    }

    /**
     * Gets the value of the cnstSiteName property.
     *
     * @return  possible object is {@link String }
     */
    public String getCnstSiteName() {
        return cnstSiteName;
    }

    /**
     * Sets the value of the cnstSiteName property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setCnstSiteName(final String value) {
        this.cnstSiteName = value;
    }

    /**
     * Gets the value of the addText property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the addText property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getAddText().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link TgAddText }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<TgAddText> getAddText() {
        if (addText == null) {
            addText = new ArrayList<TgAddText>();
        }
        return this.addText;
    }
}
