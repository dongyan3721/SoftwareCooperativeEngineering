<!--
-- @description 老师首页，进来之后选自己要进入的课（二级目录）
-- @date 2024/4/22-19:00:54
-->

<script setup>
import OtherComponent from '../../components/teacher/teacher-menu.vue';
import router from "@/router/index.js";
import {ref} from 'vue';
import {ListAllTeacherCourses} from "@/web-api/teacher/teacherCourseSelection.js";
import {useTeacherClassStore} from "@/store";

const tabPosition = ref('left')
const courses = ref([]);
const {setClassId, setClass} = useTeacherClassStore()

const handleCourseClick =(courseUrl, clazz) => {
    router.push('/teacher-student-import');
    setClassId(courseUrl)
    setClass(clazz)
};

onMounted(async () => {
  try {
    const response = await ListAllTeacherCourses();
    courses.value = response.data;
  } catch (error) {
    console.error('Error fetching courses:', error);
  }
});
</script>

<template>
  <el-row>
    <el-col :span="3"/>
    <el-col :span="18">
    <div class="course-container">
      <div class="course-inner-container">
        <div class="course-inner1-container">
          <div class="course-inner1-container-tap">
            <el-tabs :tab-position="tabPosition" style="height: fit-content; display: flex; align-items: center;">
              <el-tab-pane style="font-weight: lighter; font-size: medium; color: #757474; margin: 0 auto;">本校课程
              </el-tab-pane>
            </el-tabs>
          </div>
          <el-row>
            <el-col :span="6" v-for="(course, index) in courses" :key="index">
              <div class="grid-row">
                <div class="grid-col">
                  <div class="course clearfix cursor-pointer" @click="handleCourseClick(course.classId, course)">
                    <div class="course-cover">
                      <a target="_blank" style="outline: none;">
                        <img :src="course.classPicture" style="width: 250px; height: 160px; ">
                      </a>
                    </div>
                    <div class="course-info">
                      <h3 class="inlineBlock">
                        <a class="color1" :href="course.courseUrl" target="_blank">
                          <p class="course-name" :title="course.courseName">{{ course.courseName }}</p>
                        </a>
                      </h3>
                      <p class="info">{{ course.teacherName }}</p>
                      <p class="info">{{ course.classTerm }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    </el-col>
    <el-col :span="3"/>
  </el-row>
</template>

<style scoped>
.course-container {
  padding: 5px 5px 5px 5px; /* 设置内部间距为20px */
  background-color: #f9f9f9;
  box-shadow: 0 0 0 20px #f9f9f9; /* 设置外部红色阴影，宽度为20px */

}

.course-inner-container {
  padding: 20px; /* 设置内部间距为20px */
  background-color: #ffffff;

}

.course-inner1-container {
  background-color: #eae9e9;
}

.course-inner1-container-tap {
  padding-top: 10px;
  margin-top: 20px;
  background-color: #eae9e9;
  display: flex;
  justify-content: space-between;
}

.course-inner-container-button .el-button + .el-button {
  margin-left: 40px; /* 使用左边距将第二个按钮向右推移 */
}

.grid-row {
  display: flex;
  justify-content: space-between;
  padding: 10px 10px 0px 10px;
}

.grid-col {
  flex: 1;
  padding: 10px;
}

.course-name {
  margin-top: 10px;
  font-weight: bold;
  font-size: small;
}

.info {
  font-weight: lighter;
  font-size: x-small;
  color: #999999;
  margin-top: 5px;
}

</style>





