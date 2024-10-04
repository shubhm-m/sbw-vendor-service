package openg2p.vendor.items;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(ItemDTO itemDTO) throws IllegalArgumentException {
        Optional<Item> itemOptional = itemRepository.findBySerialId(itemDTO.getSerialId());
        if (itemOptional.isPresent())
            throw new ValidationException("Serial Id already exits");

        if (itemDTO.getVendorAmount() > itemDTO.getMaxAmount()) {
            throw new ValidationException("Vendor amount cannot exceed maximum amount");
        }
        Item item = new ObjectMapper().convertValue(itemDTO, Item.class);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
