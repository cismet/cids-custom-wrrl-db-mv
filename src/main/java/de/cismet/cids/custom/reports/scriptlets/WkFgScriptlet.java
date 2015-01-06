/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.reports.scriptlets;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFillField;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.TeilgebieteSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cismap.commons.HeadlessMapProvider;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.features.FeatureGroups;
import de.cismet.cismap.commons.features.PureFeatureGroup;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;

/**
 * The WkFgScriptlet contains the methods which are called from the WkFg reports.
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkFgScriptlet extends JRDefaultScriptlet {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgScriptlet.class);

    //~ Instance fields --------------------------------------------------------

    private final MetaClass MC_MST_MESS = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "chemie_mst_messungen");
    private final MetaClass MC_MST_STAMM = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "chemie_mst_stammdaten");

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JRBeanCollectionDataSource getJRBeanCollectionDataSource() {
        try {
            final Collection<JRDataSource> list = new ArrayList<JRDataSource>();
            list.add((JRDataSource)getParameterValue("REPORT_DATA_SOURCE"));
            return new JRBeanCollectionDataSource(list);
        } catch (JRScriptletException ex) {
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getSelf() {
        try {
            final MetaClass mcWkFg = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
            final String query = "SELECT "
                        + "   " + mcWkFg.getID() + ", "
                        + "   " + mcWkFg.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcWkFg.getTableName() + " "
                        + "WHERE "
                        + "   id = " + String.valueOf(getId())
                        + ";";
            return getBeansFromQuery(query);
        } catch (Exception ex) {
            LOG.error("Error while getting self for wk-fg with id " + String.valueOf(getId()), ex);

            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getMassnahmen() {
        try {
            final MetaClass mcMassnahmen = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");

            final String query = "SELECT "
                        + "   " + mcMassnahmen.getID() + ", "
                        + "   " + mcMassnahmen.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcMassnahmen.getTableName() + " "
                        + "WHERE "
                        + "   wk_fg = " + String.valueOf(getId())
                        + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            LOG.error("Error while getting massnahmen for wk-fg with id " + String.valueOf(getId()), ex);

            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getTeilgebiet() {
        final GeometryFactory gf = new GeometryFactory();
        final Collection<CidsBean> wkTeile = (Collection<CidsBean>)((JRFillField)fieldsMap.get("teile")).getValue();
        final Collection<LineString> lineStrings = new ArrayList<LineString>();
        for (final CidsBean wkTeilBean : wkTeile) {
            final CidsBean geomBean = (CidsBean)wkTeilBean.getProperty("linie.geom");
            final LineString geom = (LineString)geomBean.getProperty("geo_field");
            lineStrings.add(geom);
        }
        final Geometry wkGeom = gf.createMultiLineString(
                lineStrings.toArray(new LineString[0]));

        try {
            final CidsServerSearch search = new TeilgebieteSearch(wkGeom.toText());
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof String) {
                    return o.toString();
                }
            }
        } catch (ConnectionException e) {
            LOG.error("Exception during a cids server search.", e); // NOI18N
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getGewName() {
        final Collection<CidsBean> wkTeile = (Collection<CidsBean>)((JRFillField)fieldsMap.get("teile")).getValue();
        final List<String> gewNames = new ArrayList();
        String allNames = null;

        for (final CidsBean wkTeilBean : wkTeile) {
            final String gewName = (String)wkTeilBean.getProperty("linie.von.route.routenname");
            if (!gewNames.contains(gewName)) {
                gewNames.add(gewName);
            }
        }

        for (final String name : gewNames) {
            if (allNames == null) {
                allNames = name;
            } else {
                allNames += ", " + name;
            }
        }

        return allNames;
    }

    /**
     * fetches a list with lawa types, ordered by their "von"-Station,for the wk_fg report. adds "Kein Typ" and
     * "Gewässerwechsel" elements to that list.
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getLawa() {
        try {
            final MetaClass mcLawa = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "lawa");
            final MetaClass mcStation_linie = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "station_linie");
            final MetaClass mcStation = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "station");

            final String query = "SELECT"
                        + "   " + mcLawa.getID() + ", "
                        + "   l." + mcLawa.getPrimaryKey() + ", "
                        + "       s.wert "
                        + " FROM "
                        + "   " + mcLawa.getTableName() + " l "
                        + " JOIN "
                        + "   " + mcStation_linie.getTableName() + " sl on l.linie = sl."
                        + mcStation_linie.getPrimaryKey()
                        + " JOIN "
                        + "   " + mcStation.getTableName() + " s on sl.von = s." + mcStation.getPrimaryKey()
                        + " WHERE "
                        + "       wk_k = '" + getWkK() + "' "
                        + " order by s.wert ;";

            final ArrayList<CidsBean> lawa_types = getBeansFromQuery(query);

            if (lawa_types.size() > 1) {
                extendLawa_TypesWithNoTypeElements(lawa_types);
            }

            return lawa_types;
        } catch (Exception ex) {
            LOG.error("Error while getting lawa types for wk-fg with id " + String.valueOf(getId()), ex);

            return null;
        }
    }

    /**
     * Adds "Kein Typ" and "Gewässerwechsel" elements to the Lawa_types list. These elements are added when the end
     * station and the begin station of two successive Lawa types differ.
     *
     * @param   lawa_types  The list to modify with Lawa Types
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void extendLawa_TypesWithNoTypeElements(final ArrayList<CidsBean> lawa_types) throws Exception {
        // cursor position of ListIterator is always between the elements
        // possible cursor positions (^):  ^ Element(0) ^ Element(1) ^ Element(2) ^ ... Element(n-1) ^
        final ListIterator<CidsBean> iter = lawa_types.listIterator();
        CidsBean lawa_type1 = iter.next();
        while (iter.hasNext()) {
            final CidsBean lawa_type2 = iter.next();
            final Double lawa1_bis = ((Double)lawa_type1.getProperty("linie.bis.wert"));
            final Double lawa2_von = ((Double)lawa_type2.getProperty("linie.von.wert"));

            if (lawa1_bis < lawa2_von) {
                final CidsBean newLawa = createNewLawaCidsBean();

                final long cids1RouteGWK = ((Long)lawa_type1.getProperty("linie.von.route.gwk"));
                final long cids2RouteGWK = ((Long)lawa_type2.getProperty("linie.von.route.gwk"));

                if (cids1RouteGWK == cids2RouteGWK) {
                    newLawa.setProperty("lawa_nr.description", "kein Typ");
                    newLawa.setProperty("linie.von.wert", lawa1_bis);
                    newLawa.setProperty("linie.bis.wert", lawa2_von);
                } else {
                    newLawa.setProperty("lawa_nr.description", "Gewässerwechsel");
                }

                // add newLawa before cids2
                iter.previous();
                iter.add(newLawa);
                // set the iterator back to the old position (after cids2)
                iter.next();
            }
            lawa_type1 = lawa_type2;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private CidsBean createNewLawaCidsBean() throws Exception {
        final CidsBean newLawa = CidsBeanSupport.createNewCidsBeanFromTableName("LAWA");
        final CidsBean newLawa_Nr = CidsBeanSupport.createNewCidsBeanFromTableName("LA_LAWA_NR");
        final CidsBean newLinie = CidsBeanSupport.createNewCidsBeanFromTableName("Station_linie");
        final CidsBean newVon = CidsBeanSupport.createNewCidsBeanFromTableName("Station");
        final CidsBean newBis = CidsBeanSupport.createNewCidsBeanFromTableName("Station");

        newLawa.setProperty("lawa_nr", newLawa_Nr);
        newLawa.setProperty("linie", newLinie);
        newLinie.setProperty("von", newVon);
        newLinie.setProperty("bis", newBis);
        return newLawa;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getMst() {
        try {
            final String query = ""
                        + "SELECT "
                        + "   " + MC_MST_MESS.getID() + ", "
                        + "   m." + MC_MST_MESS.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + MC_MST_MESS.getTableName() + " m, "
                        + "   " + MC_MST_STAMM.getTableName() + " s "
                        + "WHERE "
                        + "   m.messstelle = s.id AND "
                        + "   s.wk_fg = " + getId() + " "
                        + "ORDER BY "
                        + "   messjahr DESC ";

            return getBeansFromQuery(query);
        } catch (final Exception ex) {
            LOG.error("Error while getting mst for wk-fg with id " + String.valueOf(getId()), ex);

            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getId() {
        return (Integer)((JRFillField)fieldsMap.get("id")).getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWkK() {
        return (String)((JRFillField)fieldsMap.get("wk_k")).getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ArrayList<CidsBean> getBeansFromQuery(final String query) {
        final ArrayList<CidsBean> collection = new ArrayList<CidsBean>();
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(query);
            }
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
     * generates the map in the WkFg Report. The map consists of a background and some layers, which are merged.
     *
     * @return  DOCUMENT ME!
     */
    public Image generateMap() {
        try {
            final String urlBackground = "http://www.geodaten-mv.de/dienste/gdimv_topomv"
                        + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";
            final String urlOverlay = "http://wms.fis-wasser-mv.de/services?&VERSION=1.1.1"
                        + "&REQUEST=GetMap"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&TRANSPARENT=TRUE"
                        + "&BGCOLOR=0xF0F0F0"
                        + "&EXCEPTIONS=application/vnd.ogc.se_xml"
                        + "&LAYERS=wk_fg,report_route_stat"
                        + "&STYLES=default,default";
            final GeometryFactory gf = new GeometryFactory();
            final Collection<CidsBean> wkTeile = (Collection<CidsBean>)((JRFillField)fieldsMap.get("teile")).getValue();
            final Collection<LineString> lineStrings = new ArrayList<LineString>();
            for (final CidsBean wkTeilBean : wkTeile) {
                final CidsBean geomBean = (CidsBean)wkTeilBean.getProperty("linie.geom");
                final LineString geom = (LineString)geomBean.getProperty("geo_field");
                lineStrings.add(geom);
            }
            final XBoundingBox boundingBox = new XBoundingBox(gf.createMultiLineString(
                        lineStrings.toArray(new LineString[0])));
            boundingBox.setX1(boundingBox.getX1() - 50);
            boundingBox.setY1(boundingBox.getY1() - 50);
            boundingBox.setX2(boundingBox.getX2() + 50);
            boundingBox.setY2(boundingBox.getY2() + 50);

            final HeadlessMapProvider mapProvider = new HeadlessMapProvider();
            mapProvider.setCenterMapOnResize(true);
            mapProvider.setBoundingBox(boundingBox);
            SimpleWmsGetMapUrl getMapUrl = new SimpleWmsGetMapUrl(urlBackground);
            SimpleWMS simpleWms = new SimpleWMS(getMapUrl);
            mapProvider.addLayer(simpleWms);
            getMapUrl = new SimpleWmsGetMapUrl(urlOverlay);
            simpleWms = new SimpleWMS(getMapUrl);
            mapProvider.addLayer(simpleWms);
            final DefaultStyledFeature f = new DefaultStyledFeature();
            f.setGeometry(gf.createMultiLineString(lineStrings.toArray(new LineString[0])));
            f.setHighlightingEnabled(true);
            f.setPrimaryAnnotation((String)((JRFillField)fieldsMap.get("wk_k")).getValue());
            f.setPrimaryAnnotationVisible(true);
            f.setPrimaryAnnotationPaint(Color.BLACK);
            f.setPrimaryAnnotationHalo(Color.WHITE);
            f.setAutoScale(true);
            f.setLinePaint(Color.RED);
            f.setLineWidth(3);
            mapProvider.addFeature(f);

            return mapProvider.getImageAndWait(72, 130, 555, 375);
        } catch (Exception e) {
            LOG.error("Error while retrievin gmap.", e);
            return null;
        }
    }

    /**
     * generates the map in the WkFg Report. The map consists of a background and some layers, which are merged.
     *
     * @return  DOCUMENT ME!
     */
    public Image generateOverviewMap() {
        try {
            final String urlBackground = "http://www.geodaten-mv.de/dienste/gdimv_topomv"
                        + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";
//            final String urlOverlay = "http://wms.fis-wasser-mv.de/services?&VERSION=1.1.1"
//                        + "&REQUEST=GetMap"
//                        + "&BBOX=<cismap:boundingBox>"
//                        + "&WIDTH=<cismap:width>"
//                        + "&HEIGHT=<cismap:height>"
//                        + "&SRS=EPSG:35833&FORMAT=image/png"
//                        + "&TRANSPARENT=TRUE"
//                        + "&BGCOLOR=0xF0F0F0"
//                        + "&EXCEPTIONS=application/vnd.ogc.se_xml"
//                        + "&LAYERS=wk_fg,report_route_stat"
//                        + "&STYLES=default,default";
            final GeometryFactory gf = new GeometryFactory();
            final Collection<CidsBean> wkTeile = (Collection<CidsBean>)((JRFillField)fieldsMap.get("teile")).getValue();
            final Collection<LineString> lineStrings = new ArrayList<LineString>();
            for (final CidsBean wkTeilBean : wkTeile) {
                final CidsBean geomBean = (CidsBean)wkTeilBean.getProperty("linie.geom");
                final LineString geom = (LineString)geomBean.getProperty("geo_field");
                lineStrings.add(geom);
            }
            final XBoundingBox boundingBox = new XBoundingBox(gf.createMultiLineString(
                        lineStrings.toArray(new LineString[0])));
            boundingBox.setX1(boundingBox.getX1() - 50);
            boundingBox.setY1(boundingBox.getY1() - 50);
            boundingBox.setX2(boundingBox.getX2() + 50);
            boundingBox.setY2(boundingBox.getY2() + 50);

            final HeadlessMapProvider mapProvider = new HeadlessMapProvider();
            mapProvider.setCenterMapOnResize(true);
            mapProvider.setBoundingBox(boundingBox);
            final SimpleWmsGetMapUrl getMapUrl = new SimpleWmsGetMapUrl(urlBackground);
            final SimpleWMS simpleWms = new SimpleWMS(getMapUrl);
            mapProvider.addLayer(simpleWms);
//            getMapUrl = new SimpleWmsGetMapUrl(urlOverlay);
//            simpleWms = new SimpleWMS(getMapUrl);
//            mapProvider.addLayer(simpleWms);
            final DefaultStyledFeature f = new DefaultStyledFeature();
            f.setGeometry(gf.createMultiLineString(lineStrings.toArray(new LineString[0])));
            f.setHighlightingEnabled(true);
            f.setPrimaryAnnotation((String)((JRFillField)fieldsMap.get("wk_k")).getValue());
            f.setPrimaryAnnotationVisible(true);
            f.setPrimaryAnnotationPaint(Color.BLACK);
            f.setPrimaryAnnotationHalo(Color.WHITE);
            f.setAutoScale(true);
            f.setLinePaint(Color.RED);
            f.setLineWidth(3);
            mapProvider.addFeature(f);

            return mapProvider.getImageAndWait(72, 130, 555, 375);
        } catch (Exception e) {
            LOG.error("Error while retrievin gmap.", e);
            return null;
        }
    }

    /**
     * gets a collection of measure type codes and returns their names as String with the format: "code: measureType \n
     * code: measureType \n ... code: measureType"
     *
     * @param   measureTypeCollection  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String formatMeasureTypeCodes(final Collection<CidsBean> measureTypeCollection) {
        String measureTypeCodes = "";
        for (final CidsBean measureTypeCode : measureTypeCollection) {
            measureTypeCodes += measureTypeCode.getProperty("value") + ": " + measureTypeCode.getProperty("name")
                        + "\n";
        }
        if (!measureTypeCodes.equals("")) {
            measureTypeCodes = measureTypeCodes.substring(0, measureTypeCodes.length() - 1);
        }
        return measureTypeCodes;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class SignallingRetrievalListener implements RetrievalListener {

        //~ Instance fields ----------------------------------------------------

        private BufferedImage image = null;
        private Lock lock;
        private Condition condition;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SignallingRetrievalListener object.
         *
         * @param  lock       DOCUMENT ME!
         * @param  condition  DOCUMENT ME!
         */
        public SignallingRetrievalListener(final Lock lock, final Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void retrievalStarted(final RetrievalEvent e) {
        }

        @Override
        public void retrievalProgress(final RetrievalEvent e) {
        }

        @Override
        public void retrievalComplete(final RetrievalEvent e) {
            if (e.getRetrievedObject() instanceof Image) {
                final Image retrievedImage = (Image)e.getRetrievedObject();
                image = new BufferedImage(
                        retrievedImage.getWidth(null),
                        retrievedImage.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                final Graphics2D g = (Graphics2D)image.getGraphics();
                g.drawImage(retrievedImage, 0, 0, null);
                g.dispose();
            }
            signalAll();
        }

        @Override
        public void retrievalAborted(final RetrievalEvent e) {
            signalAll();
        }

        @Override
        public void retrievalError(final RetrievalEvent e) {
            signalAll();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public BufferedImage getRetrievedImage() {
            return image;
        }

        /**
         * DOCUMENT ME!
         */
        private void signalAll() {
            lock.lock();
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
