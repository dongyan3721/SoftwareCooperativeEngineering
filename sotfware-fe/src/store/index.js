/**
 * pinia包
 */

import {createPinia} from "pinia";
import persist from 'pinia-plugin-persistedstate'


const pinia = createPinia()
pinia.use(persist)

// 默认导出给main.js挂载
export default pinia

export * from "./useUserStore.js"
export * from './useStudentClassStore.js'
export * from './useStudentGroupStore.js'
