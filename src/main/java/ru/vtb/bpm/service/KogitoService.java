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

import ru.vtb.bpm.data.KogitoData;
import ru.vtb.bpm.data.ProcessInformation;
import ru.vtb.bpm.data.TaskData;
import ru.vtb.bpm.data.request.StartProcessRequest;

public interface KogitoService {

    // 1 - стартовать процесс
    public KogitoData startProcess(StartProcessRequest request);

    // 2 - получить инфу о процессе по идентификатору
    public KogitoData getProcessDataById(String processId);

    // 3 - получить идентификатор таски по идентификатору процесса
    public TaskData getTaskDataByProcess(String processId);

    // 4 - продолжить таску по идентификатору процесса и таски
    public KogitoData toNextStep(String processId, ProcessInformation request);

    // 5 - удалить процесс
    public void deleteProcessById(String processId);
}
