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

import org.openide.util.NbBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class PflegezieleValidator {

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

    private Collection<CidsBean> entwicklungsziele;
    private Collection<CidsBean> situationstypen;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UnterhaltungsmaßnahmeValidator object.
     */
    public PflegezieleValidator() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the entwicklungsziele
     */
    public Collection<CidsBean> getEntwicklungsziele() {
        return entwicklungsziele;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entwicklungsziele  the entwicklungsziele to set
     */
    public void setEntwicklungsziele(final Collection<CidsBean> entwicklungsziele) {
        this.entwicklungsziele = entwicklungsziele;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the situationstypen
     */
    public Collection<CidsBean> getSituationstypen() {
        return situationstypen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  situationstypen  the situationstypen to set
     */
    public void setSituationstypen(final Collection<CidsBean> situationstypen) {
        this.situationstypen = situationstypen;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isReady() {
        return !((situationstypen == null) || (entwicklungsziele == null));
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
        return validate(bean, (CidsBean)bean.getProperty("operatives_ziel"), errors);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   abschnittBean  Gup_Unterhaltungsmassnahme
     * @param   pflegeziel     Gup_Massnahmenart
     * @param   errors         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ValidationResult validate(final CidsBean abschnittBean,
            final CidsBean pflegeziel,
            final List<String> errors) {
        while (!isReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }

        ValidationResult res = ValidationResult.ok;
        final double von = (Double)abschnittBean.getProperty("linie.von.wert");
        final double bis = (Double)abschnittBean.getProperty("linie.bis.wert");

        if (pflegeziel == null) {
            return ValidationResult.error;
        }

        final List<CidsBean> entBeans = pflegeziel.getBeanCollectionProperty("entwicklungsziele");

        for (final CidsBean mo : entwicklungsziele) {
            if (isLineInsideBean(mo, von, bis)) {
                if ((entBeans == null) || !entBeans.contains((CidsBean)mo.getProperty("name_bezeichnung"))) {
                    final CidsBean ent = (CidsBean)mo.getProperty("name_bezeichnung");
                    if (ent != null) {
                        errors.add(NbBundle.getMessage(
                                PflegezieleValidator.class,
                                "PflegezieleValidator.validate.invalidDevelopmentTarget",
                                ent));
                    } else {
                        errors.add(NbBundle.getMessage(
                                PflegezieleValidator.class,
                                "PflegezieleValidator.validate.DevelopmentTargetWithoutValue"));
                    }
                    res = ValidationResult.error;
                }
            }
        }

        final List<CidsBean> ueBeans = pflegeziel.getBeanCollectionProperty("unterhaltungserfordernisse");

        for (final CidsBean mo : situationstypen) {
            if (isLineInsideBean(mo, von, bis)) {
                if ((ueBeans == null) || !ueBeans.contains((CidsBean)mo.getProperty("name_beschreibung"))) {
                    final CidsBean ue = (CidsBean)mo.getProperty("name_beschreibung");
                    if (ue != null) {
                        errors.add(NbBundle.getMessage(
                                PflegezieleValidator.class,
                                "PflegezieleValidator.validate.invalidSituationType",
                                ue));
                    } else {
                        errors.add(NbBundle.getMessage(
                                PflegezieleValidator.class,
                                "PflegezieleValidator.validate.SituationTypeWithoutValue"));
                    }
                    res = ValidationResult.error;
                }
            }
        }

        return res;
    }

    /**
     * This method blocks until the validator is initialised.
     *
     * @param   abschnittBean  DOCUMENT ME!
     *
     * @return  all Situationstyp objects, which intersects the given Pflegeziel
     */
    public List<CidsBean> getSituationstypIntersectingPflegezielAbschnitt(final CidsBean abschnittBean) {
        while (!isReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }
        final List<CidsBean> situationTypList = new ArrayList<CidsBean>();
        final double von = (Double)abschnittBean.getProperty("linie.von.wert");
        final double bis = (Double)abschnittBean.getProperty("linie.bis.wert");

        for (final CidsBean mo : situationstypen) {
            if (isLineInsideBean(mo, von, bis)) {
                final CidsBean bean = (CidsBean)mo.getProperty("name_beschreibung");
                if (bean != null) {
                    situationTypList.add(bean);
                }
            }
        }

        return situationTypList;
    }

    /**
     * This method blocks until the validator is initialised.
     *
     * @param   abschnittBean  a bean of type
     *
     * @return  all Entwicklungsziel objects, which intersects the given Pflegeziel
     */
    public List<CidsBean> getEntwicklungszielIntersectingPflegezielAbschnitt(final CidsBean abschnittBean) {
        while (!isReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }
        final List<CidsBean> EntwicklungszielList = new ArrayList<CidsBean>();
        final double von = (Double)abschnittBean.getProperty("linie.von.wert");
        final double bis = (Double)abschnittBean.getProperty("linie.bis.wert");

        for (final CidsBean mo : entwicklungsziele) {
            if (isLineInsideBean(mo, von, bis)) {
                final CidsBean bean = (CidsBean)mo.getProperty("name_bezeichnung");
                if (bean != null) {
                    EntwicklungszielList.add(bean);
                }
            }
        }

        return EntwicklungszielList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   moBean     mo DOCUMENT ME!
     * @param   fromBean   DOCUMENT ME!
     * @param   untilBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isLineInsideBean(final CidsBean moBean,
            final double fromBean,
            final double untilBean) {
        final double von = (Double)moBean.getProperty("linie.von.wert");
        final double bis = (Double)moBean.getProperty("linie.bis.wert");
        final long vonRounded = Math.min(Math.round(von), Math.round(bis));
        final long bisRounded = Math.max(Math.round(von), Math.round(bis));
        final long fromBeanRounded = Math.min(Math.round(fromBean), Math.round(untilBean));
        final long untilBeanRounded = Math.max(Math.round(fromBean), Math.round(untilBean));

        return (((vonRounded < fromBeanRounded) && (bisRounded > fromBeanRounded))
                        || ((vonRounded < untilBeanRounded) && (bisRounded > untilBeanRounded))
                        || ((vonRounded >= fromBeanRounded) && (bisRounded <= untilBeanRounded)));
    }
}
