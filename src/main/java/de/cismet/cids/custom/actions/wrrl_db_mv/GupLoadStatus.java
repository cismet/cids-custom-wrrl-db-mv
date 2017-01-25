/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.actions.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public class GupLoadStatus extends AbstractAction implements CidsClientToolbarItem {

    //~ Static fields/initializers ---------------------------------------------

    private static GupLoadStatus lastInstance;

    //~ Instance fields --------------------------------------------------------

    private final MetaClass GUP_PLANUNGSABSCHNITT_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_planungsabschnitt");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupLoadStatus object.
     */
    public GupLoadStatus() {
        final ImageIcon icon = new ImageIcon(this.getClass().getResource(
                    "/de/cismet/cids/custom/actions/wrrl_db_mv/inProgress.png"));
        setIcon(icon);
        setTooltip("Gup Maßnahmenarten werden vorgeladen");
        lastInstance = this;
        setEnabled(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static GupLoadStatus getLastInstance() {
        return lastInstance;
    }

    /**
     * DOCUMENT ME!
     */
    public void setStatusOk() {
        final ImageIcon icon = new ImageIcon(this.getClass().getResource(
                    "/de/cismet/cids/custom/actions/wrrl_db_mv/ok.png"));
        setIcon(icon);
        setTooltip("Gup Maßnahmenarten wurden vorgeladen");
    }

    @Override
    public String getSorterString() {
        return "01";
    }

    @Override
    public boolean isVisible() {
        return (GUP_PLANUNGSABSCHNITT_MC != null)
                    && GUP_PLANUNGSABSCHNITT_MC.getPermissions()
                    .hasWritePermission(
                            SessionManager.getSession().getUser());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  i  DOCUMENT ME!
     */
    private void setIcon(final Icon i) {
        putValue(Action.SMALL_ICON, i);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  text  DOCUMENT ME!
     */
    private void setTooltip(final String text) {
        putValue(Action.SHORT_DESCRIPTION, text);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
    }
}
