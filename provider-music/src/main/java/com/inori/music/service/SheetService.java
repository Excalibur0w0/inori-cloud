package com.inori.music.service;

import com.inori.music.dto.SheetDTO;
import com.inori.music.pojo.TblSheet;
import com.inori.music.pojo.TblSheetUser;
import com.inori.music.pojo.TblSong;

import java.util.List;

public interface SheetService {
    TblSheet findById(String shtId);

    SheetDTO findDTOById(String sheetId);

    List<TblSheet> findAll();

    List<TblSheet> findByCreator(String authorId);

    List<TblSheet> findByCollector(String collectUserId);

    List<TblSheet> searchSheet(String keywords);

    TblSheet createEmptySheet(TblSheet sheet, String userId);

    TblSheet createSheetBySongs(TblSheet sheet, List<TblSong> tblSongs, String userId);

    // 如果部队songId作安全性验证，很可能不能保证数据一致性，重构时需要注意
    TblSheet createSheetBySongsId(TblSheet sheet, List<String> songIds, String authorId);

    void deleteSheet(String shtId);

    /**
     * 获取收藏此歌单的人数
     * @param sheetId 歌单Id
     * @return 收藏数量
     */
    Long getCollectionCount(String sheetId);

    TblSheetUser collectSheet(String sheetId, String userId);

    boolean cancelCollectSheet(String sheetId, String userId);

    TblSheet updateSheet(String sheetId, TblSheet newSheet);

    List<TblSheet> wrapWithImagePath(List<TblSheet> list);
}
