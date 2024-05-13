/**
 * @author Santa Antilles
 * @description 文件上传接口
 * @date 2024/5/14-00:32:09
 */
import request from "@/util/request.js";

export default function (blob){
    const formData = new FormData()
    formData.append('file', blob)
    return request({
        url: '/common/upload',
        method: "POST",
        data: formData
    })
}

