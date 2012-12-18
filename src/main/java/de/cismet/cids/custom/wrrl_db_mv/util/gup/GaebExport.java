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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.math.BigDecimal;

import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import de.cismet.cids.gaeb.types.GaebContainer;
import de.cismet.cids.gaeb.types.GaebLvItem;
import de.cismet.cids.gaeb.xsd.types.ObjectFactory;
import de.cismet.cids.gaeb.xsd.types.TgAddress;
import de.cismet.cids.gaeb.xsd.types.TgAward;
import de.cismet.cids.gaeb.xsd.types.TgAwardInfo;
import de.cismet.cids.gaeb.xsd.types.TgBoQ;
import de.cismet.cids.gaeb.xsd.types.TgBoQBody;
import de.cismet.cids.gaeb.xsd.types.TgBoQInfo;
import de.cismet.cids.gaeb.xsd.types.TgBoQText;
import de.cismet.cids.gaeb.xsd.types.TgCompleteText;
import de.cismet.cids.gaeb.xsd.types.TgDescription;
import de.cismet.cids.gaeb.xsd.types.TgFText;
import de.cismet.cids.gaeb.xsd.types.TgGAEB;
import de.cismet.cids.gaeb.xsd.types.TgGAEBInfo;
import de.cismet.cids.gaeb.xsd.types.TgItem;
import de.cismet.cids.gaeb.xsd.types.TgItemlist;
import de.cismet.cids.gaeb.xsd.types.TgMLText;
import de.cismet.cids.gaeb.xsd.types.TgOWN;
import de.cismet.cids.gaeb.xsd.types.TgOutlTxt;
import de.cismet.cids.gaeb.xsd.types.TgOutlineText;
import de.cismet.cids.gaeb.xsd.types.TgPrjInfo;
import de.cismet.cids.gaeb.xsd.types.TgYes;
import de.cismet.cids.gaeb.xsd.types.TgYesNo;
import de.cismet.cids.gaeb.xsd.types.Tgp;
import de.cismet.cids.gaeb.xsd.types.Tgspan;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GaebExport {

    //~ Instance fields --------------------------------------------------------

// private static final boolean BIETERKOMMENTAR = true;
// private static final boolean NEBENANGEBOT = true;

    private final ObjectFactory factory = new ObjectFactory();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GaebExport object.
     */
    public GaebExport() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   gaebContainer  massnBeans DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  DatatypeConfigurationException  DOCUMENT ME!
     * @throws  JAXBException                   DOCUMENT ME!
     * @throws  IOException                     DOCUMENT ME!
     */
    public String startExport(final GaebContainer gaebContainer) throws DatatypeConfigurationException,
        JAXBException,
        IOException {
        final DatatypeFactory df = DatatypeFactory.newInstance();
        final GregorianCalendar now = new GregorianCalendar();
        final GregorianCalendar versDate = new GregorianCalendar(2007, 5, 1);
        final TgGAEB gaeb = factory.createTgGAEB();
        final TgGAEBInfo gaebInfo = factory.createTgGAEBInfo();
        final TgPrjInfo prjInfo = factory.createTgPrjInfo();
        final TgAward award = factory.createTgAward();
        final TgOWN own = factory.createTgOWN();
        gaeb.setGAEBInfo(gaebInfo);
        gaeb.setPrjInfo(prjInfo);
        gaeb.setAward(award);

        gaebInfo.setVersion("3.1");
        gaebInfo.setVersDate(df.newXMLGregorianCalendar(versDate));
        gaebInfo.setDate(df.newXMLGregorianCalendarDate(
                now.get(GregorianCalendar.YEAR),
                now.get(GregorianCalendar.MONTH)
                        + 1,
                now.get(GregorianCalendar.DAY_OF_MONTH),
                DatatypeConstants.FIELD_UNDEFINED));
        gaebInfo.setTime(df.newXMLGregorianCalendarTime(
                now.get(GregorianCalendar.HOUR_OF_DAY),
                now.get(GregorianCalendar.MINUTE),
                now.get(GregorianCalendar.SECOND),
                DatatypeConstants.FIELD_UNDEFINED));
        gaebInfo.setProgSystem("cids GAEB tool");
        gaebInfo.setProgName("FIS Navigator");

        prjInfo.setNamePrj(gaebContainer.getProjectName());
        prjInfo.setDescrip(getSimpleText(gaebContainer.getDescription()));
        prjInfo.setBidCommPerm(getYesNo(gaebContainer.isBieterKommentar()));

        if (gaebContainer.isNebenangebot()) {
            prjInfo.setAlterBidPerm(TgYes.YES);
        }

        prjInfo.setCur("EUR");
        prjInfo.setCurLbl("Euro");

        final TgAwardInfo awardInfo = factory.createTgAwardInfo();
        award.setDP("81");
        award.setAwardInfo(awardInfo);
        awardInfo.setCur("EUR");
        awardInfo.setCurLbl("Euro");
        award.setOWN(own);
        // Beschreibung der Attribute auf Seite 28 der Praesentation
        // todo: Weitere Vergabeinfos eintragen

        final TgAddress address = factory.createTgAddress();
        // todo: Adresse des Auftraggebers hinufuegen (ist das fuer alle Lose gleich oder nicht?)
        own.setAddress(address);

        final TgBoQ boq = factory.createTgBoQ();
        award.setBoQ(boq);

        final TgBoQInfo boqInfo = factory.createTgBoQInfo();
        boq.setBoQInfo(boqInfo);

        // TODO: set boqInfo

        final TgBoQBody body = factory.createTgBoQBody();
        final TgItemlist itemList = factory.createTgItemlist();
        body.setItemlist(itemList);

        for (final GaebLvItem tmp : gaebContainer.getItemList()) {
            itemList.getRemarkOrPerfDescrOrItem().add(getItem(tmp));
        }

        boq.setBoQBody(body);
        String result = new String(marshall(gaeb));
        result = result.replace("xmlns:ns2", "xmlns");
        result = result.replace("ns2:", "");
        result = result.replace(
                "xmlns=\"http://www.w3.org/2000/09/xmldsig#\"",
                "xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"");

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   gaebItem  id DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private TgItem getItem(final GaebLvItem gaebItem) {
        final TgItem item = factory.createTgItem();
        item.setID(gaebItem.getId());
        final JAXBElement<BigDecimal> menge = factory.createTgItemQty(gaebItem.getAmount());
        final JAXBElement<String> einheit = factory.createTgItemQU(gaebItem.getMeasure());
        final TgDescription description = factory.createTgDescription();

        final TgOutlineText kurzbeschreibung = factory.createTgOutlineText();
        final TgCompleteText langbeschreibung = factory.createTgCompleteText();
        langbeschreibung.setOutlineText(kurzbeschreibung);

        final TgOutlTxt text = factory.createTgOutlTxt();
        final TgMLText mlText = factory.createTgMLText();
        text.getTextOutlTxtOrTextComplement().add(mlText);
        mlText.getPOrDivOrSpan().add(getSpan(gaebItem.getDescriptionShort()));
        kurzbeschreibung.setOutlTxt(text);
        final TgBoQText langText = factory.createTgBoQText();
        langText.getTextOrTextComplementOrAttachment()
                .add(factory.createTgBoQTextText(getSimpleText(gaebItem.getDescription())));
        langbeschreibung.setDetailTxt(langText);

        description.setCompleteText(langbeschreibung);
        description.setOutlineText(kurzbeschreibung);
        item.getContent().add(menge);
        item.getContent().add(einheit);
        item.getContent().add(factory.createTgItemDescription(description));
        item.setRNoPart(String.valueOf(gaebItem.getOz()));
        return item;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   string  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private TgFText getSimpleText(final String string) {
        final TgFText text = factory.createTgFText();
        final Tgp tp = factory.createTgp();
        tp.getSpanOrBrOrImage().add(getSpan(string));
        final JAXBElement p = factory.createTgFTextP(tp);
        text.getPOrDivOrSpan().add(p);

        return text;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JAXBElement<Tgspan> getSpan(final String text) {
        final Tgspan span = factory.createTgspan();
        span.setValue(text);
        return factory.createTgFTextSpan(span);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   yes  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private TgYesNo getYesNo(final boolean yes) {
        return (yes ? TgYesNo.YES : TgYesNo.NO);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   gaeb  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  JAXBException  DOCUMENT ME!
     */
    private byte[] marshall(final TgGAEB gaeb) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance("de.cismet.cids.gaeb.xsd.types");
        final Marshaller marshaller = context.createMarshaller();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(factory.createGAEB(gaeb), os);
        return os.toByteArray();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
//        final CidsBean cidsBean = DevelopmentTools.createCidsBeanFromRMIConnectionOnLocalhost(
//                "WRRL_DB_MV",
//                "Administratoren",
//                "admin",
//                "cismet",
//                "GUP_LOS",
//                56);
//        final CidsServerSearch search = new MassnahmenGaebSearch(cidsBean.getProperty("id").toString());
//        final ArrayList<ArrayList> massnBeans = DevelopmentTools.executeServerSearch(
//                "WRRL_DB_MV",
//                "Administratoren",
//                "admin",
//                "cismet",
//                search);
//        final GaebExport ex = new GaebExport();
//        final GaebContainer c = new GaebContainer();
//
//        final List<GaebLvItem> l = new ArrayList<GaebLvItem>();
//
//        if (massnBeans != null) {
//            for (final ArrayList al : massnBeans) {
//                final GaebLvItem item = new GaebLvItem();
//                item.setId(((Integer)al.get(0)).toString());
//                item.setAmount(new BigDecimal((Double)al.get(1)));
//                item.setMeasure(String.valueOf(al.get(2)));
//                item.setDescription(String.valueOf(al.get(3)));
//                item.setDescriptionShort(String.valueOf(al.get(4)));
//                l.add(item);
//            }
//        }
//        c.setItemList(l);
//        c.setProjectName(cidsBean.getProperty("bezeichnung").toString());
//        c.setNebenangebot(true);
//        c.setBieterKommentar(true);
//        final String result = ex.startExport(c);
//
//        final FileWriter fw = new FileWriter(new File("/home/therter/share/test.x81"));
//        final BufferedWriter bw = new BufferedWriter(fw);
//        bw.write(result);
//        bw.close();
    }
}
