<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세조회</title>
</head>
<body>
	<h2>공지사항 상세조회</h2>
	<form action="/notice/modify.kh" method="post" enctype="multipart/form-data">
	<input type="hidden" name="noticeNo" value=${notice.noticeNo }>
	<!-- 	input박스로 값을 받아야 null 입력 방지가 가능하다. -->
	<input type="hidden" name="noticeFilename" value="${notice.noticeFilename }"/>
	<input type="hidden" name="noticeFileRename" value="${notice.noticeFileRename }"/>
	<input type="hidden" name="noticeFilepath" value="${notice.noticeFilepath }"/>
	<input type="hidden" name="noticeFilelength" value="${notice.noticeFilelength }"/>
		<ul>
			<li>
				<label>제목</label>
				<input type="text" name="noticeSubject" value="${notice.noticeSubject }"/>
<%-- 				<span>${notice.noticeSubject }</span> --%>
			</li>
			<li>
				<label>작성자</label>
				<input type="text" value="${notice.noticeWriter }"/>
<%-- 				<span>${notice.noticeWriter }</span> --%>
			</li>
			<li>
				<label>내용</label>
				<textarea rows="4" cols="50" name="noticeContent">${notice.noticeContent }</textarea>
<%-- 				<span>${notice.noticeContent }</span> --%>
			</li>
			<li>
				<label>첨부파일</label>
<%-- 				<input type="text" value="${notice.noticeSubject }"/> --%>
				<span><a href="../resources/nuploadFiles/${notice.noticeFileRename }" download> ${notice.noticeFilename }</a></span>
				<input type="file" name="reloadFile">
			</li>
		</ul>
		<br>
		<br>
		<div>
			<input type="submit" value="수정하기">
		</div>
	</form>
	<script>
// 		function showModifyPage() {
// 			var noticeNo = "${notice.noticeNo }";
// 			location.href = "/notice/modify.kh?noticeNo="+noticeNo;
// 		}
// 		function showNoticeList() {
// 			location.href = "/notice/list.kh;
// 		}
 	</script>
</body>
</html>