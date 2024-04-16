<template>
  <div id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return {
      user: null,
      users: [],
      posts: []
    }
  },
  beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"));
    }

    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    });

    axios.get("/api/1/posts").then(response => {
      this.posts = response.data;
    });
    setInterval(() => {
      axios.get("/api/1/users").then(response => {
        this.users = response.data;
      });
      axios.get("/api/1/posts").then(response => {
        this.posts = response.data;
      });
    }, 3000);

  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/jwt", {
        login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onRegister", (login, name, password) => {
      if (name === "") {
        this.$root.$emit("onRegisterValidationError", "Name is required");
        return;
      }
      if (login === "") {
        this.$root.$emit("onRegisterValidationError", "Password is required");
        return;
      }
      if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/register", {
        name, login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onRegisterValidationError", error.response.data);
      });
    });

    this.$root.$on("onWritePost", (title, text) => {
      if (this.user === null) {
        this.$root.$emit("onWritePostValidationError", "You should login before writing post");
        return;
      }
      if (title === "") {
        this.$root.$emit("onWritePostValidationError", "Title is required");
        return;
      }
      if (text === "") {
        this.$root.$emit("onWritePostValidationError", "Text is required");
        return;
      }
      const jwt = localStorage.getItem("jwt");

      axios.post("/api/1/posts/writePost", {
            title, text
          }, {params: {jwt}}
      ).then(() => {
        this.$root.$emit("onUpdatePosts");
        this.$root.$emit("onChangePage", "Index");
      }).catch(error => {
        this.$root.$emit("onWritePostValidationError", error.response.data);
      });
    });

    this.$root.$on("onWriteComment", (text, post) => {
      if (this.user === null) {
        this.$root.$emit("onWriteCommentValidationError", "You should login before writing comment");
        return;
      }
      if (post === null) {
        this.$root.$emit("onWriteCommentValidationError", "Post does not exist");
        return;
      }
      if (text === "" || !text.trim()) {
        this.$root.$emit("onWriteCommentValidationError", "Comment should not be empty");
        return;
      }
      const jwt = localStorage.getItem("jwt");

      axios.post(`/api/1/posts/writeComment?postId=${post.id}`, {
            text: text
          }, {params: {jwt}}
      ).then(response => {
        this.$root.$emit("onUpdatePosts");
        this.$root.$emit("onChangePage", "PostPage", {post: response.data});
      }).catch(error => {
        this.$root.$emit("onWriteCommentValidationError", error.response.data);
      });
    });

    this.$root.$on("onUpdatePosts", () => {
      axios.get("/api/1/posts").then(response => {
        this.posts = response.data;
      })
    });

    this.$root.$on("onJwt", (jwt) => {
      localStorage.setItem("jwt", jwt);

      axios.get("/api/1/users/auth", {
        params: {
          jwt
        }
      }).then(response => {
        this.user = response.data;
        this.$root.$emit("onChangePage", "Index");
      }).catch(() => this.$root.$emit("onLogout"))
    });

    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

  }
}
</script>

<style>
#app {

}
</style>
