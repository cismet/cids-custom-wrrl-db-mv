/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2013 therter
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
package de.cismet.cids.custom.reports;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.openide.util.Exceptions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupLosEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnBean;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnLegendBean;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnStationComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.RulerBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.RulerLabelBand;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.HeadlessMapProvider;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultXStyledFeature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.commons.gui.piccolo.FixedWidthStroke;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;

import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.Band;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class GeppReport extends AbstractJasperReportPrint implements ProgressMonitorHandler {

    //~ Static fields/initializers ---------------------------------------------

    public static final String BOESCHUNGSBREITE = "Bn";
    public static final String BOESCHUNGSLAENGE = "Bl";
    public static final String CBMPROM = "Cm";
    public static final String DEICHKRONENBREITE = "Db";
    public static final String RANDSTREIFENBREITE = "Rs";
    public static final String SCHNITTTIEFE = "Schnitt";
    public static final String SOHLBREITE = "Sb";
    public static final String STUECK = "St.";
    public static final String STUNDE = "h";
    public static final String VORLANDBREITE = "Vb";
    static final int MAX_WIDTH = 197;
    static final int PARA_SPACE = 3;

    private static final String REPORT_URL = "/de/cismet/cids/custom/reports/gepp_pruefer.jasper";
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_unterhaltungsmassnahme");
    private static final Logger LOG = Logger.getLogger(GeppReport.class);
    private static final DateFormat DF = new SimpleDateFormat("dd.MM.yyyy");

    //~ Instance fields --------------------------------------------------------

    private JBand band = null;
    private CidsBean bean = null;
    private RulerBand ruler = new RulerBand(0, 5000);
    private BufferedImage bandImage;
    private int bandHeight = 219;

    private List<CidsBean> all;
    private List<CidsBean> rechtesUferList;
    private List<CidsBean> sohleList;
    private List<CidsBean> linkesUferList;
    private List<CidsBean> rechtesUmfeldList;
    private List<CidsBean> linkesUmfeldList;
    private ProgressMonitor monitor;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GeppReport object.
     *
     * @param  parent          DOCUMENT ME!
     * @param  bean            DOCUMENT ME!
     * @param  band            parent DOCUMENT ME!
     * @param  massnBandArray  DOCUMENT ME!
     */
    public GeppReport(final JFrame parent,
            final CidsBean bean,
            final JBand band,
            final MassnahmenBand[] massnBandArray) {
        super(parent, REPORT_URL, bean);
        setBeansCollection(false);
        this.bean = bean;

        fillMassnArrays(bean);

        if (band != null) {
            final RulerLabelBand rulerLabel = new RulerLabelBand(band.getModel().getMin(), band.getModel().getMax());
            ruler = new RulerBand(band.getModel().getMin(), band.getModel().getMax());
            ((SimpleBandModel)band.getModel()).insertBand(rulerLabel, 0);
            ((SimpleBandModel)band.getModel()).insertBand(ruler, 1);
            this.band = band;
            band.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            // show band numbers
            int number = 1;
            massnBandArray[0].showNumbers(number);
            number += rechtesUferList.size();
            massnBandArray[1].showNumbers(number);
            number += linkesUferList.size();
            massnBandArray[2].showNumbers(number);
            number += sohleList.size();
            massnBandArray[3].showNumbers(number);
            number += rechtesUmfeldList.size();
            massnBandArray[4].showNumbers(number);

            try {
                bandImage = componentToImage(band);
            } catch (Exception e) {
                LOG.error("Error while creating image from band", e);
            }

            massnBandArray[0].hideNumbers();
            massnBandArray[1].hideNumbers();
            massnBandArray[2].hideNumbers();
            massnBandArray[3].hideNumbers();
            massnBandArray[4].hideNumbers();
            ((SimpleBandModel)band.getModel()).removeBand(ruler);
            ((SimpleBandModel)band.getModel()).removeBand(rulerLabel);
            band.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        }
    }

    //~ Methods ----------------------------------------------------------------

// /**
// * Creates a new GeppReport object.
// *
// * @param  parent  DOCUMENT ME!
// * @param  bean    DOCUMENT ME!
// * @param  model   band DOCUMENT ME!
// */
// public GeppReport(final JComponent parent, final CidsBean bean, final BandModel model) {
// super(REPORT_URL, bean);
// setBeansCollection(false);
// this.bean = bean;
//
// if (model != null) {
// final SimpleBandModel reportModel = (SimpleBandModel)((SimpleBandModel)model).copy();
// final RulerBand ruler = new RulerBand(reportModel.getMin(), reportModel.getMax());
// reportModel.insertBand(ruler, 1);
// band = new JBand();
// band.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
// parent.add(band);
// band.setSize(605, 400);
// band.setModel(reportModel);
// reportModel.fireBandModelChanged();
// reportModel.fireBandModelValuesChanged();
// }
// }

    /**
     * DOCUMENT ME!
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String toString(final Object obj) {
        if (obj == null) {
            return "";
        }

        return String.valueOf(obj);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  current  DOCUMENT ME!
     */
    private void fillMassnArrays(final CidsBean current) {
        all = current.getBeanCollectionProperty("massnahmen");
        rechtesUferList = new ArrayList<CidsBean>();
        sohleList = new ArrayList<CidsBean>();
        linkesUferList = new ArrayList<CidsBean>();
        rechtesUmfeldList = new ArrayList<CidsBean>();
        linkesUmfeldList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : all) {
            final Integer kind = (Integer)tmp.getProperty("wo.id");

            switch (kind) {
                case GupPlanungsabschnittEditor.GUP_UFER_LINKS: {
                    linkesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UFER_RECHTS: {
                    rechtesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UMFELD_LINKS: {
                    linkesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS: {
                    rechtesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_SOHLE: {
                    sohleList.add(tmp);
                    break;
                }
            }
        }
    }

    @Override
    public Map generateReportParam(final CidsBean current) {
        final HashMap params = new HashMap();
        final List<CidsBean> list = new ArrayList<CidsBean>();
        list.add(current);

        try {
            final long time = System.currentTimeMillis();
            bandImage = removeTransparentLeftMargin(bandImage);
            LOG.error("time " + (System.currentTimeMillis() - time));
            bandHeight = (int)(bandImage.getHeight(null) * 605 / bandImage.getWidth(null));
//            bandHeight *= 1.4;
        } catch (Exception e) {
            LOG.error("Error while creating image from band", e);
        }

        params.put("currentDate", DF.format(new Date()));
        params.put("geppName", getGeppName(current));
        params.put("planungsabschnitt", list);

        int number = 0;
        params.put("uferRechts", getMassnBeans(rechtesUferList, number));
        number += rechtesUferList.size();
        params.put("uferLinks", getMassnBeans(linkesUferList, number));
        number += linkesUferList.size();
        params.put("sohle", getMassnBeans(sohleList, number));
        number += sohleList.size();
        params.put("umfeldRechts", getMassnBeans(rechtesUmfeldList, number));
        number += rechtesUmfeldList.size();
        params.put("umfeldLinks", getMassnBeans(linkesUmfeldList, number));

        params.put("massnLegend", getMassnLegendBeans(all));

        try {
            final InputStream is = getClass().getResourceAsStream("/de/cismet/cids/custom/reports/GeppLegend.jpg");
            final BufferedImage mapLegendImg = ImageIO.read(is);

            if (band != null) {
                params.put("map", generateMap());
                params.put("legend", mapLegendImg);
                params.put("band", bandImage);
                params.put("bandLegend", createMassnLegend(all));
            } else {
                // zum Testen
                File img = new File("/home/therter/TestMap.jpg");
                final BufferedImage imgMap = ImageIO.read(img);
                img = new File("/home/therter/testBand.jpg");
                final BufferedImage imgBand = ImageIO.read(img);
                params.put("map", generateMap());
                params.put("legend", mapLegendImg);
                params.put("band", imgBand);
                params.put("bandLegend", createMassnLegend(all));
//                params.put("bandLegend", createMassnLegend(all));
            }
        } catch (Exception e) {
            LOG.error("Error while filling the parameters.", e);
        }
        return params;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getGeppName(final CidsBean bean) {
        String geppName = toString(bean.getProperty("name"));
        geppName += " [" + String.valueOf(bean.getProperty("linie.von.wert"));
        geppName += ", " + String.valueOf(bean.getProperty("linie.bis.wert")) + "]";

        return geppName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBeans    DOCUMENT ME!
     * @param   startNumber  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<MassnBean> getMassnBeans(final List<CidsBean> cidsBeans, final int startNumber) {
        final List<MassnBean> beans = new ArrayList<MassnBean>();

        if (cidsBeans != null) {
            final List<CidsBean> sortedCidsBeans = new ArrayList<CidsBean>(cidsBeans.size());
            sortedCidsBeans.addAll(cidsBeans);
            Collections.sort(sortedCidsBeans, new MassnStationComparator());
            int number = 0;

            for (final CidsBean tmp : sortedCidsBeans) {
                final MassnBean mBean = new MassnBean();
                mBean.setMassnahme((String)tmp.getProperty("massnahme.massnahmen_id"));
                mBean.setStationierung(extractStats(tmp));
                mBean.setBemerkung(extractBemerkung(tmp));
                mBean.setNummer(startNumber + (++number));

                try {
                    final Double a = GupLosEditor.calcMenge(GupLosEditor.convertToList(tmp),
                            GupLosEditor.getAufmassRegel(tmp));

                    if (a != null) {
                        mBean.setMenge(a + " " + GupLosEditor.getEinheit(tmp));
                    } else {
                        mBean.setMenge("Fehler");
                    }
                } catch (Exception e) {
                    mBean.setMenge("Fehler");
                }
                beans.add(mBean);
            }
        }

        return beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBeans  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<MassnLegendBean> getMassnLegendBeans(final List<CidsBean> cidsBeans) {
        final List<MassnLegendBean> beans = new ArrayList<MassnLegendBean>();

        if (cidsBeans != null) {
            final List<CidsBean> sortedCidsBeans = new ArrayList<CidsBean>(cidsBeans.size());
            sortedCidsBeans.addAll(cidsBeans);

            for (final CidsBean tmp : sortedCidsBeans) {
                final MassnLegendBean mBean = new MassnLegendBean();
                mBean.setMassnahme(toString(tmp.getProperty("massnahme.massnahmen_id")));
                mBean.setGewerk(toString(tmp.getProperty("massnahme.gewerk.name")));
                mBean.setEinsatzvariante(toString(tmp.getProperty("massnahme.einsatzvariante.name")));
                mBean.setVerbleib(toString(tmp.getProperty("massnahme.verbleib.name")));
                mBean.setGeraet(toString(tmp.getProperty("massnahme.geraet.name")));

                if (!mBean.getMassnahme().equals("") && !beans.contains(mBean)) {
                    beans.add(mBean);
                }
            }
        }

        Collections.sort(beans);
        return beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBeans  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Image createMassnLegend(final List<CidsBean> cidsBeans) {
        final List<CidsBean> sortedCidsBeans = new ArrayList<CidsBean>();

        if (cidsBeans != null) {
            for (final CidsBean tmp : cidsBeans) {
                final CidsBean tmpArt = (CidsBean)tmp.getProperty("massnahme");

                if ((tmpArt != null) && (tmpArt.getProperty("kompartiment.id") != null)
                            && (tmpArt.getProperty("massnahmen_id") != null)
                            && !sortedCidsBeans.contains(tmpArt)) {
                    sortedCidsBeans.add(tmpArt);
                }
            }

            Collections.sort(sortedCidsBeans, new MassnArtComparator());
        }

        int titleSize = 11;
        int textSize = 9;
//        int titleSize = 17;
//        int textSize = 15;
        final int textStart = 30;

        int y;

        do {
            titleSize += 1;
            textSize += 1;
            y = titleSize - PARA_SPACE;
            y = drawLegendIntro(null, textSize, textStart, y, null, false);
            y = drawLegendMassn(sortedCidsBeans, null, titleSize, textSize, textStart, y, null, false);
        } while ((y < bandHeight) && (textSize < 15));

        titleSize -= 1;
        textSize -= 1;

        final BufferedImage img = new BufferedImage(MAX_WIDTH,
                y,
                BufferedImage.TYPE_INT_ARGB_PRE);

        final Graphics g = img.getGraphics();
        final Font basicFont = new Font("Arial", Font.PLAIN, titleSize - PARA_SPACE);

        y = titleSize - PARA_SPACE;
        y = drawLegendIntro(basicFont, textSize, textStart, y, g, true);
        y = drawLegendMassn(sortedCidsBeans, basicFont, titleSize, textSize, textStart, y, g, true);

        return img;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   basicFont  DOCUMENT ME!
     * @param   textSize   DOCUMENT ME!
     * @param   textStart  DOCUMENT ME!
     * @param   y          DOCUMENT ME!
     * @param   g          DOCUMENT ME!
     * @param   draw       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int drawLegendIntro(final Font basicFont,
            final int textSize,
            final int textStart,
            int y,
            final Graphics g,
            final boolean draw) {
        if (draw) {
            g.setColor(Color.BLACK);
            g.setFont(basicFont.deriveFont(Font.BOLD));
            g.drawString("Legende", 5, y);
        }

        // draw legend for invalid parts
        try {
            y = y + textSize;

            if (draw) {
                g.setFont(basicFont.deriveFont(textSize - PARA_SPACE));
                final InputStream is = getClass().getResourceAsStream("/de/cismet/cids/custom/reports/invalid.png");
                final BufferedImage invalidImg = ImageIO.read(is);

                g.drawImage(invalidImg, 1, y - (textSize / 2), null);
                g.setColor(Color.BLACK);
                g.drawString("invalide Maßnahme", textStart, y);
            }
        } catch (Exception e) {
            LOG.error("Cannot add the invalid representation to the legend.", e);
        }

        // draw ruler space
        y = y + textSize;

        if (draw) {
            g.setFont(basicFont.deriveFont(textSize - PARA_SPACE));
            g.drawLine(8, y - (textSize / 2) + (textSize / 3), 8, y - (textSize / 2) - (textSize / 3));
            g.drawLine(9, y - (textSize / 2) + (textSize / 3), 9, y - (textSize / 2) - (textSize / 3));
            g.drawLine(13, y - (textSize / 2) + (textSize / 3), 13, y - (textSize / 2) - (textSize / 3));
            g.drawLine(14, y - (textSize / 2) + (textSize / 3), 14, y - (textSize / 2) - (textSize / 3));
            g.setColor(Color.BLACK);
            g.drawString(ruler.getDistance() + " m", textStart, y);
        }

        return y;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   sortedCidsBeans  DOCUMENT ME!
     * @param   basicFont        DOCUMENT ME!
     * @param   titleSize        DOCUMENT ME!
     * @param   textSize         DOCUMENT ME!
     * @param   textStart        DOCUMENT ME!
     * @param   y                DOCUMENT ME!
     * @param   g                DOCUMENT ME!
     * @param   draw             DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int drawLegendMassn(final List<CidsBean> sortedCidsBeans,
            final Font basicFont,
            final int titleSize,
            final int textSize,
            final int textStart,
            int y,
            final Graphics g,
            final boolean draw) {
        CidsBean komp = null;

        for (final CidsBean tmp : sortedCidsBeans) {
            if ((komp == null) || !komp.equals((CidsBean)tmp.getProperty("kompartiment"))) {
                komp = (CidsBean)tmp.getProperty("kompartiment");
                y = y + titleSize;

                if (draw) {
                    g.setFont(basicFont);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(tmp.getProperty("kompartiment.name")), 5, y);
                    g.setFont(basicFont.deriveFont(textSize - PARA_SPACE));
                }
            }

            y = y + textSize;

            if (draw) {
                try {
                    g.setColor(Color.decode((String)tmp.getProperty("color")));
                } catch (Exception e) {
                    LOG.error("Cannot handle colour code.", e);
                }
                g.drawLine(1, y - (textSize / 2), 25, y - (textSize / 2));
                g.drawLine(1, y - (textSize / 2) + 1, 25, y - (textSize / 2) + 1);
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(tmp.getProperty("massnahmen_id")), textStart, y);
            }
        }

        return y;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String extractStats(final CidsBean cBean) {
        final double von = (Double)cBean.getProperty("linie.von.wert");
        final double bis = (Double)cBean.getProperty("linie.bis.wert");

        return ((int)von) + " - " + ((int)bis);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String extractBemerkung(final CidsBean cBean) {
        final StringBuilder bemerkungen = new StringBuilder();

        final Boolean bl = (Boolean)cBean.getProperty("massnahme.boeschungslaenge");
        final Boolean bn = (Boolean)cBean.getProperty("massnahme.boeschungsneigung");
        final Boolean db = (Boolean)cBean.getProperty("massnahme.deichkronenbreite");
        final Boolean rs = (Boolean)cBean.getProperty("massnahme.randstreifenbreite");
        final Boolean sb = (Boolean)cBean.getProperty("massnahme.sohlbreite");
        final Boolean vb = (Boolean)cBean.getProperty("massnahme.vorlandbreite");
        final Boolean cm = (Boolean)cBean.getProperty("massnahme.cbmprom");
        final Boolean st = (Boolean)cBean.getProperty("massnahme.stueck");
        final Boolean stu = (Boolean)cBean.getProperty("massnahme.stunden");
        final Boolean sch = (Boolean)cBean.getProperty("massnahme.schnitttiefe");

        if ((bl != null) && bl) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(BOESCHUNGSLAENGE + ": " + toString(cBean.getProperty("boeschungslaenge")));
        }

        if ((bn != null) && bn) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(BOESCHUNGSBREITE + ": " + toString(cBean.getProperty("boeschungsbreite")));
        }

        if ((db != null) && db) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(DEICHKRONENBREITE + ": " + toString(cBean.getProperty("deichkronenbreite")));
        }

        if ((rs != null) && rs) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(RANDSTREIFENBREITE + ": " + toString(cBean.getProperty("randstreifenbreite")));
        }

        if ((sb != null) && sb) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(SOHLBREITE + ": " + toString(cBean.getProperty("sohlbreite")));
        }

        if ((vb != null) && vb) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(VORLANDBREITE + ": " + toString(cBean.getProperty("vorlandbreite")));
        }

        if ((cm != null) && cm) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(CBMPROM + ": " + toString(cBean.getProperty("cbmprom")));
        }

        if ((st != null) && st) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(STUECK + ": " + toString(cBean.getProperty("stueck")));
        }

        if ((stu != null) && stu) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(STUNDE + ": " + toString(cBean.getProperty("stunden")));
        }

        if ((sch != null) && sch) {
            if (!bemerkungen.toString().equals("")) {
                bemerkungen.append(", ");
            }
            bemerkungen.append(SCHNITTTIEFE + ": " + toString(cBean.getProperty("schnitttiefe")));
        }

        final String hints = toString(cBean.getProperty("hinweise"));
        if (!hints.equals("")) {
            bemerkungen.append(", ");
        }

        bemerkungen.append(hints);

        final String auflagen = toString(cBean.getProperty("auflagen"));

        if (!auflagen.equals("")) {
            bemerkungen.append(", ");
        }

        bemerkungen.append(auflagen);

        return bemerkungen.toString();
    }

    /**
     * Draws an image from a component.
     *
     * @param   component  DOCUMENT ME!
     *
     * @return  the given component as image
     */
    private BufferedImage componentToImage(final JComponent component) {
        layoutComponent(component);
        final BufferedImage img = new BufferedImage(component.getWidth(),
                component.getHeight(),
                BufferedImage.TYPE_INT_ARGB_PRE);
        final Graphics g = img.getGraphics();
        g.setColor(component.getForeground());
        g.setFont(component.getFont());
        component.paintAll(g);

        return img.getSubimage(0, 0, img.getWidth(), img.getHeight());
    }

    /**
     * Draws an image from a component.
     *
     * @param   image  component DOCUMENT ME!
     *
     * @return  the given component as image
     *
     * @throws  IOException  DOCUMENT ME!
     */
    private BufferedImage removeTransparentLeftMargin(final BufferedImage image) throws IOException {
        int x = 0;

        for (x = image.getData().getMinX(); x < (image.getData().getMinX() + image.getData().getWidth()); ++x) {
            boolean isEmpty = true;

            for (int y = image.getData().getMinY(); y < (image.getData().getMinY() + image.getData().getHeight());
                        y = y + 3) {
                final double[] d = image.getData().getPixel(x, y, (double[])null);
                if (d[3] != 0) {
                    isEmpty = false;
                    break;
                }
            }

            if (monitor != null) {
                if (x < 100) {
                    monitor.setProgress(x);
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        return image.getSubimage(x, 0, image.getWidth() - x, image.getHeight());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  component  DOCUMENT ME!
     */
    private void layoutComponent(final Component component) {
        synchronized (component.getTreeLock()) {
            component.doLayout();

            if (component instanceof Container) {
                for (final Component child : ((Container)component).getComponents()) {
                    layoutComponent(child);
                }
            }
        }
    }

    @Override
    public Map generateReportParam(final Collection<CidsBean> beans) {
        return Collections.EMPTY_MAP;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            final CidsBean cBean;
            cBean = DevelopmentTools.createCidsBeanFromRMIConnectionOnLocalhost(
                    "WRRL_DB_MV",
                    "Administratoren",
                    "admin",
                    "kif",
                    "gup_planungsabschnitt",
//                    72);
                    19);
            System.out.println("create report");
            CismapBroker.getInstance().setDefaultCrsAlias(-1);
            CismapBroker.getInstance().setDefaultCrs("EPSG:35833");
            final GeppReport report = new GeppReport(null, cBean, null, null);
            report.print();
            System.out.println("report created");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Image generateMap() {
        try {
            final String mapUrl = "http://www.geodaten-mv.de/dienste/gdimv_topomv"
                        + "?REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&LAYERS=gdimv_topomv"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&SRS=EPSG:35833&FORMAT=image/png"
                        + "&WIDTH=<cismap:width>"
                        + "&HEIGHT=<cismap:height>"
                        + "&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage";

            final String overlayUrl = "http://wms.fis-wasser-mv.de/services?&VERSION=1.1.1&REQUEST=GetMap"
                        + "&BBOX=<cismap:boundingBox>"
                        + "&WIDTH=<cismap:width>&HEIGHT=<cismap:height>"
                        + "&SRS=EPSG:35833&FORMAT=image/png&TRANSPARENT=true&BGCOLOR=0xF0F0F0"
                        + "&EXCEPTIONS=application/vnd.ogc.se_xml&LAYERS=gepp&STYLES=default";
//            final String overlayUrl = "http://wms.fis-wasser-mv.de/services?&VERSION=1.1.1&REQUEST=GetMap"
//                        + "&BBOX=<cismap:boundingBox>"
//                        + "&WIDTH=<cismap:width>&HEIGHT=<cismap:height>"
//                        + "&SRS=EPSG:35833&FORMAT=image/png&TRANSPARENT=true&BGCOLOR=0xF0F0F0"
//                        + "&EXCEPTIONS=application/vnd.ogc.se_xml&LAYERS=route_stat_big&STYLES=default";

            final Geometry geom = (Geometry)bean.getProperty("linie.geom.geo_field");

            final HeadlessMapProvider mapProvider = new HeadlessMapProvider();
            SimpleWmsGetMapUrl url = new SimpleWmsGetMapUrl(mapUrl);
            SimpleWMS wms = new SimpleWMS(url);
            mapProvider.addLayer(wms);
            url = new SimpleWmsGetMapUrl(overlayUrl);
            wms = new SimpleWMS(url);
            mapProvider.addLayer(wms);

            final XBoundingBox boundingBox;
            final CustomFixedWidthStroke stroke = new CustomFixedWidthStroke(5);
            final DefaultXStyledFeature feature = new DefaultXStyledFeature(null, null, null, null, stroke);
            feature.setLinePaint(new Color(0, 0, 255, 128));
//            PureNewFeature feature = new PureNewFeature(geom);
            feature.setGeometry(geom);
            feature.setTransparency(0.5f);
            mapProvider.addFeature(feature);

            if (CismapBroker.getInstance().getMappingComponent() != null) {
                boundingBox = new XBoundingBox(geom);
                boundingBox.setX1(boundingBox.getX1() - 50);
                boundingBox.setY1(boundingBox.getY1() - 50);
                boundingBox.setX2(boundingBox.getX2() + 50);
                boundingBox.setY2(boundingBox.getY2() + 50);
            } else {
                // only for test purposes
                boundingBox = new XBoundingBox(
                        33298653.1,
                        5994912.610934,
                        33308958.598,
                        5999709.97916,
                        "EPSG:35833",
                        true);
            }

            mapProvider.setBoundingBox(boundingBox);
            final Future<Image> imageFuture = mapProvider.getImage(72, 125, 600, 400);
            return imageFuture.get();
                /*
                 * final BufferedImage map = fetchMap(mapUrl, geom, size); final BufferedImage overlay =
                 * fetchMap(overlayUrl, geom, size);
                 *
                 * return mergeImages(map, overlay);
                 */
        } catch (Exception ex) {
            LOG.fatal("error", ex);
            return null;
        }
    }

    @Override
    public void setMonitor(final ProgressMonitor monitor) {
        this.monitor = monitor;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class SignallingRetrievalListener implements RetrievalListener {

        //~ Instance fields ----------------------------------------------------

        private BufferedImage image = null;
        private Lock lock;
        private Condition condition;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SignallingRetrievalListener object.
         *
         * @param  lock       DOCUMENT ME!
         * @param  condition  DOCUMENT ME!
         */
        public SignallingRetrievalListener(final Lock lock, final Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void retrievalStarted(final RetrievalEvent e) {
        }

        @Override
        public void retrievalProgress(final RetrievalEvent e) {
        }

        @Override
        public void retrievalComplete(final RetrievalEvent e) {
            if (e.getRetrievedObject() instanceof Image) {
                final Image retrievedImage = (Image)e.getRetrievedObject();
                image = new BufferedImage(
                        retrievedImage.getWidth(null),
                        retrievedImage.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                final Graphics2D g = (Graphics2D)image.getGraphics();
                g.drawImage(retrievedImage, 0, 0, null);
                g.dispose();
            }
            signalAll();
        }

        @Override
        public void retrievalAborted(final RetrievalEvent e) {
            signalAll();
        }

        @Override
        public void retrievalError(final RetrievalEvent e) {
            signalAll();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public BufferedImage getRetrievedImage() {
            return image;
        }

        /**
         * DOCUMENT ME!
         */
        private void signalAll() {
            lock.lock();
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MassnArtComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            final Integer kompId1 = (Integer)o1.getProperty("kompartiment.id");
            final Integer kompId2 = (Integer)o2.getProperty("kompartiment.id");

            if (kompId1 == kompId2) {
                final String massnahmeId1 = (String)o1.getProperty("massnahmen_id");
                final String massnahmeId2 = (String)o2.getProperty("massnahmen_id");

                if ((massnahmeId1 != null) && (massnahmeId2 != null)) {
                    return massnahmeId1.compareTo(massnahmeId2);
                } else {
                    if ((massnahmeId1 == null) && (massnahmeId2 == null)) {
                        return 0;
                    } else if (massnahmeId1 == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            } else {
                if (kompId1 == null) {
                    return -1;
                } else if (kompId2 == null) {
                    return 1;
                } else {
                    return (int)Math.signum(kompId1 - kompId2);
                }
            }
        }
    }
}
