package openg2p.vendor.items;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<Object>> addItem(@Valid @RequestBody Item item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        try {
            Item createdItem = itemService.addItem(item);

            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Item added successfully",
                    datetime,
                    createdItem
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Validation failed: " + e.getMessage(),
                    datetime,
                    null  // No data to return on error
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse<Object>> getAllItems() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        try {
            List<Item> items = itemService.getAllItems();
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Successful",
                    datetime,
                    items
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
