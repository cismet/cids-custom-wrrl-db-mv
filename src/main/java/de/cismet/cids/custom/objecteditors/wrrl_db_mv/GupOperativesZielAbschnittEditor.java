/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 28.06.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.method.MethodManager;
import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.OperativeZieleComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.PflegezieleValidator;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UnterhaltungsmassnahmeValidator;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupOperativesZielAbschnittEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    PropertyChangeListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    public static final int OPERATIVES_ZIEL_UFER = 1;
    public static final int OPERATIVES_ZIEL_SOHLE = 2;
    public static final int OPERATIVES_ZIEL_UMFELD = 3;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupOperativesZielAbschnittEditor.class);
    private static final MetaClass PFLEGEZIEL_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_operatives_ziel");
    private static final String ENTWICKLUNGSZIEL_TEMPLATE =
        "exists (select 1 from GUP_OPERATIVES_ZIEL_GUP_ENTWICKLUNGSZIEL_NAME ent where gup_operatives_ziel_reference = p.id and ent.entwicklungsziel = %s)";
    private static final String SITUATIONSTYP_TEMPLATE =
        "exists (select 1 from GUP_OPERATIVES_ZIEL_GUP_UNTERHALTUNGSERFORDERNIS_NAME ue where gup_operatives_ziel_reference = p.id and ue.unterhaltungserfordernis = %s)";

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private List<CidsBean> others;
    private PflegezieleValidator validator;
    private boolean readOnly;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jscEval;
    private javax.swing.JButton lblSearch;
    private javax.swing.JLabel lblValid;
    private javax.swing.JLabel lblValidLab;
    private javax.swing.JLabel lblZiel;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JTextArea textEval;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupOperativesZielAbschnittEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupOperativesZielAbschnittEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer(true)
                                                : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");
        initComponents();

        if (!readOnly) {
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
            linearReferencedLineEditor.setOtherLinesEnabled(true);
            linearReferencedLineEditor.setOtherLinesQueryAddition(
                "gup_operatives_ziel_abschnitt",
                "gup_operatives_ziel_abschnitt.linie = ");
            linearReferencedLineEditor.setShowOtherInDialog(true);
        } else {
            RendererTools.makeReadOnly(cbName);
        }
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblZiel = new javax.swing.JLabel();
        cbName = new OperativeZieleComboBox();
        lblSearch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        linearReferencedLineEditor = linearReferencedLineEditor;
        jPanel3 = new javax.swing.JPanel();
        lblValidLab = new javax.swing.JLabel();
        lblValid = new javax.swing.JLabel();
        jscEval = new javax.swing.JScrollPane();
        textEval = new javax.swing.JTextArea();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 400));
        setLayout(new java.awt.GridBagLayout());

        lblZiel.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielAbschnittEditor.class,
                "GupOperativesZielAbschnittEditor.lblZiel.text")); // NOI18N
        lblZiel.setMaximumSize(new java.awt.Dimension(170, 17));
        lblZiel.setMinimumSize(new java.awt.Dimension(170, 17));
        lblZiel.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 35, 5, 5);
        add(lblZiel, gridBagConstraints);

        cbName.setMaximumSize(new java.awt.Dimension(420, 20));
        cbName.setMinimumSize(new java.awt.Dimension(420, 20));
        cbName.setPreferredSize(new java.awt.Dimension(520, 20));

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.operatives_ziel}"),
                cbName,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        add(cbName, gridBagConstraints);

        lblSearch.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielAbschnittEditor.class,
                "GupOperativesZielAbschnittEditor.lblSearch.text",
                new Object[] {})); // NOI18N
        lblSearch.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    lblSearchActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 35);
        add(lblSearch, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 35, 10, 10);
        jPanel2.add(linearReferencedLineEditor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblValidLab.setFont(new java.awt.Font("Ubuntu", 1, 15));       // NOI18N
        lblValidLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValidLab.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielAbschnittEditor.class,
                "GupOperativesZielAbschnittEditor.lblValidLab.text")); // NOI18N
        lblValidLab.setPreferredSize(new java.awt.Dimension(210, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(lblValidLab, gridBagConstraints);

        lblValid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValid.setMaximumSize(new java.awt.Dimension(128, 128));
        lblValid.setMinimumSize(new java.awt.Dimension(64, 64));
        lblValid.setPreferredSize(new java.awt.Dimension(64, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.001;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel3.add(lblValid, gridBagConstraints);

        jscEval.setMaximumSize(new java.awt.Dimension(235, 100));
        jscEval.setMinimumSize(new java.awt.Dimension(235, 100));
        jscEval.setPreferredSize(new java.awt.Dimension(235, 100));

        textEval.setColumns(18);
        textEval.setRows(3);
        jscEval.setViewportView(textEval);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel3.add(jscEval, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 10, 35);
        jPanel1.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblSearchActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_lblSearchActionPerformed
        searchValidPflegeziele();
    }                                                                             //GEN-LAST:event_lblSearchActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            if (this.others != null) {
                final List<CidsBean> lineBeans = new ArrayList<CidsBean>();
                final Object id = cidsBean.getProperty("linie.id");

                for (final CidsBean b : this.others) {
                    final CidsBean tmp = (CidsBean)b.getProperty("linie");

                    if ((tmp != null) && (!tmp.getProperty("id").equals(id))) {
                        lineBeans.add(tmp);
                    }
                }
                linearReferencedLineEditor.setOtherLines(lineBeans);
            }

            linearReferencedLineEditor.setCidsBean(cidsBean);

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final Object item = cidsBean.getProperty("operatives_ziel");
                        if (item != null) {
                            cbName.setSelectedItem(item);
                        }
                    }
                }).start();

            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.addPropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM);
                final CidsBean bis = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO);

                if (von != null) {
                    von.addPropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.addPropertyChangeListener(this);
                }
            }
            cidsBean.addPropertyChangeListener(this);
            validatePflegeziel();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kompartiment  the kompartiment to set
     */
    public void setKompartiment(final int kompartiment) {
        ((OperativeZieleComboBox)cbName).setKompartiment(kompartiment);
        cbName.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
    public void unbind() {
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  others  DOCUMENT ME!
     */
    public void setOthers(final List<CidsBean> others) {
        this.others = others;
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.removePropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty("von");
                final CidsBean bis = (CidsBean)line.getProperty("bis");

                if (von != null) {
                    von.removePropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.removePropertyChangeListener(this);
                }
            }
        }
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(GupOperativesZielAbschnittEditor.class, "GupOperativesZielAbschnittEditor.getTitle");
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    /**
     * DOCUMENT ME!
     */
    private void validatePflegeziel() {
        lblValid.setVisible(false);
        // validiere Maßnahme
        CismetThreadPool.execute(new SwingWorker<PflegezieleValidator.ValidationResult, Void>() {

                List<String> errors = new ArrayList<String>();

                @Override
                protected PflegezieleValidator.ValidationResult doInBackground() throws Exception {
                    if (validator == null) {
                        return null;
                    } else {
                        return validator.validate(cidsBean, errors);
                    }
                }

                @Override
                protected void done() {
                    try {
                        final PflegezieleValidator.ValidationResult res = get();

                        if (res != null) {
                            lblValid.setVisible(true);
                        }
                        if (res == PflegezieleValidator.ValidationResult.ok) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok_64.png")));
                            lblValid.setToolTipText("OK");
                            textEval.setText("");
                            jscEval.setVisible(false);
                        } else if (res == PflegezieleValidator.ValidationResult.warning) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok_auflagen_64.png")));
                            lblValid.setToolTipText("");
                            textEval.setText("");
                            jscEval.setVisible(false);
                        } else if (res == PflegezieleValidator.ValidationResult.error) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/stop_64.png")));
                            final StringBuilder errorText = new StringBuilder("<html>");
                            final StringBuilder errorT = new StringBuilder("");

                            for (final String tmp : errors) {
                                errorText.append(tmp).append("<br />");
                                errorT.append(tmp).append("\n");
                            }

                            errorText.append("</html>");
                            lblValid.setToolTipText(errorText.toString());
                            textEval.setText(errorT.toString());
                            jscEval.setVisible(true);
                        }
                    } catch (Exception e) {
                        LOG.error("Exception while validating.", e);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    public void searchValidPflegeziele() {
        final WaitingDialogThread<List<Node>> t = new WaitingDialogThread<List<Node>>(StaticSwingTools.getParentFrame(
                    this),
                true,
                "Lade gültige Pflegeziele",
                null,
                100) {

                @Override
                protected List<Node> doInBackground() throws Exception {
                    final MetaObject[] mo = getValidPflegezielObjects();
                    final List<Node> validPflegezielList = new ArrayList<Node>();

                    for (final MetaObject tmp : mo) {
                        validPflegezielList.add(new MetaObjectNode(tmp.getBean()));
                    }

                    return validPflegezielList;
                }

                @Override
                protected void done() {
                    try {
                        final List<Node> validPflegezielList = get();
                        if (validPflegezielList.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                GupOperativesZielAbschnittEditor.this,
                                "Es wurden keine validen Pflegeziele gefunden",
                                "Keine validen Pflegeziele gefunden",
                                JOptionPane.INFORMATION_MESSAGE);
                        }

                        MethodManager.getManager()
                                .showSearchResults(
                                    null,
                                    validPflegezielList.toArray(
                                        new Node[validPflegezielList.size()]),
                                    false);
                        MethodManager.getManager().showSearchResults();
                    } catch (Exception e) {
                        LOG.error("Error while retrieving Pflegeziel objects.", e);
                    }
                }
            };

        t.start();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ConnectionException  DOCUMENT ME!
     */
    private MetaObject[] getValidPflegezielObjects() throws ConnectionException {
        final StringBuilder newQuery = new StringBuilder("select distinct " + PFLEGEZIEL_MC.getID() + ","
                        + PFLEGEZIEL_MC.getPrimaryKey()
                        + " from " + PFLEGEZIEL_MC.getTableName() + " p ");

        final List<CidsBean> entwicklungszielList = validator.getEntwicklungszielIntersectingPflegezielAbschnitt(
                cidsBean);
        final List<CidsBean> situationstypList = validator.getSituationstypIntersectingPflegezielAbschnitt(cidsBean);
        boolean firstCondition = true;

        if ((entwicklungszielList != null) && !entwicklungszielList.isEmpty()) {
            for (final CidsBean entBean : entwicklungszielList) {
                if (firstCondition) {
                    newQuery.append(" WHERE ");
                    firstCondition = false;
                } else {
                    newQuery.append(" AND ");
                }
                newQuery.append(String.format(ENTWICKLUNGSZIEL_TEMPLATE, String.valueOf(entBean.getProperty("id"))));
            }
        }

        if ((situationstypList != null) && !situationstypList.isEmpty()) {
            for (final CidsBean sitBean : situationstypList) {
                if (firstCondition) {
                    newQuery.append(" WHERE ");
                    firstCondition = false;
                } else {
                    newQuery.append(" AND ");
                }
                newQuery.append(String.format(SITUATIONSTYP_TEMPLATE, String.valueOf(sitBean.getProperty("id"))));
            }
        }

        return SessionManager.getProxy()
                    .getMetaObjectByQuery(SessionManager.getSession().getUser(), newQuery.toString());
    }

    @Override
    public boolean prepareForSave() {
        return linearReferencedLineEditor.prepareForSave();
    }
    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "x",
            "gup_operatives_ziel",
            1,
            1280,
            1024);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the validator
     */
    public PflegezieleValidator getValidator() {
        return validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validator  the validator to set
     */
    public void setValidator(final PflegezieleValidator validator) {
        this.validator = validator;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("massnahme")) {
            validatePflegeziel();
        }
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (readOnly) {
            return;
        }

        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_operatives_ziel")) { // NOI18N
                    try {
                        cidsBean.setProperty("operatives_ziel", bean.getMetaObject());
                        validatePflegeziel();
                    } catch (Exception ex) {
                        LOG.error("Error while setting new operatives ziel.", ex);
                    }
                }
            }
        }
    }
}
