/**
 * @author Santa Antilles
 * @description 文件预览地址
 * @date 2024/4/20-13:28:04
 */

import previewInterface from "@/configuration/previewInterface.js";
import {Base64} from "js-base64";

export function manufacturePreviewUrl(fileDownLoadUrl){
    return `${previewInterface}?url=${encodeURIComponent(Base64.encode(fileDownLoadUrl))}`
}
