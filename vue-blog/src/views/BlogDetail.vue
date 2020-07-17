<template>
  <div class="m-container">
    <Header></Header>
    <div class="mblog">
      <h2>{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog"><router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">编辑</router-link></el-link>
      <el-divider direction="vertical"></el-divider>
      <el-link @click="deleteBlog(blog.id)" icon="el-icon-delete">删除</el-link>
      <el-divider></el-divider>
      <div class="content markdown-body" v-html="blog.content"></div>
    </div>
  </div>
</template>
<script>
  import 'github-markdown-css/github-markdown.css' // 然后添加样式markdown-body
  import Header from "@/components/Header";
  export default {
    name: "BlogDetail",
    components: {
      Header
    },
    data() {
      return {
        blog: {
          userId: null,
          title: "",
          description: "",
          content: ""
        },
        ownBlog: false
      }
    },
    methods: {
      getBlog() {
        const blogId = this.$route.params.blogId
        const _this = this
        this.$axios.get('/blog/' + blogId).then((res) => {
          _this.blog = res.data.data
          var MarkdownIt = require('markdown-it'),
            md = new MarkdownIt();
          var result = md.render(_this.blog.content);
          _this.blog.content = result
          // 判断是否是自己的文章，能否编辑
          _this.ownBlog =  (_this.blog.userId === _this.$store.getters.getUser.id)
        });
      },
      deleteBlog(id){
        const _this = this;
        this.$axios.delete("/delete/" + id).then(res => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          _this.$router.push('/blogs')
        })
      }
    },
    created() {
      this.getBlog()
    }
  }
</script>
<style>
  .mblog{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 98%;
    min-height: 700px;
    margin: 20px auto;
    padding: 20px 20px;
  }
</style>
