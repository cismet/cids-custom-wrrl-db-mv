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

    private static final ImageIcon GROUP_ICON = new ImageIcon(SimMassnahmenGruppeIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/plus-shield.png_16x16.png"));
    private static final ImageIcon SINGLE_ICON = new ImageIcon(SimMassnahmenGruppeIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/plus-circle.png_16x16.png"));

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public SimMassnahmenGruppeIconFactory() {
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
