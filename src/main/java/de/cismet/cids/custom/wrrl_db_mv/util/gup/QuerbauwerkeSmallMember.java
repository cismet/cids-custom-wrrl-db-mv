/*
 * Copyright (C) 2011 cismet GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.tools.gui.jbands.SimpleSectionPanel;
import de.cismet.tools.gui.jbands.interfaces.Section;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author thorsten
 */
public class QuerbauwerkeSmallMember extends QuerbauwerkeMember implements Section{
    SimpleSectionPanel comp=new SimpleSectionPanel();
    public QuerbauwerkeSmallMember(ArrayList result) {
        super(result);
        comp.setBackground(getColor());
        comp.setToolTipText(name);
    }

    public QuerbauwerkeSmallMember() {
        super();
    }

    
    @Override
    public double getFrom() {
        return super.getMin();
    }

    @Override
    public double getTo() {
        return super.getMax();
        
    }

    @Override
    public JComponent getBandMemberComponent() {
        return comp;
    }
    
    
    
}
