<!--
-- @author Santa Antilles
-- @description 学生大菜单，写页面只要往对应插槽里面插就行了
-- @date 2024/4/23-20:44:31
-->

<script setup>
import {useRoute} from "vue-router";
const route = useRoute()
import {ArrowRight, EditPen, Notebook} from "@element-plus/icons-vue";
import {Peoples, Hands} from "@icon-park/vue-next";
const currentUrl = ref(route.path);
import {useUserStore} from "@/store/index.js";
import {studentLogin} from "@/web-api/general/login.js";
const userStore = useUserStore()
const {avatar, userName} = storeToRefs(userStore)
const {clearLoginInFo} = userStore
import router from '@/router'
const gotoIndex = ()=>{
  router.push('/login')
}

const quitLogin = ()=>{
  clearLoginInFo()
  gotoIndex()
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" style="min-height: 100vh; background:#bbbbbb;">
        <div class="aside-head-info"
             style="width: 200px; height: 80px; align-items: center; justify-items: center; display: flex; background: rgb(248,245,243);">
          <span
              style="display: flex; align-items: center; justify-content: center; width: 200px">软件协同设计</span>
        </div>
        <el-menu style="border: none" :default-active="currentUrl" router background-color="bbb" text-color="#f9f9f9">
          <el-menu-item index="/student-group-apply">
            <template #title>
              <el-icon><hands theme="outline" size="18" fill="#fff"/></el-icon>
              加入小组
            </template>
          </el-menu-item>
          <el-menu-item index="/student-main">
            <template #title>
              <el-icon><EditPen /></el-icon>
              课程学习
            </template>
          </el-menu-item>
          <el-menu-item index="/student-task">
            <template #title>
              <el-icon><Notebook /></el-icon>
              课程任务
            </template>
          </el-menu-item>
          <el-menu-item index="/student-group">
            <template #title>
              <el-icon><peoples theme="outline" size="18" fill="#fff"/></el-icon>
              我的小组
            </template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="box-shadow: 2px 0 6px rgba(0, 21, 41, .36); display: flex; align-items: center; justify-content: space-between">
          <el-breadcrumb :separator-icon="ArrowRight">
            <el-breadcrumb-item v-for="item in route.matched" :to="{path: item.path}">{{ item.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="avatar-container" style="align-items: center; display: flex; justify-content: flex-end">
            <el-dropdown style="display: flex; align-items: center; justify-content: flex-end" placement="bottom">
              <span class="el-dropdown-link" style="display: flex; align-items: center; cursor: pointer">
                <el-avatar :size="24" :src="avatar" style="margin: 0 5px"/>
                {{ '欢迎您，' + userName }}
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click.native="gotoIndex">返回首页</el-dropdown-item>
                  <el-dropdown-item @click.native="quitLogin">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <slot></slot>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">
:deep(.is-active){
  color: #171823;
}
</style>