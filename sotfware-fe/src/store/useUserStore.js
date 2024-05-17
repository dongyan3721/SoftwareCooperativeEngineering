import {defineStore} from "pinia";

// 定义store

// 可以用来修改单个属性
/**userStore.$patch({
    userName: '张三'
})

// 这种回调函数的形式适合修改集合类的数据，比如数组
userStore.$patch((state) => {
    state.avatar = 'https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/596.jpg'
})
 // 重置
 userStore.$reset()

// 监听和持久化
 watch(
 pinia.state,
 (state) => {
 // 每当状态发生变化时，将整个 state 持久化到本地存储。
 localStorage.setItem('piniaState', JSON.stringify(state))
 },
 { deep: true }
 )


 */
const _undefined = void 0
export const useUserStore = defineStore(
    'user',
    ()=>{

        // 设置登录
        const setGeneralLoginInfo = (resp)=>{
            token.value = resp.token
            userId.value = resp.data.studentId || resp.data.teacherId
            userName.value = resp.data.studentName || resp.data.teacherName
            avatar.value = resp.data.avatar
        }

        const setUserRole = (val)=>{
            // 根据用户填的表单数据来设置用户大角色
            userRole.value = val
        }

        const setStudentLoginSpeciality = (resp)=>{
            studentRole.value = resp.data.studentRole
            studentGroup.value = resp.data.studentGroup
            studentClass.value = resp.data.studentClass
        }

        const setTeacherLoginSpeciality = (resp)=>{
            // 暂时不设置
        }

        const clearLoginInFo = ()=>{
            token.value = _undefined
            userRole.value = _undefined
            userId.value = _undefined
            userName.value = _undefined
            avatar.value = _undefined
            studentRole.value = _undefined
            studentGroup.value = _undefined
            studentClass.value = _undefined
        }

        const token = ref()

        // 用户大角色，是学生还是老师
        const userRole = ref()

        // 学生或教师id
        const userId = ref()

        // 学生或教师姓名
        const userName = ref()

        // 头像
        const avatar = ref()

        // 学生在项目过程中扮演的角色
        const studentRole = ref()

        // 学生归属小组id号
        const studentGroup = ref()

        // 学生归属教学班号
        const studentClass = ref()


        return {
            token, setGeneralLoginInfo, setStudentLoginSpeciality, setTeacherLoginSpeciality,
            userRole, userId, userName, avatar, studentRole, studentClass, studentGroup, clearLoginInFo, setUserRole
        }
    },
    // 通用持久化
    {
        persist: true
    }
)
