package kr.co.wjin.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.wjin.board.domain.BoardVO;
import kr.co.wjin.board.domain.PageInfo;
import kr.co.wjin.board.service.BoardService;
import kr.co.wjin.board.store.BoardStore;

@Repository
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardStore bStore;
	@Autowired
	private SqlSession session;
	@Override
	public int insertBoard(BoardVO board) {
		int result = bStore.insertBoard(session, board);
		return result;
	}
	@Override
	public List<BoardVO> selectBoardList(PageInfo pInfo) {
		List<BoardVO> bList = bStore.selectBoardList(session, pInfo);
		return bList;
	}
	@Override
	public BoardVO selectOneByNo(Integer boardNo) {
		BoardVO boardVO = bStore.selectOneByNo(session, boardNo);
		return boardVO;
	}

}
