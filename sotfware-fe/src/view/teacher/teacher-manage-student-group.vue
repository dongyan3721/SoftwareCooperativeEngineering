<!--
-- @author Santa Antilles
-- @description
-- @date 2024/5/21-12:03:07
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import UniverseSection from "@/components/universe-section.vue";

const groupPermission = ref(false)
const swLoading = ref(false);
const handleChangeGroupStatus = (val)=>{
  groupPermission.value = !val
  swLoading.value = true
  setTimeout(function (){
    groupPermission.value = val
    swLoading.value = false
  }, 2000)
  if(val){
    // 学生可以看到组队菜单
  }else{
    // 关闭学生的组队菜单
  }
}

const taskSw = ref(false)
const taskSwLoading = ref(false)
const handleTaskLoading = (val)=>{
  taskSw.value = !val
  taskSwLoading.value = true
  setTimeout(function (){
    taskSw.value = val
    taskSwLoading.value = false
  }, 2000)
  // 启动后，关闭学生组队开关
  // 这开关开了就不让关了
  // 开关开了之后学生才能看到任务提交菜单
}

const classOverSw = ref(false)
const classOverLoading = ref(false)
const handleClassOverLoading = (val)=>{
  classOverSw.value = !val
  classOverLoading.value = true
  setTimeout(function (){
    classOverSw.value = val
    classOverLoading.value = false
  }, 2000)
  // 启动后，不对其他开关状态做限制
  // 这开关开了就不让关了
  // 开关开了之后学生才能看到任务提交菜单
}

</script>

<template>
  <teacher-menu>
    <universe-section title="组队控制开关"/>
    <el-switch size="large" v-model="groupPermission" :loading="swLoading" inactive-text="！闭关，队组"
               active-text="组队，启动！" @change="handleChangeGroupStatus"/>
    <universe-section title="项目启动开关"/>
    <el-switch size="large" v-model="taskSw" :loading="taskSwLoading" inactive-text="！束结，目项"
               active-text="项目，启动！" @change="handleTaskLoading"/>
    <universe-section title="结课开关"/>
    <el-switch size="large" v-model="classOverSw" :loading="classOverLoading" inactive-text="！启开，程课"
               active-text="课程，结束！" @change="handleClassOverLoading"/>
  </teacher-menu>
</template>

<style scoped>

</style>