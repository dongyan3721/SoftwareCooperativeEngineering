/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/21-14:40:53
 */

import request from "@/util/request.js";

export function listAllGroupLeaderApply(classId, params = {page: 1, pageSize: 10}){
    return request({
        url: '/teacher/group/appeal/' + classId,
        method: 'GET',
        params
    })
}


