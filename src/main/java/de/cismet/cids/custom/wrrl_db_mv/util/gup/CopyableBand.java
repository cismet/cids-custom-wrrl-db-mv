/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.interfaces.BandMember;

import static de.cismet.cids.custom.wrrl_db_mv.util.gup.LineBand.membersToCopy;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CopyableBand extends DefaultBand {

    //~ Static fields/initializers ---------------------------------------------

    protected static ArrayList<BandMember> membersToCopy = null;

    //~ Instance fields --------------------------------------------------------

    protected javax.swing.JPopupMenu jPopupMenu1;
    protected boolean readOnly = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultBand object.
     */
    public CopyableBand() {
        super();
        initPopupMenu();
    }

    /**
     * Creates a new DefaultBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public CopyableBand(final float heightWeight) {
        super(heightWeight);
        initPopupMenu();
    }

    /**
     * Creates a new DefaultBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public CopyableBand(final String title) {
        super(title);
        initPopupMenu();
    }

    /**
     * Creates a new DefaultBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public CopyableBand(final float heightWeight, final String title) {
        super(heightWeight, title);
        initPopupMenu();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initPopupMenu() {
        prefix.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseReleased(final MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        openPopup(e);
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        openPopup(e);
                    }
                }

                private void openPopup(final MouseEvent e) {
                    final JMenuItem miCopy = new JMenuItem("Abschnitte kopieren");
                    final JMenuItem miPaste = new JMenuItem("Abschnitte einfügen");
                    miCopy.setEnabled((members != null) && !members.isEmpty());
                    miPaste.setEnabled(
                        !readOnly
                                && (membersToCopy != null)
                                && !membersToCopy.isEmpty()
                                && members.isEmpty()
                                && (getMin(membersToCopy) >= CopyableBand.this.getMin())
                                && (getMax(membersToCopy) <= CopyableBand.this.getMax()));

                    miCopy.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                membersToCopy = members;
                            }
                        });

                    miPaste.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                for (final BandMember member : membersToCopy) {
                                    addSpecifiedMember(member.getMin(), member.getMax());
                                }
                            }
                        });

                    jPopupMenu1 = new JPopupMenu();
                    jPopupMenu1.add(miCopy);
                    jPopupMenu1.add(miPaste);

                    jPopupMenu1.show((Component)e.getSource(), e.getX(), e.getY());
                }

                private double getMin(final ArrayList<BandMember> parts) {
                    double min = Double.MAX_VALUE;

                    for (final BandMember part : parts) {
                        if (part.getMin() < min) {
                            min = part.getMin();
                        }
                    }

                    return min;
                }

                private double getMax(final ArrayList<BandMember> parts) {
                    double max = Double.MIN_VALUE;

                    for (final BandMember part : parts) {
                        if (part.getMax() > max) {
                            max = part.getMax();
                        }
                    }

                    return max;
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  startStation  DOCUMENT ME!
     * @param  endStation    minStart DOCUMENT ME!
     */
    protected void addSpecifiedMember(final Double startStation, final Double endStation) {
        // overwrite this method when readOnly = false
    }
}
