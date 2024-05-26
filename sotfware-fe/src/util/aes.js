/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/27-02:29:19
 */
import CryptoJS from 'crypto-js'
import {iv, key} from "@/configuration/pwd.js";

const _key = CryptoJS.enc.Utf8.parse(key);
const _iv = CryptoJS.enc.Utf8.parse(iv);

// AES加密
export function aesEncrypt(original){
    return CryptoJS.AES.encrypt(original, _key, {
        iv: _iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    }).toString()
}

// AES解密
export function aesDecrypt(ex){
    return CryptoJS.AES.decrypt(ex, _key, {
        iv: _iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    }).toString(CryptoJS.enc.Utf8)
}
