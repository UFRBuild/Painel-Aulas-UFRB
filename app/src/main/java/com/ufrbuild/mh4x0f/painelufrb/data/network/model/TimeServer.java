/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019 UFRBuild - Marcos Bomfim

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

package com.ufrbuild.mh4x0f.painelufrb.data.network.model;
import android.util.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class TimeServer {
    @Expose
    @SerializedName("result")
    private long result;

    private long start_time_min;
    private long start_time_max;
    private long start_min;
    private long start_max;


    public void setResult(int ds) {
        this.result = ds;
    }

    public long getResult() {
        return result;
    }

    public long getStart_time_min() {
        return start_time_min;
    }

    public void setStart_time_min(long start_time_min) {
        this.start_time_min = start_time_min;
    }

    public long getStart_time_max() {
        return start_time_max;
    }

    public void setStart_time_max(long start_time_max) {
        this.start_time_max = start_time_max;
    }

    public void Config(){
        if (getResult() != 0){
            long timecurrent = getResult() * 1000;
            DateTime(timecurrent, 5);
        }
    }

    private void DateTime(long ant, int comeback){
        Date datacurrent = new Date(ant);

        Log.i("result", "DateTime: " + datacurrent.getTime());
        long t_curr = datacurrent.getTime() / 1000 >> 0;
        Log.i("result", "t_curr: " + t_curr);
        long t_sec = (t_curr % 86400) - 3*3600;
        Log.i("result", "t_sec: " + t_sec);
        long t_ref = t_curr - t_sec;
        Log.i("result", "t_ref: " + t_ref);
        long sec = t_sec + comeback*60;
        Log.i("result", "sec: " + sec);
        this.start_min = 0;
        this.start_max = 0;

        // 7h00 as 10h00
        if (sec >= (7*3600) && sec < (10*3600)){
            this.start_min =7*3600;
            this.start_max =10*3600;
        }
        // 10h00 as 13h00
        else if (sec >= 10*3600 && sec < 13*3600) {
            this.start_min =10*3600;
            this.start_max =13*3600;
        }
        // 13h00 as 16h00
        else if (sec >= 13*3600 && sec < 16*3600) {
            this.start_min =13*3600;
            this.start_max =16*3600;
        }
        // 16h00 as 18h00
        else if (sec >= 16*3600 && sec < 18*3600) {
            this.start_min =16*3600;
            this.start_max =18*3600;
        }
        // 18h00 as 20h00
        else if (sec >= 18*3600 && sec < 20*3600) {
            this.start_min =18*3600;
            this.start_max =20*3600;
        }
        // 20h00 as 22h00
        else if (sec >= 20*3600 && sec < 22*3600) {
            this.start_min =20*3600;
            this.start_max =22*3600;
        }

        // cria atributos com quantidade de segundos da data e hora

        setStart_time_min(this.start_min + t_ref);
        setStart_time_max(this.start_max + t_ref);
    }


}
