/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/16-19:22:45
 */

import request from "@/util/request.js";

export function createNewStudentGroup(data) {
    return request({
        url: '..',
        method: 'POST',
        data
    })
}

export function queryExistingStudentGroups(params){
    return request({
        url: '..',
        method: 'GET',
        params
    })
}


