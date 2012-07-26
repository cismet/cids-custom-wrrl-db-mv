/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupOperativesZielAbschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.CismetThreadPool;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class OperativeZieleComboBox extends ScrollableComboBox {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            OperativeZieleComboBox.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_operatives_ziel");

    //~ Instance fields --------------------------------------------------------

    private int kompartiment;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenComboBox object.
     */
    public OperativeZieleComboBox() {
        super();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  mc  DOCUMENT ME!
     */
    private void init(final MetaClass mc) {
        if (!isFakeModel()) {
            CismetThreadPool.execute(new SwingWorker<DefaultComboBoxModel, Void>() {

                    @Override
                    protected DefaultComboBoxModel doInBackground() throws Exception {
                        return getModelByMetaClass(mc);
                    }

                    @Override
                    protected void done() {
                        try {
                            setModel(get());
                            setSelectedItem(cidsBean);
                        } catch (InterruptedException interruptedException) {
                        } catch (ExecutionException executionException) {
                            log.error("Error while initializing the model of a referenceCombo", executionException); // NOI18N
                        }
                    }
                });
        } else {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mc  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private DefaultComboBoxModel getModelByMetaClass(final MetaClass mc) throws Exception {
        final String query = "select " + mc.getID() + "," + mc.getPrimaryKey() + " from " + mc.getTableName(); // NOI18N
        final MetaObject[] MetaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
        final List<CidsBean> cbv = new ArrayList<CidsBean>(MetaObjects.length);

        for (final MetaObject mo : MetaObjects) {
            if (isRelevantBean(kompartiment, mo.getBean())) {
                cbv.add(mo.getBean());
            }
        }

        // Sorts the model using String comparison on the bean's toString()
        Collections.sort(cbv, beanToStringComparator);
        return new DefaultComboBoxModel(cbv.toArray());
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kompartiment  DOCUMENT ME!
     * @param   bean          DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean isRelevantBean(final int kompartiment, final CidsBean bean) {
        String kompartimentProp = "ufer";

        if (kompartiment == GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_SOHLE) {
            kompartimentProp = "sohle";
        } else if (kompartiment == GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD) {
            kompartimentProp = "umfeld";
        }
        final Boolean hasRightType = (Boolean)bean.getProperty(kompartimentProp);

        return ((hasRightType != null) && hasRightType.booleanValue());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the kompartiment
     */
    public int getKompartiment() {
        return kompartiment;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kompartiment  the kompartiment to set
     */
    public void setKompartiment(final int kompartiment) {
        if (this.kompartiment != kompartiment) {
            this.kompartiment = kompartiment;
            init(MC);
        } else {
            setSelectedItem(getSelectedItem());
        }
    }

    @Override
    public void setMetaClass(final MetaClass metaClass) {
    }

    @Override
    public MetaClass getMetaClass() {
        return MC;
    }
}
