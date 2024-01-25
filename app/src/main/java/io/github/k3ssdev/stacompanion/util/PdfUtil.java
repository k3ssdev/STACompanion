package io.github.k3ssdev.stacompanion.util;

import android.content.Context;
import android.content.Intent;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class PdfUtil {

    public static void createCharacterSheetPdf(Context context, Uri uri, CharacterSheet characterSheet) {
        try {
            InputStream inputStream = context.getAssets().open("STA_personaje.pdf");
            PdfReader reader = new PdfReader(inputStream);
            PdfStamper stamper = new PdfStamper(reader, context.getContentResolver().openOutputStream(uri));

            PdfContentByte cb = stamper.getOverContent(1);
            //BaseFont bf = BaseFont.createFont();
            BaseFont bf = BaseFont.createFont("assets/Stardate81316-aolE.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cb.setColorFill(BaseColor.BLACK);
            cb.setFontAndSize(bf, 12);

            // Añade datps del personaje al PDF con las coordenadas x e y

            // Página 1, Datos personales
            addTextAtCoordinates(cb, characterSheet.getCharacterName(), 222.2f, reader.getPageSize(1).getHeight() - 67.0f);
            addTextAtCoordinates(cb, characterSheet.getPlayerName(), 432.2f, reader.getPageSize(1).getHeight() - 67.0f);
            addTextAtCoordinates(cb, characterSheet.getSpecies(), 222.2f, reader.getPageSize(1).getHeight() - 85.0f);
            addTextAtCoordinates(cb, characterSheet.getRank(), 432.2f, reader.getPageSize(1).getHeight() - 85.0f);
            addTextAtCoordinates(cb, characterSheet.getEnvironment(), 222.2f, reader.getPageSize(1).getHeight() - 103.0f);
            addTextAtCoordinates(cb, characterSheet.getUpbringing(), 432.2f, reader.getPageSize(1).getHeight() - 103.0f);
            addTextAtCoordinates(cb, characterSheet.getAssignment(), 222.2f, reader.getPageSize(1).getHeight() - 121.0f);
            addTextAtCoordinates(cb, characterSheet.getCareer(), 432.2f, reader.getPageSize(1).getHeight() - 121.0f);
            addTextAtCoordinates(cb, characterSheet.getTraits(), 222.2f, reader.getPageSize(1).getHeight() - 139.0f);

            // Página 1, Atributos
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getControl()), 213.0f, reader.getPageSize(1).getHeight() - 245.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getDaring()), 291.0f, reader.getPageSize(1).getHeight() - 245.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getPresence()), 369.0f, reader.getPageSize(1).getHeight() - 245.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getFitness()), 213.0f, reader.getPageSize(1).getHeight() - 269.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getInsight()), 291.0f, reader.getPageSize(1).getHeight() - 269.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getReason()), 369.0f, reader.getPageSize(1).getHeight() - 269.0f);

            // Página 1, Habilidades
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getCommand()), 213.0f, reader.getPageSize(1).getHeight() - 318.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getSecurity()), 291.0f, reader.getPageSize(1).getHeight() - 318.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getScience()), 369.0f, reader.getPageSize(1).getHeight() - 318.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getConn()), 213.0f, reader.getPageSize(1).getHeight() - 341.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getEngineering()), 291.0f, reader.getPageSize(1).getHeight() - 341.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getMedicine()), 369.0f, reader.getPageSize(1).getHeight() - 341.0f);

            // Página 1, Valores
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getValues()), 155.0f, reader.getPageSize(1).getHeight() - 385.0f);

            // Página 1, Especialidades
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getFocuses()), 401.0f, reader.getPageSize(1).getHeight() - 242.0f);

            // Página 1, Talentos
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getTalents()), 155.0f, reader.getPageSize(1).getHeight() - 499.0f);

            // Página 1, Estado
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getMaxStress()), 542.0f, reader.getPageSize(1).getHeight() - 446.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getDetermination()), 304.0f, reader.getPageSize(1).getHeight() - 458.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getInjuries()), 401.0f, reader.getPageSize(1).getHeight() - 554.0f);

            // Página 1, Ataques y equipo
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getAttacks()), 210.0f, reader.getPageSize(1).getHeight() - 653.0f);
            addTextAtCoordinates(cb, String.valueOf(characterSheet.getEquipment()), 402.0f, reader.getPageSize(1).getHeight() - 678.0f);

            // Página 2
            PdfContentByte cb2 = stamper.getOverContent(2);
            cb2.setColorFill(BaseColor.BLACK);
            cb2.setFontAndSize(bf, 12);

            // Página 2, Apariencia
            addTextAtCoordinates(cb2, String.valueOf(characterSheet.getAge()), 67.0f, reader.getPageSize(1).getHeight() - 67.0f);

            stamper.close();
            reader.close();


            // Abre el archivo PDF con un intent a una aplicación externa
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PdfUtil", "Error al crear el PDF");
        }
    }

    private static void addTextAtCoordinates(PdfContentByte cb, String text, float x, float y) throws IOException {
        cb.beginText();
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, x, y, 0);
        cb.endText();
    }
}