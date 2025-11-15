package com.MisRaices.ProyectoFinal.PDF;

import com.MisRaices.ProyectoFinal.entity.Pedido;
import com.MisRaices.ProyectoFinal.entity.PedidoDetalle;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
@Component
public class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    @Value("${app.pdf.directory:./pdf-facturas}")
    private String pdfDirectory;

    @Value("${app.logo.url:https://drive.google.com/uc?export=view&id=1AfkG0j2lL95vKQcxQUPK1nfpVT8q}")
    private String logoUrl;

    @Value("${app.company.name:Vivero Mis Raíces}")
    private String companyName;

    @Value("${app.company.address:Av. Principal 123, Ciudad}")
    private String companyAddress;

    @Value("${app.company.phone:+1 234 567 8900}")
    private String companyPhone;

    @Value("${app.company.email:info@misraices.com}")
    private String companyEmail;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String generarFacturaPDF(Pedido pedido) {
        String fileName = null;
        Document document = null;
        FileOutputStream fileOutputStream = null;

        try {
            // Crear directorio si no existe
            File directory = new File(pdfDirectory);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new IOException("No se pudo crear el directorio: " + pdfDirectory);
                }
                logger.info("Directorio PDF creado: {}", pdfDirectory);
            }

            // Generar nombre de archivo único
            fileName = String.format("factura_%s_%d.pdf",
                    pedido.getFechaPedido().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")),
                    pedido.getId());

            String filePath = directory + File.separator + fileName;

            document = new Document(PageSize.A4, 40, 40, 80, 40);
            fileOutputStream = new FileOutputStream(filePath);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);

            // Agregar header y footer personalizados
            writer.setPageEvent(new PdfPageEventHandler());

            document.open();

            // Generar contenido del PDF
            generarContenidoFactura(document, pedido);

            logger.info("Factura PDF generada exitosamente: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Error generando factura PDF para pedido ID: {}", pedido.getId(), e);
            return null;
        } finally {
            // Cerrar recursos
            if (document != null) {
                document.close();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.warn("Error cerrando FileOutputStream", e);
                }
            }
        }
    }

    private void generarContenidoFactura(Document document, Pedido pedido) throws DocumentException, IOException {
        // Fuentes predefinidas
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(46, 125, 50));
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font fontTablaHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        Font fontTablaCuerpo = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
        Font fontTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(46, 125, 50));

        // Header con logo e información de la empresa
        agregarHeader(document);

        // Título
        Paragraph titulo = new Paragraph("FACTURA", fontTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        document.add(titulo);

        // Información de la factura en dos columnas
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{1f, 1f});

        // Columna izquierda - Información del cliente
        String clienteInfo = "CLIENTE:\n" +
                pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido() + "\n" +
                "Email: " + pedido.getUsuario().getCorreo() + "\n" +
                "Tel: " + (pedido.getUsuario().getTelefono() != null ? pedido.getUsuario().getTelefono() : "N/A");

        if (pedido.getUsuario().getDireccion() != null) {
            clienteInfo += "\n" + pedido.getUsuario().getDireccion().getCalle() + " " +
                    pedido.getUsuario().getDireccion().getNumero() + "\n" +
                    pedido.getUsuario().getDireccion().getCiudad() + ", " +
                    pedido.getUsuario().getDireccion().getProvincia();
        }

        PdfPCell clienteCell = new PdfPCell(new Phrase(clienteInfo, fontNormal));
        clienteCell.setBorder(PdfPCell.NO_BORDER);
        clienteCell.setPadding(5);
        infoTable.addCell(clienteCell);

        // Columna derecha - Información de la factura
        String facturaInfo = "FACTURA #: PED-" + pedido.getId() + "\n" +
                "FECHA: " + pedido.getFechaPedido().format(DATE_TIME_FORMATTER) + "\n" +
                "ESTADO: " + pedido.getEstado().toString() + "\n" +
                "MÉTODO PAGO: Tarjeta de Crédito";

        PdfPCell facturaCell = new PdfPCell(new Phrase(facturaInfo, fontNormal));
        facturaCell.setBorder(PdfPCell.NO_BORDER);
        facturaCell.setPadding(5);
        facturaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        infoTable.addCell(facturaCell);

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // Tabla de productos
        PdfPTable productosTable = new PdfPTable(4); // Corregido: 4 columnas en lugar de 5
        productosTable.setWidthPercentage(100);
        productosTable.setWidths(new float[]{3f, 2f, 2f, 2f}); // Corregido: 4 widths

        BaseColor headerColor = new BaseColor(46, 125, 50); // Verde corporativo

        // Headers de la tabla
        String[] headers = {"PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "SUBTOTAL"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, fontTablaHeader));
            headerCell.setBackgroundColor(headerColor);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(8);
            headerCell.setBorderWidth(1);
            productosTable.addCell(headerCell);
        }

        // Filas de productos
        double totalPedido = 0;
        for (PedidoDetalle detalle : pedido.getDetalle()) {
            double subtotal = detalle.getProducto().getPrecio() * detalle.getCantidad();
            totalPedido += subtotal;

            agregarCeldaProducto(productosTable, detalle.getProducto().getNombre(), fontTablaCuerpo, Element.ALIGN_LEFT);
            agregarCeldaProducto(productosTable, String.valueOf(detalle.getCantidad()), fontTablaCuerpo, Element.ALIGN_CENTER);
            agregarCeldaProducto(productosTable, String.format("$%,.2f", detalle.getProducto().getPrecio()), fontTablaCuerpo, Element.ALIGN_RIGHT);
            agregarCeldaProducto(productosTable, String.format("$%,.2f", subtotal), fontTablaCuerpo, Element.ALIGN_RIGHT);
        }

        document.add(productosTable);
        document.add(Chunk.NEWLINE);

        // Total
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(50);
        totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalTable.setWidths(new float[]{2f, 1f});

        agregarCeldaTotal(totalTable, "SUBTOTAL:", String.format("$%,.2f", totalPedido), fontNormal);
        agregarCeldaTotal(totalTable, "IVA (0%):", "$0.00", fontNormal);
        agregarCeldaTotal(totalTable, "TOTAL:", String.format("$%,.2f", totalPedido), fontTotal);

        document.add(totalTable);
        document.add(Chunk.NEWLINE);

        // Notas
        Paragraph notas = new Paragraph(
                "NOTAS:\n" +
                        "• Esta factura es un comprobante de su compra\n" +
                        "• Para consultas o reclamos, contacte a: " + companyEmail + "\n" +
                        "• Gracias por su preferencia",
                fontNormal
        );
        notas.setSpacingBefore(20f);
        document.add(notas);
    }

    private void agregarHeader(Document document) throws DocumentException, IOException {
        try {
            Image logo = Image.getInstance(logoUrl);
            logo.scaleToFit(80, 80);
            logo.setAbsolutePosition(40, PageSize.A4.getHeight() - 60);
            document.add(logo);
        } catch (Exception e) {
            logger.warn("No se pudo cargar el logo, continuando sin él", e);
        }

        Paragraph companyInfo = new Paragraph(
                companyName + "\n" +
                        companyAddress + "\n" +
                        "Tel: " + companyPhone + " | Email: " + companyEmail,
                new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.DARK_GRAY)
        );
        companyInfo.setAlignment(Element.ALIGN_RIGHT);
        document.add(companyInfo);

        document.add(new Paragraph(" "));
        document.add(new LineSeparator());
        document.add(Chunk.NEWLINE);
    }

    private void agregarCeldaProducto(PdfPTable table, String texto, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(6);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private void agregarCeldaTotal(PdfPTable table, String descripcion, String valor, Font font) {
        PdfPCell descCell = new PdfPCell(new Phrase(descripcion, font));
        descCell.setBorder(PdfPCell.NO_BORDER);
        descCell.setPadding(5);
        table.addCell(descCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, font));
        valorCell.setBorder(PdfPCell.NO_BORDER);
        valorCell.setPadding(5);
        valorCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valorCell);
    }

    // Clase interna para header y footer de cada página
    private class PdfPageEventHandler extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY);

                Paragraph footer = new Paragraph(
                        "Página " + writer.getPageNumber() + " | " +
                                companyName + " | " +
                                "Generado el: " + java.time.LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        footerFont
                );
                footer.setAlignment(Element.ALIGN_CENTER);

                ColumnText.showTextAligned(
                        writer.getDirectContent(),
                        Element.ALIGN_CENTER,
                        footer,
                        document.right() / 2,
                        document.bottom() - 20,
                        0
                );
            } catch (Exception e) {
                logger.warn("Error agregando footer al PDF", e);
            }
        }
    }
}