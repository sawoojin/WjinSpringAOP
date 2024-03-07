package kr.co.wjin.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.wjin.board.domain.BoardVO;
import kr.co.wjin.board.domain.PageInfo;

@Service
public interface BoardService {

	/**
	 * 게시물 등록 Service
	 * @param board
	 * @return int
	 */
	int insertBoard(BoardVO board);

	/**
	 * 게시물 보기 Service
	 * @param pInfo 
	 * @return bList
	 */
	List<BoardVO> selectBoardList(PageInfo pInfo);

	/**
	 * 게시물 상세 조회 Service
	 * @param boardNo
	 * @return boardVO
	 */
	BoardVO selectOneByNo(Integer boardNo);

}
