<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="board-body">
    <div class="board-tit">
        <span><c:out value="${requestScope.detailData.title}"/></span>
    </div>
    <div class="board-info">
        <span>${requestScope.detailData.rdt}</span>
        <a href="/user/info?writer=${requestScope.detailData.writer}"><span>${requestScope.detailData.writerNm}</span></a>
        <span>${requestScope.detailData.hit}</span>
    </div>
    <div class="board-ctnt">
        <span id="ctnt"><c:out value="${requestScope.detailData.ctnt}"/></span>
    </div>
    <c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.iuser == requestScope.detailData.writer}">
        <div class="board-btn">
            <a href="/board/del?iboard=${requestScope.detailData.iboard}" class="disableLink"
               onclick="delBoardBtn(${requestScope.detailData.iboard})">
                <button>삭제</button>
            </a>
            <a href="/board/regmod?iboard=${requestScope.detailData.iboard}">
                <button>수정</button>
            </a>
        </div>
    </c:if>


    <div>
        <table>
            <thead>
            <tr>
                <th>작성자</th>
                <th>내용</th>
                <th>날짜</th>
                <th>비고</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cmtItem" items="${requestScope.cmtData}">
                <tr>
                    <td>${cmtItem.writerNm}</td>
                    <td><c:out value="${cmtItem.ctnt}"/></td>
                    <td>${cmtItem.rdt}</td>
                    <td><c:if test="${sessionScope.loginUser.iuser == cmtItem.writer}">
                        <button onclick="delCmt(${cmtItem.icmt}, ${requestScope.detailData.iboard})">삭제</button>
                        <button id="modCmtBtn" onclick="openModForm(${cmtItem.icmt}, '${cmtItem.ctnt}');">수정</button>
                    </c:if></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${sessionScope.loginUser != null}">
        <form action="/board/cmt/reg" method="post">
            <div>
                <input type="hidden" name="iboard" value="${requestScope.detailData.iboard}">
                <input type="text" name="ctnt" class="cmt-text">
                <input type="submit" value="댓글 쓰기" class="cmtBtn">
            </div>
        </form>
    </c:if>
</div>
<div class="openModContainer">
    <div class="openModFrm">
        <div class="cmtModBox">
            <form action="/board/cmt/mod" method="post" id="cmtModFrm">
                <input type="hidden" name="iboard" value="${requestScope.detailData.iboard}">
                <input type="text" name="ctnt" placeholder="ctnt">
                <input type="submit" value="저장">
            </form>
            <button onclick="cancelCmtMod()">취소</button>
        </div>
    </div>
</div>
<script src="/res/js/board/detail.js"></script>