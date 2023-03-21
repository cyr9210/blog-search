# 블로그 검색 API 

## 요구 사항
- [x] 블로그 검색
   - [x] 키워드를 통해 블로그 검색
     - 검색 소스는 [카카오 API의 키워드로 블로그 검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.
   - [x] 정확도순, 최신순 정렬 기능
   - [x] 페이징 처리
   - [ ] failover [네이버 블로그 검색](https://developers.naver.com/docs/serviceapi/search/blog/blog.md)
- [x] 인기 검색어 목록
  - [x] 많이 검색한 순서 top10
  - [x] 검색된 횟수 추가

## Spec
- Java 11
- SpringBoot 2.7.9
- Feign Client
- Spock (test framework)

## 우대사항
- [ ] 멀티모듈
- [ ] 데이터가 많고 트리픽이 많은 환경 동시성 제어

### 동시성 제어 고민..
- redisson 분산락
    - redis 사용안하기로 결정
- 낙관적락 or 비관적락 중 고민
  - 트래픽과 데이터가 많음.

## 필요 추가 작업사항
- [ ] index 추가
- [ ] feign client 헤더 주입
