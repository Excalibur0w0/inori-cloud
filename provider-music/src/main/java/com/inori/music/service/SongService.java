package com.inori.music.service;

import com.inori.music.pojo.TblSong;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {
    TblSong getById(String songId);

    void likeSong(String songId, String userId);

    List<TblSong> searchAllSongs(String keywords);

    void dislikeSong(String songId, String userId);

    List<TblSong> getSongsByShtId(String shtId);

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
}
