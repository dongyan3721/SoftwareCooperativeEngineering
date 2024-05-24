<!--
-- @author Santa Antilles
-- @description
-- @date 2024/5/21-12:03:07
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import UniverseSection from "@/components/universe-section.vue";
import {useTeacherClassStore} from "@/store/index.js";
import {ElMessage} from "element-plus";
import {Seal, DataArrival} from "@icon-park/vue-next";
import {
  audit,
  listAllGroupLeaderApply,
  teacherListClassTasks,
  teacherModifyClassPhase
} from "@/web-api/teacher/teacherManageStudentGroup.js";
import {sys_class_phase} from "@/configuration/dictionary.js";
import {dictDataExtractWidthReflection} from "@/util/dictDataExtarct.js";
const teacherClassStore = useTeacherClassStore()
const clazz = teacherClassStore.bClass

const groupPermission = ref(clazz.phase>sys_class_phase.INIT);
const swLoading = ref(false);
const handleChangeGroupStatus = (val)=>{
  // val为初始化时候groupPermission的反向值

  if(!groupPermission.value){
    groupPermission.value = !val
    ElMessage.error('时间线不允许回退！')
    return
  }
  groupPermission.value = !val
  swLoading.value = true

  // 这里做上从课程初始化阶段到组队阶段的逻辑
  teacherModifyClassPhase({classId: clazz.classId, phase: sys_class_phase.TEAMING}).then(res=>{
    groupPermission.value = val
    clazz.phase = sys_class_phase.TEAMING
  }).finally(()=>{
    swLoading.value = false
  })
  // setTimeout(function (){
  //   groupPermission.value = val
  //   swLoading.value = false
  // }, 2000)
  // if(val){
  //   // 学生可以看到组队菜单
  // }else{
  //   // 关闭学生的组队菜单
  // }
}

const taskSw = ref(clazz.phase>sys_class_phase.TEAMING)
const taskSwLoading = ref(false)
const handleTaskLoading = (val)=>{

  if(!taskSw.value){
    taskSw.value = !val;
    ElMessage.error('时间线不允许回退！')
    return
  }

  taskSw.value = !val
  taskSwLoading.value = true
  teacherModifyClassPhase({classId: clazz.classId, phase: sys_class_phase.TASKING}).then(res=>{
    taskSw.value = val
    clazz.phase = sys_class_phase.TASKING
  }).finally(()=>{
    taskSwLoading.value = false
  })
  // setTimeout(function (){
  //   taskSw.value = val
  //   taskSwLoading.value = false
  // }, 2000)
  // 启动后，关闭学生组队开关
  // 这开关开了就不让关了
  // 开关开了之后学生才能看到任务提交菜单
}

const classOverSw = ref(clazz.phase>sys_class_phase.TASKING)
const classOverLoading = ref(false)
const handleClassOverLoading = (val)=>{

  if(!classOverSw.value){
    classOverSw.value = !val;
    ElMessage.error('时间线不允许回退！')
    return
  }

  classOverSw.value = !val
  classOverLoading.value = true
  teacherModifyClassPhase({classId: clazz.classId, phase: sys_class_phase.OVER}).then(res=>{
    classOverSw.value = val
    clazz.phase = sys_class_phase.OVER
  }).finally(()=>{
    classOverLoading.value = false
  })
  // setTimeout(function (){
  //   classOverSw.value = val
  //   classOverLoading.value = false
  // }, 2000)
  // 启动后，不对其他开关状态做限制
  // 这开关开了就不让关了
  // 开关开了之后学生才能看到任务提交菜单
}

const handleAuditStudentCreateTeamRequest = (appealId, isAccepted)=>{
  audit(appealId, isAccepted).then(()=>{
    ElMessage.success(isAccepted?'已通过':'已驳回')
    beginRequestApply()
  })
}
const reqTableLoading = ref(false)
const beginRequestApply = ()=>{
  // 获取所有本教学班的组长申请
  reqTableLoading.value = true
  listAllGroupLeaderApply(clazz.classId).then(res=>{
    totalStudentGroupApply.value = res.total
    allTeamBuildRequest.value = res.rows
    reqTableLoading.value = false
  })
}
const allTeamBuildRequest = ref()
const auditDialogVis = ref(false)
const totalStudentGroupApply = ref()


