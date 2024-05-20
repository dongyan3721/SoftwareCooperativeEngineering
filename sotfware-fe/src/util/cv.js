/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/17-21:13:32
 */

const backgroundColors = [
    '#8c02ea', '#d1be4b', '#b54089', '#1d49ac', '#aa6c15', '#ed4b8b', '#8190ee', '#8c1370',
    '#7e62b9', '#b8d840', '#9e29e3', '#f576be', "#f54d4d", "#f87544", "#ffae00", "#dcff00",
    '#F4AB87', '#EEC88D', '#76CADF', '#97DA9D', '#88DCD8', '#FB7F89', '#F0E403', '#F576BE',
    '#ACADFF', '#7EC3FB', '#D0DB02', '#C07B11', '#00ACC2', '#2AAD41', '#A59D00', '#EB4747',
    '#CD0EBD', '#DE3997'
];

/**
 * 用于构建默认头像
 * @param inputString
 * @returns {*}
 */
export function stringToPngBase64(inputString) {
    // 取字符串的最后一个字符
    const lastChar = inputString[inputString.length - 1];

    // 获取canvas元素及其上下文
    // const canvas = document.createElement('canvas');
    let canvas = document.createElement('canvas');
    canvas.width = 100;
    canvas.height = 100;
    const ctx = canvas.getContext('2d');

    // 随机选择一个背景色
    // 设置画布背景色并绘制圆形
    ctx.fillStyle = backgroundColors[Math.floor(Math.random() * backgroundColors.length)];
    ctx.beginPath();
    ctx.arc(50, 50, 50, 0, Math.PI * 2);
    ctx.fill();

    // 在画布上绘制字符
    ctx.font = 'bold 60px Arial';
    ctx.fillStyle = 'black';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.fillText(lastChar, canvas.width / 2, canvas.height / 2);

    // 将canvas内容转换为DataURL（即Base64格式的图像）
    return canvas.toDataURL('image/png');
}
