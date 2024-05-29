<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { AddClassChapter, ListClassChapter, DeleteClassChapter, ModifyClassChapter, getChapterContents, addChapterContent, deleteChapterContent, modifyChapterContent } from "@/web-api/teacher/teacherCourseSetting.js";
import upload from "@/web-api/upload.js";
import {manufacturePreviewUrl} from "@/util/filePreviewUrl.js"
import { useTeacherClassStore } from "@/store";
import { Plus } from "@element-plus/icons-vue";
import TeacherMenu from "@/components/teacher/teacher-menu.vue";

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

// 定义窗口
const AddDialogVis = ref(false);
const ModifyDialogVis = ref(false);
const AddDialogConVis = ref(false);
const ModifyDialogConVis = ref(false);
const classId = useTeacherClassStore().classId;
const data1 = ref([]);
const data2 = ref([]);
const chapters = ref([]); // 创建一个章节对象数组
const deletedContentId = ref(null); // 用于存储删除的小节ID

// 定义章节上传内容
const form1 = reactive({
  chapterId: null,
  classId: classId,
  chapterName: null,
  order: null
});

// 定义小节上传内容
const form2 = reactive({
  contentId: null,
  chapterId: null,
  resourceLink: null,
  contentDescription: null,
  resourceType: null,
  file: null // 添加用于存储文件的属性
});

const fileList = ref([]); // 用于手动管理文件列表

// 窗口初始化
const handleOpenAddDialog = () => {
  form1.chapterId = null;
  form1.chapterName = null;
  form1.classId = classId;
  form1.order = null;
  AddDialogVis.value = true;
};

const handleOpenModifyDialog = (chapter) => {
  form1.chapterId = chapter.chapterId;
  form1.chapterName = chapter.chapterName;
  form1.classId = chapter.classId;
  form1.order = chapter.order;
  ModifyDialogVis.value = true;
};

const handleOpenAddDialogCon = (chapterId) => {
  form2.contentId = null;
  form2.chapterId = chapterId;
  form2.resourceLink = null;
  form2.contentDescription = null;
  form2.resourceType = null;
  fileList.value = []; // 重置文件列表
  AddDialogConVis.value = true;
};

const handleOpenModifyDialogCon = (content) => {
  form2.contentId = content.contentId;
  form2.chapterId = content.chapterId;
  form2.resourceLink = content.resourceLink;
  form2.contentDescription = content.contentDescription;
  form2.resourceType = content.resourceType;
  fileList.value = []; // 重置文件列表
  ModifyDialogConVis.value = true;
};



// 方法：获取章节列表
const fetchChapters = async () => {
  try {
    const response = await ListClassChapter(classId);
    data1.value = response.data;

    // 将 data1 中的每个项转换为 Chapter 对象，并存储在 chapters 数组中
    chapters.value = data1.value
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
    data2.value = response.data;

    // 将 data2 中的每个项转换为 ChapterContent 对象，并存储在 chapterContent 数组中
    const chapterContents = data2.value
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

// 组件挂载时调用获取章节列表方法
onBeforeMount(()=>{
  fetchChapters();
});

// 定义窗口取消函数
const cancelSubmit = () => {
  AddDialogVis.value = false;
};

const cancelModify = () => {
  ModifyDialogVis.value = false;
};

const cancelContentSubmit = () => {
  AddDialogConVis.value = false;
};

const cancelContentModify = () => {
  ModifyDialogConVis.value = false;
};

// 定义与js交互的函数
// 提交表单以添加章节
const handleSubmitForm = async () => {
  try {
    if (form1) {
      await AddClassChapter(form1);
      ElMessage.success('添加成功！');
      AddDialogVis.value = false;
      // 刷新章节列表
      await fetchChapters();
    }
  } catch (error) {
    console.error('Error adding chapter:', error);
    ElMessage.error('添加章节失败，请重试');
  }
};

// 确认删除章节
const confirmDeleteChapter = async (chapterId) => {
  ElMessageBox.confirm(
      '确认删除此章节吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    try {
      await DeleteClassChapter(chapterId);
      ElMessage.success('删除成功！');
      await fetchChapters(); // 刷新章节列表
    } catch (error) {
      console.error('Error deleting chapter:', error);
      ElMessage.error('删除章节失败，请重试');
    }
  }).catch(() => {
    ElMessage.info('删除操作已取消');
  });
};

