/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.fgsk.eval;

import java.util.ArrayList;

import de.cismet.cids.custom.util.IncompleteDataException;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GewaesserCalc extends FgSkCalculations {

    //~ Methods ----------------------------------------------------------------

    @Override
    public void recalculate_points(final CidsBean kartierabschnitt) throws IncompleteDataException {
        final CidsBean gewaessertype = (CidsBean)kartierabschnitt.getProperty("gewaessertype_id");
        final CidsBean flaechennutzungLinksId = (CidsBean)kartierabschnitt.getProperty("flaechennutzung_links_id");
        final CidsBean flaechennutzungRechtsId = (CidsBean)kartierabschnitt.getProperty("flaechennutzung_rechts_id");
        final CidsBean gewaesserrandstreifenLinksId = (CidsBean)kartierabschnitt.getProperty(
                "gewaesserrandstreifen_links_id");
        final CidsBean gewaesserrandstreifenRechtsId = (CidsBean)kartierabschnitt.getProperty(
                "gewaesserrandstreifen_rechts_id");

        if ((gewaessertype == null) || (flaechennutzungLinksId == null) || (flaechennutzungRechtsId == null)
                    || (gewaesserrandstreifenLinksId == null)
                    || (gewaesserrandstreifenRechtsId == null)) {
            throw new IncompleteDataException();
        }

        final int ratingRandLinks = getBewertung(
                "select bewertung from fgsk.gewaesserrandstreifen_auswertung where id_gewaessertyp = "
                        + getBewId(gewaessertype)
                        + " and id_gewaesserrandstreifen = "
                        + getBewId(gewaesserrandstreifenLinksId));
        final int ratingRandRechts = getBewertung(
                "select bewertung from fgsk.gewaesserrandstreifen_auswertung where id_gewaessertyp = "
                        + getBewId(gewaessertype)
                        + " and id_gewaesserrandstreifen = "
                        + getBewId(gewaesserrandstreifenRechtsId));
        final int ratingFlaLinks = getBewertung(
                "select bewertung from fgsk.gewaesserrandstreifen_auswertung where id_gewaessertyp = "
                        + getBewId(gewaessertype)
                        + " and id_flaechennutzung = "
                        + getBewId(flaechennutzungLinksId));
        final int ratingFlaRechts = getBewertung(
                "select bewertung from fgsk.gewaesserrandstreifen_auswertung where id_gewaessertyp = "
                        + getBewId(gewaessertype)
                        + " and id_flaechennutzung = "
                        + getBewId(flaechennutzungRechtsId));

        ArrayList<String> schUmfeldstrukturen = new ArrayList<String>();
        for (final String[] tmp : SCHAE_UMFELD_LINKS) {
            final Object o = kartierabschnitt.getProperty(tmp[1]);
            if ((o != null) && !o.toString().equals("0")) {
                schUmfeldstrukturen.add(tmp[0]);
            }
        }
        double malusUmfeldstrukturLinks = 0;

        if (schUmfeldstrukturen.size() > 0) {
            final String fromClause = createUnion(
                    SCHAE_UMFELD_QUERY,
                    schUmfeldstrukturen.toArray(new String[schUmfeldstrukturen.size()]));
            malusUmfeldstrukturLinks = getBewertung("select sum(bewertung) from (" + fromClause + ") a;");
        }

        if (((getBewId(gewaessertype) == 11) || (getBewId(gewaessertype) == 12)) && (malusUmfeldstrukturLinks < -1)) {
            malusUmfeldstrukturLinks = -1;
        } else if (malusUmfeldstrukturLinks < -3) {
            malusUmfeldstrukturLinks = -3;
        }

        schUmfeldstrukturen = new ArrayList<String>();
        for (final String[] tmp : SCHAE_UMFELD_RECHTS) {
            final Object o = kartierabschnitt.getProperty(tmp[1]);
            if ((o != null) && !o.toString().equals("0")) {
                schUmfeldstrukturen.add(tmp[0]);
            }
        }
        double malusUmfeldstrukturRechts = 0;

        if (schUmfeldstrukturen.size() > 0) {
            final String fromClause = createUnion(
                    SCHAE_UMFELD_QUERY,
                    schUmfeldstrukturen.toArray(new String[schUmfeldstrukturen.size()]));
            malusUmfeldstrukturRechts = getBewertung("select sum(bewertung) from (" + fromClause + ") a;");
        }

        if (((getBewId(gewaessertype) == 11) || (getBewId(gewaessertype) == 12)) && (malusUmfeldstrukturRechts < -1)) {
            malusUmfeldstrukturRechts = -1;
        } else if (malusUmfeldstrukturRechts < -3) {
            malusUmfeldstrukturRechts = -3;
        }

        double ratingTotal = 0.0;
        int numRating = 0;

        if (ratingRandLinks > 0) {
            ratingTotal += ratingRandLinks;
            ++numRating;
        }

        if (ratingRandRechts > 0) {
            ratingTotal += ratingRandRechts;
            ++numRating;
        }

        if (ratingFlaLinks > 0) {
            ratingTotal += ratingFlaLinks;
            ++numRating;
        }

        if (ratingFlaRechts > 0) {
            ratingTotal += ratingFlaRechts;
            ++numRating;
        }

        if (malusUmfeldstrukturLinks < 0) {
            ratingTotal += malusUmfeldstrukturLinks;
        }

        if (malusUmfeldstrukturRechts < 0) {
            ratingTotal += malusUmfeldstrukturRechts;
        }

        try {
            kartierabschnitt.setProperty("gewaesserumfeld_summe_punktzahl", ratingTotal);
            kartierabschnitt.setProperty("gewaesserumfeld_summe_kriterien", numRating);
        } catch (final Exception e) {
            LOG.error("Error while setting the calculated properties.", e);
        }

        // Call Gesamtbewertungs-function
    }
}
