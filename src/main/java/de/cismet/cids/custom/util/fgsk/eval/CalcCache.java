/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.fgsk.eval;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.search.CidsServerSearch;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLDBMVConcurrency;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.AbstractSubstrateRatingSearch.DefaultSubstrateRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.AbstractSubstrateRatingSearch.PercentSubstrateRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.AbstractTripletRatingSearch.BankFitmentRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.AbstractTripletRatingSearch.BankVegetationRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.AbstractTripletRatingSearch.BedFitmentRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.ComplexRatingSearch.DefaultComplexRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.ComplexRatingSearch.ProfileDepthBreadthRelationRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.ComplexRatingSearch.Range;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.SectionLengthSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.SimpleMappingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.SimpleRatingSearch.DoubleRatingSearch;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.SimpleRatingSearch.IntegerRatingSearch;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class CalcCache {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(CalcCache.class);

    public static final String ANY_KEY = "*"; // NOI18N

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BadEnvStructureType {

        //~ Enum constants -----------------------------------------------------

        AG("ag", 1), FT("ft", 2), GUA("gua", 3), BV("bv", 4), MA("ma", 5), HW("hw", 6), SO("so", 7); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BadEnvStructureType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum LongBenchType {

        //~ Enum constants -----------------------------------------------------

        UFKG("ufkg", 1), IB("ib", 2), MB("mb", 3); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private LongBenchType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum CourseStructureType {

        //~ Enum constants -----------------------------------------------------

        TV("tv", 1), SB("sb", 2), IBI("ibi", 3), LW("lw", 4), LV("lv", 5), LG("lg", 6); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private CourseStructureType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BedSubtrateType {

        //~ Enum constants -----------------------------------------------------

        CLAY("ton", 1), SAND("san", 2), FLINT("kie", 3), STONE("ste", 4), BLOCK("blo", 5), MUD("sch", 6),
        PEAT("tor", 7), DEADWOOD("tot", 8), ROOTS("wur", 9), ARTIFICIAL_SUBSTRATE("kue", 10); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BedSubtrateType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public static BedSubtrateType[] getNaturalBedSubstrateTypes() {
            return new BedSubtrateType[] { CLAY, SAND, FLINT, STONE, BLOCK, PEAT, DEADWOOD, ROOTS };
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public static BedSubtrateType[] getArtificialSubstrateTypes() {
            return new BedSubtrateType[] { MUD, ARTIFICIAL_SUBSTRATE };
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public static BedSubtrateType[] getHardSubstrateTypes() {
            return new BedSubtrateType[] { DEADWOOD, ROOTS };
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BedStructureType {

        //~ Enum constants -----------------------------------------------------

        RIP("rip", 1), TH("th", 2), WU("wu", 3), KO("ko", 4); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BedStructureType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BedContaminationType {

        //~ Enum constants -----------------------------------------------------

        MUE("mue", 1), ST("st", 2), ABW("abw", 3), VO("vo", 4), SA("sa", 5), SO("so", 6); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BedContaminationType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BankStructureType {

        //~ Enum constants -----------------------------------------------------

        BU("bu", 1), PB("pb", 2), US("us", 3), SB("sb", 4), HA("ha", 5), NBOE("nboe", 6), SO("so", 7); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BankStructureType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum BankContaminationType {

        //~ Enum constants -----------------------------------------------------

        MUE("mue", 1), ST("st", 2), TS("ts", 3), EL("el", 4), SO("so", 5); // NOI18N

        //~ Instance fields ----------------------------------------------------

        private final String code;
        private final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new BadEnvStructureType object.
         *
         * @param  code  DOCUMENT ME!
         * @param  id    DOCUMENT ME!
         */
        private BankContaminationType(final String code, final int id) {
            this.code = code;
            this.id = id;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getId() {
            return id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCode() {
            return code;
        }
    }

    //~ Instance fields --------------------------------------------------------

    private final transient Map<String, Integer> wbTrimmingRatings;
    private final transient Map<String, Integer> wbLandUseRatings;
    private final transient Map<String, Double> badEnvStructureRatings;
    private final transient Map<Integer, Double> crossBenchSectionLength;
    private final transient Map<Integer, Map<Range, Integer>> crossBenchRatings;
    private final transient Map<String, Integer> flowDiversityRatings;
    private final transient Map<String, Integer> flowVelocityRatings;
    private final transient Map<String, Integer> depthVarianceRatings;
    private final transient Map<Integer, Double> longBenchSectionLength;
    private final transient Map<Integer, Double> courseStructureSectionLength;
    private final transient Map<String, Integer> courseLoopRatings;
    private final transient Map<String, Integer> loopErosionRatings;
    private final transient Map<Integer, Map<Range, Integer>> longBenchRatings;
    private final transient Map<Integer, Map<Range, Integer>> courseStructureRatings;
    private final transient Map<Integer, Map<Range, Integer>> profileDepthBreathRelationRatings;
    private final transient Map<String, Integer> profileTypeRatingsRatings;
    private final transient Map<String, Integer> breadthErosionRatings;
    private final transient Map<String, Integer> profileTypeRatings;
    private final transient Map<Integer, Double> bedStructureSectionLength;
    private final transient Map<Integer, Map<Range, Integer>> bedStructureRatings;
    private final transient Map<Integer, Double> maxBedContaminationRatings;
    private final transient Map<String, Map<Range, Integer>> naturalSubstrateRatings;
    private final transient Map<String, Map<Range, Integer>> artificialSubstrateRatings;
    private final transient Map<String, Map<Range, Integer>> hardSubstrateRatings;
    private final transient Map<String, Integer> bedFitmentRatings;
    private final transient Map<String, Double> bedContaminationRatings;
    private final transient Map<Integer, Double> bankStructureSectionLength;
    private final transient Map<Integer, Map<Range, Integer>> bankStructureRatings;
    private final transient Map<String, Integer> bankVegetationRatings;
    private final transient Map<String, Integer> bankFitmentRatings;
    private final transient Map<String, Double> bankContaminationRatings;

    private final transient Object lock;

    private transient volatile boolean init;
    private transient ExecutorService executor;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CalcCache object.
     */
    private CalcCache() {
        this.init = false;
        this.lock = new Object();

        this.wbTrimmingRatings = new HashMap<String, Integer>();
        this.wbLandUseRatings = new HashMap<String, Integer>();
        this.badEnvStructureRatings = new HashMap<String, Double>();
        this.crossBenchSectionLength = new HashMap<Integer, Double>();
        this.crossBenchRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.flowDiversityRatings = new HashMap<String, Integer>();
        this.flowVelocityRatings = new HashMap<String, Integer>();
        this.depthVarianceRatings = new HashMap<String, Integer>();
        this.longBenchSectionLength = new HashMap<Integer, Double>();
        this.courseStructureSectionLength = new HashMap<Integer, Double>();
        this.courseLoopRatings = new HashMap<String, Integer>();
        this.loopErosionRatings = new HashMap<String, Integer>();
        this.longBenchRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.courseStructureRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.profileDepthBreathRelationRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.profileTypeRatingsRatings = new HashMap<String, Integer>();
        this.breadthErosionRatings = new HashMap<String, Integer>();
        this.profileTypeRatings = new HashMap<String, Integer>();
        this.bedStructureSectionLength = new HashMap<Integer, Double>();
        this.bedStructureRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.maxBedContaminationRatings = new HashMap<Integer, Double>();
        this.naturalSubstrateRatings = new HashMap<String, Map<Range, Integer>>();
        this.artificialSubstrateRatings = new HashMap<String, Map<Range, Integer>>();
        this.hardSubstrateRatings = new HashMap<String, Map<Range, Integer>>();
        this.bedFitmentRatings = new HashMap<String, Integer>();
        this.bedContaminationRatings = new HashMap<String, Double>();
        this.bankStructureSectionLength = new HashMap<Integer, Double>();
        this.bankStructureRatings = new HashMap<Integer, Map<Range, Integer>>();
        this.bankVegetationRatings = new HashMap<String, Integer>();
        this.bankFitmentRatings = new HashMap<String, Integer>();
        this.bankContaminationRatings = new HashMap<String, Double>();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CalcCache getInstance() {
        return LazyInitializer.INSTANCE;
    }

    /**
     * DOCUMENT ME!
     */
    public void init() {
        if (!init) {
            synchronized (lock) {
                if (!init) {
                    if (executor == null) {
                        executor = Executors.newCachedThreadPool(
                                WRRLDBMVConcurrency.createThreadFactory("calc-cache", new CalcCacheExceptionHandler())); // NOI18N
                        doInit();
                    } else {
                        // We don't want to use another thread that waits for the executor to terminate so we just don't
                        // update the init flag, as long as no subsequent call to init() was performed. Any
                        // implementation will call init() right after isInitialised() returned false, and we can live
                        // well with this false negative
                        if (executor.isTerminated()) {
                            executor = null;
                            init = true;
                        }
                    }
                }
            }
        }

        // TODO init
    }

    /**
     * DOCUMENT ME!
     */
    public void clear() {
        synchronized (lock) {
            if (init) {
                // TODO:
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void doInit() {
        // wb environment rating
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_flaechennutzung_auswertung"), // NOI18N
                wbLandUseRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_gewaesserrandstreifen_auswertung"), // NOI18N
                wbTrimmingRatings));
        executor.submit(new CacheInitialiser(
                new DoubleRatingSearch("public.fgsk_schaedlicheumfeldstrukturen_auswertung"), // NOI18N
                badEnvStructureRatings));

        // long profile rating
        executor.submit(new CacheInitialiser(
                new SectionLengthSearch("fgsk_querbaenke_auswertung"), // NOI18N
                crossBenchSectionLength));
        executor.submit(new CacheInitialiser(
                new DefaultComplexRatingSearch("public.fgsk_querbaenke_auswertung"), // NOI18N
                crossBenchRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_stroemungs_diversitaet_auswertung"), // NOI18N
                flowDiversityRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_fliessgeschwindigkeit_auswertung"), // NOI18N
                flowVelocityRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_tiefenvarianz_auswertung"), // NOI18N
                depthVarianceRatings));

        // course evolution rating
        executor.submit(new CacheInitialiser(
                new SectionLengthSearch("fgsk_laengsbaenke_auswertung"), // NOI18N
                longBenchSectionLength));
        executor.submit(new CacheInitialiser(
                new SectionLengthSearch("fgsk_laufstrukturen_auswertung"), // NOI18N
                courseStructureSectionLength));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_laufkruemmung_auswertung"), // NOI18N
                courseLoopRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_kruemmungserosion_auswertung"), // NOI18N
                loopErosionRatings));
        executor.submit(new CacheInitialiser(
                new DefaultComplexRatingSearch("public.fgsk_laengsbaenke_auswertung"), // NOI18N
                longBenchRatings));
        executor.submit(new CacheInitialiser(
                new DefaultComplexRatingSearch("public.fgsk_laufstrukturen_auswertung"), // NOI18N
                courseStructureRatings));

        // cross profile rating
        executor.submit(new CacheInitialiser(
                new ProfileDepthBreadthRelationRatingSearch(),
                profileDepthBreathRelationRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_breitenvarianz_auswertung"), // NOI18N
                profileTypeRatingsRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_breitenerosion_auswertung"), // NOI18N
                breadthErosionRatings));
        executor.submit(new CacheInitialiser(
                new IntegerRatingSearch("public.fgsk_profiltyp_auswertung"), // NOI18N
                profileTypeRatings));

        // bed structure rating
        executor.submit(new CacheInitialiser(
                new SectionLengthSearch("public.fgsk_sohlenstrukturen_auswertung"), // NOI18N
                bedStructureSectionLength));
        executor.submit(new CacheInitialiser(
                new DefaultComplexRatingSearch("public.fgsk_sohlenstrukturen_auswertung"), // NOI18N
                bedStructureRatings));
        executor.submit(new CacheInitialiser(
                new SimpleMappingSearch(
                    "public.fgsk_belastung_sohle_max_auswertung", // NOI18N
                    "id_gewaessertyp", // NOI18N
                    Integer.class,
                    "maximum", // NOI18N
                    Double.class),
                maxBedContaminationRatings));
        executor.submit(new CacheInitialiser(
                new DefaultSubstrateRatingSearch("public.fgsk_natuerliche_substrate_auswertung"), // NOI18N
                naturalSubstrateRatings));
        executor.submit(new CacheInitialiser(
                new PercentSubstrateRatingSearch("public.fgsk_prozent_kuenstliche_substrate_auswertung"), // NOI18N
                artificialSubstrateRatings));
        executor.submit(new CacheInitialiser(
                new PercentSubstrateRatingSearch("public.fgsk_prozent_organische_hartsubstrate_auswertung"), // NOI18N
                hardSubstrateRatings));
        executor.submit(new CacheInitialiser(new BedFitmentRatingSearch(), bedFitmentRatings));
        executor.submit(new CacheInitialiser(
                new DoubleRatingSearch("public.fgsk_belastung_sohle_auswertung"), // NOI18N
                bedContaminationRatings));

        // bank structure rating
        executor.submit(new CacheInitialiser(
                new SectionLengthSearch("public.fgsk_uferstrukturen_auswertung"), // NOI18N
                bankStructureSectionLength));
        executor.submit(new CacheInitialiser(
                new DefaultComplexRatingSearch("public.fgsk_uferstrukturen_auswertung"), // NOI18N
                bankStructureRatings));
        executor.submit(new CacheInitialiser(new BankVegetationRatingSearch(), bankVegetationRatings));
        executor.submit(new CacheInitialiser(new BankFitmentRatingSearch(), bankFitmentRatings));
        executor.submit(new CacheInitialiser(
                new DoubleRatingSearch("public.fgsk_uferbelastungen_auswertung"), // NOI18N
                bankContaminationRatings));

        executor.shutdown();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean waitForInit() {
        init();

        if (executor != null) {
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
                synchronized (lock) {
                    if (executor != null) {
                        init = true;
                    }
                }
            } catch (final InterruptedException ex) {
                LOG.error("cannot wait for initialisation", ex); // NOI18N

                return false;
            }
        }

        return init;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTrimmingId  DOCUMENT ME!
     * @param   wbTypeId      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getWBTrimmingRating(final int wbTrimmingId, final int wbTypeId) {
        check();

        return wbTrimmingRatings.get(wbTrimmingId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbLandUseId  DOCUMENT ME!
     * @param   wbTypeId     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getWBLandUseRating(final int wbLandUseId, final int wbTypeId) {
        check();

        return wbLandUseRatings.get(wbLandUseId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   type      DOCUMENT ME!
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBadEnvStructureRating(final BadEnvStructureType type, final int wbTypeId) {
        check();

        return badEnvStructureRatings.get(type.getId() + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getCrossBenchSectionLength(final int wbTypeId) {
        check();

        return crossBenchSectionLength.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   crossBenchCount  DOCUMENT ME!
     * @param   wbTypeId         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getCrossBenchRating(final Double crossBenchCount, final int wbTypeId) {
        if (crossBenchCount == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = crossBenchRatings.get(wbTypeId);

        return getRangeRating(ranges, crossBenchCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   flowDiversityId  DOCUMENT ME!
     * @param   wbTypeId         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getFlowDiversityRating(final int flowDiversityId, final int wbTypeId) {
        check();

        return flowDiversityRatings.get(flowDiversityId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   flowVelocityId  DOCUMENT ME!
     * @param   wbTypeId        DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getFlowVelocityRating(final int flowVelocityId, final int wbTypeId) {
        check();

        return flowVelocityRatings.get(flowVelocityId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   depthVarianceId  DOCUMENT ME!
     * @param   wbTypeId         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getDepthVarianceRating(final int depthVarianceId, final int wbTypeId) {
        check();

        return depthVarianceRatings.get(depthVarianceId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLongBenchSectionLength(final int wbTypeId) {
        check();

        return longBenchSectionLength.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getCourseStructureSectionLength(final int wbTypeId) {
        check();

        return courseStructureSectionLength.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   courseLoopId  DOCUMENT ME!
     * @param   wbTypeId      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getCourseLoopRating(final int courseLoopId, final int wbTypeId) {
        check();

        return courseLoopRatings.get(courseLoopId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   loopErosionId  DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLoopErosionRating(final int loopErosionId, final int wbTypeId) {
        check();

        return loopErosionRatings.get(loopErosionId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   longBenchCount  DOCUMENT ME!
     * @param   wbTypeId        DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLongBenchRating(final Double longBenchCount, final int wbTypeId) {
        if (longBenchCount == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = longBenchRatings.get(wbTypeId);

        return getRangeRating(ranges, longBenchCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   courseStructureCount  DOCUMENT ME!
     * @param   wbTypeId              DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getCourseStructureRating(final Double courseStructureCount, final int wbTypeId) {
        if (courseStructureCount == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = courseStructureRatings.get(wbTypeId);

        return getRangeRating(ranges, courseStructureCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   depthBreathRelation  DOCUMENT ME!
     * @param   wbTypeId             DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getProfileDepthBreadthRelationRating(final Double depthBreathRelation, final int wbTypeId) {
        if (depthBreathRelation == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = profileDepthBreathRelationRatings.get(wbTypeId);

        return getRangeRating(ranges, depthBreathRelation);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   depthBreathRelation  DOCUMENT ME!
     * @param   wbTypeId             DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBiggestProfileDepthBreadthRelationRating(final Double depthBreathRelation, final int wbTypeId) {
        if (depthBreathRelation == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = profileDepthBreathRelationRatings.get(wbTypeId);

        if (ranges != null) {
            for (final Range range : ranges.keySet()) {
                if ((depthBreathRelation > range.getTo()) && (ranges.get(range) == 5)) {
                    return 5;
                }
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   depthBreathRelation  DOCUMENT ME!
     * @param   wbTypeId             DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLowestProfileDepthBreadthRelationRating(final Double depthBreathRelation, final int wbTypeId) {
        if (depthBreathRelation == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = profileDepthBreathRelationRatings.get(wbTypeId);

        if (ranges != null) {
            for (final Range range : ranges.keySet()) {
                if ((depthBreathRelation < range.getFrom()) && (ranges.get(range) == 1)) {
                    return 1;
                }
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   breadthVarianceId  DOCUMENT ME!
     * @param   wbTypeId           DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBreadthVarianceRating(final int breadthVarianceId, final int wbTypeId) {
        check();

        return profileTypeRatingsRatings.get(breadthVarianceId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   breadthErosionId  DOCUMENT ME!
     * @param   wbTypeId          DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBreadthErosionRating(final int breadthErosionId, final int wbTypeId) {
        check();

        return breadthErosionRatings.get(breadthErosionId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   profileTypeId  DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getProfileTypeRating(final int profileTypeId, final int wbTypeId) {
        check();

        return profileTypeRatings.get(profileTypeId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBedStructureSectionLength(final int wbTypeId) {
        check();

        return bedStructureSectionLength.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   absBedStructureCount  DOCUMENT ME!
     * @param   wbTypeId              DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBedStructureRating(final Double absBedStructureCount, final int wbTypeId) {
        if (absBedStructureCount == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = bedStructureRatings.get(wbTypeId);

        return getRangeRating(ranges, absBedStructureCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getMaxBedContaminationRating(final int wbTypeId) {
        check();

        return maxBedContaminationRatings.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   naturalSubstrateCount  DOCUMENT ME!
     * @param   wbSubType              DOCUMENT ME!
     * @param   wbTypeId               DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getNaturalSubstrateRating(final Double naturalSubstrateCount,
            final String wbSubType,
            final int wbTypeId) {
        if (naturalSubstrateCount == null) {
            return null;
        }

        check();

        Map<Range, Integer> ranges = naturalSubstrateRatings.get(wbTypeId + "-" + wbSubType); // NOI18N

        if (ranges == null) {
            ranges = naturalSubstrateRatings.get(wbTypeId + "-" + ANY_KEY); // NOI18N
        }

        return getRangeRating(ranges, naturalSubstrateCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   artificialSubstrateCount  DOCUMENT ME!
     * @param   wbSubType                 DOCUMENT ME!
     * @param   wbTypeId                  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getArtificialSubstrateRating(final Double artificialSubstrateCount,
            final String wbSubType,
            final int wbTypeId) {
        if (artificialSubstrateCount == null) {
            return null;
        }

        check();

        Map<Range, Integer> ranges = artificialSubstrateRatings.get(wbTypeId + "-" + wbSubType); // NOI18N

        if (ranges == null) {
            ranges = artificialSubstrateRatings.get(wbTypeId + "-" + ANY_KEY); // NOI18N
        }

        return getRangeRating(ranges, artificialSubstrateCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   hardSubstrateCount  DOCUMENT ME!
     * @param   wbSubType           DOCUMENT ME!
     * @param   wbTypeId            DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getHardSubstrateRating(final Double hardSubstrateCount, final String wbSubType, final int wbTypeId) {
        if (hardSubstrateCount == null) {
            return null;
        }

        check();

        Map<Range, Integer> ranges = hardSubstrateRatings.get(wbTypeId + "-" + wbSubType); // NOI18N

        if (ranges == null) {
            ranges = hardSubstrateRatings.get(wbTypeId + "-" + ANY_KEY); // NOI18N
        }

        return getRangeRating(ranges, hardSubstrateCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bedFitmentId   DOCUMENT ME!
     * @param   zBedFitmentId  DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBedFitmentRating(final int bedFitmentId, final int zBedFitmentId, final int wbTypeId) {
        check();

        return bedFitmentRatings.get(wbTypeId + "-" + bedFitmentId + "-" + zBedFitmentId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bedContaminationID  DOCUMENT ME!
     * @param   wbTypeId            DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBedContaminationRating(final int bedContaminationID, final int wbTypeId) {
        check();

        return bedContaminationRatings.get(bedContaminationID + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wbTypeId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBankStructureSectionLength(final int wbTypeId) {
        check();

        return bankStructureSectionLength.get(wbTypeId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   absBankStructureCount  DOCUMENT ME!
     * @param   wbTypeId               DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankStructureRating(final Double absBankStructureCount, final int wbTypeId) {
        if (absBankStructureCount == null) {
            return null;
        }

        check();

        final Map<Range, Integer> ranges = bankStructureRatings.get(wbTypeId);

        return getRangeRating(ranges, absBankStructureCount);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bankVegetationId       DOCUMENT ME!
     * @param   bankVegetationTypical  DOCUMENT ME!
     * @param   wbTypeId               DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankVegetationRating(final int bankVegetationId,
            final boolean bankVegetationTypical,
            final int wbTypeId) {
        check();

        return bankVegetationRatings.get(wbTypeId + "-" + bankVegetationId + "-" + (bankVegetationTypical ? 1 : 2)); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bankFitmentId  DOCUMENT ME!
     * @param   zBankFitment   DOCUMENT ME!
     * @param   wbTypeId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getBankFitmentRating(final int bankFitmentId, final int zBankFitment, final int wbTypeId) {
        check();

        return bankFitmentRatings.get(wbTypeId + "-" + bankFitmentId + "-" + zBankFitment); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bankContaminationId  DOCUMENT ME!
     * @param   wbTypeId             DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBankContaminationRating(final int bankContaminationId, final int wbTypeId) {
        check();

        return bankContaminationRatings.get(bankContaminationId + "-" + wbTypeId); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ranges  DOCUMENT ME!
     * @param   length  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Integer getRangeRating(final Map<Range, Integer> ranges, final double length) {
        if (ranges != null) {
            for (final Range range : ranges.keySet()) {
                if (range.withinRange(length)) {
                    return ranges.get(range);
                }
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private void check() {
        if (!waitForInit()) {
            throw new IllegalStateException("the cache is not properly initialised"); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isInitialised() {
        return init;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class CacheInitialiser implements Runnable {

        //~ Instance fields ----------------------------------------------------

        private final transient Map toFill;
        private final transient CidsServerSearch search;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CacheInitialiser object.
         *
         * @param   search  DOCUMENT ME!
         * @param   toFill  DOCUMENT ME!
         *
         * @throws  IllegalArgumentException  DOCUMENT ME!
         */
        private CacheInitialiser(final CidsServerSearch search, final Map toFill) {
            if ((search == null) || (toFill == null)) {
                throw new IllegalArgumentException("no param must be null"); // NOI18N
            }

            this.toFill = toFill;
            this.search = search;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void run() {
            final Collection result;
            try {
                result = SessionManager.getProxy().customServerSearch(search);
            } catch (final ConnectionException ex) {
                final String message = "cannot perform custom search"; // NOI18N
                LOG.error(message, ex);

                throw new IllegalStateException(message, ex);
            }

            if (result.isEmpty()) {
                final String message = "illegal server search result: " + result; // NOI18N
                LOG.error(message);
                throw new IllegalStateException(message);
            }

            final Object searchResult = result.iterator().next();

            if (searchResult instanceof Map) {
                toFill.putAll((Map)searchResult);
            } else {
                final String message = "illegal server search result: " + searchResult; // NOI18N
                LOG.error(message);
                throw new IllegalStateException(message);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class CalcCacheExceptionHandler implements Thread.UncaughtExceptionHandler {

        //~ Methods ------------------------------------------------------------

        @Override
        public void uncaughtException(final Thread t, final Throwable e) {
            if (e instanceof Error) {
                LOG.fatal("encountered unexpected error", e); // NOI18N

                throw (Error)e;
            } else {
                LOG.error("cannot initialise calc cache", e); // NOI18N
                synchronized (lock) {
                    executor.shutdownNow();
                    init = false;
                    executor = null;
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class LazyInitializer {

        //~ Static fields/initializers -----------------------------------------

        private static final transient CalcCache INSTANCE = new CalcCache();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new LazyInitializer object.
         */
        private LazyInitializer() {
        }
    }
}
