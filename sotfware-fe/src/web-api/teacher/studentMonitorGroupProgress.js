/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/26-19:47:26
 */

import request from "@/util/request.js";

// 查询当前学生队伍的提交处于哪个阶段
export function getStudentStage(groupId){
    return request({
        url: '/student/process',
        method: 'GET',
        params: {groupId}
    })
}

export function getClassGroups(classId){
    return request({
        url: '/student/group/'+classId,
        method: 'GET'
    })
}

export function getClassTasks(classId){
    return request({
        url: '/student/task',
        method: 'GET',
        params: {classId}
    })
}

