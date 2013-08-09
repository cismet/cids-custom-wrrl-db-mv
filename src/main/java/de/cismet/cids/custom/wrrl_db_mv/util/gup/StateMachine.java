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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class StateMachine {

    //~ Instance fields --------------------------------------------------------

    private int[][] adjacencyMatrix;
    private int state = -1;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StateMachine object.
     *
     * @param  adjacencyMatrix  DOCUMENT ME!
     */
    public StateMachine(final int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  newState  DOCUMENT ME!
     */
    public void forceState(final int newState) {
        this.state = newState;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getState() {
        return state;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   newState  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public void setState(final int newState) throws IllegalArgumentException {
        if ((state == -1) || (adjacencyMatrix[state][newState] != 0)) {
            this.state = newState;
        } else {
            throw new IllegalArgumentException("The given state cannot be reached from the current state");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   newState  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getRoleForState(final int newState) {
        return adjacencyMatrix[state][newState];
    }

    /**
     * DOCUMENT ME!
     *
     * @param   newState  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isStatePossible(final int newState) {
        return adjacencyMatrix[state][newState] != 0;
    }
}
