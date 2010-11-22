package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.EventListener;

/**
 *
 * @author jruiz
 */
public interface StationArrayEditorListener extends EventListener {

    public void editorAdded(StationEditor source);

    public void editorRemoved(StationEditor source);

}
