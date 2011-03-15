/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jweintraut
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
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;

import java.sql.Timestamp;

import java.text.DateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * Renders an entity of class GEO_HINT in the mapping component (cismap Plugin). Displays the feature being colored
 * depending on the hint's priority. Additionally, the infoPanel will display the name of the user who added this hint,
 * the specification time and the comment.
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class GeoHintFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color GEOHINT_COLOR_HIGH_PRIORITY = new Color(255, 0, 0);
    private static final Color GEOHINT_COLOR_NORMAL_PRIORITY = new Color(255, 255, 0);
    private static final Color GEOHINT_COLOR_LOW_PRIORITY = new Color(0, 255, 0);
    private static final Color GEOHINT_COLOR_FALLBACK = new Color(0, 0, 255);

    private static Logger LOG = Logger.getLogger(GeoHintFeatureRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private JPanel pnlMore;
    private JLabel lblUsrAndTimestamp;
    private JTextArea txtAComment;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GeoHintFeatureRenderer object.
     */
    public GeoHintFeatureRenderer() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Initializes the necessary components.
     */
    private void initComponents() {
        pnlMore = new JPanel();
        lblUsrAndTimestamp = new JLabel();
        txtAComment = new JTextArea();

        txtAComment.setLineWrap(true);
        txtAComment.setWrapStyleWord(true);
        txtAComment.setEditable(false);
        txtAComment.setBorder(null);

        pnlMore.setLayout(new BorderLayout());
        pnlMore.add(lblUsrAndTimestamp, BorderLayout.PAGE_START);
        pnlMore.add(txtAComment, BorderLayout.CENTER);

        add(pnlMore);
    }

    @Override
    public void setMetaObject(final MetaObject metaObject) throws ConnectionException {
        super.setMetaObject(metaObject);

        if (cidsBean != null) {
            final String usr = (String)cidsBean.getProperty("usr");
            final String comment = (String)cidsBean.getProperty("comment");
            final Timestamp timestamp = (Timestamp)cidsBean.getProperty("timestamp");

            final String date = DateFormat.getDateInstance().format(timestamp);
            final String time = DateFormat.getTimeInstance().format(timestamp);
            lblUsrAndTimestamp.setText(NbBundle.getMessage(
                    GeoHintFeatureRenderer.class,
                    "GeoHintFeatureRenderer.lblUsrAndTimestamp.text",
                    usr,
                    date,
                    time));
            txtAComment.setText(comment);
        }
    }

    @Override
    public Paint getLinePaint(final CidsFeature subFeature) {
        return getFillingStyle();
    }

    @Override
    public Paint getFillingStyle() {
        Paint result = GEOHINT_COLOR_FALLBACK;

        if (cidsBean != null) {
            final Object priority = cidsBean.getProperty("priority");
            if (priority instanceof CidsBean) {
                final CidsBean priorityBean = (CidsBean)priority;
                if (priorityBean.getProperty("id") instanceof Integer) {
                    final Integer priorityValue = (Integer)priorityBean.getProperty("id");
                    if (priorityValue.intValue() == 1) {
                        result = GEOHINT_COLOR_HIGH_PRIORITY;
                    } else if (priorityValue.intValue() == 2) {
                        result = GEOHINT_COLOR_NORMAL_PRIORITY;
                    } else if (priorityValue.intValue() == 3) {
                        result = GEOHINT_COLOR_LOW_PRIORITY;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void assign() {
        // NOP
    }
}
