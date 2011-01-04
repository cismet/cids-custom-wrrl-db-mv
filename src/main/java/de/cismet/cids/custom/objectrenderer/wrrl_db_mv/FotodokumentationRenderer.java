/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jweintraut
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
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import javax.swing.JComponent;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FotodokumentationEditor;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class FotodokumentationRenderer extends FotodokumentationEditor implements TitleComponentProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FotodokumentationRenderer object.
     */
    public FotodokumentationRenderer() {
        super(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getTitleComponent() {
        return super.getPanTitle();
    }
}
