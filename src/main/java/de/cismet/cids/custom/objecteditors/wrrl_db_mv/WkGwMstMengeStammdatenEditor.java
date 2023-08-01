/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * ChemieMstStammdatenEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObjectNode;

import javafx.application.Platform;

import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.WritableImage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CoordinateConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.WebDavHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.security.WebAccessManager;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.FXWebViewPanel;
import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkGwMstMengeStammdatenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            WkGwMstMengeStammdatenEditor.class);
    private static final String OLD_SIZE =
        "{\"viewer\":{\"width\":1100,\"height\":700,\"padding\":10,\"fill\":false},\"browser\":{\"width\":1100,\"height\":700,\"padding\":40,\"fill\":false}}";
    private static final String NEW_SIZE =
        "{\"viewer\":{\"width\":1000,\"height\":636,\"padding\":10,\"fill\":false},\"browser\":{\"width\":1000,\"height\":636,\"padding\":40,\"fill\":false}}";
//    private static final String URL_TEMPLATE = "https://fis-wasser-mv.de/charts/ganglinien/mkz/%1s.html";
    private static final String URL_TEMPLATE = "https://www.fis-wasser-mv.de/doku/chart_menge/%1s.html";
    private static int CHART_WIDTH = 800;
    private static int CHART_HEIGHT = 600;

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    private final FXWebViewPanel browserPanel = new FXWebViewPanel();
    private JLabel jLabel1 = new JLabel();
    private volatile boolean loadingComplete = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBauj;
    private javax.swing.JLabel lblBaujVal;
    private javax.swing.JLabel lblFilterOb;
    private javax.swing.JLabel lblFilterObVal;
    private javax.swing.JLabel lblFilterUn;
    private javax.swing.JLabel lblFilterUnVal;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGelHoehe;
    private javax.swing.JLabel lblGelHoeheVal;
    private javax.swing.JLabel lblGewVal;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHoeheM;
    private javax.swing.JLabel lblHoeheMVal;
    private javax.swing.JLabel lblLageVal;
    private javax.swing.JLabel lblMstCodeVal;
    private javax.swing.JLabel lblMstKennz;
    private javax.swing.JLabel lblMstName;
    private javax.swing.JLabel lblReHo;
    private javax.swing.JLabel lblWkGw;
    private javax.swing.JLabel lblWkGwVal;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panStamm;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkGwMstMengeStammdatenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkGwMstMengeStammdatenEditor(final boolean readOnly) {
        this(readOnly, false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     * @param  embedded  DOCUMENT ME!
     */
    public WkGwMstMengeStammdatenEditor(final boolean readOnly, final boolean embedded) {
        this.readOnly = readOnly;
        initComponents();

        if (embedded) {
            panStamm.setVisible(false);
        }

        if (readOnly) {
            lblWkGwVal.setForeground(Color.BLUE);
        }

        jPanel4.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isMouseOver(e)) {
                        try {
                            final String url = String.format(
                                    URL_TEMPLATE,
                                    WebDavHelper.encodeURL(String.valueOf(cidsBean.getProperty("messstelle"))));
                            BrowserLauncher.openURL(url);
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                    if (!isHandCursor && isMouseOver(e)) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        isHandCursor = true;
                    } else if (isHandCursor) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    if (isHandCursor) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                private boolean isMouseOver(final MouseEvent e) {
                    return ((e.getPoint().x > 10) && (e.getPoint().x < 85) && (e.getPoint().y < 18));
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            try {
                jPanel1.removeAll();
                jPanel1.add(browserPanel, java.awt.BorderLayout.CENTER);
                final String url = String.format(
                        URL_TEMPLATE,
                        WebDavHelper.encodeURL(String.valueOf(cidsBean.getProperty("messstelle"))));
                final InputStream is = WebAccessManager.getInstance().doRequest(new URL(url));
                final Charset cs = Charset.forName("UTF-8");
                BufferedReader br;

                if (cs != null) {
                    br = new BufferedReader(new InputStreamReader(is, cs));
                } else {
                    br = new BufferedReader(new InputStreamReader(is));
                }

                final StringBuilder content = new StringBuilder();
                String tmp;

                while ((tmp = br.readLine()) != null) {
                    content.append(tmp).append("\n");
                }

                jPanel1.setSize(CHART_WIDTH + 100, CHART_HEIGHT);
                jPanel1.setMinimumSize(new Dimension(CHART_WIDTH + 100, CHART_HEIGHT));
                jPanel1.setMaximumSize(new Dimension(CHART_WIDTH + 100, CHART_HEIGHT));
                jPanel1.setPreferredSize(new Dimension(CHART_WIDTH + 100, CHART_HEIGHT));
                browserPanel.setSize(CHART_WIDTH, CHART_HEIGHT);
                browserPanel.setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
                browserPanel.setMaximumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
                browserPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
                browserPanel.loadContent(content.toString());
//                browserPanel.loadContent(content.toString().replace(OLD_SIZE, NEW_SIZE));
                loadingComplete = false;

                final Thread t = new Thread() {

                        @Override
                        public void run() {
                            while (!loadingComplete) {
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException ex) {
                                    // nothing to do
                                }
                                Platform.runLater(new Runnable() {

                                        @Override
                                        public void run() {
                                            if (!browserPanel.getWebEngine().getLoadWorker().isRunning()) {
                                                loadingComplete = true;
                                            }
                                        }
                                    });
                            }

                            Platform.runLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        BufferedImage bimage = null;

                                        try {
                                            final WritableImage image = browserPanel.getScene().snapshot(null);
                                            final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                                            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
                                            bimage = ImageIO.read(
                                                    new ByteArrayInputStream(byteOutput.toByteArray()));
                                        } catch (Exception e) {
                                            LOG.error("error", e);
                                        }

                                        final BufferedImage i = bimage;

                                        EventQueue.invokeLater(new Runnable() {

                                                @Override
                                                public void run() {
                                                    if (i != null) {
                                                        jPanel1.removeAll();
                                                        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);
                                                        jLabel1.setIcon(new ImageIcon(i));
                                                        jLabel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                                        jPanel1.setSize(CHART_WIDTH, CHART_HEIGHT);
                                                        jPanel1.setMinimumSize(
                                                            new Dimension(CHART_WIDTH, CHART_HEIGHT));
                                                        jPanel1.setMaximumSize(
                                                            new Dimension(CHART_WIDTH, CHART_HEIGHT));
                                                        jPanel1.setPreferredSize(
                                                            new Dimension(CHART_WIDTH, CHART_HEIGHT));
                                                        jLabel1.addMouseListener(new MouseAdapter() {

                                                                @Override
                                                                public void mouseClicked(final MouseEvent e) {
                                                                    try {
                                                                        BrowserLauncher.openURL(url);
                                                                    } catch (Exception ex) {
                                                                        LOG.warn(ex, ex);
                                                                    }
                                                                }
                                                            });
                                                    }
                                                }
                                            });
                                    }
                                });
                        }
                    };

