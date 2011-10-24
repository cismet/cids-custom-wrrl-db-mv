/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.ValidationException;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(FgskKartierabschnittEditor.class);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    private final transient ChangeListener calcL;
    // will only be changed in EDT
    private transient int selectedTabIndex;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittErgebnisse
        fgskKartierabschnittErgebnisse1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittGewaesserumfeld
        fgskKartierabschnittGewaesserumfeld1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittKartierabschnitt
        fgskKartierabschnittKartierabschnitt1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaengsprofil
        fgskKartierabschnittLaengsprofil1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaufentwicklung
        fgskKartierabschnittLaufentwicklung1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittQuerprofil
        fgskKartierabschnittQuerprofil1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittSohlenverbau
        fgskKartierabschnittSohlenverbau1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittUferstruktur
        fgskKartierabschnittUferstruktur1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JPanel panErgebnisse;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGewaesserumfeld;
    private javax.swing.JPanel panKartierabschnitt;
    private javax.swing.JPanel panLaengsprofil;
    private javax.swing.JPanel panLaufentwicklung;
    private javax.swing.JPanel panQuerprofil;
    private javax.swing.JPanel panSohlenstruktur;
    private javax.swing.JPanel panUferstruktur;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public FgskKartierabschnittEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public FgskKartierabschnittEditor(final boolean readOnly) {
        this.readOnly = readOnly;

        initComponents();

        tpMain.setUI(new TabbedPaneUITransparent());

        if (readOnly) {
            fgskKartierabschnittKartierabschnitt1.setReadOnly(readOnly);
            fgskKartierabschnittLaufentwicklung1.setReadOnly(readOnly);
            fgskKartierabschnittLaengsprofil1.setReadOnly(readOnly);
            fgskKartierabschnittQuerprofil1.setReadOnly(readOnly);
            fgskKartierabschnittSohlenverbau1.setReadOnly(readOnly);
            fgskKartierabschnittUferstruktur1.setReadOnly(readOnly);
            fgskKartierabschnittGewaesserumfeld1.setReadOnly(readOnly);
        } else {
            // fully initialise the cache
            CalcCache.getInstance().init();
        }
        this.calcL = new CalcListener();
        tpMain.addChangeListener(WeakListeners.change(calcL, tpMain));

        // will only be changed in EDT
        selectedTabIndex = tpMain.getSelectedIndex();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            fgskKartierabschnittKartierabschnitt1.setCidsBean(cidsBean);
            fgskKartierabschnittLaufentwicklung1.setCidsBean(cidsBean);
            fgskKartierabschnittLaengsprofil1.setCidsBean(cidsBean);
            fgskKartierabschnittQuerprofil1.setCidsBean(cidsBean);
            fgskKartierabschnittSohlenverbau1.setCidsBean(cidsBean);
            fgskKartierabschnittUferstruktur1.setCidsBean(cidsBean);
            fgskKartierabschnittGewaesserumfeld1.setCidsBean(cidsBean);
            fgskKartierabschnittErgebnisse1.setCidsBean(cidsBean);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        panKartierabschnitt = new javax.swing.JPanel();
        fgskKartierabschnittKartierabschnitt1 = new FgskKartierabschnittKartierabschnitt(readOnly);
        panLaufentwicklung = new javax.swing.JPanel();
        fgskKartierabschnittLaufentwicklung1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaufentwicklung();
        panLaengsprofil = new javax.swing.JPanel();
        fgskKartierabschnittLaengsprofil1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaengsprofil();
        panQuerprofil = new javax.swing.JPanel();
        fgskKartierabschnittQuerprofil1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittQuerprofil();
        panSohlenstruktur = new javax.swing.JPanel();
        fgskKartierabschnittSohlenverbau1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittSohlenverbau();
        panUferstruktur = new javax.swing.JPanel();
        fgskKartierabschnittUferstruktur1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittUferstruktur();
        panGewaesserumfeld = new javax.swing.JPanel();
        fgskKartierabschnittGewaesserumfeld1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittGewaesserumfeld();
        panErgebnisse = new javax.swing.JPanel();
        fgskKartierabschnittErgebnisse1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittErgebnisse();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1105, 720));
        setPreferredSize(new java.awt.Dimension(1100, 720));
        setLayout(new java.awt.BorderLayout());

        tpMain.setMinimumSize(new java.awt.Dimension(1104, 710));
        tpMain.setPreferredSize(new java.awt.Dimension(1104, 710));

        panKartierabschnitt.setOpaque(false);
        panKartierabschnitt.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panKartierabschnitt.add(fgskKartierabschnittKartierabschnitt1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panKartierabschnitt.TabConstraints.tabTitle"),
            panKartierabschnitt); // NOI18N

        panLaufentwicklung.setOpaque(false);
        panLaufentwicklung.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panLaufentwicklung.add(fgskKartierabschnittLaufentwicklung1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panLaufentwicklung.TabConstraints.tabTitle"),
            panLaufentwicklung); // NOI18N

        panLaengsprofil.setOpaque(false);
        panLaengsprofil.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panLaengsprofil.add(fgskKartierabschnittLaengsprofil1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panLaengsprofil.TabConstraints.tabTitle"),
            panLaengsprofil); // NOI18N

        panQuerprofil.setOpaque(false);
        panQuerprofil.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panQuerprofil.add(fgskKartierabschnittQuerprofil1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panQuerprofil.TabConstraints.tabTitle"),
            panQuerprofil); // NOI18N

        panSohlenstruktur.setOpaque(false);
        panSohlenstruktur.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panSohlenstruktur.add(fgskKartierabschnittSohlenverbau1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panSohlenstruktur.TabConstraints.tabTitle"),
            panSohlenstruktur); // NOI18N

        panUferstruktur.setOpaque(false);
        panUferstruktur.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panUferstruktur.add(fgskKartierabschnittUferstruktur1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panUferstruktur.TabConstraints.tabTitle"),
            panUferstruktur); // NOI18N

        panGewaesserumfeld.setOpaque(false);
        panGewaesserumfeld.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panGewaesserumfeld.add(fgskKartierabschnittGewaesserumfeld1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panGewaesserumfeld.TabConstraints.tabTitle"),
            panGewaesserumfeld); // NOI18N

        panErgebnisse.setOpaque(false);
        panErgebnisse.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panErgebnisse.add(fgskKartierabschnittErgebnisse1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panErgebnisse.TabConstraints.tabTitle"),
            panErgebnisse); // NOI18N

        add(tpMain, java.awt.BorderLayout.PAGE_START);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        fgskKartierabschnittKartierabschnitt1.dispose();
        fgskKartierabschnittLaufentwicklung1.dispose();
        fgskKartierabschnittLaengsprofil1.dispose();
        fgskKartierabschnittQuerprofil1.dispose();
        fgskKartierabschnittSohlenverbau1.dispose();
        fgskKartierabschnittUferstruktur1.dispose();
        fgskKartierabschnittGewaesserumfeld1.dispose();
        fgskKartierabschnittErgebnisse1.dispose();
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.getTitle().returnValuePart") + String.valueOf(cidsBean); // NOI18N
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        fgskKartierabschnittKartierabschnitt1.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        boolean res = fgskKartierabschnittKartierabschnitt1.prepareForSave();
        res &= fgskKartierabschnittQuerprofil1.prepareForSave();

        return res;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("fgsk_kartierabschnitt", FgskKartierabschnittEditor.class, WRRLUtil.DOMAIN_NAME) // NOI18N
        .run();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class CalcListener implements ChangeListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void stateChanged(final ChangeEvent e) {
            final Component leftC = tpMain.getComponentAt(selectedTabIndex);
            final Component enteredC = tpMain.getSelectedComponent();
            final Boolean vorkatierung = (Boolean)cidsBean.getProperty("vorkatierung");

            if (!handleSonderfall() || readOnly || ((vorkatierung != null) && vorkatierung.booleanValue())) {
                return;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("editor tab selection changed from old comp: " + leftC + "to new comp: " + enteredC); // NOI18N
            }

            // NOTE: if to slow, send to background
            String name = null;
            try {
                if (panErgebnisse.equals(enteredC)) {
                    try {
                        Calc.getInstance().calcWBEnvRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate wb env rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.envRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.envRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcWBLongProfileRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate long profile rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.longProfileRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.longProfileRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcCourseEvoRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate course evolution rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.courseEvoRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.courseEvoRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcWBCrossProfileRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate cross profile rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.crossProfileRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.crossProfileRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcBedStructureRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate bed structure rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.bedStructureRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.bedStructureRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcBankStructureRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate bank structure rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.bankStructureRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.bankStructureRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }

                    try {
                        Calc.getInstance().calcOverallRating(cidsBean);
                    } catch (final ValidationException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("cannot calculate overall rating", ex); // NOI18N
                        }

                        JOptionPane.showMessageDialog(
                            FgskKartierabschnittEditor.this,
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.overallRatingNotPossible.message"), // NOI18N
                            NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.overallRatingNotPossible.title"), // NOI18N
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    if (panGewaesserumfeld.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.wbenv");                     // NOI18N
                        Calc.getInstance().calcWBEnvRating(cidsBean);
                        fgskKartierabschnittErgebnisse1.setCidsBean(cidsBean);
                    } else if (panLaengsprofil.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.longProfile");               // NOI18N
                        Calc.getInstance().calcWBLongProfileRating(cidsBean);
                    } else if (panLaufentwicklung.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.courseEvo");                 // NOI18N
                        Calc.getInstance().calcCourseEvoRating(cidsBean);
                    } else if (panQuerprofil.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.crossProfile");              // NOI18N
                        Calc.getInstance().calcWBCrossProfileRating(cidsBean);
                    } else if (panSohlenstruktur.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.bedStructure");              // NOI18N
                        Calc.getInstance().calcBedStructureRating(cidsBean);
                    } else if (panUferstruktur.equals(leftC)) {
                        name = NbBundle.getMessage(
                                FgskKartierabschnittEditor.class,
                                "FgskKartierabschnittEditor.CalcListener.stateChanged.name.bankStructure");             // NOI18N
                        Calc.getInstance().calcBankStructureRating(cidsBean);
                    }
                }
            } catch (final ValidationException ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("cannot calculate rating: " + name, ex);                                                  // NOI18N
                }

                JOptionPane.showMessageDialog(
                    FgskKartierabschnittEditor.this,
                    NbBundle.getMessage(
                        FgskKartierabschnittEditor.class,
                        "FgskKartierabschnittEditor.CalcListener.stateChanged.ratingNotPossible.message", // NOI18N
                        name),
                    NbBundle.getMessage(
                        FgskKartierabschnittEditor.class,
                        "FgskKartierabschnittEditor.CalcListener.stateChanged.ratingNotPossible.title", // NOI18N
                        name),
                    JOptionPane.INFORMATION_MESSAGE);
            } finally {
                selectedTabIndex = tpMain.getSelectedIndex();
            }

            if (selectedTabIndex == 7) {
                fgskKartierabschnittErgebnisse1.refreshGueteklasse();
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @return  true, iff the calculations should be done
         */
        private boolean handleSonderfall() {
            if (cidsBean == null) {
                return false;
            }
            final CidsBean sonderfall = (CidsBean)cidsBean.getProperty("sonderfall_id");

            if ((sonderfall != null) && (((Integer)sonderfall.getProperty("id")).intValue() == 1)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("property sonderfall is set to verrohrt"); // NOI18N
                }

                if (!readOnly) {
                    try {
                        cidsBean.setProperty("punktzahl_gesamt", 0.0);
                        cidsBean.setProperty("punktzahl_sohle", null);
                        cidsBean.setProperty("punktzahl_ufer", null);
                        cidsBean.setProperty("punktzahl_land", null);
                        cidsBean.setProperty("laufentwicklung_summe_punktzahl", null);
                        cidsBean.setProperty("laufentwicklung_anzahl_kriterien", null);
                        cidsBean.setProperty("laengsprofil_summe_punktzahl", null);
                        cidsBean.setProperty("laengsprofil_anzahl_kriterien", null);
                        cidsBean.setProperty("querprofil_summe_punktzahl", null);
                        cidsBean.setProperty("querprofil_anzahl_kriterien", null);
                        cidsBean.setProperty("sohlenstruktur_summe_punktzahl", null);
                        cidsBean.setProperty("sohlenstruktur_anzahl_kriterien", null);
                        cidsBean.setProperty("uferstruktur_summe_punktzahl", null);
                        cidsBean.setProperty("uferstruktur_anzahl_kriterien", null);
                        cidsBean.setProperty("uferstruktur_summe_punktzahl_links", null);
                        cidsBean.setProperty("uferstruktur_anzahl_kriterien_links", null);
                        cidsBean.setProperty("uferstruktur_summe_punktzahl_rechts", null);
                        cidsBean.setProperty("uferstruktur_anzahl_kriterien_rechts", null);
                        cidsBean.setProperty("gewaesserumfeld_summe_punktzahl", null);
                        cidsBean.setProperty("gewaesserumfeld_anzahl_kriterien", null);
                        cidsBean.setProperty("gewaesserumfeld_summe_punktzahl_links", null);
                        cidsBean.setProperty("gewaesserumfeld_anzahl_kriterien_links", null);
                        cidsBean.setProperty("gewaesserumfeld_summe_punktzahl_rechts", null);
                        cidsBean.setProperty("gewaesserumfeld_anzahl_kriterien_rechts", null);
                        fgskKartierabschnittErgebnisse1.refreshGueteklasse();
                    } catch (final Exception ex) {
                        LOG.error("Error while setting a property", ex);
                    }
                }

                if ((tpMain.getSelectedIndex() != 7) && (tpMain.getSelectedIndex() != 0)) {
                    JOptionPane.showMessageDialog(
                        FgskKartierabschnittEditor.this,
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.noChangePossible.message"),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.noChangePossible.title"),
                        JOptionPane.INFORMATION_MESSAGE);
                    tpMain.setSelectedIndex(selectedTabIndex);
                } else {
                    selectedTabIndex = tpMain.getSelectedIndex();
                }
                return false;
            }

            return true;
        }
    }
}
