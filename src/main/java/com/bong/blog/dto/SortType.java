package com.bong.blog.dto;

import com.bong.search.service.ServiceType;
import lombok.Getter;

@Getter
public enum SortType {
    ACCURACY("정확도순", "accuracy", "sim"),
    RECENCY("최신순", "recency", "date");

    private final String meaning;
    private final String kakaoSortString;
    private final String naverSortString;

    SortType(String meaning, String sortString, String naverSortString) {
        this.meaning = meaning;
        this.kakaoSortString = sortString;
        this.naverSortString = naverSortString;
    }

    public String getSortString(ServiceType serviceType) {
        return ServiceType.KAKAO == serviceType ? getKakaoSortString() : getNaverSortString();
    }
}
