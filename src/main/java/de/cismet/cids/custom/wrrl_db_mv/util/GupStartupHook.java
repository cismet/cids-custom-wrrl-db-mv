/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

import de.cismet.cids.custom.actions.wrrl_db_mv.GupLoadStatus;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.commons.concurrency.CismetExecutors;

import de.cismet.tools.configuration.StartupHook;

import static de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport.cloneCidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class GupStartupHook implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(GupStartupHook.class);
    private static final ExecutorService executor = CismetExecutors.newFixedThreadPool(10);

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        executor.execute(new Runnable() {

                private final MetaClass MASSNAHMENART_MC = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "gup_massnahmenart");
                private final MetaClass GUP_PLANUNGSABSCHNITT_MC = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "gup_planungsabschnitt");

                @Override
                public void run() {
                    if ((GUP_PLANUNGSABSCHNITT_MC == null)
                                || !GUP_PLANUNGSABSCHNITT_MC.getPermissions().hasWritePermission(
                                    SessionManager.getSession().getUser())) {
                        // do not load all gup_massnahmenart object, if the current user has no permission to modify
                        // gup_planungsabschnitt objects
                        return;
                    }
                    try {
                        final CidsBeanNormalizer normalizer = new CidsBeanNormalizer();
                        final List<CidsBean> beans = new ArrayList<CidsBean>();
                        final String query = "select " + MASSNAHMENART_MC.getID() + ","
                                    + MASSNAHMENART_MC.getPrimaryKey()
                                    + " from " + MASSNAHMENART_MC.getTableName() + " order by id";
                        boolean newMos;
                        final int limit = 50;
                        int offset = 0;

                        do {
                            newMos = false;
                            final String loopQuery = query + " limit " + limit + " offset " + offset;
                            MetaObject[] mo = SessionManager.getProxy()
                                        .getMetaObjectByQuery(
                                            SessionManager.getSession().getUser(),
                                            loopQuery,
                                            WRRLUtil.DOMAIN_NAME);
                            offset += limit;

                            if ((mo != null) && (mo.length > 0)) {
                                newMos = true;
                                for (final MetaObject m : mo) {
                                    beans.add(normalizer.normalizeCidsBean(m.getBean()));
                                }
                            }

                            mo = null;
                        } while (newMos);
                        System.runFinalization();
                        System.gc();

                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    GupUnterhaltungsmassnahmeEditor.setMassnahmnenObjects(
                                        beans.toArray(new CidsBean[beans.size()]));
                                    GupLoadStatus.getLastInstance().setStatusOk();
                                }
                            });
                    } catch (Exception e) {
                        LOG.error("Error while retrieving massnahmen objects.", e);
                    }
                }
            });
    }
}
