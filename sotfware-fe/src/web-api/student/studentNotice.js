import request from "@/util/request.js";

export function listAllStudentNotice(params = {page: 1, pageSize: 10}) {
    return request({
        url: '/student/notice',
        method: 'GET',
        params
    })
}

export function readAllNotices() {
    return request({
        url: '/student/notice/allRead',
        method: 'POST'
    })
}

export function confirmNotice(noticeId) {
    const fomData = new FormData()
    fomData.append('noticeId', noticeId)
    return request({
        url: '/student/notice/confirm',
        method: 'POST',
        data: fomData
    });
}