package kr.co.wjin.board.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class ReplyVO {
	private int replyNo;
	private int refBoardNo;
	private String replyContent;
	private String replyWriter;
	private Date rCreateDate;
	private Timestamp rUpdateDate;
	private String updateYn;
	private String rStatus;
	
	public ReplyVO() {}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getRefBoardNo() {
		return refBoardNo;
	}

	public void setRefBoardNo(int refBoardNo) {
		this.refBoardNo = refBoardNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyWriter() {
		return replyWriter;
	}

	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}

	public Date getrCreateDate() {
		return rCreateDate;
	}

	public void setrCreateDate(Date rCreateDate) {
		this.rCreateDate = rCreateDate;
	}

	public Timestamp getrUpdateDate() {
		return rUpdateDate;
	}

	public void setrUpdateDate(Timestamp rUpdateDate) {
		this.rUpdateDate = rUpdateDate;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getrStatus() {
		return rStatus;
	}

	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}

	@Override
	public String toString() {
		return "ReplyVO [replyNo=" + replyNo + ", refBoardNo=" + refBoardNo + ", replyContent=" + replyContent
				+ ", replyWriter=" + replyWriter + ", rCreateDate=" + rCreateDate + ", rUpdateDate=" + rUpdateDate
				+ ", updateYn=" + updateYn + ", rStatus=" + rStatus + "]";
	}
	
	
}
