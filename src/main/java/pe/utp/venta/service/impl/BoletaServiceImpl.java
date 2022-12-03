package pe.utp.venta.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.utp.venta.persistence.entity.Cliente;
import pe.utp.venta.persistence.entity.Venta;
import pe.utp.venta.persistence.entity.VentaDetalle;
import pe.utp.venta.persistence.repository.ClienteRepository;
import pe.utp.venta.persistence.repository.ProductoRepository;
import pe.utp.venta.persistence.repository.VentaDetalleRepository;
import pe.utp.venta.persistence.repository.VentaRepository;
import pe.utp.venta.service.BoletaService;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoletaServiceImpl implements BoletaService {
    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final VentaDetalleRepository ventaDetalleRepository;
    private final ProductoRepository productoRepository;

    private BaseFont bfBold;
    private BaseFont bfNormal;
    private BaseFont bfItalic;

    private void initializeFonts() {
        File openSansMedium = new File(new ClassPathResource("fonts/open_sans_medium.ttf").getPath());
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

    private String buscaProducto(Long productoId) {
        return productoRepository.getById(productoId).getDescripcion();
    }

    @Override
    public ByteArrayInputStream generarBoleta(Long ventaId) {

        Optional<Venta> venta = ventaRepository.findById(ventaId);
        Optional<Cliente> cliente = clienteRepository.findById(venta.get().getClienteId());
        List<VentaDetalle> ventaDetalles = ventaDetalleRepository.findByVentaId(venta.get().getVentaId());

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter docWriter = null;
        try {

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.DOWN);

            Rectangle one = new Rectangle(204.0F, 850.5F);
            document.setPageSize(one);
            docWriter = PdfWriter.getInstance(document, out);
            document.open();
            initializeFonts();

            PdfContentByte cb = docWriter.getDirectContent();

            BaseColor black = new BaseColor(0, 0, 0);
            Font font = FontFactory.getFont("", 8.3F, Font.NORMAL);
            Font font2 = FontFactory.getFont("", 6F, Font.NORMAL);

            float y = 820F;

            createHeadings(cb, 51F, y, "BOLETA DE VENTA", 12, "normal", black);

            y -= 22F;

            ColumnText ct = new ColumnText(docWriter.getDirectContent());
            ct.setSimpleColumn(new Rectangle(3F, y, 204F, y + 20F));
            Paragraph paragraph = new Paragraph();
            paragraph.setFont(font);
            paragraph.setLeading(8f);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.add("Universidad Tecnológica del Perú");
            ct.addElement(paragraph);
            ct.go();

            y -= 20F;

            ColumnText ct1 = new ColumnText(docWriter.getDirectContent());
            ct1.setSimpleColumn(new Rectangle(3F, y, 204F, y + 20F));
            Paragraph p1 = new Paragraph();
            p1.setFont(font);
            p1.setLeading(8f);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.add("Panamericana Norte, Av. Alfredo Mendiola 6377, Los Olivos 15306");
            ct1.addElement(p1);
            ct1.go();

            y -= 10F;

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
            p4.add(new Chunk(cliente.get().getNombre(), font));
            ct4.addElement(p4);
            ct4.go();

            y -= 1F;

            Rectangle r5 = new Rectangle(1F, y, 204F, y + 20F);
            ColumnText ct5 = new ColumnText(docWriter.getDirectContent());
            ct5.setSimpleColumn(r5);

            Paragraph p5 = new Paragraph();
            p5.setFont(font);
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setLeading(10f);

            p5.add("");
            p5.add(new Chunk("DNI : ", FontFactory.getFont("", 8F, Font.BOLD)));
            p5.add(new Chunk(cliente.get().getDni(), font));
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
            p6.add(new Chunk("DIRECCIÓN : ", FontFactory.getFont("", 8F, Font.BOLD)));
            p6.add(new Chunk(cliente.get().getDireccion(), font));
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
            p7.add(new Chunk(venta.get().getFechaCreacion().format(format), font));
            ct7.addElement(p7);
            ct7.go();

            y -= 6.5F;

            Rectangle line02 = new Rectangle(0f, y, 204F, y + 0.5F);
            line02.setBackgroundColor(black);
            cb.rectangle(line02);

            y -= 12F;

            Rectangle rt1 = new Rectangle(1F, y, 100F, y + 20F);
            ColumnText ctt1 = new ColumnText(docWriter.getDirectContent());
            ctt1.setSimpleColumn(rt1);
            Paragraph pt1 = new Paragraph();
            pt1.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt1.setAlignment(Element.ALIGN_LEFT);
            pt1.add("PRODUCTO");
            ctt1.addElement(pt1);
            ctt1.go();

            Rectangle rt2 = new Rectangle(106F, y, 130F, y + 20F);
            ColumnText ctt2 = new ColumnText(docWriter.getDirectContent());
            ctt2.setSimpleColumn(rt2);
            Paragraph pt2 = new Paragraph();
            pt2.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt2.setAlignment(Element.ALIGN_LEFT);
            pt2.add("PREC");
            ctt2.addElement(pt2);
            ctt2.go();

            Rectangle rt3 = new Rectangle(137F, y, 160F, y + 20F);
            ColumnText ctt3 = new ColumnText(docWriter.getDirectContent());
            ctt3.setSimpleColumn(rt3);
            Paragraph pt3 = new Paragraph();
            pt3.setFont(FontFactory.getFont("", 8F, Font.BOLD));
            pt3.setAlignment(Element.ALIGN_LEFT);
            pt3.add("CANT");
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

            y -= 25F;
            int yury = 21;

            for (VentaDetalle vd : ventaDetalles) {

                Rectangle rx1 = new Rectangle(1F, y, 100F, y + yury);
                ColumnText ctx1 = new ColumnText(docWriter.getDirectContent());
                ctx1.setSimpleColumn(rx1);
                Paragraph px1 = new Paragraph();
                px1.setFont(font);
                px1.setLeading(8f);
                px1.setAlignment(Element.ALIGN_LEFT);
                px1.add(buscaProducto(vd.getProductoId()));
                ctx1.addElement(px1);
                ctx1.go();

                Rectangle rx2 = new Rectangle(100F, y, 130F, y + yury);
                ColumnText ctx2 = new ColumnText(docWriter.getDirectContent());
                ctx2.setSimpleColumn(rx2);
                Paragraph px2 = new Paragraph();
                px2.setFont(font);
                px2.setAlignment(Element.ALIGN_RIGHT);
                px2.add(df.format(vd.getPrecio()));
                ctx2.addElement(px2);
                ctx2.go();

                Rectangle rx3 = new Rectangle(135F, y, 162F, y + yury);
                ColumnText ctx3 = new ColumnText(docWriter.getDirectContent());
                ctx3.setSimpleColumn(rx3);
                Paragraph px3 = new Paragraph();
                px3.setFont(font);
                px3.setAlignment(Element.ALIGN_CENTER);
                px3.add("" + vd.getCantidad());
                ctx3.addElement(px3);
                ctx3.go();

                Rectangle rx4 = new Rectangle(172F, y, 200F, y + yury);
                ColumnText ctx4 = new ColumnText(docWriter.getDirectContent());
                ctx4.setSimpleColumn(rx4);
                Paragraph px4 = new Paragraph();
                px4.setFont(font);
                px4.setAlignment(Element.ALIGN_RIGHT);
                px4.add(df.format(vd.getTotal()));
                ctx4.addElement(px4);
                ctx4.go();

                y -= yury;
            }

            y += (yury / 2);

            Rectangle line3 = new Rectangle(0f, y, 204F, y + 0.5F);
            line3.setBackgroundColor(black);
            cb.rectangle(line3);

            y -= 14;

            Rectangle r18 = new Rectangle(1F, y, 80F, y + 20);
            ColumnText ct18 = new ColumnText(docWriter.getDirectContent());
            ct18.setSimpleColumn(r18);
            Paragraph p18 = new Paragraph();
            p18.setFont(font);
            p18.setAlignment(Element.ALIGN_LEFT);
            p18.add("SUB TOTAL");
            ct18.addElement(p18);
            ct18.go();

            Rectangle r19 = new Rectangle(1F, y, 200F, y + 20);
            ColumnText ct19 = new ColumnText(docWriter.getDirectContent());
            ct19.setSimpleColumn(r19);
            Paragraph p19 = new Paragraph();
            p19.setFont(font);
            p19.setAlignment(Element.ALIGN_RIGHT);
            p19.add("S/" + df.format(venta.get().getSubTotal()));
            ct19.addElement(p19);
            ct19.go();

            y -= 10;

            Rectangle r8 = new Rectangle(1F, y, 80F, y + 20);
            ColumnText ct8 = new ColumnText(docWriter.getDirectContent());
            ct8.setSimpleColumn(r8);
            Paragraph p8 = new Paragraph();
            p8.setFont(font);
            p8.setAlignment(Element.ALIGN_LEFT);
            p8.add("IGV");
            ct8.addElement(p8);
            ct8.go();

            Rectangle r9 = new Rectangle(1F, y, 200F, y + 20);
            ColumnText ct9 = new ColumnText(docWriter.getDirectContent());
            ct9.setSimpleColumn(r9);
            Paragraph p9 = new Paragraph();
            p9.setFont(font);
            p9.setAlignment(Element.ALIGN_RIGHT);
            p9.add("" + venta.get().getIgv());
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
            p11.add("S/" + df.format(venta.get().getTotal()));
            ct11.addElement(p11);
            ct11.go();

            Rectangle line4 = new Rectangle(0f, y, 204F, y + 0.5F);
            line4.setBackgroundColor(black);
            cb.rectangle(line4);

            y -= 30;

            Rectangle r13 = new Rectangle(28F, y, 177F, y + 20);
            ColumnText ct13 = new ColumnText(docWriter.getDirectContent());
            ct13.setSimpleColumn(r13);
            Paragraph p13 = new Paragraph();
            p13.setFont(font);
            p13.setLeading(10f);
            p13.setAlignment(Element.ALIGN_CENTER);

            p13.add("");
            p13.add(new Chunk("N° BOLETA : ", font));
            p13.add(new Chunk(String.format("%08d", venta.get().getVentaId()), FontFactory.getFont("", 8F, Font.BOLD)));
            ct13.addElement(p13);
            ct13.go();

            y -= 120;

            BarcodeQRCode qrcode = new BarcodeQRCode(String.format("%08d", venta.get().getVentaId()), 1, 1, null);
            Image qrcodeImage = qrcode.getImage();
            qrcodeImage.setAbsolutePosition(39F, y);
            qrcodeImage.scaleToFit(130F, 130F);
            document.add(qrcodeImage);

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
