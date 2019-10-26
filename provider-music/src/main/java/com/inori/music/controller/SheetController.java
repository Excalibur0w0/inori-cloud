package com.inori.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inori.music.client.UserServiceClient;
import com.inori.music.pojo.TblSheet;
import com.inori.music.service.FetchUserService;
import com.inori.music.service.SheetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.undo.AbstractUndoableEdit;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("sheet")
public class SheetController {
    @Autowired
    private SheetService shtService;
    @Autowired
    private FetchUserService fetchUserService;

    @GetMapping
    public TblSheet find(@RequestParam("sheetId")String sheetId) {
        return shtService.findById(sheetId);
    }

    @PostMapping
    public TblSheet create(@RequestParam("sheetName") String sheetName,
                                @RequestParam("desc") String desc,
                                @RequestParam(name = "songList", required = false) List<String> songs,
                                @RequestHeader("Authorization") String authorization) {
        String userId = fetchUserService.getUserId(authorization);
        TblSheet sht = new TblSheet();
        sht.setShtCreator(userId);
        sht.setShtName(sheetName);
        sht.setShtDesc(desc);

        if (songs != null) {
            return shtService.createSheetBySongsId(sht, songs, userId);
        } else {
            return shtService.createEmptySheet(sht, userId);
        }
    }

    @PutMapping
    public TblSheet update(@RequestParam("sheetId") String sheetId,
                               @RequestParam("sheetName") String sheetName,
                               @RequestParam("desc") String desc) {
        TblSheet tblSheet = new TblSheet();
        tblSheet.setShtName(sheetName);
        tblSheet.setShtDesc(desc);

        return shtService.updateSheet(sheetId, tblSheet);
    }

    @DeleteMapping
    public boolean delete(@RequestParam("sheetId")String sheetId) {
        shtService.deleteSheet(sheetId);
        return true;
    }

}
