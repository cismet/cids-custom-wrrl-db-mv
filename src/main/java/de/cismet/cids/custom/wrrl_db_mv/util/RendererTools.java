/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RendererTools {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  tf  DOCUMENT ME!
     */
    public static void makeReadOnly(final JTextField tf) {
        tf.setBorder(null);
        tf.setOpaque(false);
        tf.setEditable(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final JComboBox cb) {
        cb.setEnabled(false);
        cb.setRenderer(new CustomListCellRenderer());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ta  DOCUMENT ME!
     */
    public static void makeReadOnly(final JTextArea ta) {
        ta.setEditable(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final JCheckBox cb) {
        cb.setEnabled(false);
    }
}
