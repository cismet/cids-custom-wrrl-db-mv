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
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenBandMember extends LineBandMember implements CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass MASSNAHMEN_ART = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_massnahmenart");

    //~ Instance fields --------------------------------------------------------

    private List<String> errorList;
    private UnterhaltungsmaßnahmeValidator.ValidationResult res;
    private UnterhaltungsmaßnahmeValidator uv;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     * @param  uv        DOCUMENT ME!
     */
    public MassnahmenBandMember(final MassnahmenBand parent,
            final boolean readOnly,
            final UnterhaltungsmaßnahmeValidator uv) {
        super(parent, readOnly);
        this.uv = uv;

        try {
            new CidsBeanDropTarget(this);
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        setToolTip();

        validateBean();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  uv  DOCUMENT ME!
     */
    public void setUnterhaltungsmassnahmeValidator(final UnterhaltungsmaßnahmeValidator uv) {
        this.uv = uv;

        validateBean();
    }

    /**
     * DOCUMENT ME!
     */
    private void validateBean() {
        if (uv != null) {
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final List<String> errorList = new ArrayList<String>();
                        final UnterhaltungsmaßnahmeValidator.ValidationResult res = uv.validate(
                                getCidsBean(),
                                errorList);

                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    MassnahmenBandMember.this.errorList = errorList;
                                    MassnahmenBandMember.this.res = res;
                                    determineBackgroundColour();
                                    setToolTip();
                                }
                            });
                    }
                }).start();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setToolTip() {
        if ((res == null) || (res != UnterhaltungsmaßnahmeValidator.ValidationResult.error)) {
            setToolTipText(bean.getProperty("massnahme.name") + "");
        } else {
            final StringBuilder text = new StringBuilder("<html>" + bean.getProperty("massnahme.name"));

            if (errorList != null) {
                for (final String tmp : errorList) {
                    text.append(tmp).append("<br />");
                }
            }
            text.append("</html>");
            setToolTipText(bean.getProperty("massnahme.name") + "");
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if ((bean.getProperty("massnahme") == null) || (bean.getProperty("massnahme.color") == null)) {
            setDefaultBackground();
            return;
        }
        final String color = (String)bean.getProperty("massnahme.color");

        if (color != null) {
            try {
                if ((res == null) || (res != UnterhaltungsmaßnahmeValidator.ValidationResult.error)) {
                    setBackgroundPainter(new MattePainter(Color.decode(color)));
                } else {
                    setBackgroundPainter(new CompoundPainter(
                            new MattePainter(Color.decode(color)),
                            new PinstripePainter(new Color(255, 66, 66), 45, 2, 5)));
                }
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

    @Override
    public void actionPerformed(final ActionEvent e) {
        super.actionPerformed(e);
    }

//    @Override
//    public void popupMenuCanceled(final PopupMenuEvent e) {
//        // do nothing
//    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("massnahme")) {
            determineBackgroundColour();
            setSelected(isSelected);
            setToolTip();

            validateBean();
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

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (isReadOnly()) {
            return;
        }

        final CidsBean cidsBean = getCidsBean();

        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_massnahmenart")) { // NOI18N
                    try {
                        if (GupUnterhaltungsmassnahmeEditor.supportsKompartiment(
                                        bean,
                                        ((MassnahmenBand)getParentBand()).getKompartiment())) {
                            cidsBean.setProperty("massnahme", bean);
                            GupUnterhaltungsmassnahmeEditor.getHistoryModel().addElement(bean);
                        } else {
                            JOptionPane.showMessageDialog(
                                this,
                                "Die ausgewählte Maßnahme ist für das aktuelle Kompartiment nicht gültig.",
                                "Ungültige Maßnahme",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        LOG.error("Error while saving the new massnahme property", e);
                    }
                }
            }
        }
    }
}
