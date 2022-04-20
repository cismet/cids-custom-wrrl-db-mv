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

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.util.HashMap;
import java.util.Map;

import de.cismet.tools.configuration.StartupHook;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class HttpStartupHook implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(HttpStartupHook.class);
    public static boolean initialised = false;

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        final Map<HostConfiguration, Integer> maxHostConnections = new HashMap<>();
        maxHostConnections.put(HostConfiguration.ANY_HOST_CONFIGURATION, 64);
        HttpConnectionManagerParams.getDefaultParams()
                .setParameter(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS, maxHostConnections);
        HttpConnectionManagerParams.getDefaultParams()
                .setIntParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS, 64);
//        LOG.fatal("applicationStarted");
//        System.out.println("applicationStarted");
//
//        try {
//            final Map handlers = tryExtractInternalHandlerTableFromUrl();
//
//            if (handlers != null) {
//                handlers.put("https", new CustomUrlHandler(handlers.get("https")));
////                handlers.put("http", new CustomUrlHandler());
//            }
//            final Field handlersField = URL.class.getDeclaredField("handlers");
//            handlersField.setAccessible(false);
//        } catch (Exception ex) {
//            LOG.error("Cannot use custom http handler", ex);
//        }
    }

    /**
     * DOCUMENT ME!
     */
    public static synchronized void init() {
        if (!initialised) {
            try {
                final Map handlers = tryExtractInternalHandlerTableFromUrl();

                if (handlers != null) {
                    handlers.put("https", new CustomUrlHandler(handlers.get("https")));
                }

                final Field handlersField = URL.class.getDeclaredField("handlers");
                handlersField.setAccessible(false);
            } catch (Exception ex) {
                LOG.error("Cannot use custom http handler", ex);
            }

            initialised = true;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Map tryExtractInternalHandlerTableFromUrl() {
        try {
            final Field handlersField = URL.class.getDeclaredField("handlers");
            handlersField.setAccessible(true);
            return (Map)handlersField.get(null);
        } catch (Exception e) {
            LOG.error("Cannot use custom http handler", e);
            return null;
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class CustomUrlHandler extends URLStreamHandler {

        //~ Instance fields ----------------------------------------------------

        private Object origHandler;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomUrlHandler object.
         *
         * @param  origHandler  DOCUMENT ME!
         */
        public CustomUrlHandler(final Object origHandler) {
            this.origHandler = origHandler;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected URLConnection openConnection(final URL u) throws IOException {
            if (u.toString().startsWith("https://fis-wasser-mv.de/charts/steckbriefe")) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("use cismet handler");
                }

                return new CismetHttpUrlConnection(u);
            } else {
                try {
                    final Method m = origHandler.getClass().getMethod("openConnection", URL.class);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("use orig handler");
                    }
                    return (URLConnection)m.invoke(origHandler, u);
                } catch (Exception e) {
                    LOG.error("cannot use orig handler", e);
                    return new CismetHttpUrlConnection(u);
                }
            }
        }

        @Override
        protected URLConnection openConnection(final URL u, final Proxy p) throws IOException {
            if (u.toString().startsWith("https://fis-wasser-mv.de/charts/steckbriefe")) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("use cismet handler with proxy");
                }

                return new CismetHttpUrlConnection(u);
            } else {
                try {
                    final Method m = origHandler.getClass().getMethod("openConnection", URL.class, Proxy.class);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("use orig handler with proxy");
                    }
                    return (URLConnection)m.invoke(origHandler, u, p);
                } catch (Exception e) {
                    LOG.error("cannot use orig handler", e);
                    return new CismetHttpUrlConnection(u);
                }
            }
        }
    }
}
