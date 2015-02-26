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

import Sirius.server.middleware.types.MetaObjectNode;

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
public class SimulationIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static final ImageIcon CUSTOM_SIMULATION_ICON = new ImageIcon(SimulationIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/dashboard.png_16x16.png"));
    private static final ImageIcon FIX_SIMULATION_ICON = new ImageIcon(SimulationIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/icon-barchartasc.png"));
    private static final String SIM_2021_NAME = "Simulation 2021";
    private static final String SIM_2027_NAME = "Simulation 2027";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public SimulationIconFactory() {
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
//        final CidsBean bean = otn.getMetaObject().getBean();
//        final Object realisierung = bean.getProperty("realisierung");

        if (otn.getUserObject() instanceof MetaObjectNode) {
            final String name = ((MetaObjectNode)otn.getUserObject()).getName();

            if (name.equals(SIM_2021_NAME) || name.equals(SIM_2027_NAME)) {
                return FIX_SIMULATION_ICON;
            }
        }

        return CUSTOM_SIMULATION_ICON;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return null;
    }
}
