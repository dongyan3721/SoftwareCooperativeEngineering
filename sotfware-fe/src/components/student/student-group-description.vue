<!--
-- @author Santa Antilles
-- @description 学生小组信息展示组件
-- @date 2024/5/12-23:46:32
-->

<script setup>
import {Edit} from "@icon-park/vue-next";

const props = defineProps({
  groupName: String,
  groupAvatar: String,
  groupIntroduction: String,
  groupLeaderName: String,
  groupLeaderAvatar: String,
  editVis: Boolean
})

const form = reactive({
  groupName: null,
  groupAvatar: null,
  groupIntroduction: null
})

const formRef = ref()

// 控制提交详情对话窗口的可见性
const dialogVis = ref(false)
// 关闭对话窗口
const closeSubmitDialog = ()=>{
  dialogVis.value = false
}
// 打开对话窗口
const openSubmitDialog = ()=>{
  dialogVis.value = true
}

</script>

<template>
  <el-dialog v-model="dialogVis" @close="closeSubmitDialog">
    <template #header>
      <div class="flex items-center justify-start">
        <edit theme="outline" size="18" fill="#2a3f67" style="display: inline"/>
        <span class="ml-1 text-base">修改小组信息</span>
      </div>
    </template>

    <el-form v-model="form" :inline="false" ref="formRef">
      <el-form-item label="小组名称">
        <el-input clearable v-model="form.groupName"/>
      </el-form-item>
      <el-form-item label="小组头像">
<!--        upload-->
      </el-form-item>
      <el-form-item label="小组简介">
        <el-input type="textarea" clearable v-model="form.groupIntroduction"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">提交</el-button>
        <el-button type="warning">取消</el-button>
      </el-form-item>
    </el-form>
    <!--一个表格，有本组每个人上传的东西-->
  </el-dialog>
  <n-thing content-indented>
    <template #avatar>
<!--      <n-avatar>-->
<!--        <star theme="outline" size="18" fill="#2a3f67"/>-->
<!--      </n-avatar>-->
      <n-avatar :src="props.groupAvatar" :size="105"/>
    </template>
    <template #header>
      <span class="text-2xl">{{props.groupName}}</span>
    </template>
    <template #header-extra>
      <n-button size="medium" circle v-if="props.editVis" @click="openSubmitDialog">
        <template #icon>
          <edit theme="outline" size="18" fill="#2a3f67"/>
        </template>
      </n-button>
    </template>
    <p class="text-clip opacity-50">
      {{props.groupIntroduction}}
    </p>
<!--    <template #description>-->
<!--      描述-->
<!--    </template>-->
    <template #footer>
      <div class="flex flex-row items-center">
        <n-avatar :src="props.groupLeaderAvatar" circle size="small"/>
        <span class="ml-2">{{props.groupLeaderName}}</span>
      </div>
    </template>
<!--    <template #action>-->
<!--      <n-space>-->
<!--        <n-button size="small">-->
<!--          <template #icon>-->
<!--            <star theme="outline" size="18" fill="#2a3f67"/>-->
<!--          </template>-->
<!--          1 块钱-->
<!--        </n-button>-->
<!--        <n-button size="small">-->
<!--          <template #icon>-->
<!--            <star theme="outline" size="18" fill="#2a3f67"/>-->
<!--          </template>-->
<!--          10 块钱-->
<!--        </n-button>-->
<!--        <n-button size="small">-->
<!--          <template #icon>-->
<!--            <star theme="outline" size="18" fill="#2a3f67"/>-->
<!--          </template>-->
<!--          100 块钱-->
<!--        </n-button>-->
<!--      </n-space>-->
<!--    </template>-->
  </n-thing>
</template>

<style scoped>

</style>