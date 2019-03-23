function addClass(ele,cName) {
    var arr = ele.className.split(' ').concat(cName.split(" "));
    for(var i=0;i<arr.length;i++){
        for(var k=arr.length-1;k>i;k--){
            (arr[k]==="")&&arr.splice(k,1);
            (arr[i]===arr[k])&&arr.splice(k,1);
        }
    }
    ele.className = arr.join(" ");
}
function removeClass(ele,cName) {
    var arr1 = ele.className.split(' ');
    var arr2 = cName.split(" ");
    for(var i=0;i<arr2.length;i++)for(var j=arr1.length-1;j>=0;j--)(arr2[i]===arr1[j])&&arr1.splice(j,1)
    ele.className = arr1.join(" ")
}