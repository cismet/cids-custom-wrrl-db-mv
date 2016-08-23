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

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupGupEditor;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGupIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static final ImageIcon PLANUNG_ICON = new ImageIcon(GupGupIconFactory.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Draft.png"));
    private static final ImageIcon ANTRAG_ICON = new ImageIcon(GupGupIconFactory.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/application_from_storage.png"));
    private static final ImageIcon PRUEFUNG_ICON = new ImageIcon(GupGupIconFactory.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png"));
    private static final ImageIcon GENEHMIGT_ICON = new ImageIcon(GupGupIconFactory.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_16.png"));
    private static final ImageIcon ANGENOMMEN_ICON = new ImageIcon(GupGupIconFactory.class.getResource(
                "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Valid_16.png"));

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Alb_baulastIconFactory object.
     */
    public GupGupIconFactory() {
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
        return getObjectNodeIcon(otn);
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return getObjectNodeIcon(otn);
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return getObjectNodeIcon(otn);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   otn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Icon getObjectNodeIcon(final ObjectTreeNode otn) {
        final CidsBean bean = otn.getMetaObject().getBean();
        Icon icon;

        final Integer status = GupGupEditor.determineStatusByGupBean(bean);

        switch (status) {
            case GupGupEditor.STAT_PLANUNG: {
                icon = PLANUNG_ICON;
                break;
            }
            case GupGupEditor.STAT_PLANUNG_FERTIG: {
                icon = ANTRAG_ICON;
                break;
            }
            case GupGupEditor.STAT_NB:
            case GupGupEditor.STAT_WB:
            case GupGupEditor.STAT_NB_ABG:
            case GupGupEditor.STAT_WB_ABG:
            case GupGupEditor.STAT_NB_ABG_WB:
            case GupGupEditor.STAT_NB_WB_ABG:
            case GupGupEditor.STAT_NB_WB: {
                icon = PRUEFUNG_ICON;
                break;
            }
            case GupGupEditor.STAT_NB_ABG_WB_ABG: {
                icon = GENEHMIGT_ICON;
                break;
            }
            case GupGupEditor.STAT_ANGENOMMEN: {
                icon = ANGENOMMEN_ICON;
                break;
            }
            default: {
                // should never happen
                icon = PLANUNG_ICON;
                break;
            }
        }

        return icon;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return null;
    }
}
