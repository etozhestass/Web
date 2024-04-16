<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <PostPage v-if="page === 'PostPage'" :post="pageData.post"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./page/Users";
import WritePost from "./page/WritePost";
import PostPage from "./page/PostPage";


export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      pageData: null
    }
  },
  components: {
    PostPage,
    WritePost,
    Register,
    Enter,
    Index,
    Users,
    Sidebar
  },
  props: ["posts", "users"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page, data) => {
          this.page = page;
          this.pageData = data;
        });
  }
}
</script>

<style scoped>

</style>