//                t.start();
            } catch (Exception e) {
                LOG.error("error", e);
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            final JLabel lab = new JLabel("Fehlendes Diagramm");
                            lab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                            jPanel1.removeAll();
                            jPanel1.add(lab, java.awt.BorderLayout.CENTER);
                            jPanel1.setSize(500, 300);
                            jPanel1.setMinimumSize(new Dimension(500, 300));
                            jPanel1.setMaximumSize(new Dimension(500, 300));
                            jPanel1.setPreferredSize(new Dimension(500, 300));
                        }
                    });
            }
        }

        lblFoot.setText("");
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panStamm = new javax.swing.JPanel();
        lblMstName = new javax.swing.JLabel();
        lblReHo = new javax.swing.JLabel();
        lblMstCodeVal = new javax.swing.JLabel();
        lblGewVal = new javax.swing.JLabel();
        lblLageVal = new javax.swing.JLabel();
        lblFilterOb = new javax.swing.JLabel();
        lblFilterUn = new javax.swing.JLabel();
        lblFilterObVal = new javax.swing.JLabel();
        lblFilterUnVal = new javax.swing.JLabel();
        lblMstKennz = new javax.swing.JLabel();
        lblBauj = new javax.swing.JLabel();
        lblBaujVal = new javax.swing.JLabel();
        lblGelHoehe = new javax.swing.JLabel();
        lblGelHoeheVal = new javax.swing.JLabel();
        lblHoeheM = new javax.swing.JLabel();
        lblHoeheMVal = new javax.swing.JLabel();
        lblWkGw = new javax.swing.JLabel();
        lblWkGwVal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1400, 1200));
        setPreferredSize(new java.awt.Dimension(1400, 1200));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(1400, 1200));
        panInfo.setPreferredSize(new java.awt.Dimension(1400, 1200));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Messstation");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        panStamm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Stammdaten"));
        panStamm.setOpaque(false);
        panStamm.setLayout(new java.awt.GridBagLayout());

        lblMstName.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblMstName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstName, gridBagConstraints);

        lblReHo.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblReHo.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblReHo, gridBagConstraints);

        lblMstCodeVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMstCodeVal.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.messstelle}"),
                lblMstCodeVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstCodeVal, gridBagConstraints);

        lblGewVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGewVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mst_name}"),
                lblGewVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGewVal, gridBagConstraints);

        lblLageVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblLageVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.the_geom}"),
                lblLageVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("/");
        binding.setSourceUnreadableValue("error");
        binding.setConverter(new CoordinateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblLageVal, gridBagConstraints);

        lblFilterOb.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblFilterOb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblFilterOb, gridBagConstraints);

        lblFilterUn.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblFilterUn.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblFilterUn, gridBagConstraints);

        lblFilterObVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblFilterObVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.filter_bis} m"),
                lblFilterObVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblFilterObVal, gridBagConstraints);

        lblFilterUnVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblFilterUnVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.filter_von} m"),
                lblFilterUnVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblFilterUnVal, gridBagConstraints);

        lblMstKennz.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblMstKennz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstKennz, gridBagConstraints);

        lblBauj.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblBauj.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBauj, gridBagConstraints);

        lblBaujVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblBaujVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.baujahr}"),
                lblBaujVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBaujVal, gridBagConstraints);

        lblGelHoehe.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblGelHoehe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblGelHoehe, gridBagConstraints);

        lblGelHoeheVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGelHoeheVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gelaendehoehe} ${cidsBean.h_sys_gel.value}"),
                lblGelHoeheVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblGelHoeheVal, gridBagConstraints);

        lblHoeheM.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblHoeheM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblHoeheM, gridBagConstraints);

        lblHoeheMVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblHoeheMVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hoehe_messpunkt} ${cidsBean.h_sys_mp.value}"),
                lblHoeheMVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblHoeheMVal, gridBagConstraints);

        lblWkGw.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstMengeStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblFilterOb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblWkGw, gridBagConstraints);

        lblWkGwVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblWkGwVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_gw.name}"),
                lblWkGwVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblWkGwVal.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblWkGwValMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblWkGwVal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panInfoContent.add(panStamm, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                org.openide.util.NbBundle.getMessage(
                    WkGwMstMengeStammdatenEditor.class,
                    "WkGwMstMengeMessungenEditor.jPanel4.border.title",
                    new Object[] {}),
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Dialog", 0, 12),
                new java.awt.Color(28, 72, 227))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1400, 850));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1400, 850));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1400, 850));

        jPanel1.setMaximumSize(new java.awt.Dimension(1800, 1000));
        jPanel1.setMinimumSize(new java.awt.Dimension(1800, 1000));
        jPanel1.setPreferredSize(new java.awt.Dimension(1800, 1000));
        jPanel1.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panInfoContent.add(jPanel4, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblWkGwValMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblWkGwValMouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("wk_gw") instanceof CidsBean)) {
            ComponentRegistry.getRegistry()
                    .getDescriptionPane()
                    .gotoMetaObjectNode(new MetaObjectNode((CidsBean)cidsBean.getProperty("wk_gw")), false);
        }
    }                                                                          //GEN-LAST:event_lblWkGwValMouseClicked

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
