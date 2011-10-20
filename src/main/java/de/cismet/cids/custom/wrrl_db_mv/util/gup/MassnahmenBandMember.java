/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * MassnahmenBandMember.java
 *
 * Created on 18.10.2011, 21:11:46
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandMemberMouseListeningComponent;
import de.cismet.tools.gui.jbands.interfaces.BandMemberSelectable;
import de.cismet.tools.gui.jbands.interfaces.Section;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class MassnahmenBandMember extends JXPanel implements BandMember,
    Section,
    CidsBeanStore,
    BandMemberMouseListeningComponent,
    BandMemberSelectable {

    //~ Instance fields --------------------------------------------------------

    double von = 0;
    double bis = 0;
    PinstripePainter stripes = new PinstripePainter();
    Painter unselectedBackgroundPainter = null;
    Painter selectedBackgroundPainter = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private CidsBean bean;
    private boolean isSelected = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     */
    public MassnahmenBandMember() {
        initComponents();
        stripes.setPaint(new Color(200, 200, 200, 200));
        stripes.setSpacing(5.0);
        setAlpha(0.8f);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return this;
    }

    @Override
    public double getMax() {
        return (von < bis) ? bis : von;
    }

    @Override
    public double getMin() {
        return (von < bis) ? von : bis;
    }

    @Override
    public double getFrom() {
        return von;
    }

    @Override
    public double getTo() {
        return bis;
    }

    @Override
    public CidsBean getCidsBean() {
        return bean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bean = cidsBean;
        von = (Double)bean.getProperty("linie.von.wert");
        bis = (Double)bean.getProperty("linie.bis.wert");
        final int action = (Integer)bean.getProperty("massnahme.id");
        if (bean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase("gup_massnahme_sonstige")) {
            switch (action) {
                case 1: {
                    // Gehölzpflanzung
                    setBackgroundPainter(new MattePainter(new Color(105, 103, 88)));
                    break;
                }
                case 2: {
                    // Gehölzpflege
                    setBackgroundPainter(new MattePainter(new Color(197, 188, 142)));
                    break;
                }
                case 3: {
                    // Mähguträumung
                    setBackgroundPainter(new MattePainter(new Color(238, 230, 171)));
                }
            }
        } else {
            switch (action) {
                case 1: {
                    // Mahd mit Mäh-, Harkkombination
                    setBackgroundPainter(new MattePainter(new Color(229, 252, 194)));
                    break;
                }
                case 2: {
                    // Mahd mit Schlägelmähwerk
                    setBackgroundPainter(new MattePainter(new Color(69, 173, 168)));
                    break;
                }
                case 3: {
                    // Mähkorb
                    setBackgroundPainter(new MattePainter(new Color(69, 173, 168)));
                    break;
                }
                case 4: {
                    // Handmahd
                    setBackgroundPainter(new MattePainter(new Color(229, 252, 194)));

                    break;
                }
                case 5: {
                    // Mähboot
                    setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(157, 224, 173)), stripes));
                    break;
                }
                case 6: {
                    // Grundräumung
                    setBackgroundPainter(new MattePainter(new Color(89, 79, 79)));
                    break;
                }
                case 7: {
                    // Mähboot/Mähbalken
                    setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(69, 173, 168)), stripes));
                    break;
                }
                case 8: {
                    // Mähwerk
                    setBackgroundPainter(new MattePainter(new Color(84, 121, 128)));
                    break;
                }
                case 9: {
                    // Handmahd/Mähboot
                    setBackgroundPainter(new CompoundPainter(new MattePainter(new Color(229, 252, 194)), stripes));
                }
            }
        }
        unselectedBackgroundPainter = getBackgroundPainter();
        selectedBackgroundPainter = new CompoundPainter(
                unselectedBackgroundPainter,
                new RectanglePainter(
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    true,
                    new Color(100, 100, 100, 100),
                    2f,
                    new Color(50, 50, 50, 100)));

        setToolTipText(bean.getProperty("massnahme.name") + "");
        System.out.println(von
                    + "-->" + bis + ":");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 201, Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 87, Short.MAX_VALUE));
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        setAlpha(1f);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        setAlpha(0.8f);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(final boolean selection) {
        isSelected = selection;
        if (!isSelected) {
            setBackgroundPainter(unselectedBackgroundPainter);
        } else {
            setBackgroundPainter(selectedBackgroundPainter);
        }
    }

    @Override
    public BandMember getBandMember() {
        return this;
    }
}
