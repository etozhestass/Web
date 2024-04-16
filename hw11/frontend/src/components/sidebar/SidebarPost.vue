<template>
  <section>
    <div class="header">
      {{ post.title }}
    </div>
    <div class="body">
      {{ post.text }}
    </div>
    <div class="footer">
      <a href="#" @click.prevent="goToPostPage">View all</a>
    </div>
  </section>
</template>

<script>
export default {
  name: "SidebarPost",
  props: ["post"],
  methods: {
    goToPostPage() {
      this.$root.$emit("onChangePage", 'PostPage', {post: this.post, comments_shown: true});
    }
  }
}
</script>

<style scoped>

section {
  margin-bottom: 1rem;
}

section .body {
  white-space: pre-line;
}

</style>
