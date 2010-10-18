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
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;
import de.cismet.security.Proxy;
import de.cismet.security.WebDavClient;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.fill.JRFillField;

/**
 *
 * @author jweintraut
 */
public class LoadFotoScriptlet extends JRDefaultScriptlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoadFotoScriptlet.class);

    //TODO: Aus ResourceBundle lesen? s. auch de.cismet.cids.custom.objecteditors.wrrl_db_mv.FotodokumentationEditor
    private static final String WEB_DAV_USER = "cismet";
    private static final String WEB_DAV_PASSWORD = "karusu20";
    private static final String WEB_DAV_DIRECTORY = "http://webdav.cismet.de/images/";

    private static final WebDavClient webDavClient = new WebDavClient(
            Proxy.fromPreferences(), WEB_DAV_USER, WEB_DAV_PASSWORD);

    public Image loadFoto() {
        Image result = null;

        String fileName = (String) ((JRFillField) fieldsMap.get("file")).getValue();

        InputStream inputStream = null;
        try {
            inputStream = webDavClient.getInputStream(WEB_DAV_DIRECTORY + fileName);
            result = ImageIO.read(inputStream);
        } catch (IOException ex) {
            LOG.error("Couldn't load photo", ex);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    LOG.error("Couldn't close stream for " + fileName, ex);
                }
            }
        }

        if(result == null) {
            try {
                result = ImageIO.read(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/file-broken.png"));
            } catch (IOException ex1) {
                LOG.error("Couldn't load fallback photo", ex1);
            }
        }

        return result;
    }

    public Image loadBackground() {
        Image result = null;

        try {
            result = ImageIO.read(getClass().getResource("/de/cismet/cids/custom/reports/cidsFactsheet_de_01.png"));
        } catch (IOException ex1) {
            LOG.error("Couldn't load background", ex1);
        }

        return result;
    }

    public Image generateMap() {
        BufferedImage result = null;

        Lock lock = new ReentrantLock();
        Condition waitForImageRetrieval = lock.newCondition();

        String call = "http://www.geodaten-mv.de/dienste/gdimv_topomv"
                + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                + "&BBOX=<cismap:boundingBox>"
                + "&SRS=EPSG:35833&FORMAT=image/png"
                + "&WIDTH=<cismap:width>"
                + "&HEIGHT=<cismap:height>"
                + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";

        CidsBean pointBean = (CidsBean) ((JRFillField) fieldsMap.get("point")).getValue();
        Point p = (Point) pointBean.getProperty("geo_field");
        BoundingBox boundingBox = new BoundingBox(p.getX() - 500, p.getY() - 332, p.getX() + 500, p.getY() + 332);

        SimpleWMS swms=new SimpleWMS(new SimpleWmsGetMapUrl(call));
        swms.setName((String) ((JRFillField) fieldsMap.get("name")).getValue());
        swms.setBoundingBox(boundingBox);
        swms.setSize(372, 560);

        SignallingRetrievalListener listener = new SignallingRetrievalListener(lock, waitForImageRetrieval);
        swms.addRetrievalListener(listener);

        lock.lock();
        try {
            swms.retrieve(true);
            waitForImageRetrieval.await();
        } catch(Throwable t) {
            LOG.error("Error occurred while retrieving WMS image", t);
        } finally {
            lock.unlock();
        }

        result = listener.getRetrievedImage();

        int centerX = result.getWidth()/2;
        int centerY = result.getHeight()/2;

        Graphics g = result.getGraphics();
        try {
            BufferedImage pin = ImageIO.read(getClass().getResource("/de/cismet/cismap/commons/gui/res/pushpin.png"));
            int posXPin = centerX - pin.getWidth()/2;
            int posYPin = centerY - pin.getHeight();
            g.drawImage(pin, posXPin, posYPin, pin.getWidth(), pin.getHeight(), null);
        } catch (IOException ex) {
            LOG.error("Error while drawing pin on retrieved map.", ex);
        } finally {
            g.dispose();
        }

        return result;
    }

    public class SignallingRetrievalListener implements RetrievalListener {
        private BufferedImage image = null;
        private Lock lock;
        private Condition condition;

        public SignallingRetrievalListener(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void retrievalStarted(RetrievalEvent e) {
        }

        @Override
        public void retrievalProgress(RetrievalEvent e) {
        }

        @Override
        public void retrievalComplete(RetrievalEvent e) {
            if(e.getRetrievedObject() instanceof Image) {
                Image retrievedImage = (Image) e.getRetrievedObject();
                image = new BufferedImage(
                        retrievedImage.getWidth(null), retrievedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.drawImage(retrievedImage, 0, 0, null);
                g.dispose();
            }
            signalAll();
        }

        @Override
        public void retrievalAborted(RetrievalEvent e) {
            signalAll();
        }

        @Override
        public void retrievalError(RetrievalEvent e) {
            signalAll();
        }

        public BufferedImage getRetrievedImage() {
            return image;
        }

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
