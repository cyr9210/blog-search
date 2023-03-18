# 블로그 검색 API 

## 요구 사항
- [ ] 블로그 검색
   - [ ] 키워드를 통해 블로그 검색
     - 검색 소스는 [카카오 API의 키워드로 블로그 검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.
   - [ ] 정확도순, 최신순 정렬 기능
   - [ ] 페이징 처리
   - [ ] failover [네이버 블로그 검색](https://developers.naver.com/docs/serviceapi/search/blog/blog.md)
- [ ] 인기 검색어 목록
  - [ ] 많이 검색한 순서 top10
  - [ ] 검색된 횟수 추가

## Spec
- Java 11
- SpringBoot 2.7.9
- restdocs
- Feign Client
