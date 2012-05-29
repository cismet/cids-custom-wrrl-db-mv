/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.ActionEvent;

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
public class MassnahmenBandMember extends LineBandMember {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass MASSNAHMEN_ART = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_massnahmenart");

    //~ Instance fields --------------------------------------------------------

    private JMenuItem maehItem = new JMenuItem("Böschungsmahd");
    private JMenuItem handItem = new JMenuItem("Sohlkrautung");
//    private JMenuItem grundItem = new JMenuItem("Grundräumung");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public MassnahmenBandMember(final MassnahmenBand parent) {
        super(parent);
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public MassnahmenBandMember(final MassnahmenBand parent, final boolean readOnly) {
        super(parent, readOnly);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        setToolTipText(bean.getProperty("massnahme.name") + "");
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if (bean.getProperty("massnahme") == null) {
            return;
        }
        final int action = (Integer)bean.getProperty("massnahme.id");

        switch (action) {
            case 1: {
                // Mahd mit Mäh-, Harkkombination
                setBackgroundPainter(new MattePainter(new Color(229, 252, 194)));
                break;
            }
            case 2: {
                // Mahd mit Schlägelmähwerk
                setBackgroundPainter(new MattePainter(new Color(69, 173, 168)));
                break;
            }
            case 3: {
                // Mähkorb
                setBackgroundPainter(new MattePainter(new Color(69, 173, 168)));
                break;
            }
            case 4: {
                // Handmahd
                setBackgroundPainter(new MattePainter(new Color(229, 252, 194)));

                break;
            }
            case 5: {
                // Mähboot
                setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(157, 224, 173)), stripes));
                break;
            }
            case 6: {
                // Grundräumung
                setBackgroundPainter(new MattePainter(new Color(89, 79, 79)));
                break;
            }
            case 7: {
                // Mähboot/Mähbalken
                setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(69, 173, 168)), stripes));
                break;
            }
            case 8: {
                // Mähwerk
                setBackgroundPainter(new MattePainter(new Color(84, 121, 128)));
                break;
            }
            case 9: {
                // Handmahd/Mähboot
                setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(229, 252, 194)), stripes));
                break;
            }
            default: {
                setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(229, 252, 194)), stripes));
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
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    private void setMassnahme(final int id) {
        final String query = "select " + MASSNAHMEN_ART.getID() + "," + MASSNAHMEN_ART.getPrimaryKey() + " from "
                    + MASSNAHMEN_ART.getTableName() + " where id = " + id; // NOI18N
        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
        CidsBean b = null;

        if (metaObjects != null) {
            for (final MetaObject tmp : metaObjects) {
                if (tmp.getBean().getProperty("id").equals(id)) {
                    b = tmp.getBean();
                    break;
                }
            }
        }
        try {
            bean.setProperty("massnahme", b);
        } catch (Exception e) {
            LOG.error("Error while setting property massnahme.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void configurePopupMenu() {
        if (!bean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase("gup_massnahme_sonstige")) {
            maehItem.addActionListener(this);
            popup.add(maehItem);

            handItem.addActionListener(this);
            popup.add(handItem);

//            grundItem.addActionListener(this);
//            popup.add(grundItem);

            popup.addSeparator();
        }

        super.configurePopupMenu();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == maehItem) {
            setMassnahme(5);
            fireBandMemberChanged(false);
        } else if (e.getSource() == handItem) {
            setMassnahme(4);
            fireBandMemberChanged(false);
//        } else if (e.getSource() == grundItem) {
//            setMassnahme(6);
//            fireBandMemberChanged(false);
        } else {
            super.actionPerformed(e);
        }

        newMode = false;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("massnahme")) {
            determineBackgroundColour();
            setSelected(isSelected);
            setToolTipText(bean.getProperty("massnahme.name") + "");
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
