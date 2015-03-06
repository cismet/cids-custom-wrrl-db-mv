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

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupOperativesZielRouteEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.PflegezieleValidator;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UnterhaltungsmassnahmeValidator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupOperativesZielIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static final ImageIcon fallback = new ImageIcon(GupOperativesZielIconFactory.class.getResource(
                "/de/cismet/cids/custom/treeicons/wrrl_db_mv/generalSymbol.png"));

    private static final ImageIcon VALID_ICON;
    private static final ImageIcon INVALID_ICON;

    static {
        VALID_ICON = new ImageIcon(GupOperativesZielIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/generalSymbolValid.png"));
        INVALID_ICON = new ImageIcon(GupOperativesZielIconFactory.class.getResource(
                    "/de/cismet/cids/custom/treeicons/wrrl_db_mv/generalSymbolInValid.png"));
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public GupOperativesZielIconFactory() {
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
        final PflegezieleValidator validator = GupOperativesZielRouteEditor.getSearchValidator();
        final CidsBean pflegeziel = otn.getMetaObject().getBean();

        if (!validator.isReady()) {
            return fallback;
        } else {
            final CidsBean umassn = GupOperativesZielRouteEditor.getLastActivePflegezielBean();
            final PflegezieleValidator.ValidationResult rs = validator.validate(
                    umassn,
                    pflegeziel,
                    new ArrayList<String>());

            if (rs.equals(PflegezieleValidator.ValidationResult.ok)) {
                return VALID_ICON;
            } else {
                return INVALID_ICON;
            }
        }
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return null;
    }
}
