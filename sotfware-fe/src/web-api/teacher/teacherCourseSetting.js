import request from "@/util/request.js";

export function ListClassChapter(classid){
    return request({
        url: '/teacher/chapter' ,
        method: 'GET',
        params: {
            classId:classid
        }
    });
}


export function AddClassChapter(bClassChapterSettings){
    return request({
        url: '/teacher/chapter' ,
        method: 'POST',
        data:bClassChapterSettings
    });
}

export function ModifyClassChapter(bClassChapterSettings){

    return request({
        url: '/teacher/chapter' ,
        method: 'PUT',
        data:bClassChapterSettings
    });
}

export function DeleteClassChapter(chapterId) {
    return request({
        url: `/teacher/chapter/${chapterId}`,
        method: 'DELETE',
    });
}



export function getChapterContents(chapterid){
    return request({
        url: '/teacher/chapterContent' ,
        method: 'GET',
        params: {
            chapterId:chapterid
        }
    });
}

export function addChapterContent(bClassChapterContent){
    return request({
        url: '/teacher/chapterContent' ,
        method: 'POST',
        data:bClassChapterContent
    });
}


export function modifyChapterContent(bClassChapterContent){

    return request({
        url: '/teacher/chapterContent' ,
        method: 'PUT',
        data:bClassChapterContent
    });
}

export function deleteChapterContent(contentId) {
    return request({
        url: `/teacher/chapterContent/${contentId}`,
        method: 'DELETE',
    });
}