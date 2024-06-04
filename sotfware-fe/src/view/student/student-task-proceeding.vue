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
  getGroupSubtasks, modifySubTaskDescription,
  postManagerComment, submitSubtask
} from "@/web-api/student/studentProceeding.js";
import {ElMessage, ElMessageBox, genFileId} from "element-plus";
import upload from "@/web-api/upload.js";
import {generalValidatorJudgeIfEmpty} from "@/util/common.js";
import {Edit} from "@element-plus/icons-vue";

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
const currentTaskId = ref()
const getProcess = () => {
  let groupId = currentUser.studentGroup
  // console.log(groupId)
  getGroupProcess(groupId).then(res => {
    process = res.data
    for (let i = 0; i < process; i++)
      classTasks.value[i].taskFinishStatus = sys_task_submit_status.PASS
    process<5?classTasks.value[process].taskFinishStatus = sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON:console.log('全部完成')
    for (let i = process + 1; i < 5; i++)
      classTasks.value[i].taskFinishStatus = sys_task_submit_status.UNREACHED
  })
}

const handleGoToSubmitTask = ()=>{
  if(!currentUserBelongSubTask.value){
    ElMessage.error('孩子你无敌了，你犯法了你知道吗？')
    return
  }
  if(!permitMemberSubmit.value){
    ElMessage.error('阶段负责人尚未将所有子任务分配完成，请等待分配完成后再试~')
    return
  }
  if(isTaskDistributor.value && !permitStageManagerSubmit.value){
    ElMessage.error('您还有组员未提交，您暂时不能提交最终版本！')
    return;
  }
  if(isTaskDistributor.value && permitMemberSubmit.value){
    ElMessageBox.confirm(
        '请确认给组员评分后再行提交',
        '提示',
        {
          confirmButtonText: '我已评完',
          cancelButtonText: '先去评分',
          type: 'warning',
        }).then(()=>{
      tempForm.link = null
      fileList.value = []
      visStudentSubmit.value = true
    })
    return;
  }

  // 打开提交框
  // ElMessage.success('11111')
  tempForm.link = null
  fileList.value = []
  visStudentSubmit.value = true
}

const rowRef = ref()

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
    let cnt = 0
    markList.value = []
    res.data.forEach(a=>{
      if(a.submitType) {
        taskDistributorId.value = a.taskHandler
        isTaskDistributor.value = taskDistributorId.value === currentUser.userId
      }

      if(a.submitStatus===sys_task_submit_status.WAIT_FOR_TEACHER_MARK) ++cnt

      // 但凡有一个任务没分配，就不允许提交
      if(a.submitStatus===sys_task_submit_status.UNASSIGNED) permitMemberSubmit.value = false

      // 获取当前登录人对应的任务id
      if(a.taskHandler==currentUser.userId) currentUserBelongSubTask.value = a
      // 获取除了阶段负责人外的所有成员，评分用
      if(!a.submitType){
        markList.value.push({
          groupId: currentUser.studentGroup,
          targetStuId: a.taskHandler,
          targetStuName: a.taskHandlerName,
          taskId: curTaskId
        })
      }
    })
    permitStageManagerSubmit.value = cnt === 4
  })
}

// 当前阶段负责人的id
const taskDistributorId = ref()
const isTaskDistributor = ref(false)
// 是否允许组员提交，默认允许
const permitMemberSubmit = ref(true)
// 当前登录人对应的子任务id
const currentUserBelongSubTask = ref()
// 记录当前是否允许阶段负责人提交
const permitStageManagerSubmit = ref(false)
const markList = ref([])

const taskContent = reactive({taskContent: null})
const visTaskContent = ref(false)
const selectedSubTask = ref()
const setSubtask = (subtask) => {
  selectedSubTask.value = subtask
  taskContent.taskContent = null
  visTaskContent.value = true
}
const submitSubTaskModify = ()=>{
  const params = {...selectedSubTask.value}
  params.taskHandlerWork = taskContent.taskContent
  modifySubTaskDescription(params).then(res=>{
    ElMessage.success('修改成功')
    visTaskContent.value = false
    getClassTasks()
  })
}

