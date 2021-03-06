/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WFSFormTester.java
 *
 * Created on 25. Juli 2006, 17:38
 */
package de.cismet.cismap.custom.wfsforms;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import org.deegree.datatypes.QualifiedName;
import org.deegree.model.feature.DefaultFeature;
import org.deegree.model.feature.FeatureProperty;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.interaction.events.CrsChangedEvent;
import de.cismet.cismap.commons.wfsforms.AbstractWFSForm;
import de.cismet.cismap.commons.wfsforms.WFSFormFeature;
import de.cismet.cismap.commons.wfsforms.WFSFormQuery;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten.hell@cismet.de
 * @version  $Revision$, $Date$
 */
public class WFSFormGemeindenSearch extends AbstractWFSForm implements ActionListener {

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private WFSFormFeature strasse = null;
    private WFSFormFeature gemeinde = null;
    private WFSFormFeature lastVisualizedFeature = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboGemeinden;
    private javax.swing.JCheckBox chkLockScale;
    private javax.swing.JCheckBox chkVisualize;
    private javax.swing.JButton cmdOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblBehind;
    private javax.swing.JPanel panFill;
    private javax.swing.JProgressBar prbGemeinden;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WFSFormTester.
     */
    public WFSFormGemeindenSearch() {
        if (log.isDebugEnabled()) {
            log.debug("new WFSFormGemeindeSearch"); // NOI18N
        }
        try {
            initComponents();
//        cboStreets.setEditable(true);
//        cboNr.setEditable(true);
            StaticSwingTools.decorateWithFixedAutoCompleteDecorator(cboGemeinden);
//        prbLocationtypes.setPreferredSize(new java.awt.Dimension(1,5));
            prbGemeinden.setPreferredSize(new java.awt.Dimension(1, 5));

            cboGemeinden.setRenderer(new ListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(final JList list,
                            final Object value,
                            final int index,
                            final boolean isSelected,
                            final boolean cellHasFocus) {
                        final DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
                        final JLabel lbl = (JLabel)(dlcr.getListCellRendererComponent(
                                    list,
                                    value,
                                    index,
                                    isSelected,
                                    cellHasFocus));
                        String additionalInfo = "";                // NOI18N
                        try {
                            final FeatureProperty[] fpa = ((WFSFormFeature)value).getRawFeatureArray(
                                    "app",
                                    "the_geom",
                                    "http://www.deegree.org/app"); // NOI18N

                            for (int i = 0; i < fpa.length; ++i) {
                                if (i > 0) {
                                    additionalInfo += ", "; // NOI18N
                                }

                                additionalInfo += ((DefaultFeature)fpa[i].getValue())
                                            .getProperties(
                                                new QualifiedName(
                                                    "app",
                                                    "the_geom",
                                                    new URI("http://www.deegree.org/app")))[0].getValue()
                                            .toString(); // NOI18N
                            }
                        } catch (Exception ex) {
                            log.error(ex, ex);
                        }

                        if (additionalInfo != null) {
                            lbl.setToolTipText(additionalInfo);
                        }
                        return lbl;
                    }
                });

//        listComponents.put("cboAllLocationtypes",cboLocationtypes);
//        listComponents.put("cboAllLocationtypesProgress",prbLocationtypes);
            listComponents.put("cboGemeinden", cboGemeinden);         // NOI18N
            listComponents.put("cboGemeindenProgress", prbGemeinden); // NOI18N

            pMark.setVisible(false);
            pMark.setSweetSpotX(0.5d);
            pMark.setSweetSpotY(1d);
            txtSearch.getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void changedUpdate(final DocumentEvent e) {
                        doSearch();
                    }

                    @Override
                    public void insertUpdate(final DocumentEvent e) {
                        doSearch();
                    }

                    @Override
                    public void removeUpdate(final DocumentEvent e) {
                        doSearch();
                    }
                });

            lblBehind.setMinimumSize(new Dimension(94, 16));
            lblBehind.setMaximumSize(new Dimension(94, 16));
            lblBehind.setPreferredSize(new Dimension(94, 16));
            super.addActionListener(this);

            // CismapBroker.getInstance().getMappingComponent().getHighlightingLayer().addChild(pMark);
        } catch (Exception e) {
            log.error("Could not Create WFForm", e); // NOI18N
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void garbageDuringAutoCompletion(final JComboBox box) {
    }
    /**
     * DOCUMENT ME!
     */
    private void doSearch() {
        try {
            if (txtSearch.getText().length() >= 3) {
                if (log.isDebugEnabled()) {
                    log.debug("doSearch");                         // NOI18N
                }
                final HashMap<String, String> hm = new HashMap<String, String>();
                final String newString = getRightEncodedString(txtSearch.getText());
                hm.put("@@search_text@@", newString);              // NOI18N
                requestRefresh("cboGemeinden", hm);                // NOI18N
            } else {
                lblBehind.setText(org.openide.util.NbBundle.getMessage(
                        WFSFormGemeindenSearch.class,
                        "WFSFormGemeindenSearch.lblBehind.text")); // NOI18N
            }
        } catch (Exception e) {
            log.error("Fehler beim Ausführen der Suche", e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        cmdOk = new javax.swing.JButton();
        chkVisualize = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        chkLockScale = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblBehind = new javax.swing.JLabel();
        panFill = new javax.swing.JPanel();
        cboGemeinden = new javax.swing.JComboBox();
        prbGemeinden = new javax.swing.JProgressBar();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setMinimumSize(new java.awt.Dimension(373, 1));
        setLayout(new java.awt.GridBagLayout());

        cmdOk.setMnemonic('P');
        cmdOk.setText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.cmdOk.text")); // NOI18N
        cmdOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(cmdOk, gridBagConstraints);

        chkVisualize.setSelected(true);
        chkVisualize.setToolTipText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.chkVisualize.toolTipText")); // NOI18N
        chkVisualize.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkVisualize.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkVisualizeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 0, 0);
        add(chkVisualize, gridBagConstraints);

        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cismap/commons/gui/res/markPoint.png"))); // NOI18N
        jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.jLabel1.toolTipText"));                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 7, 0, 0);
        add(jLabel1, gridBagConstraints);

        chkLockScale.setSelected(true);
        chkLockScale.setToolTipText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.chkLockScale.toolTipText")); // NOI18N
        chkLockScale.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 14, 0, 0);
        add(chkLockScale, gridBagConstraints);

        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cismap/commons/gui/res/fixMapScale.png"))); // NOI18N
        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.jLabel2.toolTipText"));                                // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 7, 0, 0);
        add(jLabel2, gridBagConstraints);

        txtSearch.setMaximumSize(new java.awt.Dimension(220, 19));
        txtSearch.setMinimumSize(new java.awt.Dimension(220, 19));
        txtSearch.setPreferredSize(new java.awt.Dimension(220, 19));
        txtSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {

                @Override
                public void inputMethodTextChanged(final java.awt.event.InputMethodEvent evt) {
                    txtSearchInputMethodTextChanged(evt);
                }
                @Override
                public void caretPositionChanged(final java.awt.event.InputMethodEvent evt) {
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(txtSearch, gridBagConstraints);

        lblBehind.setText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.lblBehind.text")); // NOI18N
        lblBehind.setMaximumSize(new java.awt.Dimension(150, 14));
        lblBehind.setMinimumSize(new java.awt.Dimension(150, 14));
        lblBehind.setPreferredSize(new java.awt.Dimension(150, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lblBehind, gridBagConstraints);

        panFill.setMinimumSize(new java.awt.Dimension(1, 1));
        panFill.setPreferredSize(new java.awt.Dimension(1, 1));

        final org.jdesktop.layout.GroupLayout panFillLayout = new org.jdesktop.layout.GroupLayout(panFill);
        panFill.setLayout(panFillLayout);
        panFillLayout.setHorizontalGroup(
            panFillLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 11, Short.MAX_VALUE));
        panFillLayout.setVerticalGroup(
            panFillLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 34, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 1.0;
        add(panFill, gridBagConstraints);

        cboGemeinden.setEnabled(false);
        cboGemeinden.setMaximumSize(new java.awt.Dimension(27, 19));
        cboGemeinden.setMinimumSize(new java.awt.Dimension(27, 19));
        cboGemeinden.setPreferredSize(new java.awt.Dimension(27, 19));
        cboGemeinden.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cboGemeindenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 30.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 0);
        add(cboGemeinden, gridBagConstraints);

        prbGemeinden.setBorderPainted(false);
        prbGemeinden.setMaximumSize(new java.awt.Dimension(100, 5));
        prbGemeinden.setMinimumSize(new java.awt.Dimension(100, 5));
        prbGemeinden.setPreferredSize(new java.awt.Dimension(100, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 10);
        add(prbGemeinden, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSearchInputMethodTextChanged(final java.awt.event.InputMethodEvent evt) { //GEN-FIRST:event_txtSearchInputMethodTextChanged
        log.fatal("kik");                                                                     // NOI18N
    }                                                                                         //GEN-LAST:event_txtSearchInputMethodTextChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkVisualizeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkVisualizeActionPerformed
//        MappingComponent mc = getMappingComponent();
//        if (mc == null) {
//            mc = CismapBroker.getInstance().getMappingComponent();
//        }

        if (gemeinde != null) {
            visualizePosition(chkVisualize.isSelected());
        }
    } //GEN-LAST:event_chkVisualizeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  showMarker  DOCUMENT ME!
     */
    public void visualizePosition(final boolean showMarker) {
        lastVisualizedFeature = gemeinde;
        mappingComponent.getHighlightingLayer().removeAllChildren();
        mappingComponent.getHighlightingLayer().addChild(pMark);
        mappingComponent.addStickyNode(pMark);
        final Point p = CrsTransformer.transformToGivenCrs((Point)getPointFromGemeinde(),
                mappingComponent.getMappingModel().getSrs().getCode());
        final double x = mappingComponent.getWtst().getScreenX(p.getCoordinate().x);
        final double y = mappingComponent.getWtst().getScreenY(p.getCoordinate().y);
        pMark.setOffset(x, y);
        pMark.setVisible(showMarker);
        mappingComponent.rescaleStickyNodes();

        CismapBroker.getInstance().removeCrsChangeListener(this);
        CismapBroker.getInstance().addCrsChangeListener(this);
    }

    @Override
    public void crsChanged(final CrsChangedEvent event) {
        if (mappingComponent.getHighlightingLayer().getAllNodes().contains(pMark) && (lastVisualizedFeature != null)) {
            visualizePosition(lastVisualizedFeature, pMark.getVisible());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdOkActionPerformed
        final boolean history = true;
        MappingComponent mc = getMappingComponent();
        if (mc == null) {
            mc = CismapBroker.getInstance().getMappingComponent();
        }
        final boolean scaling = !(mc.isFixedMapScale()) && !(chkLockScale.isSelected());
        XBoundingBox bb = null;
        final int animation = mc.getAnimationDuration();
        if (gemeinde != null) {
            bb = new XBoundingBox(getPointFromGemeinde().buffer(1000));
        } else {
            return;
        }
        mc.gotoBoundingBox(bb, history, scaling, animation);
        chkVisualizeActionPerformed(null);
    }                                                                         //GEN-LAST:event_cmdOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Geometry getPointFromGemeinde() {
        final URI uri = gemeinde.getFeature().getProperties()[0].getName().getNamespace();
        final QualifiedName qNameHWert = new QualifiedName("hochwert", uri);
        final QualifiedName qNameRWert = new QualifiedName("rechstwert", uri);
        final String hochwert = (String)gemeinde.getFeature().getProperties(qNameHWert)[0].getValue();
        final String rechtswert = (String)gemeinde.getFeature().getProperties(qNameRWert)[0].getValue();
        final double h = Double.parseDouble(hochwert);
        final double r = Double.parseDouble(rechtswert);
        final GeometryFactory factory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 5650);
        return factory.createPoint(new Coordinate(r, h));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cboGemeindenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cboGemeindenActionPerformed
        if (log.isDebugEnabled()) {
            log.debug("cboGemeindenActionPerformed()");                              // NOI18N
        }
        if (cboGemeinden.getSelectedItem() instanceof WFSFormFeature) {
            gemeinde = (WFSFormFeature)cboGemeinden.getSelectedItem();
        }
    }                                                                                //GEN-LAST:event_cboGemeindenActionPerformed

    @Override
    public void actionPerformed(final ActionEvent e) {
        lblBehind.setText(org.openide.util.NbBundle.getMessage(
                WFSFormGemeindenSearch.class,
                "WFSFormGemeindenSearch.lblBehind.text2",
                new Object[] { cboGemeinden.getItemCount() }));                  // NOI18N
        if (log.isDebugEnabled()) {
            log.debug("cboGemeinden.getItemAt(0):" + cboGemeinden.getItemAt(0)); // NOI18N
        }
        if (cboGemeinden.getItemCount() == 1) {
            cboGemeinden.setEditable(false);
            cboGemeinden.setSelectedItem(cboGemeinden.getItemAt(0));
            cboGemeinden.setEditable(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        Log4JQuickConfig.configure4LumbermillOnLocalhost();
        final WFSFormQuery gem = new WFSFormQuery();
        gem.setComponentName("cboGemeinden");                                          // NOI18N
        gem.setServerUrl("http://wfs.fis-wasser-mv.de/services");
        gem.setPropertyNamespace("http://www.deegree.org/app");
        gem.setPropertyPrefix("app");
        gem.setDisplayTextProperty("gen");                                             // NOI18N
        gem.setExtentProperty("the_geom");                                             // NOI18N
        gem.setFilename("/de/cismet/cismap/custom/wfsforms/gemeindentestrequest.xml"); // NOI18N
        gem.setId("gemeinden_suche");                                                  // NOI18N
        gem.setIdProperty("id");                                                       // NOI18N
        gem.setTitle("Suche nach Gemeinden");                                          // NOI18N
        gem.setType(WFSFormQuery.FOLLOWUP);
        gem.setWfsQueryString(readFileFromRessourceAsString(
                "/de/cismet/cismap/custom/wfsforms/gemeindentestrequest.xml"));        // NOI18N

        System.out.println(gem.getWfsQueryString());

        final JFrame f = new JFrame("Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final WFSFormGemeindenSearch s = new WFSFormGemeindenSearch();
        final Vector<WFSFormQuery> v = new Vector<WFSFormQuery>();
        v.add(gem);
        s.setQueries(v);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   file  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  java.io.IOException  DOCUMENT ME!
     */
    private static String readFileFromRessourceAsString(final String file) throws java.io.IOException {
        Log4JQuickConfig.configure4LumbermillOnLocalhost();
        final StringBuffer fileData = new StringBuffer(1000);
        final URL url = WFSFormGemeindenSearch.class.getResource(file);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            final String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   s  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getRightEncodedString(final String s) {
//        try {
//            return new String(s.getBytes("UTF-8"), "ISO-8859-1");
//        }
//        catch (Exception e){
//            log.error("Fehler beim KOnvertieren",e);
//            return null;
//        }

        String ret = s;
        ret = ret.replaceAll("ä", "&#228;");
        ret = ret.replaceAll("Ä", "&#196;");
        ret = ret.replaceAll("ö", "&#246;");
        ret = ret.replaceAll("Ö", "&#214;");
        ret = ret.replaceAll("ü", "&#252;");
        ret = ret.replaceAll("Ü", "&#220;");
        ret = ret.replaceAll("ß", "&#223;");
        return ret;
    }
}
