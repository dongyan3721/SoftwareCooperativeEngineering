package com.softwarecooperative.softwareciooperative.framework.net;

import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:22:21
 */
public class NotificationTemplate {

    public static final String NEW_NOTICE = "您有新的消息，请及时处理";

    public static String NEW_LEADER_APPEAL(String studentName) {
        return studentName + "申请成为组长，请确认是否通过";
    }
    public static String NEW_MEMBER_APPEAL(String studentName) {
        return studentName + "申请加入团队，请确认是否通过";
    }

    public static String LEADER_APPROVE(String studentName, String groupName) {
        return studentName + "同学您好，您的组长申请已通过，您现在是" + groupName + "的组长（开发经理）";
    }
    public static String LEADER_DENY(String studentName) {
        return studentName + "同学您好，很遗憾您的组长申请未通过审核";
    }
    public static String GROUP_DISBAND(String groupName) {
        return groupName + "团队已解散";
    }

    public static String IN_APPROVE(String studentName, String groupName) {
        return studentName + "同学您好，您的加入申请已通过，您现在是" + groupName + "的组员";
    }
    public static String IN_DENY(String studentName) {
        return studentName + "同学您好，很遗憾您的加入申请未通过审核";
    }

    public static String ROLE_CHANGED(String studentName, Integer newRole) {
        return studentName + "同学，您的角色已被更改为" + BStudent.roleCode2String(newRole.toString());
    }

    public static String REMOVE_FROM_GROUP(String studentName, String groupName) {
        return studentName + "同学，您已被移出团队" + groupName;
    }

    public static String EXIT_GROUP(String studentName) {
        return studentName + "已退出团队";
    }

    public static String TASK_HANDED_ON(String studentName, String taskStudentRole) {
        return studentName + "同学在" + BStudent.roleCode2String(taskStudentRole).substring(0, 2) + "阶段的任务已提交";
    }

    public static String SUBTASK_CHANGED(String taskHandlerName) {
        return taskHandlerName + "同学，您的阶段任务已修改，请及时查收";
    }

    public static String STUDENT_HAS_MARKED(String studentName, Integer performance) {
        return studentName + "同学，根据您在任务中的表现，组员给您打了" + performance + "分";
    }
}
