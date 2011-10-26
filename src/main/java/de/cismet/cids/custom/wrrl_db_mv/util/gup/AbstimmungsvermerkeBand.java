/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.MinimumHeightBand;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class AbstimmungsvermerkeBand extends MinimumHeightBand implements CidsBeanCollectionStore {

    //~ Instance fields --------------------------------------------------------

    Collection<CidsBean> beans = new ArrayList<CidsBean>();
    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstimmungsvermerkeBand object.
     */
    public AbstimmungsvermerkeBand() {
        super("Abstimmungsvermerke");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return beans;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        this.beans = beans;
        if (beans != null) {
            for (final CidsBean b : beans) {
                final AbstimmungsvermerkMember avm = new AbstimmungsvermerkMember();
                try {
                    avm.setCidsBean(b);
                    addMember(avm);
                } catch (Exception e) {
                    // dann halt nich
                    log.error("Fehler beim Erzeugen von AbstimmungsvermerkMember", e);
                }
            }
        }
    }
}
