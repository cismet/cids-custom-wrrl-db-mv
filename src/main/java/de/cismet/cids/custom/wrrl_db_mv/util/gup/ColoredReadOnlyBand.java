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

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ColoredReadOnlyBand extends MinimumHeightBand implements CidsBeanCollectionStore {

    //~ Instance fields --------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */

    Collection<CidsBean> beans = new ArrayList<CidsBean>();

    private String colorProperty;
    private String tooltipProperty;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsBand object.
     *
     * @param  name             side DOCUMENT ME!
     * @param  colorProperty    DOCUMENT ME!
     * @param  tooltipProperty  DOCUMENT ME!
     */
    public ColoredReadOnlyBand(final String name, final String colorProperty, final String tooltipProperty) {
        super(name);
        this.colorProperty = colorProperty;
        this.tooltipProperty = tooltipProperty;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return beans;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        this.beans = beans;
        if (beans != null) {
            for (final CidsBean b : beans) {
                final ColoredReadOnlyBandMember unrm = new ColoredReadOnlyBandMember();
                try {
                    unrm.setCidsBean(b, getTooltipProperty(), getColorProperty());
                    addMember(unrm);
                } catch (Exception e) {
                    // dann halt nicht
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the colorProperty
     */
    public String getColorProperty() {
        return colorProperty;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  colorProperty  the colorProperty to set
     */
    public void setColorProperty(final String colorProperty) {
        this.colorProperty = colorProperty;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the tooltipProperty
     */
    public String getTooltipProperty() {
        return tooltipProperty;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tooltipProperty  the tooltipProperty to set
     */
    public void setTooltipProperty(final String tooltipProperty) {
        this.tooltipProperty = tooltipProperty;
    }
}
