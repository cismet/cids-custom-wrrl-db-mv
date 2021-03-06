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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Aufgestellt vom Gemeinsamen Ausschuss Elektronik im Bauwesen (GAEB) © 2002 - 2007by DIN Deutsches Institut fuer
 * Normung e. V.
 *
 * <p>Java class for tgGAEB complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgGAEB">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="GAEBInfo" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgGAEBInfo"/>
           &lt;element name="PrjInfo" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgPrjInfo" minOccurs="0"/>
           &lt;element name="Award" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAward"/>
           &lt;element name="AddText" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAddText" maxOccurs="unbounded" minOccurs="0"/>
           &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" maxOccurs="unbounded" minOccurs="0"/>
         &lt;/sequence>
         &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tgGAEB",
    propOrder = {
            "gaebInfo",
            "prjInfo",
            "award",
            "addText",
            "signature"
        }
)
public class TgGAEB {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(
        name = "GAEBInfo",
        required = true
    )
    protected TgGAEBInfo gaebInfo;
    @XmlElement(name = "PrjInfo")
    protected TgPrjInfo prjInfo;
    @XmlElement(
        name = "Award",
        required = true
    )
    protected TgAward award;
    @XmlElement(name = "AddText")
    protected List<TgAddText> addText;
    @XmlElement(
        name = "Signature",
        namespace = "http://www.w3.org/2000/09/xmldsig#"
    )
    protected List<SignatureType> signature;
    @XmlAttribute(
        name = "space",
        namespace = "http://www.w3.org/XML/1998/namespace"
    )
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String space;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the gaebInfo property.
     *
     * @return  possible object is {@link TgGAEBInfo }
     */
    public TgGAEBInfo getGAEBInfo() {
        return gaebInfo;
    }

    /**
     * Sets the value of the gaebInfo property.
     *
     * @param  value  allowed object is {@link TgGAEBInfo }
     */
    public void setGAEBInfo(final TgGAEBInfo value) {
        this.gaebInfo = value;
    }

    /**
     * Gets the value of the prjInfo property.
     *
     * @return  possible object is {@link TgPrjInfo }
     */
    public TgPrjInfo getPrjInfo() {
        return prjInfo;
    }

    /**
     * Sets the value of the prjInfo property.
     *
     * @param  value  allowed object is {@link TgPrjInfo }
     */
    public void setPrjInfo(final TgPrjInfo value) {
        this.prjInfo = value;
    }

    /**
     * Gets the value of the award property.
     *
     * @return  possible object is {@link TgAward }
     */
    public TgAward getAward() {
        return award;
    }

    /**
     * Sets the value of the award property.
     *
     * @param  value  allowed object is {@link TgAward }
     */
    public void setAward(final TgAward value) {
        this.award = value;
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

    /**
     * Gets the value of the signature property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the signature property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getSignature().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link SignatureType }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<SignatureType> getSignature() {
        if (signature == null) {
            signature = new ArrayList<SignatureType>();
        }
        return this.signature;
    }

    /**
     * Gets the value of the space property.
     *
     * @return  possible object is {@link String }
     */
    public String getSpace() {
        if (space == null) {
            return "preserve";
        } else {
            return space;
        }
    }

    /**
     * Sets the value of the space property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setSpace(final String value) {
        this.space = value;
    }
}
