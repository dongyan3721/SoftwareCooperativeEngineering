<!--
-- @author Santa Antilles
-- @description 教师导入学生
-- @date 2024/5/20-11:44:54
-->

<script setup>

import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import UniverseSection from "@/components/universe-section.vue";
import {Plus, Upload, Search, Refresh} from "@element-plus/icons-vue";
import {useTeacherClassStore} from "@/store/index.js";
import {ElMessage, genFileId} from "element-plus";
import upload from "@/web-api/upload.js";
import {generalValidatorJudgeIfEmpty} from "@/util/common.js";
import {handleAddStudent, handleUpdateStudent, handleDeleteStudent, queryStudents} from "@/web-api/teacher/teacherImportStudent.js";
import {stringToPngBase64} from "@/util/cv.js";
import {base64ToBlob} from "@/util/dongyan.js";
const teacherClassStore = useTeacherClassStore();
teacherClassStore.setClassId(142543296)
const pageClass = teacherClassStore.classId;
const classStudents = ref(
//     [
//   {studentId: 114514191, studentName: '李田所', avatar: 'https://software-cooperative-engineering.oss-cn-hangzhou.aliyuncs.com/9bdef4d9d814b3720059b533c198055d.jpg',
//   studentRole: '开发经理', studentGroup: 'AAA'}
// ]
)

onBeforeMount(function (){
  querySatisfiedStudents(queryParams)
})

const queryParams = reactive({
  studentId: null,
  studentName: null,
  classId: pageClass,
  pageNum: 1,
  pageSize: 20
})
const queryFormRef = ref()
function resetQuery(){
  if(!queryFormRef.value) return
  queryFormRef.value.resetFields()
  queryParams.studentId = null
  queryParams.studentName = null
  queryParams.classId = pageClass
  queryParams.pageNum = 1
  queryParams.pageSize = 20
  querySatisfiedStudents(queryParams)
}

function querySatisfiedStudents(params){
  queryStudents(params).then(res=>{
    classStudents.value = res.rows
  })
}

const form = reactive({
  studentId: null,
  studentName: null,
  studentClass: pageClass,
  avatar: null
})

const formRef = ref()

function resetForm(){
  if(!formRef.value) return
  formRef.value.resetFields();
  form.studentClass = teacherClassStore.classId
}

const modifyDialogVis = ref(false)
// 单个新增学生-打开窗口
const handleOpenAddDialog = ()=>{
  resetForm()
  form.studentId = null
  form.avatar = null
  form.studentClass = pageClass
  form.studentName = null
  useDefaultAvatar.value = true
  fileList.value = []
  modifyDialogVis.value = true
}
const deleteStudent = id=>{
  // 确认一下
  // handleDeleteStudent(id).then(res=>{
  //
  // })
}
// 打开批量上传窗口
const handleOpenBatchAddDialog = ()=>{

}
// 单个修改学生-打开窗口
const handleOpenUpdateDialog = (student)=>{
  resetForm()
  console.log(form)
  form.studentId = student.studentId
  form.avatar = student.avatar
  form.studentName = student.studentName
  form.studentClass = pageClass
  fileList.value = [{name: 'aaa', url: student.avatar}]
  modifyDialogVis.value = true
}

const useDefaultAvatar = ref(true)

