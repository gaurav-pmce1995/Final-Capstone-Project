package com.kanban.taskmanagement.service.impl;

import com.kanban.taskmanagement.exception.BoardAlreadyExistsException;
import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Board;
import com.kanban.taskmanagement.model.Kanban;
import com.kanban.taskmanagement.repository.KanbanRepository;
import com.kanban.taskmanagement.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final KanbanRepository kanbanRepository;

    @Override
    public Board createBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardAlreadyExistsException {
        Kanban kanban = getKanbanById(kanbanId);

        List<Board> boards = kanban.getBoards();

        boolean boardAlreadyExists = boards.stream().anyMatch(board -> boardName.equalsIgnoreCase(board.getBoardName()));
        if (boardAlreadyExists) {
            throw new BoardAlreadyExistsException();
        }
        Board newBoard = new Board();
        newBoard.setBoardName(boardName);
        boards.add(newBoard);
        kanbanRepository.save(kanban);
        return newBoard;
    }

    @Override
    public Board getBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException {

        Kanban kanban = getKanbanById(kanbanId);
        List<Board> boards = kanban.getBoards();
        for (Board board : boards) {
            if (board.getBoardName().equals(boardName)) {
                return board;
            }
        }
        throw new BoardNotFoundException();
    }

    @Override
    public List<Board> getAllBoards(String kanbanId) throws KanbanNotFoundException, BoardNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        List<Board> boards = kanban.getBoards();

        if (boards.isEmpty()) {
            throw new BoardNotFoundException();
        }
        return boards;
    }

    @Override
    public Board updateBoard(String kanbanId, String boardName, Board updatedBoard) throws KanbanNotFoundException, BoardNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        List<Board> boards = kanban.getBoards();

        for (Board board : boards) {
            if (board.getBoardName().equalsIgnoreCase(boardName)) {
                if (updatedBoard.getBoardName() != null)
                   {
                       board.setBoardName(updatedBoard.getBoardName());
                   }
                if(updatedBoard.getTaskStatuses() != null)
                {
                    board.setTaskStatuses(updatedBoard.getTaskStatuses());
                }
                kanbanRepository.save(kanban);
                return updatedBoard;
            }
        }

        throw new BoardNotFoundException();
    }

    @Override
    public boolean deleteBoard(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        List<Board> boards = kanban.getBoards();
        for (Board board : boards) {
            if (board.getBoardName().equalsIgnoreCase(boardName)) {
                boards.remove(board);
                kanbanRepository.save(kanban);
                return true;
            }
        }
        throw new BoardNotFoundException();
    }

    private Kanban getKanbanById(String kanbanId) throws KanbanNotFoundException {
        Optional<Kanban> optionalKanban = kanbanRepository.findById(kanbanId);
        if (optionalKanban.isPresent()) {
            return optionalKanban.get();
        }
        throw new KanbanNotFoundException();
    }

}
