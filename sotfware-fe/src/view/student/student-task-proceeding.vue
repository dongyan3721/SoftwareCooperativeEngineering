<!--
-- @description 学生任务进度
-- @date 2024/4/21-22:53:43
-->

<script setup>

import ProgressBar from "@/components/general/progress-bar.vue";
import {sys_task_submit_status} from "@/configuration/dictionary.js";
import StudentMenu from "@/components/student/student-menu.vue";

const testData = ref([
  {
    title: '需求分析',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd2ds21',
    deadline: '2024-05-01 18:00:00'
  },
  {
    title: '进度计划',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd03ds21',
    deadline: '2024-05-05 18:00:00'
  },
  {
    title: '质量计划',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd2ds21892',
    deadline: '2024-05-10 18:00:00'
  },
  {
    title: '开发实现',
    taskFinishStatus: sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON,
    taskId: 'ad3dd2ds63s21',
    deadline: '2024-05-15 18:00:00'
  },
  {
    title: '测试验收',
    taskFinishStatus: sys_task_submit_status.UNREACHED,
    taskId: 'ad3dd2df0us21',
    deadline: '2024-05-20 18:00:00'
  }
])

const handleGoToSubmitTask = (taskId)=>{
  console.log(taskId)
}

</script>

<template>
  <student-menu>
    <progress-bar :progress-items="testData" @goto-hand-on="handleGoToSubmitTask"/>
  </student-menu>
</template>

<style scoped>

</style>