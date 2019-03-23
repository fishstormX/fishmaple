Vue.component('top-component', {
    data(){
        return {
            searchvalue: "",
            tool:"",
            user:{},
            login:"#c3c3c3"
        }
    },
    props: ['user','tool'],
    template:
        '<div><a style="display:block " href="/uc" class="nav_link"><svg class="icon icon-login" v-bind:style="{color : login}" aria-hidden="true">' +
        '                    <use xlink:href="#icon-gerenzhongxin-denglu"></use>' +
        '               </svg><span style="margin:26px 10px 0 0;float:right;">{{user.name}}</span></a>' +

        '<el-row class="top_bar" style="margin-right:110px" id="top-bar">' +

        '   <el-col  class="head-nav" :span="17">' +
       '        <a href="/index" class="nav_link"><img class="logo middle_pic" src="/pics/logo_m_m.png">' +
        '     <img class="logo middle_fish" src="/pics/logo-fish-small.png"></a>' +
        '        <ul id="nav">' +
       // '            <li><a href="/idea/" class="nav_link">idea</a></li> ' +
        '            <li><a href="/blog" class="nav_link">博客</a></li>' +
        '            <li><a href="/tool" class="nav_link">工具箱</a></li>' +
        '           <li><a href="/issues" class="nav_link">issues</a></li>' +
        //'            <li><a href="/document" class="nav_link">文件托管</a></li>' +
        '            <li><a href="/lab" class="nav_link">实验室</a></li>' +
       // '            <li><a href="/fishchat" class="nav_link">聊天室</a></li>' +
        '            <li><a href="/dictionary" class="nav_link">Dwiki</a></li>' +
       // '            <li><a href="/fishchat" class="nav_link">射线(beta)</a></li>' +
        '        </ul>' +
        '   </el-col>' +
        '   <el-col :span="6">' +
        '   <el-input class="search top100" v-model="searchvalue" @keyup.enter.native="dosearch"' +
        '       placeholder="请输入内容">'+
        '   <el-button @click="dosearch" id="search_btn" type="primary" slot="append">' +
        '               <svg class="icon" aria-hidden="true">' +
        '                    <use xlink:href="#icon-sousuo-m"></use>' +
        '               </svg>　站内检索' +
        '   </el-button>'+
        '   </el-input>' +
        '   </el-col>' +
       '</el-row></div>',
    methods:{
        dosearch() {
            if(this.tool!="tool")
            window.location.href = '/search?content=' + this.searchvalue
            else{
                this.$http.get('/api/tool/search?searchvalue='+this.searchvalue).then(function(res){
                    if(res.bodyText==='getupload') {
                        uploadDialog.dialogVisible = res.bodyText;
                    }else{
                        window.location.href = '/search?content=' + this.searchvalue
                    }
                },function(){
                    console.log('请求失败');
                });
            }
        }
    },
    watch: {
        user: function () {
            if(this.user.id!=null){
                this.login="#000"
            }else{
                this.login="#c3c3c3"
            }
        }
    }
    
})

Vue.component('foot-component', {
    template:
        ' <ul class="foot_nav">' +
        '            <li><a href="/aboutMe.html">关于我</a></li>' +
        '            <li><a href="/version.html">版本迭代</a></li>' +
        '            <li><a href="/restart.html">重启服务器</a></li>' +
        '            <li><a href="/log.html">日志查看</span></a></li>' +
        '            <li><span style="color:darkgray">与我互动</span></li>' +
        '            <li><a href="/druid">druid监控</a></li>' +
        '            <li><a href="/sitemap.html">网站地图</a></li>' +
        '            <li><a href="https://github.com/fish-storm"><img class="icons" src="/img/giticon.png" height="17px">github</a></li>' +
        ' </ul><br>'

})
Vue.component('copyright', {
    template:
        ' <ul class="foot_nav" id="copyright">' +
        '          <li><a target="_blank" href="http://www.miitbeian.gov.cn"> 黑ICP备17006684号-1</a>　fishmaple</li>' +
        ' </ul>'

})
Vue.component('contact-component',{
    template:
        '<div id="contact">'+
        '    <div><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=809789268&menu=yes">' +
        '       <img class="contact-s" src="/img/qq_s.png"></a></div>' +
        '    <div><a target="_blank" href="https://weibo.com/2605516155">' +
        '       <img class="contact-s" src="/img/weibo_s.png"></a></div>' +
        '    <div><a target="_blank" href="">' +
        '       <img class="contact-s" src="/img/wechat_s.png"></a></div>' +
        '    <div><a target="_blank" href="https://github.com/fish-storm">' +
        '       <img class="contact-s"src="/img/github_s.png"></a></div>'+
        '</div>'
})

