/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.util;

import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.util.HashMap;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class CidsBeanCache {

    //~ Static fields/initializers ---------------------------------------------

    private static CidsBeanCache INSTANCE = new CidsBeanCache();

    //~ Instance fields --------------------------------------------------------

    Logger LOG = Logger.getLogger(CidsBeanCache.class);

    private HashMap<MetaObject, CidsBean> cache = new HashMap();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CidsBeanCache object.
     */
    private CidsBeanCache() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBeanCache getInstance() {
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   metaobject  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getCidsBeanForMetaObject(final MetaObject metaobject) {
        CidsBean cidsBean = cache.get(metaobject);
        if (cidsBean == null) {
            cidsBean = metaobject.getBean();
            cache.put(metaobject, cidsBean);
            if (LOG.isDebugEnabled()) {
                LOG.debug("bean cached");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("cached bean is used");
            }
        }
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    public void clear() {
        cache.clear();
    }
}
