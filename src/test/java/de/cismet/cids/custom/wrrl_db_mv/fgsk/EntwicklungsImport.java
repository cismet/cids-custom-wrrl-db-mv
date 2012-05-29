/*
 * Copyright (C) 2012 cismet GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Die View entwicklungsziel_import und die Funktionen getmtcbystation, getguetebesserdreibystation werden in der Datenbank benötigt.
 * Danach müssen noch die Geometrien erzeugt werden. dazu existiert ein Skript
 * @author therter
 */
public class EntwicklungsImport {
    private static int ZUSTAND_ERHALTEN = 9;
    private static int GESTALTEN = 10;
    private static int ENTWICKELN = 11;
    private static int BELASSEN = 12;
    private static int ANPASSEN = 13;
    private Connection conn;
    private Statement stmt;
    private Statement stmt2;
    private List<InsertionData> data = new ArrayList();
    
    public EntwicklungsImport() throws Exception {
        final Properties p = new Properties();
        p.put("log4j.appender.Remote", "org.apache.log4j.net.SocketAppender");
        p.put("log4j.appender.Remote.remoteHost", "localhost");
        p.put("log4j.appender.Remote.port", "4445");
        p.put("log4j.appender.Remote.locationInfo", "true");
        p.put("log4j.rootLogger", "ALL,Remote");
        org.apache.log4j.PropertyConfigurator.configure(p);

        Class.forName("org.postgresql.Driver");
        //verbindet mit Datenbank
        conn = DriverManager.getConnection("jdbc:postgresql://fis-wasser-mv.de/wrrl-db", "postgres", "");                   
        stmt = conn.createStatement();
        stmt2 = conn.createStatement();
        
        run();
    }
    
    private void run() throws Exception {
        System.out.println("line 1: " + createLine(0, 73485, 1659));
        System.out.println("line2: " + createLine(0, 17302, 1698));
//        ResultSet rs = stmt.executeQuery("select * from entwicklungsziel_import");
//        
//        if (rs == null) {
//            return;
//        }
//        
//        while (rs.next()) {
//            Array ia = rs.getArray(4);
//            Array fgska = rs.getArray(7);
//            int von  = rs.getInt(2);
//            int bis  = rs.getInt(3);
//            int index = 0;
//            int evk = rs.getInt(5);
//            int route = rs.getInt(6);
//            int wkFg = rs.getInt(1);
//            
//            String[] strList = null;
//            String[] strListFgsk = null;
//            List<Integer> intList = new ArrayList<Integer>();
//            if (ia != null) {
//                strList = (String[])ia.getArray();
//            }
//            if (fgska != null) {
//                strListFgsk = (String[])fgska.getArray();
//            }
//            
//            intList.add(von);
//            intList.add(bis);
//            
//            if (strList != null) {
//                for (String s : strList) {
//                    try {
//                        int i = Integer.parseInt(s);
//
//                        if (!intList.contains(i)) {
//                            intList.add(i);
//                        }
//                    } catch (NumberFormatException e) {
//                        System.out.println("error" + e.getMessage());
//                        e.printStackTrace();
//                    }
//                }
//            }
//            if (strListFgsk != null) {
//                for (String s : strListFgsk) {
//                    try {
//                        int i = Integer.parseInt(s);
//
//                        if (!intList.contains(i)) {
//                            intList.add(i);
//                        }
//                    } catch (NumberFormatException e) {
//                        System.out.println("error" + e.getMessage());
//                        e.printStackTrace();
//                    }
//                }
//            }
//            
//            Collections.sort(intList);
//            
//            int start = 0;
//            int end = 0;
//            
//            start = intList.get(index++);
//            end = intList.get(index++);
//            
//            while (index < intList.size()) {
//                insertPart(start, end, evk, route, wkFg);
//                start = end;
//                end = intList.get(index++);
//            }
//            
//            insertPart(start, end, evk, route, wkFg);
//            
//            System.out.println("neue wk-fg");
//            commit();
//        }
    }
    
    private void insertPart(int von, int bis, int evk, int route, int wkFg) throws Exception {
        if (evk == 1) {
            List<Integer> mtc = getMtc((int)((bis - von) /2), route, wkFg);
            
            if (mtc == null || mtc.isEmpty()) {
                Integer sg = getStrukturguete((int)((bis - von) /2), route);
                
                if (sg != null && sg.intValue() > 0 && sg.intValue() < 3) {
                    insert(von, bis, route, ZUSTAND_ERHALTEN);
                }
            } else {
                if (hasMtcForGes(mtc)) {
                    insert(von, bis, route, GESTALTEN);
                } else if (hasMtcForEnt(mtc)) {
                    insert(von, bis, route, ENTWICKELN);
                }
            }
        } else {
            List<Integer> mtc = getMtc((int)((bis - von) /2), route, wkFg);
            
            if (mtc == null || mtc.isEmpty()) {
                insert(von, bis, route, BELASSEN);
            } else {
                if (hasMtcForGes(mtc)) {
                    insert(von, bis, route, GESTALTEN);
                } else if (hasMtcForErhEnt(mtc)) {
                    insert(von, bis, route, ENTWICKELN);
                } else if (hasMtcForAnp(mtc)) {
                    insert(von, bis, route, ANPASSEN);
                }
            }
        }
    }
    
