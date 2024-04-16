<template>
  <div>
    <Post :post="post" :key="post.id" :comments_shown="true"/>
  </div>
</template>

<script>
import Post from "./Post";

export default {
  name: "PostPage",
  components: {
    Post
  },
  props: ["post"],
}
</script>

<style scoped>

</style>