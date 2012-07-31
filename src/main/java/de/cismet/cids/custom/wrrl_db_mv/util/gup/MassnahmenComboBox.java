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
public class MassnahmenComboBox extends ScrollableComboBox {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            MassnahmenComboBox.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_massnahmenart");

    static {
        new Thread(new Runnable() {

                @Override
                public void run() {
                    final String query = "select " + MC.getID() + "," + MC.getPrimaryKey() + " from "
                                + MC.getTableName();     // NOI18N
                    final MetaObject[] MetaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
                }
            }).start();
    }

    //~ Instance fields --------------------------------------------------------

    private int kompartiment;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenComboBox object.
     */
    public MassnahmenComboBox() {
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
        final String orderBy = "";                                                                             // NOI18N
        final String query = "select " + mc.getID() + "," + mc.getPrimaryKey() + " from " + mc.getTableName(); // NOI18N
        final MetaObject[] MetaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
        final List<CidsBean> cbv = new ArrayList<CidsBean>(MetaObjects.length);

        for (final MetaObject mo : MetaObjects) {
            final Object kompartimentObject = mo.getBean().getProperty("kompartiment.id");
            if ((kompartimentObject != null) && kompartimentObject.equals(kompartiment)) {
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
