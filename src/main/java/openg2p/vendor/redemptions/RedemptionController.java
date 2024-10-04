package openg2p.vendor.redemptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import openg2p.vendor.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/redemption")
public class RedemptionController {

    @Autowired
    private RedemptionService redemptionService;  // This is now the interface

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Object>> addRedemption(@RequestBody RedemptionDTO redemptionDTO) throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        try {
            Redemption savedRedemption = redemptionService.addRedemption(redemptionDTO);
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Successful",
                    datetime,
                    savedRedemption
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Failed: " + e.getMessage(),
                    datetime,
                    null  // No data to return on error
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}

