package pe.utp.venta.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.utp.venta.persistence.model.CuotasVentas;
import pe.utp.venta.persistence.model.DetalleVentas;
import pe.utp.venta.service.BoletaService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletaServiceImpl implements BoletaService {
    private BaseFont bfBold;
    private BaseFont bfNormal;
    private BaseFont bfItalic;

    private void initializeFonts() {
        File openSansMedium = new File(new ClassPathResource("fonts/open_sans_medium.ttf").getPath());
        System.out.println(openSansMedium.getAbsolutePath());
        FontFactory.register(openSansMedium.getAbsolutePath(), "open_sans_medium");
        Font font = FontFactory.getFont("open_sans_medium", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
        bfNormal = font.getBaseFont();

        File openSansBold = new File(new ClassPathResource("fonts/open_sans_bold.ttf").getPath());
        FontFactory.register(openSansBold.getAbsolutePath(), "open_sans_bold");
        Font font1 = FontFactory.getFont("open_sans_bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
        bfBold = font1.getBaseFont();

        File openSansItalic = new File(new ClassPathResource("fonts/open_sans_italic.ttf").getPath());
        FontFactory.register(openSansItalic.getAbsolutePath(), "open_sans_italic");
        Font font2 = FontFactory.getFont("open_sans_italic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
        bfItalic = font2.getBaseFont();
    }

    private void createHeadings(PdfContentByte cb, Float x, Float y, String text, int size, String fontStyle, BaseColor color) {
        cb.beginText();
        if (fontStyle == "normal")
            cb.setFontAndSize(bfNormal, size);
        else if (fontStyle == "bold")
            cb.setFontAndSize(bfBold, size);
        else if (fontStyle == "italic")
            cb.setFontAndSize(bfItalic, size);


        cb.setColorFill(color);
        cb.setTextMatrix(x, y);
        cb.showText(text);
        cb.endText();
    }

//    public ByteArrayInputStream generarPdf() {
//
//
//    }

    @Override
    public ByteArrayInputStream generarBoleta() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter docWriter = null;
        try {

            String tipo_documento = "BOLETA DE VENTA ELECTRÓNICA";
            String titulo_direccion_cliente = "";
            String direccion_cliente = "";

            String ruc = "RUC 10726691873";
            String empresa = "Pepito sac";
            String direccion = "Mz k lt. 22 ";
            String numero_documento = "0000001";
            String cliente = "Franklin Ruiz Asto Leon";
            String ruc_cliente = "10726691873";
            String tipo_documento_cliente = "DNI";

            String dinero = "S/.";
            String emitido = LocalDate.now().toString();
            String igv = "0.18";
            double total = 2.00;
            String total_texto = "Total compra";
            String tipo_pago = "";
            String total_cuota_pago = "";

            List<DetalleVentas> lista_venta = new ArrayList<DetalleVentas>();

//            invoice.ListInvoiceLine.forEach(it -> {
//                lista_venta.add(new DetalleVentas(
//                        (int) Double.parseDouble(it.InvoicedQuantity),
//                        it.Item.Description,
//                        Double.parseDouble(it.Price.PriceAmount),
//                        Double.parseDouble(it.LineExtensionAmount),
//                        it.Item.SellersItemIdentification.ID)
//                );
//            });

            List<CuotasVentas> lista_cuota = new ArrayList<>();

            Rectangle one = new Rectangle(204.0F, 850.5F);
            document.setPageSize(one);
            docWriter = PdfWriter.getInstance(document, out);
            document.open();
            initializeFonts();

            PdfContentByte cb = docWriter.getDirectContent();

            BaseColor black = new BaseColor(0, 0, 0);
            Font font = FontFactory.getFont("", 8.3F, Font.NORMAL);
            Font font2 = FontFactory.getFont("", 6F, Font.NORMAL);


            float y = 840F;

            createHeadings(cb, 51F, y, ruc, 12, "normal", black);

            y -= 22F;

            ColumnText ct = new ColumnText(docWriter.getDirectContent());
            ct.setSimpleColumn(new Rectangle(3F, y, 204F, y + 20F));
            Paragraph paragraph = new Paragraph();
            paragraph.setFont(font);
            paragraph.setLeading(8f);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.add(empresa);
            ct.addElement(paragraph);
            ct.go();

            y -= 20F;

            ColumnText ct99 = new ColumnText(docWriter.getDirectContent());
            ct99.setSimpleColumn(new Rectangle(3F, y, 204F, y + 20F));
            Paragraph p99 = new Paragraph();
            p99.setFont(font);
            p99.setLeading(8f);
            p99.setAlignment(Element.ALIGN_CENTER);
            p99.add("Empresa pepito");
            ct99.addElement(p99);
            ct99.go();

            y -= 13F;

            ColumnText ct1 = new ColumnText(docWriter.getDirectContent());
            ct1.setSimpleColumn(new Rectangle(3F, y, 204F, y + 20F));
            Paragraph p1 = new Paragraph();
            p1.setFont(font);
            p1.setLeading(8f);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.add(direccion);
            ct1.addElement(p1);
            ct1.go();

            y -= 10F;

            ColumnText ct2 = new ColumnText(docWriter.getDirectContent());
            ct2.setSimpleColumn(new Rectangle(3F, y, 204F, y + 16F));
            Paragraph p2 = new Paragraph();
            p2.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.add(tipo_documento);
            ct2.addElement(p2);
            ct2.go();

            y -= 13F;

            Rectangle r3 = new Rectangle(3F, y, 204F, y + 20);
            ColumnText ct3 = new ColumnText(docWriter.getDirectContent());
            ct3.setSimpleColumn(r3);
            Paragraph p3 = new Paragraph();
            p3.setFont(font);
            p3.setAlignment(Element.ALIGN_CENTER);
            p3.add(numero_documento);
            ct3.addElement(p3);
            ct3.go();

            y -= 4.5F;

            Rectangle line1 = new Rectangle(0f, y, 204F, y + 0.5F);
            line1.setBackgroundColor(black);
            cb.rectangle(line1);

            y -= 32.5F;

            Rectangle r4 = new Rectangle(1F, y, 204F, y + 30);
            ColumnText ct4 = new ColumnText(docWriter.getDirectContent());
            ct4.setSimpleColumn(r4);

            Paragraph p4 = new Paragraph();
            p4.setFont(font);
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setLeading(10f);

            p4.add("");
            p4.add(new Chunk("CLIENTE : ", FontFactory.getFont("", 8F, Font.BOLD)));
            p4.add(new Chunk(cliente, font));

            ct4.addElement(p4);

            ct4.go();

            y -= 10F;

            Rectangle r5 = new Rectangle(1F, y, 204F, y + 20F);
            ColumnText ct5 = new ColumnText(docWriter.getDirectContent());
            ct5.setSimpleColumn(r5);

            Paragraph p5 = new Paragraph();
            p5.setFont(font);
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setLeading(10f);

            p5.add("");
            p5.add(new Chunk(tipo_documento_cliente, FontFactory.getFont("", 8F, Font.BOLD)));
            p5.add(new Chunk(ruc_cliente, font));

            ct5.addElement(p5);
            ct5.go();

            y -= 10;

            Rectangle r6 = new Rectangle(1F, y, 204F, y + 20F);
            ColumnText ct6 = new ColumnText(docWriter.getDirectContent());
            ct6.setSimpleColumn(r6);

            Paragraph p6 = new Paragraph();
            p6.setFont(font);
            p6.setAlignment(Element.ALIGN_LEFT);
            p6.setLeading(10f);

            p6.add("");
            p6.add(new Chunk(titulo_direccion_cliente, FontFactory.getFont("", 8F, Font.BOLD)));
            p6.add(new Chunk(direccion_cliente, font));

            ct6.addElement(p6);
            ct6.go();

            y -= 12F;

            Rectangle r7 = new Rectangle(0F, y, 204F, y + 20F);
            ColumnText ct7 = new ColumnText(docWriter.getDirectContent());
            ct7.setSimpleColumn(r7);

            Paragraph p7 = new Paragraph();
            p7.setFont(font);
            p7.setAlignment(Element.ALIGN_RIGHT);
            p7.add("");
            p7.add(new Chunk("EMITIDO : ", FontFactory.getFont("", 8F, Font.BOLD)));
            p7.add(new Chunk(emitido, font));

            ct7.addElement(p7);
            ct7.go();

            y -= 6.5F;

//            //fin de linea

            Rectangle line02 = new Rectangle(0f, y, 204F, y + 0.5F);
            line02.setBackgroundColor(black);
            cb.rectangle(line02);

            y -= 12F;

            if (lista_cuota.size() > 0) {

                Rectangle rt0_1 = new Rectangle(1F, y, 300F, y + 20F);
                ColumnText ctt0_1 = new ColumnText(docWriter.getDirectContent());
                ctt0_1.setSimpleColumn(rt0_1);
                Paragraph pt0_1 = new Paragraph();
                pt0_1.setFont(FontFactory.getFont("", 8F, Font.BOLD));
                pt0_1.setAlignment(Element.ALIGN_LEFT);
                pt0_1.add("INFORMACIÓN DEL CRÉDITO");
                ctt0_1.addElement(pt0_1);
                ctt0_1.go();

                y -= 8F;
//
                Rectangle rt_01 = new Rectangle(1F, y, 300F, y + 20F);
                ColumnText ctt_01 = new ColumnText(docWriter.getDirectContent());
                ctt_01.setSimpleColumn(rt_01);
                Paragraph pt_01 = new Paragraph();
                pt_01.setFont(FontFactory.getFont("", 6.5F, Font.NORMAL));
                pt_01.setAlignment(Element.ALIGN_LEFT);
                pt_01.add("Monto neto pendiente de pago S/" + total_cuota_pago);
                ctt_01.addElement(pt_01);
                ctt_01.go();

                y -= 15F;

                Rectangle rt01 = new Rectangle(1F, y, 30F, y + 20F);
                ColumnText ctt01 = new ColumnText(docWriter.getDirectContent());
                ctt01.setSimpleColumn(rt01);
                Paragraph pt01 = new Paragraph();
                pt01.setFont(FontFactory.getFont("", 8F, Font.BOLD));
                pt01.setAlignment(Element.ALIGN_LEFT);
                pt01.add("CUOTA");
                ctt01.addElement(pt01);
                ctt01.go();

                Rectangle rt02 = new Rectangle(40F, y, 70F, y + 20F);
                ColumnText ctt02 = new ColumnText(docWriter.getDirectContent());
                ctt02.setSimpleColumn(rt02);
                Paragraph pt02 = new Paragraph();
                pt02.setFont(FontFactory.getFont("", 8F, Font.BOLD));
                pt02.setAlignment(Element.ALIGN_LEFT);
                pt02.add("MONTO");
                ctt02.addElement(pt02);
                ctt02.go();

                Rectangle rt03 = new Rectangle(80F, y, 160F, y + 20F);
                ColumnText ctt03 = new ColumnText(docWriter.getDirectContent());
                ctt03.setSimpleColumn(rt03);
                Paragraph pt03 = new Paragraph();
                pt03.setFont(FontFactory.getFont("", 8F, Font.BOLD));
                pt03.setAlignment(Element.ALIGN_LEFT);
                pt03.add("FEC. VENCIMIENTO");
                ctt03.addElement(pt03);
                ctt03.go();

                y -= 10F;

                int yury0 = 16;

                for (CuotasVentas cuota : lista_cuota) {
                    Rectangle rx1 = new Rectangle(1F, y, 30F, y + yury0);
                    ColumnText ctx1 = new ColumnText(docWriter.getDirectContent());
                    ctx1.setSimpleColumn(rx1);
                    Paragraph px1 = new Paragraph();
                    px1.setFont(font);
                    px1.setAlignment(Element.ALIGN_CENTER);
                    px1.add(cuota.getCuota() + "");
                    ctx1.addElement(px1);
                    try {
                        ctx1.go();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                    Rectangle rx2 = new Rectangle(40F, y, 70F, y + yury0);
                    ColumnText ctx2 = new ColumnText(docWriter.getDirectContent());
                    ctx2.setSimpleColumn(rx2);
                    Paragraph px2 = new Paragraph();
                    px2.setFont(font);
                    px2.setAlignment(Element.ALIGN_LEFT);
                    px2.add(cuota.getMonto());
                    ctx2.addElement(px2);
                    try {
                        ctx2.go();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                    Rectangle rx3 = new Rectangle(92F, y, 160F, y + yury0);
                    ColumnText ctx3 = new ColumnText(docWriter.getDirectContent());
                    ctx3.setSimpleColumn(rx3);
                    Paragraph px3 = new Paragraph();
                    px3.setFont(font);
                    px3.setAlignment(Element.ALIGN_LEFT);
                    px3.add(cuota.getFecha());
                    ctx3.addElement(px3);
                    try {
                        ctx3.go();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                    y -= yury0;

                }

                y += (yury0 / 2);

                //----------------------------------------------------------
                Rectangle line2 = new Rectangle(0f, y, 204F, y + 0.5F);
                line2.setBackgroundColor(black);
                cb.rectangle(line2);

                y -= 20.5F;
            }

            Rectangle rt1 = new Rectangle(1F, y, 30F, y + 20F);
            ColumnText ctt1 = new ColumnText(docWriter.getDirectContent());
            ctt1.setSimpleColumn(rt1);
            Paragraph pt1 = new Paragraph();
            pt1.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt1.setAlignment(Element.ALIGN_LEFT);
            pt1.add("CANT.");
            ctt1.addElement(pt1);
            ctt1.go();

            Rectangle rt2 = new Rectangle(30F, y, 130F, y + 20F);
            ColumnText ctt2 = new ColumnText(docWriter.getDirectContent());
            ctt2.setSimpleColumn(rt2);
            Paragraph pt2 = new Paragraph();
            pt2.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt2.setAlignment(Element.ALIGN_LEFT);
            pt2.add("DESC.");
            ctt2.addElement(pt2);
            ctt2.go();

            Rectangle rt3 = new Rectangle(137F, y, 160F, y + 20F);
            ColumnText ctt3 = new ColumnText(docWriter.getDirectContent());
            ctt3.setSimpleColumn(rt3);
            Paragraph pt3 = new Paragraph();
            pt3.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt3.setAlignment(Element.ALIGN_LEFT);
            pt3.add("P.U.");
            ctt3.addElement(pt3);
            ctt3.go();

            Rectangle rt4 = new Rectangle(172F, y, 200F, y + 20F);
            ColumnText ctt4 = new ColumnText(docWriter.getDirectContent());
            ctt4.setSimpleColumn(rt4);
            Paragraph pt4 = new Paragraph();
            pt4.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt4.setAlignment(Element.ALIGN_LEFT);
            pt4.add("TOTAL");
            ctt4.addElement(pt4);
            ctt4.go();

            y -= 30F;
            int yury = 30;

            for (DetalleVentas venta : lista_venta) {
                Rectangle rx1 = new Rectangle(10F, y, 30F, y + yury);
                ColumnText ctx1 = new ColumnText(docWriter.getDirectContent());
                ctx1.setSimpleColumn(rx1);
                Paragraph px1 = new Paragraph();
                px1.setFont(font);
                px1.setAlignment(Element.ALIGN_LEFT);
                px1.add(String.valueOf(venta.getCantidad()));
                ctx1.addElement(px1);
                ctx1.go();

                Rectangle rx2 = new Rectangle(30F, y, 130F, y + yury);
                ColumnText ctx2 = new ColumnText(docWriter.getDirectContent());
                ctx2.setSimpleColumn(rx2);
                Paragraph px2 = new Paragraph();
                px2.setFont(font);
                px2.setLeading(10f);
                px2.setAlignment(Element.ALIGN_LEFT);
                px2.add(venta.getDescripcion());
                if (venta.getDescripcion().length() <= 18) {
                    px2.add(new Chunk("\n" + venta.getCodigo(), FontFactory.getFont("", 8F, Font.BOLD)));
                }
                ctx2.addElement(px2);
                ctx2.go();

                Rectangle rx3 = new Rectangle(135F, y, 162F, y + yury);
                ColumnText ctx3 = new ColumnText(docWriter.getDirectContent());
                ctx3.setSimpleColumn(rx3);
                Paragraph px3 = new Paragraph();
                px3.setFont(font);
                px3.setAlignment(Element.ALIGN_RIGHT);
                px3.add(venta.getPrecioUnitario().toString());
                ctx3.addElement(px3);
                ctx3.go();

                Rectangle rx4 = new Rectangle(172F, y, 200F, y + yury);
                ColumnText ctx4 = new ColumnText(docWriter.getDirectContent());
                ctx4.setSimpleColumn(rx4);
                Paragraph px4 = new Paragraph();
                px4.setFont(font);
                px4.setAlignment(Element.ALIGN_RIGHT);

                px4.add(venta.getTotal().toString());
                ctx4.addElement(px4);
                ctx4.go();

                y -= yury;
            }

            y += (yury / 2);

            //cierre inicio
            Rectangle line3 = new Rectangle(0f, y, 204F, y + 0.5F);
            line3.setBackgroundColor(black);
            cb.rectangle(line3);

            y -= 14;
            Rectangle r8 = new Rectangle(1F, y, 80F, y + 20);
            ColumnText ct8 = new ColumnText(docWriter.getDirectContent());
            ct8.setSimpleColumn(r8);
            Paragraph p8 = new Paragraph();
            p8.setFont(font);
            p8.setAlignment(Element.ALIGN_LEFT);
            p8.add("IGV 18%");

            ct8.addElement(p8);
            ct8.go();

            Rectangle r9 = new Rectangle(1F, y, 200F, y + 20);
            ColumnText ct9 = new ColumnText(docWriter.getDirectContent());
            ct9.setSimpleColumn(r9);
            Paragraph p9 = new Paragraph();
            p9.setFont(font);
            p9.setAlignment(Element.ALIGN_RIGHT);
            p9.add("S/." + igv);
            ct9.addElement(p9);
            ct9.go();

            y -= 10;

            Rectangle r10 = new Rectangle(1F, y, 80F, y + 20);
            ColumnText ct10 = new ColumnText(docWriter.getDirectContent());
            ct10.setSimpleColumn(r10);
            Paragraph p10 = new Paragraph();
            p10.setFont(font);
            p10.setAlignment(Element.ALIGN_LEFT);
            p10.add("TOTAL");
            ct10.addElement(p10);
            ct10.go();

            Rectangle r11 = new Rectangle(1F, y, 200F, y + 20);
            ColumnText ct11 = new ColumnText(docWriter.getDirectContent());
            ct11.setSimpleColumn(r11);
            Paragraph p11 = new Paragraph();
            p11.setFont(font);
            p11.setAlignment(Element.ALIGN_RIGHT);
            p11.add("S/." + total);
            ct11.addElement(p11);
            ct11.go();

            //cierre fin

            Rectangle line4 = new Rectangle(0f, y, 204F, y + 0.5F);
            line4.setBackgroundColor(black);
            cb.rectangle(line4);

            y -= 20;

            Rectangle r12 = new Rectangle(0F, y, 205F, y + 20);
            ColumnText ct12 = new ColumnText(docWriter.getDirectContent());
            ct12.setSimpleColumn(r12);
            Paragraph p12 = new Paragraph();
            p12.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            p12.setLeading(10f);
            p12.setAlignment(Element.ALIGN_CENTER);
            p12.add(total_texto);
            ct12.addElement(p12);
            ct12.go();

            y -= 25;

            Rectangle r13 = new Rectangle(28F, y, 177F, y + 20);
            ColumnText ct13 = new ColumnText(docWriter.getDirectContent());
            ct13.setSimpleColumn(r13);
            Paragraph p13 = new Paragraph();
            p13.setFont(font);
            p13.setLeading(10f);
            p13.setAlignment(Element.ALIGN_CENTER);

            p13.add("");
            p13.add(new Chunk("texto 1", font));
            p13.add(new Chunk(tipo_documento, FontFactory.getFont("", 8F, Font.BOLD)));

            ct13.addElement(p13);
            ct13.go();

            y -= 130;

            BarcodeQRCode qrcode = new BarcodeQRCode(ruc, 1, 1, null);
            Image qrcodeImage = qrcode.getImage();
            qrcodeImage.setAbsolutePosition(39F, y);
            qrcodeImage.scaleToFit(130F, 130F);
            document.add(qrcodeImage);

            y -= 20;

            Rectangle r14 = new Rectangle(0F, y, 204F, y + 20);
            ColumnText ct14 = new ColumnText(docWriter.getDirectContent());
            ct14.setSimpleColumn(r14);
            Paragraph p14 = new Paragraph();
            p14.setFont(font);
            p14.setLeading(10f);
            p14.setAlignment(Element.ALIGN_CENTER);
            p14.add("www.url.com");
            ct14.addElement(p14);

            ct14.go();

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
