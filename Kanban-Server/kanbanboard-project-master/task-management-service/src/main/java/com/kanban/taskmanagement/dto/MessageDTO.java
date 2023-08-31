package com.kanban.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {
    private JSONObject jsonObject;
}
