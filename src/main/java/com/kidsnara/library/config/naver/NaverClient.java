package com.kidsnara.library.config.naver;

import com.kidsnara.library.dto.naver.SearchBookReq;
import com.kidsnara.library.dto.naver.SearchBookRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NaverClient {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverSecret;

    @Value("${naver.url.search.book}")
    private String naverSearchUrl;

    public SearchBookRes bookSearch(SearchBookReq searchBookReq) {
        var uri = UriComponentsBuilder
                .fromUriString(naverSearchUrl)
                .queryParams(searchBookReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchBookRes>(){};

        var responseEntity = new RestTemplate()
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        responseType
                );
        return responseEntity.getBody();
    }
}
