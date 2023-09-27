package com.sitamvan.eshop.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemController {
    ItemService itemService;

    private ModelMapper modelMapper;

    public ItemController(ItemService itemService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDto>> getAllItems(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        List<Item> items = itemService.getAllItems(page, size);
        List<ItemDto> itemDtos = items.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto) {
        try {
            Item item = itemService.save(new Item(itemDto.getName(), itemDto.getDesc(), itemDto.getCategory(),
                    itemDto.getPrice(), itemDto.getRating(), itemDto.getStock()));
            return new ResponseEntity<>(convertToDto(item), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ItemDto convertToDto(Item item) {
        ItemDto dto = modelMapper.map(item, ItemDto.class);
        return dto;
    }
}
