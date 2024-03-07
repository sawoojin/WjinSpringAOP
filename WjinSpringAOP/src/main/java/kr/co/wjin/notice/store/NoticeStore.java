package kr.co.wjin.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.co.wjin.notice.domain.NoticeVO;
import kr.co.wjin.notice.domain.PageInfo;

public interface NoticeStore {

	/**
	 * 공지사항 등록 Store
	 * @param session
	 * @param notice
	 * @return result
	 */
	int insertNotice(SqlSession session, NoticeVO notice);

	/**
	 * 공지사항 조회 Store
	 * @param session
	 * @return nList
	 */
	List<NoticeVO> selectNoticeList(SqlSession session, PageInfo pInfo);

	/**
	 * 전체 게시물 횟수 Store
	 * @param session
	 * @return
	 */
	int selectTotalCount(SqlSession session);

	int selectTotalCount(SqlSession session, Map<String, String> paramMap);
	
	/**
	 * 게시물 세부 페이지 Store
	 * @param session
	 * @param noticeNo
	 * @return nOne
	 */
	NoticeVO selectNoticeByNo(SqlSession session, int noticeNo);

	/**
	 * 게시물 업데이트 Store
	 * @param session
	 * @param notice
	 * @return result
	 */
	int updateNotice(SqlSession session, NoticeVO notice);

	/**
	 * 삭제 Store
	 * @param session
	 * @param noticeNo
	 * @return result
	 */
	int deleteNotice(SqlSession session, int noticeNo);

	List<NoticeVO> searchNoticeList(SqlSession session, PageInfo pInfo, Map<String, String> paramMap);



}
