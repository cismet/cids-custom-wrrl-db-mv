package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author jruiz
 */
public class WkTeileEditor extends javax.swing.JPanel implements CidsBeanDropListener {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkTeileEditor.class);
    private static final Color[] COLORS = new Color[] {
        new Color(41, 86, 178), new Color(101, 156, 239), new Color(125, 189, 0), new Color(220, 246, 0), new Color(255, 91, 0)
    };

    private Collection<CidsBean> cidsBeans;
    private Collection<WkTeilEditor> wkTeilEditors = new ArrayList<WkTeilEditor>();
    private HashMap<JButton, WkTeilEditor> wkTeileMap = new HashMap<JButton, WkTeilEditor>();
    private int colorIndex = 0;
    private Collection<WkTeileEditorListener> listeners = new ArrayList<WkTeileEditorListener>();

    /** Creates new form WkTeileEditor */
    public WkTeileEditor() {
        initComponents();
        new CidsBeanDropTarget(this);
    }
    
    @Override
    public void beansDropped(ArrayList<CidsBean> beans) {
        for (CidsBean bean : beans) {
            try {
                String tableName = SessionManager.getProxy().getMetaClass(bean.getMetaObject().getClassKey()).getTableName();
                if (tableName.equals("ROUTE")) {
                    WkTeilEditor editor = WkTeilEditor.createFromRoute(bean);
                    addEditor(editor);
                    cidsBeans.add(editor.getCidsBean());
                } else if (tableName.equals("STATION_TEST")) {
                    WkTeilEditor editor = WkTeilEditor.createFromRoute((CidsBean) ((CidsBean) bean.getProperty("a")).getProperty("route"));
                    addEditor(editor);
                    cidsBeans.add(editor.getCidsBean());
                } else {
                    return;
                }
            } catch (ConnectionException ex) {
                LOG.debug("SessionManager.getProxy().getMetaClass()", ex);
            }
        }
    }

    public boolean addWkTeileEditorListener(WkTeileEditorListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(WkTeileEditorListener listener) {
        return listeners.remove(listener);
    }

    private void fireWkTeilAdded() {
        for (WkTeileEditorListener listener : listeners) {
            listener.wkTeilAdded();
        }
    }

    private void fireWkTeilRemoved() {
        for (WkTeileEditorListener listener : listeners) {
            listener.wkTeilRemoved();
        }
    }

    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    private Color getNextColor() {
        colorIndex = (colorIndex + 1) % COLORS.length;
        return COLORS[colorIndex];
    }

    public void setCidsBeans(Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(1);
        }
        
        for (CidsBean wkTeilBean : cidsBeans) {           
            WkTeilEditor wkTeilEditor = new WkTeilEditor();
            wkTeilEditor.setCidsBean(wkTeilBean);
            addEditor(wkTeilEditor);
        }
    }

    private void addEditor(WkTeilEditor wkTeilEditor) {
        wkTeilEditors.add(wkTeilEditor);

        Color color = getNextColor();
        wkTeilEditor.setLineColor(color);

        JPanel panItem = new JPanel(new GridBagLayout());
        panItem.setOpaque(false);
        GridBagConstraints gridBagConstraints;
        JButton btnRemove = new JButton();

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton button = (JButton) evt.getSource();
                WkTeilEditor wkTeilEditor = wkTeileMap.get(button);
                removeEditor(wkTeilEditor);
            }
        });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        panItem.add(wkTeilEditor, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets.left = 5;
        gridBagConstraints.insets.right = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panItem.add(btnRemove, gridBagConstraints);

        wkTeileMap.put(btnRemove, wkTeilEditor);
        jPanel1.add(panItem);
        ((java.awt.GridLayout) jPanel1.getLayout()).setRows(jPanel1.getComponentCount());
        
        revalidate();
        fireWkTeilAdded();
    }

    private void removeEditor(WkTeilEditor wkTeilEditor) {
        cidsBeans.remove(wkTeilEditor.getCidsBean());
        
        jPanel1.remove(wkTeilEditor.getParent());
        wkTeilEditor.dispose();

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(1);
        }

        wkTeilEditors.remove(wkTeilEditor);

        revalidate();
        fireWkTeilRemoved();
    }

    public void dispose() {
        for (WkTeilEditor wkTeilEditor : wkTeilEditors) {
            wkTeilEditor.dispose();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        semiRoundedPanel1 = new de.cismet.tools.gui.SemiRoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(200, 100));
        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        semiRoundedPanel1.setBackground(new java.awt.Color(51, 51, 51));
        semiRoundedPanel1.setMinimumSize(new java.awt.Dimension(55, 24));
        semiRoundedPanel1.setPreferredSize(new java.awt.Dimension(0, 24));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(WkTeileEditor.class, "WkTeileEditor.jLabel1.text")); // NOI18N
        semiRoundedPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        roundedPanel1.add(semiRoundedPanel1, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 48));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        roundedPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(10, 24));
        jPanel2.setOpaque(false);

        jLabel2.setText(" "); // NOI18N
        jPanel2.add(jLabel2);

        roundedPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        add(roundedPanel1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    // End of variables declaration//GEN-END:variables

}
