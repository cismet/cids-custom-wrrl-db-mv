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
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.utils.interfaces.CidsBeanAction;

import de.cismet.ext.CExtContext;
import de.cismet.ext.CExtProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CExtProvider.class)
public class FgskSimulationCExtProvider implements CExtProvider<CidsBeanAction> {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(FgskSimulationCExtProvider.class);
    private static final String SIMULATION_CLASS_NAME = "de.cismet.cids.dynamics.Simulation";
    private static final CidsBeanAction ACTION = new FgskSimulationCidsBeanAction();

    //~ Instance fields --------------------------------------------------------

    private final String ifaceClass;
    private final String concreteClass;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskSimulationCExtProvider object.
     */
    public FgskSimulationCExtProvider() {
        ifaceClass = "de.cismet.cids.utils.interfaces.CidsBeanAction";                        // NOI18N
        concreteClass = "de.cismet.cids.custom.wrrl_db_mv.fgsk.FgskSimulationCidsBeanAction"; // NOI18N
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Collection<? extends CidsBeanAction> provideExtensions(final CExtContext context) {
        final List<CidsBeanAction> actions = new ArrayList<CidsBeanAction>(1);

        if (context != null) {
            final Object ctxReference = context.getProperty(CExtContext.CTX_REFERENCE);

            final Object ctxObject;
            if (ctxReference instanceof Collection) {
                final Collection ctxCollection = (Collection)ctxReference;

                if (ctxCollection.size() == 1) {
                    ctxObject = ctxCollection.iterator().next();
                } else {
                    ctxObject = null;
                }
            } else if (ctxReference instanceof Object[]) {
                final Object[] ctxArray = (Object[])ctxReference;

                if (ctxArray.length == 1) {
                    ctxObject = ctxArray[0];
                } else {
                    ctxObject = null;
                }
            } else {
                ctxObject = ctxReference;
            }

            final MetaClass mc;
            final CidsBean ctxBean;
            if (ctxObject instanceof CidsBean) {
                ctxBean = (CidsBean)ctxObject;
                mc = ctxBean.getMetaObject().getMetaClass();
            } else if (ctxObject instanceof MetaObject) {
                final MetaObject mo = (MetaObject)ctxObject;
                ctxBean = mo.getBean();
                mc = mo.getMetaClass();
            } else {
                ctxBean = null;
                mc = null;
            }

            if (((mc != null) && (ctxBean != null)) && ctxBean.getClass().getName().equals(SIMULATION_CLASS_NAME)) {
                ACTION.setCidsBean(ctxBean);
                actions.add(ACTION);
            }
        }

        return actions;
    }

    @Override
    public Class<? extends CidsBeanAction> getType() {
        return CidsBeanAction.class;
    }

    @Override
    public boolean canProvide(final Class<?> c) {
        final String cName = c.getCanonicalName();

        return (cName == null) ? false : (ifaceClass.equals(cName) || concreteClass.equals(cName));
    }
}
