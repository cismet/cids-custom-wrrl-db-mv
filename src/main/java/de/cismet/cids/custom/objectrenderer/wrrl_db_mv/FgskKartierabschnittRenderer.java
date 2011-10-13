/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import javax.swing.JComponent;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittRenderer extends FgskKartierabschnittEditor implements TitleComponentProvider {

    //~ Instance fields --------------------------------------------------------

    private final transient FgskKartierabschnittTitleComponent titleComponent;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskKartierabschnittRenderer object.
     */
    public FgskKartierabschnittRenderer() {
        super(true);
        titleComponent = new FgskKartierabschnittTitleComponent();
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
