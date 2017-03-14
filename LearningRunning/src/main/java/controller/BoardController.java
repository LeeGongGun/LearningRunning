package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.spi.LoggerFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.runner.Request;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

import spring.AuthInfo;
import spring.BoardDao;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.RegisterRequest;

@Controller
public class BoardController {
	private MemberDao memberDao;
	private BoardDao boardDao;
	private MultipartResolver multipartResolver;
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping("/board")
	public String indexMapping() {
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="board/list",method=RequestMethod.GET)
	public String list(@ModelAttribute("command") BoardListSearchCommand listSearchCommand) {
		return "/board/boardList";
		
	}
	@RequestMapping(value="board/list",method=RequestMethod.POST)
	public String listAjax(@ModelAttribute("command") BoardListSearchCommand listSearchCommand,Errors errors,HttpSession session,Model model) {
		;
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String cnt = "\"totalCnt\": "+boardDao.getListCount(listSearchCommand.getSearchText());
			String json = ow.writeValueAsString(boardDao.getBoardList(listSearchCommand.getSearchText(),listSearchCommand.getPage(), 10));
			model.addAttribute("json", "{"+cnt +", \"data\": "+json+"}");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/ajax/ajaxDefault";
		
	}
	@RequestMapping("board/detail/{id}")
	public String detail(@PathVariable("id") Long boardId,HttpSession session,Model model) {
		Board board = boardDao.getDetail(boardId);
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		String reader = authInfo.getName();
		if(!board.getWriter().equals(reader)){
			 boardDao.updateReadCount(boardId);
		}
		model.addAttribute("board",board);
		return "/board/boardDetail";
	}
	
	@RequestMapping("board/new")
	public String boardNewForm(Board board,Model model) {
		return "/board/boardForm";
	}
	@RequestMapping(value="board/new",method=RequestMethod.POST)
	public String boardNewSubmit(Board board,HttpServletRequest request,Model model) {
		MultipartFile multi = board.getFile();
		String newFileName = "";
		if(!multi.isEmpty()){
			String fileName = multi.getOriginalFilename(); 
			newFileName= System.currentTimeMillis()+"_"+fileName;
			String path = board.getFileDir()+newFileName;
			try {
				File file =  new File(path);
				multi.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			board.setFileName(newFileName);
		}
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getRemoteAddr();
		board.setIp(ip);
		int insertOk = boardDao.insertBoard(board);
		if(insertOk==1) return "redirect:/board/detail/"+board.getNum();
		else return "redirect:/board/list";
		
	}
	
	//글수정
	@RequestMapping(value="board/edit/{id}",method=RequestMethod.GET)
	public String boardEditForm(@ModelAttribute("command") Board board,@PathVariable("id") Long boardId,HttpSession session,Model model) {
		AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
		board = boardDao.getDetail(boardId);		
		if(!authInfo.getName().equals(board.getWriter())) return "redirect:/board/detail/"+boardId;
		model.addAttribute("preBoard", board);
		return "/board/boardFormEdit";
	}
	
	@RequestMapping(value="board/edit/{id}",method=RequestMethod.POST)
	public String boardEditSubmit(@ModelAttribute("command") Board board,HttpSession session,Model model) {
		AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
		if(!authInfo.getName().equals(board.getWriter())) return "redirect:/board/detail/"+board.getNum();
		MultipartFile multi = board.getFile();
		String newFileName = "";
		if(!multi.isEmpty()){
			String fileName = multi.getOriginalFilename(); 
			newFileName= System.currentTimeMillis()+"_"+fileName;
			String path = board.getFileDir()+newFileName;
			try {
				File file =  new File(path);
				multi.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			board.setFileName(newFileName);
		}
		int updateOK = boardDao.updateBoard(board);
		if(updateOK==1) return "redirect:/board/detail/"+board.getNum();
		else return "redirect:board/edit/"+board.getNum();
	}

	// 답글달기
	@RequestMapping(value="board/reply/{id}",method=RequestMethod.GET)
	public String boardReplyForm(Board board,@PathVariable("id") Long boardId, Model model) {
		model.addAttribute("preBoard", boardDao.getDetail(boardId));
		return "/board/boardFormReply";
	}
	
	@RequestMapping(value="board/reply/{id}",method=RequestMethod.POST)
	public String boardReplySubmit(Board board,Model model) {
		MultipartFile multi = board.getFile();
		String newFileName = "";
		if(!multi.isEmpty()){
			String fileName = multi.getOriginalFilename(); 
			newFileName= System.currentTimeMillis()+"_"+fileName;
			String path = board.getFileDir()+newFileName;
			try {
				File file =  new File(path);
				multi.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			board.setFileName(newFileName);
		}
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getRemoteAddr();
		board.setIp(ip);
		int replyOK=0;
		try {
			replyOK = boardDao.replyBoard(board);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if(replyOK>0) return "redirect:/board/detail/"+board.getNum();
		else return "redirect:/board/reply/"+board.getNum();
	}
	
	@RequestMapping(value="board/delete/{id}")
	public String boardDelete(@PathVariable("id") Long boardId,HttpSession session, Model model) {
		
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		String thisWriter = authInfo.getName();
		if(!boardDao.getDetail(boardId).getWriter().equals(thisWriter)){
			return "redirect:/board/detail/"+boardId;
		}
		int delOK=boardDao.deleteBoard(boardId);
		if(delOK==1) return "redirect:/board/list";
		else return "redirect:/board/detail/"+boardId;
		
	}

	private void initMultipartResolver(ApplicationContext context)
	  {
	    try
	    {
	      this.multipartResolver = ((MultipartResolver)context.getBean("multipartResolver", MultipartResolver.class));

	    }
	    catch (NoSuchBeanDefinitionException ex)
	    {
	      this.multipartResolver = null;
	      ex.printStackTrace();
	    }
	  }
	
}
