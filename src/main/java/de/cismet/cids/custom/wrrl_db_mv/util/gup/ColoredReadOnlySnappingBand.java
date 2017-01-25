/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.MinimumHeightBand;
import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ColoredReadOnlySnappingBand extends ColoredReadOnlyBand implements BandSnappingPointProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsBand object.
     *
     * @param  name             side DOCUMENT ME!
     * @param  colorProperty    DOCUMENT ME!
     * @param  tooltipProperty  DOCUMENT ME!
     */
    public ColoredReadOnlySnappingBand(final String name, final String colorProperty, final String tooltipProperty) {
        super(name, colorProperty, tooltipProperty);
    }
}
