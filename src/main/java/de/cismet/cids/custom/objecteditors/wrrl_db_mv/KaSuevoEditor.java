/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * BioMstMessungenEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.jdesktop.beansbinding.Converter;

import java.awt.Color;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
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
public class KaSuevoEditor extends JPanel implements CidsBeanRenderer, EditorSaveListener, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KaSuevoEditor.class);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblABsbfracht;
    private javax.swing.JLabel lblABsbjm;
    private javax.swing.JLabel lblACsbfracht;
    private javax.swing.JLabel lblACsbjm;
    private javax.swing.JLabel lblAMenge;
    private javax.swing.JLabel lblANfracht;
    private javax.swing.JLabel lblANjm;
    private javax.swing.JLabel lblAPfracht;
    private javax.swing.JLabel lblAPjm;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblSuevoAblaufGesamtN;
    private javax.swing.JLabel lblSuevoAblaufGesamtnFracht;
    private javax.swing.JLabel lblSuevoAblaufNh4n;
    private javax.swing.JLabel lblSuevoAblaufNh4nFracht;
    private javax.swing.JLabel lblSuevoZulaufGesamtn;
    private javax.swing.JLabel lblSuevoZulaufGesamtnFracht;
    private javax.swing.JLabel lblSuevoZulaufNh4n;
    private javax.swing.JLabel lblSuevoZulaufNh4nFracht;
    private javax.swing.JLabel lblZBsbfracht;
    private javax.swing.JLabel lblZBsbjm;
    private javax.swing.JLabel lblZCsbfracht;
    private javax.swing.JLabel lblZCsbjm;
    private javax.swing.JLabel lblZMenge;
    private javax.swing.JLabel lblZNfracht;
    private javax.swing.JLabel lblZNjm;
    private javax.swing.JLabel lblZPfracht;
    private javax.swing.JLabel lblZPjm;
    private javax.swing.JPanel panFooter;
    private javax.swing.JTextField txtABsbfracht;
    private javax.swing.JTextField txtABsbjm;
    private javax.swing.JTextField txtACsbfracht;
    private javax.swing.JTextField txtACsbjm;
    private javax.swing.JTextField txtAMenge;
    private javax.swing.JTextField txtANfracht;
    private javax.swing.JTextField txtANjm;
    private javax.swing.JTextField txtAPfracht;
    private javax.swing.JTextField txtAPjm;
    private javax.swing.JTextField txtSuevoAblaufGesamtN;
    private javax.swing.JTextField txtSuevoAblaufGesamtnFracht;
    private javax.swing.JTextField txtSuevoAblaufNh4n;
    private javax.swing.JTextField txtSuevoAblaufNh4nFracht;
    private javax.swing.JTextField txtSuevoZulaufGesamtn;
    private javax.swing.JTextField txtSuevoZulaufGesamtnFracht;
    private javax.swing.JTextField txtSuevoZulaufNh4n;
    private javax.swing.JTextField txtSuevoZulaufNh4nFracht;
    private javax.swing.JTextField txtZBsbfracht;
    private javax.swing.JTextField txtZBsbjm;
    private javax.swing.JTextField txtZCsbfracht;
    private javax.swing.JTextField txtZCsbjm;
    private javax.swing.JTextField txtZMenge;
    private javax.swing.JTextField txtZNfracht;
    private javax.swing.JTextField txtZNjm;
    private javax.swing.JTextField txtZPfracht;
    private javax.swing.JTextField txtZPjm;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public KaSuevoEditor() {
        this(true);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public KaSuevoEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        setCidsBean(cidsBean, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     * @param  parent    DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean, final CidsBean parent) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            if (!readOnly) {
                setEnable(true);
            }
            bindingGroup.bind();

            if (parent != null) {
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            cidsBean.addPropertyChangeListener(new SubObjectPropertyChangedListener(parent));
                        }
                    });
            }
        } else {
            if (!readOnly) {
                setEnable(false);
            }
            clearForm();
        }

        if (readOnly) {
            setEnable(false);
        }
        lblFoot.setText("");

        setFieldColor(txtACsbjm, "a_csbjm", "csb_ew");
        setFieldColor(txtABsbjm, "a_bsbjm", "bsb_ew");
        setFieldColor(txtANjm, "a_njm", "n_ew");
        setFieldColor(txtAPjm, "a_pjm", "p_ew");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  txtField  DOCUMENT ME!
     * @param  attr      DOCUMENT ME!
     * @param  ewAttr    DOCUMENT ME!
     */
    private void setFieldColor(final JTextField txtField, final String attr, final String ewAttr) {
        if ((cidsBean != null) && (cidsBean.getProperty(attr) instanceof Number)
                    && (cidsBean.getProperty(ewAttr) instanceof Number)) {
            if (((Number)cidsBean.getProperty(attr)).doubleValue()
                        <= ((Number)cidsBean.getProperty(ewAttr)).doubleValue()) {
                txtField.setBackground(Color.GREEN);
                txtField.setOpaque(true);
            } else {
                txtField.setBackground(Color.RED);
                txtField.setOpaque(true);
            }
        } else {
            txtField.setOpaque(false);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        txtABsbjm.setText("");
        txtACsbjm.setText("");
        txtAMenge.setText("");
        txtANjm.setText("");
        txtAPjm.setText("");
        txtSuevoAblaufGesamtN.setText("");
        txtSuevoAblaufNh4n.setText("");
        txtSuevoZulaufGesamtn.setText("");
        txtSuevoZulaufNh4n.setText("");
        txtZBsbjm.setText("");
        txtZCsbjm.setText("");
        txtZMenge.setText("");
        txtZCsbfracht.setText("");
        txtANfracht.setText("");
        txtSuevoAblaufNh4nFracht.setText("");
        txtAPfracht.setText("");
        txtZBsbfracht.setText("");
        txtSuevoZulaufGesamtnFracht.setText("");
        txtZNfracht.setText("");
        txtSuevoZulaufNh4nFracht.setText("");
        txtZPfracht.setText("");
        txtACsbfracht.setText("");
        txtABsbfracht.setText("");
        txtSuevoAblaufGesamtnFracht.setText("");
        txtZNjm.setText("");
        txtZPjm.setText("");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void setEnable(final boolean enable) {
        if (!enable) {
            RendererTools.makeReadOnly(txtABsbjm);
            RendererTools.makeReadOnly(txtACsbjm);
            RendererTools.makeReadOnly(txtAMenge);
            RendererTools.makeReadOnly(txtANjm);
            RendererTools.makeReadOnly(txtAPjm);
            RendererTools.makeReadOnly(txtSuevoAblaufGesamtN);
            RendererTools.makeReadOnly(txtSuevoAblaufNh4n);
            RendererTools.makeReadOnly(txtSuevoZulaufGesamtn);
            RendererTools.makeReadOnly(txtSuevoZulaufNh4n);
            RendererTools.makeReadOnly(txtZBsbjm);
            RendererTools.makeReadOnly(txtZCsbjm);
            RendererTools.makeReadOnly(txtZMenge);
            RendererTools.makeReadOnly(txtZCsbfracht);
            RendererTools.makeReadOnly(txtANfracht);
            RendererTools.makeReadOnly(txtSuevoAblaufNh4nFracht);
            RendererTools.makeReadOnly(txtAPfracht);
            RendererTools.makeReadOnly(txtZBsbfracht);
            RendererTools.makeReadOnly(txtSuevoZulaufGesamtnFracht);
            RendererTools.makeReadOnly(txtZNfracht);
            RendererTools.makeReadOnly(txtSuevoZulaufNh4nFracht);
            RendererTools.makeReadOnly(txtZPfracht);
            RendererTools.makeReadOnly(txtACsbfracht);
            RendererTools.makeReadOnly(txtABsbfracht);
            RendererTools.makeReadOnly(txtSuevoAblaufGesamtnFracht);
            RendererTools.makeReadOnly(txtZNjm);
            RendererTools.makeReadOnly(txtZPjm);
        } else {
            RendererTools.makeWritable(txtABsbjm);
            RendererTools.makeWritable(txtACsbjm);
            RendererTools.makeWritable(txtAMenge);
            RendererTools.makeWritable(txtANjm);
            RendererTools.makeWritable(txtAPjm);
            RendererTools.makeWritable(txtSuevoAblaufGesamtN);
            RendererTools.makeWritable(txtSuevoAblaufNh4n);
            RendererTools.makeWritable(txtSuevoZulaufGesamtn);
            RendererTools.makeWritable(txtSuevoZulaufNh4n);
            RendererTools.makeWritable(txtZBsbjm);
            RendererTools.makeWritable(txtZCsbjm);
            RendererTools.makeWritable(txtZMenge);
            RendererTools.makeWritable(txtZCsbfracht);
            RendererTools.makeWritable(txtANfracht);
            RendererTools.makeWritable(txtSuevoAblaufNh4nFracht);
            RendererTools.makeWritable(txtAPfracht);
            RendererTools.makeWritable(txtZBsbfracht);
            RendererTools.makeWritable(txtSuevoZulaufGesamtnFracht);
            RendererTools.makeWritable(txtZNfracht);
            RendererTools.makeWritable(txtSuevoZulaufNh4nFracht);
            RendererTools.makeWritable(txtZPfracht);
            RendererTools.makeWritable(txtACsbfracht);
            RendererTools.makeWritable(txtABsbfracht);
            RendererTools.makeWritable(txtSuevoAblaufGesamtnFracht);
            RendererTools.makeWritable(txtZNjm);
            RendererTools.makeWritable(txtZPjm);
        }
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
        jPanel4 = new javax.swing.JPanel();
        lblZMenge = new javax.swing.JLabel();
        lblZCsbfracht = new javax.swing.JLabel();
        lblZCsbjm = new javax.swing.JLabel();
        lblZBsbjm = new javax.swing.JLabel();
        lblSuevoZulaufGesamtn = new javax.swing.JLabel();
        lblSuevoZulaufNh4n = new javax.swing.JLabel();
        lblZBsbfracht = new javax.swing.JLabel();
        lblSuevoZulaufGesamtnFracht = new javax.swing.JLabel();
        lblZNfracht = new javax.swing.JLabel();
        lblSuevoZulaufNh4nFracht = new javax.swing.JLabel();
        lblZPfracht = new javax.swing.JLabel();
        lblAMenge = new javax.swing.JLabel();
        lblACsbjm = new javax.swing.JLabel();
        lblACsbfracht = new javax.swing.JLabel();
        lblZNjm = new javax.swing.JLabel();
        lblZPjm = new javax.swing.JLabel();
        lblABsbjm = new javax.swing.JLabel();
        lblSuevoAblaufGesamtN = new javax.swing.JLabel();
        lblANjm = new javax.swing.JLabel();
        lblSuevoAblaufNh4n = new javax.swing.JLabel();
        lblAPjm = new javax.swing.JLabel();
        lblABsbfracht = new javax.swing.JLabel();
        lblSuevoAblaufGesamtnFracht = new javax.swing.JLabel();
        lblANfracht = new javax.swing.JLabel();
        lblSuevoAblaufNh4nFracht = new javax.swing.JLabel();
        lblAPfracht = new javax.swing.JLabel();
        txtZMenge = new javax.swing.JTextField();
        txtZCsbjm = new javax.swing.JTextField();
        txtZBsbjm = new javax.swing.JTextField();
        txtSuevoZulaufGesamtn = new javax.swing.JTextField();
        txtZNjm = new javax.swing.JTextField();
        txtSuevoZulaufNh4n = new javax.swing.JTextField();
        txtZPjm = new javax.swing.JTextField();
        txtAMenge = new javax.swing.JTextField();
        txtACsbjm = new javax.swing.JTextField();
        txtABsbjm = new javax.swing.JTextField();
        txtSuevoAblaufGesamtN = new javax.swing.JTextField();
        txtANjm = new javax.swing.JTextField();
        txtSuevoAblaufNh4n = new javax.swing.JTextField();
        txtAPjm = new javax.swing.JTextField();
        txtZCsbfracht = new javax.swing.JTextField();
        txtZBsbfracht = new javax.swing.JTextField();
        txtSuevoZulaufGesamtnFracht = new javax.swing.JTextField();
        txtZNfracht = new javax.swing.JTextField();
        txtSuevoZulaufNh4nFracht = new javax.swing.JTextField();
        txtZPfracht = new javax.swing.JTextField();
        txtACsbfracht = new javax.swing.JTextField();
        txtABsbfracht = new javax.swing.JTextField();
        txtSuevoAblaufGesamtnFracht = new javax.swing.JTextField();
        txtANfracht = new javax.swing.JTextField();
        txtSuevoAblaufNh4nFracht = new javax.swing.JTextField();
        txtAPfracht = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                org.openide.util.NbBundle.getMessage(
                    KaSuevoEditor.class,
                    "KaSuevoEditor.jPanel4.border.title",
                    new Object[] {}))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblZMenge.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblZMenge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblZMenge, gridBagConstraints);

        lblZCsbfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblZCsbfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblZCsbfracht, gridBagConstraints);

        lblZCsbjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblZCsbjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblZCsbjm, gridBagConstraints);

        lblZBsbjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblZBsbjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblZBsbjm, gridBagConstraints);

        lblSuevoZulaufGesamtn.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoZulaufGesamtn.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSuevoZulaufGesamtn, gridBagConstraints);

        lblSuevoZulaufNh4n.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoZulaufNh4n.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblSuevoZulaufNh4n, gridBagConstraints);

        lblZBsbfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblZBsbfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblZBsbfracht, gridBagConstraints);

        lblSuevoZulaufGesamtnFracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoZulaufGesamtnFracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblSuevoZulaufGesamtnFracht, gridBagConstraints);

        lblZNfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblZNfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanel4.add(lblZNfracht, gridBagConstraints);

        lblSuevoZulaufNh4nFracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoZulaufNh4nFracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanel4.add(lblSuevoZulaufNh4nFracht, gridBagConstraints);

        lblZPfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblZPfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblZPfracht, gridBagConstraints);

        lblAMenge.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaAnlageEditor.lblAMenge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAMenge, gridBagConstraints);

        lblACsbjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaAnlageEditor.lblACsbjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblACsbjm, gridBagConstraints);

        lblACsbfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblACsbfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblACsbfracht, gridBagConstraints);

        lblZNjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblZNjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblZNjm, gridBagConstraints);

        lblZPjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblZPjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblZPjm, gridBagConstraints);

        lblABsbjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaAnlageEditor.lblABsbjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblABsbjm, gridBagConstraints);

        lblSuevoAblaufGesamtN.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lbllblSuevoAblaufGesamtN.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSuevoAblaufGesamtN, gridBagConstraints);

        lblANjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblANjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblANjm, gridBagConstraints);

        lblSuevoAblaufNh4n.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoAblaufNh4n.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblSuevoAblaufNh4n, gridBagConstraints);

        lblAPjm.setText(org.openide.util.NbBundle.getMessage(KaSuevoEditor.class, "KaSuevoEditor.lblAPjm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAPjm, gridBagConstraints);

        lblABsbfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblABsbfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblABsbfracht, gridBagConstraints);

        lblSuevoAblaufGesamtnFracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoAblaufGesamtnFracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblSuevoAblaufGesamtnFracht, gridBagConstraints);

        lblANfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblANfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanel4.add(lblANfracht, gridBagConstraints);

        lblSuevoAblaufNh4nFracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblSuevoAblaufNh4nFracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanel4.add(lblSuevoAblaufNh4nFracht, gridBagConstraints);

        lblAPfracht.setText(org.openide.util.NbBundle.getMessage(
                KaSuevoEditor.class,
                "KaSuevoEditor.lblAPfracht.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel4.add(lblAPfracht, gridBagConstraints);

        txtZMenge.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZMenge.setPreferredSize(new java.awt.Dimension(150, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_menge}"),
                txtZMenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtZMenge.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZMengeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZMenge, gridBagConstraints);

        txtZCsbjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZCsbjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_csbjm}"),
                txtZCsbjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtZCsbjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZCsbjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZCsbjm, gridBagConstraints);

        txtZBsbjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZBsbjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_bsbjm}"),
                txtZBsbjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtZBsbjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZBsbjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZBsbjm, gridBagConstraints);

        txtSuevoZulaufGesamtn.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoZulaufGesamtn.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_zulauf_gesamtn}"),
                txtSuevoZulaufGesamtn,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtSuevoZulaufGesamtn.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoZulaufGesamtnActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoZulaufGesamtn, gridBagConstraints);

        txtZNjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZNjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_njm}"),
                txtZNjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtZNjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZNjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZNjm, gridBagConstraints);

        txtSuevoZulaufNh4n.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoZulaufNh4n.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_zulauf_nh4n}"),
                txtSuevoZulaufNh4n,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtSuevoZulaufNh4n.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoZulaufNh4nActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoZulaufNh4n, gridBagConstraints);

        txtZPjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZPjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_pjm}"),
                txtZPjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtZPjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZPjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZPjm, gridBagConstraints);

        txtAMenge.setMinimumSize(new java.awt.Dimension(150, 20));
        txtAMenge.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_menge}"),
                txtAMenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtAMenge.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtAMengeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtAMenge, gridBagConstraints);

        txtACsbjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtACsbjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_csbjm}"),
                txtACsbjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtACsbjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtACsbjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtACsbjm, gridBagConstraints);

        txtABsbjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtABsbjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_bsbjm}"),
                txtABsbjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtABsbjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtABsbjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtABsbjm, gridBagConstraints);

        txtSuevoAblaufGesamtN.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoAblaufGesamtN.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_ablauf_gesamtn}"),
                txtSuevoAblaufGesamtN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtSuevoAblaufGesamtN.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoAblaufGesamtNActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoAblaufGesamtN, gridBagConstraints);

        txtANjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtANjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_njm}"),
                txtANjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtANjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtANjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtANjm, gridBagConstraints);

        txtSuevoAblaufNh4n.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoAblaufNh4n.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_ablauf_nh4n}"),
                txtSuevoAblaufNh4n,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtSuevoAblaufNh4n.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoAblaufNh4nActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoAblaufNh4n, gridBagConstraints);

        txtAPjm.setMinimumSize(new java.awt.Dimension(150, 20));
        txtAPjm.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_pjm}"),
                txtAPjm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new DoubleConverter());
        bindingGroup.addBinding(binding);

        txtAPjm.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtAPjmActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtAPjm, gridBagConstraints);

        txtZCsbfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZCsbfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_csbfracht}"),
                txtZCsbfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtZCsbfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZCsbfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZCsbfracht, gridBagConstraints);

        txtZBsbfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZBsbfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_bsbfracht}"),
                txtZBsbfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtZBsbfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZBsbfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZBsbfracht, gridBagConstraints);

        txtSuevoZulaufGesamtnFracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoZulaufGesamtnFracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_zulauf_gesamtn_fracht}"),
                txtSuevoZulaufGesamtnFracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtSuevoZulaufGesamtnFracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoZulaufGesamtnFrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoZulaufGesamtnFracht, gridBagConstraints);

        txtZNfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZNfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_nfracht}"),
                txtZNfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtZNfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZNfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZNfracht, gridBagConstraints);

        txtSuevoZulaufNh4nFracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoZulaufNh4nFracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_zulauf_nh4n_fracht}"),
                txtSuevoZulaufNh4nFracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtSuevoZulaufNh4nFracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoZulaufNh4nFrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoZulaufNh4nFracht, gridBagConstraints);

        txtZPfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtZPfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_pfracht}"),
                txtZPfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtZPfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZPfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtZPfracht, gridBagConstraints);

        txtACsbfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtACsbfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_csbfracht}"),
                txtACsbfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtACsbfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtACsbfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtACsbfracht, gridBagConstraints);

        txtABsbfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtABsbfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_bsbfracht}"),
                txtABsbfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtABsbfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtABsbfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtABsbfracht, gridBagConstraints);

        txtSuevoAblaufGesamtnFracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoAblaufGesamtnFracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_ablauf_gesamtn_fracht}"),
                txtSuevoAblaufGesamtnFracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtSuevoAblaufGesamtnFracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoAblaufGesamtnFrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoAblaufGesamtnFracht, gridBagConstraints);

        txtANfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtANfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_nfracht}"),
                txtANfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtANfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtANfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtANfracht, gridBagConstraints);

        txtSuevoAblaufNh4nFracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtSuevoAblaufNh4nFracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suevo_ablauf_nh4n_fracht}"),
                txtSuevoAblaufNh4nFracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtSuevoAblaufNh4nFracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSuevoAblaufNh4nFrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSuevoAblaufNh4nFracht, gridBagConstraints);

        txtAPfracht.setMinimumSize(new java.awt.Dimension(150, 20));
        txtAPfracht.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.a_pfracht}"),
                txtAPfracht,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        txtAPfracht.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtAPfrachtActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtAPfracht, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel1, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.3;
        jPanel4.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel4.add(jSeparator1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        add(jPanel4, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZMengeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZMengeActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZMengeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZCsbjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZCsbjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZCsbjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZBsbjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZBsbjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZBsbjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoZulaufGesamtnActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoZulaufGesamtnActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoZulaufGesamtnActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZNjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZNjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZNjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoZulaufNh4nActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoZulaufNh4nActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoZulaufNh4nActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZPjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZPjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZPjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtAMengeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtAMengeActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtAMengeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtACsbjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtACsbjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtACsbjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtABsbjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtABsbjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtABsbjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoAblaufGesamtNActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoAblaufGesamtNActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoAblaufGesamtNActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtANjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtANjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtANjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoAblaufNh4nActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoAblaufNh4nActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoAblaufNh4nActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtAPjmActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtAPjmActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtAPjmActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZCsbfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZCsbfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZCsbfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZBsbfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZBsbfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZBsbfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoZulaufGesamtnFrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoZulaufGesamtnFrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoZulaufGesamtnFrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZNfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZNfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZNfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoZulaufNh4nFrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoZulaufNh4nFrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoZulaufNh4nFrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZPfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZPfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtZPfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtACsbfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtACsbfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtACsbfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtABsbfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtABsbfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtABsbfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoAblaufGesamtnFrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoAblaufGesamtnFrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoAblaufGesamtnFrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtANfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtANfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtANfrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSuevoAblaufNh4nFrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSuevoAblaufNh4nFrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtSuevoAblaufNh4nFrachtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtAPfrachtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtAPfrachtActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtAPfrachtActionPerformed

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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class SubObjectPropertyChangedListener implements PropertyChangeListener {

        //~ Instance fields ----------------------------------------------------

        private final CidsBean parent;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubObjectPropertyChangedListener object.
         *
         * @param  parent  DOCUMENT ME!
         */
        public SubObjectPropertyChangedListener(final CidsBean parent) {
            this.parent = parent;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (parent != null) {
                parent.setArtificialChangeFlag(true);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class DoubleConverter extends Converter<Double, String> {

        //~ Instance fields ----------------------------------------------------

        private DecimalFormat formatter;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DoubleConverter object.
         */
        public DoubleConverter() {
            formatter = new DecimalFormat("0.########");
            formatter.setGroupingUsed(true);
            formatter.setGroupingSize(3);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final Double value) {
            if (value != null) {
                return formatter.format(value.doubleValue());
            } else {
                return null;
            }
        }

        @Override
        public Double convertReverse(final String value) {
            if (value != null) {
                try {
//                    return new BigDecimal(value);
                    return new Double(value);
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
