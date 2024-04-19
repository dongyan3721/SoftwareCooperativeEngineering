/**
 * @author Santa Antilles
 * @description 访问后端文件的上下文路径
 * @date 2024/4/19-17:37:50
 */

const NOW_ENVIRONMENT = import.meta.env.VITE_MODE_NAME

const BASE_API_CONFIG = {
    production: 'http://47.101.34.15:8080',
    development: 'http://127.0.0.1:8080'
}

export default BASE_API_CONFIG[NOW_ENVIRONMENT]
