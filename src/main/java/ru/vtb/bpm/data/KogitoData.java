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
package ru.vtb.bpm.data;

/**
 * Для получения всей инфы о процессе из kogito
 */
public class KogitoData extends ProcessInformation {
    private String id;
    private ProcessInformation processInformation;
    private String errorText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProcessInformation getProcessInformation() {
        return processInformation;
    }

    public void setProcessInformation(ProcessInformation processInformation) {
        this.processInformation = processInformation;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public String toString() {
        return "KogitoData{" +
                "id='" + id + '\'' +
                ", errorText='" + errorText + '\'' +
                ", fio='" + fio + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportSeries='" + passportSeries + '\'' +
                ", consentToMicroloan=" + consentToMicroloan +
                ", processInformation=" + processInformation +
                '}';
    }
}
