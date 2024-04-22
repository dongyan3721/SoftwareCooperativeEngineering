/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/22-15:40:24
 */
import request from "@/util/request.js";

/**
 * Promise成功：{value: aa, label: bb}
 * @param dictName
 */
function dictDataExtract(dictName){
    return new Promise((resolve, reject) => {
        request({
            url: '/common/dict/'+dictName,
            method: 'GET'
        }).then(res=>{
            resolve(res.rows.map(dict=>{return {value: dict.value, label: dict.tag}}))
        }).catch(err=>reject(err))
    })
}

export default dictDataExtract