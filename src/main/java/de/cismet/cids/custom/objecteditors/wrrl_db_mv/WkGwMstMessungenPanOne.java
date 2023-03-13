/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.EventQueue;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JTextField;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkGwMstMessungenPanOne extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WkGwMstMessungenPanOne.class);
    private static final String PROP_WERT = "wert_";
    private static final String PROP_EINHEIT = "einh_";
    private static final String PROP_UNTERB = "unterbg_";
    private static final String PROP_GK = "gk_";
    private static final String PROP_SW = "sw_";

    private static final String PROP_NITRAT = "nitrat";
    private static final String PROP_ARSEN = "arsen";
    private static final String PROP_CADMIUM = "cadmium";
    private static final String PROP_BLEI = "blei";
    private static final String PROP_QUECKSILBER = "quecksilber";
    private static final String PROP_AMMONIUM = "ammonium";
    private static final String PROP_CHLORID = "chlorid";
    private static final String PROP_NITRIT = "nitrit";
    private static final String PROP_ORTHO = "orthophosphat_p";
    private static final String PROP_SULFAT = "sulfat";
    private static final String PROP_SUM_TRI_TE = "sum_tri_tetrachlorethen";

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private enum PropertyType {

        //~ Enum constants -----------------------------------------------------

        MST, SW, GK, EINHEIT, HINWEIS, HI_TOOLTIP
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean[] cidsBeans;
    private JTextField[] textFields;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblAs5;
    private javax.swing.JLabel lblCd5;
    private javax.swing.JLabel lblCl;
    private javax.swing.JLabel lblGk;
    private javax.swing.JLabel lblGk1;
    private javax.swing.JLabel lblGk2;
    private javax.swing.JLabel lblHg5;
    private javax.swing.JLabel lblMst1Header;
    private javax.swing.JLabel lblMst2Header;
    private javax.swing.JLabel lblNh4;
    private javax.swing.JLabel lblNitrit;
    private javax.swing.JLabel lblNo3;
    private javax.swing.JLabel lblPb5;
    private javax.swing.JLabel lblPhyChem1;
    private javax.swing.JLabel lblPo4;
    private javax.swing.JLabel lblSchwellwertHeader;
    private javax.swing.JLabel lblSo42;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblSumTriTe;
    private javax.swing.JTextField txtAs5Ei;
    private javax.swing.JTextField txtAs5Gk;
    private javax.swing.JTextField txtAs5Hi;
    private javax.swing.JTextField txtAs5Mst1;
    private javax.swing.JTextField txtAs5Mst2;
    private javax.swing.JTextField txtAs5sw;
    private javax.swing.JTextField txtCd5Ei;
    private javax.swing.JTextField txtCd5Gk;
    private javax.swing.JTextField txtCd5Hi;
    private javax.swing.JTextField txtCd5Mst1;
    private javax.swing.JTextField txtCd5Mst2;
    private javax.swing.JTextField txtCd5sw;
    private javax.swing.JTextField txtClEi;
    private javax.swing.JTextField txtClGk;
    private javax.swing.JTextField txtClHi;
    private javax.swing.JTextField txtClMst1;
    private javax.swing.JTextField txtClMst2;
    private javax.swing.JTextField txtClsw;
    private javax.swing.JTextField txtHg5Ei;
    private javax.swing.JTextField txtHg5Gk;
    private javax.swing.JTextField txtHg5Hi;
    private javax.swing.JTextField txtHg5Mst1;
    private javax.swing.JTextField txtHg5Mst2;
    private javax.swing.JTextField txtHg5sw;
    private javax.swing.JTextField txtNh4Ei;
    private javax.swing.JTextField txtNh4Gk;
    private javax.swing.JTextField txtNh4Hi;
    private javax.swing.JTextField txtNh4Mst1;
    private javax.swing.JTextField txtNh4Mst2;
    private javax.swing.JTextField txtNh4sw;
    private javax.swing.JTextField txtNitritEi;
    private javax.swing.JTextField txtNitritGk;
    private javax.swing.JTextField txtNitritHi;
    private javax.swing.JTextField txtNitritMst1;
    private javax.swing.JTextField txtNitritMst2;
    private javax.swing.JTextField txtNitritsw;
    private javax.swing.JTextField txtNo3Ei;
    private javax.swing.JTextField txtNo3Gk;
    private javax.swing.JTextField txtNo3Hi;
    private javax.swing.JTextField txtNo3Mst1;
    private javax.swing.JTextField txtNo3Mst2;
    private javax.swing.JTextField txtNo3sw;
    private javax.swing.JTextField txtPb5Ei;
    private javax.swing.JTextField txtPb5Gk;
    private javax.swing.JTextField txtPb5Hi;
    private javax.swing.JTextField txtPb5Mst1;
    private javax.swing.JTextField txtPb5Mst2;
    private javax.swing.JTextField txtPb5sw;
    private javax.swing.JTextField txtPo4Ei;
    private javax.swing.JTextField txtPo4Gk;
    private javax.swing.JTextField txtPo4Hi;
    private javax.swing.JTextField txtPo4Mst1;
    private javax.swing.JTextField txtPo4Mst2;
    private javax.swing.JTextField txtPo4sw;
    private javax.swing.JTextField txtSo42Ei;
    private javax.swing.JTextField txtSo42Gk;
    private javax.swing.JTextField txtSo42Hi;
    private javax.swing.JTextField txtSo42Mst1;
    private javax.swing.JTextField txtSo42Mst2;
    private javax.swing.JTextField txtSo42sw;
    private javax.swing.JTextField txtSumTriTeEi;
    private javax.swing.JTextField txtSumTriTeGk;
    private javax.swing.JTextField txtSumTriTeHi;
    private javax.swing.JTextField txtSumTriTeMst1;
    private javax.swing.JTextField txtSumTriTeMst2;
    private javax.swing.JTextField txtSumTriTesw;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ChemieMstMessungenPanOne object.
     */
    public WkGwMstMessungenPanOne() {
        this(false);
    }

    /**
     * Creates new form ChemieMstMessungenPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkGwMstMessungenPanOne(final boolean readOnly) {
        initComponents();

        textFields = new JTextField[66];
        textFields[0] = txtAs5Gk;
        textFields[1] = txtAs5Mst1;
        textFields[2] = txtAs5sw;
        textFields[3] = txtCd5Gk;
        textFields[4] = txtCd5Mst1;
        textFields[5] = txtCd5sw;
        textFields[6] = txtClGk;
        textFields[7] = txtClMst1;
        textFields[8] = txtClsw;
        textFields[9] = txtHg5Gk;
        textFields[10] = txtHg5Mst1;
        textFields[11] = txtHg5sw;

        textFields[12] = txtNh4Gk;
        textFields[13] = txtNh4Mst1;
        textFields[14] = txtNh4sw;
        textFields[15] = txtNitritGk;
        textFields[16] = txtNitritMst1;
        textFields[17] = txtNitritsw;
        textFields[18] = txtNo3Gk;
        textFields[19] = txtNo3Mst1;
        textFields[20] = txtNo3sw;

        textFields[21] = txtPb5Gk;
        textFields[22] = txtPb5Mst1;
        textFields[23] = txtPb5sw;
        textFields[24] = txtPo4Gk;
        textFields[25] = txtPo4Mst1;
        textFields[26] = txtPo4sw;
        textFields[27] = txtSo42Gk;
        textFields[28] = txtSo42Mst1;
        textFields[29] = txtSo42sw;

        textFields[30] = txtSumTriTeGk;
        textFields[31] = txtSumTriTeMst1;
        textFields[32] = txtSumTriTesw;
        textFields[33] = txtAs5Mst2;
        textFields[34] = txtCd5Mst2;
        textFields[35] = txtClMst2;
        textFields[36] = txtHg5Mst2;
        textFields[37] = txtNh4Mst2;
        textFields[38] = txtNitritMst2;
        textFields[39] = txtNo3Mst2;
        textFields[40] = txtPb5Mst2;
        textFields[41] = txtPo4Mst2;
        textFields[42] = txtSo42Mst2;
        textFields[43] = txtSumTriTeMst2;

        textFields[44] = txtAs5Ei;
        textFields[45] = txtCd5Ei;
        textFields[46] = txtClEi;
        textFields[47] = txtHg5Ei;
        textFields[48] = txtNh4Ei;
        textFields[49] = txtNitritEi;
        textFields[50] = txtNo3Ei;
        textFields[51] = txtPb5Ei;
        textFields[52] = txtPo4Ei;
        textFields[53] = txtSo42Ei;
        textFields[54] = txtSumTriTeEi;

        textFields[55] = txtAs5Hi;
        textFields[56] = txtCd5Hi;
        textFields[57] = txtClHi;
        textFields[58] = txtHg5Hi;
        textFields[59] = txtNh4Hi;
        textFields[60] = txtNitritHi;
        textFields[61] = txtNo3Hi;
        textFields[62] = txtPb5Hi;
        textFields[63] = txtPo4Hi;
        textFields[64] = txtSo42Hi;
        textFields[65] = txtSumTriTeHi;

        setEnable(!readOnly);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBeans  DOCUMENT ME!
     */
    public void setCidsBeans(final CidsBean[] cidsBeans) {
        if (!EventQueue.isDispatchThread()) {
            LOG.error("setCidsBeans() invocation not in edt", new Exception());
        }
        this.cidsBeans = cidsBeans;

        if (cidsBeans != null) {
            if (cidsBeans.length > 0) {
                txtAs5Gk.setText(getBeanProperty(PROP_ARSEN, PropertyType.GK, 1));
                txtAs5Mst1.setText(getBeanProperty(PROP_ARSEN, PropertyType.MST, 1));
                txtAs5sw.setText(getBeanProperty(PROP_ARSEN, PropertyType.SW, 1));
                txtAs5Ei.setText(getBeanProperty(PROP_ARSEN, PropertyType.EINHEIT, 1));
                txtAs5Hi.setText(getBeanProperty(PROP_ARSEN, PropertyType.HINWEIS, 1));
                txtAs5Hi.setToolTipText(getBeanProperty(PROP_ARSEN, PropertyType.HI_TOOLTIP, 1));

                txtCd5Gk.setText(getBeanProperty(PROP_CADMIUM, PropertyType.GK, 1));
                txtCd5Mst1.setText(getBeanProperty(PROP_CADMIUM, PropertyType.MST, 1));
                txtCd5sw.setText(getBeanProperty(PROP_CADMIUM, PropertyType.SW, 1));
                txtCd5Ei.setText(getBeanProperty(PROP_CADMIUM, PropertyType.EINHEIT, 1));
                txtCd5Hi.setText(getBeanProperty(PROP_CADMIUM, PropertyType.HINWEIS, 1));
                txtCd5Hi.setToolTipText(getBeanProperty(PROP_CADMIUM, PropertyType.HI_TOOLTIP, 1));

                txtClGk.setText(getBeanProperty(PROP_CHLORID, PropertyType.GK, 1));
                txtClMst1.setText(getBeanProperty(PROP_CHLORID, PropertyType.MST, 1));
                txtClsw.setText(getBeanProperty(PROP_CHLORID, PropertyType.SW, 1));
                txtClEi.setText(getBeanProperty(PROP_CHLORID, PropertyType.EINHEIT, 1));
                txtClHi.setText(getBeanProperty(PROP_CHLORID, PropertyType.HINWEIS, 1));
                txtClHi.setToolTipText(getBeanProperty(PROP_CHLORID, PropertyType.HI_TOOLTIP, 1));

                txtHg5Gk.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.GK, 1));
                txtHg5Mst1.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.MST, 1));
                txtHg5sw.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.SW, 1));
                txtHg5Ei.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.EINHEIT, 1));
                txtHg5Hi.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.HINWEIS, 1));
                txtHg5Hi.setToolTipText(getBeanProperty(PROP_QUECKSILBER, PropertyType.HI_TOOLTIP, 1));

                txtNh4Gk.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.GK, 1));
                txtNh4Mst1.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.MST, 1));
                txtNh4sw.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.SW, 1));
                txtNh4Ei.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.EINHEIT, 1));
                txtNh4Hi.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.HINWEIS, 1));
                txtNh4Hi.setToolTipText(getBeanProperty(PROP_AMMONIUM, PropertyType.HI_TOOLTIP, 1));

                txtNitritGk.setText(getBeanProperty(PROP_NITRIT, PropertyType.GK, 1));
                txtNitritMst1.setText(getBeanProperty(PROP_NITRIT, PropertyType.MST, 1));
                txtNitritsw.setText(getBeanProperty(PROP_NITRIT, PropertyType.SW, 1));
                txtNitritEi.setText(getBeanProperty(PROP_NITRIT, PropertyType.EINHEIT, 1));
                txtNitritHi.setText(getBeanProperty(PROP_NITRIT, PropertyType.HINWEIS, 1));
                txtNitritHi.setToolTipText(getBeanProperty(PROP_NITRIT, PropertyType.HI_TOOLTIP, 1));

                txtNo3Gk.setText(getBeanProperty(PROP_NITRAT, PropertyType.GK, 1));
                txtNo3Mst1.setText(getBeanProperty(PROP_NITRAT, PropertyType.MST, 1));
                txtNo3sw.setText(getBeanProperty(PROP_NITRAT, PropertyType.SW, 1));
                txtNo3Ei.setText(getBeanProperty(PROP_NITRAT, PropertyType.EINHEIT, 1));
                txtNo3Hi.setText(getBeanProperty(PROP_NITRAT, PropertyType.HINWEIS, 1));
                txtNo3Hi.setToolTipText(getBeanProperty(PROP_NITRAT, PropertyType.HI_TOOLTIP, 1));

                txtPb5Gk.setText(getBeanProperty(PROP_BLEI, PropertyType.GK, 1));
                txtPb5Mst1.setText(getBeanProperty(PROP_BLEI, PropertyType.MST, 1));
                txtPb5sw.setText(getBeanProperty(PROP_BLEI, PropertyType.SW, 1));
                txtPb5Ei.setText(getBeanProperty(PROP_BLEI, PropertyType.EINHEIT, 1));
                txtPb5Hi.setText(getBeanProperty(PROP_BLEI, PropertyType.HINWEIS, 1));
                txtPb5Hi.setToolTipText(getBeanProperty(PROP_BLEI, PropertyType.HI_TOOLTIP, 1));

                txtPo4Gk.setText(getBeanProperty(PROP_ORTHO, PropertyType.GK, 1));
                txtPo4Mst1.setText(getBeanProperty(PROP_ORTHO, PropertyType.MST, 1));
                txtPo4sw.setText(getBeanProperty(PROP_ORTHO, PropertyType.SW, 1));
                txtPo4Ei.setText(getBeanProperty(PROP_ORTHO, PropertyType.EINHEIT, 1));
                txtPo4Hi.setText(getBeanProperty(PROP_ORTHO, PropertyType.HINWEIS, 1));
                txtPo4Hi.setToolTipText(getBeanProperty(PROP_ORTHO, PropertyType.HI_TOOLTIP, 1));

                txtSo42Gk.setText(getBeanProperty(PROP_SULFAT, PropertyType.GK, 1));
                txtSo42Mst1.setText(getBeanProperty(PROP_SULFAT, PropertyType.MST, 1));
                txtSo42sw.setText(getBeanProperty(PROP_SULFAT, PropertyType.SW, 1));
                txtSo42Ei.setText(getBeanProperty(PROP_SULFAT, PropertyType.EINHEIT, 1));
                txtSo42Hi.setText(getBeanProperty(PROP_SULFAT, PropertyType.HINWEIS, 1));
                txtSo42Hi.setToolTipText(getBeanProperty(PROP_SULFAT, PropertyType.HI_TOOLTIP, 1));

                txtSumTriTeGk.setText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.GK, 1));
                txtSumTriTeMst1.setText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.MST, 1));
                txtSumTriTeEi.setText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.EINHEIT, 1));
                txtSumTriTeHi.setText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.HINWEIS, 1));
                txtSumTriTeHi.setToolTipText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.HI_TOOLTIP, 1));

                txtAs5Mst2.setText(getBeanProperty(PROP_ARSEN, PropertyType.MST, 2));
                txtCd5Mst2.setText(getBeanProperty(PROP_CADMIUM, PropertyType.MST, 2));
                txtClMst2.setText(getBeanProperty(PROP_CHLORID, PropertyType.MST, 2));
                txtHg5Mst2.setText(getBeanProperty(PROP_QUECKSILBER, PropertyType.MST, 2));
                txtNh4Mst2.setText(getBeanProperty(PROP_AMMONIUM, PropertyType.MST, 2));
                txtNitritMst2.setText(getBeanProperty(PROP_NITRIT, PropertyType.MST, 2));
                txtNo3Mst2.setText(getBeanProperty(PROP_NITRAT, PropertyType.MST, 2));
                txtPb5Mst2.setText(getBeanProperty(PROP_BLEI, PropertyType.MST, 2));
                txtPo4Mst2.setText(getBeanProperty(PROP_ORTHO, PropertyType.MST, 2));
                txtSo42Mst2.setText(getBeanProperty(PROP_SULFAT, PropertyType.MST, 2));
                txtSumTriTeMst2.setText(getBeanProperty(PROP_SUM_TRI_TE, PropertyType.MST, 2));
            }
            setColors();
        } else {
            clearForm();
            for (final JTextField tfield : textFields) {
                tfield.setBackground(new Color(245, 246, 247));
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   substance  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int roundDecimal(final String substance) {
        if (substance.equals(PROP_ARSEN) || substance.equals(PROP_CADMIUM) || substance.equals(PROP_BLEI)
                    || substance.equals(PROP_QUECKSILBER)) {
            return 3;
        } else if (substance.equals(PROP_NITRAT) || substance.equals(PROP_AMMONIUM) || substance.equals(PROP_NITRIT)
                    || substance.equals(PROP_ORTHO) || substance.equals(PROP_SUM_TRI_TE)) {
            return 2;
        } else {
            // PROP_CHLORID, PROP_SULFAT
            return 1;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   name    DOCUMENT ME!
     * @param   type    DOCUMENT ME!
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getBeanProperty(final String name, final PropertyType type, final int number) {
        if (cidsBeans.length > 2) {
            String propName = "";
            Object prop = null;

            switch (type) {
                case GK: {
                    propName = PROP_GK + name;
                    int num = cidsBeans.length - 1;
                    prop = cidsBeans[num].getProperty(propName);

                    while ((prop == null) && (num > 0)) {
                        --num;
                        prop = cidsBeans[num].getProperty(propName);
                    }

                    if (prop != null) {
                        return String.valueOf(prop);
                    } else {
                        return "";
                    }
                }
                case MST: {
                    if (number == 2) {
                        return "";
                    }
                    propName = PROP_WERT + name;
                    Double value = 0.0;
                    int nullValues = 0;

                    for (int i = 0; i < cidsBeans.length; ++i) {
                        final Number n = ((Number)cidsBeans[i].getProperty(propName));

                        if (n == null) {
                            ++nullValues;
                        } else {
                            value += n.doubleValue();
                        }
                    }

                    if (nullValues != cidsBeans.length) {
                        value /= (cidsBeans.length - nullValues);
                        value = Math.round(value * Math.pow(10, roundDecimal(name))) / Math.pow(10, roundDecimal(name));
                    } else {
                        return "";
                    }

                    final String datum = String.valueOf(cidsBeans[number - 1].getProperty("datum"));

                    return datum + ": " + String.valueOf(value);
                }
                case HI_TOOLTIP:
                case HINWEIS: {
                    propName = PROP_WERT + name;
                    Double value = 0.0;
                    int nullValues = 0;

                    for (int i = 0; i < cidsBeans.length; ++i) {
                        final Number n = ((Number)cidsBeans[i].getProperty(propName));

                        if (n == null) {
                            ++nullValues;
                        } else {
                            value += n.doubleValue();
                        }
                    }

                    if (type.equals(PropertyType.HI_TOOLTIP)) {
                        return "Es existieren in diesem Messjahr " + (cidsBeans.length - nullValues)
                                    + " Messungen zu diesem Parameter.";
                    } else {
                        return (cidsBeans.length - nullValues) + "";
                    }
                }
                case SW: {
                    propName = PROP_SW + name;
                    prop = cidsBeans[0].getProperty(propName);
                }
                break;
                case EINHEIT: {
                    propName = PROP_EINHEIT + name;
                    prop = cidsBeans[0].getProperty(propName);
                }
                break;
            }

            if (prop != null) {
                return String.valueOf(prop);
            } else {
                return "";
            }
        } else if ((number == 2) && (cidsBeans.length < 2)) {
            return "";
        } else {
            String propName = "";

            switch (type) {
                case GK: {
                    propName = PROP_GK + name;
                    Object prop = cidsBeans[cidsBeans.length - 1].getProperty(propName);

                    if (prop == null) {
                        prop = cidsBeans[0].getProperty(propName);
                    }

                    if (prop != null) {
                        return String.valueOf(prop);
                    } else {
                        return "";
                    }
                }
                case MST: {
                    propName = PROP_WERT;
                    break;
                }
                case HI_TOOLTIP:
                case HINWEIS: {
                    propName = PROP_WERT + name;
                    Double value = 0.0;
                    int nullValues = 0;

                    for (int i = 0; i < cidsBeans.length; ++i) {
                        final Number n = ((Number)cidsBeans[i].getProperty(propName));

                        if (n == null) {
                            ++nullValues;
                        } else {
                            value += n.doubleValue();
                        }
                    }

                    if (type.equals(PropertyType.HI_TOOLTIP)) {
                        return "Es existieren in diesem Messjahr " + (cidsBeans.length - nullValues)
                                    + " Messungen zu diesem Parameter.";
                    } else {
                        return (cidsBeans.length - nullValues) + "";
                    }
                }
                case SW: {
                    propName = PROP_SW;
                    break;
                }
                case EINHEIT: {
                    propName = PROP_EINHEIT;
                    break;
                }
            }

            propName += name;

            Object prop = cidsBeans[number - 1].getProperty(propName);

            if (prop instanceof BigDecimal) {
                prop = new Double(((BigDecimal)prop).doubleValue());
            }

            if (prop != null) {
                if (type.equals(PropertyType.MST)) {
                    String datum;

                    if (cidsBeans[number - 1].getProperty("datum") instanceof java.sql.Date) {
                        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        datum = format.format(new Date(
                                    ((java.sql.Date)cidsBeans[number - 1].getProperty("datum")).getTime()));
                    } else {
                        datum = String.valueOf(cidsBeans[number - 1].getProperty("datum"));
                    }
                    return datum + ": " + String.valueOf(prop);
                } else {
                    return String.valueOf(prop);
                }
            } else {
                return "";
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    public void setEnable(final boolean enable) {
        for (final JTextField tfield : textFields) {
            tfield.setEnabled(false);
            tfield.setForeground(new Color(0, 0, 0));
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        for (final JTextField tfield : textFields) {
            tfield.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblPhyChem1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        lblMst1Header = new javax.swing.JLabel();
        lblMst2Header = new javax.swing.JLabel();
        lblSchwellwertHeader = new javax.swing.JLabel();
        lblGk = new javax.swing.JLabel();
        lblGk1 = new javax.swing.JLabel();
        lblGk2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblNo3 = new javax.swing.JLabel();
        txtNo3Mst1 = new javax.swing.JTextField();
        txtNo3Mst2 = new javax.swing.JTextField();
        txtNo3sw = new javax.swing.JTextField();
        txtNo3Gk = new javax.swing.JTextField();
        txtNo3Ei = new javax.swing.JTextField();
        txtNo3Hi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblAs5 = new javax.swing.JLabel();
        txtAs5Mst1 = new javax.swing.JTextField();
        txtAs5Mst2 = new javax.swing.JTextField();
        txtAs5sw = new javax.swing.JTextField();
        txtAs5Gk = new javax.swing.JTextField();
        txtAs5Ei = new javax.swing.JTextField();
        txtAs5Hi = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblCd5 = new javax.swing.JLabel();
        txtCd5Mst1 = new javax.swing.JTextField();
        txtCd5Mst2 = new javax.swing.JTextField();
        txtCd5sw = new javax.swing.JTextField();
        txtCd5Gk = new javax.swing.JTextField();
        txtCd5Ei = new javax.swing.JTextField();
        txtCd5Hi = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lblPb5 = new javax.swing.JLabel();
        txtPb5Mst1 = new javax.swing.JTextField();
        txtPb5Mst2 = new javax.swing.JTextField();
        txtPb5sw = new javax.swing.JTextField();
        txtPb5Gk = new javax.swing.JTextField();
        txtPb5Ei = new javax.swing.JTextField();
        txtPb5Hi = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lblHg5 = new javax.swing.JLabel();
        txtHg5Mst1 = new javax.swing.JTextField();
        txtHg5Mst2 = new javax.swing.JTextField();
        txtHg5sw = new javax.swing.JTextField();
        txtHg5Gk = new javax.swing.JTextField();
        txtHg5Ei = new javax.swing.JTextField();
        txtHg5Hi = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        lblNh4 = new javax.swing.JLabel();
        txtNh4Mst1 = new javax.swing.JTextField();
        txtNh4Mst2 = new javax.swing.JTextField();
        txtNh4sw = new javax.swing.JTextField();
        txtNh4Gk = new javax.swing.JTextField();
        txtNh4Ei = new javax.swing.JTextField();
        txtNh4Hi = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        lblCl = new javax.swing.JLabel();
        txtClMst1 = new javax.swing.JTextField();
        txtClMst2 = new javax.swing.JTextField();
        txtClsw = new javax.swing.JTextField();
        txtClGk = new javax.swing.JTextField();
        txtClEi = new javax.swing.JTextField();
        txtClHi = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        lblNitrit = new javax.swing.JLabel();
        txtNitritMst1 = new javax.swing.JTextField();
        txtNitritMst2 = new javax.swing.JTextField();
        txtNitritsw = new javax.swing.JTextField();
        txtNitritGk = new javax.swing.JTextField();
        txtNitritEi = new javax.swing.JTextField();
        txtNitritHi = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        lblPo4 = new javax.swing.JLabel();
        txtPo4Mst1 = new javax.swing.JTextField();
        txtPo4Mst2 = new javax.swing.JTextField();
        txtPo4sw = new javax.swing.JTextField();
        txtPo4Gk = new javax.swing.JTextField();
        txtPo4Ei = new javax.swing.JTextField();
        txtPo4Hi = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        lblSo42 = new javax.swing.JLabel();
        txtSo42Mst1 = new javax.swing.JTextField();
        txtSo42Mst2 = new javax.swing.JTextField();
        txtSo42sw = new javax.swing.JTextField();
        txtSo42Gk = new javax.swing.JTextField();
        txtSo42Ei = new javax.swing.JTextField();
        txtSo42Hi = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        lblSumTriTe = new javax.swing.JLabel();
        txtSumTriTeMst1 = new javax.swing.JTextField();
        txtSumTriTeMst2 = new javax.swing.JTextField();
        txtSumTriTesw = new javax.swing.JTextField();
        txtSumTriTeGk = new javax.swing.JTextField();
        txtSumTriTeEi = new javax.swing.JTextField();
        txtSumTriTeHi = new javax.swing.JTextField();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblPhyChem1, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblPhyChem1.text")); // NOI18N
        lblPhyChem1.setMaximumSize(new java.awt.Dimension(277, 35));
        lblPhyChem1.setMinimumSize(new java.awt.Dimension(277, 35));
        lblPhyChem1.setPreferredSize(new java.awt.Dimension(277, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblPhyChem1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel9, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblSpace, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblSpace.text")); // NOI18N
        lblSpace.setMaximumSize(new java.awt.Dimension(167, 17));
        lblSpace.setMinimumSize(new java.awt.Dimension(167, 17));
        lblSpace.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblSpace, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblMst1Header, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblMst1Header.text")); // NOI18N
        lblMst1Header.setMaximumSize(new java.awt.Dimension(220, 17));
        lblMst1Header.setMinimumSize(new java.awt.Dimension(220, 17));
        lblMst1Header.setPreferredSize(new java.awt.Dimension(220, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblMst1Header, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblMst2Header, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblMst2Header.text")); // NOI18N
        lblMst2Header.setMaximumSize(new java.awt.Dimension(220, 17));
        lblMst2Header.setMinimumSize(new java.awt.Dimension(220, 17));
        lblMst2Header.setPreferredSize(new java.awt.Dimension(220, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblMst2Header, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblSchwellwertHeader, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblSchwellwertHeader.text")); // NOI18N
        lblSchwellwertHeader.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSchwellwertHeader.setMinimumSize(new java.awt.Dimension(120, 35));
        lblSchwellwertHeader.setPreferredSize(new java.awt.Dimension(120, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblSchwellwertHeader, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblGk, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblGk.text")); // NOI18N
        lblGk.setMaximumSize(new java.awt.Dimension(110, 35));
        lblGk.setMinimumSize(new java.awt.Dimension(110, 35));
        lblGk.setPreferredSize(new java.awt.Dimension(110, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblGk, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblGk1, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblGk1.text")); // NOI18N
        lblGk1.setMaximumSize(new java.awt.Dimension(60, 17));
        lblGk1.setMinimumSize(new java.awt.Dimension(60, 17));
        lblGk1.setPreferredSize(new java.awt.Dimension(60, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblGk1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblGk2, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblGk2.text")); // NOI18N
        lblGk2.setMaximumSize(new java.awt.Dimension(200, 35));
        lblGk2.setMinimumSize(new java.awt.Dimension(200, 35));
        lblGk2.setPreferredSize(new java.awt.Dimension(200, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblGk2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel8, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblNo3, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblNo3.text")); // NOI18N
        lblNo3.setMaximumSize(new java.awt.Dimension(167, 25));
        lblNo3.setMinimumSize(new java.awt.Dimension(167, 25));
        lblNo3.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblNo3, gridBagConstraints);

        txtNo3Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3Mst1.text", new Object[] {})); // NOI18N
        txtNo3Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNo3Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNo3Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3Mst1, gridBagConstraints);

        txtNo3Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3Mst2.text", new Object[] {})); // NOI18N
        txtNo3Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNo3Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNo3Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3Mst2, gridBagConstraints);

        txtNo3sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3sw.text", new Object[] {})); // NOI18N
        txtNo3sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtNo3sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtNo3sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3sw, gridBagConstraints);

        txtNo3Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3Gk.text", new Object[] {})); // NOI18N
        txtNo3Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtNo3Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtNo3Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3Gk, gridBagConstraints);

        txtNo3Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3Ei.text", new Object[] {})); // NOI18N
        txtNo3Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtNo3Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtNo3Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3Ei, gridBagConstraints);

        txtNo3Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNo3Hi.text", new Object[] {})); // NOI18N
        txtNo3Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtNo3Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtNo3Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNo3Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblAs5, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblAs5.text")); // NOI18N
        lblAs5.setMaximumSize(new java.awt.Dimension(167, 25));
        lblAs5.setMinimumSize(new java.awt.Dimension(167, 25));
        lblAs5.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblAs5, gridBagConstraints);

        txtAs5Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5Mst1.text", new Object[] {})); // NOI18N
        txtAs5Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtAs5Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtAs5Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5Mst1, gridBagConstraints);

        txtAs5Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5Mst2.text", new Object[] {})); // NOI18N
        txtAs5Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtAs5Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtAs5Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5Mst2, gridBagConstraints);

        txtAs5sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5sw.text", new Object[] {})); // NOI18N
        txtAs5sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtAs5sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtAs5sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5sw, gridBagConstraints);

        txtAs5Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5Gk.text", new Object[] {})); // NOI18N
        txtAs5Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtAs5Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtAs5Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5Gk, gridBagConstraints);

        txtAs5Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5Ei.text", new Object[] {})); // NOI18N
        txtAs5Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtAs5Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtAs5Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5Ei, gridBagConstraints);

        txtAs5Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtAs5Hi.text", new Object[] {})); // NOI18N
        txtAs5Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtAs5Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtAs5Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAs5Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblCd5, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblCd5.text", new Object[] {})); // NOI18N
        lblCd5.setMaximumSize(new java.awt.Dimension(167, 25));
        lblCd5.setMinimumSize(new java.awt.Dimension(167, 25));
        lblCd5.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblCd5, gridBagConstraints);

        txtCd5Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5Mst1.text", new Object[] {})); // NOI18N
        txtCd5Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtCd5Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtCd5Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5Mst1, gridBagConstraints);

        txtCd5Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5Mst2.text", new Object[] {})); // NOI18N
        txtCd5Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtCd5Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtCd5Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5Mst2, gridBagConstraints);

        txtCd5sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5sw.text", new Object[] {})); // NOI18N
        txtCd5sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtCd5sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtCd5sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5sw, gridBagConstraints);

        txtCd5Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5Gk.text", new Object[] {})); // NOI18N
        txtCd5Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtCd5Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtCd5Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5Gk, gridBagConstraints);

        txtCd5Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5Ei.text", new Object[] {})); // NOI18N
        txtCd5Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtCd5Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtCd5Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5Ei, gridBagConstraints);

        txtCd5Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtCd5Hi.text", new Object[] {})); // NOI18N
        txtCd5Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtCd5Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtCd5Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCd5Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblPb5, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblPb5.text")); // NOI18N
        lblPb5.setMaximumSize(new java.awt.Dimension(167, 25));
        lblPb5.setMinimumSize(new java.awt.Dimension(167, 25));
        lblPb5.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(lblPb5, gridBagConstraints);

        txtPb5Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5Mst1.text", new Object[] {})); // NOI18N
        txtPb5Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtPb5Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtPb5Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5Mst1, gridBagConstraints);

        txtPb5Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5Mst2.text", new Object[] {})); // NOI18N
        txtPb5Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtPb5Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtPb5Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5Mst2, gridBagConstraints);

        txtPb5sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5sw.text", new Object[] {})); // NOI18N
        txtPb5sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtPb5sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtPb5sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5sw, gridBagConstraints);

        txtPb5Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5Gk.text", new Object[] {})); // NOI18N
        txtPb5Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtPb5Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtPb5Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5Gk, gridBagConstraints);

        txtPb5Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5Ei.text", new Object[] {})); // NOI18N
        txtPb5Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtPb5Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtPb5Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5Ei, gridBagConstraints);

        txtPb5Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPb5Hi.text", new Object[] {})); // NOI18N
        txtPb5Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtPb5Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtPb5Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPb5Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblHg5, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblHg5.text")); // NOI18N
        lblHg5.setMaximumSize(new java.awt.Dimension(167, 25));
        lblHg5.setMinimumSize(new java.awt.Dimension(167, 25));
        lblHg5.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblHg5, gridBagConstraints);

        txtHg5Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5Mst1.text", new Object[] {})); // NOI18N
        txtHg5Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtHg5Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtHg5Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5Mst1, gridBagConstraints);

        txtHg5Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5Mst2.text", new Object[] {})); // NOI18N
        txtHg5Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtHg5Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtHg5Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5Mst2, gridBagConstraints);

        txtHg5sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5sw.text", new Object[] {})); // NOI18N
        txtHg5sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtHg5sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtHg5sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5sw, gridBagConstraints);

        txtHg5Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5Gk.text", new Object[] {})); // NOI18N
        txtHg5Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtHg5Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtHg5Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5Gk, gridBagConstraints);

        txtHg5Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5Ei.text", new Object[] {})); // NOI18N
        txtHg5Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtHg5Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtHg5Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5Ei, gridBagConstraints);

        txtHg5Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtHg5Hi.text", new Object[] {})); // NOI18N
        txtHg5Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtHg5Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtHg5Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtHg5Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblNh4, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblNh4.text")); // NOI18N
        lblNh4.setMaximumSize(new java.awt.Dimension(167, 25));
        lblNh4.setMinimumSize(new java.awt.Dimension(167, 25));
        lblNh4.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblNh4, gridBagConstraints);

        txtNh4Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4Mst1.text", new Object[] {})); // NOI18N
        txtNh4Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNh4Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNh4Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4Mst1, gridBagConstraints);

        txtNh4Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4Mst2.text", new Object[] {})); // NOI18N
        txtNh4Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNh4Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNh4Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4Mst2, gridBagConstraints);

        txtNh4sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4sw.text", new Object[] {})); // NOI18N
        txtNh4sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtNh4sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtNh4sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4sw, gridBagConstraints);

        txtNh4Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4Gk.text", new Object[] {})); // NOI18N
        txtNh4Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtNh4Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtNh4Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4Gk, gridBagConstraints);

        txtNh4Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4Ei.text", new Object[] {})); // NOI18N
        txtNh4Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtNh4Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtNh4Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4Ei, gridBagConstraints);

        txtNh4Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNh4Hi.text", new Object[] {})); // NOI18N
        txtNh4Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtNh4Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtNh4Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNh4Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblCl, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblCl.text")); // NOI18N
        lblCl.setMaximumSize(new java.awt.Dimension(167, 25));
        lblCl.setMinimumSize(new java.awt.Dimension(167, 25));
        lblCl.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(lblCl, gridBagConstraints);

        txtClMst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClMst1.text", new Object[] {})); // NOI18N
        txtClMst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtClMst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtClMst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClMst1, gridBagConstraints);

        txtClMst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClMst2.text", new Object[] {})); // NOI18N
        txtClMst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtClMst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtClMst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClMst2, gridBagConstraints);

        txtClsw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClsw.text", new Object[] {})); // NOI18N
        txtClsw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtClsw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtClsw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClsw, gridBagConstraints);

        txtClGk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClGk.text", new Object[] {})); // NOI18N
        txtClGk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtClGk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtClGk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClGk, gridBagConstraints);

        txtClEi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClEi.text", new Object[] {})); // NOI18N
        txtClEi.setMaximumSize(new java.awt.Dimension(60, 25));
        txtClEi.setMinimumSize(new java.awt.Dimension(60, 25));
        txtClEi.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClEi, gridBagConstraints);

        txtClHi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtClHi.text", new Object[] {})); // NOI18N
        txtClHi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtClHi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtClHi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtClHi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel10, gridBagConstraints);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblNitrit, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblNitrit.text")); // NOI18N
        lblNitrit.setMaximumSize(new java.awt.Dimension(167, 25));
        lblNitrit.setMinimumSize(new java.awt.Dimension(167, 25));
        lblNitrit.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(lblNitrit, gridBagConstraints);

        txtNitritMst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritMst1.text", new Object[] {})); // NOI18N
        txtNitritMst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNitritMst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNitritMst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritMst1, gridBagConstraints);

        txtNitritMst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritMst2.text", new Object[] {})); // NOI18N
        txtNitritMst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtNitritMst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtNitritMst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritMst2, gridBagConstraints);

        txtNitritsw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritsw.text", new Object[] {})); // NOI18N
        txtNitritsw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtNitritsw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtNitritsw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritsw, gridBagConstraints);

        txtNitritGk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritGk.text", new Object[] {})); // NOI18N
        txtNitritGk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtNitritGk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtNitritGk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritGk, gridBagConstraints);

        txtNitritEi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritEi.text", new Object[] {})); // NOI18N
        txtNitritEi.setMaximumSize(new java.awt.Dimension(60, 25));
        txtNitritEi.setMinimumSize(new java.awt.Dimension(60, 25));
        txtNitritEi.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritEi, gridBagConstraints);

        txtNitritHi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtNitritHi.text", new Object[] {})); // NOI18N
        txtNitritHi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtNitritHi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtNitritHi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtNitritHi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel11, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblPo4, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblPo4.text")); // NOI18N
        lblPo4.setMaximumSize(new java.awt.Dimension(167, 25));
        lblPo4.setMinimumSize(new java.awt.Dimension(167, 25));
        lblPo4.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(lblPo4, gridBagConstraints);

        txtPo4Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4Mst1.text", new Object[] {})); // NOI18N
        txtPo4Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtPo4Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtPo4Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4Mst1, gridBagConstraints);

        txtPo4Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4Mst2.text", new Object[] {})); // NOI18N
        txtPo4Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtPo4Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtPo4Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4Mst2, gridBagConstraints);

        txtPo4sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4sw.text", new Object[] {})); // NOI18N
        txtPo4sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtPo4sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtPo4sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4sw, gridBagConstraints);

        txtPo4Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4Gk.text", new Object[] {})); // NOI18N
        txtPo4Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtPo4Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtPo4Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4Gk, gridBagConstraints);

        txtPo4Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4Ei.text", new Object[] {})); // NOI18N
        txtPo4Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtPo4Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtPo4Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4Ei, gridBagConstraints);

        txtPo4Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtPo4Hi.text", new Object[] {})); // NOI18N
        txtPo4Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtPo4Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtPo4Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtPo4Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel12, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblSo42, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblSo42.text")); // NOI18N
        lblSo42.setMaximumSize(new java.awt.Dimension(167, 25));
        lblSo42.setMinimumSize(new java.awt.Dimension(167, 25));
        lblSo42.setPreferredSize(new java.awt.Dimension(167, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(lblSo42, gridBagConstraints);

        txtSo42Mst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42Mst1.text", new Object[] {})); // NOI18N
        txtSo42Mst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtSo42Mst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtSo42Mst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42Mst1, gridBagConstraints);

        txtSo42Mst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42Mst2.text", new Object[] {})); // NOI18N
        txtSo42Mst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtSo42Mst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtSo42Mst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42Mst2, gridBagConstraints);

        txtSo42sw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42sw.text", new Object[] {})); // NOI18N
        txtSo42sw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtSo42sw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtSo42sw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42sw, gridBagConstraints);

        txtSo42Gk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42Gk.text", new Object[] {})); // NOI18N
        txtSo42Gk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtSo42Gk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtSo42Gk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42Gk, gridBagConstraints);

        txtSo42Ei.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42Ei.text", new Object[] {})); // NOI18N
        txtSo42Ei.setMaximumSize(new java.awt.Dimension(60, 25));
        txtSo42Ei.setMinimumSize(new java.awt.Dimension(60, 25));
        txtSo42Ei.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42Ei, gridBagConstraints);

        txtSo42Hi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSo42Hi.text", new Object[] {})); // NOI18N
        txtSo42Hi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtSo42Hi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtSo42Hi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtSo42Hi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel13, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblSumTriTe, org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.lblSumTriTe.text")); // NOI18N
        lblSumTriTe.setMaximumSize(new java.awt.Dimension(167, 17));
        lblSumTriTe.setMinimumSize(new java.awt.Dimension(167, 35));
        lblSumTriTe.setPreferredSize(new java.awt.Dimension(167, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(lblSumTriTe, gridBagConstraints);

        txtSumTriTeMst1.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTeMst1.text", new Object[] {})); // NOI18N
        txtSumTriTeMst1.setMaximumSize(new java.awt.Dimension(220, 25));
        txtSumTriTeMst1.setMinimumSize(new java.awt.Dimension(220, 25));
        txtSumTriTeMst1.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTeMst1, gridBagConstraints);

        txtSumTriTeMst2.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTeMst2.text", new Object[] {})); // NOI18N
        txtSumTriTeMst2.setMaximumSize(new java.awt.Dimension(220, 25));
        txtSumTriTeMst2.setMinimumSize(new java.awt.Dimension(220, 25));
        txtSumTriTeMst2.setPreferredSize(new java.awt.Dimension(220, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTeMst2, gridBagConstraints);

        txtSumTriTesw.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTesw.text", new Object[] {})); // NOI18N
        txtSumTriTesw.setMaximumSize(new java.awt.Dimension(120, 25));
        txtSumTriTesw.setMinimumSize(new java.awt.Dimension(120, 25));
        txtSumTriTesw.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTesw, gridBagConstraints);

        txtSumTriTeGk.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTeGk.text", new Object[] {})); // NOI18N
        txtSumTriTeGk.setMaximumSize(new java.awt.Dimension(110, 25));
        txtSumTriTeGk.setMinimumSize(new java.awt.Dimension(110, 25));
        txtSumTriTeGk.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTeGk, gridBagConstraints);

        txtSumTriTeEi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTeEi.text", new Object[] {})); // NOI18N
        txtSumTriTeEi.setMaximumSize(new java.awt.Dimension(60, 25));
        txtSumTriTeEi.setMinimumSize(new java.awt.Dimension(60, 25));
        txtSumTriTeEi.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTeEi, gridBagConstraints);

        txtSumTriTeHi.setText(org.openide.util.NbBundle.getMessage(WkGwMstMessungenPanOne.class, "WkGwMstMessungenPanOne.txtSumTriTeHi.text", new Object[] {})); // NOI18N
        txtSumTriTeHi.setMaximumSize(new java.awt.Dimension(200, 25));
        txtSumTriTeHi.setMinimumSize(new java.awt.Dimension(200, 25));
        txtSumTriTeHi.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel14.add(txtSumTriTeHi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean[] getCidsBeans() {
        return this.cidsBeans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Color getColor(final String text) {
        if (text.equalsIgnoreCase("ja")) {
            return Color.GREEN;
        } else if (text.equalsIgnoreCase("nein")) {
            return Color.RED;
        } else {
            return new Color(245, 246, 247);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setColors() {
        for (final JTextField tfield : textFields) {
            tfield.setDisabledTextColor(new Color(0, 0, 0));
            tfield.setBackground(getColor(tfield.getText()));
        }
    }
}
