package kr.co.wjin.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.co.wjin.board.domain.BoardVO;
import kr.co.wjin.board.domain.PageInfo;


public interface BoardStore {

	/**
	 * 게시물 등록 Store
	 * @param session
	 * @param board
	 * @return int
	 */
	int insertBoard(SqlSession session, BoardVO board);

	/**
	 * 게시물 조회 Store
	 * @param session
	 * @param pInfo 
	 * @param boardNo
	 * @return bList
	 */
	List<BoardVO> selectBoardList(SqlSession session, PageInfo pInfo);

	/**
	 * 게시물 상세 조회 Store
	 * @param boardNo
	 * @return boardVO
	 */
	BoardVO selectOneByNo(SqlSession session, Integer boardNo);

}
