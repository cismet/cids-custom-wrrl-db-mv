/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jweintraut
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import java.awt.Color;
import java.awt.Paint;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class GeoHintFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color GEOHINT_COLOR = new Color(38, 92, 255);

    //~ Methods ----------------------------------------------------------------

    @Override
    public Paint getLinePaint(final CidsFeature subFeature) {
        return GEOHINT_COLOR;
    }

    @Override
    public Paint getFillingStyle() {
        return GEOHINT_COLOR;
    }

    @Override
    public void assign() {
        // NOP
    }
}
