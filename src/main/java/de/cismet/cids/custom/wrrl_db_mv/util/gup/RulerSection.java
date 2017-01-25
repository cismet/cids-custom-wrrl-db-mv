/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import javax.swing.JComponent;

import de.cismet.tools.gui.jbands.SimpleSection;
import de.cismet.tools.gui.jbands.TextSectionComponent;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class RulerSection extends SimpleSection {

    //~ Instance fields --------------------------------------------------------

    protected String title;
    protected RulerSectionComponent comp;
    protected boolean big;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SimpleTextSection object.
     *
     * @param  title  DOCUMENT ME!
     * @param  from   DOCUMENT ME!
     * @param  to     DOCUMENT ME!
     * @param  big    DOCUMENT ME!
     */
    public RulerSection(final String title,
            final double from,
            final double to,
            final boolean big) {
        super(from, to);
        this.title = title;
        this.big = big;
        comp = new RulerSectionComponent(big);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return comp;
    }
}
