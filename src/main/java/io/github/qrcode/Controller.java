package io.github.qrcode;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/api/health")
    public ResponseEntity<Void> getHealth() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<?> getQrcode(@RequestParam(name = "contents") String contents,
                                       @RequestParam(name = "size", required = false, defaultValue = "250") int size,
                                       @RequestParam(name = "correction", required = false, defaultValue = "L") String correction,
                                       @RequestParam(name = "type", required = false, defaultValue = "png") String type) {
        type = type.toUpperCase();
        correction = correction.toUpperCase();
        if (contents == null || contents.isBlank()) {
            ErrorResponse errorResponse = new ErrorResponse("Contents cannot be null or blank");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (size > 350 || size < 150) {
            ErrorResponse errorResponse = new ErrorResponse("Image size must be between 150 and 350 pixels");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if(!correction.equals("L") && !correction.equals("M") && !correction.equals("Q") && !correction.equals("H")){
            ErrorResponse errorResponse = new ErrorResponse("Permitted error correction levels are L, M, Q, H");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (!type.equals("PNG") && !type.equals("JPEG") && !type.equals("GIF")) {
            ErrorResponse errorResponse = new ErrorResponse("Only png, jpeg and gif image types are supported");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Qrcode qrcode = new Qrcode(size, type, contents, correction);
        MediaType contentType;
        switch (type) {
            case "JPEG" -> contentType = MediaType.IMAGE_JPEG;
            case "GIF" -> contentType = MediaType.IMAGE_GIF;
            default -> contentType = MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok().contentType(contentType).body(qrcode.toByteArray());
    }
}
