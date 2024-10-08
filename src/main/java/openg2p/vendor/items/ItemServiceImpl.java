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

    @Override
    public Item getItemById(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public Item updateItem(ItemDTO itemDTO) {
        Optional<Item> itemOptional = itemRepository.findById(itemDTO.getId());
        if (itemOptional.isPresent()) {
            if (!(itemDTO.getSerialId()).equals(itemOptional.get().getSerialId())) {
                itemOptional = itemRepository.findBySerialIdAndIdNot(itemDTO.getSerialId(), itemDTO.getId());
                if (itemOptional.isPresent())
                    throw new ValidationException("Serial Id is mapped with another item");
            }
            if (itemDTO.getVendorAmount() > itemDTO.getMaxAmount()) {
                throw new ValidationException("Vendor amount cannot exceed maximum amount");
            }
            Item item = new ObjectMapper().convertValue(itemDTO, Item.class);
            return itemRepository.save(item);
        }
        throw new ValidationException("Item not found with ID: " + itemDTO.getId());
    }
}
