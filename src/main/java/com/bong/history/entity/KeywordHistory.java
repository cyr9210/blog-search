package com.bong.history.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(indexes = {
        @Index(name = "uk_keyword", columnList = "keyword", unique = true),
        @Index(name = "idx_count", columnList = "count")
})
public class KeywordHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long count;

    @Column
    private String keyword;

    public KeywordHistory(String keyword) {
        this.keyword = keyword;
        this.count = 1L;
    }

    public void searchKeyword() {
        this.count++;
    }
}
