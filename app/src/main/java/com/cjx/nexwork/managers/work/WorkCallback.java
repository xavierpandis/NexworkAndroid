package com.cjx.nexwork.managers.work;

import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.model.dto.WorkDTO;

import java.util.List;

/**
 * Created by jotabono on 8/2/17.
 */

public interface WorkCallback {
    void onSuccess(List<Work> workList);

    void onFailure(Throwable t);
}
