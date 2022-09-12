package com.zoulj.msapp.interfaces.controller;

import com.zoulj.msapp.application.service.ItemService;
import com.zoulj.msapp.infrastructure.annotation.TokenCheck;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.command.ItemCommand;
import com.zoulj.msapp.interfaces.vo.CommonReturnType;
import com.zoulj.msapp.interfaces.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public CommonReturnType createItem(@Valid @RequestBody ItemCommand itemCommand) throws BusinessException {
        ItemVO itemVO = itemService.createItem(itemCommand);
        return CommonReturnType.create(itemVO);
    }

    @GetMapping("/get")
    @TokenCheck(check = false)
    public CommonReturnType getItem(@RequestParam(value = "id") String id) {
        return CommonReturnType.create(itemService.getItemById(Long.valueOf(id)));
    }

    @GetMapping("/get-list")
    @TokenCheck(check = false)
    public CommonReturnType listItem(@RequestParam(value = "title",required = false) String title,
                                     @RequestParam(value = "pageNo",required = false,defaultValue = "-1") Integer pageNo,
                                     @RequestParam(value = "pageSize",required = false,defaultValue = "-1") Integer pageSize) {
        return CommonReturnType.create(itemService.listItem(title, pageNo, pageSize));
    }
}
