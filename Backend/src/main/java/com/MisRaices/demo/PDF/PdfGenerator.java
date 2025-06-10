package com.MisRaices.demo.PDF;

import com.MisRaices.demo.entity.Pedido;
import com.MisRaices.demo.entity.PedidoDetalle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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

public class PdfGenerator {

    private static final String RUTA_PDF = "/ruta/a/tu/directorio/pdf";

    public static String generarFacturaPDF(Pedido pedido) {
        try {

            File directory = new File(RUTA_PDF);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String pathArchivo = directory + File.separator + "factura-compra-" + pedido.getId() + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));

            document.open();

            Image logo = Image.getInstance("https://drive.google.com/uc?export=view&id=1AfkG0j2lL95vKQcxQUPK1knaxnfpVT8q");  // Reemplaza con la ruta de tu logo
            logo.scaleToFit(100, 100);  // Ajusta el tamaño del logo
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);

            Paragraph encabezado = new Paragraph("Factura de Compra Vivero Mis Raices", fontTitulo);
            encabezado.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(encabezado);

            Paragraph fechaCompra = new Paragraph("Fecha: " + pedido.getFechaPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), fontCuerpo);
            fechaCompra.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(fechaCompra);

            Paragraph pedidoId = new Paragraph("ID Pedido: " + pedido.getId(), fontCuerpo);
            pedidoId.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pedidoId);

            Paragraph cliente = new Paragraph("Cliente: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido(), fontCuerpo);
            cliente.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(cliente);

            Paragraph direccion = new Paragraph("Direccíon: " + pedido.getUsuario().getDireccion().getCalle() + " " + pedido.getUsuario().getDireccion().getNumero(), fontCuerpo);
            cliente.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(direccion);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            table.addCell(new PdfPCell(new Phrase("Producto", fontCuerpo)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", fontCuerpo)));
            table.addCell(new PdfPCell(new Phrase("Precio", fontCuerpo)));

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                table.addCell(new PdfPCell(new Phrase(detalle.getProducto().getNombre(), fontCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fontCuerpo)));
                table.addCell(new PdfPCell(new Phrase("$" + detalle.getProducto().getPrecio(), fontCuerpo)));
            }

            document.add(table);

            Paragraph total = new Paragraph("Total: $" + pedido.getTotal(), fontCuerpo);
            total.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(total);

            document.close();

            return pathArchivo;

        } catch (DocumentException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
