<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="allPosts" :comments="comments" :users="users"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <EditPost v-if="page === 'EditPost'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <PostPage v-if="page === 'PostPage'" :users="users" :comments="comments" :post="pageData.post"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import Register from "./page/Register";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Users from "./page/Users"
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
    Register,
    WritePost,
    Enter,
    Index,
    Sidebar,
    EditPost,
    Users,
    PostPage
  },
  props: ["posts", "users", "comments"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    },
    allPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
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
