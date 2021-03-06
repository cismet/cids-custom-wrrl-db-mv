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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for tgLeftRight.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;simpleType name="tgLeftRight">
     &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
       &lt;enumeration value="left"/>
       &lt;enumeration value="right"/>
     &lt;/restriction>
   &lt;/simpleType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlType(name = "tgLeftRight")
@XmlEnum
public enum TgLeftRight {

    //~ Enum constants ---------------------------------------------------------

    @XmlEnumValue("left")
    LEFT("left"),
    @XmlEnumValue("right")
    RIGHT("right");

    //~ Instance fields --------------------------------------------------------

    private final String value;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TgLeftRight object.
     *
     * @param  v  DOCUMENT ME!
     */
    TgLeftRight(final String v) {
        value = v;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String value() {
        return value;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   v  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public static TgLeftRight fromValue(final String v) {
        for (final TgLeftRight c : TgLeftRight.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
