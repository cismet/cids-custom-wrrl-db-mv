/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnBezugListListener implements ObservableListListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MassnBezugListListener.class);
    private static final MetaClass MASSNAHMEN_BEZEICHNUNG = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GUP_MASSNAHMENBEZUG");
    private static final transient ReentrantReadWriteLock MASSNAHMEN_BEZEICHNUNG_LOCK = new ReentrantReadWriteLock();
    private static HashMap<Integer, CidsBean> MASSNAHMEN_BEZEICHNUNGEN = new HashMap<Integer, CidsBean>();
    private static int waitingOperations;

    static {
        de.cismet.tools.CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    MASSNAHMEN_BEZEICHNUNG_LOCK.writeLock().lock();
                    try {
                        final String query = "select " + MASSNAHMEN_BEZEICHNUNG.getID() + ", "
                                    + MASSNAHMEN_BEZEICHNUNG.getPrimaryKey()
                                    + " from "
                                    + MASSNAHMEN_BEZEICHNUNG.getTableName();

                        final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                        for (final MetaObject mo : metaObjects) {
                            MASSNAHMEN_BEZEICHNUNGEN.put(mo.getId(), mo.getBean());
                        }
                    } catch (Exception e) {
                        LOG.error("error", e);
                    } finally {
                        MASSNAHMEN_BEZEICHNUNG_LOCK.writeLock().unlock();
                    }
                }
            });
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private String collectionPropertyName;
    private int kindId;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomListListener object.
     *
     * @param  kindId                  DOCUMENT ME!
     * @param  cidsBean                DOCUMENT ME!
     * @param  collectionPropertyName  DOCUMENT ME!
     */
    public MassnBezugListListener(final int kindId, final CidsBean cidsBean, final String collectionPropertyName) {
        this.kindId = kindId;
        this.cidsBean = cidsBean;
        this.collectionPropertyName = collectionPropertyName;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void listElementsAdded(final ObservableList list, final int index, final int length) {
        if (length == 1) {
            final List<CidsBean> all = cidsBean.getBeanCollectionProperty(collectionPropertyName);

            final CidsBean bean = (CidsBean)list.get(index);
            all.add(bean);

            try {
                synchronized (this) {
                    ++waitingOperations;
                }
                de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<CidsBean, Void>() {

                        @Override
                        protected CidsBean doInBackground() throws Exception {
                            CidsBean res = null;
                            MASSNAHMEN_BEZEICHNUNG_LOCK.readLock().lock();
                            try {
                                res = MASSNAHMEN_BEZEICHNUNGEN.get(kindId);
                            } finally {
                                MASSNAHMEN_BEZEICHNUNG_LOCK.readLock().unlock();
                            }
                            return res;
                        }

                        @Override
                        protected void done() {
                            try {
                                final CidsBean kind = get();
                                if (kind != null) {
                                    bean.setProperty("wo", kind);
                                } else {
                                    LOG.error("Massnahmenbezeichnung whith id " + kindId + " does not exist.");
                                }
                            } catch (Exception e) {
                                LOG.error("Problem beim Suchen der Massnahmenbezeichnungen", e);
                            } finally {
                                synchronized (this) {
                                    --waitingOperations;
                                }
                            }
                        }
                    });
            } catch (Exception e) {
                LOG.error("Cannot set the wo attribute of an object of the type massnahme.", e);
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void listElementsRemoved(final ObservableList list, final int index, final List oldElements) {
        final List<CidsBean> all = cidsBean.getBeanCollectionProperty("massnahmen");

        all.remove((CidsBean)list.get(index));
    }

    @Override
    public void listElementReplaced(final ObservableList list, final int index, final Object oldElement) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void listElementPropertyChanged(final ObservableList list, final int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static synchronized boolean isReady() {
        return waitingOperations == 0;
    }
}
