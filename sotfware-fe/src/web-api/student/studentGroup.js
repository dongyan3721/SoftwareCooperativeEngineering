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
        url: '/student/group/detail/'+studentGroup,
        method: 'GET'
    })
}

export function groupGetClassByClassId(classId){
    return request({
        url: '/common/class/'+classId,
        method: 'GET'
    })
}

