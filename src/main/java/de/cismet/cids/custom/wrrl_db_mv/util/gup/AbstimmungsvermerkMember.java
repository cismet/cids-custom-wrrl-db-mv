/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;

import java.awt.Color;
import java.awt.Dimension;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class AbstimmungsvermerkMember extends AbschnittsinfoMember {

    //~ Instance fields --------------------------------------------------------

    String was;
    String wer;
    int typ;
    Painter[] painters = {
            new MattePainter(new Color(0xFE932F)),
            new MattePainter(new Color(0xC9F49A)),
            new MattePainter(new Color(0xFF003F)),
        };

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstimmungsvermerkMember object.
     */
    public AbstimmungsvermerkMember() {
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);

        was = String.valueOf(cidsBean.getProperty("was"));
        wer = String.valueOf(cidsBean.getProperty("wer"));
        typ = (Integer)cidsBean.getProperty("status.id");
        setToolTipText(was + "(" + wer + ")");
        setBackgroundPainter(painters[typ - 2]);
        System.out.println(was);
    }
}
