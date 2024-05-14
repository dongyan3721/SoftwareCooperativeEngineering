<!--
-- @author Santa Antilles
-- @description 学生组队界面
-- @date 2024/5/14-19:25:11
-->

<script setup>
import StudentMenu from "@/components/student/student-menu.vue";
import {useUserStore, useStudentClassStore} from "@/store";
import {PeoplesTwo} from "@icon-park/vue-next";
import UniverseFormCompressedFileUpload from "@/components/universe-form-compressed-file-upload.vue";
import upload from "@/web-api/upload.js";

const userStore = useUserStore();
const studentClassStore = useStudentClassStore();
const existedGroups = ref([])
const applyForNewGroupVis = ref(false)
const openApplyForNewTeamDialog = ()=>{
  applyForNewGroupVis.value = true
}
const applyForm = reactive({
  groupName: null,
  classId: studentClassStore.classId,
  groupAvatar: null,
  groupIntroduction: null,
  groupLeaderId: userStore.userId,
  groupLeaderName: userStore.userName,
  groupLeaderAvatar: userStore.avatar
})
const applyFormRef = ref()
const resetApplyForm = (fRef)=>{
  if(!fRef) return
  fRef.resetFields();
  applyForm.classId = studentClassStore.classId
  applyForm.groupLeaderId = userStore.userId
  applyForm.groupLeaderName = userStore.userName
  applyForm.groupLeaderAvatar = userStore.avatar
}
const handOnApply = ()=>{

}
</script>

<template>
  <el-dialog width="600px" v-model="applyForNewGroupVis" @close="resetApplyForm(applyFormRef)">
    <template #header>
      <div class="flex flex-row items-center">
        <peoples-two theme="outline" size="16" fill="#212529"/>
        <span class="inline-block ml-1">创建小组</span>
      </div>
    </template>
    <el-form :model="applyForm" ref="applyFormRef" size="large">
      <el-form-item label="小组名称">
        <el-input v-model="applyForm.groupName" clearable/>
      </el-form-item>
      <el-form-item label="小组头像">
        <universe-form-compressed-file-upload :initial-picture="''" :universe-upload-function="upload"
                                              :callback-visit-url-attribute="'msg'" v-model="applyForm.groupAvatar"/>
      </el-form-item>
      <el-form-item label="小组介绍">
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
        <span class="text-2xl font-semibold block my-1">申请组建队伍</span>
        <n-button class="my-1" type="error" size="large" @click="openApplyForNewTeamDialog">点我新建</n-button>
      </div>

      <div class="w-100 h-single bg-black opacity-50 my-1"/>

<!--      加入已有小组-->
      <div class="w-100 h-2/3 mt-3">
        <span class="text-2xl font-semibold">加入队伍</span>
        <n-card v-for="group in existedGroups" :key="group.groupId">

        </n-card>
      </div>
    </div>
  </student-menu>
</template>

<style scoped>

</style>