/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.List;

import de.cismet.security.WebAccessManager;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HTMLTools {

    //~ Methods ----------------------------------------------------------------

    /**
     * Gets the combined text of the given element and all its children. Block elements and &lt;br /&gt; enforce line
     * breaks.
     *
     * @param   element  the element that should be converted to text
     *
     * @return  he combined text of the given element and all its children.
     */
    public static String getText(final org.jsoup.nodes.Element element) {
        final StringBuilder text = new StringBuilder();

        final List<Node> nodes = element.childNodes();

        for (final org.jsoup.nodes.Node n : nodes) {
            if (n instanceof TextNode) {
                text.append(((TextNode)n).text());
            } else if (n instanceof org.jsoup.nodes.Element) {
                final org.jsoup.nodes.Element e = (org.jsoup.nodes.Element)n;

                if (e.isBlock() || e.tagName().equals("br")) {
                    text.append(getText(e)).append('\n');
                } else {
                    text.append(getText(e));
                }
            }
        }

        return text.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   url  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static String loadUrlAsString(final URL url) throws Exception {
        final InputStream is = WebAccessManager.getInstance().doRequest(url);
        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String tmp;
        final StringBuilder sb = new StringBuilder();

        while ((tmp = br.readLine()) != null) {
            sb.append(tmp).append("\n");
        }

        return sb.toString();
    }
}
