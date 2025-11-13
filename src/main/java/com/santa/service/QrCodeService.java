package com.santa.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrCodeService {

    public String generateBase64(String groupId) throws WriterException, IOException {
        try{
            String linkUrl = "https://santa.tanuki.click/"+groupId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(linkUrl, BarcodeFormat.QR_CODE, 256, 256);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "jpeg", outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());}
        catch(WriterException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
