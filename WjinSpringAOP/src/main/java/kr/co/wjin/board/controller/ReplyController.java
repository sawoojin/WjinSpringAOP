package kr.co.wjin.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kr.co.wjin.board.domain.ReplyVO;
import kr.co.wjin.board.service.ReplyService;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService rService;
	
	@ResponseBody
	@RequestMapping(value="/reply/add.kh", method=RequestMethod.POST)
	public String insertReplyAjax(@ModelAttribute ReplyVO reply
			, HttpSession session) {
		try {
			String replyWriter = (String)session.getAttribute("memberId");
			int result = 0;
			if(replyWriter != null && !replyWriter.equals("")) {
				reply.setReplyWriter(replyWriter);
				result = rService.insertReply(reply);
			} else {
				return "Login needed";
			}
			if(result > 0) {
				return "success";
			}else {
				return "fail";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
//	@RequestMapping(value="/reply/add.kh", method=RequestMethod.POST)
//	public String insertReply(Model model, HttpSession session
//			, @ModelAttribute ReplyVO replyVO) {
//		try {
//			String memberId = (String)session.getAttribute("memberId");
//			if(memberId != null) {
//				replyVO.setReplyWriter(memberId);
//			} else {
//				model.addAttribute("msg", "로그인이 필요합니다.");
//				return "common/errorPage";
//			}
//			int result = rService.insertReply(replyVO);
//			return "redirect:/board/detail.kh?boardNo="+replyVO.getRefBoardNo();
//		} catch (Exception e) {
//			model.addAttribute("msg", e.getMessage());
//			return "common/errorPage";
//		}
//	}
	
	@ResponseBody
	@RequestMapping(value="/reply/list.kh"
		, produces="application/json;charset=utf-8"
		, method=RequestMethod.GET)
	public String showReplyList(@RequestParam("refBoardNo") Integer refBoardNo) {
		// selectReplyList 로 댓글목록 조회
		List<ReplyVO> rList = rService.selectReplyList(refBoardNo);
		// List -> JSONArray로 변경해서 리턴
//		JSONObject json = new JSONObject();
//		JSONArray jsonArr = new JSONArray();
//		for(ReplyVO reply : rList) {
//			json.put("replyNo", reply.getReplyNo());
//			json.put("refBoardNo", reply.getRefBoardNo());
//			json.put("replyContent", reply.getReplyContent());
//			json.put("replyWriter", reply.getReplyWriter());
//			json.put("rCreateDate", reply.getrCreateDate());
//			json.put("rUpdateDate", reply.getrUpdateDate());
//			json.put("updateYn", reply.getUpdateYn());
//			json.put("rStatus", reply.getrStatus());
//			jsonArr.add(json);
//		}
		// List -> JSONArray로 간단히 변경시키는 간단한 라이브러리 2
		// GSON -> Google JSON
		Gson gson = new Gson();
		return gson.toJson(rList);
	}
	
	@ResponseBody
	@RequestMapping(value="/reply/update.kh" ,method=RequestMethod.POST)
	public String updateReply(@ModelAttribute ReplyVO reply) {
		try {
			int result = rService.updateReply(reply);
			if(result > 0) {
				return "success";
			}else {
				return "fail";
			}
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/reply/remove.kh"
			, method=RequestMethod.POST)
	public String removeRelpy(@RequestParam("replyNo") Integer replyNo) {
		try {
			int result = rService.removeReply(replyNo);
			if(result > 0) {
				return "success";
			}else {
				return "fail";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}