    private boolean hasMtcForEnt(List<Integer> mtc) {
        int[] vals = {71,76,77,78,79,85};
        
        for (int i : vals) {
            if (mtc.contains(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasMtcForErhEnt(List<Integer> mtc) {
        int[] vals = {71,76,77,78,85};
        
        for (int i : vals) {
            if (mtc.contains(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasMtcForGes(List<Integer> mtc) {
        int[] vals = {28,29,61,62,63,64,65,66,67,68,69,70,72,73,74,75,93};
        
        for (int i : vals) {
            if (mtc.contains(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasMtcForAnp(List<Integer> mtc) {
        int[] vals = {79};
        
        for (int i : vals) {
            if (mtc.contains(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private Integer getStrukturguete(int wert, int route) throws Exception {
        ResultSet rs = stmt2.executeQuery("select getguetebesserdreibystation(" + route + "," + wert + ")");

        if (rs != null && rs.next()) {
            return rs.getInt(1);
        }
        
        return null;
    }
    
    private List<Integer> getMtc(int wert, int route, int wk_fg) throws Exception {
        ResultSet rs = stmt2.executeQuery("select getmtcbystation(" + route + "," + wert + ", array[''::text])");
        
        if (rs != null && rs.next()) {
            List<Integer> result = new ArrayList<Integer>();
            Array ar = rs.getArray(1);
            String[] strList = (String[])ar.getArray();
            
            for (String tmp : strList) {
                try {
                    int i = Integer.parseInt(tmp);
                    result.add(i);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            
            return result;
        }
        
        return null;
    }
    
    private void insert(int von, int bis, int route, int val) throws Exception {
        boolean noInsertion = false;
        
        if (!data.isEmpty()) {
            InsertionData i = data.get(data.size() - 1);
            
            if (i.getVal() == val && i.getBis() == von) {
                i.setBis(bis);
                noInsertion = true;
            }
        }
        
        if (!noInsertion) {
            InsertionData id = new InsertionData();
            id.setVon(von);
            id.setBis(bis);
            id.setRoute(route);
            id.setVal(val);
            
            data.add(id);
        }
    }
    
    
    private void commit() throws Exception {
        for (InsertionData i : data) {
           System.out.println("von: " + i.getVon() + ", bis: " + i.getBis() + ", route: " + i.getRoute() + ", val: " + i.getVal());
            insert_intern(i.getVon(), i.getBis(), i.getRoute(), i.getVal());
        }
        
        data.clear();
    }

    private void insert_intern(int von, int bis, int route, int val) throws Exception {
        long line = createLine(von, bis, route);
        stmt2.execute("insert into gup_entwicklungsziel (linie, name_bezeichnung) VALUES (" + line + "," + val + ")");
    }
    
    
    private long createLine(int von, int bis, int route) throws Exception {
        long vonStat = createStation(von, route);
        long bisStat = createStation(bis, route);
        
        stmt2.execute("insert into station_linie (von, bis) VALUES (" + vonStat + "," + bisStat + ")", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt2.getGeneratedKeys();
        
        if (rs != null && rs.next()) {
            return rs.getInt("id");
        }
        
        return -1;
    }
    

    private long createStation(int wert, int route) throws Exception {
        stmt2.execute("insert into station (wert, route) VALUES (" + wert + "," + route + ")", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt2.getGeneratedKeys();
        
        if (rs != null && rs.next()) {
            return rs.getLong("id");
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        try {
            new EntwicklungsImport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (stmt != null) {
            stmt.close();
        }
        if (stmt2 != null) {
            stmt2.close();
        }
    }
    
    private class InsertionData {
        private int von;
        private int bis;
        private int route;
        private int val;

        /**
         * @return the von
         */
        public int getVon() {
            return von;
        }

        /**
         * @param von the von to set
         */
        public void setVon(int von) {
            this.von = von;
        }

        /**
         * @return the bis
         */
        public int getBis() {
            return bis;
        }

        /**
         * @param bis the bis to set
         */
        public void setBis(int bis) {
            this.bis = bis;
        }

        /**
         * @return the route
         */
        public int getRoute() {
            return route;
        }

        /**
         * @param route the route to set
         */
        public void setRoute(int route) {
            this.route = route;
        }

        /**
         * @return the val
         */
        public int getVal() {
            return val;
        }

        /**
         * @param val the val to set
         */
        public void setVal(int val) {
            this.val = val;
        }
    }
}
