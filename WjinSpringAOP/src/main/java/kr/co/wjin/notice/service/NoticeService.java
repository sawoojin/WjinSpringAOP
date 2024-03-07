package kr.co.wjin.notice.service;

import java.util.List;
import java.util.Map;

import kr.co.wjin.notice.domain.NoticeVO;
import kr.co.wjin.notice.domain.PageInfo;

public interface NoticeService {

	/**
	 * 공지사항 등록 Service
	 * @param notice
	 * @return result
	 */
	int insertNotice(NoticeVO notice);

	/**
	 * 공지사항 리스트
	 * @return nList
	 */
	List<NoticeVO> selectNoticeList(PageInfo pInfo);

	/**
	 * 전체 게시물 횟수
	 * @param paramMap 
	 * @return int
	 */
	int getTotalCount();

	int getTotalCount(Map<String, String> paramMap);
	/**
	 * 게시물 세부 페이지
	 * @param noticeNo
	 * @return nOne
	 */
	NoticeVO selectNoticeByNo(int noticeNo);

	/**
	 * 게시물 업데이트
	 * @param notice
	 * @return result
	 */
	int updateNotice(NoticeVO notice);

	/**
	 * 삭제
	 * @param noticeNo
	 * @return result
	 */
	int deleteNotice(int noticeNo);

	/**
	 * 공지사항 검색 Service
	 * @param paramMap
	 * @param pInfo 
	 * @return List
	 */
	List<NoticeVO> searchNoticeByKeyword(Map<String, String> paramMap, PageInfo pInfo);




}
