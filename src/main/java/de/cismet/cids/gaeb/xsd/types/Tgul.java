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
 * Aufzählungsliste in formatiertem Text.
 *
 * <p>Java class for tgul complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="tgul">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;choice maxOccurs="unbounded" minOccurs="0">
           &lt;element name="li" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgli" maxOccurs="unbounded"/>
           &lt;element name="ul" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgul" maxOccurs="unbounded" minOccurs="0"/>
           &lt;element name="ol" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgol" maxOccurs="unbounded" minOccurs="0"/>
         &lt;/choice>
         &lt;attribute name="style" type="{http://www.gaeb.de/GAEB_DA_XML/200407}tgAttStyle" />
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tgul",
    propOrder = { "liOrUlOrOl" }
)
public class Tgul {

    //~ Instance fields --------------------------------------------------------

    @XmlElements(
        {
            @XmlElement(
                name = "li",
                type = Tgli.class
            ),
            @XmlElement(
                name = "ul",
                type = Tgul.class
            ),
            @XmlElement(
                name = "ol",
                type = Tgol.class
            )
        }
    )
    protected List<Object> liOrUlOrOl;
    @XmlAttribute(name = "style")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String style;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the liOrUlOrOl property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the liOrUlOrOl property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getLiOrUlOrOl().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Tgli } {@link Tgul } {@link Tgol }</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<Object> getLiOrUlOrOl() {
        if (liOrUlOrOl == null) {
            liOrUlOrOl = new ArrayList<Object>();
        }
        return this.liOrUlOrOl;
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
}
