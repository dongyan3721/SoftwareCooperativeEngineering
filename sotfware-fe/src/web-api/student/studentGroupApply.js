/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/16-19:22:45
 */

import request from "@/util/request.js";
import {listAllGroupLeaderApply} from "@/web-api/teacher/teacherManageStudentGroup.js";
import {getStudentById} from "@/web-api/student/student.js";

export function applyToBeLeader(group){
    return request({
        url: '/student/group/appealLeader',
        data: group,
        method: 'POST'
    })
}

export function queryExistingStudentGroups(classId){
    return request({
        url: '/student/group/'+classId,
        method: 'GET'
    })
}

export function applyToBeGroupMember(groupId){
    return request({
        url: '/student/group/appealIn',
        method: 'POST',
        data: {groupId}
    })
}

export function checkStudentHasPermissionToCreateTeam(studentId, classId){
    return new Promise(resolve=>{
        // 检查这个人有没有小组
        getStudentById(studentId).then(res=>{
            if(res.data.studentGroup) resolve(false)
            // 检查这个人有没有正在申请的条目
            listAllGroupLeaderApply(classId).then(res=>{
                res.rows.forEach(apply=>{
                    if(apply.stduentId===studentId) resolve(false)
                })
                resolve(true)
            })
        }).catch(()=>resolve(false))
    })
}

