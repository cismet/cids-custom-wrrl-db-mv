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
 * <p>Java class for tgYesNo.</p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 *
 * <pre>
   &lt;simpleType name="tgYesNo">
     &lt;restriction base="{http://www.gaeb.de/GAEB_DA_XML/200407}tgNormalizedString">
       &lt;enumeration value="Yes"/>
       &lt;enumeration value="No"/>
     &lt;/restriction>
   &lt;/simpleType>
 * </pre>
 *
 * @version  $Revision$, $Date$
 */
@XmlType(name = "tgYesNo")
@XmlEnum
public enum TgYesNo {

    //~ Enum constants ---------------------------------------------------------

    @XmlEnumValue("Yes")
    YES("Yes"),
    @XmlEnumValue("No")
    NO("No");

    //~ Instance fields --------------------------------------------------------

    private final String value;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TgYesNo object.
     *
     * @param  v  DOCUMENT ME!
     */
    TgYesNo(final String v) {
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
    public static TgYesNo fromValue(final String v) {
        for (final TgYesNo c : TgYesNo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
