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
public interface StationArrayEditorListener extends EventListener {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    void editorAdded(StationEditor source);

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    void editorRemoved(StationEditor source);
}
