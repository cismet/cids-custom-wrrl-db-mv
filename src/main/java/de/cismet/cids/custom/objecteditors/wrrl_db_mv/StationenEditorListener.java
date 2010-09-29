package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.EventListener;

/**
 *
 * @author jruiz
 */
public interface StationenEditorListener extends EventListener {

    public void stationAdded();

    public void stationRemoved();

}
