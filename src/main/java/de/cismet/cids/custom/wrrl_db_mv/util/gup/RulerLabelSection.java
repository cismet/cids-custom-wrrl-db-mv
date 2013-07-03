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
public class RulerLabelSection extends SimpleSection {

    //~ Instance fields --------------------------------------------------------

    protected String title;
    protected RulerLabelSectionComponent comp;
    protected boolean big;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SimpleTextSection object.
     *
     * @param  title  DOCUMENT ME!
     * @param  from   DOCUMENT ME!
     * @param  to     DOCUMENT ME!
     */
    public RulerLabelSection(final String title,
            final double from,
            final double to) {
        super(from, to);
        this.title = title;
        comp = new RulerLabelSectionComponent(title);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return comp;
    }
}
