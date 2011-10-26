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
public class AnwohnerBand extends DefaultBand {

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum Seite {

        //~ Enum constants -----------------------------------------------------

        RECHTS, LINKS, BEIDE
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AnwohnerBand object.
     */
    public AnwohnerBand() {
        this("Anwohner");
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public AnwohnerBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public AnwohnerBand(final float heightWeight) {
        this(heightWeight, "");
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public AnwohnerBand(final float heightWeight, final String title) {
        super(heightWeight, title);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     * @param  s              DOCUMENT ME!
     */
    public void addAnwohnerFromQueryResult(final ArrayList<ArrayList> inputResulSet, final Seite s) {
        assert (s != null);
        for (final ArrayList zeile : inputResulSet) {
            if (s == Seite.RECHTS) {
                if (zeile.get(1).toString().equalsIgnoreCase(Seite.RECHTS.toString())
                            || zeile.get(1).toString().equalsIgnoreCase(Seite.BEIDE.toString())) {
                    final AnwohnerMember am = new AnwohnerMember(zeile);
                    addMember(am);
                }
            } else if (s == Seite.LINKS) {
                if (zeile.get(1).toString().equalsIgnoreCase(Seite.LINKS.toString())
                            || zeile.get(1).toString().equalsIgnoreCase(Seite.BEIDE.toString())) {
                    final AnwohnerMember am = new AnwohnerMember(zeile);
                    addMember(am);
                }
            } else {
                final AnwohnerMember am = new AnwohnerMember(zeile);
                addMember(am);
            }
        }
    }
}
