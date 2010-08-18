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

import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 *
 * @author stefan
 */
public class UIUtil {

    private UIUtil() {
        throw new AssertionError();
    }

    public static void showExceptionToUser(Exception ex, JComponent parent) {
        final ErrorInfo ei = new ErrorInfo("Fehler", "Beim Vorgang ist ein Fehler aufgetreten", null,
                null, ex, Level.SEVERE, null);
        JXErrorPane.showDialog(parent, ei);
    }

    public static void findOptimalPositionOnScreen(JDialog component) {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        component.setSize(screenSize.width / 2, screenSize.height / 2);
        java.awt.Insets insets = component.getInsets();
        component.setSize(component.getWidth() + insets.left + insets.right, component.getHeight() + insets.top + insets.bottom + 20);
        component.setLocation((screenSize.width - component.getWidth()) / 2, (screenSize.height - component.getHeight()) / 2);
    }
}
