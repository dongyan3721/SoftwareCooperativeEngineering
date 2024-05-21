<!--
-- @author Santa Antilles
-- @description 通用表单内部一行文件上传
-- @date 2024/5/20-23:42:32
-->

<script setup>

import {ElMessage, genFileId} from "element-plus";

const props = defineProps({
  modelValue: String,
  universeUploadFunction: Function,
  initialImagesUrl: String,
  callbackVisitUrlAttribute: String
})

const emit = defineEmits(['update:modelValue'])


const upload = ref()
const handleExceed = (files) => {
  upload.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  upload.value.handleStart(file)
}

const handleManualUpload = (file)=>{
  props.universeUploadFunction(file).then(res=>{
    emit('update:modelValue', res[props.callbackVisitUrlAttribute])
  })
  ElMessage.success("上传成功~")
}
// 拒绝删除
const handleRejectRemove = ()=>{
  return false
}
const fileList = ref([{url: props.initialImagesUrl, name: 'test'}])

</script>

<template>
  <el-upload v-model:file-list="fileList" ref="upload" :limit="1"  :on-exceed="handleExceed" :before-remove="handleRejectRemove"
             @change="handleManualUpload" :auto-upload="false" accept=".png,.jpg,.jpeg">
    <template #trigger>
      <el-button type="primary">选择文件</el-button>
    </template>
    <template #tip>
      <div class="el-upload__tip text-red">
        限制上传一张.png/.jp(e)g格式的图片
      </div>
    </template>
  </el-upload>
</template>

<style scoped>

</style>