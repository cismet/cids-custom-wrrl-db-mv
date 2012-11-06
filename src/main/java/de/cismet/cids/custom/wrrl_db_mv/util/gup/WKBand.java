/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.connection.SessionManager;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.custom.wrrl_db_mv.server.search.WkSearchByStations;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.MinimumHeightBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.SimpleTextSection;
import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class WKBand extends MinimumHeightBand implements BandSnappingPointProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            WKBand.class);

    //~ Instance fields --------------------------------------------------------

    private double max;
    private double min;
    private ArrayList<ArrayList> inputResulSet;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public WKBand(final double min, final double max) {
        this(min, max, (ArrayList<ArrayList>)null);
    }

    /**
     * Creates a new WKBand object.
     *
     * @param  min            DOCUMENT ME!
     * @param  max            DOCUMENT ME!
     * @param  inputResulSet  DOCUMENT ME!
     */
    public WKBand(final double min, final double max, final ArrayList<ArrayList> inputResulSet) {
        this(min, max, inputResulSet, "WK");
    }
    /**
     * Creates a new WKBand object.
     *
     * @param  min    DOCUMENT ME!
     * @param  max    DOCUMENT ME!
     * @param  title  heightWeight DOCUMENT ME!
     */
    public WKBand(final double min,
            final double max,
            final String title) {
        this(min, max, null, title);
    }
    /**
     * Creates a new WKBand object.
     *
     * @param  min            DOCUMENT ME!
     * @param  max            DOCUMENT ME!
     * @param  inputResulSet  DOCUMENT ME!
     * @param  title          heightWeight DOCUMENT ME!
     */
    public WKBand(final double min,
            final double max,
            final ArrayList<ArrayList> inputResulSet,
            final String title) {
        super(title);
        this.max = max;
        this.min = min;

        if (inputResulSet != null) {
            setWK(inputResulSet);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void setWK(final ArrayList<ArrayList> inputResulSet) {
        removeAllMember();
        this.inputResulSet = inputResulSet;
        for (final ArrayList zeile : inputResulSet) {
            final String wk_k = String.valueOf(zeile.get(0));
            double von = (Double)zeile.get(1);
            double bis = (Double)zeile.get(2);
            boolean openLeft = false;
            boolean openRight = false;
            if (von < min) {
                von = min;
                openLeft = true;
            }

            if (bis > max) {
                bis = max;
                openRight = true;
            }
            addMember(new SimpleTextSection(wk_k, von, bis, openLeft, openRight));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public void setMinMax(final double min, final double max) {
        this.min = min;
        this.max = max;

        if (inputResulSet != null) {
            setWK(inputResulSet);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sbm    DOCUMENT ME!
     * @param  gwk    DOCUMENT ME!
     * @param  jband  DOCUMENT ME!
     */
    public void fillAndInsertBand(final SimpleBandModel sbm, final String gwk, final JBand jband) {
        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                @Override
                protected ArrayList<ArrayList> doInBackground() throws Exception {
                    final CidsServerSearch searchWK = new WkSearchByStations(
                            sbm.getMin(),
                            sbm.getMax(),
                            gwk);

                    final Collection resWK = SessionManager.getProxy()
                                .customServerSearch(SessionManager.getSession().getUser(), searchWK);
                    return (ArrayList<ArrayList>)resWK;
                }

                @Override
                protected void done() {
                    try {
                        final ArrayList<ArrayList> res = get();
                        setWK(res);
                        sbm.insertBand(WKBand.this, 0);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
//                        updateUI();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Wasserkoerper", e);
                    }
                }
            });
    }
}
