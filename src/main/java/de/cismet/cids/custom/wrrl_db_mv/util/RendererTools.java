/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.cismet.cids.editors.DefaultBindableCheckboxField;
import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.DefaultBindableDateChooser;

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
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableDateChooser cb) {
        cb.setEditable(false);
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
     * @param  spinner  ta DOCUMENT ME!
     */
    public static void makeReadOnly(final JSpinner spinner) {
        spinner.setEnabled(false);
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

    /**
     * DOCUMENT ME!
     *
     * @param  rb  cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JRadioButton rb) {
        rb.setEnabled(false);
        rb.setForeground(new Color(76, 76, 76));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tf  DOCUMENT ME!
     */
    public static void makeWritable(final JTextField tf) {
        tf.setBorder(new JTextField().getBorder());
        tf.setOpaque(true);
        tf.setEditable(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final JComboBox cb) {
        cb.setEnabled(true);
        cb.setRenderer((new JComboBox()).getRenderer());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableDateChooser cb) {
        cb.setEditable(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ta  DOCUMENT ME!
     */
    public static void makeWritable(final JTextArea ta) {
        ta.setEditable(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  spinner  ta DOCUMENT ME!
     */
    public static void makeWritable(final JSpinner spinner) {
        spinner.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final JCheckBox cb) {
        cb.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableColorChooser cc) {
        cc.setReadOnly(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableCheckboxField cc) {
        cc.setReadOnly(false);
    }
}
