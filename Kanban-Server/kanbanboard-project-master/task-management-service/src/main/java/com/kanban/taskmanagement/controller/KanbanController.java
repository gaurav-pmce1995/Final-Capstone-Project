package com.kanban.taskmanagement.controller;

import com.kanban.taskmanagement.exception.KanbanAlreadyExistsException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Kanban;
import com.kanban.taskmanagement.model.User;
import com.kanban.taskmanagement.service.KanbanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kanbans")
@AllArgsConstructor
public class KanbanController {

    private final KanbanService kanbanService;

    /**
     * Handle HTTP Post request to create new Kanban
     *
     * @param kanban  The Kanban object
     * @param request The HTTP servlet request containing logged-in user information
     * @return Response with new kanban object and 200 status if found or error message with 500 status.
     * @throws KanbanAlreadyExistsException if Kanban already exists with same information
     */
    @PostMapping
    public ResponseEntity<?> createKanban(@RequestBody Kanban kanban, HttpServletRequest request) throws KanbanAlreadyExistsException {
        // Extract email of logged-in user
        String email = (String) request.getAttribute("current-user");
        System.out.println("Saadiyah  ---->"+email);
        try {
            Kanban newKanban = kanbanService.createKanban(kanban, email);
            return new ResponseEntity<>(newKanban, HttpStatus.CREATED);
        } catch (KanbanAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieve a Kanban
     *
     * @param kanbanId The ID of Kanban to retrieve
     * @return Response with retrieved Kanban and 200 status if found or error message with 500 status.
     * @throws KanbanNotFoundException If the requested ID is not found
     */
    @GetMapping("/{kanbanId}")
    public ResponseEntity<?> getKanbanById(@PathVariable String kanbanId) throws KanbanNotFoundException {
        try {
            Kanban kanban = kanbanService.getKanbanById(kanbanId);
            return new ResponseEntity<>(kanban, HttpStatus.OK);
        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handle Put request to update Kanban
     *
     * @param kanbanId The ID of Kanban to retrieve
     * @param kanban Kanban object
     * @return Response with updated Kanban and 200 status if updated or error message with 500 status
     * @throws KanbanNotFoundException If requested Kanban not found
     */
    @PutMapping("/{kanbanId}")
    public ResponseEntity<?> updateKanban(@PathVariable String kanbanId, @RequestBody Kanban kanban) throws KanbanNotFoundException {
        try {
            Kanban updateKanban = kanbanService.updateKanban(kanbanId, kanban);
            return new ResponseEntity<>(updateKanban, HttpStatus.OK);
        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handle Delete request to delete Kanban
     *
     * @param kanbanId The ID of kanban to delete
     * @return Response with Successfully deleted message and with 200 status if deleted or error message with 500 status
     * @throws KanbanNotFoundException If requested ID not found
     */
    @DeleteMapping("/{kanbanId}")
    public ResponseEntity<?> deleteKanban(@PathVariable String kanbanId) throws KanbanNotFoundException {
        try {
            kanbanService.deleteKanban(kanbanId);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param kanbanId ID of kanban to be assigned
     * @param user     To whom Kanban is being assigned
     * @return Response with assigned Kanban and 200 status if successful or error message with 500 status
     * @throws KanbanNotFoundException if no Kanban is found
     */
    @PostMapping("/{kanbanId}/assign-kanban")
    public ResponseEntity<?> assignKanbanToUser(@PathVariable String kanbanId, @RequestBody User user) throws KanbanNotFoundException {
        try {

            Kanban assignedKanban = kanbanService.assignKanbanToUser(kanbanId, user.getEmail());

            return new ResponseEntity<>(assignedKanban, HttpStatus.OK);


        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve Kanbans for specific to the logged-in user
     *
     * @param request The HTTP servlet request containing user information
     * @return Response with containing list of kanban of logged-in user and 200 status if found or error message with 500 status.
     * @throws KanbanNotFoundException if no Kanbans are found
     */
    @GetMapping("/user-specific-kanban")     //for fetching assigned kanbans
    public ResponseEntity<?> getUserSpecificKanban(HttpServletRequest request) throws KanbanNotFoundException {
        // Extract email of logged-in user
        String email = (String) request.getAttribute("current-user");
        try {
            List<Kanban> kanbans = kanbanService.getKanbanByEmail(email);
            return new ResponseEntity<>(kanbans, HttpStatus.OK);
        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve Kanbans for assigned user
     *
     * @param request The HTTP servlet request containing user information
     * @return Response with containing list of kanban for assigned user and 200 status if found or error message with 500 status
     * @throws KanbanNotFoundException if no Kanbans are found
     */
    @GetMapping("/assigned-kanban")
    public ResponseEntity<?> getAssignedKanban(HttpServletRequest request) throws KanbanNotFoundException {
        // Extract email of logged-in user
        String email = (String) request.getAttribute("current-user");
        System.out.println("     email id   "+email);
        try {
            List<Kanban> kanbans = kanbanService.getAssignedKanban(email);
            return new ResponseEntity<>(kanbans, HttpStatus.OK);
        } catch (KanbanNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
