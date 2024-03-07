package kr.co.wjin.member.service;

import kr.co.wjin.member.domain.MemberVO;

public interface MemberService {

	/**
	 * 회원 정보 등록
	 * @param member
	 * @return result
	 */
	int insertMember(MemberVO member);

	/**
	 * 회원 로그인 Service
	 * @param member
	 * @return mOne
	 */
	MemberVO checkMemberLogin(MemberVO member);

	/**
	 * 회원 아이디 검색
	 * @param memberId
	 * @return mOne
	 */
	MemberVO getOneById(String memberId);

	/**
	 * 회원 정보 수정
	 * @param member
	 * @return result
	 */
	int updateMember(MemberVO member);

	/**
	 * 회원 정보 삭제
	 * @param memberId
	 * @return result
	 */
	int deleteMember(String memberId);
	
}
