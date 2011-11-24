/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.EventListener;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public interface LinearReferencedLineEditorListener extends EventListener {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    void linearReferencedLineCreated();

    /**
     * DOCUMENT ME!
     *
     * @param  visible  DOCUMENT ME!
     */
    void otherLinesPanelVisibilityChange(boolean visible);
}
