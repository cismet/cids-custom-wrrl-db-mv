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

import org.openide.util.Cancellable;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.gaeb.types.GaebContainer;
import de.cismet.cids.gaeb.types.GaebLvItem;

import de.cismet.tools.gui.downloadmanager.AbstractDownload;
import de.cismet.tools.gui.downloadmanager.Download;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GaebDownload extends AbstractDownload implements Cancellable {

    //~ Instance fields --------------------------------------------------------

    private String filename;
    private String extension;
    private CidsBean cidsBean;
    private ArrayList<ArrayList> massnBeans;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WebDavDownload object.
     *
     * @param  massnBeans  client DOCUMENT ME!
     * @param  cidsBean    path DOCUMENT ME!
     * @param  directory   DOCUMENT ME!
     * @param  title       DOCUMENT ME!
     * @param  filename    DOCUMENT ME!
     * @param  extension   DOCUMENT ME!
     */
    public GaebDownload(final ArrayList<ArrayList> massnBeans,
            final CidsBean cidsBean,
            final String directory,
            final String title,
            final String filename,
            final String extension) {
        this.massnBeans = massnBeans;
        this.cidsBean = cidsBean;
        this.directory = directory;
        this.title = title;
        this.filename = filename;
        this.extension = extension;

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
        BufferedWriter bw = null;

        try {
            final GaebExport ex = new GaebExport();
            final GaebContainer c = new GaebContainer();
            final List<GaebLvItem> l = new ArrayList<GaebLvItem>();
            int i = 0;

            if (massnBeans != null) {
                for (final ArrayList al : massnBeans) {
                    final GaebLvItem item = new GaebLvItem();
                    item.setId(((Integer)al.get(5)).toString());
                    item.setAmount(new BigDecimal((Double)al.get(3)));
                    item.setMeasure(String.valueOf(al.get(4)));
                    item.setDescription(String.valueOf(al.get(6)));
                    item.setDescriptionShort(String.valueOf(al.get(0)));
                    item.setOz(String.valueOf(++i));
                    l.add(item);
                }
            }
            c.setItemList(l);
            c.setProjectName(String.valueOf(cidsBean.getProperty("bezeichnung")));
            c.setDescription(String.valueOf(cidsBean.getProperty("bemerkung")));
            c.setNebenangebot(true);
            c.setBieterKommentar(true);
            final String result = ex.startExport(c);

            if (!Thread.interrupted()) {
                bw = new BufferedWriter(new FileWriter(fileToSaveTo));
                bw.write(result);
            } else {
                log.info("Download was interuppted");
                deleteFile();
                return;
            }
        } catch (Exception e) {
            error(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
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

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof GaebDownload)) {
            return false;
        }

        final GaebDownload other = (GaebDownload)obj;

        boolean result = true;

        if ((this.cidsBean != other.cidsBean) && ((this.cidsBean == null) || !this.cidsBean.equals(other.cidsBean))) {
            result &= false;
        }

        if ((this.fileToSaveTo == null) ? (other.fileToSaveTo != null)
                                        : (!this.fileToSaveTo.equals(other.fileToSaveTo))) {
            result &= false;
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = (43 * hash) + ((this.cidsBean != null) ? this.cidsBean.hashCode() : 0);
        hash = (43 * hash) + ((this.fileToSaveTo != null) ? this.fileToSaveTo.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean cancel() {
        final boolean cancelled = downloadFuture.cancel(true);
        if (cancelled) {
            status = State.ABORTED;
            stateChanged();
        }
        return cancelled;
    }

    /**
     * DOCUMENT ME!
     */
    private void deleteFile() {
        if (fileToSaveTo.exists() && fileToSaveTo.isFile()) {
            fileToSaveTo.delete();
        }
    }
}
