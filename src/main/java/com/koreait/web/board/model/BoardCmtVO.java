package com.koreait.web.board.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCmtVO {
    private int icmt;
    private int iboard;
    private String ctnt;
    private int writer;
    private String rdt;
    private String writerNm;
}
