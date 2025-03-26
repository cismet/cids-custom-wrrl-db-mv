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
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.method.MethodManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanCache;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public class FgskSplitArToolbarItem extends AbstractAction implements CidsClientToolbarItem {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "fgsk_kartierabschnitt");
    private static Logger LOG = Logger.getLogger(FgskSplitArToolbarItem.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskToolbarItem object.
     */
    public FgskSplitArToolbarItem() {
        setIcon(new ImageIcon(this.getClass().getResource(
                    "/de/cismet/cids/custom/icons/wrrl-db-mv/fgskSplit.png")));
        setTooltip("Kartierabschnitt teilen (außerhalb Route)");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        final Frame parentFrame = StaticSwingTools.getParentFrame(mappingComponent);
        final Collection<Feature> features = mappingComponent.getFeatureCollection().getSelectedFeatures();
        if (features.size() != 2) {
            JOptionPane.showMessageDialog(
                parentFrame,
                "Es muss genau ein FGSK-Objekt und eine Linie selektiert sein.",
                "Fehler",
                JOptionPane.ERROR_MESSAGE);
        } else {
            Feature feature = features.toArray(new Feature[0])[0];
            Feature feature2 = features.toArray(new Feature[0])[1];

            if (!(feature instanceof CidsFeature)) {
                feature = features.toArray(new Feature[0])[1];
            }

            if (!(feature2 instanceof PureNewFeature)) {
                feature2 = features.toArray(new Feature[0])[0];
            }

            if ((!(feature instanceof CidsFeature) || !(feature2 instanceof PureNewFeature))) {
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Es muss genau ein FGSK-Objekt und eine Linie selektiert sein.",
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
                    final CidsBean openBean = FgskKartierabschnittEditor.getCurrentlyOpenBean();
                    final CidsBean cidsBean = cidsFeature.getMetaObject().getBean();
                    if ((openBean != null)) { // && (openBean.getMetaObject().getID() ==
                                              // cidsBean.getMetaObject().getID())) {
                        JOptionPane.showMessageDialog(
                            parentFrame,
                            "Der Editor des Kartierabschnitts muss vor dem Teilen geschlossen werden.",
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                    } else {
//                        final FgskSplitDialog dialog = new FgskSplitDialog(cidsBean, mappingComponent);
//                        StaticSwingTools.showDialog(dialog);
                        final MetaClass MC = ClassCacheMultiple.getMetaClass(
                                WRRLUtil.DOMAIN_NAME,
                                "fgsk_kartierabschnitt");

                        final String query = "select " + MC.getID() + ", splitFgsk from splitFgsk("
                                    + cidsFeature.getMetaObject().getId() + ", '" + feature2.getGeometry().toText()
                                    + "')"; // NOI18N
                        try {
                            CidsBeanCache.getInstance().clear();
                            final MetaObject[] mos = SessionManager.getSession()
                                        .getConnection()
                                        .getMetaObjectByQuery(SessionManager.getSession().getUser(),
                                            query,
                                            ConnectionContext.create(
                                                AbstractConnectionContext.Category.OTHER,
                                                FgskSplitArToolbarItem.class.getName()));

                            final Collection<Node> nodes = new ArrayList<Node>();

                            for (final MetaObject tmp : mos) {
                                nodes.add(new MetaObjectNode(tmp.getBean()));
                            }
                            MethodManager.getManager()
                                    .showSearchResults(null, nodes.toArray(new Node[nodes.size()]), false);
                        } catch (ConnectionException ex) {
                            LOG.error("Error while splitting FGSK object", ex);
                        }
                    }
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
        try {
            return MC_FGSK.getPermissions().hasWritePermission(SessionManager.getSession().getUser())
                        && SessionManager.getConnection()
                        .hasConfigAttr(
                                SessionManager.getSession().getUser(),
                                "splitArFgsk",
                                ConnectionContext.create(
                                    AbstractConnectionContext.Category.OTHER,
                                    FgskSplitArToolbarItem.class.getName()));
        } catch (Exception e) {
            LOG.error("Cannot check config attr", e);
            return false;
        }
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
