<template>
  <div class="content">
    <h3>欢迎{{user.username}}来到gayhub!</h3>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <div class="divider">
        <span><el-link href="/blogs" target="_blank">主页</el-link></span>
          <el-divider direction="vertical"></el-divider>
        <span>
          <el-link href="/blog/add" type="success">写博客</el-link>
          <el-divider direction="vertical"></el-divider>
        </span>
        <span v-show="!hasLogin"><el-link type="warning" @click="logout">登录</el-link>
        </span >
        <span v-show="hasLogin"><el-link type="danger" @click="logout">退出登陆</el-link>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        user: {
          username: '请先登录',
          avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
        },
        hasLogin: false
      }
    },
    methods: {
      logout() {
        const _this = this;
        this.$axios.get("/logout", {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(res => {
          _this.$store.commit("REMOVE_INFO");
          _this.$router.push("/login");
        })
      }
    },
    created() {
      this.user.username = this.$store.getters.getUser.user;
      this.user.avatar = this.$store.getters.getUser.avatar;

      this.hasLogin = true;
    }
  }
</script>

<style scoped>
  .content {
    margin: 0 auto;
    max-width: 960px;
    text-align: center;
  }
  .divider{
    margin: 10px 10px;
  }
</style>
