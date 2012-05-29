/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingSingletonInstances;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.gui.jbands.BandEvent;
import de.cismet.tools.gui.jbands.BandMemberEvent;
import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.interfaces.BandListener;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandMemberListener;
import de.cismet.tools.gui.jbands.interfaces.BandModificationProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public abstract class LineBand extends DefaultBand implements CidsBeanCollectionStore,
    BandModificationProvider,
    BandMemberListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(LineBand.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<CidsBean> massnahmen;
    protected String objectTableName = null;
    protected String lineFieldName = "linie";
    protected boolean readOnly = false;
    private List<BandListener> listenerList = new ArrayList<BandListener>();

    private HashMap<String, CidsBean> beanMap = new HashMap<String, CidsBean>();
    private boolean normalise = false;
    private CidsBean route;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public LineBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public LineBand(final float heightWeight, final String objectTableName) {
        super(heightWeight);
        this.objectTableName = objectTableName;
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public LineBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title);
        this.objectTableName = objectTableName;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return massnahmen;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        massnahmen = beans;
        removeAllMember();
        normalizeStations();

        if (massnahmen != null) {
            for (final CidsBean massnahme : massnahmen) {
                final LineBandMember m = createBandMemberFromBean();
                m.setReadOnly(readOnly);
                m.addBandMemberListener(this);
                m.setCidsBean(massnahme);
                addMember(m);
            }
        } else {
            massnahmen = new ArrayList<CidsBean>();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  route  DOCUMENT ME!
     */
    public void setRoute(final CidsBean route) {
        this.route = route;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getRoute() {
//        if ((massnahmen != null) && (massnahmen.size() > 0)) {
//            for (final CidsBean cb : massnahmen) {
//                return (CidsBean)cb.getProperty(lineFieldName + ".von.route");
//            }
//        } else {
//
//        }
        return route;
    }

    @Override
    public void addMember(final Double startStation,
            final Double endStation,
            final Double minStart,
            final Double maxEnd,
            final List<BandMember> memberList) {
        if (endStation == null) {
            addUnspecifiedMember(startStation, minStart, maxEnd, memberList);
        } else {
            addSpecifiedMember(startStation, endStation);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  objectBean    DOCUMENT ME!
     * @param  startStation  DOCUMENT ME!
     * @param  endStation    endValue DOCUMENT ME!
     */
    public void addMember(final CidsBean objectBean, final CidsBean startStation,
            final CidsBean endStation) {
        try {
            final CidsBean line = CidsBeanSupport.createNewCidsBeanFromTableName("station_linie");
            line.setProperty("von", startStation);
            line.setProperty("bis", endStation);
            line.setProperty("id", LinearReferencingHelper.getNewLineId());
            objectBean.setProperty(lineFieldName, line);
            final LineBandMember m = refresh(objectBean, true);
            massnahmen.add(objectBean);
            fireBandChanged(new BandEvent());
        } catch (Exception e) {
            LOG.error("error while creating new station.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void normalizeStations() {
        beanMap.clear();
        setNormalise(true);

        // sorgt dafuer, dass bei geteilten Stationen auch die entsprechenden cidsBeans geteilt werden
        if (massnahmen != null) {
            for (final CidsBean massnahme : massnahmen) {
                final CidsBean line = LinearReferencingSingletonInstances.CIDSBEAN_CACHE.getCachedBeanFor((CidsBean)
                        massnahme.getProperty(lineFieldName));

                try {
                    if ((line != null) && (line != massnahme.getProperty(lineFieldName))) {
                        massnahme.setProperty(lineFieldName, line);
                    }

                    final CidsBean von = LinearReferencingSingletonInstances.CIDSBEAN_CACHE.getCachedBeanFor((CidsBean)
                            massnahme.getProperty(lineFieldName + ".von"));
                    final CidsBean bis = LinearReferencingSingletonInstances.CIDSBEAN_CACHE.getCachedBeanFor((CidsBean)
                            massnahme.getProperty(lineFieldName + ".bis"));

                    if ((von != null) && (von != massnahme.getProperty(lineFieldName + ".von"))) {
                        massnahme.setProperty(lineFieldName + ".von", von);
                    }

                    if ((bis != null) && (bis != massnahme.getProperty(lineFieldName + ".bis"))) {
                        massnahme.setProperty(lineFieldName + ".bis", bis);
                    }
                } catch (Exception e) {
                    LOG.error("Error while setting cidsBean.", e);
                }
            }
            setNormalise(false);

            fireBandChanged(new BandEvent());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  member  DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    public void normalizeStationOfModifiedMember(final CidsBean member, final boolean isFrom) {
        final String propWert = isFrom ? (lineFieldName + ".von.wert") : (lineFieldName + ".bis.wert");
        final String propStat = isFrom ? (lineFieldName + ".von") : (lineFieldName + ".bis");
        final String memberWert = String.valueOf(member.getProperty(propWert));
        final CidsBean memberStat = (CidsBean)member.getProperty(propStat);

        // sorgt dafuer, dass bei geteilten Stationen auch die entsprechenden cidsBeans geteilt werden
        for (final CidsBean otherBean : massnahmen) {
            if (otherBean != member) {
                CidsBean otherStat = (CidsBean)otherBean.getProperty(lineFieldName + ".von");

                checkToMerge(member, otherStat, memberStat, memberWert, isFrom);
                otherStat = (CidsBean)otherBean.getProperty(lineFieldName + ".bis");
                checkToMerge(member, otherStat, memberStat, memberWert, isFrom);
            }
        }
        normalizeStations();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  objectBean  DOCUMENT ME!
     * @param  otherStat   DOCUMENT ME!
     * @param  memberStat  DOCUMENT ME!
     * @param  memberWert  DOCUMENT ME!
     * @param  isFrom      DOCUMENT ME!
     */
    private void checkToMerge(final CidsBean objectBean,
            final CidsBean otherStat,
            final CidsBean memberStat,
            final String memberWert,
            final boolean isFrom) {
        if (otherStat != null) {
            final String wert = String.valueOf(otherStat.getProperty("wert"));
            if ((wert != null) && !wert.equals("null") && wert.equals(memberWert)) {
                try {
                    if (otherStat != memberStat) {
                        LinearReferencingSingletonInstances.MERGE_REGISTRY.firePointBeanMergeRequest(
                            objectBean,
                            isFrom,
                            otherStat);
                    }
                } catch (Exception e) {
                    LOG.error("Error while sharing station.", e);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    public void splitStation(final CidsBean bean, final double value, final boolean isFrom) {
        final String prop = isFrom ? (lineFieldName + ".von") : (lineFieldName + ".bis");
        final CidsBean station = (CidsBean)bean.getProperty(prop);

        for (final CidsBean otherBean : massnahmen) {
            if (otherBean != bean) {
                final CidsBean otherStat = (CidsBean)otherBean.getProperty(lineFieldName + "."
                                + (isFrom ? "bis" : "von"));

                if ((otherStat != null) && (otherStat == station)) {
                    try {
                        LinearReferencingSingletonInstances.MERGE_REGISTRY.firePointBeanSplitRequest(bean, isFrom);
                    } catch (Exception e) {
                        LOG.error("Error while sharing station.", e);
                    }
                }
            }
        }
        normalizeStations();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   route  DOCUMENT ME!
     * @param   value  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean createNewStation(final CidsBean route, final double value) throws Exception {
        // neue Station erstellen
        final CidsBean newStat = CidsBeanSupport.createNewCidsBeanFromTableName("station");
        final CidsBean geom = CidsBeanSupport.createNewCidsBeanFromTableName("geom");
        newStat.setProperty("route", route);
        newStat.setProperty("wert", value);
        newStat.setProperty("id", LinearReferencingHelper.getNewStationId());
        newStat.setProperty(LinearReferencingConstants.PROP_STATION_GEOM, geom);

        // Punktgeometrie erstellen
        final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(
                LinearReferencingHelper.getLinearValueFromStationBean(newStat),
                LinearReferencingHelper.getRouteGeometryFromStationBean(newStat));
        pointGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
        LinearReferencingHelper.setPointGeometryToStationBean(pointGeom, newStat);

        return newStat;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  startStation  DOCUMENT ME!
     * @param  minStart      DOCUMENT ME!
     * @param  maxEnd        DOCUMENT ME!
     * @param  memberList    DOCUMENT ME!
     */
    private void addUnspecifiedMember(final Double startStation,
            final Double minStart,
            final Double maxEnd,
            List<BandMember> memberList) {
        double distanceBefore = Double.MAX_VALUE;
        double distanceBehind = Double.MAX_VALUE;
        CidsBean beanBefore = null;
        CidsBean beanBehind = null;

        if (memberList != null) {
            // member list will be considered.
            for (final BandMember tmp : memberList) {
                if (tmp instanceof CidsBeanStore) {
                    final CidsBean b = ((CidsBeanStore)tmp).getCidsBean();
                    final Double from = (Double)tmp.getMin();
                    final Double till = (Double)tmp.getMax();

                    if ((from != null) && (till != null)) {
                        double distance = startStation - till;
                        if ((distance < distanceBefore) && (distance >= 0)) {
                            distanceBefore = distance;
                            beanBefore = (CidsBean)b.getProperty(lineFieldName + ".bis");
                        }

                        distance = from - startStation;
                        if ((distance < distanceBehind) && (distance >= 0)) {
                            distanceBehind = distance;
                            beanBehind = (CidsBean)b.getProperty(lineFieldName + ".von");
                        }
                    }
                } else {
                    // member list will be ignored
                    beanBefore = null;
                    beanBehind = null;
                    memberList = null;
                }
            }
        }

        if (memberList == null) {
            for (final CidsBean tmp : massnahmen) {
                final Double from = (Double)tmp.getProperty(lineFieldName + ".von.wert");
                final Double till = (Double)tmp.getProperty(lineFieldName + ".bis.wert");

                if ((from != null) && (till != null)) {
                    double distance = startStation - till;
                    if ((distance < distanceBefore) && (distance >= 0)) {
                        distanceBefore = distance;
                        beanBefore = (CidsBean)tmp.getProperty(lineFieldName + ".bis");
                    }

                    distance = from - startStation;
                    if ((distance < distanceBehind) && (distance >= 0)) {
                        distanceBehind = distance;
                        beanBehind = (CidsBean)tmp.getProperty(lineFieldName + ".von");
                    }
                }
            }
        }

        try {
            if (beanBefore == null) {
                beanBefore = createNewStation(getRoute(), minStart);
            }

            if (beanBehind == null) {
                beanBehind = createNewStation(getRoute(), maxEnd);
            }

            addNewMember(beanBefore, beanBehind);
        } catch (Exception e) {
            LOG.error("error while creating new station.", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  startStation  DOCUMENT ME!
     * @param  endStation    minStart DOCUMENT ME!
     */
    private void addSpecifiedMember(final Double startStation,
            final Double endStation) {
        CidsBean beanBefore = null;
        CidsBean beanBehind = null;

        try {
            beanBefore = createNewStation(getRoute(), startStation);
            beanBehind = createNewStation(getRoute(), endStation);
            addNewMember(beanBefore, beanBehind);
        } catch (Exception e) {
            LOG.error("error while creating new station.", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   beanBefore  DOCUMENT ME!
     * @param   beanBehind  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void addNewMember(final CidsBean beanBefore, final CidsBean beanBehind) throws Exception {
        final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(objectTableName);
        final CidsBean line = CidsBeanSupport.createNewCidsBeanFromTableName("station_linie");
        final CidsBean geom = CidsBeanSupport.createNewCidsBeanFromTableName("geom");
        final Geometry lineGeom = LinearReferencedLineFeature.createSubline((Double)beanBefore.getProperty("wert"),
                (Double)beanBehind.getProperty("wert"),
                (Geometry)beanBehind.getProperty("route.geom.geo_field"));
        lineGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
        geom.setProperty("geo_field", lineGeom);
        line.setProperty("von", beanBefore);
        line.setProperty("bis", beanBehind);
        line.setProperty("geom", geom);
        line.setProperty("id", LinearReferencingHelper.getNewStationId());
        newBean.setProperty(lineFieldName, line);
        final LineBandMember m = refresh(newBean, true);

        massnahmen.add(newBean);

        fireBandChanged(new BandEvent());
        m.setNewMode();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   special  DOCUMENT ME!
     * @param   add      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LineBandMember refresh(final CidsBean special, final boolean add) {
        removeAllMember();

        for (final CidsBean massnahme : massnahmen) {
            if (add || (massnahme != special)) {
                final LineBandMember m = createBandMemberFromBean();
                m.setReadOnly(readOnly);
                m.setCidsBean(massnahme);
                m.addBandMemberListener(this);
                addMember(m);
            }
        }

        if (add) {
            final LineBandMember m = createBandMemberFromBean();
            m.setReadOnly(readOnly);
            m.setCidsBean(special);
            m.addBandMemberListener(this);
            addMember(m);
            return m;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract LineBandMember createBandMemberFromBean();

    @Override
    public void addBandListener(final BandListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeBandListener(final BandListener listener) {
        listenerList.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    public void fireBandChanged(final BandEvent e) {
        for (final BandListener l : listenerList) {
            l.bandChanged(e);
        }
    }

    @Override
    public void bandMemberChanged(final BandMemberEvent e) {
        final BandEvent ev = new BandEvent();
        if ((e != null)) {
            if (e.isSelectionLost()) {
                ev.setSelectionLost(true);
            }
            ev.setModelChanged(e.isModelChanged());
        }
        fireBandChanged(ev);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  member  DOCUMENT ME!
     */
    public void deleteMember(final LineBandMember member) {
        final CidsBean memberBean = member.getCidsBean();
        refresh(memberBean, false);
        massnahmen.remove(memberBean);

        final BandEvent e = new BandEvent();
        e.setSelectionLost(true);
        fireBandChanged(e);

//        try {
//            // todo: Stationen, die nicht mehrfach verwendet werden, sollen geloescht werden
//            if (memberBean.getProperty("linie") != null) {
//                ((CidsBean)memberBean.getProperty("linie")).delete();
//            }
//            memberBean.delete();
//        } catch (Exception e) {
//            LOG.error("Error while deleting a band member.", e);
//        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the normalise
     */
    public boolean isNormalise() {
        return normalise;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  normalise  the normalise to set
     */
    public void setNormalise(final boolean normalise) {
        this.normalise = normalise;
    }
}
