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

        const clazz = ref()

        // 设置登录
        const setStudentClass = (_class)=>{
            clazz.value = _class
        }

        // 清空信息
        const clearClassInfo = ()=>{
            clazz.value = _undefined
        }

        return {
            clazz, setStudentClass, clearClassInfo
        }
    },
    // 通用持久化
    {
        persist: true
    }
)