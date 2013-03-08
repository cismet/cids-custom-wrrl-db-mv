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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class StationLineBackup {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            StationLineBackup.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean lastRoute;
    private Double lastFrom;
    private Double lastTill;
    private String lineProperty = "linie";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StationLineBackup object.
     *
     * @param  lineProperty  DOCUMENT ME!
     */
    public StationLineBackup(final String lineProperty) {
        this.lineProperty = lineProperty;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Saves the station line properties of the given cids bean.
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void save(final CidsBean cidsBean) {
        lastRoute = (CidsBean)LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)cidsBean.getProperty(
                    lineProperty
                            + ".von"));
        lastFrom = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    lineProperty
                            + ".von"));
        lastTill = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    lineProperty
                            + ".bis"));
    }

    /**
     * Restores the station line properties of the given cids bean.
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void restoreStationValues(final CidsBean cidsBean) {
        try {
            cidsBean.setProperty(lineProperty + ".von.route", lastRoute);
            cidsBean.setProperty(lineProperty + ".bis.route", lastRoute);
            cidsBean.setProperty(lineProperty + ".von.wert", lastFrom);
            cidsBean.setProperty(lineProperty + ".bis.wert", lastTill);
        } catch (Exception e) {
            LOG.error("Error while restoring the station values.", e);
        }
    }

    /**
     * Cut the given beans, if they are not within the given bounds.
     *
     * @param  all      DOCUMENT ME!
     * @param  from     DOCUMENT ME!
     * @param  till     DOCUMENT ME!
     * @param  routeId  DOCUMENT ME!
     */
    public void cutSubobjects(final List<CidsBean> all, final double from, final double till, final int routeId) {
        final List<CidsBean> massnToDelete = new ArrayList<CidsBean>();

        for (final CidsBean bean : all) {
            if (!bean.getProperty("linie.von.route.id").equals(routeId)) {
                // the massnahmen object is on an other route
                massnToDelete.add(bean);
            } else {
                if ((((Double)bean.getProperty("linie.bis.wert")) < from)
                            || (((Double)bean.getProperty("linie.von.wert")) > till)) {
                    // the massnahmen object is before or  after the planungsabschnitt object
                    massnToDelete.add(bean);
                } else {
                    try {
                        // the massnahmen object must be cut
                        if (((Double)bean.getProperty("linie.von.wert")) < from) {
                            bean.setProperty("linie.von.wert", from);
                        }

                        if (((Double)bean.getProperty("linie.bis.wert")) > till) {
                            bean.setProperty("linie.bis.wert", till);
                        }
                    } catch (Exception e) {
                        LOG.error("Error while setting the valid station value", e);
                    }
                }
            }
        }

        all.removeAll(massnToDelete);
    }
}
