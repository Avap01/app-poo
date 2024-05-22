package controller;


	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.multipart.MultipartFile;

	import java.awt.image.BufferedImage;
	import javax.imageio.ImageIO;
	import java.io.ByteArrayOutputStream;
	import java.io.IOException;

	@RestController
	@RequestMapping("/api/images")
	public class ImageController {

	    @PostMapping("/upload")
	    public ResponseEntity<byte[]> uploadImage(@RequestParam("file") MultipartFile file) {
	        try {
	            // Processa a imagem
	            BufferedImage editedImage = processImage(file);

	      
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageIO.write(editedImage, "png", baos);
	            byte[] imageInByte = baos.toByteArray();

	            return ResponseEntity.status(HttpStatus.OK).body(imageInByte);
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    private BufferedImage processImage(MultipartFile file) throws IOException {
	        // Carrega a imagem
	        BufferedImage image = ImageIO.read(file.getInputStream());

	      
	        for (int y = 0; y < image.getHeight(); y++) {
	            for (int x = 0; x < image.getWidth(); x++) {
	                int rgba = image.getRGB(x, y);
	                int alpha = (rgba >> 24) & 0xff;
	                int red = (rgba >> 16) & 0xff;
	                int green = (rgba >> 8) & 0xff;
	                int blue = rgba & 0xff;
	                int gray = (red + green + blue) / 3;
	                int newPixel = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
	                image.setRGB(x, y, newPixel);
	            }
	        }

	        return image;
	    }
	}


