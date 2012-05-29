/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;

import de.cismet.tools.gui.jbands.DefaultBand;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class NaturschutzBand extends DefaultBand {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NaturschutzBand object.
     */
    public NaturschutzBand() {
        this(1f);
    }

    /**
     * Creates a new NaturschutzBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public NaturschutzBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new NaturschutzBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public NaturschutzBand(final float heightWeight) {
        super(heightWeight, "Naturschutz");
    }

    /**
     * Creates a new NaturschutzBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public NaturschutzBand(final float heightWeight, final String title) {
        super(heightWeight, title);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void addMembersFromQueryResult(final ArrayList<ArrayList> inputResulSet) {
        if (inputResulSet != null) {
            for (final ArrayList zeile : inputResulSet) {
                final NaturschutzBandMember qbm = new NaturschutzBandMember(zeile);
                addMember(qbm);
            }
        }
    }
}
