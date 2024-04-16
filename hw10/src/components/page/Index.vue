<template>
  <div>
      <Post v-for="post in posts" :post="post" :comments="comments"
            :users="users" :key="post.id"/>
  </div>
</template>

<script>
import Post from "./Post";

export default {
  name: "Index",
  components: {
    Post
  },
  props: ["posts", "users", "comments"]
}
</script>

<style scoped>

</style>
