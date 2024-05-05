<!--
-- @author Santa Antilles
-- @description 进度条组件
-- @date 2024/5/5-10:50:02
-->

<script setup>
import {changeColor} from "seemly";
import {LoadingOne, HourglassFull, CheckOne, Caution, Seal} from "@icon-park/vue-next";

const rate = computed(()=>{
  let rec = 0;
  props.progressItems.forEach(t=>{
    if(t.taskFinishStatus==='2') ++rec
  })
  return rec/props.progressItems.length*100
})


const props = defineProps({
  progressItems: Array
  /**
   * {
   *   deadline: 截止时间,
   *   title: 任务名，
   *   taskId：任务id，
   *   taskFinishStatus: 任务完成状态
   *   完成或正在进行或未完成
   *   只要按照group_id查询b_student_task_submit，把本组所有完成过的任务都查出来，看最后一个项目，如果最后一个项目的
   *   提交状态全部是2，那么认为全部结束；如果有3，则是任务被打回状态，有1是等待批阅，有0是没有完成
   *   排序先于这个任务的都是完成，晚于这个任务的都是未完成
   * }
   */
  /**
   * 给出本课程的任务列表，再给出分界处任务的详细信息和任务的状态即可，前端做清洗
   */
  // 最后加上固定的开始结束
  // 任务开始时，系统自动创建小组b_student_task_submit模板，任务让阶段负责人去分配
})


const handleViewSubmitDetail = (taskId) =>{

}

</script>

<template>
  <el-row>
    <el-col :span="4"/>
    <el-col :span="16">
      <div class="my-2">
        <n-timeline horizontal size="large" :icon-size="24">
          <n-timeline-item v-for="t in props.progressItems" type="success" :title="t.title" :time="t.deadline" :key="t.taskId">
<!--            提交详情在完成提交的任务内出现-->
            <el-button text type="primary" @click="handleViewSubmitDetail" v-if="t.taskFinishStatus==='2'">提交详情</el-button>
            <el-button text v-else :disabled="t.taskFinishStatus==='1'||t.taskFinishStatus===''">前往提交</el-button>
            <template #icon>
              <check-one theme="outline" size="24" fill="#46b300" v-if="t.taskFinishStatus==='2'"/>
              <loading-one theme="outline" size="24" fill="#003dff" v-if="t.taskFinishStatus==='0'"/>
              <seal theme="outline" size="24" fill="#003dff" v-if="t.taskFinishStatus==='1'"/>
              <caution theme="outline" size="24" fill="#dd524d" v-if="t.taskFinishStatus==='3'"/>
              <hourglass-full theme="outline" size="24" fill="#606266" v-if="t.taskFinishStatus===''"/>
            </template>
          </n-timeline-item>
<!--          <n-timeline-item type="success" title="需求分析" time="2024-05-04 20:46">-->
<!--            <template #icon>-->
<!--              <check-one theme="outline" size="24" fill="#46b300"/>-->
<!--            </template>-->
<!--          </n-timeline-item>-->
<!--          <n-timeline-item type="success" title="进度计划" time="2024-05-10 20:46">-->
<!--            <template #icon>-->
<!--              <loading-one theme="outline" size="24" fill="#003dff"/>-->
<!--            </template>-->
<!--          </n-timeline-item>-->
<!--          <n-timeline-item type="success" title="项目结束" time="2024-05-20 20:46">-->
<!--            <template #icon>-->
<!--              <hourglass-full theme="outline" size="24" fill="#606266"/>-->
<!--            </template>-->
<!--          </n-timeline-item>-->

        </n-timeline>
        <div class="mt-2">
          <n-progress
              type="line"
              indicator-placement="inside"
              :percentage="rate"
              color="#dd524d"
              :rail-color="changeColor('#dd524d', { alpha: 0.2 })"
              :height="24"
          />
        </div>
      </div>
    </el-col>
    <el-col :span="4"/>
  </el-row>
</template>

<style scoped>

</style>