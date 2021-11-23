/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.server.middleware.types.MetaClass;

import java.awt.Component;
import java.awt.event.MouseEvent;

import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * A table cell editor that shows a bindable combobox.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class DefaultBindableScrollableComboboxCellEditor extends AbstractCellEditor implements TableCellEditor {

    //~ Instance fields --------------------------------------------------------

    private final ScrollableComboBox comboBox;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultBindableComboboxCellEditor object.
     *
     * @param  metaClass  DOCUMENT ME!
     */
    public DefaultBindableScrollableComboboxCellEditor(final MetaClass metaClass) {
        comboBox = new ScrollableComboBox(metaClass);
    }

    /**
     * Creates a new DefaultBindableComboboxCellEditor object.
     *
     * @param  metaClass  DOCUMENT ME!
     * @param  nullable   DOCUMENT ME!
     */
    public DefaultBindableScrollableComboboxCellEditor(final MetaClass metaClass, final boolean nullable) {
        comboBox = new ScrollableComboBox(metaClass, nullable, false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean isCellEditable(final EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= 2;
        }
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table,
            final Object value,
            final boolean isSelected,
            final int row,
            final int column) {
        comboBox.setSelectedItem(value);

        return comboBox;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ScrollableComboBox getComboBox() {
        return comboBox;
    }
}
