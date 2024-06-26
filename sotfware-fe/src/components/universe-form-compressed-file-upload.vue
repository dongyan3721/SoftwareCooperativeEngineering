<!--
-- @author Santa Antilles
-- @description 基于element-plus上传框封装一个支持、VueCropper裁剪框，用于表单内的文件上传
-- @date 2024/5/14-21:20:36
-- 封装上传的文件大小不超过10KB
-->

<script setup>
import {Plus} from "@element-plus/icons-vue";
import {VueCropper} from "vue-cropper";
import {base64ToBlob, compressImage} from "@/util/dongyan.js";
import {Scissors} from "@icon-park/vue-next";
import {genFileId} from "element-plus";

const props = defineProps({
  initialPicture: String,
  universeUploadFunction: Function,
  callbackVisitUrlAttribute: String,
  modelValue: String
})

const emit = defineEmits(['update:modelValue'])

let dialogUploadFileList = ref([{
  name: 'test',
  url: props.initialPicture
}])
const recordWhetherAvatarHadBeenModified = ref(false)
const dialogImageHeight = ref('50px')
const dialogImageWidth = ref()
const bodyPreviewDialogWidth = ref()
function adjustDialogImageWidthAndHeight() {
  // console.log(fileList.value[0])
  let imageUrl = dialogUploadFileList.value[0].url;
  let image = new Image();
  image.src = imageUrl;
  // 有坑，最好延迟1s
  setTimeout(()=>{
    dialogImageWidth.value = `${50 * image.width / image.height}px`;
    bodyPreviewDialogWidth.value = `${50 * image.width / image.height + 50}px`
    bodyPreviewDialogVis.value = true
  }, 100)
  console.log(bodyPreviewDialogWidth.value)
}
// 控制是否需要替换
const replaceOriginalAvatarFlag = ref(false)
// 控制页面内预览对话框可见性
const bodyPreviewDialogVis = ref(false);
// 关闭主页面预览对话框
const handleBodyPreviewDialogClose = ()=>{
  bodyPreviewDialogVis.value = false
}
// body上传框引用
const bodyUploadRef = ref()
// 图片预览
const handlePictureCardPreview = () => {
  // 固定对话框高度为50，宽度成比例
  adjustDialogImageWidthAndHeight()
}
// 选取图片超出限制时触发，自动去掉最前一个上传的图片，保留最新上传的图
// 此处做特殊处理，因为这边需要对图像进行裁剪，所以不需要触发on-change钩子，在on-exceed这里处理即可
// files为上传的文件的列表，就算只上传一个文件也是一个列表
const handleExceed = (files) => {
  if(!replaceOriginalAvatarFlag.value){
    cropOptions.img = URL.createObjectURL(files[0]);
    cropVis.value = true
    return
  }
  bodyUploadRef.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  bodyUploadRef.value.handleStart(file)
}
const handleAvatarInitialUpload = (ev)=>{
  cropOptions.img = URL.createObjectURL(ev.raw);
  console.log(cropOptions.img)
  openCropDialog()
  dialogUploadFileList.value = []
}
// 拒绝删除
const handleRejectRemove = ()=>{
  return false
}
// 由于只留一张，所以删除的时候认为头像没有被修改
const handleAvatarRemove = ()=>{
  recordWhetherAvatarHadBeenModified.value = false
}

// 裁剪框的配置
let cropOptions = reactive({
  img: '', // 裁剪图片的地址 url 地址, base64, blob
  outputSize: 1, // 裁剪生成图片的质量
  outputType: 'png', // 裁剪生成图片的格式 jpeg, png, webp
  info: false, // 裁剪框的大小信息
  canScale: true, // 图片是否允许滚轮缩放
  autoCrop: true, // 是否默认生成截图框
  autoCropWidth: 50, // 默认生成截图框宽度
  autoCropHeight: 50, // 默认生成截图框高度
  fixedBox: false, // 固定截图框大小 不允许改变
  fixed: true, // 是否开启截图框宽高固定比例，这个如果设置为true，截图框会是固定比例缩放的，如果设置为false，则截图框的狂宽高比例就不固定了
  fixedNumber: [1, 1], // 截图框的宽高比例 [ 宽度 , 高度 ]
  canMove: true, // 上传图片是否可以移动
  canMoveBox: true, // 截图框能否拖动
  original: false, // 上传图片按照原始比例渲染
  centerBox: true, // 截图框是否被限制在图片里面
  infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
  full: true, // 是否输出原图比例的截图
  enlarge: '1', // 图片根据截图框输出比例倍数
  mode: 'contain' // 图片默认渲染方式 contain , cover, 100px, 100% auto
})
// 打开头像裁剪对话框
const openCropDialog = ()=>{
  cropVis.value = true
}

