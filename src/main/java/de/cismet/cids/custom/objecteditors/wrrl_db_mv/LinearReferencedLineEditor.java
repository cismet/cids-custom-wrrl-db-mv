/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.FeatureRegistryListener;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LineEditorDropBehavior;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.PointBeanMergeListener;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.interaction.CrsChangeListener;
import de.cismet.cismap.commons.interaction.events.CrsChangedEvent;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class LinearReferencedLineEditor extends JPanel implements DisposableCidsBeanStore,
    LinearReferencingConstants,
    CidsBeanDropListener,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color[] COLORS = new Color[] {
            new Color(41, 86, 178),
            new Color(101, 156, 239),
            new Color(125, 189, 0),
            new Color(220, 246, 0),
            new Color(255, 91, 0)
        };

    private static final MetaClass MC_STATION = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            CN_STATION);
    private static final MetaClass MC_STATIONLINIE = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            CN_STATIONLINE);

    private static Icon ICON_MERGED_WITH_FROM_POINT = new javax.swing.ImageIcon(LinearReferencedLineEditor.class
                    .getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-left.png"));
    private static Icon ICON_MERGED_WITH_TO_POINT = new javax.swing.ImageIcon(LinearReferencedLineEditor.class
                    .getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-right.png"));

    static DataFlavor CIDSBEAN_DATAFLAVOR = DataFlavor.stringFlavor;

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private enum Card {

        //~ Enum constants -----------------------------------------------------

        edit, add, error
    }

    //~ Instance fields --------------------------------------------------------

    private LinearReferencedLineFeature feature;
    private LinearReferencedPointFeature fromPointFeature;
    private LinearReferencedPointFeature toPointFeature;
    private boolean isFromSpinnerChangeLocked = false;
    private boolean isFromFeatureChangeLocked = false;
    private boolean isFromBeanChangeLocked = false;
    private boolean isToSpinnerChangeLocked = false;
    private boolean isToFeatureChangeLocked = false;
    private boolean isToBeanChangeLocked = false;
    private PropertyChangeListener fromPointBeanChangeListener;
    private PropertyChangeListener toPointBeanChangeListener;
    private PointBeanMergeListener fromPointBeanMergeListener;
    private PointBeanMergeListener toPointBeanMergeListener;
    private LinearReferencedPointFeatureListener fromFeatureListener;
    private LinearReferencedPointFeatureListener toFeatureListener;
    private FeatureRegistryListener fromPointToFeatureRegistryListener;
    private FeatureRegistryListener toPointToFeatureRegistryListener;
    private LineEditorDropBehavior dropBehavior;
    private CrsChangeListener crsChangeListener;
    private Feature fromBadGeomFeature;
    private Feature toBadGeomFeature;
    private String lineField;
    private CidsBean cidsBean;
    private XBoundingBox boundingbox;
    private boolean isAutoZoomActivated = true;
    private boolean inited = false;
    private boolean changedSinceDrop = false;
    private boolean isEditable;
    private boolean isDrawingFeatureEnabled;
    private LinearReferencedLineEditor mergeParentLineEditor;
    private double backupFromPointValue = 0d;
    private double backupToPointValue = 0d;
    private String otherLinesFromQueryPart;
    private String otherLinesWhereQueryPart;
    private final ArrayList<CidsBean> otherLineBeans = new ArrayList<CidsBean>();
    private boolean isOtherLinesEnabled = true;

    private Collection<LinearReferencedLineEditorListener> listeners =
        new ArrayList<LinearReferencedLineEditorListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnFromBadGeom;
    private javax.swing.JButton btnFromBadGeomCorrect;
    private javax.swing.JButton btnFromPointSplit;
    private javax.swing.JToggleButton btnRoute;
    private javax.swing.JToggleButton btnToBadGeom;
    private javax.swing.JButton btnToBadGeomCorrect;
    private javax.swing.JButton btnToPointSplit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFromIcon;
    private javax.swing.JLabel lblFromValue;
    private javax.swing.JLabel lblFrontPointSplit;
    private javax.swing.JLabel lblRoute;
    private javax.swing.JLabel lblToIcon;
    private javax.swing.JLabel lblToPointSplit;
    private javax.swing.JLabel lblToValue;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JPanel panFromBadGeomSpacer;
    private javax.swing.JPanel panLine;
    private javax.swing.JPanel panLinePoints;
    private javax.swing.JPanel panOtherLines;
    private javax.swing.JPanel panSpacer;
    private javax.swing.JPanel panToBadGeomSpacer;
    private javax.swing.JSpinner spnFrom;
    private javax.swing.JSpinner spnTo;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LinearReferencedLineEditor object.
     */
    public LinearReferencedLineEditor() {
        this(true);
    }

    /**
     * Creates a new LinearReferencedLineEditor object.
     *
     * @param  isEditable  DOCUMENT ME!
     */
    protected LinearReferencedLineEditor(final boolean isEditable) {
        this(isEditable, isEditable);
    }

    /**
     * Creates new form LinearReferencedLineEditor.
     *
     * @param  isEditable                DOCUMENT ME!
     * @param  isDrawingFeaturesEnabled  DOCUMENT ME!
     */
    protected LinearReferencedLineEditor(final boolean isEditable, final boolean isDrawingFeaturesEnabled) {
        initComponents();

        setEditable(isEditable);
        setDrawingFeaturesEnabled(isDrawingFeaturesEnabled);

        try {
            if (isEditable) {
                new CidsBeanDropTarget(this);
            }
            new DropTarget(lblFromIcon, new PointBeanDropTargetListener(FROM));
            new DropTarget(lblToIcon, new PointBeanDropTargetListener(TO));
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating DropTargets", ex);
            }
        }
        initSpinnerListener(FROM);
        initSpinnerListener(TO);
        initFeatureRegistryListener(FROM);
        initFeatureRegistryListener(TO);
        initPointFeatureListener(FROM);
        initPointFeatureListener(TO);
        initPointBeanChangeListener(FROM);
        initPointBeanChangeListener(TO);
        initPointBeanMergeListener(FROM);
        initPointBeanMergeListener(TO);
        initPointTransferHandler(FROM);
        initPointTransferHandler(TO);
        initPointIconLabelMouseListener(FROM);
        initPointIconLabelMouseListener(TO);

        if (isDrawingFeaturesEnabled()) {
            initCrsChangeListener();

            CismapBroker.getInstance().addCrsChangeListener(getCrsChangeListener());
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  isOtherLinesEnabled  DOCUMENT ME!
     */
    public void setOtherLinesEnabled(final boolean isOtherLinesEnabled) {
        this.isOtherLinesEnabled = isOtherLinesEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isOtherLinesEnabled() {
        return isOtherLinesEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    protected final void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
        spnFrom.setVisible(isEditable);
        spnTo.setVisible(isEditable);
        lblFromValue.setVisible(!isEditable);
        lblToValue.setVisible(!isEditable);
        btnRoute.setVisible(isEditable);
        lblRoute.setVisible(!isEditable);
        if (isInited()) {
            refresh();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public final boolean isEditable() {
        return isEditable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lineBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Color getNextColor(final CidsBean lineBean) {
        return COLORS[Math.abs(lineBean.hashCode() % COLORS.length)];
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isDrawingFeatureEnabled  DOCUMENT ME!
     */
    protected final void setDrawingFeaturesEnabled(final boolean isDrawingFeatureEnabled) {
        this.isDrawingFeatureEnabled = isDrawingFeatureEnabled;
        if (isInited()) {
            refresh();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public final boolean isDrawingFeaturesEnabled() {
        return isDrawingFeatureEnabled;
    }

    /**
     * DOCUMENT ME!
     */
    private void fireLineAdded() {
        for (final LinearReferencedLineEditorListener listener : listeners) {
            listener.linearReferencedLineCreated();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addListener(final LinearReferencedLineEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final LinearReferencedLineEditorListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineFeature getLineFeature() {
        return feature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  feature  DOCUMENT ME!
     */
    private void setFeature(final LinearReferencedLineFeature feature) {
        this.feature = feature;

        if (feature != null) {
            final LinearReferencedPointFeature fromPointFeature = feature.getPointFeature(FROM);
            final LinearReferencedPointFeature toPointFeature = feature.getPointFeature(TO);

            // feature editable status setzen, außer es ist schon editable
            if ((fromPointFeature != null) && !fromPointFeature.isEditable()) {
                fromPointFeature.setEditable(isEditable());
            }
            if ((toPointFeature != null) && !toPointFeature.isEditable()) {
                toPointFeature.setEditable(isEditable());
            }
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * >> BEAN.
     */
    private void cleanupLine() {
        final LinearReferencedLineFeature oldFeature = getLineFeature();
        if (oldFeature != null) {
            final CidsBean oldBean = FEATURE_REGISTRY.getCidsBean(oldFeature);
            FEATURE_REGISTRY.removeLinearReferencedLineFeature(oldBean);
            setFeature(null);
        }

        cleanupPoint(FROM);
        cleanupPoint(TO);
        cleanOtherLinesPanel();

        showCard(Card.add);

        setInited(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("station_linie", LinearReferencedLineEditor.class, WRRLUtil.DOMAIN_NAME).run();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void cleanupPoint(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);

        if (pointBean != null) {
            pointBean.removePropertyChangeListener(getPointBeanChangeListener(isFrom));
            MERGE_REGISTRY.removeListener(pointBean, getPointBeanMergeListener(isFrom));
        }

        final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
        if (pointFeature != null) {
            pointFeature.removeListener(getPointFeatureListener(isFrom));
            if (pointBean != null) {
                FEATURE_REGISTRY.removeStationFeature(pointBean);
            }
            FEATURE_REGISTRY.removeListener(pointBean, getFeatureRegistryListener(isFrom));

            // bean ist null, feature also auch
            setPointFeature(null, isFrom);
        }

        final Feature badGeomFeature = getBadGeomFeature(isFrom);
        if (badGeomFeature != null) {
            MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
            setBadGeomFeature(null, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  color  DOCUMENT ME!
     */
    protected void setLineColor(final Color color) {
        panLine.setBackground(color);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected Color getLineColor() {
        return panLine.getBackground();
    }

    /**
     * DOCUMENT ME!
     */
    private void initLine() {
        // wird das aktuelle crs unterstützt ?
        if (!isCrsSupported(CismapBroker.getInstance().getSrs())) {
            showCrsNotSupported();
            // noch nicht initialisiert ?
        } else if (!isInited()) {
            final CidsBean lineBean = getLineBean();
            if (lineBean != null) {
                initPoint(FROM);
                initPoint(TO);

                if (isDrawingFeaturesEnabled()) {
                    // feature erzeugen
                    final LinearReferencedLineFeature lineFeature = FEATURE_REGISTRY.addLinearReferencedLineFeature(
                            lineBean,
                            getPointFeature(FROM),
                            getPointFeature(TO));

                    lineFeature.setLinePaint(getLineColor());

                    setFeature(lineFeature);
                }
                final String routeText = "Route: "
                            + Long.toString(LinearReferencingHelper.getRouteGwkFromStationBean(getPointBean(FROM)));

                fireLineAdded();

                pointBeanValueChanged(FROM);
                pointBeanValueChanged(TO);

                lblRoute.setText(routeText);
                if (isEditable()) {
                    initSpinner(FROM);
                    initSpinner(TO);
                    btnRoute.setText(routeText);
                }

                showCard(Card.edit);

                updateOtherLinesPanelVisibility();

                setInited(true);
            } else {
                if (isEditable()) {
                    showCard(Card.add);
                } else {
                    setErrorMsg("keine Stationierung zugewiesen.");
                    showCard(Card.error);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initSpinner(final boolean isFrom) {
        ((SpinnerNumberModel)getPointSpinner(isFrom).getModel()).setMaximum(Math.ceil(
                getRouteGeometry().getLength()));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void backupPointValue(final boolean isFrom) {
        setBackupPointValue(getPointValue(isFrom), isFrom);
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        // aufräumen falls vorher cidsbean schon gesetzt war
        cleanupLine();

        this.cidsBean = cidsBean;

        // cache für beans setzen
        final CidsBean cachedLineBean = CIDSBEAN_CACHE.getCachedBeanFor(getLineBean());
        final CidsBean cachedFromPointBean = CIDSBEAN_CACHE.getCachedBeanFor(getPointBean(FROM));
        final CidsBean cachedToPointBean = CIDSBEAN_CACHE.getCachedBeanFor(getPointBean(TO));

        // beans mit denen aus dem Cache erstzen
        // (nur wenn nicht editable, sonst werden die änderungen an der bean unter umständen nicht gespeichert)
        if (!isEditable()) {
            setLineBean(cachedLineBean);
            setPointBean(cachedFromPointBean, FROM);
            setPointBean(cachedToPointBean, TO);
        }

        // position der punkte sichern (für reset)
        backupPointValue(FROM);
        backupPointValue(TO);

        // andere linien auf selber route ermitteln
        if (isEditable() && isOtherLinesEnabled()) {
            updateOtherLinesOnBaseline();
        }
        btnRoute.setVisible(panOtherLines.getComponents().length != 0);
        lblRoute.setVisible(panOtherLines.getComponents().length == 0);

        if (getLineBean() != null) {
            // Farbe setzen (wird neu ermittelt, falls nicht schon eine feature existiert)
            setLineColor(getNextColor(getLineBean()));
        }

        // neu initialisieren
        initLine();
    }

    /**
     * DOCUMENT ME!
     */
    public void refresh() {
        cleanupLine();
        initLine();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasChangedSinceDrop() {
        return changedSinceDrop;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  changedSinceDrop  DOCUMENT ME!
     */
    private void setChangedSinceDrop(final boolean changedSinceDrop) {
        this.changedSinceDrop = changedSinceDrop;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isInited() {
        return inited;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inited  DOCUMENT ME!
     */
    private void setInited(final boolean inited) {
        this.inited = inited;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JButton getPointSplitButton(final boolean isFrom) {
        return (isFrom) ? btnFromPointSplit : btnToPointSplit;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JLabel getPointSplitLabel(final boolean isFrom) {
        return (isFrom) ? lblFrontPointSplit : lblToPointSplit;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getPointField(final boolean isFrom) {
        return (isFrom) ? PROP_STATIONLINIE_FROM : PROP_STATIONLINIE_TO;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lineField  DOCUMENT ME!
     */
    public void setLineField(final String lineField) {
        this.lineField = lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getLineField() {
        return lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasBadGeomFeature() {
        return (getBadGeomFeature(FROM) != null) && (getBadGeomFeature(TO) != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Feature getBadGeomFeature(final boolean isFrom) {
        return (isFrom) ? fromBadGeomFeature : toBadGeomFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  badGeomFeature  DOCUMENT ME!
     * @param  isFrom          DOCUMENT ME!
     */
    private void setBadGeomFeature(final Feature badGeomFeature, final boolean isFrom) {
        if (isFrom) {
            fromBadGeomFeature = badGeomFeature;
        } else {
            toBadGeomFeature = badGeomFeature;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  otherLinesFromQueryPart   DOCUMENT ME!
     * @param  otherLinesWhereQueryPart  DOCUMENT ME!
     */
    public void setOtherLinesQueryAddition(final String otherLinesFromQueryPart,
            final String otherLinesWhereQueryPart) {
        this.otherLinesFromQueryPart = otherLinesFromQueryPart;
        this.otherLinesWhereQueryPart = otherLinesWhereQueryPart;
    }

    /**
     * DOCUMENT ME!
     */
    private void updateOtherLinesOnBaseline() {
        if (getLineBean() != null) {
            final int route_id = (Integer)getLineBean().getProperty("von.route.id");
            final int id = (Integer)getLineBean().getProperty("id");

            final String queryOtherLines = "SELECT "
                        + "   " + MC_STATIONLINIE.getID() + ", "
                        + "   station_linie." + MC_STATIONLINIE.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + MC_STATIONLINIE.getTableName() + " AS station_linie, "
                        + "   " + MC_STATION.getTableName() + " AS station "
                        + ((otherLinesFromQueryPart != null) ? (", " + otherLinesFromQueryPart + " ") : "")
                        + "WHERE "
                        + "   station.id = station_linie.von "
                        + "   AND station.route = " + route_id + " "
                        + "   AND station_linie.id != " + id + " "
                        + ((otherLinesWhereQueryPart != null)
                            ? (" AND " + otherLinesWhereQueryPart + " station_linie.id") : "")
                        + ";";
            if (LOG.isDebugEnabled()) {
                LOG.debug(queryOtherLines);
            }
            MetaObject[] mosOtherLines = null;
            try {
                mosOtherLines = SessionManager.getProxy().getMetaObjectByQuery(queryOtherLines, 0);
            } catch (Exception ex) {
                LOG.error("error while loading other lines on baseline", ex);
            }

            cleanOtherLinesPanel();
            otherLineBeans.clear();
            for (final MetaObject moOtherLine : mosOtherLines) {
                final CidsBean otherLineBean = moOtherLine.getBean();
                final LinearReferencedLineRenderer renderer = new LinearReferencedLineRenderer();
                renderer.setMergeParentLineEditor(this);
                renderer.setCidsBean(otherLineBean);
                renderer.updateSplitMergeControls(FROM);
                renderer.updateSplitMergeControls(TO);
                panOtherLines.add(renderer);

                otherLineBeans.add(renderer.getLineBean());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JButton getBadGeomCorrectButton(final boolean isFrom) {
        return (isFrom) ? btnFromBadGeomCorrect : btnToBadGeomCorrect;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JToggleButton getBadGeomButton(final boolean isFrom) {
        return (isFrom) ? btnFromBadGeom : btnToBadGeom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JLabel getPointIconLabel(final boolean isFrom) {
        return (isFrom) ? lblFromIcon : lblToIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private FeatureRegistryListener getFeatureRegistryListener(final boolean isFrom) {
        return (isFrom) ? fromPointToFeatureRegistryListener : toPointToFeatureRegistryListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setFeatureRegistryListener(final FeatureRegistryListener listener, final boolean isFrom) {
        if (isFrom) {
            fromPointToFeatureRegistryListener = listener;
        } else {
            toPointToFeatureRegistryListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CrsChangeListener getCrsChangeListener() {
        return crsChangeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  crsChangeListener  DOCUMENT ME!
     */
    private void setCrsChangeListener(final CrsChangeListener crsChangeListener) {
        this.crsChangeListener = crsChangeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private PropertyChangeListener getPointBeanChangeListener(final boolean isFrom) {
        return (isFrom) ? fromPointBeanChangeListener : toPointBeanChangeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setPointBeanChangeListener(final PropertyChangeListener listener, final boolean isFrom) {
        if (isFrom) {
            fromPointBeanChangeListener = listener;
        } else {
            toPointBeanChangeListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private PointBeanMergeListener getPointBeanMergeListener(final boolean isFrom) {
        return (isFrom) ? fromPointBeanMergeListener : toPointBeanMergeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setPointBeanMergeListener(final PointBeanMergeListener listener, final boolean isFrom) {
        if (isFrom) {
            fromPointBeanMergeListener = listener;
        } else {
            toPointBeanMergeListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected LinearReferencedPointFeature getPointFeature(final boolean isFrom) {
        return (isFrom) ? fromPointFeature : toPointFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pointFeature  DOCUMENT ME!
     * @param  isFrom        DOCUMENT ME!
     */
    private void setPointFeature(final LinearReferencedPointFeature pointFeature,
            final boolean isFrom) {
        // feature editable status setzen, außer es ist schon editable
        if ((pointFeature != null) && !pointFeature.isEditable()) {
            pointFeature.setEditable(isEditable());
        }

        if (isFrom) {
            fromPointFeature = pointFeature;
        } else {
            toPointFeature = pointFeature;
        }

        final LinearReferencedLineFeature lineFeature = getLineFeature();
        if (lineFeature != null) {
            getLineFeature().setPointFeature(pointFeature, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeatureListener getPointFeatureListener(final boolean isFrom) {
        return (isFrom) ? fromFeatureListener : toFeatureListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  featureListener  DOCUMENT ME!
     * @param  isFrom           DOCUMENT ME!
     */
    private void setPointFeatureListener(final LinearReferencedPointFeatureListener featureListener,
            final boolean isFrom) {
        if (isFrom) {
            fromFeatureListener = featureListener;
        } else {
            toFeatureListener = featureListener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JSpinner getPointSpinner(final boolean isFrom) {
        return (isFrom) ? spnFrom : spnTo;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LineEditorDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dropBehavior  DOCUMENT ME!
     */
    public void setDropBehavior(final LineEditorDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setBackupPointValue(final double value, final boolean isFrom) {
        if (isFrom) {
            backupFromPointValue = value;
        } else {
            backupToPointValue = value;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double getBackupPointValue(final boolean isFrom) {
        return (isFrom) ? backupFromPointValue : backupToPointValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    protected void resetPointValue(final boolean isFrom) {
        setPointValueToBean(getBackupPointValue(isFrom), isFrom);
        pointBeanValueChanged(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPoint(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);
        if (pointBean != null) {
            pointBean.addPropertyChangeListener(getPointBeanChangeListener(isFrom));
            MERGE_REGISTRY.addListener(pointBean, getPointBeanMergeListener(isFrom));

            if (isEditable()) {
                final double distance = LinearReferencingHelper.distanceOfStationGeomToRouteGeomFromStationBean(
                        pointBean);

                if (distance > 1) {
                    setBadGeomFeature(StationEditor.createBadGeomFeature(
                            LinearReferencingHelper.getPointGeometryFromStationBean(pointBean)),
                        isFrom);
                } else {
                    setBadGeomFeature(null, isFrom);
                }
            }

            if (isDrawingFeaturesEnabled()) {
                FEATURE_REGISTRY.addListener(pointBean, getFeatureRegistryListener(isFrom));

                // feature erzeugen
                final LinearReferencedPointFeature pointFeature = FEATURE_REGISTRY.addStationFeature(
                        pointBean);

                // feature listener
                pointFeature.addListener(getPointFeatureListener(isFrom));

                // feature setzen
                setPointFeature(pointFeature, isFrom);
            }
        }
        updateBadGeomButton(isFrom);
        updateSplitMergeControls(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pointBean  DOCUMENT ME!
     * @param  isFrom     DOCUMENT ME!
     */
    private void setPoint(final CidsBean pointBean, final boolean isFrom) {
        cleanupPoint(isFrom);
        setPointBean(pointBean, isFrom);
        initPoint(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void switchBadGeomVisibility(final boolean isFrom) {
        if (isEditable()) {
            final Feature badGeomFeature = getBadGeomFeature(isFrom);
            final Feature pointFeature = getPointFeature(isFrom);

            final boolean selected = getBadGeomButton(isFrom).isSelected();

            if (selected) {
                boundingbox = (XBoundingBox)MAPPING_COMPONENT.getCurrentBoundingBox();

                MAPPING_COMPONENT.getFeatureCollection().addFeature(badGeomFeature);
                MAPPING_COMPONENT.getFeatureCollection().select(pointFeature);

                zoomToBadFeature(isFrom);
            } else {
                MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
                MAPPING_COMPONENT.gotoBoundingBoxWithoutHistory(boundingbox);
            }

            getBadGeomCorrectButton(isFrom).setVisible(selected);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void zoomToBadFeature(final boolean isFrom) {
        final Feature badGeomFeature = getBadGeomFeature(isFrom);
        final Collection<Feature> aFeatureCollection = new ArrayList<Feature>();
        aFeatureCollection.add(badGeomFeature);
        aFeatureCollection.add(getLineFeature());
        // TODO boundingbox
        MAPPING_COMPONENT.zoomToAFeatureCollection(aFeatureCollection, false, MAPPING_COMPONENT.isFixedMapScale());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Feature> getZoomFeatures() {
        final Collection<Feature> zoomFeatures = new ArrayList<Feature>();
        addZoomFeaturesToCollection(zoomFeatures);
        return zoomFeatures;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  collection  DOCUMENT ME!
     */
    public void addZoomFeaturesToCollection(final Collection<Feature> collection) {
        final Feature fromPointFeature = getPointFeature(FROM);
        final Feature toPointFeature = getPointFeature(TO);
        if ((fromPointFeature != null) && (toPointFeature != null)) {
            final Feature boundedFromFeature = new PureNewFeature(fromPointFeature.getGeometry().buffer(500));
            final Feature boundedToFeature = new PureNewFeature(toPointFeature.getGeometry().buffer(500));
            collection.add(boundedFromFeature);
            collection.add(boundedToFeature);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void correctBadGeom(final boolean isFrom) {
        if (isEditable()) {
            final LinearReferencedPointFeature feature = getPointFeature(isFrom);
            final Feature badFeature = getBadGeomFeature(isFrom);
            feature.moveTo(badFeature.getGeometry().getCoordinate());
            zoomToBadFeature(isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  card  DOCUMENT ME!
     */
    private void showCard(final Card card) {
        switch (card) {
            case edit: {
                ((CardLayout)getLayout()).show(this, "edit");
                break;
            }
            case add: {
                ((CardLayout)getLayout()).show(this, "add");
                break;
            }
            case error: {
                ((CardLayout)getLayout()).show(this, "error");
                break;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     * @param  cidsBean   DOCUMENT ME!
     * @param  lineField  DOCUMENT ME!
     */
    public static void fillFromRoute(final CidsBean routeBean, final CidsBean cidsBean, final String lineField) {
        try {
            final CidsBean linieBean = LinearReferencingHelper.createLineBeanFromRouteBean(routeBean);
            cidsBean.setProperty(lineField, linieBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
    }

    @Override
    public void dispose() {
        cleanupLine();

        CismapBroker.getInstance().removeCrsChangeListener(getCrsChangeListener());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initFeatureRegistryListener(final boolean isFrom) {
        final FeatureRegistryListener featureRegistryListener = new FeatureRegistryListener() {

                @Override
                public void FeatureCountChanged() {
                    updateSplitMergeControls(isFrom);
                }
            };

        setFeatureRegistryListener(featureRegistryListener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initSpinnerListener(final boolean isFrom) {
        final JSpinner spinner = getPointSpinner(isFrom);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField()
                .getDocument()
                .addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(final DocumentEvent de) {
                            spinnerValueChanged(isFrom);
                        }

                        @Override
                        public void removeUpdate(final DocumentEvent de) {
                            spinnerValueChanged(isFrom);
                        }

                        @Override
                        public void changedUpdate(final DocumentEvent de) {
                            spinnerValueChanged(isFrom);
                        }
                    });
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(final FocusEvent fe) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getPointFeature(isFrom));
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPointFeatureListener(final boolean isFrom) {
        final LinearReferencedPointFeatureListener featureListener = new LinearReferencedPointFeatureListener() {

                @Override
                public void featureMoved(final LinearReferencedPointFeature pointFeature) {
                    featureValueChanged(isFrom);
                }

                @Override
                public void featureMerged(final LinearReferencedPointFeature mergePoint,
                        final LinearReferencedPointFeature withPoint) {
//                    final CidsBean withBean = FEATURE_REGISTRY.getCidsBean(withPoint);
//
//                    final LinearReferencedPointFeature fromFeature = getPointFeature(FROM);
//                    final LinearReferencedPointFeature toFeature = getPointFeature(TO);
//                    if ((fromFeature.equals(mergePoint) && !toFeature.equals(withPoint))
//                                || (toFeature.equals(mergePoint) && !fromFeature.equals(withPoint))) {
//                        final boolean isFrom = fromFeature.equals(mergePoint);
//
//                        MERGE_REGISTRY.firePointBeanMerged(getPointBean(isFrom), withBean);
//                    }
                }
            };

        setPointFeatureListener(featureListener, isFrom);
    }

    /**
     * DOCUMENT ME!
     */
    private void initCrsChangeListener() {
        setCrsChangeListener(new CrsChangeListener() {

                @Override
                public void crsChanged(final CrsChangedEvent event) {
                    if (!isCrsSupported(event.getCurrentCrs())) {
                        showCrsNotSupported();
                    } else {
                        initLine();
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void showCrsNotSupported() {
        cleanupLine();
        setErrorMsg("Das aktuelle CRS wird vom Stationierungseditor nicht unterstützt.");
        showCard(Card.error);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  msg  DOCUMENT ME!
     */
    private void setErrorMsg(final String msg) {
        lblError.setText(msg);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   crs  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isCrsSupported(final Crs crs) {
        return CrsTransformer.extractSridFromCrs(crs.getCode()) == 35833;
    }

    /**
     * cidsbean ändern.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void featureValueChanged(final boolean isFrom) {
        if (isEditable()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("feature changed", new CurrentStackTrace());
            }
            try {
                lockFeatureChange(true, isFrom);

                if (!isBeanChangeLocked(isFrom)) {
                    final LinearReferencedPointFeature linearRefFeature = getPointFeature(isFrom);

                    final double value = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
                    setPointValueToBean(value, isFrom);
                }
            } finally {
                lockFeatureChange(false, isFrom);
            }
        }
    }

    /**
     * cidsbean ändern.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void spinnerValueChanged(final boolean isFrom) {
        if (isEditable()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("spinner changed", new CurrentStackTrace());
            }
            try {
                lockSpinnerChange(true, isFrom);

                final AbstractFormatter formatter = ((JSpinner.DefaultEditor)getPointSpinner(isFrom).getEditor())
                            .getTextField().getFormatter();
                final String text = ((JSpinner.DefaultEditor)getPointSpinner(isFrom).getEditor()).getTextField()
                            .getText();
                if (!text.isEmpty()) {
                    try {
                        final double value = (Double)formatter.stringToValue(text);
                        setPointValueToBean(value, isFrom);
                    } catch (ParseException ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error parsing spinner", ex);
                        }
                    }
                }
            } finally {
                lockSpinnerChange(false, isFrom);
            }
        }
    }
    /**
     * spinner ändern feature ändern realgeoms anpassen.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void pointBeanValueChanged(final boolean isFrom) {
        try {
            lockBeanChange(true, isFrom);

            setChangedSinceDrop(true);

            final CidsBean pointBean = getPointBean(isFrom);

            if (pointBean != null) {
                final double value = (Double)pointBean.getProperty(PROP_STATION_VALUE);

                if (isEditable()) {
                    setPointValueToSpinner(value, isFrom);
                } else {
                    setPointValueToLabel(value, isFrom);
                }

                if (isDrawingFeaturesEnabled()) {
                    setPointValueToFeature(value, isFrom);
                }

                // realgeoms nur nach manueller eingabe updaten
                if (isInited()) {
                    updateRealGeoms(isFrom);
                }
            }
        } finally {
            lockBeanChange(false, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void updateRealGeoms() {
        updateRealGeoms(FROM);
        updateRealGeoms(TO);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JLabel getValueLabel(final boolean isFrom) {
        return (isFrom) ? lblFromValue : lblToValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToLabel(final double value, final boolean isFrom) {
        final JLabel valueLabel = getValueLabel(isFrom);
        valueLabel.setText(Long.toString(Math.round(value)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getPointValue(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);
        if (pointBean != null) {
            return LinearReferencingHelper.getLinearValueFromStationBean(pointBean);
        } else {
            return 0d;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    protected void updateSplitMergeControls(final boolean isFrom) {
        if (isEditable()) {
            final boolean isPointMerged = isPointMerged(isFrom);
            if (btnRoute.isSelected()) {
                getPointSplitButton(isFrom).setVisible(isPointMerged);
                getPointSplitLabel(isFrom).setVisible(false);
            } else {
                getPointSplitButton(isFrom).setVisible(false);
                getPointSplitLabel(isFrom).setVisible(isPointMerged);
                getPointSplitLabel(isFrom).setIcon(isFrom ? ICON_MERGED_WITH_FROM_POINT : ICON_MERGED_WITH_TO_POINT);
            }
        } else {
            getPointSplitButton(isFrom).setVisible(false);
            final LinearReferencedLineEditor editor = getMergeParentLineEditor();
            final JLabel lblPointSplit = getPointSplitLabel(isFrom);
            if (editor == null) {
                lblPointSplit.setVisible(false);
            } else if (getPointBean(isFrom).equals(editor.getPointBean(FROM))) {
                lblPointSplit.setIcon(ICON_MERGED_WITH_FROM_POINT);
                lblPointSplit.setVisible(true);
            } else if (getPointBean(isFrom).equals(editor.getPointBean(TO))) {
                lblPointSplit.setIcon(ICON_MERGED_WITH_TO_POINT);
                lblPointSplit.setVisible(true);
            } else {
                lblPointSplit.setVisible(false);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isPointMerged(final boolean isFrom) {
        if (getLineBean() != null) {
            final CidsBean pointBean = getPointBean(isFrom);
            for (final Component component : panOtherLines.getComponents()) {
                final LinearReferencedLineRenderer renderer = (LinearReferencedLineRenderer)component;
                final CidsBean fromPointBean = renderer.getPointBean(FROM);
                final CidsBean toPointBean = renderer.getPointBean(TO);
                if ((fromPointBean != null) && fromPointBean.equals(pointBean)) {
                    return true;
                } else if ((toPointBean != null) && toPointBean.equals(pointBean)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateBadGeomButton(final boolean isFrom) {
        final boolean visible = isEditable() && (getBadGeomFeature(isFrom) != null);
        getBadGeomButton(isFrom).setVisible(visible);
        getBadGeomCorrectButton(isFrom).setVisible(visible && getBadGeomButton(isFrom).isSelected());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToSpinner(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change spinner");
        }

        if (!isSpinnerChangeLocked(isFrom)) {
            final JSpinner pointSpinner = getPointSpinner(isFrom);
            pointSpinner.setValue(Math.round(value));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToFeature(final double value, final boolean isFrom) {
        if (!isFeatureChangeLocked(isFrom)) {
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            if (pointFeature != null) {
                pointFeature.setInfoFormat(new DecimalFormat("###"));
                pointFeature.moveToPosition(value);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("there are no feature to move");
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateRealGeoms(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("update real geom");
        }

        try {
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            final CidsBean pointBean = getPointBean(isFrom);

            // realgeom der Station anpassen
            if (pointFeature != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change station geom");
                }

                final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(LinearReferencingHelper
                                .getLinearValueFromStationBean(pointBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(pointBean));
                pointGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
                LinearReferencingHelper.setPointGeometryToStationBean(pointGeom, pointBean);
            }

            // realgeom der Linie anpassen
            if (LOG.isDebugEnabled()) {
                LOG.debug("change line geom");
            }
            final Geometry lineGeom = LinearReferencedLineFeature.createSubline(getPointValue(FROM),
                    getPointValue(TO),
                    LinearReferencingHelper.getRouteGeometryFromStationBean(getPointBean(isFrom)));
            lineGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
            setLineGeometry(lineGeom);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while setting real geoms", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToBean(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change bean value to " + value);
        }
        if (!isBeanChangeLocked(isFrom)) {
            final CidsBean pointBean = getPointBean(isFrom);
            final double oldValue = LinearReferencingHelper.getLinearValueFromStationBean(pointBean);

            if (oldValue != Math.round(value)) {
                try {
                    LinearReferencingHelper.setLinearValueToStationBean((double)Math.round(value), pointBean);
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("error changing bean", ex);
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("no changes needed, old value was " + oldValue);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isFeatureChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromFeatureChangeLocked : isToFeatureChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isSpinnerChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromSpinnerChangeLocked : isToSpinnerChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromBeanChangeLocked : isToBeanChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockFeatureChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromFeatureChangeLocked = lock;
        } else {
            isToFeatureChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockSpinnerChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromSpinnerChangeLocked = lock;
        } else {
            isToSpinnerChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockBeanChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromBeanChangeLocked = lock;
        } else {
            isToBeanChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPointBeanChangeListener(final boolean isFrom) {
        final PropertyChangeListener listener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent pce) {
                    if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                        pointBeanValueChanged(isFrom);
                    }
                }
            };

        setPointBeanChangeListener(listener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPointBeanMergeListener(final boolean isFrom) {
        final PointBeanMergeListener listener = new PointBeanMergeListener() {

                @Override
                public void pointBeanMerged(final CidsBean pointBean) {
                    updateSplitMergeControls(isFrom);
                    refresh();
                }

                @Override
                public void pointBeanSplitted() {
                    updateSplitMergeControls(isFrom);
                    resetPointValue(isFrom);
                    refresh();
                }
            };

        setPointBeanMergeListener(listener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPointTransferHandler(final boolean isFrom) {
        getPointIconLabel(isFrom).setTransferHandler(new PointBeanTransferHandler(isFrom));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initPointIconLabelMouseListener(final boolean isFrom) {
        getPointIconLabel(isFrom).addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent evt) {
                    final boolean isNew = getPointBean(isFrom).getMetaObject().getStatus() == MetaObject.NEW;
                    if (isEditable() && !isPointMerged(isFrom) /* && !isNew*/) {
                        final JComponent comp = (JComponent)evt.getSource();
                        final TransferHandler th = comp.getTransferHandler();
                        th.exportAsDrag(comp, evt, TransferHandler.COPY);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Geometry getRouteGeometry() {
        final CidsBean routeGeomBean = getRouteGeomBean();
        if (routeGeomBean == null) {
            return null;
        }
        return (Geometry)routeGeomBean.getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected CidsBean getPointBean(final boolean isFrom) {
        final CidsBean pointBean = LinearReferencingHelper.getStationBeanFromLineBean(getLineBean(), isFrom);
        return pointBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected CidsBean getLineBean() {
        if (getLineField() == null) {
            return getCidsBean();
        } else {
            final CidsBean lineBean = getLineBean(getCidsBean(), getLineField());
            return lineBean;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean   DOCUMENT ME!
     * @param   lineField  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getLineBean(final CidsBean cidsBean, final String lineField) {
        if (cidsBean == null) {
            return null;
        }
        return (CidsBean)cidsBean.getProperty(lineField);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteBean() {
        final CidsBean pointBean = getPointBean(FROM);
        if (pointBean == null) {
            return null;
        }
        return (CidsBean)pointBean.getProperty(PROP_STATION_ROUTE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteGeomBean() {
        final CidsBean routeBean = getRouteBean();
        if (routeBean == null) {
            return null;
        }
        return (CidsBean)routeBean.getProperty(PROP_ROUTE_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   line  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void setLineGeometry(final Geometry line) throws Exception {
        LinearReferencingHelper.setGeometryToLineBean(line, getLineBean());
        if (getLineBean() != null) {
            getLineBean().setArtificialChangeFlag(true);
        }
    }

    @Override
    public void setEnabled(final boolean bln) {
        super.setEnabled(bln);
        jLabel3.setVisible(bln);
        getPointSpinner(FROM).setEnabled(bln);
        getPointSpinner(TO).setEnabled(bln);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panEdit = new javax.swing.JPanel();
        panLinePoints = new javax.swing.JPanel();
        lblFromIcon = new javax.swing.JLabel();
        lblToIcon = new javax.swing.JLabel();
        spnFrom = new javax.swing.JSpinner();
        spnTo = new javax.swing.JSpinner();
        lblFromValue = new javax.swing.JLabel();
        lblToValue = new javax.swing.JLabel();
        panFromBadGeomSpacer = new javax.swing.JPanel();
        btnFromBadGeom = new javax.swing.JToggleButton();
        btnFromBadGeomCorrect = new javax.swing.JButton();
        panToBadGeomSpacer = new javax.swing.JPanel();
        btnToBadGeomCorrect = new javax.swing.JButton();
        btnToBadGeom = new javax.swing.JToggleButton();
        lblToPointSplit = new javax.swing.JLabel();
        lblFrontPointSplit = new javax.swing.JLabel();
        btnFromPointSplit = new javax.swing.JButton();
        btnToPointSplit = new javax.swing.JButton();
        lblRoute = new javax.swing.JLabel();
        btnRoute = new javax.swing.JToggleButton();
        panLine = new javax.swing.JPanel();
        panOtherLines = new javax.swing.JPanel();
        panSpacer = new javax.swing.JPanel();
        panAdd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        lblError = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        panEdit.setOpaque(false);
        panEdit.setLayout(new java.awt.GridBagLayout());

        panLinePoints.setOpaque(false);
        panLinePoints.setLayout(new java.awt.GridBagLayout());

        lblFromIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblFromIcon.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblFromIcon.text_1"));                                       // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        panLinePoints.add(lblFromIcon, gridBagConstraints);

        lblToIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblToIcon.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblToIcon.text_1"));                                         // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 15);
        panLinePoints.add(lblToIcon, gridBagConstraints);

        spnFrom.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spnFrom.setEditor(new javax.swing.JSpinner.NumberEditor(spnFrom, "###"));
        spnFrom.setMinimumSize(new java.awt.Dimension(100, 28));
        spnFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        panLinePoints.add(spnFrom, gridBagConstraints);

        spnTo.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spnTo.setEditor(new javax.swing.JSpinner.NumberEditor(spnTo, "###"));
        spnTo.setMinimumSize(new java.awt.Dimension(100, 28));
        spnTo.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        panLinePoints.add(spnTo, gridBagConstraints);

        lblFromValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblFromValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFromValue.setMaximumSize(new java.awt.Dimension(100, 28));
        lblFromValue.setMinimumSize(new java.awt.Dimension(100, 28));
        lblFromValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panLinePoints.add(lblFromValue, gridBagConstraints);

        lblToValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblToValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblToValue.setMaximumSize(new java.awt.Dimension(100, 28));
        lblToValue.setMinimumSize(new java.awt.Dimension(100, 28));
        lblToValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        panLinePoints.add(lblToValue, gridBagConstraints);

        panFromBadGeomSpacer.setMaximumSize(new java.awt.Dimension(86, 28));
        panFromBadGeomSpacer.setMinimumSize(new java.awt.Dimension(86, 28));
        panFromBadGeomSpacer.setOpaque(false);
        panFromBadGeomSpacer.setPreferredSize(new java.awt.Dimension(86, 28));
        panFromBadGeomSpacer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        btnFromBadGeom.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        btnFromBadGeom.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeom.text"));                                          // NOI18N
        btnFromBadGeom.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeom.toolTipText"));                                   // NOI18N
        btnFromBadGeom.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromBadGeomActionPerformed(evt);
                }
            });
        panFromBadGeomSpacer.add(btnFromBadGeom);

        btnFromBadGeomCorrect.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete.png"))); // NOI18N
        btnFromBadGeomCorrect.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeomCorrect.text_1"));                                 // NOI18N
        btnFromBadGeomCorrect.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeomCorrect.toolTipText"));                            // NOI18N
        btnFromBadGeomCorrect.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromBadGeomCorrectActionPerformed(evt);
                }
            });
        panFromBadGeomSpacer.add(btnFromBadGeomCorrect);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panLinePoints.add(panFromBadGeomSpacer, gridBagConstraints);

        panToBadGeomSpacer.setMaximumSize(new java.awt.Dimension(86, 28));
        panToBadGeomSpacer.setMinimumSize(new java.awt.Dimension(86, 28));
        panToBadGeomSpacer.setOpaque(false);
        panToBadGeomSpacer.setPreferredSize(new java.awt.Dimension(86, 28));
        panToBadGeomSpacer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        btnToBadGeomCorrect.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete-child.png"))); // NOI18N
        btnToBadGeomCorrect.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeomCorrect.text_1"));                                         // NOI18N
        btnToBadGeomCorrect.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeomCorrect.toolTipText"));                                    // NOI18N
        btnToBadGeomCorrect.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToBadGeomCorrectActionPerformed(evt);
                }
            });
        panToBadGeomSpacer.add(btnToBadGeomCorrect);

        btnToBadGeom.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        btnToBadGeom.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeom.text"));                                            // NOI18N
        btnToBadGeom.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeom.toolTipText"));                                     // NOI18N
        btnToBadGeom.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToBadGeomActionPerformed(evt);
                }
            });
        panToBadGeomSpacer.add(btnToBadGeom);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panLinePoints.add(panToBadGeomSpacer, gridBagConstraints);

        lblToPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblToPointSplit.text"));        // NOI18N
        lblToPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblToPointSplit.toolTipText")); // NOI18N
        lblToPointSplit.setMaximumSize(new java.awt.Dimension(16, 16));
        lblToPointSplit.setMinimumSize(new java.awt.Dimension(16, 16));
        lblToPointSplit.setPreferredSize(new java.awt.Dimension(16, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        panLinePoints.add(lblToPointSplit, gridBagConstraints);

        lblFrontPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblFrontPointSplit.text_1"));      // NOI18N
        lblFrontPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblFrontPointSplit.toolTipText")); // NOI18N
        lblFrontPointSplit.setMaximumSize(new java.awt.Dimension(16, 16));
        lblFrontPointSplit.setMinimumSize(new java.awt.Dimension(16, 16));
        lblFrontPointSplit.setPreferredSize(new java.awt.Dimension(16, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panLinePoints.add(lblFrontPointSplit, gridBagConstraints);

        btnFromPointSplit.setIcon(ICON_MERGED_WITH_FROM_POINT);
        btnFromPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromPointSplit.text"));        // NOI18N
        btnFromPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromPointSplit.toolTipText")); // NOI18N
        btnFromPointSplit.setMaximumSize(new java.awt.Dimension(28, 28));
        btnFromPointSplit.setMinimumSize(new java.awt.Dimension(28, 28));
        btnFromPointSplit.setPreferredSize(new java.awt.Dimension(28, 28));
        btnFromPointSplit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromPointSplitActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panLinePoints.add(btnFromPointSplit, gridBagConstraints);

        btnToPointSplit.setIcon(ICON_MERGED_WITH_TO_POINT);
        btnToPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToPointSplit.text"));        // NOI18N
        btnToPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToPointSplit.toolTipText")); // NOI18N
        btnToPointSplit.setMaximumSize(new java.awt.Dimension(28, 28));
        btnToPointSplit.setMinimumSize(new java.awt.Dimension(28, 28));
        btnToPointSplit.setPreferredSize(new java.awt.Dimension(28, 28));
        btnToPointSplit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToPointSplitActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        panLinePoints.add(btnToPointSplit, gridBagConstraints);

        lblRoute.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRoute.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblRoute.text_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        panLinePoints.add(lblRoute, gridBagConstraints);

        btnRoute.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnRoute.text"));        // NOI18N
        btnRoute.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnRoute.toolTipText")); // NOI18N
        btnRoute.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRouteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        panLinePoints.add(btnRoute, gridBagConstraints);

        panLine.setBackground(new java.awt.Color(255, 91, 0));
        panLine.setMinimumSize(new java.awt.Dimension(10, 3));
        panLine.setPreferredSize(new java.awt.Dimension(100, 3));
        panLine.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panLinePoints.add(panLine, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panEdit.add(panLinePoints, gridBagConstraints);

        panOtherLines.setOpaque(false);
        panOtherLines.setLayout(new javax.swing.BoxLayout(panOtherLines, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panEdit.add(panOtherLines, gridBagConstraints);

        panSpacer.setMaximumSize(new java.awt.Dimension(0, 0));
        panSpacer.setMinimumSize(new java.awt.Dimension(0, 0));
        panSpacer.setOpaque(false);
        panSpacer.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panEdit.add(panSpacer, gridBagConstraints);

        add(panEdit, "edit");

        panAdd.setOpaque(false);
        panAdd.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel3.text_1")); // NOI18N
        panAdd.add(jLabel3, new java.awt.GridBagConstraints());

        add(panAdd, "add");

        panError.setOpaque(false);
        panError.setLayout(new java.awt.GridBagLayout());

        lblError.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblError.text_1")); // NOI18N
        panError.add(lblError, new java.awt.GridBagConstraints());

        add(panError, "error");
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFromPointSplitActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFromPointSplitActionPerformed
        splitPoint(FROM);
    }//GEN-LAST:event_btnFromPointSplitActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToPointSplitActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToPointSplitActionPerformed
        splitPoint(TO);
    }//GEN-LAST:event_btnToPointSplitActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToBadGeomCorrectActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToBadGeomCorrectActionPerformed
        correctBadGeom(TO);
    }//GEN-LAST:event_btnToBadGeomCorrectActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToBadGeomActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToBadGeomActionPerformed
        switchBadGeomVisibility(TO);
    }//GEN-LAST:event_btnToBadGeomActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFromBadGeomCorrectActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFromBadGeomCorrectActionPerformed
        correctBadGeom(FROM);
    }//GEN-LAST:event_btnFromBadGeomCorrectActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFromBadGeomActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFromBadGeomActionPerformed
        switchBadGeomVisibility(FROM);
    }//GEN-LAST:event_btnFromBadGeomActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRouteActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRouteActionPerformed
        updateOtherLinesPanelVisibility();
    }//GEN-LAST:event_btnRouteActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void updateOtherLinesPanelVisibility() {
        if (isEditable() && isOtherLinesEnabled()) {
            // panel anzeigen / verbergen
            panOtherLines.setVisible(btnRoute.isSelected());

            // gegebenenfalls die features der subrenderer anzeigen / verbergen
            if (isDrawingFeaturesEnabled()) {
                for (final Component component : panOtherLines.getComponents()) {
                    final LinearReferencedLineRenderer renderer = (LinearReferencedLineRenderer)component;
                    renderer.setDrawingFeaturesEnabled(btnRoute.isSelected());
                }
            }
        } else {
            panOtherLines.setVisible(false);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void cleanOtherLinesPanel() {
        for (final Component component : panOtherLines.getComponents()) {
            final LinearReferencedLineRenderer renderer = (LinearReferencedLineRenderer)component;
            renderer.dispose();
        }
        panOtherLines.removeAll();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void splitPoint(final boolean isFrom) {
        if (isEditable()) {
            final CidsBean oldPointBean = getPointBean(isFrom);
            final LinearReferencedPointFeature oldPointFeature = getPointFeature(isFrom);
            final double oldPosition = getPointFeature(isFrom).getCurrentPosition();

            final CidsBean newPointBean = LinearReferencingHelper.createStationBeanFromRouteBean(LinearReferencingHelper
                            .getRouteBeanFromStationBean(getPointBean(isFrom)));
            setPoint(newPointBean, isFrom);
            pointBeanValueChanged(isFrom);

            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            getLineFeature().setPointFeature(pointFeature, isFrom);

            if ((fromPointFeature != null) && !fromPointFeature.isEditable()) {
                fromPointFeature.setEditable(isEditable());
            }

            if (oldPointFeature != null) {
                oldPointFeature.setEditable(false);
            }

            MERGE_REGISTRY.firePointBeanSplitted(oldPointBean);

            // neue station auf selbe position setzen wie die alte
            pointFeature.moveToPosition(oldPosition);

            // das reseten eines punktes wieder rückgängig machen falls das feature editable ist (was bedeutet dass
            // irgendein editor diese bean verwendet)
            if (oldPointFeature.isEditable()) {
                try {
                    LinearReferencingHelper.setLinearValueToStationBean(oldPosition, oldPointBean);
                } catch (Exception ex) {
                    LOG.error("error while avoiding reset of editable point", ex);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pointBean  DOCUMENT ME!
     * @param  isFrom     DOCUMENT ME!
     */
    private void setPointBean(final CidsBean pointBean, final boolean isFrom) {
        try {
            getLineBean().setProperty(getPointField(isFrom), pointBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while setting cidsbean for point", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lineBean  DOCUMENT ME!
     */
    private void setLineBean(final CidsBean lineBean) {
        try {
            cleanupLine();
            if (getCidsBean() != null && getLineField() != null) {
                getCidsBean().setProperty(getLineField(), lineBean);
            }
            initLine();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while setting line bean to cidsbean", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     */
    private void setLineBeanFromRouteBean(final CidsBean routeBean) {
        final CidsBean lineBean = LinearReferencingHelper.createLineBeanFromRouteBean(routeBean);
        setLineBean(lineBean);

        // Geometrie für BoundingBox erzeufen
        final XBoundingBox boundingBox = (XBoundingBox)MAPPING_COMPONENT.getCurrentBoundingBox();
        final Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        final GeometryFactory gf = new GeometryFactory();
        final LineString boundingBoxGeom = gf.createLineString(coordinates.toArray(new Coordinate[0]));

        final LinearReferencedLineFeature lineFeature = getLineFeature();

        // ermitteln welche Punkte sich innerhalb der Boundingbox befinden
        final Coordinate fromCoord = lineFeature.getPointFeature(FROM).getGeometry().getCoordinate();
        final Coordinate toCoord = lineFeature.getPointFeature(TO).getGeometry().getCoordinate();
        final boolean testFrom = (fromCoord.x > boundingBox.getX1())
                    && (fromCoord.x < boundingBox.getX2())
                    && (fromCoord.y > boundingBox.getY1())
                    && (fromCoord.y < boundingBox.getY2());
        final boolean testTo = (toCoord.x > boundingBox.getX1())
                    && (toCoord.x < boundingBox.getX2())
                    && (toCoord.y > boundingBox.getY1())
                    && (toCoord.y < boundingBox.getY2());

        // Startwerte festlegen
        final LineString featureGeom = (LineString)lineFeature.getGeometry();
        double minPosition = (testFrom) ? 0 : featureGeom.getLength();
        double maxPosition = (testTo) ? featureGeom.getLength() : 0;

        // Coordinaten durchlaufen und anhand der Position auf der Linie sortieren
        final Geometry intersectionGeom = featureGeom.intersection(boundingBoxGeom);
        for (final Coordinate coord : intersectionGeom.getCoordinates()) {
            final double position = LinearReferencedPointFeature.getPositionOnLine(
                    coord,
                    featureGeom);
            if (position > maxPosition) {
                maxPosition = position;
            }
            if (position < minPosition) {
                minPosition = position;
            }
        }

        // sollte max größer min sein, dann umdrehen
        if (minPosition > maxPosition) {
            final double tmp = minPosition;
            minPosition = maxPosition;
            maxPosition = tmp;
        }

        // ermittelte from und to Position setzen
        setPointValueToBean(minPosition, FROM);
        setPointValueToBean(maxPosition, TO);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> cidsBeans) {
        if (isEnabled() && isEditable()) {
            for (final CidsBean routeBean : cidsBeans) {
                if (routeBean.getMetaObject().getMetaClass().getName().equals(CN_ROUTE)) {
                    if ((getDropBehavior() == null) || getDropBehavior().checkForAdding(routeBean)) {
                        setLineBeanFromRouteBean(routeBean);
                        setChangedSinceDrop(false);
                    }
                    if (isAutoZoomActivated) {
                        MapUtil.zoomToFeatureCollection(getZoomFeatures());
                    }
                    return;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("no route found in dropped objects");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom        DOCUMENT ME!
     * @param  targetIsFrom  DOCUMENT ME!
     */
    private void updateSnappedRealGeoms(final boolean isFrom, final boolean targetIsFrom) {
        final MetaClass mcLine = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, CN_STATIONLINE);

        final int ownLineId = getLineBean().getMetaObject().getId();
        final int pointId = getPointBean(isFrom).getMetaObject().getId();
        final String query = "SELECT "
                    + "   " + mcLine.getId() + ", "
                    + "   " + mcLine.getPrimaryKey() + " "
                    + "FROM "
                    + "   " + mcLine.getTableName() + " "
                    + "WHERE "
                    + "   " + mcLine.getPrimaryKey() + " != " + ownLineId + " AND "
                    + "   " + getPointField(targetIsFrom) + " = " + pointId + " "
                    + ";";

        try {
            // wk_teile mit gleicher station an einem Ende holen
            final MetaObject[] mos = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            for (final MetaObject mo : mos) {
                // bean des wk_teils
                final CidsBean targetBean = mo.getBean();

                // beans der stationen
                final CidsBean targetFromBean = (CidsBean)targetBean.getProperty(getPointField(FROM));
                final CidsBean targetToBean = (CidsBean)targetBean.getProperty(getPointField(TO));

                // features erzeugen um damit die Geometrie neu zu berechnen
                final LinearReferencedPointFeature targetFromFeature = new LinearReferencedPointFeature(
                        LinearReferencingHelper.getLinearValueFromStationBean(targetFromBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(targetFromBean));
                final LinearReferencedPointFeature targetToFeature = new LinearReferencedPointFeature(
                        LinearReferencingHelper.getLinearValueFromStationBean(targetToBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(targetToBean));
                final LinearReferencedLineFeature targetFeature = new LinearReferencedLineFeature(
                        targetFromFeature,
                        targetToFeature);

                final LinearReferencedPointFeature targetPointFeature = (targetIsFrom) ? targetFromFeature
                                                                                       : targetToFeature;

                // gesnappte Station auf den selben StationierungsWert wie das Original setzen
                targetPointFeature.moveToPosition(getPointFeature(isFrom).getCurrentPosition());

                // von feature neu berechnete geometrie im wk_teil setzen
                LinearReferencingHelper.setGeometryToLineBean(targetFeature.getGeometry(), targetBean);

                // wk_teil speichern
                targetBean.persist();
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateSnappedRealGeoms(final boolean isFrom) {
        updateSnappedRealGeoms(isFrom, FROM);
        updateSnappedRealGeoms(isFrom, TO);
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (event.getStatus() != EditorSaveStatus.SAVE_SUCCESS) {
            final CidsBean savedBean = event.getSavedBean();
            if (savedBean != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("editor closed: " + event.getSavedBean().getMOString(), new CurrentStackTrace());
                }
                final CidsBean oldCidsBean = getCidsBean();
                setCidsBean(event.getSavedBean());

                if (isEditable()) {
                    updateSnappedRealGeoms(FROM);
                    updateSnappedRealGeoms(TO);
                }

                setCidsBean(oldCidsBean);
            }
        } else if (event.getStatus() == EditorSaveStatus.SAVE_SUCCESS) {
            for (final CidsBean otherLineBean : otherLineBeans) {
                if (otherLineBean.hasArtificialChangeFlag()) {
                    try {
                        otherLineBean.persist();
                    } catch (Exception ex) {
                        LOG.error("error during persist", ex);
                    }
                }
            }
            otherLineBeans.clear();
        }
        CIDSBEAN_CACHE.clear();
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mergeParentLineEditor  DOCUMENT ME!
     */
    protected void setMergeParentLineEditor(final LinearReferencedLineEditor mergeParentLineEditor) {
        this.mergeParentLineEditor = mergeParentLineEditor;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedLineEditor getMergeParentLineEditor() {
        return mergeParentLineEditor;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class PointBeanTransferHandler extends TransferHandler {

        //~ Instance fields ----------------------------------------------------

        boolean isFrom;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PointBeanTransferHandler object.
         *
         * @param  isFrom  DOCUMENT ME!
         */
        public PointBeanTransferHandler(final boolean isFrom) {
            this.isFrom = isFrom;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Transferable createTransferable(final JComponent c) {
            return new PointBeanTransferable(isFrom);
        }

        @Override
        public int getSourceActions(final JComponent c) {
            return COPY;
        }

        //~ Inner Classes ------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private class PointBeanTransferable implements Transferable {

            //~ Instance fields ------------------------------------------------

            private final boolean isFrom;

            //~ Constructors ---------------------------------------------------

            /**
             * Creates a new PointTransferable object.
             *
             * @param  isFrom  pointBean DOCUMENT ME!
             */
            private PointBeanTransferable(final boolean isFrom) {
                this.isFrom = isFrom;
            }

            //~ Methods --------------------------------------------------------

            @Override
            public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) {
                    throw new UnsupportedFlavorException(flavor);
                }
                return isFrom;
            }

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { CIDSBEAN_DATAFLAVOR };
            }

            @Override
            public boolean isDataFlavorSupported(final DataFlavor flavor) {
                return flavor.equals(CIDSBEAN_DATAFLAVOR);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class PointBeanDropTargetListener implements DropTargetListener {

        //~ Instance fields ----------------------------------------------------

        private boolean isFrom;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PointBeanDropTargetListener object.
         *
         * @param  isFrom  DOCUMENT ME!
         */
        public PointBeanDropTargetListener(final boolean isFrom) {
            this.isFrom = isFrom;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void drop(final DropTargetDropEvent e) {
            try {
                final LinearReferencedLineEditor mergeParentLineEditor = getMergeParentLineEditor();
                if (mergeParentLineEditor != null) {
                    final Transferable tr = e.getTransferable();
                    final DataFlavor[] flavors = tr.getTransferDataFlavors();
                    for (int i = 0; i < flavors.length; i++) {
                        if (flavors[i].isFlavorSerializedObjectType()) {
                            e.acceptDrop(e.getDropAction());
                            final boolean isParentFrom = (Boolean)tr.getTransferData(CIDSBEAN_DATAFLAVOR);
                            final CidsBean parentPointBean = mergeParentLineEditor.getPointBean(isParentFrom);
                            // darf nicht mergen wenn dadurch der from und der to point gleich wären
                            if (!getPointBean(isFrom).equals(mergeParentLineEditor.getPointBean(!isParentFrom))) {
                                mergeParentLineEditor.setPoint(getPointBean(isFrom), isParentFrom);
                                mergeParentLineEditor.pointBeanValueChanged(isParentFrom);
                                e.dropComplete(true);
                                MERGE_REGISTRY.firePointBeanMerged(parentPointBean, getPointBean(isFrom));
                                return;
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                LOG.error("error while drop", t);
            }
            e.rejectDrop();
        }

        @Override
        public void dragEnter(final DropTargetDragEvent dtde) {
            LOG.info("dragEnter");
        }

        @Override
        public void dragOver(final DropTargetDragEvent dtde) {
            LOG.info("dragOver");
        }

        @Override
        public void dropActionChanged(final DropTargetDragEvent dtde) {
            LOG.info("dropActionChanged");
        }

        @Override
        public void dragExit(final DropTargetEvent dte) {
            LOG.info("dragExit");
        }
    }
}
