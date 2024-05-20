<!--
-- @description 学生任务进度
-- @date 2024/4/21-22:53:43
-->

<script setup>

import ProgressBar from "@/components/general/progress-bar.vue";
import {sys_task_submit_status} from "@/configuration/dictionary.js";
import StudentMenu from "@/components/student/student-menu.vue";
import {downToGetInt} from "@/util/dongyan.js";
import UniverseSection from "@/components/universe-section.vue";

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

const rowRef = ref()

const logRef = ()=>{
  console.log(rowRef.value)
}

const customWidth = ref()

const resizeListener = ()=>{
  if(!rowRef.value) {
    customWidth.value = '120px'
    return
  }
  const totalWidth = rowRef.value.$el.offsetWidth * 0.75
  customWidth.value= `${downToGetInt(totalWidth, testData.value.length) < 120?120:downToGetInt(totalWidth, testData.value.length)}px`
}

const testDeliveredTasks = [
  {taskHandler: 1324133143, taskHandlerName: 'AAAA', taskHandlerWork: '大家都嗯好的你多拿点几哦旗舰店跑车呢和大佛进厂', submitStatus: '0'},
  {taskHandler: 1324133143, taskHandlerName: 'AAAA', taskHandlerWork: '大家都嗯好的你多拿点几哦旗舰店跑车呢和大佛进厂', submitStatus: '1'},
  {taskHandler: 1324133143, taskHandlerName: 'AAAA', taskHandlerWork: '大家都嗯好的你多拿点几哦旗舰店跑车呢和大佛进厂', submitStatus: '0'},
  {taskHandler: 1324133143, taskHandlerName: 'AAAA', taskHandlerWork: '大家都嗯好的你多拿点几哦旗舰店跑车呢和大佛进厂', submitStatus: '1'},
  {taskHandler: 1324133143, taskHandlerName: 'AAAA', taskHandlerWork: '大家都嗯好的你多拿点几哦旗舰店跑车呢和大佛进厂', submitStatus: '0'},
]

onMounted(()=>{
  window.addEventListener('resize', resizeListener)
  resizeListener();
})

onUnmounted(()=>{
  window.removeEventListener('resize', resizeListener)
})

</script>

<template>
  <student-menu>

    <universe-section class="mb-3" title="任务进度"/>

    <el-row ref="rowRef">
      <el-col :span="3"/>
      <el-col :span="18">
        <progress-bar :progress-items="testData" :separator-width="customWidth" @goto-hand-on="handleGoToSubmitTask"/>
      </el-col>
      <el-col :span="3"/>
    </el-row>

    <universe-section title="任务分配"/>

    <n-card class="w-100">
      <el-table :data="testDeliveredTasks">
        <el-table-column prop="taskHandlerName" align="center" label="任务处理人"/>
        <el-table-column prop="taskHandlerWork" align="center" label="任务内容"/>
        <el-table-column align="center" label="提交状态">
          <template #default="scope">
            <el-button :type="scope.row.submitStatus==='0'?'danger':'success'" @click="handleGoToSubmitTask(scope.row.taskHandlerName)">
              {{scope.row.submitStatus==='0'?'未交':'已交'}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button text type="primary" :disabled="scope.row.submitStatus!=='0'">提交任务</el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #action>
        <div class="flex justify-end w-100 h-100">
          <el-button type="success" size="large">组员评价</el-button>
          <el-button size="large">负责人评价</el-button>
          <el-button type="warning" size="large">提交任务</el-button>
        </div>
      </template>
    </n-card>

    <div class="w-100 flex justify-center" style="font-family: alibaba-inclusive">
      <p class="my-2 text-4xl text-gray-200 italic">先辈语：逸一时，误一世，逸久逸久罢已零</p>
    </div>

  </student-menu>
</template>

<style scoped>

</style>