
/*
 * FotosEditor.java
 *
 * Created on 10.08.2010, 16:47:00
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.ImageUtil;
import de.cismet.cids.custom.util.UIUtil;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;
import de.cismet.tools.BrowserLauncher;
import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;
import java.awt.CardLayout;
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author stefan
 */
public class FotodokumentationEditor extends javax.swing.JPanel implements DisposableCidsBeanStore {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FotodokumentationEditor.class);
    private static final ImageIcon ERROR_ICON = new ImageIcon(FotodokumentationEditor.class.getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/file-broken.png"));
    private static final ImageIcon IMAGE_FOLDER_ICON = new ImageIcon(FotodokumentationEditor.class.getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/folder-image.png"));
    private static final ImageIcon FOLDER_ICON = new ImageIcon(FotodokumentationEditor.class.getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/inode-directory.png"));
    private static final String WEB_DAV_USER = "cismet";
    private static final String WEB_DAV_PASSWORD = "karusu20";
    private static final String WEB_DAV_DIRECTORY = "http://webdav.cismet.de/images/";
    private static final Pattern IMAGE_FILE_PATTERN = Pattern.compile(".*\\.(bmp|png|jpg|jpeg|tif|tiff|wbmp)$", Pattern.CASE_INSENSITIVE);
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
    private static final int CACHE_SIZE = 3;
    private static final Map<String, SoftReference<BufferedImage>> IMAGE_CACHE = new LinkedHashMap<String, SoftReference<BufferedImage>>(CACHE_SIZE) {

        @Override
        public SoftReference<BufferedImage> put(String key, SoftReference<BufferedImage> value) {
            if (size() >= CACHE_SIZE) {
                Iterator<?> it = entrySet().iterator();
                while (size() > CACHE_SIZE - CACHE_SIZE / 10) {
                    it.remove();
                }
            }
            return super.put(key, value);
        }
    };
    private static final Comparator<CidsBean> FOTO_BEAN_COMPARATOR = new Comparator<CidsBean>() {

        @Override
        public int compare(CidsBean o1, CidsBean o2) {
            Integer angle1 = (Integer) o1.getProperty("angle");
            Integer angle2 = (Integer) o2.getProperty("angle");
            if (angle1 == null) {
                angle1 = -1;
            }
            if (angle2 == null) {
                angle2 = -1;
            }
            int comp = angle1.compareTo(angle2);
            if (comp == 0) {
                Object name1 = o1.getProperty("name");
                Object name2 = o2.getProperty("name");
                comp = AlphanumComparator.getInstance().compare(name1, name2);
            }
            return comp;
        }
    };

    /** Creates new form FotosEditor */
    public FotodokumentationEditor() {
        initComponents();
//        sortedFotoListModel = new SortedListModel(new DefaultListModel(), SortedListModel.SortOrder.ASCENDING, FOTO_BEAN_COMPARATOR);
//        lstFotos.setModel(sortedFotoListModel);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || IMAGE_FILE_PATTERN.matcher(f.getName()).matches();
            }

            @Override
            public String getDescription() {
                return "Bilddateien";
            }
        });
        fileChooser.setMultiSelectionEnabled(true);
        timer = new Timer(175, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentResizeWorker != null) {
                    currentResizeWorker.cancel(true);
                }
                currentResizeWorker = new ImageResizeWorker();
                CismetThreadPool.execute(currentResizeWorker);
            }
        });
        timer.setRepeats(false);
        cardLayout = (CardLayout) panCard.getLayout();
        spnAngle.setModel(new SpinnerNumberModel(0, 0, 359, 1));
        rpVorschau.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (image != null && !lblBusy.isBusy()) {
                    showWait(true);
                    timer.restart();
                }
            }
        });
        DropTargetListener dropListener = new DropTargetAdapter() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
//                if (image == null) {
                lblPicture.setIcon(IMAGE_FOLDER_ICON);
//                }
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
//                if (image == null) {
                lblPicture.setIcon(FOLDER_ICON);
