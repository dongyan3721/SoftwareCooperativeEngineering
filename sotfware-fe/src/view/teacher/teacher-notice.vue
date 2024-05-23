<!--
-- @description 教师通知页面
-- @date 2024/5/14-19:25:30
-->

<script setup>
import {ref} from 'vue';
import TeacherMenu from "@/components/teacher/teacher-menu.vue";
import {ElMessage} from "element-plus";
import {confirmNotice, listAllTeacherNotice, readAllNotices} from "@/web-api/teacher/teacherNotice.js";
const notices = ref([]);
const currentPage = ref(1); // 当前页数
const pageSize = 5; // 每页显示条数

onMounted(async () => {
  try {
    const response = await listAllTeacherNotice();
    notices.value = response.rows|| [];
  } catch (error) {
    ElMessage.error('获取失败')
    notices.value = [];
  }
});
const readNotice = (index) => {
  confirmNotice(notices.value[index].noticeId)
      .then(res => {
        // 确认成功后，更新 notices 数组中相应通知的 hasRead 状态
        notices.value[index].hasRead = true;
        ElMessage.success('确认成功！');
      })
      .catch(err => {
        ElMessage.error('确认失败');
      });
};

const readAllNotice = ()=>{
  readAllNotices().then(res=>{
    notices.value.forEach(notice => {
      notice.hasRead = true;
    });
    ElMessage.success('全部已读')
  }).catch(err=>{
    ElMessage.error('全部已读失败');
  })
}
// 计算当前页应该显示的消息数据
const paginatedNotices = computed(() => {
  if (!notices.value) return []; // 如果 notices.value 是 undefined，返回空数组
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return notices.value.slice(start, end);
});

// 处理分页器页数变化的函数
const handleCurrentChange = (page) => {
  currentPage.value = page;
};
</script>

<template>
  <teacher-menu>
    <div class="notice-container">
      <div class="notice-inner-container">
        <el-button type="primary" @click="readAllNotice" style="margin-top: 10px;margin-left: 10px">全部已读</el-button>
        <!-- 消息通知卡片 -->
        <div v-for="(notice, index) in paginatedNotices" :key="index">
          <el-card class="notice-card">
            <div slot="header" class="clearfix" style="margin-bottom: 5px">
              <el-avatar :size="24" :src="notice.noticePublisherAvatar" style="margin: 0 5px"/>
              <span style="font-size: larger;font-weight: bold;margin-left: 5px">{{ notice.noticePublisher }}</span>
            </div>

            <div>
              <p>消息内容：{{ notice.noticeContent }}</p>
              <p>发布时间：{{ notice.noticePublishSnapshot }}</p>
            </div>
            <div slot="footer">
              <el-button
                  type="primary"
                  round
                  :disabled="notice.isConfirmed"
                  @click="readNotice(index)"
                  style="margin-left: 90%"
              >
                {{ notice.hasRead ? '已确认' : '确认' }}
              </el-button>
            </div>
          </el-card>
        </div>

        <!-- 分页器 -->
        <div class="pagination-container">
          <el-pagination
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-size="pageSize"
              :total="notices.length"
              layout="prev, pager, next"
          />
        </div>

      </div>
    </div>
  </teacher-menu>
</template>

<style scoped>
.notice-container {
  padding: 5px 5px 5px 5px; /* 设置内部间距为20px */
  background-color: #f9f9f9;
  box-shadow: 0 0 0 20px #f9f9f9; /* 设置外部红色阴影，宽度为20px */

}
.notice-inner-container {
  margin:5px auto;
  width: 95%;
  padding: 10px; /* 设置内部间距为20px */
  background-color: #ffffff;
  align-items: center; /* 垂直居中 */
  position: relative;
}
.notice-card {
  margin: 20px auto; /* 消息卡片间距 */
  width: 80%;
}

.pagination-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  margin-top: 20px; /* 调整顶部间距 */
}
</style>