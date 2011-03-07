/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jweintraut
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
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.actions.wrrl_db_mv.SetHintDialog.Priority;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.utils.MetaClassCacheService;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.features.CommonFeatureAction;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CommonFeatureAction.class)
public class SetHintAction extends AbstractAction implements CommonFeatureAction {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(SetHintAction.class);

    //~ Instance fields --------------------------------------------------------

    private Feature feature;
    private MetaClass geoHintMetaClass;
    private boolean isCurrentUserAllowedToSetHint;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SetHintAction object.
     */
    public SetHintAction() {
        super(NbBundle.getMessage(SetHintAction.class, "SetHintAction.name"),
            new javax.swing.ImageIcon(
                SetHintAction.class.getResource("/de/cismet/cids/custom/actions/wrrl_db_mv/tag_blue_add.png")));

        try {
            final MetaClassCacheService classcache = Lookup.getDefault().lookup(MetaClassCacheService.class);
            geoHintMetaClass = classcache.getMetaClass(SessionManager.getSession().getUser().getDomain(), "geo_hint");
            isCurrentUserAllowedToSetHint = geoHintMetaClass.getPermissions()
                        .hasWritePermission(SessionManager.getSession().getUser().getUserGroup());
        } catch (Exception e) {
            LOG.error(
                "An error occurred while trying to set up SetHintAction. There was a problem with the lookup mechanism or session handling.",
                e);
            setEnabled(false);
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final SetHintDialog dlgSetHint = new SetHintDialog(CismapBroker.getInstance().getMappingComponent());
        UIUtil.findOptimalPositionOnScreen(dlgSetHint);
        dlgSetHint.setVisible(true);

        if (dlgSetHint.wasCancelled()) {
            return;
        }

        // TODO: Make this hardcoded mapping from dialog to table "priority" dynamic
        int priorityId = 2;
        if (dlgSetHint.getPriorityProperty().equals(Priority.HIGH)) {
            priorityId = 1;
        } else if (dlgSetHint.getPriorityProperty().equals(Priority.LOW)) {
            priorityId = 3;
        }

        try {
            final MetaClass priorityMetaClass = ClassCacheMultiple.getMetaClass(
                    CidsBeanSupport.DOMAIN_NAME,
                    "priority");
            final MetaObject priorityMetaObject = SessionManager.getProxy()
                        .getMetaObject(priorityId, priorityMetaClass.getID(), CidsBeanSupport.DOMAIN_NAME);
            final CidsBean hint = CidsBeanSupport.createNewCidsBeanFromTableName("geo_hint");
            final CidsBean geometry = CidsBeanSupport.createNewCidsBeanFromTableName("geom");

            hint.setProperty("usr", SessionManager.getSession().getUser().toString());
            hint.setProperty("timestamp", new java.sql.Timestamp(System.currentTimeMillis()));
            hint.setProperty("name", dlgSetHint.getNameProperty());
            hint.setProperty("comment", dlgSetHint.getCommentProperty());
            hint.setProperty("priority", priorityMetaObject.getBean());

            // TODO: Should be centralised somewhere. It's the third occurrence of this calculation.
            int srid = feature.getGeometry().getSRID();
            final int defaultSrid = CrsTransformer.extractSridFromCrs(CismapBroker.getInstance().getDefaultCrs());
            if (srid == CismapBroker.getInstance().getDefaultCrsAlias()) {
                srid = defaultSrid;
            }
            if (srid != defaultSrid) {
                feature.setGeometry(CrsTransformer.transformToDefaultCrs(feature.getGeometry()));
            }
            feature.getGeometry().setSRID(CismapBroker.getInstance().getDefaultCrsAlias());

            geometry.setProperty("geo_field", feature.getGeometry());
            hint.setProperty("geometry", geometry);

            /*MetaObject priorityMetaObject = priorityMetaClass.getEmptyInstance();
             *CidsBean priority = priorityMetaObject.getBean();*/

            hint.persist();
        } catch (Exception ex) {
            LOG.error("Could not persist new entity for table 'geo_hint'.", ex);
            JOptionPane.showMessageDialog(CismapBroker.getInstance().getMappingComponent(),
                NbBundle.getMessage(SetHintAction.class, "SetHintAction.actionPerformed(ActionEvent).errorMessage"),
                NbBundle.getMessage(SetHintAction.class, "SetHintAction.actionPerformed(ActionEvent).errorTitle"),
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setSourceFeature(final Feature source) {
        feature = source;
    }

    @Override
    public Feature getSourceFeature() {
        return feature;
    }

    @Override
    public boolean isActive() {
        return isCurrentUserAllowedToSetHint && (feature instanceof PureNewFeature);
    }

    @Override
    public int getSorter() {
        // Position im Kontextmenü
        return 10;
    }
}
