package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.EventListener;

/**
 *
 * @author jruiz
 */
public interface LinearReferencedLineArrayEditorListener extends EventListener {

    public void editorAdded(LinearReferencedLineEditor source);

    public void editorRemoved(LinearReferencedLineEditor source);

}
