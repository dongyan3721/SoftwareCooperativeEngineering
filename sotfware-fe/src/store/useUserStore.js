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
export const useUserStore = defineStore(
    'user',
    ()=>{
        const token = ref()

        const setCookie = (resp)=>{
            token.value = resp
        }

        const clearCookie = ()=>{
            token.value = undefined
        }

        return {
            token, setCookie, clearCookie
        }
    },
    // 通用持久化
    {
        persist: {
            storage: {
                getItem(key){
                    return localStorage.getItem(key)
                },
                serItem(key, value){
                    localStorage.setItem(key, value)
                }
            }
        }
    }
)
