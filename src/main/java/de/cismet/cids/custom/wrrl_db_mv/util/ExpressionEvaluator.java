/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilationFailedException;

/**
 * This class can evaluate simple math expressions.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ExpressionEvaluator {

    //~ Instance fields --------------------------------------------------------

    private char[] allowedCharacters = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '.',
            ' ',
            '(',
            ')',
            '+',
            '*',
            '-',
            '/',
            '^'
        };

    private final GroovyShell shell = new GroovyShell();

    //~ Methods ----------------------------------------------------------------

    /**
     * evaluates the given expression.
     *
     * @param   expression  DOCUMENT ME!
     *
     * @return  the result of the expression or null if the expression is not valid
     *
     * @throws  CompilationFailedException  DOCUMENT ME!
     *
     * @see     isValidExpression
     */
    public Double eval(final String expression) throws CompilationFailedException {
        if (!isValidExpression(expression)) {
            return null;
        }

        final Object result = shell.evaluate(expression);

        if (result instanceof Number) {
            return ((Number)result).doubleValue();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   expression  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isValidExpression(final String expression) {
        for (int i = 0; i < expression.length(); ++i) {
            if (!isValid(expression.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   c  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isValid(final char c) {
        for (final char tmp : allowedCharacters) {
            if (c == tmp) {
                return true;
            }
        }

        return false;
    }
}
