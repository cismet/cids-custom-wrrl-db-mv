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

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.configuration.ConfigurationManager;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReportUtils {

    //~ Methods ----------------------------------------------------------------

    /**
     * Initialises the MappingComponent.
     */
    public static void initCismap() {
        final ConfigurationManager configManager = new ConfigurationManager();
        final MappingComponent mappingComponent = new MappingComponent();

        configManager.setDefaultFileName("de/cismet/cids/custom/reports/defaultCismapProperties.xml");
        configManager.setFallBackFileName("de/cismet/cids/custom/reports/defaultCismapProperties.xml");

        final ActiveLayerModel mappingModel = new ActiveLayerModel();
        configManager.addConfigurable((ActiveLayerModel)mappingModel);
        configManager.addConfigurable(mappingComponent);

        configManager.configure(mappingModel);
        mappingComponent.preparationSetMappingModel(mappingModel);
        configManager.configure(mappingComponent);

        mappingComponent.setMappingModel(mappingModel);

        CismapBroker.getInstance().setMappingComponent(mappingComponent);
    }
}
