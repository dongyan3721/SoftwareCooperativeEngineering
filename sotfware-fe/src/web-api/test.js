/**
 * @author Santa Antilles
 * @description
 * @date 2024/5/19-12:08:29
 */

function min(a, b){
    return a<b?a:b;
}

const findSubstring = function(s, words) {
    const dic = {}
    for (let w of words) dic[w] = 0;
    const step = words[0].length;
    const subs = words.length;
    let ans = [], l = 0, r=0, total = 0;
    while(r<s.length){
        const sliceStep = min(r+step, s.length);
        if(dic[s.slice(r, sliceStep)]===(void 0)){
            l = r
            ++r;
            for (let w of words) dic[w] = 0;
            total = 0;
        }else if(dic[s.slice(r, sliceStep)]===0){
            dic[s.slice(r, sliceStep)] +=1;
            ++total;
            if(total===subs) ans.push(l);
            r+=step;
        }else if(dic[s.slice(r, sliceStep)]===1){
            dic[s.slice(r, sliceStep)] +=1;
            ++total;
            while(l<r){
                dic[s.slice(l, step+l)] -=1;
                --total;
                l+=step;
                if(dic[s.slice(r, sliceStep)]===1) break;
            }
            if(total===subs) ans.push(l);
            r+=step;
        }
    }
    return ans;
};

console.log(findSubstring("wordgoodgoodgoodbestword", ["word","good","best","word"]))
