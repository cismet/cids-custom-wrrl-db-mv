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
package de.cismet.cids.custom.treeicons.wrrl_db_mv;

import Sirius.navigator.types.treenode.ClassTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.types.treenode.PureTreeNode;
import Sirius.navigator.ui.tree.CidsTreeObjectIconFactory;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UnterhaltungsmassnahmeValidator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupMassnahmenartIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static ImageIcon fallback = new ImageIcon(GupMassnahmenartIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/cog.png_16x16.png"));

    private static final ImageIcon VALID_ICON;
    private static final ImageIcon INVALID_ICON;
    private static final ImageIcon WARNING_ICON;
    private static final ImageIcon ERROR_ICON;

    static {
        VALID_ICON = new ImageIcon(GupMassnahmenartIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/green_cog.png"));
        INVALID_ICON = new ImageIcon(GupMassnahmenartIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/red_cog.png"));
        ERROR_ICON = new ImageIcon(GupMassnahmenartIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/cog.png_16x16.png"));
        WARNING_ICON = new ImageIcon(GupMassnahmenartIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/cog.png_16x16.png"));
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public GupMassnahmenartIconFactory() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return null;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return null;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return null;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return null;
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return null;
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        final UnterhaltungsmassnahmeValidator uv = GupPlanungsabschnittEditor.getSearchValidator();

        final int komp = GupUnterhaltungsmassnahmeEditor.getLastKompartiment();

        if (GupUnterhaltungsmassnahmeEditor.getLastKompartiment() == 0) {
            return fallback;
        } else {
            final CidsBean massn = otn.getMetaObject().getBean();

            if (!GupUnterhaltungsmassnahmeEditor.supportsKompartiment(massn, komp)) {
                return ERROR_ICON;
            }
        }

        if (!uv.isReady()) {
            return fallback;
        } else {
            final CidsBean massn = otn.getMetaObject().getBean();
            final CidsBean umassn = GupPlanungsabschnittEditor.getLastActiveMassnBean();
            final UnterhaltungsmassnahmeValidator.ValidationResult rs = uv.validate(
                    umassn,
                    massn,
                    new ArrayList<String>());

            if (rs.equals(UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
                return VALID_ICON;
            } else if (rs.equals(UnterhaltungsmassnahmeValidator.ValidationResult.error)) {
                return INVALID_ICON;
            } else {
                return WARNING_ICON;
            }
        }
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return null;
    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @param   node  DOCUMENT ME!
//     *
//     * @return  DOCUMENT ME!
//     */
//    private Icon generateIconFromState(final ObjectTreeNode node) {
//        Icon result = null;
//        if (node != null) {
//            final MetaObject baulastMO = node.getMetaObject(false);
//            if (baulastMO != null) {
//                final CidsBean baulastBean = baulastMO.getBean();
//                result = node.getLeafIcon();
//
//                if (!checkIfBaulastBeansIsComplete(baulastBean)) {
//                    final Icon overlay = Static2DTools.createOverlayIcon(
//                            WARNING_ICON,
//                            result.getIconWidth(),
//                            result.getIconHeight());
//                    result = Static2DTools.mergeIcons(result, overlay);
////                result = overlay;
////                result = Static2DTools.mergeIcons(result, Static2DTools.createOverlayIcon(WARNING_ICON, result.getIconWidth(), result.getIconHeight()));
////                result = Static2DTools.mergeIcons(new Icon[]{result, WARNING_ICON});
//                } else {
//                    if (baulastBean.getProperty("loeschungsdatum") != null) {
//                        final Icon overlay = Static2DTools.createOverlayIcon(
//                                DELETED_ICON,
//                                result.getIconWidth(),
//                                result.getIconHeight());
//                        result = Static2DTools.mergeIcons(result, overlay);
////                    result = overlay;
////                result = Static2DTools.mergeIcons(result, Static2DTools.createOverlayIcon(DELETED_ICON, result.getIconWidth(), result.getIconHeight()));
////                result = Static2DTools.mergeIcons(new Icon[]{result, DELETED_ICON});
//                    } else if (baulastBean.getProperty("geschlossen_am") != null) {
//                        final Icon overlay = Static2DTools.createOverlayIcon(
//                                CLOSED_ICON,
//                                result.getIconWidth(),
//                                result.getIconHeight());
//                        result = Static2DTools.mergeIcons(result, overlay);
////                    result = overlay;
////                result = Static2DTools.mergeIcons(result, Static2DTools.createOverlayIcon(CLOSED_ICON, result.getIconWidth(), result.getIconHeight()));
////                result = Static2DTools.mergeIcons(new Icon[]{result, CLOSED_ICON});
//                    }
//                }
//                return result;
//            } else {
//                if (!listOfRetrievingObjectWorkers.contains(node)) {
//                    synchronized (listOfRetrievingObjectWorkers) {
//                        if (!listOfRetrievingObjectWorkers.contains(node)) {
//                            listOfRetrievingObjectWorkers.add(node);
//                            objectRetrievalExecutor.execute(new javax.swing.SwingWorker<Void, Void>() {
//
//                                    @Override
//                                    protected Void doInBackground() throws Exception {
//                                        if (!(node == null)) {
//                                            if (node.getPath()[0].equals(
//                                                            ComponentRegistry.getRegistry().getSearchResultsTree()
//                                                                .getModel().getRoot())) {
//                                                // Searchtree
//                                                if (ComponentRegistry.getRegistry().getSearchResultsTree().containsNode(
//                                                                node.getNode())) {
//                                                    node.getMetaObject(true);
//                                                }
//                                            } else {
//                                                // normaler Baum
//                                                node.getMetaObject(true);
//                                            }
//                                        }
//                                        return null;
//                                    }
//
//                                    @Override
//                                    protected void done() {
//                                        try {
//                                            synchronized (listOfRetrievingObjectWorkers) {
//                                                listOfRetrievingObjectWorkers.remove(node);
//                                            }
//                                            final Void result = get();
//                                            if (node.getPath()[0].equals(
//                                                            ComponentRegistry.getRegistry().getSearchResultsTree()
//                                                                .getModel().getRoot())) {
//                                                // Searchtree
//                                                ((DefaultTreeModel)ComponentRegistry.getRegistry()
//                                                            .getSearchResultsTree().getModel()).nodeChanged(node);
//                                            } else {
//                                                // normaler Baum
//                                                ((DefaultTreeModel)ComponentRegistry.getRegistry().getCatalogueTree()
//                                                            .getModel()).nodeChanged(node);
//                                            }
//                                        } catch (Exception e) {
//                                            log.error("Fehler beim Laden des MetaObjects", e);
//                                        }
//                                    }
//                                });
//                        }
//                    }
//                } else {
//                    // evtl log meldungen
//                }
//            }
//            return fallback;
//        }
//
//        return null;
//    }
}
