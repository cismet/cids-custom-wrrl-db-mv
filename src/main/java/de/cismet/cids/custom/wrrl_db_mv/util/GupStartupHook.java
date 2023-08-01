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

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.stream.ImageInputStream;

import javax.swing.ProgressMonitor;

import de.cismet.cids.custom.actions.wrrl_db_mv.GupLoadStatus;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FotodokumentationEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.commons.concurrency.CismetExecutors;

import de.cismet.commons.security.WebDavClient;

import de.cismet.netutil.ProxyHandler;

import de.cismet.tools.ExifReader;
import de.cismet.tools.PasswordEncrypter;

import de.cismet.tools.configuration.StartupHook;

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
        GupPlanungsabschnittEditor editor = new GupPlanungsabschnittEditor();
        editor = null;

        final Thread gupInit = new Thread() {

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
            };

//        final Thread gupInit = new Thread() {
//
//                private final MetaClass FOTO_MC = ClassCacheMultiple.getMetaClass(
//                        WRRLUtil.DOMAIN_NAME,
//                        "fotodokumentation");
//
//                @Override
//                public void run() {
//                    try {
//                        final ResourceBundle bundle = ResourceBundle.getBundle(
//                                "WebDav",
//                                Locale.getDefault(),
//                                FotodokumentationEditor.class.getClassLoader());
//                        String pass = bundle.getString("password");
//
//                        if ((pass != null) && pass.startsWith(PasswordEncrypter.CRYPT_PREFIX)) {
//                            pass = PasswordEncrypter.decryptString(pass);
//                        }
//
//                        final String WEB_DAV_PASSWORD = pass;
//                        final String WEB_DAV_USER = bundle.getString("username");
//                        final String WEB_DAV_DIRECTORY = bundle.getString("url");
//
//                        final WebDavClient webDavClient = new WebDavClient(ProxyHandler.getInstance().getProxy(),
//                                WEB_DAV_USER,
//                                WEB_DAV_PASSWORD,
//                                true);
//                        final String query = "select " + FOTO_MC.getID() + ","
//                                    + FOTO_MC.getPrimaryKey()
//                                    + " from " + FOTO_MC.getTableName() + " order by id";
//                        boolean newFoto;
//                        final int limit = 500;
//                        int offset = 0;
//
//                        do {
//                            newFoto = false;
//                            final String loopQuery = query + " limit " + limit + " offset " + offset;
//                            MetaObject[] mo = SessionManager.getProxy()
//                                        .getMetaObjectByQuery(
//                                            SessionManager.getSession().getUser(),
//                                            loopQuery,
//                                            WRRLUtil.DOMAIN_NAME);
//                            offset += limit;
//
//                            if ((mo != null) && (mo.length > 0)) {
//                                newFoto = true;
//                                for (final MetaObject m : mo) {
//                                    final CidsBean bean = m.getBean();
//                                    final List<CidsBean> fotos = CidsBeanSupport.getBeanCollectionFromProperty(
//                                            bean,
//                                            "fotos");
//                                    try {
//                                        if (fotos != null) {
//                                            for (final CidsBean foto : fotos) {
//                                                if ((foto.getProperty("foto_date") == null)
//                                                            && (foto.getProperty("file") != null)) {
//                                                    final File imageFile = downloadImageFromWebDAV(
//                                                            webDavClient,
//                                                            WEB_DAV_DIRECTORY,
//                                                            (String)foto.getProperty("file"));
//
//                                                    final ExifReader reader = new ExifReader(imageFile);
//                                                    final java.util.Date imageDate = reader.getTimeDate();
//
//                                                    if ((imageDate != null) && ((imageDate.getYear() + 1900) > 1950)) {
//                                                        foto.setProperty(
//                                                            "foto_date",
//                                                            new java.sql.Timestamp(imageDate.getTime()));
//                                                        foto.persist();
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Error at photo " + bean.getPrimaryKeyValue());
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            mo = null;
//                        } while (newFoto);
//                        System.runFinalization();
//                        System.gc();
//                    } catch (Exception e) {
//                        LOG.error("Error while retrieving massnahmen objects.", e);
//                    }
//                }
//            };

        gupInit.setPriority(Thread.MIN_PRIORITY);
        gupInit.start();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   webDavClient       DOCUMENT ME!
     * @param   WEB_DAV_DIRECTORY  DOCUMENT ME!
     * @param   fileName           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    private File downloadImageFromWebDAV(final WebDavClient webDavClient,
            final String WEB_DAV_DIRECTORY,
            final String fileName) throws IOException {
        final String encodedFileName = WebDavHelper.encodeURL(fileName);
        final InputStream iStream = webDavClient.getInputStream(WEB_DAV_DIRECTORY
                        + encodedFileName);
        try {
            String n = fileName;

            if (n.contains("/")) {
                n = n.substring(n.lastIndexOf("/") + 1);
            }
            final File f = new File("/home/therter/tmp/fotos/" + n);

            final FileOutputStream fout = new FileOutputStream(f);
            final byte[] tmp = new byte[256];
            int count;

            while ((count = iStream.read(tmp)) != -1) {
                fout.write(tmp, 0, count);
            }

            fout.flush();
            fout.close();

            return f;
        } finally {
            IOUtils.closeQuietly(iStream);
        }
    }
}
