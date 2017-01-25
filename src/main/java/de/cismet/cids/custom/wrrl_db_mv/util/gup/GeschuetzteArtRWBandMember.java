/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;

import java.beans.PropertyChangeEvent;

import javax.swing.JMenuItem;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GeschuetzteArtRWBandMember extends LineBandMember {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass GESCHUETZTE_ART = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GESCHUETZTE_ART");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public GeschuetzteArtRWBandMember(final GeschuetzteArtRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public GeschuetzteArtRWBandMember(final GeschuetzteArtRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        if (bean.getProperty("art.name") != null) {
            setToolTipText(bean.getProperty("art.name") + "");
        } else {
            setToolTipText("");
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if ((bean.getProperty("art") == null) || (bean.getProperty("art.color") == null)) {
            setDefaultBackground();
            return;
        }

        final String color = (String)bean.getProperty("art.color");

        if (color != null) {
            try {
                setBackgroundPainter(new MattePainter(Color.decode(color)));
            } catch (NumberFormatException e) {
                LOG.error("Error while parsing the color.", e);
                setDefaultBackground();
            }
        }

        unselectedBackgroundPainter = getBackgroundPainter();
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
        if (evt.getPropertyName().equals("art")) {
            determineBackgroundColour();
            setSelected(isSelected);
            if (bean.getProperty("art.name") != null) {
                setToolTipText(bean.getProperty("art.name") + "");
            } else {
                setToolTipText("");
            }
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
}
