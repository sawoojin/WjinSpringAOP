package kr.co.wjin.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.wjin.board.domain.ReplyVO;
import kr.co.wjin.board.service.ReplyService;
import kr.co.wjin.board.store.ReplyStore;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyStore rStore;
	
	@Autowired
	private SqlSession session;
	
	@Override
	public int insertReply(ReplyVO replyVO) {
		int result = rStore.insertReply(session, replyVO);
		return result;
	}

	@Override
	public List<ReplyVO> selectReplyList(Integer refBoardNo) {
		List<ReplyVO> rList = rStore.selectReplyList(session, refBoardNo);
		return rList;
	}

	@Override
	public int removeReply(Integer replyNo) {
		int result = rStore.removeReply(session, replyNo);
		return result;
	}

	@Override
	public int updateReply(ReplyVO reply) {
		int result = rStore.updateReply(session, reply);
		return result;
	}

}
