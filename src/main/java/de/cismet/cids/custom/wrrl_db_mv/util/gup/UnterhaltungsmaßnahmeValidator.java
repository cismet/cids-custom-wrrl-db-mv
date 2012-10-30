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

import Sirius.server.middleware.types.MetaObject;

import org.openide.util.Exceptions;

import java.util.List;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UnterhaltungsmaßnahmeValidator {

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static enum ValidationResult {

        //~ Enum constants -----------------------------------------------------

        ok, warning, error
    }

    //~ Instance fields --------------------------------------------------------

    private MetaObject[] verbreitungsraum;
    private MetaObject[] schutzgebiete;
    private MetaObject[] operativeZiele;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UnterhaltungsmaßnahmeValidator object.
     */
    public UnterhaltungsmaßnahmeValidator() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the verbreitungsraum
     */
    public MetaObject[] getVerbreitungsraum() {
        return verbreitungsraum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  verbreitungsraum  the verbreitungsraum to set
     */
    public void setVerbreitungsraum(final MetaObject[] verbreitungsraum) {
        this.verbreitungsraum = verbreitungsraum;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the schutzgebiete
     */
    public MetaObject[] getSchutzgebiete() {
        return schutzgebiete;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schutzgebiete  the schutzgebiete to set
     */
    public void setSchutzgebiete(final MetaObject[] schutzgebiete) {
        this.schutzgebiete = schutzgebiete;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the operativeZiele
     */
    public MetaObject[] getOperativeZiele() {
        return operativeZiele;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  operativeZiele  the operativeZiele to set
     */
    public void setOperativeZiele(final MetaObject[] operativeZiele) {
        this.operativeZiele = operativeZiele;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ValidationResult validate(final CidsBean bean) {
        while ((operativeZiele == null) || (verbreitungsraum == null) || (schutzgebiete == null)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }

        ValidationResult res = ValidationResult.ok;
        final double von = (Double)bean.getProperty("linie.von.wert");
        final double bis = (Double)bean.getProperty("linie.bis.wert");
        final int wo = (Integer)bean.getProperty("wo.id");
        final CidsBean massn = (CidsBean)bean.getProperty("massnahme");

        if (massn == null) {
            return ValidationResult.error;
        }

        final List<CidsBean> opBeans = massn.getBeanCollectionProperty("operative_ziele");

        for (final MetaObject mo : operativeZiele) {
            if (isLineInsideBean(mo, von, bis, wo)) {
                if ((opBeans == null) || !opBeans.contains(mo.getBean())) {
                    res = ValidationResult.error;
                }
            }
        }

        final List<CidsBean> vrBeans = massn.getBeanCollectionProperty("vermeidungsgruppen");

        for (final MetaObject mo : verbreitungsraum) {
            if (isLineInsideBean(mo, von, bis, wo)) {
                if ((vrBeans == null) || !vrBeans.contains(mo.getBean())) {
                    res = ValidationResult.error;
                }
            }
        }

        for (final MetaObject mo : schutzgebiete) {
            if (isLineInsideBean(mo, von, bis, wo)) {
                res = ValidationResult.error;
            }
        }

        return res;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mo         DOCUMENT ME!
     * @param   fromBean   DOCUMENT ME!
     * @param   untilBean  DOCUMENT ME!
     * @param   woId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isLineInsideBean(final MetaObject mo,
            final double fromBean,
            final double untilBean,
            final int woId) {
        final CidsBean moBean = mo.getBean();
        final double von = (Double)moBean.getProperty("linie.von.wert");
        final double bis = (Double)moBean.getProperty("linie.bis.wert");
        final int wo = (Integer)moBean.getProperty("wo.id");

        return (wo == woId)
                    && (((von < fromBean) && (bis > fromBean)) || ((von < untilBean) && (bis > untilBean))
                        || ((von >= fromBean) && (bis <= untilBean)));
    }
}
