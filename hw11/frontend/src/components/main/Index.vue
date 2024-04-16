<template>
  <div>
    <Post v-for="post in this.posts" :post="post" :key="post.id"/>
  </div>
</template>

<script>
import Post from "../page/Post";

export default {
  name: "Index",
  components: {
    Post
  },
  props: ["posts"]
}
</script>

<style scoped>

</style>
