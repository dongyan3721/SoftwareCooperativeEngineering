<!--
-- @author Santa Antilles
-- @description
-- @date 2024/5/21-12:03:07
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import UniverseSection from "@/components/universe-section.vue";

const groupPermission = ref(false)
const swLoading = ref(false);
const handleChangeGroupStatus = (val)=>{
  groupPermission.value = !val
  swLoading.value = true
  setTimeout(function (){
    groupPermission.value = val
    swLoading.value = false
  }, 2000)
  if(val){
    // 学生可以看到组队菜单
  }else{
    // 关闭学生的组队菜单
  }
}
</script>

<template>
  <teacher-menu>
    <universe-section title="组队控制"/>
    <el-switch size="large" v-model="groupPermission" :loading="swLoading" inactive-text="！闭关，队组"
               active-text="组队，启动！" @change="handleChangeGroupStatus"/>
    <universe-section title="申请审批"/>

  </teacher-menu>
</template>

<style scoped>

</style>