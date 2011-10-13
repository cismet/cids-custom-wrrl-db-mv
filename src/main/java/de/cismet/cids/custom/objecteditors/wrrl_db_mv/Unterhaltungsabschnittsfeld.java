/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import de.cismet.cids.custom.wrrl_db_mv.util.gup.GUPTableModel;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class Unterhaltungsabschnittsfeld extends javax.swing.JPanel implements MouseListener,
    ActionListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            Unterhaltungsabschnittsfeld.class);

    //~ Instance fields --------------------------------------------------------

    final JRadioButton normalScale = new JRadioButton("normal");
    final JRadioButton logScale = new JRadioButton("logarithmisch");

    private ButtonGroup scaleGroup = new ButtonGroup();
    private final JPanel controlPanel = new JPanel();
    private List<ActionListener> actionListener = new ArrayList<ActionListener>();
    private List<CidsBeanDropListener> cidsBeanDropListener = new ArrayList<CidsBeanDropListener>();
    private HashMap<JComponent, ComponentInformation> componentActionTextMap =
        new HashMap<JComponent, ComponentInformation>();
    private GUPTableModel model;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Unterhaltungsabschnittsfeld object.
     */
    public Unterhaltungsabschnittsfeld() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * shows an empty station editor to enter the water borders.
     */
    private void init() {
        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new java.awt.GridBagLayout());
        setOpaque(false);
        normalScale.addActionListener(this);
        logScale.addActionListener(this);
        scaleGroup.add(normalScale);
        scaleGroup.add(logScale);
        controlPanel.setLayout(new GridBagLayout());
        gridBagConstraints = createGridBagConstraint(0, 0, 0, 0, 1, java.awt.GridBagConstraints.NONE);
        controlPanel.add(normalScale, gridBagConstraints);
        gridBagConstraints = createGridBagConstraint(1, 0, 1, 0, 1, java.awt.GridBagConstraints.NONE);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        controlPanel.add(logScale, gridBagConstraints);
        gridBagConstraints = createGridBagConstraint(0, 0, 0, 0, 2, java.awt.GridBagConstraints.HORIZONTAL);
        add(controlPanel, gridBagConstraints);
        scaleGroup.setSelected(normalScale.getModel(), true);

        if (model.showVerticalHeader()) {
            for (int i = 0; i < model.getRows(); ++i) {
                final JComponent wkLab = model.getVerticalHeader(i);
                gridBagConstraints = createGridBagConstraint(0, i + 1, 0, 0, 1, java.awt.GridBagConstraints.HORIZONTAL);
                add(wkLab, gridBagConstraints);
            }
        }

        for (int row = 0; row < model.getRows(); ++row) {
            final JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());

            final int cols = model.getCols(row);

            for (int col = 0; col < cols; ++col) {
                final JComponent comp = (JComponent)model.getValue(col, row);
                gridBagConstraints = model.getConstraint(col, row);
                componentActionTextMap.put(comp, new ComponentInformation(col, row, gridBagConstraints));
                comp.addMouseListener(this);
                comp.setMinimumSize(new Dimension(1, 1));
                panel.add(comp, gridBagConstraints);
            }

            gridBagConstraints = createGridBagConstraint(
                    1,
                    row
                            + 1,
                    1,
                    model.getRowWeight(row),
                    1,
                    java.awt.GridBagConstraints.BOTH);
            add(panel, gridBagConstraints);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshScale() {
        for (final JComponent tmp : componentActionTextMap.keySet()) {
            final ComponentInformation cp = componentActionTextMap.get(tmp);
            double weight;

            if (scaleGroup.getSelection() == logScale.getModel()) {
                weight = model.getWeight(cp.getX(), cp.getY(), true);
            } else {
                weight = model.getWeight(cp.getX(), cp.getY(), false);
            }
            System.out.println("weight: " + weight);
            cp.getConstraints().weightx = weight;
            final Container c = tmp.getParent();
            c.remove(tmp);
            c.add(tmp, cp.getConstraints());
        }
        validate();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   gridx      DOCUMENT ME!
     * @param   gridy      DOCUMENT ME!
     * @param   weightx    DOCUMENT ME!
     * @param   weighty    DOCUMENT ME!
     * @param   gridwidth  DOCUMENT ME!
     * @param   fill       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static GridBagConstraints createGridBagConstraint(final int gridx,
            final int gridy,
            final double weightx,
            final double weighty,
            final int gridwidth,
            final int fill) {
        final GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        gridBagConstraints.gridwidth = gridwidth;

        return gridBagConstraints;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  model  DOCUMENT ME!
     */
    public void setModel(final GUPTableModel model) {
        this.model = model;
        init();
    }

    /**
     * DOCUMENT ME!
     */
    public void refresh() {
        final Component[] comps = getComponents();

        for (final Component tmp : comps) {
            remove(tmp);
        }

        init();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        final JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new GridLayout(1, 1));
        final Unterhaltungsabschnittsfeld uaf = new Unterhaltungsabschnittsfeld();
        uaf.setModel(new GUPTableModel() {

                final List<WkUnterhaltung> l = new ArrayList<WkUnterhaltung>();

                {
                    WasserkoerperLabel wkLab = new WasserkoerperLabel();
                    wkLab.setWKName("NAM-200");
                    List<Double> uasLaenge = new ArrayList<Double>();
                    uasLaenge.add(100.0);
                    uasLaenge.add(2.0);
                    uasLaenge.add(4000.0);
                    uasLaenge.add(300.0);
                    uasLaenge.add(350.0);
                    uasLaenge.add(210.0);
                    uasLaenge.add(180.0);
                    uasLaenge.add(230.0);
                    uasLaenge.add(360.0);
                    uasLaenge.add(400.0);
                    uasLaenge.add(120.0);
                    uasLaenge.add(50.0);
                    List<JLabel> uas = new ArrayList<JLabel>();
                    JLabel tmp = new JLabel();
                    tmp.setText("ua-1");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-2");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-3");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-6");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-7");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-8");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-9");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-10");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-11");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-12");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-13");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-14");
                    uas.add(tmp);
                    WkUnterhaltung wk = new WkUnterhaltung(wkLab, uas, uasLaenge);
                    l.add(wk);

                    uasLaenge = new ArrayList<Double>();
                    uasLaenge.add(150.0);
                    uasLaenge.add(200.0);
                    uasLaenge.add(250.0);
                    uasLaenge.add(300.0);
                    uasLaenge.add(400.0);
                    uasLaenge.add(230.0);
                    uasLaenge.add(100.0);
                    uasLaenge.add(80.0);

                    uas = new ArrayList<JLabel>();
                    tmp = new JLabel();
                    tmp.setText("ua-4");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-5");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-15");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-16");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-17");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-18");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-19");
                    uas.add(tmp);
                    tmp = new JLabel();
                    tmp.setText("ua-20");
                    uas.add(tmp);
                    wkLab = new WasserkoerperLabel();
                    wkLab.setWKName("OTOL-200");
                    wk = new WkUnterhaltung(wkLab, uas, uasLaenge);
                    l.add(wk);
                }

                @Override
                public JComponent getValue(final int x, final int y) {
                    if (y == 0) {
                        final WasserkoerperLabel res = l.get(x).getWkLab();
                        confSize(res);
                        return res;
                    } else if (y == 1) {
                        int n = 0;
                        for (final WkUnterhaltung tmp : l) {
                            if (tmp.getUnterhaltungsabschnitte().size() > (x - n)) {
                                final JLabel res = tmp.getUnterhaltungsabschnitte().get(x - n);
                                res.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
//                                res.addMouseListener(Unterhaltungsabschnittsfeld.this);
                                res.setText("<html>&nbsp;</html>");
                                confSize(res);

                                return res;
                            } else {
                                n += tmp.getUnterhaltungsabschnitte().size();
                            }
                        }
                        return null;
                    }

                    return null;
                }

                @Override
                public int getRows() {
                    return 2;
                }

                @Override
                public int getCols(final int row) {
                    if (row == 0) {
                        return l.size();
                    } else {
                        int n = 0;
                        for (final WkUnterhaltung tmp : l) {
                            n += tmp.getUnterhaltungsabschnitte().size();
                        }
                        return n;
                    }
                }

                @Override
                public double getWeight(final int x, final int y, final boolean log) {
                    double result = 0.0;

                    if (y == 0) {
                        final List<Double> lengths = l.get(x).getUnterhaltungsabschnitteLaenge();
                        for (final Double tmp : lengths) {
                            if (log) {
                                result += Math.log10(tmp);
                            } else {
                                result += tmp;
                            }
                        }
                    } else if (y == 1) {
                        int n = 0;
                        for (final WkUnterhaltung tmp : l) {
                            if (tmp.getUnterhaltungsabschnitteLaenge().size() > (x - n)) {
                                if (log) {
                                    result = Math.log10(tmp.getUnterhaltungsabschnitteLaenge().get(x - n));
                                    break;
                                } else {
                                    result = tmp.getUnterhaltungsabschnitteLaenge().get(x - n);
                                    break;
                                }
                            } else {
                                n += tmp.getUnterhaltungsabschnitteLaenge().size();
                            }
                        }
                    }

                    return result;
                }

                private void confSize(final JComponent comp) {
                    final Dimension d = new Dimension(1, 20);
                    comp.setPreferredSize(d);
                    comp.setMinimumSize(d);
                    comp.setMaximumSize(d);
                    comp.setSize(0, 0);
                }

                @Override
                public GridBagConstraints getConstraint(final int x, final int y) {
                    return createGridBagConstraint(
                            x,
                            0,
                            getWeight(x, y, false),
                            ((y == 0) ? 0 : 1),
                            1,
                            ((y == 0) ? java.awt.GridBagConstraints.HORIZONTAL : java.awt.GridBagConstraints.BOTH));
                }

                @Override
                public double getRowWeight(final int row) {
                    return row;
                }

                @Override
                public JComponent getVerticalHeader(final int row) {
                    if (row == 0) {
                        final JLabel lab = new JLabel("WK:");
                        lab.setSize(20, 20);
                        return lab;
                    } else {
                        final JLabel lab = new JLabel("UA:");
                        lab.setSize(20, 20);
                        return lab;
                    }
                }

                @Override
                public boolean showVerticalHeader() {
                    return true;
                }

                @Override
                public boolean fullScreen() {
                    return false;
                }

                class WkUnterhaltung {

                    private WasserkoerperLabel wkLab;
                    private List<JLabel> unterhaltungsabschnitte;
                    private List<Double> unterhaltungsabschnitteLaenge;

                    /**
                     * Creates a new WkUnterhaltung object.
                     *
                     * @param  wkLab                          DOCUMENT ME!
                     * @param  unterhaltungsabschnitte        DOCUMENT ME!
                     * @param  unterhaltungsabschnitteLaenge  DOCUMENT ME!
                     */
                    public WkUnterhaltung(final WasserkoerperLabel wkLab,
                            final List<JLabel> unterhaltungsabschnitte,
                            final List<Double> unterhaltungsabschnitteLaenge) {
                        this.wkLab = wkLab;
                        this.unterhaltungsabschnitte = unterhaltungsabschnitte;
                        this.unterhaltungsabschnitteLaenge = unterhaltungsabschnitteLaenge;
//                        normalizeWeights();
                    }

                    /**
                     * DOCUMENT ME!
                     *
                     * @param  unterhaltungsabschnitt  DOCUMENT ME!
                     */
                    public void addUnterhaltungsabschnitt(final JLabel unterhaltungsabschnitt) {
                        getUnterhaltungsabschnitte().add(unterhaltungsabschnitt);
                    }

                    /**
                     * DOCUMENT ME!
                     *
                     * @return  the wkLab
                     */
                    public WasserkoerperLabel getWkLab() {
                        return wkLab;
                    }

                    /**
                     * DOCUMENT ME!
                     *
                     * @return  the unterhaltungsabschnitte
                     */
                    public List<JLabel> getUnterhaltungsabschnitte() {
                        return unterhaltungsabschnitte;
                    }

                    /**
                     * DOCUMENT ME!
                     *
                     * @return  the unterhaltungsabschnitte
                     */
                    public List<Double> getUnterhaltungsabschnitteLaenge() {
                        return unterhaltungsabschnitteLaenge;
                    }

                    private void normalizeWeights() {
                        double sum = 0.0;
                        final List<Double> unterhaltungsabschnitteTmp = new ArrayList<Double>();

                        for (final Double tmp : unterhaltungsabschnitteLaenge) {
                            sum += tmp;
                        }

                        for (final Double tmp : unterhaltungsabschnitteLaenge) {
                            unterhaltungsabschnitteTmp.add(tmp.doubleValue() / sum);
                        }

                        unterhaltungsabschnitteLaenge = unterhaltungsabschnitteTmp;
                    }
                }
            });
        frame.getContentPane().add(uaf);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 75);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (!e.isPopupTrigger() && (e.getButton() == MouseEvent.BUTTON1)) {
            final ActionEvent ae = new ActionEvent(e.getSource(),
                    e.getID(),
                    this.componentActionTextMap.get((JComponent)e.getSource()).toString(),
                    e.getWhen(),
                    e.getModifiers());

            LOG.error("mouse" + this.componentActionTextMap.get((JComponent)e.getSource()));

            for (final ActionListener listener : actionListener) {
                listener.actionPerformed(ae);
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        for (final CidsBeanDropListener tmp : cidsBeanDropListener) {
            tmp.beansDropped(beans);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addCidsBeanDropListener(final CidsBeanDropListener listener) {
        this.cidsBeanDropListener.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeCidsBeanDropListener(final CidsBeanDropListener listener) {
        this.cidsBeanDropListener.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addActionListener(final ActionListener listener) {
        this.actionListener.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeActionListener(final ActionListener listener) {
        this.actionListener.remove(listener);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        refreshScale();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ComponentInformation {

        //~ Instance fields ----------------------------------------------------

        private int x;
        private int y;
        private GridBagConstraints constraints;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ComponentInformation object.
         *
         * @param  x            DOCUMENT ME!
         * @param  y            DOCUMENT ME!
         * @param  constraints  DOCUMENT ME!
         */
        public ComponentInformation(final int x, final int y, final GridBagConstraints constraints) {
            this.x = x;
            this.y = y;
            this.constraints = constraints;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the x
         */
        public int getX() {
            return x;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  x  the x to set
         */
        public void setX(final int x) {
            this.x = x;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the y
         */
        public int getY() {
            return y;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  y  the y to set
         */
        public void setY(final int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the constraints
         */
        public GridBagConstraints getConstraints() {
            return constraints;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  constraints  the constraints to set
         */
        public void setConstraints(final GridBagConstraints constraints) {
            this.constraints = constraints;
        }
    }
}
