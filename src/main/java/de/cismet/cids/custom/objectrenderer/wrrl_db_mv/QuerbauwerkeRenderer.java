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
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import javax.swing.JComponent;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkeEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeRenderer extends QuerbauwerkeEditor implements TitleComponentProvider {

    //~ Instance fields --------------------------------------------------------

    private final transient QuerbauwerkeTitleComponent titleComponent;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new QuerbauwerkeRenderer object.
     */
    public QuerbauwerkeRenderer() {
        super(true);
        titleComponent = new QuerbauwerkeTitleComponent();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);

        if (cidsBean != null) {
            titleComponent.setCidsBean(cidsBean);
        }
    }

    @Override
    public JComponent getTitleComponent() {
        return titleComponent;
    }
}
