package kr.co.wjin.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.co.wjin.board.domain.ReplyVO;

public interface ReplyStore {

	/**
	 * 댓글 등록 Store
	 * @param session
	 * @param replyVO
	 * @return int
	 */
	int insertReply(SqlSession session, ReplyVO replyVO);

	/**
	 * 댓글 목록 조회 Store
	 * @param session
	 * @return rList
	 */
	List<ReplyVO> selectReplyList(SqlSession session, Integer refBoardNo);

	/**
	 * 댓글 선택 삭제 Store
	 * @param session
	 * @param replyNo
	 * @return int
	 */
	int removeReply(SqlSession session, Integer replyNo);

	/**
	 * 댓글 선택 수정 Store
	 * @param session
	 * @param reply
	 * @return
	 */
	int updateReply(SqlSession session, ReplyVO reply);

}
