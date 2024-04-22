<!--
-- @author Santa Antilles
-- @description
-- @date 2024/4/21-22:55:29
-->

<template>
  <div class="flex h-screen w-screen items-center justify-center">
    <Verify :show="showVerify" @close="closeVerifyWindow" @success="handleVerifySuccess"></Verify>
    <div class="h-2/5 w-2/5 min-w-150 min-h-75 bg-white blur-sm rounded-xl relative">
      <div class="w-9/10 h-1/10 flex items-center justify-center absolute ">
        <h2>欢迎回来</h2>
      </div>
      <div class="form-main">
        <div class="left-decorate-image">
          <img src="@/assets/login-decorate.png" alt="登录认证">
        </div>
        <div class="separator-vertical-line">
          <div class="line-separator"/>
        </div>
        <div class="main-input">
          <el-form :model="form" ref="formRef" :inline="false" size="large" :rules="formRules">
            <el-form-item prop="email">
              <template #label>
                <el-icon size="large"><User/></el-icon>
              </template>
              <el-input v-model="form.email" placeholder="邮箱" clearable/>
            </el-form-item>
            <el-form-item prop="password">
              <template #label>
                <el-icon size="large"><Lock/></el-icon>
              </template>
              <el-input v-model="form.password" placeholder="密码" clearable type="password"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="verifyAndLogin" size="large">登录</el-button>
              <p style="margin: 0 0 0 15px">
                还没有账号？去
                <span class="indication-to-register" @click="gotoRegister">
                  注册
                </span>
                或
                <span class="indication-to-register" @click="gotoIndex">
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

</script>