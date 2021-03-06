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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Tabellenzeile.
 *
 * <p>Java class for tgtr complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgtr">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence maxOccurs="unbounded">
           &lt;element name="th" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgth" maxOccurs="unbounded" minOccurs="0"/>
           &lt;element name="td" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgtd" maxOccurs="unbounded" minOccurs="0"/>
         &lt;/sequence>
         &lt;attribute name="align" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttAlign" />
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
    name = "tgtr",
    propOrder = { "thAndTd" }
)
public class Tgtr {

    //~ Instance fields --------------------------------------------------------

    @XmlElements(
        {
            @XmlElement(
                name = "th",
                type = Tgth.class
            ),
            @XmlElement(
                name = "td",
                type = Tgtd.class
            )
        }
    )
    protected List<Object> thAndTd;
    @XmlAttribute(name = "align")
    protected TgAttAlign align;
    @XmlAttribute(name = "style")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String style;
    @XmlAttribute(name = "valign")
    protected TgAttValign valign;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the thAndTd property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the thAndTd property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getThAndTd().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Tgth } {@link Tgtd }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<Object> getThAndTd() {
        if (thAndTd == null) {
            thAndTd = new ArrayList<Object>();
        }
        return this.thAndTd;
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
