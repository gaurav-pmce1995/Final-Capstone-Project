package com.kanban.taskmanagement.service;

import com.kanban.taskmanagement.exception.BoardAlreadyExistsException;
import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Board;

import java.util.List;

public interface BoardService {
    Board createBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardAlreadyExistsException;
    Board getBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException;
    List<Board> getAllBoards(String kanbanId) throws KanbanNotFoundException, BoardNotFoundException;
    Board updateBoard(String kanbanId, String boardName, Board board) throws KanbanNotFoundException, BoardNotFoundException;
    boolean deleteBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException;
}
