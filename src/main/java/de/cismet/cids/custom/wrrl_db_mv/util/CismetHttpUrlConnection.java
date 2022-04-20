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

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.cismet.netutil.Proxy;
import de.cismet.netutil.ProxyHandler;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CismetHttpUrlConnection extends HttpURLConnection {

    //~ Instance fields --------------------------------------------------------

    Integer responseCode = null;

    private HttpClient client;
    private final HashMap<String, String> requestProperties = new HashMap<String, String>();
    private Header[] responseHeaders = null;
    private InputStream response = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CismetHttpUrlConnection object.
     *
     * @param  url  DOCUMENT ME!
     */
    public CismetHttpUrlConnection(final URL url) {
        super(url);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public InputStream getInputStream() throws IOException {
        if (client == null) {
            connect();
        }

        return response;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public String getContentType() {
        if (responseHeaders != null) {
            for (final Header header : responseHeaders) {
                if (header.getName().equalsIgnoreCase("Content-Type")) {
                    return header.getValue();
                }
            }

            return super.getContentType();
        } else {
            return null;
        }
    }

    @Override
    public String getContentEncoding() {
        return super.getContentEncoding();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  is  DOCUMENT ME!
     */
    private void printStream(final InputStream is) {
        try {
            final FileOutputStream fos = new FileOutputStream("/home/therter/test.svg");
            final byte[] tmp = new byte[512];
            int count = -1;

            while ((count = is.read(tmp)) != -1) {
                fos.write(tmp, 0, count);
            }

            fos.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void disconnect() {
        try {
            response.close();
        } catch (IOException ex) {
            System.out.println("Cannot close input stream from connection");
        }
        response = null;
        client = null;
    }

    @Override
    public boolean usingProxy() {
        return ProxyHandler.getInstance().getProxy() != null;
    }

    @Override
    public void setRequestProperty(final String key, final String value) {
        requestProperties.put(key, value);
    }

    @Override
    public void addRequestProperty(final String key, final String value) {
        requestProperties.put(key, value);
    }

    @Override
    public int getResponseCode() throws IOException {
        if (client == null) {
            connect();
        }

        if (responseCode == null) {
            final HeadMethod head = new HeadMethod(url.toString());

            try {
                return client.executeMethod(head);
            } finally {
                head.releaseConnection();
            }
        } else {
            return responseCode;
        }
    }

    @Override
    public void connect() throws IOException {
        if (client == null) {
            client = getClient();
        }

        if (response == null) {
            final GetMethod get = new GetMethod(url.toString());
            responseCode = client.executeMethod(get);

            for (final String key : requestProperties.keySet()) {
                get.addRequestHeader(key, requestProperties.get(key));
            }

            responseHeaders = get.getResponseHeaders();

            response = get.getResponseBodyAsStream();
        }
    }

    @Override
    public int getContentLength() {
        try {
            return getInputStream().available();
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private HttpClient getClient() {
        final Proxy proxy = ProxyHandler.getInstance().getProxy();
        final HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        final List authPrefs = new ArrayList();
        authPrefs.add(AuthPolicy.DIGEST);
        authPrefs.add(AuthPolicy.BASIC);
        authPrefs.add(AuthPolicy.NTLM);

        client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);

        if (((proxy != null) && (proxy.getHost() != null) && (proxy.getPort() > 0)
                        && proxy.isValid() && proxy.isEnabledFor((url != null) ? url.getHost() : null))) {
            client.getHostConfiguration().setProxy(proxy.getHost(), proxy.getPort());

            // proxy needs authentication
            if ((proxy.getUsername() != null) && (proxy.getPassword() != null)) {
                final AuthScope authscope = new AuthScope(proxy.getHost(), proxy.getPort());
                final Credentials credentials = new NTCredentials(proxy.getUsername(),
                        proxy.getPassword(),
                        "", // NOI18N
                        (proxy.getDomain() == null) ? "" : proxy.getDomain());
                client.getState().setProxyCredentials(authscope, credentials);
            }
        }

        return client;
    }
}
