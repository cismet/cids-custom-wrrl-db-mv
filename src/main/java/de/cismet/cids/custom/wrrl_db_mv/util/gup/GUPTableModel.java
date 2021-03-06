/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.awt.GridBagConstraints;

import javax.swing.JComponent;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public interface GUPTableModel {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   x  DOCUMENT ME!
     * @param   y  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    JComponent getValue(int x, int y);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    int getRows();
    /**
     * DOCUMENT ME!
     *
     * @param   row  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    int getCols(int row);

    /**
     * DOCUMENT ME!
     *
     * @param   x  DOCUMENT ME!
     * @param   y  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    GridBagConstraints getConstraint(int x, int y);

    /**
     * DOCUMENT ME!
     *
     * @param   row  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    double getRowWeight(int row);

    /**
     * DOCUMENT ME!
     *
     * @param   row  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    JComponent getVerticalHeader(int row);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean showVerticalHeader();

    /**
     * DOCUMENT ME!
     *
     * @param   col  DOCUMENT ME!
     * @param   row  DOCUMENT ME!
     * @param   log  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    double getWeight(int col, int row, boolean log);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean fullScreen();
}
