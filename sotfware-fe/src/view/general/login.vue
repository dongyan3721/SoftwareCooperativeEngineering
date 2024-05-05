<!--
-- @author Santa Antilles
-- @description
-- @date 2024/4/21-22:55:29
-->

<template>
  <div class="flex h-screen w-screen items-center justify-center page-container"
        style="background-image: url('/login-register-background.jpg');
        background-size: cover; background-position: center center" id="background1">
    <Verify :show="showVerify" @close="closeVerifyWindow" @success="handleVerifySuccess"></Verify>
    <div class="container h-2/5 w-2/6 min-w-150 min-h-75 rounded-xl flex-col items-center justify-around"
          style="background-color: rgba(255, 255, 255, .6)">
      <div class="w-9/10 h-auto flex items-center justify-center mt-2">
        <h2 class="text-3xl font-bold">欢迎回来</h2>
      </div>
      <div class="w-9/10 h-4/5 flex mt-3 justify-around">
<!--        <div class="w-3/10 h-9/10">-->
<!--          <img src="/login-decorate.png" alt="登录认证" class="h-100 w-100 rounded-xl">-->
<!--        </div>-->
<!--        <div class="w-1/10 h-19/20 flex items-center justify-center">-->
<!--          <div class="h-100 w-1.5 bg-neutral-400 opacity-75"/>-->
<!--        </div>-->
        <div class="h-9/10 flex items-center">
          <el-form :model="form" ref="formRef" :inline="false" size="large" :rules="formRules">
            <el-form-item prop="id">
              <template #label>
                <el-icon size="large"><User/></el-icon>
              </template>
              <el-input v-model="form.id" placeholder="账号" clearable/>
            </el-form-item>
            <el-form-item prop="password">
              <template #label>
                <el-icon size="large"><Lock/></el-icon>
              </template>
              <el-input v-model="form.password" placeholder="密码" clearable type="password"/>
            </el-form-item>
            <el-form-item prop="role">
              <template #label>
                <fingerprint-three theme="outline" size="18" fill="#606266"/>
              </template>
              <el-radio-group v-model="form.role">
                <el-radio v-for="role in userRoleDict" size="large" :value="role.value">{{role.label}}登录</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="verifyAndLogin" size="large">登录</el-button>
              <p style="margin: 0 0 0 15px">
                还没有账号？去
                <span class="hover:text-sky-600 hover:cursor-pointer" @click="gotoRegister">
                  注册
                </span>
                或
                <span class="hover:text-sky-600 hover:cursor-pointer" @click="gotoIndex">
                  返回首页
                </span>
              </p>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import Verify from 'vue3-puzzle-vcode'
import {Lock, User} from "@element-plus/icons-vue";
import {generalValidatorJudgeIfEmpty} from "@/util/common.js";
import {useUserStore} from "@/store/useUserStore.js";
import dictDataExtract from "@/util/dictDataExtarct.js";
import {FingerprintThree} from "@icon-park/vue-next";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
import {studentLogin, teacherLogin} from "@/web-api/general/login.js";
const userStore = useUserStore()
const {setGeneralLoginInfo, setStudentLoginSpeciality, setUserRole} = userStore

function setUserStore(response){
  // 登录身份为学生
  if (valueNameReflection[form.role]==='教师'){
    setGeneralLoginInfo(response)
  }else{
    setGeneralLoginInfo(response)
    setStudentLoginSpeciality(response)
  }
}

// 控制验证码是否出现
let showVerify = ref(false)

const verifyAndLogin = ()=>{
  showVerify.value = true
}

const closeVerifyWindow = ()=>{
  showVerify.value = false
}

const handleVerifySuccess = ()=>{
  showVerify.value = false
  submitForm();
}

const submitForm = ()=>{
  if(!formRef){
    ElMessage({
      message: '请完善必要的输入信息！！',
      type: 'warning'
    })
    return
  }
  formRef.value.validate(valid=>{
    if(valid){
      if(valueNameReflection[form.role]==='教师'){
        teacherLogin({
          teacherId: form.id, password: form.password
        }).then(res=>{
          if(res.code===200){
            // 设置前端存储信息
            setUserStore(res)
            setUserRole(form.role)
            ElMessage.success("登录成功！")
            router.push('/teacher-main')
          }else{
            ElMessage.error("账号或密码错误！")
          }
        }).catch(err=>console.log(err))
      }else{
        studentLogin({
          studentId: form.id, password: form.password
        }).then(res=>{
          if(res.code===200){
            setUserStore(res)
            setUserRole(form.role)
            ElMessage.success("登录成功！")
            router.push('/student-amin')
          }else{
            ElMessage.error("账号或密码错误！")
          }
        }).catch(err=>console.log(err))
      }
    }
  })
}

const userRoleDict = ref()

let form = reactive({
  id: null,
  password: null,
  role: null
})
let formRef = ref()

const resetForm = ()=>{
  if(!formRef.value){
    return
  }
  formRef.value.resetFields();
}

let formRules = reactive({
  id: [
    {
      validator: generalValidatorJudgeIfEmpty('账号'), trigger: 'blur'
    }
  ],
  password: [
    {
      validator: generalValidatorJudgeIfEmpty('密码'), trigger: 'blur'
    }
  ],
  role : [
    {
      validator: generalValidatorJudgeIfEmpty('登录角色'), trigger: 'blur'
    }
  ]
})

const gotoRegister = ()=>{

}

const gotoIndex = ()=>{

}

// 映射值与角色
const valueNameReflection = reactive({})

onBeforeMount(function (){
  dictDataExtract('sys_user_role').then(res=>{
    userRoleDict.value = res
    res.forEach(dict=>{
      valueNameReflection[dict.value] = dict.label
    })
  })
})

</script>

<style scoped lang="scss">
:deep(.el-form-item__label){
  align-items: center !important;
}
</style>