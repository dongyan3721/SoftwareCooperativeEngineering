<!--
-- @description 老师批改学生作业
-- @date 2024/4/21-22:53:08
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import {getClassGroups, getClassTasks, getStudentStage} from "@/web-api/teacher/studentMonitorGroupProgress.js";
import {useTeacherClassStore} from "@/store/index.js";
import {sys_task_submit_status} from "@/configuration/dictionary.js";
import TeacherTaskTimeLine from "@/components/teacher/teacher-task-time-line.vue";
import {downToGetInt} from "@/util/dongyan.js";

const teacherClassStore = useTeacherClassStore();
const classStudentTaskSubmits = ref([])
const totalTasks = ref()
const taskSubmitDic = ref([])

const progressItem = computed(()=>{
  return taskSubmitDic.value.map(task=>{
    const key = Object.keys(task)[0]
    return {
      groupId: key,
      value: task[key]
    }
  })
})
const groupNameRecord = ref([])

// 任务监督首先要获取本课程中所有小组
function requestStudentSubmitRecords(){
  // 调用之前先把相关的东西清空
  taskSubmitDic.value = []
  groupNameRecord.value = []
  getClassTasks(teacherClassStore.bClass.classId).then(res=>{
    classStudentTaskSubmits.value = res.data
    totalTasks.value = res.data.length
    getClassGroups(teacherClassStore.bClass.classId).then(r=>{
      r.data.forEach(group=>{
        getStudentStage(group.groupId).then(s=>{
          const resolved = s.data
          const tl = []
          classStudentTaskSubmits.value.forEach((task, index)=>{
            const temp = {...task}
            if(index<resolved){
              temp.taskFinishStatus = sys_task_submit_status.PASS
            }else if(resolved===index){
              temp.taskFinishStatus = sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON
            }else{
              temp.taskFinishStatus = sys_task_submit_status.UNREACHED
            }
            tl.push(temp)
          })
          taskSubmitDic.value.push({[group.groupId]: tl})
          groupNameRecord.value.push(group.groupName)
        })
      })
    })
  })
}
// const resizeListener = ()=>{
//   if(!rowRef.value) {
//     customWidth.value = '120px'
//     return
//   }
//   const totalWidth = rowRef.value.$el.offsetWidth * 0.75
//   customWidth.value= `${downToGetInt(totalWidth, classTasks.value.length) < 120?120:downToGetInt(totalWidth, classTasks.value.length)}px`
// }
//
// onMounted(()=>{
//   window.addEventListener('resize', resizeListener)
//   resizeListener();
// })
//
// onUnmounted(()=>{
//   window.removeEventListener('resize', resizeListener)
// })

onBeforeMount(()=>{
  requestStudentSubmitRecords()
  setTimeout(()=>{
    console.log(taskSubmitDic.value)
  }, 2000)
})

</script>

<template>
  <teacher-menu>
    <teacher-task-time-line v-for="(line, index) in progressItem" :progress-items="line.value" :group-name="groupNameRecord[index]"
                            :group-id="line.groupId" :key="line.groupId" @done-check="requestStudentSubmitRecords"/>
  </teacher-menu>
</template>

<style scoped>

</style>