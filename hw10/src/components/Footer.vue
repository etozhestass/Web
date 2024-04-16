<template>
    <footer>
      <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
      <div>
        <span> Number of users: {{users_number}}</span>
        <span> | Number of posts: {{posts_number}}</span>
      </div>
    </footer>
</template>

<script>

export default {
    name: "Footer",
    props: ["users_number", "posts_number"]
}
</script>

<style scoped>

</style>