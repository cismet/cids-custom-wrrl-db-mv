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
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.CismetThreadPool;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenHistoryListModel implements ListModel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(MassnahmenHistoryListModel.class);

    //~ Instance fields --------------------------------------------------------

    public MetaClass MASSNAHMEN_TYP_MC;
    private List<CidsBean> list = new ArrayList<CidsBean>();
    private List<ListDataListener> listener = new ArrayList<ListDataListener>();
    private CidsBean userHistory;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenHistoryListModel object.
     */
    public MassnahmenHistoryListModel() {
        try {
            MASSNAHMEN_TYP_MC = ClassCacheMultiple.getMetaClass(
                    WRRLUtil.DOMAIN_NAME,
                    "gup_massnahmen_historie");                                                  // NOI18N
            final User user = SessionManager.getSession().getUser();
            final String query = "select distinct " + MASSNAHMEN_TYP_MC.getID() + ", "
                        + MASSNAHMEN_TYP_MC.getPrimaryKey() + " from "
                        + MASSNAHMEN_TYP_MC.getTableName()
                        + " where username = '" + user.getName() + "@" + user.getDomain() + "'"; // NOI18N
            // final String query = "select distinct " + MASSNAHMEN_TYP_MC.getID() + ", m."
            // + MASSNAHMEN_TYP_MC.getPrimaryKey() + " from "
            // + MASSNAHMEN_TYP_MC.getTableName() + " m join gup_unterhaltungsmassnahme u on (u.massnahme = m.id) "
            // + "where bearbeiter = '" + user.getName() + "@" + user.getDomain() + "' limit 10"; // NOI18N
            final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectsByQuery(query);

            if ((metaObjects != null) && (metaObjects.length == 1)) {
                userHistory = metaObjects[0].getBean();
            } else {
                userHistory = CidsBeanSupport.createNewCidsBeanFromTableName("gup_massnahmen_historie");
                userHistory.setProperty("username", user.getName() + "@" + user.getDomain());
            }

            final List<CidsBean> history = CidsBeanSupport.getBeanCollectionFromProperty(
                    userHistory,
                    "massnahmenarten");

            if (history != null) {
                for (final CidsBean tmp : history) {
                    list.add(tmp);
                }
            }
            fireListDataListener();
            GupUnterhaltungsmassnahmeEditor.setHistoryModel(this);
        } catch (Exception e) {
            LOG.error("Error while loading history.", e);
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(final int index) {
        return list.get(index);
    }

    @Override
    public void addListDataListener(final ListDataListener l) {
        listener.add(l);
    }

    @Override
    public void removeListDataListener(final ListDataListener l) {
        listener.remove(l);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  b  DOCUMENT ME!
     */
    public void addElement(final CidsBean b) {
        if (list.contains(b)) {
            list.remove(b);
        }

        if (list.size() > 0) {
            list.add(list.get(list.size() - 1));
            for (int i = list.size() - 1; i > 0; --i) {
                list.set(i, list.get(i - 1));
            }

            list.set(0, b);
        } else {
            list.add(b);
        }
        fireListDataListener();

        CismetThreadPool.executeSequentially(new Runnable() {

                @Override
                public void run() {
                    try {
                        final List<CidsBean> history = CidsBeanSupport.getBeanCollectionFromProperty(
                                userHistory,
                                "massnahmenarten");
                        history.clear();
                        if (list.size() <= 10) {
                            history.addAll(list);
                        } else {
                            history.addAll(list.subList(0, 10));
                        }

                        userHistory = userHistory.persist();
                    } catch (Exception e) {
                        LOG.error("Error while saving history.", e);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void fireListDataListener() {
        final ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, list.size());

        for (final ListDataListener tmp : listener) {
            tmp.contentsChanged(e);
        }
    }
}
