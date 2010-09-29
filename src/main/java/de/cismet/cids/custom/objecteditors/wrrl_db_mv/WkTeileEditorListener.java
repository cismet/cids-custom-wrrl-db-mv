package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.EventListener;

/**
 *
 * @author jruiz
 */
public interface WkTeileEditorListener extends EventListener {

    public void wkTeilAdded();

    public void wkTeilRemoved();

}
