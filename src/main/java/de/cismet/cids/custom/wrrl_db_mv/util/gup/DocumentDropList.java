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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JList;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.WebDavHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.commons.security.WebDavClient;

import de.cismet.netutil.Proxy;

import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.PasswordEncrypter;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitDialog;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;
import de.cismet.tools.gui.downloadmanager.WebDavDownload;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class DocumentDropList extends JList implements DropTargetListener, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            DocumentDropList.class);
    private static final String FILE_PROTOCOL_PREFIX = "file://";

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean = null;

    private final String WEB_DAV_USER;
    private final String WEB_DAV_PASSWORD;
    private final String WEB_DAV_DIRECTORY;

    private boolean readOnly = false;
    private WebDavClient webDavClient = null;
    private final List<CidsBean> removedFotoBeans = new ArrayList<CidsBean>();
    private final List<CidsBean> removeNewAddedFotoBean = new ArrayList<CidsBean>();
    private String beanProperty = "dokumente";
    private DropTarget dropTarget = null;

    //~ Instance initializers --------------------------------------------------

    {
        final ResourceBundle bundle = ResourceBundle.getBundle("WebDav");
        String pass = bundle.getString("password");

        if ((pass != null) && pass.startsWith(PasswordEncrypter.CRYPT_PREFIX)) {
            pass = PasswordEncrypter.decryptString(pass);
        }

        WEB_DAV_PASSWORD = pass;
        WEB_DAV_USER = bundle.getString("username");
        WEB_DAV_DIRECTORY = bundle.getString("url");
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DocumentDragList object.
     *
     * @param  readOnly      DOCUMENT ME!
     * @param  beanProperty  DOCUMENT ME!
     */
    public DocumentDropList(final boolean readOnly, final String beanProperty) {
        if (!readOnly) {
//                new CidsBeanDropTarget(this);
            dropTarget = new DropTarget(this, this);
        }
        this.webDavClient = new WebDavClient(Proxy.fromPreferences(), WEB_DAV_USER, WEB_DAV_PASSWORD, true);
        this.beanProperty = beanProperty;
        this.readOnly = readOnly;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
    }

    @Override
    public void dragEnter(final DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(final DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(final DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(final DropTargetEvent dte) {
    }

    @Override
    public void drop(final DropTargetDropEvent dtde) {
        boolean a = false;
        try {
            final Transferable tr = dtde.getTransferable();
            final DataFlavor[] flavors = tr.getTransferDataFlavors();

            for (int i = 0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    // zunaechst annehmen
                    dtde.acceptDrop(dtde.getDropAction());
                    final List<File> files = (List<File>)tr.getTransferData(flavors[i]);
                    if ((files != null) && (files.size() > 0)) {
                        addFiles(files);
                    }
                    dtde.dropComplete(true);
                    return;
                } else if (flavors[i].isRepresentationClassInputStream()) {
                    // this is used under linux
                    if (!a) {
                        dtde.acceptDrop(dtde.getDropAction());
                        a = true;
                    }
                    final BufferedReader br = new BufferedReader(new InputStreamReader(
                                (InputStream)tr.getTransferData(flavors[i])));
                    String tmp = null;
                    final List<File> fileList = new ArrayList<File>();
                    while ((tmp = br.readLine()) != null) {
                        if (tmp.trim().startsWith(FILE_PROTOCOL_PREFIX)) {
                            File f = new File(tmp.trim().substring(FILE_PROTOCOL_PREFIX.length()));
                            if (f.exists()) {
                                fileList.add(f);
                            } else {
                                f = new File(URLDecoder.decode(
                                            tmp.trim().substring(FILE_PROTOCOL_PREFIX.length()),
                                            "UTF-8"));

                                if (f.exists()) {
                                    fileList.add(f);
                                } else {
                                    LOG.warn("File " + f.toString() + " does not exist.");
                                }
                            }
                        }
                    }
                    br.close();

                    if ((fileList != null) && (fileList.size() > 0)) {
                        addFiles(fileList);
                        dtde.dropComplete(true);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            LOG.warn(ex, ex);
        }
        // Problem ist aufgetreten
        dtde.rejectDrop();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fileList  DOCUMENT ME!
     */
    public void addFiles(final List<File> fileList) {
        if ((fileList != null) && (fileList.size() > 0)) {
            final WaitDialog wd = new WaitDialog(
                    StaticSwingTools.getParentFrame(this),
                    true,
                    "Speichere Dokument",
                    null);
            CismetThreadPool.execute(new DocumentDropList.DocumentUploadWorker(fileList, wd));
            StaticSwingTools.showDialog(wd);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  index  DOCUMENT ME!
     */
    public void removeObject(final int index) {
        final CidsBean bean = (CidsBean)getModel().getElementAt(index);
        final List<CidsBean> docs = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, beanProperty);

        docs.remove(bean);
        removedFotoBeans.add(bean);
    }

    /**
     * DOCUMENT ME!
     */
    public void downloadSelectedDocs() {
        final Object[] docs = getSelectedValues();

        for (final Object doc : docs) {
            if (doc instanceof CidsBean) {
                final CidsBean bean = (CidsBean)doc;
                if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(this)) {
                    final String jobname = DownloadManagerDialog.getInstance().getJobName();
                    final String name = bean.getProperty("name").toString();
                    final String file = bean.getProperty("file").toString();
                    final String extension = name.substring(name.lastIndexOf("."));
                    final String filename = name.substring(0, name.lastIndexOf("."));

                    DownloadManager.instance()
                            .add(new WebDavDownload(
                                    webDavClient,
                                    WEB_DAV_DIRECTORY
                                    + WebDavHelper.encodeURL(file),
                                    jobname,
                                    filename
                                    + extension,
                                    filename,
                                    extension));
                }
            }
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (EditorSaveListener.EditorSaveStatus.SAVE_SUCCESS == event.getStatus()) {
            for (final CidsBean deleteBean : removedFotoBeans) {
                final String fileName = (String)deleteBean.getProperty("file");
                try {
                    WebDavHelper.deleteFileFromWebDAV(fileName, webDavClient, WEB_DAV_DIRECTORY);
                    deleteBean.delete();
                } catch (Exception ex) {
                    LOG.error(ex, ex);
                }
            }
        } else {
            for (final CidsBean deleteBean : removeNewAddedFotoBean) {
                final String fileName = (String)deleteBean.getProperty("file");
                WebDavHelper.deleteFileFromWebDAV(fileName, webDavClient, WEB_DAV_DIRECTORY);
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class DocumentUploadWorker extends SwingWorker<Collection<CidsBean>, Void> {

        //~ Static fields/initializers -----------------------------------------

        private static final String FILE_PREFIX = "DOC-";

        //~ Instance fields ----------------------------------------------------

        private final Collection<File> docs;
        private final WaitDialog wd;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImageUploadWorker object.
         *
         * @param  docs  fotos DOCUMENT ME!
         * @param  wd    DOCUMENT ME!
         */
        public DocumentUploadWorker(final Collection<File> docs, final WaitDialog wd) {
            this.docs = docs;
            this.wd = wd;
//                lblPicture.setText("");
//                lblPicture.setToolTipText(null);
//                showWait(true);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Collection<CidsBean> doInBackground() throws Exception {
            try {
                final Collection<CidsBean> newBeans = new ArrayList<CidsBean>();
                for (final File imageFile : docs) {
                    final String webFileName = WebDavHelper.generateWebDAVFileName(FILE_PREFIX, imageFile);
                    WebDavHelper.uploadFileToWebDAV(
                        webFileName,
                        imageFile,
                        WEB_DAV_DIRECTORY,
                        webDavClient,
                        DocumentDropList.this);
                    final CidsBean newFotoBean = CidsBeanSupport.createNewCidsBeanFromTableName("GUP_DOKUMENT");
                    newFotoBean.setProperty("name", imageFile.getName());
                    newFotoBean.setProperty("file", webFileName);
                    newBeans.add(newFotoBean);
                }
                return newBeans;
            } finally {
                while (!wd.isVisible()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // nothing to do
                    }
                }

                wd.setVisible(false);
                wd.dispose();
            }
        }

        @Override
        protected void done() {
            try {
                final Collection<CidsBean> newBeans = get();
                if (!newBeans.isEmpty()) {
                    final List<CidsBean> oldBeans = CidsBeanSupport.getBeanCollectionFromProperty(
                            cidsBean,
                            beanProperty);
                    oldBeans.addAll(newBeans);
                    removeNewAddedFotoBean.addAll(newBeans);
                    setSelectedValue(newBeans.iterator().next(), true);
                }
            } catch (InterruptedException ex) {
                LOG.warn(ex, ex);
            } catch (ExecutionException ex) {
                LOG.error(ex, ex);
            } finally {
            }
        }
    }
}
