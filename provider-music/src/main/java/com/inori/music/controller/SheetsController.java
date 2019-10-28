package com.inori.music.controller;

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

    @GetMapping
    public List<TblSheet> getAllSheet(@RequestHeader("Authorization") String authorization) {
        String userId = fetchUserService.getUserId(authorization);
        System.out.println(userId);
        return shtService.findByCreator(userId);
    }
}