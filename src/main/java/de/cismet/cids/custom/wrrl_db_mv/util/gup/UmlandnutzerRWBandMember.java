/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;

import java.beans.PropertyChangeEvent;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UmlandnutzerRWBandMember extends LineBandMember {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public UmlandnutzerRWBandMember(final UmlandnutzerRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public UmlandnutzerRWBandMember(final UmlandnutzerRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        String name = getUserName();
        if (name == null) {
            name = "unbekannt";
        }

        setToolTipText(name);
        setText(name);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if (bean.getProperty("nutzer") == null) {
            setDefaultBackground();
            return;
        }

        unselectedBackgroundPainter = (new MattePainter(new Color(153, 204, 255)));

        selectedBackgroundPainter = new CompoundPainter(
                unselectedBackgroundPainter,
                new RectanglePainter(
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    true,
                    new Color(100, 100, 100, 100),
                    2f,
                    new Color(50, 50, 50, 100)));
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("nutzer")) {
            String name = getUserName();
            determineBackgroundColour();
            setSelected(isSelected);

            if (name == null) {
                name = "unbekannt";
            }
            setToolTipText(name);
            setText(name);
        } else {
            super.propertyChange(evt);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    @Override
    protected CidsBean cloneBean(final CidsBean bean) throws Exception {
        return CidsBeanSupport.cloneCidsBean(bean, false);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getUserName() {
        String name = null;
        if ((bean.getProperty("nutzer.vorname") != null) && (bean.getProperty("nutzer.name") != null)) {
            name = bean.getProperty("nutzer.vorname") + " " + bean.getProperty("nutzer.name");
        } else if (bean.getProperty("nutzer.name") != null) {
            name = (String)bean.getProperty("nutzer.name");
        }

        return name;
    }
}
