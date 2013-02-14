/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupUnterhaltungsmassnahmeEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class MassnahmenBand extends LineBand implements BandSnappingPointProvider {

    //~ Instance fields --------------------------------------------------------

    private int measureType;
    private UnterhaltungsmassnahmeValidator uv = null;
    private Boolean invertSide = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     * @param  invertSide       DOCUMENT ME!
     */
    public MassnahmenBand(final String title, final String objectTableName, final Boolean invertSide) {
        this(1f, title, objectTableName, invertSide);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     * @param  invertSide       DOCUMENT ME!
     */
    public MassnahmenBand(final float heightWeight, final String objectTableName, final Boolean invertSide) {
        super(heightWeight, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
        this.invertSide = invertSide;
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     * @param  invertSide       DOCUMENT ME!
     */
    public MassnahmenBand(final float heightWeight,
            final String title,
            final String objectTableName,
            final Boolean invertSide) {
        super(heightWeight, title, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
        this.invertSide = invertSide;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final MassnahmenBandMember m = new MassnahmenBandMember(this, readOnly, uv, invertSide);

        return m;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measureType  DOCUMENT ME!
     */
    public void setMeasureType(final int measureType) {
        this.measureType = measureType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getMeasureType() {
        return measureType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getKompartiment() {
        if ((measureType == GupPlanungsabschnittEditor.GUP_UFER_LINKS)
                    || (measureType == GupPlanungsabschnittEditor.GUP_UFER_RECHTS)) {
            return GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UFER;
        } else if ((measureType == GupPlanungsabschnittEditor.GUP_UMFELD_LINKS)
                    || (measureType == GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS)) {
            return GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UMFELD;
        } else {
            return GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_SOHLE;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  uv  DOCUMENT ME!
     */
    public void setUnterhaltungsmassnahmeValidator(final UnterhaltungsmassnahmeValidator uv) {
        this.uv = uv;

        for (int i = 0; i < getNumberOfMembers(); ++i) {
            final MassnahmenBandMember bm = (MassnahmenBandMember)getMember(i);

            bm.setUnterhaltungsmassnahmeValidator(uv);
        }
    }
}
