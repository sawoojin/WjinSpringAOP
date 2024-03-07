package kr.co.wjin.notice.store.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.wjin.notice.domain.NoticeVO;
import kr.co.wjin.notice.domain.PageInfo;
import kr.co.wjin.notice.store.NoticeStore;

@Repository
public class NoticeStoreImpl implements NoticeStore{

	@Override
	public int insertNotice(SqlSession session, NoticeVO notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	@Override
	public List<NoticeVO> selectNoticeList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBound = new RowBounds(offset, limit);
		List<NoticeVO> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBound); // rowBound형태는 3번째 값으로 넘겨야 함
		return nList;
	}

	@Override
	public int selectTotalCount(SqlSession session) {
		int totalCount = session.selectOne("NoticeMapper.seletTotalCount");
		return totalCount;
	}
	
	@Override
	public int selectTotalCount(SqlSession session, Map<String, String> paramMap) {
		int totalCount = session.selectOne("NoticeMapper.searchTotalCount", paramMap);
		return totalCount;
	}

	@Override
	public int updateNotice(SqlSession session, NoticeVO notice) {
		int result = session.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	@Override
	public NoticeVO selectNoticeByNo(SqlSession session, int noticeNo) {
		NoticeVO notice = session.selectOne("NoticeMapper.selectNoticeByNo", noticeNo);
		return notice;
	}



	@Override
	public int deleteNotice(SqlSession session, int noticeNo) {
		int result = session.delete("NoticeMapper.deleteNotice", noticeNo); 
		return result;
	}

	@Override
	public List<NoticeVO> searchNoticeList(SqlSession session, PageInfo pInfo, Map<String, String> paramMap) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBound = new RowBounds(offset, limit);
		List<NoticeVO> searchList = session.selectList("NoticeMapper.selectNoticeByKeyword", paramMap, rowBound);
		return searchList;
	}



}
