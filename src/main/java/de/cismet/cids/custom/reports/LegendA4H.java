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
package de.cismet.cids.custom.reports;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class LegendA4H extends AbstractLegendPrintingTemplate {

    //~ Static fields/initializers ---------------------------------------------

    private static final int LEGEND_HEIGHT = 758;
    private static final int LEGEND_WIDTH = 535;

    //~ Methods ----------------------------------------------------------------

    @Override
    protected int getLegendWidth() {
        return LEGEND_WIDTH;
    }

    @Override
    protected int getLegendHeight() {
        return LEGEND_HEIGHT;
    }
}
