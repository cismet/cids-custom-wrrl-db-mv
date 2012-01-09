/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.reports;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.utils.jasperreports.ReportSwingWorker;

import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkFgReport {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgReport.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public static void showReport(final CidsBean cidsBean) {
        final Collection<CidsBean> coll = new ArrayList<CidsBean>();
        coll.add(cidsBean);

        final ArrayList<Collection<CidsBean>> beans = new ArrayList<Collection<CidsBean>>();
        beans.add(coll);
        beans.add(getMassnahmen((Integer)cidsBean.getProperty("id")));

        final ArrayList<String> reports = new ArrayList<String>();
        reports.add("/de/cismet/cids/custom/reports/wk_fg.jasper");
        reports.add("/de/cismet/cids/custom/reports/wk_fg_massnahmen.jasper");

        final ReportSwingWorker worker = new ReportSwingWorker(
                beans,
                reports,
                true,
                StaticSwingTools.getParentFrame(CismapBroker.getInstance().getMappingComponent()),
                CismapBroker.getInstance().getCismapFolderPath());
        worker.execute();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Collection<CidsBean> getMassnahmen(final int id) {
        try {
            final MetaClass mcMassnahmen = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");

            final String query = "SELECT "
                        + "   " + mcMassnahmen.getID() + ", "
                        + "   " + mcMassnahmen.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcMassnahmen.getTableName() + " "
                        + "WHERE "
                        + "   wk_fg = " + String.valueOf(id)
                        + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.fatal("", ex);
            }
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Collection<CidsBean> getBeansFromQuery(final String query) {
        final ArrayList<CidsBean> collection = new ArrayList<CidsBean>();
        try {
            for (final MetaObject mo : SessionManager.getProxy().getMetaObjectByQuery(query, 0)) {
                collection.add(mo.getBean());
            }
        } catch (ConnectionException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while fetching metaobject", ex);
            }
        }

        return collection;
    }
}
