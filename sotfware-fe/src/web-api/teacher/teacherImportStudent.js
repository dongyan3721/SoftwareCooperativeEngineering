/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/21-10:12:00
 */

import request from "@/util/request.js";

const handleDeleteStudent = (id)=>{
    return request({
        url: '/teacher/student/'+id,
        method: 'DELETE'
    })
}
function handleAddStudent (student){
    return request({
        url: '/teacher/student',
        method: 'POST',
        data: student
    })
}
function handleUpdateStudent(student){
    return request({
        url: '/teacher/student',
        method: 'PUT',
        data: student
    })
}

function queryStudents(student){
    return request({
        url: 'teacher/student',
        params: student,
        method: 'GET'
    })
}

export {handleDeleteStudent, handleUpdateStudent, handleAddStudent, queryStudents}

