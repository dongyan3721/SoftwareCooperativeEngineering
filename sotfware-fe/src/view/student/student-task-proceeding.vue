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
import {useUserStore} from "@/store/index.js";
import {
  getClassTask,
  getGroupProcess,
  getGroupSubtasks,
  postManagerComment
} from "@/web-api/student/studentProceeding.js";

const currentUser = useUserStore()

let classTasks = ref([{}, {}, {}, {}, {}])
let process = 0

const getClassTasks = () => {
  let classId = currentUser.studentClass
  // console.log(classId.id)
  getClassTask(classId).then(res => {
    if (res.code !== 200)
      return
    for (let i = 0; i < res.data.length; i++) {
      let source = res.data[i]
      let target = classTasks.value[i]
      target.title = source.taskContent
      target.taskId = source.taskId
      target.deadline = source.deadline
    }
    console.log("tasks " + classTasks.value[0].title)
    getSubtasks()
  })
}

const getProcess = () => {
  let groupId = currentUser.studentGroup
  // console.log(groupId)
  getGroupProcess(groupId).then(res => {
    if (res.code !== 200)
      return
    process = res.data
    for (let i = 0; i < process; i++)
      classTasks.value[i].taskFinishStatus = sys_task_submit_status.PASS
    classTasks.value[process].taskFinishStatus = sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON
    for (let i = process + 1; i < 5; i++)
      classTasks.value[i].taskFinishStatus = sys_task_submit_status.UNREACHED
    console.log("process " + classTasks.value[0].taskFinishStatus)
  })
}

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
  customWidth.value= `${downToGetInt(totalWidth, classTasks.value.length) < 120?120:downToGetInt(totalWidth, classTasks.value.length)}px`
}

let subtasks = ref([])

const getSubtasks = () => {
  if (process === 5)
    return
  let groupId = currentUser.studentGroup
  let curTaskId = classTasks.value[process].taskId
  getGroupSubtasks(groupId, curTaskId).then(res => {
    subtasks.value = res.data
  })
}

const setSubtask = () => {
  // TODO 设置子任务
}

const submitSubtask = () => {
  // TODO 提交子任务
}

const managerComment = () => {
  // TODO 阶段负责人打分，弹出一个弹窗
  // let dto = {
  //   "targetStuId": ,
  //   "groupId": currentUser.studentGroup,
  //   "taskId": ,
  //   "performance": ,
  //   "comment": ,
  // }
  // postManagerComment()
}

onMounted(()=>{
  window.addEventListener('resize', resizeListener)
  resizeListener();

  getProcess()
  getClassTasks()
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
        <progress-bar :progress-items="classTasks" :separator-width="customWidth" @goto-hand-on="handleGoToSubmitTask"/>
      </el-col>
      <el-col :span="3"/>
    </el-row>

    <universe-section title="任务分配"/>

    <n-card class="w-100">
      <el-table :data="subtasks">
        <el-table-column prop="taskHandlerName" align="center" label="任务处理人"/>
        <el-table-column prop="taskHandlerWork" align="center" label="任务内容"/>
        <el-table-column align="center" label="提交状态">
          <template #default="scope">
            <el-button :type="scope.row.submitStatus === sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON ||
                scope.row.submitStatus === sys_task_submit_status.UNASSIGNED ? 'danger' : 'success'"
                       @click="handleGoToSubmitTask(scope.row.taskHandlerName)">
              {{scope.row.submitStatus === sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON ||
                scope.row.submitStatus === sys_task_submit_status.UNASSIGNED ? '未交' : '已交'}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button text type="primary" @click="setSubtask">分配任务</el-button>
            <el-button text type="primary" :disabled="scope.row.submitStatus!=='0'" @click="submitSubtask">提交任务</el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #action>
        <div class="flex justify-end w-100 h-100">
<!--          <el-button type="success" size="large">组员评价</el-button>-->
          <el-button size="large" @click="managerComment">负责人评价</el-button>
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