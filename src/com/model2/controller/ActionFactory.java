package com.model2.controller;

import com.model2.controller.action.Action;
import com.model2.controller.action.BoardCheckPassAction;
import com.model2.controller.action.BoardCheckPassFormAction;
import com.model2.controller.action.BoardDeleteAction;
import com.model2.controller.action.BoardListAction;
import com.model2.controller.action.BoardUpdateAction;
import com.model2.controller.action.BoardUpdateFormAction;
import com.model2.controller.action.BoardViewAction;
import com.model2.controller.action.BoardWriteAction;
import com.model2.controller.action.BoardWriteFormAction;

public class ActionFactory {
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory(){
		super();
	}
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command) {
		Action action = null;
		System.out.println("ActionFactory: " + command);
		
		if(command.contentEquals("board_list")) {
			action = new BoardListAction();
		}else if(command.equals("board_write_form")) {
			action= new BoardWriteFormAction();
		}else if(command.equals("board_write")) {
			action = new BoardWriteAction();
		}else if(command.equals("board_view")) {
			action = new BoardViewAction();
		}else if(command.equals("board_check_pass_form")) {
			action = new BoardCheckPassFormAction();
		}else if(command.equals("board_check_pass")) {
			action = new BoardCheckPassAction();
		}else if(command.equals("board_update_form")) {
			action = new BoardUpdateFormAction();
		}else if(command.equals("board_update")) {
			action = new BoardUpdateAction();
		}else if(command.equals("board_delete")) {
			action = new BoardDeleteAction();
		}
		return action;
	}
}
