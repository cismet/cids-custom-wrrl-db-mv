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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.painter.MattePainter;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ExtendedMattePainter extends MattePainter {

    //~ Instance fields --------------------------------------------------------

    double factor;
    boolean invertSides;

    private Paint secondPaint;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ExtendedMattePainter object.
     *
     * @param  paint           DOCUMENT ME!
     * @param  paintStretched  DOCUMENT ME!
     */
    public ExtendedMattePainter(final Paint paint, final boolean paintStretched) {
        super(paint, paintStretched);
    }

    /**
     * Creates a new ExtendedMattePainter object.
     *
     * @param  paint        DOCUMENT ME!
     * @param  secondPaint  DOCUMENT ME!
     * @param  factor       DOCUMENT ME!
     * @param  invertSides  DOCUMENT ME!
     */
    public ExtendedMattePainter(final Paint paint,
            final Paint secondPaint,
            final double factor,
            final boolean invertSides) {
        super(paint);
        this.secondPaint = secondPaint;
        this.factor = factor;
        this.invertSides = invertSides;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected void doPaint(final Graphics2D g, final Object component, final int width, final int height) {
        final Paint p = getFillPaint();
        if (p != null) {
            g.setPaint(p);
            g.fill(provideShape(g, component, width, height, true));
            g.setPaint(secondPaint);
            g.fill(provideShape(g, component, width, height, false));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   g         DOCUMENT ME!
     * @param   comp      DOCUMENT ME!
     * @param   width     DOCUMENT ME!
     * @param   height    DOCUMENT ME!
     * @param   mainSide  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Shape provideShape(final Graphics2D g,
            final Object comp,
            final int width,
            final int height,
            final boolean mainSide) {
        if (!invertSides) {
            final int offset = (int)(factor * height);
            if (mainSide) {
                return new Rectangle(0, offset, width, height - offset);
            } else {
                return new Rectangle(0, 0, width, offset);
            }
        } else {
            final int offset = (int)((1 - factor) * height);

            if (mainSide) {
                return new Rectangle(0, 0, width, offset);
            } else {
                return new Rectangle(0, offset, width, height - offset);
            }
        }
    }
}
