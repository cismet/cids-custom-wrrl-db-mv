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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for tgStLNo complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgStLNo">
     &lt;simpleContent>
       &lt;extension base="&lt;http://www.gaeb.de/GAEB_DA_XML/200407>tgStLNoText">
         &lt;attribute name="Type">
           &lt;simpleType>
             &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
               &lt;enumeration value="STLB-BauZ"/>
               &lt;enumeration value="StLB"/>
               &lt;enumeration value="StLK"/>
             &lt;/restriction>
           &lt;/simpleType>
         &lt;/attribute>
       &lt;/extension>
     &lt;/simpleContent>
   &lt;/complexType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tgStLNo",
    propOrder = { "value" }
)
public class TgStLNo {

    //~ Instance fields --------------------------------------------------------

    @XmlValue
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String value;
    @XmlAttribute(name = "Type")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String type;

    //~ Methods ----------------------------------------------------------------

    /**
     * Standardleistungsnummer (StLB/STLK).
     *
     * @return  possible object is {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return  possible object is {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setType(final String value) {
        this.type = value;
    }
}