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

import Sirius.navigator.ui.ComponentRegistry;

import org.openide.util.NbBundle;

import java.util.Collection;
import java.util.List;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UnterhaltungsmassnahmeValidator {

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

    private VermeidungsgruppeMitGeom[] verbreitungsraum;
    private Collection<CidsBean> schutzgebiete;
    private Collection<CidsBean> operativeZiele;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UnterhaltungsmaßnahmeValidator object.
     */
    public UnterhaltungsmassnahmeValidator() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the verbreitungsraum
     */
    public VermeidungsgruppeMitGeom[] getVerbreitungsraum() {
        return verbreitungsraum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  verbreitungsraum  the verbreitungsraum to set
     */
    public void setVerbreitungsraum(final VermeidungsgruppeMitGeom[] verbreitungsraum) {
        this.verbreitungsraum = verbreitungsraum;

        if ((operativeZiele != null) && (schutzgebiete != null)) {
            ComponentRegistry.getRegistry().getSearchResultsTree().repaint();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the schutzgebiete
     */
    public Collection<CidsBean> getSchutzgebiete() {
        return schutzgebiete;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schutzgebiete  the schutzgebiete to set
     */
    public void setSchutzgebiete(final Collection<CidsBean> schutzgebiete) {
        this.schutzgebiete = schutzgebiete;

        if ((verbreitungsraum != null) && (operativeZiele != null)) {
            ComponentRegistry.getRegistry().getSearchResultsTree().repaint();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the operativeZiele
     */
    public Collection<CidsBean> getOperativeZiele() {
        return operativeZiele;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  operativeZiele  the operativeZiele to set
     */
    public void setOperativeZiele(final Collection<CidsBean> operativeZiele) {
        this.operativeZiele = operativeZiele;

        if ((verbreitungsraum != null) && (schutzgebiete != null)) {
            ComponentRegistry.getRegistry().getSearchResultsTree().repaint();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isReady() {
        return !((operativeZiele == null) || (verbreitungsraum == null) || (schutzgebiete == null));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean    Gup_Unterhaltungsmassnahme
     * @param   errors  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ValidationResult validate(final CidsBean bean, final List<String> errors) {
        return validate(bean, (CidsBean)bean.getProperty("massnahme"), errors);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   abschnittBean  Gup_Unterhaltungsmassnahme
     * @param   massnArt       Gup_Massnahmenart
     * @param   errors         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ValidationResult validate(final CidsBean abschnittBean, final CidsBean massnArt, final List<String> errors) {
        while ((operativeZiele == null) || (verbreitungsraum == null) || (schutzgebiete == null)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }

        ValidationResult res = ValidationResult.ok;
        final double von = (Double)abschnittBean.getProperty("linie.von.wert");
        final double bis = (Double)abschnittBean.getProperty("linie.bis.wert");
        final Integer wo = (Integer)abschnittBean.getProperty("wo.id");

        if (massnArt == null) {
            return ValidationResult.error;
        }

        if (massnArt.getBeanCollectionProperty("gewerk") != null) {
            final List<CidsBean> opBeans = massnArt.getBeanCollectionProperty("gewerk.operative_ziele");
            for (final CidsBean mo : operativeZiele) {
                if (isLineInsideBean(mo, von, bis, wo)) {
                    if ((opBeans == null) || !opBeans.contains((CidsBean)mo.getProperty("operatives_ziel"))) {
                        final CidsBean oz = (CidsBean)mo.getProperty("operatives_ziel");
                        if (oz != null) {
                            errors.add(NbBundle.getMessage(
                                    UnterhaltungsmassnahmeValidator.class,
                                    "UnterhaltungsmassnahmeValidator.validate.invalidCareTarget",
                                    oz));
                        } else {
                            errors.add(NbBundle.getMessage(
                                    UnterhaltungsmassnahmeValidator.class,
                                    "UnterhaltungsmassnahmeValidator.validate.careTargetWithoutValue"));
                        }
                        res = ValidationResult.error;
                    }
                }
            }
        }

        final List<CidsBean> vrBeans = massnArt.getBeanCollectionProperty("vermeidungsgruppen");

        for (final VermeidungsgruppeMitGeom vg : verbreitungsraum) {
            if (isLineInsideBean(vg, von, bis, wo)) {
                if ((vrBeans == null) || !vrBeans.contains((CidsBean)vg.getVermeidungsgruppe())) {
                    final CidsBean v = (CidsBean)vg.getVermeidungsgruppe();
                    final Boolean warning = ((v == null) ? null
                                                         : (Boolean)vg.getVermeidungsgruppe().getProperty("warnung"));

                    if ((warning != null) && warning) {
                        errors.add(NbBundle.getMessage(
                                UnterhaltungsmassnahmeValidator.class,
                                "UnterhaltungsmassnahmeValidator.validate.preventionGroupYellow",
                                v));

                        if (!res.equals(ValidationResult.error)) {
                            res = ValidationResult.warning;
                        }
                    } else {
                        if (v != null) {
                            errors.add(NbBundle.getMessage(
                                    UnterhaltungsmassnahmeValidator.class,
                                    "UnterhaltungsmassnahmeValidator.validate.invalidPreventionGroup",
                                    v));
                        } else {
                            errors.add(NbBundle.getMessage(
                                    UnterhaltungsmassnahmeValidator.class,
                                    "UnterhaltungsmassnahmeValidator.validate.preventionGroupWithoutValue"));
                        }
                        res = ValidationResult.error;
                    }
                }
            }
        }

        int schuWo = wo;
        // Umlandmaßnahmen rechts/links nutzen bei den Schutzgebieten auch Ufer rechts/links
        if (schuWo == GupPlanungsabschnittEditor.GUP_UMFELD_LINKS) {
            schuWo = GupPlanungsabschnittEditor.GUP_UFER_LINKS;
        } else if (wo == GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS) {
            schuWo = GupPlanungsabschnittEditor.GUP_UFER_RECHTS;
        }

        for (final CidsBean mo : schutzgebiete) {
            if (isLineInsideBean(mo, von, bis, schuWo)) {
                errors.add(NbBundle.getMessage(
                        UnterhaltungsmassnahmeValidator.class,
                        "UnterhaltungsmassnahmeValidator.validate.measureInNatureReserve"));
                res = ValidationResult.error;
            }
        }

        final Boolean alAnf = (Boolean)massnArt.getProperty("erfuellt_al_anf");

        if ((alAnf == null) || !alAnf) {
            errors.add(NbBundle.getMessage(
                    UnterhaltungsmassnahmeValidator.class,
                    "UnterhaltungsmassnahmeValidator.validate.notAlAnf"));
            res = ValidationResult.error;
        }

        return res;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   moBean     mo DOCUMENT ME!
     * @param   fromBean   DOCUMENT ME!
     * @param   untilBean  DOCUMENT ME!
     * @param   woId       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isLineInsideBean(final CidsBean moBean,
            final double fromBean,
            final double untilBean,
            final int woId) {
//        final CidsBean moBean = mo.getBean();
        final double von = (Double)moBean.getProperty("linie.von.wert");
        final double bis = (Double)moBean.getProperty("linie.bis.wert");
        final Integer wo = (Integer)moBean.getProperty("wo.id");

        if (wo == null) {
            return false;
        }

        return (wo == woId)
                    && (((von < fromBean) && (bis > fromBean)) || ((von < untilBean) && (bis > untilBean))
                        || ((von >= fromBean) && (bis <= untilBean)));
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
    private boolean isLineInsideBean(final VermeidungsgruppeMitGeom mo,
            final double fromBean,
            final double untilBean,
            final int woId) {
        return isLineInsideBean(mo.getGeschuetzteArt(), fromBean, untilBean, woId);
    }
}
