import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import inject from "@rollup/plugin-inject";
import path from 'path'
import AutoImport from 'unplugin-auto-import/dist/vite.js'

// https://vitejs.dev/config/
export default defineConfig(({command, mode})=>{
  const env = loadEnv(mode, './')
  return {
    base: './',
    server: {
      host: 'localhost',
      port: 5173,
      proxy: {
        [env. VITE_BASE_URL]: {
          target: 'http://localhost:8080',
          changeOrigin: true,
          ws: false,
          pathRewrite: {
            ['^' + env. VITE_BASE_URL]: ''
          }
        }
      }
    },
    plugins: [
      vue(),
      inject({
        $: "jquery",  // 这里会自动载入 node_modules 中的 jquery   jquery全局变量
        jQuery: "jquery",
        "windows.jQuery": "jquery"
      }),
      // 自动导入vue相关依赖
      AutoImport({
        imports: ['vue', 'vue-router', 'pinia']
      })
    ],
    resolve: {
      //设置路径别名
      alias: {
        '@': path.resolve(__dirname, './src'),
        '*': path.resolve('')
      },
    }
  }
})
