package kr.co.wjin.board.domain;

public class PageInfo {
	// 현재 페이지 / 전체 게시물 수 / 범위의 갯수 / 페이지당 게시물 수 / 페이지당 범위의 수 / 시작, 끝 값
	private int currentPage;
	private int totalCount;
	private int naviTotalCount;
	private int recordCountPerPage;
	private int naviCountPerPage;
	private int startNavi;
	private int endNavi;
	
	public PageInfo() {
		this.recordCountPerPage = 10;
		this.naviCountPerPage = 10;
	}

	
	//
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNaviTotalCount() {
		return naviTotalCount;
	}

	public void setNaviTotalCount(int naviTotalCount) {
		this.naviTotalCount = naviTotalCount;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getNaviCountPerPage() {
		return naviCountPerPage;
	}

	public void setNaviCountPerPage(int naviCountPerPage) {
		this.naviCountPerPage = naviCountPerPage;
	}

	public int getStartNavi() {
		return startNavi;
	}

	public void setStartNavi(int startNavi) {
		this.startNavi = startNavi;
	}

	public int getEndNavi() {
		return endNavi;
	}

	public void setEndNavi(int endNavi) {
		this.endNavi = endNavi;
	}

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", totalCount=" + totalCount + ", naviTotalCount="
				+ naviTotalCount + ", recordCountPerPage=" + recordCountPerPage + ", naviCountPerPage="
				+ naviCountPerPage + ", startNavi=" + startNavi + ", endNavi=" + endNavi + "]";
	}
	
	
}
