<!--
-- @description 学生小组页
-- @date 2024/4/21-22:48:44
-->

<script setup>

import StudentMenu from "@/components/student/student-menu.vue";
import StudentGroupDescription from "@/components/student/student-group-description.vue";
import UniverseSection from "@/components/universe-section.vue";
import Chatroom from "@/components/general/chatroom.vue";
// 我的小组进来之前先请求一波后端看看自己最新的小组情况
import {useUserStore, useStudentGroupStore, useStudentClassStore} from "@/store/index.js";
import {getGroupByGroupId, groupGetClassByClassId, groupGetStudentById} from "@/web-api/student/studentGroup.js";
import {sys_class_phase} from "@/configuration/dictionary.js";
const userStore = useUserStore()
const studentGroupStore = useStudentGroupStore()
const studentClassStore = useStudentClassStore()

const userHasGroup = ref(false)
const pageGroupInfo = ref({
  groupId: null,
  groupName: null,
  classId: null,
  groupAvatar: null,
  groupIntroduction: null,
  groupLeaderId: null,
  groupLeaderName: null,
  groupLeaderAvatar: null
})

const requestGroupDetail = ()=>{
  getGroupByGroupId(userStore.studentGroup).then(res=>{
    pageGroupInfo.value = res.data
  })
}

onBeforeMount(()=>{
  // 优先更新一遍当前课程情况
  groupGetClassByClassId(userStore.studentClass).then(res=>{
    studentClassStore.setStudentClass(res.data)
  })

  // 首先看有没有组
  groupGetStudentById(userStore.userId).then(res=>{
    userStore.studentGroup = res.data.studentGroup
    // 组号不是0，有组
    if(res.data.studentGroup) {
      userHasGroup.value = true
      // 其次看是不是组长
      getGroupByGroupId(userStore.studentGroup).then(res=>{
        // 当前本人是不是组长
        studentGroupStore.setIfLeader(res.data.groupLeaderId === userStore.userId)
        studentGroupStore.setStudentGroup(res.data)
        pageGroupInfo.value = res.data
      })
    }

  })
})
</script>

<template>
  <student-menu>
    <universe-section title="小组概况"/>
    <student-group-description :edit-vis="studentGroupStore.isLeader" :group-avatar="pageGroupInfo.groupAvatar"
                               :group-introduction="pageGroupInfo.groupIntroduction" @update="requestGroupDetail"
                               :group-leader-avatar="pageGroupInfo.groupLeaderAvatar"
                               :group-leader-name="pageGroupInfo.groupLeaderName" :show-audit-add-in-team="studentGroupStore.isLeader&&studentClassStore.clazz.phase===sys_class_phase.TEAMING"
                               :group-name="pageGroupInfo.groupName" v-show="userHasGroup"/>
    <universe-section title="互通有无"/>
    <chatroom/>
  </student-menu>
</template>

<style scoped>

</style>