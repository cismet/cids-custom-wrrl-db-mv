/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * AltlastRenderer.java
 *
 * Created on 07.12.2011, 10:28:41
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.method.MethodManager;

import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.renderer.ListCellContext;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.ValidationException;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenartSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenvorschlagSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.server.search.AbstractCidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.commons.concurrency.CismetConcurrency;

import de.cismet.tools.Equals;

import de.cismet.tools.gui.VerticalTextIcon;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SimSimulationsabschnittEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final CalcCache cache = CalcCache.getInstance();
    private static final Logger LOG = Logger.getLogger(SimSimulationsabschnittEditor.class);

    //~ Instance fields --------------------------------------------------------

    private String title;
    private CidsBean simulation;
    private CidsBean cidsBean;
    private List<CidsBean> massnahmen;
    private final int scale = 12;
    private boolean readOnly;
    private DefaultListModel<CidsBean> model = new DefaultListModel<CidsBean>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JButton jbRem;
    private javax.swing.JButton jbVorschlag;
    private javax.swing.JLabel lblAnzBesSohlstrukturen;
    private javax.swing.JLabel lblAnzBesSohlstrukturenVal;
    private javax.swing.JLabel lblAnzBesUferstrukturenLi;
    private javax.swing.JLabel lblAnzBesUferstrukturenLiVal;
    private javax.swing.JLabel lblAnzBesUferstrukturenRe;
    private javax.swing.JLabel lblAnzBesUferstrukturenReVal;
    private javax.swing.JLabel lblAnzLa;
    private javax.swing.JLabel lblAnzLaVal;
    private javax.swing.JLabel lblAnzLauf;
    private javax.swing.JLabel lblAnzLaufVal;
    private javax.swing.JLabel lblAnzQuerbaenke;
    private javax.swing.JLabel lblAnzQuerbaenkeVal;
    private javax.swing.JLabel lblBelastungenSohle;
    private javax.swing.JLabel lblBelastungenSohleVal;
    private javax.swing.JLabel lblBesondereUferbelastungenLi;
    private javax.swing.JLabel lblBesondereUferbelastungenLiVal;
    private javax.swing.JLabel lblBesondereUferbelastungenRe;
    private javax.swing.JLabel lblBesondereUferbelastungenReVal;
    private javax.swing.JLabel lblBreitenerosion;
    private javax.swing.JLabel lblBreitenerosionVal;
    private javax.swing.JLabel lblBreitenvarianz;
    private javax.swing.JLabel lblBreitenvarianzVal;
    private javax.swing.JLabel lblFlaechennutzungLi;
    private javax.swing.JLabel lblFlaechennutzungLiVal;
    private javax.swing.JLabel lblFlaechennutzungRe;
    private javax.swing.JLabel lblFlaechennutzungReVal;
    private javax.swing.JLabel lblFliessgeschwindigkeit;
    private javax.swing.JLabel lblFliessgeschwindigkeitVal;
    private javax.swing.JLabel lblGewaesserrandstreifenLi;
    private javax.swing.JLabel lblGewaesserrandstreifenLiVal;
    private javax.swing.JLabel lblGewaesserrandstreifenRe;
    private javax.swing.JLabel lblGewaesserrandstreifenReVal;
    private javax.swing.JLabel lblGewaesserumfeld;
    private javax.swing.JLabel lblIndFische;
    private javax.swing.JLabel lblIndMkPhy;
    private javax.swing.JLabel lblIndMzb;
    private javax.swing.JLabel lblIndOMKosten;
    private javax.swing.JLabel lblKruemmungserosion;
    private javax.swing.JLabel lblKruemmungserosionVal;
    private javax.swing.JLabel lblLaengsprofil;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblLaufentwicklung;
    private javax.swing.JLabel lblLaufkr;
    private javax.swing.JLabel lblLaufkrVal;
    private javax.swing.JLabel lblMassn;
    private javax.swing.JLabel lblProfiltyp;
    private javax.swing.JLabel lblProfiltypVal;
    private javax.swing.JLabel lblQuerprofil;
    private javax.swing.JLabel lblSchaedlicheUmfeldstrukturenLi;
    private javax.swing.JLabel lblSchaedlicheUmfeldstrukturenLiVal;
    private javax.swing.JLabel lblSchaedlicheUmfeldstrukturenRe;
    private javax.swing.JLabel lblSchaedlicheUmfeldstrukturenReVal;
    private javax.swing.JLabel lblSohle;
    private javax.swing.JLabel lblSohlenstruktur;
    private javax.swing.JLabel lblSohltiefe;
    private javax.swing.JLabel lblSohltiefeVal;
    private javax.swing.JLabel lblSohlverbau;
    private javax.swing.JLabel lblSohlverbauVal;
    private javax.swing.JLabel lblStroemngsdiversitaet;
    private javax.swing.JLabel lblStroemngsdiversitaetVal;
    private javax.swing.JLabel lblSubstratdiversitaet;
    private javax.swing.JLabel lblSubstratdiversitaetVal;
    private javax.swing.JLabel lblTiefenerosion;
    private javax.swing.JLabel lblTiefenerosionVal;
    private javax.swing.JLabel lblTiefenvarianz;
    private javax.swing.JLabel lblTiefenvarianzVal;
    private javax.swing.JLabel lblUfer;
    private javax.swing.JLabel lblUferbewuchsLi;
    private javax.swing.JLabel lblUferbewuchsLiVal;
    private javax.swing.JLabel lblUferbewuchsRe;
    private javax.swing.JLabel lblUferbewuchsReVal;
    private javax.swing.JLabel lblUferstruktur;
    private javax.swing.JLabel lblUferverbauLi;
    private javax.swing.JLabel lblUferverbauLiVal;
    private javax.swing.JLabel lblUferverbauRe;
    private javax.swing.JLabel lblUferverbauReVal;
    private javax.swing.JList liMassn;
    private javax.swing.JPanel panLand;
    private javax.swing.JPanel panSohle;
    private javax.swing.JPanel panUfer;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form AltlastRenderer.
     */
    public SimSimulationsabschnittEditor() {
        this(false);
    }

    /**
     * Creates new form AltlastRenderer.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public SimSimulationsabschnittEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (!readOnly) {
            new CidsBeanDropTarget(liMassn);
        }

        liMassn.setModel(model);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panSohle = new javax.swing.JPanel();
        lblSohle = new javax.swing.JLabel();
        lblLaufkr = new javax.swing.JLabel();
        lblLaufkrVal = new javax.swing.JLabel();
        lblAnzLa = new javax.swing.JLabel();
        lblAnzLaVal = new javax.swing.JLabel();
        lblAnzLauf = new javax.swing.JLabel();
        lblAnzLaufVal = new javax.swing.JLabel();
        lblKruemmungserosion = new javax.swing.JLabel();
        lblKruemmungserosionVal = new javax.swing.JLabel();
        lblAnzQuerbaenke = new javax.swing.JLabel();
        lblAnzQuerbaenkeVal = new javax.swing.JLabel();
        lblStroemngsdiversitaet = new javax.swing.JLabel();
        lblStroemngsdiversitaetVal = new javax.swing.JLabel();
        lblTiefenvarianz = new javax.swing.JLabel();
        lblTiefenvarianzVal = new javax.swing.JLabel();
        lblTiefenerosion = new javax.swing.JLabel();
        lblTiefenerosionVal = new javax.swing.JLabel();
        lblFliessgeschwindigkeit = new javax.swing.JLabel();
        lblFliessgeschwindigkeitVal = new javax.swing.JLabel();
        lblSubstratdiversitaet = new javax.swing.JLabel();
        lblSubstratdiversitaetVal = new javax.swing.JLabel();
        lblAnzBesSohlstrukturen = new javax.swing.JLabel();
        lblAnzBesSohlstrukturenVal = new javax.swing.JLabel();
        lblSohlverbau = new javax.swing.JLabel();
        lblSohlverbauVal = new javax.swing.JLabel();
        lblBelastungenSohle = new javax.swing.JLabel();
        lblBelastungenSohleVal = new javax.swing.JLabel();
        lblLaufentwicklung = new javax.swing.JLabel();
        lblLaengsprofil = new javax.swing.JLabel();
        lblSohlenstruktur = new javax.swing.JLabel();
        panUfer = new javax.swing.JPanel();
        lblUfer = new javax.swing.JLabel();
        lblSohltiefe = new javax.swing.JLabel();
        lblSohltiefeVal = new javax.swing.JLabel();
        lblBreitenerosion = new javax.swing.JLabel();
        lblBreitenerosionVal = new javax.swing.JLabel();
        lblBreitenvarianz = new javax.swing.JLabel();
        lblBreitenvarianzVal = new javax.swing.JLabel();
        lblProfiltyp = new javax.swing.JLabel();
        lblProfiltypVal = new javax.swing.JLabel();
        lblAnzBesUferstrukturenRe = new javax.swing.JLabel();
        lblAnzBesUferstrukturenReVal = new javax.swing.JLabel();
        lblAnzBesUferstrukturenLi = new javax.swing.JLabel();
        lblAnzBesUferstrukturenLiVal = new javax.swing.JLabel();
        lblUferbewuchsRe = new javax.swing.JLabel();
        lblUferbewuchsReVal = new javax.swing.JLabel();
        lblUferbewuchsLi = new javax.swing.JLabel();
        lblUferbewuchsLiVal = new javax.swing.JLabel();
        lblUferverbauRe = new javax.swing.JLabel();
        lblUferverbauReVal = new javax.swing.JLabel();
        lblUferverbauLi = new javax.swing.JLabel();
        lblUferverbauLiVal = new javax.swing.JLabel();
        lblBesondereUferbelastungenRe = new javax.swing.JLabel();
        lblBesondereUferbelastungenReVal = new javax.swing.JLabel();
        lblBesondereUferbelastungenLi = new javax.swing.JLabel();
        lblBesondereUferbelastungenLiVal = new javax.swing.JLabel();
        lblQuerprofil = new javax.swing.JLabel();
        lblUferstruktur = new javax.swing.JLabel();
        panLand = new javax.swing.JPanel();
        lblLand = new javax.swing.JLabel();
        lblGewaesserrandstreifenRe = new javax.swing.JLabel();
        lblGewaesserrandstreifenReVal = new javax.swing.JLabel();
        lblGewaesserrandstreifenLi = new javax.swing.JLabel();
        lblGewaesserrandstreifenLiVal = new javax.swing.JLabel();
        lblFlaechennutzungRe = new javax.swing.JLabel();
        lblFlaechennutzungReVal = new javax.swing.JLabel();
        lblFlaechennutzungLi = new javax.swing.JLabel();
        lblFlaechennutzungLiVal = new javax.swing.JLabel();
        lblSchaedlicheUmfeldstrukturenRe = new javax.swing.JLabel();
        lblSchaedlicheUmfeldstrukturenReVal = new javax.swing.JLabel();
        lblSchaedlicheUmfeldstrukturenLi = new javax.swing.JLabel();
        lblSchaedlicheUmfeldstrukturenLiVal = new javax.swing.JLabel();
        lblGewaesserumfeld = new javax.swing.JLabel();
        jbVorschlag = new javax.swing.JButton();
        lblMassn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liMassn = new CidsBeanDropList();
        jbRem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblIndFische = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblIndMzb = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        lblIndMkPhy = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        lblIndOMKosten = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        panSohle.setOpaque(false);
        panSohle.setLayout(new java.awt.GridBagLayout());

        lblSohle.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSohle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSohle, gridBagConstraints);

        lblLaufkr.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblLaufkr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblLaufkr, gridBagConstraints);

        lblLaufkrVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaufkrVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblLaufkrVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblLaufkrVal, gridBagConstraints);

        lblAnzLa.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzLa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzLa, gridBagConstraints);

        lblAnzLaVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzLaVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzLaVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzLaVal, gridBagConstraints);

        lblAnzLauf.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzLauf.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzLauf, gridBagConstraints);

        lblAnzLaufVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzLaufVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzLaufVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzLaufVal, gridBagConstraints);

        lblKruemmungserosion.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblKruemmungserosion.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblKruemmungserosion, gridBagConstraints);

        lblKruemmungserosionVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKruemmungserosionVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblKruemmungserosionVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblKruemmungserosionVal, gridBagConstraints);

        lblAnzQuerbaenke.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzQuerbaenke.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzQuerbaenke, gridBagConstraints);

        lblAnzQuerbaenkeVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzQuerbaenkeVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzQuerbaenkeVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzQuerbaenkeVal, gridBagConstraints);

        lblStroemngsdiversitaet.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblStroemngsdiversitaet.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblStroemngsdiversitaet, gridBagConstraints);

        lblStroemngsdiversitaetVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStroemngsdiversitaetVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblStroemngsdiversitaetVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblStroemngsdiversitaetVal, gridBagConstraints);

        lblTiefenvarianz.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblTiefenvarianz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblTiefenvarianz, gridBagConstraints);

        lblTiefenvarianzVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTiefenvarianzVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblTiefenvarianzVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblTiefenvarianzVal, gridBagConstraints);

        lblTiefenerosion.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblTiefenerosion.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblTiefenerosion, gridBagConstraints);

        lblTiefenerosionVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTiefenerosionVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblTiefenerosionVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblTiefenerosionVal, gridBagConstraints);

        lblFliessgeschwindigkeit.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblFliessgeschwindigkeit.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblFliessgeschwindigkeit, gridBagConstraints);

        lblFliessgeschwindigkeitVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFliessgeschwindigkeitVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblFliessgeschwindigkeitVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblFliessgeschwindigkeitVal, gridBagConstraints);

        lblSubstratdiversitaet.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSubstratdiversitaet.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSubstratdiversitaet, gridBagConstraints);

        lblSubstratdiversitaetVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubstratdiversitaetVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblSubstratdiversitaetVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSubstratdiversitaetVal, gridBagConstraints);

        lblAnzBesSohlstrukturen.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzBesSohlstrukturen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzBesSohlstrukturen, gridBagConstraints);

        lblAnzBesSohlstrukturenVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzBesSohlstrukturenVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzBesSohlstrukturenVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblAnzBesSohlstrukturenVal, gridBagConstraints);

        lblSohlverbau.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSohlverbau.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSohlverbau, gridBagConstraints);

        lblSohlverbauVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSohlverbauVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblSohlverbauVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSohlverbauVal, gridBagConstraints);

        lblBelastungenSohle.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblBelastungenSohle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblBelastungenSohle, gridBagConstraints);

        lblBelastungenSohleVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBelastungenSohleVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBelastungenSohleVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblBelastungenSohleVal, gridBagConstraints);

        lblLaufentwicklung.setIcon(new VerticalTextIcon("Laufentwicklung", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblLaufentwicklung, gridBagConstraints);

        lblLaengsprofil.setIcon(new VerticalTextIcon("Längsprofil", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblLaengsprofil, gridBagConstraints);

        lblSohlenstruktur.setIcon(new VerticalTextIcon("Sohlenstruktur", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panSohle.add(lblSohlenstruktur, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panSohle, gridBagConstraints);

        panUfer.setOpaque(false);
        panUfer.setLayout(new java.awt.GridBagLayout());

        lblUfer.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblUfer.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUfer, gridBagConstraints);

        lblSohltiefe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSohltiefe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblSohltiefe, gridBagConstraints);

        lblSohltiefeVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSohltiefeVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblSohltiefeVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblSohltiefeVal, gridBagConstraints);

        lblBreitenerosion.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblBreitenerosion.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBreitenerosion, gridBagConstraints);

        lblBreitenerosionVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBreitenerosionVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBreitenerosionVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBreitenerosionVal, gridBagConstraints);

        lblBreitenvarianz.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblBreitenvarianz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBreitenvarianz, gridBagConstraints);

        lblBreitenvarianzVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBreitenvarianzVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBreitenvarianzVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBreitenvarianzVal, gridBagConstraints);

        lblProfiltyp.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblProfiltyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblProfiltyp, gridBagConstraints);

        lblProfiltypVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfiltypVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblProfiltypVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblProfiltypVal, gridBagConstraints);

        lblAnzBesUferstrukturenRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzBesUferstrukturenRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblAnzBesUferstrukturenRe, gridBagConstraints);

        lblAnzBesUferstrukturenReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzBesUferstrukturenReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzBesUferstrukturenReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblAnzBesUferstrukturenReVal, gridBagConstraints);

        lblAnzBesUferstrukturenLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblAnzBesUferstrukturenLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblAnzBesUferstrukturenLi, gridBagConstraints);

        lblAnzBesUferstrukturenLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnzBesUferstrukturenLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblAnzBesUferstrukturenLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblAnzBesUferstrukturenLiVal, gridBagConstraints);

        lblUferbewuchsRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblUferbewuchsRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferbewuchsRe, gridBagConstraints);

        lblUferbewuchsReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUferbewuchsReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblUferbewuchsReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferbewuchsReVal, gridBagConstraints);

        lblUferbewuchsLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblUferbewuchsLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferbewuchsLi, gridBagConstraints);

        lblUferbewuchsLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUferbewuchsLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblUferbewuchsLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferbewuchsLiVal, gridBagConstraints);

        lblUferverbauRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblUferverbauRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferverbauRe, gridBagConstraints);

        lblUferverbauReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUferverbauReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblUferverbauReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferverbauReVal, gridBagConstraints);

        lblUferverbauLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblUferverbauLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferverbauLi, gridBagConstraints);

        lblUferverbauLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUferverbauLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblUferverbauLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferverbauLiVal, gridBagConstraints);

        lblBesondereUferbelastungenRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblBesondereUferbelastungenRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBesondereUferbelastungenRe, gridBagConstraints);

        lblBesondereUferbelastungenReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBesondereUferbelastungenReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBesondereUferbelastungenReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBesondereUferbelastungenReVal, gridBagConstraints);

        lblBesondereUferbelastungenLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblBesondereUferbelastungenLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBesondereUferbelastungenLi, gridBagConstraints);

        lblBesondereUferbelastungenLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBesondereUferbelastungenLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBesondereUferbelastungenLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblBesondereUferbelastungenLiVal, gridBagConstraints);

        lblQuerprofil.setIcon(new VerticalTextIcon("Querprofil", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblQuerprofil, gridBagConstraints);

        lblUferstruktur.setIcon(new VerticalTextIcon("Uferstruktur", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panUfer.add(lblUferstruktur, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panUfer, gridBagConstraints);

        panLand.setOpaque(false);
        panLand.setLayout(new java.awt.GridBagLayout());

        lblLand.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblLand.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblLand, gridBagConstraints);

        lblGewaesserrandstreifenRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblGewaesserrandstreifenRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblGewaesserrandstreifenRe, gridBagConstraints);

        lblGewaesserrandstreifenReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGewaesserrandstreifenReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblGewaesserrandstreifenReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblGewaesserrandstreifenReVal, gridBagConstraints);

        lblGewaesserrandstreifenLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblGewaesserrandstreifenLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblGewaesserrandstreifenLi, gridBagConstraints);

        lblGewaesserrandstreifenLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGewaesserrandstreifenLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblGewaesserrandstreifenLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblGewaesserrandstreifenLiVal, gridBagConstraints);

        lblFlaechennutzungRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblFlaechennutzungRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblFlaechennutzungRe, gridBagConstraints);

        lblFlaechennutzungReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFlaechennutzungReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblFlaechennutzungReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblFlaechennutzungReVal, gridBagConstraints);

        lblFlaechennutzungLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblFlaechennutzungLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblFlaechennutzungLi, gridBagConstraints);

        lblFlaechennutzungLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFlaechennutzungLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblFlaechennutzungLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblFlaechennutzungLiVal, gridBagConstraints);

        lblSchaedlicheUmfeldstrukturenRe.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSchaedlicheUmfeldstrukturenRe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblSchaedlicheUmfeldstrukturenRe, gridBagConstraints);

        lblSchaedlicheUmfeldstrukturenReVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSchaedlicheUmfeldstrukturenReVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblSchaedlicheUmfeldstrukturenReVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblSchaedlicheUmfeldstrukturenReVal, gridBagConstraints);

        lblSchaedlicheUmfeldstrukturenLi.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblSchaedlicheUmfeldstrukturenLi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblSchaedlicheUmfeldstrukturenLi, gridBagConstraints);

        lblSchaedlicheUmfeldstrukturenLiVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSchaedlicheUmfeldstrukturenLiVal.setMinimumSize(new java.awt.Dimension(30, 20));
        lblSchaedlicheUmfeldstrukturenLiVal.setPreferredSize(new java.awt.Dimension(30, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblSchaedlicheUmfeldstrukturenLiVal, gridBagConstraints);

        lblGewaesserumfeld.setIcon(new VerticalTextIcon("Gewässerumfeld", false));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        panLand.add(lblGewaesserumfeld, gridBagConstraints);

        jbVorschlag.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.jbVorschlag.text")); // NOI18N
        jbVorschlag.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbVorschlagActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        panLand.add(jbVorschlag, gridBagConstraints);

        lblMassn.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblMassn.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 0);
        panLand.add(lblMassn, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(liMassn);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 10, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jbRem.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
        jbRem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbRemActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel1.add(jbRem, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panLand.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(panLand, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblIndFische.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblIndFische.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 30, 0, 0);
        jPanel2.add(lblIndFische, gridBagConstraints);

        jTextField1.setMinimumSize(new java.awt.Dimension(50, 25));
        jTextField1.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 15);
        jPanel2.add(jTextField1, gridBagConstraints);

        lblIndMzb.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblIndMzb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 0);
        jPanel2.add(lblIndMzb, gridBagConstraints);

        jTextField2.setMinimumSize(new java.awt.Dimension(50, 25));
        jTextField2.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 15);
        jPanel2.add(jTextField2, gridBagConstraints);

        lblIndMkPhy.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblIndMkPhy.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 0);
        jPanel2.add(lblIndMkPhy, gridBagConstraints);

        jTextField3.setMinimumSize(new java.awt.Dimension(50, 25));
        jTextField3.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 15);
        jPanel2.add(jTextField3, gridBagConstraints);

        lblIndOMKosten.setText(org.openide.util.NbBundle.getMessage(
                SimSimulationsabschnittEditor.class,
                "SimSimulationsabschnittEditor.lblIndOMKosten.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 10);
        jPanel2.add(lblIndOMKosten, gridBagConstraints);

        jTextField4.setMinimumSize(new java.awt.Dimension(50, 25));
        jTextField4.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 15);
        jPanel2.add(jTextField4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbVorschlagActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVorschlagActionPerformed
        final SwingWorker<Collection<Node>, Void> worker = new SwingWorker<Collection<Node>, Void>() {

                @Override
                protected Collection<Node> doInBackground() throws Exception {
                    final AbstractCidsServerSearch search = new MassnahmenvorschlagSearch(
                            cidsBean.getMetaObject(),
                            SessionManager.getSession().getUser());
                    return SessionManager.getProxy().customServerSearch(SessionManager.getSession().getUser(), search);
                }

                @Override
                protected void done() {
                    try {
                        final Collection<Node> res = get();

                        MethodManager.getManager().showSearchResults(res.toArray(new Node[res.size()]), false);
                        MethodManager.getManager().showSearchResults();
                    } catch (Exception e) {
                        LOG.error("Error during server search", e);
                    }
                }
            };
        CismetConcurrency.getInstance("fgsk-sim").getDefaultExecutor().submit(worker);
    }//GEN-LAST:event_jbVorschlagActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbRemActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemActionPerformed
        final Object[] selectedElements = liMassn.getSelectedValues();

        for (final Object el : selectedElements) {
            model.removeElement(el);
            model.addElement((CidsBean)el);
        }
        try {
            calc(cidsBean, massnahmen, true);
        } catch (Exception e) {
            LOG.error("Error during calculation.", e);
        }
    }//GEN-LAST:event_jbRemActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cl  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color getColor(final Integer cl) {
        switch (cl) {
            case 1: {
                return new Color(10, 12, 149);
            }
            case 2: {
                return new Color(11, 142, 12);
            }
            case 3: {
                return new Color(253, 252, 4);
            }
            case 4: {
                return new Color(214, 162, 79);
            }
            case 5: {
                return new Color(165, 40, 34);
            }
            default: {
                return new Color(192, 192, 192);
            }
        }
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
//        bindingGroup.unbind();

        if (cidsBean != null) {
//            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
//                bindingGroup,
//                cidsBean);
//            bindingGroup.bind();
            try {
                calc(cidsBean, massnahmen, true);
            } catch (Exception e) {
                LOG.error("Error while calculating the simulation results", e);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmen  DOCUMENT ME!
     */
    public void setMassnahmen(final List<CidsBean> massnahmen) {
        this.massnahmen = massnahmen;
        model.clear();
        for (final CidsBean massn : massnahmen) {
            model.addElement(massn);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  simulation  DOCUMENT ME!
     */
    public void setSimulation(final CidsBean simulation) {
        this.simulation = simulation;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean        DOCUMENT ME!
     * @param   massnahmen  DOCUMENT ME!
     * @param   fillFields  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public Double calc(final CidsBean bean, final List<CidsBean> massnahmen, final boolean fillFields)
            throws Exception {
        final CidsBean wbTypeBean = (CidsBean)bean.getProperty(Calc.PROP_WB_TYPE);

        if (wbTypeBean == null) {
            if (fillFields) {
                clearAll();
            }
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + bean); // NOI18N
        }
        final int wbTypeId = wbTypeBean.getMetaObject().getId();
        final double stationLength = Calc.getStationLength(bean);
        final CidsBean courseLoopBean = (CidsBean)bean.getProperty(Calc.PROP_COURSE_LOOP);
        final CidsBean loopErosionBean = (CidsBean)bean.getProperty(Calc.PROP_LOOP_EROSION);

        Integer ratingCourseLoop = cache.getCourseLoopRating(courseLoopBean.getMetaObject().getId(), wbTypeId);
        Integer ratingLoopErosion = cache.getLoopErosionRating(loopErosionBean.getMetaObject().getId(), wbTypeId);

        final Double absLongBenchSum = getLongBenchSum(bean, wbTypeId, stationLength);
        final Double absCourseStructureSum = getCourseStructureSum(bean, wbTypeId, stationLength);
        final Double absCrossBenchCount = getCrossBenchCount(bean, wbTypeId, stationLength);
        final CidsBean flowDiversityBean = (CidsBean)bean.getProperty(Calc.PROP_FLOW_DIVERSITY);
        final CidsBean depthVarianceBean = (CidsBean)bean.getProperty(Calc.PROP_DEPTH_VARIANCE);
        final CidsBean flowVelocityBean = (CidsBean)bean.getProperty(Calc.PROP_FLOW_VELOCITY);

        Integer ratingLongBench = cache.getLongBenchRating(absLongBenchSum, wbTypeId);
        Integer ratingCourseStructure = cache.getCourseStructureRating(absCourseStructureSum, wbTypeId);
        Integer ratingCrossBench = cache.getCrossBenchRating(absCrossBenchCount, wbTypeId);
        Integer ratingFlowDiversity = cache.getFlowDiversityRating(flowDiversityBean.getMetaObject().getId(),
                wbTypeId);
        Integer ratingFlowVelocity = cache.getFlowVelocityRating(flowVelocityBean.getMetaObject().getId(),
                wbTypeId);
        Integer ratingDepthVariance = cache.getDepthVarianceRating(depthVarianceBean.getMetaObject().getId(),
                wbTypeId);
        Integer substrateDiversityRating = getSubstrateDiversity(bean, wbTypeId, stationLength);
        Integer bedStructureRating = getBedStructureRating(bean, wbTypeId, stationLength);
        final CidsBean bedFitmentBean = (CidsBean)bean.getProperty(Calc.PROP_BED_FITMENT);
        final CidsBean zBedFitmentBean = (CidsBean)bean.getProperty(Calc.PROP_Z_BED_FITMENT);
        Integer ratingBedFitment = cache.getBedFitmentRating(
                bedFitmentBean.getMetaObject().getId(),
                zBedFitmentBean.getMetaObject().getId(),
                wbTypeId);
        final double upperProfileBreadth = (Double)bean.getProperty(Calc.PROP_UPPER_PROFILE_BREADTH);
        final double incisionDepth = (Double)bean.getProperty(Calc.PROP_INCISION_DEPTH);
        final double waterDepth = (Double)bean.getProperty(Calc.PROP_WATER_DEPTH);
        final double profileDepth = incisionDepth + waterDepth;

        final double breadthDepthRelation = Calc.round(Calc.round(upperProfileBreadth / profileDepth, scale));

        final CidsBean profileTypeBean = (CidsBean)bean.getProperty(Calc.PROP_PROFILE_TYPE);
        final CidsBean breadthVarianceBean = (CidsBean)bean.getProperty(Calc.PROP_BREADTH_VARIANCE);
        final CidsBean breadthErosionBean = (CidsBean)bean.getProperty(Calc.PROP_BREADTH_EROSION);

        Integer ratingDepthBreadth = cache.getProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
        if (ratingDepthBreadth == null) {
            ratingDepthBreadth = cache.getBiggestProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
            if (ratingDepthBreadth == null) {
                ratingDepthBreadth = cache.getLowestProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
            }
        }

        Integer ratingBreadthVariance = cache.getBreadthVarianceRating(
                breadthVarianceBean.getMetaObject().getId(),
                wbTypeId);
        Integer ratingBreadthErosion = cache.getBreadthErosionRating(breadthErosionBean.getMetaObject().getId(),
                wbTypeId);
        Integer ratingProfileType = cache.getProfileTypeRating(profileTypeBean.getMetaObject().getId(),
                wbTypeId);
        Integer ratingBankStructureLeft = Calc.getInstance()
                    .getBankStructureRating(wbTypeId, stationLength, bean, true);
        Integer ratingBankStructureRight = Calc.getInstance()
                    .getBankStructureRating(wbTypeId, stationLength, bean, false);
        Integer ratingBankVegetationRight = Calc.getInstance()
                    .getBankVegetationRating(wbTypeId, stationLength, bean, false);
        Integer ratingBankVegetationLeft = Calc.getInstance()
                    .getBankVegetationRating(wbTypeId, stationLength, bean, true);
        Integer ratingBankFitmentRight = Calc.getInstance().getBankFitmentRating(wbTypeId, stationLength, bean, false);
        Integer ratingBankFitmentLeft = Calc.getInstance().getBankFitmentRating(wbTypeId, stationLength, bean, true);
        final Double ratingBankContaminationRight = Calc.getInstance()
                    .getBankContaminationRating(wbTypeId, stationLength, bean, false);
        final Double ratingBankContaminationLeft = Calc.getInstance()
                    .getBankContaminationRating(wbTypeId, stationLength, bean, true);
        Integer ratingWBTrimmingRight = Calc.getInstance().getWBTrimmingRating(wbTypeId, bean, false);
        Integer ratingWBTrimmingLeft = Calc.getInstance().getWBTrimmingRating(wbTypeId, bean, true);
        Integer ratingLandUseRight = Calc.getInstance().getLandUseRating(wbTypeId, bean, false);
        Integer ratingLandUseLeft = Calc.getInstance().getLandUseRating(wbTypeId, bean, true);
        Double ratingBadEnvRight = Calc.getInstance().getBadEnvRating(wbTypeId, bean, false);
        Double ratingBadEnvLeft = Calc.getInstance().getBadEnvRating(wbTypeId, bean, true);
        final Double ratingBedContamination = Calc.getInstance().calcBedContamination(bean, wbTypeId);

        ratingCourseLoop = addProperty(ratingCourseLoop, "laufkruemmung", massnahmen, wbTypeId);
        ratingLoopErosion = addProperty(ratingLoopErosion, "kruemmungserosion", massnahmen, wbTypeId);
        ratingLongBench = addProperty(ratingLongBench, "anzahl_laengsbaenken_mvs", massnahmen, wbTypeId);
        ratingCourseStructure = addProperty(ratingCourseStructure, "anzahl_besonderer_laufstrukturen", massnahmen, wbTypeId);
        ratingCrossBench = addProperty(ratingCrossBench, "anzahl_querbaenke_mvs", massnahmen, wbTypeId);
        ratingFlowDiversity = addProperty(ratingFlowDiversity, "stroemungsdiversitaet", massnahmen, wbTypeId);
        ratingFlowVelocity = addProperty(ratingFlowVelocity, "fliessgeschwindigkeit", massnahmen, wbTypeId);
        ratingDepthVariance = addProperty(ratingDepthVariance, "tiefenvarianz", massnahmen, wbTypeId);
        substrateDiversityRating = addProperty(substrateDiversityRating, "substratdiversitaet", massnahmen, wbTypeId);
        bedStructureRating = addProperty(bedStructureRating, "anzahl_besonderer_sohlstrukturen", massnahmen, wbTypeId);
        ratingBedFitment = addProperty(ratingBedFitment, "sohlverbau", massnahmen, wbTypeId);
        ratingDepthBreadth = addProperty(ratingDepthBreadth, "sohltiefe_obere_profilbreite", massnahmen, wbTypeId);
        ratingBreadthErosion = addProperty(ratingBreadthErosion, "breitenerosion", massnahmen, wbTypeId);
        ratingBreadthVariance = addProperty(ratingBreadthVariance, "breitenvarianz", massnahmen, wbTypeId);
        ratingProfileType = addProperty(ratingProfileType, "profiltyp", massnahmen, wbTypeId);
        ratingBankStructureRight = addProperty(
                ratingBankStructureRight,
                "anzahl_besonderer_uferstrukturen",
                massnahmen, wbTypeId);
        ratingBankStructureLeft = addProperty(ratingBankStructureLeft, "anzahl_besonderer_uferstrukturen", massnahmen, wbTypeId);
        ratingBankVegetationRight = addProperty(ratingBankVegetationRight, "uferbewuchs", massnahmen, wbTypeId);
        ratingBankVegetationLeft = addProperty(ratingBankVegetationLeft, "uferbewuchs", massnahmen, wbTypeId);
        ratingBankFitmentRight = addProperty(ratingBankFitmentRight, "uferverbau", massnahmen, wbTypeId);
        ratingBankFitmentLeft = addProperty(ratingBankFitmentLeft, "uferverbau", massnahmen, wbTypeId);
        ratingWBTrimmingRight = addProperty(ratingWBTrimmingRight, "gewaesserrandstreifen", massnahmen, wbTypeId);
        ratingWBTrimmingLeft = addProperty(ratingWBTrimmingLeft, "gewaesserrandstreifen", massnahmen, wbTypeId);
        ratingLandUseRight = addProperty(ratingLandUseRight, "flaechennutzung", massnahmen, wbTypeId);
        ratingLandUseLeft = addProperty(ratingLandUseLeft, "flaechennutzung", massnahmen, wbTypeId);
        ratingBadEnvRight = Calc.correctBadEnvRating(addProperty(
                    ratingBadEnvRight,
                    "sonstige_umfeldstrukturen",
                    massnahmen, wbTypeId),
                wbTypeId);
        ratingBadEnvLeft = Calc.correctBadEnvRating(addProperty(
                    ratingBadEnvLeft,
                    "sonstige_umfeldstrukturen",
                    massnahmen, wbTypeId),
                wbTypeId);

        // start overall rating calculation
        final Calc.RatingStruct wbLongProfileRating = new Calc.RatingStruct();
        final Calc.RatingStruct courseEvoRating = new Calc.RatingStruct();
        final Calc.RatingStruct wbCrossProfileRating = new Calc.RatingStruct();
        final Calc.RatingStruct finalBedStructureRating = new Calc.RatingStruct();
        final Calc.RatingStruct bankStructureRatingLeft = new Calc.RatingStruct();
        final Calc.RatingStruct bankStructureRatingRight = new Calc.RatingStruct();
        final Calc.RatingStruct wbEnvSumRatingRight = new Calc.RatingStruct();
        final Calc.RatingStruct wbEnvSumRatingLeft = new Calc.RatingStruct();

        Calc.overallRating(
            wbLongProfileRating,
            true,
            ratingCrossBench,
            ratingFlowDiversity,
            ratingFlowVelocity,
            ratingDepthVariance);
        Calc.overallRating(
            courseEvoRating,
            true,
            ratingCourseLoop,
            ratingLoopErosion,
            ratingLongBench,
            ratingCourseStructure);
        Calc.overallRating(
            wbCrossProfileRating,
            true,
            ratingDepthBreadth,
            ratingProfileType,
            ratingBreadthErosion,
            ratingBreadthVariance);
        Calc.overallRating(
            finalBedStructureRating,
            true,
            substrateDiversityRating,
            bedStructureRating,
            ratingBedFitment);

        Calc.overallRating(finalBedStructureRating, false, ratingBedContamination);

        Calc.overallRating(
            bankStructureRatingLeft,
            true,
            ratingBankStructureLeft,
            ratingBankVegetationLeft,
            ratingBankFitmentLeft);
        Calc.overallRating(bankStructureRatingLeft, false, ratingBankContaminationLeft);
        Calc.overallRating(
            bankStructureRatingRight,
            true,
            ratingBankStructureRight,
            ratingBankVegetationRight,
            ratingBankFitmentRight);
        Calc.overallRating(bankStructureRatingRight, false, ratingBankContaminationRight);
        Calc.overallRating(wbEnvSumRatingRight, true, ratingWBTrimmingRight, ratingLandUseRight);
        Calc.overallRating(wbEnvSumRatingRight, false, ratingBadEnvRight);
        Calc.overallRating(wbEnvSumRatingLeft, true, ratingWBTrimmingLeft, ratingLandUseLeft);
        Calc.overallRating(wbEnvSumRatingLeft, false, ratingBadEnvLeft);
        double finalRating = finalBedStructureRating.rating;

        if (finalRating < 1) {
            finalRating = 1;
        }

        final CidsBean dummyBean = CidsBeanSupport.createNewCidsBeanFromTableName("fgsk_kartierabschnitt");
        dummyBean.setProperty(Calc.PROP_COURSE_EVO_SUM_RATING, (Double)courseEvoRating.rating);
        dummyBean.setProperty(Calc.PROP_COURSE_EVO_SUM_CRIT, (Integer)courseEvoRating.criteriaCount);
        dummyBean.setProperty(Calc.PROP_LONG_PROFILE_SUM_RATING, (Double)wbLongProfileRating.rating);
        dummyBean.setProperty(Calc.PROP_LONG_PROFILE_SUM_CRIT, (Integer)wbLongProfileRating.criteriaCount);
        dummyBean.setProperty(Calc.PROP_BED_STRUCTURE_SUM_RATING, (Double)finalRating);
        dummyBean.setProperty(Calc.PROP_BED_STRUCTURE_SUM_CRIT, (Integer)finalBedStructureRating.criteriaCount);

        double ratingBankStructure = bankStructureRatingLeft.rating + bankStructureRatingRight.rating;
        // rating correction according to Kuechler, not present in original implementation
        if (ratingBankStructure < 1) {
            ratingBankStructure = 1;
        }
        double wbEnvSumRating = wbEnvSumRatingRight.rating + wbEnvSumRatingLeft.rating;
        // rating correction according to Kuechler, not present in original implementation
        if (wbEnvSumRating < 1) {
            wbEnvSumRating = 1;
        }

        dummyBean.setProperty(Calc.PROP_CROSS_PROFILE_SUM_RATING, (Double)wbCrossProfileRating.rating);
        dummyBean.setProperty(Calc.PROP_CROSS_PROFILE_SUM_CRIT, (Integer)wbCrossProfileRating.criteriaCount);
        dummyBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING, (Double)ratingBankStructure);
        dummyBean.setProperty(
            Calc.PROP_BANK_STRUCTURE_SUM_CRIT,
            (Integer)bankStructureRatingLeft.criteriaCount
                    + bankStructureRatingRight.criteriaCount);
        dummyBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING_LE, (Double)bankStructureRatingLeft.rating);
        dummyBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT_LE, (Integer)bankStructureRatingLeft.criteriaCount);
        dummyBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING_RI, (Double)bankStructureRatingRight.rating);
        dummyBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT_RI, (Integer)bankStructureRatingRight.criteriaCount);

        dummyBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING, wbEnvSumRating);
        dummyBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING_LE, wbEnvSumRatingLeft.rating);
        dummyBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING_RI, wbEnvSumRatingRight.rating);
        dummyBean.setProperty(
            Calc.PROP_WB_ENV_SUM_CRIT,
            wbEnvSumRatingRight.criteriaCount
                    + wbEnvSumRatingLeft.criteriaCount);
        dummyBean.setProperty(Calc.PROP_WB_ENV_SUM_CRIT_LE, wbEnvSumRatingLeft.criteriaCount);
        dummyBean.setProperty(Calc.PROP_WB_ENV_SUM_CRIT_RI, wbEnvSumRatingRight.criteriaCount);

        Calc.getInstance().calcOverallRating(dummyBean);
        // end overall rating calculation

        if (fillFields) {
            fillLabel(lblLaufkrVal, ratingCourseLoop);
            fillLabel(lblKruemmungserosionVal, ratingLoopErosion);
            fillLabel(lblAnzLaVal, ratingLongBench);
            fillLabel(lblAnzLaufVal, ratingCourseStructure);
            fillLabel(lblAnzQuerbaenkeVal, ratingCrossBench);
            fillLabel(lblStroemngsdiversitaetVal, ratingFlowDiversity);
            fillLabel(lblFliessgeschwindigkeitVal, ratingFlowVelocity);
            fillLabel(lblTiefenvarianzVal, ratingDepthVariance);
            fillLabel(lblSubstratdiversitaetVal, substrateDiversityRating);
            fillLabel(lblAnzBesSohlstrukturenVal, bedStructureRating);
            fillLabel(lblSohlverbauVal, ratingBedFitment);
            fillLabel(lblSohltiefeVal, ratingDepthBreadth);
            fillLabel(lblBreitenerosionVal, ratingBreadthErosion);
            fillLabel(lblBreitenvarianzVal, ratingBreadthVariance);
            fillLabel(lblProfiltypVal, ratingProfileType);
            fillLabel(lblAnzBesUferstrukturenReVal, ratingBankStructureRight);
            fillLabel(lblAnzBesUferstrukturenLiVal, ratingBankStructureLeft);
            fillLabel(lblUferbewuchsReVal, ratingBankVegetationRight);
            fillLabel(lblUferbewuchsLiVal, ratingBankVegetationLeft);
            fillLabel(lblUferverbauReVal, ratingBankFitmentRight);
            fillLabel(lblUferverbauLiVal, ratingBankFitmentLeft);
            fillLabel(lblBesondereUferbelastungenReVal, ratingBankContaminationRight);
            fillLabel(lblBesondereUferbelastungenLiVal, ratingBankContaminationLeft);
            fillLabel(lblGewaesserrandstreifenReVal, ratingWBTrimmingRight);
            fillLabel(lblGewaesserrandstreifenLiVal, ratingWBTrimmingLeft);
            fillLabel(lblFlaechennutzungReVal, ratingLandUseRight);
            fillLabel(lblFlaechennutzungLiVal, ratingLandUseLeft);
            fillLabel(lblSchaedlicheUmfeldstrukturenReVal, ratingBadEnvRight);
            fillLabel(lblSchaedlicheUmfeldstrukturenLiVal, ratingBadEnvLeft);
        }
        return (Double)dummyBean.getProperty(Calc.PROP_WB_OVERALL_RATING);
    }

    /**
     * DOCUMENT ME!
     */
    private void clearAll() {
        fillLabel(lblLaufkrVal, null);
        fillLabel(lblKruemmungserosionVal, null);
        fillLabel(lblAnzLaVal, null);
        fillLabel(lblAnzLaufVal, null);
        fillLabel(lblAnzQuerbaenkeVal, null);
        fillLabel(lblStroemngsdiversitaetVal, null);
        fillLabel(lblFliessgeschwindigkeitVal, null);
        fillLabel(lblTiefenvarianzVal, null);
        fillLabel(lblSubstratdiversitaetVal, null);
        fillLabel(lblAnzBesSohlstrukturenVal, null);
        fillLabel(lblSohlverbauVal, null);
        fillLabel(lblSohltiefeVal, null);
        fillLabel(lblBreitenerosionVal, null);
        fillLabel(lblBreitenvarianzVal, null);
        fillLabel(lblProfiltypVal, null);
        fillLabel(lblAnzBesUferstrukturenReVal, null);
        fillLabel(lblAnzBesUferstrukturenLiVal, null);
        fillLabel(lblUferbewuchsReVal, null);
        fillLabel(lblUferbewuchsLiVal, null);
        fillLabel(lblUferverbauReVal, null);
        fillLabel(lblUferverbauLiVal, null);
        fillLabel(lblBesondereUferbelastungenReVal, null);
        fillLabel(lblBesondereUferbelastungenLiVal, null);
        fillLabel(lblGewaesserrandstreifenReVal, null);
        fillLabel(lblGewaesserrandstreifenLiVal, null);
        fillLabel(lblFlaechennutzungReVal, null);
        fillLabel(lblFlaechennutzungLiVal, null);
        fillLabel(lblSchaedlicheUmfeldstrukturenReVal, null);
        fillLabel(lblSchaedlicheUmfeldstrukturenLiVal, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   massnahmen    DOCUMENT ME!
     * @param   propertyName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getMassnBonus(final List<CidsBean> massnahmen, final String propertyName, final int wbTypeId) {
        int bonus = 0;

        if (massnahmen != null) {
            for (final CidsBean tmpMassn : massnahmen) {
                bonus += (Integer)tmpMassn.getProperty(propertyName);
            }
        }
        return bonus;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lab     DOCUMENT ME!
     * @param  points  DOCUMENT ME!
     */
    private void fillLabel(final JLabel lab, final Number points) {
        if (points != null) {
            double pointsAsDouble = points.doubleValue();

            if (pointsAsDouble > 5) {
                pointsAsDouble = 5;
            }
            final int cl = convertPointsToClass(pointsAsDouble);

            lab.setText(String.valueOf(cl));
            lab.setBackground(getColor(cl));
            lab.setOpaque(true);
        } else {
            lab.setText("");
            lab.setOpaque(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   p  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static int convertPointsToClass(final double p) {
        int gueteklasse = 0;

        if (p <= 1.5) {
            gueteklasse = 5;
        } else if (p <= 2.5) {
            gueteklasse = 4;
        } else if (p <= 3.5) {
            gueteklasse = 3;
        } else if (p <= 4.5) {
            gueteklasse = 2;
        } else if (p > 4.5) {
            gueteklasse = 1;
        }

        return gueteklasse;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value         DOCUMENT ME!
     * @param   propertyName  DOCUMENT ME!
     * @param   massnahmen    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Double addProperty(final Double value, final String propertyName, final List<CidsBean> massnahmen, final int wbTypeId) {
        if (value == null) {
            return null;
        }

        return value + getMassnBonus(massnahmen, "laufkruemmung", wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value         DOCUMENT ME!
     * @param   propertyName  DOCUMENT ME!
     * @param   massnahmen    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Integer addProperty(final Integer value, final String propertyName, final List<CidsBean> massnahmen, final int wbTypeId) {
        if (value == null) {
            return null;
        }

        return value + getMassnBonus(massnahmen, "laufkruemmung", wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean       DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Integer getBedStructureRating(final CidsBean cidsBean, final int wbTypeId, final double stationLength) {
        final Double bedStructureSectionLength = cache.getBedStructureSectionLength(wbTypeId);

        final Integer ratingBedStructure;
        if ((bedStructureSectionLength != null) && (bedStructureSectionLength > 0)) {
            double bedStructureCount = 0;
            for (final CalcCache.BedStructureType type : CalcCache.BedStructureType.values()) {
                final Double count = (Double)cidsBean.getProperty(Calc.fieldFromCode(
                            "PROP_BED_STRUCTURE_",
                            type.getCode())); // NOI18N

                if (count != null) {
                    bedStructureCount += count;
                }
            }

            final double absBedStructureCount = Calc.round(Calc.round(
                        bedStructureCount
                                / (stationLength / bedStructureSectionLength),
                        scale));

            ratingBedStructure = cache.getBedStructureRating(absBedStructureCount, wbTypeId);
        } else {
            ratingBedStructure = null;
        }

        return ratingBedStructure;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean       DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Integer getSubstrateDiversity(final CidsBean cidsBean, final int wbTypeId, final double stationLength) {
        int naturalSubstratesCount = 0;
        for (final CalcCache.BedSubtrateType type : CalcCache.BedSubtrateType.getNaturalBedSubstrateTypes()) {
            final Double percentage = (Double)cidsBean.getProperty(
                    Calc.fieldFromCode(Calc.PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if ((percentage != null) && (percentage > 0)) {
                naturalSubstratesCount++;
            }
        }

        double artificalSubstratePercentage = 0;
        for (final CalcCache.BedSubtrateType type : CalcCache.BedSubtrateType.getArtificialSubstrateTypes()) {
            final Double percentage = (Double)cidsBean.getProperty(
                    Calc.fieldFromCode(Calc.PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if (percentage != null) {
                artificalSubstratePercentage += percentage;
            }
        }

        double hardSubstratePercentage = 0;
        for (final CalcCache.BedSubtrateType type : CalcCache.BedSubtrateType.getHardSubstrateTypes()) {
            final Double percentage = (Double)cidsBean.getProperty(
                    Calc.fieldFromCode(Calc.PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if (percentage != null) {
                hardSubstratePercentage += percentage;
            }
        }

        final Collection<CidsBean> wbSubTypeList = (Collection<CidsBean>)cidsBean.getProperty(Calc.PROP_WB_SUB_TYPE);
        String wbSubTypeId = null;
        if ((wbSubTypeList != null) && !wbSubTypeList.isEmpty()) {
            final Iterator<CidsBean> it = wbSubTypeList.iterator();
            while (it.hasNext()) {
                final String val = (String)it.next().getProperty(Calc.PROP_VALUE);
                if ((val != null) && ("S".equals(val) || "M".equals(val))) {              // NOI18N
                    if (wbSubTypeId == null) {
                        wbSubTypeId = val;
                    } else {
                        throw new IllegalStateException(
                            "found more than one relevant subtype for kartierabschnitt: " // NOI18N
                                    + cidsBean);
                    }
                }
            }
        }

        final Integer ratingNaturalSubstrates = cache.getNaturalSubstrateRating(Double.valueOf(
                    (double)naturalSubstratesCount),
                wbSubTypeId,
                wbTypeId);
        final Integer ratingArtificialSubstrates = cache.getArtificialSubstrateRating(
                artificalSubstratePercentage,
                wbSubTypeId,
                wbTypeId);
        final Integer ratingHardSubstrates = cache.getHardSubstrateRating(
                hardSubstratePercentage,
                wbSubTypeId,
                wbTypeId);

        boolean ignoreSubstrates = true;
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_BLO) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_BLO) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_KIE) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_KIE) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_KUE) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_KUE) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TON) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TON) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TOR) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TOR) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TOT) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_TOT) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_SAN) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_SAN) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_SCH) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_SCH) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_STE) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_STE) == 0.0));
        ignoreSubstrates &= ((cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_WUR) == null)
                        || ((Double)cidsBean.getProperty(Calc.PROP_BED_SUBSTRATE_WUR) == 0.0));

        final Integer ratingSubstrates;

        if (!ignoreSubstrates
                    && Equals.nonNull(ratingNaturalSubstrates, ratingArtificialSubstrates, ratingHardSubstrates)) {
            ratingSubstrates = Calc.round(Calc.round(
                        (ratingNaturalSubstrates + ratingArtificialSubstrates + ratingHardSubstrates)
                                / 3.0,
                        scale));
        } else {
            ratingSubstrates = null;
        }

        return ratingSubstrates;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean       DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Double getCourseStructureSum(final CidsBean cidsBean, final int wbTypeId, final double stationLength) {
        // Anz.besonderer Laufstrukturen
        double courseStructureSum = 0;
        for (final CalcCache.CourseStructureType type : CalcCache.CourseStructureType.values()) {
            final Double count = (Double)cidsBean.getProperty(Calc.fieldFromCode(
                        "PROP_COURSE_STRUCTURE_",
                        type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                courseStructureSum += count;
            }
        }

        final Double courseStructureSectionLength = cache.getCourseStructureSectionLength(wbTypeId);

        final Double absCourseStructureSum;
        if (courseStructureSum == 0.5) {
            absCourseStructureSum = courseStructureSum;
        } else if (courseStructureSectionLength == null) {
            absCourseStructureSum = null;
        } else {
            absCourseStructureSum = (double)Calc.round(Calc.round(
                        courseStructureSum
                                / (stationLength / courseStructureSectionLength),
                        scale));
        }

        return absCourseStructureSum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean       DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Double getLongBenchSum(final CidsBean cidsBean, final int wbTypeId, final double stationLength) {
        // Laengsbaenke
        double longBenchSum = 0;
        for (final CalcCache.LongBenchType type : CalcCache.LongBenchType.values()) {
            final Double count = (Double)cidsBean.getProperty(Calc.fieldFromCode("PROP_LONG_BENCH_", type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                longBenchSum += count;
            }
        }

        final Double longBenchSectionLength = cache.getLongBenchSectionLength(wbTypeId);

        final Double absLongBenchSum;

        if (longBenchSum == 0.5) {
            absLongBenchSum = longBenchSum;
        } else if (longBenchSectionLength == null) {
            absLongBenchSum = null;
        } else {
            absLongBenchSum = (double)Calc.round(Calc.round(
                        longBenchSum
                                / (stationLength / longBenchSectionLength),
                        scale));
        }

        return absLongBenchSum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean       DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Double getCrossBenchCount(final CidsBean cidsBean, final int wbTypeId, final double stationLength) {
        final double crossBenchCount = (Double)cidsBean.getProperty(Calc.PROP_CROSS_BENCH_COUNT); // NOI18N
        final Double sectionLength = cache.getCrossBenchSectionLength(wbTypeId);

        final Double absCrossBenchCount;
        if (crossBenchCount == 0.5) {
            absCrossBenchCount = crossBenchCount;
        } else if (sectionLength == null) {
            absCrossBenchCount = null;
        } else {
            // we cannot use Math.round here since it behaves slightly different. Math.round will round .5 up where as
            // the fgsk round will round this down.
            absCrossBenchCount = (double)Calc.round(Calc.round(
                        crossBenchCount
                                / (stationLength / sectionLength),
                        scale));
        }

        return absCrossBenchCount;
    }

    @Override
    public void dispose() {
    }

    @Override
    public String getTitle() {
        if (cidsBean != null) {
            return
                (((cidsBean.toString() == null) || cidsBean.toString().equals("") || cidsBean.toString().equals("null"))
                    ? "unbenannt" : cidsBean.toString());
        } else {
            return "unbenannt";
        }
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.initSessionManagerFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "kif");
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "kif",
            "simulation",
            2,
            1280,
            1024);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CidsBeanDropList extends JList implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly) {
                return;
            }
            if (beans.size() > 0) {
                final CidsBean tmpBean = beans.get(0);

                if (tmpBean.getClass().getName().equals("de.cismet.cids.dynamics.Sim_massnahme")) {
                    try {
                        final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                                "sim_massnahmen_anwendungen");
                        newBean.setProperty("fgsk_ka", cidsBean);
                        newBean.setProperty("massnahme", tmpBean);
                        newBean.setProperty("simulation", simulation);
                        massnahmen.add(tmpBean);
                        model.addElement(newBean);
                        calc(cidsBean, massnahmen, true);

                        simulation.getBeanCollectionProperty("angewendete_simulationsmassnahmen").add(newBean);
                    } catch (Exception e) {
                        LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
                    }
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomListCellRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList<?> list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final CidsBean val = (CidsBean)value;
            final String title = String.valueOf(val.getProperty("massnahme"));
            return super.getListCellRendererComponent(list, title, index, isSelected, cellHasFocus);
        }
    }
}
