package kr.co.wjin.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.wjin.member.domain.MemberVO;
import kr.co.wjin.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	@RequestMapping(value="/member/register.kh", method=RequestMethod.GET)
	public String showInsertForm() {
		return "member/insert";	
	}
	@RequestMapping(value="/member/register.kh", method=RequestMethod.POST)
	public String insertMember(
			@ModelAttribute MemberVO member,
//			@RequestParam("memberId") String memberId,
//			@RequestParam("memberPw") String memberPw,
//			@RequestParam("memberName") String memberName,
//			@RequestParam("memberAge") int memberAge,
//			@RequestParam("memberGender") String memberGender,
//			@RequestParam("memberEmail") String memberEmail,
//			@RequestParam("memberPhone") String memberPhone,
//			@RequestParam("memberAddress") String memberAddress,
//			@RequestParam("memberHobby") String memberHobby,
			Model model) {
		try {
//			MemberVO member = new MemberVO(memberId, memberPw, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddress, memberHobby);	
			int result = mService.insertMember(member);
			if(result > 0) {
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "Service Failed!");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="/member/login.kh", method=RequestMethod.POST)
	public String memberLogin(
			String memberId,
			String memberPw,
			Model model,
			HttpSession session) {
		try {
			MemberVO member = new MemberVO(memberId, memberPw);
			member = mService.checkMemberLogin(member);
			if(member != null) {
				session.setAttribute("memberId", member.getMemberId());
				session.setAttribute("memberName", member.getMemberName());
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "No Data Found!");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="/member/logout.kh", method=RequestMethod.GET)
	public String memberLogout(HttpSession session, Model model) {
		try {
			if(session != null) {
				session.invalidate();
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "로그아웃 오류");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="/member/mypage.kh", method=RequestMethod.GET)
	public String showMyPage(HttpSession session, Model model) {
		try {
			String memberId = (String)session.getAttribute("memberId");
			MemberVO member = null;
			if(memberId != null) {	
				member = mService.getOneById(memberId);
			}
			if(member != null) {
				model.addAttribute("member", member);
				return "member/mypage";
			}else {
				model.addAttribute("msg", "회원 정보 조회를 실패함");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="member/update.kh" ,method=RequestMethod.GET)
	public String showModifyForm(HttpSession session, Model model) {
		try {
			String memberId = (String)session.getAttribute("memberId");
			MemberVO member = null;
			if(memberId != null) {
				member = mService.getOneById(memberId);
			}
			if(member != null) {
				model.addAttribute("member", member);
				return "member/modify";
			}else {
				model.addAttribute("msg", "회원 정보 수정을 실패함");
				return "common/errorPage";
			}	
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="/member/update.kh", method=RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute MemberVO member,
//			String memberId,
//			String memberPw,
//			String memberEmail,
//			String memberPhone,
//			String memberAddress,
//			String memberHobby,
			Model model) {
		try {
//			MemberVO member = new MemberVO(memberId, memberPw, memberEmail, memberPhone, memberAddress, memberHobby);
			int result = mService.updateMember(member);
			if(result > 0) {
				return "redirect:/member/mypage.kh";
			}else {
				model.addAttribute("msg", "회원 정보 수정을 실패함");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	@RequestMapping(value="/member/delete.kh", method=RequestMethod.GET)
	public String deleteMember(String memberId,HttpSession session, Model model) {
		try {
			String sessionId = (String)session.getAttribute("memberId");
			int result = 0;
			if(sessionId != null && sessionId.equals(memberId)) {
				result = mService.deleteMember(memberId);				
			}else {
				model.addAttribute("msg", "로그인을 해야 합니다.");
				return "common/errorPage";
			}
			if(result > 0) {
				return "redirect:/member/logout.kh";
			}else {
				model.addAttribute("msg", "회원 삭제를 실패함");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
}	