const avatarUpload = ref()
const handleExceed = (files)=>{
  avatarUpload.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  avatarUpload.value.handleStart(file)
}
const handleManualUpload = (file)=>{
  console.log(file)
  upload(file.raw).then(res=>{
    form.avatar = res.msg
  })
  ElMessage.success("上传成功~")
}
const handleSubmitForm = ()=>{
  // 验证表单、根据studentId情况提交
  console.log(form)
  formRef.value.validate(valid=>{
    if(valid){
      if(!useDefaultAvatar.value&&!form.avatar){
        ElMessage.error('请为学生选择头像')
        return
      }
      if(form.studentId){
        // handleUpdateStudent(form).then()
      }else{
        if(useDefaultAvatar.value){
          upload(base64ToBlob(stringToPngBase64(form.studentName))).then(res=>{
            form.avatar = res.msg
            handleAddStudent(form).then(res=>{
              ElMessage.success('添加成功！')
              // querySatisfiedStudents(queryParams);
            })
          })
        }else{
          handleAddStudent(form).then(res=>{
            ElMessage.success('添加成功！')
            // querySatisfiedStudents(queryParams);
          })
        }
      }
    }
    modifyDialogVis.value = false
    resetForm()
  })


}
const cancelSubmit = ()=>{
  resetForm();
  modifyDialogVis.value = false
}
const fileList = ref()

const rules = {
  studentName: [{validator: generalValidatorJudgeIfEmpty('学生姓名'), trigger: 'blur'}]
}

</script>

<template>

  <el-dialog append-to-body width="400px" v-model="modifyDialogVis">
    <template #header>
      <div class="w-100 h-100 flex flex-row items-center">
        <el-icon size="16"><plus/></el-icon>
        <span class="inline-block ml-1">{{form.studentId?'修改':'添加'}}学生</span>
      </div>
    </template>
    <el-form :model="form" ref="formRef" :inline="false" :rules="rules">
      <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="form.studentName" clearable/>
      </el-form-item>
      <el-form-item label="学生头像" prop="avatar">
        <el-switch v-model="useDefaultAvatar" v-if="!form.studentId" active-text="使用默认头像"/>
        <el-upload v-if="!useDefaultAvatar||form.studentId" v-model:file-list="fileList" ref="avatarUpload" :limit="1"
                   :on-exceed="handleExceed" :before-remove="()=>false" @change="handleManualUpload"
                   :auto-upload="false" accept=".png,.jpg,.jpeg">
          <template #trigger>
            <el-button type="primary">选择文件</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip text-red-500">
              限制上传一张.png/.jp(e)g格式的图片
            </div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmitForm">确定</el-button>
        <el-button type="warning" @click="cancelSubmit">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <teacher-menu>
    <universe-section title="课程名单"/>

    <el-form :model="queryParams" ref="queryFormRef" :inline="true">
      <el-form-item label="学生学号">
        <el-input v-model="queryParams.studentId" clearable/>
      </el-form-item>
      <el-form-item label="学生姓名">
        <el-input v-model="queryParams.studentName" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="querySatisfiedStudents">
          <template #icon>
            <el-icon><Search/></el-icon>
          </template>
        </el-button>
        <el-button type="warning" @click="resetQuery">
          <template #icon>
            <el-icon><refresh/></el-icon>
          </template>
        </el-button>
      </el-form-item>
    </el-form>

    <el-button type="warning" @click="handleOpenAddDialog">导入</el-button>
    <el-button type="danger" @click="handleOpenBatchAddDialog">
      <template #icon>
        <el-icon><Upload/></el-icon>
      </template>
      批量导入
    </el-button>
    <el-table :data="classStudents" ref="tableData">
      <template #empty>
        当前这门课还没有学生...
      </template>
      <el-table-column label="学号" prop="studentId" align="center"/>
      <el-table-column label="姓名" prop="studentName" align="center"/>
      <el-table-column label="头像" prop="studentAvatar" align="center">
<!--        二合一概要、类设计、控制设计、页面跳转设计-->
        <template #default="inline">
          <n-avatar circle :size="36" :src="inline.row.avatar">
            <template #fallback>
              过于英姿，不便展示
            </template>
          </n-avatar>
        </template>
      </el-table-column>
      <el-table-column label="组内角色" prop="studentRole" align="center"/>
      <el-table-column label="归属小组" prop="studentGroup" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button @click="deleteStudent(scope.row.studentId)" text size="small">移除</el-button>
          <el-button @click="handleOpenUpdateDialog(scope.row)" text size="small">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </teacher-menu>
</template>

<style scoped>

</style>