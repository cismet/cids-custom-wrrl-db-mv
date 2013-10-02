/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;

import java.beans.PropertyChangeEvent;

import de.cismet.cids.custom.wrrl_db_mv.util.gup.LineBandMember;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RestriktionRWBandMember extends LineBandMember {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public RestriktionRWBandMember(final RestriktionRWBand parent) {
        super(parent);
        lineFieldName = "ausdehnung";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public RestriktionRWBandMember(final RestriktionRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "ausdehnung";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        String name = getKind();
        if ((name == null) || name.equals("null")) {
            name = "";
        }

        setToolTipText(name);
//        setText(name);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if (bean.getProperty("restriktion") == null) {
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
        if (evt.getPropertyName().equals("restriktion")) {
            String name = getKind();
            determineBackgroundColour();
            setSelected(isSelected);

            if (name == null) {
                name = "unbekannt";
            }
            setToolTipText(name);
//            setText(name);
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
    private String getKind() {
        final String kind = (String)bean.getProperty("restriktion.name");

        if (kind != null) {
            return kind;
        } else {
            return "";
        }
    }
}
