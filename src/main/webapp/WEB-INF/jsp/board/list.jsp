<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="if" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="listContainer">
    <div class="list">
        <div>
            <form action="" method="get" id="frm">
                <select name="searchType">
                    <option value="1" ${1 == param.searchType ? 'selected':''}>전체</option>
                    <option value="2" ${2 == param.searchType ? 'selected':''}>제목</option>
                    <option value="3" ${3 == param.searchType ? 'selected':''}>내용</option>
                    <option value="4" ${4 == param.searchType ? 'selected':''}>작성자</option>
                </select>
                <input type="search" name="searchText" value="${param.searchText}" placeholder="검색">
                <input type="submit" value="검색">

                <select name="rowCnt">
                    <c:forEach var="rowCnt" begin="20" end="50" step="10">
                        <option value="${rowCnt}" ${rowCnt == param.rowCnt ? 'selected' : ''}>${rowCnt}개</option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <c:choose>
            <c:when test="${requestScope.list == null }">
                <div class="unDefined">
                    <span>글이 없음.</span>
                </div>
            </c:when>

            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>글 번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일시</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="item">
                        <c:set var="eachTitle" value="${item.title}"/>
                        <c:set var="eachWriterNm" value="${item.writerNm}"/>
                        <c:if test="${param.searchType == 2}">
                            <c:set var="eachTitle" value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}"/>
                        </c:if>
                        <c:if test="${param.searchType == 4}">
                            <c:set var="eachWriterNm" value="${fn:replace(eachWriterNm, param.searchText, '<mark>'+=param.searchText+='</mark>')}"/>
                        </c:if>
                        <tr class="tableList" onclick="moveToDetail(${item.iboard});">
                            <td>${item.iboard}</td>
                            <td>${eachTitle}</td>
                            <td>${eachWriterNm}</td>
                            <td>${item.hit}</td>
                            <td>${item.rdt}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="page">
                    <c:forEach var="page" begin="1" end="${requestScope.maxPage}">
                        <a href="/board/list?page=${page}&searchType=${param.searchType}&searchText=${param.searchText}&rowCnt=${param.rowCnt}">
                            <span class="${page == param.page || ( page == 1 && param.page == null) ? 'thisPage': ''}">${page}</span>
                        </a>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>


    <div class="rightDiv">

        <div class="userDiv">
            <c:choose>
                <c:when test="${loginUser != null}">
                    <div class="listLogin">
                        <p>${sessionScope.loginUser.uid}(${sessionScope.loginUser.nm})님 환영합니다.</p>
                        <div class="detailLogin">
                            <a href="">회원정보</a>
                            <a href="/user/logout">Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="detailLogin">
                        <form action="/board/list" method="post" id="loginFrm">
                            <div class="err">${err}</div>
                            <div class="idpw_input">
                                <input type="text" name="uid" placeholder="User ID">
                            </div>
                            <div class="idpw_input">
                                <input type="password" name="upw" placeholder="User Password">
                            </div>
                            <div class="flex-input-box">
                                <input type="submit" value="Login">
                                <a href="/user/join"><input type="button" value="Join"></a>
                                <input type="button" id="chkPw" value="비밀번호 보이기"></input>
                            </div>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="hotList">
            <table>
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.hotList}" var="item">
                    <tr class="tableList" onclick="moveToDetail(${item.iboard});">
                        <td><c:out value="${item.title}"/></td>
                        <td>${item.writerNm}</td>
                        <td>${item.hit}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="/res/js/board/list.js"></script>