/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import net.infonode.gui.Colors;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;

import java.awt.Color;
import java.awt.Dimension;

import de.cismet.cids.custom.wrrl_db_mv.util.gup.UmlandnutzungsBand.Seite;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class UmlandnutzungsMember extends AbschnittsinfoMember {

    //~ Instance fields --------------------------------------------------------

    String nutzung;
    int nutzungsart;
    UmlandnutzungsBand.Seite s;
    Painter[] painters = {
            new MattePainter(new Color(83, 133, 0)),
            new MattePainter(new Color(68, 169, 78)),
            new MattePainter(new Color(58, 114, 69)),
            new MattePainter(new Color(76, 198, 40)),
            new MattePainter(new Color(145, 103, 22)),
            new MattePainter(new Color(169, 207, 140)),
            new MattePainter(new Color(167, 214, 7)),
            new MattePainter(new Color(99, 43, 0)),
            new MattePainter(new Color(250, 103, 15)),
            new MattePainter(new Color(40, 228, 6)),
            new CompoundPainter(new MattePainter(new Color(131, 134, 137)),
                new PinstripePainter(new Color(167, 214, 7), 45d, 2, 5)),
            new MattePainter(new Color(131, 134, 137)),
            new MattePainter(new Color(131, 134, 137)),
            new MattePainter(new Color(56, 176, 255)),
            new MattePainter(new Color(131, 134, 137)),
        };

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsMember object.
     *
     * @param  s  DOCUMENT ME!
     */
    public UmlandnutzungsMember(final Seite s) {
        this.s = s;
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);

        nutzung = String.valueOf(cidsBean.getProperty("umlandnutzung.name"));
        nutzungsart = (Integer)cidsBean.getProperty("umlandnutzung.id");
        setToolTipText(nutzung);
        setBackgroundPainter(painters[nutzungsart - 1]);
    }
}
