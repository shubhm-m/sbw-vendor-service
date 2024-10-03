package openg2p.vendor.items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(Item item) throws IllegalArgumentException {
        // Validate vendor amount against max amount
        if (item.getVendorAmount() > item.getMaxAmount()) {
            throw new ValidationException("Vendor amount cannot exceed maximum amount");
        }
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
