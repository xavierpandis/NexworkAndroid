package com.cjx.nexwork.managers.study;

import com.cjx.nexwork.model.Study;

import java.util.List;

public interface StudyDetailCallback {
    void onSuccess(Study study);

    void onFailure(Throwable t);
}
