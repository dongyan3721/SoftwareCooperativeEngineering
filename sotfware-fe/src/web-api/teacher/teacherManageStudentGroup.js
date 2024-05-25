/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/21-14:40:53
 */

import request from "@/util/request.js";

export function listAllGroupLeaderApply(classId, params = {page: 1, pageSize: 10}){
    return request({
        url: '/teacher/group/appeal/'+classId,
        method: 'GET',
        params
    })
}

export function audit(appealId, isAccepted){
    const f = new FormData()
    f.append('isAccept', isAccepted);
    f.append('appealId', appealId);
    return request({
        url: '/teacher/group/approveLeader',
        data: f,
        method: 'POST'
    })
}

export function teacherUpdateClassTasks(clazz){
    return request({
        url: '/teacher/task',
        data: clazz,
        method: 'PUT'
    })
}

export function teacherListClassTasks(classId){
    return request({
        url: '/teacher/task',
        params: {classId},
        method: 'GET'
    })
}

export function teacherModifyClassPhase(data){
    return request({
        url: '/teacher/class/phase',
        data,
        method: 'PUT'
    })
}
