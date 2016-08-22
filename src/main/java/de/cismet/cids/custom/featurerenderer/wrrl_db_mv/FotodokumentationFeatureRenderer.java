/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import java.awt.image.BufferedImage;

import java.net.URL;

import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.tools.gui.ImageUtil;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FotodokumentationFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log;
    public static final BufferedImage ARROW;
    public static final FeatureAnnotationSymbol ARROW_NULL;

    static {
        log = org.apache.log4j.Logger.getLogger(FotodokumentationFeatureRenderer.class);
        try {
            final URL arrowUrl = FotodokumentationFeatureRenderer.class.getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/angle.png");
            ARROW = ImageIO.read(arrowUrl);
            ARROW_NULL = new FeatureAnnotationSymbol(new ImageIcon(
                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/angle_null.png").getImage());
        } catch (Exception ex) {
            log.fatal(ex, ex);
            throw new RuntimeException(ex);
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void assign() {
    }

    @Override
    public FeatureAnnotationSymbol getPointSymbol() {
        final List<CidsBean> fotos = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
        for (final CidsBean foto : fotos) {
            final Object winkelObj = foto.getProperty("angle");
            if (winkelObj instanceof Integer) {
                final int winkel = (Integer)winkelObj;
                final BufferedImage rotatedArrow = ImageUtil.rotateImage(ARROW, -winkel);
                final FeatureAnnotationSymbol symb = new FeatureAnnotationSymbol(rotatedArrow);
                symb.setSweetSpotX(0.5);
                symb.setSweetSpotY(0.5);
                return symb;
            }
        }
        return ARROW_NULL;
    }
}
