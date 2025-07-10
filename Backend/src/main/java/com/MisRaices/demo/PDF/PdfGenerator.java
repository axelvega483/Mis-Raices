package com.MisRaices.demo.PDF;

import com.MisRaices.demo.entity.Pedido;
import com.MisRaices.demo.entity.PedidoDetalle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class PdfGenerator {

    private static final String RUTA_PDF = "/ruta/a/tu/directorio/pdf";

    public static String generarFacturaPDF(Pedido pedido) {
        try {
            File directory = new File(RUTA_PDF);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String pathArchivo = directory + File.separator + "factura-compra-" + pedido.getId() + ".pdf";

            Document document = new Document(PageSize.A4, 36, 36, 54, 36); // márgenes: izq, der, sup, inf
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Font fontTablaHeader = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            Font fontTablaCuerpo = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);

            Image logo = Image.getInstance("https://drive.google.com/uc?export=view&id=1AfkG0j2lL95vKQcxQUPK1knaxnfpVT8q");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);

            Paragraph titulo = new Paragraph("Factura de Compra - Vivero Mis Raíces", fontTitulo);
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            titulo.setSpacingAfter(10f);
            document.add(titulo);

            document.add(new Paragraph("Fecha: " + pedido.getFechaPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), fontSubtitulo));
            document.add(new Paragraph("ID de Pedido: " + pedido.getId(), fontSubtitulo));
            document.add(new Paragraph("Cliente: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido(), fontSubtitulo));
            document.add(new Paragraph("Dirección: " + pedido.getUsuario().getDireccion().getCalle() + " " + pedido.getUsuario().getDireccion().getNumero(), fontSubtitulo));

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 2f, 2f});

            BaseColor headerColor = new BaseColor(0, 121, 107);
            Stream.of("Producto", "Cantidad", "Precio")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell(new Phrase(columnTitle, fontTablaHeader));
                        header.setBackgroundColor(headerColor);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setPadding(5);
                        table.addCell(header);
                    });

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                PdfPCell productoCell = new PdfPCell(new Phrase(detalle.getProducto().getNombre(), fontTablaCuerpo));
                productoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(productoCell);

                PdfPCell cantidadCell = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fontTablaCuerpo));
                cantidadCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cantidadCell);

                PdfPCell precioCell = new PdfPCell(new Phrase(String.format("$%.2f", detalle.getProducto().getPrecio()), fontTablaCuerpo));
                precioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(precioCell);
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph total = new Paragraph("Total: $" + String.format("%.2f", pedido.getTotal()), fontSubtitulo);
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return pathArchivo;

        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            return null;
        }

    }

}
