package com.kanban.taskmanagement.controller;

import com.kanban.taskmanagement.exception.BoardAlreadyExistsException;
import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Board;
import com.kanban.taskmanagement.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/kanbans/{kanbanId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    ResponseEntity<?> createBoard(@PathVariable String kanbanId, @RequestBody Board board) throws KanbanNotFoundException, BoardAlreadyExistsException {
        try {
            boardService.createBoard(kanbanId, board.getBoardName());
            return new ResponseEntity<>(board, HttpStatus.CREATED);
        } catch (KanbanNotFoundException | BoardAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{boardName}")
    ResponseEntity<?> getBoardByName(@PathVariable String kanbanId, @PathVariable String boardName) throws KanbanNotFoundException, BoardNotFoundException {
        try {
            Board board = boardService.getBoard(kanbanId, boardName);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBoards(@PathVariable String kanbanId) throws KanbanNotFoundException, BoardNotFoundException {
        try {
            List<Board> boards = boardService.getAllBoards(kanbanId);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{boardName}")
    public ResponseEntity<?> updateBoardByName(@PathVariable String kanbanId, @PathVariable String boardName, @RequestBody Board board) throws KanbanNotFoundException, BoardNotFoundException {
        try {
            Board updateBoard = boardService.updateBoard(kanbanId, boardName, board);
            return new ResponseEntity<>(updateBoard, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{boardName}")
    public ResponseEntity<?> deleteBoardByName(@PathVariable String kanbanId, @PathVariable String boardName) throws KanbanNotFoundException, BoardNotFoundException {
        try {
            /*Made changes from line 76 to 79*/
            String result = String.valueOf(boardService.deleteBoard(kanbanId, boardName));
            Map<String, String> response = new HashMap<>();
            response.put("deleteResponse", result );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
