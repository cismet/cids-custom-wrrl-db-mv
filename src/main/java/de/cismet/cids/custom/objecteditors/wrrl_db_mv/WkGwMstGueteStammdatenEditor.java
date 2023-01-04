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

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CoordinateConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.security.WebAccessManager;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkGwMstGueteStammdatenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    DocumentListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "WkGwMstGueteStammdatenEditor");
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            WkGwMstGueteStammdatenEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "wk_gw_mst_chemie_messungen",
            CC);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    private boolean noDocumentUpdate = false;
    private volatile boolean urlExists = true;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnForward;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusVal;
    private javax.swing.JLabel lblSteckbrief;
    private javax.swing.JLabel lblSteckbriefVal;
    private javax.swing.JLabel lblWkGw;
    private javax.swing.JLabel lblWkGwVal;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panScr;
    private javax.swing.JPanel panStamm;
    private javax.swing.JTextField txtJahr;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstChemieMessungenEditor wkGwMstChemieMessungenEditor1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkGwMstGueteStammdatenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkGwMstGueteStammdatenEditor(final boolean readOnly) {
        this(readOnly, false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     * @param  embedded  DOCUMENT ME!
     */
    public WkGwMstGueteStammdatenEditor(final boolean readOnly, final boolean embedded) {
        this.readOnly = readOnly;
        initComponents();

        if (embedded) {
            panStamm.setVisible(false);
        }

        txtJahr.getDocument().addDocumentListener(this);

        if (readOnly) {
            lblWkGwVal.setForeground(Color.BLUE);
            lblWkGwVal.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        final CidsBean bean = (CidsBean)cidsBean.getProperty("wk_gw");

                        if ((bean != null) && readOnly) {
                            ComponentRegistry.getRegistry()
                                    .getDescriptionPane()
                                    .gotoMetaObject(bean.getMetaObject(), "");
                        }
                    }
                });
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            urlExists = true;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        txtJahr.setText(String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR)));
                    }
                });
            refreshMeasures();
            bindingGroup.bind();
            final String url = "https://www.fis-wasser-mv.de/doku/gwk_steckbr/" + cidsBean.getProperty("messstelle")
                        + ".pdf";

            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        lblSteckbriefVal.setText(
                            "<html><a href=\""
                                    + url
                                    + "\">"
                                    + cidsBean.getProperty("messstelle")
                                    + "</a></html>");
                    }
                });
            lblSteckbriefVal.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        try {
                            if (urlExists) {
                                BrowserLauncher.openURL(url);
                            }
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                });

            final Thread checkUrl = new Thread() {

                    @Override
                    public void run() {
                        try {
                            final boolean check = WebAccessManager.getInstance().checkIfURLaccessible(new URL(url));

                            if (!check) {
                                EventQueue.invokeLater(new Runnable() {

                                        @Override
                                        public void run() {
                                            lblSteckbriefVal.setText("kein Steckbrief vorhanden");
                                            urlExists = false;
                                        }
                                    });
                            }
                        } catch (MalformedURLException e) {
                            LOG.error("URL invalid: " + url, e);
                        }
                    }
                };

            checkUrl.start();
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        wkGwMstChemieMessungenEditor1.setCidsBeans(null, null);
                    }
                });
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panStamm = new javax.swing.JPanel();
        lblMstKennz = new javax.swing.JLabel();
        lblMstName = new javax.swing.JLabel();
        lblReHo = new javax.swing.JLabel();
        lblBauj = new javax.swing.JLabel();
        lblMstCodeVal = new javax.swing.JLabel();
        lblGewVal = new javax.swing.JLabel();
        lblLageVal = new javax.swing.JLabel();
        lblBaujVal = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblStatusVal = new javax.swing.JLabel();
        lblFilterOb = new javax.swing.JLabel();
        lblFilterObVal = new javax.swing.JLabel();
        lblFilterUnVal = new javax.swing.JLabel();
        lblFilterUn = new javax.swing.JLabel();
        lblGelHoehe = new javax.swing.JLabel();
        lblGelHoeheVal = new javax.swing.JLabel();
        lblHoeheMVal = new javax.swing.JLabel();
        lblHoeheM = new javax.swing.JLabel();
        lblSteckbrief = new javax.swing.JLabel();
        lblSteckbriefVal = new javax.swing.JLabel();
        lblWkGw = new javax.swing.JLabel();
        lblWkGwVal = new javax.swing.JLabel();
        panScr = new javax.swing.JPanel();
        txtJahr = new javax.swing.JTextField();
        btnBack1 = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        wkGwMstChemieMessungenEditor1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstChemieMessungenEditor();
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

        lblMstKennz.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblMstKennz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstKennz, gridBagConstraints);

        lblMstName.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblMstName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstName, gridBagConstraints);

        lblReHo.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblReHo.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblReHo, gridBagConstraints);

        lblBauj.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblBauj.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBauj, gridBagConstraints);

        lblMstCodeVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMstCodeVal.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.messstelle}"),
                lblMstCodeVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
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
        binding.setSourceUnreadableValue("<error>");
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
        binding.setSourceUnreadableValue("<error>");
        binding.setConverter(new CoordinateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblLageVal, gridBagConstraints);

        lblBaujVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblBaujVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.baujahr}"),
                lblBaujVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBaujVal, gridBagConstraints);

        lblStatus.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstStammdatenEditor.lbStatus.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblStatus, gridBagConstraints);

        lblStatusVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblStatusVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status}"),
                lblStatusVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblStatusVal, gridBagConstraints);

        lblFilterOb.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblFilterOb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblFilterOb, gridBagConstraints);

        lblFilterObVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblFilterObVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.filter_bis} m"),
                lblFilterObVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
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
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblFilterUnVal, gridBagConstraints);

        lblFilterUn.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblFilterUn.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblFilterUn, gridBagConstraints);

        lblGelHoehe.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
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

        lblHoeheM.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblHoeheM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblHoeheM, gridBagConstraints);

        lblSteckbrief.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstStammdatenEditor.lbSteckbrief.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblSteckbrief, gridBagConstraints);

        lblSteckbriefVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSteckbriefVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblSteckbriefVal, gridBagConstraints);

        lblWkGw.setText(org.openide.util.NbBundle.getMessage(
                WkGwMstGueteStammdatenEditor.class,
                "WkGwMstMengeStammdatenEditor.lblWkGw.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblWkGw, gridBagConstraints);

        lblWkGwVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblWkGwVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_gw.name}"),
                lblWkGwVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblWkGwVal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panInfoContent.add(panStamm, gridBagConstraints);

        panScr.setOpaque(false);
        panScr.setLayout(new java.awt.GridBagLayout());

        txtJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahr.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(txtJahr, gridBagConstraints);

        btnBack1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left.png")));          // NOI18N
        btnBack1.setBorder(null);
        btnBack1.setBorderPainted(false);
        btnBack1.setContentAreaFilled(false);
        btnBack1.setFocusPainted(false);
        btnBack1.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack1.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack1.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack1.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-pressed.png")));  // NOI18N
        btnBack1.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-selected.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBack1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnBack1, gridBagConstraints);

        btnForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right.png")));          // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-pressed.png")));  // NOI18N
        btnForward.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-selected.png"))); // NOI18N
        btnForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnForwardActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnForward, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panInfoContent.add(panScr, gridBagConstraints);

        wkGwMstChemieMessungenEditor1.setMinimumSize(new java.awt.Dimension(1000, 600));
        wkGwMstChemieMessungenEditor1.setPreferredSize(new java.awt.Dimension(1000, 600));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(wkGwMstChemieMessungenEditor1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

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
    private void btnBack1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBack1ActionPerformed
        int year = getCurrentlyEnteredYear();
        --year;

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        noDocumentUpdate = false;

        final int newYear = year;
        final WaitingDialogThread<YearAndMeasure> wdt = new WaitingDialogThread<YearAndMeasure>(
                StaticSwingTools.getParentFrame(this),
                true,
                "Lade Messwerte",
                null,
                100) {

                @Override
                protected YearAndMeasure doInBackground() throws Exception {
                    CidsBean[] measure = null;
                    int measureYear = newYear;

                    do {
                        measure = getDataForYear(measureYear);
                        --measureYear;
                    } while ((measure == null) && (measureYear > 2000));

                    return new YearAndMeasure(measure, ++measureYear);
                }

                @Override
                protected void done() {
                    try {
                        final YearAndMeasure measure = get();

                        noDocumentUpdate = true;
                        txtJahr.setText(String.valueOf(measure.getYear()));
                        noDocumentUpdate = false;
                        showNewMeasure(measure.getMeasure());
                    } catch (Exception e) {
                        LOG.error("Erro while searching measure values", e);
                    }
                }
            };

        wdt.start();
    } //GEN-LAST:event_btnBack1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnForwardActionPerformed
        int year = getCurrentlyEnteredYear();
        ++year;

        final int newYear = year;
        final WaitingDialogThread<YearAndMeasure> wdt = new WaitingDialogThread<YearAndMeasure>(StaticSwingTools
                        .getParentFrame(this),
                true,
                "Lade Messwerte",
                null,
                100) {

                @Override
                protected YearAndMeasure doInBackground() throws Exception {
                    CidsBean[] measure = null;
                    int measureYear = newYear;
                    final int currentYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

                    do {
                        measure = getDataForYear(measureYear);
                        ++measureYear;
                    } while ((measure == null) && (measureYear <= currentYear));

                    return new YearAndMeasure(measure, --measureYear);
                }

                @Override
                protected void done() {
                    try {
                        final YearAndMeasure measure = get();

                        noDocumentUpdate = true;
                        txtJahr.setText(String.valueOf(measure.getYear()));
                        noDocumentUpdate = false;
                        showNewMeasure(measure.getMeasure());
                    } catch (Exception e) {
                        LOG.error("Erro while searching measure values", e);
                    }
                }
            };

        wdt.start();
    } //GEN-LAST:event_btnForwardActionPerformed

    /**
     * DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     */
    private void refreshMeasures() {
        final int year = getCurrentlyEnteredYear();

        final CidsBean[] measure = getDataForYear(year);

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    showNewMeasure(measure);
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measure  DOCUMENT ME!
     */
    private void showNewMeasure(final CidsBean[] measure) {
        wkGwMstChemieMessungenEditor1.setCidsBeans(measure, cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getCurrentlyEnteredYear() {
        try {
            return Integer.parseInt(txtJahr.getText());
        } catch (final NumberFormatException e) {
            return new GregorianCalendar().get(GregorianCalendar.YEAR);
        }
    }

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

    /**
     * DOCUMENT ME!
     *
     * @param   year  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean[] getDataForYear(final int year) {
        try {
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName();      // NOI18N
            query += " m WHERE m.mst = '" + cidsBean.getProperty("messstelle");                                      // NOI18N
            query += "' AND year = " + year
                        + " and (wert_nitrat is not null or wert_arsen is not null or wert_cadmium is not null "
                        + "or wert_blei is not null or wert_quecksilber is not null or wert_ammonium is not null "
                        + "or wert_chlorid is not null or wert_nitrit is not null or wert_orthophosphat_p is not null or "
                        + "wert_sulfat is not null or wert_sum_tri_tetrachlorethen is not null) order by datum asc"; // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

            if ((metaObjects != null) && (metaObjects.length >= 0)) {
                final List<CidsBean> beans = new ArrayList<>();

                for (final MetaObject mo : metaObjects) {
                    final CidsBean retVal = mo.getBean();

                    // if the bean is already in the beansToSave list, the bean from the list should be used
                    beans.add(retVal);
                }

                return (beans.isEmpty() ? null : beans.toArray(new CidsBean[beans.size()]));
            } else {
                return null;
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
            return null;
        }
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
        // nothing to do
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class YearAndMeasure {

        //~ Instance fields ----------------------------------------------------

        private CidsBean[] measure;
        private int year;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new YearAndMeasure object.
         *
         * @param  measure  DOCUMENT ME!
         * @param  year     DOCUMENT ME!
         */
        public YearAndMeasure(final CidsBean[] measure, final int year) {
            this.measure = measure;
            this.year = year;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the measure
         */
        public CidsBean[] getMeasure() {
            return measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  measure  the measure to set
         */
        public void setMeasure(final CidsBean[] measure) {
            this.measure = measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the year
         */
        public int getYear() {
            return year;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  year  the year to set
         */
        public void setYear(final int year) {
            this.year = year;
        }
    }
}
