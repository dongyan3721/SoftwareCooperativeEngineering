/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/22-16:12:55
 */
import request from "@/util/request.js";

export function studentLogin(student){
    return request({
        url: '/student/login',
        data: student,
        method: 'POST',
        token: false
    })
}

export function teacherLogin(teacher){
    return request({
        url: '/teacher/login',
        data: teacher,
        method: 'POST',
        token: false
    })
}
