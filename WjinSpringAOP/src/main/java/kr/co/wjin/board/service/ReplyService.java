package kr.co.wjin.board.service;

import java.util.List;

import kr.co.wjin.board.domain.ReplyVO;

public interface ReplyService {

	/**
	 * 댓글 등록 Service
	 * @param replyVO
	 * @return int
	 */
	int insertReply(ReplyVO replyVO);

	/**
	 * 댓글 목록 조회 Service
	 * @return List
	 */
	List<ReplyVO> selectReplyList(Integer refBoardNo);

	/**
	 * 댓글 선택 삭제 Service
	 * @param refBoardNo
	 * @return int
	 */
	int removeReply(Integer replyNo);

	/**
	 * 댓글 선택 수정 Service
	 * @param reply
	 * @return int
	 */
	int updateReply(ReplyVO reply);

}
