<template>
  <div>
    <Post :post="post" :comments="comments" :users="users" :key="post.id" :comments_shown="true"/>
  </div>
</template>

<script>
import Post from "./Post";

export default {
  name: "PostPage",
  components: {
    Post
  },
  props: ["post", "users", "comments"],
}
</script>

<style scoped>

</style>