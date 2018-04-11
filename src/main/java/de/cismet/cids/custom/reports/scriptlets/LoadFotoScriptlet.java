/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jweintraut
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.reports.scriptlets;

import com.vividsolutions.jts.geom.Point;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.fill.JRFillField;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import de.cismet.cids.custom.wrrl_db_mv.util.WebDavHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;

import de.cismet.commons.security.WebDavClient;

import de.cismet.netutil.Proxy;

import de.cismet.tools.PasswordEncrypter;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class LoadFotoScriptlet extends JRDefaultScriptlet {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoadFotoScriptlet.class);

    private static final String WEB_DAV_USER;
    private static final String WEB_DAV_PASSWORD;
    private static final String WEB_DAV_DIRECTORY;

    private static final WebDavClient webDavClient;

    static {
        final ResourceBundle bundle = ResourceBundle.getBundle("WebDav");
        String pass = bundle.getString("password");

        if ((pass != null) && pass.startsWith(PasswordEncrypter.CRYPT_PREFIX)) {
            pass = PasswordEncrypter.decryptString(pass);
        }

        WEB_DAV_PASSWORD = pass;
        WEB_DAV_USER = bundle.getString("username");
        WEB_DAV_DIRECTORY = bundle.getString("url");

        webDavClient = new WebDavClient(
                Proxy.fromPreferences(),
                WEB_DAV_USER,
                WEB_DAV_PASSWORD,
                true);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Image loadFoto() {
        Image result = null;

        final String fileName = (String)((JRFillField)fieldsMap.get("file")).getValue();
        final String encodedFilename = WebDavHelper.encodeURL(fileName);

        InputStream inputStream = null;
        try {
            inputStream = webDavClient.getInputStream(WEB_DAV_DIRECTORY + encodedFilename);
            result = ImageIO.read(inputStream);
        } catch (IOException ex) {
            LOG.error("Couldn't load photo", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    LOG.error("Couldn't close stream for " + encodedFilename, ex);
                }
            }
        }

        if (result == null) {
            try {
                result = ImageIO.read(getClass().getResource(
                            "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/file-broken.png"));
            } catch (IOException ex1) {
                LOG.error("Couldn't load fallback photo", ex1);
            }
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Image loadBackground() {
        Image result = null;

        try {
            result = ImageIO.read(getClass().getResource("/de/cismet/cids/custom/reports/cidsFactsheet_de_01.png"));
        } catch (IOException ex1) {
            LOG.error("Couldn't load background", ex1);
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Image generateMap() {
        BufferedImage result = null;

        final Lock lock = new ReentrantLock();
        final Condition waitForImageRetrieval = lock.newCondition();

        final String call = "http://www.geodaten-mv.de/dienste/webatlasde_wms/service"
                    + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=WebAtlasDE_MV_farbe"
                    + "&BBOX=<cismap:boundingBox>"
                    + "&SRS=EPSG:5650&FORMAT=image/png"
                    + "&WIDTH=<cismap:width>"
                    + "&HEIGHT=<cismap:height>"
                    + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";

        final CidsBean pointBean = (CidsBean)((JRFillField)fieldsMap.get("point")).getValue();
        final Point p = (Point)pointBean.getProperty("geo_field");
        final BoundingBox boundingBox = new BoundingBox(p.getX() - 500, p.getY() - 332, p.getX() + 500, p.getY() + 332);

        final SimpleWMS swms = new SimpleWMS(new SimpleWmsGetMapUrl(call));
        swms.setName((String)((JRFillField)fieldsMap.get("name")).getValue());
        swms.setBoundingBox(boundingBox);
        swms.setSize(372, 560);

        final SignallingRetrievalListener listener = new SignallingRetrievalListener(lock, waitForImageRetrieval);
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

        final int centerX = result.getWidth() / 2;
        final int centerY = result.getHeight() / 2;

        final Graphics g = result.getGraphics();
        try {
            final BufferedImage pin = ImageIO.read(getClass().getResource(
                        "/de/cismet/cismap/commons/gui/res/pushpin.png"));
            final int posXPin = centerX - (pin.getWidth() / 2);
            final int posYPin = centerY - pin.getHeight();
            g.drawImage(pin, posXPin, posYPin, pin.getWidth(), pin.getHeight(), null);
        } catch (IOException ex) {
            LOG.error("Error while drawing pin on retrieved map.", ex);
        } finally {
            g.dispose();
        }

        return result;
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
