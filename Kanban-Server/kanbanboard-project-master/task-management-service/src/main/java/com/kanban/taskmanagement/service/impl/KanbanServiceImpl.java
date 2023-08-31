package com.kanban.taskmanagement.service.impl;

import com.kanban.taskmanagement.dto.MessageDTO;
import com.kanban.taskmanagement.exception.KanbanAlreadyExistsException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Kanban;
import com.kanban.taskmanagement.repository.KanbanRepository;
import com.kanban.taskmanagement.service.KanbanService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KanbanServiceImpl implements KanbanService {

    private final KanbanRepository kanbanRepository;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    @Override
    public Kanban createKanban(Kanban newKanban, String email) throws KanbanAlreadyExistsException {
        List<Kanban> kanbanList = kanbanRepository.findByEmail(email);
        for(Kanban kanban : kanbanList) {
            if (kanban.getKanbanName().equalsIgnoreCase(newKanban.getKanbanName())) {
                throw new KanbanAlreadyExistsException();
            }
        }
        newKanban.setEmail(email);
        return kanbanRepository.save(newKanban);
    }

    @Override
    public Kanban getKanbanById(String kanbanId) throws KanbanNotFoundException {
        Optional<Kanban> kanban = kanbanRepository.findById(kanbanId);
        if (kanban.isEmpty()) {
            throw new KanbanNotFoundException();
        }
        return kanban.get();
    }

    @Override
    public List<Kanban> getKanbanByEmail(String email) throws KanbanNotFoundException {
        List<Kanban> kanban = kanbanRepository.findByEmail(email);
        if (kanban.isEmpty()) {
            throw new KanbanNotFoundException();
        }
        return kanban;
    }

    @Override
    public void deleteKanban(String kanbanId) throws KanbanNotFoundException {
        Optional<Kanban> optionalKanban = kanbanRepository.findById(kanbanId);

        if (optionalKanban.isEmpty()) {
            throw new KanbanNotFoundException();
        }
        Kanban kanban = optionalKanban.get();
        kanbanRepository.delete(kanban);
    }

    @Override
    public Kanban updateKanban(String kanbanId, Kanban updatedKanban) throws KanbanNotFoundException {
        Optional<Kanban> optionalKanban = kanbanRepository.findById(kanbanId);

        if (optionalKanban.isPresent()) {
            Kanban existingKanban = optionalKanban.get();

            if (updatedKanban.getKanbanName() != null) {
                existingKanban.setKanbanName(updatedKanban.getKanbanName());
            }

            return kanbanRepository.save(existingKanban);
        }
        throw new KanbanNotFoundException();
    }

    @Override
    public Kanban assignKanbanToUser(String kanbanId, String assigneeEmail) throws KanbanNotFoundException {
        Optional<Kanban> optionalKanban = kanbanRepository.findById(kanbanId);
        if (optionalKanban.isEmpty()) {
            throw new KanbanNotFoundException();
        }
        Kanban kanban = optionalKanban.get();
        kanban.getTeamMembers().add(assigneeEmail);


        MessageDTO message = new MessageDTO();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", assigneeEmail);
        jsonObject.put("kanban", kanban.getKanbanName());

        message.setJsonObject(jsonObject);

        rabbitTemplate.convertAndSend(directExchange.getName(), "kanban.routingKey", message);

        return kanbanRepository.save(kanban);
    }

    @Override
    public List<Kanban> getAssignedKanban(String email) throws KanbanNotFoundException {
        List<Kanban> kanban = kanbanRepository.findByTeamMembers(email);
        System.out.println(kanban+"   my list of kanbanssssssss---------");
        if (kanban.isEmpty()) {
            throw new KanbanNotFoundException();
        }
        return kanban;
    }
}
