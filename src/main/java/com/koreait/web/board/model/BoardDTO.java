package com.koreait.web.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private int iboard;
    private int searchType;
    private String searchText;
    private int rowCnt;
    private int page;
    private int idx;
}
