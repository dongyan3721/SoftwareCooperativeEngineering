const routes = [
    {
        path: '/',
        redirect: '/preview'
    },
    {
        path: '/test',
        name: 'index',
        component: ()=>import('@/view/Test.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: ()=>import('@/view/general/login.vue')
    },
    {
        path: '/student-main',
        name: 'student-main',
        component: ()=>import('@/view/student/student-course-settings.vue')
    },
    {
        path: '/student-task',
        name: 'student-task',
        component: ()=>import('@/view/student/student-task-proceeding.vue')
    },
    {
        path: '/student-group',
        name: 'student-group',
        component: ()=>import('@/view/student/student-group.vue')
    },
    {
        path: '/teacher-main',
        name: 'teacher-main',
        component: ()=>import('@/view/teacher/teacher-course-selection.vue')
    },
    {
        path: '/teacher-grade',
        name: 'teacher-grade',
        component: ()=>import('@/view/teacher/teacher-manage-grades.vue')
    },
    {
        path: '/teacher-monitor',
        name: 'teacher-monitor',
        // 老师监督小组任务进度用，可以查看历史小组提交内容以及批改小组最新提交内容
        component:()=>import('@/view/teacher/teacher-monitor-group-progress.vue')
    },
    {
        path: "/teacher-chat",
        name: 'teacher-chat',
        component: ()=>import("@/view/teacher/teacher-chat-with.vue")
    },
    {
        path: '/teacher-course-setting',
        name: 'teacher-course-setting',
        component: ()=>import('@/view/teacher/teacher-course-settings.vue')
    },
    {
        path: '/preview',
        name: 'preview',
        component: ()=>import('@/components/student/student-group-description.vue')
    },
    {
        path: '/teacher-notice',
        name: 'teacher-notice',
        component: ()=>import('@/view/teacher/teacher-notice.vue')
    },
    {
        path: '/student-group-apply',
        name: 'student-group-apply',
        component: ()=>import('@/view/student/student-group-apply.vue')
    },
    {
        path: '/student-notice',
        name: 'student-notice',
        component: ()=>import('@/view/student/student-notice.vue')
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