// 删除小节
const confirmDeleteChapterContent = async (contentId) => {
  ElMessageBox.confirm(
      '确认删除此小节吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    try {
      await deleteChapterContent(contentId);
      console.log(contentId);
      deletedContentId.value = contentId; // 更新删除的小节ID
      ElMessage.success('删除成功！');
      // 刷新章节列表
      await fetchChapters();
    } catch (error) {
      console.error('Error deleting chapter:', error);
      ElMessage.error('删除小节失败，请重试');
    }
  }).catch(() => {
    // 用户取消删除操作
    ElMessage.info('删除操作已取消');
  });
};

// 修改章节
const modifyDialog = async () => {
  try {
    if (form1) {
      await ModifyClassChapter(form1);
      ElMessage.success('修改成功！');
      ModifyDialogVis.value = false;
      // 刷新章节列表
      await fetchChapters();
    }
  } catch (error) {
    console.error('Error adding chapter:', error);
    ElMessage.error('修改章节失败，请重试');
  }
};

//修改小节
const modifyDialogContent = async () => {
  try {
    if (form2) {
      await modifyChapterContent(form2);
      ElMessage.success('修改成功！');
      ModifyDialogConVis.value = false;
      // 刷新章节内容
      await fetchChapterContent(form2.chapterId);
    }
  } catch (error) {
    console.error('Error modifying chapter content:', error);
    ElMessage.error('修改小节失败，请重试');
  }
};

//添加小节
const AddChapterContent = async () => {
  try {
    if (form2) {
      await addChapterContent(form2);
      ElMessage.success('添加成功！');
      AddDialogConVis.value = false;
      // 刷新当前章节内容
      await fetchChapterContent(form2.chapterId);
    }
  } catch (error) {
    console.error('Error adding chapter content:', error);
    ElMessage.error('添加小节失败，请重试');
  }
};

const handleManualUpload = (file) => {
  console.log(file)
  upload(file.raw).then(res => {
    form2.resourceLink = res.msg
  })
  ElMessage.success("上传成功~")
  fileList.value.push(file); // 添加文件到文件列表
}

const _window =window
</script>

