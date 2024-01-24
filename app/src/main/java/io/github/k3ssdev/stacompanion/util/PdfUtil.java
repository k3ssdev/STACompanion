package io.github.k3ssdev.stacompanion.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class PdfUtil {

    public static void createCharacterSheetPdf(Context context, Uri uri, CharacterSheet characterSheet) {
        try {

            // TODO: Abrir documento PDF hoja de personaje
            // Create a new PDF document
            Document document = new Document();

            // Write the PDF document to a file
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            PdfWriter.getInstance(document, outputStream);

            // Abre el documento PDF
            document.open();

            // TODO: Agregar texto al documento PDF en las coordenadas (x, y)
            // Add text to the PDF document
            document.add(new Paragraph("Character Name: " + characterSheet.getCharacterName()));
            document.add(new Paragraph("Species: " + characterSheet.getSpecies()));

            // Close the PDF document
            document.close();

            // Check if the file was created
            if (outputStream != null) {
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PdfUtil", "Error creating PDF file");
        }
    }
}