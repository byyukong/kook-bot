package com.kook.pojo.music.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hy
 * @date 2022/11/30 14:01
 */

@NoArgsConstructor
@Data
public class WyyMusicVo {
    private ResponseDataDTO responseData;

    @NoArgsConstructor
    @Data
    public static class ResponseDataDTO {
        private Integer recordCount;
        private List<RecordsDTO> records;

        @NoArgsConstructor
        @Data
        public static class RecordsDTO {
            private String id;
            private String name;
            private Integer duration;
            private List<ArtistsDTO> artists;
            private ResponseDataDTO.RecordsDTO.AlbumDTO album;
            private Boolean playFlag;
            private Boolean downloadFlag;
            private Boolean payPlayFlag;
            private Boolean payDownloadFlag;
            private Boolean vipFlag;
            private Boolean liked;
            private String coverImgUrl;
            private Boolean vipPlayFlag;
            private Integer songMaxBr;
            private Object songTag;
            private Object alg;
            private Boolean visible;

            @NoArgsConstructor
            @Data
            public static class AlbumDTO {
                private String id;
                private String name;
            }

            @NoArgsConstructor
            @Data
            public static class ArtistsDTO {
                private String id;
                private String name;
            }
        }
    }
}
