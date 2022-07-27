package com.model2.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.dao.BoardDAO;
import com.model2.dto.BoardVO;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "/board/boardList.jsp";
	      
	      BoardDAO bDao = BoardDAO.getInstance();
	      //게시판 페이지 만들기
	      //------------------------------------start
	      int page=1;
	      int limit=10;
	      
	      if(request.getParameter("page")!=null){
	         page=Integer.parseInt(request.getParameter("page"));
	      }
	      //전체페이지
	      int listcount= bDao.getListCount(); //총 리스트 수를 받아옴
	      
	      List<BoardVO> boardList = bDao.getBoardList(page,limit); //리스트를 받아옴
	      
	      int maxpage = (int)((double)listcount/limit +0.95); //페이징연산
	      int startpage = ((int)((double)page/10 + 0.9)-1) * 10 + 1;//1,11,21,31....
	      int endpage = startpage + 10 -1; //10,20,30,...
	      
	      System.out.println(startpage);
	      System.out.println(endpage);
	      
	      if(endpage > maxpage)
	    	  endpage = maxpage;
	      //--------------------------------------end
	      
	      //List<BoardVO> boardList = bDao.selectAllBoards();
	      request.setAttribute("page", page);
	      request.setAttribute("maxpage", maxpage);
	      request.setAttribute("startpage", startpage);
	      request.setAttribute("endpage", endpage);
	      request.setAttribute("listcount", listcount);
	      request.setAttribute("boardList", boardList);
	      
	      RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	      dispatcher.forward(request, response);
	}
	
}
