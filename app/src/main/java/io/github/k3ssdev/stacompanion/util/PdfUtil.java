package io.github.k3ssdev.stacompanion.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class PdfUtil {

    public static void createCharacterSheetPdf(Context context, Uri uri, CharacterSheet characterSheet) {
        try {
            // Abre el archivo PDF con InputStream y crea un PdfReader
            //PdfReader reader = new PdfReader("path/to/STA_personaje.pdf");
            InputStream inputStream = context.getAssets().open("STA_personaje.pdf");
            PdfReader reader = new PdfReader(inputStream);

            // Crea un PdfStamper con el PdfReader y un OutputStream
            PdfStamper stamper = new PdfStamper(reader, context.getContentResolver().openOutputStream(uri));

            // Obtiene el PdfContentByte del PdfStamper para escribir en el PDF
            PdfContentByte cb = stamper.getOverContent(1);
            BaseFont bf = BaseFont.createFont();

            // Cambia el color y el tamaño de la fuente
            cb.setColorFill(BaseColor.BLACK);
            cb.setFontAndSize(bf, 10);

            // Añade el nombre del personaje al PDF con las coordenadas x e y
            String text = characterSheet.getCharacterName();
            float x = 222.2f;
            float y = reader.getPageSize(1).getHeight() - 67.0f;
            cb.beginText();
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, x, y, 0);
            cb.endText();

            // Cierra el PdfStamper
            stamper.close();

            // Cierra el PdfReader
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PdfUtil", "Error al crear el PDF");
        }
    }
}