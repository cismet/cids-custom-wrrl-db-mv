/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.awt.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import javax.swing.ProgressMonitorInputStream;

import de.cismet.commons.security.WebDavClient;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WebDavHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(WebDavHelper.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   prefix        DOCUMENT ME!
     * @param   originalFile  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String generateWebDAVFileName(final String prefix, final File originalFile) {
        final String[] fileNameSplit = originalFile.getName().split("\\.");
        String webFileName = prefix + System.currentTimeMillis() + "-" + Math.abs(originalFile.getName().hashCode());
        if (fileNameSplit.length > 1) {
            final String ext = fileNameSplit[fileNameSplit.length - 1];
            webFileName += "." + ext;
        }
        return webFileName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   fileName         DOCUMENT ME!
     * @param   toUpload         DOCUMENT ME!
     * @param   webDavDirectory  DOCUMENT ME!
     * @param   webDavClient     DOCUMENT ME!
     * @param   parent           DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public static void uploadFileToWebDAV(final String fileName,
            final File toUpload,
            final String webDavDirectory,
            final WebDavClient webDavClient,
            final Component parent) throws IOException {
        final BufferedInputStream bfis = new BufferedInputStream(new ProgressMonitorInputStream(
                    parent,
                    "Bild wird übertragen...",
                    new FileInputStream(toUpload)));
        try {
            webDavClient.put(webDavDirectory + encodeURL(fileName), bfis);
        } finally {
            IOUtils.closeQuietly(bfis);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   fileName         DOCUMENT ME!
     * @param   webDavClient     DOCUMENT ME!
     * @param   webDavDirectory  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean deleteFileFromWebDAV(final String fileName,
            final WebDavClient webDavClient,
            final String webDavDirectory) {
        if ((fileName != null) && (fileName.length() > 0)) {
            try {
                webDavClient.delete(webDavDirectory + encodeURL(fileName));
                return true;
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   url  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String encodeURL(final String url) {
        try {
            if (url == null) {
                return null;
            }
            final String[] tokens = url.split("/", -1);
            StringBuilder encodedURL = null;

            for (final String tmp : tokens) {
                if (encodedURL == null) {
                    encodedURL = new StringBuilder(URLEncoder.encode(tmp, "UTF-8"));
                } else {
                    encodedURL.append("/").append(URLEncoder.encode(tmp, "UTF-8"));
                }
            }

            if (encodedURL != null) {
                // replace all + with %20 because the method URLEncoder.encode() replaces all spaces with '+', but
                // the web dav client interprets %20 as a space.
                return encodedURL.toString().replaceAll("\\+", "%20");
            } else {
                return "";
            }
        } catch (final UnsupportedEncodingException e) {
            LOG.error("Unsupported encoding.", e);
        }
        return url;
    }
}
