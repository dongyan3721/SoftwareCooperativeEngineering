const routes = [
    {
        path: '/',
        redirect: '/index'
    },
    {
        path: '/index',
        name: 'index',
        component: ()=>import('@/view/Test.vue')
    }
];

import {createRouter} from 'vue-router'
import {createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(),
    // history: createWebHashHistory(),
    routes: routes
})

export default router