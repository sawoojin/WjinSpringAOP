package kr.co.wjin.board.store.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.wjin.board.domain.BoardVO;
import kr.co.wjin.board.domain.PageInfo;
import kr.co.wjin.board.store.BoardStore;

@Repository
public class BoardStoreImpl implements BoardStore{

	@Override
	public int insertBoard(SqlSession session, BoardVO board) {
		int result = session.insert("BoardMapper.insertBoard", board);
		return result;
	}

	@Override
	public List<BoardVO> selectBoardList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<BoardVO> bList = session.selectList("BoardMapper.selectBoardList", null, rowBounds);
		return bList;
	}

	@Override
	public BoardVO selectOneByNo(SqlSession session, Integer boardNo) {
		BoardVO boardVO = session.selectOne("BoardMapper.selectOneByNo", boardNo);
		return boardVO;
	}

}
