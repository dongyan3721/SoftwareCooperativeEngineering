import {defineStore} from "pinia";
import {aesDecrypt, aesEncrypt} from "@/util/aes.js";

/**
 * @author Santa Antilles
 * @description 存储学生当前的队伍信息
 * @date 2024/5/15-01:34:25
 */
const _undefined = void 0
export const useStudentGroupStore = defineStore(
    'student-group',
    ()=>{

        const group = ref()
        const isLeader = ref()

        // 设置队伍id信息
        const setStudentGroup = (group_id)=>{
            group.value = group_id
        }

        // 设置当前登录用户是否是组长身份
        const setIfLeader = (is_leader)=>{
            isLeader.value = is_leader
        }

        // 清空信息
        const clearGroupInfo = ()=>{
            group.value = _undefined
            isLeader.value = _undefined
        }

        return {
            group, isLeader, setStudentGroup, setIfLeader, clearGroupInfo
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
 