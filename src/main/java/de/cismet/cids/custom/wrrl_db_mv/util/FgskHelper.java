/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import java.awt.Component;
import java.awt.event.FocusListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskHelper {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  box     DOCUMENT ME!
     * @param  fields  DOCUMENT ME!
     */
    public static void fillNvCheckbox(final JCheckBox box, final JTextField[] fields) {
        boolean nothing = true;
        for (final JTextField tmp : fields) {
            nothing &= (CidsBeanSupport.textToDouble(tmp, 1.0) == 0.0);
        }

        box.setSelected(nothing);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  l       DOCUMENT ME!
     * @param  fields  DOCUMENT ME!
     */
    public static void addListenerForNvCheck(final FocusListener l, final JTextField[] fields) {
        for (final JTextField tmp : fields) {
            tmp.addFocusListener(l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parent  DOCUMENT ME!
     * @param  box     DOCUMENT ME!
     * @param  fields  DOCUMENT ME!
     */
    public static void nvCheckBoxStateChange(final Component parent, final JCheckBox box, final JTextField[] fields) {
        if (box.isSelected()) {
            boolean nothing = true;
            for (final JTextField tmp : fields) {
                nothing &= (CidsBeanSupport.textToDouble(tmp, 0.0) == 0.0);
            }

            if (nothing) {
                for (final JTextField tmp : fields) {
                    tmp.setText("0");
                }
            } else {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(parent),
                    "Es sind bereits Felder auf einen Wert ungleich Null gesetzt.",
                    "Felder gesetzt",
                    JOptionPane.INFORMATION_MESSAGE);
                box.setSelected(false);
            }
        }
    }
}
