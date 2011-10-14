/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.lang.reflect.InvocationTargetException;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.MassnahmenUmsetzungEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * This class is provides a cache for the MassnahmenUmsetzung object that is given by the constructor.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenUmsetzungCache extends Hashtable<String, CidsBean> implements Runnable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MassnahmenUmsetzungCache.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");
    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
    private static final MetaClass MC_WK_SG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_sg");
    private static final MetaClass MC_WK_KG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_kg");
    private static final MetaClass MC_WK_GW = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_gw");
    public static final String ACTION_PREFIX = "action";
    public static final String WKK_PREFIX = "wkk";

    //~ Instance fields --------------------------------------------------------

    CidsBean bean = null;
    private Thread loader = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomLoader object.
     *
     * @param  bean  DOCUMENT ME!
     */
    public MassnahmenUmsetzungCache(final CidsBean bean) {
        this.bean = bean;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public void refresh() {
        if ((loader != null) && loader.isAlive()) {
            loader.interrupt();
        }
        while ((loader != null) && loader.isAlive()) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                // nothing to do
            }
        }

        clear();
        loader = new Thread(this);
        loader.start();
    }

    @Override
    public void run() {
        if (bean != null) {
            final List<CidsBean> impls = bean.getBeanCollectionProperty("umsetzung");

            for (final CidsBean impl : impls) {
                String key = ACTION_PREFIX + impl.getProperty("massnahme");

                if (Thread.interrupted()) {
                    return;
                }

                if (!key.endsWith("null") && (get(key) == null)) {
                    final CidsBean cacheItem = bindActionField(null, impl);
                    put(key, cacheItem);
                }
                if (Thread.interrupted()) {
                    return;
                }

                final MetaClass mc = getWkMc(impl);
                if (mc != null) {
                    key = mc.getTableName() + WKK_PREFIX + getWk_kId(impl);
                    if (!key.endsWith("null") && (get(key) == null)) {
                        final CidsBean cacheItem = bindWkkField(null, impl);
                        put(key, cacheItem);
                    }
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lblValWk_k  DOCUMENT ME!
     * @param   cidsBean    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean bindWkkField(final JLabel lblValWk_k,
            final CidsBean cidsBean) {
        CidsBean wkFgObject = null;
        String labelText = "";

        if (cidsBean != null) {
            // load the water body name from the server
            final Integer wk_id = getWk_kId(cidsBean);

            if (wk_id == null) {
                labelText = CidsBeanSupport.FIELD_NOT_SET;
            } else {
                final MetaClass mc = getWkMc(cidsBean);
                String query = "select " + mc.getID() + ", m." + mc.getPrimaryKey() + " from " + mc.getTableName(); // NOI18N
                query += " m WHERE m.id = " + wk_id;                                                                // NOI18N

                try {
                    if (!SwingUtilities.isEventDispatchThread() && Thread.interrupted()) {
                        return null;
                    }
                    final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                    if (metaObjects.length == 1) {
                        wkFgObject = metaObjects[0].getBean();
                        final Object wk_text = wkFgObject.getProperty(getWk_kProperty(cidsBean)); // NOI18N

                        if (!SwingUtilities.isEventDispatchThread() && Thread.interrupted()) {
                            return null;
                        }
                        if (wk_text != null) {
                            labelText = wk_text.toString();
                        } else {
                            labelText = CidsBeanSupport.FIELD_NOT_SET;
                        }
                    } else {
                        LOG.error(
                            metaObjects.length
                                    + " water bodies found, but exactly one water body should be found.");
                        labelText = "Error";
                    }
                } catch (ConnectionException e) {
                    LOG.error("Error while loading the water body object with query: " + query, e);
                }
            }
        }

        setTextInEdt(lblValWk_k, labelText);
        return wkFgObject;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lblValMassnahme_nr  DOCUMENT ME!
     * @param   cidsBean            DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean bindActionField(final JLabel lblValMassnahme_nr,
            final CidsBean cidsBean) {
        String labelText = "";
        CidsBean massnahme = null;

        if (cidsBean != null) {
            // load the massn_id of the massnahme object from the server
            final Integer massn_id = (Integer)cidsBean.getProperty("massnahme");

            if (massn_id == null) {
                labelText = CidsBeanSupport.FIELD_NOT_SET;
            } else {
                String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from "
                            + MC.getTableName();        // NOI18N
                query += " m WHERE m.id = " + massn_id; // NOI18N

                try {
                    if (!SwingUtilities.isEventDispatchThread() && Thread.interrupted()) {
                        return null;
                    }
                    final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                    if (metaObjects.length == 1) {
                        massnahme = metaObjects[0].getBean();
                        final Object massn_id_text = massnahme.getProperty("massn_id"); // NOI18N

                        if (!SwingUtilities.isEventDispatchThread() && Thread.interrupted()) {
                            return null;
                        }
                        if (massn_id_text != null) {
                            labelText = massn_id_text.toString();
                        } else {
                            labelText = CidsBeanSupport.FIELD_NOT_SET;
                        }
                    } else {
                        LOG.error(
                            metaObjects.length
                                    + " activity found, but exactly one activity should be found.");
                        labelText = "Error";
                    }
                } catch (ConnectionException e) {
                    LOG.error("Error while loading the measurement object with query: " + query, e);
                }
            }
        }

        setTextInEdt(lblValMassnahme_nr, labelText);
        return massnahme;
    }

    /**
     * Set the text attribute of the given label in the edt thread.
     *
     * @param  label  DOCUMENT ME!
     * @param  text   DOCUMENT ME!
     */
    public static void setTextInEdt(final JLabel label, final String text) {
        if (label == null) {
            return;
        } else if (SwingUtilities.isEventDispatchThread()) {
            label.setText(text);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            label.setText(text);
                        }
                    });
            } catch (final InterruptedException e) {
                // nothing to do
            } catch (final InvocationTargetException e) {
                LOG.error("Exception while setting text.", e);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  the id of the water body that is set
     */
    public static Integer getWk_kId(final CidsBean cidsBean) {
        if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[0]) != null) {
            return (Integer)cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[0]);
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[1]) != null) {
            return (Integer)cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[1]);
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[2]) != null) {
            return (Integer)cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[2]);
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[3]) != null) {
            return (Integer)cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[3]);
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  The meta class of the water body object that is set
     */
    public static MetaClass getWkMc(final CidsBean cidsBean) {
        if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[0]) != null) {
            return MC_WK_FG;
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[1]) != null) {
            return MC_WK_SG;
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[2]) != null) {
            return MC_WK_KG;
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[3]) != null) {
            return MC_WK_GW;
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  an object of the type massnahmenUmsetzung
     *
     * @return  the name of the name property of the water body that is set.
     */
    public static String getWk_kProperty(final CidsBean cidsBean) {
        if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[0]) != null) {
            return "wk_k"; // NOI18N
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[1]) != null) {
            return "wk_k"; // NOI18N
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[2]) != null) {
            return "name"; // NOI18N
        } else if (cidsBean.getProperty(MassnahmenUmsetzungEditor.WB_PROPERTIES[3]) != null) {
            return "name"; // NOI18N
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   massnahmenUmsetzung  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getAction(final CidsBean massnahmenUmsetzung) {
        if (massnahmenUmsetzung != null) {
            final String actionKey = ACTION_PREFIX + massnahmenUmsetzung.getProperty("massnahme");
            return get(actionKey);
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   massnahmenUmsetzung  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getWB(final CidsBean massnahmenUmsetzung) {
        final MetaClass mc = getWkMc(massnahmenUmsetzung);
        if ((mc != null) && (massnahmenUmsetzung != null)) {
            final String wkkKey = mc.getTableName() + WKK_PREFIX
                        + getWk_kId((massnahmenUmsetzung));
            return get(wkkKey);
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmenUmsetzung  DOCUMENT ME!
     * @param  action               DOCUMENT ME!
     */
    public void addAction(final CidsBean massnahmenUmsetzung, final CidsBean action) {
        if ((massnahmenUmsetzung != null) && (action != null)) {
            final String actionKey = ACTION_PREFIX + massnahmenUmsetzung.getProperty("massnahme");
            put(actionKey, action);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmenUmsetzung  DOCUMENT ME!
     * @param  wb                   DOCUMENT ME!
     */
    public void addWB(final CidsBean massnahmenUmsetzung, final CidsBean wb) {
        if ((massnahmenUmsetzung != null) && (wb != null)) {
            final MetaClass mc = getWkMc(massnahmenUmsetzung);
            if (mc != null) {
                final String wkkKey = mc.getTableName() + WKK_PREFIX
                            + getWk_kId((massnahmenUmsetzung));
                put(wkkKey, wb);
            }
        }
    }
}
