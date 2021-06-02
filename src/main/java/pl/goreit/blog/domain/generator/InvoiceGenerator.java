package pl.goreit.blog.domain.generator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import pl.goreit.blog.domain.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class InvoiceGenerator {

    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;

    private Order order;
    private Company company;
    private Person person;
    private Integer counter;

    public InvoiceGenerator(Order order, Workshop workshop, Person person) {
        this.order = order;
        this.company = workshop.getCompany();
        this.person = person;
        this.counter = workshop.getInvoiceCounter();
    }

    public void generate(String pdfFilename) {

        this.createPDF(pdfFilename);
    }

    private void createPDF(String pdfFilename) {

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();

        try {
            String path = "docs/"+company.getNip()+"/" + pdfFilename;
            File pathDkic = new File("docs/"+company.getNip() );
            if ( !pathDkic.exists()){
                pathDkic.mkdir();
            }
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.LETTER);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();

            boolean beginPage = true;
            int y = 0;

            for (int i = 0; i < order.getOrderLines().size(); i++) {
                if (beginPage) {
                    beginPage = false;
                    generateLayout(doc, cb);
                    generateHeader(doc, cb);
                    y = 615;
                }
                generateDetail(doc, cb, i, y, order.getOrderLines().get(i));
                y = y - 15;
                if (y < 50) {
                    printPageNumber(cb);
                    doc.newPage();
                    beginPage = true;
                }
            }
            printPageNumber(cb);
            printSum(cb);

        } catch (Exception dex) {
            dex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }

    private void generateLayout(Document doc, PdfContentByte cb) {

        try {

            cb.setLineWidth(1f);

//            // Invoice Header box layout
//            cb.rectangle(420, 700, 150, 60);
//            cb.moveTo(420, 720);
//            cb.lineTo(570, 720);
//            cb.moveTo(420, 740);
//            cb.lineTo(570, 740);
//            cb.moveTo(480, 700);
//            cb.lineTo(480, 760);
//            cb.stroke();

            // Invoice Detail box layout
            cb.rectangle(20, 50, 550, 600);
            cb.moveTo(20, 630);
            cb.lineTo(570, 630);
            cb.moveTo(50, 50);
            cb.lineTo(50, 650);
            cb.moveTo(150, 50);
            cb.lineTo(150, 650);
            cb.moveTo(430, 50);
            cb.lineTo(430, 650);
            cb.moveTo(500, 50);
            cb.lineTo(500, 650);
            cb.stroke();

            // Invoice Detail box Text Headings
            createHeadings(cb, 22, 633, "Nr");
            createHeadings(cb, 52, 633, "Ilosc");
            createHeadings(cb, 152, 633, "Opis");
            createHeadings(cb, 432, 633, "Cena");
            createHeadings(cb, 502, 633, "Cena z VAT");

            //add the images
            Image companyLogo = Image.getInstance("images/goreit_logo.jpg");
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);

        } catch (Exception dex) {
            dex.printStackTrace();
        }

    }

    private void generateHeader(Document doc, PdfContentByte cb) {

        try {

            createHeadings(cb, 100, 743, "nr faktury : " + this.counter + "/" + LocalDate.now().getYear());
            createHeadings(cb, 100, 723, "Termin platnosci:" + LocalDate.now().toString());
            createHeadings(cb, 100, 703, "Data sprzedazy: " + LocalDate.now().toString());

            createHeadings(cb, 250, 770, "SPRZEDAJACY");
            createHeadings(cb, 250, 743, company.getName());
            createHeadings(cb, 250, 723, company.getAddress().getStreet() + " " + company.getAddress().getStreetNumber() + "/" + company.getAddress().getApartmentNumber());
            createHeadings(cb, 250, 703, company.getAddress().getPostCode() + ", " + company.getAddress().getCity());
            createHeadings(cb, 250, 683, "Nip " + company.getNip());

            createHeadings(cb, 400, 770, "KUPUJACY");
            createHeadings(cb, 400, 743, person.getFirstName() + " " + person.getLastName());
            createHeadings(cb, 400, 723, person.getAddress().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y, OrderLine orderLine) {
        DecimalFormat df = new DecimalFormat("0.00");

        try {

            createContent(cb, 48, y, String.valueOf(index + 1), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 52, y, orderLine.getAmount().toString(), PdfContentByte.ALIGN_LEFT);
            createContent(cb, 152, y, orderLine.getProductTitle(), PdfContentByte.ALIGN_LEFT);

            double price = orderLine.getPrice().multiply(BigDecimal.valueOf(orderLine.getAmount())).doubleValue();
            double extPrice = orderLine.getPrice().multiply(BigDecimal.valueOf(orderLine.getAmount())).multiply(BigDecimal.valueOf(1.23)).doubleValue();
            createContent(cb, 498, y, df.format(price), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, df.format(extPrice), PdfContentByte.ALIGN_RIGHT);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {


        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void printSum(PdfContentByte cb) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "W sumie", 420, 25, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, order.getTotalCost().toString(), 500, 25, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, order.getTotalCost().multiply(BigDecimal.valueOf(1.23)).toString(), 570, 25, 0);
        cb.endText();

        pageNumber++;
    }

    private void printPageNumber(PdfContentByte cb) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + (pageNumber + 1), 300, 25, 0);
        cb.endText();

        pageNumber++;
    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();
    }

    private void initializeFonts() {

        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }


    }

}