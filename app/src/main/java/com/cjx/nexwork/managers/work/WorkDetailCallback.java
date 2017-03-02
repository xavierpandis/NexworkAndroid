package com.cjx.nexwork.managers.work;

import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;

public interface WorkDetailCallback {
    void onSuccess(Work work);

    void onFailure(Throwable t);
}
