/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class ValidationException extends Exception {

    //~ Instance fields --------------------------------------------------------

    private final transient boolean exception;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new instance of <code>ValidationException</code> without detail message.
     */
    public ValidationException() {
        this(null, null, false);
    }

    /**
     * Constructs an instance of <code>ValidationException</code> with the specified detail message.
     *
     * @param  msg  the detail message.
     */
    public ValidationException(final String msg) {
        this(msg, null, false);
    }

    /**
     * Constructs an instance of <code>ValidationException</code> with the specified detail message and the specified
     * cause.
     *
     * @param  msg    the detail message.
     * @param  cause  the exception cause
     */
    public ValidationException(final String msg, final Throwable cause) {
        this(msg, cause, false);
    }

    /**
     * Creates a new ValidationException object.
     *
     * @param  message    DOCUMENT ME!
     * @param  exception  DOCUMENT ME!
     */
    public ValidationException(final String message, final boolean exception) {
        this(message, null, exception);
    }

    /**
     * Creates a new ValidationException object.
     *
     * @param  message    DOCUMENT ME!
     * @param  cause      DOCUMENT ME!
     * @param  exception  DOCUMENT ME!
     */
    public ValidationException(final String message, final Throwable cause, final boolean exception) {
        super(message, cause);
        this.exception = exception;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Whether this validation error is cause because the fact that the kartierabschnitt is an exception.
     *
     * @return  DOCUMENT ME!
     */
    public boolean isException() {
        return exception;
    }
}
