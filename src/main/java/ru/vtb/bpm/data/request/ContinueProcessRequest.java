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
package ru.vtb.bpm.data.request;

import ru.vtb.bpm.data.ProcessInformation;

public class ContinueProcessRequest implements Request {
    long userId;
    private ProcessInformation processInformation;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ProcessInformation getProcessInformation() {
        return processInformation;
    }

    public void setProcessInformation(ProcessInformation processInformation) {
        this.processInformation = processInformation;
    }

    @Override
    public String toString() {
        return "ContinueProcessRequest{" +
                "userId=" + userId +
                ", processInformation=" + processInformation +
                '}';
    }
}
