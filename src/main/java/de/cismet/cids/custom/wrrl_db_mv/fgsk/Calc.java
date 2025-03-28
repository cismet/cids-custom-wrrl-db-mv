/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

import java.util.Collection;
import java.util.Iterator;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache.BankContaminationType;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache.BankStructureType;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache.BedContaminationType;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache.BedStructureType;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache.BedSubtrateType;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.Equals;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class Calc {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_BED_SUBSTRATE_PREFIX = "PROP_BED_SUBSTRATE_";                         // NOI18N
    public static final String PROP_LAND_USE_RI = "flaechennutzung_rechts_id";                            // NOI18N
    public static final String PROP_LAND_USE_LE = "flaechennutzung_links_id";                             // NOI18N
    public static final String PROP_LINE = "linie";                                                       // NOI18N
    public static final String PROP_TO = "bis";                                                           // NOI18N
    public static final String PROP_FROM = "von";                                                         // NOI18N
    public static final String PROP_UPPER_PROFILE_BREADTH = "obere_profilbreite";                         // NOI18N
    public static final String PROP_WERT = "wert";                                                        // NOI18N
    public static final String PROP_VALUE = "value";                                                      // NOI18N
    public static final String PROP_WB_TRIMMING_RI = "gewaesserrandstreifen_rechts_id";                   // NOI18N
    public static final String PROP_WB_TRIMMING_LE = "gewaesserrandstreifen_links_id";                    // NOI18N
    public static final String PROP_WB_ENV_SUM_RATING = "gewaesserumfeld_summe_punktzahl";                // NOI18N
    public static final String PROP_WB_ENV_SUM_CRIT = "gewaesserumfeld_anzahl_kriterien";                 // NOI18N
    public static final String PROP_WB_ENV_SUM_RATING_LE = "gewaesserumfeld_summe_punktzahl_links";       // NOI18N
    public static final String PROP_WB_ENV_SUM_CRIT_LE = "gewaesserumfeld_anzahl_kriterien_links";        // NOI18N
    public static final String PROP_WB_ENV_SUM_RATING_RI = "gewaesserumfeld_summe_punktzahl_rechts";      // NOI18N
    public static final String PROP_WB_ENV_SUM_CRIT_RI = "gewaesserumfeld_anzahl_kriterien_rechts";       // NOI18N
    public static final String PROP_WB_TYPE = "gewaessertyp_id";                                          // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_AG_LE = "s_umfeldstrukturen_ag_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_AG_RI = "s_umfeldstrukturen_ag_rechts";                // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_FT_LE = "s_umfeldstrukturen_ft_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_FT_RI = "s_umfeldstrukturen_ft_rechts";                // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_GUA_LE = "s_umfeldstrukturen_gua_links";               // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_GUA_RI = "s_umfeldstrukturen_gua_rechts";              // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_BV_LE = "s_umfeldstrukturen_bv_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_BV_RI = "s_umfeldstrukturen_bv_rechts";                // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_MA_LE = "s_umfeldstrukturen_ma_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_MA_RI = "s_umfeldstrukturen_ma_rechts";                // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_HW_LE = "s_umfeldstrukturen_hw_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_HW_RI = "s_umfeldstrukturen_hw_rechts";                // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_SO_LE = "s_umfeldstrukturen_so_links";                 // NOI18N
    public static final String PROP_BAD_ENV_STRUCT_SO_RI = "s_umfeldstrukturen_so_rechts";                // NOI18N
    public static final String PROP_FLOW_VELOCITY = "fliessgeschwindigkeit_id";                           // NOI18N
    public static final String PROP_FLOW_DIVERSITY = "stroemungsdiversitaet_id";                          // NOI18N
    public static final String PROP_DEPTH_VARIANCE = "tiefenvarianz_id";                                  // NOI18N
    public static final String PROP_DEPTH_EROSION = "tiefenerosion_id";                                   // NOI18N
    public static final String PROP_CROSS_BENCH_COUNT = "querbaenke_anzahl";                              // NOI18N
    public static final String PROP_LONG_PROFILE_SUM_RATING = "laengsprofil_summe_punktzahl";             // NOI18N
    public static final String PROP_LONG_PROFILE_SUM_CRIT = "laengsprofil_anzahl_kriterien";              // NOI18N
    public static final String PROP_COURSE_LOOP = "laufkruemmung_id";                                     // NOI18N
    public static final String PROP_LOOP_EROSION = "kruemmungserosion_id";                                // NOI18N
    public static final String PROP_LONG_BENCH_UFKG = "laengsbaenke_ufkg";                                // NOI18N
    public static final String PROP_LONG_BENCH_IB = "laengsbaenke_ib";                                    // NOI18N
    public static final String PROP_LONG_BENCH_MB = "laengsbaenke_mb";                                    // NOI18N
    public static final String PROP_COURSE_STRUCTURE_TV = "laufstrukturen_tv";                            // NOI18N
    public static final String PROP_COURSE_STRUCTURE_SB = "laufstrukturen_sb";                            // NOI18N
    public static final String PROP_COURSE_STRUCTURE_IBI = "laufstrukturen_ibi";                          // NOI18N
    public static final String PROP_COURSE_STRUCTURE_LW = "laufstrukturen_lw";                            // NOI18N
    public static final String PROP_COURSE_STRUCTURE_LV = "laufstrukturen_lv";                            // NOI18N
    public static final String PROP_COURSE_STRUCTURE_LG = "laufstrukturen_lg";                            // NOI18N
    public static final String PROP_COURSE_EVO_SUM_RATING = "laufentwicklung_summe_punktzahl";            // NOI18N
    public static final String PROP_COURSE_EVO_SUM_CRIT = "laufentwicklung_anzahl_kriterien";             // NOI18N
    public static final String PROP_PROFILE_TYPE = "profiltyp_id";                                        // NOI18N
    public static final String PROP_BREADTH_VARIANCE = "breitenvarianz_id";                               // NOI18N
    public static final String PROP_BREADTH_EROSION = "breitenerosion_id";                                // NOI18N
    public static final String PROP_INCISION_DEPTH = "einschnitttiefe";                                   // NOI18N
    public static final String PROP_WATER_DEPTH = "wassertiefe";                                          // NOI18N
    public static final String PROP_CROSS_PROFILE_SUM_RATING = "querprofil_summe_punktzahl";              // NOI18N
    public static final String PROP_CROSS_PROFILE_SUM_CRIT = "querprofil_anzahl_kriterien";               // NOI18N
    public static final String PROP_BED_FITMENT = "sohlenverbau_id";                                      // NOI18N
    public static final String PROP_Z_BED_FITMENT = "z_sohlenverbau_id";                                  // NOI18N
    public static final String PROP_WB_SUB_TYPE = "gewaessersubtyp";                                      // NOI18N
    public static final String PROP_BED_SUBSTRATE_TON = "sohlensubstrat_ton";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_SAN = "sohlensubstrat_san";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_KIE = "sohlensubstrat_kie";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_STE = "sohlensubstrat_ste";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_BLO = "sohlensubstrat_blo";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_SCH = "sohlensubstrat_sch";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_TOR = "sohlensubstrat_tor";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_TOT = "sohlensubstrat_tot";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_WUR = "sohlensubstrat_wur";                             // NOI18N
    public static final String PROP_BED_SUBSTRATE_KUE = "sohlensubstrat_kue";                             // NOI18N
    public static final String PROP_BED_STRUCTURE_RIP = "sohlenstrukturen_rip";                           // NOI18N
    public static final String PROP_BED_STRUCTURE_TH = "sohlenstrukturen_th";                             // NOI18N
    public static final String PROP_BED_STRUCTURE_WU = "sohlenstrukturen_wu";                             // NOI18N
    public static final String PROP_BED_STRUCTURE_KO = "sohlenstrukturen_ko";                             // NOI18N
    public static final String PROP_BED_CONTAMINATION_MUE = "belastung_sohle_mue";                        // NOI18N
    public static final String PROP_BED_CONTAMINATION_ST = "belastung_sohle_st";                          // NOI18N
    public static final String PROP_BED_CONTAMINATION_ABW = "belastung_sohle_abw";                        // NOI18N
    public static final String PROP_BED_CONTAMINATION_VO = "belastung_sohle_vo";                          // NOI18N
    public static final String PROP_BED_CONTAMINATION_SA = "belastung_sohle_sa";                          // NOI18N
    public static final String PROP_BED_CONTAMINATION_SO = "belastung_sohle_so";                          // NOI18N
    public static final String PROP_BED_STRUCTURE_SUM_RATING = "sohlenstruktur_summe_punktzahl";          // NOI18N
    public static final String PROP_BED_STRUCTURE_SUM_CRIT = "sohlenstruktur_anzahl_kriterien";           // NOI18N
    public static final String PROP_BANK_VEGETATION_LE = "ufervegetation_links_id";                       // NOI18N
    public static final String PROP_BANK_VEGETATION_RI = "ufervegetation_rechts_id";                      // NOI18N
    public static final String PROP_BANK_VEGETATION_TYPICAL_LE = "ufervegetation_links_typical";          // NOI18N
    public static final String PROP_BANK_VEGETATION_TYPICAL_RI = "ufervegetation_rechts_typical";         // NOI18N
    public static final String PROP_BANK_FITMENT_LE = "uferverbau_links_id";                              // NOI18N
    public static final String PROP_BANK_FITMENT_RI = "uferverbau_rechts_id";                             // NOI18N
    public static final String PROP_Z_BANK_FITMENT_LE = "z_uferverbau_links_id";                          // NOI18N
    public static final String PROP_Z_BANK_FITMENT_RI = "z_uferverbau_rechts_id";                         // NOI18N
    public static final String PROP_BANK_STRUCTURE_BU_LE = "uferstruktur_bu_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_BU_RI = "uferstruktur_bu_rechts";                      // NOI18N
    public static final String PROP_BANK_STRUCTURE_PB_LE = "uferstruktur_pb_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_PB_RI = "uferstruktur_pb_rechts";                      // NOI18N
    public static final String PROP_BANK_STRUCTURE_US_LE = "uferstruktur_us_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_US_RI = "uferstruktur_us_rechts";                      // NOI18N
    public static final String PROP_BANK_STRUCTURE_SB_LE = "uferstruktur_sb_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_SB_RI = "uferstruktur_sb_rechts";                      // NOI18N
    public static final String PROP_BANK_STRUCTURE_HA_LE = "uferstruktur_ha_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_HA_RI = "uferstruktur_ha_rechts";                      // NOI18N
    public static final String PROP_BANK_STRUCTURE_NBOE_LE = "uferstruktur_nboe_links";                   // NOI18N
    public static final String PROP_BANK_STRUCTURE_NBOE_RI = "uferstruktur_nboe_rechts";                  // NOI18N
    public static final String PROP_BANK_STRUCTURE_SO_LE = "uferstruktur_so_links";                       // NOI18N
    public static final String PROP_BANK_STRUCTURE_SO_RI = "uferstruktur_so_rechts";                      // NOI18N
    public static final String PROP_BANK_CONTAMINATION_MUE_LE = "uferbelastungen_mue_links";              // NOI18N
    public static final String PROP_BANK_CONTAMINATION_MUE_RI = "uferbelastungen_mue_rechts";             // NOI18N
    public static final String PROP_BANK_CONTAMINATION_ST_LE = "uferbelastungen_st_links";                // NOI18N
    public static final String PROP_BANK_CONTAMINATION_ST_RI = "uferbelastungen_st_rechts";               // NOI18N
    public static final String PROP_BANK_CONTAMINATION_TS_LE = "uferbelastungen_ts_links";                // NOI18N
    public static final String PROP_BANK_CONTAMINATION_TS_RI = "uferbelastungen_ts_rechts";               // NOI18N
    public static final String PROP_BANK_CONTAMINATION_EL_LE = "uferbelastungen_el_links";                // NOI18N
    public static final String PROP_BANK_CONTAMINATION_EL_RI = "uferbelastungen_el_rechts";               // NOI18N
    public static final String PROP_BANK_CONTAMINATION_SO_LE = "uferbelastungen_so_links";                // NOI18N
    public static final String PROP_BANK_CONTAMINATION_SO_RI = "uferbelastungen_so_rechts";               // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_RATING = "uferstruktur_summe_punktzahl";           // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_CRIT = "uferstruktur_anzahl_kriterien";            // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_RATING_LE = "uferstruktur_summe_punktzahl_links";  // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_CRIT_LE = "uferstruktur_anzahl_kriterien_links";   // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_RATING_RI = "uferstruktur_summe_punktzahl_rechts"; // NOI18N
    public static final String PROP_BANK_STRUCTURE_SUM_CRIT_RI = "uferstruktur_anzahl_kriterien_rechts";  // NOI18N
    public static final String PROP_WB_OVERALL_RATING = "punktzahl_gesamt";                               // NOI18N
    public static final String PROP_WB_BED_RATING = "punktzahl_sohle";                                    // NOI18N
    public static final String PROP_WB_BANK_RATING = "punktzahl_ufer";                                    // NOI18N
    public static final String PROP_WB_BANK_RATING_LE = "punktzahl_ufer_links";                           // NOI18N
    public static final String PROP_WB_BANK_RATING_RI = "punktzahl_ufer_rechts";                          // NOI18N
    public static final String PROP_WB_ENV_RATING = "punktzahl_land";                                     // NOI18N
    public static final String PROP_WB_ENV_RATING_LE = "punktzahl_land_links";                            // NOI18N
    public static final String PROP_WB_ENV_RATING_RI = "punktzahl_land_rechts";                           // NOI18N
    public static final String PROP_EXCEPTION = "sonderfall_id";                                          // NOI18N

    // Variables used in Simulation
    public static final String PROP_SIM_FLOW_DIV_RATING = "bewertung_stroemungsdiversitaet";                      // NOI18N
    public static final String PROP_SIM_CROSS_BENCH_COUNT_RATING = "bewertung_anzahl_querbaenke";                 // NOI18N
    public static final String PROP_SIM_LONG_BENCH_COUNT_RATING = "bewertung_anzahl_laengsbaenke";                // NOI18N
    public static final String PROP_SIM_BREADTH_VARIANCE_RATING = "bewertung_breitenvarianz";                     // NOI18N
    public static final String PROP_SIM_BED_STRUCTURE_COUNT_RATING = "bewertung_anzahl_besondere_sohlstrukturen"; // NOI18N

    static final CalcCache cache = CalcCache.getInstance();
    private static final transient Logger LOG = Logger.getLogger(Calc.class);

    //~ Instance fields --------------------------------------------------------

    private final transient int scale;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Calc object.
     */
    private Calc() {
        scale = 12;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Calc getInstance() {
        return LazyInitializer.INSTANCE;
    }

    /**
     * Calls {@link #calcWBEnvRating(de.cismet.cids.dynamics.CidsBean, boolean)} twice, for the left and the right side.
     * The overall result is the sum of the partial results.
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException    DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public void calcWBEnvRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        calcWBEnvRating(kaBean, true);
        calcWBEnvRating(kaBean, false);

        final Double ratingLeft = (Double)kaBean.getProperty(PROP_WB_ENV_SUM_RATING_LE);
        final Integer criteriaCountLeft = (Integer)kaBean.getProperty(PROP_WB_ENV_SUM_CRIT_LE);
        final Double ratingRight = (Double)kaBean.getProperty(PROP_WB_ENV_SUM_RATING_RI);
        final Integer criteriaCountRight = (Integer)kaBean.getProperty(PROP_WB_ENV_SUM_CRIT_RI);

        double rating = ratingLeft + ratingRight;
        // rating correction according to Kuechler, not present in original implementation
        if (rating < 1) {
            rating = 1;
        }

        // set the final values
        try {
            kaBean.setProperty(PROP_WB_ENV_SUM_RATING, rating);
            kaBean.setProperty(PROP_WB_ENV_SUM_CRIT, criteriaCountLeft + criteriaCountRight);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ValidationException       DOCUMENT ME!
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    private boolean isException(final CidsBean kaBean) throws ValidationException {
        if (kaBean == null) {
            throw new IllegalArgumentException("cidsBean must not be null"); // NOI18N
        }

        final CidsBean exception = (CidsBean)kaBean.getProperty(PROP_EXCEPTION);
        if ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(PROP_VALUE))) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("aborting calculation, because the kartierabschnitt is an exception: " + kaBean); // NOI18N
            }

            throw new ValidationException("calculation for exceptional kartierabschnitte is illegal", true); // NOI18N
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     * @param   left    DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcWBEnvRating(final CidsBean kaBean, final boolean left) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        fieldFromCode("PROP_LAND_USE", "", left), // NOI18N
                        fieldFromCode("PROP_WB_TRIMMING", "", left))) { // NOI18N
            throw new ValidationException("the waterbody environment properties contain null values"); // NOI18N
        }

        final CidsBean exception = (CidsBean)kaBean.getProperty(PROP_EXCEPTION);
        if ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(PROP_VALUE))) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("aborting calculation, because the kartierabschnitt is an exception: " + kaBean); // NOI18N
            }

            return;
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();
        final Integer ratingWBTriming = getWBTrimmingRating(wbTypeId, kaBean, left);
        final Integer ratingLandUse = getLandUseRating(wbTypeId, kaBean, left);
        final double badEnvRating = getBadEnvRating(wbTypeId, kaBean, left);

        // this operation changes the given rating and count values directly
        final RatingStruct rating = new RatingStruct();
        overallRating(rating, true, ratingWBTriming, ratingLandUse);
        overallRating(rating, false, badEnvRating);

        // set the final values
        try {
            kaBean.setProperty(fieldFromCode("PROP_WB_ENV_SUM_RATING", "", left), rating.rating);      // NOI18N
            kaBean.setProperty(fieldFromCode("PROP_WB_ENV_SUM_CRIT", "", left), rating.criteriaCount); // NOI18N
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean;                             // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     * @param   kaBean    DOCUMENT ME!
     * @param   left      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBadEnvRating(final int wbTypeId, final CidsBean kaBean, final boolean left) {
        double badEnvRating = 0;
        for (final CalcCache.BadEnvStructureType type : CalcCache.BadEnvStructureType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode(
                        "PROP_BAD_ENV_STRUCT_", // NOI18N
                        type.getCode(),
                        left));
            if ((count != null) && (count > 0)) {
                final Double rating = cache.getBadEnvStructureRating(type, wbTypeId);

                if (rating == null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("ignoring rating for type: " + type + " | wbtypeId: " + wbTypeId); // NOI18N
                    }
                } else {
                    badEnvRating += rating;
                }
            }
        }
        badEnvRating = correctBadEnvRating(badEnvRating, wbTypeId);

        return badEnvRating;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     * @param   kaBean    DOCUMENT ME!
     * @param   left      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBadEnvCount(final int wbTypeId, final CidsBean kaBean, final boolean left) {
        double badEnvCount = 0.0;
        for (final CalcCache.BadEnvStructureType type : CalcCache.BadEnvStructureType.values()) {
            if (type.getId() != 7) {
                // die sonstigen sollen ignoriert werden, da diese Teilparameter nicht in die Berechnung
                // der Güteklassen eingehen (rein informativer Charakter)
                final Double count = (Double)kaBean.getProperty(fieldFromCode(
                            "PROP_BAD_ENV_STRUCT_", // NOI18N
                            type.getCode(),
                            left));
                if ((count != null) && (count > 0)) {
                    badEnvCount += count;
                }
            }
        }

        return badEnvCount;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     * @param   kaBean    DOCUMENT ME!
     * @param   left      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLandUseRating(final int wbTypeId, final CidsBean kaBean, final boolean left) {
        final CidsBean landUseBean = (CidsBean)kaBean.getProperty(fieldFromCode("PROP_LAND_USE", "", left)); // NOI18N

        return cache.getWBLandUseRating(landUseBean.getMetaObject().getId(), wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     * @param   kaBean    DOCUMENT ME!
     * @param   left      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getWBTrimmingRating(final int wbTypeId, final CidsBean kaBean, final boolean left) {
        final CidsBean wbTrimmingBean = (CidsBean)kaBean.getProperty(fieldFromCode("PROP_WB_TRIMMING", "", left)); // NOI18N

        return cache.getWBTrimmingRating(wbTrimmingBean.getMetaObject().getId(), wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcWBLongProfileRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        PROP_FLOW_VELOCITY,
                        PROP_FLOW_DIVERSITY,
                        PROP_DEPTH_VARIANCE,
                        PROP_DEPTH_EROSION,
                        PROP_CROSS_BENCH_COUNT)) {
            throw new ValidationException("the waterbody long profile properties contain null values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();
        final double crossBenchCount = (Double)kaBean.getProperty(PROP_CROSS_BENCH_COUNT); // NOI18N
        final Double sectionLength = cache.getCrossBenchSectionLength(wbTypeId);
        final double stationLength = getStationLength(kaBean);

        final Double absCrossBenchCount;
        if (crossBenchCount == 0.5) {
            absCrossBenchCount = crossBenchCount;
        } else if (sectionLength == null) {
            absCrossBenchCount = null;
        } else {
            // we cannot use Math.round here since it behaves slightly different. Math.round will round .5 up where as
            // the fgsk round will round this down.
            absCrossBenchCount = (double)round(round(crossBenchCount / (stationLength / sectionLength), scale));
        }

        final CidsBean flowDiversityBean = (CidsBean)kaBean.getProperty(PROP_FLOW_DIVERSITY);
        final CidsBean depthVarianceBean = (CidsBean)kaBean.getProperty(PROP_DEPTH_VARIANCE);
        final CidsBean flowVelocityBean = (CidsBean)kaBean.getProperty(PROP_FLOW_VELOCITY);

        final Integer ratingCrossBench = cache.getCrossBenchRating(absCrossBenchCount, wbTypeId);
        final Integer ratingFlowDiversity = cache.getFlowDiversityRating(flowDiversityBean.getMetaObject().getId(),
                wbTypeId);
        final Integer ratingFlowVelocity = cache.getFlowVelocityRating(flowVelocityBean.getMetaObject().getId(),
                wbTypeId);
        final Integer ratingDepthVariance = cache.getDepthVarianceRating(depthVarianceBean.getMetaObject().getId(),
                wbTypeId);

        final RatingStruct rating = new RatingStruct();

        // this operation changes the given rating and count values directly
        overallRating(rating, true, ratingCrossBench, ratingFlowDiversity, ratingFlowVelocity, ratingDepthVariance);

        // set the final values
        try {
            kaBean.setProperty(PROP_LONG_PROFILE_SUM_RATING, rating.rating);
            kaBean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, rating.criteriaCount);
            kaBean.setProperty(PROP_SIM_FLOW_DIV_RATING, ratingFlowDiversity);
            kaBean.setProperty(PROP_SIM_CROSS_BENCH_COUNT_RATING, ratingCrossBench);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcCourseEvoRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (!propsNotNull(
                        kaBean,
                        PROP_COURSE_LOOP,
                        PROP_LOOP_EROSION)) {
            throw new ValidationException("the waterbody course evolution properties contain null values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        double longBenchSum = 0;
        for (final CalcCache.LongBenchType type : CalcCache.LongBenchType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode("PROP_LONG_BENCH_", type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                longBenchSum += count;
            }
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();
        final double stationLength = getStationLength(kaBean);
        final Double longBenchSectionLength = cache.getLongBenchSectionLength(wbTypeId);

        final Double absLongBenchSum;
        if (longBenchSum == 0.5) {
            absLongBenchSum = longBenchSum;
        } else if (longBenchSectionLength == null) {
            absLongBenchSum = null;
        } else {
            absLongBenchSum = (double)round(round(longBenchSum / (stationLength / longBenchSectionLength), scale));
        }

        double courseStructureSum = 0;
        for (final CalcCache.CourseStructureType type : CalcCache.CourseStructureType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode("PROP_COURSE_STRUCTURE_", type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                courseStructureSum += count;
            }
        }

        final Double courseStructureSectionLength = cache.getCourseStructureSectionLength(wbTypeId);

        final Double absCourseStructureSum;
        if (courseStructureSum == 0.5) {
            absCourseStructureSum = courseStructureSum;
        } else if (courseStructureSectionLength == null) {
            absCourseStructureSum = null;
        } else {
            absCourseStructureSum = (double)round(round(
                        courseStructureSum
                                / (stationLength / courseStructureSectionLength),
                        scale));
        }

        final CidsBean courseLoopBean = (CidsBean)kaBean.getProperty(PROP_COURSE_LOOP);
        final CidsBean loopErosionBean = (CidsBean)kaBean.getProperty(PROP_LOOP_EROSION);

        final Integer ratingCourseLoop = cache.getCourseLoopRating(courseLoopBean.getMetaObject().getId(), wbTypeId);
        final Integer ratingLoopErosion = cache.getLoopErosionRating(loopErosionBean.getMetaObject().getId(), wbTypeId);
        final Integer ratingCourseStructure = cache.getCourseStructureRating(absCourseStructureSum, wbTypeId);
        final Double ratingLongBench = cache.getLongBenchRating(absLongBenchSum, wbTypeId);

        final RatingStruct rating = new RatingStruct();

        // this operation changes the given rating and count values directly
        overallRating(rating, true, ratingCourseLoop, ratingLoopErosion, ratingCourseStructure);
        overallRating(rating, false, ratingLongBench);

        // set the final values
        try {
            kaBean.setProperty(PROP_COURSE_EVO_SUM_RATING, rating.rating);
            kaBean.setProperty(PROP_COURSE_EVO_SUM_CRIT, rating.criteriaCount);
            kaBean.setProperty(PROP_SIM_LONG_BENCH_COUNT_RATING, ratingLongBench);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcWBCrossProfileRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        PROP_PROFILE_TYPE,
                        PROP_BREADTH_VARIANCE,
                        PROP_BREADTH_EROSION,
                        PROP_UPPER_PROFILE_BREADTH,
                        PROP_INCISION_DEPTH,
                        PROP_WATER_DEPTH)) {
            throw new ValidationException("the waterbody cross profile properties contain null values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();

        final double upperProfileBreadth = (Double)kaBean.getProperty(PROP_UPPER_PROFILE_BREADTH);
        final double incisionDepth = (Double)kaBean.getProperty(PROP_INCISION_DEPTH);
        final double waterDepth = (Double)kaBean.getProperty(PROP_WATER_DEPTH);
        final double profileDepth = incisionDepth + waterDepth;

        final double breadthDepthRelation = round(round(upperProfileBreadth / profileDepth, scale));

        final CidsBean profileTypeBean = (CidsBean)kaBean.getProperty(PROP_PROFILE_TYPE);
        final CidsBean breadthVarianceBean = (CidsBean)kaBean.getProperty(PROP_BREADTH_VARIANCE);
        final CidsBean breadthErosionBean = (CidsBean)kaBean.getProperty(PROP_BREADTH_EROSION);

        Integer ratingDepthBreadth = cache.getProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
        if (ratingDepthBreadth == null) {
            ratingDepthBreadth = cache.getBiggestProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
            if (ratingDepthBreadth == null) {
                ratingDepthBreadth = cache.getLowestProfileDepthBreadthRelationRating(breadthDepthRelation, wbTypeId);
            }
        }

        final Integer ratingBreadthVariance = cache.getBreadthVarianceRating(
                breadthVarianceBean.getMetaObject().getId(),
                wbTypeId);
        final Integer ratingBreadthErosion = cache.getBreadthErosionRating(breadthErosionBean.getMetaObject().getId(),
                wbTypeId);
        final Integer ratingProfileType = cache.getProfileTypeRating(profileTypeBean.getMetaObject().getId(),
                wbTypeId);

        final RatingStruct rating = new RatingStruct();

        // this operation changes the given rating and count values directly
        overallRating(rating, true, ratingDepthBreadth, ratingProfileType, ratingBreadthErosion, ratingBreadthVariance);

        // set the final values
        try {
            kaBean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, rating.rating);
            kaBean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, rating.criteriaCount);
            kaBean.setProperty(PROP_SIM_BREADTH_VARIANCE_RATING, ratingBreadthVariance);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcBedStructureRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (!propsNotNull(
                        kaBean,
                        PROP_BED_FITMENT,
                        PROP_Z_BED_FITMENT)) {
            throw new ValidationException("the waterbody bed structure properties contain null values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        int naturalSubstratesCount = 0;
        for (final BedSubtrateType type : BedSubtrateType.getNaturalBedSubstrateTypes()) {
            final Double percentage = (Double)kaBean.getProperty(
                    fieldFromCode(PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if ((percentage != null) && (percentage > 0)) {
                naturalSubstratesCount++;
            }
        }

        double artificalSubstratePercentage = 0;
        for (final BedSubtrateType type : BedSubtrateType.getArtificialSubstrateTypes()) {
            final Double percentage = (Double)kaBean.getProperty(
                    fieldFromCode(PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if (percentage != null) {
                artificalSubstratePercentage += percentage;
            }
        }

        double hardSubstratePercentage = 0;
        for (final BedSubtrateType type : BedSubtrateType.getHardSubstrateTypes()) {
            final Double percentage = (Double)kaBean.getProperty(
                    fieldFromCode(PROP_BED_SUBSTRATE_PREFIX, type.getCode()));
            if (percentage != null) {
                hardSubstratePercentage += percentage;
            }
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();

        final Double bedStructureSectionLength = cache.getBedStructureSectionLength(wbTypeId);
        final double stationLength = getStationLength(kaBean);

        final CidsBean bedFitmentBean = (CidsBean)kaBean.getProperty(PROP_BED_FITMENT);
        final CidsBean zBedFitmentBean = (CidsBean)kaBean.getProperty(PROP_Z_BED_FITMENT);

        final Double ratingBedStructure;
        if ((bedStructureSectionLength != null) && (bedStructureSectionLength > 0)) {
            double bedStructureCount = 0;
            for (final BedStructureType type : BedStructureType.values()) {
                final Double count = (Double)kaBean.getProperty(fieldFromCode("PROP_BED_STRUCTURE_", type.getCode())); // NOI18N

                if (count != null) {
                    bedStructureCount += count;
                }
            }

            final double absBedStructureCount = round(round(
                        bedStructureCount
                                / (stationLength / bedStructureSectionLength),
                        scale));

            ratingBedStructure = cache.getBedStructureRating(absBedStructureCount, wbTypeId);
        } else {
            ratingBedStructure = null;
        }

        final Collection<CidsBean> wbSubTypeList = (Collection<CidsBean>)kaBean.getProperty(PROP_WB_SUB_TYPE);
        String wbSubTypeId = null;
        if ((wbSubTypeList != null) && !wbSubTypeList.isEmpty()) {
            final Iterator<CidsBean> it = wbSubTypeList.iterator();
            while (it.hasNext()) {
                final String val = (String)it.next().getProperty(PROP_VALUE);
                if ((val != null) && ("S".equals(val) || "M".equals(val))) {              // NOI18N
                    if (wbSubTypeId == null) {
                        wbSubTypeId = val;
                    } else {
                        throw new IllegalStateException(
                            "found more than one relevant subtype for kartierabschnitt: " // NOI18N
                                    + kaBean);
                    }
                }
            }
        }

        final Integer ratingNaturalSubstrates = cache.getNaturalSubstrateRating(Double.valueOf(
                    (double)naturalSubstratesCount),
                wbSubTypeId,
                wbTypeId);
        final Integer ratingArtificialSubstrates = cache.getArtificialSubstrateRating(
                artificalSubstratePercentage,
                wbSubTypeId,
                wbTypeId);
        final Integer ratingHardSubstrates = cache.getHardSubstrateRating(
                hardSubstratePercentage,
                wbSubTypeId,
                wbTypeId);

        boolean ignoreSubstrates = true;
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_BLO) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_BLO) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_KIE) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_KIE) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_KUE) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_KUE) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_TON) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_TON) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_TOR) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_TOR) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_TOT) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_TOT) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_SAN) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_SAN) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_SCH) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_SCH) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_STE) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_STE) == 0.0));
        ignoreSubstrates &= ((kaBean.getProperty(PROP_BED_SUBSTRATE_WUR) == null)
                        || ((Double)kaBean.getProperty(PROP_BED_SUBSTRATE_WUR) == 0.0));

        final Integer ratingSubstrates;
        final Double ratingSubstratesDouble;
        if (!ignoreSubstrates
                    && Equals.nonNull(ratingNaturalSubstrates, ratingArtificialSubstrates, ratingHardSubstrates)) {
            ratingSubstrates = round(round(
                        (ratingNaturalSubstrates + ratingArtificialSubstrates + ratingHardSubstrates)
                                / 3.0,
                        scale));
            ratingSubstratesDouble = round(
                    (ratingNaturalSubstrates + ratingArtificialSubstrates + ratingHardSubstrates)
                            / 3.0,
                    1);
        } else {
            ratingSubstrates = null;
            ratingSubstratesDouble = null;
        }

        try {
            kaBean.setProperty("bewertung_substratdiversitaet", ratingSubstratesDouble);
        } catch (Exception e) {
            LOG.error("Could not set bewertung_substratdiversitaet");
        }

        final Integer ratingBedFitment = cache.getBedFitmentRating(
                bedFitmentBean.getMetaObject().getId(),
                zBedFitmentBean.getMetaObject().getId(),
                wbTypeId);

        final Double ratingBedContamination = calcBedContamination(kaBean, wbTypeId);

        final RatingStruct rating = new RatingStruct();

        // this operation changes the given rating and count values directly
        overallRating(rating, true, ratingSubstrates, ratingBedFitment);
        overallRating(rating, false, ratingBedContamination, ratingBedStructure);

        double finalRating = rating.rating;
        // NOTE: rating correction according to Kuechler, not present in original implementation
        // NOTE: new desired behaviour according to github/lung-mv #78
        if ((finalRating < 1) && (rating.criteriaCount > 0)) {
            finalRating = 1;
        }

        // set the final values
        try {
            kaBean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, finalRating);
            kaBean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, rating.criteriaCount);
            kaBean.setProperty(PROP_SIM_BED_STRUCTURE_COUNT_RATING, ratingBedStructure);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean    DOCUMENT ME!
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double calcBedContamination(final CidsBean kaBean, final int wbTypeId) {
        Double ratingBedContamination = null;
        final Double ratingMaxBedContamination = cache.getMaxBedContaminationRating(wbTypeId);
        for (final BedContaminationType type : BedContaminationType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode("PROP_BED_CONTAMINATION_", type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                final Double rating = cache.getBedContaminationRating(type.getId(), wbTypeId);
                if (rating != null) {
                    if (ratingBedContamination == null) {
                        ratingBedContamination = 0d;
                    }

                    ratingBedContamination += rating;
                }
            }
        }

        if ((ratingBedContamination != null) && (ratingBedContamination < ratingMaxBedContamination)) {
            ratingBedContamination = ratingMaxBedContamination;
        }

        return ratingBedContamination;
    }
    /**
     * DOCUMENT ME!
     *
     * @param   kaBean    DOCUMENT ME!
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double calcBedContaminationCount(final CidsBean kaBean, final int wbTypeId) {
        double bedContamination = 0.0;

        for (final BedContaminationType type : BedContaminationType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode("PROP_BED_CONTAMINATION_", type.getCode())); // NOI18N
            if ((count != null) && (count > 0)) {
                bedContamination += count;
            }
        }

        return bedContamination;
    }

    /**
     * Calls {@link #calcBankStructureRating(de.cismet.cids.dynamics.CidsBean, boolean)} twice, for the left and the
     * right side. The overall result is the sum of the partial results.
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException    DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public void calcBankStructureRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        calcBankStructureRating(kaBean, true);
        calcBankStructureRating(kaBean, false);

        final Double ratingLeft = (Double)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING_LE);
        final Integer criteriaCountLeft = (Integer)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT_LE);
        final Double ratingRight = (Double)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING_RI);
        final Integer criteriaCountRight = (Integer)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT_RI);

        double rating = ratingLeft + ratingRight;
        // rating correction according to Kuechler, not present in original implementation
        if (rating < 1) {
            rating = 1;
        }

        // set the final values
        try {
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, rating);
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, criteriaCountLeft + criteriaCountRight);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     * @param   left    DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcBankStructureRating(final CidsBean kaBean, final boolean left) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        fieldFromCode("PROP_BANK_VEGETATION", "", left), // NOI18N
                        fieldFromCode("PROP_BANK_FITMENT", "", left), // NOI18N
                        fieldFromCode("PROP_Z_BANK_FITMENT", "", left))) { // NOI18N
            throw new ValidationException("the waterbody bank structure properties contain null values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without water body type: " + kaBean); // NOI18N
        }

        final int wbTypeId = wbTypeBean.getMetaObject().getId();

        final double stationLength = getStationLength(kaBean);

        final Integer ratingBankVegetation = getBankVegetationRating(wbTypeId, stationLength, kaBean, left);
        final Integer ratingBankStructure = getBankStructureRating(wbTypeId, stationLength, kaBean, left);
        final Integer ratingBankFitment = getBankFitmentRating(wbTypeId, stationLength, kaBean, left);
        final Double ratingBankContamination = getBankContaminationRating(wbTypeId, stationLength, kaBean, left);
        final RatingStruct rating = new RatingStruct();

        // this operation changes the given rating and count values directly
        overallRating(rating, true, ratingBankStructure, ratingBankVegetation, ratingBankFitment);
        overallRating(rating, false, ratingBankContamination);

        // set the final values
        try {
            kaBean.setProperty(fieldFromCode("PROP_BANK_STRUCTURE_SUM_RATING", "", left), rating.rating);      // NOI18N
            kaBean.setProperty(fieldFromCode("PROP_BANK_STRUCTURE_SUM_CRIT", "", left), rating.criteriaCount); // NOI18N
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean;                                     // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     * @param   kaBean         DOCUMENT ME!
     * @param   left           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBankContaminationRating(final int wbTypeId,
            final double stationLength,
            final CidsBean kaBean,
            final boolean left) {
        Double ratingBankContamination = null;

        for (final BankContaminationType type : BankContaminationType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode(
                        "PROP_BANK_CONTAMINATION_", // NOI18N
                        type.getCode(),
                        left));
            if ((count != null) && (count > 0)) {
                final Double rating = cache.getBankContaminationRating(type.getId(), wbTypeId);
                if (rating != null) {
                    if (ratingBankContamination == null) {
                        ratingBankContamination = 0d;
                    }

                    ratingBankContamination += rating;
                }
            }

            if ((ratingBankContamination != null) && (ratingBankContamination < -2.5)) {
                ratingBankContamination = -2.5;
            }
        }

        return ratingBankContamination;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     * @param   kaBean    DOCUMENT ME!
     * @param   left      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBankContaminationCount(final int wbTypeId,
            final CidsBean kaBean,
            final boolean left) {
        double bankContamination = 0.0;

        for (final BankContaminationType type : BankContaminationType.values()) {
            final Double count = (Double)kaBean.getProperty(fieldFromCode(
                        "PROP_BANK_CONTAMINATION_", // NOI18N
                        type.getCode(),
                        left));
            if ((count != null) && (count > 0)) {
                bankContamination += count;
            }
        }

        return bankContamination;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     * @param   kaBean         DOCUMENT ME!
     * @param   left           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankFitmentRating(final int wbTypeId,
            final double stationLength,
            final CidsBean kaBean,
            final boolean left) {
        final CidsBean bankFitmentBean = (CidsBean)kaBean.getProperty(fieldFromCode("PROP_BANK_FITMENT", "", left));    // NOI18N
        final CidsBean zBankFitmentBean = (CidsBean)kaBean.getProperty(fieldFromCode("PROP_Z_BANK_FITMENT", "", left)); // NOI18N

        return cache.getBankFitmentRating(bankFitmentBean.getMetaObject().getId(),
                zBankFitmentBean.getMetaObject().getId(),
                wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     * @param   kaBean         DOCUMENT ME!
     * @param   left           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankVegetationRating(final int wbTypeId,
            final double stationLength,
            final CidsBean kaBean,
            final boolean left) {
        final CidsBean bankVegetationBean = (CidsBean)kaBean.getProperty(fieldFromCode(
                    "PROP_BANK_VEGETATION", // NOI18N
                    "", // NOI18N
                    left));
        final Boolean typical = (Boolean)kaBean.getProperty(fieldFromCode(
                    "PROP_BANK_VEGETATION_TYPICAL", // NOI18N
                    "", // NOI18N
                    left));
        final boolean bankVegetationTypical = (typical == null) ? false : typical;

        return cache.getBankVegetationRating(bankVegetationBean.getMetaObject().getId(),
                bankVegetationTypical,
                wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId       DOCUMENT ME!
     * @param   stationLength  DOCUMENT ME!
     * @param   kaBean         DOCUMENT ME!
     * @param   left           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankStructureRating(final int wbTypeId,
            final double stationLength,
            final CidsBean kaBean,
            final boolean left) {
        final Integer ratingBankStructure;
        final Double bankStructureSectionLength = cache.getBankStructureSectionLength(wbTypeId);

        if ((stationLength > 0) && (bankStructureSectionLength != null)) {
            double bankStructureCount = 0;
            for (final BankStructureType type : BankStructureType.values()) {
                final Double count = (Double)kaBean.getProperty(fieldFromCode(
                            "PROP_BANK_STRUCTURE_", // NOI18N
                            type.getCode(),
                            left));
                if ((count != null) && (count > 0)) {
                    bankStructureCount += count;
                }
            }

            final double absBankStructureCount;
            if (bankStructureCount == 0.5d) {
                absBankStructureCount = bankStructureCount;
            } else {
                absBankStructureCount = round(round(
                            bankStructureCount
                                    / stationLength
                                    * bankStructureSectionLength,
                            scale));
            }

            ratingBankStructure = cache.getBankStructureRating(absBankStructureCount, wbTypeId);
        } else {
            ratingBankStructure = null;
        }

        return ratingBankStructure;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  UnsupportedOperationException DOCUMENT ME!
     */
    public void calcOverallRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (!propsNotNullOrZero(
                        kaBean,
                        PROP_WB_BED_RATING,
                        PROP_WB_BANK_RATING,
                        PROP_WB_ENV_RATING)) {
            throw new ValidationException("the waterbody rating properties contain null or zero values"); // NOI18N
        }

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without wb type");
        }
        final Integer wbType = (Integer)wbTypeBean.getProperty(PROP_VALUE);
        if (wbType == null) {
            throw new IllegalStateException("kartierabschnitt bean with illegal wb type");
        }

        final double ratingBed = (Double)kaBean.getProperty(PROP_WB_BED_RATING);
        final double ratingBank = (Double)kaBean.getProperty(PROP_WB_BANK_RATING);
        final double ratingEnv = (Double)kaBean.getProperty(PROP_WB_ENV_RATING);

        final double ratingOverall;
        if (wbType == 23) {
            ratingOverall = (ratingBed + (2 * ratingBank) + (2 * ratingEnv)) / 5.0d;
        } else {
            ratingOverall = (ratingBed + ratingBank + ratingEnv) / 3.0d;
        }

        // set the final values
        try {
            kaBean.setProperty(PROP_WB_OVERALL_RATING, ratingOverall);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcBedRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        PROP_COURSE_EVO_SUM_RATING,
                        PROP_COURSE_EVO_SUM_CRIT,
                        PROP_BED_STRUCTURE_SUM_RATING,
                        PROP_BED_STRUCTURE_SUM_CRIT,
                        PROP_LONG_PROFILE_SUM_RATING,
                        PROP_LONG_PROFILE_SUM_CRIT)) {
            throw new ValidationException("the bed rating properties contain null values"); // NOI18N
        }

        final double ratingCourseEvo = (Double)kaBean.getProperty(PROP_COURSE_EVO_SUM_RATING);
        final int critCountCourseEvo = (Integer)kaBean.getProperty(PROP_COURSE_EVO_SUM_CRIT);
        final double ratingLongProfile = (Double)kaBean.getProperty(PROP_LONG_PROFILE_SUM_RATING);
        final int critCountLongProfile = (Integer)kaBean.getProperty(PROP_LONG_PROFILE_SUM_CRIT);
        final double ratingBedStructure = (Double)kaBean.getProperty(PROP_BED_STRUCTURE_SUM_RATING);
        final int critCountBedStructure = (Integer)kaBean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT);

        final CidsBean wbTypeBean = (CidsBean)kaBean.getProperty(PROP_WB_TYPE);
        if (wbTypeBean == null) {
            throw new IllegalStateException("kartierabschnitt bean without wb type");
        }
        final Integer wbType = (Integer)wbTypeBean.getProperty(PROP_VALUE);
        if (wbType == null) {
            throw new IllegalStateException("kartierabschnitt bean with illegal wb type");
        }

        // they are allowed to be 0 if their wb type is 23
        if ((ratingLongProfile == 0) || (critCountLongProfile == 0)) {
            if (wbType != 23) {
                throw new ValidationException(
                    "the longprofile rating or criteria count is 0 but the wb type is not 23"); // NOI18N
            }
        }

        // NOTE: rating is now possible if at least one criteria is available (lung-mv #78)
        final int critCount = critCountCourseEvo + critCountLongProfile + critCountBedStructure;
        if (critCount <= 0) {
            throw new ValidationException("the bed rating does not contain at least one criteria"); // NOI18N
        }

        // NOTE: according to A. Goetze (Phone 20131017) the rating may never be higher than 5 and shall thus be capped
        final double ratingBed = Math.min((ratingCourseEvo + ratingLongProfile + ratingBedStructure) / (critCount),
                5.0);

        // set the final values
        try {
            kaBean.setProperty(PROP_WB_BED_RATING, ratingBed);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcBankRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNull(
                        kaBean,
                        PROP_CROSS_PROFILE_SUM_RATING,
                        PROP_CROSS_PROFILE_SUM_CRIT,
                        PROP_BANK_STRUCTURE_SUM_RATING,
                        PROP_BANK_STRUCTURE_SUM_CRIT,
                        PROP_BANK_STRUCTURE_SUM_RATING_LE,
                        PROP_BANK_STRUCTURE_SUM_CRIT_LE,
                        PROP_BANK_STRUCTURE_SUM_RATING_RI,
                        PROP_BANK_STRUCTURE_SUM_CRIT_RI)) {
            throw new ValidationException("the bank rating properties contain null or zero values"); // NOI18N
        }

        final double ratingCrossProfile = (Double)kaBean.getProperty(PROP_CROSS_PROFILE_SUM_RATING);
        final int critCountCrossProfile = (Integer)kaBean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT);
        final double ratingBankStructure = (Double)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING);
        final int critCountBankStructure = (Integer)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT);
        final double ratingBankStructureLe = (Double)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING_LE);
        final int critCountBankStructureLe = (Integer)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT_LE);
        final double ratingBankStructureRi = (Double)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING_RI);
        final int critCountBankStructureRi = (Integer)kaBean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT_RI);

        final int critCount = critCountCrossProfile + critCountBankStructure;
        final int critCountLe = critCountCrossProfile + critCountBankStructureLe;
        final int critCountRi = critCountCrossProfile + critCountBankStructureRi;

        // NOTE: rating is now possible if at least one criteria is available (lung-mv #78)
        if ((critCount <= 0) || (critCountLe <= 0) || (critCountRi <= 0)) {
            throw new ValidationException("the bank rating does not contain at least one criteria"); // NOI18N
        }

        // NOTE: according to A. Goetze (Phone 20131017) the rating may never be higher than 5 and shall thus be capped
        final double ratingBank = Math.min((ratingCrossProfile + ratingBankStructure) / (critCount), 5.0);
        final double ratingBankLe = Math.min((ratingCrossProfile + ratingBankStructureLe) / (critCountLe), 5.0);
        final double ratingBankRi = Math.min((ratingCrossProfile + ratingBankStructureRi) / (critCountRi), 5.0);

        try {
            kaBean.setProperty(PROP_WB_BANK_RATING, ratingBank);
            kaBean.setProperty(PROP_WB_BANK_RATING_LE, ratingBankLe);
            kaBean.setProperty(PROP_WB_BANK_RATING_RI, ratingBankRi);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  ValidationException  DOCUMENT ME!
     */
    public void calcEnvRating(final CidsBean kaBean) throws ValidationException {
        if (isException(kaBean)) {
            return;
        }

        if (
            !propsNotNullOrZero(
                        kaBean,
                        PROP_WB_ENV_SUM_RATING,
                        PROP_WB_ENV_SUM_RATING_LE,
                        PROP_WB_ENV_SUM_RATING_RI,
                        PROP_WB_ENV_SUM_CRIT,
                        PROP_WB_ENV_SUM_CRIT_LE,
                        PROP_WB_ENV_SUM_CRIT_RI)) {
            throw new ValidationException("the env rating properties contain null or zero values"); // NOI18N
        }

        final double ratingWBEnv = (Double)kaBean.getProperty(PROP_WB_ENV_SUM_RATING);
        final double ratingWBEnvLe = (Double)kaBean.getProperty(PROP_WB_ENV_SUM_RATING_LE);
        final double ratingWBEnvRi = (Double)kaBean.getProperty(PROP_WB_ENV_SUM_RATING_RI);
        final int critCountWBEnv = (Integer)kaBean.getProperty(PROP_WB_ENV_SUM_CRIT);
        final int critCountWBEnvLe = (Integer)kaBean.getProperty(PROP_WB_ENV_SUM_CRIT_LE);
        final int critCountWBEnvRi = (Integer)kaBean.getProperty(PROP_WB_ENV_SUM_CRIT_RI);

        final double ratingEnv = Math.min(ratingWBEnv / critCountWBEnv, 5.0);
        final double ratingEnvLe = Math.min(ratingWBEnvLe / critCountWBEnvLe, 5.0);
        final double ratingEnvRi = Math.min(ratingWBEnvRi / critCountWBEnvRi, 5.0);

        try {
            kaBean.setProperty(PROP_WB_ENV_RATING, ratingEnv);
            kaBean.setProperty(PROP_WB_ENV_RATING_LE, ratingEnvLe);
            kaBean.setProperty(PROP_WB_ENV_RATING_RI, ratingEnvRi);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public void removeAllRatings(final CidsBean kaBean) {
        try {
            kaBean.setProperty(PROP_WB_ENV_SUM_RATING, null);
            kaBean.setProperty(PROP_WB_ENV_SUM_CRIT, null);

            kaBean.setProperty(PROP_LONG_PROFILE_SUM_RATING, null);
            kaBean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, null);
            kaBean.setProperty(PROP_SIM_FLOW_DIV_RATING, null);
            kaBean.setProperty(PROP_SIM_CROSS_BENCH_COUNT_RATING, null);

            kaBean.setProperty(PROP_COURSE_EVO_SUM_RATING, null);
            kaBean.setProperty(PROP_COURSE_EVO_SUM_CRIT, null);
            kaBean.setProperty(PROP_SIM_LONG_BENCH_COUNT_RATING, null);

            kaBean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, null);
            kaBean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, null);
            kaBean.setProperty(PROP_SIM_BREADTH_VARIANCE_RATING, null);

            kaBean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, null);
            kaBean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, null);
            kaBean.setProperty(PROP_SIM_BED_STRUCTURE_COUNT_RATING, null);

            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, null);
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, null);

            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING_LE, null);
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING_RI, null);
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT_LE, null);
            kaBean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT_RI, null);
            kaBean.setProperty(PROP_WB_ENV_SUM_RATING_LE, null);
            kaBean.setProperty(PROP_WB_ENV_SUM_RATING_RI, null);
            kaBean.setProperty(PROP_WB_ENV_SUM_CRIT_LE, null);
            kaBean.setProperty(PROP_WB_ENV_SUM_CRIT_RI, null);

            kaBean.setProperty(PROP_WB_BED_RATING, null);
            kaBean.setProperty(PROP_WB_BANK_RATING, null);
            kaBean.setProperty(PROP_WB_BANK_RATING_LE, null);
            kaBean.setProperty(PROP_WB_BANK_RATING_RI, null);
            kaBean.setProperty(PROP_WB_ENV_RATING, null);
            kaBean.setProperty(PROP_WB_ENV_RATING_LE, null);
            kaBean.setProperty(PROP_WB_ENV_RATING_RI, null);
            kaBean.setProperty(PROP_WB_OVERALL_RATING, null);
        } catch (final Exception e) {
            final String message = "cannot update bean values: " + kaBean; // NOI18N
            LOG.error(message, e);

            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static double getStationLength(final CidsBean kaBean) {
        try {
            final CidsBean stationBean = (CidsBean)kaBean.getProperty(PROP_LINE);
            final CidsBean toBean = (CidsBean)stationBean.getProperty(PROP_TO);
            final CidsBean fromBean = (CidsBean)stationBean.getProperty(PROP_FROM);
            final Double toValue = (Double)toBean.getProperty(PROP_WERT);
            final Double fromValue = (Double)fromBean.getProperty(PROP_WERT);

            return Math.abs(toValue - fromValue);
        } catch (final Exception e) {
            final String message = "illegal station settings in kartierabschnitt"; // NOI18N
            LOG.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   d  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static int round(final double d) {
        // mathematical rounding
        return (int)Math.floor(d + 0.5);
            // fgsk original rounding
// return (int)Math.ceil(d - 0.5);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   d      DOCUMENT ME!
     * @param   scale  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double round(final double d, final int scale) {
        final long factor = Math.round(Math.pow(10, scale));

        final double rounded = Math.floor((d * factor) + 0.5d);

        return rounded / factor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rs              the {@link RatingStruct} object that will be used to carry the results
     * @param  count           whether the given ratings influence the criteria count
     * @param  criteraRatings  the ratings that are subject to influence the overall rating
     */
    public static void overallRating(final RatingStruct rs, final boolean count, final Number... criteraRatings) {
        for (final Number rating : criteraRatings) {
            if (rating != null) {
                rs.rating += rating.doubleValue();
                if (count) {
                    rs.criteriaCount++;
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   badEnvRating  DOCUMENT ME!
     * @param   wbTypeId      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double correctBadEnvRating(final double badEnvRating, final int wbTypeId) {
        // obey the given max malus defined by the scheme
        if (((wbTypeId == 11) || (wbTypeId == 12)) && (badEnvRating < -1)) {
            return -1;
        } else if (badEnvRating < -3) {
            return -3;
        } else {
            return badEnvRating;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   prefix  DOCUMENT ME!
     * @param   code    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String fieldFromCode(final String prefix, final String code) {
        return fieldFromCode(prefix, code, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   prefix  DOCUMENT ME!
     * @param   code    DOCUMENT ME!
     * @param   left    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static String fieldFromCode(final String prefix, final String code, final Boolean left) {
        final Field f;
        try {
            f = Calc.class.getField(prefix + code.toUpperCase()
                            + ((left == null) ? "" // NOI18N
                                              : ("_" + (left ? "LE" : "RI")))); // NOI18N

            return (String)f.get(null);
        } catch (final NoSuchFieldException ex) {
            final String message = "field for code not declared or illegal format: "     // NOI18N
                        + "[prefix=" + prefix + "|code=" + code + "|left=" + left + "]"; // NOI18N
            LOG.error(message, ex);

            throw new IllegalStateException(message, ex);
        } catch (final SecurityException ex) {
            final String message = "cannot access public static field"; // NOI18N
            LOG.fatal(message, ex);

            throw new IllegalStateException(message, ex);
        } catch (final IllegalArgumentException ex) {
            final String message = "cannot access public static field with null object"; // NOI18N
            LOG.fatal(message, ex);

            throw new IllegalStateException(message, ex);
        } catch (final IllegalAccessException ex) {
            final String message = "cannot access public static field"; // NOI18N
            LOG.fatal(message, ex);

            throw new IllegalStateException(message, ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean   DOCUMENT ME!
     * @param   props  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    private boolean propsNotNull(final CidsBean bean, final String... props) {
        if (bean == null) {
            throw new IllegalArgumentException("bean must not be null");  // NOI18N
        } else if (props == null) {
            throw new IllegalArgumentException("props must not be null"); // NOI18N
        }

        if (props.length == 0) {
            LOG.warn("empty property list"); // NOI18N

            return true;
        }

        for (final String prop : props) {
            if (bean.getProperty(prop) == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean   DOCUMENT ME!
     * @param   props  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     * @throws  ClassCastException        DOCUMENT ME!
     */
    private boolean propsNotNullOrZero(final CidsBean bean, final String... props) {
        if (bean == null) {
            throw new IllegalArgumentException("bean must not be null");  // NOI18N
        } else if (props == null) {
            throw new IllegalArgumentException("props must not be null"); // NOI18N
        }

        if (props.length == 0) {
            LOG.warn("empty property list"); // NOI18N

            return true;
        }

        for (final String prop : props) {
            final Object value = bean.getProperty(prop);
            if (value == null) {
                return false;
            } else {
                if (value instanceof Number) {
                    final Number n = (Number)value;

                    if (n.doubleValue() == 0.0d) {
                        return false;
                    }
                } else {
                    throw new ClassCastException(
                        "property value not instance of number: "
                                + prop
                                + " | "
                                + value.getClass()); // NOI18N
                }
            }
        }

        return true;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static final class RatingStruct {

        //~ Instance fields ----------------------------------------------------

        public double rating = 0d;
        public int criteriaCount = 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class LazyInitializer {

        //~ Static fields/initializers -----------------------------------------

        private static final transient Calc INSTANCE = new Calc();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new LazyInitializer object.
         */
        private LazyInitializer() {
        }
    }
}
