/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.JXTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeSupportingComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.YesNoConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ChemieMstMessungenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ChemieMstMessungenEditor.class);
    private static final String[][] STOFF_HEADER = {
            { "Stoffname", "substance_code.name_de" }, // NOI18N
            { "Messwert", "messwert" },                // NOI18N
            { "Einheit", "einheit" },                  // NOI18N
            { "Grenzwert", "grenzwert" },              // NOI18N
            { "UQN-Art", "uqn_type.value" },           // NOI18N
            { "Jahr", "jahr_messung" }                 // NOI18N
        };

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbChemZust;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbDiffStInklUbiqSchUN;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFlussspez;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne chemieMstMessungenPanOne1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblChemZustGes;
    private javax.swing.JLabel lblDiffStInklUbiqSchUN;
    private javax.swing.JLabel lblFlussspez;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblUQN6;
    private javax.swing.JLabel lblUQN8;
    private javax.swing.JPanel panAnl6;
    private javax.swing.JPanel panAnl8;
    private javax.swing.JPanel panFooter;
    private javax.swing.JTextField txtChemZustBem;
    private javax.swing.JTextField txtDiffStInklUbiqSchUN;
    private javax.swing.JTextField txtFlussspez;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public ChemieMstMessungenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public ChemieMstMessungenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        RendererTools.makeReadOnly(cbChemZust);
        RendererTools.makeReadOnly(cbDiffStInklUbiqSchUN);
        RendererTools.makeReadOnly(cbFlussspez);

        txtChemZustBem.setEditable(false);
        txtDiffStInklUbiqSchUN.setEditable(false);
        txtFlussspez.setEditable(false);
        clearForm();
        jPanel4.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isMouseOver(e)) {
                        try {
                            BrowserLauncher.openURL(
                                "https://www.gesetze-im-internet.de/ogewv_2016/anlage_7.html");
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

        lblDiffStInklUbiqSchUN.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isMouseOver(e)) {
                        try {
                            BrowserLauncher.openURL(
                                "https://www.gesetze-im-internet.de/ogewv_2016/anlage_8.html");
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                    if (!isHandCursor && isMouseOver(e)) {
                        lblDiffStInklUbiqSchUN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        isHandCursor = true;
                    } else if (isHandCursor) {
                        lblDiffStInklUbiqSchUN.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    if (isHandCursor) {
                        lblDiffStInklUbiqSchUN.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                private boolean isMouseOver(final MouseEvent e) {
                    return ((e.getPoint().x > 0) && (e.getPoint().x < 285) && (e.getPoint().y < 18));
                }
            });
        txtChemZustBem.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    final String chemGesBem = (String)cidsBean.getProperty("chem_stat_bemerkung");

                    if ((chemGesBem != null) && chemGesBem.startsWith("http")) {
                        try {
                            BrowserLauncher.openURL(chemGesBem);
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }
            });
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

            if ((parent != null) && !readOnly) {
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            cidsBean.addPropertyChangeListener(new SubObjectPropertyChangedListener(parent));
                        }
                    });
            }

            final String chemGesBem = (String)cidsBean.getProperty("chem_stat_bemerkung");

            if ((chemGesBem != null) && chemGesBem.startsWith("http")) {
                txtChemZustBem.setForeground(new Color(28, 72, 227));
            } else {
                txtChemZustBem.setForeground(new Color(93, 93, 93));
            }

            if (cidsBean.getProperty("messstelle") instanceof CidsBean) {
                refreshUQN((CidsBean)cidsBean.getProperty("messstelle"));
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

        chemieMstMessungenPanOne1.setCidsBean(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  messstelle  DOCUMENT ME!
     */
    public void refreshUQN(final CidsBean messstelle) {
        final Thread t = new Thread() {

                @Override
                public void run() {
                    final List<CidsBean> stoffList = getChemMst(messstelle);
                    final List<CidsBean> anl6 = new ArrayList<>();
                    final List<CidsBean> anl8 = new ArrayList<>();

                    for (final CidsBean stoff : stoffList) {
                        final Boolean anl6Val = (Boolean)stoff.getProperty("substance_code.anl_6");
                        final Boolean anl8Val = (Boolean)stoff.getProperty("substance_code.anl_8");

                        if ((anl6Val != null) && anl6Val) {
                            anl6.add(stoff);
                        } else if ((anl8Val != null) && anl8Val) {
                            anl8.add(stoff);
                        }
                    }

                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                panAnl6.removeAll();
                                panAnl8.removeAll();

                                if ((anl6 == null) || anl6.isEmpty()) {
                                    final JLabel lblUQN6Val = new JLabel();
                                    lblUQN6Val.setMinimumSize(new java.awt.Dimension(380, 20));
                                    lblUQN6Val.setPreferredSize(new java.awt.Dimension(380, 20));
                                    lblUQN6Val.setText("keine UQN Überschreitungen");
                                    panAnl6.add(lblUQN6Val, BorderLayout.CENTER);
                                } else {
                                    final JXTable tab = new WkFgPanThirteen.UQNTable(anl6, STOFF_HEADER);
                                    final JScrollPane scrollPane = new JScrollPane(tab);
                                    scrollPane.setMinimumSize(new java.awt.Dimension(580, 100));
                                    scrollPane.setPreferredSize(new java.awt.Dimension(580, 100));
                                    scrollPane.setOpaque(false);
                                    panAnl6.add(scrollPane, BorderLayout.CENTER);
                                }

                                if ((anl8 == null) || anl8.isEmpty()) {
                                    final JLabel lblUQN8Val = new JLabel();
                                    lblUQN8Val.setMinimumSize(new java.awt.Dimension(380, 20));
                                    lblUQN8Val.setPreferredSize(new java.awt.Dimension(380, 20));
                                    lblUQN8Val.setText("keine UQN Überschreitungen");
                                    panAnl8.add(lblUQN8Val, BorderLayout.CENTER);
                                } else {
                                    final JXTable tab = new WkFgPanThirteen.UQNTable(anl8, STOFF_HEADER);
                                    final JScrollPane scrollPane = new JScrollPane(tab);
                                    scrollPane.setMinimumSize(new java.awt.Dimension(580, 100));
                                    scrollPane.setPreferredSize(new java.awt.Dimension(580, 100));
                                    scrollPane.setOpaque(false);
                                    panAnl8.add(scrollPane, BorderLayout.CENTER);
                                }
                            }
                        });
                }
            };

        t.start();
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        lblFoot.setText("");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void setEnable(final boolean enable) {
        EventQueue.invokeLater(new Thread("Enable editor") {

                @Override
                public void run() {
                    chemieMstMessungenPanOne1.setEnable(enable);
                }
            });
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
        chemieMstMessungenPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne();
        lblChemZustGes = new javax.swing.JLabel();
        lblDiffStInklUbiqSchUN = new javax.swing.JLabel();
        lblUQN6 = new javax.swing.JLabel();
        lblFlussspez = new javax.swing.JLabel();
        lblUQN8 = new javax.swing.JLabel();
        cbChemZust = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        txtChemZustBem = new javax.swing.JTextField();
        cbDiffStInklUbiqSchUN = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        txtDiffStInklUbiqSchUN = new javax.swing.JTextField();
        cbFlussspez = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        txtFlussspez = new javax.swing.JTextField();
        panAnl8 = new javax.swing.JPanel();
        panAnl6 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(910, 1250));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 1250));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                org.openide.util.NbBundle.getMessage(
                    ChemieMstMessungenEditor.class,
                    "ChemieMstMessungenEditor.jPanel4.border.title",
                    new Object[] {}),
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Dialog", 0, 12),
                new java.awt.Color(28, 72, 227))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel4.add(chemieMstMessungenPanOne1, gridBagConstraints);

        lblChemZustGes.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblChemZustGes.text_1",
                new Object[] {})); // NOI18N
        lblChemZustGes.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblChemZustGes.toolTipText",
                new Object[] {})); // NOI18N
        lblChemZustGes.setMinimumSize(new java.awt.Dimension(420, 20));
        lblChemZustGes.setPreferredSize(new java.awt.Dimension(420, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblChemZustGes, gridBagConstraints);

        lblDiffStInklUbiqSchUN.setForeground(new java.awt.Color(28, 72, 227));
        lblDiffStInklUbiqSchUN.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblDiffStInklUbiqSchUN.text",
                new Object[] {})); // NOI18N
        lblDiffStInklUbiqSchUN.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblDiffStInklUbiqSchUN.toolTipText",
                new Object[] {})); // NOI18N
        lblDiffStInklUbiqSchUN.setMinimumSize(new java.awt.Dimension(420, 20));
        lblDiffStInklUbiqSchUN.setPreferredSize(new java.awt.Dimension(420, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDiffStInklUbiqSchUN, gridBagConstraints);

        lblUQN6.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblUQN6.text",
                new Object[] {}));         // NOI18N
        lblUQN6.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblUQN6.toolTipText",
                new Object[] {}));         // NOI18N
        lblUQN6.setMinimumSize(new java.awt.Dimension(420, 20));
        lblUQN6.setPreferredSize(new java.awt.Dimension(420, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUQN6, gridBagConstraints);
        lblUQN6.getAccessibleContext()
                .setAccessibleName(org.openide.util.NbBundle.getMessage(
                        ChemieMstMessungenEditor.class,
                        "ChemieMstMessungenEditor.lblUQN6.AccessibleContext.accessibleName",
                        new Object[] {})); // NOI18N
        lblUQN6.getAccessibleContext()
                .setAccessibleDescription(org.openide.util.NbBundle.getMessage(
                        ChemieMstMessungenEditor.class,
                        "ChemieMstMessungenEditor.lblUQN6.AccessibleContext.accessibleDescription",
                        new Object[] {})); // NOI18N

        lblFlussspez.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblFlussspez.text",
                new Object[] {})); // NOI18N
        lblFlussspez.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblFlussspez.toolTipText",
                new Object[] {})); // NOI18N
        lblFlussspez.setMinimumSize(new java.awt.Dimension(420, 20));
        lblFlussspez.setPreferredSize(new java.awt.Dimension(420, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblFlussspez, gridBagConstraints);

        lblUQN8.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "ChemieMstMessungenEditor.lblUQN8.text",
                new Object[] {}));         // NOI18N
        lblUQN8.setMinimumSize(new java.awt.Dimension(420, 20));
        lblUQN8.setPreferredSize(new java.awt.Dimension(420, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUQN8, gridBagConstraints);
        lblUQN8.getAccessibleContext()
                .setAccessibleName(org.openide.util.NbBundle.getMessage(
                        ChemieMstMessungenEditor.class,
                        "ChemieMstMessungenEditor.lblUQN8.AccessibleContext.accessibleName",
                        new Object[] {})); // NOI18N
        lblUQN8.getAccessibleContext()
                .setAccessibleDescription(org.openide.util.NbBundle.getMessage(
                        ChemieMstMessungenEditor.class,
                        "ChemieMstMessungenEditor.lblUQN8.AccessibleContext.accessibleDescription",
                        new Object[] {})); // NOI18N

        cbChemZust.setMaximumSize(new java.awt.Dimension(225, 20));
        cbChemZust.setMinimumSize(new java.awt.Dimension(225, 20));
        cbChemZust.setPreferredSize(new java.awt.Dimension(225, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat}"),
                cbChemZust,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbChemZust.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbChemZustActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbChemZust, gridBagConstraints);

        txtChemZustBem.setMinimumSize(new java.awt.Dimension(100, 20));
        txtChemZustBem.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat_bemerkung}"),
                txtChemZustBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtChemZustBem, gridBagConstraints);

        cbDiffStInklUbiqSchUN.setMaximumSize(new java.awt.Dimension(225, 20));
        cbDiffStInklUbiqSchUN.setMinimumSize(new java.awt.Dimension(225, 20));
        cbDiffStInklUbiqSchUN.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.propristoffeohneubiqstoffe}"),
                cbDiffStInklUbiqSchUN,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbDiffStInklUbiqSchUN.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbDiffStInklUbiqSchUNActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbDiffStInklUbiqSchUN, gridBagConstraints);

        txtDiffStInklUbiqSchUN.setMinimumSize(new java.awt.Dimension(100, 20));
        txtDiffStInklUbiqSchUN.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.propristoffeohneubiqstoffebemerkung}"),
                txtDiffStInklUbiqSchUN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtDiffStInklUbiqSchUN, gridBagConstraints);

        cbFlussspez.setMaximumSize(new java.awt.Dimension(225, 20));
        cbFlussspez.setMinimumSize(new java.awt.Dimension(225, 20));
        cbFlussspez.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.flussgebietsspezifische_schadstoffe}"),
                cbFlussspez,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbFlussspez.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbFlussspezActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbFlussspez, gridBagConstraints);

        txtFlussspez.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFlussspez.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.flussgebietsspezifische_schadstoffe_bem}"),
                txtFlussspez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtFlussspez, gridBagConstraints);

        panAnl8.setOpaque(false);
        panAnl8.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panAnl8, gridBagConstraints);

        panAnl6.setOpaque(false);
        panAnl6.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panAnl6, gridBagConstraints);

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
    private void cbChemZustActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbChemZustActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbChemZustActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbDiffStInklUbiqSchUNActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbDiffStInklUbiqSchUNActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbDiffStInklUbiqSchUNActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbFlussspezActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbFlussspezActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbFlussspezActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  mit              DOCUMENT ME!
     * @param  oD               DOCUMENT ME!
     * @param  reverseColors    DOCUMENT ME!
     * @param  foregroundColor  DOCUMENT ME!
     */
    public static void setColorOfField(final JTextField mit,
            final Number oD,
            final boolean reverseColors,
            final Color foregroundColor) {
        mit.setDisabledTextColor(new Color(139, 142, 143));
        mit.setBackground(new Color(245, 246, 247));

        if ((mit.getText() == null) || mit.getText().equals("") || mit.getText().equals("<nicht gesetzt>")) {
            mit.setBackground(new Color(245, 246, 247));
            return;
        }

        if (oD == null) {
            mit.setBackground(new Color(245, 246, 247));
            return;
        }

        try {
            final double mitD = Double.parseDouble(mit.getText().replace(",", "."));

            if (reverseColors) {
                mit.setBackground(calcColorReverse(mitD, oD.doubleValue()));
            } else {
                mit.setBackground(calcColor(mitD, oD.doubleValue()));
            }

            if (mit.getBackground().equals(Color.RED)) {
                mit.setDisabledTextColor(new Color(255, 255, 255));
//                mit.setForeground(new Color(0, 0, 255));
            }
//            mit.setOpaque(true);
//            mit.repaint();
        } catch (NumberFormatException e) {
            mit.setOpaque(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mittel  DOCUMENT ME!
     * @param   o       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color calcColor(final double mittel, final double o) {
        if (mittel <= o) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mittel  DOCUMENT ME!
     * @param   o       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color calcColorReverse(final double mittel, final double o) {
        if (mittel >= o) {
            return Color.GREEN;
        } else {
            return Color.RED;
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
     * @param   messstelle  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<CidsBean> getChemMst(final CidsBean messstelle) {
        final List<CidsBean> data = new ArrayList<CidsBean>();

        try {
            final MetaClass MC = ClassCacheMultiple.getMetaClass(
                    WRRLUtil.DOMAIN_NAME,
                    "chemie_mst_stoff");
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m";                                                                                      // NOI18N
            query += " WHERE mst = " + messstelle.getProperty("id");
            final MetaObject[] metaObjects = MetaObjectCache.getInstance()
                        .getMetaObjectsByQuery(query, MC, false, WkFgEditor.CONNECTION_CONTEXT);

            for (final MetaObject mo : metaObjects) {
                data.add(mo.getBean());
            }
        } catch (final CacheException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }

        return data;
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
}
