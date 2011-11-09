/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineArrayEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class LinearReferencedLineArrayRenderer extends LinearReferencedLineArrayEditor {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LinearReferencedLineArrayRenderer object.
     */
    public LinearReferencedLineArrayRenderer() {
        super(true);
        setOtherLinesEnabled(false);
        setDrawingFeaturesEnabled(false);
    }
}
