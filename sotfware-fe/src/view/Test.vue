<script setup>

const testTableData = reactive(
    [
  { name: 'AAA', duty: '撰写文档1', uploadStatus: '未交'},
  { name: 'BBB', duty: '撰写文档2', uploadStatus: '已交'},
  { name: 'CCC', duty: '撰写文档3', uploadStatus: '未交'},
  { name: 'DDD', duty: '撰写文档4', uploadStatus: '未交'},
  { name: 'EEE', duty: '撰写文档5', uploadStatus: '已交'}
]
)
</script>

<template>
<!--  <div class="w-screen h-screen  border-slate-600 font-bold text-stone-50 text-center text-9xl bg-black">-->
<!--    TailWind Css-->
<!--  </div>-->
  <div style="height: 600px; width: 800px">
    <el-table :data="testTableData" empty-text="任务待分配">
      <el-table-column label="组员姓名" align="center" prop="name"/>
      <el-table-column label="组员任务" align="center" prop="duty"/>
      <el-table-column label="提交状态" align="center">
        <template #default="scope">
          <el-button :type="scope.row.uploadStatus==='未交'?'danger':'success'">{{scope.row.uploadStatus}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
<!--          对于不同人，别的人按钮无效-->
          <el-button text type="primary">提交任务</el-button>
        </template>
      </el-table-column>
    </el-table>
<!--    这一行有四个按钮，分别是负责人提交成果给老师(组员不可见)、负责人给同学评分(组员不可见)、同学给负责人评分(负责人不可见)、负责人开启评分按钮(组员不可见)-->
    <el-row style="margin-top: 2rem;">
      <el-col :span="4">
        <el-button type="warning" :disabled="true">提交任务</el-button>
      </el-col>
      <el-col :span="4">
        <el-button type="success">发起互评</el-button>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" :disabled="true">组员评价</el-button>
      </el-col>
      <el-col :span="4">
        <el-button :disabled="true">负责人评价</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-col){
  display: flex;
  justify-content: center;
}

</style>