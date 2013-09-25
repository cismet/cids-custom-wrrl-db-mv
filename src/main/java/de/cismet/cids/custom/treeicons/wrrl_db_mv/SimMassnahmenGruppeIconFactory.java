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

import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SimMassnahmenGruppeIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            SimMassnahmenGruppeIconFactory.class);
    private static ImageIcon fallback = new ImageIcon(SimMassnahmenGruppeIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/plus-shield.png_16x16.png"));

    //~ Instance fields --------------------------------------------------------

    private final ImageIcon GROUP_ICON;
    private final ImageIcon SINGLE_ICON;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public SimMassnahmenGruppeIconFactory() {
        GROUP_ICON = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/plus-shield.png_16x16.png"));
        SINGLE_ICON = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/plus-circle.png_16x16.png"));
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
        final CidsBean bean = otn.getMetaObject().getBean();
        final List<CidsBean> beans = bean.getBeanCollectionProperty("massnahmen");

        if ((beans != null) && (beans.size() > 1)) {
            return GROUP_ICON;
        } else {
            return SINGLE_ICON;
        }
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return null;
    }
}
