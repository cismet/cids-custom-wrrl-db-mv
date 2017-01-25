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
package de.cismet.cids.gaeb.types;

import java.util.ArrayList;
import java.util.List;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GaebContainer {

    //~ Instance fields --------------------------------------------------------

    private List<GaebLvItem> itemList = new ArrayList<GaebLvItem>();
    private String projectName;
    private boolean nebenangebot = true;
    private boolean bieterKommentar = true;
    private String description = "";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the itemList
     */
    public List<GaebLvItem> getItemList() {
        return itemList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  itemList  the itemList to set
     */
    public void setItemList(final List<GaebLvItem> itemList) {
        this.itemList = itemList;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectName  the projectName to set
     */
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the nebenangebot
     */
    public boolean isNebenangebot() {
        return nebenangebot;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nebenangebot  the nebenangebot to set
     */
    public void setNebenangebot(final boolean nebenangebot) {
        this.nebenangebot = nebenangebot;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the bieterKommentar
     */
    public boolean isBieterKommentar() {
        return bieterKommentar;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bieterKommentar  the bieterKommentar to set
     */
    public void setBieterKommentar(final boolean bieterKommentar) {
        this.bieterKommentar = bieterKommentar;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  description  the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
