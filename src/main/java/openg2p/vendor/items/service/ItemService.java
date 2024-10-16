package openg2p.vendor.items.service;

import openg2p.vendor.items.dto.ItemDTO;
import openg2p.vendor.items.entity.Item;

import java.util.List;

public interface ItemService {
    Item addItem(ItemDTO item) throws IllegalArgumentException;
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item updateItem(ItemDTO itemDTO);
    List<Item> getItemListByVendorId(Long vendorId);
}
