import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import NaiveUI from 'naive-ui'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router/index'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap'
import $ from 'jquery'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import '../tail.main.scss'
import pinia from "@/store/index.js";
// 全局样式引入
// import '@/assets/style/typesetting.scss'


const app = createApp(App)
// 全局挂载element-plus图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//集成element-plus并国际化
app.use(ElementPlus, {
    locale: zhCn,
})
// 集成naiveUI
app.use(NaiveUI)
// 集成vue-router
app.use(router)
// 集成jQuery
app.use($)
// 集成pinia
app.use(pinia)
app.mount('#app')
