<template>
  <div>
    <el-container>
      <el-header>
        <img class="logo" src="../../static/img/gaygay.jpg" height="272" width="244"/></el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

          <el-form-item label="用户名" prop="username">
            <el-input type="text" v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>

        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
    export default {
        name: "Blogs.vue",
        data() {
            return {
                ruleForm: {
                    username: 'lcy',
                    password: '111111',
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this;
                        this.$axios.post('http://localhost:8081/login', this.ruleForm).then(res =>{
                          this.$message({
                            message: '登录成功',
                            type: 'success'
                          });
                          //console.log(res.headers);
                          console.log(res);
                          const jwt = res.headers['authorization'];
                          const userInfo = res.data.data;

                          _this.$store.commit("SET_TOKEN", jwt)
                          _this.$store.commit("SET_USERINFO", userInfo)

                          console.log(_this.$store.getters.getUser)

                          _this.$router.push("/blogs");
                      })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style>
  .el-header, .el-footer {
    margin-top: 10px;
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .logo {
    height: 50px;
    width: 50px;
    margin-top: 5px;
  }

  .demo-ruleForm {
    max-width: 500px;
    margin: 0 auto;
  }
</style>
