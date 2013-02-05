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

import org.mortbay.log.Log;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.LawaTableModel;
import de.cismet.cids.custom.wrrl_db_mv.util.TeileComparator;

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

        final HashMap parameters = new HashMap();
        parameters.put("STATIONIERUNGEN", getStationierungen(cidsBean));
        parameters.put("GEWAESSERKENNZAHLEN", getGewaesserkennzahlen(cidsBean));
        parameters.put("LAWA-DETAILTYP", getLawaDetailTyp(cidsBean));
        parameters.put("BEWIRTSCHAFTUNGSBEREICHE", getBewirtschaftungsbereiche(cidsBean));

        final ReportSwingWorker worker = new ReportSwingWorker(
                beans,
                reports,
                true,
                StaticSwingTools.getParentFrame(CismapBroker.getInstance().getMappingComponent()),
                CismapBroker.getInstance().getCismapFolderPath(),
                parameters);
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

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  Gewaesserkennzahlen from a WK_FG as String, with the format "gwk, gwk, ... gwk"
     */
    private static String getGewaesserkennzahlen(final CidsBean cidsBean) {
        String gewaesserkennzahlen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)cidsBean.getProperty("teile");
        for (final CidsBean teil : teile) {
            final CidsBean linie = (CidsBean)teil.getProperty("linie");
            final CidsBean station_von = (CidsBean)linie.getProperty("von");
            final CidsBean route = (CidsBean)station_von.getProperty("route");
            final Long gewaesserkennzahl = (Long)route.getProperty("gwk");

            gewaesserkennzahlen += gewaesserkennzahl.toString() + ", ";
        }
        if (!gewaesserkennzahlen.equals("")) {
            gewaesserkennzahlen = gewaesserkennzahlen.substring(0, gewaesserkennzahlen.length() - 2);
        }

        return gewaesserkennzahlen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  LAWA_Detailtyp from a WK_FG as String, with the format "typ1# (xx.x%), ... kein Typ (xx.x%)"
     */
    private static String getLawaDetailTyp(final CidsBean cidsBean) {
        final LawaTableModel model = new LawaTableModel();
        model.setCidsBean(cidsBean);
        final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "teile");
        if (teile != null) {
            Collections.sort(teile, new TeileComparator());
        }
        model.setTeile(teile);
        model.refreshData();

        final int typIndex = 0;
        final int anteilIndex = 1;
        final int amountDifferentTypes = model.getRowCount();

        String lawaDetailTyp = "";

        for (int i = 0; i < (amountDifferentTypes - 1); i++) {
            final String typ = (String)model.getValueAt(i, typIndex);
            final String typ_number = typ.split("-")[0];
            final String anteil = (String)model.getValueAt(i, anteilIndex);
            lawaDetailTyp += typ_number + " (" + anteil + "%), ";
        }
        if (!lawaDetailTyp.equals("")) {
            lawaDetailTyp = lawaDetailTyp.substring(0, lawaDetailTyp.length() - 2);
        }

        return lawaDetailTyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  Stationierungen from a WK_FG as String, with the format "von - bis, von - bis, ... von - bis"
     */
    private static String getStationierungen(final CidsBean cidsBean) {
        String stationierungen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)cidsBean.getProperty("teile");
        final DecimalFormat df = new DecimalFormat(",##0");
        for (final CidsBean teil : teile) {
            final CidsBean linie = (CidsBean)teil.getProperty("linie");
            final CidsBean station_von = (CidsBean)linie.getProperty("von");
            final Double wert_von = (Double)station_von.getProperty("wert");
            final CidsBean station_bis = (CidsBean)linie.getProperty("bis");
            final Double wert_bis = (Double)station_bis.getProperty("wert");

            stationierungen += df.format(wert_von) + " - " + df.format(wert_bis) + ", ";
        }
        if (!stationierungen.equals("")) {
            stationierungen = stationierungen.substring(0, stationierungen.length() - 2);
        }

        return stationierungen;
    }

    /**
     * gets the Bewirtschaftungsbereiche from a WK_FG with format "von - bis, von - bis, ... von - bis". each
     * Bewirtschaftungsbereich has usually the same von and bis stations as one Teil. Except it might finish earlier,
     * but the starting station is always the same.
     *
     * @param   cidsBean  a WK_FG CidsBean
     *
     * @return  Bewirtschaftungsbereiche from a WK_FG as String, with the format "von - bis, von - bis, ... von - bis"
     */
    private static String getBewirtschaftungsbereiche(final CidsBean cidsBean) {
        String stationierungen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)cidsBean.getProperty("teile");
        final DecimalFormat df = new DecimalFormat(",##0");
        for (final CidsBean teil : teile) {
            final Double bewirtschaftung_von = (Double)teil.getProperty("linie.von.wert");

            final Collection<CidsBean> bewirtschaftungsende_coll = getBewirtschaftungsende(teil);
            Double bewirtschaftung_bis;

            if (bewirtschaftungsende_coll.isEmpty()) {
                bewirtschaftung_bis = (Double)teil.getProperty("linie.bis.wert");
            } else if (bewirtschaftungsende_coll.size() == 1) {
                final CidsBean bewirtschaftungsende = bewirtschaftungsende_coll.toArray(new CidsBean[0])[0];
                bewirtschaftung_bis = (Double)bewirtschaftungsende.getProperty("stat.wert");
            } else { // bewirtschaftungsende should contain only none or one CidsBean?
                bewirtschaftung_bis = null;
                Log.warn("Teil " + teil.getProperty("ID") + " hat mehrere Bewirtschaftungsenden.");
            }
            stationierungen += df.format(bewirtschaftung_von) + " - " + df.format(bewirtschaftung_bis) + ", ";
        }
        if (!stationierungen.equals("")) {
            stationierungen = stationierungen.substring(0, stationierungen.length() - 2);
        }

        return stationierungen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   teilBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    // TODO fuer jedes Teil ausfuehren, falls kein Bewirtschaftungsende gefunden, dann wird normales Ende der Linie
    // benutzt
    private static Collection<CidsBean> getBewirtschaftungsende(final CidsBean teilBean) {
        try {
            final MetaClass BEW_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "bewirtschaftungsende");

            String query = "SELECT " + BEW_MC.getID() + ", b." + BEW_MC.getPrimaryKey() + " ";
            query += "FROM " + BEW_MC.getTableName() + " b JOIN station s ON b.stat = s.id ";
            query += "WHERE route = " + teilBean.getProperty("linie.von.route.id") + " and s.wert > "
                        + teilBean.getProperty("linie.von.wert") + " and s.wert < "
                        + teilBean.getProperty("linie.bis.wert") + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.fatal("", ex);
            }
            return null;
        }
    }
}
