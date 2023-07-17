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
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;

import org.apache.log4j.Logger;

import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.configuration.StartupHook;
import org.openide.util.lookup.ServiceProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class ConfigStartupHook implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(ConfigStartupHook.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        try {
            final boolean showFullPath = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(),
                            "showFullWmsPath",
                            ConnectionContext.create(AbstractConnectionContext.Category.STARTUP, "ConfigStartup"));

            if (showFullPath) {
                CismapBroker.getInstance().setWMSLayerNamesWithPath(true);
            }
        } catch (Exception e) {
            LOG.error("Cannot read configuration attribute showFullWmsPath", e);
        }
    }
}
