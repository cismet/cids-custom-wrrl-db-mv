/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * AbschnittsinfoMember.java
 *
 * Created on 21.10.2011, 10:47:29
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.JXPanel;

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
public class AbschnittsinfoMember extends JXPanel implements BandMember, Section, CidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    CidsBean abschnittsInfo;
    double from;
    double to;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form AbschnittsinfoMember.
     */
    public AbschnittsinfoMember() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 300, Short.MAX_VALUE));
    } // </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public JComponent getBandMemberComponent() {
        return this;
    }

    @Override
    public double getMax() {
        return to;
    }

    @Override
    public double getMin() {
        return from;
    }

    @Override
    public double getFrom() {
        return from;
    }

    @Override
    public double getTo() {
        return to;
    }

    @Override
    public CidsBean getCidsBean() {
        return abschnittsInfo;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        abschnittsInfo = cidsBean;
        from = (Double)abschnittsInfo.getProperty("linie.von.wert");
        to = (Double)abschnittsInfo.getProperty("linie.bis.wert");
    }
}