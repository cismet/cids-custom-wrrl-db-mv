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

import de.cismet.cids.editors.DefaultBindableCheckboxField;
import de.cismet.cids.editors.DefaultBindableColorChooser;

/**
 * Contains some methods to set gui components to read only.
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

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableColorChooser cc) {
        cc.setReadOnly(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableCheckboxField cc) {
        cc.setReadOnly(true);
    }
}
