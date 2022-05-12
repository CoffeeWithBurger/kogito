/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.vtb.bpm.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.vtb.bpm.data.KogitoData;
import ru.vtb.bpm.data.ProcessInformation;
import ru.vtb.bpm.data.TaskData;
import ru.vtb.bpm.data.request.StartProcessRequest;

@Service
public class KogitoServiceImpl implements KogitoService {
    @Value("http://localhost:8080")
    private String url;
    private final RestTemplate restTemplate;
    private final String schemeId = "process";

    public KogitoServiceImpl(@Qualifier("restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public KogitoData startProcess(StartProcessRequest request) {
        System.out.println("Creating a new process");
        ProcessInformation processInformation = new ProcessInformation();
        processInformation.setFio(request.getFio());
        processInformation.setBirthDate(request.getBirthDate());

        try {
            KogitoData kogitoData = restTemplate.postForObject(url + "/" + schemeId, processInformation, KogitoData.class);

            System.out.println("Data of new process: " + (kogitoData == null ? "" : kogitoData.toString()));
            System.out.println("The new process has been successfully created");
            return kogitoData;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Failed to start a new process");
        return null;
    }

    // получить инфу о процессе по идентификатору
    public KogitoData getProcessDataById(String processId) {
        System.out.println("Process search by ID");
        try {
            KogitoData kogitoData = restTemplate.getForObject(url + "/" + schemeId + "/" + processId, KogitoData.class);
            System.out.println(kogitoData == null ? "" : kogitoData.toString());
            return kogitoData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Process data not found");
        return null;
    }

    public KogitoData toNextStep(String processId, ProcessInformation processInformation) {
        System.out.println("Transition to a new stage of the process");
        TaskData taskData = getTaskDataByProcess(processId);

        KogitoData kogitoData = restTemplate.postForObject(
                url + "/" + schemeId + "/" + processId + "/" + taskData.getStepName() + "/" + taskData.getId(),
                processInformation, KogitoData.class);
        System.out.println("Process data after moving to the next step: " + (kogitoData == null ? "" : kogitoData.toString()));

        return kogitoData;
    }

    // получить идентификатор таски по идентификатору процесса
    public TaskData getTaskDataByProcess(String processId) {
        System.out.println("Process task search by ID");

        String response = "";
        try {
            // таски уже может не быть, а 404 будет обработана, как ошибка
            String taskUrl = url + "/" + schemeId + "/" + processId + "/tasks";
            System.out.println("Send request to " + taskUrl);
            response = restTemplate.getForObject(taskUrl, String.class);
            System.out.println("Task data: " + response);
        } catch (Exception e) {
            System.out.println("Process task not found");
            return null;
        }

        // Ответ приходит массивом из одного элемента и надо убрать скобки
        String[] strings = StringUtils.substringsBetween(response, "[", "]");
        if (strings != null && strings.length > 0)
            response = strings[0];

        TaskData taskData = new TaskData();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            taskData.setId(jsonNode.get("id").asText());
            taskData.setStepName(jsonNode.get("name").asText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Short task information: " + taskData.toString());
        return taskData;
    }

    // удалить процесс
    public void deleteProcessById(String processId) {
        // connection refused
        restTemplate.delete(url + "/" + schemeId + "/" + processId);
        System.out.println("Process with id " + processId + " deleted");
    }
}
