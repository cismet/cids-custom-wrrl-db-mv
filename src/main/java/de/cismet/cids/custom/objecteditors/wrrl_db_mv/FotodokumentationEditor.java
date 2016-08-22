/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * FotosEditor.java
 *
 * Created on 10.08.2010, 16:47:00
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;

import com.vividsolutions.jts.geom.Point;

import org.apache.commons.io.IOUtils;

import org.jdesktop.beansbinding.Converter;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.ref.SoftReference;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.stream.ImageInputStream;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.reports.FotodokumentationReport;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.WebDavHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.BeanInitializer;
import de.cismet.cids.editors.BeanInitializerProvider;
import de.cismet.cids.editors.DefaultBeanInitializer;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.commons.security.WebDavClient;

import de.cismet.netutil.Proxy;

import de.cismet.tools.BrowserLauncher;
import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.ExifReader;
import de.cismet.tools.PasswordEncrypter;

import de.cismet.tools.gui.CurvedFlowBackgroundPanel;
import de.cismet.tools.gui.ImageUtil;
import de.cismet.tools.gui.RoundedPanel;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FotodokumentationEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    BeanInitializerProvider,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final String FILE_PROTOCOL_PREFIX = "file://";
    private static final MetaClass WK_FG_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FotodokumentationEditor.class);
    private static final ImageIcon ERROR_ICON = new ImageIcon(FotodokumentationEditor.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/file-broken.png"));
    private static final ImageIcon IMAGE_FOLDER_ICON = new ImageIcon(FotodokumentationEditor.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/folder-image.png"));
    private static final ImageIcon FOLDER_ICON = new ImageIcon(FotodokumentationEditor.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/inode-directory.png"));
    private static final String PROP_NAME = "name";
    private static final String PROP_FILE = "file";
    private static final String PROP_ANGLE = "angle";
    private static final String WEB_DAV_USER;
    private static final String WEB_DAV_PASSWORD;
    private static final String WEB_DAV_DIRECTORY;
    private static final String FILE_PREFIX = "FOTO-";
    private static final Pattern IMAGE_FILE_PATTERN = Pattern.compile(
            ".*\\.(bmp|png|jpg|jpeg|tif|tiff|wbmp)$",
            Pattern.CASE_INSENSITIVE);
    private static final int CACHE_SIZE = 20;
    private static final Converter<CidsBean, String> GEOMETRY_CONVERTER = new Converter<CidsBean, String>() {

            @Override
            public String convertForward(final CidsBean value) {
                if (value != null) {
                    final Object geom = value.getProperty("geo_field");
                    return String.valueOf(geom);
                } else {
                    return "Keine Geometrie gesetzt";
                }
            }

            @Override
            public CidsBean convertReverse(final String value) {
                return null;
            }
        };

    private static final Map<String, SoftReference<BufferedImage>> IMAGE_CACHE =
        new LinkedHashMap<String, SoftReference<BufferedImage>>(CACHE_SIZE) {

            @Override
            protected boolean removeEldestEntry(final Entry<String, SoftReference<BufferedImage>> eldest) {
                return size() >= CACHE_SIZE;
            }
        };

    private static final Comparator<CidsBean> FOTO_BEAN_COMPARATOR = new Comparator<CidsBean>() {

            @Override
            public int compare(final CidsBean o1, final CidsBean o2) {
                Integer angle1 = (Integer)o1.getProperty(PROP_ANGLE);
                Integer angle2 = (Integer)o2.getProperty(PROP_ANGLE);
                if (angle1 == null) {
                    angle1 = -1;
                }
                if (angle2 == null) {
                    angle2 = -1;
                }
                int comp = angle1.compareTo(angle2);
                if (comp == 0) {
                    final Object name1 = o1.getProperty(PROP_NAME);
                    final Object name2 = o2.getProperty(PROP_NAME);
                    comp = AlphanumComparator.getInstance().compare(name1, name2);
                }
                return comp;
            }
        };

    static {
        final ResourceBundle bundle = ResourceBundle.getBundle("WebDav");
        String pass = bundle.getString("password");

        if ((pass != null) && pass.startsWith(PasswordEncrypter.CRYPT_PREFIX)) {
            pass = PasswordEncrypter.decryptString(pass);
        }

        WEB_DAV_PASSWORD = pass;
        WEB_DAV_USER = bundle.getString("username");
        WEB_DAV_DIRECTORY = bundle.getString("url");
    }

    //~ Instance fields --------------------------------------------------------

    private final JFileChooser fileChooser;
    private final Timer timer;
    private final CardLayout cardLayout;
    private final DropTarget dropTarget;
    private final PropertyChangeListener listRepaintListener;
//    private SortedListModel sortedFotoListModel;
    private ImageResizeWorker currentResizeWorker;
    private BufferedImage image;
    private CidsBean cidsBean;
    private CidsBean fotoCidsBean;
    private boolean listListenerEnabled;
    private boolean resizeListenerEnabled;
    private final boolean editable;
    private final List<CidsBean> removedFotoBeans = new ArrayList<CidsBean>();
    private final List<CidsBean> removeNewAddedFotoBean = new ArrayList<CidsBean>();
    private final WebDavClient webDavClient;
    private ComponentListener componenShownListener;
    private Dimension lastDims;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFoto;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnRemFoto;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAngle;
    private org.jdesktop.swingx.JXBusyLabel lblBusy;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateTxt;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDescriptionDoku;
    private javax.swing.JLabel lblDokumentationName;
    private javax.swing.JLabel lblFile;
    private javax.swing.JLabel lblFileTxt;
    private javax.swing.JLabel lblFotoList;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeadingVorschau;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTxtAngel;
    private javax.swing.JLabel lblTxtGeom;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserTxt;
    private javax.swing.JLabel lblWkFg;
    private javax.swing.JLabel lblWkFgDesc;
    private javax.swing.JList lstFotos;
    private javax.swing.JPanel panButtons;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panContrFotoList;
    private javax.swing.JPanel panFooterLeft;
    private javax.swing.JPanel panFooterRight;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private javax.swing.JPanel panPreview;
    private javax.swing.JPanel panTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.RoundedPanel roundedPanel2;
    private de.cismet.tools.gui.RoundedPanel rpVorschau;
    private javax.swing.JScrollPane scpDescription;
    private javax.swing.JScrollPane scpDescription1;
    private javax.swing.JScrollPane scpFotoList;
    private javax.swing.JSpinner spnAngle;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTextArea taDescriptionDoku;
    private javax.swing.JTextField txtDokumentationName;
    private javax.swing.JTextField txtName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form FotosEditor.
     */
    public FotodokumentationEditor() {
        this(true);
    }

    /**
     * Creates a new FotodokumentationEditor object.
     *
     * @param  editable  DOCUMENT ME!
     */
    public FotodokumentationEditor(final boolean editable) {
        this.editable = editable;
        this.listListenerEnabled = true;
        this.resizeListenerEnabled = true;
        this.webDavClient = new WebDavClient(Proxy.fromPreferences(), WEB_DAV_USER, WEB_DAV_PASSWORD);
        initComponents();
        if (editable) {
            new CidsBeanDropTarget(lblWkFg);
        }

        setEditable();
//        Proxy.fromPreferences()
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(final File f) {
                    return f.isDirectory() || IMAGE_FILE_PATTERN.matcher(f.getName()).matches();
                }

                @Override
                public String getDescription() {
                    return "Bilddateien";
                }
            });
        fileChooser.setMultiSelectionEnabled(true);
        timer = new Timer(300, new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (resizeListenerEnabled) {
//                    if (isShowing()) {
                            if (currentResizeWorker != null) {
                                currentResizeWorker.cancel(true);
                            }
                            currentResizeWorker = new ImageResizeWorker();
                            CismetThreadPool.execute(currentResizeWorker);
//                    } else {
//                        timer.restart();
//                    }
                        }
                    }
                });
        timer.setRepeats(false);
        cardLayout = (CardLayout)panCard.getLayout();
        spnAngle.setModel(new SpinnerNumberModel(0, 0, 359, 1));
        rpVorschau.addComponentListener(new ComponentAdapter() {

                @Override
                public void componentResized(final ComponentEvent e) {
                    // Bei Windows wird dieses Event manchmal in einer Endlosschleife gefeuert.
                    final double width = e.getComponent().getSize().getWidth();
                    final double height = e.getComponent().getSize().getHeight();
                    if ((lastDims == null)
                                || ((Math.abs(lastDims.getHeight() - height) > 5)
                                    || (Math.abs(lastDims.getWidth() - width) > 5))) {
                        if ((image != null) && !lblBusy.isBusy()) {
                            lastDims = e.getComponent().getSize();
                            showWait(true);
                            timer.restart();
                        }
                    }
                }
            });

        final DropTargetListener dropListener = new DropTargetAdapter() {

                @Override
                public void dragEnter(final DropTargetDragEvent dtde) {
//                if (image == null) {
                    lblPicture.setIcon(IMAGE_FOLDER_ICON);
//                }
                }

                @Override
                public void dragExit(final DropTargetEvent dte) {
//                if (image == null) {
                    lblPicture.setIcon(FOLDER_ICON);
//                }
                }

                @Override
                public void drop(final DropTargetDropEvent e) {
                    try {
                        final Transferable tr = e.getTransferable();
                        final DataFlavor[] flavors = tr.getTransferDataFlavors();
                        boolean isAccepted = false;
                        for (int i = 0; i < flavors.length; i++) {
                            if (flavors[i].isFlavorJavaFileListType()) {
                                // zunaechst annehmen
                                e.acceptDrop(e.getDropAction());
                                final List<File> files = (List<File>)tr.getTransferData(flavors[i]);
                                if ((files != null) && (files.size() > 0)) {
                                    CismetThreadPool.execute(new ImageUploadWorker(files));
                                }
                                e.dropComplete(true);
                                return;
                            } else if (flavors[i].isRepresentationClassInputStream()) {
                                // this is used under linux
                                if (!isAccepted) {
                                    e.acceptDrop(e.getDropAction());
                                    isAccepted = true;
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
                                                log.warn("File " + f.toString() + " does not exist.");
                                            }
                                        }
                                    }
                                }
                                br.close();

                                if ((fileList != null) && (fileList.size() > 0)) {
                                    CismetThreadPool.execute(new ImageUploadWorker(fileList));
                                    e.dropComplete(true);
                                    return;
                                }
                            }
                        }
                    } catch (Exception ex) {
                        log.warn(ex, ex);
                    }
                    // Problem ist aufgetreten
                    e.rejectDrop();
                }
            };
        if (editable) {
            dropTarget = new DropTarget(panPreview, dropListener);
        } else {
            dropTarget = null;
        }
        listRepaintListener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent evt) {
                    lstFotos.repaint();
                }
            };
        lblPicture.setIcon(FOLDER_ICON);
