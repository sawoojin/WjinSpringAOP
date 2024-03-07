<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="../resources/css/main.css">
</head>
<body>
	<h1>게시글 상세</h1>
	<ul>
		<li>
			<label>제목</label>
			<span>${board.boardTitle }</span>
		</li>
		<li>
			<label>작성자</label>
			<span>${board.boardWriter }</span>
		</li>
		<li>
			<label>내용</label>
			<p>${board.boardContent }</p>
		</li>
		<li>
			<label>첨부파일</label>
			<a href="#">${board.boardFilename }</a>
		</li>
	</ul>
	<br>
	<div>
		<button>수정하라</button>
		<button>삭제하리</button>
		<button>목록가라</button>
		<button>뒤로가라</button>
	</div>
	<!-- 댓글등록 -->
	<hr>
	<form action="/reply/add.kh" method="post">
		<input type="hidden" name="refBoardNo" id="refBoardNo" value="${board.boardNo }">
		<table width="500" border="1">
			<tr>
				<td>
					<input type="text" name="replyContent" id="replyContent" size="">
				</td>
				<td>
					<input type="button" id ="rSubmit" value="완료"> 
				</td>
			</tr>
		</table>
	</form>
	<!-- 댓글목록 -->
	<table width="550" border="1" id="replyTable">
		<tbody></tbody>
<%-- 	<c:forEach items="${rList }" var="reply"> --%>
<!-- 		<tr> -->
<%-- 			<td>${reply.replyWriter }</td> --%>
<%-- 			<td>${reply.replyContent }</td> --%>
<%-- 			<td>${reply.rCreateDate }</td> --%>
<!-- 			<td> -->
<!-- 				<a href="#">수정</a> -->
<!-- 				<a href="#">삭제</a> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<%-- 	</c:forEach> --%>
	</table>
	<script>
		getReplyList();
		function getReplyList() {
			var refBoardNo = $("#refBoardNo").val();
			$.ajax({
				url : "/reply/list.kh",
				data : {"refBoardNo" : refBoardNo},
				type : "GET",
				success : function(result) {
					var tableBody = $("#replyTable tbody");
					tableBody.empty();
// 					tableBody.html("");
					var tr;
					var replyWriter;
					var replyContent;
					var rCreateDate;
					var btnArea;
					if(result.length > 0) {
						for(var i in result) {
							var replyWriterVal = result[i].replyWriter;
							var replyContetnVal = result[i].replyContent;
							var rCreateDateVal = result[i].rCreateDate;
							var replyNoVal = result[i].replyNo;
							tr = $("<tr>") // <tr></tr>
							replyWriter = $("<td>").text(replyWriterVal)
							replyContent = $("<td>").text(replyContetnVal);
							rCreateDate = $("<td width='100'>").text(rCreateDateVal);
							btnArea = $("<td width='90'>")
											.append("<a href='javascript:void(0)' onclick='modifyViewReply(this, "+replyNoVal+", \""+replyContetnVal+"\");'>수정</a> ")
											.append("<a href='javascript:void(0)' onclick='removeReply("+replyNoVal+");'>삭제</a></td>");
							tr.append(replyWriter);
							tr.append(replyContent);
							tr.append(rCreateDate);
							tr.append(btnArea);
							tableBody.append(tr);
						}
					}
				},
				error : function() {
					alert("Ajax는 실패작입니다");
				}
			});
		}
		function modifyViewReply(obj, rNo, rContent) {
			console.log(obj);
			var tr = $("<tr>");
			tr.append("<td colspan='3'><input type='text' size='50' value='"+rContent+"'></td>");
			tr.append("<td><button type='button' onclick='modifyReply("+rNo+", this);'>수정끗</button></td>");
			$(obj).parent().parent().after(tr);
// 			$("#replyTable tbody").append(tr);
// 			$("<td>").append("<input type='text'>");
		}
		function modifyReply(replyNo, obj) {
			var replyContent = $(obj).parent().prev().children().val();
			$.ajax({
				url : "/reply/update.kh",
				data : {
					"replyNo" : replyNo,
					"replyContent" : replyContent
				},
				type : "POST",
				success : function(result) {
					alert(result);
					getReplyList();
				},
				error : function() {
					alert("Ajax는 실패작입니다 수정");
				}
			});
		}
		function removeReply(replyNo) {
// 			var replyNo = 11;
			$.ajax({
				url : "/reply/remove.kh",
				data : {"replyNo" : replyNo},
				type : "POST",
				success : function(result) {
// 					alert(result);
					getReplyList();
				},
				error : function() {
					alert("Ajax는 실패작입니다 삭제");
				}
			});
		}
		
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