package com.sitamvan.eshop.item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> getAllItems(Integer page, Integer size);
    Item save(Item item);
    Optional<Item> getItemById(Integer id);
}
