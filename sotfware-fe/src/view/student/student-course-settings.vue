<!--
-- @description 学生首页（课程内容查看页）
-- @date 2024/4/21-22:24:36
-->

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { getChapterContents, ListClassChapter } from "@/web-api/teacher/teacherCourseSetting.js";
import { manufacturePreviewUrl } from "@/util/filePreviewUrl.js";
import { useUserStore } from "@/store";
import StudentMenu from "@/components/student/student-menu.vue";

// 定义 Chapter 类
class Chapter {
  constructor(chapterId, classId, chapterName, order) {
    this.chapterId = chapterId;
    this.classId = classId;
    this.chapterName = chapterName;
    this.order = order;
    this.contents = []; // 新增属性，用于存储章节内容
  }
}

// 定义小节类
class ChapterContent {
  constructor(contentId, chapterId, resourceLink, contentDescription, resourceType) {
    this.contentId = contentId;
    this.chapterId = chapterId;
    this.resourceLink = resourceLink;
    this.contentDescription = contentDescription;
    this.resourceType = resourceType;
  }
}

// 变量和状态定义
const classId = useUserStore().studentClass
const chapters = ref([]); // 创建一个章节对象数组

// 方法：获取章节列表
const fetchChapters = async () => {
  try {
    const response = await ListClassChapter(classId);
    const data1 = response.data;

    // 将 data1 中的每个项转换为 Chapter 对象，并存储在 chapters 数组中
    chapters.value = data1
        .map(item => new Chapter(item.chapterId, item.classId, item.chapterName, item.order))
        .sort((a, b) => a.order - b.order);

    // 获取每个章节的小节内容
    for (const chapter of chapters.value) {
      await fetchChapterContent(chapter.chapterId);
    }
  } catch (error) {
    console.error('Error fetching courses:', error);
  }
};

// 获取小节内容
const fetchChapterContent = async (chapterId) => {
  try {
    const response = await getChapterContents(chapterId);
    const data2 = response.data;

    // 将 data2 中的每个项转换为 ChapterContent 对象，并存储在 chapterContent 数组中
    const chapterContents = data2
        .map(item => new ChapterContent(item.contentId, item.chapterId, item.resourceLink, item.contentDescription, item.resourceType));

    // 根据 chapterId 将小节内容分组并存储在 chapters 的对应项中
    const chapter = chapters.value.find(chapter => chapter.chapterId === chapterId);
    if (chapter) {
      chapter.contents = chapterContents;
    }
  } catch (error) {
    console.error(`Error fetching chapter contents for chapterId ${chapterId}:`, error);
  }
};


const _window = window

// 组件挂载时调用获取章节列表方法
onMounted(() => {
  fetchChapters();
});
</script>

<template>
  <student-menu>
    <p>{{classId}}
    </p>
    <div class="data-container">
      <!-- 遍历 chapters 数组并显示章节信息 -->
      <el-row :gutter="20">
        <el-col v-for="(chapter, index) in chapters" :key="index" :span="24">
          <el-card class="chapter-card" shadow="hover">
            <div slot="header" class="clearfix header-content">
              <p class="chapter-name">{{ chapter.chapterName }}</p>
            </div>
            <div class="chapter-content">
              <el-collapse>
                <el-collapse-item v-for="(content, index) in chapter.contents" :title="content.contentDescription" :name="index + 1">
                  <p>
                    {{ content.resourceLink }}
                    <el-button type="primary" @click="_window.open(manufacturePreviewUrl(content.resourceLink), '_blank')">预览</el-button>
                  </p>
                </el-collapse-item>
              </el-collapse>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </student-menu>
</template>

<style scoped>
.data-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.chapter-card {
  margin-bottom: 20px;
}

.chapter-content {
  margin: 10px 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chapter-name {
  font-size: 20px;
  font-weight: bold;
}
</style>
