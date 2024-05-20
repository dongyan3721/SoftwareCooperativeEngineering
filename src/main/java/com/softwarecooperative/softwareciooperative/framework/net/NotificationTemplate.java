package com.softwarecooperative.softwareciooperative.framework.net;

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

}
