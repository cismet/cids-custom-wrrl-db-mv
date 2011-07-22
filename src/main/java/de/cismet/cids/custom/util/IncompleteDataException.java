/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class IncompleteDataException extends Exception {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new IncompleteDataException object.
     */
    public IncompleteDataException() {
    }

    /**
     * Creates a new IncompleteDataException object.
     *
     * @param  message  DOCUMENT ME!
     */
    public IncompleteDataException(final String message) {
        super(message);
    }
}