//                }
            }

            @Override
            public void drop(DropTargetDropEvent e) {
                try {
                    Transferable tr = e.getTransferable();
                    DataFlavor[] flavors = tr.getTransferDataFlavors();
                    for (int i = 0; i < flavors.length; i++) {
                        if (flavors[i].isFlavorJavaFileListType()) {
                            //zunaechst annehmen
                            e.acceptDrop(e.getDropAction());
                            List<File> files = (List<File>) tr.getTransferData(flavors[i]);
                            if (files != null && files.size() > 0) {
                                CismetThreadPool.execute(new ImageUploadWorker(files));
                            }
                            e.dropComplete(true);
                            return;
                        }
                    }
                } catch (Exception ex) {
                    log.warn(ex, ex);
                }
                //Problem ist aufgetreten
                e.rejectDrop();

            }
        };
        dropTarget = new DropTarget(panPreview, dropListener);
        listRepaintListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                lstFotos.repaint();
            }
        };
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            bindingGroup.bind();
            if (lstFotos.getModel().getSize() > 0) {
                lstFotos.setSelectedIndex(0);
            }
        } else {
//            sortedFotoListModel = null;
            lstFotos.setModel(new DefaultListModel());
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        if (fotoCidsBean != null) {
            fotoCidsBean.removePropertyChangeListener(listRepaintListener);
        }
    }

    private boolean deleteFileFromWebDAV(String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                final Sardine sardine = SardineFactory.begin(WEB_DAV_USER, WEB_DAV_PASSWORD);
                sardine.delete(WEB_DAV_DIRECTORY + fileName);
                return true;
            } catch (Exception ex) {
                log.error(ex, ex);
            }
        }
        return false;
    }

    private void uploadFileToWebDAV(String fileName, File toUpload) throws SardineException, FileNotFoundException {
        final Sardine sardine = SardineFactory.begin(WEB_DAV_USER, WEB_DAV_PASSWORD);
        final BufferedInputStream bfis = new BufferedInputStream(new ProgressMonitorInputStream(this, "Bild wird übertragen...", new FileInputStream(toUpload)));
        try {
            sardine.put(WEB_DAV_DIRECTORY + fileName, bfis);
        } finally {
            IOUtils.closeQuietly(bfis);
        }
    }

    private BufferedImage downloadImageFromWebDAV(String fileName) throws SardineException, IOException {
        final Sardine sardine = SardineFactory.begin(WEB_DAV_USER, WEB_DAV_PASSWORD);
        final InputStream iStream = sardine.getInputStream(WEB_DAV_DIRECTORY + fileName);
        try {
            final ImageInputStream iiStream = ImageIO.createImageInputStream(iStream);
            final ImageReader reader = ImageIO.getImageReaders(iiStream).next();
            final ProgressMonitor monitor = new ProgressMonitor(this, "Bild wird übertragen...", "", 0, 100);
//            monitor.setMillisToPopup(500);
            reader.addIIOReadProgressListener(new IIOReadProgressListener() {

                @Override
                public void sequenceStarted(ImageReader source, int minIndex) {
                }

                @Override
                public void sequenceComplete(ImageReader source) {
                }

                @Override
                public void imageStarted(ImageReader source, int imageIndex) {
                    monitor.setProgress(monitor.getMinimum());
                }

                @Override
                public void imageProgress(ImageReader source, float percentageDone) {
                    if (monitor.isCanceled()) {
                        try {
                            iiStream.close();
                        } catch (IOException ex) {
                            //NOP
                        }
                    } else {
                        monitor.setProgress(Math.round(percentageDone));
                    }
                }

                @Override
                public void imageComplete(ImageReader source) {
                    monitor.setProgress(monitor.getMaximum());
                }

                @Override
                public void thumbnailStarted(ImageReader source, int imageIndex, int thumbnailIndex) {
                }

                @Override
                public void thumbnailProgress(ImageReader source, float percentageDone) {
                }

                @Override
                public void thumbnailComplete(ImageReader source) {
                }

                @Override
                public void readAborted(ImageReader source) {
                    monitor.close();
                }
            });
            ImageReadParam param = reader.getDefaultReadParam();
            reader.setInput(iiStream, true, true);
            BufferedImage result;
            try {
                result = reader.read(0, param);
            } finally {
                reader.dispose();
                iiStream.close();
            }
//            final BufferedImage result = ImageIO.read(iStream);
            return result;
        } finally {
            IOUtils.closeQuietly(iStream);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        lblStaeun = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        txtStaeun = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
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
        rpVorschau = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingVorschau = new javax.swing.JLabel();
        panCard = new javax.swing.JPanel();
        lblBusy = new org.jdesktop.swingx.JXBusyLabel(new Dimension(75,75));
        panPreview = new javax.swing.JPanel();
        lblPicture = new javax.swing.JLabel();
        rpDetail = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingDetail = new javax.swing.JLabel();
        lblAngle = new javax.swing.JLabel();
        spnAngle = new javax.swing.JSpinner();
        lblDate = new javax.swing.JLabel();
        dpDate = new org.jdesktop.swingx.JXDatePicker();
        lblDescription = new javax.swing.JLabel();
        scpDescription = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblSpace = new javax.swing.JLabel();
        lblFile = new javax.swing.JLabel();
        lblFileTxt = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        roundedPanel1.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Fotodokumentation");
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

        lblStaeun.setText("staeun");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblStaeun, gridBagConstraints);

        lblUser.setText("av_user");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblUser, gridBagConstraints);

        txtStaeun.setMinimumSize(new java.awt.Dimension(350, 20));
        txtStaeun.setPreferredSize(new java.awt.Dimension(350, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.staeun}"), txtStaeun, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(txtStaeun, gridBagConstraints);

        txtUser.setMinimumSize(new java.awt.Dimension(350, 20));
        txtUser.setPreferredSize(new java.awt.Dimension(350, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cisdBean.av_user}"), txtUser, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(txtUser, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        roundedPanel1.add(jLabel8, gridBagConstraints);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fotos}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, lstFotos);
        jListBinding.setSourceNullValue(null);
        jListBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jListBinding);

        lstFotos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFotosValueChanged(evt);
            }
        });
        scpFotoList.setViewportView(lstFotos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(scpFotoList, gridBagConstraints);

        lblFotoList.setText("Fotos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblFotoList, gridBagConstraints);

        panContrFotoList.setOpaque(false);
        panContrFotoList.setLayout(new java.awt.GridBagLayout());

        btnAddFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddFoto.setText(org.openide.util.NbBundle.getMessage(FotodokumentationEditor.class, "WkFgPanOne.btnAddImpactSrc.text")); // NOI18N
        btnAddFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFotoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrFotoList.add(btnAddFoto, gridBagConstraints);

        btnRemFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemFoto.setText(org.openide.util.NbBundle.getMessage(FotodokumentationEditor.class, "WkFgPanOne.btnRemImpactSrc.text")); // NOI18N
        btnRemFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        roundedPanel1.add(panContrFotoList, gridBagConstraints);

        lblDokumentationName.setText("name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel1.add(lblDokumentationName, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"), txtDokumentationName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        roundedPanel1.add(txtDokumentationName, gridBagConstraints);

        lblDescriptionDoku.setText("description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(lblDescriptionDoku, gridBagConstraints);

        taDescriptionDoku.setColumns(20);
        taDescriptionDoku.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.description}"), taDescriptionDoku, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpDescription1.setViewportView(taDescriptionDoku);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(scpDescription1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(roundedPanel1, gridBagConstraints);

        rpVorschau.setLayout(new java.awt.GridBagLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeadingVorschau.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingVorschau.setText("Ausgewähltes Foto");
        panHeadInfo1.add(lblHeadingVorschau);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        rpVorschau.add(panHeadInfo1, gridBagConstraints);

        panCard.setOpaque(false);
        panCard.setLayout(new java.awt.CardLayout());

        lblBusy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panCard.add(lblBusy, "busy");

        panPreview.setOpaque(false);
        panPreview.setLayout(new java.awt.GridBagLayout());

        lblPicture.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblPicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPicture.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPicture.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblPicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPictureMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPictureMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
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

        rpDetail.setLayout(new java.awt.GridBagLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeadingDetail.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingDetail.setText("Informationen zum Foto");
        panHeadInfo2.add(lblHeadingDetail);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        rpDetail.add(panHeadInfo2, gridBagConstraints);

        lblAngle.setText("angle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(lblAngle, gridBagConstraints);

        spnAngle.setMinimumSize(new java.awt.Dimension(60, 20));
        spnAngle.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFotos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.angle}"), spnAngle, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(spnAngle, gridBagConstraints);

        lblDate.setText("av_date");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(lblDate, gridBagConstraints);

        dpDate.setMaximumSize(new java.awt.Dimension(150, 22));
        dpDate.setMinimumSize(new java.awt.Dimension(150, 22));
        dpDate.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(dpDate, gridBagConstraints);

        lblDescription.setText("description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(lblDescription, gridBagConstraints);

        taDescription.setColumns(20);
        taDescription.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFotos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.description}"), taDescription, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpDescription.setViewportView(taDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(scpDescription, gridBagConstraints);

        lblName.setText("name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        rpDetail.add(lblName, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(350, 20));
        txtName.setPreferredSize(new java.awt.Dimension(350, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFotos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.name}"), txtName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        rpDetail.add(txtName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        rpDetail.add(lblSpace, gridBagConstraints);

        lblFile.setText("file");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        rpDetail.add(lblFile, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFotos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.file}"), lblFileTxt, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpDetail.add(lblFileTxt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        rpVorschau.add(rpDetail, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(rpVorschau, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void showWait(boolean wait) {
        if (wait) {
            if (!lblBusy.isBusy()) {
                cardLayout.show(panCard, "busy");
                lblPicture.setIcon(null);
                lblBusy.setBusy(true);
                btnAddFoto.setEnabled(false);
                btnRemFoto.setEnabled(false);
                lstFotos.setEnabled(false);
                dropTarget.setActive(false);
            }
        } else {
            cardLayout.show(panCard, "preview");
            lblBusy.setBusy(false);
            btnAddFoto.setEnabled(true);
            btnRemFoto.setEnabled(true);
            lstFotos.setEnabled(true);
            dropTarget.setActive(true);
        }
    }

    private void indicateError(String tooltip) {
        lblPicture.setIcon(ERROR_ICON);
        lblPicture.setText("Fehler beim Übertragen des Bildes!");
        lblPicture.setToolTipText(tooltip);
    }
    private void lblPictureMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPictureMouseEntered
        lblPicture.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblPictureMouseEntered

    private void lblPictureMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPictureMouseExited
        lblPicture.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_lblPictureMouseExited

    private void lblPictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPictureMouseClicked
        Object selectionObj = lstFotos.getSelectedValue();
        if (selectionObj instanceof CidsBean) {
            CidsBean selection = (CidsBean) selectionObj;
            Object fileProperty = selection.getProperty("file");
            if (fileProperty != null) {
                try {
                    BrowserLauncher.openURL(WEB_DAV_DIRECTORY + fileProperty.toString());
                } catch (Exception ex) {
                    log.warn(ex, ex);
                }
            }
        }
    }//GEN-LAST:event_lblPictureMouseClicked

    private void btnAddFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFotoActionPerformed
        fileChooser.showOpenDialog(this);
        final File[] selFiles = fileChooser.getSelectedFiles();
        if (selFiles != null) {
            CismetThreadPool.execute(new ImageUploadWorker(Arrays.asList(selFiles)));
        }

}//GEN-LAST:event_btnAddFotoActionPerformed

    private void btnRemFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemFotoActionPerformed
        final Object[] selection = lstFotos.getSelectedValues();
        if (selection != null && selection.length > 0) {
            final int answer = JOptionPane.showConfirmDialog(this, "Sollen die Fotos wirklich gelöscht werden?", "Fotos entfernen", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    List<Object> removeList = Arrays.asList(selection);
                    List<CidsBean> fotos = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
                    if (fotos != null) {
                        fotos.removeAll(removeList);
                        for (CidsBean foto : fotos) {
                            IMAGE_CACHE.remove(String.valueOf(foto.getProperty("file")));
                        }
                    }
                } catch (Exception e) {
                    log.error(e, e);
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
}//GEN-LAST:event_btnRemFotoActionPerformed

    private void lstFotosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFotosValueChanged
        if (!evt.getValueIsAdjusting()) {
            Object fotoObj = lstFotos.getSelectedValue();
            if (fotoObj instanceof CidsBean) {
                if (fotoCidsBean != null) {
                    fotoCidsBean.removePropertyChangeListener(listRepaintListener);

                }
                fotoCidsBean = (CidsBean) fotoObj;
                fotoCidsBean.addPropertyChangeListener(listRepaintListener);
                Object fileObj = fotoCidsBean.getProperty("file");
                boolean cacheHit = false;
                if (fileObj != null) {
                    String file = fileObj.toString();
                    SoftReference<BufferedImage> cachedImageRef = IMAGE_CACHE.get(file);
                    if (cachedImageRef != null) {
                        BufferedImage cachedImage = cachedImageRef.get();
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
            } else if (fotoCidsBean != null) {
                fotoCidsBean.removePropertyChangeListener(listRepaintListener);
            }
        }
    }//GEN-LAST:event_lstFotosValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFoto;
    private javax.swing.JButton btnRemFoto;
    private org.jdesktop.swingx.JXDatePicker dpDate;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblAngle;
    private org.jdesktop.swingx.JXBusyLabel lblBusy;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDescriptionDoku;
    private javax.swing.JLabel lblDokumentationName;
    private javax.swing.JLabel lblFile;
    private javax.swing.JLabel lblFileTxt;
    private javax.swing.JLabel lblFotoList;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeadingDetail;
    private javax.swing.JLabel lblHeadingVorschau;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblStaeun;
    private javax.swing.JLabel lblUser;
    private javax.swing.JList lstFotos;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panContrFotoList;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private javax.swing.JPanel panPreview;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.RoundedPanel rpDetail;
    private de.cismet.tools.gui.RoundedPanel rpVorschau;
    private javax.swing.JScrollPane scpDescription;
    private javax.swing.JScrollPane scpDescription1;
    private javax.swing.JScrollPane scpFotoList;
    private javax.swing.JSpinner spnAngle;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTextArea taDescriptionDoku;
    private javax.swing.JTextField txtDokumentationName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStaeun;
    private javax.swing.JTextField txtUser;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private String generateWebDAVFileName(File originalFile) {
        String[] fileNameSplit = originalFile.getName().split("\\.");
        String webFileName = "FOTODOK-" + Math.abs(originalFile.getName().hashCode()) + "-" + System.currentTimeMillis();
        if (fileNameSplit.length > 1) {
            String ext = fileNameSplit[fileNameSplit.length - 1];
            webFileName += "." + ext;
        }
        return webFileName;
    }

    final class ImageResizeWorker extends SwingWorker<ImageIcon, Void> {

        public ImageResizeWorker() {
            //TODO image im EDT auslesen und final speichern!
            if (image != null) {
                lblPicture.setText("Wird neu skaliert...");
                lstFotos.setEnabled(false);
            }
        }

        @Override
        protected ImageIcon doInBackground() throws Exception {
            if (image != null) {
                ImageIcon result = new ImageIcon(ImageUtil.adjustScale(image, rpVorschau, 10, 50));
                return result;
            } else {
                return null;
            }
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                try {
                    ImageIcon result = get();
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
                }
            }
        }
    }

    final class ImageUploadWorker extends SwingWorker<Collection<CidsBean>, Void> {

        private final Collection<File> fotos;

        public ImageUploadWorker(Collection<File> fotos) {
            this.fotos = fotos;
            lblPicture.setText("");
            lblPicture.setToolTipText(null);
            showWait(true);
        }

        @Override
        protected Collection<CidsBean> doInBackground() throws Exception {
            Collection<CidsBean> newBeans = new ArrayList<CidsBean>();
            for (File imageFile : fotos) {
                String webFileName = generateWebDAVFileName(imageFile);
                uploadFileToWebDAV(webFileName, imageFile);
                CidsBean newFotoBean = CidsBeanSupport.createNewCidsBeanFromTableName("FOTO");
                newFotoBean.setProperty("name", imageFile.getName());
                newFotoBean.setProperty("file", webFileName);
                newFotoBean.setProperty("angle", 0);
                newBeans.add(newFotoBean);
            }
            return newBeans;
        }

        @Override
        protected void done() {
            try {
                Collection<CidsBean> newBeans = get();
                List<CidsBean> oldBeans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "fotos");
                oldBeans.addAll(newBeans);
            } catch (InterruptedException ex) {
                log.warn(ex, ex);
            } catch (ExecutionException ex) {
                log.error(ex, ex);
            } finally {
                showWait(false);
            }
        }
    }

    final class LoadSelectedImageWorker extends SwingWorker<BufferedImage, Void> {

        private final String file;

        public LoadSelectedImageWorker(String toLoad) {
            this.file = toLoad;
            lblPicture.setText("");
            lblPicture.setToolTipText(null);
            showWait(true);
        }

        @Override
        protected BufferedImage doInBackground() throws Exception {
            if (file != null && file.length() > 0) {
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
                Throwable cause = ex.getCause();
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
}
