package com.inori.music.service;

import com.inori.music.dto.SongDTO;
import com.inori.music.pojo.FileImg;
import com.inori.music.pojo.TblSong;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {
    TblSong getById(String songId);

    boolean isSongExist(String songId);

    void likeSong(String songId, String userId);

    void dislikeSong(String songId, String userId);

    void collectSong(String sheetId, String songId, String userId);

    void cancelCollect(String sheetId, String songId, String userId);

    List<TblSong> searchAllSongs(String keywords);

    boolean isDislikeSong(String songId, String userId);

    List<TblSong> getSongsByShtId(String shtId);

    List<TblSong> getSongsByUserLike(String userId);

    List<TblSong> getUserFavoriteSongs(String userId);

    Long countSongsByShtId(String shtId);

    List<TblSong> getSongsByUploader(String uploadUserId);

    Long countSongByUploader(String uploadUserId);

    List<TblSong> getAll();

    List<TblSong> getSongsByAuthor(String author);

    boolean uploadSingleSongChunk(String md5,
                                  MultipartFile file,
                                  Long curIndex,
                                  Long totalChunks);

    boolean afterCompletedUpload(String md5, String extension, String uploaderId);

    List<Long> checkSongIntegrity(String md5, Long totalChunks);
    
    Resource getSingleSongByMd5(String md5);

    FileImg getSongImage(String imgPath);

    public List<TblSong> wrapWithFavorite(List<TblSong> list, String userId);
}
