package com.kidsnara.library.config.naver;

import com.kidsnara.library.dto.naver.SearchBookReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    void isbn으로_책검색(){
        var search = new SearchBookReq();
        search.setQuery("9791189683825");

        var result = naverClient.bookSearch(search);
        String title = result.getItems().get(0).getTitle();
        System.out.println(title);
    }

}