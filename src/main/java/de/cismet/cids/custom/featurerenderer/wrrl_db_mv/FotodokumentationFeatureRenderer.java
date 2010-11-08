package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.ImageUtil;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author stefan
 */
public class FotodokumentationFeatureRenderer extends CustomCidsFeatureRenderer {

    private static final org.apache.log4j.Logger log;
    public static final BufferedImage ARROW;
    public static final FeatureAnnotationSymbol ARROW_NULL;

    static {
        log = org.apache.log4j.Logger.getLogger(FotodokumentationFeatureRenderer.class);
        try {
            URL arrowUrl = FotodokumentationFeatureRenderer.class.getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/angle.png");
            ARROW = ImageIO.read(arrowUrl);
            ARROW_NULL = new FeatureAnnotationSymbol(new ImageIcon("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/angle_null.png").getImage());
        } catch (Exception ex) {
            log.fatal(ex, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void assign() {
    }

    @Override
    public FeatureAnnotationSymbol getPointSymbol() {
        log.fatal("TADAAA!");
        final List<CidsBean> fotos = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
        for (CidsBean foto : fotos) {
            final Object winkelObj = foto.getProperty("angle");
            if (winkelObj instanceof Integer) {
                final int winkel = (Integer) winkelObj;
                BufferedImage rotatedArrow = ImageUtil.rotateImage(ARROW, winkel);
                FeatureAnnotationSymbol symb = new FeatureAnnotationSymbol(rotatedArrow);
//                symb.setSweetSpotX(0.5);
//                symb.setSweetSpotY(0.5);
                return symb;
            }
        }
        return ARROW_NULL;
    }
}
