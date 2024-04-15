/**
 * @author Santa Antilles
 * @usage 各种通用功能，包括各种常用函数、字典等
 */

// 判断字符串能不能构成纯数字
export function isNumeric(value) {
    return /^-?\d*\.?\d+$/.test(value);
}

// 通用验证器，只验证字段是否为空
export const generalValidatorJudgeIfEmpty = (msg)=>{
    return (rule, value, callback)=>{
        if(value===''||value===null){
            callback(new Error(`${msg}不能为空！`));
        }
        callback();
    }
}

export const emptyValidator = ()=>{
    return (rule, value, callback)=>{
        callback();
    }
}

// 日后这个可能还要加特殊字符的验证，这里跟用户名长度判断分开
const generalPasswordValidator = (attribute)=>{
    return (rule, value, callback)=>{
        if(value===''||value===null){
            callback(new Error(`${attribute}不能为空！`))
        }else if(value.length<6){
            callback(new Error(`${attribute}长度过短！`))
        }else if(value.length>16){
            callback(new Error(`${attribute}长度过长！`))
        }
        callback();
    }
}
export {generalPasswordValidator}

export function nicknameLengthValidator(attribute, max_length, min_length){
    return (rule, value, callback)=>{
        if(value===''||value===null){
            callback(new Error(`${attribute}不能为空！`))
        }else if(value.length<min_length){
            callback(new Error(`${attribute}长度过短！`))
        }else if(value.length>max_length){
            callback(new Error(`${attribute}长度过长！`))
        }
        callback();
    }
}

const generalPhoneNumberValidator = (attribute)=>{
    let phonePattern = /^1[3456789]\d{9}$/
    return (rule, value, callback)=>{
        if(value!==null||value!==''){
            if(!phonePattern.test(value)){
                callback(new Error(`${attribute}格式不正确！`));
            }else{
                callback();
            }
        }else{
            callback();
        }
        callback();
    }
}
export {generalPhoneNumberValidator}

const generalEmailValidator = (attribute)=>{
    let pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return (rule, value, callback)=>{
        if(value!==null||value!==''){
            if(!pattern.test(value)){
                callback(new Error(`${attribute}格式不正确！`));
            }else{
                callback();
            }
        }else{
            callback();
        }
        callback();
    }
}
export {generalEmailValidator}

export function sleep(delay) {
    let start = (new Date()).getTime();
    while ((new Date()).getTime() - start < delay) {
        continue;
    }
}

export function generalNumericValidator(attribute){
    return function (rule, value, callback) {
        if(!isNumeric(value)){
            callback(new Error(`${attribute}不是合法的数字！`));
            return;
        }
        callback();
    }
}