<script setup>
import ProgressBar from "@/components/general/progress-bar.vue";

const testData = ref([
  {
    title: '需求分析',
    taskFinishStatus: '2',
    taskId: 'ad3dd2ds21',
    deadline: '2024-05-01 18:00:00'
  },
  {
    title: '进度计划',
    taskFinishStatus: '2',
    taskId: 'ad3dd03ds21',
    deadline: '2024-05-05 18:00:00'
  },
  {
    title: '质量计划',
    taskFinishStatus: '2',
    taskId: 'ad3dd2ds21892',
    deadline: '2024-05-10 18:00:00'
  },
  {
    title: '开发实现',
    taskFinishStatus: '1',
    taskId: 'ad3dd2ds63s21',
    deadline: '2024-05-15 18:00:00'
  },
  {
    title: '测试验收',
    taskFinishStatus: '',
    taskId: 'ad3dd2df0us21',
    deadline: '2024-05-20 18:00:00'
  }
])

</script>

<template>
  <progress-bar :progress-items="testData"/>
</template>

<style scoped lang="scss">
:deep(.el-col){
  display: flex;
  justify-content: center;
}

</style>