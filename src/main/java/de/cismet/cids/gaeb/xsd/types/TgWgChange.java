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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Lohnaenderung.
 *
 * <p>Java class for tgWgChange complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgWgChange">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="LblRefWage" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString120"/>
           &lt;element name="RedPriceComp" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgDecimal_5_2" minOccurs="0"/>
           &lt;sequence maxOccurs="unbounded">
             &lt;choice maxOccurs="unbounded">
               &lt;element name="RefBoQ" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgRefBoQ"/>
               &lt;element name="RefLotNo" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgRefBoQCtgy"/>
               &lt;element name="RefLotGrNo" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgRefLotGrp"/>
               &lt;element name="RefItem" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgRefItem"/>
             &lt;/choice>
             &lt;element name="WgChangeRate" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgDecimal_6_4" minOccurs="0"/>
           &lt;/sequence>
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
    name = "tgWgChange",
    propOrder = {
            "lblRefWage",
            "redPriceComp",
            "refBoQOrRefLotNoOrRefLotGrNo"
        }
)
public class TgWgChange {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(
        name = "LblRefWage",
        required = true
    )
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lblRefWage;
    @XmlElement(name = "RedPriceComp")
    protected BigDecimal redPriceComp;
    @XmlElements(
        {
            @XmlElement(
                name = "RefBoQ",
                type = TgRefBoQ.class
            ),
            @XmlElement(
                name = "RefLotNo",
                type = TgRefBoQCtgy.class
            ),
            @XmlElement(
                name = "RefLotGrNo",
                type = TgRefLotGrp.class
            ),
            @XmlElement(
                name = "RefItem",
                type = TgRefItem.class
            ),
            @XmlElement(
                name = "WgChangeRate",
                type = BigDecimal.class
            )
        }
    )
    protected List<Object> refBoQOrRefLotNoOrRefLotGrNo;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the lblRefWage property.
     *
     * @return  possible object is {@link String }
     */
    public String getLblRefWage() {
        return lblRefWage;
    }

    /**
     * Sets the value of the lblRefWage property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setLblRefWage(final String value) {
        this.lblRefWage = value;
    }

    /**
     * Gets the value of the redPriceComp property.
     *
     * @return  possible object is {@link BigDecimal }
     */
    public BigDecimal getRedPriceComp() {
        return redPriceComp;
    }

    /**
     * Sets the value of the redPriceComp property.
     *
     * @param  value  allowed object is {@link BigDecimal }
     */
    public void setRedPriceComp(final BigDecimal value) {
        this.redPriceComp = value;
    }

    /**
     * Gets the value of the refBoQOrRefLotNoOrRefLotGrNo property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the refBoQOrRefLotNoOrRefLotGrNo property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getRefBoQOrRefLotNoOrRefLotGrNo().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link TgRefBoQ } {@link TgRefBoQCtgy }
     * {@link TgRefLotGrp } {@link TgRefItem } {@link BigDecimal }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<Object> getRefBoQOrRefLotNoOrRefLotGrNo() {
        if (refBoQOrRefLotNoOrRefLotGrNo == null) {
            refBoQOrRefLotNoOrRefLotGrNo = new ArrayList<Object>();
        }
        return this.refBoQOrRefLotNoOrRefLotGrNo;
    }
}