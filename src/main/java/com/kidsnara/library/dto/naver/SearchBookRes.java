package com.kidsnara.library.dto.naver;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class SearchBookRes {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<SearchItem> items;

    @Getter @Setter
    public static class SearchItem {
        private String title;
        private String author;
        private int discount; // 가격
        private String publisher;
        private String isbn;
    }

}
