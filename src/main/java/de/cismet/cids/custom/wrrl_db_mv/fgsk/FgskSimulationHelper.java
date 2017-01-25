/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimulationEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgWkkSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskSimulationHelper {

    //~ Static fields/initializers ---------------------------------------------

    public static final String SIM_MASSNAHME_CLASS_NAME = "de.cismet.cids.dynamics.Sim_massnahme";
    public static final String SIM_MASSNAHMEN_GRUPPE_CLASS_NAME = "de.cismet.cids.dynamics.Sim_massnahmen_gruppe";
    private static final Logger LOG = Logger.getLogger(FgskSimulationHelper.class);
    private static final MetaClass SIM_MASSNAHME_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "sim_massnahme");
    private static final MetaClass SIM_MASSNAHMEN_GRUPPE_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "sim_massnahmen_gruppe");
    private static final MetaClass SIMULATION_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "simulation");
    private static final MetaClass REALISIERUNG_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "massnahmen_realisierung");
    private static final CalculationCache<List, MetaObject[]> simMassnahmenCache =
        new CalculationCache<List, MetaObject[]>(
            new SimMassnCalculator());
    private static final CalculationCache<MetaClass, MetaObject[]> allSimMassnahmenCache =
        new CalculationCache<MetaClass, MetaObject[]>(
            new AllBeansOfTypeCalculator());
    private static final CalculationCache<MetaClass, MetaObject[]> allSimMassnahmenGruppeCache =
        new CalculationCache<MetaClass, MetaObject[]>(
            new AllBeansOfTypeCalculator());
    private static final Map<Integer, CidsBean> realisierungMap = new HashMap<Integer, CidsBean>();
    private static final Map<Integer, CidsBean> massnBeanMap = new HashMap<Integer, CidsBean>();
    private static final Map<Integer, CidsBean> massnGruppeBeanMap = new HashMap<Integer, CidsBean>();
    public static final String FGSK_KA_PROPERTY = "fgsk_ka";
    public static final String CUSTOM_COSTS_PROPERTY = "sonstige_kosten";
    public static final String SIMULATIONSMASSNAHMEN_PROPERTY = "angewendete_simulationsmassnahmen";
    public static final String EINZEL_MASSNAHME_PROPERTY = "einzel_massnahme";
    public static final String MASSNAHME_PROPERTY = "massnahme";

    static {
        try {
            final MetaObject[] massnMos = allSimMassnahmenCache.calcValue(SIM_MASSNAHME_MC);

            for (final MetaObject massn : massnMos) {
                massnBeanMap.put(massn.getId(), massn.getBean());
            }
        } catch (Exception e) {
            LOG.error("Error while retrieving all sim_massnahme objects.", e);
        }
        try {
            final MetaObject[] massnGruppeMos = allSimMassnahmenGruppeCache.calcValue(SIM_MASSNAHMEN_GRUPPE_MC);

            for (final MetaObject gruppe : massnGruppeMos) {
                massnGruppeBeanMap.put(gruppe.getId(), gruppe.getBean());
            }
        } catch (Exception e) {
            LOG.error("Error while retrieving all sim_massnahme_gruppe objects.", e);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * The resulting list contains all massnahmen of the given list. The list can contain massnahmengruppen and
     * massnahmen.
     *
     * @param   list  DOCUMENT ME!
     *
     * @return  a list with all massnahmen beans of the given list
     */
    public static List<CidsBean> getMassnahmenBeans(final List<CidsBean> list) {
        final List<CidsBean> massnList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : list) {
            if (tmp.getClass().getName().equalsIgnoreCase(SIM_MASSNAHMEN_GRUPPE_CLASS_NAME)) {
                final List<CidsBean> massnahmen = tmp.getBeanCollectionProperty("massnahmen");

                if (massnahmen != null) {
                    massnList.addAll(massnahmen);
                }
            } else if (tmp.getClass().getName().equalsIgnoreCase(SIM_MASSNAHME_CLASS_NAME)) {
                massnList.add(tmp);
            }
        }

        return massnList;
    }

    /**
     * Checks, if the given massnahme or massnahmen_gruppe is already assigned to the given fgsk bean.
     *
     * @param   massn       DOCUMENT ME!
     * @param   simulation  DOCUMENT ME!
     * @param   fgsk        DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean isMassnGroupContained(final CidsBean massn, final CidsBean simulation, final CidsBean fgsk) {
        final List<CidsBean> simList = simulation.getBeanCollectionProperty(SIMULATIONSMASSNAHMEN_PROPERTY);
        final boolean isGroup = massn.getClass().getName().equals(SIM_MASSNAHMEN_GRUPPE_CLASS_NAME);
        final int fgsk_id = (Integer)fgsk.getProperty("id");

        for (final CidsBean simMassn : simList) {
            if (isGroup) {
                final Object simMassnGr = simMassn.getProperty(MASSNAHME_PROPERTY);
                if ((simMassnGr != null) && simMassn.getProperty(FGSK_KA_PROPERTY).equals(fgsk_id)
                            && simMassnGr.equals(massn.getProperty("id"))) {
                    return true;
                }
            } else if (!isGroup) {
                final Object simEinzelMassn = simMassn.getProperty(EINZEL_MASSNAHME_PROPERTY);
                if ((simEinzelMassn != null) && simMassn.getProperty(FGSK_KA_PROPERTY).equals(fgsk_id)
                            && simEinzelMassn.equals(massn.getProperty("id"))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Adds the given massnahmen object for the given fgsk to the simulation. If the massnahmen object is already
     * assigned to the fgsk object, nothing will happen.
     *
     * @param  simulationBean  DOCUMENT ME!
     * @param  fgsk            DOCUMENT ME!
     * @param  massn           DOCUMENT ME!
     * @param  complete        DOCUMENT ME!
     */
    public static void addMassnahme(final CidsBean simulationBean,
            final CidsBean fgsk,
            final CidsBean massn,
            final Boolean complete) {
        if (FgskSimulationHelper.isMassnGroupContained(massn, simulationBean, fgsk)) {
            return;
        }
        try {
            final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("sim_massnahmen_anwendungen");
            newBean.setProperty(FGSK_KA_PROPERTY, fgsk.getProperty("id"));

            if (massn.getClass().getName().equals(SIM_MASSNAHMEN_GRUPPE_CLASS_NAME)) {
                newBean.setProperty(MASSNAHME_PROPERTY, massn.getProperty("id"));
            } else if (massn.getClass().getName().equals(SIM_MASSNAHME_CLASS_NAME)) {
                newBean.setProperty(EINZEL_MASSNAHME_PROPERTY, massn.getProperty("id"));
            }

            if ((complete != null) && complete) {
                newBean.setProperty("complete", true);
            } else {
                newBean.setProperty("complete", false);
            }

            simulationBean.getBeanCollectionProperty(SIMULATIONSMASSNAHMEN_PROPERTY).add(newBean);
        } catch (Exception e) {
            LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
        }
    }

    /**
     * Determines the custom costs of the given fgsk object.
     *
     * @param   simulationBean  DOCUMENT ME!
     * @param   fgsk            DOCUMENT ME!
     *
     * @return  the custom costs of the given fgsk object
     */
    public static CidsBean getCustomCostsBeanForFgsk(final CidsBean simulationBean, final CidsBean fgsk) {
        final Integer fgskId = (Integer)fgsk.getProperty("id");
        final List<CidsBean> angMassn = simulationBean.getBeanCollectionProperty(
                FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);

        for (final CidsBean massn : angMassn) {
            if (massn.getProperty(FgskSimulationHelper.FGSK_KA_PROPERTY).equals(fgskId)) {
                final Double costs = (Double)massn.getProperty(FgskSimulationHelper.CUSTOM_COSTS_PROPERTY);

                if (costs != null) {
                    return massn;
                }
            }
        }

        return null;
    }

    /**
     * Set the given custom costs in the given fgsk object. The custom costs will be removed, if costs == null.
     *
     * @param  simulationBean  the simulation bean that contains the given fgsk object
     * @param  fgsk            the fgsk object, the costs should be assigned to
     * @param  costs           the costs to set
     */
    public static void setCustomCostsForFgsk(final CidsBean simulationBean,
            final CidsBean fgsk,
            final Double costs) {
        try {
            final CidsBean customCostsBean = FgskSimulationHelper.getCustomCostsBeanForFgsk(simulationBean, fgsk);

            if (customCostsBean != null) {
                // there are already custom costs for the given fgsk
                if (costs != null) {
                    customCostsBean.setProperty(CUSTOM_COSTS_PROPERTY, costs);
                } else {
                    final List<CidsBean> angMassn = simulationBean.getBeanCollectionProperty(
                            FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);
                    angMassn.remove(customCostsBean);
                }
            } else if (costs != null) {
                final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("sim_massnahmen_anwendungen");
                newBean.setProperty(FGSK_KA_PROPERTY, fgsk.getProperty("id"));
                newBean.setProperty("complete", true);
                newBean.setProperty(CUSTOM_COSTS_PROPERTY, costs);
                simulationBean.getBeanCollectionProperty(SIMULATIONSMASSNAHMEN_PROPERTY).add(newBean);
            }
        } catch (Exception e) {
            LOG.error("error while changing the given costs", e);
        }
    }

    /**
     * Fills the given object with the planned massnahmen objects.
     *
     * @param   simulationBean  DOCUMENT ME!
     * @param   wkFg            DOCUMENT ME!
     * @param   realisierung    wkfgCache
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean fillMassnSimulation(final CidsBean simulationBean,
            final String wkFg,
            final CidsBean realisierung) {
        try {
            final List in = new ArrayList(1);
            in.add(wkFg);
            final SimulationEditor.FgskCalculator fgskCalc = new SimulationEditor.FgskCalculator();
            final MetaObject[] fgskMos = fgskCalc.calculate(in);
            simulationBean.setProperty("wk_key", wkFg);
            simulationBean.setProperty("realisierung", realisierung);
            simulationBean.setProperty("read_only", true);
            final Map<CidsBean, List<CidsBean>> massnMap = new HashMap<CidsBean, List<CidsBean>>();
            simulationBean.getBeanCollectionProperty(SIMULATIONSMASSNAHMEN_PROPERTY).clear();

            if ((fgskMos != null) && (fgskMos.length > 0)) {
                final SimulationEditor.MassnBvpCalculator massnCalc = new SimulationEditor.MassnBvpCalculator();
                in.add(realisierung.getProperty("value"));
                final MetaObject[] massnahmenMos = massnCalc.calculate(in);

                for (final MetaObject mo : massnahmenMos) {
                    final CidsBean maBean = mo.getBean();
                    final List<CidsBean> MassnBeans = getSimMassnBeanFromMassnBvp(maBean);

                    if ((MassnBeans != null) && !MassnBeans.isEmpty()) {
                        final CidsBean stationLine = (CidsBean)maBean.getProperty("linie");
                        final double from = Math.min((Double)stationLine.getProperty("von.wert"),
                                (Double)stationLine.getProperty("bis.wert"));
                        final double to = Math.max((Double)stationLine.getProperty("von.wert"),
                                (Double)stationLine.getProperty("bis.wert"));
                        final Long gwk = (Long)stationLine.getProperty("von.route.gwk");

                        final List<CidsBean> fgskList = getAllCorrespondingFgsk(from, to, gwk, fgskMos);

                        for (final CidsBean fgsk : fgskList) {
                            for (final CidsBean massn : MassnBeans) {
                                final double percentage = FgskSimulationHelper.determineFgskIntersectionPercentage(
                                        fgsk,
                                        from,
                                        to,
                                        gwk);
                                final boolean complete = percentage == 100.0;
                                addMassnahme(simulationBean, fgsk, massn, complete);
                                if (massnMap.get(fgsk) == null) {
                                    massnMap.put(fgsk, new ArrayList<CidsBean>());
                                }
                                massnMap.get(fgsk).add(massn);
                            }
                        }
                    }
                }

                final List<CidsBean> classList = simulationBean.getBeanCollectionProperty("klassen");
                classList.clear();

                for (final MetaObject fgsk : fgskMos) {
                    final CidsBean calcClass = CidsBeanSupport.createNewCidsBeanFromTableName("sim_berechnete_klasse");
                    classList.add(calcClass);
                    final CidsBean fgskBean = fgsk.getBean();
                    calcClass.setProperty(FGSK_KA_PROPERTY, fgskBean.getProperty("id"));
                    Double p = null;

                    try {
                        p = SimSimulationsabschnittEditor.calc(fgskBean, massnMap.get(fgskBean), false, null);
                    } catch (Exception e) {
                        LOG.warn("Cannot calculate fgsk kartierabschnitt", e);
                    }
                    final CidsBean exception = (CidsBean)fgskBean.getProperty(Calc.PROP_EXCEPTION);
                    Integer cl = null;

                    if ((exception != null) && Integer.valueOf(1).equals(exception.getProperty(Calc.PROP_VALUE))) {
                        cl = 5;
                    } else if ((p != null) && (p > 0.0)) {
                        cl = CalcCache.getQualityClass(p);
                    }

                    calcClass.setProperty("klasse", cl);
                }

                return simulationBean;
            }
        } catch (Exception e) {
            LOG.error("Error while creating fgsk simulation", e);
        }

        return null;
    }

    /**
     * Determines the simulation massnahme for the given massname BVP.
     *
     * @param   massnBvpBean  DOCUMENT ME!
     *
     * @return  The simulation massnahme for the given massname BVP
     */
    public static List<CidsBean> getSimMassnBeanFromMassnBvp(final CidsBean massnBvpBean) {
        final List<CidsBean> result = new ArrayList<CidsBean>();
        final CidsBean massnSchluessel = (CidsBean)massnBvpBean.getProperty("massnahmen_schluessel");

        if (massnSchluessel != null) {
            try {
                final String fgsk_nr = (String)massnSchluessel.getProperty("fgsk_nr");

                if (fgsk_nr != null) {
                    final MetaObject[] mos = simMassnahmenCache.calcValue(Collections.nCopies(1, fgsk_nr));

                    if (mos != null) {
                        for (final MetaObject mo : mos) {
                            result.add(mo.getBean());
                        }
                    }
                }
            } catch (Exception e) {
                LOG.warn("mnt is not a number", e);
            }
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   from   DOCUMENT ME!
     * @param   to     DOCUMENT ME!
     * @param   gwk    DOCUMENT ME!
     * @param   fgsks  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static List<CidsBean> getAllCorrespondingFgsk(final double from,
            final double to,
            final Long gwk,
            final MetaObject[] fgsks) {
        final List<CidsBean> resultList = new ArrayList<CidsBean>();

        for (final MetaObject mo : fgsks) {
            if (determineFgskIntersectionPercentage(mo.getBean(), from, to, gwk) > 50.0) {
                resultList.add(mo.getBean());
            }
        }

        return resultList;
    }

    /**
     * Determines how many percent of the fgsk line intersects the given line.
     *
     * @param   fgskBean  DOCUMENT ME!
     * @param   from      DOCUMENT ME!
     * @param   to        DOCUMENT ME!
     * @param   gwk       DOCUMENT ME!
     *
     * @return  the percenage of length of the intersection line from the total length of the fgsk line
     */
    public static double determineFgskIntersectionPercentage(final CidsBean fgskBean,
            final double from,
            final double to,
            final Long gwk) {
        if (fgskBean.getProperty("linie.von.route.gwk").equals(gwk)) {
            final double fgskFrom = Math.min((Double)fgskBean.getProperty("linie.von.wert"),
                    (Double)fgskBean.getProperty("linie.bis.wert"));
            final double fgskTo = Math.max((Double)fgskBean.getProperty("linie.von.wert"),
                    (Double)fgskBean.getProperty("linie.bis.wert"));

            final double intersectionFrom = Math.max(fgskFrom, from);
            final double intersectionTo = Math.min(fgskTo, to);
            double intersectionLength = intersectionTo - intersectionFrom;

            if (intersectionLength < 0) {
                // there is no intersection
                intersectionLength = 0;
            }

            if ((intersectionTo - intersectionFrom) == 0) {
                LOG.warn("FGSK Kartierabschnitt with a length of 0 found.");
                return 0.0;
            }

            return intersectionLength * 100 / (fgskTo - fgskFrom);
        }

        return 0.0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getSimMassnahmeById(final Integer id) {
        if (id == null) {
            return null;
        } else {
            return massnBeanMap.get(id);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getSimMassnahmeGruppeById(final Integer id) {
        if (id == null) {
            return null;
        } else {
            return massnGruppeBeanMap.get(id);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk               DOCUMENT ME!
     * @param  realisierung2027  DOCUMENT ME!
     */
    public static void reCreateSimulation(final String wkk, final boolean realisierung2027) {
        try {
            final int realisierung = (realisierung2027 ? 2027 : 2021);
            CidsBean realisierungBean = realisierungMap.get(realisierung);

            if (realisierungBean == null) {
                final String real_query = "SELECT " + REALISIERUNG_MC.getID() + ",  " + REALISIERUNG_MC.getPrimaryKey()
                            + " FROM "
                            + REALISIERUNG_MC.getTableName()
                            + " WHERE value =" + realisierung;
                final MetaObject[] realMeta = SessionManager.getProxy().getMetaObjectByQuery(real_query, 0);

                if ((realMeta != null) && (realMeta.length > 0)) {
                    realisierungBean = realMeta[0].getBean();
                    realisierungMap.put(realisierung, realisierungBean);
                }
            }

            final String query = "SELECT " + SIMULATION_MC.getID() + ",  s." + SIMULATION_MC.getPrimaryKey() + " FROM "
                        + SIMULATION_MC.getTableName() + " s, massnahmen_realisierung mr"
                        + " WHERE mr.id = s.realisierung and s.wk_key = '" + wkk
                        + "' and read_only = true and s.realisierung = mr.id and mr.value = " + realisierung;

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for massnahmen: " + query);
            }
//            final long start = System.currentTimeMillis();
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
//            LOG.error("Zeit zum Laden: " + (System.currentTimeMillis() - start));

            final CidsBean simulationBean;

            if ((metaObjects != null) && (metaObjects.length > 0)) {
                simulationBean = metaObjects[0].getBean();
            } else {
                simulationBean = CidsBeanSupport.createNewCidsBeanFromTableName("simulation");
                simulationBean.setProperty("name", "Simulation " + realisierung);
                simulationBean.setProperty(
                    "beschreibung",
                    "Automatisch erstellte Simulation der geplanten Maßnahmen bis "
                            + realisierung
                            + ".");
            }

            fillMassnSimulation(simulationBean, wkk, realisierungBean);

            simulationBean.persist();
        } catch (Exception e) {
            LOG.error("Cannot recreate simulationBean", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public static void createAllSimulations() {
        try {
//            final String wkk = "ROEG-0300";
//            reCreateSimulation(wkk, false);
//            reCreateSimulation(wkk, true);
            final CidsServerSearch search = new WkFgWkkSearch();
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);

            if (resArray != null) {
                int count = 0;

                for (final ArrayList wkfgArray : resArray) {
                    final String wkk = (String)wkfgArray.get(0);
                    LOG.error("wkk: " + wkk + " " + (++count) + "/" + resArray.size());
                    if (count > 443) {
                        reCreateSimulation(wkk, false);
                        reCreateSimulation(wkk, true);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error while creating all default simulations.", e);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class SimMassnCalculator implements Calculator<List, MetaObject[]> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "SELECT " + SIM_MASSNAHME_MC.getID() + ",  " + SIM_MASSNAHME_MC.getPrimaryKey()
                        + " FROM "
                        + SIM_MASSNAHME_MC.getTableName() + " WHERE key = '"
                        + String.valueOf(input.get(0)) + "';";

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for sim_massnahmen: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class AllBeansOfTypeCalculator implements Calculator<MetaClass, MetaObject[]> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   mc  input den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final MetaClass mc) throws Exception {
            final String query = "SELECT " + mc.getID() + ",  " + mc.getPrimaryKey()
                        + " FROM "
                        + mc.getTableName();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for calculate: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            return metaObjects;
        }
    }
}
