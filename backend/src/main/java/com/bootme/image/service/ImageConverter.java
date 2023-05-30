package com.bootme.image.service;

import com.bootme.common.exception.FileHandlingException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static com.bootme.common.exception.ErrorType.FILE_CONVERSION_FAIL;

@Service
@RequiredArgsConstructor
public class ImageConverter {

    private final Tika tika;

    public File convertToProgressive(File file) {
        try (ImageOutputStream iops = new FileImageOutputStream(file)) {
            ImageWriter imageWriter = ImageIO.getImageWritersByMIMEType(tika.detect(file)).next();
            imageWriter.setOutput(iops);

            final BufferedImage image = ImageIO.read(file);
            IIOImage iioImage = new IIOImage(image, null, null);

            imageWriter.write(null, iioImage, createImageWriterWithProgressiveMode());
            imageWriter.dispose();
            return file;
        } catch (IOException e) {
            throw new FileHandlingException(FILE_CONVERSION_FAIL, e.getMessage());
        }
    }

    private ImageWriteParam createImageWriterWithProgressiveMode() {
        JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.KOREA);
        imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        return imageWriteParam;
    }

}
