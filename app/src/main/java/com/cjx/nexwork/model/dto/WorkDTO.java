package com.cjx.nexwork.model.dto;

import com.cjx.nexwork.model.Work;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jotabono on 8/2/17.
 */

public class WorkDTO {

    @SerializedName("trabajo")
    private Work work;

    public WorkDTO(Work work) {
        this.work = work;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
                "work=" + work +
                '}';
    }
}
