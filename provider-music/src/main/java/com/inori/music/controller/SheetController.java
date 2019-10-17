package com.inori.music.controller;

import com.inori.music.dao.TblSheetDao;
import com.inori.music.pojo.TblSheet;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.SheetService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class SheetController {
    @Autowired
    private SheetService shtService;
    @Autowired
    private HttpServletRequest request;


    @PostMapping(("createEmptySheet"))
    public ResponseEntity<Boolean> createEmptySheet(@RequestParam("shtName")String sheetName,
                                                    @RequestParam("creator")String userId,
                                                    @RequestParam("desc") String desc) {
        TblSheet sht = new TblSheet();
        sht.setShtCreator(userId);
        sht.setShtName(sheetName);
        sht.setShtDesc(desc);
        TblSheet result = shtService.createEmptySheet(sht, userId);

        return new ResponseEntity<Boolean>(result != null, HttpStatus.OK);
    }

    @PostMapping("createSheet")
    public ResponseEntity<Boolean> createSheet(@RequestParam("shtNameZz")String sheetName,
                                               @RequestParam("creator")String userId,
                                               @RequestParam("desc")String desc,
                                               @RequestParam("songList")List<String> songs) {
        TblSheet sht = new TblSheet();
        sht.setShtCreator(userId);
        sht.setShtName(sheetName);
        sht.setShtDesc(desc);

        shtService.createSheetBySongsId(sht, songs, userId);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
