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
import {
  delegateGroupUser,
  getGroupByGroupId,
  groupGetClassByClassId,
  groupGetStudentById, queryGroupMembers, studentBreakDownGroup
} from "@/web-api/student/studentGroup.js";
import {sys_class_phase} from "@/configuration/dictionary.js";
import {Delete, Bye, PreviewOpen, Delivery} from "@icon-park/vue-next";
import {dictDataExtractWidthReflection} from "@/util/dictDataExtarct.js";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";
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

const tearDown = ()=>{
  ElMessageBox.confirm(
      '确定要解散小组吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
        studentBreakDownGroup(userStore.studentGroup).then(()=>{
          ElMessage.success('解散成功')
          router.push('/student-group-apply')
        })
  })
}

const goAway = ()=>{
  ElMessageBox.confirm(
      '确定要退出小组吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
    studentBreakDownGroup(userStore.studentGroup).then(()=>{
      ElMessage.success('已退出')
      router.push('/student-group-apply')
    })
  })
}

const studentT = reactive({
  studentId: null,
  role: null
})
const openDelegateDialog = (studentId)=>{
  studentT.studentId = studentId
  visDelegateDialog.value = true
}
const visDelegateDialog = ref(false)
// 划分成员角色
const doAssign = ()=>{
  delegateGroupUser(studentT.studentId, studentT.role).then(res=>{
    ElMessage.success('角色分配成功！')
    studentT.role = null
    studentT.studentId = null
    queryGroupMembers(userStore.studentGroup).then(res=>{
      tbDataViewAllGroupMembers.value = res.data.map(student=>{
        student.studentRole = {
          label: sys_dict_student_role_r.value.reflect[student.studentRole],
          value: student.studentRole
        }
        return student
      })
    })
  })
  visDelegateDialog.value = false
}

const sys_dict_student_role_r = ref({data: null, reflect: null})
dictDataExtractWidthReflection('sys_student_role').then(res=>{
  sys_dict_student_role_r.value = res
})

const resolveViewGroupDetail = ()=>{
  visAllGroupMembers.value = true
  queryGroupMembers(userStore.studentGroup).then(res=>{
    tbDataViewAllGroupMembers.value = res.data.map(student=>{
      student.studentRole = {
        label: sys_dict_student_role_r.value.reflect[student.studentRole],
        value: student.studentRole
      }
      return student
    })
  })
}
const tbDataViewAllGroupMembers = ref()
const visAllGroupMembers = ref(false)

</script>

<template>

  <el-dialog v-model="visDelegateDialog" title="角色分配">
    <el-select v-model="studentT.role" placeholder="请选择组员角色">
      <el-option v-for="option in sys_dict_student_role_r.data" :label="option.label" :value="option.value"
                 :key="option.value"/>
    </el-select>
    <template #footer>
      <div class="h-100 w-100 flex justify-end items-center">
        <el-button type="primary" @click="doAssign">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="visAllGroupMembers">
    <template #header>
      <div class="h-100 w-100 flex items-center">
        <preview-open theme="outline" size="16" fill="#2a3f67"/>
        <span class="ml-1 inline-block">查看小组成员</span>
      </div>
    </template>
    <el-table :data="tbDataViewAllGroupMembers">
      <el-table-column label="学生姓名" prop="studentName" align="center"/>
      <el-table-column label="头像" align="center">
        <template #default="scope">
          <n-avatar :size="32" circle :src="scope.row.avatar"/>
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="studentRole.label" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" text @click="openDelegateDialog(scope.row.studentId)">分配角色</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <student-menu>
    <universe-section title="小组概况"/>
    <student-group-description :edit-vis="studentGroupStore.isLeader" :group-avatar="pageGroupInfo.groupAvatar"
                               :group-introduction="pageGroupInfo.groupIntroduction" @modified="requestGroupDetail"
                               :group-leader-avatar="pageGroupInfo.groupLeaderAvatar"
                               :group-leader-name="pageGroupInfo.groupLeaderName"
                               :show-audit-add-in-team="studentGroupStore.isLeader"
                               :group-name="pageGroupInfo.groupName" v-if="userHasGroup">

<!--      组队阶段，允许组长修改组员角色-->
      <n-button type="primary" size="medium" @click="resolveViewGroupDetail" class="ml-2"
                v-if="studentGroupStore.isLeader&&studentClassStore.clazz.phase===sys_class_phase.TEAMING">
        <template #icon>
          <delivery theme="outline" size=16 fill="#2a3f67"/>
        </template>
        分配角色
      </n-button>
<!--      组队阶段，允许组长解散队伍-->
      <n-button type="error" size="medium" @click="tearDown" class="ml-2"
                v-if="studentGroupStore.isLeader&&studentClassStore.clazz.phase===sys_class_phase.TEAMING">
        <template #icon>
          <delete theme="outline" size=16 fill="#2a3f67"/>
        </template>
        解散小组
      </n-button>
<!--      组队阶段，允许小组成员离开队伍-->
      <n-button type="warning" size="medium" @click="goAway" class="ml-2"
                v-if="!studentGroupStore.isLeader&&studentClassStore.clazz.phase===sys_class_phase.TEAMING">
        <template #icon>
          <bye theme="outline" size="16" fill="#2a3f67"/>
        </template>
        离开小组
      </n-button>
    </student-group-description>
<!--    当前人是队长才允许审批组员加入申请-->
    <universe-section title="互通有无"/>
    <chatroom/>
  </student-menu>
</template>

<style scoped>

</style>