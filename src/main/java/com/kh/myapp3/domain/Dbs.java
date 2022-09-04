package com.kh.myapp3.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Dbs {
    private DB db;
    @Data
    static class DB {// 공연정보목록

        private String mt20id; // 공연 ID PF132236
        private String prfnm; //공연명 우리연애할까
        private String prfpdfrom; //공연시작일 2016.05.12
        private String prfpdto; //공연종료일 2016.07.31
        private String fcltynm; // 공연시설명(공연장명) 피가로아트홀(구 훈아트홀)
        private String poster; //공연포스터경로
        private String genrenm; //공연 장르명 연극
        private String prfstate; //공연상태 공연완료
        private String openrun; // 오픈런 Y

    }
}
