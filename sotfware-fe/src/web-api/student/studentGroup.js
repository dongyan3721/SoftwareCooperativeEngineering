/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/25-15:14:23
 */
import request from "@/util/request.js";

export function groupGetStudentById(studentId){
    return request({
        url: '/student/'+studentId,
        method: 'GET'
    })
}

export function getGroupByGroupId(studentGroup){
    return request({
        url: '/student/group/getDetail',
        method: 'GET',
        params: {groupId: studentGroup}
    })
}

export function groupGetClassByClassId(classId){
    return request({
        url: '/common/class/'+classId,
        method: 'GET'
    })
}

export function getAllTeamApply(groupId, params){
    return request({
        url: '/student/group/appeal/'+groupId,
        params,
        method: 'GET'
    })
}


export function updateStudentGroup(data) {
    return request({
        url: '/student/group',
        data,
        method: 'PUT'
    })
}

export function auditApplication(appealId, isAccept){
    const f = new FormData()
    f.append('appealId', appealId)
    f.append('isAccept', isAccept)
    return request({
        url: '/student/group/approveIn',
        method: 'POST',
        data: f
    })
}

export function studentBreakDownGroup(groupId){
    return request({
        url: '/student/group/'+groupId,
        method: 'DELETE'
    })
}

export function studentGetAwayFromGroup(groupId){
    const f = new FormData()
    f.append('groupId', groupId)
    return request({
        url: '/student/group/exit',
        method: 'DELETE',
        data: f
    })
}

export function queryGroupMembers(groupId){
    return request({
        url: '/student/group/allMember',
        params: {groupId},
        method: 'GET'
    })
}

export function delegateGroupUser(studentId, role){
    const formData = new FormData()
    formData.append('studentId', studentId)
    formData.append('role', role)
    return request({
        url: '/student/group/appoint',
        data: formData,
        method: 'POST'
    })
}