//        lstFotos.addListSelectionListener(new ListSelectionListener() {
//
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                defineButtonStatus();
//            }
//        });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "sb",
                "fotodokumentation",
                1,
                1280,
                1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setEditable() {
        scpDescription.getViewport().setOpaque(editable);
        scpDescription.setOpaque(editable);
        scpDescription1.getViewport().setOpaque(editable);
        scpDescription1.setOpaque(editable);
        setComponentEditable(taDescription);
        setComponentEditable(taDescriptionDoku);
        setComponentEditable(txtDokumentationName);
        setComponentEditable(txtName);
        if (!editable) {
            final EmptyBorder eBorder = new EmptyBorder(0, 0, 0, 0);
            scpDescription.setBorder(eBorder);
            scpDescription1.setBorder(eBorder);
            panContrFotoList.setVisible(false);
//            cbGeom.setEditable(false);
//            cbGeom.setEnabled(false);
//            spnAngle.getEditor().setOpaque(false);
//            spnAngle.setOpaque(false);
//            spnAngle.setBorder(null);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tComp  DOCUMENT ME!
     */
    private void setComponentEditable(final JTextComponent tComp) {
        tComp.setEditable(editable);
        tComp.setOpaque(editable);
        if (!editable) {
            tComp.setBorder(null);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        this.cidsBean = cidsBean;
        refreshWkFg();
        cidsBean.addPropertyChangeListener(this);

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            lstFotos.getModel().addListDataListener(new ListDataListener() {

                    @Override
                    public void intervalAdded(final ListDataEvent e) {
                        defineButtonStatus();
                    }

                    @Override
                    public void intervalRemoved(final ListDataEvent e) {
                        defineButtonStatus();
                    }

                    @Override
                    public void contentsChanged(final ListDataEvent e) {
                        defineButtonStatus();
                    }
                });
            if (lstFotos.getModel().getSize() > 0) {
                lstFotos.setSelectedIndex(0);
            } else {
                cardLayout.show(panCard, "preview");
            }
        } else {
            lstFotos.setModel(new DefaultListModel());
        }
        defineButtonStatus();
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshWkFg() {
        if (cidsBean == null) {
            lblWkFg.setText(" ");
        } else {
            try {
                final Object wk_fg = cidsBean.getProperty("wk_fg");
                if (wk_fg == null) {
                    lblWkFg.setText("<nicht gesetzt>");
                } else {
                    final int wk_fg_id = (Integer)wk_fg;
                    String query = "SELECT " + WK_FG_MC.getID() + ", wk_fg." + WK_FG_MC.getPrimaryKey() + " ";
                    query += "FROM " + WK_FG_MC.getTableName() + " AS wk_fg ";
                    query += "WHERE wk_fg.id = " + wk_fg_id + ";";
                    final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                    if (metaObjects.length > 0) {
                        final CidsBean wkfgBean = metaObjects[0].getBean();
                        lblWkFg.setText((String)wkfgBean.getProperty("wk_k"));
                    } else {
                        lblWkFg.setText("<nicht gefunden>");
                    }
                }
            } catch (Exception ex) {
                if (log.isDebugEnabled()) {
                    log.debug("fehler while refreshing wk_fg label", ex);
                }
                lblWkFg.setText("<Error>");
            }
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        timer.stop();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        if (fotoCidsBean != null) {
            fotoCidsBean.removePropertyChangeListener(listRepaintListener);
        }
        if (currentResizeWorker != null) {
            currentResizeWorker.cancel(true);
        }
        ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JPanel getPanTitle() {
        return panTitle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   fileName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    private BufferedImage downloadImageFromWebDAV(final String fileName) throws IOException {
        final String encodedFileName = WebDavHelper.encodeURL(fileName);
        final InputStream iStream = webDavClient.getInputStream(WEB_DAV_DIRECTORY
                        + encodedFileName);
        if (log.isDebugEnabled()) {
            log.debug("original: " + fileName + "\nweb dav path: " + WEB_DAV_DIRECTORY + encodedFileName);
        }
        try {
            final ImageInputStream iiStream = ImageIO.createImageInputStream(iStream);
            final Iterator<ImageReader> itReader = ImageIO.getImageReaders(iiStream);
            final ImageReader reader = itReader.next();
            final ProgressMonitor monitor = new ProgressMonitor(this, "Bild wird übertragen...", "", 0, 100);
//            monitor.setMillisToPopup(500);
            reader.addIIOReadProgressListener(new IIOReadProgressListener() {

                    @Override
                    public void sequenceStarted(final ImageReader source, final int minIndex) {
                    }

                    @Override
                    public void sequenceComplete(final ImageReader source) {
                    }

                    @Override
                    public void imageStarted(final ImageReader source, final int imageIndex) {
                        monitor.setProgress(monitor.getMinimum());
                    }

                    @Override
                    public void imageProgress(final ImageReader source, final float percentageDone) {
                        if (monitor.isCanceled()) {
                            try {
                                iiStream.close();
                            } catch (IOException ex) {
                                // NOP
                            }
                        } else {
                            monitor.setProgress(Math.round(percentageDone));
                        }
                    }

                    @Override
                    public void imageComplete(final ImageReader source) {
                        monitor.setProgress(monitor.getMaximum());
                    }

                    @Override
                    public void thumbnailStarted(final ImageReader source,
                            final int imageIndex,
                            final int thumbnailIndex) {
                    }

                    @Override
                    public void thumbnailProgress(final ImageReader source, final float percentageDone) {
                    }

                    @Override
                    public void thumbnailComplete(final ImageReader source) {
                    }

                    @Override
                    public void readAborted(final ImageReader source) {
                        monitor.close();
                    }
                });

            final ImageReadParam param = reader.getDefaultReadParam();
            reader.setInput(iiStream, true, true);
            final BufferedImage result;
            try {
                result = reader.read(0, param);
            } finally {
                reader.dispose();
                iiStream.close();
            }
            return result;
        } finally {
            IOUtils.closeQuietly(iStream);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblTxtAngel = new javax.swing.JLabel();
        lblTxtGeom = new javax.swing.JLabel();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        lblWkFgDesc = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        scpFotoList = new javax.swing.JScrollPane();
        lstFotos = new javax.swing.JList();
        lblFotoList = new javax.swing.JLabel();
        panContrFotoList = new javax.swing.JPanel();
        btnAddFoto = new javax.swing.JButton();
        btnRemFoto = new javax.swing.JButton();
        lblDokumentationName = new javax.swing.JLabel();
        txtDokumentationName = new javax.swing.JTextField();
        lblDescriptionDoku = new javax.swing.JLabel();
        scpDescription1 = new javax.swing.JScrollPane();
        taDescriptionDoku = new javax.swing.JTextArea();
        lblUserTxt = new javax.swing.JLabel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        jLabel1 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblDateTxt = new javax.swing.JLabel();
        lblWkFg = new WkFgLabel();
        rpVorschau = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingVorschau = new javax.swing.JLabel();
        panCard = new javax.swing.JPanel();
        lblBusy = new org.jdesktop.swingx.JXBusyLabel(new Dimension(75, 75));
        panPreview = new javax.swing.JPanel();
        lblPicture = new javax.swing.JLabel();
        final RoundedPanel rp = new RoundedPanel();
        rp.setBackground(new java.awt.Color(51, 51, 51));
        rp.setAlpha(255);
        panButtons = rp;
        panFooterLeft = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        panFooterRight = new javax.swing.JPanel();
        btnForward = new javax.swing.JButton();
        roundedPanel2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        lblAngle = new javax.swing.JLabel();
        spnAngle = new javax.swing.JSpinner();
        lblDescription = new javax.swing.JLabel();
        scpDescription = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblSpace = new javax.swing.JLabel();
        lblFile = new javax.swing.JLabel();
        lblFileTxt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        final CurvedFlowBackgroundPanel cfp = new CurvedFlowBackgroundPanel();
        // cfp.setOben(0.53944d);
        // cfp.setUnten(0.16844d);
        cfp.setRelativeHeights(true);
        cfp.setOben(230);
        cfp.setUnten(117);
        cfp.setBackground(new Color(255, 255, 255, 60));
        // cfp.setRelativeHeights(true);
        // cfp.setDesignMode(true);
        jPanel2 = cfp;

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                lstFotos,
                org.jdesktop.beansbinding.ELProperty.create("${selectedElement.angle}"),
                lblTxtAngel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.point}"),
                lblTxtGeom,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        binding.setConverter(GEOMETRY_CONVERTER);
        bindingGroup.addBinding(binding);

        panTitle.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblTitle.text")); // NOI18N

        btnReport.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer.png")));         // NOI18N
        btnReport.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationRenderer.btnReport.text"));                                                     // NOI18N
        btnReport.setBorder(null);
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.setFocusPainted(false);
        btnReport.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer_pressed.png"))); // NOI18N
        btnReport.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnReportActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout panTitleLayout = new javax.swing.GroupLayout(panTitle);
        panTitle.setLayout(panTitleLayout);
        panTitleLayout.setHorizontalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panTitleLayout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    190,
                    Short.MAX_VALUE).addComponent(btnReport)));
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle)
                        .addComponent(btnReport));

        setMinimumSize(new java.awt.Dimension(807, 485));
        setPreferredSize(new java.awt.Dimension(807, 683));
        setLayout(new java.awt.GridBagLayout());

        roundedPanel1.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 134;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        roundedPanel1.add(panHeadInfo, gridBagConstraints);

        lblWkFgDesc.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblWkFgDesc.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblWkFgDesc.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblWkFgDesc, gridBagConstraints);

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblUser.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblUser.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblUser, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        roundedPanel1.add(jLabel8, gridBagConstraints);

        scpFotoList.setMinimumSize(new java.awt.Dimension(258, 110));
        scpFotoList.setPreferredSize(new java.awt.Dimension(258, 110));

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.fotos}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstFotos);
        jListBinding.setSourceNullValue(null);
        jListBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jListBinding);

        lstFotos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstFotosValueChanged(evt);
                }
            });
        scpFotoList.setViewportView(lstFotos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(scpFotoList, gridBagConstraints);

        lblFotoList.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblFotoList.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblFotoList.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblFotoList, gridBagConstraints);

        panContrFotoList.setOpaque(false);
        panContrFotoList.setLayout(new java.awt.GridBagLayout());

        btnAddFoto.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddFoto.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "WkFgPanOne.btnAddImpactSrc.text"));                                                           // NOI18N
        btnAddFoto.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddFotoActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrFotoList.add(btnAddFoto, gridBagConstraints);

        btnRemFoto.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemFoto.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "WkFgPanOne.btnRemImpactSrc.text"));                                                              // NOI18N
        btnRemFoto.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemFotoActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrFotoList.add(btnRemFoto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        roundedPanel1.add(panContrFotoList, gridBagConstraints);

        lblDokumentationName.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDokumentationName.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblDokumentationName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel1.add(lblDokumentationName, gridBagConstraints);

        txtDokumentationName.setMinimumSize(new java.awt.Dimension(350, 25));
        txtDokumentationName.setPreferredSize(new java.awt.Dimension(350, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtDokumentationName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel1.add(txtDokumentationName, gridBagConstraints);

        lblDescriptionDoku.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescriptionDoku.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblDescriptionDoku.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblDescriptionDoku, gridBagConstraints);

        scpDescription1.setMinimumSize(new java.awt.Dimension(222, 75));
        scpDescription1.setPreferredSize(new java.awt.Dimension(222, 75));

        taDescriptionDoku.setColumns(20);
        taDescriptionDoku.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        taDescriptionDoku.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.description}"),
                taDescriptionDoku,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpDescription1.setViewportView(taDescriptionDoku);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(scpDescription1, gridBagConstraints);

        lblUserTxt.setMaximumSize(new java.awt.Dimension(40, 25));
        lblUserTxt.setMinimumSize(new java.awt.Dimension(40, 25));
        lblUserTxt.setPreferredSize(new java.awt.Dimension(40, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.av_user}"),
                lblUserTxt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblUserTxt, gridBagConstraints);

        if (editable) {
            binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                    org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                    this,
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean.point}"),
                    cbGeom,
                    org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
            binding.setSourceNullValue(null);
            binding.setSourceUnreadableValue(null);
            binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
            bindingGroup.addBinding(binding);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(cbGeom, gridBagConstraints);
        if (!editable) {
            roundedPanel1.remove(cbGeom);
            roundedPanel1.add(lblTxtGeom, gridBagConstraints);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(jLabel1, gridBagConstraints);

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDate.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblDate.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblDate, gridBagConstraints);

        lblDateTxt.setMaximumSize(new java.awt.Dimension(0, 25));
        lblDateTxt.setMinimumSize(new java.awt.Dimension(0, 25));
        lblDateTxt.setPreferredSize(new java.awt.Dimension(0, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.av_date}"),
                lblDateTxt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        binding.setConverter(TimestampConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblDateTxt, gridBagConstraints);

        lblWkFg.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblWkFg.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblWkFg, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(roundedPanel1, gridBagConstraints);

        rpVorschau.setLayout(new java.awt.GridBagLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeadingVorschau.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingVorschau.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblHeadingVorschau.text")); // NOI18N
        panHeadInfo1.add(lblHeadingVorschau);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        rpVorschau.add(panHeadInfo1, gridBagConstraints);

        panCard.setOpaque(false);
        panCard.setLayout(new java.awt.CardLayout());

        lblBusy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBusy.setMaximumSize(new java.awt.Dimension(140, 40));
        lblBusy.setMinimumSize(new java.awt.Dimension(140, 40));
        lblBusy.setPreferredSize(new java.awt.Dimension(140, 40));
        panCard.add(lblBusy, "busy");

        panPreview.setOpaque(false);
        panPreview.setLayout(new java.awt.GridBagLayout());

        lblPicture.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblPicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPicture.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPicture.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblPicture.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblPictureMouseClicked(evt);
                }
                @Override
                public void mouseEntered(final java.awt.event.MouseEvent evt) {
                    lblPictureMouseEntered(evt);
                }
                @Override
                public void mouseExited(final java.awt.event.MouseEvent evt) {
                    lblPictureMouseExited(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panPreview.add(lblPicture, gridBagConstraints);

        panCard.add(panPreview, "preview");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        rpVorschau.add(panCard, gridBagConstraints);

        panButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panButtons.setMaximumSize(new java.awt.Dimension(120, 40));
        panButtons.setMinimumSize(new java.awt.Dimension(120, 40));
        panButtons.setOpaque(false);
        panButtons.setPreferredSize(new java.awt.Dimension(120, 40));
        panButtons.setLayout(new java.awt.GridBagLayout());

        panFooterLeft.setMaximumSize(new java.awt.Dimension(20, 40));
        panFooterLeft.setMinimumSize(new java.awt.Dimension(20, 40));
        panFooterLeft.setOpaque(false);
        panFooterLeft.setPreferredSize(new java.awt.Dimension(20, 40));
        panFooterLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        btnBack.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left.png")));          // NOI18N
        btnBack.setBorder(null);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setEnabled(false);
        btnBack.setFocusPainted(false);
        btnBack.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-pressed.png")));  // NOI18N
        btnBack.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-selected.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBackActionPerformed(evt);
                }
            });
        panFooterLeft.add(btnBack);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panButtons.add(panFooterLeft, gridBagConstraints);

        panFooterRight.setMaximumSize(new java.awt.Dimension(20, 40));
        panFooterRight.setMinimumSize(new java.awt.Dimension(20, 40));
        panFooterRight.setOpaque(false);
        panFooterRight.setPreferredSize(new java.awt.Dimension(20, 40));
        panFooterRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        btnForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right.png")));          // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-pressed.png")));  // NOI18N
        btnForward.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-selected.png"))); // NOI18N
        btnForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnForwardActionPerformed(evt);
                }
            });
        panFooterRight.add(btnForward);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panButtons.add(panFooterRight, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        rpVorschau.add(panButtons, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        add(rpVorschau, gridBagConstraints);

        roundedPanel2.setLayout(new java.awt.GridBagLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblHeading1.text")); // NOI18N
        panHeadInfo2.add(lblHeading1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 134;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        roundedPanel2.add(panHeadInfo2, gridBagConstraints);

        lblAngle.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblAngle.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblAngle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel2.add(lblAngle, gridBagConstraints);

        spnAngle.setMinimumSize(new java.awt.Dimension(60, 25));
        spnAngle.setPreferredSize(new java.awt.Dimension(60, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                lstFotos,
                org.jdesktop.beansbinding.ELProperty.create("${selectedElement.angle}"),
                spnAngle,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel2.add(spnAngle, gridBagConstraints);
        if (!editable) {
            roundedPanel2.remove(spnAngle);
            roundedPanel2.add(lblTxtAngel, gridBagConstraints);
        }

        lblDescription.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescription.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblDescription.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel2.add(lblDescription, gridBagConstraints);

        scpDescription.setMinimumSize(new java.awt.Dimension(222, 72));

        taDescription.setColumns(20);
        taDescription.setFont(new java.awt.Font("Tahoma", 0, 11));
        taDescription.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                lstFotos,
                org.jdesktop.beansbinding.ELProperty.create("${selectedElement.description}"),
                taDescription,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        scpDescription.setViewportView(taDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel2.add(scpDescription, gridBagConstraints);

        lblName.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblName.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel2.add(lblName, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(350, 25));
        txtName.setPreferredSize(new java.awt.Dimension(350, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                lstFotos,
                org.jdesktop.beansbinding.ELProperty.create("${selectedElement.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel2.add(txtName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        roundedPanel2.add(lblSpace, gridBagConstraints);

        lblFile.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblFile.setText(org.openide.util.NbBundle.getMessage(
                FotodokumentationEditor.class,
                "FotodokumentationEditor.lblFile.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        roundedPanel2.add(lblFile, gridBagConstraints);

        lblFileTxt.setMaximumSize(new java.awt.Dimension(0, 25));
        lblFileTxt.setMinimumSize(new java.awt.Dimension(0, 25));
        lblFileTxt.setPreferredSize(new java.awt.Dimension(0, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                lstFotos,
                org.jdesktop.beansbinding.ELProperty.create("${selectedElement.file}"),
                lblFileTxt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel2.add(lblFileTxt, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.weightx = 1.0;
        roundedPanel2.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(roundedPanel2, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(50, 20));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(250, 0, 3, 0);
        add(jPanel2, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  wait  DOCUMENT ME!
     */
    private void showWait(final boolean wait) {
        if (wait) {
            if (!lblBusy.isBusy()) {
                cardLayout.show(panCard, "busy");
                lblPicture.setIcon(null);
                lblBusy.setBusy(true);
                btnAddFoto.setEnabled(false);
                btnRemFoto.setEnabled(false);
                lstFotos.setEnabled(false);
                btnBack.setEnabled(false);
                btnForward.setEnabled(false);
                if (dropTarget != null) {
                    dropTarget.setActive(false);
                }
            }
        } else {
            cardLayout.show(panCard, "preview");
            lblBusy.setBusy(false);
            btnAddFoto.setEnabled(true);
            btnRemFoto.setEnabled(true);
            lstFotos.setEnabled(true);
            defineButtonStatus();
            if (dropTarget != null) {
                dropTarget.setActive(true);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tooltip  DOCUMENT ME!
     */
    private void indicateError(final String tooltip) {
        lblPicture.setIcon(ERROR_ICON);
        lblPicture.setText("Fehler beim Übertragen des Bildes!");
        lblPicture.setToolTipText(tooltip);
    }
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblPictureMouseEntered(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblPictureMouseEntered
        lblPicture.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }                                                                          //GEN-LAST:event_lblPictureMouseEntered

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblPictureMouseExited(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblPictureMouseExited
        lblPicture.setCursor(Cursor.getDefaultCursor());
    }                                                                         //GEN-LAST:event_lblPictureMouseExited

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblPictureMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblPictureMouseClicked
        final Object selectionObj = lstFotos.getSelectedValue();
        if (selectionObj instanceof CidsBean) {
            final CidsBean selection = (CidsBean)selectionObj;
            final Object fileProperty = selection.getProperty(PROP_FILE);
            if (fileProperty != null) {
                try {
                    final String fileName = fileProperty.toString();
                    BrowserLauncher.openURL(WEB_DAV_DIRECTORY + WebDavHelper.encodeURL(fileName));
                } catch (Exception ex) {
                    log.warn(ex, ex);
                }
            }
        } else {
            btnAddFotoActionPerformed(null);
        }
    }                                                                          //GEN-LAST:event_lblPictureMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddFotoActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddFotoActionPerformed
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
            final File[] selFiles = fileChooser.getSelectedFiles();
            if ((selFiles != null) && (selFiles.length > 0)) {
                CismetThreadPool.execute(new ImageUploadWorker(Arrays.asList(selFiles)));
            }
        }
    }                                                                              //GEN-LAST:event_btnAddFotoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemFotoActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemFotoActionPerformed
        final Object[] selection = lstFotos.getSelectedValues();
        if ((selection != null) && (selection.length > 0)) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Sollen die Fotos wirklich gelöscht werden?",
                    "Fotos entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    listListenerEnabled = false;
                    final List<Object> removeList = Arrays.asList(selection);
                    final List<CidsBean> fotos = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
                    if (fotos != null) {
                        fotos.removeAll(removeList);
                    }
                    for (final Object toDeleteObj : removeList) {
                        if (toDeleteObj instanceof CidsBean) {
                            final CidsBean fotoToDelete = (CidsBean)toDeleteObj;
                            final String file = String.valueOf(fotoToDelete.getProperty(PROP_FILE));
                            IMAGE_CACHE.remove(file);
                            removedFotoBeans.add(fotoToDelete);
                        }
                    }
                } catch (Exception e) {
                    log.error(e, e);
                    UIUtil.showExceptionToUser(e, this);
                } finally {
                    listListenerEnabled = true;
                    final int modelSize = lstFotos.getModel().getSize();
                    if (modelSize > 0) {
                        lstFotos.setSelectedIndex(0);
                    } else {
                        image = null;
                        lblPicture.setIcon(FOLDER_ICON);
                    }
                }
            }
        }
    }                                                                              //GEN-LAST:event_btnRemFotoActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void loadFoto() {
        final Object fotoObj = lstFotos.getSelectedValue();
        if (fotoCidsBean != null) {
            fotoCidsBean.removePropertyChangeListener(listRepaintListener);
        }
        if (fotoObj instanceof CidsBean) {
            fotoCidsBean = (CidsBean)fotoObj;
            fotoCidsBean.addPropertyChangeListener(listRepaintListener);
            final Object fileObj = fotoCidsBean.getProperty(PROP_FILE);
            boolean cacheHit = false;
            if (fileObj != null) {
                final String file = fileObj.toString();
                final SoftReference<BufferedImage> cachedImageRef = IMAGE_CACHE.get(file);
                if (cachedImageRef != null) {
                    final BufferedImage cachedImage = cachedImageRef.get();
                    if (cachedImage != null) {
                        cacheHit = true;
                        image = cachedImage;
                        showWait(true);
                        timer.restart();
                    }
                }
                if (!cacheHit) {
                    CismetThreadPool.execute(new LoadSelectedImageWorker(file));
                }
            }
        } else {
            image = null;
            lblPicture.setIcon(FOLDER_ICON);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstFotosValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstFotosValueChanged
//        if (isShowing()) {
        if (!evt.getValueIsAdjusting() && listListenerEnabled) {
            loadFoto();
        }
//        }
    } //GEN-LAST:event_lstFotosValueChanged

    /**
     * DOCUMENT ME!
     */
    public void defineButtonStatus() {
        final int selectedIdx = lstFotos.getSelectedIndex();
        btnBack.setEnabled(selectedIdx > 0);
        btnForward.setEnabled((selectedIdx < (lstFotos.getModel().getSize() - 1)) && (selectedIdx > -1));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnBackActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBackActionPerformed
        lstFotos.setSelectedIndex(lstFotos.getSelectedIndex() - 1);
    }                                                                           //GEN-LAST:event_btnBackActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnForwardActionPerformed
        lstFotos.setSelectedIndex(lstFotos.getSelectedIndex() + 1);
    }                                                                              //GEN-LAST:event_btnForwardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnReportActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnReportActionPerformed
        FotodokumentationReport.showReport(cidsBean);
    }                                                                             //GEN-LAST:event_btnReportActionPerformed

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (EditorSaveStatus.SAVE_SUCCESS == event.getStatus()) {
            for (final CidsBean deleteBean : removedFotoBeans) {
                final String fileName = (String)deleteBean.getProperty(PROP_FILE);
                try {
                    WebDavHelper.deleteFileFromWebDAV(fileName, webDavClient, WEB_DAV_DIRECTORY);
                    deleteBean.delete();
                } catch (Exception ex) {
                    log.error(ex, ex);
                }
            }
        } else {
            for (final CidsBean deleteBean : removeNewAddedFotoBean) {
                final String fileName = (String)deleteBean.getProperty(PROP_FILE);
                WebDavHelper.deleteFileFromWebDAV(fileName, webDavClient, WEB_DAV_DIRECTORY);
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
            try {
                final User user = SessionManager.getSession().getUser();
                cidsBean.setProperty("av_user", user.getName() + "@" + user.getDomain());
                cidsBean.setProperty("av_date", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                log.error(ex, ex);
            }
        }
        return true;
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        lblTitle.setText(title);
    }

    @Override
    public BeanInitializer getBeanInitializer() {
        return new DefaultBeanInitializer(cidsBean) {

                @Override
                protected void processSimpleProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final Object simpleValueToProcess) throws Exception {
                    if (propertyName.equals("av_user") || propertyName.equals("av_date")) {
                        return;
                    }
                    super.processSimpleProperty(beanToInit, propertyName, simpleValueToProcess);
                }

//                @Override
//                protected void processArrayProperty(final CidsBean beanToInit,
//                        final String propertyName,
//                        final Collection<CidsBean> arrayValueToProcess) throws Exception {
//                    return;
//                }

                @Override
                protected void processComplexProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final CidsBean complexValueToProcess) throws Exception {
                    return;
                }
            };
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("wk_fg")) {
            refreshWkFg();
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class ImageResizeWorker extends SwingWorker<ImageIcon, Void> {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImageResizeWorker object.
         */
        public ImageResizeWorker() {
            // TODO image im EDT auslesen und final speichern!
            if (image != null) {
                lblPicture.setText("Wird neu skaliert...");
                lstFotos.setEnabled(false);
            }
//            log.fatal("RESIZE Image!", new Exception());
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected ImageIcon doInBackground() throws Exception {
            if (image != null) {
//                if (panButtons.getSize().getWidth() + 10 < panPreview.getSize().getWidth()) {
                // ImageIcon result = new ImageIcon(ImageUtil.adjustScale(image, panPreview, 20, 20));
                final ImageIcon result = new ImageIcon(ImageUtil.adjustScale(image, panCard, 20, 20));
                return result;
//                } else {
//                    return new ImageIcon(image);
//                }
            } else {
                return null;
            }
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                try {
                    resizeListenerEnabled = false;
                    final ImageIcon result = get();
                    lblPicture.setIcon(result);
                    lblPicture.setText("");
                    lblPicture.setToolTipText(null);
                } catch (InterruptedException ex) {
                    log.warn(ex, ex);
                } catch (ExecutionException ex) {
                    log.error(ex, ex);
                    lblPicture.setText("Fehler beim Skalieren!");
                } finally {
                    showWait(false);
                    if (currentResizeWorker == this) {
                        currentResizeWorker = null;
                    }
                    resizeListenerEnabled = true;
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class ImageUploadWorker extends SwingWorker<Collection<CidsBean>, Void> {

        //~ Static fields/initializers -----------------------------------------

        private static final String PROP_POINT = "point";

        //~ Instance fields ----------------------------------------------------

        private boolean exifInfosFound = false;

        private final Collection<File> fotos;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImageUploadWorker object.
         *
         * @param  fotos  DOCUMENT ME!
         */
        public ImageUploadWorker(final Collection<File> fotos) {
            this.fotos = fotos;
            lblPicture.setText("");
            lblPicture.setToolTipText(null);
            showWait(true);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Collection<CidsBean> doInBackground() throws Exception {
            final Collection<CidsBean> newBeans = new ArrayList<CidsBean>();
            for (final File imageFile : fotos) {
                final String webFileName = WebDavHelper.generateWebDAVFileName(FILE_PREFIX, imageFile);
                WebDavHelper.uploadFileToWebDAV(
                    webFileName,
                    imageFile,
                    WEB_DAV_DIRECTORY,
                    webDavClient,
                    FotodokumentationEditor.this);
                final CidsBean newFotoBean = CidsBeanSupport.createNewCidsBeanFromTableName("FOTO");
                newFotoBean.setProperty(PROP_NAME, imageFile.getName());
                newFotoBean.setProperty(PROP_FILE, webFileName);
                newFotoBean.setProperty(PROP_ANGLE, 0);
                final ExifReader reader = new ExifReader(imageFile);

                try {
                    if (cidsBean.getProperty(PROP_POINT) == null) {
                        // the coordinates of the image should be used
                        Point point = reader.getGpsCoords();
                        if (point != null) {
                            final Collection<Feature> features = new ArrayList<Feature>();
                            point = CrsTransformer.transformToDefaultCrs(point);
                            point.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
                            final PureNewFeature feature = new PureNewFeature(point);
                            // feature.setName(imageFile.getName());
                            features.add(feature);
                            //
                            // CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeature(feature);
                            final CidsBean geom = CidsBeanSupport.createNewCidsBeanFromTableName(
                                    LinearReferencingConstants.CN_GEOM);
                            MapUtil.zoomToFeatureCollection(features);

                            geom.setProperty(LinearReferencingConstants.PROP_GEOM_GEOFIELD, point);
                            cidsBean.setProperty(PROP_POINT, geom);
                            exifInfosFound = true;
                        }
                    }

                    newFotoBean.setProperty(PROP_ANGLE, (int)reader.getGpsDirection());
                } catch (Throwable ex) {
                    log.error("Error while reading exif data.", ex);
                }
                newBeans.add(newFotoBean);
            }
            return newBeans;
        }

        @Override
        protected void done() {
            try {
                final Collection<CidsBean> newBeans = get();
                if (!newBeans.isEmpty()) {
                    final List<CidsBean> oldBeans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
                    oldBeans.addAll(newBeans);
                    removeNewAddedFotoBean.addAll(newBeans);
                    lstFotos.setSelectedValue(newBeans.iterator().next(), true);
                } else {
                    lblPicture.setIcon(FOLDER_ICON);
                }
            } catch (InterruptedException ex) {
                log.warn(ex, ex);
            } catch (ExecutionException ex) {
                log.error(ex, ex);
            } finally {
                showWait(false);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class LoadSelectedImageWorker extends SwingWorker<BufferedImage, Void> {

        //~ Instance fields ----------------------------------------------------

        private final String file;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new LoadSelectedImageWorker object.
         *
         * @param  toLoad  DOCUMENT ME!
         */
        public LoadSelectedImageWorker(final String toLoad) {
            this.file = toLoad;
            lblPicture.setText("");
            lblPicture.setToolTipText(null);
            showWait(true);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected BufferedImage doInBackground() throws Exception {
            if ((file != null) && (file.length() > 0)) {
                return downloadImageFromWebDAV(file);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                image = get();
                if (image != null) {
                    IMAGE_CACHE.put(file, new SoftReference<BufferedImage>(image));
                    timer.restart();
                } else {
                    indicateError("Bild konnte nicht geladen werden: Unbekanntes Bildformat");
                }
            } catch (InterruptedException ex) {
                image = null;
                log.warn(ex, ex);
            } catch (ExecutionException ex) {
                image = null;
                log.error(ex, ex);
                String causeMessage = "";
                final Throwable cause = ex.getCause();
                if (cause != null) {
                    causeMessage = cause.getMessage();
                }
                indicateError(causeMessage);
            } finally {
                if (image == null) {
                    showWait(false);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class WkFgLabel extends JLabel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (editable) {
                CidsBean toAdd = null;
                for (final CidsBean bean : beans) {
                    if (bean.getMetaObject().getMetaClass().getName().equals("wk_fg")) {
                        toAdd = bean;
                        break;
                    }
                }

                if (toAdd != null) {
                    try {
                        cidsBean.setProperty("wk_fg", toAdd.getProperty("id"));
                        cidsBean.setProperty("wk_k", toAdd.getProperty("wk_k"));
                        refreshWkFg();
                    } catch (Exception ex) {
                        log.error("error while setting wk_fg", ex);
                    }
                }
            }
        }
    }
}