const taskDialogVis = ref(false)
const classTasks = ref()
const taskTabLoading = ref(false)
const requestClassTasksList = ()=>{
  taskTabLoading.value = true
  teacherListClassTasks(clazz.classId).then(res=>{
    classTasks.value = res.data.map(task=>{
      // 转成中文
      task.taskStudentRole = sys_student_role.value.reflect[task.taskStudentRole]
    })
    taskTabLoading.value = false
  })
}
const sys_student_role = ref({data: null, reflect: null})
dictDataExtractWidthReflection('sys_student_role').then(res=>{
  sys_student_role.value = res
})

const modifyDialogVis = ref(false)
const modifyForm = reactive({
  classId: clazz.classId,
  deadline: null,
  taskContent: null,
  taskId: null,
  taskOrder: null,
  taskStudentRole: null
})
</script>

<template>
  <teacher-menu>
    <universe-section title="组队控制开关"/>
<!--    组队关闭之后强行启动项目-->
    <el-switch size="large" v-model="groupPermission" :loading="swLoading" inactive-text="！闭关，队组"
               active-text="组队，启动！" @change="handleChangeGroupStatus"/>
<!--    当课程状态不为0的时候，允许审批组长申请-->
    <el-button @click="()=>{groupPermission?auditDialogVis=true:ElMessage.error('当前组队阶段未开始'); if(auditDialogVis){beginRequestApply()}}"
               type="primary" size="large" class="block my-2">审批组队申请</el-button>
    <universe-section title="项目启动开关"/>
    <el-switch size="large" v-model="taskSw" :loading="taskSwLoading" inactive-text="！束结，目项"
               active-text="项目，启动！" @change="handleTaskLoading"/>
    <el-button @click="()=>{taskDialogVis=true; requestClassTasksList()}" type="primary" size="large" class="block my-2" :disabled="!taskSw">
      修改课程任务配置
    </el-button>
    <universe-section title="结课开关"/>
    <el-switch size="large" v-model="classOverSw" :loading="classOverLoading" inactive-text="！启开，程课"
               active-text="课程，结束！" @change="handleClassOverLoading"/>

<!--    用于审批学生建队申请的对话框-->
    <el-dialog v-model="auditDialogVis">
      <template #header>
        <div class="h-100 w-100 flex items-center">
          <seal theme="outline" size="16" fill="#2a3f67"/>
          <span class="inline-block ml-1">审批</span>
        </div>
      </template>
      <el-table :data="allTeamBuildRequest" v-loading="reqTableLoading">
        <el-table-column prop="studentName" label="申请人姓名" align="center"/>
        <el-table-column prop="studentId" label="申请人学号" align="center"/>
        <el-table-column label="申请人头像" align="center">
          <template #default="line">
            <n-avatar :size="32" circle :src="line.row.studentAvatar"></n-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="groupName" label="小组名称" align="center"/>
        <el-table-column label="操作" align="center">
          <template #default="line">
            <el-button text type="primary" size="small" @click="handleAuditStudentCreateTeamRequest(line.row.appealId, true)">同意</el-button>
            <el-button text type="danger" size="small" @click="handleAuditStudentCreateTeamRequest(line.row.appealId, false)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

<!--    用于修改自动创建任务的对话框-->
    <el-dialog v-model="taskDialogVis" width="600px">
      <template #header>
        <div class="h-100 w-100 flex items-center">
          <data-arrival theme="outline" size="16" fill="#2a3f67"/>
          <span class="ml-1 inline-block">修改任务配置</span>
        </div>
      </template>
      <el-table :data="classTasks" v-loading="taskTabLoading">
        <el-table-column label="任务名称" prop="taskContent" align="center"/>
        <el-table-column label="任务负责人角色" prop="taskStudentRole" align="center"/>
        <el-table-column label="截止时间" prop="deadline" align="center"/>
        <el-table-column label="操作">
          <template #default="line">
            <el-button @click="()=>{modifyDialogVis=true}" text type="primary">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog v-model="modifyDialogVis">

    </el-dialog>

  </teacher-menu>
</template>

<style scoped>

</style>