package com.inori.music.service.imp;

import com.inori.music.dao.TblSheetDao;
import com.inori.music.dao.TblSheetSongDao;
import com.inori.music.dao.TblSheetUserDao;
import com.inori.music.pojo.TblSheet;
import com.inori.music.pojo.TblSheetSong;
import com.inori.music.pojo.TblSheetUser;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class SheetServiceImp implements SheetService {
    @Autowired
    private TblSheetDao tblSheetDao;

    @Autowired
    private TblSheetSongDao tblSheetSongDao;

    @Autowired
    private TblSheetUserDao tblSheetUserDao;

    @Override
    public TblSheet findById(String shtId) {
        return tblSheetDao.findById(shtId).orElse(null);
    }

    @Override
    public List<TblSheet> findAll() {
        return tblSheetDao.findAll();
    }

    @Override
    public List<TblSheet> searchSheet(String keywords) {
        return null;
    }

    @Override
    public TblSheet createEmptySheet(TblSheet sheet, String authorId) {
        Date now = new Date(System.currentTimeMillis());

        sheet.setUuid(UUID.randomUUID().toString());
        sheet.setCreatedAt(now);
        sheet.setUpdatedAt(now);
        sheet.setShtCreator(authorId);

        return tblSheetDao.save(sheet);
    }

    @Transactional
    @Override
    public TblSheet createSheetBySongs(TblSheet sheet, List<TblSong> tblSongs, String authorId) {
        Date now = new Date(System.currentTimeMillis());

        sheet.setUuid(UUID.randomUUID().toString());
        sheet.setCreatedAt(now);
        sheet.setUpdatedAt(now);
        sheet.setShtCreator(authorId);

        TblSheet resSheet = tblSheetDao.save(sheet);

        if (resSheet != null) {
            List<TblSheetSong> sheetSongs = new LinkedList<>();
            tblSongs.forEach(song -> sheetSongs.add(
                    new TblSheetSong(UUID.randomUUID().toString(), song.getUuid(), resSheet.getUuid())));
            tblSheetSongDao.saveAll(sheetSongs);
        }

        return resSheet;
    }

    @Transactional
    @Override
    public TblSheet createSheetBySongsId(TblSheet sheet, List<String> songIds, String authorId) {
        Date now = new Date(System.currentTimeMillis());

        sheet.setUuid(UUID.randomUUID().toString());
        sheet.setCreatedAt(now);
        sheet.setUpdatedAt(now);
        sheet.setShtCreator(authorId);

        TblSheet resSheet = tblSheetDao.save(sheet);

        if (resSheet != null) {
            List<TblSheetSong> sheetSongs = new LinkedList<>();
            songIds.forEach(songId -> sheetSongs.add(
                    new TblSheetSong(UUID.randomUUID().toString(), songId, resSheet.getUuid())));
            tblSheetSongDao.saveAll(sheetSongs);
        }
        return resSheet;
    }

    @Override
    public void deleteSheet(String shtId) {
        tblSheetDao.deleteById(shtId);
    }

    /**
     * 获取收藏该歌单的用户数量
     * @param sheetId 歌单Id
     * @return
     */
    @Override
    public Long getCollectionCount(String sheetId) {
        TblSheetUser tblSheetUser = new TblSheetUser();
        tblSheetUser.setShtId(sheetId);

        return tblSheetUserDao.count(Example.of(tblSheetUser));
    }

    /**
     * 用户收藏歌单
     * @param sheetId
     * @param userId
     * @return
     */
    @Override
    public TblSheetUser collectSheet(String sheetId, String userId) {
        TblSheetUser tblSheetUser = new TblSheetUser();

        tblSheetUser.setUuid(UUID.randomUUID().toString());
        tblSheetUser.setShtId(sheetId);
        tblSheetUser.setUserId(userId);

        return tblSheetUserDao.save(tblSheetUser);
    }
}
