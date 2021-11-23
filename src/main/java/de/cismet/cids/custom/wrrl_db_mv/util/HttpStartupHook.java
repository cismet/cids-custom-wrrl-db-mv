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

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

import de.cismet.tools.configuration.StartupHook;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class HttpStartupHook implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(HttpStartupHook.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        final Map<HostConfiguration, Integer> maxHostConnections = new HashMap<HostConfiguration, Integer>();
        maxHostConnections.put(HostConfiguration.ANY_HOST_CONFIGURATION, 64);
        HttpConnectionManagerParams.getDefaultParams()
                .setParameter(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS, maxHostConnections);
        HttpConnectionManagerParams.getDefaultParams()
                .setIntParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS, 64);
//        HttpConnectionManagerParams.getDefaultParams().setIntParameter(HttpConnectionManagerParams.SO_LINGER, 2);
    }
}
