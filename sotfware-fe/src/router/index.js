import {sys_user_role} from "@/configuration/dictionary.js";

const routes = [
    {
        path: '/',
        redirect: '/teacher-main'
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
        component: ()=>import('@/view/teacher/teacher-course-setting.vue')
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
    },
    {
        path: '/teacher-student-import',
        name: 'teacher-student-import',
        component: ()=>import('@/view/teacher/teacher-import-students.vue')
    },
    {
        path: '/teacher-manage-group',
        name: 'teacher-manage-group',
        component: () => import('@/view/teacher/teacher-manage-student-group.vue')
    },
    {
        path: '/403',
        name: '403',
        component: () => import('@/view/general/HttpStatus403.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/view/general/HttpStatus404.vue')
    },

    {
        path: '/teacher-course-setting',
        name: 'teacher-course-setting',
        component: () => import('@/view/teacher/teacher-course-setting.vue')
    }
];

import {createRouter} from 'vue-router'
import {createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(),
    // history: createWebHashHistory(),
    routes: routes
})

import {useUserStore} from "@/store/index.js";
// 路由前置守卫
const whiteList = ['login', 'preview', '403', '404']
router.beforeEach((to, from, next) => {
    // console.log(to, from)

    if(whiteList.includes(to.name)){
        next(); return;
    }
    const userStore = useUserStore();
    if(!userStore.token){
        next('/login'); return;
    }
    // 带了token
    // 学生尝试切换到老师的路由
    if(to.name.indexOf('teacher')===0&&userStore.userRole===sys_user_role.STUDENT){
        next('/403');
        return;
    }
    if(to.name.indexOf('student')===0&&userStore.userRole===sys_user_role.TEACHER){
        next('/403');
        return;
    }
    // 其余情况均为合法，出事再说~
    next()
});

export default router