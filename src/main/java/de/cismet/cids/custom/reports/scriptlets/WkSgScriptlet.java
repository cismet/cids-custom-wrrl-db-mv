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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFillField;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

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
public class WkSgScriptlet extends JRDefaultScriptlet {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkSgScriptlet.class);

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
            final MetaClass mcWkSg = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_sg");
            final String query = "SELECT "
                        + "   " + mcWkSg.getID() + ", "
                        + "   " + mcWkSg.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcWkSg.getTableName() + " "
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
    public Collection<CidsBean> getSwstn() {
        try {
            final MetaClass mcSwstn = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "swstn");
            final MetaClass mcWkSg = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_sg");
            final MetaClass mcGeom = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "geom");

            final String query = "SELECT "
                        + "   " + mcSwstn.getID() + ", "
                        + "   swstn." + mcSwstn.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcSwstn.getTableName() + " AS swstn, "
                        + "   " + mcWkSg.getTableName() + " AS wk_sg, "
                        + "   " + mcGeom.getTableName() + " AS gs, "
                        + "   " + mcGeom.getTableName() + " AS gf "
                        + "WHERE "
                        + "   swstn.point = gs.id AND "
                        + "   wk_sg.geom = gf.id AND "
                        + "   wk_sg.id = " + String.valueOf(getId()) + " AND "
                        + "   st_intersects(gf.geo_field, gs.geo_field)"
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
    private Collection<CidsBean> getBeansFromQuery(final String query) {
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
    public Image generateMap() {
        try {
            BufferedImage result = null;

            final Lock lock = new ReentrantLock();
            final Condition waitForImageRetrieval = lock.newCondition();

            final String call = "http://www.geodaten-mv.de/dienste/gdimv_topomv"
                        + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";

            final CidsBean geomBean = (CidsBean)((JRFillField)fieldsMap.get("geom")).getValue();
            final Geometry geom = (Geometry)geomBean.getProperty("geo_field");

            final BoundingBox boundingBox = new BoundingBox(geom);
            boundingBox.setX1(boundingBox.getX1() - 50);
            boundingBox.setY1(boundingBox.getY1() - 50);
            boundingBox.setX2(boundingBox.getX2() + 50);
            boundingBox.setY2(boundingBox.getY2() + 50);

            final SimpleWMS swms = new SimpleWMS(new SimpleWmsGetMapUrl(call));
            swms.setName((String)((JRFillField)fieldsMap.get("ls_name")).getValue());
            swms.setBoundingBox(boundingBox);
            swms.setSize(375, 555);

            final WkSgScriptlet.SignallingRetrievalListener listener = new WkSgScriptlet.SignallingRetrievalListener(
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
                        BufferedImage.TYPE_INT_RGB);
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