<template>
  <teacher-menu>
    <el-button type="primary" @click="handleOpenAddDialog">添加章节</el-button>
    <div class="data-container">
      <!-- 遍历 chapters 数组并显示章节信息 -->
      <el-row :gutter="20">
        <el-col v-for="(chapter, index) in chapters" :key="index" :span="24">
          <el-card class="chapter-card" shadow="hover">
            <div slot="header" class="clearfix header-content">
              <p class="chapter-name">{{ chapter.chapterName }}</p>
              <div class="chapter-buttons">
                <el-button type="primary" @click="handleOpenAddDialogCon(chapter.chapterId)">添加小节</el-button>
                <el-button type="warning" @click="handleOpenModifyDialog(chapter)">修改</el-button>
                <el-button type="danger" @click="confirmDeleteChapter(chapter.chapterId)">删除</el-button>
              </div>
            </div>
            <div class="chapter-content">
              <el-collapse>
                <el-collapse-item v-for="(content, index) in chapter.contents" :title="content.contentDescription" :name="index + 1">
                  <p>
                    {{ content.resourceLink }}
                    <el-button type="primary" @click="_window.open(manufacturePreviewUrl(content.resourceLink), '_blank')">预览</el-button>
                  </p>
                  <el-button type="warning" @click="handleOpenModifyDialogCon(content)">修改</el-button>
                  <el-button type="danger" @click="confirmDeleteChapterContent(content.contentId)">删除</el-button>
                </el-collapse-item>
              </el-collapse>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </teacher-menu>
  <el-dialog append-to-body width="400px" v-model="AddDialogVis">
    <template #header>
      <div class="w-100 h-100 flex flex-row items-center">
        <el-icon size="16"><plus /></el-icon>
        <span class="inline-block ml-1">{{ form1.chapterId ? '修改' : '添加' }}章节</span>
      </div>
    </template>
    <el-form :model="form1" ref="formRef" :inline="false">
      <el-form-item label="章节名称" prop="chapterName">
        <el-input v-model="form1.chapterName" clearable />
      </el-form-item>
      <el-form-item label="章节序号" prop="order">
        <el-input v-model="form1.order" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmitForm">确定</el-button>
        <el-button type="warning" @click="cancelSubmit">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog append-to-body width="400px" v-model="ModifyDialogVis">
    <template #header>
      <div class="w-100 h-100 flex flex-row items-center">
        <el-icon size="16"><plus /></el-icon>
        <span class="inline-block ml-1">{{ form1.chapterId ? '修改' : '添加' }}章节</span>
      </div>
    </template>
    <el-form :model="form1" ref="formRef" :inline="false">
      <el-form-item label="章节名称" prop="chapterName">
        <el-input v-model="form1.chapterName" clearable />
      </el-form-item>
      <el-form-item label="章节序号" prop="order">
        <el-input v-model="form1.order" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modifyDialog">确定</el-button>
        <el-button type="warning" @click="cancelModify">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog append-to-body width="400px" v-model="AddDialogConVis">
    <template #header>
      <div class="w-100 h-100 flex flex-row items-center">
        <el-icon size="16"><plus /></el-icon>
        <span class="inline-block ml-1">{{ form2.contentId ? '修改' : '添加' }}小节</span>
      </div>
    </template>
    <el-form :model="form2" ref="formRef2" :inline="false">
      <el-form-item label="内容描述" prop="contentDescription">
        <el-input v-model="form2.contentDescription" clearable />
      </el-form-item>
      <el-form-item label="上传文件" prop="file">
        <el-upload
            :file-list="fileList.value"
            :before-remove="() => false" @change="handleManualUpload"
            :auto-upload="false" accept="*">
          <el-button type="primary">选择文件</el-button>
          <div slot="tip" class="el-upload__tip">不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="AddChapterContent">确定</el-button>
        <el-button type="warning" @click="cancelContentSubmit">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog append-to-body width="400px" v-model="ModifyDialogConVis">
    <template #header>
      <div class="w-100 h-100 flex flex-row items-center">
        <el-icon size="16"><plus /></el-icon>
        <span class="inline-block ml-1">{{ form2.contentId ? '修改' : '添加' }}小节</span>
      </div>
    </template>
    <el-form :model="form2" ref="formRef2" :inline="false">
      <el-form-item label="内容描述" prop="contentDescription">
        <el-input v-model="form2.contentDescription" clearable />
      </el-form-item>
      <el-form-item label="上传文件" prop="file">
        <el-upload
            :file-list="fileList.value"
            :before-remove="() => false" @change="handleManualUpload"
            :auto-upload="false" accept="*">
          <el-button type="primary">选择文件</el-button>
          <div slot="tip" class="el-upload__tip">不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modifyDialogContent">确定</el-button>
        <el-button type="warning" @click="cancelContentModify">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

</template>

<style scoped>
.data-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.deleted-content-id {
  margin-bottom: 20px;
  font-size: 16px;
  color: red;
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
  font-size: 20px; /* 调整章节名称字体大小 */
  font-weight: bold;
}

.chapter-id {
  font-size: 18px; /* 调整章节ID字体大小 */
  color: #888; /* 调整章节ID字体颜色 */
}
</style>
