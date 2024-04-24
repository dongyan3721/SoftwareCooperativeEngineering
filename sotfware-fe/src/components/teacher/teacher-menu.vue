<!--
-- @author Santa Antilles
-- @description
-- @date 2024/4/24-20:19:15
-->

<script setup>
import {useRoute} from "vue-router";
const route = useRoute()
import {ArrowRight, Edit, ChatDotRound, Monitor, Setting} from "@element-plus/icons-vue";
const currentUrl = ref(route.path);
import {useUserStore} from "@/store/index.js";
const userStore = useUserStore()
const {avatar, userName} = storeToRefs(userStore)
const {clearLoginInFo} = userStore

const gotoIndex = ()=>{

}

const quitLogin = ()=>{
  clearLoginInFo()
  gotoIndex()
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" style="min-height: 100vh; background-color: rgb(22,46,86)">
        <div class="aside-head-info"
             style="width: 200px; height: 80px; align-items: center; justify-items: center; display: flex">
          <span
              style="color: #f9f9f9; display: flex; align-items: center; justify-content: center; width: 200px">软件协同设计</span>
        </div>
        <el-menu style="border: none" :default-active="currentUrl" router background-color="#162E56" text-color="#f9f9f9">
          <el-menu-item index="/teacher-course-setting">
            <template #title>
              <el-icon><Setting /></el-icon>
              课程设置
            </template>
          </el-menu-item>
          <el-menu-item index="/teacher-monitor">
            <template #title>
              <el-icon><Monitor /></el-icon>
              任务监督
            </template>
          </el-menu-item>
          <el-menu-item index="/teacher-chat">
            <template #title>
              <el-icon><ChatDotRound /></el-icon>
              师生探讨
            </template>
          </el-menu-item>
          <el-menu-item index="/teacher-grade">
            <template #title>
              <el-icon>
                <Edit />
              </el-icon>
              学生成绩
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

<style scoped>

</style>