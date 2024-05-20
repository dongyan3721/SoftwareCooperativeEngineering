<script setup>
import ProgressBar from "@/components/general/progress-bar.vue";
import {sys_task_submit_status} from "@/configuration/dictionary.js";
import StudentGroupDescription from "@/components/student/student-group-description.vue";
import {stringToPngBase64} from "@/util/cv.js";

const testData = ref([
  {
    title: '需求分析',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd2ds21',
    deadline: '2024-05-01 18:00:00'
  },
  {
    title: '进度计划',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd03ds21',
    deadline: '2024-05-05 18:00:00'
  },
  {
    title: '质量计划',
    taskFinishStatus: sys_task_submit_status.PASS,
    taskId: 'ad3dd2ds21892',
    deadline: '2024-05-10 18:00:00'
  },
  {
    title: '开发实现',
    taskFinishStatus: sys_task_submit_status.WAIT_FOR_STUDENT_HAND_ON,
    taskId: 'ad3dd2ds63s21',
    deadline: '2024-05-15 18:00:00'
  },
  {
    title: '测试验收',
    taskFinishStatus: sys_task_submit_status.UNREACHED,
    taskId: 'ad3dd2df0us21',
    deadline: '2024-05-20 18:00:00'
  }
])

onMounted(()=>{
  const inputStr = '董岩';
  const base64Image = stringToPngBase64(inputStr);
  console.log(base64Image);

  // 可选：在页面上显示图像
  const img = new Image();
  img.src = base64Image;
  document.body.appendChild(img);
})

</script>

<template>
  <progress-bar :progress-items="testData"/>
  <br/>
  <el-row>
    <el-col :span="4"/>
    <el-col :span="16">
      <student-group-description :edit-vis="true" group-avatar="http://47.120.49.22:8080/static/avatar/bbb.jpg"
                                 group-introduction="大号的要我人多啊合肥索菲亚四风也大有的等会我熬夜的我i滚犊子来吃吧世俗一人一额土狗复古刷到过"
                                 group-leader-avatar="http://47.120.49.22:8080/static/avatar/bbb.jpg"
                                 group-leader-name="AAA莆田高仿K姐"
                                 group-name="贼能C"/>
    </el-col>
    <el-col :span="4"/>
  </el-row>
</template>

<style scoped lang="scss">
:deep(.el-col){
  display: flex;
  justify-content: center;
}

</style>