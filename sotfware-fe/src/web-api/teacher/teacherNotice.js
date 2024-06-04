import request from "@/util/request.js";

export function listAllTeacherNotice(params = {page: 1, pageSize: 5}) {
    return request({
        url: '/teacher/notice',
        method: 'GET',
        params
    })
}

export function readAllNotices() {
    return request({
        url: '/teacher/notice/allRead',
        method: 'POST'
    })
}

export function confirmNotice(noticeId) {
    const fomData = new FormData()
    fomData.append('noticeId', noticeId)
    return request({
        url: '/teacher/notice/confirm',
        method: 'POST',
        data: fomData
    });
}