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

public class StartProcessRequest implements Request {
    long userId;
    private String fio;
    private String birthDate;

    public StartProcessRequest() {
    }

    public StartProcessRequest(long userId, String fio, String birthDate) {
        this.userId = userId;
        this.fio = fio;
        this.birthDate = birthDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "StartProcessRequest{" +
                "userId=" + userId +
                ", fio='" + fio + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
