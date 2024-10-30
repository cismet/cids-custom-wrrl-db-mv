/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * TabbedPaneUITransparent.java
 *
 * Created on 5. November 2007, 15:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

/**
 *
 * @author srichter
 */

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TabbedPaneUITransparent extends BasicTabbedPaneUI {

    //~ Static fields/initializers ---------------------------------------------

    /** Keys to use for forward focus traversal when the JComponent is managing focus. */
    private static Set<KeyStroke> managingFocusForwardTraversalKeys;

    /** Keys to use for backward focus traversal when the JComponent is managing focus. */
    private static Set<KeyStroke> managingFocusBackwardTraversalKeys;

    //~ Methods ----------------------------------------------------------------

    @Override
    protected void installDefaults() {
        super.installDefaults();

        // focus forward traversal key
        if (managingFocusForwardTraversalKeys == null) {
            managingFocusForwardTraversalKeys = new HashSet<>();
            managingFocusForwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        }
        tabPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, managingFocusForwardTraversalKeys);
        // focus backward traversal key
        if (managingFocusBackwardTraversalKeys == null) {
            managingFocusBackwardTraversalKeys = new HashSet<>();
            managingFocusBackwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK));
        }
        tabPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, managingFocusBackwardTraversalKeys);
    }

    @Override
    protected void uninstallDefaults() {
        // sets the focus forward and backward traversal keys to null
        // to restore the defaults
        tabPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        tabPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        super.uninstallDefaults();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   c  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ComponentUI createUI(final JComponent c) {
        return new TabbedPaneUITransparent();
    }

    @Override
    protected void paintContentBorder(final Graphics g, final int arg1, final int arg2) {
    }

    @Override
    protected void paintTabBackground(final Graphics g,
            final int tabPlacement,
            final int tabIndex,
            final int x,
            final int y,
            final int w,
            final int h,
            final boolean isSelected) {
        super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
    }

    @Override
    protected void paintTab(final Graphics g,
            final int tabPlacement,
            final Rectangle[] rects,
            final int tabIndex,
            final Rectangle iconRect,
            final Rectangle textRect) {
        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
    }

    @Override
    protected int calculateTabHeight(final int tabPlacement, final int tabIndex, final int fontHeight) {
        final int retValue;
        retValue = super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 15;
        return retValue;
    }

    @Override
    protected void paintText(final Graphics g,
            final int tabPlacement,
            final Font font,
            final FontMetrics metrics,
            final int tabIndex,
            final String title,
            final Rectangle textRect,
            final boolean isSelected) {
        ((Graphics2D)g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
    }

    @Override
    protected Insets getContentBorderInsets(final int tabPlacement) {
        return new Insets(2, 0, 0, 2);
    }

    @Override
    protected void paintFocusIndicator(final Graphics g,
            final int tabPlacement,
            final Rectangle[] rects,
            final int tabIndex,
            final Rectangle iconRect,
            final Rectangle textRect,
            final boolean isSelected) {
    }
}
