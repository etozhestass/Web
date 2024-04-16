<template>
  <div>
  <article>
    <div class="title"><a class="link" href="#" @click.p.prevent="goToPostPage">{{ post.title }}</a></div>
    <div class="information">By {{ users[post.userId].name }}</div>
    <div class="body">{{ post.text }}</div>
    <ul class="attachment">
      <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
      <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
    </ul>
    <div class="footer">
      <div class="left">
        <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
        <span class="positive-score">+173</span>
        <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
      </div>
      <div class="right">
        <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
        <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
        <a href="#">{{commentsNumber}}</a>
      </div>
    </div>
  </article>
    <div class="form" v-if="comments_shown">
      <div class="header">Write Comment</div>
      <div class="body">
        <form @submit.prevent="onWriteComment">
          <div class="field">
            <div class="name">
              <label for="text">Text:</label>
            </div>
            <div class="value">
              <textarea id="text" name="text" v-model="text"></textarea>
            </div>
          </div>
          <div class="error">{{ error }}</div>
          <div class="button-field">
            <input type="submit" value="Write">
          </div>
        </form>
      </div>
    </div>
    <section v-if="commentsNumber && comments_shown">
      <article v-for="comment in viewComments" :key="comment.id">
        <div class="information">By {{ users[comment.userId].name }}</div>
        <div class="body">{{ comment.text }}</div>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  name: "Post",
  data: function () {
    return {
      text: "",
      error: ""
    }
  },
  props: ["post", "users", "comments", "comments_shown"],
  computed: {
    viewComments: function () {
      return this.comments.filter(comment => comment.postId === this.post.id);
    },
    commentsNumber: function () {
      if (this.viewComments) {
        return this.viewComments.length;
      }
      return 0;
    }
  },
  methods: {
    goToPostPage() {
      this.text = "";
      this.$root.$emit("onChangePage", 'PostPage', {post: this.post});
    },
    onWriteComment : function () {
      this.error = "";
      this.$root.$emit("onWriteComment", this.text, this.post.id);
      this.text = "";
    }
  },
  beforeMount() {
    this.$root.$on("onWriteCommentValidationError", error => this.error = error);
  },
  beforeCreate() {
    this.text = "";
  }
}
</script>

<style scoped>
/* article */
article {
  margin-bottom: 2em;
}

article .title {
  color: var(--caption-color);
  font-weight: bold;
  font-size: 1.25rem;
}

article .title .link {
  text-decoration: none;
  color: inherit;
}

article .tags li {
  display: inline-flex;
}

article .information {
  margin-top: 0.25rem;
  font-size: 0.85rem;
  color: #888;
}

article .body {
  border-left: 4px solid var(--border-color);
  padding-left: 0.75rem;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  white-space: pre-wrap;
}

article .body p:last-child {
  margin: 0;
}

article .attachment {
  padding: 0;
  margin: 0.5rem 0 0 0;
}

article .attachment li {
  list-style: none;
  padding: 0.25rem 20px;
  margin: 0;
  background: url("../../assets/img/paperclip-16x16.png") 0 2px no-repeat;
  font-size: 0.75rem;
  color: #888;
}

article .footer {
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  overflow: hidden;
  padding: 0.1rem;
  margin-top: 0.25rem;
}

article .footer .left {
  float: left;
  padding-left: 0.5rem;
}

article .footer .left img {
  position: relative;
  top: 5px;
}

article .footer .right img {
  position: relative;
  margin-left: 0.5rem;
  top: 2px;
}

article .footer .right {
  float: right;
  font-size: 0.85rem;
  line-height: 2rem;
  padding-right: 0.5rem;
}

article .footer .positive-score {
  color: green;
  font-weight: bold;
  line-height: 1.75rem;
}

textarea {
  resize: none;
}
</style>