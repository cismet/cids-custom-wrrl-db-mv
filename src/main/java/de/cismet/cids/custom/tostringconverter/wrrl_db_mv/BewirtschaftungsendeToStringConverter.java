/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import de.cismet.cids.custom.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BewirtschaftungsendeToStringConverter extends CustomToStringConverter {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            BewirtschaftungsendeToStringConverter.class);

    //~ Instance fields --------------------------------------------------------

    final MetaClass mcWkFg = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg");

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        try {
            final int wkfgId = Integer.valueOf((String)cidsBean.getProperty("wk_k"));

            final String queryWkFg = "SELECT " + mcWkFg.getID() + ", wk_fg." + mcWkFg.getPrimaryKey() + " "
                        + "FROM " + mcWkFg.getTableName() + " AS wk_fg "
                        + "WHERE id = " + wkfgId + ";";
            final MetaObject[] mosWkFg;
            if (LOG.isDebugEnabled()) {
                LOG.debug("queryWkFg  => " + queryWkFg);
            }
            mosWkFg = SessionManager.getProxy().getMetaObjectByQuery(queryWkFg, 0);
            final CidsBean wkfgBean = mosWkFg[0].getBean();
            return "Bewirtschaftungsende auf " + String.valueOf(wkfgBean.getProperty("wk_k"));
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating string", ex);
            }
            return "Bewirtschaftungsende auf ...";
        }
    }
}
