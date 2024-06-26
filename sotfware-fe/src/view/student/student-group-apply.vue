<!--
-- @author Santa Antilles
-- @description 学生组队界面
-- @date 2024/5/14-19:25:11
-->

<script setup>
import StudentMenu from "@/components/student/student-menu.vue";
import {useUserStore} from "@/store";
import {PeoplesTwo, PreviewOpen} from "@icon-park/vue-next";
import UniverseFormCompressedFileUpload from "@/components/universe-form-compressed-file-upload.vue";
import upload from "@/web-api/upload.js";
import {
  applyToBeGroupMember, applyToBeLeader,
  checkStudentHasPermissionToCreateTeam,
  queryExistingStudentGroups
} from "@/web-api/student/studentGroupApply.js";
import {downToGetInt, generateSequence, upToGetInt} from "@/util/dongyan.js";
import UniverseSection from "@/components/universe-section.vue";
import {ElMessage} from "element-plus";
import {queryGroupMembers} from "@/web-api/student/studentGroup.js";
import {dictDataExtractWidthReflection} from "@/util/dictDataExtarct.js";

const userStore = useUserStore();
const applyForNewGroupVis = ref(false)
const openApplyForNewTeamDialog = async ()=>{
  const permission = await checkStudentHasPermissionToCreateTeam(userStore.userId, userStore.studentClass)
  if(permission) applyForNewGroupVis.value = true
  else ElMessage.error("您已经归属于一个小组或有小组申请在审核中，不满足申请条件")
}
const applyForm = reactive({
  groupName: null,
  classId: userStore.studentClass,
  groupAvatar: null,
  groupIntroduction: null,
  groupLeaderId: userStore.userId,
  groupLeaderName: userStore.userName,
  groupLeaderAvatar: userStore.avatar
})
const applyFormRef = ref()
const resetApplyForm = (fRef)=>{
  applyForNewGroupVis.value = false
  if(!fRef) return
  fRef.resetFields();
  applyForm.classId = userStore.studentClass;
  applyForm.groupLeaderId = userStore.userId;
  applyForm.groupLeaderName = userStore.userName;
  applyForm.groupLeaderAvatar = userStore.avatar;
}
const handOnApply = ()=>{
  applyToBeLeader(applyForm).then(res=>{
    ElMessage.success('成功递交申请，等待教师审批中...')
    applyForNewGroupVis.value = false
    requestExistingGroups()
  }).catch(err=>{
    ElMessage.error('创建小组失败！');
    applyForNewGroupVis.value = false
  })
}

const existingGroups = ref([])
const exitGroupsRowCount = computed(()=>{
  return generateSequence(0, upToGetInt(existingGroups.value.length, existGroupCol))
})
// 每行4列
const existGroupCol = 4

const queryParams = {
  page: 1,
  pageSize: 16
}
function requestExistingGroups(){
  queryExistingStudentGroups(userStore.studentClass, queryParams).then(res=>{
    existingGroups.value = res.data
  })
}

const resolveJoinGroup = (groupId, index)=>{
  applyToBeGroupMember(groupId).then(res=>{
    ElMessage.success('申请成功~')
    existingGroups.value[index].hasAppeal=true
  }).catch(err=>{
    ElMessage.error("不满足申请加入小组条件！")
  })
}

const sys_dict_student_role_r = ref({data: null, reflect: null})
dictDataExtractWidthReflection('sys_student_role').then(res=>{
  sys_dict_student_role_r.value = res
})

const resolveViewGroupDetail = (groupId)=>{
  visAllGroupMembers.value = true
  loadingAllGroupMembers.value = true
  queryGroupMembers(groupId).then(res=>{
    tbDataViewAllGroupMembers.value = res.data.map(student=>{
      student.studentRole = {
        label: sys_dict_student_role_r.value.reflect[student.studentRole],
        value: student.studentRole
      }
      return student
    })
    loadingAllGroupMembers.value = false
  })
}
const tbDataViewAllGroupMembers = ref()
const visAllGroupMembers = ref(false)
const loadingAllGroupMembers = ref(false)
// 新建点击按钮的时候查一下这个学生有没有正在申请的小组或者已经有组了

