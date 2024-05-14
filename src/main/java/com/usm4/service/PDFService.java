package com.usm4.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.usm4.entity.Booking;
import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {

    public String generateBookingDetailsPdf(Booking booking) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C://Users//DELL//Desktop//psa_spring//airbnb-booking//booking-confirmation" + booking.getId() + ".pdf"));
            document.open();

            // Title Font and Paragraph
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph title = new Paragraph("Booking Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // Create a table with 2 columns
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Add booking details to the table
            addTableCell(table, "Guest Name:", booking.getGuestName());
            addTableCell(table, "Total Nights:", String.valueOf(booking.getTotalNights()));
            addTableCell(table, "Total Price:", String.valueOf(booking.getTotalPrice()));
            addTableCell(table, "Check-in Time:", String.valueOf(booking.getCheckInTime()));
            addTableCell(table, "Booking Date:", booking.getBookingDate().toString());

            document.add(table);
            document.close();
            return "C://Users//DELL//Desktop//psa_spring//airbnb-booking//booking-confirmation" + booking.getId() + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to add cell to the table
    private void addTableCell(PdfPTable table, String header, String value) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK)));
        table.addCell(headerCell);
        table.addCell(valueCell);
    }
}
