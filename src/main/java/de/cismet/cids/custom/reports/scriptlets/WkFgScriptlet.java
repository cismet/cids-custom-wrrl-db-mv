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

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFillField;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;

/**
 * DOCUMENT ME!
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
            if (LOG.isDebugEnabled()) {
                LOG.fatal("", ex);
            }
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
            if (LOG.isDebugEnabled()) {
                LOG.fatal("", ex);
            }
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getLawa() {
        try {
            final MetaClass mcLawa = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "lawa");

            final String query = "SELECT "
                        + "   " + mcLawa.getID() + ", "
                        + "   " + mcLawa.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcLawa.getTableName() + " "
                        + "WHERE "
                        + "   wk_k = '" + getWkK() + "' "
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
                        + "   messjahr DESC "
                        + "LIMIT 1;"; // NOI18N

            return getBeansFromQuery(query);
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.fatal("", ex);
            }
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
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Image generateMap() {
        final BufferedImage background = fetchMap("http://www.geodaten-mv.de/dienste/gdimv_topomv"
                        + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage");
        final BufferedImage overlay = fetchMap("http://wms.fis-wasser-mv.de/services?&VERSION=1.1.1"
                        + "&REQUEST=GetMap"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&TRANSPARENT=TRUE"
                        + "&BGCOLOR=0xF0F0F0"
                        + "&EXCEPTIONS=application/vnd.ogc.se_xml"
                        + "&LAYERS=route_stat,biomst,chemmst,wk_fg"
                        + "&STYLES=default,default,default,default");

        return mergeImages(background, overlay);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   call  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private BufferedImage fetchMap(final String call) {
        try {
            BufferedImage result = null;

            final Lock lock = new ReentrantLock();
            final Condition waitForImageRetrieval = lock.newCondition();

            final GeometryFactory gf = new GeometryFactory();
            final Collection<CidsBean> wkTeile = (Collection<CidsBean>)((JRFillField)fieldsMap.get("teile")).getValue();
            final Collection<LineString> lineStrings = new ArrayList<LineString>();
            for (final CidsBean wkTeilBean : wkTeile) {
                final CidsBean geomBean = (CidsBean)wkTeilBean.getProperty("linie.geom");
                final LineString geom = (LineString)geomBean.getProperty("geo_field");
                lineStrings.add(geom);
            }
            final BoundingBox boundingBox = new BoundingBox(gf.createMultiLineString(
                        lineStrings.toArray(new LineString[0])));
            boundingBox.setX1(boundingBox.getX1() - 50);
            boundingBox.setY1(boundingBox.getY1() - 50);
            boundingBox.setX2(boundingBox.getX2() + 50);
            boundingBox.setY2(boundingBox.getY2() + 50);

            final SimpleWMS swms = new SimpleWMS(new SimpleWmsGetMapUrl(call));
            swms.setName((String)((JRFillField)fieldsMap.get("wk_n")).getValue());
            swms.setBoundingBox(boundingBox);
            swms.setSize(375, 555);

            final WkFgScriptlet.SignallingRetrievalListener listener = new WkFgScriptlet.SignallingRetrievalListener(
                    lock,
                    waitForImageRetrieval);
            swms.addRetrievalListener(listener);

            lock.lock();
            try {
                swms.retrieve(true);
                waitForImageRetrieval.await();
            } catch (Throwable t) {
                LOG.error("Error occurred while retrieving WMS image", t);
            } finally {
                lock.unlock();
            }

            result = listener.getRetrievedImage();

            return result;
        } catch (Exception ex) {
            LOG.fatal("error", ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   image    DOCUMENT ME!
     * @param   overlay  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private BufferedImage mergeImages(final BufferedImage image, final BufferedImage overlay) {
        final int w = Math.max(image.getWidth(), overlay.getWidth());
        final int h = Math.max(image.getHeight(), overlay.getHeight());
        final BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics g = combined.getGraphics();

        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        return combined;
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
