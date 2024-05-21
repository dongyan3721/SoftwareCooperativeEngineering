/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/21-14:37:50
 */

import request from "@/util/request.js";

export function getStudentById(studentId){
    return request({
        url: '/student/'+studentId,
        method: 'GET'
    })
}

export function updateStudent(student){
    return request({
        url: '/student',
        data: student,
        method: 'PUT'
    })
}


