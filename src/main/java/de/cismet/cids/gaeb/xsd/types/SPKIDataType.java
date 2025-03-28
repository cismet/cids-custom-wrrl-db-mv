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

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for SPKIDataType complex type.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;complexType name="SPKIDataType">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence maxOccurs="unbounded">
           &lt;element name="SPKISexp" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
           &lt;any processContents='lax' namespace='##other' minOccurs="0"/>
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
    name = "SPKIDataType",
    namespace = "http://www.w3.org/2000/09/xmldsig#",
    propOrder = { "spkiSexpAndAny" }
)
public class SPKIDataType {

    //~ Instance fields --------------------------------------------------------

    @XmlElementRef(
        name = "SPKISexp",
        namespace = "http://www.w3.org/2000/09/xmldsig#",
        type = JAXBElement.class
    )
    @XmlAnyElement(lax = true)
    protected List<Object> spkiSexpAndAny;

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the value of the spkiSexpAndAny property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make
     * to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method
     * for the spkiSexpAndAny property.</p>
     *
     * <p>For example, to add a new item, do as follows:</p>
     *
     * <pre>
          getSPKISexpAndAny().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Object } {@link Element } {@link JAXBElement }
     * {@code <}{@link byte[]}{@code >}</p>
     *
     * @return  DOCUMENT ME!
     */
    public List<Object> getSPKISexpAndAny() {
        if (spkiSexpAndAny == null) {
            spkiSexpAndAny = new ArrayList<Object>();
        }
        return this.spkiSexpAndAny;
    }
}
