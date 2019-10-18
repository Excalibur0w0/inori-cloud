package com.inori.music.controller;

import com.inori.music.pojo.TblSheet;
import com.inori.music.service.SheetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Log4j2
public class SheetController {
    @Autowired
    private SheetService shtService;
    @Autowired
    private HttpServletRequest request;


    @PostMapping(("createEmptySheet"))
    public TblSheet createEmptySheet(@RequestParam("sheetName") String sheetName,
                                    @RequestParam("creator") String userId,
                                    @RequestParam("desc") String desc) {
        TblSheet sht = new TblSheet();
        sht.setShtCreator(userId);
        sht.setShtName(sheetName);
        sht.setShtDesc(desc);
        TblSheet result = shtService.createEmptySheet(sht, userId);

        return result;
    }

    @PostMapping("createSheet")
    public TblSheet createSheet(@RequestParam("sheetName") String sheetName,
                               @RequestParam("creator") String userId,
                               @RequestParam("desc") String desc,
                               @RequestParam("songList") List<String> songs) {
        TblSheet sht = new TblSheet();
        sht.setShtCreator(userId);
        sht.setShtName(sheetName);
        sht.setShtDesc(desc);

        return shtService.createSheetBySongsId(sht, songs, userId);
    }

    @GetMapping("getAllSheet")
    public List<TblSheet> getAllSheet(@RequestParam("userId") String userId) {

        return shtService.findByCreator(userId);
    }

    @PostMapping("alterSheet")
    public TblSheet alterSheet(@RequestParam("sheetId") String sheetId,
                               @RequestParam("sheetName") String sheetName,
                               @RequestParam("desc") String desc) {
        TblSheet tblSheet = new TblSheet();
        tblSheet.setShtName(sheetName);
        tblSheet.setShtDesc(desc);

        return shtService.updateSheet(sheetId, tblSheet);
    }

    @GetMapping("getSheetInfo")
    public TblSheet getSheetInfo(@RequestParam("sheetId")String sheetId) {
        return shtService.findById(sheetId);
    }

}
