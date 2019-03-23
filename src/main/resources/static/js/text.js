Vue.component('text-component', {
    props: ['textvalue'],
    template:
        '<el-input' +
        '  type="textarea"' +
        '  placeholder="请输入内容"' +
        '  v-bind:value="textvalue" ' +
        '  resize="none" ' +
        '  v-on:input.native="$emit(`input`, $event.target.value)">' +
        '</el-input>'
})
