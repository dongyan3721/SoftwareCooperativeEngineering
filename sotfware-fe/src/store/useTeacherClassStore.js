/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/20-21:53:43
 */

import {defineStore} from "pinia";
import {aesDecrypt, aesEncrypt} from "@/util/aes.js";

const _undefined = void 0
export const useTeacherClassStore = defineStore(
    'teacher-class',
    ()=>{

        const classId = ref()
        const bClass = ref()

        // 设置队伍id信息
        const setClassId = (class_id)=>{
            classId.value = class_id
        }

        const setClass = (clazz)=>{
            bClass.value = clazz
        }

        return {
            classId, setClassId, setClass, bClass
        }
    },
    // 通用持久化
    {
        persist: {
            storage: {
                getItem(key){
                    return aesDecrypt(localStorage.getItem(key))
                },
                setItem(key, value){
                    return localStorage.setItem(key, aesEncrypt(value))
                }
            }
        }
    }
)

