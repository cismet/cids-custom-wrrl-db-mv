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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReadOnlyTextBand extends CopyableBand implements CidsBeanCollectionStore {

    //~ Instance fields --------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */

    Collection<CidsBean> beans = new ArrayList<>();

    private String textProperty;
    private String tooltipProperty;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsBand object.
     *
     * @param  name             side DOCUMENT ME!
     * @param  textProperty     if this parameter is null, cidsBean.toString will be used as text
     * @param  tooltipProperty  if this parameter is null, cidsBean.toString will be used as tooltip text
     */
    public ReadOnlyTextBand(final String name, final String textProperty, final String tooltipProperty) {
        super(name);
        this.textProperty = textProperty;
        this.tooltipProperty = tooltipProperty;
        readOnly = true;
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
                final ReadOnlyTextBandMember unrm = new ReadOnlyTextBandMember();
                try {
                    unrm.setCidsBean(b, getTooltipProperty(), getTextProperty());
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
    public String getTextProperty() {
        return textProperty;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  textProperty  colorProperty the colorProperty to set
     */
    public void setTextProperty(final String textProperty) {
        this.textProperty = textProperty;
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