onBeforeMount(function (){
  requestExistingGroups();
})


</script>

<template>

  <el-dialog v-model="visAllGroupMembers">
    <template #header>
      <div class="h-100 w-100 flex items-center">
        <preview-open theme="outline" size="16" fill="#2a3f67"/>
        <span class="ml-1 inline-block">查看小组成员</span>
      </div>
    </template>
    <el-table v-loading="loadingAllGroupMembers" :data="tbDataViewAllGroupMembers">
      <el-table-column label="学生姓名" prop="studentName" align="center"/>
      <el-table-column label="头像" align="center">
        <template #default="scope">
          <n-avatar :size="32" circle :src="scope.row.avatar"/>
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="studentRole.label" align="center"/>
    </el-table>
  </el-dialog>

  <el-dialog width="600px" v-model="applyForNewGroupVis" @close="resetApplyForm(applyFormRef)">
    <template #header>
      <div class="flex flex-row items-center">
        <peoples-two theme="outline" size="16" fill="#212529"/>
        <span class="inline-block ml-1">创建小组</span>
      </div>
    </template>
    <!--    坑! 不指定prop无法reset表单-->
    <el-form :model="applyForm" ref="applyFormRef" size="large">
      <el-form-item label="小组名称" prop="groupName">
        <el-input v-model="applyForm.groupName" clearable/>
      </el-form-item>
      <el-form-item label="小组头像" prop="groupAvatar">
        <universe-form-compressed-file-upload :initial-picture="''" :universe-upload-function="upload"
                                              :callback-visit-url-attribute="'msg'" v-model="applyForm.groupAvatar"/>
      </el-form-item>
      <el-form-item label="小组介绍" prop="groupIntroduction">
        <el-input type="textarea" clearable v-model="applyForm.groupIntroduction"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handOnApply">提交申请</el-button>
        <el-button type="warning" @click="resetApplyForm(applyFormRef)">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <student-menu>
    <div class="w-100 h-100">
<!--      建立新的小组-->
      <div class="w-100">
        <universe-section title="申请创建小组"/>
        <n-button class="my-1" type="error" size="large" @click="openApplyForNewTeamDialog">点我新建</n-button>
      </div>

<!--      <div class="w-100 h-single bg-black opacity-50 my-1"/>-->

<!--      加入已有小组-->
      <div class="w-100 h-2/3 mt-3">
        <universe-section title="加入队伍"  />
        <el-row v-for="count in exitGroupsRowCount" :key="count" :gutter="20" class="my-4">
          <el-col :span="downToGetInt(24, existGroupCol)" :key="i"
              v-for="i in generateSequence(count*4, (count+1)*4>existingGroups.length?existingGroups.length:(count+1)*4)">
            <n-card hoverable class="rounded-xl">
              <div class="w-100 h-100">
                <div class="flex flex-row items-center">
                  <n-avatar :src="existingGroups[i].groupAvatar" :size="80"/>
                  <div class="flex flex-col ml-3 justify-between">
                    <span class="my-1 block text-xl alibaba-font">{{existingGroups[i].groupName}}</span>
                    <div class="flex flex-row">
                      <n-avatar :src="existingGroups[i].groupLeaderAvatar" round size="small"/>
                      <span class="text-gray-400 my-1 ml-1 alibaba-font">{{existingGroups[i].groupLeaderName}}</span>
                    </div>
                  </div>
                </div>
              </div>
              <template #action>
                <el-button size="small" type="warning" @click="resolveViewGroupDetail(existingGroups[i].groupId)">让我看看！</el-button>
                <el-button size="small" type="primary" @click="resolveJoinGroup(existingGroups[i].groupId, i)" :disabled="existingGroups[i].hasAppeal">我汤姆来辣</el-button>
              </template>
            </n-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </student-menu>
</template>

<style scoped>

</style>