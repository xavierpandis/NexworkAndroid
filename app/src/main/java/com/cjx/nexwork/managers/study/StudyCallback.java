package com.cjx.nexwork.managers.study;

import com.cjx.nexwork.model.Study;

import java.util.List;

public interface StudyCallback {
    void onSuccess(List<Study> studiesList);

    void onFailure(Throwable t);
}