const managerComment = () => {
  visMarkGroupMates.value = true
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

const calcBtnStatus = (status)=>{
  if(status==sys_task_submit_status.UNASSIGNED) return 'default'
  else if(status==sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON) return 'danger'
  else return 'success'
}

const calcBtnText = (status)=>{
  if(status==sys_task_submit_status.UNASSIGNED) return '未分配任务'
  else if(status==sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON) return '未交'
  else return '已交'
}

const visStudentSubmit = ref(false)
const uploadRef = ref()
const fileList = ref()
const handleExceed = (files)=>{
  uploadRef.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  uploadRef.value.handleStart(file)
}
const tempForm = reactive({
  link: null
})
const handleManualUpload = (file)=>{
  console.log(file)
  upload(file.raw).then(res=>{
    tempForm.link = res.msg
  })
  ElMessage.success("文件上传成功，请点击定按钮完成提交")
}
const doCertifySubmit = ()=>{
  const subtask = {...currentUserBelongSubTask.value}
  subtask.submitLink = tempForm.link
  submitSubtask(subtask).then(res=>{
    ElMessage.success('提交成功！')
    visStudentSubmit.value = false
    getSubtasks()
  })
}

const visMarkGroupMates = ref(false)


// const translate = ref()
// const tsion = ref()
// const prev = ()=>{
//
// }
// const next = ()=>{
//
// }
const tempMark = ref()
const openMarkWindow = (row)=>{
  tempMark.value = {...row}
  visMark.value = true
}
const visMark = ref(false)
const markForm = reactive({
  performance: null,
  comment: null
})
const markFormRef = ref()
const resetMarkForm = ()=>{
  if(!markFormRef.value) return
  markFormRef.value.resetFields()
}
const doMark = ()=>{
  const param = {...tempMark.value}
  param.performance = markForm.performance
  param.comment = markForm.comment
  postManagerComment(param).then(res=>{
    ElMessage.success('评分完成！')
    visMark.value = false
    resetMarkForm()
  })
}
</script>

<template>

  <el-dialog v-model="visMark" title="评分与评价" width="400px">
    <el-form :model="markForm" ref="markFormRef">
      <el-form-item label="评分" prop="performance">
        <el-input-number v-model="markForm.performance" :max="100" :min="0"/>
      </el-form-item>
      <el-form-item label="评价" prop="comment">
        <el-input type="text" clearable v-model="markForm.comment"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doMark">提交</el-button>
        <el-button type="warning" @click="()=>{resetMarkForm(); visMark.value=false}">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="visMarkGroupMates" title="阶段负责人评分" width="500px">
<!--    <div class="swiper-box">-->
<!--      <div class="swiper-content">-->
<!--        <div class="swiper-show" :style="{'transform': 'translateX('+translate+')px', 'transition': tsion?'all 0.5s':'none'}">-->
<!--          -->
<!--        </div>-->
<!--        -->
<!--        <div class="arrow-box">-->
<!--          <span @click="prev"></span>-->
<!--          <span @click="next"></span>-->
<!--        </div>-->
<!--        -->
<!--      </div>-->
<!--    </div>-->
    <el-table :data="markList">
      <el-table-column label="组员学号" prop="targetStuId" align="center"/>
      <el-table-column label="组员姓名" prop="targetStuName" align="center"/>
      <el-table-column label="当前任务" prop="taskId" align="center" v-if="false"/>
      <el-table-column label="当前小组" prop="groupId" align="center" v-if="false"/>
      <el-table-column label="评分" align="center">
        <template #default="scope">
          <el-button text type="primary" @click="openMarkWindow(scope.row)">
            <template #icon>
              <el-icon><edit/></el-icon>
            </template>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-dialog>

  <el-dialog v-model="visStudentSubmit" title="提交子任务" width="400px">
    <el-upload v-model:file-list="fileList" ref="uploadRef" :limit="1"
               :on-exceed="handleExceed" :before-remove="()=>false" @change="handleManualUpload"
               :auto-upload="false">
      <template #trigger>
        <el-button type="primary">选择文件</el-button>
      </template>
      <template #tip>
        <div class="el-upload__tip text-red-500">
          限制上传5MB以内的文件
        </div>
      </template>
    </el-upload>
    <template #footer>
      <div class="h-100 w-100 flex justify-end">
        <el-button type="primary" @click="doCertifySubmit">提交</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="visTaskContent" title="分配子任务" width="400px">
    <el-form :model="taskContent">
      <el-form-item label="子任务内容">
        <el-input v-model="taskContent.taskContent" clearable placeholder="填写子任务内容"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitSubTaskModify">确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>


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
            <el-button :type="calcBtnStatus(scope.row.submitStatus)">
              {{calcBtnText(scope.row.submitStatus)}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button text type="primary" @click="setSubtask(scope.row)" :disabled="!isTaskDistributor">分配任务</el-button>
<!--            <el-button text type="primary" :disabled="scope.row.submitStatus!=='0'" @click="submitSubtask">提交任务</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <template #action>
        <div class="flex justify-end w-100 h-100">
<!--          <el-button type="success" size="large">组员评价</el-button>-->
          <el-button size="large" @click="managerComment" v-if="isTaskDistributor">负责人评价</el-button>
          <el-button type="warning" size="large" @click="handleGoToSubmitTask">提交任务</el-button>
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