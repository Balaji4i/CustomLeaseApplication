package custom.lease.custom.view.beans.filmStrip;

import oracle.adf.controller.TaskFlowId;

public class DashBoard {
    private String taskFlowId = "/WEB-INF/TaskFlow/SearchProperty_BTF.xml#SearchProperty_BTF";

    public DashBoard() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }
}
