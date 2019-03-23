Vue.component('top-component', {
    props: ['inputvalue','dosearch'],
    template:
        '<el-row class="top_bar">' +
        /*'        <a href="/" class="nav_link"><img class="logo middle_pic" src="/pics/logo_m.png"></a>' +*/
        '   <el-col :span="9">' +
        '        <ul id="nav">' +
        '            <li><a href="/idea/" class="nav_link">idea</a></li>' +
        '            <li><a href="/blog/" class="nav_link">博客</a></li>' +
        '            <li><a href="/tool/" class="nav_link">工具箱</a></li>' +
        '            <li><a href="/document/" class="nav_link">文件托管</a></li>' +
        '            <li><a href="/markdown" class="nav_link">实验室</a></li>' +
        //'            <li><a href="/fishchat/" class="nav_link">聊天室</a></li>' +
        '            <li><a href="/slide/" class="nav_link">fiseah</a></li>' +
        '        </ul>' +
        '   </el-col>' +
       /* '   <el-col :span="5">' +
        '   <el-input class="search top100" v-bind:value="inputvalue"' +
        '      v-on:input.native="$emit(`input`, $event.target.value)"' +
        '       placeholder="请输入内容">'+
        '   <el-button @keyup.enter="entersearch" @click="dosearch" id="search_btn" type="primary" slot="append">' +
        '               <svg class="icon" aria-hidden="true">' +
        '                    <use xlink:href="#icon-sousuo-m"></use>' +
        '               </svg>　站内检索' +
        '   </el-button>'+
        '   </el-input>'+
        '   </el-col>' +*/
        '</el-row>'

})
