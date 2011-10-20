/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.actions.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import org.openide.util.lookup.ServiceProvider;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public class FgskSplitToolbarItem extends AbstractAction implements CidsClientToolbarItem {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "fgsk_kartierabschnitt");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskToolbarItem object.
     */
    public FgskSplitToolbarItem() {
        setIcon(new ImageIcon(this.getClass().getResource(
                    "/de/cismet/cids/custom/icons/wrrl-db-mv/fgsk.png")));
        setTooltip("Kartierabschnitt teilen");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        final Frame parentFrame = StaticSwingTools.getParentFrame(mappingComponent);
        final Collection<Feature> features = mappingComponent.getFeatureCollection().getSelectedFeatures();
        if (features.size() != 1) {
            JOptionPane.showMessageDialog(
                parentFrame,
                "Es muss genau ein Objekt selektiert sein.",
                "Fehler",
                JOptionPane.ERROR_MESSAGE);
        } else {
            final Feature feature = features.toArray(new Feature[0])[0];
            if (!(feature instanceof CidsFeature)) {
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Es muss genau ein Objekt selektiert sein.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                final CidsFeature cidsFeature = (CidsFeature)feature;
                final boolean isKartierabschnitt = cidsFeature.getMetaClass()
                            .getTableName()
                            .equalsIgnoreCase("fgsk_kartierabschnitt");
                if (!isKartierabschnitt) {
                    JOptionPane.showMessageDialog(
                        parentFrame,
                        "Bei dem selektierten Objekt handelt es sich nicht um ein Kartierabschnitt.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    final CidsBean cidsBean = cidsFeature.getMetaObject().getBean();
                    final FgskSplitDialog dialog = new FgskSplitDialog(cidsBean, mappingComponent);
                    dialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(
                            CismapBroker.getInstance().getMappingComponent()));
                    dialog.setVisible(true);
                }
            }
        }
    }

    @Override
    public String getSorterString() {
        return "Z";
    }

    @Override
    public boolean isVisible() {
        return MC_FGSK.getPermissions().hasWritePermission(SessionManager.getSession().getUser().getUserGroup());
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
}
