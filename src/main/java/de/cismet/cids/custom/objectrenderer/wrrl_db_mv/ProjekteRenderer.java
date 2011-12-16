/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.ProjekteEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjekteRenderer extends ProjekteEditor {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjekteRenderer object.
     */
    public ProjekteRenderer() {
        super(true);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            DevelopmentTools.createRendererInFrameFromRMIConnectionOnLocalhost(
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "x",
                "projekte",
                420,
                "Test",
                1280,
                1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
