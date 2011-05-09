/*
 *  Copyright (C) 2011 thorsten
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
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import Sirius.server.newuser.User;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import de.cismet.cids.dynamics.AbstractCustomBeanPermissionProvider;
import de.cismet.tools.PropertyReader;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author thorsten
 */
public abstract class BasicGeometryFomFilePermissionProvider extends AbstractCustomBeanPermissionProvider {

    public static HashMap<String, Geometry> PERMISSIONGEOMS = new HashMap<String, Geometry>();
    private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BasicGeometryFomFilePermissionProvider.class);

    static {
        final PropertyReader geomperm = new PropertyReader("/de/cismet/cids/custom/permissions/geomperm.properties");
        WKBReader r = new WKBReader(new GeometryFactory());
        Set keys = geomperm.getInternalProperties().keySet();
        for (Object key : keys) {
            try {
                String hexVal = geomperm.getProperty((String) key);
                if (hexVal != null) {
                    Geometry g = r.read(WKBReader.hexToBytes(hexVal));
                    PERMISSIONGEOMS.put((String) key, g);
                }
            } catch (ParseException ex) {
                log.error("Error during parse of MM Permission Geom");
            }
        }
    }

    @Override
    public boolean getCustomReadPermissionDecisionforUser(User u) {
        return true;
    }

    @Override
    public boolean getCustomWritePermissionDecisionforUser(User u) {
        if (u.getUserGroup().getName().equalsIgnoreCase("administratoren")){
            log.debug("member of admin group. permission is granted");
            return true;
        }

        Object g = cidsBean.getProperty(getGeomProperty());
        if (g == null) {
            log.info(getGeomProperty() + "delivered a null value. access is granted");
            return true;
        }
        if (g instanceof Geometry) {
            Geometry objectGeom = (Geometry) g;
            String userGroup = u.getUserGroup().getName();
            String end = userGroup.substring(userGroup.length() - 2);
            Geometry groupGeom = PERMISSIONGEOMS.get(end);
            if (groupGeom == null) {
                log.info("the user group did not have a permission geometry. permission is denied");
                return false;
            }
            return groupGeom.contains(objectGeom);
        } else {
            log.warn(getGeomProperty() + "delivered neither a Geometry value nor null. access is denied");
            return false;
        }

    }

    public abstract String getGeomProperty();

    public static void main(String[] args) {
        System.out.println("WM:" + PERMISSIONGEOMS.get("WM").getArea());
        System.out.println("NM:" + PERMISSIONGEOMS.get("NM").getArea());
        System.out.println("VP:" + PERMISSIONGEOMS.get("VP").getArea());
        System.out.println("MS:" + PERMISSIONGEOMS.get("MS").getArea());

        System.out.println("liebhaberEI".substring("liebhaberEI".length() - 2));
    }
}
