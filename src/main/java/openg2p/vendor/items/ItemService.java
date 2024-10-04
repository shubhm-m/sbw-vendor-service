package openg2p.vendor.items;
import java.util.List;

public interface ItemService {
    Item addItem(ItemDTO item) throws IllegalArgumentException;
    List<Item> getAllItems();
}
