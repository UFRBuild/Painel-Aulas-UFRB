/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019/2020 UFRBuild - Marcos Bomfim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.ufrbuild.mh4x0f.painelufrb.utils;


public class AppConstants {

    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final String TIMESTAMP = "yyyyMMdd_HHmmss";
    public static final String mDiscPref = "disciplines_pref";

    // API SMSA UFRB
    public static final String API_URL_Discipline = "https://smsa.ufrb.edu.br/backend/CRUD/";
    public static final String API_URL_Timer = "https://smsa.ufrb.edu.br/backend/Time/";
    public static final String API_URL_GIST = "https://gist.githubusercontent.com/mh4x0f/89068d42f16ccb5d3e46e94a6c55d93c/raw/";
}
