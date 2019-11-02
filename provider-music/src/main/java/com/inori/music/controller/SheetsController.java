package com.inori.music.controller;

import com.inori.music.dto.SheetDTO;
import com.inori.music.pojo.TblSheet;
import com.inori.music.service.FetchUserService;
import com.inori.music.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("sheets")
@RestController
public class SheetsController {
    @Autowired
    private FetchUserService fetchUserService;
    @Autowired
    private SheetService shtService;

    @GetMapping("createdBy")
    public List<TblSheet> getSheetsByCreator(@RequestParam("userId") String userId) {
        return shtService.wrapWithImagePath(shtService.findByCreator(userId));
    }

    @GetMapping("collectedBy")
    public List<TblSheet> getSheetsByCollector(@RequestParam("userId") String userId) {
        return shtService.wrapWithImagePath(shtService.findByCollector(userId));
    }

    @GetMapping("all")
    public List<TblSheet> getAllSheets() {
        return shtService.wrapWithImagePath(shtService.findAll());
    }
}
