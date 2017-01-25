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
package de.cismet.cids.custom.reports;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.apache.log4j.Logger;

import org.openide.util.Cancellable;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupLosEditor;

import de.cismet.tools.gui.downloadmanager.AbstractDownload;
import de.cismet.tools.gui.downloadmanager.Download;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class LosReport extends AbstractDownload implements Cancellable {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(LosReport.class);

    //~ Instance fields --------------------------------------------------------

    private String filename;
    private String extension;
    private List<String> sheetNames = new ArrayList<String>();
    private ArrayList<ArrayList> aufmass;
    private ArrayList<ArrayList> massn;

    //~ Constructors -----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   aufmass    fl baCd DOCUMENT ME!
     * @param   massn      gew DOCUMENT ME!
     * @param   directory  DOCUMENT ME!
     * @param   title      DOCUMENT ME!
     * @param   filename   DOCUMENT ME!
     * @param   extension  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public LosReport(final ArrayList<ArrayList> aufmass,
            final ArrayList<ArrayList> massn,
            final String directory,
            final String title,
            final String filename,
            final String extension) throws Exception {
        this.directory = directory;
        this.title = title;
        this.filename = filename;
        this.extension = extension;
        this.aufmass = aufmass;
        this.massn = massn;

        status = Download.State.WAITING;

        determineDestinationFile(filename, extension);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void run() {
        if (status != Download.State.WAITING) {
            return;
        }

        status = Download.State.RUNNING;
        stateChanged();
        SimpleOutputStreamExporterOutput exportOut = null;

        try {
            final HashMap<String, Object> parameters = new HashMap<String, Object>();
            final Map<String, JRDataSource> dataSources = new HashMap<String, JRDataSource>();
            parameters.put("dataSources", dataSources);

            final FeatureDataSource dummyDataSource = new FeatureDataSource(new ArrayList());
            // load report
            final JasperReport jasperReport = (JasperReport)JRLoader.loadObject(LosReport.class.getResourceAsStream(
                        "/de/cismet/cids/custom/reports/los.jasper"));

            dataSources.put("aufmass", getAufmass(aufmass));
            dataSources.put("massnahmen", getMassnahmen(massn));

            // create print from report and data
            final JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dummyDataSource);

            final FileOutputStream fout = new FileOutputStream(fileToSaveTo);
            final BufferedOutputStream out = new BufferedOutputStream(fout);
            final JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exportOut = new SimpleOutputStreamExporterOutput(out);
            exporter.setExporterOutput(exportOut);

            final SimpleXlsReportConfiguration config = new SimpleXlsReportConfiguration();
            config.setOnePagePerSheet(Boolean.TRUE);
            config.setSheetNames(sheetNames.toArray(new String[sheetNames.size()]));
            config.setShowGridLines(true);
            config.setColumnWidthRatio(1.5f);
            config.setRemoveEmptySpaceBetweenColumns(true);
            config.setRemoveEmptySpaceBetweenRows(true);
            config.setCellHidden(true);
            config.setDetectCellType(true);
            exporter.setConfiguration(config);
            exporter.exportReport();

            exportOut.close();
        } catch (Exception e) {
            error(e);
        } finally {
            if (exportOut != null) {
                try {
                    exportOut.close();
                } catch (Exception e) {
                    log.warn("Exception occured while closing file.", e);
                }
            }
        }

        if (status == Download.State.RUNNING) {
            status = Download.State.COMPLETED;
            stateChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   aufmass  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private FeatureDataSource getAufmass(final ArrayList<ArrayList> aufmass) throws Exception {
        final List<Map<String, Object>> features = new ArrayList<Map<String, Object>>();
        sheetNames.add("Aufmaß");

        for (final ArrayList aufm : aufmass) {
            final Map<String, Object> feature = new HashMap<String, Object>();
            feature.put("massn_id", aufm.get(GupLosEditor.GROUP_MASSN_ID));
            feature.put("massn_bez", aufm.get(GupLosEditor.GROUP_MASSN_TYP));
            feature.put("leistungstext", aufm.get(GupLosEditor.GROUP_LEISTUNGSTEXT));
            feature.put("abschn", aufm.get(GupLosEditor.GROUP_TEILSTUECKE));
            feature.put("laenge", aufm.get(GupLosEditor.GROUP_LAENGE));
            feature.put("aufmass", aufm.get(GupLosEditor.GROUP_AUFMASS));
            feature.put("einheit", aufm.get(GupLosEditor.GROUP_MASSEINHEIT));

            features.add(feature);
        }

        return new FeatureDataSource(features);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   massnahmen  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private FeatureDataSource getMassnahmen(final ArrayList<ArrayList> massnahmen) throws Exception {
        final List<Map<String, Object>> features = new ArrayList<Map<String, Object>>();
        sheetNames.add("Maßnahmen");

        for (final ArrayList aufm : massnahmen) {
            final Map<String, Object> feature = new HashMap<String, Object>();
            feature.put("route", aufm.get(GupLosEditor.GWK));
            feature.put("planungsabschnitt", aufm.get(GupLosEditor.PLANUNGSABSCHNITT_NAME));
            feature.put("massn_id", aufm.get(GupLosEditor.MASSNAHMEN_ID));
            feature.put("kompartiment", aufm.get(GupLosEditor.MASSNAHMENBEZUG));
            feature.put("von", aufm.get(GupLosEditor.MASSNAHME_VON));
            feature.put("bis", aufm.get(GupLosEditor.MASSNAHME_BIS));
            feature.put("attr1", GupLosEditor.getAttribute(aufm, 1, GupLosEditor.FieldKind.name));
            feature.put("attr1_wert", GupLosEditor.getAttribute(aufm, 1, GupLosEditor.FieldKind.value));
            feature.put("attr1_einheit", GupLosEditor.getAttribute(aufm, 1, GupLosEditor.FieldKind.measure));
            feature.put("attr2", GupLosEditor.getAttribute(aufm, 2, GupLosEditor.FieldKind.name));
            feature.put("attr2_wert", GupLosEditor.getAttribute(aufm, 2, GupLosEditor.FieldKind.value));
            feature.put("attr2_einheit", GupLosEditor.getAttribute(aufm, 2, GupLosEditor.FieldKind.measure));
            feature.put("attr3", GupLosEditor.getAttribute(aufm, 3, GupLosEditor.FieldKind.name));
            feature.put("attr3_wert", GupLosEditor.getAttribute(aufm, 3, GupLosEditor.FieldKind.value));
            feature.put("attr3_einheit", GupLosEditor.getAttribute(aufm, 3, GupLosEditor.FieldKind.measure));

            features.add(feature);
        }

        return new FeatureDataSource(features);
    }

    @Override
    public boolean cancel() {
        boolean cancelled = true;
        if (downloadFuture != null) {
            cancelled = downloadFuture.cancel(true);
        }

        if (cancelled) {
            status = State.ABORTED;
            stateChanged();
        }
        return cancelled;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class FeatureDataSource implements JRDataSource {

        //~ Instance fields ----------------------------------------------------

        private int index = -1;
        private final List<Map<String, Object>> features;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new FeatureDataSource object.
         *
         * @param  copy  features DOCUMENT ME!
         */
        public FeatureDataSource(final FeatureDataSource copy) {
            this.features = copy.features;
        }

        /**
         * Creates a new FeatureDataSource object.
         *
         * @param  features  DOCUMENT ME!
         */
        public FeatureDataSource(final List<Map<String, Object>> features) {
            this.features = features;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean next() throws JRException {
            ++index;

            if (features.isEmpty() && (index == 0)) {
                return true;
            } else {
                return (index) < features.size();
            }
        }

        @Override
        public Object getFieldValue(final JRField jrf) throws JRException {
            if (features.isEmpty()) {
                return null;
            } else {
                return features.get(index).get(jrf.getName());
            }
        }
    }
}
