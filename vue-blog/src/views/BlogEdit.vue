<template>
  <div>
    <Header></Header>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item  label="标题" prop="title">
        <el-input v-model="ruleForm.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要" prop="description">
        <el-input type="textarea" v-model="ruleForm.description"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="description">
        <mavon-editor v-model="ruleForm.content"></mavon-editor>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import Header from "../components/Header";
    export default {
      name: "BlogEdit.vue",
      components: {Header},
      data() {
        return {
          ruleForm: {
            title: '',
            description: '',
            content: '',
          },
          rules: {
            title: [
              { required: true, message: '请输入标题', trigger: 'blur' },
              { min: 2, max: 25, message: '长度在 2 到 25 个字符', trigger: 'blur' }
            ],
            description: [
              { required: true, message: '请输入摘要', trigger: 'blur' }
            ],
            content: [
              { required: true, message: '请输入博客内容', trigger: 'blur' }
            ],
          }
        };
      },
      methods: {
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              const _this = this;
              this.$axios.post("/blog/edit", this.ruleForm, {
                headers: {
                  "Authorization": localStorage.getItem("token")
                }
              }).then(res => {
                this.$message({
                  message: '成功创建博客',
                  type: 'success'
                });
                _this.$router.push('/blogs')
              })
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        },
      },
      created() {
        const  blogId = this.$route.params.blogId;
        if (blogId) {
          const _this = this;
          this.$axios.get('/blog/' + blogId).then(res => {
            const blog = res.data.data;
            _this.ruleForm.id = blog.id;
            _this.ruleForm.title= blog.title;
            _this.ruleForm.description = blog.description;
            _this.ruleForm.content = blog.content;

          })
        }
      }
    }
</script>

<style scoped>
  .demo-ruleForm{
    margin: 20px auto;
    text-align: center;
    max-width: 80%;
  }
</style>
