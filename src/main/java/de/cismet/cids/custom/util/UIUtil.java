/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 stefan
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.util;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class UIUtil {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UIUtil object.
     *
     * @throws  AssertionError  DOCUMENT ME!
     */
    private UIUtil() {
        throw new AssertionError();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  ex      DOCUMENT ME!
     * @param  parent  DOCUMENT ME!
     */
    public static void showExceptionToUser(final Exception ex, final JComponent parent) {
        final ErrorInfo ei = new ErrorInfo(
                "Fehler",
                "Beim Vorgang ist ein Fehler aufgetreten",
                null,
                null,
                ex,
                Level.SEVERE,
                null);
        JXErrorPane.showDialog(parent, ei);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  component  DOCUMENT ME!
     */
    public static void findOptimalPositionOnScreen(final JDialog component) {
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        component.setSize(screenSize.width / 2, screenSize.height / 2);
        final java.awt.Insets insets = component.getInsets();
        component.setSize(component.getWidth() + insets.left + insets.right,
            component.getHeight()
                    + insets.top
                    + insets.bottom
                    + 20);
        component.setLocation((screenSize.width - component.getWidth()) / 2,
            (screenSize.height - component.getHeight())
                    / 2);
    }
}
