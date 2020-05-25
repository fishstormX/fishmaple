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
       '        <a href="/index" class="nav_link"><img class="logo middle_pic" alt="鱼鱼logo" src="/pics/logo_m_m.png">' +
        '     <img alt="鱼鱼logom" class="logo middle_fish" src="/pics/logo-fish-small.png"></a>' +
        '        <ul id="nav">' +
       // '            <li><a href="/idea/" class="nav_link">idea</a></li> ' +
        '            <li><a href="/blog" class="nav_link">博客</a></li>' +
        //'            <li><a href="/tool" class="nav_link">工具箱</a></li>' +
        '           <li><a href="/issues" class="nav_link">Issue</a></li>' +
        //'            <li><a href="/document" class="nav_link">文件托管</a></li>' +
        '            <li><a href="/lab" class="nav_link">实验室</a></li>' +
       // '            <li><a href="/fishchat" class="nav_link">聊天室</a></li>' +
        '            <li><a href="/dictionary" class="nav_link">Dwiki</a></li>' +
        '            <li><a href="/fishapp.html" class="nav_link">App</a></li>' +
        '           <li><a href="/doc" class="nav_link">文档仓库</a></li>' +
        //'            <li><a href="/reveal" class="nav_link">ppt online</a></li>' +
        '            <li><a href="/friendLinks" class="nav_link">友情链接</a></li>' +
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
    data(){
        return {
            tongji:{uv:0,pv:0,ip:0}
        }
    },
    props: ['tongji'],
    template:
        ' <div><ul class="foot_nav">' +
        '            <li><a href="/aboutMe.html">关于我</a></li>' +
        '            <li><a href="/version.html">版本迭代</a></li>' +
        '            <li><a href="/tool">工具箱</a></li>' +
        '            <li><a href="/friendLinks">友情链接</a></li>' +
        //'            <li><a href="/restart.html">重启服务器</a></li>' +
       // '            <li><a href="/log.html">日志查看</a></li>' +
        '            <li><a href="/sideWall">给我留言/友链申请</a></li>' +
        '            <li><a href="/druid">druid</a></li>' +
        '            <li><a href="/sitemap.html">网站地图</a></li>' +
        '            <li><a href="https://github.com/fishstormX"><img alt="github" class="icons" src="/img/giticon.png" height="17px">github</a></li>' +
        ' </ul>'+
        ' <ul style="font-size:14px" class="foot_nav">' +
        '            <li>统计自 2019.3.4</li>' +
        '            <li>浏览量(PV):<span style="color:blue">{{tongji.pv}}</span></li>' +
        '            <li>访客数(UV):<span style="color:blue">{{tongji.uv}}</span></li>' +
        '            <li>IP数:<span style="color:blue">{{tongji.ip}}</span></li>' +
        '            <li>数据来源：<a href="https://tongji.baidu.com"><img style="position:relative;top:3px" ' +
        'alt="百度统计"  src="https://tongji.baidu.com/sc-web/image/icon/31.gif"></a></li>' +
       ' </ul></div>'
  ,mounted(){
        this.$http.get('/api/tongji').then(function(res){
            this.tongji = JSON.parse(res.bodyText);
        },function(){
            console.log('请求失败');
        });
    }

})
Vue.component('copyright', {
    template:
        ' <ul class="foot_nav" id="copyright">' +
        '          <li><a target="_blank" href="http://www.beian.miit.gov.cn">京ICP备20014973号-1</a>　fishmaple</li>' +
        ' </ul>'

})
Vue.component('contact-component',{
    template:
        '<div id="contact">'+
        '    <div><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=809789268&menu=yes">' +
        '       <img class="contact-s" alt="qq" src="/img/qq_s.png"></a></div>' +
        '    <div><a target="_blank" href="https://weibo.com/2605516155">' +
        '       <img class="contact-s" alt="微博" src="/img/weibo_s.png"></a></div>' +
        //'    <div><a target="_blank" href="">' +
        //'       <img class="contact-s" alt="qq" src="/img/wechat_s.png"></a></div>' +
        '    <div><a target="_blank" href="https://github.com/fishstormX">' +
        '       <img class="contact-s" alt="github" src="/img/github_s.png"></a></div>'+
        '</div>'
})

