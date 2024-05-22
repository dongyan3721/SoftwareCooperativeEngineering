import request from "@/util/request.js";

export function ListAllTeacherCourses(){
    return request({
        url: '/teacher/class/teacherAll' ,
        method: 'GET'
    });
}