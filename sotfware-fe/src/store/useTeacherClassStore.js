/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/20-21:53:43
 */

import {defineStore} from "pinia";

const _undefined = void 0
export const useTeacherClassStore = defineStore(
    'teacher-class',
    ()=>{

        const classId = ref()

        // 设置队伍id信息
        const setClassId = (class_id)=>{
            classId.value = class_id
        }


        return {
            classId, setClassId
        }
    },
    // 通用持久化
    {
        persist: true
    }
)

