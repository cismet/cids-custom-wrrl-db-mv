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

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Zelle einer Tabelle.
 *
 * <p>Java class for tgtd complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgtd">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;choice maxOccurs="unbounded" minOccurs="0">
           &lt;element name="p" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgp"/>
           &lt;element name="div" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgdiv"/>
           &lt;element name="span" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgspan"/>
           &lt;element name="br" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgbr"/>
           &lt;element name="table" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgtable" maxOccurs="unbounded"/>
         &lt;/choice>
         &lt;attribute name="align" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttAlign" />
         &lt;attribute name="colspan" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttColspan" />
         &lt;attribute name="rowspan" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttRowspan" />
         &lt;attribute name="style" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttStyle" />
         &lt;attribute name="valign" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttValign" />
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tgtd",
    propOrder = { "pOrDivOrSpan" }
)
public class Tgtd {

    //~ Instance fields --------------------------------------------------------

    @XmlElementRefs(
        {
            @XmlElementRef(
                name = "span",
                namespace = "http://www.gaeb.de/GAEB_DA_XML/200407",
                type = JAXBElement.class
            ),
            @XmlElementRef(
                name = "table",
                namespace = "http://www.gaeb.de/GAEB_DA_XML/200407",
                type = JAXBElement.class
            ),
            @XmlElementRef(
                name = "p",
                namespace = "http://www.gaeb.de/GAEB_DA_XML/200407",
                type = JAXBElement.class
            ),
            @XmlElementRef(
                name = "br",
                namespace = "http://www.gaeb.de/GAEB_DA_XML/200407",
                type = JAXBElement.class
            ),
            @XmlElementRef(
                name = "div",
                namespace = "http://www.gaeb.de/GAEB_DA_XML/200407",
                type = JAXBElement.class
            )
        }
    )
    protected List<JAXBElement<?>> pOrDivOrSpan;
    @XmlAttribute(name = "align")
    protected TgAttAlign align;
    @XmlAttribute(name = "colspan")
    protected BigInteger colspan;
    @XmlAttribute(name = "rowspan")
    protected BigInteger rowspan;
    @XmlAttribute(name = "style")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String style;
    @XmlAttribute(name = "valign")
    protected TgAttValign valign;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the pOrDivOrSpan property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the pOrDivOrSpan property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getPOrDivOrSpan().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Tgdiv }{@code >} {@link JAXBElement }{@code <}{@link Tgspan }{@code >}
     * {@link JAXBElement }{@code <}{@link Tgtable }{@code >} {@link JAXBElement }{@code <}{@link Tgp }{@code >}</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<JAXBElement<?>> getPOrDivOrSpan() {
        if (pOrDivOrSpan == null) {
            pOrDivOrSpan = new ArrayList<JAXBElement<?>>();
        }
        return this.pOrDivOrSpan;
    }

    /**
     * Gets the value of the align property.
     *
     * @return  possible object is {@link TgAttAlign }
     */
    public TgAttAlign getAlign() {
        return align;
    }

    /**
     * Sets the value of the align property.
     *
     * @param  value  allowed object is {@link TgAttAlign }
     */
    public void setAlign(final TgAttAlign value) {
        this.align = value;
    }

    /**
     * Gets the value of the colspan property.
     *
     * @return  possible object is {@link BigInteger }
     */
    public BigInteger getColspan() {
        return colspan;
    }

    /**
     * Sets the value of the colspan property.
     *
     * @param  value  allowed object is {@link BigInteger }
     */
    public void setColspan(final BigInteger value) {
        this.colspan = value;
    }

    /**
     * Gets the value of the rowspan property.
     *
     * @return  possible object is {@link BigInteger }
     */
    public BigInteger getRowspan() {
        return rowspan;
    }

    /**
     * Sets the value of the rowspan property.
     *
     * @param  value  allowed object is {@link BigInteger }
     */
    public void setRowspan(final BigInteger value) {
        this.rowspan = value;
    }

    /**
     * Gets the value of the style property.
     *
     * @return  possible object is {@link String }
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setStyle(final String value) {
        this.style = value;
    }

    /**
     * Gets the value of the valign property.
     *
     * @return  possible object is {@link TgAttValign }
     */
    public TgAttValign getValign() {
        return valign;
    }

    /**
     * Sets the value of the valign property.
     *
     * @param  value  allowed object is {@link TgAttValign }
     */
    public void setValign(final TgAttValign value) {
        this.valign = value;
    }
}