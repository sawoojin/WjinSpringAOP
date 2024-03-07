<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세조회</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<h2>공지사항 상세조회</h2>
	<ul>
		<li>
			<label>제목</label>
			<span>${notice.noticeSubject }</span>
		</li>
		<li>
			<label>작성자</label>
			<span>${notice.noticeWriter }</span>
		</li>
		<li>
			<label>내용</label>
			<span>${notice.noticeContent }</span>
		</li>
		<li>
			<label>첨부파일</label>
			<span><a href="../resources/nuploadFiles/${notice.noticeFileRename }" download> ${notice.noticeFilename }</a></span>
		</li>
	</ul>
	<br>
	<br>
	<div>
		<button type="button" onclick="showModifyPage();">수정하기</button>
		<button type="button" onclick="deleteNotice(${notice.noticeNo });">삭제하기</button>
		<button type="button" onclick="showNoticeList();">목록으로 이동</button>
	</div>
	<hr>
	<form action="/reply/add.kh" method="post">
		<input type="hidden" name="refBoardNo" id="refBoardNo" value="${board.boardNo }">
		<table width="500" border="1">
			<tr>
				<td>
					<input type="text" id="replyContent">
				</td>
				<td>
					<button type="button" id="rSubmit">댓글등록</button>
				</td>
			</tr>
		</table>
	</form>
		
	<!-- 댓글목록 -->
	<table width="550" border="1" id="replyTable">
		<tbody></tbody>
	</table>
	
	<script>
		function deleteNotice(noticeNo) {
			if(confirm("삭제 ㄱ?")) {
				location.href = "/notice/delete.kh?noticeNo="+noticeNo;
			}
		}
		function showModifyPage() {
			var noticeNo = "${notice.noticeNo }";
			location.href = "/notice/modify.kh?noticeNo="+noticeNo;
		}
		function showNoticeList() {
			location.href = "/notice/list.kh";
		}
		// ajax
		$("#rSubmit").on("click", function() {
			var refBoardNo = $("#refBoardNo").val();
			var replyContent = $("#replyContent").val();
			$.ajax({
				url : "/reply/add.kh",
				data : {"refBoardNo" : refBoardNo, "replyContent" : replyContent},
				type : "POST",
				success : function(result) {
					getReplyList();
					$("#replyContent").val("");
				},
				error : function() {
					alert("실패");
				}
			});
		});
	</script>
</body>
</html>