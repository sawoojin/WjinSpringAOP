package kr.co.wjin.notice.controller;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.wjin.notice.domain.NoticeVO;
import kr.co.wjin.notice.domain.PageInfo;
import kr.co.wjin.notice.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService nService;
	
//	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.POST)
//	public String showInsertForm() {
//		return "notice/register";
//	}
	
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.GET)
	public ModelAndView showInsertForm(ModelAndView mv) {
		mv.setViewName("notice/register");
		return mv;
	}
	
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.POST)
	public ModelAndView insertNotice(ModelAndView mv
			, @ModelAttribute NoticeVO notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				Map<String, Object> infoMap =  this.saveFile(uploadFile, request);
				String fileName = (String)infoMap.get("fileName");
				String fileRename = (String)infoMap.get("fileRename");
				String filePath = (String)infoMap.get("filePath");
				long fileLength = (long)infoMap.get("fileSize");
				
				notice.setNoticeFilename(fileName);
				notice.setNoticeFileRename(fileRename);
				notice.setNoticeFilepath(filePath);
				notice.setNoticeFilelength(fileLength);
			}
			int result = nService.insertNotice(notice);
			if(result > 0) {
				mv.setViewName("redirect:/notice/list.kh");
			}else {
				mv.addObject("msg", "공지사항 등록이 완료되지 않았습니다.");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	private Map<String, Object> saveFile(MultipartFile uploadFile, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		// 파일 이름
		String fileName = uploadFile.getOriginalFilename();
		// 저장 경로
		String projectPath = request.getSession().getServletContext().getRealPath("resources");
		String saveDirectory = projectPath + "\\nuploadFiles";
		File sDir = new File(saveDirectory);
		if(!sDir.exists()) {
			sDir.mkdir(); // nuploadFile 폴더가 없으면 자동으로 만들어줌
		}
		// 파일 리네임의 필요성!!
		// A : 1.png, B : 1.png -> 내용은 다르지만 이름이 같은 파일을 구별하기 위해서 꼭 파일 리네임을 해주어야함.
		// 리네임을 할때에는 올린 시각을 기준으로 파일이름을 만들어서 따로 저장(NOTICE_FILERENAME에 저장)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // 나중에 SS 랑 비교
		String strResult = sdf.format(new Date(System.currentTimeMillis()));
		String ext = fileName.substring(fileName.lastIndexOf(".")+1); // 원본파일의 확장자 글자부터 +1
		String fileRename = strResult + "." + ext;
		
		String savePath = saveDirectory + "\\" + fileRename;
		File file = new File(savePath);
		// 파일 저장
		uploadFile.transferTo(file);
		// DB에 저장할 파일정보 셋팅
		// 1. 파일이름, 2. 파일리네임, 3. 파일경로, 4. 파일크기
		long fileLength = uploadFile.getSize();
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("fileName", fileName);
		infoMap.put("fileRename", fileRename);
		infoMap.put("filePath", savePath);
		infoMap.put("fileSize", fileLength);
		return infoMap;
	}
	@RequestMapping(value="/notice/list.kh", method=RequestMethod.GET)
	public ModelAndView showNoticeList(ModelAndView mv,
			@RequestParam(value="page", required = false, defaultValue = "1")
			Integer currentPage) {
		try {
			int totalCount = nService.getTotalCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<NoticeVO> nList = nService.selectNoticeList(pInfo);
			mv.addObject("nList", nList);
			mv.addObject("pInfo", pInfo);
			mv.setViewName("notice/list");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	@RequestMapping(value="/notice/search.kh", method=RequestMethod.GET)
	public ModelAndView searchNoticeList(ModelAndView mv,
			@RequestParam("searchCondition") String searchCondition,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam(value="page", required = false, defaultValue = "1")
		Integer currentPage) {
			Map<String, String> paramMap = new HashMap<String, String>();
			int totalCount = nService.getTotalCount(paramMap);
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			paramMap.put("searchCondition", searchCondition);
			paramMap.put("searchKeyword", searchKeyword);

			List<NoticeVO> searchList = nService.searchNoticeByKeyword(paramMap, pInfo);
			mv.addObject("sList", searchList);
			mv.addObject("pInfo", pInfo);
			mv.addObject("searchCondition", searchCondition);
			mv.addObject("searchKeyword", searchKeyword);
			
			mv.setViewName("notice/search");
		return mv;
	}

	private PageInfo getPageInfo(Integer currentPage, int totalCount) {
		PageInfo pi = null;
		int recordCountPerPage = 10; // 한 페이지당 보여줄 게시물의 수
		int naviCountPerPage = 5; // 한 페이지당 보여줄 범위의 갯수
		int naviTotalCount; // 범위의 총 갯수 n/
		int startNavi;
		int endNavi;
		
		// 페이지 총 갯수를 구하기 위해 double값 전환 후 올림값 처리와 다시 int값으로 전환
		naviTotalCount = (int)((double)totalCount/recordCountPerPage+0.9);
		startNavi = (((int)((double)currentPage/naviCountPerPage+0.9))-1)*naviCountPerPage + 1;
		endNavi = startNavi + naviCountPerPage -1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		pi = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage, startNavi, endNavi);
		return pi;
	}
	@RequestMapping(value="/notice/detail.kh", method=RequestMethod.GET)
	public ModelAndView showNoticeDetail (ModelAndView mv, int noticeNo) {
		try {
			NoticeVO notice = nService.selectNoticeByNo(noticeNo);
			if(notice != null) {
				mv.addObject("notice", notice).setViewName("notice/detail");
			}else {
				mv.addObject("msg", "데이터가 없습니다.").setViewName("common/errorPage");			
			}
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.GET)
	public ModelAndView showModifyForm(ModelAndView mv, int noticeNo) {
		try {
			NoticeVO notice = nService.selectNoticeByNo(noticeNo);
			if(notice != null) {
				mv.addObject("notice", notice);
				mv.setViewName("notice/modify");
			}else {
				mv.addObject("msg", "데이터가 없습니다.").setViewName("common/errorPage");	
			}
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage()).setViewName("common/errorPage");
		}
		return mv;
	}
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.POST)
	public ModelAndView updateNotice(
			ModelAndView mv,
			@ModelAttribute NoticeVO notice,
			@RequestParam(value="reloadFile", required = false) MultipartFile reloadFile,
			HttpServletRequest request) {
		try {
			// 수정기능 : 1. 대체한다 2. 삭제 후 등록한다
			if(reloadFile != null && !reloadFile.isEmpty()) {
				String fileName = notice.getNoticeFileRename();
				if(fileName != null) {
					// 기존 파일 삭제
					this.deleteFile(request, fileName);
				}
				// 새로 업로드하려는 파일 저장
				Map<String, Object> infoMap = this.saveFile(reloadFile, request);
				notice.setNoticeFilename((String)infoMap.get("fileName"));
				notice.setNoticeFileRename((String)infoMap.get("fileRename"));
				notice.setNoticeFilepath((String)infoMap.get("filePath"));
				notice.setNoticeFilelength((long)infoMap.get("fileSize"));
			}
			int result = nService.updateNotice(notice);
			if(result > 0) {
				mv.setViewName("redirect:/notice/detail.kh?noticeNo="+notice.getNoticeNo());
//				return "redirect:/notice/detail.kh?noticeNo="+notice.getNoticeNo();
			}else {
				mv.addObject("msg", "데이터가 없습니다.").setViewName("common/errorPage");
			}
		} catch (Exception e) {
			
		}
		return mv;
	}

	private void deleteFile(HttpServletRequest request, String fileName) {
		String rPath = request.getSession().getServletContext().getRealPath("resources");
		String delFilepath = rPath + "\\nuploadFiles\\" + fileName;
		File delFile = new File(delFilepath);
		if(delFile.exists()) {
			delFile.delete();
		}	
	}
	@RequestMapping(value="/notice/delete.kh", method=RequestMethod.GET)
	public ModelAndView deleteNotice(ModelAndView mv, int noticeNo) {
		try {
			int result = nService.deleteNotice(noticeNo);
			if(result > 0) {
					mv.setViewName("redirect:/notice/list.kh");
			}else {
				mv.addObject("msg", "데이터가 없습니다.").setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage()).setViewName("common/errorPage");
		}
		return mv;
	}
}

















