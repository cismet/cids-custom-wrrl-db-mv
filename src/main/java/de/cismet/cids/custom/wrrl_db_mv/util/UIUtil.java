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
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.MetaCatalogueTree;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.sql.Timestamp;

import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class UIUtil {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            UIUtil.class);

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

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     * @param  lab       DOCUMENT ME!
     */
    public static void setLastModifier(final CidsBean cidsBean, final JLabel lab) {
        Object avUser = cidsBean.getProperty("av_user");
        Object avTime = cidsBean.getProperty("av_time");

        if (avUser == null) {
            avUser = "(unbekannt)";
        }
        if (avTime instanceof Timestamp) {
            avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
        } else {
            avTime = "(unbekannt)";
        }

        lab.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
    }

    /**
     * refreshs the navigator tree.
     *
     * @param  treePath  DOCUMENT ME!
     */
    public static void refreshTree(final TreePath treePath) {
        if (treePath != null) {
            try {
                final MetaCatalogueTree tree = ComponentRegistry.getRegistry().getCatalogueTree();
                ((DefaultTreeModel)tree.getModel()).reload();
                tree.exploreSubtree(treePath);
//                final Enumeration<TreeNode> firstChilds = parentNode.children();
//
//                while (firstChilds.hasMoreElements()) {
//                    final TreeNode firstChild = firstChilds.nextElement();
//                    ComponentRegistry.getRegistry()
//                            .getCatalogueTree()
//                            .refreshTreePath(treePath.pathByAddingChild(firstChild));
//                }
            } catch (Exception e) {
                LOG.error("Error when refreshing Tree", e); // NOI18N
            }
        }
    }
}
