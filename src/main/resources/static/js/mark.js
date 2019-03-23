/**
 * ROOT COMPONENT
 */
var app = new Vue({
  el: '#app',
  data: {
    appTitle: 'VueDown Editor',
    code: '#这是一个标题\n---\n对\n - dui ',
    isSeen: true,
    isSettings: false,
    isFull: false,
    isUpdating: false,
    actualSkin: {
      color: '#0cc',
      background: 'rgba(0, 204, 204, .4)',
      wall: 'rgba(0, 204, 204, .15)'
    }
  },
  computed: {
    compiledOutput(){
      return marked(this.code, {sanitize: true});
    }  
  }
});