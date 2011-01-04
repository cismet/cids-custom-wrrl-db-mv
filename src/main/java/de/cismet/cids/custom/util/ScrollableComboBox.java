/*
 * Copyright (c) 2001-2009 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.cismet.cids.custom.util;

import Sirius.server.middleware.types.MetaClass;
import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.Rectangle;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticComboBoxUI;
import de.cismet.cids.dynamics.CidsBean;
import java.util.Comparator;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalScrollBarUI;

/**
 *
 * @author therter
 */
public class ScrollableComboBox extends DefaultBindableReferenceCombo {

    public ScrollableComboBox(final MetaClass mc) {
        super(mc);
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }


    public ScrollableComboBox(final MetaClass mc, boolean nullable, boolean onlyUsed) {
        super(mc, nullable, onlyUsed);
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }

    public ScrollableComboBox(final MetaClass mc, boolean nullable, boolean onlyUsed, Comparator<CidsBean> comparator) {
        super(mc, nullable, onlyUsed, comparator);
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }

    public ScrollableComboBox() {
        super();
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }

    public ScrollableComboBox(Comparator<CidsBean> comparator) {
        super(comparator);
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }

    @Override
    public void setUI(ComboBoxUI ui) {
        super.setUI(ui);
        if (getUI() instanceof PlasticComboBoxUI) {
            setUI(ScrollableComboUI.createUI(null));
        }
    }
}

class ScrollableComboUI extends PlasticComboBoxUI {

    private ScrollableComboUI() {
    }

    public static ComponentUI createUI(JComponent b) {
        // ensures the invocation of ensurePhantomHasPlasticUI() of the super type;
        PlasticComboBoxUI.createUI(b);
        return new ScrollableComboUI();
    }

    @Override
    protected ComboPopup createPopup() {
        return new PlasticScrollableComboPopup(comboBox);
    }

    /**
     * Differs from the BasicComboPopup in that it uses the standard
     * popmenu border and honors an optional popup prototype display value.
     *
     * This code was written by Karsten Lentzsch and modified by therter
     */
    private class PlasticScrollableComboPopup extends BasicComboPopup {

        private PlasticScrollableComboPopup(JComboBox combo) {
            super(combo);
        }

        /**
         * Configures the list created by #createList().
         */
        @Override
        protected void configureList() {
            super.configureList();
            list.setForeground(UIManager.getColor("MenuItem.foreground"));
            list.setBackground(UIManager.getColor("MenuItem.background"));
        }

        @Override
        protected JScrollPane createScroller() {
            JScrollPane sp = new JScrollPane( list,
                                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
            return sp;
        }

        /**
         * Configures the JScrollPane created by #createScroller().
         */
        @Override
        protected void configureScroller() {
            super.configureScroller();
            scroller.getVerticalScrollBar().putClientProperty(
                MetalScrollBarUI.FREE_STANDING_PROP,
                Boolean.FALSE);
            scroller.getHorizontalScrollBar().putClientProperty(
                MetalScrollBarUI.FREE_STANDING_PROP,
                Boolean.FALSE);
        }

        /**
         * Calculates the placement and size of the popup portion
         * of the combo box based on the combo box location and
         * the enclosing screen bounds. If no transformations are required,
         * then the returned rectangle will have the same values
         * as the parameters.<p>
         *
         * In addition to the superclass behavior, this class offers
         * to use the combo's popup prototype display value to compute
         * the popup menu width. This is an optional feature of the
         * JGoodies Plastic L&amp;fs implemented via a client property key.<p>
         *
         * If a prototype is set, the popup width is the maximum of the
         * combobox width and the prototype based popup width.
         * For the latter the renderer is used to render the prototype.
         * The prototype based popup width is the prototype's width
         * plus the scrollbar width - if any. The scrollbar test checks
         * if there are more items than the combo's maximum row count.
         *
         * @param px starting x location
         * @param py starting y location
         * @param pw starting width
         * @param ph starting height
         * @return a rectangle which represents the placement and size of the popup
         *
         * @see Options#COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY
         * @see JComboBox#getMaximumRowCount()
         */
        @Override
        protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
                Rectangle defaultBounds = super.computePopupBounds(px, py, pw, ph);
                Object popupPrototypeDisplayValue = comboBox.getClientProperty(
                        Options.COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY);
                if (popupPrototypeDisplayValue == null) {
                    return defaultBounds;
                }

                ListCellRenderer renderer = list.getCellRenderer();
                Component c = renderer.getListCellRendererComponent(
                        list, popupPrototypeDisplayValue, -1, true, true);
                pw = c.getPreferredSize().width;
                boolean hasVerticalScrollBar =
                    comboBox.getItemCount() > comboBox.getMaximumRowCount();
                if (hasVerticalScrollBar) {
                    // Add the scrollbar width.
                    JScrollBar verticalBar = scroller.getVerticalScrollBar();
                    pw += verticalBar.getPreferredSize().width;
                }
                Rectangle prototypeBasedBounds = super.computePopupBounds(px, py, pw, ph);
                return prototypeBasedBounds.width > defaultBounds.width
                    ? prototypeBasedBounds
                    : defaultBounds;
        }

    }
}