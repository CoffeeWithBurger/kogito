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
package ru.vtb.bpm.data.response;

import ru.vtb.bpm.data.ProcessInformation;

public class SuccessfulResponse implements Response {
    private String task;
    private ProcessInformation processInformation;
    private Long userId;

    public SuccessfulResponse(String task, ProcessInformation processInformation, Long userId) {
        this.task = task;
        this.processInformation = processInformation;
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public ProcessInformation getProcessInformation() {
        return processInformation;
    }

    public void setProcessInformation(ProcessInformation processInformation) {
        this.processInformation = processInformation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SuccessfulResponse{" +
                "task='" + task + '\'' +
                ", processInformation=" + processInformation +
                ", userId=" + userId +
                '}';
    }
}
