/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgLawaTypeSearch;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

/**
 * DOCUMENT ME!
 *
 * @author   gbaatz
 * @version  $Revision$, $Date$
 */
public class LawaTableModel extends AbstractTableModel {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LawaTableModel.class);

    //~ Instance fields --------------------------------------------------------

    private final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "lawa");
    private String[] header = { "Typ", "Anteil %", "Länge m", "Anzahl Teilstücke" }; // NOI18N
    private String[][] data = new String[0][0];
    private CidsBean cidsBean;
    private List<CidsBean> teile;

    //~ Methods ----------------------------------------------------------------

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        if (columnIndex < header.length) {
            return header[columnIndex];
        } else {
            return "";
        }
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        if ((rowIndex < data.length) && (columnIndex < header.length)) {
            return data[rowIndex][columnIndex];
        } else {
            return ""; // NOI18N
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        // nothing to do, because it is not allowed to modify columns
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshData() {
        try {
            final CidsServerSearch search = new WkFgLawaTypeSearch(String.valueOf(getCidsBean().getProperty("id")));
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;
            data = new String[0][0];
            double totalLength = -1;
            final HashMap<Integer, LawaType> lawaTypes = new HashMap<Integer, LawaType>();

            if (resArray != null) {
                for (final ArrayList attributes : resArray) {
                    if (attributes.size() == 6) {
                        final Object codeObject = attributes.get(0);
                        final Object descriptionObject = attributes.get(1);
                        final Object totalLengthObject = attributes.get(2);
                        final Object intersectionLengthObject = attributes.get(3);

                        if ((codeObject instanceof Integer) && (descriptionObject instanceof String)
                                    && (totalLengthObject instanceof Double)
                                    && (intersectionLengthObject instanceof Double)) {
                            if (((Double)intersectionLengthObject).doubleValue() > 0.0) {
                                if ((totalLength == -1)
                                            && (((Integer)codeObject != -1) || (resArray.size() == 1))) {
                                    totalLength = ((Double)totalLengthObject).doubleValue();
                                }
                                LawaType type = lawaTypes.get((Integer)codeObject);

                                if (type == null) {
                                    type = new LawaType();
                                    type.setCode((Integer)codeObject);
                                    type.setDescription((String)descriptionObject);
                                    lawaTypes.put((Integer)codeObject, type);
                                }

                                type.setCount(type.getCount() + 1);
                                type.setTotalLength(type.getTotalLength()
                                            + ((Double)intersectionLengthObject).doubleValue());
                            }
                        } else {
                            LOG.error("The search results have the wrong data types");
                        }
                    }
                }

                final LawaType noType = lawaTypes.get(-1);
                if (noType != null) {
                    final int count = calcNoTypeCount(resArray);
                    noType.setCount(count);
                }
                fillData(lawaTypes, totalLength);
            }
            fireTableDataChanged();
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   resArray  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int calcNoTypeCount(final ArrayList<ArrayList> resArray) {
        int count = 0;
        int i = 0;
        double lastEnd = -1;
        double partEnd = 0;
        CidsBean currentPart;

        int index = 0;
        if ((getTeile() == null) || (getTeile().size() < 1)) {
            // not part found
            return 0;
        }

        currentPart = getTeile().get(index++);
        lastEnd = (Double)currentPart.getProperty("linie.von.wert");
        partEnd = (Double)currentPart.getProperty("linie.bis.wert");

        for (; i < resArray.size(); ++i) {
            final ArrayList attributes = resArray.get(i);
            if (attributes.size() == 6) {
                final Integer code = (Integer)attributes.get(0);
                final Double intersectionLength = (Double)attributes.get(3);
                final Long gwk = (Long)attributes.get(4);
                final Double from = (Double)attributes.get(5);

                if (code != -1) {
                    if (!gwk.equals((Long)currentPart.getProperty("linie.von.route.gwk"))) {
                        // andere Route
// LOG.error("++ andere Route gwk " + gwk + " other "
// + (Long)currentPart.getProperty("linie.von.route.gwk"));
                        ++count;
                        if (index < getTeile().size()) {
                            currentPart = getTeile().get(index++);
                            lastEnd = (Double)currentPart.getProperty("linie.von.wert");
                            partEnd = (Double)currentPart.getProperty("linie.bis.wert");
                        } else {
                            return count;
                        }
                        --i;
                    } else {
                        if ((from > lastEnd) && (from < partEnd)) {
//                                LOG.error("++ Lücke " + from + " lastEnd " + lastEnd + " partEnd" + partEnd);
                            ++count;
                        }

                        if ((from + intersectionLength) > lastEnd) {
                            lastEnd = from + intersectionLength;
                            if (lastEnd >= partEnd) {
                                if (index < getTeile().size()) {
                                    currentPart = getTeile().get(index++);
                                    lastEnd = (Double)currentPart.getProperty("linie.von.wert");
                                    partEnd = (Double)currentPart.getProperty("linie.bis.wert");
                                } else {
                                    return count;
                                }
                                --i;
                            }
                        }
                    }
                }
            }
        }

        if (lastEnd < partEnd) {
//                LOG.error("++ am Ende fehlt etwas lastEnd " + lastEnd + " partEnd " + partEnd);
            ++count;
        }

        return count;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lawaTypes        DOCUMENT ME!
     * @param  wkFgTotalLength  DOCUMENT ME!
     */
    private void fillData(final HashMap<Integer, LawaType> lawaTypes, final double wkFgTotalLength) {
        data = new String[lawaTypes.keySet().size() + 1][header.length];
        final Iterator<Integer> keyIt = lawaTypes.keySet().iterator();
        int counter = 0;
        int counterTotal = 0;
        double percentageTotal = 0.0;
        double lengthTotal = 0.0;

        while (keyIt.hasNext()) {
            final LawaType type = lawaTypes.get(keyIt.next());

            if ((type != null) && (type.getCode() != -1)) {
                final double percentage = type.getTotalLength() * 100.0 / wkFgTotalLength;
                percentageTotal += percentage;
                lengthTotal += type.getTotalLength();
                counterTotal += type.getCount();
                data[counter][0] = type.getCode() + "-" + type.getDescription();
                data[counter][1] = String.valueOf(round(percentage, 1));
                data[counter][2] = String.valueOf(round(type.getTotalLength(), 0));
                data[counter][3] = String.valueOf(type.getCount());
            } else if ((type != null) && (type.getCode() == -1)) {
                --counter;
            } else {
                LOG.error("LawaType object is null. This should never happen");
            }

            ++counter;
        }

        final LawaType noType = lawaTypes.get(-1);
        if (noType != null) {
            percentageTotal += noType.getTotalLength() * 100 / wkFgTotalLength;
            lengthTotal += noType.getTotalLength();
            counterTotal += noType.getCount();
        }
        data[counter][0] = "kein Typ";
        if (noType != null) {
            data[counter][1] = String.valueOf(round(noType.getTotalLength() * 100 / wkFgTotalLength, 1));
            data[counter][2] = String.valueOf(round(noType.getTotalLength(), 0));
            data[counter][3] = String.valueOf(noType.getCount());
        } else {
            --counter;
        }
        ++counter;
        data[counter][0] = "Gesamt";
        data[counter][1] = String.valueOf(round(percentageTotal, 1));
        data[counter][2] = String.valueOf(round(lengthTotal, 0));
        data[counter][3] = String.valueOf(counterTotal);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     * @param   scale  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double round(final double value, final int scale) {
        final double pow = Math.pow(10, scale);
        final int rounded = (int)(value * pow);

        return rounded / pow;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the cidsBean
     */
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  the cidsBean to set
     */
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the teile
     */
    public List<CidsBean> getTeile() {
        return teile;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  teile  the teile to set
     */
    public void setTeile(final List<CidsBean> teile) {
        this.teile = teile;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class LawaType {

        //~ Instance fields ----------------------------------------------------

        private int code;
        private String description;
        private double totalLength;
        private int count;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the code
         */
        public int getCode() {
            return code;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  code  the code to set
         */
        public void setCode(final int code) {
            this.code = code;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  description  the description to set
         */
        public void setDescription(final String description) {
            this.description = description;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the totalLength
         */
        public double getTotalLength() {
            return totalLength;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  totalLength  the totalLength to set
         */
        public void setTotalLength(final double totalLength) {
            this.totalLength = totalLength;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the count
         */
        public int getCount() {
            return count;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  count  the count to set
         */
        public void setCount(final int count) {
            this.count = count;
        }
    }
}
