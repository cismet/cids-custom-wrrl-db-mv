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
    private UnterhaltungsmassnahmeValidator.ValidationResult res;
    private UnterhaltungsmassnahmeValidator uv;
    private boolean invertSide = false;
    private boolean multiColorAllowed = true;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent      DOCUMENT ME!
     * @param  readOnly    DOCUMENT ME!
     * @param  uv          DOCUMENT ME!
     * @param  invertSide  DOCUMENT ME!
     */
    public MassnahmenBandMember(final MassnahmenBand parent,
            final boolean readOnly,
            final UnterhaltungsmassnahmeValidator uv,
            final Boolean invertSide) {
        super(parent, readOnly);
        this.uv = uv;

        if (invertSide != null) {
            this.invertSide = invertSide;
        } else {
            multiColorAllowed = false;
        }

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
    public void setUnterhaltungsmassnahmeValidator(final UnterhaltungsmassnahmeValidator uv) {
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
                        final UnterhaltungsmassnahmeValidator.ValidationResult res = uv.validate(
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
        if ((res == null) || (res == UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
            if (bean.getProperty("massnahme.name") != null) {
                setToolTipText(bean.getProperty("massnahme.name") + "");
            } else {
                setToolTipText("");
            }
        } else {
            final StringBuilder text = new StringBuilder("<html>" + bean.getProperty("massnahme.name"));

            if (errorList != null) {
                if (errorList.size() > 0) {
                    text.append("<br />");
                }
                for (final String tmp : errorList) {
                    text.append(tmp).append("<br />");
                }
            }
            text.append("</html>");
            if (bean.getProperty("massnahme.name") != null) {
                setToolTipText(bean.getProperty("massnahme.name") + "");
            } else {
                setToolTipText("");
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if ((bean.getProperty("massnahme") == null) || (bean.getProperty("massnahme.gewerk") == null)
                    || (bean.getProperty("massnahme.gewerk.color") == null)) {
            setDefaultBackground();
            return;
        }
        final String color = (String)bean.getProperty("massnahme.gewerk.color");
        double factor = 0.0;
        Color secondColor = new Color(255, 66, 66);

        if (multiColorAllowed) {
            if (bean.getProperty("massnahme.einsatzvariante.factor") != null) {
                factor = (Double)bean.getProperty("massnahme.einsatzvariante.factor");
            }

            if (bean.getProperty("massnahme.einsatzvariante.color") != null) {
                final String secColorString = (String)bean.getProperty("massnahme.einsatzvariante.color");
                secondColor = Color.decode(secColorString);
            }
        }
        if (color != null) {
            try {
                String auflagenNb = (String)bean.getProperty("auflagen_nb");
                String auflagenWb = (String)bean.getProperty("auflagen_wb");

                auflagenNb = (((auflagenNb != null) && auflagenNb.equals("")) ? null : auflagenNb);
                auflagenWb = (((auflagenWb != null) && auflagenWb.equals("")) ? null : auflagenWb);

                if ((res == null) || (res == UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
                    if ((auflagenNb != null) || (auflagenWb != null)) {
                        setBackgroundPainter(new CompoundPainter(
                                new ExtendedMattePainter(Color.decode(color), secondColor, factor, invertSide),
                                new PinstripePainter(new Color(255, 255, 255), 90, 2, 5)));
                    } else {
                        setBackgroundPainter(new ExtendedMattePainter(
                                Color.decode(color),
                                secondColor,
                                factor,
                                invertSide));
                    }
                } else {
                    Color validatorResultColor = new Color(255, 66, 66);

                    if (res == UnterhaltungsmassnahmeValidator.ValidationResult.warning) {
                        validatorResultColor = new Color(229, 240, 76);
                    }

                    if ((auflagenNb != null) || (auflagenWb != null)) {
                        setBackgroundPainter(new CompoundPainter(
                                new ExtendedMattePainter(Color.decode(color), secondColor, factor, invertSide),
                                new PinstripePainter(validatorResultColor, 45, 2, 5),
                                new PinstripePainter(new Color(255, 255, 255), 90, 2, 5)));
                    } else {
                        setBackgroundPainter(new CompoundPainter(
                                new ExtendedMattePainter(Color.decode(color), secondColor, factor, invertSide),
                                new PinstripePainter(validatorResultColor, 45, 2, 5)));
                    }
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

        setSelected(isSelected);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void setDefaultBackground() {
        String auflagenNb = (String)bean.getProperty("auflagen_nb");
        String auflagenWb = (String)bean.getProperty("auflagen_wb");
        auflagenNb = (((auflagenNb != null) && auflagenNb.equals("")) ? null : auflagenNb);
        auflagenWb = (((auflagenWb != null) && auflagenWb.equals("")) ? null : auflagenWb);

        unselectedBackgroundPainter = new MattePainter(new Color(229, 0, 0));
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

        if ((res == null) || (res == UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
            if ((auflagenNb != null) || (auflagenWb != null)) {
                setBackgroundPainter(new CompoundPainter(
                        unselectedBackgroundPainter,
                        new PinstripePainter(new Color(255, 255, 255), 90, 2, 5)));
            } else {
                setBackgroundPainter(unselectedBackgroundPainter);
            }
        } else {
            if ((auflagenNb != null) || (auflagenWb != null)) {
                setBackgroundPainter(new CompoundPainter(
                        unselectedBackgroundPainter,
                        new PinstripePainter(new Color(255, 66, 66), 45, 2, 5),
                        new PinstripePainter(new Color(255, 255, 255), 90, 2, 5)));
            } else {
                setBackgroundPainter(new CompoundPainter(
                        unselectedBackgroundPainter,
                        new PinstripePainter(new Color(255, 66, 66), 45, 2, 5)));
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

        if (isSelected) {
            setBackgroundPainter(selectedBackgroundPainter);
        } else {
            setBackgroundPainter(unselectedBackgroundPainter);
        }
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
            validateBean();
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
