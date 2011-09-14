/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.gup;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.WasserkoerperLabel;
import de.cismet.cids.custom.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UnterhaltungsabschnitteModel implements GUPTableModel {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            UnterhaltungsabschnitteModel.class);

    //~ Instance fields --------------------------------------------------------

    WkUnterhaltung[] wku;
    private LinearReferencedLineEditor lrl = null;
    private CidsBean bean;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UnterhaltungsaabschnitteModel object.
     *
     * @param  bean  DOCUMENT ME!
     */
    public UnterhaltungsabschnitteModel(final CidsBean bean) {
        this.bean = bean;
        final List<CidsBean> uAbschnitte = CidsBeanSupport.getBeanCollectionFromProperty(
                bean,
                "unterhaltungsabschnitte");
        if ((uAbschnitte != null) && (uAbschnitte.size() > 0)) {
            long routeGwk = -1;
            int minStart = -1;
            int maxEnd = -1;

            for (final CidsBean tmp : uAbschnitte) {
                final CidsBean stationLinie = (CidsBean)tmp.getProperty("linie");
                final CidsBean start = (CidsBean)stationLinie.getProperty("von");
                final CidsBean end = (CidsBean)stationLinie.getProperty("bis");
                final CidsBean routeVon = (CidsBean)start.getProperty("route");
                final CidsBean routeBis = (CidsBean)end.getProperty("route");
                final long startGwk = (Long)routeVon.getProperty("gwk");
                final long endGwk = (Long)routeBis.getProperty("gwk");
                final int startValue = (Integer)start.getProperty("wert");
                final int endValue = (Integer)end.getProperty("wert");

                if (routeGwk == -1) {
                    routeGwk = startGwk;
                }

                if ((routeGwk != startGwk) || (startGwk == endGwk)) {
                    LOG.error("The gwk of a unterhaltungsabschnitt object is not correct. former gwk: " + routeGwk
                                + " startGwk: " + startGwk + " endGwk: " + endGwk);
                }

                if ((startValue < minStart) || (minStart == -1)) {
                    minStart = startValue;
                }

                if (endValue > maxEnd) {
                    maxEnd = endValue;
                }
            }

            if ((minStart == -1) || (maxEnd == -1) || (routeGwk == -1)) {
                LOG.error("Cannot determine the correct station points for the gwk: " + routeGwk
                            + " min start: " + minStart + " max end: " + maxEnd);
            }

            final CidsBean[] wbs = readWbsFromServer(routeGwk, minStart, maxEnd);
            Arrays.sort(wbs, new WBComparator(routeGwk));

            wku = new WkUnterhaltung[wbs.length];
            for (int i = 0; i < wbs.length; ++i) {
                final WasserkoerperLabel wkLab = new WasserkoerperLabel();
                wkLab.setName(wbs[i].toString());
                final List<JLabel> uas = new ArrayList<JLabel>();
                final List<Double> uaLength = new ArrayList<Double>();

                final List<CidsBean> allUas = getUAsWithinWB(uAbschnitte, wbs[i], routeGwk);
                Collections.sort(allUas, new UAComparator());

                for (final CidsBean tmp : allUas) {
                    final JLabel lab = new JLabel(String.valueOf(tmp.getProperty("id")));
                    uas.add(lab);
                    uaLength.add(getLineLength((CidsBean)tmp.getProperty("linie")));
                }

                wku[i] = new WkUnterhaltung(wkLab, uas, uaLength);
            }
        } else {
            wku = null;
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getValue(final int x, final int y) {
        if (isEmpty()) {
            if (x == 0) {
                final LinearReferencedLineEditor lrl = getLineEditor();
                lrl.setOtherLinesEnabled(false);
                try {
                    lrl.setLineField("linie");
                    lrl.setCidsBean(CidsBeanSupport.createNewCidsBeanFromTableName("GUP_UNTERHALTUNGSABSCHNITTE"));
                } catch (final Exception e) {
                    LOG.error("Cannot create a cids bean of the type station_linie", e);
                }
                return lrl;
            } else {
                return new JButton("übernehmen");
            }
        } else {
            if (y == 0) {
                final WasserkoerperLabel res = wku[x].getWkLab();
                confSize(res);
                return res;
            } else if (y == 1) {
                int n = 0;
                for (final WkUnterhaltung tmp : wku) {
                    if (tmp.getUnterhaltungsabschnitte().size() > (x - n)) {
                        final JLabel res = tmp.getUnterhaltungsabschnitte().get(x - n);
                        res.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
        return !isEmpty();
    }

    @Override
    public int getRows() {
        if (isEmpty()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getCols(final int row) {
        if (isEmpty()) {
            return 2;
        } else {
            if (row == 0) {
                return wku.length;
            } else {
                int n = 0;
                for (final WkUnterhaltung tmp : wku) {
                    n += tmp.getUnterhaltungsabschnitte().size();
                }
                return n;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   x    DOCUMENT ME!
     * @param   y    DOCUMENT ME!
     * @param   log  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public double getWeight(final int x, final int y, final boolean log) {
        if (isEmpty()) {
            return 1.0;
        } else {
            if (y == 0) {
                final List<Double> lengths = wku[x].getUnterhaltungsabschnitteLaenge();
                double sum = 0.0;
                for (final Double tmp : lengths) {
                    if (log) {
                        sum += Math.log10(tmp);
                    } else {
                        sum += tmp;
                    }
                }

                return sum;
            } else if (y == 1) {
                int n = 0;
                for (final WkUnterhaltung tmp : wku) {
                    if (tmp.getUnterhaltungsabschnitteLaenge().size() > (x - n)) {
                        if (log) {
                            return Math.log10(tmp.getUnterhaltungsabschnitteLaenge().get(x - n));
                        } else {
                            return tmp.getUnterhaltungsabschnitteLaenge().get(x - n);
                        }
                    } else {
                        n += tmp.getUnterhaltungsabschnitteLaenge().size();
                    }
                }
            }

            return 0.0;
        }
    }

    @Override
    public GridBagConstraints getConstraint(final int x, final int y) {
        if (isEmpty()) {
            if (x == 0) {
                return createGridBagConstraint(
                        x,
                        y,
                        1,
                        1,
                        1,
                        java.awt.GridBagConstraints.BOTH);
            } else {
                final GridBagConstraints gbc = createGridBagConstraint(
                        x,
                        y,
                        0,
                        0,
                        1,
                        java.awt.GridBagConstraints.NONE);
                return gbc;
            }
        } else {
            return createGridBagConstraint(
                    x,
                    0,
                    getWeight(x, y, false),
                    ((y == 0) ? 0 : 1),
                    1,
                    ((y == 0) ? java.awt.GridBagConstraints.HORIZONTAL : java.awt.GridBagConstraints.BOTH));
        }
    }

    @Override
    public double getRowWeight(final int row) {
        if (isEmpty()) {
            return 1.0;
        } else {
            if (row == 0) {
                return 0.0;
            } else {
                return 1.0;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineEditor getLineEditor() {
        if (lrl == null) {
            lrl = new LinearReferencedLineEditor();
        }

        return lrl;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  comp  DOCUMENT ME!
     */
    private void confSize(final JComponent comp) {
        final Dimension d = new Dimension(0, 20);
        comp.setPreferredSize(d);
        comp.setMinimumSize(d);
        comp.setMaximumSize(d);
        comp.setSize(0, 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isEmpty() {
        return wku == null;
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
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);

        return gridBagConstraints;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   line  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double getLineLength(final CidsBean line) {
        final Geometry geom = (Geometry)line.getProperty("geom.geo_field");

        return geom.getLength();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   uas           DOCUMENT ME!
     * @param   wb            DOCUMENT ME!
     * @param   referenceGwk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<CidsBean> getUAsWithinWB(final List<CidsBean> uas, final CidsBean wb, final long referenceGwk) {
        final List<CidsBean> res = new ArrayList<CidsBean>();

        for (final CidsBean tmp : uas) {
            if (isUaInWb(tmp, wb, referenceGwk)) {
                res.add(tmp);
            }
        }

        return res;
    }

    /**
     * checks if the given unterhaltungsabschnitt is within the given wk_fg.
     *
     * @param   ua            a object of the type unterhaltungsabschnitt
     * @param   wb            a object of the type wk_fg
     * @param   referenceGwk  the referenced gwk
     *
     * @return  DOCUMENT ME!
     */
    private boolean isUaInWb(final CidsBean ua, final CidsBean wb, final long referenceGwk) {
        final CidsBean uaLine = (CidsBean)ua.getProperty("linie");
        final CidsBean wbLine = (CidsBean)extractPartWithSuitableGwk(wb, referenceGwk).getProperty("linie");
        double uaStart = (Double)uaLine.getProperty("von.wert");
        double uaEnd = (Double)uaLine.getProperty("bis.wert");
        double wbStart = (Double)wbLine.getProperty("von.wert");
        double wbEnd = (Double)wbLine.getProperty("bis.wert");

        if (uaStart > uaEnd) {
            final double tmp = uaStart;
            uaStart = uaEnd;
            uaEnd = tmp;
        }

        if (wbStart > wbEnd) {
            final double tmp = wbStart;
            wbStart = wbEnd;
            wbEnd = tmp;
        }

        if ((uaStart >= wbStart) && (uaEnd <= wbEnd)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   gwk    DOCUMENT ME!
     * @param   start  DOCUMENT ME!
     * @param   end    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean[] readWbsFromServer(final long gwk, final int start, final int end) {
        final MetaClass mc = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg");
        final String query = "select distinct " + mc.getID() + ", " + mc.getPrimaryKey() + " from "
                    + mc.getTableName() + " fg, wk_fg_teile teile, wk_teil teil, station_linie linie, station von,"
                    + " station bis, route WHERE teile.wk_fg_reference = fg.id AND teile.teil = teil.id AND "
                    + "teil.linie = linie.id AND "
                    + "linie.von = von.id AND linie.bis = bis.id AND von.route = route.id AND "
                    + "route.gwk = " + gwk + " AND ((von.wert >= " + start + " AND von.wert < " + end
                    + ") OR (bis.wert > "
                    + start + " AND bis.wert <= " + end + ") OR (von.wert <= " + start + " AND bis.wert >= " + end
                    + "))";

        try {
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            final CidsBean[] beans = new CidsBean[metaObjects.length];
            for (int i = 0; i < metaObjects.length; ++i) {
                beans[i] = metaObjects[i].getBean();
            }

            return beans;
        } catch (final ConnectionException e) {
            LOG.error("Connection exception.", e);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o             an obejct of the type wk_fg
     * @param   referenceGwk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean extractPartWithSuitableGwk(final CidsBean o, final long referenceGwk) {
        final List<CidsBean> l = CidsBeanSupport.getBeanCollectionFromProperty(o, "teile");

        for (final CidsBean tmp : l) {
            final Integer tmpGwk = (Integer)tmp.getProperty("linie.von.route.gwk");
            if ((tmpGwk != null) && (tmpGwk == referenceGwk)) {
                return tmp;
            }
        }

        return null;
    }

    @Override
    public boolean fullScreen() {
        return isEmpty();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class WkUnterhaltung {

        //~ Instance fields ----------------------------------------------------

        private WasserkoerperLabel wkLab;
        private List<JLabel> unterhaltungsabschnitte;
        private List<Double> unterhaltungsabschnitteLaenge;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WkUnterhaltung object.
         */
        public WkUnterhaltung() {
            unterhaltungsabschnitte = new ArrayList<JLabel>();
        }

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
        }

        //~ Methods ------------------------------------------------------------

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
         * @param  wkLab  the wkLab to set
         */
        public void setWkLab(final WasserkoerperLabel wkLab) {
            this.wkLab = wkLab;
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
         * @param  unterhaltungsabschnitte  the unterhaltungsabschnitte to set
         */
        public void setUnterhaltungsabschnitte(final List<JLabel> unterhaltungsabschnitte) {
            this.unterhaltungsabschnitte = unterhaltungsabschnitte;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the unterhaltungsabschnitte
         */
        public List<Double> getUnterhaltungsabschnitteLaenge() {
            return unterhaltungsabschnitteLaenge;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  unterhaltungsabschnitteLaenge  the unterhaltungsabschnitte to set
         */
        public void setUnterhaltungsabschnitteLaenge(final List<Double> unterhaltungsabschnitteLaenge) {
            this.unterhaltungsabschnitteLaenge = unterhaltungsabschnitteLaenge;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class WBComparator extends UAComparator {

        //~ Instance fields ----------------------------------------------------

        private long gwk;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WBComparator object.
         *
         * @param  gwk  DOCUMENT ME!
         */
        public WBComparator(final long gwk) {
            this.gwk = gwk;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            final CidsBean p1 = extractPartWithSuitableGwk(o1, gwk);
            final CidsBean p2 = extractPartWithSuitableGwk(o2, gwk);

            int check = nullCheck(p1, p2);
            if (check != 2) {
                return check;
            }

            final Integer s1 = (Integer)p1.getProperty("linie.von.wert");
            final Integer s2 = (Integer)p2.getProperty("linie.von.wert");

            check = nullCheck(s1, s2);
            if (check != 2) {
                return check;
            }

            return s1.compareTo(s2);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class UAComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   o1  DOCUMENT ME!
         * @param   o2  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        protected int nullCheck(final Object o1, final Object o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            }

            return 2;
        }

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            final Integer s1 = (Integer)o1.getProperty("linie.von.wert");
            final Integer s2 = (Integer)o2.getProperty("linie.von.wert");

            final int check = nullCheck(s1, s2);
            if (check != 2) {
                return check;
            }

            return s1.compareTo(s2);
        }
    }
}
