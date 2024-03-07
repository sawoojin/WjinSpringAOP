package kr.co.wjin.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.wjin.board.domain.BoardVO;
import kr.co.wjin.board.domain.PageInfo;
import kr.co.wjin.board.domain.ReplyVO;
import kr.co.wjin.board.service.BoardService;
import kr.co.wjin.board.service.ReplyService;

@Controller
public class BoardController {
	
	public ModelAndView showRegisterForm(ModelAndView mv) {
		return mv;
	}
	
	@Autowired
	private BoardService bService;
	@Autowired
	private ReplyService rService;
	
	@RequestMapping(value="/board/register.kh", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {	
		return "board/register";
	}
	@RequestMapping(value="/board/register.kh", method=RequestMethod.POST)
	public String insertBoard(@ModelAttribute BoardVO board
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, Model model
			, HttpServletRequest request
			, HttpSession session) {
		try {
			String writer = (String)session.getAttribute("memberId");
			if(session != null && writer !=null && !"".equals(writer)) {
				board.setBoardWriter(writer);
				if(uploadFile != null && uploadFile.isEmpty()) {
					Map<String, Object> bMap = this.saveFile(request, uploadFile);
					board.setBoardFilename((String)bMap.get("fileName"));
					board.setBoardFileRename((String) bMap.get("fileRename"));
					board.setBoardFilePath((String) bMap.get("filePath"));
					board.setBoardLength((long) bMap.get("boardLength"));
				}
			}else {
				model.addAttribute("msg", "로그인이 필요합니다.");
				return "common/errorPage";
			}
			int result = bService.insertBoard(board);
			if(result > 0) {
				return "redirect:/board/list.kh";
			}else {
				model.addAttribute("msg", "공지사항 등록이 완료되지 않았습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	private Map<String, Object> saveFile(HttpServletRequest request, MultipartFile uploadFile) throws Exception {
		String boardFolder = request.getSession().getServletContext().getRealPath("resources");
		String savePath = boardFolder + "\\buploadFiles";
		File saveFolder = new File(savePath);
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		String fileName = uploadFile.getOriginalFilename();
		// A : 1.png, B : 1.png
		// 시간으로 파일 리네임하기
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileRename = sdf.format(new Date(System.currentTimeMillis())) + "," + ext;
		// 파일 저장하는 코드
		File saveFile = new File(savePath+"\\"+fileRename);
		uploadFile.transferTo(saveFile);
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/"+fileRename);
		fileMap.put("fileLength", uploadFile.getSize());
		return fileMap;
	}
	@RequestMapping(value="/board/list.kh", method=RequestMethod.GET)
	public String showNoticeList(Model model
			, @RequestParam(value="page", required=false, defaultValue="1")Integer currentPage) {
		try {
			Integer totalCount = 229;
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<BoardVO> bList = bService.selectBoardList(pInfo);
			if(!bList.isEmpty()) {
				model.addAttribute("pInfo", pInfo);
				model.addAttribute("bList", bList);
			}else {
				model.addAttribute("bLsit", null);
			}
			return "board/list";			
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}	
	}
	
	@RequestMapping(value="/board/detail.kh", method=RequestMethod.GET)
	public String showNoticeDetail(Model model, Integer boardNo) {
		try {
			BoardVO boardVO = bService.selectOneByNo(boardNo);
			List<ReplyVO> rList = rService.selectReplyList(boardNo);
			model.addAttribute("board", boardVO);
			model.addAttribute("rList", rList);
			return "board/detail";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	
	// 페이징
	private PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		PageInfo pInfo = new PageInfo();
		int RecoudCountPerPage = pInfo.getRecordCountPerPage();
		int NaviCountPerPage = pInfo.getNaviCountPerPage();
		int naviTotalCount;
		int startNavi; 
		int endNavi;
		// 올림하는 내장 함수 ceil 형변환은 없다. 따로 해주자
		naviTotalCount = (int)Math.ceil((double)totalCount/RecoudCountPerPage);
		startNavi = ((int)((double)currentPage/NaviCountPerPage+0.9)-1)*NaviCountPerPage+1;
		endNavi = startNavi + NaviCountPerPage - 1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		pInfo.setCurrentPage(currentPage);
		pInfo.setNaviTotalCount(naviTotalCount);
		pInfo.setStartNavi(startNavi);
		pInfo.setEndNavi(endNavi);
		return pInfo;
	}
	
	
}
