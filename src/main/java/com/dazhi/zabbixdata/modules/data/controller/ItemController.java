package com.dazhi.zabbixdata.modules.data.controller;

import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import com.dazhi.zabbixdata.modules.data.service.ItemService;
import com.dazhi.zabbixdata.common.uitls.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "列出所有监控项对应信息")
    @GetMapping("/listItem")
    public Result listItem(@RequestParam(defaultValue = "0") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Sort.Order order = Sort.Order.asc("mappingName");
        Sort.Order order2 = Sort.Order.desc("weight");
        Sort sort = Sort.by(order, order2);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<ItemNameMapping> excelPage = itemService.listItem(pageable);
        return Result.ok(excelPage);
    }

    @ApiOperation(value = "新增Item")
    @PostMapping("/addItem")
    public Result addItem(@RequestBody ItemNameMapping itemNameMapping) {
        itemService.addItem(itemNameMapping);
        return Result.ok();
    }

    @ApiOperation(value = "更新Item")
    @PutMapping("/updateItem")
    public Result updateItem(@RequestBody ItemNameMapping itemNameMapping) {
        itemService.updateItem(itemNameMapping);
        return Result.ok();
    }

    @ApiOperation(value = "删除Item")
    @DeleteMapping("/delete/{id}")
    public Result updateItem(@PathVariable("id") Integer id) {
        itemService.deleteItemById(id);
        return Result.ok();
    }
}
