package io.github.qrcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class Qrcode {
    private int size;
    private String type;
    private String contents;
    private String correction;
    private BufferedImage image;

    public Qrcode(int size, String type, String contents, String correction) {
        this.size = size;
        this.type = type;
        this.contents = contents;
        this.correction = correction;

        QRCodeWriter writer = new QRCodeWriter();
        ErrorCorrectionLevel errorCorrectionLevel = null;
        switch (correction){
            case "L" -> errorCorrectionLevel = ErrorCorrectionLevel.L;
            case "M" -> errorCorrectionLevel = ErrorCorrectionLevel.M;
            case "Q" -> errorCorrectionLevel = ErrorCorrectionLevel.Q;
            case "H" -> errorCorrectionLevel = ErrorCorrectionLevel.H;
        }
        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            image = MatrixToImageWriter.toBufferedImage(bitMatrix); //переводит матрицу из битов в изображение
        } catch (WriterException e) {
            throw new RuntimeException();
        }
    }

    public byte[] toByteArray(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); //пустой контейнер baos
            if(type.equals("PNG")){
                ImageIO.write(image, "png", baos); // записывает PNG-представление картинки в контейнер
            }
            else if(type.equals("JPEG")){
                ImageIO.write(image, "jpeg", baos); // записывает JPEG-представление картинки в контейнер
            }
            else if(type.equals("GIF")){
                ImageIO.write(image, "gif", baos); // записывает GIF-представление картинки в контейнер
            }
            return baos.toByteArray(); //возвращает все байты контейнера как массив byte[]
        }
        catch (IOException e){
            throw new RuntimeException();
        }
    }
}
