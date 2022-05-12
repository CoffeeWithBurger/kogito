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
package ru.vtb.bpm.enums;

public enum Task {
    UNKNOWN(0, "unknown"),
    START_PROCESS(1, "firstStep"),
    SECOND_STEP(2, "secondStep"),
    THIRD_STEP(3, "thirdStep"),
    FOURTH_STEP(4, "fourthStep");

    /**
     * Порядковый номер задачи
     **/
    private final int number;
    /**
     * Имя задачи для запроса в kogito
     **/
    private final String name;

    Task(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static Task getTaskByName(String taskName) {
        for (Task e : values()) {
            if (e.name.equals(taskName))
                return e;
        }
        return UNKNOWN;
    }
}