const handleCloseCropDialog = ()=>{
  cropVis.value = false
}
// 裁剪框可见性
let cropVis = ref(false);
// 裁剪框引用
let cropperRef = ref()
const rotateLeft = () => {
  // 这个默认是旋转90度的，官网有说明
  // 后面会完善不要让这个一下子旋转90度的代码
  cropperRef.value.rotateLeft()
}
// 预览框内图像src
let previewUrl = ref();
// 是否预览
let showPreview = ref(false)
// 向右旋转
const rotateRight = () => {
  // 这个默认也是旋转90度的，官网有说明
  // 后面会完善不要让这个一下子旋转90度的代码
  cropperRef.value.rotateRight()
}
// 获取图片，在预览框内加载
const getCropDataBase64 = () => {
  // 这个是获取base64的图片
  cropperRef.value.getCropData((data) => {
    // do something
    // 如这里，可以获取上传base64位的图片给服务器
    // 也可以将base64位的图片转化为file文件，然后通过form表单的形式提交给后端，让后端返回一个图片的访问地址等
    // 这里就简单把截取到的图片展示一下吧，这里就暂时不上传给后端了
    compressImage(data).then(b64=>{
      previewUrl.value = b64;
      console.log(b64);
      showPreview.value = true
    }).catch(err=>{
      console.log(err)
    })
  })
}
// 将确认上传的新头像放在缓冲区
const transferUploadedToBuffer = ()=>{

  const avatarBlob = base64ToBlob(previewUrl.value)


  recordWhetherAvatarHadBeenModified.value = true
  dialogUploadFileList.value = [
    {
      name: 'newAvatar.png',
      url: previewUrl.value
    }
  ]
  props.universeUploadFunction(avatarBlob).then(res=>{
    emit('update:modelValue', res[props.callbackVisitUrlAttribute])
    cropVis.value = false;
  })
}

</script>

<template>
  <el-dialog v-model="cropVis" @close="handleCloseCropDialog" width="600px">
    <template #header>
      <div class="flex flex-row items-center">
        <scissors theme="outline" size="18" fill="#2a3f67" class="inline"/>
        <span class="ml-1 text-base">头像上传裁剪</span>
      </div>
    </template>
    <el-row>
      <VueCropper style="width: 560px; height: 300px" ref="cropperRef" :img="cropOptions.img" :outputSize="cropOptions.outputSize"
                  :outputType="cropOptions.outputType" :info="cropOptions.info" :canScale="cropOptions.canScale" :autoCrop="cropOptions.autoCrop"
                  :autoCropWidth="cropOptions.autoCropWidth" :autoCropHeight="cropOptions.autoCropHeight" :fixedBox="cropOptions.fixedBox"
                  :fixed="cropOptions.fixed" :fixedNumber="cropOptions.fixedNumber" :canMove="cropOptions.canMove" :canMoveBox="cropOptions.canMoveBox"
                  :original="cropOptions.original" :centerBox="cropOptions.centerBox" :infoTrue="cropOptions.infoTrue" :full="cropOptions.full"
                  :enlarge="cropOptions.enlarge" :mode="cropOptions.mode"/>
    </el-row>
    <el-row class="w-100 h-14 mt-2.5">
      <el-button type="primary" plain @click="rotateLeft">←向左旋转图片</el-button>
      <el-button type="primary" plain @click="rotateRight">向右旋转图片→</el-button>
      <el-button type="primary" @click="getCropDataBase64">获取截取的图片</el-button>
    </el-row>
    <hr>
    <div>
      头像预览
    </div>
    <div style="width: 600px" class="flex items-center justify-center">
      <!-- 若图片只设置宽度，可以保持等比例展示图片 -->
      <img :src="previewUrl" style="width: 50px; height: 50px" alt="preview" v-show="showPreview">
      <div v-show="!showPreview">
        <img src="@/assets/observe.png" alt="observe" height="44" width="88"/>
        <span>还没有选择图片哦</span>
      </div>
    </div>
    <template #footer>
      <el-button type="primary" @click="transferUploadedToBuffer" :disabled="!showPreview">确定</el-button>
    </template>
  </el-dialog>
  <!-- 二层对话框，预览小组头像-->
  <el-dialog v-model="bodyPreviewDialogVis" @close="handleBodyPreviewDialogClose" :width="bodyPreviewDialogWidth" append-to-body>
    <img :src="dialogUploadFileList[0].url" alt="preview" :height="dialogImageHeight" :width="dialogImageWidth"/>
    <template #header><span>头像预览</span></template>
  </el-dialog>

  <el-upload
      v-model:file-list="dialogUploadFileList"
      ref="bodyUploadRef"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-exceed="handleExceed"
      :on-remove="handleAvatarRemove"
      :on-change="handleAvatarInitialUpload"
      :before-remove="handleRejectRemove"
      :auto-upload="false"
      :limit="1">
    <template #default>
      <el-icon><Plus/></el-icon>
    </template>
  </el-upload>
</template>

<style scoped>

</style>