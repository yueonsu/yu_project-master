<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="board-body">
    <div class="board-tit">
        <span><c:out value="${requestScope.detailData.title}"/></span>
    </div>
    <div class="board-info">
        <span>${requestScope.detailData.rdt}</span>
        <a href="/user/userInfo"><span>${requestScope.detailData.writerNm}</span></a>
        <span>${requestScope.detailData.hit}</span>
    </div>
    <div class="board-ctnt">
        <span><c:out value="${requestScope.detailData.ctnt}"/></span>
    </div>
    <c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.iuser == requestScope.detailData.writer}">
        <div class="board-btn">
            <a href="/board/del?iboard=${requestScope.detailData.iboard}"><button>삭제</button></a>
            <a href="/board/regmod?iboard=${requestScope.detailData.iboard}"><button>수정</button></a>
        </div>
    </c:if>
</div>