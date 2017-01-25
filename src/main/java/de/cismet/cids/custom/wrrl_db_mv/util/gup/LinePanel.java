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

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class LinePanel extends JPanel {

    //~ Instance fields --------------------------------------------------------

    private boolean big = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LinePanel object.
     */
    public LinePanel() {
    }

    /**
     * Creates a new LinePanel object.
     *
     * @param  big  DOCUMENT ME!
     */
    public LinePanel(final boolean big) {
        this.big = big;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void paint(final Graphics g) {
        int x = getWidth() / 2;

        if (getWidth() == 1) {
            x = 0;
        }

        if (isBig()) {
            g.drawLine(x, 0, x, 14);
        } else {
            g.drawLine(x, 0, x, 6);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the big
     */
    public boolean isBig() {
        return big;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  big  the big to set
     */
    public void setBig(final boolean big) {
        this.big = big;
    }
}
