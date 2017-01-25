/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.lookupoptions.AbstractOptionsCategory;
import de.cismet.lookupoptions.OptionsCategory;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = OptionsCategory.class)
public class WrrlOptionsCategory extends AbstractOptionsCategory {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getName() {
        return org.openide.util.NbBundle.getMessage(WrrlOptionsCategory.class, "WrrlOptionsCategory.name"); // NOI18N
    }

    @Override
    public Icon getIcon() {
        final Image image = ImageUtilities.loadImage(
                "de/cismet/cids/custom/wrrl_db_mv/util/icon-settingsthree-gears.png");
        if (image != null) {
            return new ImageIcon(image);
        } else {
            return null;
        }
    }

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public String getTooltip() {
        return org.openide.util.NbBundle.getMessage(WrrlOptionsCategory.class, "WrrlOptionsCategory.tooltip"); // NOI18N
    }
}
