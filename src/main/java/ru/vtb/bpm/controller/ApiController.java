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
package ru.vtb.bpm.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import ru.vtb.bpm.data.KogitoData;
import ru.vtb.bpm.data.TaskData;
import ru.vtb.bpm.data.request.ContinueProcessRequest;
import ru.vtb.bpm.data.request.StartProcessRequest;
import ru.vtb.bpm.data.response.ErrorResponse;
import ru.vtb.bpm.data.response.Response;
import ru.vtb.bpm.data.response.SuccessfulResponse;
import ru.vtb.bpm.enums.Task;
import ru.vtb.bpm.service.KogitoService;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
    // userId - <bpId - Task> (вполне возможно, что таска не нужна)
    private Map<Long, Pair<String, Task>> map = new ConcurrentHashMap<>();
    private KogitoService kogitoService;

    public ApiController(KogitoService kogitoService) {
        this.kogitoService = kogitoService;
    }

    // получить состояние процесса (данные и шаг)
    @GetMapping(path = "/process/{userId}", produces = { "application/json" })
    public Response getProcessInfo(@PathVariable(name = "userId") String userId) {
        System.out.println("Get process info");
        Long id;
        try {
            id = Long.parseLong(userId);
        } catch (Exception e) {
            return new ErrorResponse("INCORRECT_ID", "Incorrect user ID format");
        }
        if (!map.containsKey(id)) {
            return new ErrorResponse("PROCESS_NOT_FOUND", "The process with the specified ID was not found");
        }

        String processId = map.get(id).getLeft();
        KogitoData processData = kogitoService.getProcessDataById(processId);
        SuccessfulResponse response =
                new SuccessfulResponse(map.get(id).getRight().getName(), processData.getProcessInformation(), id);
        System.out.println("Process info response: " + response.toString());

        return response;
    }

    // начать новый процесс
    @PostMapping(path = "/process/start", consumes = { "application/json" },
            produces = { "application/json" })
    public Response startProcess(@RequestBody StartProcessRequest request) {
        System.out.println("Start new process");
        if (map.containsKey(request.getUserId())) {
            kogitoService.deleteProcessById(map.get(request.getUserId()).getLeft());
        }
        KogitoData kogitoData = kogitoService.startProcess(request);
        TaskData taskData = kogitoService.getTaskDataByProcess(kogitoData.getId());
        System.out.println("New process created with data: " + kogitoData.toString());

        map.put(request.getUserId(), new ImmutablePair<>(kogitoData.getId(), Task.getTaskByName(taskData.getStepName())));
        SuccessfulResponse response =
                new SuccessfulResponse(taskData.getStepName(), kogitoData.getProcessInformation(), request.getUserId());
        System.out.println("Start process response: " + response.toString());

        return response;
    }

    // продолжить процесс (пройти текущий шаг)
    @PostMapping(path = "/process/continue", consumes = { "application/json" },
            produces = { "application/json" })
    public Response continueProcess(@RequestBody ContinueProcessRequest request) {
        System.out.println("Continue process");
        long id = request.getUserId();

        if (!map.containsKey(id)) {
            return new ErrorResponse("PROCESS_NOT_FOUND", "The process with the specified ID was not found");
        }

        KogitoData processData = kogitoService.toNextStep(map.get(id).getLeft(), request.getProcessInformation());
        String processId = processData.getId();

        TaskData taskData = kogitoService.getTaskDataByProcess(processId);
        SuccessfulResponse response = null;
        // процесс завершился
        if (taskData == null) {
            System.out.println("Process completed");
            map.entrySet().removeIf(e -> e.getKey() == id);
            response = new SuccessfulResponse("Completed", processData.getProcessInformation(), id);
        } else { // процесс продолжается
            System.out.println("The process is waiting for the next step");
            Task task = Task.getTaskByName(taskData.getStepName());
            map.put(id, new ImmutablePair<>(processId, task));
            response = new SuccessfulResponse(task.getName(), processData.getProcessInformation(), id);
        }
        System.out.println("'Continue process' response: " + response.toString());
        return response;
    }
}
