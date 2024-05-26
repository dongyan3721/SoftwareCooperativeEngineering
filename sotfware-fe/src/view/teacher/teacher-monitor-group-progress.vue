<!--
-- @description 老师批改学生作业
-- @date 2024/4/21-22:53:08
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import {getClassGroups, getClassTasks, getStudentStage} from "@/web-api/teacher/studentMonitorGroupProgress.js";
import {useTeacherClassStore} from "@/store/index.js";
import {sys_task_submit_status} from "@/configuration/dictionary.js";

const teacherClassStore = useTeacherClassStore();
const classStudentTaskSubmits = ref([])
const totalTasks = ref()
// 任务监督首先要获取本课程中所有小组
function requestStudentSubmitRecords(){

  getClassTasks(teacherClassStore.bClass.classId).then(res=>{
    classStudentTaskSubmits.value = res.data
    totalTasks.value = res.data.length
    getClassGroups(teacherClassStore.bClass.classId).then(r=>{
      r.data.forEach(group=>{
        getStudentStage(group.groupId).then(s=>{
          const resolved = s.data
          classStudentTaskSubmits.value = classStudentTaskSubmits.value.map((task, index)=>{
            if(index<resolved){
              task.taskFinishStatus = sys_task_submit_status.PASS
            }else if(resolved===index){
              task.taskFinishStatus = sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON
            }else{
              task.taskFinishStatus = sys_task_submit_status.UNREACHED
            }
          })
        })
        console.log(classStudentTaskSubmits.value)
      })
    })
  })

onBeforeMount(()=>{
  requestStudentSubmitRecords()
})
}

</script>

<template>
  <teacher-menu>

  </teacher-menu>
</template>

<style scoped>

</style>