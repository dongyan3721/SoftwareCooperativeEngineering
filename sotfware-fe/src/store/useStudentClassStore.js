import {defineStore} from "pinia";

/**
 * @author Santa Antilles
 * @description 存储学生的一些课程信息，如当前位于哪个课程
 * @date 2024/5/15-01:33:40
 */
const _undefined = void 0
export const useStudentClassStore = defineStore(
    'student-class',
    ()=>{

        const classId = ref()

        // 设置登录
        const setStudentClass = (class_id)=>{
            classId.value = class_id
        }

        // 清空信息
        const clearClassInfo = ()=>{
            classId.value = _undefined
        }

        return {
            classId, setStudentClass, clearClassInfo
        }
    },
    // 通用持久化
    {
        persist: true
    }
)