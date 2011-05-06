/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 therter
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


/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjekteFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Methods ----------------------------------------------------------------

    @Override
    public void assign() {
        // No operation
    }

    @Override
    public Paint getFillingStyle() {
        final Color c = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        return c;
    }

    @Override
    public Paint getLinePaint() {
        return Color.RED;
    }

    @Override
    public float getTransparency() {
        return 1.0f;
    }
}
