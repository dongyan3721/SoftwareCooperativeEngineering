<!--
-- @description 学生小组页
-- @date 2024/4/21-22:48:44
-->

<script setup>

import StudentMenu from "@/components/student/student-menu.vue";
import StudentGroupDescription from "@/components/student/student-group-description.vue";
import UniverseSection from "@/components/universe-section.vue";
import Chatroom from "@/components/general/chatroom.vue";
</script>

<template>
  <student-menu>
    <universe-section title="小组概况"/>
    <student-group-description :edit-vis="true" group-avatar="http://47.120.49.22:8080/static/avatar/bbb.jpg"
                               group-introduction="大号的要我人多啊合肥索菲亚四风也大有的等会我熬夜的我i滚犊子来吃吧世俗一人一额土狗复古刷到过"
                               group-leader-avatar="http://47.120.49.22:8080/static/avatar/bbb.jpg"
                               group-leader-name="AAA莆田高仿K姐"
                               group-name="贼能C"/>
    <universe-section title="互通有无"/>
    <chatroom/>
  </student-menu>
</template>

<style scoped>

</style>