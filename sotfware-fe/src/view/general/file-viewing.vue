<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import {useRoute} from "vue-router";
import {manufacturePreviewUrl} from "@/util/filePreviewUrl.js";
import StudentMenu from "@/components/student/student-menu.vue";

const route = useRoute()
const classChapter = route.query

</script>

<template>
  <teacher-menu v-if="classChapter.userType === 'teacher'">
    <el-container direction="horizontal">
      <el-button type="primary" @click="this.$router.back()">返回</el-button>
      <el-header>{{ classChapter.header }}</el-header>
    </el-container>

    <div class="data-container">
<!--      <iframe id=“frame” :src="manufacturePreviewUrl(classChapter.link)" style="width: 100%;border: 0px;margin:0;display: none"></iframe>-->
      <iframe allow="fullscreen" :src="manufacturePreviewUrl(classChapter.link)" class="w-full aspect-[9/4]"
              frameborder="no"></iframe>
    </div>
  </teacher-menu>
  <student-menu v-else-if="classChapter.userType === 'student'">
    <el-container direction="horizontal">
      <el-button type="primary" @click="this.$router.back()">返回</el-button>
      <el-header>{{ classChapter.header }}</el-header>
    </el-container>

    <div class="data-container">
      <!--      <iframe id=“frame” :src="manufacturePreviewUrl(classChapter.link)" style="width: 100%;border: 0px;margin:0;display: none"></iframe>-->
      <iframe allow="fullscreen" :src="manufacturePreviewUrl(classChapter.link)" class="w-full aspect-[9/4]"
              frameborder="no"></iframe>
    </div>
  </student-menu>
</template>

<style scoped>
.data-container {
  padding: 20px;
  background-color: #f9f9f9;
}
</style>