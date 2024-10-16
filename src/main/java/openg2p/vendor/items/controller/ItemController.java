package openg2p.vendor.items.controller;

import openg2p.vendor.items.dto.ItemDTO;
import openg2p.vendor.items.service.ItemService;
import openg2p.vendor.items.entity.Item;
import openg2p.vendor.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<Object>> addItem(@Valid @RequestBody ItemDTO item) {
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
                    HttpStatus.OK.value(),
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

    @GetMapping("/list/vendor/{id}")
    public ResponseEntity<ApiResponse<Object>> getItemListByVendorId(@PathVariable Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);
        List<Item> items = itemService.getItemListByVendorId(id);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successful",
                datetime,
                items
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);
        Item item = itemService.getItemById(id);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successful",
                datetime,
                item
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Object>> updateItem(@Valid @RequestBody ItemDTO itemDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);
        if (itemDTO.getId()==null)
            throw new ValidationException("Id is required");
        Item item = itemService.updateItem(itemDTO);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Updated Successfully",
                datetime,
                item
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
