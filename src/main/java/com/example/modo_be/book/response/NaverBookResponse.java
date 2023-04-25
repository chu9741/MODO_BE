package com.example.modo_be.book.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverBookResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBookInfo> items;

    @Builder
    public NaverBookResponse(String lastBuildDate, int total, int start, int display, List<NaverBookInfo> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }
}
